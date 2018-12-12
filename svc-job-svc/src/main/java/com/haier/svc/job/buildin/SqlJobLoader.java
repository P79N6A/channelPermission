package com.haier.svc.job.buildin;

import static org.quartz.JobBuilder.newJob;

import org.quartz.JobDetail;

import com.haier.svc.job.AbstractJobLoader;
import com.haier.svc.job.InvalidateCfgDataException;
import com.haier.svc.job.JobManager;
import com.haier.svc.job.model.SysJob;
import com.haier.common.util.StringUtil;

/**
 * 
 * @version 1.0
 * @author 刘志斌 yudi@sina.com
 * @see SqlJob
 */
public class SqlJobLoader extends AbstractJobLoader {
    private String sql;

    public SqlJobLoader(SysJob job, JobManager manager) {
        super(job, manager);
    }

    @Override
    protected void parseCfgData() {
        this.sql = (String) job.getCfgData("sql");
        if (StringUtil.isEmpty(this.sql, true))
            throw new InvalidateCfgDataException("Null or empty sql");
    }

    @Override
    protected JobDetail internalBuildJobDetail() {
        JobDetail jd = newJob(SqlJob.class).withIdentity(
            "[job-" + job.getJobType() + "-" + job.getJobId() + "]", DEFAULT_GROUP).build();
        if (log.isInfoEnabled())
            log.info("[job-" + job.getJobId() + "][" + job.getJobName() + "] SQL job: " + sql);
        return jd;
    }

}
