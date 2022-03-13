package org.youngmonkeys.eun.app.request;

import com.tvd12.ezyfox.io.EzyStrings;
import lombok.Data;
import org.youngmonkeys.eun.app.entity.EzyDataMember;
import org.youngmonkeys.eun.common.constant.ParameterCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

@Data
public class CreateGameObjectRoomOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.PrefabPath)
    String prefabPath;

    @EzyDataMember(code = ParameterCode.InitializeData)
    Object initializeData;

    @EzyDataMember(code = ParameterCode.SynchronizationData)
    Object synchronizationData;

    @EzyDataMember(code = ParameterCode.CustomGameObjectProperties, isOptional = true)
    CustomHashtable customGameObjectProperties;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (EzyStrings.isNoContent(prefabPath)) return false;

        return true;
    }
}