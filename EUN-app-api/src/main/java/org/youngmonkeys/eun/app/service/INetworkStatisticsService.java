package org.youngmonkeys.eun.app.service;

import org.youngmonkeys.eun.app.entity.INetworkStatistics;

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
