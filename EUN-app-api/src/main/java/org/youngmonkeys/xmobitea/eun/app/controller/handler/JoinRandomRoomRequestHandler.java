package org.youngmonkeys.xmobitea.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.controller.handler.base.RoomRequestHandler;
import org.youngmonkeys.xmobitea.eun.app.request.JoinRandomRoomOperationRequest;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ParameterCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ReturnCode;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;
import org.youngmonkeys.xmobitea.eun.common.service.CustomRandom;

@EzySingleton
public class JoinRandomRoomRequestHandler extends RoomRequestHandler {
    @EzyAutoBind
    private JoinRoomRequestHandler joinRoomRequestHandler;

    @Override
    public Integer getCode() {
        return OperationCode.JoinRandomRoom;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, JoinRandomRoomOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        if (getCurrentRoomPeer(peer) != null) {
            var response = new OperationResponse(operationRequest);
            response.setReturnCode(ReturnCode.UserInRoom);
            return response;
        }

        var expectedProperties = request.getExpectedProperties();
        var targetExpectedCount = request.getTargetExpectedCount();

        var currentLobby = getCurrentLobbyPeer(peer);

        var expectedRoomLst = RoomRequestHandler.getExpectedRoomLst(currentLobby, expectedProperties, targetExpectedCount);
        var expectedRoom = expectedRoomLst.size() != 0 ? expectedRoomLst.get(CustomRandom.range(0, expectedRoomLst.size())) : null;

        if (expectedRoom == null) {
            var response = new OperationResponse(operationRequest);

            response.setReturnCode(ReturnCode.UserInRoom);
            return response;
        }
        else {
            var joinRoomOperationRequest = new OperationRequest();
            joinRoomOperationRequest.setRequestId(operationRequest.getRequestId());
            joinRoomOperationRequest.setOperationCode(operationRequest.getOperationCode());

            var parameters = new EUNHashtable();
            parameters.add(ParameterCode.RoomId, expectedRoom.getRoomId());
            parameters.add(ParameterCode.Password, expectedRoom.getPassword());

            joinRoomOperationRequest.setParameters(parameters);

            return joinRoomRequestHandler.handle(peer, joinRoomOperationRequest);
        }
    }
}
