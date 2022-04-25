package org.youngmonkeys.xmobitea.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.request.ChangeGameObjectCustomPropertiesOperationRequest;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.app.controller.handler.base.RoomRequestHandler;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ReturnCode;

@EzySingleton
public class ChangeGameObjectCustomPropertiesRequestHandler extends RoomRequestHandler {
    @Override
    public Integer getCode() {
        return OperationCode.ChangeGameObjectCustomProperties;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, ChangeGameObjectCustomPropertiesOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var currentRoom = getCurrentRoomPeer(peer);

        if (currentRoom == null) {
            return newOperationInvalid(operationRequest);
        }

        var response = new OperationResponse(operationRequest);

        var customPlayerProperties = request.getCustomGameObjectProperties();
        var objectId = request.getObjectId();

        var setDone = currentRoom.setCustomGameObjectProperties(peer, objectId, customPlayerProperties);
        if (setDone) response.setReturnCode(ReturnCode.Ok);
        else response.setReturnCode(ReturnCode.NotOk);

        return response;
    }
}
