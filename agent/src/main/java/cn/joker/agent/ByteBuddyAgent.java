package cn.joker.agent;

import cn.joker.common.advice.impl.LogMethodInfoAdvice;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.Installer;
import org.apache.log4j.BasicConfigurator;

import java.lang.instrument.Instrumentation;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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


        log.info(" attach agent main args : {}" , arguments);

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){

            String args = scanner.nextLine();
            if(args.equals("exit")){
                log.info(" exit agent main command !");
                LogMethodInfoAdvice.printCounter =new AtomicInteger(0);
                return;
            }else{

                //TODO 处理类似 watch 的命令
                // watch cn.joker.bytecode.bytebuddy.Person -n2 -x1 -r
                args.trim();
                if(args.startsWith("watch")){

                    String[] split = args.split(" ");
                    String className = split[1];
                    if(args.contains("-n")){
                        int printTimes = Integer.valueOf(split[2].split("-n")[1]);
                        LogMethodInfoAdvice.printCounter = new AtomicInteger(printTimes);
                    }
                    if(args.contains("-x")){
                        int printParamLevel = Integer.valueOf(split[3].split("-x")[1]);
                        LogMethodInfoAdvice.printParamLevel = new AtomicInteger(printParamLevel);
                    }
                    LogMethodInfoAdvice.isPrintReturn=new AtomicBoolean(args.contains("-r"));
                    Installer.agentmain(arguments, instrumentation);
                    log.info(" attach agentmain !");
                    //do enhance
                    MethodExistEnhancer.enhance(Class.forName(className), LogMethodInfoAdvice.class);
                }else{
                    log.info(" unsupported command {} !" , args);
                    return;
                }

            }
        }
    }
}
