package cn.joker.common.condition.rule;

import com.pgl.erp.oss.internal.condition.IRule;
import com.pgl.erp.oss.internal.condition.Symbolic;
import com.pgl.erp.oss.internal.condition.enums.LogicSymbolic;

/**
 * @author jintaoZou
 * @date 2022/9/23-15:41
 */
public abstract class BasicAbstractRule implements IRule {

    /**
     * 获取规则计算操作
     * @return
     */
    public abstract Symbolic getMatchSymbolic();

    /**
     * 获取规则传递操作
     * @return
     */
    public abstract LogicSymbolic getLogicSymbolic();

    /**
     * 规则描述信息
     * @return
     */
    public abstract String getDescription();

    /**
     * 目标对象类型
     * @return
     */
    public abstract Class<?> getTargetType();

    /**
     * 目标字段名称
     * @return
     */
    public abstract String getFieldName();

    /**
     * 目标字段类型
     * @return
     */
    public abstract Class<?> getFieldType();

    /**
     * 匹配值来源 ： 1. 规则配置值   2.从前一个规则匹配结果传递过来的值
     * @param valueToMatch
     */
    public abstract void setMatchValue(Object valueToMatch);

    /**
     * 获取需要匹配的规则值
     * @return
     */
    public abstract Object getMatchValue();

}
