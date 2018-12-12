package com.haier.system.service;

import com.haier.system.model.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 李超 on 2018/1/12.
 */
public interface SysPermissionService {
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
