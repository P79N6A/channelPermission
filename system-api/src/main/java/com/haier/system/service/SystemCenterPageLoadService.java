package com.haier.system.service;

import com.haier.common.ServiceResult;
import com.haier.system.model.SysMenu;

import java.util.List;

/**
 * Created by 李超 on 2018/1/23.
 */
public interface SystemCenterPageLoadService {
    public ServiceResult<List<SysMenu>> loadUserMenu(String sessionId);

    public ServiceResult<List<SysMenu>> getMenu();

    public ServiceResult<List<SysMenu>> getMenuById(Integer id);
}
