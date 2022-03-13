package org.youngmonkeys.eun.app.request;

import lombok.Data;
import org.youngmonkeys.eun.app.entity.EzyDataMember;
import org.youngmonkeys.eun.common.constant.ParameterCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

@Data
public class ChangeGameObjectCustomPropertiesOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.ObjectId)
    int objectId;

    @EzyDataMember(code = ParameterCode.CustomGameObjectProperties)
    CustomHashtable customGameObjectProperties;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (objectId < 0) return false;
        if (customGameObjectProperties == null || customGameObjectProperties.size() == 0) return false;

        return true;
    }
}