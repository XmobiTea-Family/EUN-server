package org.youngmonkeys.xmobitea.eun.app.controller;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.event.EzyUserAccessAppEvent;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.service.IUserService;
import org.youngmonkeys.xmobitea.eun.app.service.IDisconnectRoomService;
import org.youngmonkeys.xmobitea.eun.common.constant.PeerPropertyCode;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_ACCESS_APP;

@EzySingleton
@EzyEventHandler(USER_ACCESS_APP) // refer EzyEventType
public class UserAccessAppController
        extends EzyAbstractAppEventController<EzyUserAccessAppEvent> {

    @EzyAutoBind
    private IUserService userService;

    @EzyAutoBind
    private IDisconnectRoomService disconnectRoomService;

    @Override
    public void handle(EzyAppContext ctx, EzyUserAccessAppEvent event) {
        var peer = event.getUser();
        var userId = peer.getName();

        event.getOutput().add(System.currentTimeMillis());

        var disconnectRoomInfo = disconnectRoomService.getDisconnectRoom(userId);
        if (disconnectRoomInfo != null) {
            var room = disconnectRoomInfo.getRoom();

            if (room.getLobby().joinLobby(userId, peer))
                peer.setProperty(PeerPropertyCode.Lobby, room.getLobby());

            room.reconnectRoom(peer);

            disconnectRoomService.removeDisconnectRoom(userId);
        }

        logger.info("USER_ACCESS_APP: " + userId);
    }
}
