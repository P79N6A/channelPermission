package com.haier.system.dao;

import com.haier.system.model.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysMenuDao {
    List<SysMenu> loadMenus(@Param("ids") List<Integer> ids);

    List<SysMenu> findAllMenus();

    int create(SysMenu menu);

    int update(SysMenu menu);

    List<SysMenu> getMenuById(Integer id);

    List<SysMenu> getMgMenu(String type);
}