package org.youngmonkeys.eun.app.request;

import lombok.Data;
import org.youngmonkeys.eun.app.entity.EzyDataMember;
import org.youngmonkeys.eun.common.constant.ParameterCode;

@Data
public class ChangeLeaderClientOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.LeaderClientPlayerId)
    int leaderClientPlayerId;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (leaderClientPlayerId < 0) return false;

        return true;
    }
}