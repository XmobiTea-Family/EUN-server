package org.youngmonkeys.eun.app.entity;

import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.common.service.CustomRandom;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Lobby implements ILobby {
    private Hashtable<String, EzyUser> peerDic;
    private Hashtable<Integer, IRoom> roomDic;

    private int lobbyId;

    private List<String> chatUserIdLst;

    @Override
    public int getLobbyId() {
        return lobbyId;
    }

    @Override
    public int playerSize() {
        return peerDic.size();
    }

    @Override
    public int roomSize() {
        return roomDic.size();
    }

    @Override
    public boolean joinLobby(@NonNull String userId, @NonNull EzyUser peer) {
        if (peerDic.containsKey(userId)) peerDic.replace(userId, peer);
        else peerDic.put(userId, peer);

        return true;
    }

    @Override
    public boolean leaveLobby(@NonNull String userId) {
        if (peerDic.containsKey(userId)) {
            peerDic.remove(userId);
            removeChatPeer(userId);
            return true;
        }

        return false;
    }

    @Override
    public Iterator<EzyUser> getUserIterator() {
        return peerDic.values().iterator();
    }

    @Override
    public Iterator<IRoom> getRoomIterator() {
        return roomDic.values().iterator();
    }

    @Override
    public Object[] getLobbyStats() {
        return new Object[] {
                getLobbyId(),
                playerSize(),
                roomSize()
        };
    }

    @Override
    public IRoom getRoom(int roomId) {
        return roomDic.getOrDefault(roomId, null);
    }

    @Override
    public boolean addRoom(@NonNull IRoom room) {
        if (roomDic.containsKey(room.getRoomId())) {
            return false;
        }

        roomDic.put(room.getRoomId(), room);
        return true;
    }

    @Override
    public boolean removeRoom(@NonNull IRoom room) {
        if (!roomDic.containsKey(room.getRoomId())) {
            return false;
        }

        roomDic.remove(room.getRoomId());
        return true;
    }

    @Override
    public int getRandomRoomId() {
        var roomId = 0;
        do {
            roomId = CustomRandom.range(0, Integer.MAX_VALUE);
        }
        while (roomDic.containsKey(roomId));

        return roomId;
    }

    @Override
    public boolean addChatPeer(@NonNull String userId) {
        if (chatUserIdLst.contains(userId)) return false;

        chatUserIdLst.add(userId);
        return true;
    }

    @Override
    public boolean removeChatPeer(@NonNull String userId) {
        if (!chatUserIdLst.contains(userId)) return false;

        chatUserIdLst.remove(userId);
        return true;
    }

    @Override
    public Iterator<String> getChatPeerUserIdIterator() {
        return chatUserIdLst.iterator();
    }

    public Lobby(int lobbyId) {
        peerDic = new Hashtable<>();
        roomDic = new Hashtable<>();

        chatUserIdLst = new Vector<>();

        this.lobbyId = lobbyId;
    }
}
