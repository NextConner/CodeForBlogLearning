package cn.joker.common.condition.enums;

import com.pgl.erp.oss.internal.condition.Symbolic;

/**
 * @author jintaoZou
 * @date 2022/8/17-14:38
 */
public enum CharSymbolic implements Symbolic<String, String> {
    CONTAINS, IN, START_WITH, END_WITH, EQUALS;

    @Override
    public boolean isChar() {
        return true;
    }

    @Override
    public boolean handle(String matchValue, String factValue) {
        switch (this) {
            case EQUALS:
                return matchValue.equals(factValue);
            case CONTAINS:
                return matchValue.contains(factValue);
            case IN:
                return factValue.contains(matchValue);
            case START_WITH:
                return matchValue.startsWith(factValue);
            case END_WITH:
                return matchValue.endsWith(factValue);
            default:
                return false;
        }
    }

    @Override
    public String translateName() {

        switch (this) {
            case IN:
                return "被包含";

            case START_WITH:
                return "匹配前缀";

            case CONTAINS:
                return "匹配包含";

            case EQUALS:
                return "匹配相等";

            case END_WITH:
                return "匹配后缀";
            default:
                return "未知操作";
        }
    }


}
