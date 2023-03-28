package cn.joker.agent;

import cn.joker.agent.transfs.DefaultClassFileTransformer;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class DefaultDemoAgent {

    public static void premain(String args, Instrumentation ist) {
        System.out.println(System.currentTimeMillis() + "premain!");
        //前置处理
        ist.addTransformer(new DefaultClassFileTransformer());
    }

    public static void agentmain(String args, Instrumentation ist) throws ClassNotFoundException, UnmodifiableClassException {
        System.out.println(System.currentTimeMillis() + "agentmain!");
        ist.addTransformer(new DefaultClassFileTransformer(),true);
        //
        ist.retransformClasses(Class.forName("cn.joker.agent.Demo",false,ClassLoader.getSystemClassLoader()));
    }

}
