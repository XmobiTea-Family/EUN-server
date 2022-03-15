package org.youngmonkeys.xmobitea.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.controller.handler.base.LobbyRequestHandler;
import org.youngmonkeys.xmobitea.eun.app.request.LeaveLobbyOperationRequest;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ReturnCode;

@EzySingleton
public class LeaveLobbyRequestHandler extends LobbyRequestHandler {
    @Override
    public Integer getCode() {
        return OperationCode.LeaveLobby;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, LeaveLobbyOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var response = new OperationResponse(operationRequest);
        response.setReturnCode(ReturnCode.Ok);

        joinLobbyPeer(peer, 0);

        return response;
    }
}