package org.youngmonkeys.xmobitea.eun.app.controller.handler;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.controller.handler.base.LobbyRequestHandler;
import org.youngmonkeys.xmobitea.eun.app.entity.ILobby;
import org.youngmonkeys.xmobitea.eun.app.request.JoinLobbyOperationRequest;
import org.youngmonkeys.xmobitea.eun.app.request.OperationRequest;
import org.youngmonkeys.xmobitea.eun.app.response.OperationResponse;
import org.youngmonkeys.xmobitea.eun.common.constant.OperationCode;
import org.youngmonkeys.xmobitea.eun.common.constant.PeerPropertyCode;
import org.youngmonkeys.xmobitea.eun.common.constant.ReturnCode;

@EzySingleton
public class JoinLobbyRequestHandler extends LobbyRequestHandler {
    @Override
    public Integer getCode() {
        return OperationCode.JoinLobby;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        var request = requestConverterService.createOperationRequest(operationRequest, JoinLobbyOperationRequest.class);
        if (request == null || !request.isValidRequest()) {
            return newInvalidRequestParameters(operationRequest);
        }

        var userId = peer.getName();
        var response = new OperationResponse(operationRequest);
        response.setReturnCode(ReturnCode.Ok);

        var currentLobby = peer.getProperty(PeerPropertyCode.Lobby, ILobby.class);
        if (currentLobby != null) currentLobby.leaveLobby(userId);

        currentLobby = lobbyService.getLobby(request.getLobbyId());
        currentLobby.joinLobby(userId, peer);
        peer.setProperty(PeerPropertyCode.Lobby, currentLobby);

        return response;
    }
}