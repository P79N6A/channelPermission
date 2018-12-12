package com.haier.system.services;

import com.haier.system.dao.SysPermissionDao;
import com.haier.system.model.SysPermission;
import com.haier.system.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 李超 on 2018/1/12.
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    SysPermissionDao sysPermissionDao;

    @Override
    public List<SysPermission> find(Integer ownerType, List<Integer> ownerIds, Integer resType, List<Integer> resIds) {
        return sysPermissionDao.find(ownerType, ownerIds, resType, resIds);
    }

    @Override
    public List<Integer> findResourceIdsByOwnerId(int ownerType, int ownerId, int resourceType) {
        return sysPermissionDao.findResourceIdsByOwnerId(ownerType, ownerId, resourceType);
    }

    @Override
    public List<Integer> findResourceIdsByOwnerIds(int ownerType, List<Integer> ownerIds, int resourceType) {
        return sysPermissionDao.findResourceIdsByOwnerIds(ownerType, ownerIds, resourceType);
    }

    @Override
    public int update(SysPermission perm) {
        return sysPermissionDao.update(perm);
    }

    @Override
    public void create(SysPermission perm) {
        sysPermissionDao.create(perm);
    }
}
