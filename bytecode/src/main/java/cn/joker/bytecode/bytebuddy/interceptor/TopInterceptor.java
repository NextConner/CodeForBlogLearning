package cn.joker.bytecode.bytebuddy.interceptor;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.implementation.bind.annotation.This;

import java.util.concurrent.Callable;

public class TopInterceptor {

    public Object handle(@SuperCall Callable superCall , @This Object currentObj , @AllArguments Object[] args){

        try {
            System.out.println("enter entry interceptor!");
            return superCall.call();
        }catch (Exception e){
            return null;
        }finally {
            System.out.println("will out of  entry interceptor!");
        }
    }

}
