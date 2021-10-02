package org.youngmonkeys.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.controller.handler.base.RoomRequestHandler;
import org.youngmonkeys.eun.app.event.OperationEvent;
import org.youngmonkeys.eun.app.request.ChatRoomOperationRequest;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.common.constant.EventCode;
import org.youngmonkeys.eun.common.constant.OperationCode;
import org.youngmonkeys.eun.common.constant.ParameterCode;
import org.youngmonkeys.eun.common.constant.ReturnCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EzySingleton
public class ChatRoomRequestHandler  extends RoomRequestHandler {
    protected ExecutorService threadPool;

    @Override
    public Integer getCode() {
        return OperationCode.ChatRoom;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, ChatRoomOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var currentRoom = getCurrentRoomPeer(peer);

        if (currentRoom == null) {
            return newOperationInvalid(operationRequest);
        }

        var response = new OperationResponse(operationRequest);

        var userId = peer.getName();
        var message = request.getMessage();

        var userIterator = currentRoom.getUserIdIterator(-1);
        var onChatRoomEvent = new OperationEvent(EventCode.OnChatRoom);
        var parameters = new CustomHashtable();
        parameters.put(ParameterCode.Message, new Object[] {
                userId,
                message,
                System.currentTimeMillis()
        });
        onChatRoomEvent.setParameters(parameters);

        threadPool.execute(() -> userService.sendEventToSomePeerByUserIds(userIterator, onChatRoomEvent));

        response.setReturnCode(ReturnCode.Ok);
        return response;
    }

    public ChatRoomRequestHandler() {
        threadPool = Executors.newSingleThreadExecutor();
    }
}