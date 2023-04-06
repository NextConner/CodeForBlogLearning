package cn.joker.common.condition.enums;

import com.pgl.erp.oss.internal.condition.Symbolic;

import java.util.Collection;

/**
 * @author jintaoZou
 * @date 2022/9/30-10:56
 */
public enum CollectionSymbolic implements Symbolic<Collection, Collection> {

    LIST, SET;

    @Override
    public boolean handle(Collection matchValue, Collection factValue) throws Exception {
        switch (this){
            case LIST : LIST.handle(matchValue,factValue);break;
            case SET: SET.handle(matchValue,factValue);break;
            default: return false;
        }
        return false;
    }
}
