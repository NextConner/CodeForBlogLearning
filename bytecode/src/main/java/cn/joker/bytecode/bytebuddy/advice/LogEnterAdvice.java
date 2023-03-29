package cn.joker.bytecode.bytebuddy.advice;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 *  1. 运行时方法定义 advice
 *  2. 注解类似{@link net.bytebuddy.implementation.bind.annotation.AllArguments}
 *  3. {@link  RuntimeType} 注解表示方法返回值类型运行时确定
 */

public class LogEnterAdvice {

    public static Logger logger = LoggerFactory.getLogger(LogEnterAdvice.class);

    @RuntimeType
    @Advice.OnMethodEnter
    public static Object  logEnter(
            @Advice.Origin Method method,
            @Advice.AllArguments(readOnly = false , typing = Assigner.Typing.DYNAMIC)
                                     Object... args){

        logger.info("before advised method [ {} ] params value [ {} ] !",method.getName() , args[0].toString());
        Long startMillis = System.currentTimeMillis();
        logger.info("after advised method [ {} ] params value [ {} ] !",method.getName() , startMillis);
        return startMillis;
    }


}
