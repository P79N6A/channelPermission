package com.haier.svc.job.buildin;

import org.quartz.JobExecutionContext;

import com.alibaba.dubbo.rpc.service.GenericService;
import com.haier.svc.job.AbstractJob;
import com.haier.svc.job.JobResult;
import com.haier.svc.job.model.SysJob;

/**
 * <strong>调用Dubbo服务方法的Job</strong><br />
 * 1. 被调用的Dubbo服务方法必须无参数，服务方法的返回值会被忽略，Job不对服务方法返回值进行任何处理；<br />
 *    &nbsp;&nbsp;&nbsp;&nbsp;除此之外对被调用的Dubbo服务方法无任何其他要求；<br />
 * 2. Job工程对被调用的服务无需任何依赖，Job工程无需引用被调用服务的接口、实体，被调用服务的jar无需部署到Job服务器；<br />
 *    &nbsp;&nbsp;&nbsp;&nbsp;被调用服务部署好之后，只需将配置存入sys_job表即可；
 * 
 * <p>Job配置请参考{@link DubboJobLoader}，其他类型的Job参考{@link AbstractJob}
 * 
 * @version 1.0
 * @author 刘志斌 yudi@sina.com
 */
public class DubboJob extends AbstractJob {
    @Override
    public JobResult excecute(SysJob job, Integer logId, JobExecutionContext context)
                                                                                     throws Exception {
        DubboJobLoader loader = (DubboJobLoader) super.getJobLoader(context);
        GenericService service = loader.getReferenceConfig().get();
        try {
            //            if (loader.getMethod().equals("autoConfirmOrder")) {
            //                log.info("invoke autoConfirmOrder start");
            //            }
            service.$invoke(loader.getMethod(), null, null);
            //            if (loader.getMethod().equals("autoConfirmOrder")) {
            //                log.info("invoke autoConfirmOrder end");
            //            }
        } catch (Exception e) {
            throw new DubboServiceInvocationException("Invoke service '" + loader.getInterface()
                                                      + "." + loader.getMethod() + "()' error: "
                                                      + e.getMessage(), e);
        }
        return null;
    }
}