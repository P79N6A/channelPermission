package com.haier.system.services;

import com.haier.system.dao.SysUserRoleDao;
import com.haier.system.model.SysUserRole;
import com.haier.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 李超 on 2018/1/12.
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    SysUserRoleDao sysUserRoleDao;

    @Override
    public SysUserRole get(Integer userRoleId) {
        return sysUserRoleDao.get(userRoleId);
    }

    @Override
    public Integer insert(SysUserRole userRole) {
        return sysUserRoleDao.insert(userRole);
    }

    @Override
    public Integer update(SysUserRole userRole) {
        return sysUserRoleDao.update(userRole);
    }
}
