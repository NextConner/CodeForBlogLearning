package cn.joker.agent.transfs;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class DefaultClassFileTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        try {
            if (className.contains("Demo")) {

                final ClassPool classPool = ClassPool.getDefault();
                final CtClass demoClass = classPool.get("cn.joker.agent.Demo");
                CtMethod hello = demoClass.getDeclaredMethod("hello");
                //  setBody 方法要求方法体符合语法格式，多行需要 {} 包围
                hello.setBody(" {return \"hello byte code !\";}");
                byte[] bytes = demoClass.toBytecode();
                //移除曾加载的 demoClass 对象，重新加载
                demoClass.detach();
                return bytes;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
