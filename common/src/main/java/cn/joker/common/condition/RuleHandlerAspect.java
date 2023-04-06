package cn.joker.common.condition;


import com.pgl.erp.oss.internal.condition.annotation.RuleScene;
import com.pgl.erp.oss.internal.data.mapper.TSRuleInfoMapper;
import com.pgl.erp.oss.internal.data.mapper.TSSceneRuleMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

/**
 * 规则方法处理切面
 *
 * @author jintaoZou
 * @date 2022/9/22-16:23
 */

@Slf4j
@Aspect
@Component
public class RuleHandlerAspect {

    RuleSceneInterceptor interceptor;

    public RuleHandlerAspect(TSSceneRuleMapper tsSceneRuleMapper, TSRuleInfoMapper tsRuleInfoMapper) {
        this.interceptor = new RuleSceneInterceptor(RuleScene.class, tsSceneRuleMapper, tsRuleInfoMapper);
    }

    @Pointcut("@annotation(com.pgl.erp.oss.internal.condition.annotation.RuleScene)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {


        log.debug("enable scene rule match pointcut!");
        Object result;
        try {
            Object target = joinPoint.getTarget();
            Object[] args = joinPoint.getArgs();
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            InvocationContext context = RuleSceneInterceptor.objectContextMap.getOrDefault(target, new ReflectiveMethodInvocationContext(target, method, args));
            result = interceptor.intercept(context);
        } catch (Throwable throwable) {
            log.error(" interceptor method error : {} ", throwable);
            result = joinPoint.proceed();
        }

        return result;
    }

}
