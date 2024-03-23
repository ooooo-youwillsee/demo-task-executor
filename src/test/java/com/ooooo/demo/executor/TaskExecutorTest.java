package com.ooooo.demo.executor;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.ooooo.demo.task.OnceTask;
import com.ooooo.demo.task.RepeatTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/ooooo-youwillsee">ooooo</a>
 * @since 1.0.0
 */
class TaskExecutorTest {

    private TaskExecutor taskExecutor;

    private ThreadPoolExecutor threadPoolExecutor;

    @BeforeEach
    void beforeEach() {
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        taskExecutor = new DefaultTaskExecutor(threadPoolExecutor, null);
    }

    @Test
    void submitOnceTask() {
        taskExecutor.submit(new OnceTask(() -> {
            System.out.println(DateUtil.now() + " " + Thread.currentThread().getName());
        }));

        ThreadUtil.sleep(3, TimeUnit.SECONDS);
    }

    @Test
    void submitRepeatTask() {
        taskExecutor.submit(new RepeatTask(() -> {
            System.out.println(DateUtil.now() + " " + Thread.currentThread().getName());
        }, 500, TimeUnit.MILLISECONDS));

        ThreadUtil.sleep(3, TimeUnit.SECONDS);
    }

    @Test
    void poolSizeAdjuster() {
        TaskExecutorPoolSizeAdjuster poolSizeAdjuster = new TaskExecutorPoolSizeAdjuster() {

            private Date preDate = new Date();

            @Override
            public int calcCorePoolSize() {
                // 已经过去3秒了
                if (DateUtil.offsetSecond(preDate, 3).isBefore(new Date())) {
                    return 5;
                }
                return 2;
            }

            @Override
            public int calctMaximumPoolSize() {
                if (DateUtil.offsetSecond(preDate, 3).isBefore(new Date())) {
                    return 10;
                }
                return 2;
            }
        };
        taskExecutor = new DefaultTaskExecutor(threadPoolExecutor, poolSizeAdjuster);
        for (int i = 0; i < 5; i++) {
            int no = i;
            taskExecutor.submit(new RepeatTask(() -> {
                System.out.println(no + " -> " + DateUtil.now() + " " + Thread.currentThread().getName());
            }, 1000, TimeUnit.MILLISECONDS));
        }

        ThreadUtil.sleep(30, TimeUnit.SECONDS);
    }
}