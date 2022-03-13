package org.youngmonkeys.eun.common.entity;

import lombok.Data;

import java.util.List;

@Data
public class RoomOption {
    CustomHashtable customRoomProperties;
    List<Integer> customRoomPropertiesForLobby;
    String password;
    int maxPlayer;
    boolean isVisible;
    boolean isOpen;
    int ttl;
}
