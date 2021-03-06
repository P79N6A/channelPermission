package com.haier.system.service;

import com.haier.system.model.SysRole;
import com.haier.system.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 李超 on 2018/1/12.
 */
public interface SysUserService {
    List<Integer> findUserRoleIds(@Param("userId") Integer userId);

    List<SysUser> find(@Param("name") String name, @Param("loginId") String loginId,
                       @Param("status") Integer status, @Param("start") Integer start,
                       @Param("size") Integer size);

    Integer findCount(@Param("name") String name, @Param("loginId") String loginId,
                      @Param("status") Integer status);

    SysUser get(int sysUserId);

    SysUser getByLoginId(@Param("loginId") String loginId);

    int update(SysUser sysUser);

    int create(SysUser sysUser);

    int updatePasswordByUserId(@Param("userId") Integer userId,
                               @Param("password") String newPassword);

    List<SysRole> getRoleByUserId(Integer roleId);
}
