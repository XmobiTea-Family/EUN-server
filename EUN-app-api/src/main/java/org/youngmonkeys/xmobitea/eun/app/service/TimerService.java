package org.youngmonkeys.xmobitea.eun.app.service;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.util.EzyLoggable;
import lombok.var;

import java.time.LocalTime;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@EzySingleton
public class TimerService extends EzyLoggable implements ITimerService {

    private final List<Runnable> everySecondRunnableLst;
    private final List<Runnable> everyMinuteRunnableLst;
    private final List<Runnable> everyHourRunnableLst;
    private final List<Runnable> everyDayRunnableLst;

    private void handle() {
        var now = LocalTime.now();

        onEverySecond();
        if (now.getSecond() == 0) onEveryMinute();
        if (now.getMinute() == 0) onEveryHour();
        if (now.getHour() == 0) onEveryDay();
    }

    private void onEverySecond() {
        for (var runnable : everySecondRunnableLst) {
            try {
                runnable.run();
            } catch (Exception ex) {
                logger.error("onEverySecond", ex);
            }
        }
    }

    private void onEveryMinute() {
        for (var runnable : everyMinuteRunnableLst) {
            try {
                runnable.run();
            } catch (Exception ex) {
                logger.error("onEveryMinute", ex);
            }
        }
    }

    private void onEveryHour() {
        for (var runnable : everyHourRunnableLst) {
            try {
                runnable.run();
            } catch (Exception ex) {
                logger.error("onEveryHour", ex);
            }
        }
    }

    private void onEveryDay() {
        for (var runnable : everyDayRunnableLst) {
            try {
                runnable.run();
            } catch (Exception ex) {
                logger.error("onEveryDay", ex);
            }
        }
    }

    public TimerService() {
        var threadPool = Executors.newSingleThreadScheduledExecutor();

        everySecondRunnableLst = new Vector<>();
        everyMinuteRunnableLst = new Vector<>();
        everyHourRunnableLst = new Vector<>();
        everyDayRunnableLst = new Vector<>();

        threadPool.scheduleAtFixedRate(this::handle, 500, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void subscriberEverySecond(Runnable runnable) {
        everySecondRunnableLst.add(runnable);
    }

    @Override
    public void unSubscriberEverySecond(Runnable runnable) {
        everySecondRunnableLst.remove(runnable);
    }

    @Override
    public void subscriberEveryMinute(Runnable runnable) {
        everyMinuteRunnableLst.add(runnable);
    }

    @Override
    public void unSubscriberEveryMinute(Runnable runnable) {
        everyMinuteRunnableLst.remove(runnable);
    }

    @Override
    public void subscriberEveryHour(Runnable runnable) {
        everyHourRunnableLst.add(runnable);
    }

    @Override
    public void unSubscriberEveryHour(Runnable runnable) {
        everyHourRunnableLst.remove(runnable);
    }

    @Override
    public void subscriberEveryDay(Runnable runnable) {
        everyDayRunnableLst.add(runnable);
    }

    @Override
    public void unSubscriberEveryDay(Runnable runnable) {
        everyDayRunnableLst.remove(runnable);
    }
}
