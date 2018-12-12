package com.haier.afterSale.helper;

import org.springframework.stereotype.Service;


/**
 * 多线程操作类
 *
 * @Filename: ThreadHelper.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 */
@Service("threadHelper")
public class ThreadHelper {
    ThreadPool threadPool;

    public ThreadPool getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    /**
     * 加入多线程执行
     * @param job
     * @param obj
     */
    public void addToThreadPool(ExecuteJob job, Object obj) {
        job.setObj(obj);
        threadPool.excuteJob(job);
    }

    public boolean addToThreadPoolByTimes(ExecuteJob job, Object obj, Integer times) {
        ThreadPool tp = new ThreadPool(null,null,null);
        job.setObj(obj);
        return tp.excuteJobByTimes(job, times, 0);
    }

    /**
     * 线程任务是否完成
     * @param job
     * @return
     */
    public boolean isDone(ExecuteJob job) {
        return job.isDone();
    }
}
