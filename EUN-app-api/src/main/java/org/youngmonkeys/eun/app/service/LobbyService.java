package org.youngmonkeys.eun.app.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.entity.ILobby;
import org.youngmonkeys.eun.app.entity.IRoom;
import org.youngmonkeys.eun.app.entity.Lobby;

import java.util.Hashtable;
import java.util.Iterator;

@EzySingleton
public class LobbyService implements ILobbyService {
    private Hashtable<Integer, ILobby> lobbyDic;

    @Override
    public int size() {
        return lobbyDic.size();
    }

    @Override
    public ILobby getLobby(int lobbyId) {
        if (lobbyDic.containsKey(lobbyId))
            return lobbyDic.get(lobbyId);

        var newLobby = new Lobby(lobbyId);
        lobbyDic.put(lobbyId, newLobby);

        return newLobby;
    }

    @Override
    public Iterator<ILobby> getLobbyLst() {
        return lobbyDic.values().iterator();
    }

    public LobbyService() {
        lobbyDic = new Hashtable<>();
    }
}