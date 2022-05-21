package org.youngmonkeys.xmobitea.eun.app.controller.handler.base;

import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.entity.IRoom;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.common.constant.PeerPropertyCode;

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
