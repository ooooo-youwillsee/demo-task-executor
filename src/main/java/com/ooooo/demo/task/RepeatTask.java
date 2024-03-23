package com.ooooo.demo.task;

import com.ooooo.demo.executor.TaskExecutor;

import java.util.concurrent.TimeUnit;

/**
 * 重复执行任务
 *
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public class RepeatTask extends AbstractTask {

    private final long maxDelay;

    private final TimeUnit timeUnit;

    public RepeatTask(Runnable runnable, long maxDelay, TimeUnit timeUnit) {
        super(runnable);
        this.maxDelay = maxDelay;
        this.timeUnit = timeUnit;
    }

    @Override
    public void run() {
        long prevTime = System.currentTimeMillis();
        try {
            super.run();
        } finally {
            long diff = System.currentTimeMillis() - prevTime;
            diff = Long.max(maxDelay - diff, 0);
            taskExecutor.schedule(this, diff, timeUnit);
        }
    }
}
