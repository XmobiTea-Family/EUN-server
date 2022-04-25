package org.youngmonkeys.xmobitea.eun.common.constant;

public final class ReturnCode {
    public static final int InvalidRequestParameters = -6;
    public static final int OperationInvalid = -3;
    public static final int InternalServerError = -2;
    public static final int NotOk = -1;

    public static final int Ok = 0;
    public static final int RoomFull = 10;
    public static final int RoomClosed = 11;
    public static final int LobbyFull = 12;
    public static final int RoomNotFound = 14;
    public static final int RoomPasswordWrong = 15;

    public static final int UserInRoom = 16;

    private ReturnCode() {}
}
