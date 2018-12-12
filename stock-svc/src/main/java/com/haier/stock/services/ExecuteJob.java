package com.haier.stock.services;

/**
 * 多线程任务,可传入执行接口对象,使用ThreadHelper加入线程池
 *                       
 * @Filename: ExecuteJob.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class ExecuteJob implements Runnable {
    private boolean isDone = false;

    private Object  obj;

    private IExcute excute;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public IExcute getExcute() {
        return excute;
    }

    public void setExcute(IExcute excute) {
        this.excute = excute;
    }

    public void done() {
        isDone = true;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                excute.execute(obj);
            } finally {
                done();
                notify();
            }
        }
    }

    public void waitForComplete() throws InterruptedException {
        synchronized (this) {
            if (!isDone) {
                wait();
            }
        }
    }
}
