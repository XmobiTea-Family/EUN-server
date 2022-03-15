package org.youngmonkeys.xmobitea.eun.common.entity;

import lombok.Data;

@Data
public class RoomPlayer {
    int playerId;
    String userId;
    EUNHashtable customProperties;

    public RoomPlayer(int playerId, String userId, EUNHashtable customProperties) {
        this.playerId = playerId;
        this.userId = userId;
        this.customProperties = customProperties;
    }

    public Object toData() {
        return new Object[] {
                playerId,
                userId,
                customProperties
        };
    }
}
