package org.youngmonkeys.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.controller.handler.base.RoomRequestHandler;
import org.youngmonkeys.eun.app.request.ChangeRoomInfoOperationRequest;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.common.constant.OperationCode;
import org.youngmonkeys.eun.common.constant.ParameterCode;
import org.youngmonkeys.eun.common.constant.ReturnCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

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

        var customHashtable = request.getCustomHashtable();

        if (customHashtable.containsKey(ParameterCode.MaxPlayer)) {
            var data = (int)customHashtable.get(ParameterCode.MaxPlayer);
            currentRoom.setMaxPlayer(peer, data);
        }

        if (customHashtable.containsKey(ParameterCode.CustomRoomProperties)) {
            var data = (CustomHashtable)customHashtable.get(ParameterCode.CustomRoomProperties);
            currentRoom.setCustomRoomProperties(peer, data);
        }

        if (customHashtable.containsKey(ParameterCode.CustomRoomPropertiesForLobby)) {
            var data = (CustomHashtable)customHashtable.get(ParameterCode.CustomRoomPropertiesForLobby);
            currentRoom.setCustomRoomPropertiesForLobby(peer, data);
        }

        if (customHashtable.containsKey(ParameterCode.IsOpen)) {
            var data = (boolean)customHashtable.get(ParameterCode.IsOpen);
            currentRoom.setOpen(peer, data);
        }

        if (customHashtable.containsKey(ParameterCode.IsVisible)) {
            var data = (boolean)customHashtable.get(ParameterCode.IsVisible);
            currentRoom.setVisible(peer, data);
        }

        if (customHashtable.containsKey(ParameterCode.Password)) {
            var data = (String)customHashtable.get(ParameterCode.Password);
            currentRoom.setPassword(peer, data);
        }

        if (customHashtable.containsKey(ParameterCode.Ttl)) {
            var data = (int)customHashtable.get(ParameterCode.Ttl);
            currentRoom.setTtl(peer, data);
        }

        response.setReturnCode(ReturnCode.Ok);
        return response;
    }
}