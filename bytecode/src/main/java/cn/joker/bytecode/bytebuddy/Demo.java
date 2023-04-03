package cn.joker.bytecode.bytebuddy;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Demo {

    public static void main(String[] args) throws Exception {

//        //log config
//        BasicConfigurator.configure();

        String name = ManagementFactory.getRuntimeMXBean().getName();
        // get pid
        String pid = name.split("@")[0];
        System.out.println(pid);

        File file = new File("D:\\pid");
        file.deleteOnExit();
        file.createNewFile();
        IOUtils.write(pid, new FileOutputStream(file), StandardCharsets.UTF_8.displayName());

        new Thread(() -> {
            while (true) {
                log.info("  --- {}  ", new Person().hello(System.currentTimeMillis()));
                try {
                    TimeUnit.SECONDS.sleep(20);
                } catch (Exception e) {

                }
            }
        }).start();


    }


}
