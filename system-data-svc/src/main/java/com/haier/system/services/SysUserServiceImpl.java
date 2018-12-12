package com.haier.system.services;

import com.haier.system.dao.SysUserDao;
import com.haier.system.model.SysRole;
import com.haier.system.model.SysUser;
import com.haier.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 李超 on 2018/1/12.
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserDao sysUserDao;

    @Override
    public List<Integer> findUserRoleIds(Integer userId) {
        return sysUserDao.findUserRoleIds(userId);
    }

    @Override
    public List<SysUser> find(String name, String loginId, Integer status, Integer start, Integer size) {
        return sysUserDao.find(name, loginId, status, start, size);
    }

    @Override
    public Integer findCount(String name, String loginId, Integer status) {
        return sysUserDao.findCount(name, loginId, status);
    }

    @Override
    public SysUser get(int sysUserId) {
        return sysUserDao.get(sysUserId);
    }

    @Override
    public SysUser getByLoginId(String loginId) {
        return sysUserDao.getByLoginId(loginId);
    }

    @Override
    public int update(SysUser sysUser) {
        return sysUserDao.update(sysUser);
    }

    @Override
    public int create(SysUser sysUser) {
        sysUserDao.create(sysUser);
        return sysUser.getUserId();
    }

    @Override
    public int updatePasswordByUserId(Integer userId, String newPassword) {
        return sysUserDao.updatePasswordByUserId(userId, newPassword);
    }

    @Override
    public List<SysRole> getRoleByUserId(Integer roleId) {
        return sysUserDao.getRoleByUserId(roleId);
    }
}
