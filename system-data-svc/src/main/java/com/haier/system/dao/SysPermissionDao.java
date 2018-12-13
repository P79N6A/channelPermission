package com.haier.system.dao;

import com.haier.system.model.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysPermissionDao {
    List<SysPermission> find(@Param("ownerType") Integer ownerType,
                             @Param("ownerIds") List<Integer> ownerIds,
                             @Param("resType") Integer resType,
                             @Param("resIds") List<Integer> resIds);

    List<Integer> findResourceIdsByOwnerId(@Param("ownerType") int ownerType,
                                           @Param("ownerId") int ownerId,
                                           @Param("resType") int resourceType);

    List<Integer> findResourceIdsByOwnerIds(@Param("ownerType") int ownerType,
                                            @Param("ownerIds") List<Integer> ownerIds,
                                            @Param("resType") int resourceType);

    int update(SysPermission perm);

    void create(SysPermission perm);
}