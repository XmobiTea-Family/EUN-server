package org.youngmonkeys.eun.app.entity;

import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.event.OperationEvent;
import org.youngmonkeys.eun.app.service.IUserService;
import org.youngmonkeys.eun.common.constant.EventCode;
import org.youngmonkeys.eun.common.constant.ParameterCode;
import org.youngmonkeys.eun.common.constant.PeerPropertyCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;
import org.youngmonkeys.eun.common.entity.RoomGameObject;
import org.youngmonkeys.eun.common.entity.RoomOption;
import org.youngmonkeys.eun.common.entity.RoomPlayer;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Room implements IRoom {
    int ttl;
    int currentPlayerId;
    int roomId;
    List<RoomPlayer> roomPlayerLst;
    String leaderClientUserId;
    int maxPlayer;
    CustomHashtable customRoomProperties;
    List<Integer> customRoomPropertiesForLobby;
    boolean isVisible;
    boolean isOpen;
    long tsCreate;
    String password;
    ILobby lobby;

    HashMap<Integer, RoomGameObject> gameObjectDic;

    ExecutorService threadPool;

    IUserService userService;

    @Override
    public int getRoomId() {
        return roomId;
    }

    @Override
    public Iterator<RoomPlayer> getRoomPlayerLst() {
        return roomPlayerLst.iterator();
    }

    @Override
    public int getPlayerCount() {
        return roomPlayerLst.size();
    }

    @Override
    public int getMaxPlayer() {
        return maxPlayer;
    }

    @Override
    public CustomHashtable getCustomRoomProperties() {
        return customRoomProperties;
    }

    @Override
    public Iterator<Integer> getCustomRoomPropertiesForLobby() {
        return customRoomPropertiesForLobby.iterator();
    }

    @Override
    public boolean joinRoom(EzyUser peer) {
        if (!isOpen) return false;
        if (roomPlayerLst.size() >= maxPlayer) return false;

        RoomPlayer roomPlayer;

        var userId = peer.getName();
        var roomPlayerOption = roomPlayerLst.stream().filter(x -> x.getUserId().equals(userId)).findAny();

        if (!roomPlayerOption.isPresent()) {
            roomPlayer = new RoomPlayer(currentPlayerId++, userId, new CustomHashtable());
            roomPlayerLst.add(roomPlayer);

            threadPool.execute(() -> {
                var onPlayerJoinRoomEvent = new OperationEvent(EventCode.OnPlayerJoinRoom);
                var playerJoinRoomParameters = new CustomHashtable();
                playerJoinRoomParameters.put(ParameterCode.Data, roomPlayer.toData());
                onPlayerJoinRoomEvent.setParameters(playerJoinRoomParameters);

                userService.sendEventToSomePeerByUserIds(getUserIdIterator(roomPlayer.getPlayerId()), onPlayerJoinRoomEvent);
            });
        }
        else roomPlayer = roomPlayerOption.get();

        peer.setProperty(PeerPropertyCode.Room, this);
        peer.setProperty(PeerPropertyCode.RoomPlayer, roomPlayer);

        threadPool.execute(() -> {
            var onJoinRoomEvent = new OperationEvent(EventCode.OnJoinRoom);
            var joinRoomParameters = new CustomHashtable();
            joinRoomParameters.put(ParameterCode.Data, toFullData());
            onJoinRoomEvent.setParameters(joinRoomParameters);

            userService.sendEvent(peer, onJoinRoomEvent);
        });

        if (isLeaderClientNotFound()) {
            changeLeaderClient(roomPlayer.getPlayerId());
        }

        return true;
    }

    @Override
    public boolean leaveRoom(EzyUser peer) {
        var userId = peer.getName();

        return leaveRoomBecauseTimeoutReconnect(userId);
    }

    @Override
    public boolean leaveRoomBecauseTimeoutReconnect(String userId) {
        var roomPlayerOption = roomPlayerLst.stream().filter(x -> x.getUserId().equals(userId)).findAny();
        if (!roomPlayerOption.isPresent()) {
            return false;
        }

        var peer = userService.getPeer(userId);

        var roomPlayer = roomPlayerOption.get();
        var removed = roomPlayerLst.remove(roomPlayer);
        if (!removed) return false;
        else {
            if (peer != null) {
                peer.removeProperty(PeerPropertyCode.Room);
                peer.removeProperty(PeerPropertyCode.RoomPlayer);

                threadPool.execute(() -> {
                    var onPlayerJoinRoomEvent = new OperationEvent(EventCode.OnLeftRoom);
                    userService.sendEvent(peer, onPlayerJoinRoomEvent);
                });
            }
        }

        threadPool.execute(() -> {
            var onPlayerLeftRoomEvent = new OperationEvent(EventCode.OnPlayerLeftRoom);
            var playerLeftRoomParameters = new CustomHashtable();
            playerLeftRoomParameters.put(ParameterCode.Data, roomPlayer.toData());
            onPlayerLeftRoomEvent.setParameters(playerLeftRoomParameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(roomPlayer.getPlayerId()), onPlayerLeftRoomEvent);
        });

        if (roomPlayerLst.size() <= 0) {
            // trong phong khong con ai nua
            lobby.removeRoom(this);
        } else {
            if (leaderClientUserId.equals(userId)) {
                var newLeaderClientPlayerId = -1;

                for (var cRoomPlayer : roomPlayerLst) {
                    if (cRoomPlayer.getPlayerId() != roomPlayer.getPlayerId() && userService.containsPeer(cRoomPlayer.getUserId()))
                        newLeaderClientPlayerId = cRoomPlayer.getPlayerId();
                    break;
                }

                if (newLeaderClientPlayerId == -1) lobby.removeRoom(this);
                else changeLeaderClient(newLeaderClientPlayerId);
            }
        }

        return true;
    }

    @Override
    public void disconnectRoom(EzyUser peer) {
        var userId = peer.getName();

        var roomPlayer = peer.getProperty(PeerPropertyCode.RoomPlayer, RoomPlayer.class);

        if (leaderClientUserId.equals(userId)) {
            var newLeaderClientPlayerId = -1;

            for (var cRoomPlayer : roomPlayerLst) {
                if (cRoomPlayer.getPlayerId() != roomPlayer.getPlayerId() && userService.containsPeer(cRoomPlayer.getUserId()))
                    newLeaderClientPlayerId = cRoomPlayer.getPlayerId();
                break;
            }

            if (newLeaderClientPlayerId != -1) changeLeaderClient(newLeaderClientPlayerId);
        }
    }

    @Override
    public void reconnectRoom(EzyUser peer) {
        var userId = peer.getName();

        var roomPlayerOption = roomPlayerLst.stream().filter(x -> x.getUserId().equals(userId)).findAny();
        var roomPlayer = roomPlayerOption.get();

        peer.setProperty(PeerPropertyCode.Room, this);
        peer.setProperty(PeerPropertyCode.RoomPlayer, roomPlayer);

        threadPool.execute(() -> {
            System.out.println("userService send OnJoinRoom");

            var onJoinRoomEvent = new OperationEvent(EventCode.OnJoinRoom);
            var joinRoomParameters = new CustomHashtable();
            joinRoomParameters.put(ParameterCode.Data, toFullData());
            onJoinRoomEvent.setParameters(joinRoomParameters);

            userService.sendEvent(peer, onJoinRoomEvent);
        });

        if (isLeaderClientNotFound()) {
            changeLeaderClient(roomPlayer.getPlayerId());
        }
    }

    @Override
    public boolean changeLeaderClient(EzyUser peer, int newLeaderClientPlayerId) {
        if (!isLeaderClient(peer)) return false;

        var userId = peer.getName();

        var newLeaderClientOptional = roomPlayerLst.stream().filter(x -> x.getPlayerId() == newLeaderClientPlayerId).findAny();
        if (!newLeaderClientOptional.isPresent()) return false;

        var newLeaderClient = newLeaderClientOptional.get();
        var newLeaderClientUserId = newLeaderClient.getUserId();

        // can not change for this mine user
        if (newLeaderClientUserId.equals(userId)) return false;

        var thisLeaderClientPeer = userService.getPeer(newLeaderClientUserId);
        if (thisLeaderClientPeer == null) return false;

        this.leaderClientUserId = newLeaderClientUserId;

        threadPool.execute(() -> {
            var onPlayerJoinRoomEvent = new OperationEvent(EventCode.OnLeaderClientChange);
            var playerJoinRoomParameters = new CustomHashtable();
            playerJoinRoomParameters.put(ParameterCode.Data, newLeaderClient.toData());
            onPlayerJoinRoomEvent.setParameters(playerJoinRoomParameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), onPlayerJoinRoomEvent);
        });

        return true;
    }

    @Override
    public boolean getIsVisible() {
        return isVisible;
    }

    @Override
    public boolean getIsOpen() {
        return isOpen;
    }

    @Override
    public boolean setCustomRoomProperties(EzyUser peer, CustomHashtable customRoomProperties) {
        if (!isLeaderClient(peer)) return false;

        var keySet = customRoomProperties.keySet();
        for (var key : keySet) {
            var value = customRoomProperties.get(key);

            if (value == null) {
                if (this.customRoomProperties.containsKey(key)) {
                    this.customRoomProperties.remove(key);
                }
            }
            else {
                if (!this.customRoomProperties.containsKey(key))
                    this.customRoomProperties.put(key, value);
            }
        }

        threadPool.execute(() -> {
            var onRoomInfoChangeEvent = new OperationEvent(EventCode.OnRoomInfoChange);
            var roomInfoChangeParameters = new CustomHashtable();
            roomInfoChangeParameters.put(ParameterCode.CustomRoomProperties, customRoomProperties);
            onRoomInfoChangeEvent.setParameters(roomInfoChangeParameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), onRoomInfoChangeEvent);
        });

        return true;
    }

    @Override
    public boolean setCustomRoomPropertiesForLobby(EzyUser peer, CustomHashtable customRoomPropertiesForLobby) {
        if (!isLeaderClient(peer)) return false;

        var keySet = customRoomPropertiesForLobby.keySet();
        for (var key : keySet) {
            var value = customRoomPropertiesForLobby.get(key);

            if (value == null) {
                if (this.customRoomPropertiesForLobby.contains(key)) {
                    this.customRoomPropertiesForLobby.remove(key);
                }
            }
            else {
                if (!this.customRoomPropertiesForLobby.contains(key))
                    this.customRoomPropertiesForLobby.add(key);
            }
        }

        threadPool.execute(() -> {
            var onRoomInfoChangeEvent = new OperationEvent(EventCode.OnRoomInfoChange);
            var roomInfoChangeParameters = new CustomHashtable();
            roomInfoChangeParameters.put(ParameterCode.CustomRoomPropertiesForLobby, this.customRoomPropertiesForLobby);
            onRoomInfoChangeEvent.setParameters(roomInfoChangeParameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), onRoomInfoChangeEvent);
        });

        return true;
    }

    @Override
    public boolean setMaxPlayer(EzyUser peer, int maxPlayer) {
        if (!isLeaderClient(peer)) return false;
        if (maxPlayer == this.maxPlayer) return false;

        if (this.maxPlayer < getPlayerCount()) return false;
        this.maxPlayer = maxPlayer;

        threadPool.execute(() -> {
            var onRoomInfoChangeEvent = new OperationEvent(EventCode.OnRoomInfoChange);
            var roomInfoChangeParameters = new CustomHashtable();
            roomInfoChangeParameters.put(ParameterCode.MaxPlayer, maxPlayer);
            onRoomInfoChangeEvent.setParameters(roomInfoChangeParameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), onRoomInfoChangeEvent);
        });

        return true;
    }

    @Override
    public boolean setOpen(EzyUser peer, boolean isOpen) {
        if (!isLeaderClient(peer)) return false;
        if (isOpen == this.isOpen) return false;

        this.isOpen = isOpen;

        threadPool.execute(() -> {
            var onRoomInfoChangeEvent = new OperationEvent(EventCode.OnRoomInfoChange);
            var roomInfoChangeParameters = new CustomHashtable();
            roomInfoChangeParameters.put(ParameterCode.IsOpen, isOpen);
            onRoomInfoChangeEvent.setParameters(roomInfoChangeParameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), onRoomInfoChangeEvent);
        });

        return true;
    }

    @Override
    public boolean setVisible(EzyUser peer, boolean isVisible) {
        if (!isLeaderClient(peer)) return false;
        if (isVisible == this.isVisible) return false;

        this.isVisible = isVisible;

        threadPool.execute(() -> {
            var onRoomInfoChangeEvent = new OperationEvent(EventCode.OnRoomInfoChange);
            var roomInfoChangeParameters = new CustomHashtable();
            roomInfoChangeParameters.put(ParameterCode.IsVisible, isVisible);
            onRoomInfoChangeEvent.setParameters(roomInfoChangeParameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), onRoomInfoChangeEvent);
        });

        return true;
    }

    @Override
    public boolean setPassword(EzyUser peer, String password) {
        if (!isLeaderClient(peer)) return false;
        if (password.equals(this.password)) return false;

        this.password = password;

        threadPool.execute(() -> {
            var onRoomInfoChangeEvent = new OperationEvent(EventCode.OnRoomInfoChange);
            var roomInfoChangeParameters = new CustomHashtable();
            roomInfoChangeParameters.put(ParameterCode.Password, password);
            onRoomInfoChangeEvent.setParameters(roomInfoChangeParameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), onRoomInfoChangeEvent);
        });

        return true;
    }

    @Override
    public boolean setTtl(EzyUser peer, int ttl) {
        if (!isLeaderClient(peer)) return false;

        this.ttl = ttl;

        return true;
    }

    @Override
    public boolean setCustomPlayerProperties(EzyUser peer, int playerId, CustomHashtable customPlayerProperties) {
        var roomPlayerOption = roomPlayerLst.stream().filter(x -> x.getPlayerId() == playerId).findAny();
        if (!roomPlayerOption.isPresent()) return false;

        var roomPlayer = roomPlayerOption.get();

        var keySet = customPlayerProperties.keySet();
        for (var key : keySet) {
            var value = customPlayerProperties.get(key);

            if (value == null) {
                if (roomPlayer.getCustomProperties().containsKey(key)) {
                    roomPlayer.getCustomProperties().remove(key);
                }
            }
            else {
                if (!roomPlayer.getCustomProperties().containsKey(key))
                    roomPlayer.getCustomProperties().put(key, value);
                else  roomPlayer.getCustomProperties().replace(key, value);
            }
        }

        threadPool.execute(() -> {
            var onCustomPlayerPropertiesChangeEvent = new OperationEvent(EventCode.OnCustomPlayerPropertiesChange);
            var customPlayerPropertiesChangeParameters = new CustomHashtable();
            customPlayerPropertiesChangeParameters.put(ParameterCode.Data, new Object[] {
                    roomPlayer.getPlayerId(),
                    customPlayerProperties
            });
            onCustomPlayerPropertiesChangeEvent.setParameters(customPlayerPropertiesChangeParameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), onCustomPlayerPropertiesChangeEvent);
        });

        return true;
    }

    private int getObjectId() {
        var objectId = 0;

        while (gameObjectDic.containsKey(objectId)) {
            objectId++;
        }

        return objectId;
    }

    @Override
    public RoomGameObject createGameObject(EzyUser peer, String prefabPath, Object initializeData, Object synchronizationData) {
        var userId = peer.getName();

        var roomPlayerOption = roomPlayerLst.stream().filter(x -> x.getUserId().equals(userId)).findAny();
        if (!roomPlayerOption.isPresent()) return null;

        var roomPlayer = roomPlayerOption.get();

        var roomGameObject = new RoomGameObject();

        roomGameObject.setObjectId(getObjectId());
        roomGameObject.setOwnerId(roomPlayer.getPlayerId());
        roomGameObject.setInitializeData(initializeData);
        roomGameObject.setPrefabPath(prefabPath);
        roomGameObject.setSynchronizationData(synchronizationData);

        gameObjectDic.put(roomGameObject.getObjectId(), roomGameObject);

        threadPool.execute(() -> {
            var event = new OperationEvent(EventCode.OnCreateGameObject);
            var parameters = new CustomHashtable();
            parameters.put(ParameterCode.Data, roomGameObject.toData());
            event.setParameters(parameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), event);
        });

        return roomGameObject;
    }

    @Override
    public boolean destroyGameObject(EzyUser peer, int objectId) {
        var roomGameObject = gameObjectDic.getOrDefault(objectId, null);
        if (roomGameObject == null) return false;

        gameObjectDic.remove(objectId);

        threadPool.execute(() -> {
            var event = new OperationEvent(EventCode.OnDestroyGameObject);
            var parameters = new CustomHashtable();
            parameters.put(ParameterCode.ObjectId, new Object[] {
                    objectId
            });
            event.setParameters(parameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), event);
        });

        return true;
    }

    @Override
    public boolean synchronizationDataGameObject(EzyUser peer, int objectId, Object synchronizationData) {
        var roomGameObject = gameObjectDic.getOrDefault(objectId, null);
        if (roomGameObject == null) return false;

        roomGameObject.setSynchronizationData(synchronizationData);

        threadPool.execute(() -> {
            var event = new OperationEvent(EventCode.OnSynchronizationDataGameObject);
            var parameters = new CustomHashtable();
            parameters.put(ParameterCode.Data, new Object[] {
                    objectId,
                    synchronizationData
            });
            event.setParameters(parameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), event);
        });

        return true;
    }

    @Override
    public boolean rpcGameObject(EzyUser peer, int objectId, int eunRPCCommand, Object rpcData) {
        var roomGameObject = gameObjectDic.getOrDefault(objectId, null);
        if (roomGameObject == null) return false;

        threadPool.execute(() -> {
            var event = new OperationEvent(EventCode.OnRpcGameObject);
            var eventParameters = new CustomHashtable();
            eventParameters.put(ParameterCode.Data, new Object[] {
                    objectId,
                    eunRPCCommand,
                    rpcData
            });
            event.setParameters(eventParameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), event);
        });

        return true;
    }

    @Override
    public boolean transferOwnerGameObject(EzyUser peer, int objectId, int newOwnerId) {
        var roomGameObject = gameObjectDic.getOrDefault(objectId, null);
        if (roomGameObject == null) return false;
        if (roomGameObject.getOwnerId() == newOwnerId) return false;

        roomGameObject.setOwnerId(newOwnerId);

        threadPool.execute(() -> {
            var event = new OperationEvent(EventCode.OnTransferOwnerGameObject);
            var eventParameters = new CustomHashtable();
            eventParameters.put(ParameterCode.Data, new Object[] {
                    objectId,
                    newOwnerId
            });
            event.setParameters(eventParameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), event);
        });

        return true;
    }

    @Override
    public long getTsCreate() {
        return tsCreate;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int getTtl() {
        return ttl;
    }

    @Override
    public Object[] toFullLobbyData() {
        var customRoomProperties = getCustomRoomProperties();
        var customRoomPropertiesForLobby = getCustomRoomPropertiesForLobby();

        var returnCustomRoomPropertiesForLobby = new CustomHashtable();
        customRoomPropertiesForLobby.forEachRemaining(childCustomRoomPropertiesForLobby -> {
            if (customRoomProperties.containsKey(childCustomRoomPropertiesForLobby)) {
                if (!returnCustomRoomPropertiesForLobby.containsKey(childCustomRoomPropertiesForLobby))
                    returnCustomRoomPropertiesForLobby.put(childCustomRoomPropertiesForLobby, customRoomProperties.get(childCustomRoomPropertiesForLobby));
                else returnCustomRoomPropertiesForLobby.replace(childCustomRoomPropertiesForLobby, customRoomProperties.get(childCustomRoomPropertiesForLobby));
            }
        });

        return new Object[] {
                roomId,
                isOpen,
                maxPlayer,
                !EzyStrings.isNoContent(password),
                getPlayerCount(),
                returnCustomRoomPropertiesForLobby
        };
    }

    @Override
    public Object[] toFullData() {
        var returnRoomPlayer = new LinkedList<Object>();
        for (var i = 0; i < roomPlayerLst.size(); i++) {
            returnRoomPlayer.add(roomPlayerLst.get(i).toData());
        }

        var gameObjectValues = gameObjectDic.values().iterator();
        var returnGameObjectDic = new LinkedList<Object>();
        while (gameObjectValues.hasNext()) {
            returnGameObjectDic.add(gameObjectValues.next().toData());
        }

        return new Object[]{
                roomId,
                isOpen,
                maxPlayer,
                password,
                returnRoomPlayer,
                customRoomProperties,
                customRoomPropertiesForLobby,
                isVisible,
                leaderClientUserId,
                tsCreate,
                ttl,
                returnGameObjectDic
        };
    }

    @Override
    public Iterator<String> getUserIdIterator(int excludeUserId) {
        return getUserIdIterable(excludeUserId).iterator();
    }

    @Override
    public ILobby getLobby() {
        return lobby;
    }

    private Iterable<String> getUserIdIterable(int excludeUserId) {
        var userIdLst = new LinkedList<String>();

        for (var roomPlayer : roomPlayerLst) {
            if (roomPlayer.getPlayerId() != excludeUserId) userIdLst.add(roomPlayer.getUserId());
        }

        return userIdLst;
    }

    private boolean isLeaderClient(@NonNull EzyUser peer) {
        var userId = peer.getName();
        return leaderClientUserId.equals(userId);
    }

    private boolean changeLeaderClient(int newLeaderClientPlayerId) {
        var newLeaderClientOptional = roomPlayerLst.stream().filter(x -> x.getPlayerId() == newLeaderClientPlayerId).findAny();
        if (!newLeaderClientOptional.isPresent()) return false;

        var newLeaderClient = newLeaderClientOptional.get();
        var leaderClientUserId = newLeaderClient.getUserId();

        var peer = userService.getPeer(leaderClientUserId);
        if (peer == null) return false;

        this.leaderClientUserId = leaderClientUserId;

        threadPool.execute(() -> {
            var onLeaderClientChangeEvent = new OperationEvent(EventCode.OnLeaderClientChange);
            var leaderClientChangeParameters = new CustomHashtable();
            leaderClientChangeParameters.put(ParameterCode.Data, newLeaderClient.toData());
            onLeaderClientChangeEvent.setParameters(leaderClientChangeParameters);

            userService.sendEventToSomePeerByUserIds(getUserIdIterator(-1), onLeaderClientChangeEvent);
        });

        return true;
    }

    private boolean isLeaderClientNotFound() {
        if (EzyStrings.isNoContent(leaderClientUserId)) return true;

        var isContainPeer = userService.containsPeer(leaderClientUserId);

        return !isContainPeer;
    }

    public Room(int roomId, @NonNull RoomOption roomOption, @NonNull ILobby lobby, @NonNull IUserService userService) {
        this.roomId = roomId;
        this.roomPlayerLst = new LinkedList<>();

        this.maxPlayer = roomOption.getMaxPlayer();

        this.isVisible = roomOption.isVisible();
        this.isOpen = roomOption.isOpen();
        this.password = roomOption.getPassword();
        this.lobby = lobby;

        this.tsCreate = System.currentTimeMillis();
        this.ttl = roomOption.getTtl();
        this.gameObjectDic = new HashMap<Integer, RoomGameObject>();

        this.userService = userService;

        this.threadPool = Executors.newSingleThreadExecutor();
        {
            this.customRoomProperties = new CustomHashtable();
            this.customRoomProperties.putAll(roomOption.getCustomRoomProperties());
        }

        {
            this.customRoomPropertiesForLobby = new LinkedList<>();
            this.customRoomPropertiesForLobby.addAll(roomOption.getCustomRoomPropertiesForLobby());
        }

        leaderClientUserId = "";

        currentPlayerId = 0;
    }
}