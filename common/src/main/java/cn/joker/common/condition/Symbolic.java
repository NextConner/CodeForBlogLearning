package cn.joker.common.condition;

import com.pgl.erp.oss.internal.condition.enums.CharSymbolic;
import com.pgl.erp.oss.internal.condition.enums.LogicSymbolic;
import com.pgl.erp.oss.internal.condition.enums.NumericSymbolic;
import com.pgl.erp.oss.internal.condition.enums.ObjectSymbolic;

import java.util.Collection;
import java.util.Collections;

/**
 * {@link LogicSymbolic} : 规则传递逻辑
 * {@link CharSymbolic} : 字符类型匹配逻辑
 * {@link NumericSymbolic} : 数字类型匹配逻辑
 * {@link ObjectSymbolic} : 对象类型匹配逻辑
 * <p>
 * 判断优先级 ： CharSymbolic > NumericSymbolic > ObjectSymbolic
 *
 * @author jintaoZou
 * @date 2022/8/15-16:51
 */
public interface Symbolic<T, R> {

    default boolean isLogic() {
        return false;
    }

    default boolean isNumeric() {
        return false;
    }

    default boolean isChar() {
        return false;
    }

    default boolean isEqual() {
        return false;
    }

    default boolean handle(T matchValue, R factValue) throws Exception {
        return false;
    }

    default Collection handleCollection(T matchValue, R factValue) throws Exception {
        return Collections.EMPTY_LIST;
    }


    default boolean handle(T matchValue, R factValue, Object objectToMatch) throws Exception {
        return false;
    }

    static Symbolic valueOf(String symbolic) {

        CharSymbolic charSymbolic = CharSymbolic.valueOf(symbolic);
        if(null != charSymbolic){
            return  charSymbolic;
        }else{
            NumericSymbolic numericSymbolic = NumericSymbolic.valueOf(symbolic);
            if(null != numericSymbolic){
                return numericSymbolic;
            }else{
                return  ObjectSymbolic.valueOf(symbolic);
            }
        }

    }

    default String translateName() {
        return "未知";
    }

}
