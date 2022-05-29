package org.youngmonkeys.xmobitea.eun.app.controller;

import static com.tvd12.ezyfoxserver.constant.EzyEventNames.SERVER_READY;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.core.annotation.EzyEventHandler;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import com.tvd12.ezyfoxserver.controller.EzyAbstractAppEventController;
import com.tvd12.ezyfoxserver.event.EzyServerReadyEvent;
import com.tvd12.ezyfoxserver.util.EzyBannerPrinter;
import lombok.var;
import org.youngmonkeys.xmobitea.eun.app.AppEntry;

@EzySingleton
@EzyEventHandler(SERVER_READY) // refer EzyEventType
public class ServerReadyController 
		extends EzyAbstractAppEventController<EzyServerReadyEvent> {

	@Override
	public void handle(EzyAppContext ctx, EzyServerReadyEvent event) {
		logger.info("XmobiTea EUN app: fire custom app ready");

		var ezyBannerPrinter = new EzyBannerPrinter();
		System.out.println(ezyBannerPrinter.getBannerText("eun-banner.txt"));
		System.out.println("Welcome to EUN Version " + AppEntry.EUN_Version);
	}
	
}
