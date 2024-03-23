package com.ooooo.demo.executor;

import com.ooooo.demo.task.AbstractTask;
import com.ooooo.demo.task.RepeatTask;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public class DefaultTaskExecutor implements TaskExecutor {

    private final HashedWheelTimer timer;
    private final ThreadPoolExecutor threadPoolExecutor;
    private final TaskExecutorPoolSizeAdjuster poolSizeAdjuster;

    public DefaultTaskExecutor(ThreadPoolExecutor threadPoolExecutor, TaskExecutorPoolSizeAdjuster poolSizeAdjuster) {
        assert threadPoolExecutor != null;
        this.timer = new HashedWheelTimer(new DefaultThreadFactory("TaskExecutor-Timer"), 10, TimeUnit.MILLISECONDS, 100, true, -1, threadPoolExecutor);
        this.threadPoolExecutor = threadPoolExecutor;
        this.poolSizeAdjuster = poolSizeAdjuster;
        init();
    }

    private void init() {
        if (poolSizeAdjuster != null) {
            submit(new RepeatTask(() -> {
                int maxPoolSize = poolSizeAdjuster.calctMaximumPoolSize();
                threadPoolExecutor.setMaximumPoolSize(maxPoolSize);
                int corePoolSize = poolSizeAdjuster.calcCorePoolSize();
                threadPoolExecutor.setCorePoolSize(corePoolSize);
            }, 10, TimeUnit.SECONDS));
        }
    }

    @Override
    public void submit(AbstractTask task) {
        task.setTaskExecutor(this);
        schedule(task, 10, TimeUnit.MILLISECONDS);
    }

    @Override
    public void schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
        timer.newTimeout(new TimerTaskWrapper(runnable), delay, timeUnit);
    }


    private class TimerTaskWrapper implements TimerTask {

        private Runnable runnable;

        public TimerTaskWrapper(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run(Timeout timeout) throws Exception {
            this.runnable.run();
        }
    }

}
