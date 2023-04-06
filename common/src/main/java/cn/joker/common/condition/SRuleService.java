package cn.joker.common.condition;

import com.pgl.erp.oss.base.api.entity.ClientVO;
import com.pgl.erp.oss.internal.condition.annotation.MatchOnCollection;
import com.pgl.erp.oss.internal.condition.annotation.MatchOnEntity;
import com.pgl.erp.oss.internal.condition.annotation.RuleScene;
import com.pgl.erp.oss.internal.condition.enums.FailureMatchPolicy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jintaoZou
 * @date 2022/10/13-14:59
 */

@RuleScene(enable = true,sceneCode = "testNameMatch", sceneName = "测试", failPolicy = FailureMatchPolicy.DO_LOG_ONLY)
@Service
public class SRuleService {

    @RuleScene(sceneName = "测试匹配方法" , sceneCode = "testNameMatch" , enable = true)
    public List<ClientVO> filterName(ClientVO clientVO,@MatchOnCollection(asReturnValue = true) List<ClientVO> nameList) {
        return nameList.stream().filter(client -> client.getName().startsWith(clientVO.getName())).collect(Collectors.toList());
    }

}
