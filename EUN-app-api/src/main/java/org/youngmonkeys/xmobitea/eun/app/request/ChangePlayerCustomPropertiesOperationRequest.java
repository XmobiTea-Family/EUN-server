package org.youngmonkeys.xmobitea.eun.app.request;

import lombok.Data;
import org.youngmonkeys.xmobitea.eun.app.entity.EzyDataMember;
import org.youngmonkeys.xmobitea.eun.common.constant.ParameterCode;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;

@Data
public class ChangePlayerCustomPropertiesOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.OwnerId)
    int ownerId;

    @EzyDataMember(code = ParameterCode.CustomPlayerProperties)
    EUNHashtable customPlayerProperties;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (ownerId < 0) return false;
        if (customPlayerProperties == null || customPlayerProperties.count() == 0) return false;

        return true;
    }
}