package org.youngmonkeys.eun.app.entity;

import com.tvd12.ezyfoxserver.statistics.EzyNetworkRoStats;

public class NetworkStatistics implements INetworkStatistics {
    private EzyNetworkRoStats networkRoStats;

    @Override
    public long getReadBytes() {
        return networkRoStats.getReadBytes();
    }

    @Override
    public long getWrittenBytes() {
        return networkRoStats.getWrittenBytes();
    }

    @Override
    public long getDroppedInBytes() {
        return networkRoStats.getDroppedInBytes();
    }

    @Override
    public long getWriteErrorBytes() {
        return networkRoStats.getWriteErrorBytes();
    }

    @Override
    public long getTotalReadBytes() {
        return getReadBytes() + getDroppedInBytes();
    }

    @Override
    public long getTotalWriteBytes() {
        return getWrittenBytes() + getWriteErrorBytes();
    }

    @Override
    public long getReadPackets() {
        return networkRoStats.getReadPackets();
    }

    @Override
    public long getWrittenPackets() {
        return networkRoStats.getWrittenPackets();
    }

    @Override
    public long getDroppedInPackets() {
        return networkRoStats.getDroppedInPackets();
    }

    @Override
    public long getDroppedOutPackets() {
        return networkRoStats.getDroppedOutPackets();
    }

    @Override
    public long getWriteErrorPackets() {
        return networkRoStats.getWriteErrorPackets();
    }

    @Override
    public long getTotalReadPackets() {
        return getReadPackets() + getDroppedInPackets();
    }

    @Override
    public long getTotalWritePackets() {
        return getWrittenPackets() + getWriteErrorPackets() + getDroppedOutPackets();
    }

    public NetworkStatistics(EzyNetworkRoStats networkRoStats) {
        this.networkRoStats = networkRoStats;
    }
}
