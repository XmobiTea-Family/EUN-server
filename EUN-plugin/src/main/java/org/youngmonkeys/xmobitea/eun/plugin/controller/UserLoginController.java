package org.youngmonkeys.xmobitea.eun.plugin.controller;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.USER_LOGIN;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyData;
import com.tvd12.ezyfoxserver.context.EzyPluginContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractPluginEventController;
import com.tvd12.ezyfoxserver.event.EzyUserLoginEvent;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.common.entity.EUNArray;

@EzySingleton
@EzyEventHandler(USER_LOGIN)
public class UserLoginController extends EzyAbstractPluginEventController<EzyUserLoginEvent> {

	@Override
	public void handle(EzyPluginContext ctx, EzyUserLoginEvent event) {
		// the userId
		var userId = event.getUsername();

		// the customData
		var customData = new EUNArray.Builder().addAll(((EzyArray)event.getData()).toList());

		logger.info("{} login in", event.getUsername());
	}
}
