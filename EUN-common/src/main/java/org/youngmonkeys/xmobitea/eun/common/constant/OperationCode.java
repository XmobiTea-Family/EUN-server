package org.youngmonkeys.xmobitea.eun.common.constant;

public final class OperationCode {
    public static final int SyncTs = 0;
    public static final int GetLobbyStatsLst = 1;            // return all lobby in this application version
    public static final int GetCurrentLobbyStats = 2;          // return lobby stats in this application version
    public static final int JoinLobby = 3;              // join lobby
    public static final int LeaveLobby = 4;             // leave lobby
    public static final int ChatAll = 5;                // chat all
    public static final int ChatLobby = 6;              // chat lobby
    public static final int ChatRoom = 7;               // chat room
    public static final int CreateRoom = 8;             // create room
    public static final int JoinOrCreateRoom = 9;       // join or create room
    public static final int JoinRoom = 10;               // join room
    public static final int LeaveRoom = 11;             // leave room

    public static final int ChangeLeaderClient = 12;    // change leader client room
    public static final int ChangeRoomInfo = 13;        // change the room info
    public static final int SubscriberChatAll = 14;
    public static final int SubscriberChatLobby = 15;
    public static final int ChangePlayerCustomProperties = 16;

    public static final int RpcGameObjectRoom = 17;                   // send rpc in room
    public static final int CreateGameObjectRoom = 18;
    public static final int DestroyGameObjectRoom = 19;
    public static final int SynchronizationDataGameObjectRoom = 20;
    public static final int TransferGameObjectRoom = 21;
    public static final int VoiceChat = 22;
    public static final int RpcGameObjectRoomTo = 23;
    public static final int ChangeGameObjectCustomProperties = 24;

    public static String getOperationName(int operationCode)
    {
        if (SyncTs == operationCode) return "SyncTs";
        if (GetLobbyStatsLst == operationCode) return "GetLobbyStatsLst";
        if (GetCurrentLobbyStats == operationCode) return "GetCurrentLobbyStats";
        if (JoinLobby == operationCode) return "JoinLobby";
        if (LeaveLobby == operationCode) return "LeaveLobby";
        if (ChatAll == operationCode) return "ChatAll";
        if (ChatLobby == operationCode) return "ChatLobby";
        if (ChatRoom == operationCode) return "ChatRoom";
        if (CreateRoom == operationCode) return "CreateRoom";
        if (JoinOrCreateRoom == operationCode) return "JoinOrCreateRoom";
        if (JoinRoom == operationCode) return "JoinRoom";
        if (LeaveRoom == operationCode) return "LeaveRoom";
        if (ChangeLeaderClient == operationCode) return "ChangeLeaderClient";
        if (ChangeRoomInfo == operationCode) return "ChangeRoomInfo";
        if (SubscriberChatAll == operationCode) return "SubscriberChatAll";
        if (SubscriberChatLobby == operationCode) return "SubscriberChatLobby";
        if (ChangePlayerCustomProperties == operationCode) return "ChangePlayerCustomProperties";
        if (RpcGameObjectRoom == operationCode) return "RpcGameObjectRoom";
        if (CreateGameObjectRoom == operationCode) return "CreateGameObjectRoom";
        if (DestroyGameObjectRoom == operationCode) return "DestroyGameObjectRoom";
        if (SynchronizationDataGameObjectRoom == operationCode) return "SynchronizationDataGameObjectRoom";
        if (TransferGameObjectRoom == operationCode) return "TransferGameObjectRoom";
        if (VoiceChat == operationCode) return "VoiceChat";
        if (RpcGameObjectRoomTo == operationCode) return "RpcGameObjectRoomTo";
        if (ChangeGameObjectCustomProperties == operationCode) return "ChangeGameObjectCustomProperties";

        return "Invalid";
    }

    private OperationCode() {}
}
