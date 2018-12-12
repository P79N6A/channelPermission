package com.haier.system.services;

import com.haier.system.dao.SysRoleDao;
import com.haier.system.model.SysRole;
import com.haier.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 李超 on 2018/1/12.
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    SysRoleDao sysRoleDao;

    @Override
    public List<SysRole> find(String name, Integer status, Integer start, Integer size) {
        return sysRoleDao.find(name, status, start, size);
    }

    @Override
    public Integer findCount(String name, Integer status) {
        return sysRoleDao.findCount(name, status);
    }

    @Override
    public List<SysRole> findUserRoleAssigned(Integer userId) {
        return sysRoleDao.findUserRoleAssigned(userId);
    }

    @Override
    public List<SysRole> findUserRoleUnAssigned(Integer userId) {
        return sysRoleDao.findUserRoleUnAssigned(userId);
    }

    @Override
    public int assignUserRoles(Integer userId, List<Integer> roleIds, String updateUser) {
        return sysRoleDao.assignUserRoles(userId, roleIds, updateUser);
    }

    @Override
    public int unassignUserRole(Integer userId, Integer roleId, String updateUser) {
        return sysRoleDao.unassignUserRole(userId, roleId, updateUser);
    }

    @Override
    public SysRole get(int roleId) {
        return sysRoleDao.get(roleId);
    }

    @Override
    public int update(SysRole role) {
        return sysRoleDao.update(role);
    }

    @Override
    public void create(SysRole role) {
        sysRoleDao.create(role);
    }
}
