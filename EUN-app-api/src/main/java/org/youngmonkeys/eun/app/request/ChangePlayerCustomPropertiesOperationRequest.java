package org.youngmonkeys.eun.app.request;

import lombok.Data;
import org.youngmonkeys.eun.app.entity.EzyDataMember;
import org.youngmonkeys.eun.common.constant.ParameterCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

@Data
public class ChangePlayerCustomPropertiesOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.OwnerId)
    int ownerId;

    @EzyDataMember(code = ParameterCode.CustomPlayerProperties)
    CustomHashtable customPlayerProperties;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (ownerId < 0) return false;
        if (customPlayerProperties == null || customPlayerProperties.size() == 0) return false;

        return true;
    }
}