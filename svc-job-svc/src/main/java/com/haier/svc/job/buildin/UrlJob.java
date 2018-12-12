package com.haier.svc.job.buildin;

import org.quartz.JobExecutionContext;

import com.haier.svc.job.AbstractJob;
import com.haier.svc.job.JobResult;
import com.haier.svc.job.NotImplementedJobException;
import com.haier.svc.job.model.SysJob;

/**com.haier.svc.job.model
 * 调用URL的Job。
 * <p>
 * Job执行时调用一个URL地址，URL地址配置在{@link SysJob#getCfgData()}中，格式如下：{ url:"http://www.ehaier.com/api/xxxx" }<br />
 * 某些PHP实现的后台作业程序可以使用这种方式。
 * 
 * <p>其他类型的Job参考{@link AbstractJob}
 * 
 * @version 1.0
 * @author 刘志斌 yudi@sina.com
 */
public class UrlJob extends AbstractJob {

    @Override
    public JobResult excecute(SysJob job, Integer logId, JobExecutionContext context)
                                                                                     throws Exception {
        //使用HTTP Client或者URL调用
        throw new NotImplementedJobException();
    }
}