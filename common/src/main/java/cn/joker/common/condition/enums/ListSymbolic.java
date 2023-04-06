package cn.joker.common.condition.enums;

import com.pgl.erp.oss.internal.condition.Symbolic;

import java.util.List;

/**
 * @author jintaoZou
 * @date 2022/9/30-11:01
 */
public enum ListSymbolic implements Symbolic<List, List> {

    RETAIN, UNION, MATCH, UN_MATCH;


    @Override
    public List handleCollection(List matchValue, List factValue) throws Exception {

        switch (this) {
            case RETAIN:
                matchValue.retainAll(factValue);
                break;
            case UNION:
                matchValue.addAll(factValue);
                break;
            case MATCH:
                matchValue.removeAll(factValue);
                break;

        }

        return matchValue;
    }
}
