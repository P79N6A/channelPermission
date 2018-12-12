package com.haier.svc.util;

import java.util.List;

import com.haier.svc.util.AsynchronousJobRunner.Configuration.IMonitorCallback;


public interface IThreadJob extends Runnable {

    public void addData(List data);

    public void addData(Object data);

    public void dataFinish();

    public void setMonitorCallback(IMonitorCallback monitorCallback);

    public void finishedData();
}
