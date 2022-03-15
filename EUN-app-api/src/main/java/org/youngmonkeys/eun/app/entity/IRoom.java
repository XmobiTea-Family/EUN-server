package org.youngmonkeys.eun.app.entity;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import org.youngmonkeys.eun.common.entity.*;

import java.util.Iterator;

public interface IRoom {
    int getRoomId();
    Iterator<RoomPlayer> getRoomPlayerLst();
    int getPlayerCount();
    int getMaxPlayer();
    CustomHashtable getCustomRoomProperties();
    Iterator<Integer> getCustomRoomPropertiesForLobby();
    boolean joinRoom(EzyUser peer);
    boolean leaveRoom(EzyUser peer);
    boolean leaveRoomBecauseTimeoutReconnect(String userId);
    void disconnectRoom(EzyUser peer);
    void reconnectRoom(EzyUser peer);
    boolean changeLeaderClient(EzyUser peer, int newLeaderClientPlayerId);
    boolean getIsVisible();
    boolean getIsOpen();
    boolean setCustomRoomProperties(EzyUser peer, CustomHashtable customRoomProperties);
    boolean setCustomRoomPropertiesForLobby(EzyUser peer, CustomHashtable customRoomPropertiesForLobby);
    boolean setMaxPlayer(EzyUser peer, int maxPlayer);
    boolean setOpen(EzyUser peer, boolean isOpen);
    boolean setVisible(EzyUser peer, boolean isVisible);
    boolean setPassword(EzyUser peer, String password);
    boolean setTtl(EzyUser peer, int ttl);
    boolean setCustomPlayerProperties(EzyUser peer, int playerId, CustomHashtable customPlayerProperties);

    RoomGameObject createGameObject(EzyUser peer, String prefabPath, Object initializeData, Object synchronizationData, CustomHashtable customGameObjectProperties);
    boolean setCustomGameObjectProperties(EzyUser peer, int objectId, CustomHashtable customGameObjectProperties);
    boolean destroyGameObject(EzyUser peer, int objectId);
    boolean synchronizationDataGameObject(EzyUser peer, int objectId, Object synchronizationData);
    boolean rpcGameObject(EzyUser peer, int objectId, int eunRPCCommand, Object rpcData, int ezyTargets);
    boolean rpcGameObjectTo(EzyUser peer, int objectId, int eunRPCCommand, Object rpcData, EzyArray targetPlayerIds);
    boolean transferOwnerGameObject(EzyUser peer, int objectId, int newOwnerId);
    boolean voiceChat(EzyUser peer, int objectId, Object voiceChat);

    long getTsCreate();
    String getPassword();
    int getTtl();
    Iterator<String> getUserIdIterator(int excludePlayerId);
    ILobby getLobby();

    Object[] toFullLobbyData();
    Object[] toFullData();
}