package com.haier.afterSale.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池,spring注入即可使用
 *
 * @Filename: ThreadPool.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 */
public class ThreadPool {
    private static Logger log = LoggerFactory.getLogger(ThreadPool.class);

    /**
     * 最大线程数
     */
    private Integer maxSize = 20;

    /**
     * 核心线程数
     */
    private Integer coreSize = 5;

    /**
     * 阻塞队列数
     */
    private Integer blockingQueueSize = 100;

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getCoreSize() {
        return coreSize;
    }

    public void setCoreSize(Integer coreSize) {
        this.coreSize = coreSize;
    }

    public Integer getBlockingQueueSize() {
        return blockingQueueSize;
    }

    public void setBlockingQueueSize(Integer blockingQueueSize) {
        this.blockingQueueSize = blockingQueueSize;
    }

    private ThreadPoolExecutor executorService;

    public ThreadPool(Integer maxSize, Integer coreSize, Integer blockingQueueSize) {
        this.maxSize = maxSize != null ? maxSize : this.maxSize;
        this.coreSize = coreSize != null ? coreSize : this.coreSize;
        this.blockingQueueSize = blockingQueueSize != null ? blockingQueueSize
                : this.blockingQueueSize;
        executorService = new ThreadPoolExecutor(this.coreSize, this.maxSize, 60L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(this.blockingQueueSize));
    }

    public boolean excuteJobByTimes(Runnable runnable, Integer maxTryTime, Integer times) {
        if (null == maxTryTime) {
            maxTryTime = 3;
        }
        if (times >= maxTryTime) {
            return false;
        }
        try {
            executorService.execute(runnable);
        } catch (Exception e) {
            if (e instanceof RejectedExecutionException) {
                sleep(3L);
                excuteJobByTimes(runnable, maxTryTime, times++);
            }
        }
        return true;
    }

    public void excuteJob(Runnable runnable) {
        try {
            executorService.execute(runnable);
        } catch (Exception e) {
            if (e instanceof RejectedExecutionException) {
                //while (isSleep(executorService, maxSize)) {
                sleep(5L);
                //}
                excuteJob(runnable);
            }
        }
    }

    public boolean isSleep(ThreadPoolExecutor executorService, int maxSize) {
        int activeThread = executorService.getActiveCount();
        int queueSize = executorService.getQueue().size();
        if (maxSize >= activeThread && blockingQueueSize >= queueSize) {
            return false;
        }
        return true;
    }

    public void sleep(long millis) {
        try {
            log.info("线程池已满,等待" + millis + "毫秒");
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Runnable job = new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ThreadPool threadPool = new ThreadPool(null, null, null);
        for (int i = 0; i < 100; i++) {
            threadPool.excuteJob(job);
        }
    }
}
