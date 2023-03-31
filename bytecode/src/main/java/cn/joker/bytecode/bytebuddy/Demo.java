package cn.joker.bytecode.bytebuddy;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Demo {

    public static void main(String[] args) throws Exception {

        //log config
        BasicConfigurator.configure();

        String name = ManagementFactory.getRuntimeMXBean().getName();
        // get pid
        String pid = name.split("@")[0];
        System.out.println(pid);
        new Thread(() -> {
            Person demo = new Person();
            while (true) {
                log.info("  --- {}  ", demo.hello(System.currentTimeMillis()));
                try {
                    TimeUnit.SECONDS.sleep(20);
                } catch (Exception e) {

                }
            }
        }).start();


    }



}
