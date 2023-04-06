package cn.joker.common.condition.annotation;

import java.lang.annotation.*;

/**
 * 标记注解 , 表示字段或方法的返回值是需要匹配的对象
 *
 * @author jintaoZou
 * @date 2022/9/22-16:01
 */

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER , ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MatchOnEntity {

    /**
     * 是否作为匹配的返回值
     *
     * @return
     */
    boolean asReturnValue() default false;

    boolean saveOnMatch() default  false;

}
