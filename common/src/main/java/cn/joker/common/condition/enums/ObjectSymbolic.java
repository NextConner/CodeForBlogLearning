package cn.joker.common.condition.enums;

import com.pgl.erp.oss.internal.condition.Symbolic;

/**
 * @author jintaoZou
 * @date 2022/8/17-14:58
 */
public enum ObjectSymbolic implements Symbolic<Object,Object> {

    EQAUL;

    @Override
    public boolean isEqual() {
        return true;
    }

    @Override
    public boolean handle(Object matchValue, Object factValue) {
        return matchValue.equals(factValue);
    }
}
