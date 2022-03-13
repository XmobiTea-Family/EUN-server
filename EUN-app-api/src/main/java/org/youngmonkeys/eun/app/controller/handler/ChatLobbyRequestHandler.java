package org.youngmonkeys.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.controller.handler.base.LobbyRequestHandler;
import org.youngmonkeys.eun.app.event.OperationEvent;
import org.youngmonkeys.eun.app.request.ChatLobbyOperationRequest;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.common.constant.*;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

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
        var parameters = new CustomHashtable();
        parameters.put(ParameterCode.Message, new Object[] {
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