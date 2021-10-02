package org.youngmonkeys.eun.app.request;

import lombok.Data;
import org.youngmonkeys.eun.app.entity.EzyDataMember;
import org.youngmonkeys.eun.common.constant.ParameterCode;

@Data
public class GetLobbyStatsLstOperationRequest extends OperationRequest {
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
