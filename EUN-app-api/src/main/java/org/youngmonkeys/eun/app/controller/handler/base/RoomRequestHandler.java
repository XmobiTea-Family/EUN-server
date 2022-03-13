package org.youngmonkeys.eun.app.controller.handler.base;

import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.entity.IRoom;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.common.constant.PeerPropertyCode;

public class RoomRequestHandler extends LobbyRequestHandler {
    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        return null;
    }

    protected final IRoom getCurrentRoomPeer(@NonNull EzyUser peer) {
        var currentRoom = peer.getProperty(PeerPropertyCode.Room, IRoom.class);

        return currentRoom;
    }
}
