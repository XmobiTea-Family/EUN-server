package org.youngmonkeys.xmobitea.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.controller.handler.base.LobbyRequestHandler;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.request.SubscriberChatLobbyOperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ReturnCode;

@EzySingleton
public class SubscriberChatLobbyRequestHandler extends LobbyRequestHandler {
    @Override
    public Integer getCode() {
        return OperationCode.SubscriberChatLobby;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, SubscriberChatLobbyOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var currentLobby = getCurrentLobbyPeer(peer);

        var userId = peer.getName();

        if (request.isSubscribe()) currentLobby.addChatPeer(userId);
        else currentLobby.removeChatPeer(userId);

        var response = new OperationResponse(operationRequest);
        response.setReturnCode(ReturnCode.Ok);

        return response;
    }
}