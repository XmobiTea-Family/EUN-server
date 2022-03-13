package org.youngmonkeys.eun.app.request;

import com.tvd12.ezyfox.entity.EzyArray;
import lombok.Data;
import org.youngmonkeys.eun.app.entity.EzyDataMember;
import org.youngmonkeys.eun.common.constant.ParameterCode;

@Data
public class RpcGameObjectRoomToOperationRequest extends RpcGameObjectRoomOperationRequest {
    @EzyDataMember(code = ParameterCode.TargetPlayerIds)
    EzyArray targetPlayerIds;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (targetPlayerIds.size() == 0) return false;

        return true;
    }
}
