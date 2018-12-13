package com.haier.system.service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.system.model.*;

import java.util.Date;
import java.util.List;

public abstract interface SystemCenterService {
	public abstract ServiceResult<List<SysMenu>> loadUserMenu(String paramString);

	public abstract ServiceResult<List<SysMenu>> getMgMenu();

	public abstract ServiceResult<List<SysMenu>> findMenuTree();

	// public abstract ServiceResult<Integer> createMenu(SysMenu paramSysMenu);

	public abstract ServiceResult<SysMenu> createMenu(SysMenu paramSysMenu);

	public abstract ServiceResult<Boolean> updateMenu(SysMenu paramSysMenu);

	public abstract ServiceResult<List<SysUser>> findUser(String paramString1,
			String paramString2, Integer paramInteger, PagerInfo paramPagerInfo);

	public abstract ServiceResult<SysUser> getUserByLoginId(String loginId);

	public abstract ServiceResult<Boolean> updateUser(SysUser paramSysUser);

	public abstract ServiceResult<Integer> createUser(SysUser paramSysUser);

	public abstract ServiceResult<SysUser> userLogin(String paramString1,
			String paramString2, String paramString3);

	public abstract ServiceResult<SysUser> userLoginByClientIp(
			String paramString1, String paramString2, String paramString3,
			String paramString4);

	public abstract ServiceResult<Boolean> userLogout(String paramString);

	public abstract ServiceResult<List<SysRole>> findRole(String paramString,
			Integer paramInteger, PagerInfo paramPagerInfo);

	public abstract ServiceResult<SysRole> getRoleById(Integer paramInteger);

	public abstract ServiceResult<Boolean> updateRole(SysRole paramSysRole,
			int paramInt);

	public abstract ServiceResult<Integer> createRole(SysRole paramSysRole,
			int paramInt);

	public abstract ServiceResult<List<SysRole>> findUserRoleAssigned(
			Integer paramInteger);

	public abstract ServiceResult<List<SysRole>> findUserRoleUnAssigned(
			Integer paramInteger);

	public abstract ServiceResult<Boolean> assignUserRole(Integer paramInteger,
			List<Integer> paramList);

	public abstract ServiceResult<Boolean> unassignUserRole(
			Integer paramInteger, List<Integer> paramList);

	public abstract ServiceResult<List<SysAction>> findAction(
			String paramString1, String paramString2, String paramString3,
			Integer paramInteger, String paramString4, PagerInfo paramPagerInfo);

	public abstract ServiceResult<SysAction> getActionById(int paramInt);

	public abstract ServiceResult<Boolean> updateAction(
			SysAction paramSysAction, int paramInt);

	public abstract ServiceResult<Integer> createAction(
			SysAction paramSysAction, int paramInt);

	public abstract ServiceResult<List<PermissionOwner>> findPermissionOwner(
			Integer paramInteger1, String paramString, Integer paramInteger2,
			PagerInfo paramPagerInfo);

	public abstract ServiceResult<List<PermissionResource>> findPermissionResourceTree(
			Integer paramInteger1, Integer paramInteger2, String paramString);

	public abstract ServiceResult<Boolean> assignPermission(
			Integer paramInteger1, Integer paramInteger2,
			String[] paramArrayOfString);

	public abstract ServiceResult<SysSession> checkPermission(
			String paramString1, String paramString2);

	public abstract ServiceResult<SysSession> checkPermissionByClientIp(
			String paramString1, String paramString2, String paramString3);

	public abstract ServiceResult<Boolean> checkPermissionBySessionIdAndActKey(
			String paramString1, String paramString2);

	public abstract ServiceResult<Boolean> createAccessLog(
			SysAccessLog paramSysAccessLog);

	public abstract ServiceResult<List<SysAccessLog>> findAccessLog(
			Date paramDate1, Date paramDate2, String paramString1,
			String paramString2, String paramString3, String paramString4,
			PagerInfo paramPagerInfo);

	public abstract ServiceResult<Boolean> changePwd(Integer paramInteger,
			String paramString1, String paramString2);

	public abstract ServiceResult<Boolean> initPwd(Integer paramInteger,
			String paramString1, String paramString2);

	public abstract ServiceResult<List<SysRole>> getRoleByUserId(Integer userId);

	public PlanInDate getPlanInDateById(int id);
	
	public PlanInDate getPlanInDateByTypeId(int typeId);

	public int insertPlanInDate(PlanInDate planIndate);

	public int updatePlanInDate(PlanInDate planIndate);
	
	public ServiceResult<List<PlanInDate>> findPlanInDate(PagerInfo pager);
}