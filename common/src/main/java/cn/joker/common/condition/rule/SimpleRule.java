package cn.joker.common.condition.rule;

import com.pgl.erp.oss.internal.condition.Symbolic;
import com.pgl.erp.oss.internal.condition.enums.LogicSymbolic;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * 不具备行为的简单规则对象
 *
 * @author jintaoZou
 * @date 2022/9/23-15:48
 */
public class SimpleRule extends BasicAbstractRule {



    private String description;

    //匹配逻辑符
    private Symbolic matchSymbolic;
    //传递逻辑符
    private LogicSymbolic logicSymbolic;
    //匹配目标对象类型
    private Class<?> targetType;
    //匹配目标字段名
    private String targetFieldName;

    //从被匹配对象中返回此字段的值进行传递，作为下一个条件的输入
    private String matchAndReturnField;

    //匹配的字段类型
    private Class<?> fieldType;

    //规则匹配的值
    private Object matchValue;

    public SimpleRule() {

    }



    public SimpleRule description(String description) {
        this.description = description;
        return this;
    }

    public SimpleRule matchSymbolic(Symbolic matchSymbolic) {
        this.matchSymbolic = matchSymbolic;
        return this;
    }

    public SimpleRule logicSymbolic(LogicSymbolic logicSymbolic) {
        this.logicSymbolic = logicSymbolic;
        return this;
    }

    public SimpleRule targetType(Supplier<Class<?>> targetType) {
        this.targetType = targetType.get();
        return this;
    }

    public SimpleRule targetFieldName(String targetFieldName) {
        this.targetFieldName = targetFieldName;
        return this;
    }

    public SimpleRule fieldType(Supplier<Class<?>> fieldTypeSupplier) {
        this.fieldType = fieldTypeSupplier.get();
        return this;
    }

    public SimpleRule matchAndReturn(String matchAndReturnField) {
        this.matchAndReturnField = matchAndReturnField;
        return this;
    }

    public SimpleRule matchValue(Optional matchValue){
        if(matchValue.isPresent()){
            this.matchValue = matchValue.get();
        }
        return this;
    }


    @Override
    public Symbolic getMatchSymbolic() {
        return this.matchSymbolic;
    }

    @Override
    public LogicSymbolic getLogicSymbolic() {
        return this.logicSymbolic;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Class<?> getTargetType() {
        return this.targetType;
    }

    @Override
    public String getFieldName() {
        return this.targetFieldName;
    }

    @Override
    public Class<?> getFieldType() {
        return this.fieldType;
    }

    @Override
    public void setMatchValue(Object valueToMatch) {
        this.matchValue = valueToMatch;
    }

    @Override
    public Object getMatchValue() {
        return this.matchValue;
    }

    public String getMatchAndReturnField() {
        return this.matchAndReturnField;
    }

}
