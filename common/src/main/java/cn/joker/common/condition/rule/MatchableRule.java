package cn.joker.common.condition.rule;

import com.pgl.erp.oss.internal.condition.IRule;
import com.pgl.erp.oss.internal.condition.Symbolic;
import com.pgl.erp.oss.internal.condition.Utils;
import com.pgl.erp.oss.internal.condition.enums.LogicSymbolic;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.Optional;

/**
 * 实现 Condition 方法
 *
 * @author jintaoZou
 * @date 2022/9/23-16:05
 */
@Slf4j
public class MatchableRule extends SimpleRule {


    private SimpleRule delegate;

    /**
     * 下一个条件
     */
    private MatchableRule next;

    /**
     *
     */
    private Object matchObject;

    public SimpleRule getNext() {
        return next;
    }

    public void setNext(MatchableRule next) {
        this.next = next;
    }

    @Override
    public IRule next() {
        return this.getNext();
    }

    public Object getMatchObject() {
        return matchObject;
    }

    //从外部提供待过滤的对象
    public void setMatchObject(Object matchObject) {
        this.matchObject = matchObject;
    }


    public MatchableRule(SimpleRule simpleRule) {
        this.delegate = simpleRule;
    }

    public MatchableRule(String description, Symbolic matchSymbolic, LogicSymbolic logicSymbolic, String targetType, String targetFieldName, String fieldType, String matchAndReturn) throws ClassNotFoundException {
        delegate = new SimpleRule();
        delegate.description(description)
                .matchSymbolic(matchSymbolic)
                .logicSymbolic(logicSymbolic)
                .targetType(() -> {
                    try {
                        return getClass().getClassLoader().loadClass(targetType);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(String.format(" class %s is not a valid class! ", targetType));
                    }
                })
                .targetFieldName(targetFieldName)
                .fieldType(() -> {
                    try {
                        return getClass().getClassLoader().loadClass(fieldType);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(String.format(" class %s is not a valid class! ", targetType));
                    }
                })
                .matchAndReturn(matchAndReturn);

    }


    @Override
    public Optional condition(Object objectToMatch) {


        Optional result = Optional.empty();
        try {
            if (null != objectToMatch) {
                Class<?> targetType = delegate.getTargetType();
                if (!targetType.equals(objectToMatch.getClass())) {
                    //对象不为空,但不属于当前规则需要匹配的对象,进行下一条规则匹配
                    result = Optional.of(true);
                }
            } else {
                return Optional.of(new IllegalArgumentException("income match object is null!"));
            }
            if (!result.isPresent()) {
                PropertyDescriptor pd = Utils.getPropertyDescriptors(objectToMatch.getClass(), delegate.getFieldName());
                Class<?> fieldType = pd.getPropertyType();

                Object factValue = Utils.getFieldValue(delegate.getFieldName(), objectToMatch);

                //计算当前条件是否匹配
                if (fieldType.isAssignableFrom(factValue.getClass()) && null != delegate.getMatchSymbolic()) {
                    Object matchValue = delegate.getMatchValue();
                    Boolean conditionMatch = delegate.getMatchSymbolic().handle(matchValue, factValue);
                    result = Optional.of(conditionMatch);
                    if (conditionMatch && StringUtils.isNotBlank(delegate.getMatchAndReturnField())) {
                        //TODO  获取是否存在需要传递的值
                        String returnFieldName = delegate.getMatchAndReturnField();
                        Object value = Utils.getFieldValue(returnFieldName, objectToMatch);
                        //TODO 处理异常
                        if (null != value) {
                            result = Optional.of(value);
                        }
                    }

                } else {
                    //事实字段值类型不匹配期望值类型
                    throw new IllegalArgumentException(String.format("条件值: %s 无法匹配实际值: %s ,请检查条件配置!", getMatchValue(), factValue));
                }
            }
            //判断是否存在相邻的条件
            if (result.isPresent() && null != next()) {
                fillMatchIncome(result.get(), next);
                result = Optional.of(delegate.getLogicSymbolic().handle(true, next, objectToMatch));
            }
//            log.debug("result : {}", result);
        } catch (Throwable throwable) {
            log.error("{}", throwable);
            //TODO 处理并传递异常
            log.error(Utils.format(" rule {} match {} occurs exception ! ",
                    this.delegate.getDescription(), throwable));
            return Optional.of(throwable);
        }
        return result;
    }

    /**
     * 判断是否需要传递上一个规则的匹配值
     *
     * @param lastResult
     * @param nextRule
     */
    void fillMatchIncome(Object lastResult, SimpleRule nextRule) {

        if (lastResult instanceof Throwable) {
            //发生异常
            return;
        } else if (lastResult instanceof Boolean) {
            return;
        } else if (null != lastResult) {
            nextRule.setMatchValue(lastResult);
        }
    }

    public MatchableRule configNext(MatchableRule nextRule) {
        if (this.getNext() == null) {
            this.setNext(nextRule);
            return this;
        } else if (!this.getNext().equals(nextRule)) {
            return configNext(this.next);
        } else {
            return this.next;
        }
    }

}