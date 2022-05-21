package org.youngmonkeys.xmobitea.eun.app.entity;

import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import org.youngmonkeys.xmobitea.eun.app.service.IChatAllService;

import java.util.Iterator;

public interface ILobby extends IChatAllService {
    int getLobbyId();

    int playerSize();

    int roomSize();

    boolean joinLobby(@NonNull String userId, @NonNull EzyUser peer);

    boolean leaveLobby(@NonNull String userId);

    int getRandomRoomId();

    IRoom getRoom(int roomId);

    boolean addRoom(@NonNull IRoom room);

    boolean removeRoom(@NonNull IRoom room);

    Iterator<EzyUser> getUserIterator();

    Iterator<IRoom> getRoomIterator();

    Object[] getLobbyStats();
}
