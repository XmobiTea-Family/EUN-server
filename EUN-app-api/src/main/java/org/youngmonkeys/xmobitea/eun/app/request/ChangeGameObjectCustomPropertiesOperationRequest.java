package org.youngmonkeys.xmobitea.eun.app.request;

import lombok.Data;
import org.youngmonkeys.xmobitea.eun.app.entity.EzyDataMember;
import org.youngmonkeys.xmobitea.eun.common.constant.ParameterCode;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;

@Data
public class ChangeGameObjectCustomPropertiesOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.ObjectId)
    int objectId;

    @EzyDataMember(code = ParameterCode.CustomGameObjectProperties)
    EUNHashtable customGameObjectProperties;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (objectId < 0) return false;
        if (customGameObjectProperties == null || customGameObjectProperties.count() == 0) return false;

        return true;
    }
}
