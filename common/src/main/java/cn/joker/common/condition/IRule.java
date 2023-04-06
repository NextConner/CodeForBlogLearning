package cn.joker.common.condition;


import com.pgl.erp.oss.internal.condition.enums.*;

import java.util.Optional;

/**
 * 行为抽象
 *
 * @author jintaoZou
 * @date 2022/8/16-16:35
 */
public interface IRule {


    /**
     * 当前规则条件是否满足
     *
     * @return true : 条件匹配; false : 条件不匹配
     * @throws Exception 处理条件时的反射异常
     */
    default Optional condition(Object objectToMatch){
        return Optional.empty();
    }

    /**
     * 作为当前规则的平级规则 , 通过 {@link LogicSymbolic} 处理
     *
     * @return
     */
    default IRule next() {
        return null;
    }

    default IRule child() {
        return null;
    }

    /**
     * 规则外的附加条件 , 未实现的情况下默认为 true
     *
     * @return
     */
    default boolean additionalCondition(Object objectToMatch) {
        return null != objectToMatch;
    }

    /**
     * 复合规则校验逻辑
     *
     * @return
     * @throws Exception
     */
    default Boolean fullCondition(Object... objectsToMatch) {

        boolean result;
        if (null != objectsToMatch && objectsToMatch.length > 0) {
            for (Object objectToMatch : objectsToMatch) {
                result = isSuccess(condition(objectToMatch)) && additionalCondition(objectToMatch);
                if (!result) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    default boolean isSuccess(Optional optional) {
        if (optional.isPresent()) {
            Object getObject = optional.get();
            if (getObject instanceof Boolean) {
                return (Boolean) getObject;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

}
