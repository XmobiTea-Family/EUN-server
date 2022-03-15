package org.youngmonkeys.xmobitea.eun.app.entity;

public interface INetworkStatistics {
    long getReadBytes();

    long getWrittenBytes();

    long getDroppedInBytes();

    long getWriteErrorBytes();

    long getTotalReadBytes();

    long getTotalWriteBytes();


    long getReadPackets();

    long getWrittenPackets();

    long getDroppedInPackets();

    long getDroppedOutPackets();

    long getWriteErrorPackets();

    long getTotalReadPackets();

    long getTotalWritePackets();
}
