package org.youngmonkeys.xmobitea.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.controller.handler.base.LobbyRequestHandler;
import org.youngmonkeys.xmobitea.eun.app.request.GetLobbyStatsLstOperationRequest;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ReturnCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ParameterCode;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNHashtable;

import java.util.LinkedList;

@EzySingleton
public class GetLobbyStatsLstRequestHandler extends LobbyRequestHandler {
    @Override
    public Integer getCode() {
        return OperationCode.GetLobbyStatsLst;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, GetLobbyStatsLstOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var skip = request.getSkip();
        var limit = request.getLimit();

        var returnLobbyLst = new LinkedList<Object>();
        var lobbyLst = lobbyService.getLobbyLst();

        while (lobbyLst.hasNext()) {
            var lobby = lobbyLst.next();

            if (skip > 0) {
                skip--;
                continue;
            }

            returnLobbyLst.add(lobby.getLobbyStats());
            limit--;
            if (limit <= 0) break;
        }

        var response = new OperationResponse(operationRequest);
        response.setReturnCode(ReturnCode.Ok);

        var parameters = new EUNHashtable();
        parameters.put(ParameterCode.Data, new Object[] {
                returnLobbyLst
        });
        response.setParameters(parameters);

        return response;
    }
}