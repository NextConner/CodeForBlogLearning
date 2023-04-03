package cn.joker.agent;

import cn.joker.common.command.Command;
import cn.joker.common.tcp.server.SimpleTcpServer;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.Installer;
import org.apache.log4j.BasicConfigurator;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class MyByteBuddyAgent {

    static {
        //log config
        BasicConfigurator.configure();
    }


    public static Instrumentation getInstrumentation() {
        return Installer.getInstrumentation();
    }

    public static void premain(String arguments, Instrumentation instrumentation) {
//        Installer.premain(arguments, instrumentation);
        log.info(" attach premain !");
    }

    static AtomicBoolean start = new AtomicBoolean(false);

    public static void agentmain(String arguments, Instrumentation instrumentation) throws Exception {

        log.info(" attach agent main args : {}", arguments);

        if(arguments.startsWith(Command.ATTACH.name()) && !start.get()){
           final int port = Integer.valueOf(arguments.split(":")[1]);
            new Thread(() -> SimpleTcpServer.start(port,instrumentation)).start();
            start.set(true);
        }else{
            System.out.println(arguments);
        }

    }

}
