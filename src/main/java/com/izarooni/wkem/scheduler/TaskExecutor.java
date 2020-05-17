package com.izarooni.wkem.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author izarooni
 */
public class TaskExecutor {

    private static final AtomicInteger runningTaskId = new AtomicInteger(0);
    private static final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Executors.defaultThreadFactory());

    private TaskExecutor() {
    }

    public static ScheduledThreadPoolExecutor getExecutor() {
        return executor;
    }

    public static int getActiveCount() {
        return executor.getActiveCount();
    }

    public static long getTaskCount() {
        return executor.getTaskCount();
    }

    public static long getCompletedTaskCount() {
        return executor.getCompletedTaskCount();
    }

    public static void dispose() {
        executor.purge();
        executor.shutdownNow();
    }

    public static String getTimeElapsed(long time) {
        int seconds = (int) (time / 1000 % 60);
        int minutes = (int) ((time / (1000 * 60)) % 60);
        int hours = (int) ((time / (1000 * 60 * 60)) % 24);
        int days = (int) ((time / (1000 * 60 * 60 * 24) % 30));
        String ret = "";
        if (days > 0) {
            ret += days + " day" + ((days > 1) ? "s" : "");
        }
        if (hours > 0) {
            ret += ((days > 0) ? " " : "") + // spacing from previous string concat
                    hours + " hour" + ((hours > 1) ? "s" : "");
        }
        if (minutes > 0) {
            ret += ((hours > 0 || days > 0) ? " " : "") +
                    minutes + " minute" + ((minutes > 1) ? "s" : "");
        }
        ret += ((minutes > 0 || hours > 0 || days > 0) ? " " : "") +
                seconds + " second" + ((seconds > 1) ? "s" : "");
        return ret;
    }

    public static void executeAsynchronously(Runnable runnable) {
        executor.execute(runnable);
    }

    public static synchronized TaskRunnable cancelTask(TaskRunnable task) {
        if (task != null && !task.isCanceled()) {
            task.cancel();
        }
        return null;
    }

    public static synchronized TaskRunnable executeTimestamp(Runnable runnable, long timestamp) {
        return setupId(executor.schedule(runnable, timestamp - System.currentTimeMillis(), TimeUnit.MILLISECONDS));
    }

    public static synchronized TaskRunnable executeLater(Runnable runnable, long delay) {
        return setupId(executor.schedule(runnable, delay, TimeUnit.MILLISECONDS));
    }

    public static synchronized TaskRunnable executeRepeating(Runnable runnable, long delay, long repeatingDelay) {
        return setupId(executor.scheduleAtFixedRate(runnable, delay, repeatingDelay, TimeUnit.MILLISECONDS));
    }

    private static TaskRunnable setupId(ScheduledFuture<?> schedule) {
        final int taskId = runningTaskId.getAndIncrement();
        return new TaskRunnable(schedule) {
            @Override
            public int getTaskId() {
                return taskId;
            }
        };
    }

}
