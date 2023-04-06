package cn.joker.common.condition;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.pgl.erp.common.data.entity.BaseEntity;
import com.pgl.erp.oss.internal.condition.annotation.MatchOnCollection;
import com.pgl.erp.oss.internal.condition.annotation.MatchOnEntity;
import com.pgl.erp.oss.internal.condition.annotation.RuleScene;
import com.pgl.erp.oss.internal.condition.enums.LogicSymbolic;
import com.pgl.erp.oss.internal.condition.rule.MatchableRule;
import com.pgl.erp.oss.internal.condition.rule.SimpleRule;
import com.pgl.erp.oss.internal.data.entity.TSRuleInfo;
import com.pgl.erp.oss.internal.data.entity.TSSceneRule;
import com.pgl.erp.oss.internal.data.mapper.TSRuleInfoMapper;
import com.pgl.erp.oss.internal.data.mapper.TSSceneRuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * @author jintaoZou
 * @date 2022/9/27-14:27
 */
@Slf4j
@Interceptor
public class RuleSceneInterceptor extends AnnotatedInterceptor<RuleScene> {


    final private TSSceneRuleMapper tsSceneRuleMapper;

    final private TSRuleInfoMapper tsRuleInfoMapper;
    //对象代理映射
    public static Map<Object, InvocationContext> objectContextMap = new HashMap<>();

    public RuleSceneInterceptor(Class<RuleScene> interceptorBindingType, TSSceneRuleMapper sceneRuleRepository, TSRuleInfoMapper ruleInfoRepository) {
        super(interceptorBindingType);
        this.tsSceneRuleMapper = sceneRuleRepository;
        this.tsRuleInfoMapper = ruleInfoRepository;
    }


    public Object intercept(InvocationContext context) throws Throwable {

        Object target = context.getTarget();
        objectContextMap.computeIfPresent(target, (o, invocationContext) -> context);

        RuleScene ruleScene = findInterceptorBind(context);
        String sceneCode = ruleScene.sceneCode();

        Method method = context.getMethod();
        //避免需要对方法进行多次检查，提高运行效率
        method.setAccessible(true);

        Object result = null;
        try {
            LambdaQueryWrapper<TSSceneRule> wrapper = new LambdaQueryWrapper<TSSceneRule>().eq(TSSceneRule::getSceneCode, sceneCode);
            Optional<List<TSSceneRule>> sceneOptional = Optional.ofNullable(tsSceneRuleMapper.selectList(wrapper));
//
            if (sceneOptional.isPresent() && !CollectionUtils.isEmpty(sceneOptional.get())) {

                List<TSSceneRule> sceneRuleList = sceneOptional.get();
                Set<String> ruleIdSet = sceneRuleList.stream().map(tsSceneRule -> tsSceneRule.getStartRuleId()).collect(Collectors.toSet());
                //查找规则集合
                LambdaQueryWrapper<TSRuleInfo> in = new LambdaQueryWrapper<TSRuleInfo>().in(TSRuleInfo::getId, ruleIdSet);
                List<TSRuleInfo> ruleInfoList = tsRuleInfoMapper.selectList(in);
                if (CollectionUtils.isEmpty(ruleInfoList)) {
                    throw new IllegalArgumentException(" No matchable rules !");
                }
                //初始化规则信息
                initRules(ruleInfoList);
                //配置起点规则
                MatchableRule startRule = configRule(sceneRuleList);
                Parameter[] parameters = method.getParameters();
                //获取作为条件输入的参数对象
                Parameter[] conditionObjects = Arrays.stream(parameters)
//                        .filter(param -> Utils.isAnyAnnotationPresent(param, MatchOnEntity.class, MatchOnCollection.class))
                        .collect(Collectors.toList()).toArray(new Parameter[0]);

                //匹配并返回结果
                result = doMatch(startRule, conditionObjects, context.getParameters());
            } else {
                //找不到对应的规则配置时，调用方法的默认实现
                throw new IllegalArgumentException(" No matchable rules !");
            }

            return result;
        } catch (Exception e) {
            log.error("occur exception : {} ", e);
            //发生异常时使用原本逻辑进行处理
            result = method.invoke(context.getTarget(), context.getParameters());
        }

        return result;
    }


    static WeakHashMap<String, SimpleRule> ruleMap = new WeakHashMap<>(256);

    /**
     * 进行条件匹配的方法
     *
     * @param matchableRule
     * @param parameters
     * @return
     */
    private Object doMatch(MatchableRule matchableRule, Parameter[] parameters, Object[] params) throws Exception {

        List singleMatchList = new ArrayList<>();
        AtomicReference<Object> returnObject = new AtomicReference<>();
        List listMatch = null;
        MatchOnCollection matchOnCollection = null;

        for (int i = 0; i < parameters.length; i++) {

            Parameter param = parameters[i];
            //单个对象
            if (param.isAnnotationPresent(MatchOnEntity.class)) {
                MatchOnEntity matchOn = param.getAnnotation(MatchOnEntity.class);
                if (returnObject.get() == null && matchOn.asReturnValue()) {
                    //当前值，匹配通过时会作为返回值使用
                    returnObject.set(params[i]);
                }
                singleMatchList.add(params[i]);
            }
            //集合
            if (param.isAnnotationPresent(MatchOnCollection.class)) {
                matchOnCollection = param.getAnnotation(MatchOnCollection.class);
                if (returnObject.get() == null && matchOnCollection.asReturnValue()) {
                    //当前值，匹配通过时会作为返回值使用
                    returnObject.set(params[i]);
                }
                listMatch = (List) params[i];
            }
        }

        //匹配方法
        Predicate<List> matchPredicate = list -> {
            try {
                return matchableRule.fullCondition(list.toArray());
            } catch (Exception e) {
                throw new RuntimeException();
            }
        };

        if (null == listMatch || listMatch.size() < 1) {
            if (matchPredicate.test(singleMatchList)) {
                if (returnObject.get() != null) {
                    return returnObject.get();
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return matchListRules(listMatch, singleMatchList, matchPredicate, matchOnCollection);
        }
    }

    public List matchListRules(List matchList, List listBeMatch, Predicate<List> matchPredicate, MatchOnCollection matchOnCollection) throws Exception {

        List result = new ArrayList(matchList);
        for (Object element : result) {
            listBeMatch.add(element);
            if (!matchPredicate.test(listBeMatch)) {
                matchList.remove(element);
            }
            listBeMatch.remove(listBeMatch.size() - 1);
        }
        return matchList;
    }

    //组装基础条件
    private void initRules(List<TSRuleInfo> ruleInfoList) {

        ruleInfoList.forEach(ruleInfo -> {
            SimpleRule simpleRule = new SimpleRule();
            simpleRule.description(ruleInfo.getRuleName())
                    .matchSymbolic(Symbolic.valueOf(ruleInfo.getMatchSymbolic()))
                    .fieldType(() -> {
                        String fieldType = ruleInfo.getFieldType();
                        try {
                            return getClass().getClassLoader().loadClass(fieldType);
                        } catch (ClassNotFoundException e) {
                            throw new IllegalArgumentException(String.format(" class %s is not a valid class ! ", fieldType));
                        }
                    })
                    .targetType(() -> {
                        String targetType = ruleInfo.getTargetType();
                        try {
                            return getClass().getClassLoader().loadClass(targetType);
                        } catch (ClassNotFoundException e) {
                            throw new IllegalArgumentException(String.format(" class %s is not a valid class ! ", targetType));
                        }
                    })
                    .targetFieldName(ruleInfo.getTargetFieldName())
                    .matchAndReturn(ruleInfo.getMatchAndReturnField())
                    .matchValue(Optional.ofNullable(ruleInfo.getMatchValue()));
            ruleMap.put(ruleInfo.getId(), simpleRule);
        });

    }

    //组装条件逻辑
    private MatchableRule configRule(List<TSSceneRule> sceneRules) {

        //将规则按照优先级排序
        sceneRules.sort(TSSceneRule::compareTo);
        MatchableRule headerRule = null;
        //TODO 处理场景下多个并行规则的逻辑，规则入口不同影响执行效率
        for (TSSceneRule rule : sceneRules) {
            SimpleRule startRule = ruleMap.get(rule.getStartRuleId());
            startRule.logicSymbolic(LogicSymbolic.valueOf(rule.getLogicSymbolic()));
            if (headerRule == null) {
                headerRule = new MatchableRule(startRule);
            }
            headerRule.configNext(new MatchableRule(startRule));
            headerRule.configNext(new MatchableRule(ruleMap.get(rule.getNextRuleId())));
        }
        return headerRule;
    }

}