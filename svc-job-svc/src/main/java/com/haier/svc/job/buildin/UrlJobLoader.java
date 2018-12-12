package com.haier.svc.job.buildin;

import static org.quartz.JobBuilder.newJob;

import org.quartz.JobDetail;

import com.haier.svc.job.AbstractJobLoader;
import com.haier.svc.job.InvalidateCfgDataException;
import com.haier.svc.job.JobManager;
import com.haier.svc.job.model.SysJob;
import com.haier.common.util.StringUtil;

/**
 * @version 1.0
 * @author 刘志斌 yudi@sina.com
 * @see UrlJob
 */
public class UrlJobLoader extends AbstractJobLoader {
    private String url;

    public UrlJobLoader(SysJob job, JobManager manager) {
        super(job, manager);
    }

    @Override
    protected void parseCfgData() {
        this.url = (String) job.getCfgData("url");
        if (StringUtil.isEmpty(this.url, true))
            throw new InvalidateCfgDataException("Null or empty url");
    }

    @Override
    protected JobDetail internalBuildJobDetail() {
        JobDetail jd = newJob(UrlJob.class).withIdentity(
            "[job-" + job.getJobType() + "-" + job.getJobId() + "]", DEFAULT_GROUP).build();
        if (log.isInfoEnabled())
            log.info("[job-" + job.getJobId() + "][" + job.getJobName() + "] URL job: " + url);
        return jd;
    }

}
