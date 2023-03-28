package cn.joker.agent;

import cn.joker.common.anno.AgentLog;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@AgentLog
public class Demo {
    
    public static void main(String[] args) throws Exception {


        System.out.println("Start Demo main !" + new Date());
        String name = ManagementFactory.getRuntimeMXBean().getName();
        // get pid
        String pid = name.split("@")[0];
        System.out.println(pid);

        for (VirtualMachineDescriptor vmm : VirtualMachine.list()) {
            System.out.println(vmm.displayName());
        }

        while(true){
            System.out.println(new Demo().hello(System.currentTimeMillis()));
            TimeUnit.SECONDS.sleep(10);
        }
    }

    @AgentLog
    public String  hello(Long mills){
      return "Hello demo !";
    }

}
