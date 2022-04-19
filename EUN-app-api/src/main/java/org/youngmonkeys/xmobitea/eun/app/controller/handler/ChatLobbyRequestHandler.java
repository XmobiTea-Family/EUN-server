package org.youngmonkeys.xmobitea.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.event.OperationEvent;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.common.constant.EventCode;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ParameterCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ReturnCode;
import org.youngmonkeys.xmobitea.eun.app.controller.handler.base.LobbyRequestHandler;
import org.youngmonkeys.xmobitea.eun.app.request.ChatLobbyOperationRequest;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EzySingleton
public class ChatLobbyRequestHandler extends LobbyRequestHandler {
    protected ExecutorService threadPool;

    @Override
    public Integer getCode() {
        return OperationCode.ChatLobby;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, ChatLobbyOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var currentLobby = getCurrentLobbyPeer(peer);

        var userId = peer.getName();
        var message = request.getMessage();

        var userIterator = currentLobby.getChatPeerUserIdIterator();
        var onChatLobbyEvent = new OperationEvent(EventCode.OnChatLobby);
        var parameters = new EUNHashtable();
        parameters.add(ParameterCode.Message, new Object[] {
                userId,
                message,
                System.currentTimeMillis()
        });
        onChatLobbyEvent.setParameters(parameters);

        threadPool.execute(() -> userService.sendEventToSomePeerByUserIds(userIterator, onChatLobbyEvent));

        var response = new OperationResponse(operationRequest);
        response.setReturnCode(ReturnCode.Ok);
        return response;
    }

    public ChatLobbyRequestHandler() {
        threadPool = Executors.newSingleThreadExecutor();
    }
}