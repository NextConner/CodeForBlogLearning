package cn.joker.common.condition.annotation;

import com.pgl.erp.oss.internal.condition.enums.ListSymbolic;

import java.lang.annotation.*;

/**
 *
 * @author jintaoZou
 * @date 2022/9/22-16:02
 */

@Target({ ElementType.METHOD , ElementType.FIELD , ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MatchOnEntity
public @interface MatchOnCollection {


    /**
     * 注解在 {@link java.util.Collection} 类型的参数上
     *  false : 集合中的每一个元素作为一次匹配的输入
     *  true: 整个集合作为一次匹配的输入
     * @return
     */
    boolean isMatchAll() default false;

    /**
     * 当前输入是否作为返回值
     * @return
     */
    boolean asReturnValue() default  false;

    //保留匹配的元素
    ListSymbolic listSymbolic() default ListSymbolic.MATCH;

}
