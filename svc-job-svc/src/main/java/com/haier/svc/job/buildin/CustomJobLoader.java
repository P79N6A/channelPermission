package com.haier.svc.job.buildin;

import static org.quartz.JobBuilder.newJob;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;

import com.haier.common.util.StringUtil;
import com.haier.svc.job.AbstractJob;
import com.haier.svc.job.AbstractJobLoader;
import com.haier.svc.job.InvalidateCfgDataException;
import com.haier.svc.job.JobManager;
import com.haier.svc.job.model.SysJob;

/**
 * 自定义Job的装载器。
 * <p>
 * 主要完成自定义Job配置数据的解析、基本校验，以及构造Job启动时的{@link JobDetail}、{@link Trigger}。
 * 
 * <p>
 * 自定义Job必须继承自<code>JobBase</code>，{@link SysJob#getCfgData()}格式要求如下：
 *    { class:"com.haier.cbs.job.custom.demo.CustomJobDemo1" }
 * 
 * @version 1.0
 * @author 刘志斌 yudi@sina.com
 */
public class CustomJobLoader extends AbstractJobLoader {
    private String className;

    public CustomJobLoader(SysJob job, JobManager manager) {
        super(job, manager);
    }

    @Override
    protected void parseCfgData() {
        this.className = (String) job.getCfgData("class");
        if (StringUtil.isEmpty(this.className, true))
            throw new InvalidateCfgDataException("Null or empty class name for custom job");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected JobDetail internalBuildJobDetail() {
        //得到自定义Job的class对象，并且进行校验
        Class<? extends Job> clz = null;
        try {
            clz = (Class<? extends Job>) Class.forName(this.className);
        } catch (ClassNotFoundException e) {
            throw new InvalidateCfgDataException("Custom job class '" + this.className
                                                 + "' not found");
        }
        if (!AbstractJob.class.isAssignableFrom(clz)) {
            throw new InvalidateCfgDataException("Custom job class '" + this.className
                                                 + "' not derived from:"
                                                 + AbstractJob.class.getName());
        }
        //创建JobDetail对象
        JobDetail jd = newJob(clz).withIdentity(
            "[job-" + job.getJobType() + "-" + job.getJobId() + "]", DEFAULT_GROUP).build();
        if (log.isInfoEnabled())
            log.info("[job-" + job.getJobId() + "][" + job.getJobName()
                     + "] Custom job initialized: " + clz.getName());
        return jd;
    }

}
