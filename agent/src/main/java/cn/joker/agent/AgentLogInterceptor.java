package cn.joker.agent;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.apache.commons.lang3.time.StopWatch;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@Slf4j
public class AgentLogInterceptor {

    public static Object log(@SuperCall Callable callable, @Origin Method method, @AllArguments Object... args) {

        Object result = null;
        String argsStr = "";
        ThreadLocal<StopWatch> local = new ThreadLocal<>();
        try {
            StopWatch stopWatch = new StopWatch(Thread.currentThread() + " : count");
            local.set(stopWatch);
            stopWatch.start();
            if (method.getParameterCount() > 0) {
                for (int i = 0; i < method.getParameterCount(); i++) {
                    if (null != args[i]) {
                        argsStr += " " + method.getParameters()[i].getName() + ":" + args[i] + ";";
                    }
                }
            }
            log.info("call method [{}] with args : [{}] !", method.getName(), argsStr);
            System.out.println(String.format("call method [ %s ] with args : [ %s ] !", method.getName(), argsStr));
            result = callable.call();
        } catch (Exception e) {
            System.out.println(String.format("call method [ %s ] with args : [ %s ] ,  occurs exception : [ %s ]  !", method.getName(), argsStr,e.getMessage()));
            log.error(" call method [{}] with args : [{}] , occurs exception : [{}] !", method.getName(), argsStr, e.getMessage());
        } finally {
            log.info("end call method [{}] with args : [{}] ,result is : [{}] !", method.getName(), argsStr, result);
            local.get().stop();
            log.info("call method [{}] cost time : [{}] mills!", method.getName(), local.get().getNanoTime());
            System.out.println(String.format("call method [ %s ] result is : [ %s ] cost time : [ %s ] nanos!", method.getName(), result,local.get().getTime()));
        }
        return result;
    }

}
