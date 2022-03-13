package org.youngmonkeys.eun.common.constant;

public final class EventCode {
    public static final int OnChatAll = 0;
    public static final int OnChatLobby = 1;
    public static final int OnJoinRoom = 2;
    public static final int OnLeftRoom = 3;
    public static final int OnPlayerJoinRoom = 4;
    public static final int OnPlayerLeftRoom = 5;
    public static final int OnLeaderClientChange = 6;
    public static final int OnChatRoom = 7;
    public static final int OnRoomInfoChange = 8;
    public static final int OnCustomPlayerPropertiesChange = 9;
    public static final int OnCreateGameObject = 10;
    public static final int OnDestroyGameObject = 11;
    public static final int OnSynchronizationDataGameObject = 12;
    public static final int OnRpcGameObject = 13;
    public static final int OnTransferOwnerGameObject = 14;
    public static final int OnVoiceChat = 15;
    public static final int OnCustomGameObjectPropertiesChange = 16;

    private EventCode() {}
}
