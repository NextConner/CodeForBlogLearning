package cn.joker.common;


import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import static java.lang.Thread.sleep;

public class AttachMain {


    public static void main(String[] args) throws Exception {

        for (VirtualMachineDescriptor vmm : VirtualMachine.list()) {
            System.out.println(vmm.displayName());
        }

        VirtualMachine vm = VirtualMachine.attach("131784");
        while(true) {
            vm.loadAgent("D:\\github\\CodeForBlogLearning\\agent\\target\\agent-jar-with-dependencies.jar");
            vm.detach();
            sleep(3000);
        }
    }

}
