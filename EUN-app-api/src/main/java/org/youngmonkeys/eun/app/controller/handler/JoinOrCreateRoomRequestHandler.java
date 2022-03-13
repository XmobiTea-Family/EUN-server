package org.youngmonkeys.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.controller.handler.base.RoomRequestHandler;
import org.youngmonkeys.eun.app.entity.ILobby;
import org.youngmonkeys.eun.app.entity.IRoom;
import org.youngmonkeys.eun.app.request.JoinOrCreateRoomOperationRequest;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.common.constant.OperationCode;
import org.youngmonkeys.eun.common.constant.ParameterCode;
import org.youngmonkeys.eun.common.constant.ReturnCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;
import org.youngmonkeys.eun.common.service.CustomRandom;

import java.util.LinkedList;
import java.util.List;

@EzySingleton
public class JoinOrCreateRoomRequestHandler extends RoomRequestHandler {
    @EzyAutoBind
    private CreateRoomRequestHandler createRoomRequestHandler;

    @EzyAutoBind
    private JoinRoomRequestHandler joinRoomRequestHandler;

    @Override
    public Integer getCode() {
        return OperationCode.JoinOrCreateRoom;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, JoinOrCreateRoomOperationRequest.class);
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

        var expectedRoomLst = getExpectedRoomLst(currentLobby, expectedProperties, targetExpectedCount);
        IRoom expectedRoom = expectedRoomLst.size() != 0 ? expectedRoomLst.get(CustomRandom.range(0, expectedRoomLst.size())) : null;

        if (expectedRoom == null) {
            return createRoomRequestHandler.handle(peer, operationRequest);
        }
        else {
            var joinRoomOperationRequest = new OperationRequest();
            joinRoomOperationRequest.setRequestId(operationRequest.getRequestId());
            joinRoomOperationRequest.setOperationCode(operationRequest.getOperationCode());

            var parameters = new CustomHashtable();
            parameters.put(ParameterCode.RoomId, expectedRoom.getRoomId());
            parameters.put(ParameterCode.Password, expectedRoom.getPassword());

            joinRoomOperationRequest.setParameters(parameters);

            return joinRoomRequestHandler.handle(peer, joinRoomOperationRequest);
        }
    }

    private List<IRoom> getExpectedRoomLst(ILobby currentLobby, CustomHashtable expectedProperties, int targetExpectedCount) {
        var roomIterator = currentLobby.getRoomIterator();

        var expectedRoomLst = new LinkedList<IRoom>();
        while (roomIterator.hasNext()) {
            var conditionTrueCount = 0;

            var roomItem = roomIterator.next();

            if (!roomItem.getIsOpen()) continue;
            if (!roomItem.getIsVisible()) continue;
            if (roomItem.getPlayerCount() == 0) continue;
            if (roomItem.getPlayerCount() >= roomItem.getMaxPlayer()) continue;

            if (expectedProperties != null) {

                var roomCustomRoomProperties = roomItem.getCustomRoomProperties();

                var keySet = expectedProperties.keySet();
                for (var key : keySet) {
                    var expectedValue = expectedProperties.getOrDefault(key, null);
                    var roomValue = roomCustomRoomProperties.getOrDefault(key, null);

                    if (expectedValue == null && roomValue == null) conditionTrueCount++;
                    else {
                        if (expectedValue == null) { }
                        else {
                            if (expectedValue.equals(roomValue)) {
                                conditionTrueCount++;
                            }
                        }
                    }

                    if (conditionTrueCount >= targetExpectedCount) break;
                }
            }

            if (conditionTrueCount < targetExpectedCount) continue;

            expectedRoomLst.add(roomItem);
        }

        return expectedRoomLst;
    }
}