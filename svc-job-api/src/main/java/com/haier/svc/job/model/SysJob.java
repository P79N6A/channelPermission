package com.haier.svc.job.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.haier.common.util.StringUtil;

public class SysJob implements Serializable{
	private static final long serialVersionUID = 4943975920835841L;
	public static final int STATUS_ENABLED = 1;
	public static final int STATUS_DELETED = -1;
	public static final int STATUS_DISABLED = 0;
	public static final String TYPE_CUSTOM = "custom";
	public static final String TYPE_DUBBO = "dubbo";
	public static final String TYPE_URL = "url";
	public static final String TYPE_SQL = "sql";
	private Integer jobId;
	private String jobName;
	private String jobType;
	private Integer jobStatus;
	private Map<String, Object> cfgData;
	private String cron;
	private String updateUser;
	private Date updateTime;
	private String zookeeperPath;

	public SysJob() {
		this.jobName = "";

		this.jobType = "";

		this.jobStatus = Integer.valueOf(0);

		this.cron = "";

		this.updateUser = "";
	}

	public Integer getJobId() {
		return this.jobId;
	}

	public void setJobId(Integer value) {
		this.jobId = value;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String value) {
		this.jobName = value;
	}

	public String getJobType() {
		return this.jobType;
	}

	public void setJobType(String value) {
		this.jobType = value;
	}

	public Integer getJobStatus() {
		return this.jobStatus;
	}

	public void setJobStatus(Integer value) {
		this.jobStatus = value;
	}

	public Map<String, Object> getCfgData() {
		return this.cfgData;
	}

	public Object getCfgData(String key) {
		return (((this.cfgData == null) || (StringUtil.isEmpty(key)))
				? null
				: this.cfgData.get(key));
	}

	public void setCfgData(Map<String, Object> value) {
		this.cfgData = value;
	}

	public void setCfgData(String key, Object value) {
		if (this.cfgData == null)
			this.cfgData = new HashMap();
		this.cfgData.put(key, value);
	}

	public String getCron() {
		return this.cron;
	}

	public void setCron(String value) {
		this.cron = value;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String value) {
		this.updateUser = value;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}

	public String getZookeeperPath() {
		return zookeeperPath;
	}

	public void setZookeeperPath(String zookeeperPath) {
		this.zookeeperPath = zookeeperPath;
	}
}