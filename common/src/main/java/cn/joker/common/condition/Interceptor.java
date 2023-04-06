package cn.joker.common.condition;

/**
 * @author jintaoZou
 * @date 2022/9/27-14:40
 */
public interface Interceptor {

    default Interceptor[] findInterceptors(){
        return Utils.loadService(Interceptor.class);
    }

}
