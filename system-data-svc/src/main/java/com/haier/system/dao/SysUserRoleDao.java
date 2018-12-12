package com.haier.system.dao;

import com.haier.system.model.SysUserRole;
import org.apache.ibatis.annotations.Param;


public interface SysUserRoleDao {
	
	SysUserRole get(@Param("userRoleId") Integer userRoleId);
	
	Integer insert(SysUserRole userRole);
	
	Integer update(SysUserRole userRole);
}
