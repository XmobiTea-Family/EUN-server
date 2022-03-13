package org.youngmonkeys.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.controller.handler.base.LobbyRequestHandler;
import org.youngmonkeys.eun.app.request.GetCurrentLobbyStatsOperationRequest;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.common.constant.OperationCode;
import org.youngmonkeys.eun.common.constant.ReturnCode;
import org.youngmonkeys.eun.common.constant.ParameterCode;
import org.youngmonkeys.eun.common.entity.CustomHashtable;

import java.util.LinkedList;

@EzySingleton
public class GetCurrentLobbyStatsRequestHandler extends LobbyRequestHandler {
    @Override
    public Integer getCode() {
        return OperationCode.GetCurrentLobbyStats;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, GetCurrentLobbyStatsOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var skip = request.getSkip();
        var limit = request.getLimit();

        var currentLobby = getCurrentLobbyPeer(peer);

        var returnRoomLst = new LinkedList<Object>();
        var roomIterator = currentLobby.getRoomIterator();
        while (roomIterator.hasNext()) {
            var room = roomIterator.next();
            if (!room.getIsVisible()) continue;

            if (skip > 0) {
                skip--;
                continue;
            }

            returnRoomLst.add(room.toFullLobbyData());

            limit--;
            if (limit <= 0) break;
        }

        var returnLobbyStats = currentLobby.getLobbyStats();

        var response = new OperationResponse(operationRequest);
        response.setReturnCode(ReturnCode.Ok);

        var parameters = new CustomHashtable();
        parameters.put(ParameterCode.Data, new Object[] {
                returnLobbyStats,
                returnRoomLst
        });
        response.setParameters(parameters);

        return response;
    }
}