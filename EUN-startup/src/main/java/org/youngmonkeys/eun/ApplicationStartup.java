package org.youngmonkeys.eun;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.tvd12.ezyfox.codec.JacksonCodecCreator;
import com.tvd12.ezyfoxserver.constant.EzyEventType;
import com.tvd12.ezyfoxserver.embedded.EzyEmbeddedServer;
import com.tvd12.ezyfoxserver.ext.EzyAppEntry;
import com.tvd12.ezyfoxserver.ext.EzyPluginEntry;
import com.tvd12.ezyfoxserver.setting.*;
import org.youngmonkeys.eun.plugin.PluginEntry;
import org.youngmonkeys.eun.plugin.PluginEntryLoader;
import org.youngmonkeys.eun.app.AppEntry;
import org.youngmonkeys.eun.app.AppEntryLoader;

public class ApplicationStartup {
	
	public static final String ZONE_NAME = "EUN";
	public static final String APP_NAME = "EUN";
	public static final String PLUGIN_NAME = "EUN";

	public static void main(String[] args) throws Exception {
		
		EzyPluginSettingBuilder pluginSettingBuilder = new EzyPluginSettingBuilder()
				.name(PLUGIN_NAME)
				.addListenEvent(EzyEventType.USER_LOGIN)
				.entryLoader(DecoratedPluginEntryLoader.class);
		
		EzyAppSettingBuilder appSettingBuilder = new EzyAppSettingBuilder()
				.name(APP_NAME)
				.entryLoader(DecoratedAppEntryLoader.class);

		EzyUserManagementSettingBuilder userManagementSettingBuilder = new EzyUserManagementSettingBuilder()
				.maxSessionPerUser(1)
				.userNamePattern("^[a-zA-Z0-9_.#]{15,40}$")
				.allowChangeSession(true);

		EzyZoneSettingBuilder zoneSettingBuilder = new EzyZoneSettingBuilder()
				.name(ZONE_NAME)
				.application(appSettingBuilder.build())
				.userManagement(userManagementSettingBuilder.build())
				.plugin(pluginSettingBuilder.build());

		EzySimpleWebSocketSetting webSocketSetting = new EzyWebSocketSettingBuilder()
				.active(true) // active or not,  default true
				.address("0.0.0.0") // loopback address, default 0.0.0.0
				.codecCreator(JacksonCodecCreator.class) // encoder/decoder creator, default JacksonCodecCreator
				.maxFrameSize(32678) // max frame size, default 32768
				.port(2208) // port, default 3005
				.writerThreadPoolSize(8) // thread pool size for socket writer, default 8
				.build();

		EzyUdpSettingBuilder udpSettingBuilder = new EzyUdpSettingBuilder()
				.active(true)
				.address("0.0.0.0")
				.maxRequestSize(2048)
				;

		EzySimpleSettings settings = new EzySettingsBuilder()
				.zone(zoneSettingBuilder.build())
				.udp(udpSettingBuilder.build())
				.websocket(webSocketSetting)
				.build();
		
		EzyEmbeddedServer server = EzyEmbeddedServer.builder()
				.settings(settings)
				.build();
		server.start();
	}
	
	public static class DecoratedPluginEntryLoader extends PluginEntryLoader {
		
		@Override
		public EzyPluginEntry load() throws Exception {
			return new PluginEntry() {
				
				@Override
				protected String getConfigFile(EzyPluginSetting setting) {
					return Paths.get(getPluginPath(setting), "config", "config.properties")
							.toString();
				}
				
				private String getPluginPath(EzyPluginSetting setting) {
					Path pluginPath = Paths.get("EUN-plugin");
					if(!Files.exists(pluginPath))
						pluginPath = Paths.get("../EUN-plugin");
					return pluginPath.toString();
				}
			};
		}
	}
	
	public static class DecoratedAppEntryLoader extends AppEntryLoader {
		
		@Override
		public EzyAppEntry load() throws Exception {
			return new AppEntry() {
				
				@Override
				protected String getConfigFile(EzyAppSetting setting) {
					return Paths.get(getAppPath(), "config", "config.properties")
							.toString();
				}
				
				private String getAppPath() {
					Path pluginPath = Paths.get("EUN-app-entry");
					if(!Files.exists(pluginPath))
						pluginPath = Paths.get("../EUN-app-entry");
					return pluginPath.toString();
				}
			};
		}
	}
}
