package org.youngmonkeys.xmobitea.eun.app.entity;

import lombok.Data;
import org.youngmonkeys.xmobitea.eun.common.entity.RoomPlayer;

@Data
public class DisconnectRoomInfo {
    long tsTimeoutReconnect;
    IRoom room;
    RoomPlayer roomPlayer;
}
