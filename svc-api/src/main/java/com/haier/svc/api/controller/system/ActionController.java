package com.haier.svc.api.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsondoc.core.annotation.ApiQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;
import com.haier.system.model.SysAction;
import com.haier.system.service.SystemCenterService;

/**
 * 系统管理功能
 */
@Controller
@RequestMapping("/sys/action")
public class ActionController {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                   .getLogger(ActionController.class);
    @Autowired
    SystemCenterService systemCenterService;


	@RequestMapping(value = { "toActionList" })
	public String toUserList(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> modelMap) {
		return "sys/action/actionList";
	}
    
    @RequestMapping(value = { "/find" })
    public @ResponseBody void findAction(@RequestParam(required = false) String actKey,
                                               @RequestParam(required = false) String actName,
                                               @RequestParam(required = false) String remark,
                                               @RequestParam(required = false) Integer status,
                                               @RequestParam(required = false) String menuName,
//                                               @RequestParam(required = false) Integer pi,
//                                               @RequestParam(required = false) Integer ps
                                   			@ApiQueryParam(name = "rows") @RequestParam(required = false) Integer rows,
                                			@ApiQueryParam(name = "page") @RequestParam(required = false) Integer page,
                                			HttpServletRequest request, HttpServletResponse response) {
        HttpJsonResult<List<SysAction>> result = new HttpJsonResult<List<SysAction>>();
        try {
            PagerInfo pager = null;
//            if (pi != null && pi > 0 && ps != null && ps > 0)
//                pager = new PagerInfo(ps, pi);
            if (rows == null)
				rows = 20;
			if (page == null)
				page = 1;
			pager = new PagerInfo(rows, page);
            ServiceResult<List<SysAction>> actionResult = systemCenterService.findAction(actKey, actName,
                remark, status, menuName, pager);
            if (actionResult.getSuccess() && actionResult.getPager() != null){
            	Map<String, Object> retMap = new HashMap<String, Object>();
				retMap.put("total", actionResult.getPager().getRowsCount());
				retMap.put("rows", actionResult.getResult());
				Gson gson = new Gson();
				response.addHeader("Content-type", "text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(retMap));
				response.getWriter().flush();
				response.getWriter().close();
            }
        } catch (Exception e) {
            log.error("查询操作列表异常", e);
            result.setMessage(e.getMessage());
        }

    }

    private HttpJsonResult<SysAction> internalGetAction(Integer actId) {
        try {
            ServiceResult<SysAction> serviceResult = systemCenterService.getActionById(actId);
            if (!serviceResult.getSuccess())
                return new HttpJsonResult<SysAction>(serviceResult.getMessage());

            return new HttpJsonResult<SysAction>(serviceResult.getResult());
        } catch (Exception e) {
            log.error("获取操作异常:", e);
            return new HttpJsonResult<SysAction>(e.getMessage());
        }
    }

    @RequestMapping(value = { "/get/{actionId}" }, method = { RequestMethod.GET })
    public @ResponseBody
    HttpJsonResult<SysAction> getAction(@PathVariable Integer actId) {
        return this.internalGetAction(actId);
    }

    @RequestMapping(value = { "/update" }, method = { RequestMethod.POST })
    public @ResponseBody
    HttpJsonResult<Boolean> updateAction(SysAction action, HttpServletRequest request) {
        try {
            ServiceResult<Boolean> result = this.systemCenterService.updateAction(action,
                WebUtil.currentUserId(request));
            if (!result.getSuccess())
                return new HttpJsonResult<Boolean>(result.getMessage());
            return new HttpJsonResult<Boolean>(result.getResult());
        } catch (Exception e) {
            log.error("更新操作异常", e);
            return new HttpJsonResult<Boolean>(e.getMessage());
        }
    }

    @RequestMapping(value = { "/create" }, method = { RequestMethod.POST })
    public @ResponseBody
    HttpJsonResult<SysAction> createUser(SysAction action, HttpServletRequest request) {
        try {
            ServiceResult<Integer> result = this.systemCenterService.createAction(action,
                WebUtil.currentUserId(request));
            if (!result.getSuccess())
                return new HttpJsonResult<SysAction>(result.getMessage());
            return this.internalGetAction(result.getResult());
        } catch (Exception e) {
            log.error("创建操作异常", e);
            return new HttpJsonResult<SysAction>(e.getMessage());
        }
    }
}