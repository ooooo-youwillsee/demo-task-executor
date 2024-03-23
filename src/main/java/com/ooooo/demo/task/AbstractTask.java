package com.ooooo.demo.task;

import com.ooooo.demo.executor.TaskExecutor;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public abstract class AbstractTask implements Runnable {

    protected Runnable runnable;

    protected TaskExecutor taskExecutor;

    public AbstractTask(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        this.runnable.run();
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }
}
