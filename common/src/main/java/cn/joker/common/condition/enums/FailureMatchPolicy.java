package cn.joker.common.condition.enums;

/**
 * 规则匹配失败的处理策略
 *
 * @author jintaoZou
 * @date 2022/9/22-15:16
 */
public enum FailureMatchPolicy {

    STOP, STOP_THROWS, HANDLE_METHOD, CALL_OLD_METHOD , DO_LOG_ONLY;


}
