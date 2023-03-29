package cn.joker.bytecode.bytebuddy.interceptor;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;


/**
 *  顶级 Interceptor 接口 , 通过 {@link StopWatch} 统计方法调用耗时
 */
public interface TopInterceptor {

    Logger logger = LoggerFactory.getLogger(TopInterceptor.class);

    @RuntimeType
    static Object handle(@SuperCall Callable superCall, @Origin Method method, @AllArguments Object... args) {

        logger.info(" handle method {} !", method.getName());
        Object result;
        String argsStr = "";
        ThreadLocal<StopWatch> local = new ThreadLocal<>();
        try {
            StopWatch stopWatch = new StopWatch();
            local.set(stopWatch);
            stopWatch.start();
            if (method.getParameterCount() > 0) {
                for (int i = 0; i < method.getParameterCount(); i++) {
                    if (null != args[i]) {
                        argsStr += " " + method.getParameters()[i].getName() + ":" + args[i] + ";";
                    }
                }
            }
            result = superCall.call();
            stopWatch.stop();
            logger.debug(" call method [ {} ] with args [ {} ] cost nanos time : [ {} ] ",method.getName() , argsStr,local.get().getNanoTime());
            logger.info(" call method [ {} ] with args [ {} ] ,result is [ {} ] !", method.getName(), argsStr, result);
        } catch (Exception e) {
            logger.error(" call method [ {} ] with args [ {} ] error [ {} ] !", method.getName(), argsStr, e.getMessage());
            return null;
        }
        return result;
    }

}
