package cn.joker.common.condition.enums;

import com.pgl.erp.oss.internal.condition.Symbolic;

/**
 * @author jintaoZou
 * @date 2022/8/17-14:33
 */
public enum NumericSymbolic implements Symbolic<Number,Number> {
    BIGGER , LESSER ;

    @Override
    public boolean isNumeric() {
        return true;
    }

    @Override
    public boolean handle(Number matchValue, Number factValue) {
        switch (this){
            case BIGGER: return matchValue.byteValue() > factValue.byteValue();
            case LESSER: return  matchValue.byteValue() < factValue.byteValue();
            default:return false;
        }
    }
}
