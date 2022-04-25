package org.youngmonkeys.xmobitea.eun.app.controller;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.event.EzyUserAccessAppEvent;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.service.IDisconnectRoomService;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_ACCESS_APP;

@EzySingleton
@EzyEventHandler(USER_ACCESS_APP) // refer EzyEventType
public class UserAccessAppController
        extends EzyAbstractAppEventController<EzyUserAccessAppEvent> {

    @EzyAutoBind
    private IDisconnectRoomService disconnectRoomService;

    @Override
    public void handle(EzyAppContext ctx, EzyUserAccessAppEvent event) {
        var peer = event.getUser();
        var userId = peer.getName();

        event.getOutput().add(System.currentTimeMillis());

        logger.info("USER_ACCESS_APP: " + userId);
    }
}
