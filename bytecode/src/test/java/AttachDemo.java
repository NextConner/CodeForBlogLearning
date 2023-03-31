import com.sun.tools.attach.VirtualMachine;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AttachDemo {

    public static void main(String[] args) throws Exception {

        String agent = "D:\\github\\CodeForBlogLearning\\agent\\target\\agent-jar-with-dependencies.jar";
        File file = new File("D:\\pid");
        List list = IOUtils.readLines(new FileInputStream(file), StandardCharsets.UTF_8.displayName());
        VirtualMachine vmm = VirtualMachine.attach(list.get(0).toString());
        vmm.loadAgent(agent);
        vmm.detach();
    }

}
