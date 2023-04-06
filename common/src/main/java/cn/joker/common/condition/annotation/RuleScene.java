package cn.joker.common.condition.annotation;

import com.pgl.erp.oss.internal.condition.enums.FailureMatchPolicy;
import com.pgl.erp.oss.internal.condition.enums.SceneMatchMode;

import java.lang.annotation.*;

/**
 *  1. 匹配规则使用的注解
 *
 * @author jintaoZou
 * @date 2022/9/22-15:13
 */

@Target({ElementType.TYPE , ElementType.METHOD , ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RuleScene {


    /**
     * 是否启用
     * @return
     */
    boolean enable() default  false;

    /**
     * 定义的sceneCode
     * @return
     */
    String sceneCode() default  "Not Set";

    /**
     * 定义的sceneName
     * @return
     */
    String sceneName() default  "Not Set";

    /**
     * 过滤的参数名称，
     * @return
     */
    String[] applyParams() default {};


    /**
     * 规则匹配失败时的处理 , 默认调用旧方法处理
     * @return
     */
    FailureMatchPolicy failPolicy() default FailureMatchPolicy.CALL_OLD_METHOD;


    SceneMatchMode matchMode() default SceneMatchMode.SINGLE_OBJECT;


    /**
     * 匹配结束的处理方法
     * @return
     */
    String endMethod() default "";

}
