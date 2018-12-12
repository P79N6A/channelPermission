package com.haier.svc.job.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.svc.job.model.JobLog;
import com.haier.svc.job.model.SysJobEx;

/**
 * @author 李超
 *
 */
public interface JobService {
	
	void update(SysJobEx sysJob);
	
	ServiceResult<List<SysJobEx>> getJobList(Map<String, Object> params);	
	
	void addJob(SysJobEx sysJob);

	boolean checkExists(SysJobEx job);

	ServiceResult<List<JobLog>> findJobLog(Map<String, Object> params);
}