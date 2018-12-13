package com.haier.svc.api.controller.system;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.system.model.SysRole;
import com.haier.system.model.SysUser;
import com.haier.system.service.SystemCenterService;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.DataDictionary;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 系统管理功能
 */
@Controller
@RequestMapping("/sys/user/")
public class UserController {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(UserController.class);

	@Autowired
	SystemCenterService systemCenterService;

	@RequestMapping(value = { "toUserList" }, method = { RequestMethod.GET })
	public String toUserList(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> modelMap) {
		return "sys/user/userList";
	}

	/**
	 *
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = { "getStatus" }, method = { RequestMethod.GET })
	@ResponseBody
	HttpJsonResult<List<DataDictionary>> getStatus(HttpServletRequest request) {

		HttpJsonResult<List<DataDictionary>> result = new HttpJsonResult<List<DataDictionary>>();

		List<DataDictionary> list = new ArrayList<DataDictionary>();
		DataDictionary dict = new DataDictionary();
		dict.setValue("1");
		dict.setValue_meaning("有效");
		list.add(dict);
		dict = new DataDictionary();
		dict.setValue("0");
		dict.setValue_meaning("禁用");
		list.add(dict);
		dict = new DataDictionary();
		dict.setValue("-1");
		dict.setValue_meaning("删除");
		list.add(dict);
		result.setData(list);
		return result;
	}

	@RequestMapping(value = { "/find" })
	@ResponseBody
	public void findUser(
			@RequestParam(required = false) String userName,
			@RequestParam(required = false) String loginId,
			@RequestParam(required = false) Integer status,
			// @RequestParam(required = false) Integer pi,
			// @RequestParam(required = false) Integer ps,
			@ApiQueryParam(name = "rows") @RequestParam(required = false) Integer rows,
			@ApiQueryParam(name = "page") @RequestParam(required = false) Integer page,
			HttpServletRequest request, HttpServletResponse response) {
		// HttpJsonResult<List<SysUser>> result = new
		// HttpJsonResult<List<SysUser>>();
		try {
			PagerInfo pager = null;
			// if (pi != null && pi > 0 && ps != null && ps > 0)
			if (rows == null)
				rows = 20;
			if (page == null)
				page = 1;
			pager = new PagerInfo(rows, page);
			ServiceResult<List<SysUser>> userResult = systemCenterService.findUser(
					userName, loginId, status, pager);
			// if (!userResult.getSuccess()) {
			// result.setMessage(userResult.getMessage());
			// return result;
			// }
			// //屏蔽用户密码
			// if (userResult.getResult() != null) {
			// for (SysUser user : userResult.getResult()) {
			// user.setPassword(WebUtil.PASSWORD_MASK);
			// }
			// }
			// if (userResult.getPager() != null)
			// result.setTotalCount(userResult.getPager().getRowsCount());
			// result.setData(userResult.getResult());
			if (userResult.getSuccess() && userResult.getResult() != null) {
				for (SysUser user : userResult.getResult()) {
					user.setPassword(WebUtil.PASSWORD_MASK);
				}
				List<SysUser> predictstocklist = userResult.getResult();
				Map<String, Object> retMap = new HashMap<String, Object>();
				retMap.put("total", userResult.getPager().getRowsCount());
				retMap.put("rows", predictstocklist);
				Gson gson = new Gson();
				response.addHeader("Content-type", "text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(retMap));
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (Exception e) {
			log.error("查询用户列表异常", e);
		}

	}

/*	private HttpJsonResult<SysUser> internalGetUser(Integer userId) {
		try {
			// 取用户资料
			ServiceResult<SysUser> serviceResult = systemCenterService.getUserById(userId);
			if (!serviceResult.getSuccess())
				return new HttpJsonResult<SysUser>(serviceResult.getMessage());

			return new HttpJsonResult<SysUser>(serviceResult.getResult());
		} catch (Exception e) {
			log.error("获取用户异常:", e);
			return new HttpJsonResult<SysUser>(e.getMessage());
		}
	}*/

/*	@RequestMapping(value = { "/get/{userId}" }, method = { RequestMethod.GET })
	public @ResponseBody HttpJsonResult<SysUser> getUser(
			@PathVariable Integer userId) {
		return this.internalGetUser(userId);
	}*/
	
	@RequestMapping(value = { "/update" }, method = { RequestMethod.POST })
	@ResponseBody
	public HttpJsonResult<Boolean> updateUser(
    		@RequestParam(required = false) Integer userId,//用户ID
    		@RequestParam(required = false) String userName,//用户名
    		@RequestParam(required = false) Integer userStatus,//状态
    		@RequestParam(required = false) String loginId,//登录账号
    		@RequestParam(required = false) String userEmail,//邮箱
    		@RequestParam(required = false) String userPhone,//电话
    		@RequestParam(required = false) String userMobile) {//手机
	//public HttpJsonResult<Boolean> updateUser(SysUser user) {
		HttpJsonResult<Boolean> result = new HttpJsonResult<Boolean>();
		try {
/*			if (StringUtil.isEmpty(user.getPassword(), true)) {
				result.setMessage("密码不能为空");
				return result;
			}
			// 如果密码值等于屏蔽码，说明没有更改
			if (WebUtil.PASSWORD_MASK.equals(user.getPassword().trim()))
				user.setPassword(null);

			// 校验密码是否合法:长度至少8位,必须包含大小写字母和数字组合
			if (!StringUtils.isBlank(user.getPassword())) {
				if (!PasswordRegexCheckUtil.isPasswordCheckOK(user
						.getPassword())) {
					result.setMessage("密码不是数字大小写字母组合或长度不够8位");
					return result;
				}
			}*/
			SysUser user = new SysUser();
			user.setUserId(userId);
			user.setUserName(userName);
			user.setStatus(userStatus);
			user.setLoginId(loginId);
			user.setEmail(userEmail);
			user.setPhone(userPhone);
			user.setMobile(userMobile);
			user.setUpdateUser(getUserName());
			// TODO: 取当前操作用户ID
			ServiceResult<Boolean> srvResult = this.systemCenterService.updateUser(
					user);
			if (!srvResult.getSuccess()) {
				result.setMessage(result.getMessage());
				return result;
			}
			return result;
		} catch (Exception e) {
			log.error("更新用户异常", e);
			result.setMessage("更新用户异常：" + e.getMessage());
			return result;
		}
	}

	/**
	 * 获取当前登录的用户
	 */
	private String getUserName() {
		ServletRequestAttributes attr = (ServletRequestAttributes)
				RequestContextHolder.currentRequestAttributes();
		return (String) attr.getRequest().getSession().getAttribute("userName");
	}

	@RequestMapping(value = { "/create" }, method = { RequestMethod.POST })
	@ResponseBody
	public HttpJsonResult<SysUser> createUser(
			@RequestParam(required = false) String userName,//用户名
			@RequestParam(required = false) Integer userStatus,//状态
			@RequestParam(required = false) String loginId,//登录账号
			@RequestParam(required = false) String userEmail,//邮箱
			@RequestParam(required = false) String userPhone,//电话
			@RequestParam(required = false) String userMobile//手机
	) {
		try {
			// TODO: 取当前操作用户ID
//			ServiceResult<Integer> result = this.systemCenterService.createUser(user,
//					12);

            SysUser user = new SysUser();
            user.setUserName(userName);
            user.setStatus(userStatus);
            user.setLoginId(loginId);
            user.setEmail(userEmail);
            user.setPhone(userPhone);
            user.setMobile(userMobile);
            user.setPassword("");
			user.setCreateUser(getUserName());
			user.setUpdateUser(getUserName());
			ServiceResult<Integer> result = this.systemCenterService.createUser(user);
//			if (!result.getSuccess())
//				return new HttpJsonResult<SysUser>(result.getMessage());
			return new HttpJsonResult<SysUser>(result.getMessage());
//			return this.internalGetUser(result.getResult());
		} catch (Exception e) {
			log.error("创建用户异常", e);
			return new HttpJsonResult<SysUser>(e.getMessage());
		}
	}
	
	@RequestMapping(value = { "/get/{userId}" }, method = { RequestMethod.GET })
	@ResponseBody
	public void getRoleByUserId(@PathVariable Integer userId,
			HttpServletRequest request,HttpServletResponse response){
		ServiceResult<List<SysRole>> result = systemCenterService.getRoleByUserId(userId);
		try{
			if (result.getSuccess() && result.getResult() != null) {
				
				List<SysRole> rows = result.getResult();

				Map<String, Object> retMap = new HashMap<String, Object>();
				retMap.put("rows", rows);
				Gson gson = new Gson();
				response.addHeader("Content-type", "text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(retMap));
				response.getWriter().flush();
				response.getWriter().close();
			}
		}catch(Exception e){
			
		}
	}

	@ResponseBody
    @RequestMapping(value = { "/findToLoginId" }, method = { RequestMethod.POST })
	public void findToLoginId(@RequestParam(required = false) String loginId,HttpServletRequest request,HttpServletResponse response){
        SysUser sysUser = systemCenterService.getUserByLoginId(loginId).getResult();
        Map<String, Object> retMap = new HashMap<String, Object>();
        if (sysUser != null){
            retMap.put("message", "该用户已存在！不允许添加重复用户！");
        }
        Gson gson = new Gson();
        response.addHeader("Content-type", "text/html;charset=utf-8");
        try {
            response.getWriter().write(gson.toJson(retMap.get("message")));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}