import cn.joker.common.advice.IMethodLogAdvice;
import cn.joker.common.advice.impl.LogMethodInfoAdvice;
import cn.joker.common.anno.AgentLog;
import cn.joker.common.anno.EnterMethodAdvice;
import cn.joker.common.command.Command;
import cn.joker.common.command.LocalCommand;
import cn.joker.common.tcp.client.TcpClient;
import com.sun.jna.platform.win32.Shell32Util;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.Installer;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class AttachDemo {

    public static void main(String[] args) throws Exception {

        //log config
        BasicConfigurator.configure();


//        Scanner scanner = new Scanner(System.in);
//        if(scanner.hasNextLine()){
//            String nextLine = scanner.nextLine();
//            if(nextLine.contains("local")){
//                simpleAttach();
//            } else if(nextLine.contains("agent")){
                byteAgentAttach();
//            }else{
//                return;
//            }
//        }


    }

    //TODO error usage
    private static void byteAgentAttach() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, ClassNotFoundException, InterruptedException {

        String agent = "D:\\github\\CodeForBlogLearning\\agent\\target\\agent-jar-with-dependencies.jar";
        File file = new File("D:\\pid");
        String pid = IOUtils.readLines(new FileInputStream(file), StandardCharsets.UTF_8.displayName()).get(0).toString();
        net.bytebuddy.agent.VirtualMachine vmm = (net.bytebuddy.agent.VirtualMachine) net.bytebuddy.agent.VirtualMachine.Resolver.INSTANCE.run()
                .getMethod("attach", String.class)
                .invoke(ByteBuddyAgent.ProcessProvider.ForCurrentVm.INSTANCE.resolve(),pid);
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            int localPort = serverSocket.getLocalPort();
            serverSocket.close();

            vmm.loadAgent(agent, Command.ATTACH.name()+":" + localPort);
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(15);
                    TcpClient tcpClient = new TcpClient("127.0.0.1", localPort);
                    Channel channel = tcpClient.start(null);
                    System.out.println(" tcp client start to connect :  " + localPort);
                    Scanner scanner = new Scanner(System.in);
                    while (true) {
                        if (scanner.hasNextLine()) {
                            String nextLine = scanner.nextLine();
                            if(nextLine.equals("exit")){
                                channel.writeAndFlush(nextLine);
//                                TimeUnit.SECONDS.sleep(3);
//                                tcpClient.shutdown();
                                return;
                            }
                            channel.writeAndFlush(nextLine);
                        }
                    }
                }catch (Exception e){

                }
            }).start();

        }finally {
            vmm.detach();
        }

     }

    private static void simpleAttach() throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        String agent = "D:\\github\\CodeForBlogLearning\\agent\\target\\agent-jar-with-dependencies.jar";
        File file = new File("D:\\pid");
        List list = IOUtils.readLines(new FileInputStream(file), StandardCharsets.UTF_8.displayName());
        VirtualMachine vmm = VirtualMachine.attach(list.get(0).toString());
        vmm.loadAgent(agent, "watch cn.joker.bytecode.bytebuddy.Person -n2 -x1 -r");
        vmm.detach();

    }

}
