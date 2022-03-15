package org.youngmonkeys.eun.app.service;

import org.youngmonkeys.eun.app.entity.ILobby;

import java.util.Iterator;

public interface ILobbyService {
    int size();
    ILobby getLobby(int lobbyId);
    Iterator<ILobby> getLobbyLst();
}
