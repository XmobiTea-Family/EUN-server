package org.youngmonkeys.xmobitea.eun.app.service;

import org.youngmonkeys.xmobitea.eun.app.entity.ILobby;

import java.util.Iterator;

public interface ILobbyService {
    int size();
    ILobby getLobby(int lobbyId);
    Iterator<ILobby> getLobbyLst();
}
