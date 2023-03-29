package cn.joker.common.anno;

import java.lang.annotation.*;


/**
 * 标记接口，用来表示需要被拦截处理的方法或者类
 * RetentionPolicy 默认为 CLASS ,
 * net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith(AgentLog.class) 在默认级别下可能识别不到
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AgentLog {
}
