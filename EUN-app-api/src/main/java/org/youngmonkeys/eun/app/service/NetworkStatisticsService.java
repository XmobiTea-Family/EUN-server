package org.youngmonkeys.eun.app.service;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfoxserver.context.EzyAppContext;
import lombok.var;
import org.youngmonkeys.eun.app.controller.handler.base.IWaitForEzyInitDone;
import org.youngmonkeys.eun.app.entity.INetworkStatistics;
import org.youngmonkeys.eun.app.entity.NetworkStatistics;

@EzySingleton
public class NetworkStatisticsService extends EzyLoggable implements INetworkStatisticsService, IWaitForEzyInitDone {
    enum Unit {
        BYTE(1, "B"),
        KILOBYTE(BYTE.getIntValue() * 1024, "KB"),
        MEGABYTE(KILOBYTE.getIntValue() * 1024, "MB"),
        GIGABYTE(MEGABYTE.getIntValue() * 1024, "GB"),
        TEGABYTE(GIGABYTE.getIntValue() * 1024, "TG");

        private final long intValue;
        private final String strValue;

        private Unit(long intValue, String strValue) {
            this.intValue = intValue;
            this.strValue = strValue;
        }

        public long getIntValue() {
            return this.intValue;
        }

        public String getStrValue() {
            return this.strValue;
        }
    }

    @EzyAutoBind
    private EzyAppContext appContext;

    @EzyAutoBind
    private ITimerService timerService;

    private INetworkStatistics socketNetworkStatistics;

    private INetworkStatistics webSocketNetworkStatistics;

    @Override
    public long getTotalReadPackets() {
        return socketNetworkStatistics.getTotalReadPackets() + webSocketNetworkStatistics.getTotalReadPackets();
    }

    @Override
    public long getTotalWritePackets() {
        return socketNetworkStatistics.getTotalWritePackets() + webSocketNetworkStatistics.getTotalWritePackets();
    }

    @Override
    public long getTotalErrorReadPackets() {
        return socketNetworkStatistics.getWriteErrorPackets() + webSocketNetworkStatistics.getWriteErrorPackets();
    }

    @Override
    public long getTotalErrorWritePackets() {
        return socketNetworkStatistics.getDroppedInPackets() + socketNetworkStatistics.getWriteErrorPackets() + webSocketNetworkStatistics.getDroppedInPackets() + webSocketNetworkStatistics.getWriteErrorPackets();
    }

    @Override
    public long getTotalReadBytes() {
        return socketNetworkStatistics.getTotalReadBytes() + webSocketNetworkStatistics.getTotalReadBytes();
    }

    @Override
    public long getTotalWriteBytes() {
        return socketNetworkStatistics.getTotalWriteBytes() + webSocketNetworkStatistics.getTotalWriteBytes();
    }

    @Override
    public long getTotalErrorReadBytes() {
        return socketNetworkStatistics.getDroppedInBytes() + webSocketNetworkStatistics.getDroppedInBytes();
    }

    @Override
    public long getTotalErrorWriteBytes() {
        return socketNetworkStatistics.getWriteErrorBytes() + webSocketNetworkStatistics.getWriteErrorBytes();
    }

    @Override
    public INetworkStatistics getSocketNetworkStatistics() {
        return socketNetworkStatistics;
    }

    @Override
    public INetworkStatistics getWebSocketNetworkStatistics() {
        return webSocketNetworkStatistics;
    }

    @Override
    public void config() {
        var ezyStatistics = appContext.getParent().getParent().getServer().getStatistics();

        socketNetworkStatistics = new NetworkStatistics(ezyStatistics.getSocketStats().getNetworkStats());
        webSocketNetworkStatistics = new NetworkStatistics(ezyStatistics.getWebSocketStats().getNetworkStats());

        timerService.subscriberEverySecond(() -> debug(Unit.BYTE));
    }

    private void debug(Unit unit) {
        logger.info(String.format("[RECV: %,.2f] [SEND: %,.2f] [RECV COUNT: %d] [SEND COUNT: %d] (%s)", getTotalReadBytes() * 1.0f / unit.getIntValue(), getTotalWriteBytes() * 1.0f / unit.getIntValue(), getTotalReadPackets(), getTotalWritePackets(), unit.getStrValue()));
    }
}
