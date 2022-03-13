package org.youngmonkeys.eun.app.controller.handler.base;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfoxserver.entity.EzyUser;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.entity.ILobby;
import org.youngmonkeys.eun.app.request.OperationRequest;
import org.youngmonkeys.eun.app.response.OperationResponse;
import org.youngmonkeys.eun.app.service.ILobbyService;
import org.youngmonkeys.eun.common.constant.PeerPropertyCode;

public class LobbyRequestHandler extends RequestHandler {
    @EzyAutoBind
    protected ILobbyService lobbyService;

    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public OperationResponse handle(@NonNull EzyUser peer, @NonNull OperationRequest operationRequest) {
        return null;
    }

    protected ILobby getCurrentLobbyPeer(@NonNull EzyUser peer) {
        var currentLobby = peer.getProperty(PeerPropertyCode.Lobby, ILobby.class);
        if (currentLobby == null) return joinLobbyPeer(peer, 0);

        return currentLobby;
    }

    protected ILobby joinLobbyPeer(@NonNull EzyUser peer, int lobbyId) {
        var userId = peer.getName();

        var currentLobby = peer.getProperty(PeerPropertyCode.Lobby, ILobby.class);
        if (currentLobby != null) {
            if (currentLobby.getLobbyId() == lobbyId) return currentLobby;

            currentLobby.leaveLobby(userId);
        }

        currentLobby = lobbyService.getLobby(lobbyId);
        currentLobby.joinLobby(userId, peer);

        peer.setProperty(PeerPropertyCode.Lobby, currentLobby);

        return currentLobby;
    }
}
