package org.youngmonkeys.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.controller.handler.base.RoomRequestHandler;
import org.youngmonkeys.eun.app.request.CreateGameObjectRoomOperationRequest;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.common.constant.OperationCode;
import org.youngmonkeys.eun.common.constant.ParameterCode;
import org.youngmonkeys.eun.common.constant.ReturnCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

@EzySingleton
public class CreateGameObjectRoomRequestHandler extends RoomRequestHandler {
    @Override
    public Integer getCode() {
        return OperationCode.CreateGameObjectRoom;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, CreateGameObjectRoomOperationRequest.class);

        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var currentRoom = getCurrentRoomPeer(peer);

        if (currentRoom == null) {
            return newOperationInvalid(operationRequest);
        }

        var response = new OperationResponse(operationRequest);

        var roomGameObject = currentRoom.createGameObject(peer, request.getPrefabPath(), request.getInitializeData(), request.getSynchronizationData(), request.getCustomGameObjectProperties());
        if (roomGameObject != null) {
            response.setReturnCode(ReturnCode.Ok);

            var parameters = new CustomHashtable();
            parameters.put(ParameterCode.Data, roomGameObject.toData());

            response.setParameters(parameters);
        }
        else response.setReturnCode(ReturnCode.NotOk);

        return response;
    }
}