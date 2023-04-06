package cn.joker.common.condition.enums;

import com.pgl.erp.oss.internal.data.entity.TBInternalCalculateOrderResult;

import java.util.Arrays;
import java.util.List;

/**
 * @author jintaoZou
 * @date 2022/10/14-17:26
 */
public enum RuleEntityInfo {

    CLIENT_ORDER(TBInternalCalculateOrderResult.class , "内核订单对象");

    private Class<?> type;
    private String describe;

    RuleEntityInfo(Class<?> type , String describe){
        this.type = type;
        this.describe = describe;
    }

    public static RuleEntityInfo findByType(Class<?> type){
        return Arrays.stream(values()).filter( en -> en.type.equals(type)).findFirst().get();
    }

}
