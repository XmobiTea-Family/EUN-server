package org.youngmonkeys.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.controller.handler.base.RoomRequestHandler;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.request.RpcGameObjectRoomToOperationRequest;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.common.constant.OperationCode;
import org.youngmonkeys.eun.common.constant.ReturnCode;

@EzySingleton
public class RpcGameObjectRoomToRequestHandler extends RoomRequestHandler {
    @Override
    public Integer getCode() {
        return OperationCode.RpcGameObjectRoomTo;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, RpcGameObjectRoomToOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var currentRoom = getCurrentRoomPeer(peer);

        if (currentRoom == null) {
            return newOperationInvalid(operationRequest);
        }

        var response = new OperationResponse(operationRequest);

        var answer = currentRoom.rpcGameObjectTo(peer, request.getObjectId(), request.getEunRPCCommand(), request.getRpcData(), request.getTargetPlayerIds());
        if (answer) response.setReturnCode(ReturnCode.Ok);
        else response.setReturnCode(ReturnCode.NotOk);

        return response;
    }
}
