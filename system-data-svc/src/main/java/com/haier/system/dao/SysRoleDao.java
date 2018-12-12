package com.haier.system.dao;

import com.haier.system.model.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysRoleDao {
    List<SysRole> find(@Param("name") String name, @Param("status") Integer status,
                       @Param("start") Integer start, @Param("size") Integer size);

    Integer findCount(@Param("name") String name, @Param("status") Integer status);

    /**
     * 获取已经分配给用户的角色列表。
     * 会返回所有状态的角色列表
     * @param userId
     * @return
     */
    List<SysRole> findUserRoleAssigned(@Param("userId") Integer userId);

    /**
     * 获取剩余未分配给用户的角色列表。
     * 只会返回有效状态的角色列表。
     * @param userId
     * @return
     */
    List<SysRole> findUserRoleUnAssigned(@Param("userId") Integer userId);

    /**
     * 给用户分配角色。
     * @param userId
     * @param roleIds 角色ID列表，必须至少包含1个或多个角色ID才能调用该方法，否则会报错。
     * @param updateUser 更新人
     * @return
     */
    int assignUserRoles(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds,
                        @Param("updateUser") String updateUser);

    int unassignUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId,
                         @Param("updateUser") String updateUser);

    SysRole get(int roleId);

    int update(SysRole role);

    void create(SysRole role);
}
