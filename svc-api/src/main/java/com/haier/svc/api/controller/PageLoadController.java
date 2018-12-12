package com.haier.svc.api.controller;

import com.google.gson.Gson;
import com.haier.common.ServiceResult;
import com.haier.svc.api.form.PermissionResourceForm;
import com.haier.system.model.PermissionResource;
import com.haier.system.model.SysMenu;
import com.haier.system.service.SystemCenterPageLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李超 on 2018/1/23.
 */

@Controller
@RequestMapping(value = "pageload")
public class PageLoadController {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(PageLoadController.class);

    @Autowired
    private SystemCenterPageLoadService systemCenterPageLoadService;

    @ResponseBody
    @RequestMapping(value = "/accordion")
    public String accordion(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        System.out.println("pageload sessionId ==>"+session.getId());
        List<SysMenu> menus = systemCenterPageLoadService.loadUserMenu(sessionId).getResult();
        List<SysMenu> allMenus = systemCenterPageLoadService.getMenu().getResult();
        List<SysMenu> list = new ArrayList<SysMenu>();

        if (session.getAttribute("loginId") == null){
            return "timeout";
        }

        if (menus == null){
            log.error("加载用户权限菜单异常");
            return null;
        }

        for (int i = 0;i < menus.size();i++){
            for (SysMenu sm : allMenus){
                if (sm.getMenuId().equals(menus.get(i).getParentId())){
                    if (!list.contains(sm)) {
                        list.add(sm);
                    }
                }
            }
        }
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
        for (SysMenu sm : list){
            if (sm.getMenuItemType() .equals("mg")){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("id",sm.getMenuId());
                map.put("text",sm.getMenuName());
                map.put("userName",session.getAttribute("userName"));
                if (map != null){
                    treeList.add(map);
                }
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(treeList);
        treeList.clear();
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/tree")
    public String tree(){
        ServiceResult<List<SysMenu>> menus = systemCenterPageLoadService.loadUserMenu("023a8a76-03b9-4f90-935e-49d20dface3d");
        Map<String, Object> retMap = new HashMap<String, Object>();
        List<Map<String, Object>> comboTreeList = new ArrayList<Map<String, Object>>();
        List<SysMenu> result = menus.getResult();
        if(result == null){
            log.error("加载用户菜单异常");
        }
        for(SysMenu sm : result){
            Map<String,Object> map = null;
            map = new HashMap<String,Object>();
            map.put("id", sm.getMenuId());
            map.put("text", sm.getMenuName());
            map.put("data",sm.getMenuItemData());
            map.put("type",sm.getMenuItemType());
//            map.put("checked", pr.getChecked());
            map.put("state", sm.getStatus());
            map.put("children", this.createComboTreeChildren(sm.getChildren(),sm.getParentId()));

            if(map != null){
                comboTreeList.add(map);
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(comboTreeList);
        comboTreeList.clear();
        return json;
    }

    private List<Map<String, Object>> createComboTreeChildren(List<SysMenu> list, Integer pId) {
        List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();

        for(SysMenu sm : list){
            Map<String,Object> map =  new HashMap<String,Object>();
            map.put("id", sm.getMenuId());
            map.put("text", sm.getMenuName());
            map.put("data",sm.getMenuItemData());
            map.put("type",sm.getMenuItemType());
            if (sm.getChildren() != null){
                if(sm.getParentId().equals(pId)){
                    map.put("children", this.createComboTreeChildren(sm.getChildren(), sm.getMenuId()));
                }else {
                    map.put("children", null);
                }
            }else {
                map.put("children", null);
            }
            map.put("state", sm.getStatus());
            if(map != null){
                childList.add(map);
            }
        }
        return childList;
    }

    @ResponseBody
    @RequestMapping(value = "/getMenuById")
    public String getMenuById(Integer id,HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        String sessionId = session.getId();
//        ServiceResult<List<SysMenu>> menus = systemCenterPageLoadService.getMenuById(id);
//        Map<String, Object> retMap = new HashMap<String, Object>();
        List<Map<String, Object>> comboTreeList = new ArrayList<Map<String, Object>>();
//        List<SysMenu> result = menus.getResult();
//        if(result == null){
//            log.error("加载用户菜单异常");
//        }
        List<SysMenu> menus = systemCenterPageLoadService.loadUserMenu(sessionId).getResult();
        List<SysMenu> allMenus = systemCenterPageLoadService.getMenuById(id).getResult();
        List<SysMenu> list = new ArrayList<SysMenu>();
        if (menus == null){
            log.error("加载用户权限菜单异常");
            return null;
        }
        for (int i = 0;i < menus.size();i++){
            for (SysMenu sm : allMenus){
                for (SysMenu sysMenu : menus.get(i).getChildren()){
                    if (sysMenu.getMenuId().equals(sm.getMenuId())){
                        if (!list.contains(sm)) {
                            list.add(sm);
                        }
                    }
                }

            }
        }
        for(SysMenu sm : list){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id", sm.getMenuId());
            map.put("text", sm.getMenuName());
            map.put("data",sm.getMenuItemData());
//            map.put("type",sm.getMenuItemType());
//            map.put("state", sm.getStatus());
//            if (sm.getChildren() != null){
//                map.put("children", this.createComboTreeChildren(sm.getChildren(),sm.getParentId()));
//            }else {
//                map.put("children", null);
//            }
            if(map != null){
                comboTreeList.add(map);
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(comboTreeList);
        comboTreeList.clear();
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/model")
    public String model(Integer id,HttpServletRequest request,HttpServletResponse response){
//        ServiceResult<List<SysMenu>> menus = systemCenterPageLoadService.getMenuById(id);
//        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
//        List<SysMenu> list = menus.getResult();
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        List<SysMenu> menus = systemCenterPageLoadService.loadUserMenu(sessionId).getResult();
        List<SysMenu> allMenus = systemCenterPageLoadService.getMenuById(id).getResult();
        List<SysMenu> list = new ArrayList<SysMenu>();
        if (menus == null){
            log.error("加载用户权限菜单异常");
            return null;
        }
        for (int i = 0;i < menus.size();i++){
            for (SysMenu sm : allMenus){
                if (menus.get(i).getMenuId().equals(sm.getMenuId())){
                    if (!list.contains(sm)) {
                        list.add(sm);
                    }
                }
            }
        }

        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
        for (SysMenu sm : list){
            if (sm.getMenuItemType() .equals("mdl")){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("id",sm.getMenuId());
                map.put("text",sm.getMenuName());
                if (map != null){
                    treeList.add(map);
                }
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(treeList);
        treeList.clear();
        return json;
    }

}
