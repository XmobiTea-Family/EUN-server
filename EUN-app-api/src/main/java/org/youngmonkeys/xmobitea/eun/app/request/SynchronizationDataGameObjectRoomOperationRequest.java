package org.youngmonkeys.xmobitea.eun.app.request;

import lombok.Data;
import org.youngmonkeys.xmobitea.eun.app.entity.EzyDataMember;
import org.youngmonkeys.xmobitea.eun.common.constant.ParameterCode;

@Data
public class SynchronizationDataGameObjectRoomOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.ObjectId)
    int objectId;

    @EzyDataMember(code = ParameterCode.SynchronizationData)
    Object synchronizationData;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (objectId < 0) return false;

        return true;
    }
}
