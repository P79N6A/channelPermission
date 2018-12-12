package com.haier.svc.job.services;

import java.util.List;
import java.util.Map;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.svc.job.JobManager;
import com.haier.svc.job.dao.purchase.SysJobDao;
import com.haier.svc.job.model.JobLog;
import com.haier.svc.job.model.SysJob;
import com.haier.svc.job.model.SysJobEx;
import com.haier.svc.job.service.JobService;

/**
 * @author 李超
 *
 */
@Service
public class JobServiceImpl implements JobService {

	@Autowired
	JobManager jobManager;
	@Autowired
	SysJobDao sysJobDao;

	// 通过SchedulerFactory获取一个调度器实例
	SchedulerFactory sf = new StdSchedulerFactory();

	/**
	 * 更新job配置
	 * 
	 * @param sysJob
	 */
	@Override
	public void update(SysJobEx sysJob) {

		SysJob job = new SysJob();
		BeanUtils.copyProperties(sysJob, job);
		Map<String, Object> cfgData = JsonUtil.fromJson(sysJob.getCfgDataStr());
		job.setCfgData(cfgData);
		try {
			sysJobDao.update(sysJob);
			jobManager.stopJob(job);
			if (sysJob.getJobStatus() == 1) {
				jobManager.startJob(job);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	// 查询Job配置
	@Override
	public ServiceResult<List<SysJobEx>> getJobList(Map<String, Object> params) {
		ServiceResult<List<SysJobEx>> result = new ServiceResult<List<SysJobEx>>();
		try {
			result.setResult(sysJobDao.getJobList(params));
			int pagecount = sysJobDao.getRowCnts(params);
			PagerInfo pi = new PagerInfo();
			pi.setRowsCount(pagecount);
			result.setPager(pi);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	// 添加Job任务
	@Override
	@Transactional
	public void addJob(SysJobEx sysJob) {
		SysJob job = new SysJob();
		BeanUtils.copyProperties(sysJob, job);
		Map<String, Object> cfgData = JsonUtil.fromJson(sysJob.getCfgDataStr());
		job.setCfgData(cfgData);
		try {
			sysJobDao.addJob(sysJob);
			if (sysJob.getJobStatus() == 1) {
				job.setJobId(sysJob.getJobId());
				jobManager.startJob(job);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 判断是否存在当前Job任务
	@Override
	public boolean checkExists(SysJobEx job) {
		JobKey key = new JobKey(job.getJobName());
		try {
			Scheduler scheduler = sf.getScheduler();
			return scheduler.checkExists(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public ServiceResult<List<JobLog>> findJobLog(Map<String, Object> params) {
		ServiceResult<List<JobLog>> result = new ServiceResult<List<JobLog>>();
		try {
		    result.setResult(sysJobDao.findJobLog(params));
		    int pagecount = sysJobDao.getJobLogRow(params);
		    PagerInfo pi = new PagerInfo();
		    pi.setRowsCount(pagecount);
		    result.setPager(pi);
		} catch (Exception e) {
		    result.setSuccess(false);
		    result.setMessage(e.getMessage());
		}
		return result;
	}

}
