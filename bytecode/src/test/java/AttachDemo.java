import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class AttachDemo {

    public static void main(String[] args) throws Exception {

//        Scanner scanner = new Scanner(System.in);
//        if(scanner.hasNextLine()){
//            if(scanner.nextLine().contains("local")){
//                simpleAttach();
//            } else if(scanner.nextLine().contains("agent")){
                byteAgentAttach();
//            }
//        }


    }

    //TODO error usage
    private static void byteAgentAttach() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {

        String agent = "D:\\github\\CodeForBlogLearning\\agent\\target\\agent-jar-with-dependencies.jar";

        net.bytebuddy.agent.VirtualMachine vmm = (net.bytebuddy.agent.VirtualMachine) net.bytebuddy.agent.VirtualMachine.Resolver.INSTANCE.run()
                .getMethod("attach", String.class)
                .invoke(null, ByteBuddyAgent.ProcessProvider.ForCurrentVm.INSTANCE.resolve());

        vmm.loadAgent(agent,"watch cn.joker.bytecode.bytebuddy.Person -n2 -x1 -r");

    }

    private static void simpleAttach() throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        String agent = "D:\\github\\CodeForBlogLearning\\agent\\target\\agent-jar-with-dependencies.jar";
        File file = new File("D:\\pid");
        List list = IOUtils.readLines(new FileInputStream(file), StandardCharsets.UTF_8.displayName());
        VirtualMachine vmm = VirtualMachine.attach(list.get(0).toString());
        vmm.loadAgent(agent);
        vmm.detach();
    }

}
