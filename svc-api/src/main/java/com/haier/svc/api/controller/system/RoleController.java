package com.haier.svc.api.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.system.model.SysRole;
import com.haier.system.service.SystemCenterService;
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
import com.haier.common.util.ConvertUtil;
import com.haier.common.util.StringUtil;
import com.haier.svc.api.controller.util.HttpJsonResult;

/**
 * 系统管理功能
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                   .getLogger(RoleController.class);
    @Autowired
    SystemCenterService systemCenterService;

	@RequestMapping(value = { "toRoleList" }, method = { RequestMethod.GET })
	public String toUserList(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> modelMap) {
		return "sys/role/roleList";
	}
    
    @RequestMapping(value = { "/find" })
    public @ResponseBody void findRole(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) Integer status,
//                                           @RequestParam(required = false) Integer pi,
//                                           @RequestParam(required = false) Integer ps,
                               			@ApiQueryParam(name = "rows") @RequestParam(required = false) Integer rows,
                            			@ApiQueryParam(name = "page") @RequestParam(required = false) Integer page,
                            			HttpServletRequest request, HttpServletResponse response) {
        HttpJsonResult<List<SysRole>> result = new HttpJsonResult<List<SysRole>>();
        try {
            PagerInfo pager = null;
//            if (pi != null && pi > 0 && ps != null && ps > 0)
//                pager = new PagerInfo(ps, pi);
            if (rows == null)
				rows = 20;
			if (page == null)
				page = 1;
			pager = new PagerInfo(rows, page);
            ServiceResult<List<SysRole>> roleResult = systemCenterService.findRole(name, status, pager);
//            if (roleResult.getPager() != null){
//            	result.setTotalCount(roleResult.getPager().getRowsCount());
//            }
//            result.setData(roleResult.getResult());
            if(roleResult.getSuccess() && roleResult.getResult() != null){
            	Map<String, Object> retMap = new HashMap<String, Object>();
            	retMap.put("total", roleResult.getPager().getRowsCount());
            	retMap.put("rows", roleResult.getResult());
            	Gson gson = new Gson();
            	response.addHeader("Content-type", "text/html;charset=utf-8");
            	response.getWriter().write(gson.toJson(retMap));
            	response.getWriter().flush();
            	response.getWriter().close();
            }
        } catch (Exception e) {
            log.error("查询角色列表异常", e);
            result.setMessage(e.getMessage());
        }
    }

    private HttpJsonResult<SysRole> internalGetRole(Integer roleId) {
        try {
            //取用户资料
            ServiceResult<SysRole> serviceResult = systemCenterService.getRoleById(roleId);
            if (!serviceResult.getSuccess())
                return new HttpJsonResult<SysRole>(serviceResult.getMessage());

            return new HttpJsonResult<SysRole>(serviceResult.getResult());
        } catch (Exception e) {
            log.error("获取角色异常:", e);
            return new HttpJsonResult<SysRole>(e.getMessage());
        }
    }

    @RequestMapping(value = { "/get/{roleId}" }, method = { RequestMethod.GET })
    public @ResponseBody
    HttpJsonResult<SysRole> getRole(@PathVariable Integer roleId) {
        return this.internalGetRole(roleId);
    }

    @RequestMapping(value = { "/update" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Boolean> updateRole(
    		@RequestParam(required = false) Integer roleId,
    		@RequestParam(required = false) String roleName,
    		@RequestParam(required = false) Integer roleStatus,
    		@RequestParam(required = false) String roleRemark) {
    	
    	SysRole role = new SysRole();
    	role.setRoleId(roleId);
    	role.setRoleName(roleName);
    	role.setStatus(roleStatus);
    	role.setRemark(roleRemark);
        try {
            //TODO: 取当前操作用户ID
            ServiceResult<Boolean> result = this.systemCenterService.updateRole(role, 15);
            if (!result.getSuccess())
                return new HttpJsonResult<Boolean>(result.getMessage());
            return new HttpJsonResult<Boolean>(result.getResult());
        } catch (Exception e) {
            log.error("更新角色异常", e);
            return new HttpJsonResult<Boolean>(e.getMessage());
        }
    }

    @RequestMapping(value = { "/create" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<SysRole> createRole(
    		@RequestParam(required = false) String roleName,
    		@RequestParam(required = false) Integer roleStatus,
    		@RequestParam(required = false) String roleRemark) {
    	
    	SysRole role = new SysRole();
    	role.setRoleName(roleName);
    	role.setStatus(roleStatus);
    	role.setRemark(roleRemark);
        try {
            //TODO: 取当前操作用户ID
            ServiceResult<Integer> result = this.systemCenterService.createRole(role, 12);
            if (!result.getSuccess())
                return new HttpJsonResult<SysRole>(result.getMessage());
            return this.internalGetRole(result.getResult());
        } catch (Exception e) {
            log.error("创建角色异常", e);
            return new HttpJsonResult<SysRole>(e.getMessage());
        }
    }

    @RequestMapping(value = { "/user/assigned" }, method = { RequestMethod.GET })
    @ResponseBody
    public void findUserRoleAssigned(
    		@RequestParam(required = true) Integer userId,
    		HttpServletResponse response) {
        HttpJsonResult<List<SysRole>> result = new HttpJsonResult<List<SysRole>>();
        try {
            ServiceResult<List<SysRole>> roleResult = systemCenterService.findUserRoleAssigned(userId);
            result.setData(roleResult.getResult());
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("rows", roleResult.getResult());
			Gson gson = new Gson();
			response.addHeader("Content-type", "text/html;charset=utf-8");
			response.getWriter().write(gson.toJson(retMap));
			response.getWriter().flush();
			response.getWriter().close();
        } catch (Exception e) {
            log.error("查询用户已分配角色列表异常", e);
            result.setMessage(e.getMessage());
        }

        //return result;
    }

    @RequestMapping(value = { "/user/unassigned" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<List<SysRole>> findUserRoleUnAssigned(
    		@RequestParam(required = true) Integer userId,
    		HttpServletResponse response) {
        HttpJsonResult<List<SysRole>> result = new HttpJsonResult<List<SysRole>>();
        try {
            ServiceResult<List<SysRole>> roleResult = systemCenterService.findUserRoleUnAssigned(userId);
            result.setData(roleResult.getResult());
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("rows", roleResult.getResult());
			Gson gson = new Gson();
			response.addHeader("Content-type", "text/html;charset=utf-8");
			response.getWriter().write(gson.toJson(retMap));
			response.getWriter().flush();
			response.getWriter().close();
        } catch (Exception e) {
            log.error("查询用户未分配角色列表异常", e);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = { "/user/assign" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Boolean> assignUserRole(@RequestParam(required = true) Integer userId,
                                           @RequestParam(required = true) String roleIds) {
        HttpJsonResult<Boolean> result = new HttpJsonResult<Boolean>();
        try {
            List<Integer> roleList = new ArrayList<Integer>();
            if (StringUtil.isEmpty(roleIds, true)) {
                result.setMessage("角色ID列表为空");
                return result;
            }
            for (String id : roleIds.split(",")) {
                Integer value = ConvertUtil.toInt(id, 0);
                if (value == null || value <= 0)
                    continue;
                roleList.add(value);
            }
            if (roleList.isEmpty()) {
                result.setMessage("无效的角色ID列表：" + roleIds);
                return result;
            }
            ServiceResult<Boolean> assignResult = systemCenterService.assignUserRole(userId, roleList);
            if (assignResult.getSuccess())
                result.setData(assignResult.getResult());
            else
                result.setMessage(assignResult.getMessage());
        } catch (Exception e) {
            log.error("分配用户角色异常", e);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = { "/user/unassign" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Boolean> unassignUserRole(@RequestParam(required = true) Integer userId,
                                             @RequestParam(required = true) String roleIds) {
        HttpJsonResult<Boolean> result = new HttpJsonResult<Boolean>();
        try {
            List<Integer> roleList = new ArrayList<Integer>();
            if (StringUtil.isEmpty(roleIds, true)) {
                result.setMessage("角色ID列表为空");
                return result;
            }
            for (String id : roleIds.split(",")) {
                Integer value = ConvertUtil.toInt(id, 0);
                if (value == null || value <= 0)
                    continue;
                roleList.add(value);
            }
            if (roleList.isEmpty()) {
                result.setMessage("无效的角色ID列表：" + roleIds);
                return result;
            }
            ServiceResult<Boolean> assignResult = systemCenterService.unassignUserRole(userId, roleList);
            if (assignResult.getSuccess())
                result.setData(assignResult.getResult());
            else
                result.setMessage(assignResult.getMessage());
        } catch (Exception e) {
            log.error("取消分配用户角色异常", e);
            result.setMessage(e.getMessage());
        }

        return result;
    }
}