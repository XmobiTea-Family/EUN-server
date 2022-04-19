package org.youngmonkeys.xmobitea.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.controller.handler.base.RoomRequestHandler;
import org.youngmonkeys.xmobitea.eun.app.entity.Room;
import org.youngmonkeys.xmobitea.eun.app.request.CreateRoomOperationRequest;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ReturnCode;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNArray;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;
import org.youngmonkeys.xmobitea.eun.common.entity.RoomOption;

import java.util.Vector;

@EzySingleton
public class CreateRoomRequestHandler extends RoomRequestHandler {
    @Override
    public Integer getCode() {
        return OperationCode.CreateRoom;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, CreateRoomOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var currentLobby = getCurrentLobbyPeer(peer);

        var response = new OperationResponse(operationRequest);

        if (getCurrentRoomPeer(peer) != null) {
            response.setReturnCode(ReturnCode.UserInRoom);
            return response;
        }

        var roomId = currentLobby.getRandomRoomId();
        if (roomId == -1) {
            response.setReturnCode(ReturnCode.LobbyFull);
            return response;
        }

        var roomOption = new RoomOption();

        var customRoomProperties = request.getCustomRoomProperties() != null ? request.getCustomRoomProperties() : new EUNHashtable();
        var customRoomPropertiesForLobby = request.getCustomRoomPropertiesForLobby() != null ? request.getCustomRoomPropertiesForLobby() : new EUNArray();

        roomOption.setCustomRoomProperties(customRoomProperties);
        roomOption.setCustomRoomPropertiesForLobby(customRoomPropertiesForLobby);
        roomOption.setMaxPlayer(request.getMaxPlayer());
        roomOption.setOpen(request.isOpen());
        roomOption.setVisible(request.isVisible());
        roomOption.setPassword(request.getPassword());
        roomOption.setTtl(request.getTtl() < 0 ? 0 : request.getTtl());

        var room = new Room(roomId, roomOption, currentLobby, userService);
        currentLobby.addRoom(room);

        var joined = room.joinRoom(peer);
        if (!joined) {
            response.setReturnCode(ReturnCode.NotOk);
            return response;
        }

        response.setReturnCode(ReturnCode.Ok);
        return response;
    }
}