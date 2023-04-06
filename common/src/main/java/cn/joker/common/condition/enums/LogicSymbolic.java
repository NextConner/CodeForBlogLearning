package cn.joker.common.condition.enums;


import com.pgl.erp.oss.internal.condition.IRule;
import com.pgl.erp.oss.internal.condition.Symbolic;
import com.pgl.erp.oss.internal.condition.Utils;

/**
 * 规则逻辑运算符
 *
 * @author jintaoZou
 * @date 2022/8/16-15:56
 */
public enum LogicSymbolic implements Symbolic<Boolean, IRule> {


    ROOT, THEN, OR, AND, END;

    @Override
    public boolean isLogic() {
        return true;
    }


    @Override
    public boolean handle(Boolean firstResult, IRule rule,Object objectToMatch) throws Exception {

        Object[] of = Utils.of(objectToMatch);

        switch (this) {
            case OR:
                return firstResult || rule.fullCondition(of);
            case AND:
                return firstResult && rule.fullCondition(of);
            case THEN :
                return firstResult ? rule.child().fullCondition(of) : false;
            default:
                return false;
        }
    }
}
