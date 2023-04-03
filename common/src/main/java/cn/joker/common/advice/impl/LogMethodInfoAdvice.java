package cn.joker.common.advice.impl;

import cn.joker.common.advice.IMethodLogAdvice;
import com.alibaba.fastjson.JSONObject;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author jintaoZou
 * @date 2023/3/31-14:48
 */
public class LogMethodInfoAdvice implements IMethodLogAdvice {

    public static Logger logger = LoggerFactory.getLogger(LogMethodInfoAdvice.class);

    public static AtomicInteger printCounter = new AtomicInteger(1);

    public static AtomicInteger printParamLevel = new AtomicInteger(1);

    public static AtomicBoolean isPrintReturn = new AtomicBoolean(false);

    public static AtomicReference<String> methodInfo = new AtomicReference<>();



    public static  void init(int printTimes, int paramLevel, boolean printReturn) {
        printCounter = new AtomicInteger(printTimes);
        printParamLevel = new AtomicInteger(paramLevel);
        isPrintReturn = new AtomicBoolean(printReturn);
    }

    @Override
    public int printTimes() {
        return printCounter.get();
    }

    @Override
    public int printParamLevel() {
        return printParamLevel.get();
    }


    @Override
    public Object innerAdvice() {

        return methodInfo.get();
    }

    @Advice.OnMethodExit
    @RuntimeType
    public static void enhance(@Advice.Return Object result,
                                @Advice.Origin Method method,
                                @Advice.AllArguments(readOnly = false, typing = Assigner.Typing.DYNAMIC)
                                             Object... args ) {

        while(printCounter.decrementAndGet() < 0){
            return;
        }

        JSONObject json = new JSONObject();
        json.put("method",method.getDeclaringClass().getName() +"#"+ method.getName());
        if(isPrintReturn.get()){
            json.put("return",result);
        }
        if(printParamLevel.get()>0){
            int paramLevel = printParamLevel.get();
            JSONObject params = new JSONObject();
            for(int i=0;i<method.getParameterCount();i++){
                //集合类参数
                Object param = args[i];
                if(paramLevel>1){
                    if(paramLevel-- > 1){
                        if(Collection.class.isAssignableFrom(args[i].getClass())){
                            String string = ((Collection) param).stream().collect(Collectors.joining(" , ")).toString();
                            params.put(method.getParameters()[i].getName() , string);
                        }else if(Map.class.isAssignableFrom(args[i].getClass())){
                            params.put(method.getParameters()[i].getName() , new JSONObject((Map)param).toJSONString());
                        }else {
                            params.put(method.getParameters()[i].getName() ,param.toString());
                        }
                    }
                }

                //TODO simple print args array
                params.put(method.getParameters()[i].getName() ,param.toString());
            }
            json.put("params",params.toJSONString());
        }

        logger.info(json.toJSONString());

    }
}
