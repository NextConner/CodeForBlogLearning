package cn.joker.bytecode.bytebuddy;


import cn.joker.common.anno.AgentLog;

public class Person {

    @AgentLog
    public String hello() {
        return "Hello demo : 18 ";
    }

}
