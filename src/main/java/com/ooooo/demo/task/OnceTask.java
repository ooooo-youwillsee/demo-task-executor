package com.ooooo.demo.task;

/**
 * 只执行一次
 *
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public class OnceTask extends AbstractTask {

    public OnceTask(Runnable runnable) {
        super(runnable);
    }
}
