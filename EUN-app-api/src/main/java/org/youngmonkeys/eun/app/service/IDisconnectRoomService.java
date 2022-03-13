package org.youngmonkeys.eun.app.service;

import lombok.NonNull;
import org.youngmonkeys.eun.app.entity.DisconnectRoomInfo;
import org.youngmonkeys.eun.app.entity.IRoom;

public interface IDisconnectRoomService {
    boolean addDisconnectRoom(@NonNull String userId, @NonNull DisconnectRoomInfo disconnectRoom);

    boolean removeDisconnectRoom(@NonNull String userId);

    DisconnectRoomInfo getDisconnectRoom(@NonNull String userId);
}
