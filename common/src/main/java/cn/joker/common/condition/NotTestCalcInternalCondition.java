package cn.joker.common.condition;

import com.pgl.erp.common.enums.CurrModeEnum;
import com.pgl.erp.common.util.EnvUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 是否测试内核计算模式
 */
public class NotTestCalcInternalCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context,
                           AnnotatedTypeMetadata metadata) {
        return !InternalConditionUtil.ifInternalCalcTestMode();
    }
}
