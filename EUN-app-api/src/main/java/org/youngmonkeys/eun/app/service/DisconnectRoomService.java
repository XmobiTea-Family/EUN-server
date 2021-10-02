package org.youngmonkeys.eun.app.service;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import lombok.NonNull;
import lombok.var;
import org.youngmonkeys.eun.app.entity.DisconnectRoomInfo;

import java.util.Hashtable;
import java.util.LinkedList;

@EzySingleton
public class DisconnectRoomService extends EzyLoggable implements IDisconnectRoomService {
    @EzyAutoBind
    private ITimerService timerService;

    private Hashtable<String, DisconnectRoomInfo> disconnectRoomDic;

    @Override
    public boolean addDisconnectRoom(@NonNull String userId, @NonNull DisconnectRoomInfo disconnectRoomInfo) {
        if (disconnectRoomDic.containsKey(userId)) disconnectRoomDic.replace(userId, disconnectRoomInfo);
        else disconnectRoomDic.put(userId, disconnectRoomInfo);

        return true;
    }

    @Override
    public DisconnectRoomInfo getDisconnectRoom(@NonNull String userId) {
        return disconnectRoomDic.getOrDefault(userId, null);
    }

    @Override
    public boolean removeDisconnectRoom(@NonNull String userId) {
        if (disconnectRoomDic.containsKey(userId)) {
            disconnectRoomDic.remove(userId);
            return true;
        }

        return true;
    }

    private void onEverySecond() {
        var tsNow = System.currentTimeMillis();

        var values = disconnectRoomDic.values();

        var disconnectRoomInfoTimeoutLst = new LinkedList<DisconnectRoomInfo>();
        for (var disconnectRoomInfo : values) {
            if (disconnectRoomInfo.getTsTimeoutReconnect() < tsNow) {
                disconnectRoomInfoTimeoutLst.add(disconnectRoomInfo);
            }
        }

        for (var i = 0; i < disconnectRoomInfoTimeoutLst.size(); i++) {
            var disconnectRoomInfo = disconnectRoomInfoTimeoutLst.get(i);
            var userId = disconnectRoomInfo.getRoomPlayer().getUserId();
            removeDisconnectRoom(userId);
            disconnectRoomInfo.getRoom().leaveRoomBecauseTimeoutReconnect(userId);
        }
    }

    public DisconnectRoomService() {
        disconnectRoomDic = new Hashtable<>();

        var thread = new Thread(() -> {
            try {
                Thread.sleep(300);

                timerService.subscriberEverySecond(() -> onEverySecond());
            }
            catch (Exception ex) {
                logger.error("DisconnectRoomService", ex);
            }
        });

        thread.start();
    }
}
