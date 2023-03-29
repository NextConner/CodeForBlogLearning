package cn.joker.bytecode.bytebuddy.agent;

import cn.joker.bytecode.bytebuddy.advice.LogEnterAdvice;
import cn.joker.common.anno.EnterMethodAdvice;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.matcher.ElementMatchers;

/**
 *  动态增强
 *  1：ByteBuddy 运行时增强类 , rebase 基准代码
 *  2：install 构建一个 {@link  java.lang.instrument.Instrumentation}
 *  3：指定 Advice 类与 match 条件
 *  4：load 重定义策略使用 {@link ClassReloadingStrategy#fromInstalledAgent() }
 */

public class MethodEnterEnhancer {

    public static void enhance(Class clazz){

        ByteBuddyAgent.install();
        new ByteBuddy()
                .rebase(clazz)
                .visit(
                        Advice.to(LogEnterAdvice.class)
                                .on(ElementMatchers.isAnnotatedWith(EnterMethodAdvice.class))
                )
                .make()
                .load(Thread.currentThread().getContextClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
    }



}
