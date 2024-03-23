package com.ooooo.demo.executor;

import com.ooooo.demo.task.AbstractTask;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public interface TaskExecutor {

    void submit(AbstractTask task);

    void schedule(Runnable runnable, long delay, TimeUnit timeUnit);

}
