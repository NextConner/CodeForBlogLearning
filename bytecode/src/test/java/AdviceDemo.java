import cn.joker.bytecode.bytebuddy.agent.MethodEnterEnhancer;
import cn.joker.common.anno.EnterMethodAdvice;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;

import java.util.concurrent.TimeUnit;

@Slf4j
public class AdviceDemo {

    @SneakyThrows
    public static void main(String[] args) {

        //log config
        BasicConfigurator.configure();

        MethodEnterEnhancer.enhance(AdviceDemo.class);
        while(true) {
            TimeUnit.SECONDS.sleep(5);
            new AdviceDemo().call(System.nanoTime());
        }
    }

    @EnterMethodAdvice
    public String call(Long mills){
        log.info(" old call param value = [ {} ] ",mills);
        return "call : " + mills;
    }

}
