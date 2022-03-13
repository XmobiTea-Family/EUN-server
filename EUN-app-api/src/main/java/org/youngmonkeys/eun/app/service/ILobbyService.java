package org.youngmonkeys.eun.app.service;

import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import org.youngmonkeys.eun.app.entity.ILobby;
import org.youngmonkeys.eun.app.entity.IRoom;

import java.util.Iterator;

public interface ILobbyService {
    int size();
    ILobby getLobby(int lobbyId);
    Iterator<ILobby> getLobbyLst();
}