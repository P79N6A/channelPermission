package com.haier.svc.api.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.haier.system.model.SysMenu;
import com.haier.system.service.SystemCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.haier.common.ServiceResult;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;
import com.haier.svc.api.form.MenuItem;
import com.haier.svc.api.form.MenuItemForm;
import com.haier.svc.api.form.MenuModuleForm;

@Controller
@RequestMapping("menu")
public class MenuController {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                   .getLogger(MenuController.class);

    @Autowired
    SystemCenterService systemCenterService;

	@RequestMapping(value = { "/toMenuList" })
	public String toUserList(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> modelMap) {
		return "sys/menu/menuList";
	}
	
    /**
     * 查询展示菜单
     * @param request
     * @param response
     */
    @RequestMapping(value={"/find"})
    @ResponseBody
    public void loadUserMenu(HttpServletRequest request, HttpServletResponse response) {
    	ServiceResult<List<MenuModuleForm>> result = new ServiceResult<List<MenuModuleForm>>();
        try {
            //TODO: 取登陆用户信息
//            ServiceResult<List<SysMenu>> menuResult = systemCenterService.loadUserMenu(WebUtil
//                .sessionId(request));
            HttpSession session = request.getSession();
            String sessionId = session.getId();
//            ServiceResult<List<SysMenu>> menuResult = systemCenterService.loadUserMenu(sessionId);
            ServiceResult<List<SysMenu>> menuResult = systemCenterService.findMenuTree();

            if (!menuResult.getSuccess()) {
                result.setMessage(menuResult.getMessage());
                //return result;
            }
            List<MenuModuleForm> form = new ArrayList<MenuModuleForm>(
                menuResult.getResult() == null ? 0 : menuResult.getResult().size());
            for (SysMenu module : menuResult.getResult()) {
                if (module.getChildren() == null || module.getChildren().isEmpty())
                    continue;
                MenuModuleForm mm = new MenuModuleForm();
                mm.setMmid(module.getMenuId());
                mm.setTitle(module.getMenuName());
                mm.setItems(new ArrayList<MenuItemForm>(module.getChildren().size()));
                for (SysMenu menuItem : module.getChildren()) {
                    MenuItemForm mi = new MenuItemForm();
                    mi.setMiid(menuItem.getMenuId());
                    mi.setTitle(menuItem.getMenuName());
                    mi.setType(menuItem.getMenuItemType());
                    mi.setData(menuItem.getMenuItemData());
                    mm.getItems().add(mi);
                }
                form.add(mm);
            }
            result.setResult(form);
            Map<String, Object> retMap = new HashMap<String, Object>();
            Gson gson = new Gson();
            retMap.put("rows", menuResult.getResult());
            //retMap.put("total", result.getPager().getRowsCount());
            response.addHeader("Content-type", "text/html;charset=utf-8");
			response.getWriter().write(gson.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            log.error("加载用户菜单异常", e);
            result.setMessage(e.getMessage());
        }
        
        //return result;
    }

    @RequestMapping(value = { "/find/menutree" }, method = { RequestMethod.GET })
    @ResponseBody
    public MenuItem findMenuTree() {
        //HttpJsonResult<MenuTreeRoot> result = new HttpJsonResult<MenuTreeRoot>();
        MenuItem root = new MenuItem();
        try {
            ServiceResult<List<SysMenu>> menuResult = systemCenterService.findMenuTree();
            if (!menuResult.getSuccess()) {
                //result.setMessage(menuResult.getMessage());
                return root;
            }
            root.setName("ROOT");
            root.setStatus(1);
            root.setMdata("");
            root.setPid(0);
            root.setMid(0);
            root.setExpanded(true);
            root.setLeaf(false);
            root.setMtype("root");
            root.setChildren(this.buildMenuItem(menuResult.getResult()));
            //result.setData(root);
        } catch (Exception e) {
            log.error("加载用户菜单异常", e);
            //result.setMessage(e.getMessage());
        }

        return root;
    }

    private List<MenuItem> buildMenuItem(List<SysMenu> menus) {
        if (menus == null || menus.isEmpty())
            return null;
        List<MenuItem> result = new ArrayList<MenuItem>();
        for (SysMenu menu : menus) {
            MenuItem mi = new MenuItem();
            mi.setMid(menu.getMenuId());
            mi.setPid(menu.getParentId());
            mi.setName(menu.getMenuName());
            mi.setMtype(menu.getMenuItemType());
            mi.setMdata(menu.getMenuItemData());
            mi.setStatus(menu.getStatus());
            mi.setOrderBy(menu.getOrderBy());
            mi.setUpdateTime(menu.getUpdateTime());
            mi.setUpdateUser(menu.getUpdateUser());

            if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                mi.setChildren(this.buildMenuItem(menu.getChildren()));
                mi.setLeaf(false);
                mi.setExpanded(true);
            } else {
                mi.setLeaf(true);
                mi.setExpanded(false);
            }

            if (SysMenu.MENU_MODULE.equals(menu.getMenuItemType()))
                mi.setIconCls("mtree-m");
            else
                mi.setIconCls("mtree-i");
            result.add(mi);
        }
        return result;
    }

    /**
     * 操作修改菜单项
     * @param mi
     * @return
     */
    @RequestMapping(value = { "/update" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Boolean> update(
    		@RequestParam(required = false) Integer mId,
    		@RequestParam(required = false) String mData,
    		@RequestParam(required = false) String mType,
    		@RequestParam(required = false) String name,
    		@RequestParam(required = false) Integer orderBy,
    		@RequestParam(required = false) Integer pId,
    		@RequestParam(required = false) Integer status
    		) {
        try {
            SysMenu menu = new SysMenu();
            menu.setMenuId(mId);
            menu.setParentId(pId);
            menu.setMenuItemData(mData);
            menu.setMenuItemType(mType);
            menu.setMenuName(name);
            menu.setOrderBy(orderBy);
            menu.setStatus(status);
            ServiceResult<Boolean> result = this.systemCenterService.updateMenu(menu);
            if (!result.getSuccess())
                return new HttpJsonResult<Boolean>(result.getMessage());
            return new HttpJsonResult<Boolean>(result.getResult());
        } catch (Exception e) {
            log.error("更新菜单异常", e);
            return new HttpJsonResult<Boolean>(e.getMessage());
        }
    }

    /**
     * 创建菜单项
     * @param mi
     * @return
     */
    @RequestMapping(value = { "/create" }, method = { RequestMethod.POST })
    @ResponseBody
    public void create(
    		@RequestParam(required = false) String mData,
    		@RequestParam(required = false) String mType,
    		@RequestParam(required = false) String name,
    		@RequestParam(required = false) Integer orderBy,
    		@RequestParam(required = false) Integer pId,
    		@RequestParam(required = false) Integer status,
    		HttpServletRequest request,HttpServletResponse response
    		) {
        try {
            SysMenu menu = new SysMenu();
            menu.setMenuItemData(mData);
            menu.setMenuItemType(mType);
            menu.setMenuName(name);
            menu.setOrderBy(orderBy);
            menu.setParentId(pId);
            menu.setStatus(status);
            ServiceResult<SysMenu> srvResult = this.systemCenterService.createMenu(menu);
/*            if (!srvResult.getSuccess())
                return new HttpJsonResult<Boolean>(srvResult.getMessage());
            return new HttpJsonResult<Boolean>(true);*/
            Map<String, Object> retMap = new HashMap<String, Object>();
            Gson gson = new Gson();
            retMap.put("rows", srvResult.getResult());
            //retMap.put("sessionId", );
//            retMap.put("sessionId", 1);
            response.addHeader("Content-type", "text/html;charset=utf-8");
			response.getWriter().write(gson.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            log.error("创建菜单异常", e);
            //return new HttpJsonResult<Boolean>(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = { "/getMgMenu" })
    public String getMgMenu(){
        List<SysMenu> list = systemCenterService.getMgMenu().getResult();

        if (list == null){
            log.error("加载用户权限菜单异常");
            return null;
        }
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
        for (SysMenu sm : list){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("id",sm.getMenuId());
            map.put("text",sm.getMenuName());
            if (map != null){
                treeList.add(map);
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(treeList);
        treeList.clear();
        return json;
    }
}