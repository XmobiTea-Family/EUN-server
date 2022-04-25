package org.youngmonkeys.xmobitea.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.request.ChangeRoomInfoOperationRequest;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.app.controller.handler.base.RoomRequestHandler;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ParameterCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ReturnCode;

@EzySingleton
public class ChangeRoomInfoRequestHandler extends RoomRequestHandler {
    @Override
    public Integer getCode() {
        return OperationCode.ChangeRoomInfo;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, ChangeRoomInfoOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var currentRoom = getCurrentRoomPeer(peer);

        if (currentRoom == null) {
            return newOperationInvalid(operationRequest);
        }

        var response = new OperationResponse(operationRequest);

        var eunHashtable = request.getEunHashtable();

        if (eunHashtable.containsKey(ParameterCode.MaxPlayer)) {
            var data = eunHashtable.getInt(ParameterCode.MaxPlayer);
            currentRoom.setMaxPlayer(peer, data);
        }

        if (eunHashtable.containsKey(ParameterCode.CustomRoomProperties)) {
            var data = eunHashtable.getEUNHashtable(ParameterCode.CustomRoomProperties);
            currentRoom.setCustomRoomProperties(peer, data);
        }

        if (eunHashtable.containsKey(ParameterCode.CustomRoomPropertiesForLobby)) {
            var data = eunHashtable.getEUNHashtable(ParameterCode.CustomRoomPropertiesForLobby);
            currentRoom.setCustomRoomPropertiesForLobby(peer, data);
        }

        if (eunHashtable.containsKey(ParameterCode.IsOpen)) {
            var data = eunHashtable.getBool(ParameterCode.IsOpen);
            currentRoom.setOpen(peer, data);
        }

        if (eunHashtable.containsKey(ParameterCode.IsVisible)) {
            var data = eunHashtable.getBool(ParameterCode.IsVisible);
            currentRoom.setVisible(peer, data);
        }

        if (eunHashtable.containsKey(ParameterCode.Password)) {
            var data = eunHashtable.getString(ParameterCode.Password);
            currentRoom.setPassword(peer, data);
        }

        if (eunHashtable.containsKey(ParameterCode.Ttl)) {
            var data = eunHashtable.getInt(ParameterCode.Ttl);
            currentRoom.setTtl(peer, data);
        }

        response.setReturnCode(ReturnCode.Ok);
        return response;
    }
}
