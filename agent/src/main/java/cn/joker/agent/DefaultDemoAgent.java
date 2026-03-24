package cn.joker.agent;

import cn.joker.agent.transfs.DefaultClassFileTransformer;
import cn.joker.common.anno.AgentLog;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.Installer;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.SuperMethodCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.security.ProtectionDomain;

import static net.bytebuddy.matcher.ElementMatchers.isAnnotatedWith;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class DefaultDemoAgent {


    //jvm 启动前处理
    public static void premain(String args, Instrumentation ist) {
        System.out.println(System.currentTimeMillis() + "premain!");
        //实现 java.lang.instrument.ClassFileTransformer 类的前置处理
//        ist.addTransformer(new DefaultClassFileTransformer());

        //添加 byte buddy 实现的前置处理agent
        new AgentBuilder.Default()
                .type(ElementMatchers.isAnnotatedWith(AgentLog.class))
                .transform(
                        (builder, typeDescription, classLoader, javaModule, protectionDomain)
                        -> builder.method(ElementMatchers.isAnnotatedWith(AgentLog.class))
                        .intercept(MethodDelegation.to(AgentLogInterceptor.class))
                ).installOn(ist);
    }

    //运行时动态 attach
    public static void agentmain(String args, Instrumentation ist) throws ClassNotFoundException, UnmodifiableClassException {
        System.out.println(System.currentTimeMillis() + "agentmain!");



    }

}
