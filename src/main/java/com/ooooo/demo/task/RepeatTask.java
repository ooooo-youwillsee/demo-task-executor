package com.ooooo.demo.task;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.concurrent.TimeUnit;

/**
 * 重复执行任务
 *
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public class RepeatTask extends AbstractTask {

    private final long maxDelay;

    private volatile boolean canceled;

    public RepeatTask(Runnable runnable, long maxDelay, TimeUnit timeUnit) {
        super(runnable);
        this.maxDelay = timeUnit.toMillis(maxDelay);
    }

    public void cancel() {
        this.canceled = true;
    }

    @Override
    public void run() {
        if (canceled) {
            return;
        }
        long prevTime = System.currentTimeMillis();
        try {
            super.run();
        } finally {
            long diff = System.currentTimeMillis() - prevTime;
            diff = Long.max(maxDelay - diff, 0);
            taskExecutor.schedule(this, diff, TimeUnit.MILLISECONDS);
        }
    }
}
