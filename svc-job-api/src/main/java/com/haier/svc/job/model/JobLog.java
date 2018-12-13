package com.haier.svc.job.model;

import java.io.Serializable;
import java.util.Date;

public class JobLog implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//job日志id
	private Integer logId;
	
	//jobId
	private Integer jobId;
	
	//开始时间
	private Date startTime;
	
	//结束时间
	private Date endTime;
	
	//下一次运行时间
	private Date NextTime;
	
	//job执行结果：系统状态
	private Integer sysStatus;
	
	//job执行结果：业务状态
	private Integer bizStatus;
	
	//
	private String message;

	//后添加自定义字段  用于绑定job表job名称
	private String jobName;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getNextTime() {
		return NextTime;
	}

	public void setNextTime(Date nextTime) {
		this.NextTime = nextTime;
	}

	public Integer getSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(Integer sysStatus) {
		this.sysStatus = sysStatus;
	}

	public Integer getBizStatus() {
		return bizStatus;
	}

	public void setBizStatus(Integer bizStatus) {
		this.bizStatus = bizStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}