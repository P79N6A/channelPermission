package com.haier.svc.api.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.system.model.PlanInDate;
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

@Controller
@RequestMapping("/sys/planInDate")
public class PlanInDateController {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(PlanInDateController.class);

	@Autowired
	SystemCenterService systemCenterService;

	@RequestMapping(value = { "toList" }, method = { RequestMethod.GET })
	public String toList(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> modelMap) {
		return "sys/planInDate/list";
	}

	/**
	 *
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = { "getName" }, method = { RequestMethod.GET })
	@ResponseBody
	HttpJsonResult<List<DataDictionary>> getName(HttpServletRequest request) {

		HttpJsonResult<List<DataDictionary>> result = new HttpJsonResult<List<DataDictionary>>();

		List<DataDictionary> list = new ArrayList<DataDictionary>();
		DataDictionary dict = new DataDictionary();
		dict.setValue("2");
		dict.setValue_meaning("T+2");
		list.add(dict);
		dict = new DataDictionary();
		dict.setValue("3");
		dict.setValue_meaning("T+3");
		list.add(dict);
		dict = new DataDictionary();
		dict.setValue("4");
		dict.setValue_meaning("T+4");
		list.add(dict);
		dict = new DataDictionary();
		dict.setValue("5");
		dict.setValue_meaning("T+5");
		list.add(dict);
		dict = new DataDictionary();
		dict.setValue("6");
		dict.setValue_meaning("T+6");
		list.add(dict);
		result.setData(list);
		return result;
	}

	@RequestMapping(value = { "/find" })
	@ResponseBody
	public void findUser(
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
			ServiceResult<List<PlanInDate>> result = systemCenterService
					.findPlanInDate(pager);
			if (result.getSuccess() && result.getResult() != null) {
				List<PlanInDate> resultList = result.getResult();
				Map<String, Object> retMap = new HashMap<String, Object>();
				retMap.put("total", result.getPager().getRowsCount());
				retMap.put("rows", resultList);
				Gson gson = new Gson();
				response.addHeader("Content-type", "text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(retMap));
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (Exception e) {
			log.error("查询列表异常", e);
		}

	}

//	private HttpJsonResult<SysUser> internalGetUser(Integer userId) {
//		try {
//			ServiceResult<SysUser> serviceResult = systemCenterService
//					.getUserById(userId);
//			if (!serviceResult.getSuccess())
//				return new HttpJsonResult<SysUser>(serviceResult.getMessage());
//
//			return new HttpJsonResult<SysUser>(serviceResult.getResult());
//		} catch (Exception e) {
//			log.error("获取数据异常:", e);
//			return new HttpJsonResult<SysUser>(e.getMessage());
//		}
//	}

	@RequestMapping(value = { "/update" }, method = { RequestMethod.POST })
	@ResponseBody
	public HttpJsonResult<Boolean> updatePlanInDate(
			@RequestParam(required = false) int id,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) Integer name) {
		HttpJsonResult<Boolean> result = new HttpJsonResult<Boolean>();
		try {
			PlanInDate planInDate = new PlanInDate();
			planInDate.setId(id);
			planInDate.setType(type);
			planInDate.setValue(name*7+"");
			planInDate.setName(name);

			int count = systemCenterService.updatePlanInDate(planInDate);
			if (count == 1) {
				result.setMessage("success");
				return result;
			}
			return result;
		} catch (Exception e) {
			log.error("更新异常", e);
			result.setMessage("更新异常：" + e.getMessage());
			return result;
		}
	}

//	@RequestMapping(value = { "/create" }, method = { RequestMethod.POST })
//	@ResponseBody
//	public HttpJsonResult<SysUser> createUser(SysUser user) {
//		try {
//			ServiceResult<Integer> result = this.systemCenterService
//					.createUser(user);
//			if (!result.getSuccess())
//				return new HttpJsonResult<SysUser>(result.getMessage());
//			return this.internalGetUser(result.getResult());
//		} catch (Exception e) {
//			log.error("创建用户异常", e);
//			return new HttpJsonResult<SysUser>(e.getMessage());
//		}
//	}

//	@RequestMapping(value = { "/get/{userId}" }, method = { RequestMethod.GET })
//	@ResponseBody
//	public void getRoleByUserId(@PathVariable Integer userId,
//			HttpServletRequest request, HttpServletResponse response) {
//		ServiceResult<List<SysRole>> result = systemCenterService
//				.getRoleByUserId(userId);
//		try {
//			if (result.getSuccess() && result.getResult() != null) {
//
//				List<SysRole> rows = result.getResult();
//
//				Map<String, Object> retMap = new HashMap<String, Object>();
//				retMap.put("rows", rows);
//				Gson gson = new Gson();
//				response.addHeader("Content-type", "text/html;charset=utf-8");
//				response.getWriter().write(gson.toJson(retMap));
//				response.getWriter().flush();
//				response.getWriter().close();
//			}
//		} catch (Exception e) {
//
//		}
//	}
}