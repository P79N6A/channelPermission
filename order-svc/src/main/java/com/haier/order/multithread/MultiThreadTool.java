package com.haier.order.multithread;

import com.haier.order.util.ArraySplitUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 加入多线程工具类,T为数据类型
 *                       
 * @Filename: MultiThreadTool.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class MultiThreadTool<T> {
    /**
     * 加入多线程,重试指定次数,如果不能加入多线程,本线程自己执行分组大小数据;其余数据再尝试加入多线程
     * @param excute IExcute接口,需要实现此接口
     * @param threadHelper 多线程帮助类,持有ThreadPool对象
     * @param log 日志对象
     * @param dataList 需要批量执行的数据列表
     * @param splitSize 分组大小,传null默认100条
     * @param tryTimes 加入多线程失败重试次数,传null默认3次
     */
    public void processJobs(IExcute excute, ThreadHelper threadHelper, Logger log,
                            List<T> dataList, Integer splitSize, Integer tryTimes) {
        if (null == excute || null == threadHelper || null == log || null == dataList) {
            return;
        }
        if (null == splitSize) {
            splitSize = 100;
        }
        if (null == tryTimes) {
            tryTimes = 3;
        }
        //加入多线程执行
        List<List<T>> datasList = new ArraySplitUtil<T>().splistList(dataList, splitSize);
        ExecuteJob job = null;
        List<ExecuteJob> jobList = new ArrayList<ExecuteJob>();
        for (List<T> subList : datasList) {
            job = new ExecuteJob();
            job.setExcute(excute);
            if (threadHelper.addToThreadPoolByTimes(job, subList, tryTimes)) {
                jobList.add(job);
            } else {
                //重试三次,如果线程池满加不进去,自己执行
                excute.execute(subList);
            }
        }
        //等待所有任务完成

        for (ExecuteJob executeJob : jobList) {
            try {
                executeJob.waitForComplete();
            } catch (InterruptedException e) {
                log.error("等待任务完成时出错", e);
            }
            //            while (!executeJob.isDone()) {
            //                try {
            //                    Thread.sleep(2L);
            //                } catch (InterruptedException e) {
            //                    log.error("等待任务完成时出错", e);
            //                }
            //            }
        }
    }
}
