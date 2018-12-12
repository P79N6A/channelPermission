package com.haier.system.services;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.system.model.SysMenu;
import com.haier.system.service.SysMenuService;
import com.haier.system.service.SystemCenterPageLoadService;
import com.haier.system.services.util.SystemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李超 on 2018/1/23.
 */

@Service
public class SystemCenterPageLoadServiceImpl implements SystemCenterPageLoadService {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(SystemCenterPageLoadServiceImpl.class);

    @Autowired
    SystemModel systemModel;

    @Autowired
    SysMenuService sysMenuService;

    //directory accordion tree

    @Override
    public ServiceResult<List<SysMenu>> loadUserMenu(String sessionId) {
        ServiceResult<List<SysMenu>> result = new ServiceResult<List<SysMenu>>();
        try {
            Assert.notNull(systemModel, "Property 'systemModel' is required.");
            result.setResult(systemModel.loadUserMenu(sessionId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("未知错误");
            log.error("[sys][menu]加载用户菜单时出错", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<SysMenu>> getMenu() {
        ServiceResult<List<SysMenu>> result = new ServiceResult<List<SysMenu>>();
        try {
            Assert.notNull(systemModel, "Property 'systemModel' is required.");
            result.setResult(systemModel.findAllMenuTree());
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("未知错误");
            log.error("[sys][menu]加载用户菜单时出错", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<SysMenu>> getMenuById(Integer id) {
        ServiceResult<List<SysMenu>> result = new ServiceResult<List<SysMenu>>();
        try {
            Assert.notNull(systemModel, "Property 'systemModel' is required.");
            List<SysMenu> sm = sysMenuService.getMenuById(id);
            List<SysMenu> list = new ArrayList<SysMenu>();
            for (SysMenu s : sm){
                s.setChildren(sysMenuService.getMenuById(s.getMenuId()));
                list.add(s);
            }
            result.setResult(list);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("未知错误");
            log.error("[sys][menu]加载用户菜单时出错", e);
        }
        return result;
    }
}
