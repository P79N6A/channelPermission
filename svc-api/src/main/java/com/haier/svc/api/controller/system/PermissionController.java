package com.haier.svc.api.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.system.model.PermissionOwner;
import com.haier.system.model.PermissionResource;
import com.haier.system.model.SysPermission;
import com.haier.system.service.SystemCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.form.PermissionResourceForm;

@Controller
@RequestMapping("/sys/permission")
public class PermissionController {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                   .getLogger(PermissionController.class);
    @Autowired
    SystemCenterService systemCenterService;

    
	@RequestMapping(value = { "/toPermissionList" }, method = { RequestMethod.GET })
	public String toUserList(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> modelMap) {
		return "sys/permission/permissionList";
	}
    
    @RequestMapping(value = { "/find-owner" })
    @ResponseBody
    public HttpJsonResult<List<PermissionOwner>> findOwner(@RequestParam(required = true) Integer role,
                                                           @RequestParam(required = false) String name,
                                                           @RequestParam(required = false) Integer status,
                                                           @RequestParam(required = false) Integer rows,
                                                           @RequestParam(required = false) Integer page) {
        HttpJsonResult<List<PermissionOwner>> result = new HttpJsonResult<List<PermissionOwner>>();
        try {
/*            PagerInfo pager = null;
            if (pi != null && pi > 0 && ps != null && ps > 0)
                pager = new PagerInfo(ps, pi);*/
        	PagerInfo pager = null;
        		if (rows == null)
        			rows = 20;
        		if (page == null)
        			page = 1;
			pager = new PagerInfo(rows, page);
            ServiceResult<List<PermissionOwner>> ownerResult = systemCenterService.findPermissionOwner(
                role, name, status, pager);
            if (ownerResult.getPager() != null)
                result.setTotalCount(ownerResult.getPager().getRowsCount());
            result.setData(ownerResult.getResult());
        } catch (Exception e) {
            log.error("查询权限所有者列表异常", e);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @RequestMapping(value = { "/find-resource-tree" })
    @ResponseBody
    public String findResource(@RequestParam(required = true) Integer ownerId,
                                        @RequestParam(required = true) Integer ownerType,
                                        @RequestParam(required = false) String name,
                                        HttpServletResponse response) {
    	
            ServiceResult<List<PermissionResource>> ownerResult = systemCenterService
                .findPermissionResourceTree(ownerId, ownerType, name);
            Map<String, Object> retMap = new HashMap<String, Object>();
            List<Map<String, Object>> comboTreeList = new ArrayList<Map<String, Object>>();
            List<PermissionResource> result = ownerResult.getResult();
            for(PermissionResource pr : result){
            	Map<String,Object> map = new HashMap<String,Object>();
                map.put("id", pr.getRid());
                map.put("text", pr.getName());
                map.put("checked", pr.getChecked());
                if (pr.getChildren() != null) {
                    map.put("children", this.createComboTreeChildren(pr.getChildren(), pr.getRid()));
                }else {
                    map.put("children", null);
                }
                map.put("state", pr.getStatus());
            	if(map != null){
            		comboTreeList.add(map);
            	}
            }
            Gson gson = new Gson();
            String json = gson.toJson(comboTreeList);
            comboTreeList.clear();
            return json;
    }
    
    private List<Map<String, Object>> createComboTreeChildren(List<PermissionResource> list, String pId) {
        List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
        String str = pId.split("-")[1];
        Integer a = Integer.parseInt(str);
        for(PermissionResource pr : list){
        	Map<String,Object> map = new HashMap<String,Object>();
            map.put("id", pr.getRid());
            map.put("text", pr.getName());
            map.put("checked", pr.getChecked());
            if (pr.getChildren() != null){
                if(pr.getpId().equals(a)){
                    map.put("children", this.createComboTreeChildren(pr.getChildren(), pr.getRid()));
                }else {
                    map.put("children", null);
                }
            }else {
                map.put("children", null);
            }
            map.put("state", pr.getStatus());
        	if(map != null){
        		childList.add(map);
        	}
        }
        return childList;
    }

/*    private List<PermissionResource> buildResourceTree(List<PermissionResource> resources) {
        if (resources == null || resources.isEmpty())
            return null;
        List<PermissionResource> result = new ArrayList<PermissionResource>(resources.size());
        for (PermissionResource res : resources) {
            PermissionResourceForm form = new PermissionResourceForm();
            form.setRid(res.getRid());
            form.setName(res.getName());
            form.setRtype(res.getRtype());
            form.setStatus(res.getStatus());
            form.setChecked(res.getChecked());

            if (res.getChildren() != null && !res.getChildren().isEmpty()) {
                form.setChildren(this.buildResourceTree(res.getChildren()));
                form.setExpanded(true);
                form.setLeaf(false);
            } else {
                form.setExpanded(false);
                form.setLeaf(true);
            }
            if (res.getRtype() == SysPermission.RESOURCE_MENU)
                form.setIconCls("mtree-i");
            else if (res.getRtype() == SysPermission.RESOURCE_ACTION)
                form.setIconCls("mtree-act");
            else
                form.setIconCls("mtree-m");

            result.add(form);
        }
        return result;
    }*/

    @RequestMapping(value = { "/assign" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Boolean> assign(@RequestParam(required = true) Integer ownerId,
                                   @RequestParam(required = true) Integer ownerType,
                                   @RequestParam(required = true) String ids) {
        HttpJsonResult<Boolean> result = new HttpJsonResult<Boolean>();
        try {
            ServiceResult<Boolean> srvResult = this.systemCenterService.assignPermission(ownerId,
                ownerType, StringUtil.isEmpty(ids) ? null : ids.split(","));
            if (!srvResult.getSuccess()) {
                result.setMessage(srvResult.getMessage());
            }
        } catch (Exception e) {
            log.error("[sys][perm]为用户分配权限失败", e);
            result.setMessage("为用户分配权限失败：" + e.getMessage());
        }

        return result;
    }
}