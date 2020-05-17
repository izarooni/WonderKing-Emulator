package com.izarooni.wkem.scheduler;

import java.util.concurrent.ScheduledFuture;

/**
 * @author izarooni
 */

public abstract class TaskRunnable {

    private final ScheduledFuture<?> schedule;
    private volatile boolean canceled = false;

    public TaskRunnable(ScheduledFuture<?> schedule) {
        this.schedule = schedule;
    }

    public abstract int getTaskId();

    public void cancel() {
        if (!canceled) {
            schedule.cancel(true);
            canceled = true;
        }
    }

    public boolean isCanceled() {
        return canceled;
    }
}
