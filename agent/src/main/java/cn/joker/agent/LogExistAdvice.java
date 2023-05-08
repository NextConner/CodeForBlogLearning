package cn.joker.agent;

import jdk.internal.reflect.Reflection;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 1. 运行时方法定义 advice
 * 2. 注解类似{@link net.bytebuddy.implementation.bind.annotation.AllArguments}
 * 3. {@link  RuntimeType} 注解表示方法返回值类型运行时确定
 */


public class LogExistAdvice {

    public static Logger logger = LoggerFactory.getLogger(LogExistAdvice.class);

    static public Boolean run = true;

    static {
        //log config
        BasicConfigurator.configure();
    }

    @RuntimeType
    @Advice.OnMethodExit
    public static Object logEnter(
            @Advice.Return Object result,
            @Advice.Origin Method method,
            @Advice.AllArguments(readOnly = false, typing = Assigner.Typing.DYNAMIC)
            Object... args) {

        if(!run){
            return result;
        }

        if (null != args && args.length > 0) {
            for (Object par : args) {
                logger.info("param value : {}", par);
            }
        }
        logger.info(" log exist method : {}", method.getName());
        return result;
    }


}
