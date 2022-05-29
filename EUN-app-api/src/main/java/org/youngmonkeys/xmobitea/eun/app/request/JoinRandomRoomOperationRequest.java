package org.youngmonkeys.xmobitea.eun.app.request;

import lombok.Data;
import org.youngmonkeys.xmobitea.eun.app.entity.EzyDataMember;
import org.youngmonkeys.xmobitea.eun.common.constant.ParameterCode;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;

@Data
public class JoinRandomRoomOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.TargetExpectedCount, isOptional = true)
    int targetExpectedCount;

    @EzyDataMember(code = ParameterCode.ExpectedProperties, isOptional = true)
    EUNHashtable expectedProperties;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (expectedProperties != null && targetExpectedCount <= 0) return false;
        if (expectedProperties == null && targetExpectedCount > 0) return false;

        return true;
    }
}
