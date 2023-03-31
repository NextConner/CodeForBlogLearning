package cn.joker.agent;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.Installer;
import org.apache.log4j.BasicConfigurator;

import java.lang.instrument.Instrumentation;
import java.util.Scanner;

@Slf4j
public class ByteBuddyAgent {

    static {
        //log config
        BasicConfigurator.configure();
    }

    public static Instrumentation getInstrumentation() {
        return Installer.getInstrumentation();
    }

    public static void premain(String arguments, Instrumentation instrumentation) {
        Installer.premain(arguments, instrumentation);
        log.info(" attach premain !");
    }

    @SneakyThrows
    public static void agentmain(String arguments, Instrumentation instrumentation) {

        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNextLine()){
            String clazz = scanner.nextLine();
            Installer.agentmain(arguments, instrumentation);
            log.info(" attach agentmain !");
            //do enhance
            MethodExistEnhancer.enhance(Class.forName(clazz));
        }
    }
}
