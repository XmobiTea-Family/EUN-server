package org.youngmonkeys.eun.app.request;

import lombok.Data;
import org.youngmonkeys.eun.app.entity.EzyDataMember;
import org.youngmonkeys.eun.common.constant.ParameterCode;

@Data
public class RpcGameObjectRoomOperationRequest extends OperationRequest {
    @EzyDataMember(code = ParameterCode.ObjectId)
    int objectId;

    @EzyDataMember(code = ParameterCode.EzyRPCCommand)
    int eunRPCCommand;

    @EzyDataMember(code = ParameterCode.RpcData)
    Object rpcData;

    @EzyDataMember(code = ParameterCode.EzyTargets, isOptional = true)
    int ezyTargets;

    @Override
    public boolean isValidRequest() {
        if (!super.isValidRequest()) return false;

        if (objectId < 0) return false;
        if (eunRPCCommand < 0) return false;

        return true;
    }
}
