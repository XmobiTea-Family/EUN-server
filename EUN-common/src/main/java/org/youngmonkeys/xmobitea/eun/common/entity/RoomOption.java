package org.youngmonkeys.xmobitea.eun.common.entity;

import lombok.Data;

@Data
public class RoomOption {
    EUNHashtable customRoomProperties;
    EUNArray customRoomPropertiesForLobby;
    String password;
    int maxPlayer;
    boolean isVisible;
    boolean isOpen;
    int ttl;
}
