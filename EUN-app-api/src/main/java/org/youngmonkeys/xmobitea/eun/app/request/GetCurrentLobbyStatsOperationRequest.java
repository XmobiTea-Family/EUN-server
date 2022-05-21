package org.youngmonkeys.xmobitea.eun.app.request;

import lombok.Data;
import org.youngmonkeys.xmobitea.eun.app.entity.EzyDataMember;
import org.youngmonkeys.xmobitea.eun.common.constant.ParameterCode;

@Data
public class GetCurrentLobbyStatsOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.Skip)
    int skip;

    @EzyDataMember(code = ParameterCode.Limit)
    int limit;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (skip < 0) return false;
        if (limit <= 0) return false;

        return true;
    }
}
