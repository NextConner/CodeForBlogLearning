package cn.joker.common.condition;

import com.pgl.erp.common.enums.CurrModeEnum;
import com.pgl.erp.common.util.EnvUtil;

public class InternalConditionUtil {

    public static boolean ifInternalCalcTestMode() {
        return EnvUtil.ifLocalStartup() && EnvUtil.currModeEnum== CurrModeEnum.INTERNAL_CALC_TEST;
    }
}
