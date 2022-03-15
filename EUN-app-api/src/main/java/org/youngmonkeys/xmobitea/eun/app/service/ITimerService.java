package org.youngmonkeys.xmobitea.eun.app.service;

public interface ITimerService {
    void subscriberEverySecond(Runnable runnable);

    void unSbscriberEverySecond(Runnable runnable);

    void subscriberEveryMinute(Runnable runnable);

    void unSubscriberEveryMinute(Runnable runnable);

    void subscriberEveryHour(Runnable runnable);

    void unSubscriberEveryHour(Runnable runnable);

    void subscriberEveryDay(Runnable runnable);

    void unSubscriberEveryDay(Runnable runnable);
}
