package org.youngmonkeys.eun.app.controller;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.event.EzyUserRemovedEvent;
import lombok.var;
import org.youngmonkeys.eun.app.entity.DisconnectRoomInfo;
import org.youngmonkeys.eun.app.entity.ILobby;
import org.youngmonkeys.eun.app.entity.IRoom;
import org.youngmonkeys.eun.app.service.IDisconnectRoomService;
import org.youngmonkeys.eun.app.service.IUserService;
import org.youngmonkeys.eun.common.constant.PeerPropertyCode;
import org.youngmonkeys.eun.common.entity.RoomPlayer;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_REMOVED;

@EzySingleton
@EzyEventHandler(USER_REMOVED) // refer EzyEventType
public class UserRemovedController
        extends EzyAbstractAppEventController<EzyUserRemovedEvent> {

    @EzyAutoBind
    private IUserService userService;

    @EzyAutoBind
    private IDisconnectRoomService disconnectRoomService;

    @Override
    public void handle(EzyAppContext ctx, EzyUserRemovedEvent event) {
        var peer = event.getUser();
        var userId = peer.getName();

        var currentLobby = peer.getProperty(PeerPropertyCode.Lobby, ILobby.class);
        if (currentLobby != null) {
            currentLobby.leaveLobby(userId);
            currentLobby.removeChatPeer(userId);
        }

        var currentRoom = peer.getProperty(PeerPropertyCode.Room, IRoom.class);
        if (currentRoom != null) {
            currentRoom.disconnectRoom(peer);

            var disconnectRoomInfo = new DisconnectRoomInfo();
            disconnectRoomInfo.setRoom(currentRoom);
            disconnectRoomInfo.setRoomPlayer(peer.getProperty(PeerPropertyCode.RoomPlayer, RoomPlayer.class));
            disconnectRoomInfo.setTsTimeoutReconnect(System.currentTimeMillis() + currentRoom.getTtl());

            disconnectRoomService.addDisconnectRoom(userId, disconnectRoomInfo);
        }

        logger.info("USER_REMOVED: " + userId);
    }
}