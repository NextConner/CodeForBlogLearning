import com.sun.tools.attach.VirtualMachine;

public class AttachDemo {

    public static void main(String[] args) throws Exception{

        String agent = "D:\\github\\CodeForBlogLearning\\agent\\target\\agent-jar-with-dependencies.jar";

        VirtualMachine vmm = VirtualMachine.attach("139516");

        vmm.loadAgent(agent);
        vmm.detach();

    }

}
