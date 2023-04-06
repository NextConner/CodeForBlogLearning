package cn.joker.common.condition.rule;

import com.pgl.erp.oss.internal.data.entity.TSRuleInfo;
import com.pgl.erp.oss.internal.data.entity.TSSceneRule;

import java.util.List;

/**
 * @author jintaoZou
 * @date 2022/9/27-13:51
 */
public class RuleSceneVO {

    private String sceneCode;

    private String sceneName;

    private String failurePolicy;

    private List<TSRuleInfo> appRules;

    private List<TSSceneRule> sceneRuleList;



}
