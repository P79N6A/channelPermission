package com.haier.system.service;

import com.haier.system.model.SysUserRole;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 李超 on 2018/1/12.
 */
public interface SysUserRoleService {

    SysUserRole get(@Param("userRoleId") Integer userRoleId);

    Integer insert(SysUserRole userRole);

    Integer update(SysUserRole userRole);
}
