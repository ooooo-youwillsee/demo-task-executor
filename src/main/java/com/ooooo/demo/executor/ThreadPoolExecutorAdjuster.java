package com.ooooo.demo.executor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 默认每十秒调整一次
 *
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
public interface ThreadPoolExecutorAdjuster {

    void customize(ThreadPoolExecutor threadPoolExecutor);

}
