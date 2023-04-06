package cn.joker.common.condition;

import org.slf4j.helpers.MessageFormatter;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author jintaoZou
 * @date 2022/8/17-13:54
 */
public class Utils {

    public static WeakHashMap<Class, Map<String, Method>> objMethodMap = new WeakHashMap<>();

    /**
     * 基于 Slf4j 日志格式化形式的字符串格式化方法，避免使用 String.format 时可能出现的异常
     *
     * @param msg  带有 Slf4j  格式化形式的字符串: something {} is {} !
     * @param args 实际填充进行格式化的对象数组
     * @return
     */
    public static String format(String msg, Object... args) {
        if (null == args || args.length < 1) {
            return msg;
        }
        return MessageFormatter.arrayFormat(msg, args).getMessage();
    }

    public static boolean isAllAnnotationPresent(AnnotatedElement annotatedElement, Class<? extends Annotation>... annotations) {
        return ofStream(Class.class, annotations).allMatch(annotatedElement::isAnnotationPresent);
    }

    public static Stream<Object> ofStream(Object... objects) {
        return Arrays.stream(objects);
    }

    public static <T> Stream<T> ofStream(Class<T> type, T... objects) {
        return Arrays.stream(objects);
    }

    public static boolean isAnyAnnotationPresent(AnnotatedElement annotatedElement, Class<? extends Annotation>... annotations) {
        return Arrays.stream(annotations).anyMatch(annotatedElement::isAnnotationPresent);
    }

    public static <T> T[] of(T... t) {
        return t;
    }

    public static <T> Set<T> ofSet(T... t) {
        return Arrays.asList(t).stream().collect(Collectors.toSet());
    }

    public static boolean allAssignable(Class<?> assignFom, Class... beenAssignedClasses) {
        return ofSet(beenAssignedClasses).stream().allMatch(assignFom::isAssignableFrom);
    }

    public static BeanInfo getBeanInfo(Class<?> type) throws IntrospectionException {
        objMethodMap.computeIfAbsent(type, key -> new HashMap<>());
        return Introspector.getBeanInfo(type);
    }

    public static Object getFieldValue(String fieldName, Object instance) throws IntrospectionException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {

        Map<String, Method> methodMap = objMethodMap.computeIfAbsent(instance.getClass(), aClass -> new HashMap<>());
        methodMap.computeIfAbsent(fieldName, filed -> {
            PropertyDescriptor propertyDescriptors;
            try {
                propertyDescriptors = getPropertyDescriptors(instance.getClass(), fieldName);
            } catch (IntrospectionException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            Method readMethod = propertyDescriptors.getReadMethod();
            //避免多次反射
            readMethod.setAccessible(true);
            return readMethod;
        });

        return methodMap.get(fieldName).invoke(instance);
    }

    public static PropertyDescriptor[] getPropertyDescriptors(Class<?> type) throws IntrospectionException {
        return getBeanInfo(type).getPropertyDescriptors();
    }

    public static PropertyDescriptor getPropertyDescriptors(Class<?> type, String propertyName) throws IntrospectionException, NoSuchFieldException {
        return Arrays.stream(getPropertyDescriptors(type)).filter(pd -> pd.getName().equals(propertyName)).findFirst().orElseThrow(() -> new NoSuchFieldException(propertyName));
    }

    public static <T> T[] loadService(Class<T> serviceType) {
        Spliterator<T> spliterator = ServiceLoader.load(serviceType, ClassLoader.getSystemClassLoader()).spliterator();
        return StreamSupport.stream(spliterator, false).toArray(value -> (T[]) new Object[0]);
    }


}
