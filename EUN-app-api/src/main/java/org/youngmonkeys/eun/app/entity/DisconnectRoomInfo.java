package org.youngmonkeys.eun.app.entity;

import lombok.Data;
import org.youngmonkeys.eun.common.entity.RoomPlayer;

@Data
public class DisconnectRoomInfo {
    long tsTimeoutReconnect;
    IRoom room;
    RoomPlayer roomPlayer;
}
