package com.haier.system.services;

import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.system.model.*;
import com.haier.system.service.SystemCenterService;
import com.haier.system.services.util.SystemModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Service
public class SystemCenterServiceImpl implements SystemCenterService {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(SystemCenterServiceImpl.class);
	@Autowired
	SystemModel systemModel;

	public void setSystemModel(SystemModel value) {
		this.systemModel = value;
	}

	@Override
	public ServiceResult<List<SysMenu>> loadUserMenu(String sessionId) {
		ServiceResult<List<SysMenu>> result = new ServiceResult<List<SysMenu>>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.loadUserMenu(sessionId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[sys][menu]加载用户菜单时出错", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<SysMenu>> getMgMenu(){
		ServiceResult<List<SysMenu>> result = new ServiceResult<List<SysMenu>>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.getMgMenu());
		}catch (Exception e){
			result.setSuccess(false);
			result.setMessage("查询所有业务模块时出错");
			log.error("查询所有业务模块时出错",e);
		}
		return result;
	}


	@Override
	public ServiceResult<List<SysUser>> findUser(String name, String loginId,
			Integer status, PagerInfo pager) {
		ServiceResult<List<SysUser>> result = new ServiceResult<List<SysUser>>();
		// model执行成功会设置总记录数到pager中，由ServiceResult返回给客户端
		result.setPager(pager);
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.findUser(name, loginId, status, pager));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[sys][user]查询用户列表异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<SysUser> getUserByLoginId(String loginId) {
		ServiceResult<SysUser> result = new ServiceResult<SysUser>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.getUser(loginId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[sys][user]读取用户异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> updateUser(SysUser user) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.updateUser(user));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[sys][user]更新用户异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Integer> createUser(SysUser user) {
		ServiceResult<Integer> result = new ServiceResult<Integer>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.createUser(user));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[sys][user]创建用户异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<SysRole>> findRole(String name, Integer status,
			PagerInfo pager) {
		ServiceResult<List<SysRole>> result = new ServiceResult<List<SysRole>>();
		// model执行成功会设置总记录数到pager中，由ServiceResult返回给客户端
		result.setPager(pager);
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.findRole(name, status, pager));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[sys][role]查询角色列表异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<SysRole> getRoleById(Integer roleId) {
		ServiceResult<SysRole> result = new ServiceResult<SysRole>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.getRole(roleId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[sys][role]读取角色异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> updateRole(SysRole role, int optUserId) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.updateRole(role, optUserId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[sys][role]更新角色异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Integer> createRole(SysRole role, int optUserId) {
		ServiceResult<Integer> result = new ServiceResult<Integer>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.createRole(role, optUserId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[sys][role]创建角色异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<SysRole>> findUserRoleAssigned(Integer userId) {
		ServiceResult<List<SysRole>> result = new ServiceResult<List<SysRole>>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.findUserRoleAssigned(userId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("获取用户已分配角色列表异常");
			log.error("[sys][role]获取用户已分配角色列表异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<SysRole>> findUserRoleUnAssigned(Integer userId) {
		ServiceResult<List<SysRole>> result = new ServiceResult<List<SysRole>>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.findUserRoleUnAssigned(userId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("获取用户未分配角色列表异常");
			log.error("[sys][role]获取用户未分配角色列表异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> assignUserRole(Integer userId,
			List<Integer> roleIds) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			// TODO：获取当前操作用户
			result.setResult(systemModel.assignUserRole(userId, roleIds, "刘志斌"));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("分配用户角色失败，" + e.getMessage());
			log.error("[sys][role]分配用户角色失败", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> unassignUserRole(Integer userId,
			List<Integer> roleIds) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			// TODO：获取当前操作用户
			result.setResult(systemModel.unassignUserRole(userId, roleIds,
					"刘志斌"));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("取消分配用户角色失败，" + e.getMessage());
			log.error("[sys][role]取消分配用户角色失败", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<SysMenu>> findMenuTree() {
		ServiceResult<List<SysMenu>> result = new ServiceResult<List<SysMenu>>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.findAllMenuTree());
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("获取菜单树状结构异常：" + e.getMessage());
			log.error("[sys][menu]获取菜单树状结构异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<SysMenu> createMenu(SysMenu menu) {
		ServiceResult<SysMenu> result = new ServiceResult<SysMenu>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.createMenu(menu));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误：" + e.getMessage());
			log.error("[sys][menu]创建菜单异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> updateMenu(SysMenu menu) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.updateMenu(menu));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误：" + e.getMessage());
			log.error("[sys][menu]更新菜单异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<SysAction>> findAction(String actKey,
			String actName, String remark, Integer status, String menuName,
			PagerInfo pager) {
		ServiceResult<List<SysAction>> result = new ServiceResult<List<SysAction>>();
		result.setPager(pager);
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.findAction(actKey, actName, remark,
					status, menuName, pager));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误：" + e.getMessage());
			log.error("查询操作列表异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<SysAction> getActionById(int actId) {
		ServiceResult<SysAction> result = new ServiceResult<SysAction>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.getAction(actId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误：" + e.getMessage());
			log.error("读取操作异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> updateAction(SysAction action, int optUserId) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.updateAction(action, optUserId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误：" + e.getMessage());
			log.error("更新操作异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Integer> createAction(SysAction action, int optUserId) {
		ServiceResult<Integer> result = new ServiceResult<Integer>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.createAction(action, optUserId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误：" + e.getMessage());
			log.error("创建操作异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<PermissionOwner>> findPermissionOwner(
			Integer role, String name, Integer status, PagerInfo pager) {
		ServiceResult<List<PermissionOwner>> result = new ServiceResult<List<PermissionOwner>>();
		// model执行成功会设置总记录数到pager中，由ServiceResult返回给客户端
		result.setPager(pager);
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.findPermissionOwner(role, name,
					status, pager));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("查询权限所有者列表异常：" + e.getMessage());
			log.error("[sys][perm]查询权限所有者列表异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<PermissionResource>> findPermissionResourceTree(
			Integer ownerId, Integer ownerType, String name) {
		ServiceResult<List<PermissionResource>> result = new ServiceResult<List<PermissionResource>>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.findPermissionResourceTree(ownerId,
					ownerType, name));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("查询权限资源列表异常：" + e.getMessage());
			log.error("[sys][perm]查询权限资源列表异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> assignPermission(Integer ownerId,
			Integer ownerType, String[] resourceIds) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			systemModel.assignPermission(ownerId, ownerType, resourceIds);
			result.setResult(true);
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("查询权限资源列表异常：" + e.getMessage());
			log.error("[sys][perm] 分配权限异常：owner:" + ownerId + ", type:"
					+ ownerType + ", resource:" + resourceIds, e);
		}
		return result;
	}

	@Override
	public ServiceResult<SysUser> userLogin(String loginId, String password,
			String sessionId) {
		ServiceResult<SysUser> result = new ServiceResult<SysUser>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(this.systemModel.userLogin(loginId, password,
					sessionId, null));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("登陆失败：" + e.getMessage());
			log.error("[sys][user] 登陆失败：loginId:" + loginId + ", password:"
					+ password, e);
		}
		return result;
	}

	@Override
	public ServiceResult<SysUser> userLoginByClientIp(String loginId,
			String password, String sessionId, String clientIp) {
		ServiceResult<SysUser> result = new ServiceResult<SysUser>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(this.systemModel.userLogin(loginId, password,
					sessionId, clientIp));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("登陆失败：" + e.getMessage());
			log.error("[sys][user] 登陆失败：loginId:" + loginId + ", password:"
					+ password, e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> createAccessLog(SysAccessLog accessLog) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			this.systemModel.createAccessLog(accessLog);
			result.setResult(true);
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("记录访问日志异常：" + e.getMessage());
			log.error("[sys][log] 记录访问日志异常：" + JsonUtil.toJson(accessLog), e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> userLogout(String sessionId) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(this.systemModel.userLogout(sessionId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("退出登陆失败：" + e.getMessage());
			log.error("[sys][user] 退出登陆失败：" + sessionId, e);
		}
		return result;
	}

	@Override
	public ServiceResult<SysSession> checkPermission(String sessionId,
			String requestUri) {
		ServiceResult<SysSession> result = new ServiceResult<SysSession>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(this.systemModel.checkPermission(sessionId,
					requestUri, null));
			if (result.getResult() == null) {
				result.setSuccess(false);
				result.setMessage("您不能执行该操作：没有权限");
				return result;
			}
			return result;
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage("您不能执行该操作：" + be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("您不能执行该操作：" + e.getMessage());
			log.error("[sys][perm] 权限校验失败：" + sessionId, e);
		}
		return result;
	}

	@Override
	public ServiceResult<SysSession> checkPermissionByClientIp(
			String sessionId, String requestUri, String clientIp) {
		ServiceResult<SysSession> result = new ServiceResult<SysSession>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(this.systemModel.checkPermission(sessionId,
					requestUri, clientIp));
			if (result.getResult() == null) {
				result.setSuccess(false);
				result.setMessage("您不能执行该操作：没有权限");
				return result;
			}
			return result;
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage("您不能执行该操作：" + be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("您不能执行该操作：" + e.getMessage());
			log.error("[sys][perm] 权限校验失败：" + sessionId, e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> checkPermissionBySessionIdAndActKey(
			String sessionId, String actKey) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(this.systemModel
					.checkPermissionBySessionIdAndActKey(sessionId, actKey));
			if (!result.getResult()) {
				result.setSuccess(false);
				result.setMessage("您不能执行该操作：没有权限");
				return result;
			}
			return result;
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage("您不能执行该操作：" + be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("您不能执行该操作：" + e.getMessage());
			log.error("[sys][perm] 权限校验失败：" + sessionId, e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<SysAccessLog>> findAccessLog(Date startTime,
			Date endTime, String userName, String visitUrl, String clientIp,
			String sessionId, PagerInfo pager) {
		ServiceResult<List<SysAccessLog>> result = new ServiceResult<List<SysAccessLog>>();
		result.setPager(pager);
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.findAccessLog(startTime, endTime,
					clientIp, userName, visitUrl, sessionId, pager));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误，无法查询系统日志：" + e.getMessage());
			log.error("查询系统日志异常", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> changePwd(Integer userId, String oldPassword,
			String newPassword) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(this.systemModel.changePwd(userId, oldPassword,
					newPassword));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("修改密码时出现异常：" + e.getMessage());
			log.error("修改密码时出现异常：userId:" + userId + ", oldPassword:"
					+ oldPassword + ", newPassword:" + newPassword, e);
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> initPwd(Integer userId,
			String encryptOldPassword, String initPassword) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(this.systemModel.initPwd(userId,
					encryptOldPassword, initPassword));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("重置密码时出现异常：" + e.getMessage());
			log.error("重置密码时出现异常：userId:" + userId + ", encryptOldPassword:"
					+ encryptOldPassword + ", initPassword:" + initPassword, e);
		}
		return result;
	}

	@Override
	public ServiceResult<List<SysRole>> getRoleByUserId(Integer userId) {
		ServiceResult<List<SysRole>> result = new ServiceResult<List<SysRole>>();
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.getRoleByUserId(userId));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[sys][role]读取角色异常", e);
		}
		return result;
	}

	public PlanInDate getPlanInDateById(int id) {
		return systemModel.getPlanInDateById(id);
	}

	public int insertPlanInDate(PlanInDate planIndate) {
		return systemModel.insert(planIndate);
	}

	public int updatePlanInDate(PlanInDate planIndate) {
		return systemModel.update(planIndate);
	}

	public ServiceResult<List<PlanInDate>> findPlanInDate(PagerInfo pager) {
		ServiceResult<List<PlanInDate>> result = new ServiceResult<List<PlanInDate>>();
		// model执行成功会设置总记录数到pager中，由ServiceResult返回给客户端
		result.setPager(pager);
		try {
			Assert.notNull(systemModel, "Property 'systemModel' is required.");
			result.setResult(systemModel.findPlanInDate(pager));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("未知错误");
			log.error("[sys][planInDate]查询列表异常", e);
		}
		return result;
	}
	
	public PlanInDate getPlanInDateByTypeId(int typeId){
		return systemModel.getPlanInDateByTypeId(typeId);
	}
}
