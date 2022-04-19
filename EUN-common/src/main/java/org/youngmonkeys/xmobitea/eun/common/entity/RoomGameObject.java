package org.youngmonkeys.xmobitea.eun.common.entity;

import lombok.Data;

@Data
public class RoomGameObject {
    int objectId;
    int ownerId;
    String prefabPath;
    Object synchronizationData;
    Object initializeData;
    EUNHashtable customProperties;

    public Object toData() {
        return new Object[]{
                objectId,
                ownerId,
                prefabPath,
                synchronizationData,
                initializeData,
                customProperties.toEzyData()
        };
    }
}