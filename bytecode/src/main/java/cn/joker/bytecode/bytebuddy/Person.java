package cn.joker.bytecode.bytebuddy;


import cn.joker.common.anno.AgentLog;

// cn.joker.bytecode.bytebuddy.Person

public class Person {

    @AgentLog
    public String hello(Long mills) {
        return "Hello demo :  " + mills;
    }

}
