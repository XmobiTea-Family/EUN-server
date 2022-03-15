package org.youngmonkeys.xmobitea.eun.app.service;

import org.youngmonkeys.xmobitea.eun.app.entity.INetworkStatistics;

public interface INetworkStatisticsService {
    long getTotalReadPackets();
    long getTotalWritePackets();
    long getTotalErrorReadPackets();
    long getTotalErrorWritePackets();

    long getTotalReadBytes();
    long getTotalWriteBytes();
    long getTotalErrorReadBytes();
    long getTotalErrorWriteBytes();

    INetworkStatistics getSocketNetworkStatistics();
    INetworkStatistics getWebSocketNetworkStatistics();
}
