package org.youngmonkeys.eun.common.entity;

import lombok.Data;

@Data
public class RoomPlayer {
    int playerId;
    String userId;
    CustomHashtable customProperties;

    public RoomPlayer(int playerId, String userId, CustomHashtable customProperties) {
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
