package org.youngmonkeys.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.controller.handler.base.RoomRequestHandler;
import org.youngmonkeys.eun.app.request.JoinRoomOperationRequest;
import org.youngmonkeys.eun.common.constant.OperationCode;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.common.constant.ReturnCode;

@EzySingleton
public class JoinRoomRequestHandler extends RoomRequestHandler {
    @Override
    public Integer getCode() {
        return OperationCode.JoinRoom;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, JoinRoomOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var currentLobby = getCurrentLobbyPeer(peer);

        var response = new OperationResponse(operationRequest);

        if (getCurrentRoomPeer(peer) != null) {
            response.setReturnCode(ReturnCode.UserInRoom);
            return response;
        }

        var room = currentLobby.getRoom(request.getRoomId());
        if (room == null) {
            response.setReturnCode(ReturnCode.RoomNotFound);
            return response;
        }

        var roomPassword = room.getPassword();
        if (roomPassword == null) roomPassword = "";

        var password = request.getPassword();
        if (password == null) password = "";

        if (!roomPassword.equals(password)) {
            response.setReturnCode(ReturnCode.RoomPasswordWrong);
            return response;
        }

        if (room.getPlayerCount() >= room.getMaxPlayer()) {
            response.setReturnCode(ReturnCode.RoomFull);
            return response;
        }

        if (!room.getIsOpen()) {
            response.setReturnCode(ReturnCode.RoomClosed);
            return response;
        }

        var joined = room.joinRoom(peer);
        if (!joined) {
            response.setReturnCode(ReturnCode.NotOk);
            return response;
        }

        response.setReturnCode(ReturnCode.Ok);
        return response;
    }
}