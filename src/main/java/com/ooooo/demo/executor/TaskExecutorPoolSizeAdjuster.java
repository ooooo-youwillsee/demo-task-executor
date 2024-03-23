package com.ooooo.demo.executor;

/**
 * 默认每十秒调整一次
 *
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public interface TaskExecutorPoolSizeAdjuster {

    /**
     * 计算核心线程数
     *
     * @return
     */
    int calcCorePoolSize();

    /**
     * 计算最大线程数
     *
     * @return
     */
    int calctMaximumPoolSize();


}
