package com.haier.system.services;

import com.haier.system.dao.SysMenuDao;
import com.haier.system.model.SysMenu;
import com.haier.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 李超 on 2018/1/12.
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    SysMenuDao sysMenuDao;

    @Override
    public List<SysMenu> loadMenus(List<Integer> ids) {
        return sysMenuDao.loadMenus(ids);
    }

    @Override
    public List<SysMenu> findAllMenus() {
        return sysMenuDao.findAllMenus();
    }

    @Override
    public int create(SysMenu menu) {
        sysMenuDao.create(menu);
        return menu.getMenuId();
    }

    @Override
    public int update(SysMenu menu) {
        return sysMenuDao.update(menu);
    }

    @Override
    public List<SysMenu> getMenuById(Integer id){
        return sysMenuDao.getMenuById(id);
    }

    @Override
    public List<SysMenu> getMgMenu(String type){
        return sysMenuDao.getMgMenu(type);
    }
}
