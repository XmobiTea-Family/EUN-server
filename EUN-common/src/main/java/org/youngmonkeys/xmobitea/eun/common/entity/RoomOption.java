package org.youngmonkeys.xmobitea.eun.common.entity;

import lombok.Data;

import java.util.List;

@Data
public class RoomOption {
    EUNHashtable customRoomProperties;
    List<Integer> customRoomPropertiesForLobby;
    String password;
    int maxPlayer;
    boolean isVisible;
    boolean isOpen;
    int ttl;
}
