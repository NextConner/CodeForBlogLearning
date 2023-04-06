package cn.joker.common.condition;

import javax.interceptor.InvocationContext;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author jintaoZou
 * @date 2022/9/27-14:36
 */
public abstract class AnnotatedInterceptor<A extends Annotation> implements Interceptor {

    /**
     * 当前绑定的类型
     */
    private final Class<A> interceptorBindingType;

    //serviceLoader 机制加载实现类
//    private Interceptor[] interceptors = findInterceptors();

    //注解类型和Interceptor 映射
    private Map<Class<A>, List<Interceptor>> interceptorMap = new HashMap<>();

    public AnnotatedInterceptor(Class<A> interceptorBindingType) {
        this.interceptorBindingType = interceptorBindingType;
    }

    public A findInterceptorBind(InvocationContext invocationContext) {
        return invocationContext.getMethod().getAnnotation(interceptorBindingType);
    }


    public void bindAnnotationWithInterceptor() {

//        for (Interceptor interceptor : interceptors) {
//            Type genericInterface = interceptor.getClass().getGenericInterfaces()[0];
//            if (genericInterface.getTypeName().equals(interceptorBindingType)) {
//                interceptorMap.compute(interceptorBindingType, (annoType, interceptors) -> {
//                    List<Interceptor> list = interceptorMap.getOrDefault(annoType, new ArrayList<>());
//                    list.add(interceptor);
//                    return list;
//                });
//            }
//        }
    }


}
