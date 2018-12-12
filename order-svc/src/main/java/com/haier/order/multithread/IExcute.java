package com.haier.order.multithread;

/**
 * 多线程执行接口,实现此接口可以设置ExecuteJob中执行逻辑
 *                       
 * @Filename: IExcute.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public interface IExcute {
    /**
     * 执行任务
     * @param obj
     */
    public void execute(Object obj);
}
