package com.haier.svc.api.controller.job;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jsondoc.core.annotation.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.SpringContextUtil;
import com.haier.svc.api.controller.util.job.CronExpressionManager;
import com.haier.svc.job.model.JobLog;
import com.haier.svc.job.model.SysJob;
import com.haier.svc.job.model.SysJobEx;
import com.haier.svc.job.service.JobService;

import net.sf.json.JSONObject;
@Controller
@Api(name = "定时任务模块", description = "JobController")
@RequestMapping(value = "Job")
public class JobController {
    private static org.apache.log4j.Logger logger    = org.apache.log4j.LogManager
                                                         .getLogger(JobController.class);

    @Autowired(required=false)
    private JobService jobService;

/*    @Reference(version="1.0.0")
    private JobModelService jobModelService;*/
     

    @RequestMapping(value = {"/PageJump"})
    public String PageJump() {
        return "job/jobFilter";
    }
    
    @RequestMapping(value = {"/JobLog"})
    public String JobLog(){
    	return "job/jobLog";
    }
    
    /**
     * 查询获取Job配置列表
     * @param jobName
     * @param jobStatus
     * @param rows
     * @param page
     * @param request
     * @param response
     */
    @RequestMapping(value = {"/getJobList"}, method = {RequestMethod.POST})
    public void getJobList(@RequestParam(required = false)String jobName,
    					   @RequestParam(required = false) Integer jobStatus,
                           @RequestParam(required = false) Integer rows,
                           @RequestParam(required = false) Integer page,
                           HttpServletRequest request, HttpServletResponse response){
    	
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("jobName",jobName);
        params.put("jobStatus",jobStatus);
        params.put("m", (page - 1) * rows);
        params.put("n", rows);
        
        ServiceResult<List<SysJobEx>> result = jobService.getJobList(params);
        try{
	        Map<String, Object> retMap = new HashMap<String, Object>();
	        Gson gson = new Gson();
	        retMap.put("rows", result.getResult());
	        retMap.put("total", result.getPager().getRowsCount());
	        response.addHeader("Content-type", "text/html;charset=utf-8");
			response.getWriter().write(gson.toJson(retMap));
	        response.getWriter().flush();
	        response.getWriter().close();
        }catch(Exception e){
        	
        }
    }
    
    /**
     * 新增job
     * @param jobName
     * @param jobStatus
     * @param jobType
     * @param cfgDataStr
     * @param cron
     * @param request
     * @param response
     */
    @RequestMapping(value = {"/addJob"}, method = {RequestMethod.POST})
    public void addJob(@RequestParam(required = false) String jobId,
    				   @RequestParam(required = false) String jobName,
    				   @RequestParam(required = false) Integer jobStatus,
    				   @RequestParam(required = false) String jobType,
    				   @RequestParam(required = false) String cfgDataStr,
    				   @RequestParam(required = false) String cron,
    				   HttpServletRequest request,HttpServletResponse response){
    	
    	SysJobEx sje = new SysJobEx();
    	sje.setJobName(jobName);
    	sje.setJobStatus(jobStatus);
    	sje.setJobType(jobType);
    	sje.setCfgDataStr(cfgDataStr);
    	sje.setCron(cron);
        //sje.setUpdateUser(WebUtil.currentUserName(request));//取登陆用户名为修改人
        //sje.setUpdateTime(new Date());//取当前时间为最后更改时间
        
        if(jobService.checkExists(sje)){
        	logger.error("添加失败 ！ 该Job已经存在 ！");
        }
    	jobService.addJob(sje);
    }
    
    @RequestMapping(value = {"findJobLog"})
    public void findJobLog(
    		@RequestParam(required = false) String logId,
    		@RequestParam(required = false) Integer sysStatus,
    		@RequestParam(required = false) String startTime,
    		@RequestParam(required = false) String endTime,
    		@RequestParam(required = false) Integer rows,
    		@RequestParam(required = false) Integer page,
    		HttpServletRequest request,HttpServletResponse response){
        try {
            if (rows == null)
                rows = 20;
            if (page == null)
                page = 1;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("logId", logId);
            params.put("sysStatus", sysStatus);
            params.put("startTime", startTime);
            params.put("endTime", endTime);
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            
            ServiceResult<List<JobLog>> result = jobService.findJobLog(params);
            
            Map<String, Object> retMap = new HashMap<String, Object>();
            Gson gson = new Gson();
            retMap.put("rows", result.getResult());
            retMap.put("total", result.getPager().getRowsCount());
            
            response.addHeader("Content-type", "text/html;charset=utf-8");
			response.getWriter().write(gson.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
            
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }
    
    
    
    
    
    
/*    *//**
     * 查询获取job配置列表
     * @param name
     * @param status
     * @param pageIndex
     * @param modelMap
     * @param request
     * @param response
     * @return
     *//*
    @RequestMapping(value = {"/getJobList"}, method = {RequestMethod.GET})
    public String getJobList(@RequestParam(required = false) Integer jobId,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) Integer status,
                             @RequestParam(required = false) Integer pageIndex,
                             Map<String, Object> modelMap, HttpServletRequest request,
                             HttpServletResponse response) {
        Assert.notNull(jobService, "Property 'jobModel' is required.");
        this.pageIndex = pageIndex == null || pageIndex <= 0 ? 1 : pageIndex;
        PagerInfo page = new PagerInfo(this.pageSize, this.pageIndex);
        try {
            //添加查询条件
            name = null == name ? name : name.trim();
            int listcount = jobService.getCount(name, status);
            if (listcount > 0) {
                List<SysJobEx> list = jobService.find(name, status, page.getStart(),
                    page.getPageSize(), null);
                page.setRowsCount(listcount);
                modelMap.put("rowList", list);
            }
            modelMap.put("pager", page);
        } catch (Exception e) {
            logger.error("[job][JobController]获取job配置列表时发生错误", e);
        }
        return "job/jobGrid";
    }*/

/*    *//**
     * 获取job配置
     * @param jobId
     * @param modelMap
     * @param request
     * @param response
     * @return
     *//*
    @RequestMapping(value = {"/getJob"}, method = {RequestMethod.GET})
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> getJob(@RequestParam(required = false) Integer jobId,
                                                      Map<String, Object> modelMap,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        Assert.notNull(jobService, "Property 'jobModel' is required.");
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
            //添加查询条件
            List<SysJobEx> list = jobService.find(null, null, null, null, jobId);
            SysJobEx job = list.get(0);
            modelMap.put("jobId", job.getJobId());
            modelMap.put("cfgDataStr", job.getCfgDataStr());
            modelMap.put("cron", job.getCron());
            modelMap.put("jobName", job.getJobName());
            modelMap.put("jobType", job.getJobType());
            modelMap.put("jobStatus", job.getJobStatus());
            result.setData(modelMap);
            //            result.setMessage(JsonUtil.toJson(list.get(0)));
        } catch (Exception e) {
            logger.error("[job][JobController]获取job配置时发生错误", e);
        }
        return result;
    }*/

    /**
     * 修改
     * @param jobName
     * @param jobStatus
     * @param jobType
     * @param cfgDataStr
     * @param cron
     * @param request
     * @param response
     */
    @RequestMapping(value = {"/updateJob"}, method = {RequestMethod.POST})
    public void updateJob(@RequestParam(required = true) String jobId,
			   		   @RequestParam(required = false) String jobName,
    				   @RequestParam(required = false) Integer jobStatus,
    				   @RequestParam(required = false) String jobType,
    				   @RequestParam(required = false) String cfgDataStr,
    				   @RequestParam(required = false) String cron,
    				   HttpServletRequest request,HttpServletResponse response){
    	SysJobEx sje = new SysJobEx();
    	sje.setJobId(Integer.parseInt(jobId));
    	sje.setJobName(jobName);
    	sje.setJobStatus(jobStatus);
    	sje.setJobType(jobType);
    	sje.setCfgDataStr(cfgDataStr);
    	sje.setCron(cron);
        //sje.setUpdateUser(WebUtil.currentUserName(request));//取登陆用户名为修改人
        //sje.setUpdateTime(new Date());//取当前时间为最后更改时间
    	jobService.update(sje);
    }
    
    
    /**
     * 查看cron执行时间
     * @param request
     * @return
     */
    @RequestMapping(value = { "/onViewCronPlan" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> onViewCronPlan(HttpServletRequest request) {
        String cronExp = request.getParameter("cronExp");
        CronExpressionManager cronManager = new CronExpressionManager();
        cronManager.init();
        String plan = cronManager.parseExpression(cronExp);
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        String res = plan.replace("---","\n");
        result.setMessage(res);
        return result;
    }
    
    @GetMapping("/runOnce")
    @ResponseBody
    public Map<String, String> runOnce(Integer jobId){
    	Map<String, String> map = new HashMap<>();
    	
    	SysJob job = jobService.getById(jobId);
    	if(job == null){
    		map.put("result", "failed");	
    		map.put("msg", "未找到相应的job。");
    	}else{
			String intf = job.getCfgData().get("interface").toString();
			String method = job.getCfgData().get("method").toString();
			try{
				Object bean = SpringContextUtil.getApplicationContext().getBean(Class.forName(intf));
				if(bean == null){
					map.put("result", "failed");	
					map.put("msg", "未找到" + intf + "的实例，请检查是否在模块中引用。");
				}else{
					Method m = bean.getClass().getDeclaredMethod(method, new Class<?>[]{});
					if(m == null){
						map.put("result", "failed");	
						map.put("msg", "未找到方法" + method + "，请检查。");
					}else{
						m.setAccessible(true);
						Object result = m.invoke(bean, new Object[]{});
						String resultStr = "";
						try{
							resultStr = JSONObject.fromObject(result).toString();
						}catch(Exception e){
							
						}
						map.put("result", "success");
						map.put("msg", "执行成功。" + (StringUtils.isBlank(resultStr) ? "" : "执行结果：" + resultStr));
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				map.put("result", "failed");	
				map.put("msg", e.getMessage());
			}
    	}
    	
    	return map;
    }
}