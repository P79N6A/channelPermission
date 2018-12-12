package com.haier.system.service;

import com.haier.system.model.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 李超 on 2018/1/12.
 */
public interface SysMenuService {
    List<SysMenu> loadMenus(@Param("ids") List<Integer> ids);

    List<SysMenu> findAllMenus();

    int create(SysMenu menu);

    int update(SysMenu menu);

    List<SysMenu> getMenuById(Integer id);

    List<SysMenu> getMgMenu(String type);
}
