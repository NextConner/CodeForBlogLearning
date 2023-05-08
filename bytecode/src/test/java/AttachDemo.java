import cn.joker.common.command.Command;
import cn.joker.common.tcp.client.TcpClient;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import io.netty.channel.Channel;
import jdk.internal.reflect.Reflection;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AttachDemo {

    public static void main(String[] args) throws Exception {

        System.out.println(Reflection.getCallerClass().getSimpleName());

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
                                TimeUnit.SECONDS.sleep(2);
                                tcpClient.shutdown();
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
