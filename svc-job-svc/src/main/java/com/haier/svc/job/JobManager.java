package com.haier.svc.job;

import java.util.List;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.haier.svc.job.buildin.CustomJobLoader;
import com.haier.svc.job.buildin.DubboJobLoader;
import com.haier.svc.job.buildin.SqlJobLoader;
import com.haier.svc.job.buildin.UrlJobLoader;
import com.haier.svc.job.model.SysJob;
import com.haier.svc.job.model.SysJobEx;


/**
 * Job管理器。
 *
 * @author 刘志斌 yudi@sina.com
 * @version 1.0
 */
@Component
public class JobManager implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    private final static Logger log = LoggerFactory.getLogger(JobManager.class);

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private JobRepository repository;
    private Scheduler scheduler;
    private String registryProtocol;
    private String registryAddress;
    @Value("${dubbo.registry}")
	private String zookeeperPath;

    public void init() {
        Assert.notNull(this.repository, "Property 'repository' is required");
        try {
            this.scheduler = StdSchedulerFactory.getDefaultScheduler();
            this.scheduler.start();
        } catch (SchedulerException e) {
            log.error("Can not start quartz scheduer", e);
            return;
        }

        try {
        	List<SysJob> jobList = this.repository.findSysJobDao(null, SysJob.STATUS_ENABLED);
            this.scheduleJob(jobList);
        } catch (Exception e) {
            log.error("Load job list error, no jobs scheduled!", e);
        }
    }

    private void scheduleJob(List<SysJob> jobs) throws Exception {
        if (jobs == null || jobs.isEmpty())
            return;
        for (SysJob job : jobs) {
            try {
                this.scheduleJob(job);
            } catch (Exception e) {
                //由于在scheduleJob里已经记录日志,这里不再处理
            }
        }
    }

    public void stopJob(SysJob job) throws Exception {
        try {
            JobKey jobKey = new JobKey("[job-" + job.getJobType() + "-" + job.getJobId() + "]",
                AbstractJobLoader.DEFAULT_GROUP);
            if (null != this.scheduler.getJobDetail(jobKey)) {
                this.scheduler.deleteJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error(
                "[job-" + job.getJobId() + "][" + job.getJobName() + "] Job not stoped:"
                    + e.getMessage(), e);
            throw e;
        }
    }

    public void startJob(SysJob job) throws Exception {
        try {
            this.stopJob(job);
            this.scheduleJob(job);
        } catch (SchedulerException e) {
            log.error(
                "[job-" + job.getJobId() + "][" + job.getJobName() + "] Job not started:"
                    + e.getMessage(), e);
            throw e;
        }
    }

    private void scheduleJob(SysJob job) throws Exception {
        if (job == null)
            return;

        JobLoader loader = null;
        job.setZookeeperPath(zookeeperPath);
        if (SysJob.TYPE_DUBBO.equalsIgnoreCase(job.getJobType())) {
            loader = new DubboJobLoader(job, this);
        } else if (SysJob.TYPE_SQL.equalsIgnoreCase(job.getJobType())) {
            loader = new SqlJobLoader(job, this);
        } else if (SysJob.TYPE_URL.equalsIgnoreCase(job.getJobType())) {
            loader = new UrlJobLoader(job, this);
        } else if (SysJob.TYPE_CUSTOM.equalsIgnoreCase(job.getJobType())) {
            loader = new CustomJobLoader(job, this);
        } else {
            log.error("[job-" + job.getJobId() + "][" + job.getJobName() + "] Invalidate job type:"
                + job.getJobType());
            return;
        }

        JobDetail jd = null;
        Trigger trigger = null;

        try {
            loader.parse();
            jd = loader.buildJobDetail();
            trigger = loader.buildTrigger();
        } catch (Exception e) {
            log.error("[job-" + job.getJobId() + "][" + job.getJobName() + "] Job not scheduled:"
                + e.getMessage(), e);
            throw e;
        }

        try {
            this.scheduler.scheduleJob(jd, trigger);
            log.info("[job-" + job.getJobId() + "][" + job.getJobName()
                + "] Job scheduled successfully");
        } catch (SchedulerException e) {
            log.error("[job-" + job.getJobId() + "][" + job.getJobName() + "] Job not scheduled:"
                + e.getMessage(), e);
            throw e;
        }
    }

    public void setRepository(JobRepository value) {
        this.repository = value;
    }

    public JobRepository getRepository() {
        return this.repository;
    }

    public void setRegistryProtocol(String value) {
        this.registryProtocol = value;
    }

    public String getRegistryProtocol() {
        return this.registryProtocol;
    }

    public void setRegistryAddress(String value) {
        this.registryAddress = value;
    }

    public String getRegistryAddress() {
        return this.registryAddress;
    }

    @Override
    public void setApplicationContext(ApplicationContext value) throws BeansException {
        this.applicationContext = value;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.init();
    }
}
