package org.youngmonkeys.xmobitea.eun.app;

import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfoxserver.ext.EzyAbstractAppEntryLoader;
import com.tvd12.ezyfoxserver.ext.EzyAppEntry;

public class AppEntryLoader extends EzyAbstractAppEntryLoader {

	@Override
	public EzyAppEntry load() throws Exception {
		return EzyClasses.newInstance("org.youngmonkeys.xmobitea.eun.app.AppEntry");
	}

}
