package com.haier.svc.api.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.GsonBuilder;
import com.haier.purchase.data.model.*;
import com.haier.purchase.data.service.PurchaseProductLimitGateService;
import com.haier.purchase.data.service.PurchaseProductPaymentService;
import com.haier.stock.service.StockAgeService;
import com.haier.stock.service.StockPurchaseBaseCommonService;

import com.haier.svc.api.controller.util.*;
import com.haier.svc.service.*;
import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.velocity.app.VelocityEngine;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.Gson;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.ConvertUtil;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.ItemAttribute;
import com.haier.stock.model.InvRrsWarehouse;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvWarehouse;
import com.haier.svc.api.service.CommPurchase;
import com.haier.svc.api.service.T2OrderModel;

/**
 * Created by 黄俊 on 2014/7/14.
 */
@Controller
@Api(name = "订单模块", description = "T2OrderController")
@RequestMapping(value = "purchase/")
public class T2OrderController {
	private final static org.apache.log4j.Logger logger = LogManager
			.getLogger(T2OrderController.class);

    @RequestMapping(value = {"/InterfaceLog"})
    public String InterfaceLog(){
        return "job/purchaseLog";
    }


	// t+2填报导入模板表头信息
	private static final String CHECKSTR = "序号,渠道,产品组,库位码,物料号,型号,T+2周订单数量,定制,库存满足方式,订单类型,订单类别,参考单号(73单号),更改发货方向标识(Y)";
	// 3w签收导入模板表头信息
	private static final String CHECKSTR1 = "预约单号,85单号/J单号/K单号,3w库位,物料号,本次签收数量";
	// 定制 导入项 (0：否 1：是 )
	private static final String NONCUSTOMIZED = "0";
	private static final String CUSTOMIZED = "1";
	private static final String CUSTOMIZED_WORD = "是";
	@Autowired
	private SequenceService sequenceService;
	// 状态(5 :待内部审核)
	private static String FLOWFLAG_WAITFORREVIEW = "5";
	// T+2订单状态
	private static final String T2_ORDER_STATUS = "flow_flag";
	// 订单状态类别
	private static final String ORDER_CATEGORY = "order_category";
	// 是否区分
	private static final String TRUE_FALSE_DISTINCT = "true_or_false";
	// 订单类型
	private static final String ORDER_TYPE = "order_type";
	private static final String ORDER_TYPE_DAILY = "2";
	@Autowired
	VelocityEngine velocityEngine;
	// 库存满足方式（1：在库 2：补货）
	private static String SATISFY_TYPE = "satisfy_type ";

	// T+2默认的日日顺
	private static final String T2_DEFAULT_YES = "1";
	@Autowired
	T2OrderService t2OrderService;
	@Autowired
	private T2OrderModel t2OrderModel;
	@Autowired
	PurchaseCommonService purchaseCommonService;
	@Autowired
	GateService gateService;
	@Autowired
	DataDictionaryService dataDictionaryService;
	@Autowired
	PurchaseProductLimitGateService purchaseProductLimitGateService;
	@Autowired
	StockPurchaseBaseCommonService stockPurchaseBaseCommonService;
	@Autowired
	PurchaseProductPaymentService purchaseProductPaymentService;
	@Autowired
	LogAuditService logAuditService;
	@Autowired
	StockAgeService stockAgeService;
	@Autowired
	ItemService itemService;
	@Autowired
	CommPurchase commPurchase;

	/**
	 * T+2订单填报数据查询
	 *
	 * @param report_year_week
	 *            提报周
	 * @param channel
	 *            渠道
	 * @param product_group
	 *            产品组
	 * @param cbsCategory
	 *            品类
	 * @param order_id
	 *            订单号
	 * @param materials_id
	 *            物料号
	 * @param storage_id
	 *            库位
	 * @param flow_flag
	 *            状态
	 * @param rows
	 * @param page
	 * @param response
	 */
	@ApiMethod(description = "订单查询", summary = "dingdan")
	@ApiResponseObject
	@RequestMapping(value = { "findT2QueryCommitOrderList" }, method = { RequestMethod.GET })
	void findT2QueryCommitOrderList1(
			@ApiQueryParam(name = "report_year_week") @RequestParam(required = false) String report_year_week,
			@ApiQueryParam(name = "channel") @RequestParam(required = false) String channel,
			@ApiQueryParam(name = "product_group") @RequestParam(required = false) String product_group,
			@ApiQueryParam(name = "cbsCategory") @RequestParam(required = false) String cbsCategory,
			@ApiQueryParam(name = "order_id") @RequestParam(required = false) String order_id,
			@ApiQueryParam(name = "materials_id") @RequestParam(required = false) String materials_id,
			@ApiQueryParam(name = "storage_id") @RequestParam(required = false) String storage_id,
			@ApiQueryParam(name = "flow_flag") @RequestParam(required = false) String flow_flag,
			@ApiQueryParam(name = "order_category") @RequestParam(required = false) String order_category,
			@ApiQueryParam(name = "rows") @RequestParam(required = false) Integer rows,
			@ApiQueryParam(name = "page") @RequestParam(required = false) Integer page,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (rows == null)
				rows = 20;
			if (page == null)
				page = 1;
			// 权限Map
			Map<String, Object> authMap = new HashMap<String, Object>();
			// 取得产品组权限List和渠道权限List
			/*
			 * commPurchase.getAuthMap(purchaseCommonService, request,
			 * product_group, channel, cbsCategory, authMap);
			 */
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("report_year_week", report_year_week.replace("年", "")
					.replace("周", ""));
			params.put("ed_channel_id", authMap.get("channel"));
			params.put("product_group_id", authMap.get("productGroup"));
			// 品类
			params.put("cbsCategory", authMap.get("cbsCatgory"));
			Object a = authMap.get("channel");
			Object a1 = authMap.get("productGroup");
			Object a2 = authMap.get("cbsCatgory");
			params.put("order_id", order_id);
			params.put("materials_id", materials_id);
			params.put("storage_id", storage_id);
			// 订单类别
			params.put("order_category", order_category);
			// flow_flag转化为数组
			String[] flow_flag_list = null;
			if (flow_flag != null && !"".equals(flow_flag)) {
				flow_flag_list = flow_flag.split(",");
			}
			params.put("flow_flag", flow_flag_list);
			params.put("m", (page - 1) * rows);
			params.put("n", rows);

			// 渠道和产品组数据存入HashMap
			Map<String, String> productgroupmap = new HashMap<String, String>();// 产品组
			Map<String, String> invstockchannelmap = new HashMap<String, String>();// 渠道
			// 取得产品组
			/* commPurchase.getProductMap(productgroupmap, itemService); */
			productgroupmap = t2OrderService.getProductMap(productgroupmap);
			// 取得渠道
			/*
			 * commPurchase.getChannelMap(invstockchannelmap,
			 * stockCommonService);
			 */
			invstockchannelmap = t2OrderService
					.getChannelMapByCode(invstockchannelmap);
			// 获取订单类型map
			Map<String, String> orderTypeMap = t2OrderService
					.getValueMeaningMap(ORDER_TYPE);
			// 获取流程状态map
			Map<String, String> flowFlagMap = t2OrderService
					.getValueMeaningMap(T2_ORDER_STATUS);
			// 获取订单类别
			Map<String, String> orderCategoryMap = t2OrderService
					.getValueMeaningMap(ORDER_CATEGORY);
			// 调用业务Service
			ServiceResult<List<T2OrderItem>> result = t2OrderService
					.getT2OrderList(params);

			// //获得条数
			// ServiceResult<Integer> resultcount = t2OrderService.getRowCnts();
			if (result.getSuccess() && result.getResult() != null) {
				List<T2OrderItem> predictstocklist = result.getResult();
				for (T2OrderItem item : predictstocklist) {
					// 根据渠道id取得渠道名
					item.setEd_channel_name(invstockchannelmap.get(item
							.getEd_channel_id()));
					// 根据工作组id取得工作组名
					item.setProduct_group_name(productgroupmap.get(item
							.getProduct_group_id()));
					// 根据流程状态code取得流程状态名
					item.setFlow_flag_name(flowFlagMap.get(String.valueOf(item
							.getFlow_flag())));
					// 设置订单类型
					item.setOrder_type_name(orderTypeMap.get(String
							.valueOf(item.getOrder_type_id())));
					// 订单类别
					item.setOrder_category_name(orderCategoryMap.get(String
							.valueOf(item.getOrder_category())));
				}
				Map<String, Object> retMap = new HashMap<String, Object>();
				retMap.put("total", result.getPager().getRowsCount());
				retMap.put("rows", predictstocklist);
				Gson gson = new Gson();
				response.addHeader("Content-type", "text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(retMap));
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (IOException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 订单提交
	 *
	 * @param request
	 * @param commitData
	 *            提交数据
	 */

	/**
	 * 填报页面跳转
	 */
	@RequestMapping(value = { "toT2QueryCommitOrderList" }, method = { RequestMethod.GET })
	public String toT2QueryCommitOrderList(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String report_year_week,
			Map<String, Object> modelMap) {
		// 当前日期取得
		String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
		// 转化成周
		String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null,
				"1");
		modelMap.put("currentweek", currentWeek);
		modelMap.put("report_year_week", currentWeek);
		Map<String, Object> authMap = new HashMap<>();
		commPurchase.getAuthMap(request, "", "", "", authMap);
		try {
			modelMap.put("authMap", JSON.json(authMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "purchase/t2OrderList";
	}

	/**
	 * T+2订单填报数据查询
	 *
	 * @param report_year_week
	 *            提报周
	 * @param channel
	 *            渠道
	 * @param product_group
	 *            产品组
	 * @param cbsCategory
	 *            品类
	 * @param order_id
	 *            订单号
	 * @param materials_id
	 *            物料号
	 * @param storage_id
	 *            库位
	 * @param flow_flag
	 *            状态
	 * @param rows
	 * @param page
	 * @param response
	 */
	@ApiMethod(description = "订单查询", summary = "dingdan")
	@ApiResponseObject
	@RequestMapping(value = { "findT2QueryCommitOrderList" }, method = { RequestMethod.POST })
	void findT2QueryCommitOrderList(
			@ApiQueryParam(name = "report_year_week") @RequestParam(required = false) String report_year_week,
			@ApiQueryParam(name = "channel") @RequestParam(required = false) String channel,
			@ApiQueryParam(name = "product_group") @RequestParam(required = false) String product_group,
			@ApiQueryParam(name = "cbsCategory") @RequestParam(required = false) String cbsCategory,
			@ApiQueryParam(name = "order_id") @RequestParam(required = false) String order_id,
			@ApiQueryParam(name = "materials_id") @RequestParam(required = false) String materials_id,
			@ApiQueryParam(name = "storage_id") @RequestParam(required = false) String storage_id,
			@ApiQueryParam(name = "flow_flag") @RequestParam(required = false) String flow_flag,
			@ApiQueryParam(name = "order_category") @RequestParam(required = false) String order_category,
			@ApiQueryParam(name = "rows") @RequestParam(required = false) Integer rows,
			@ApiQueryParam(name = "page") @RequestParam(required = false) Integer page,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (rows == null)
				rows = 20;
			if (page == null)
				page = 1;
			// 权限Map
			Map<String, Object> authMap = new HashMap<String, Object>();
			// // 取得产品组权限List和渠道权限List
			 commPurchase.getAuthMap(request, product_group, channel, cbsCategory, authMap);
			// 渠道和产品组数据存入HashMap
			Map<String, String> productgroupmap = new HashMap<String, String>();
			// 取得产品组
			productgroupmap = t2OrderService.getProductMap(productgroupmap);
			Map<String, Object> params = new HashMap<String, Object>();
//			for (String key : productgroupmap.keySet()) {
//				if (cbsCategory.equals(productgroupmap.get(key))) {
//					// 品类
//					params.put("cbsCategory", key);
//					break;
//				}
//			}

//			params.put("cbsCategory", cbsCategory);
			params.put("cbsCategory", authMap.get("cbsCategory"));
			params.put("report_year_week", report_year_week.replace("年", "")
					.replace("周", ""));
			params.put("ed_channel_id", authMap.get("channel"));
//			params.put("product_group_id", product_group);
			params.put("product_group_id", authMap.get("productGroup"));

//			Object a = authMap.get("channel");
//			Object a1 = authMap.get("productGroup");
//			Object a2 = authMap.get("cbsCatgory");
			params.put("order_id", order_id);
			params.put("materials_id", materials_id);
			params.put("storage_id", storage_id);
			// 订单类别
			params.put("order_category", order_category);
			// flow_flag转化为数组
			String[] flow_flag_list = null;
			if (flow_flag != null && !"".equals(flow_flag)) {
				flow_flag_list = flow_flag.split(",");
			}
			params.put("flow_flag", flow_flag_list);
			params.put("m", (page - 1) * rows);
			params.put("n", rows);

			// 渠道和产品组数据存入HashMap
			// Map<String, String> productgroupmap = new HashMap<String,
			// String>();// 产品组
			Map<String, String> invstockchannelmap = new HashMap<String, String>();// 渠道
			// 取得产品组
			// commPurchase.getProductMap(productgroupmap, itemService);
			 productgroupmap = t2OrderService.getProductMap(productgroupmap);
			// 取得渠道
			// commPurchase.getChannelMap(invstockchannelmap,
			// stockCommonService);
			invstockchannelmap = t2OrderService
					.getChannelMapByCode(invstockchannelmap);
			// 获取订单类型map
			Map<String, String> orderTypeMap = t2OrderModel
					.getValueMeaningMap(ORDER_TYPE);
			// 获取流程状态map
			Map<String, String> flowFlagMap = t2OrderModel
					.getValueMeaningMap(T2_ORDER_STATUS);
			// 获取订单类别
			Map<String, String> orderCategoryMap = t2OrderModel
					.getValueMeaningMap(ORDER_CATEGORY);
			// 调用业务Service
			ServiceResult<List<T2OrderItem>> result = t2OrderModel
					.getT2OrderList(params);

			// //获得条数
			// ServiceResult<Integer> resultcount = t2OrderService.getRowCnts();
			if (result.getSuccess() && result.getResult() != null) {
				List<T2OrderItem> predictstocklist = result.getResult();
				for (T2OrderItem item : predictstocklist) {
					// 根据渠道id取得渠道名
					item.setEd_channel_name(invstockchannelmap.get(item
							.getEd_channel_id()));
					// 根据工作组id取得工作组名
					item.setProduct_group_name(productgroupmap.get(item
							.getProduct_group_id()));
					// 根据流程状态code取得流程状态名
					item.setFlow_flag_name(flowFlagMap.get(String.valueOf(item
							.getFlow_flag())));
					// 设置订单类型
					item.setOrder_type_name(orderTypeMap.get(String
							.valueOf(item.getOrder_type_id())));
					// 订单类别
					item.setOrder_category_name(orderCategoryMap.get(String
							.valueOf(item.getOrder_category())));
				}
				Map<String, Object> retMap = new HashMap<String, Object>();
				retMap.put("total", result.getPager().getRowsCount());
				retMap.put("rows", predictstocklist);
				Gson gson = new Gson();
				response.addHeader("Content-type", "text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(retMap));
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (IOException e) {
			logger.error("错误", e);
		}
	}

	/**
	 * 导入页面跳转
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = { "/importT2Order" }, method = { RequestMethod.GET })
	public ModelAndView importPurchaseOrder(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false) String report_year_week,
			Map<String, Object> modelMap, ModelAndView model) {
		model.addObject("report_year_week", report_year_week);
		String url = request.getHeader("referer");
		model.addObject("url", url);
		model.setViewName("purchase/importT2Order");
		return model;
	}

	/**
	 * t+2填报导入
	 */
	@RequestMapping(value = { "/importT2OrderData" }, method = { RequestMethod.POST })
	public @ResponseBody HttpJsonResult<Map<String, Object>> importPredictingStockData(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) String report_year_week,
			Map<String, Object> modelMap) {

		HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
		// 转型为MultipartHttpRequest
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		Set<String> notAllowedChannedlList = new HashSet<String>(){
			{
				add("商城渠道");
				add("顺逛O+O渠道");
				add("微店渠道");
			}
		};
		// 权限Map
		// Map<String, Object> authMap = new HashMap<String, Object>();
		// 取得产品组权限List和渠道权限List
		// commPurchase.getAuthMap(purchaseCommonService, request, null, null,
		// null, authMap);
		// 闸口不秒杀的场合，时间闸口check
		long totalStartTime = System.currentTimeMillis();
		List<T2OrderItem> t2OrderItems = new ArrayList<T2OrderItem>();
		// if ("0".equals(authMap.get("gateLimit"))) {
		// // 时间闸口check
		// if (!commPurchase.isInTimeGate(gateService, "05")) {
		// result.setMessage("不在渠道上报期间内");
		// return result;
		// }
		// }
		// 获得文件
		// List<MultipartFile> files = multipartRequest.getFiles("file");
		MultipartFile file = multipartRequest.getFile("file");
		if (file == null) {
			result.setMessage("没有选择导入文件，请选择导入文件后再点击导入操作！");
			return result;
		}
		// 警告信息集合
		String MsgList = "";
		StringBuffer sb = new StringBuffer();

		String fileName = file.getOriginalFilename();
		int index = fileName.lastIndexOf(".");
		String fileExtName = fileName.substring(index + 1);
		if (!fileExtName.equalsIgnoreCase("xls")) {
			result.setMessage("选择导入文件扩展名必须为xls!");
			return result;
		}
		int count = 0;
		int nullRow = 0;

		// 判断用 产品组code
		String department = "";
		// 判断用 产品组名称
		String departmentName = "";
		// 判断用 渠道code
		String channelCode = "";
		// 品牌
		String brandId = "";
		// 品类
		String categoryId = "";
		// 型号
		String materialDesc = "";
		try {
			List<String[]> list = ExcelReader.readExcel(file.getInputStream(),
					1);
			if (list.size() <= 1) {
				result.setMessage("很抱歉！你导入的Excel没有数据记录，请查看重新整理导入！");
				return result;
			}
			// 验证模板表头信息
			String[] firstLineData = list.get(0);

			boolean flag = this.checkDataTemplate(firstLineData, CHECKSTR);

			if (!flag) {
				result.setMessage("很抱歉！你导入的Excel数据格式与系统模板格式存在差异，请下载模板后重新导入！");
				return result;
			}
			// 提报周 逻辑主KEY
			String reportYearWeek = report_year_week;
			// 修改人 create_user
			String createUser = String
					.valueOf(WebUtil.currentUserName(request));

			// 品类数据存入HashMap
			Map<String, String> categoryMap = new HashMap<String, String>();

			// 渠道数据存入HashMap
			Map<String, String> invstockchannelmap = new HashMap<String, String>();

			// 调用stockCommonService，取得渠道数据
			invstockchannelmap = t2OrderService
					.getChannelMapByName(invstockchannelmap);

			// 库存满足方式list
			List<DataDictionary> satisfyTypeList = commPurchase
					.getByValueSetId(dataDictionaryService, "satisfy_type");

			// 订单类型编码list
			List<DataDictionary> orderTypeList = commPurchase.getByValueSetId(
					dataDictionaryService, "order_type");

			// 渠道和产品组数据存入HashMap
			Map<String, String> productgroupmap = new HashMap<String, String>();
			// 取得产品组
			productgroupmap = t2OrderService.getProductMap(productgroupmap);
			// 品类取得 根据产品组code
			commPurchase.getCategoryMap(categoryMap);

			Map<String, InvWarehouse> storageCache = new HashMap<String, InvWarehouse>();
			Map<String, List<InvRrsWarehouse>> estorageCache = new HashMap<String, List<InvRrsWarehouse>>();

			// 权限定义产品组取得
			// ServiceResult<PrivilegeItem> privilegeData =
			// purchaseCommonService.getPrivilege(String
			// .valueOf(WebUtil.currentUserId(request)));
			// 管理员角色编号
			Integer role = 102;// privilegeData.getResult().getRole_id();

			// 获取T+2上单数据, 获取T+2上单数量，下市闸口信息 闸口校验
			Date currentDate = new Date();
			Map<String, Object> limitGateparams = new HashMap<String, Object>();
			List<String> channeiList = new ArrayList<String>();
			// channeiList.addAll(Arrays.asList((String[])
			// authMap.get("channel")));
			channeiList.add("QQD");
			limitGateparams.put("channel_codeList", channeiList);
			// limitGateparams.put("product_groupList",
			// authMap.get("productGroup"));
			// limitGateparams.put("cbs_catgoryList",
			// authMap.get("cbsCatgory"));
			limitGateparams.put("currentDate", currentDate);
			limitGateparams.put("type", "1");
			ServiceResult<List<ProductLimitGate>> limitGateresult = new  ServiceResult<>();
					List<ProductLimitGate> limitGatelist = purchaseProductLimitGateService
					.getProductLimitGateList(limitGateparams);
			limitGateresult.setResult(limitGatelist);			
			// 读取数据

			Date dt=new Date();
			SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> paramtime = new HashMap<String, Object>();
			paramtime.put("createTime",matter1.format(dt)+" 00:00:00");
			// 订单流水号取得
			List<T2OrderItem> t2OrderItemList=t2OrderService.getT2WdOrderId(paramtime);

			if((t2OrderItemList==null||t2OrderItemList.isEmpty())){
				Map<String, Object> paramse = new HashMap<String, Object>();
				paramse.put("id","0");
				paramse.put("name","WPOrderId");
				sequenceService.clearSequence(paramse);
			}

			for (int i = 1; i < list.size(); i++) {
				long roundStart = System.currentTimeMillis();

				String[] str = list.get(i);
				// 渠道
				String edChannelName = StringUtil.nullSafeString(str[1]).trim();
				// 产品组
				String productGroupName = StringUtil.nullSafeString(str[2]
						.trim());
				// 库位码 导入项
				String storageId = StringUtil.nullSafeString(str[3]).trim();
				// 物料号 逻辑主KEY
				String materialsId = StringUtil.nullSafeString(str[4]).trim();
				// 型号
				String materialDescription = StringUtil.nullSafeString(str[5]
						.trim());
				// T+2周订单数量 导入项 (decimal)
				String t2DeliveryPrediction = StringUtil.nullSafeString(str[6]
						.trim());
				// 定制 导入项 (0：否 1：是)
				String customization = StringUtil.nullSafeString(str[7].trim());
				// 库存满足方式 导入项 (int)
				String satisfyType = StringUtil.nullSafeString(str[8].trim());
				// 订单类型编码 导入项 (int)
				String orderType = StringUtil.nullSafeString(str[9].trim());
				// 订单类别描述
				String order_category_name = StringUtil.nullSafeString(str[10]
						.trim());
				// 订单类别描述
				String order_num_73 = StringUtil.nullSafeString(str[11]
						.trim());
				// 订单类别描述
				String send_flag = StringUtil.nullSafeString(str[12]
						.trim());
				// 体积，款先直发订单类型所需内容
				// String volume=StringUtil.nullSafeString(str[11].trim());

				// 判断用 定制
				String customizationId = "";
				// 判断用 库存满足方式
				String satisfy_type_id = "";
				// 判断用 订单类型编码
				String order_type_id = "";
				// 库位名称
				String storageName = "";
				// 工贸编码
				String industry_trade_id = "";
				// 工贸描述
				String industry_trade_desc = "";
				// 管理客户编码
				String custom_id = "";
				// 管理客户描述
				String custom_desc = "";
				// 送达方编码
				String transmit_id = "";
				// 送达方描述
				String transmit_desc = "";
				// 配送中心编码
				String distribution_center_id = "";
				// 配送中心描述
				String distribution_center_desc = "";
				// 到货库位编码
				String arrival_storage_id = "";
				// 到货库位描述
				String arrival_storage_desc = "";
				// 电商付款方编码
				String payment_id = "";
				// 电商付款方名称
				String payment_name = "";
				// 单价
				BigDecimal price = new BigDecimal(0);
				// 金额
				BigDecimal amount = new BigDecimal(0);

				boolean isAllNull = StringUtil.isEmpty(edChannelName, true)
						&& StringUtil.isEmpty(productGroupName, true)
						&& StringUtil.isEmpty(storageId, true)
						&& StringUtil.isEmpty(materialsId, true)
						&& StringUtil.isEmpty(materialDescription, true)
						&& StringUtil.isEmpty(t2DeliveryPrediction, true)
						&& StringUtil.isEmpty(customization, true)
						&& StringUtil.isEmpty(satisfyType, true)
						&& StringUtil.isEmpty(orderType, true)
						&& StringUtil.isEmpty(order_category_name, true);
				if (isAllNull) {
					nullRow++;
					continue;
				}
				// 导入模板内容的非空判断****************START*******************************
				// 渠道名称的非空判断
				if (StringUtil.isEmpty(edChannelName, true)) {
					MsgList = "很抱歉！你导入的Excel数据,第" + i
							+ "行数据的【渠道】不能为空! 请核查后重新导入！";

					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br></br>";
						sb.append(MsgList);
					}
					continue;
				}
				
				if(notAllowedChannedlList.contains(edChannelName)){
					MsgList = "很抱歉！你导入的Excel数据,第" + i
							+ "行数据的【渠道】不能包含“商城渠道”，“顺逛O+O渠道”，“微店渠道”! ";

					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br></br>";
						sb.append(MsgList);
					}
					continue;
				}

				// 库位check
				if (StringUtil.isEmpty(storageId, true)) {
					MsgList = "很抱歉！你导入的Excel数据,第" + i
							+ "行数据的【库位码】不能为空! 请核查后重新导入！";
					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br></br>";
						sb.append(MsgList);
					}
					continue;
				}

				// 物料号check
				if (StringUtil.isEmpty(materialsId, true)) {
					MsgList = "很抱歉！你导入的Excel数据,第" + i
							+ "行数据的【物料号】不能为空! 请核查后重新导入！";
					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br></br>";
						sb.append(MsgList);
					}
					continue;
				}
				// 导入模板内容的非空判断****************END*********************************

				// 导入模板内容的合理性判断及权限判断**************START**********************
				// 用渠道名称取得渠道code及渠道的正确性判断
				channelCode = invstockchannelmap.get(edChannelName);
				// 渠道的正确性判断
				if ("".equals(channelCode) || channelCode == null) {
					MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的渠道【"
							+ edChannelName + "】无法识别！请检查后重新导入！";
					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br></br>";
						sb.append(MsgList);
					}
					continue;
				}

				// 产品组code取得
				department = commPurchase
						.getDepartmentByMaterialCode(materialsId);
				// 判断用 产品组名称

				departmentName = productgroupmap.get(department);
				// 产品组名称判断，为空则用根据物料号取得的departmentName，否则要与取得的departmentName相同
				if (!StringUtil.isEmpty(productGroupName, true)
						&& !productGroupName.equals(departmentName)) {
					MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的产品组【"
							+ productGroupName + "】不正确! 请核查后重新导入！";
					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br></br>";
						sb.append(MsgList);
					}
					continue;
				}
				// 库位的合理性判断**************START**********************************************
				// 通过电商库位码获取仓库
				InvWarehouse invW = storageCache.get(storageId);
				if (invW == null) {
					ServiceResult<InvWarehouse> invWarehouseResult = stockPurchaseBaseCommonService
							.getWhByEstorgeId(storageId);
					if (invWarehouseResult.getSuccess()
							&& invWarehouseResult.getResult() != null) {
						storageCache.put(storageId,
								invWarehouseResult.getResult());
					}
					invW = invWarehouseResult.getResult();
				}
				if (invW != null) {
					// 库位名称
					storageName = invW.getEstorge_name();
					// 工贸编码
					industry_trade_id = invW.getIndustry_trade_id();
					// 工贸描述
					industry_trade_desc = invW.getIndustry_trade_desc();
					// 管理客户编码
					custom_id = invW.getCustom_id();
					// 管理客户描述
					custom_desc = invW.getCustom_desc();
					// 送达方编码
					transmit_id = invW.getTransmit_id();
					// 送达方描述
					transmit_desc = invW.getTransmit_desc();
					// 配送中心编码
					distribution_center_id = invW.getRrs_distribution_id();
					// 配送中心描述
					distribution_center_desc = invW.getRrs_distribution_name();

					// 电商付款方编码
					// payment_id = invW.getPayment_id();
					// 电商付款方名称
					// payment_name = invW.getPayment_name();
				} else {
					MsgList = "很抱歉！你导入的Excel数据,第" + i
							+ "行数据的【库位码】无法识别! 请核查后重新导入！";
					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br></br>";
						sb.append(MsgList);
					}
					continue;
				}

				// 库位的合理性判断**************END**********************************************
				// 物料号可识别性判断
				if ("".equals(department) || department == null) {
					MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的物料号【"
							+ materialsId + "】无法识别！请检查后重新导入！";
					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br></br>";
						sb.append(MsgList);
					}
					continue;
				} else {
					/*********************** 付款方获取开始（xieli），物料号获取产品组名称，产品组名称得到付款方 ***************************/

					// 海华要求如果渠道是商城和顺逛custCode设置为C200130028 lupeng 2017/03/01
					if (StringUtils.isNotEmpty(channelCode)
							&& (channelCode.equalsIgnoreCase("SC") || channelCode
									.equalsIgnoreCase("RS"))) {
						payment_name = "海尔集团电子商务有限公司(顺逛全产业)";
						payment_id = "C200130028";
					} else {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("productName", departmentName);
						List<ProductPayment> ppList = purchaseProductPaymentService
								.findPaymentNameByCode(params);
						if (ppList.size() > 0) {
							payment_name = ppList.get(0).getPayMentName();
							payment_id = ppList.get(0).getPayMentCode();
						}
					}
					/*********************** 付款方获取结束（xieli） ***************************/
				}
				// 品类
				categoryId = categoryMap.get(department);

				// 型号正确性判断materialDescription
				// 从品牌型号Map，取得型号
				Map<String, String> itemBaseMap = commPurchase
						.getItemBaseMap(materialsId);
				materialDesc = itemBaseMap.get("material_description");
				// 型号非空时，正确性判断
				// 型号判断，为空则用根据物料号取得的materialDesc，否则要与取得的materialDesc相同
				if (!StringUtil.isEmpty(materialDescription, true)
						&& !materialDesc.equals(materialDescription)) {
					MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的型号【"
							+ materialDescription + "】不正确! 请核查后重新导入！";
					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br></br>";
						sb.append(MsgList);
					}
					continue;
				}

				// 导入模板内容的数值形式判断**************START*****************************
				// T+2周订单数量正确性判断
				if (StringUtil.isEmpty(t2DeliveryPrediction, true)
						|| !CommUtil.canToInt(t2DeliveryPrediction)
						|| Integer.parseInt(t2DeliveryPrediction) <= 0
						|| Integer.parseInt(t2DeliveryPrediction) > 200) {
					MsgList = "很抱歉！你导入的Excel数据,第" + i
							+ "行数据的T+2周订单数量应该是大于零且小于等于200的整数! 请核查后重新导入！";
					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br></br>";
						sb.append(MsgList);
					}
					continue;
				}

				// 定制，非【是】即【否】
				if (CUSTOMIZED_WORD.equals(customization)) {
					customizationId = CUSTOMIZED;
				} else {
					customizationId = NONCUSTOMIZED;
				}

				// 库存满足方式非空时，文言的正确性判断
				if (!StringUtil.isEmpty(satisfyType, true)) {
					for (DataDictionary data : satisfyTypeList) {
						if (data.getValue_meaning() != null
								&& data.getValue_meaning().equals(
										String.valueOf(satisfyType))) {
							satisfy_type_id = data.getValue();
							break;
						}
					}
					// 库存满足方式文言在数据字典中存在判断
					if (StringUtil.isEmpty(satisfy_type_id, true)) {

						MsgList = "很抱歉！你导入的Excel数据,第" + i
								+ "行数据的库存满足方式数据不合法! 请核查后重新导入！";
						if (StringUtil.isEmpty(MsgList, true)) {
							sb.append(MsgList);
						} else {
							MsgList = MsgList + "<br></br>";
							sb.append(MsgList);
						}
						continue;
					}
				}

				// 订单类型编码非空时，文言的正确性判断
				if (!StringUtil.isEmpty(orderType, true)) {
					for (DataDictionary data : orderTypeList) {
						if (data.getValue_meaning() != null
								&& data.getValue_meaning().equals(
										String.valueOf(orderType))) {
							order_type_id = data.getValue();
							break;
						}
					}
					// 订单类型编码文言在数据字典中存在判断
					if (StringUtil.isEmpty(order_type_id, true)) {
						MsgList = "很抱歉！你导入的Excel数据,第" + i
								+ "行数据的订单类型编码数据不合法! 请核查后重新导入！";
						if (StringUtil.isEmpty(MsgList, true)) {
							sb.append(MsgList);
						} else {
							MsgList = MsgList + "<br></br>";
							sb.append(MsgList);
						}
						continue;
					}
				}

				// 导入模板内容的数值形式判断**************END*******************************

				// String[] authChannel = (String[]) authMap.get("channel");
				// String[] authproductGroup = (String[])
				// authMap.get("productGroup");
				// List<String> channelList = Arrays.asList(authChannel);
				// List<String> productGroupList =
				// Arrays.asList(authproductGroup);
				// 判断渠道id是否有权限
				// if (!channelList.contains(channelCode)) {
				// MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的渠道【" + edChannelName
				// + "】，你没有权限导入！请检查后重新导入！";
				// if (StringUtil.isEmpty(MsgList, true)) {
				// sb.append(MsgList);
				// } else {
				// MsgList = MsgList + "<br></br>";
				// sb.append(MsgList);
				// }
				// continue;
				// }

				// 判断产品组id是否有权限
				// if (!productGroupList.contains(department)) {
				// if (productGroupName != null && !"".equals(productGroupName))
				// {
				// MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的物料号【" + materialsId
				// + "】对应的产品组【"
				// + productGroupName + "】，你没有权限导入! 请核查后重新导入！";
				// } else {
				// MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的物料号【" + materialsId
				// + "】对应的产品组ID【"
				// + department + "】，你没有权限导入! 请核查后重新导入！";
				// }
				//
				// if (StringUtil.isEmpty(MsgList, true)) {
				// sb.append(MsgList);
				// } else {
				// MsgList = MsgList + "<br></br>";
				// sb.append(MsgList);
				// }
				// continue;
				// }
				// 判断产品组和满足方式关联
				if ("FD,LA,LB,LC".contains(department)
						&& !"1".equals(satisfy_type_id)
						&& !"2".equals(satisfy_type_id)) {
					MsgList = "很抱歉！你导入的Excel数据,第"
							+ i
							+ "行数据的产品组因为是微波炉（FD），厨房类家电（LA），环境类家电（LB），两季产品（LC），满足方式必须填写【在库】或【补货】。 请核查后重新导入！";

					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br></br>";
						sb.append(MsgList);
					}
					continue;
				}
				// 订单类别导入
				// 订单类别对应编号
				String order_category = "";
				// 库存满足方式list
				List<DataDictionary> order_category_list = commPurchase
						.getByValueSetId(dataDictionaryService,
								"order_category");
				// 如果导入数据中订单类别为空，则默认是t+2订单，order_category 为 0
				if ("".equals(order_category_name)) {
					for (DataDictionary data : order_category_list) {
						if ("t+2订单".equals(data.getValue_meaning())) {
							order_category = data.getValue();
						}
					}
				} else {
					for (DataDictionary data : order_category_list) {
						if (order_category_name.equalsIgnoreCase(data
								.getValue_meaning())) {
							order_category = data.getValue();
						}

					}
					if ("".equals(order_category)) {
						MsgList = "很抱歉！你导入的Excel数据,第" + i
								+ "行数据的订单类别有误! 请核查后重新导入！";
						if (StringUtil.isEmpty(MsgList, true)) {
							sb.append(MsgList);
						} else {
							MsgList = MsgList + "<br></br>";
							sb.append(MsgList);
						}
						continue;
					}
				}

				// 通过电商库位码获取日日顺仓库List
				List<InvRrsWarehouse> l_estorage = estorageCache.get(storageId);
				if (l_estorage == null || l_estorage.size() == 0) {
					Map<String, Object> invRrsWarehouseParams = new HashMap<String, Object>();
					invRrsWarehouseParams.put("estorge_id", storageId);
					invRrsWarehouseParams.put("t2_default", T2_DEFAULT_YES);
					ServiceResult<List<InvRrsWarehouse>> invRrsWarehouseResult = stockPurchaseBaseCommonService
							.getRrsWhByEstorgeId(invRrsWarehouseParams);
					logger.info("通过电商库位码获取日日顺仓库List结果：" + invRrsWarehouseResult.getMessage());
					if (invRrsWarehouseResult.getSuccess()
							&& invRrsWarehouseResult.getResult() != null
							&& invRrsWarehouseResult.getResult().size() > 0) {
						estorageCache.put(storageId,
								invRrsWarehouseResult.getResult());
						l_estorage = invRrsWarehouseResult.getResult();
					}
				}

				if (l_estorage != null && l_estorage.size() > 0) {
					// 到货库位编码
					arrival_storage_id = l_estorage.get(0).getRrs_wh_code();
					// 到货库位描述
					arrival_storage_desc = l_estorage.get(0).getRrs_wh_name();
				}
				// 管理员忽略闸口
				if (role != 102) {
					// T+2订单数量，上市闸口验证
					if (importCheckLimatGate(limitGateresult, channelCode,
							department, categoryId, materialsId,
							Integer.parseInt(t2DeliveryPrediction))) {
						MsgList = "很抱歉！你导入的Excel数据,第" + i
								+ "行数据的T+2周订单数量大于上市闸口数量! 请核查后重新导入！";
						if (StringUtil.isEmpty(MsgList, true)) {
							sb.append(MsgList);
						} else {
							MsgList = MsgList + "<br></br>";
							sb.append(MsgList);
						}
						continue;
					}
				}
				// 款先直发订单校验,若为款先直发订单类型，则必须满足渠道为天猫，并且产品组为非冰箱
				/*
				 * if("1".equals(order_category)){//0，t+2订单，1.款先直发订单，3.机壳不结算订单
				 * if(!"TB".equals(channelCode)){ MsgList = "很抱歉！你导入的Excel数据,第"
				 * + i + "行数据的渠道必须为天猫渠道！"; if (StringUtil.isEmpty(MsgList,
				 * true)) { sb.append(MsgList); } else { MsgList = MsgList +
				 * "<br></br>"; sb.append(MsgList); } continue; }else{
				 * 
				 * } if("冰箱".equals(departmentName)){ MsgList =
				 * "很抱歉！你导入的Excel数据,第" + i + "行数据的物料号【" + materialsId +
				 * "】对应的产品组【" + productGroupName + "】必须为非冰箱"; if
				 * (StringUtil.isEmpty(MsgList, true)) { sb.append(MsgList); }
				 * else { MsgList = MsgList + "<br></br>"; sb.append(MsgList); }
				 * continue; } }
				 */
				// 导入模板内容的合理性判断及权限判断**************END************************

				// 导入模板内容不足，其他项取得**************START***************************

				// 从品牌型号Map，取品牌code
				brandId = itemBaseMap.get("brand_code");

				// 取得样表价格
				price = new BigDecimal(itemBaseMap.get("price"));

				// 计算金额
				if (null != ConvertUtil.toDecimal(t2DeliveryPrediction, null)) {
					amount = price.multiply(ConvertUtil.toDecimal(
							t2DeliveryPrediction, null));
				}

				// 导入模板内容不足，其他项取得**************END*****************************

				T2OrderItem t2OrderItem = new T2OrderItem();

				// 订单号取得
				if ("FD,LA,LB,LC".contains(department)) {
					// 在库的场合
					if ("1".equals(satisfy_type_id)) {
						t2OrderItem.setOrder_id(commPurchase.getT2OrderId(
								purchaseCommonService, "C99"));
						// 补货的场合
					} else if ("2".equals(satisfy_type_id)) {
						t2OrderItem.setOrder_id(commPurchase.getT2OrderId(
								purchaseCommonService, "C98"));
					}
				} else {
					if ("1".equals(order_category)) {
						t2OrderItem.setOrder_id(commPurchase.getT2OrderId(
								purchaseCommonService, "C10"));
						// 款先直发订单要在3w表中插入一条数据
						/*
						 * CrmOrderItem crmOrderItem=new CrmOrderItem();
						 * crmOrderItem
						 * .setWp_order_id(commPurchase.getWPOrderId(
						 * purchaseCommonService,"C10"));
						 * t2OrderService.insert3wOrder(crmOrderItem);
						 */
					} else if ("2".equals(order_category)) {
						t2OrderItem.setOrder_id(commPurchase.getT2OrderId(
								purchaseCommonService, "C11"));
					} else {
						t2OrderItem.setOrder_id(commPurchase.getT2OrderId(
								purchaseCommonService, "C01"));
					}
				}

				// 模板提供项
				t2OrderItem.setEd_channel_id(channelCode);
				t2OrderItem.setEd_channel_name(edChannelName);
				t2OrderItem.setProduct_group_id(department);
				t2OrderItem.setStorage_id(storageId);
				t2OrderItem.setStorage_name(storageName);
				t2OrderItem.setMaterials_id(materialsId);
				t2OrderItem.setMaterials_desc(materialDesc);
				t2OrderItem.setT2_delivery_prediction(ConvertUtil.toDecimal(
						t2DeliveryPrediction, null));
				t2OrderItem.setCustomization(Integer.parseInt(customizationId));
				t2OrderItem.setOrder_category(Integer.valueOf(order_category));
				if (!StringUtil.isEmpty(satisfy_type_id, true)) {
					t2OrderItem.setSatisfy_type_id(Integer
							.parseInt(satisfy_type_id));
				}
				if (!StringUtil.isEmpty(order_type_id, true)) {
					t2OrderItem.setOrder_type_id(Integer
							.parseInt(order_type_id));
				} else {
					// 订单类型导入时，默认为“日常”
					t2OrderItem.setOrder_type_id(Integer
							.parseInt(ORDER_TYPE_DAILY));
				}
				// 模板之外插入项
				t2OrderItem.setCreate_user(createUser);
				t2OrderItem.setBrand_id(brandId);
				t2OrderItem.setCategory_id(categoryId);
				t2OrderItem.setReport_year_week(reportYearWeek);
				// 工贸编码
				t2OrderItem.setIndustry_trade_id(industry_trade_id);
				// 工贸描述
				t2OrderItem.setIndustry_trade_desc(industry_trade_desc);
				// 管理客户编码
				t2OrderItem.setCustom_id(custom_id);
				// 管理客户描述
				t2OrderItem.setCustom_desc(custom_desc);
				// 送达方编码
				t2OrderItem.setTransmit_id(transmit_id);
				// 送达方描述
				t2OrderItem.setTransmit_desc(transmit_desc);
				// 配送中心编码
				t2OrderItem.setDistribution_center_id(distribution_center_id);
				// 配送中心描述
				t2OrderItem
						.setDistribution_center_desc(distribution_center_desc);
				// 到货库位编码
				t2OrderItem.setArrival_storage_id(arrival_storage_id);
				// 到货库位描述
				t2OrderItem.setArrival_storage_desc(arrival_storage_desc);

				// 电商付款方编码
				t2OrderItem.setPayment_id(payment_id);
				// 电商付款方名称
				t2OrderItem.setPayment_name(payment_name);
				// 体积
				// t2OrderItem.setVolume(volume);

				// 单价
				if (null != String.valueOf(price)
						&& !"".equals(String.valueOf(price))
						&& !"0".equals(String.valueOf(price))) {
					t2OrderItem.setPrice(price);
				}
				// 金额
				if (null != String.valueOf(amount)
						&& !"".equals(String.valueOf(amount))
						&& !"0".equals(String.valueOf(amount))) {
					t2OrderItem.setAmount(amount);
				}

				t2OrderItem.setSend_flag(send_flag);
				t2OrderItem.setOrder_num_73(order_num_73);
				t2OrderItems.add(t2OrderItem);
				// 创建T+2订单信息表单
				// ServiceResult<Boolean> insResult =
				// t2OrderService.insertT2Order(t2OrderItem);

				// if (insResult.getSuccess() && insResult.getResult()) {
				// count++;
				// } else {
				// MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据插入DB时失败！";
				// if (StringUtil.isEmpty(MsgList, true)) {
				// sb.append(MsgList);
				// } else {
				// MsgList = MsgList + "<br></br>";
				// sb.append(MsgList);
				// }
				// continue;
				// }
			}

			long startTime = System.currentTimeMillis();
			ServiceResult<Map<String, Integer>> insResult = t2OrderModel
					.insertT2Order(t2OrderItems);
			long endTime = System.currentTimeMillis();
			long totalEndTime = System.currentTimeMillis();

			int success = 0;
			if (insResult.getResult() != null) {
				success = insResult.getResult().get("success");
			}
			modelMap.put("total", list.size() - 1 - nullRow);
			modelMap.put("ignore", list.size() - success - 1 - nullRow);
			modelMap.put("success", success);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("文件导入数据库失败", e);
			result.setMessage("很抱歉！你导入的Excel数据导入失败，请联系技术负责人！");
			return result;
		}

		modelMap.put("warn", sb.toString());
		result.setData(modelMap);
		return result;
	}

	/**
	 * T+2上单数量校验
	 *
	 * @param limitGateresult
	 * @param channelCode
	 * @param department
	 * @param categoryId
	 * @param materialsId
	 * @param t2_num
	 * @return
	 */

	private boolean importCheckLimatGate(
			ServiceResult<List<ProductLimitGate>> limitGateresult,
			String channelCode, String department, String categoryId,
			String materialsId, int t2_num) {
		boolean result = false;
		int limitGatecount = 0;
		if (limitGateresult.getSuccess()) {
			List<ProductLimitGate> materialsList = new ArrayList<ProductLimitGate>();
			List<ProductLimitGate> materialsListQQD = new ArrayList<ProductLimitGate>();
			List<ProductLimitGate> productGroupList = new ArrayList<ProductLimitGate>();
			List<ProductLimitGate> productGroupListQQD = new ArrayList<ProductLimitGate>();
			List<ProductLimitGate> cbsCatgoryList = new ArrayList<ProductLimitGate>();
			List<ProductLimitGate> cbsCatgoryListQQD = new ArrayList<ProductLimitGate>();

			for (ProductLimitGate productLimitGate : limitGateresult
					.getResult()) {
				String materials = productLimitGate.getModel_type();
				String product_group = productLimitGate.getGroup_type();
				String cbs_catgory = productLimitGate.getProduct_type();
				if ("QQD".equals(productLimitGate.getChannel_code())) {
					// 取所有物料闸口信息
					if (materials != null && product_group == null
							&& cbs_catgory == null) {
						materialsListQQD.add(productLimitGate);
					}
					// 取所有产品组闸口信息
					if (materials == null && product_group != null
							&& cbs_catgory == null) {
						productGroupListQQD.add(productLimitGate);
					}
					// 取所有品类闸口信息
					if (materials == null && product_group == null
							&& cbs_catgory != null) {
						cbsCatgoryListQQD.add(productLimitGate);
					}
				} else {
					// 取所有物料闸口信息
					if (materials != null && product_group == null
							&& cbs_catgory == null) {
						materialsList.add(productLimitGate);
					}
					// 取所有产品组闸口信息
					if (materials == null && product_group != null
							&& cbs_catgory == null) {
						productGroupList.add(productLimitGate);
					}
					// 取所有品类闸口信息
					if (materials == null && product_group == null
							&& cbs_catgory != null) {
						cbsCatgoryList.add(productLimitGate);
					}
				}
			}
			// 闸口校验
			if (!materialsList.isEmpty()) {
				for (ProductLimitGate materials : materialsList) {
					if (channelCode.equals(materials.getChannel_code())) {
						if (materialsId.equals(materials.getModel_type())) {
							return t2_num > materials.getOrder_num();
						}
					}
				}
			}
			if (!materialsListQQD.isEmpty()) {
				for (ProductLimitGate materialsQQD : materialsListQQD) {
					if (materialsId.equals(materialsQQD.getModel_type())) {
						return t2_num > materialsQQD.getOrder_num();
					}
				}
			}
			if (!productGroupList.isEmpty()) {
				for (ProductLimitGate productGroup : productGroupList) {
					if (channelCode.equals(productGroup.getChannel_code())) {
						if (department.equals(productGroup.getGroup_type())) {
							return t2_num > productGroup.getOrder_num();
						}
					}
				}
			}
			if (!productGroupListQQD.isEmpty()) {
				for (ProductLimitGate productGroupQQD : productGroupListQQD) {
					if (department.equals(productGroupQQD.getGroup_type())) {
						return t2_num > productGroupQQD.getOrder_num();
					}
				}
			}
			if (!cbsCatgoryList.isEmpty()) {
				for (ProductLimitGate cbsCatgory : cbsCatgoryList) {
					if (channelCode.equals(cbsCatgory.getChannel_code())) {
						if (categoryId.equals(cbsCatgory.getProduct_type())) {
							return t2_num > cbsCatgory.getOrder_num();
						}
					}
				}
			}
			if (!cbsCatgoryListQQD.isEmpty()) {
				for (ProductLimitGate cbsCatgoryQQD : cbsCatgoryListQQD) {
					if (categoryId.equals(cbsCatgoryQQD.getProduct_type())) {
						return t2_num > cbsCatgoryQQD.getOrder_num();
					}
				}
			}
		}
		return false;
	}

	/**
	 * 判断导入文档表头是否正确
	 *
	 * @param firstLineData
	 * @param checkStr
	 * @return
	 */
	public boolean checkDataTemplate(String[] firstLineData, String checkStr) {
		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		for (String str : firstLineData) {
			if (sb.length() > 0)
				sb.append(",");
			sb.append(str.trim());
		}
		String nullStr = sb.toString().replace(checkStr, "").replace(",", "");
		if (nullStr == null || "".equals(nullStr)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 订单提交
	 * 
	 * @param request
	 * @param commitData
	 * @return
	 */
	@RequestMapping(value = { "commitOrderList" }, method = { RequestMethod.POST })
	@ResponseBody
	public HttpJsonResult<String> commitOrderList(HttpServletRequest request,
			@RequestParam(required = true) String commitData) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();
		if (commitData != null) {
			try {
				// 取得提交数据
				JSONArray commitjson = (JSONArray) JSON.parse(commitData);
				List<String> commitList = new ArrayList<String>();
				// JSONArray转化为list
				for (int i = 0; i < commitjson.length(); i++) {
					commitList.add(commitjson.getString(i));
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("order_list", commitList);
				// 提交处理调用
				String errMsg = commitOrder(request, params);

				if (errMsg != null) {
					result.setData(errMsg);
				}

			} catch (ParseException e) {
				logger.error("", e);
				throw new BusinessException("JSON转化失败" + e.getMessage());
			}

		}
		result.setMessage("提交成功");
		return result;
	}

	/**
	 * 订单全部提交
	 *
	 * @param request
	 * @param report_year_week
	 *            提报周
	 * @param channel
	 *            渠道
	 * @param product_group
	 *            产品组
	 * @param cbsCategory
	 *            品类
	 * @param order_id
	 *            订单号
	 * @param materials_id
	 *            物料号
	 * @param storage_id
	 *            库位
	 * @param flow_flag
	 *            状态
	 * @return
	 */
	@RequestMapping(value = { "commitAllOrderList" }, method = { RequestMethod.POST })
	@ResponseBody
	public HttpJsonResult<String> commitAllOrderList(
			HttpServletRequest request,
			@RequestParam(required = false) String report_year_week,
			@RequestParam(required = false) String channel,
			@RequestParam(required = false) String product_group,
			@RequestParam(required = false) String cbsCategory,
			@RequestParam(required = false) String order_id,
			@RequestParam(required = false) String materials_id,
			@RequestParam(required = false) String storage_id,
			@RequestParam(required = false) String flow_flag) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();

		Map<String, Object> params = new HashMap<String, Object>();
		// 权限Map
		Map<String, Object> authMap = new HashMap<String, Object>();

		// 取得产品组权限List和渠道权限List
		 commPurchase.getAuthMap(request,
		 product_group,
		 channel, cbsCategory, authMap);
		// 提报周
		params.put("report_year_week", report_year_week.replace("年", "")
				.replace("周", ""));
		// 渠道
		params.put("ed_channel_id", authMap.get("channel"));
		// 产品组
		params.put("product_group_id", authMap.get("productGroup"));
		// 品类
		params.put("cbsCategory", authMap.get("cbsCategory"));
		// 订单号
		params.put("order_id", order_id);
		// 物料号
		params.put("materials_id", materials_id);
		// 库位
		params.put("storage_id", storage_id);
		// 状态
		params.put("flow_flag", new String[] { "0" });
		String errMsg = commitOrder(request, params);
		if (errMsg != null) {
			result.setData(errMsg);
		}

		result.setMessage("提交成功");
		return result;

	}

	/**
	 * 提交处理
	 *
	 * @param request
	 * @param params
	 * @return
	 */
	private String commitOrder(HttpServletRequest request,
			Map<String, Object> params) {
		// 权限定义产品组取得
		ServiceResult<PrivilegeItem> privilegeData = purchaseCommonService
				.getPrivilege(String.valueOf(WebUtil.currentUserId(request)));
		// 管理员角色编号
		// Integer role = privilegeData.getResult().getRole_id();

		String T2_report_year_week = "";
		String T3_report_year_week = "";
		// T+3提报周和T+2提报周三取得
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		T2_report_year_week = CommUtil.getWeekOfYear_Sunday(
				sdf.format(cal.getTime()), null, "0");
		cal.add(Calendar.DATE, -7);
		T3_report_year_week = CommUtil.getWeekOfYear_Sunday(
				sdf.format(cal.getTime()), null, "0");
		// 权限Map
		// Map<String, Object> authMap = new HashMap<String, Object>();
		// 取得产品组权限List和渠道权限List
		// commPurchase.getAuthMap(purchaseCommonService, request, null, null,
		// null, authMap);
		// 闸口不秒杀的场合，时间闸口check
		// if ("0".equals(authMap.get("gateLimit"))) {
		// // 时间闸口check
		// if (!commPurchase.isInTimeGate(gateService, "05")) {
		// return "不在渠道上报期间内";
		// }
		// }
		// 调用业务Service取得所有订单号
		ServiceResult<List<T2OrderItem>> queryresult = t2OrderService
				.getT2OrderList(params);
		List<T2OrderItem> t2Orderlist = queryresult.getResult();
		// 取得提交者
		String commituser = "";
		Object cu = request.getSession().getAttribute("userName");
		if(cu != null){
			commituser = cu.toString();
		}
		// 价格确认

		// 获取下市闸口信息
		Map<String, Object> limitGateparams = new HashMap<String, Object>();
		List<String> channelList = new ArrayList<String>();
		// channelList.addAll(Arrays.asList((String[]) authMap.get("channel")));
		channelList.add("QQD");
		limitGateparams.put("channel_codeList", channelList);
		// limitGateparams.put("product_groupList",
		// authMap.get("productGroup"));
		// limitGateparams.put("cbs_catgoryList", authMap.get("cbsCatgory"));
		limitGateparams.put("currentDate", currentDate);
		limitGateparams.put("type", "0");

		ServiceResult<List<ProductLimitGate>> limitGateresult = new ServiceResult<List<ProductLimitGate>>();
		List<ProductLimitGate> limitGateList = purchaseProductLimitGateService
				.getProductLimitGateList(limitGateparams);
		limitGateresult.setResult(limitGateList);
		for (T2OrderItem t2Order : t2Orderlist) {
			// 闸口不秒杀的场合，超期check

			// if ("0".equals(authMap.get("gateLimit"))) {
			//
			// // 超期闸口check
			// String errMsg = commPurchase.isInStockExceedGate(gateService,
			// t2Order.getEd_channel_id(),
			// t2Order.getMaterials_desc(), t2Order.getCategory_id(),
			// t2Order.getBrand_id(), t2Order.getStorage_id(),
			// t2Order.getMaterials_id());
			// if (errMsg != null) {
			// t2Order.setError_msg(errMsg);
			// t2OrderService.updateCheckError(t2Order);
			// continue;
			// }
			// }
			if (t2Order.getPrice() == null) { // 从Map，取得价格
				Map<String, String> itemBaseMap = commPurchase
						.getItemBaseMap(t2Order.getMaterials_id());
				if ("0".equals(itemBaseMap.get("price"))) {
					t2Order.setError_msg("请维护这个物料的价格情报！");
					t2OrderService.updateCheckError(t2Order);
					continue;
				} else {
					t2Order.setPrice(new BigDecimal(itemBaseMap.get("price")
							.toString()));
					t2Order.setAmount(t2Order.getPrice().multiply(
							t2Order.getT2_delivery_prediction()));
					t2OrderService.updatePrice(t2Order);
				}
			} // 额度闸口check
			String errMsg = commPurchase.isInLimitGate(gateService,
					t2OrderService, t2Order.getAmount(),
					t2Order.getEd_channel_id(), t2Order.getCategory_id());
			if (errMsg != null) {
				t2Order.setError_msg(errMsg);
				t2OrderService.updateCheckError(t2Order);
				continue;
			}

			// TODO
			// 下市闸口校验
			// 管理员忽略闸口
			// if (role != 102) {
			// if (commitCheckLimitGate(limitGateresult, t2Order)) {
			// t2Order.setError_msg("已经下市，不允许提交;");
			// t2OrderService.updateCheckError(t2Order);
			// continue;
			// }
			// }
			params = new HashMap<String, Object>();
			params.put("order_id", t2Order.getOrder_id());
			params.put("ed_channel_id", t2Order.getEd_channel_id());
			params.put("category_id", t2Order.getCategory_id());
			params.put("materials_id", t2Order.getMaterials_id());
			params.put("t2_delivery_prediction",
					t2Order.getT2_delivery_prediction());
			params.put("commituser", commituser);
			params.put("T2_report_year_week", T2_report_year_week);
			params.put("T3_report_year_week", T3_report_year_week);
			// params.put("gateLimit", authMap.get("gateLimit"));
			// 订单提交更新
			t2OrderService.commitT2OrderList(params);
		}
		return null;
	}

	/**
	 * 下市闸口校验
	 *
	 * @param authMap
	 * @param t2Order
	 * @param currentDate
	 * @return
	 */
	private boolean commitCheckLimitGate(
			ServiceResult<List<ProductLimitGate>> limitGateresult,
			T2OrderItem t2Order) {
		boolean result = false;
		int limitGatecount = 0;
		if (limitGateresult.getSuccess()) {
			for (ProductLimitGate productLimitGate : limitGateresult
					.getResult()) {
				String product_group = productLimitGate.getGroup_type();
				String cbs_catgory = productLimitGate.getProduct_type();
				String materials = productLimitGate.getModel_type();

				if ("QQD".equals(productLimitGate.getChannel_code())) {
					if (StringUtils.isNotEmpty(product_group)) {
						if (product_group.equals(t2Order.getProduct_group_id())) {
							limitGatecount++;
						}
					}
					if (StringUtils.isNotEmpty(cbs_catgory)) {
						if (cbs_catgory.equals(t2Order.getCategory_id())) {
							limitGatecount++;
						}
					}
					if (StringUtils.isNotEmpty(materials)) {
						if (materials.equals(t2Order.getMaterials_id())) {
							limitGatecount++;
						}
					}
					if (limitGatecount != 0) {
						return true;
					}
				}
				if (productLimitGate.getChannel_code().equals(
						t2Order.getEd_channel_id())) {
					if (StringUtils.isNotEmpty(product_group)) {
						if (product_group.equals(t2Order.getProduct_group_id())) {
							limitGatecount++;
						}
					}
					if (StringUtils.isNotEmpty(cbs_catgory)) {
						if (cbs_catgory.equals(t2Order.getCategory_id())) {
							limitGatecount++;
						}
					}
					if (StringUtils.isNotEmpty(materials)) {
						if (materials.equals(t2Order.getMaterials_id())) {
							limitGatecount++;
						}
					}
				}
			}
		}
		if (limitGatecount != 0) {
			result = true;
		}
		return result;
	}

	/**
	 * 获取T+2订单产品部审核
	 *
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "t2OrderDepartReview" }, method = { RequestMethod.GET })
	String t2OrderDepartReview(HttpServletRequest request,
			Map<String, Object> modelMap) {
		// 当前日期取得
		String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
		// 转化成周
		String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null,
				"1");
		// modelMap.put("currentweek", "2017年44周");
		modelMap.put("currentweek", currentWeek);
		Map<String, Object> authMap = new HashMap<>();
		commPurchase.getAuthMap(request, "", "", "", authMap);
		try {
			modelMap.put("authMap", JSON.json(authMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "purchase/t2OrderDepartReview";
	}

	/**
	 * 获取T+2订单审核信息
	 *
	 * @param report_year_week
	 *            提报周
	 * @param channel
	 *            渠道
	 * @param product_group
	 *            产品组
	 * @param cbsCategory
	 *            品类
	 * @param order_id
	 *            订单号
	 * @param materials_id
	 *            物料号
	 * @param storage_id
	 *            库位号
	 * @param flow_flag
	 *            状态
	 * @param order_category
	 *            订单类别
	 * @param rows
	 *            行数
	 * @param page
	 *            页数
	 * @return
	 */
	@RequestMapping(value = { "findT2OrderReviewList" }, method = { RequestMethod.POST })
	void find(@RequestParam(required = false) String report_year_week,
			@RequestParam(required = false) String channel,
			@RequestParam(required = false) String product_group,
			@RequestParam(required = false) String cbsCategory,
			@RequestParam(required = false) String order_id,
			@RequestParam(required = false) String materials_id,
			@RequestParam(required = false) String storage_id,
			@RequestParam(required = false) String flow_flag,
			@RequestParam(required = false) String order_category,
			@RequestParam(required = false) Integer rows,
			@RequestParam(required = false) Integer page,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (rows == null)
				rows = 20;
			if (page == null)
				page = 1;
//			 权限Map
			Map<String, Object> authMap = new HashMap<String, Object>();
//			 取得产品组权限List和渠道权限List
			 commPurchase.getAuthMap(request,
			 product_group, channel,
			 cbsCategory, authMap);
			Map<String, Object> params = new HashMap<String, Object>();
			if (report_year_week != null && !"".equals(report_year_week)) {
				report_year_week = report_year_week.replace("年", "");
				report_year_week = report_year_week.replace("周", "");
			}
			params.put("report_year_week", report_year_week);
			params.put("product_group_id", authMap.get("productGroup"));
			params.put("ed_channel_id", authMap.get("channel"));

			// 渠道和产品组数据存入HashMap
			Map<String, String> productgroupmap = new HashMap<String, String>();
			// 取得产品组
			productgroupmap = t2OrderService.getProductMap(productgroupmap);
//			for (String key : productgroupmap.keySet()) {
//				if (cbsCategory.equals(productgroupmap.get(key))) {
//					// 品类
//					params.put("cbsCategory", key);
//					break;
//				}
//			}
			params.put("cbsCategory", authMap.get("cbsCategory"));
			params.put("order_id", order_id);
			params.put("storage_id", storage_id);
			params.put("materials_id", materials_id);
			// 订单类别
			params.put("order_category", order_category);
			// flow_flag转化为数组
			String[] flow_flag_list = null;
			if (flow_flag != null && !"".equals(flow_flag)) {
				flow_flag_list = flow_flag.split(",");
			}
			params.put("flow_flag", flow_flag_list);
			params.put("m", (page - 1) * rows);
			params.put("n", rows);

			// 渠道和产品组数据存入HashMap
			// Map<String, String> productgroupmap = new HashMap<String,
			// String>();
			Map<String, String> invstockchannelmap = new HashMap<String, String>();
			// 取得产品组
			// commPurchase.getProductMap(productgroupmap);
			// 取得渠道
			// commPurchase.getChannelMap(invstockchannelmap);
			invstockchannelmap = t2OrderService
					.getChannelMapByCode(invstockchannelmap);
			// 渠道和产品组数据存入HashMap
			// 取得产品组
//			productgroupmap = t2OrderService.getProductMap(productgroupmap);
//			for (String key : productgroupmap.keySet()) {
//				if (cbsCategory.equals(productgroupmap.get(key))) {
//					// 品类
//					params.put("cbsCategory", key);
//					break;
//				}
//			}

//			params.put("cbsCategory", cbsCategory);
			// 获取订单类型map
			Map<String, String> orderTypeMap = commPurchase.getValueMeaningMap(
					dataDictionaryService, ORDER_TYPE);
			// 获取状态map
			Map<String, String> flowFlagMap = commPurchase.getValueMeaningMap(
					dataDictionaryService, T2_ORDER_STATUS);
			// 获取订单类别
			Map<String, String> orderCategoryMap = commPurchase
					.getValueMeaningMap(dataDictionaryService, ORDER_CATEGORY);
			// 调用业务Service
			ServiceResult<List<T2OrderItem>> result = t2OrderService
					.getT2OrderList(params);
			/*
			 * //获得条数 ServiceResult<Integer> resultcount =
			 * t2OrderService.getRowCnts();
			 */
			if (result.getSuccess() && result.getResult() != null) {
				List<T2OrderItem> predictstocklist = result.getResult();
				for (T2OrderItem item : predictstocklist) {
					// 根据渠道id取得渠道名
					item.setEd_channel_name(invstockchannelmap.get(item
							.getEd_channel_id()));
					// 根据工作组id取得工作组名
					item.setProduct_group_name(productgroupmap.get(item
							.getProduct_group_id()));
					// 设置订单类型
					item.setOrder_type_name(orderTypeMap.get(String
							.valueOf(item.getOrder_type_id())));
					// 设置订单状态
					item.setFlow_flag_name(flowFlagMap.get(String.valueOf(item
							.getFlow_flag())));
					// 设置订单类别
					item.setOrder_category_name(orderCategoryMap.get(String
							.valueOf(item.getOrder_category())));
				}
				Map<String, Object> retMap = new HashMap<String, Object>();
				retMap.put("total", result.getPager().getRowsCount());
				retMap.put("rows", predictstocklist);
				Gson gson = new Gson();
				response.addHeader("Content-type", "text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(retMap));
				// response.getWriter().write(JsonUtil.toJson(retMap));
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (IOException e) {
			logger.error("", e);
			throw new BusinessException("失败" + e.getMessage());
		}
	}

	/**
	 * T+2点击全部导出、导出Excel
	 *
	 * @param report_year_week
	 *            填报周
	 * @param channel
	 *            渠道
	 * @param product_group
	 *            产品组
	 * @param order_id
	 *            订单号
	 * @param materials_id
	 *            物料号
	 * @param storage_id
	 *            库位码
	 * @param flow_flag
	 *            状态
	 * @param response
	 * @return 方法执行完毕调用的画面
	 */
	@RequestMapping(value = { "/exportAllT2OrderList.export" })
	void exportAllT2OrderList(
			@RequestParam(required = false) String report_year_week,
			@RequestParam(required = false) String ed_channel_id_save,
			@RequestParam(required = false) String product_group_id_save,
			@RequestParam(required = false) String order_id_save,
			@RequestParam(required = false) String materials_id_save,
			@RequestParam(required = false) String storage_id_save,
			@RequestParam(required = false) String flow_flag_save,
			@RequestParam(required = false) String cbsCategory_save,
			@RequestParam(required = false) String order_category_save,
			Map<String, Object> modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		// 状态
		// flow_flag转化为数组
		String[] flow_flag_list = null;
		if (flow_flag_save != null && !"".equals(flow_flag_save)) {
			flow_flag_list = flow_flag_save.split(",");
		}
		// 权限Map
		Map<String, Object> authMap = new HashMap<String, Object>();
		// 取得产品组权限List,渠道权限List和品类List
		commPurchase.getAuthMap(request,
				product_group_id_save, ed_channel_id_save, cbsCategory_save,
				authMap);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("report_year_week", report_year_week.replace("年", "")
				.replace("周", ""));
		params.put("ed_channel_id", authMap.get("channel"));
		params.put("product_group_id", authMap.get("productGroup"));
		params.put("order_id", order_id_save);
		params.put("materials_id", materials_id_save);
		params.put("storage_id", storage_id_save);
		params.put("flow_flag", flow_flag_list);
		params.put("cbsCategory", authMap.get("cbsCategory"));
		params.put("order_category", order_category_save);
		List<T2OrderItem> orderList = getDetailsData(params);
		// 1.创建一个workbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 2.在workbook中添加一个sheet，对应Excel中的一个sheet
		HSSFSheet sheet = wb.createSheet("订单列表");
		sheet.setColumnWidth(0, (int) (21.57 * 256));
		sheet.setColumnWidth(1, (int) 21.57 * 256);
		sheet.setColumnWidth(2, (int) 21.57 * 256);
		sheet.setColumnWidth(3, (int) 21.57 * 256);
		sheet.setColumnWidth(4, (int) 21.57 * 256);
		sheet.setColumnWidth(5, (int) 21.57 * 256);
		sheet.setColumnWidth(6, (int) 11.14 * 256);
		sheet.setColumnWidth(7, (int) 8.57 * 256);
		sheet.setColumnWidth(8, (int) 21.57 * 256);
		sheet.setColumnWidth(9, (int) 21.57 * 256);
		sheet.setColumnWidth(10, (int) 21.57 * 256);
		sheet.setColumnWidth(11, (int) 21.57 * 256);
		sheet.setColumnWidth(12, (int) 21.57 * 256);
		sheet.setColumnWidth(13, (int) 21.57 * 256);
		sheet.setColumnWidth(14, (int) 21.57 * 256);
		sheet.setColumnWidth(15, (int) 21.57 * 256);
		sheet.setColumnWidth(16, (int) 21.57 * 256);
		sheet.setColumnWidth(17, (int) 21.57 * 256);
		sheet.setColumnWidth(18, (int) 11.14 * 256);
		sheet.setColumnWidth(19, (int) 8.57 * 256);
		sheet.setColumnWidth(20, (int) 21.57 * 256);

		// 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 4.创建单元格，设置值表头，设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		// 居中格式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

		int length = ExportData.orderListTitle.length;
		// 设置表头
		for (int i = 0; length - 1 >= i; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(ExportData.orderListTitle[i]);
			cell.setCellStyle(style);
		}
		Map<String, String> invstockchannelmap = new HashMap<String, String>();// 渠道
		invstockchannelmap = t2OrderService
				.getChannelMapByCode(invstockchannelmap);
		// 向单元格里添加数据
		for (short i = 0; i < orderList.size(); i++) {
			row = sheet.createRow(i + 1);
			row.createCell(0)
					.setCellValue(
							invstockchannelmap.get(orderList.get(i)
									.getEd_channel_id()));
			row.createCell(1).setCellValue(orderList.get(i).getCategory_id());
			row.createCell(2).setCellValue(
					orderList.get(i).getProduct_group_name());
			row.createCell(3).setCellValue(orderList.get(i).getOrder_id());
			row.createCell(4).setCellValue(orderList.get(i).getMaterials_id());
			row.createCell(5).setCellValue(orderList.get(i).getStorage_id());
			row.createCell(6)
					.setCellValue(
							String.valueOf(orderList.get(i)
									.getT2_delivery_prediction()));
			row.createCell(7).setCellValue(
					String.valueOf(orderList.get(i).getPrice()));
			row.createCell(8).setCellValue(
					String.valueOf(orderList.get(i).getAmount()));
			row.createCell(9)
					.setCellValue(orderList.get(i).getMaterials_desc());
			row.createCell(10)
					.setCellValue(orderList.get(i).getOrder_num_73());
			row.createCell(11)
					.setCellValue(orderList.get(i).getSend_flag());
			row.createCell(12).setCellValue(
					orderList.get(i).getFlow_flag_name());
			row.createCell(13).setCellValue(
					orderList.get(i).getOrder_type_name());
			row.createCell(14).setCellValue(
					orderList.get(i).getChannel_commit_user());
			row.createCell(15).setCellValue(
					orderList.get(i).getChannel_commit_time_display());
			row.createCell(16).setCellValue(
					orderList.get(i).getAudit_depart_user());
			row.createCell(17).setCellValue(
					orderList.get(i).getAudit_depart_time_display());
			row.createCell(18).setCellValue(orderList.get(i).getAudit_user());
			row.createCell(19).setCellValue(
					orderList.get(i).getAudit_time_display());
			row.createCell(20).setCellValue(orderList.get(i).getError_msg());

		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		java.util.Date date = new java.util.Date();
		String str = sdf.format(date);
		String fileName = "订单列表" + str;

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);

			ExportExcelUtil.exportCommon(is, fileName, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("错误", e);
		}

	}
	 public String AllToNull(String param){
			
	    	if("ALL".equals(param)||"全部".equals(param)){
	    		
	    		param=null;
	    		
	    	}
	    	return param;

	    }

	/**
	 * 审核数据(产品部审核通过)
	 *
	 * @param request
	 * @param response
	 * @param report_year_week
	 * @param reviewFlag
	 * @param reviewData
	 * @param audit_remark
	 * @param ed_channel_id
	 * @param product_group_id
	 * @param wa_info
	 * @param materials_desc
	 * @param wp_order_id
	 * @param vkorg
	 * @param matnr
	 * @param flow_flag
	 * @return
	 */
	@RequestMapping(value = { "reviewT2OrderDepart" }, method = { RequestMethod.POST })
	@ResponseBody
	public HttpJsonResult<String> reviewT2OrderDepart(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) String channel,
			@RequestParam(required = false) String product_group,
			@RequestParam(required = false) String cbsCategory,
			@RequestParam(required = false) String order_id,
			@RequestParam(required = false) String materials_id,
			@RequestParam(required = false) String storage_id,
			@RequestParam(required = false) String flow_flag,
			@RequestParam(required = false) String order_category,
			@RequestParam(required = false) String report_year_week,
			@RequestParam(required = false) String reviewFlag,
			@RequestParam(required = false) String reviewData,
			@RequestParam(required = false) String audit_remark) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();
		Map<String, Object> authMap = new HashMap<String, Object>();
		// 取得产品组权限List和渠道权限List
		 commPurchase.getAuthMap( request,
		 product_group,
		 channel, channel, authMap);
		// 时间闸口check
		// if ("0".equals(authMap.get("gateLimit"))) {
		// // 时间闸口check
		// if (!commPurchase.isInTimeGate(gateService, "17")) {
		// result.setData("不在产品部审核期间内");
		// return result;
		// }
		// }
		try {
			Map<String, Object> reviewParams = new HashMap<String, Object>();
			reviewParams.put("flow_flag", reviewFlag);
			reviewParams.put("audit_remark", audit_remark);
			reviewParams.put("audit_user", request.getSession().getAttribute("userName").toString());
			List<String> reviewList = new ArrayList<String>();
			if ("[]".equals(reviewData)) {
				Map<String, Object> params = new HashMap<String, Object>();
				String[] flow_flag_list = new String[] { "3" };
				params.put("report_year_week", report_year_week
						.replace("年", "").replace("周", ""));
				params.put("ed_channel_id", authMap.get("channel"));
				params.put("product_group_id", authMap.get("productGroup"));
				params.put("cbsCategory", authMap.get("cbsCategory"));
				params.put("order_id", order_id);
				params.put("materials_id", materials_id);
				params.put("storage_id", storage_id);
				params.put("flow_flag", flow_flag_list);
				params.put("order_category", order_category);
				// 调用业务Service取得所有订单号
				ServiceResult<List<T2OrderItem>> queryresult = t2OrderService
						.getT2OrderList(params);
				if (queryresult.getSuccess() && queryresult.getResult() != null) {
					List<T2OrderItem> t2OrderList = queryresult.getResult();
					for (int i = 0; i < t2OrderList.size(); i++) {
						reviewList.add(t2OrderList.get(i).getOrder_id());
					}
				}
			} else {
				JSONArray reviewJson = (JSONArray) JSON.parse(reviewData);
				// JSONArray转化为list
				for (int i = 0; i < reviewJson.length(); i++) {
					reviewList.add(reviewJson.getString(i));
				}
			}
			reviewParams.put("reviewList", reviewList);
			ServiceResult<Boolean> reviewResult = t2OrderService
					.reviewT2OrderDepart(reviewParams);
			result.setSuccess(reviewResult.getResult());
		} catch (ParseException e) {
			result.setSuccess(false);
			e.printStackTrace();
			logger.error("JSON转换失败！ 错误：", e);
		}
		return result;
	}

	/**
	 * 获取T+2订单审核菜单链接（供应链审核）
	 *
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "t2OrderReviewList" }, method = { RequestMethod.GET })
	String t2OrderReviewList(HttpServletRequest request,
			Map<String, Object> modelMap) {
		// 当前日期取得
		String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
		// 转化成周
		String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null,
				"1");
		modelMap.put("currentweek", currentWeek);
		Map<String, Object> authMap = new HashMap<>();
		commPurchase.getAuthMap(request, "", "", "", authMap);
		try {
			modelMap.put("authMap", JSON.json(authMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "purchase/t2OrderReviewList";
	}

	/**
	 * T+2订单审核页面跳转
	 *
	 * @param request
	 * @param reviewData
	 * @param report_year_week
	 * @param ed_channel_id_save
	 * @param product_group_id_save
	 * @param order_id_save
	 * @param materials_id_save
	 * @param storage_id_save
	 * @param modelMap
	 * @return
	 */
/*	@RequestMapping(value = { "/t2OrderReviewList" }, method = { RequestMethod.GET })
	public String gotoAllT2OrderReviewDetailList(HttpServletRequest request,
			@RequestParam(required = false) String reviewData,
			@RequestParam(required = false) String report_year_week,
			@RequestParam(required = false) String ed_channel_id_save,
			@RequestParam(required = false) String product_group_id_save,
			@RequestParam(required = false) String order_id_save,
			@RequestParam(required = false) String materials_id_save,
			@RequestParam(required = false) String storage_id_save,
			@RequestParam(required = false) String order_category_save,
			Map<String, Object> modelMap) {
		if (reviewData != null && !"".equals(reviewData)) {
			// 部分审核时，订单ID取得
			modelMap.put("reviewData", reviewData);
		} else {
			// 全部审核时，订单ID取得
			// flow_flag转化为数组
			String[] flow_flag_list = new String[] { FLOWFLAG_WAITFORREVIEW };
			String[] reviewArray = null;
			Map<String, Object> params = new HashMap<String, Object>();
//			// 权限Map
//			Map<String, Object> authMap = new HashMap<String, Object>();
//			// 取得产品组权限List和渠道权限List
//			commPurchase.getAuthMap(purchaseCommonService, request,
//					product_group_id_save, ed_channel_id_save, null, authMap);
			params.put("report_year_week", report_year_week.replace("年", "")
					.replace("周", ""));
			params.put("ed_channel_id", ed_channel_id_save);
			params.put("product_group_id", product_group_id_save);
			params.put("order_id", order_id_save);
			params.put("materials_id", materials_id_save);
			params.put("storage_id", storage_id_save);
			params.put("flow_flag", flow_flag_list);
			params.put("order_category", order_category_save);
			// 调用业务Service取得所有订单号
			ServiceResult<List<T2OrderItem>> queryresult = t2OrderService
					.getT2OrderList(params);
			if (queryresult.getSuccess() && queryresult.getResult() != null) {
				List<T2OrderItem> t2OrderList = queryresult.getResult();
				reviewArray = new String[t2OrderList.size()];
				for (int i = 0; i < t2OrderList.size(); i++) {
					reviewArray[i] = t2OrderList.get(i).getOrder_id();
				}
			}
			Gson gson = new Gson();
			modelMap.put("reviewData", gson.toJson(reviewArray));
		}
		String url = request.getHeader("referer");
		modelMap.put("url", url);
		// 当前日期取得
		String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
		// 转化成周
		String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null,
				"1");
		modelMap.put("currentweek", currentWeek);
		return "/purchase/t2OrderReviewList";
		//return "/purchase/t2OrderReviewDetailList";
	}*/

	/**
	 * T+2审核页面订单撤销
	 *
	 * @param request
	 * @param revokeData
	 *            撤销数据
	 */
	@RequestMapping(value = { "revokeOrderList" }, method = { RequestMethod.POST })
	@ResponseBody
	public HttpJsonResult<String> revokeOrderList(HttpServletRequest request,
			@RequestParam(required = true) String revokeData) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();
		if (revokeData != null && !revokeData.equals("")) {
			try {
				// 取得撤销数据
				JSONArray revokejson = (JSONArray) JSON.parse(revokeData);
				List<String> revokeList = new ArrayList<String>();
				// JSONArray转化为list
				for (int i = 0; i < revokejson.length(); i++) {
					revokeList.add(revokejson.getString(i));
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("revokeList", revokeList);
				params.put("audit_user", request.getSession().getAttribute("userName").toString());
				// 订单撤销更新
				t2OrderService.revokeT2OrderList(params);
			} catch (ParseException e) {
				logger.error("", e);
				result.setSuccess(false);
				result.setMessage("撤销失败");
			}

		}
		return result;
	}

	/**
	 * 订单数量修改
	 *
	 * @param request
	 * @param order_id
	 * @param modifycount
	 * @return
	 */
	@RequestMapping(value = { "updateCount" }, method = { RequestMethod.POST })
	@ResponseBody
	public HttpJsonResult<String> updateCount(HttpServletRequest request,
			@RequestParam(required = false) String order_id,
			@RequestParam(required = false) int modifycount) {

		HttpJsonResult<String> result = new HttpJsonResult<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		// 订单号
		params.put("order_id", order_id);
		// 修改的数量
		params.put("modifycount", modifycount);

		// 日志操作录入 start xuelin.zhao/////////////
		String user = WebUtil.currentUserName(request);
		this.logAuditService.unionLog("haier_t2_order_t",
				request.getParameter("t2_delivery_prediction"),
				String.valueOf(modifycount), "1", user, order_id);
		// end///////////////////////////////////

		// 订单数量修改
		ServiceResult<Boolean> serResult = t2OrderService.updateCount(params);
		if (serResult.getSuccess() && serResult.getResult() != null
				&& serResult.getResult() == true) {
			// 数量修改成功
			result.setTotalCount(1);
		} else {
			// 数量修改失败
			result.setTotalCount(0);
		}
		return result;
	}

	/**
	 * 订单审核
	 *
	 * @param request
	 * @param reviewFlag
	 * @param reviewData
	 * @param audit_remark
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = { "reviewT2OrderReviewDetailList" }, method = { RequestMethod.POST })
	@ResponseBody
	public HttpJsonResult<String> commitOrderList(HttpServletRequest request,
			@RequestParam(required = false) String reviewFlag,
			@RequestParam(required = false) String reviewData,
			@RequestParam(required = false) String audit_remark,
			Map<String, Object> modelMap) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();
//		// 权限Map
		Map<String, Object> authMap = new HashMap<String, Object>();
//		// 取得产品组权限List和渠道权限List
//		commPurchase.getAuthMap( request, null, null,
//				null, authMap);
		// 闸口不秒杀的场合，时间闸口check
//		if ("0".equals(authMap.get("gateLimit"))) {
//			// 时间闸口check
//			if (!commPurchase.isInTimeGate(gateService, "07")) {
//				result.setData("不在提交期间内");
//				return result;
//			}
//		}
		List<String> reviewList = new ArrayList<String>();
		List<String> t2ReviewList = new ArrayList<String>();
		List<T2OrderItem> pedReviewList = new ArrayList<T2OrderItem>();
		List<T2OrderItem> ensReviewList = new ArrayList<T2OrderItem>();
		// 审核通过FLAG（10:审核通过 -10：作废）
		String review_flag = "";
		String T2_report_year_week = "";
		String T3_report_year_week = "";
		if ("0".equals(reviewFlag)) {
			review_flag = "10";
			// T+3提报周和T+2提报周三取得
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			T2_report_year_week = CommUtil.getWeekOfYear_Sunday(
					sdf.format(cal.getTime()), null, "0");
			cal.add(Calendar.DATE, -7);
			T3_report_year_week = CommUtil.getWeekOfYear_Sunday(
					sdf.format(cal.getTime()), null, "0");
		} else {
			review_flag = "-10";
		}
		// 审核者
		String audit_user = request.getSession().getAttribute("userName").toString();
		// 审核状态判断
		if (reviewData != null && !"[]".equals(reviewData)) {
			try {
				// 取得提交数据 
				JSONArray reviewjson = (JSONArray) JSON.parse(reviewData);
				// JSONArray转化为list
				for (int i = 0; i < reviewjson.length(); i++) {
					reviewList.add(reviewjson.getString(i));
				}

			} catch (ParseException e) {
				e.printStackTrace();
				logger.error("JSON转换失败！ 错误：" + e.getMessage());
			}
		}
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("order_list", reviewList);
		// 调用业务Service取得所有订单
		ServiceResult<List<T2OrderItem>> queryresult = t2OrderService
				.getT2OrderList(paramsMap);
		if (queryresult.getSuccess() && queryresult.getResult() != null) {
			for (T2OrderItem t2OrderItem : queryresult.getResult()) {
				if (t2OrderItem.getOrder_category() == 0) {
					t2ReviewList.add(t2OrderItem.getOrder_id());
				}else if (t2OrderItem.getOrder_category() == 1) {
					pedReviewList.add(t2OrderItem);
				}else if (t2OrderItem.getOrder_category() == 2) {
					ensReviewList.add(t2OrderItem);
				}
			}
		}
		// T+2订单
		if (t2ReviewList != null && t2ReviewList.size() > 0) {
			Collections.sort(t2ReviewList);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("reviewList", t2ReviewList);
			params.put("audit_user", audit_user);
			params.put("flow_flag", review_flag);
			params.put("audit_remark", audit_remark);
			params.put("T2_report_year_week", T2_report_year_week);
			params.put("T3_report_year_week", T3_report_year_week);
			// 订单审核状态更新
			ServiceResult<Boolean> reviewResult = t2OrderService
					.reviewT2OrderList(params);
			if (reviewResult.getSuccess() && reviewResult.getResult()) {
				result.setMessage("success");
			} else {
				result.setMessage("error");
			}
		}
		// 款先直发订单
		if (pedReviewList != null && pedReviewList.size() > 0) {
			// Collections.sort(pedReviewList);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("order_list", pedReviewList);
			params.put("audit_user", audit_user);
			params.put("mark", "10");
			Map<String, Object> reviewMap = new HashMap<String, Object>();
			// 手工采购
			ServiceResult<List<String>> insWAOBTCresult = t2OrderService
					.insertWAOrderBillToCRM(params);
			if (insWAOBTCresult.getSuccess()
					&& insWAOBTCresult.getResult() != null) {
				reviewMap.put("reviewList", insWAOBTCresult.getResult());
				reviewMap.put("audit_user", audit_user);
				reviewMap.put("flow_flag", review_flag);
				reviewMap.put("audit_remark", audit_remark);
				reviewMap.put("T2_report_year_week", T2_report_year_week);
				reviewMap.put("T3_report_year_week", T3_report_year_week);
				// 订单审核状态更新
				ServiceResult<Boolean> reviewResult = t2OrderService
						.reviewKXOrderList(reviewMap);
				System.out.println(reviewResult.getMessage());
				if (reviewResult.getSuccess() && reviewResult.getResult()) {
					result.setMessage("success");
				} else {
					result.setMessage("error");
				}
			}
		}
		// 机壳不结算订单
		if (ensReviewList != null && ensReviewList.size() > 0) {
			// Collections.sort(ensReviewList);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("order_list", ensReviewList);
			params.put("audit_user", audit_user);
			params.put("mark", "11");
			Map<String, Object> reviewMap = new HashMap<String, Object>();
			// 手工采购
			ServiceResult<List<String>> insWAOBTCresult = t2OrderService
					.insertWAOrderBillToCRM(params);
			if (insWAOBTCresult.getSuccess()
					&& insWAOBTCresult.getResult() != null) {
				reviewMap.put("reviewList", insWAOBTCresult.getResult());
				reviewMap.put("audit_user", audit_user);
				reviewMap.put("flow_flag", review_flag);
				reviewMap.put("audit_remark", audit_remark);
				reviewMap.put("T2_report_year_week", T2_report_year_week);
				reviewMap.put("T3_report_year_week", T3_report_year_week);
				// 订单审核状态更新
				ServiceResult<Boolean> reviewResult = t2OrderService
						.reviewT2OrderList(reviewMap);
				if (reviewResult.getSuccess() && reviewResult.getResult()) {
					result.setMessage("success");
				} else {
					result.setMessage("error");
				}
			}
		}
		if ((ensReviewList != null && ensReviewList.size() > 0)
				|| (t2ReviewList != null && t2ReviewList.size() > 0)
				|| (pedReviewList != null && pedReviewList.size() > 0)) {

		} else {
			result.setMessage("error");
		}
		return result;
	}

	/*  *//**
	 * 在审核详细页面中为datagrid加载值
	 *
	 * @param reviewData
	 *            审核T+2订单编号
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws ParseException
	 *             ,IOException
	 */
	@RequestMapping(value = { "findT2OrderReviewDetailList" }, method = { RequestMethod.POST })
	public void findT2OrderReviewDetailList(HttpServletResponse response,
			HttpServletRequest request,
			@RequestParam(required = false) String reviewData,
			Map<String, Object> modelMap) throws IOException {

		Map<String, Object> params = new HashMap<String, Object>();
		JSONArray reviewJson = new JSONArray();
		String[] reviewArray = null;
		// flow_flag转化为数组
		String[] flow_flag_list = new String[] { FLOWFLAG_WAITFORREVIEW };

		try {
			if (reviewData != null && !"[]".equals(reviewData)) {
				reviewJson = (JSONArray) JSON.parse(reviewData);
				reviewArray = new String[reviewJson.length()];
				// JSONArray转化为list
				for (int i = 0; i < reviewJson.length(); i++) {
					reviewArray[i] = (String) reviewJson.get(i);
				}
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
			logger.error("JSON转换失败！ 错误：" + e1.getMessage());
		}

		try {
			if (reviewArray != null && reviewArray.length > 0) { // 订单ID
				params.put("order_list", reviewArray);
				params.put("flow_flag", flow_flag_list);

				List<T2OrderItem> t2OrderList = getDetailsData(params); // 获得条数
				ServiceResult<Integer> resultcount = t2OrderService
						.getRowCnts();

				if (t2OrderList != null && resultcount.getSuccess()
						&& resultcount.getResult() != null) {
					modelMap.put("rows", t2OrderList);
					modelMap.put("total", resultcount.getResult());
				}

				Gson gson = new Gson();
				response.addHeader("Content-type", "text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(modelMap));
				response.getWriter().flush();
				response.getWriter().close();
			}

		} catch (IOException e) {
			logger.error("", e);
			throw new BusinessException("审核数据加载失败" + e.getMessage());
		}
	}

	/**
	 * T+2审核提交画面导出数据处理（将不同DB中检索出的数据组织在一起）
	 *
	 * @param Map
	 *            <String, Object> params 检索条件中的参数
	 * @return result 需要显示或导出数据结果
	 */
	public List<T2OrderItem> getDetailsData(Map<String, Object> params) {
		// 渠道和产品组数据存入HashMap
		Map<String, String> productgroupmap = new HashMap<String, String>();
		Map<String, String> invstockchannelmap = new HashMap<String, String>();
		// 取得产品组
		commPurchase.getProductMap(productgroupmap);
		// 取得渠道
//		commPurchase.getChannelMap(invstockchannelmap);
		invstockchannelmap = t2OrderService
				.getChannelMapByCode(invstockchannelmap);
		// 获取订单类型map
		Map<String, String> orderTypeMap = commPurchase.getValueMeaningMap(
				dataDictionaryService, ORDER_TYPE);
		// 获取流程状态map
		Map<String, String> flowFlagMap = commPurchase.getValueMeaningMap(
				dataDictionaryService, T2_ORDER_STATUS);
		// 调用业务Service
		ServiceResult<List<T2OrderItem>> result = t2OrderService
				.getT2OrderList(params);
		// 获得条数
		List<T2OrderItem> t2OrderList = new ArrayList<T2OrderItem>();
		if (result.getSuccess() && result.getResult() != null) {
			t2OrderList = result.getResult();
			for (T2OrderItem item : t2OrderList) {
				// 取得流程状态名称
				item.setFlow_flag_name(flowFlagMap.get(String.valueOf(item
						.getFlow_flag())));
				// 根据渠道id取得渠道名
				item.setEd_channel_name(invstockchannelmap.get(item
						.getEd_channel_id()));
				// 根据工作组id取得工作组名
				item.setProduct_group_name(productgroupmap.get(item
						.getProduct_group_id()));
				// 编辑订单类型
				item.setOrder_type_name(orderTypeMap.get(String.valueOf(item
						.getOrder_type_id())));
			}
		}
		return t2OrderList;
	}

	/**
	 * 导出Excel模板
	 *
	 * @param response
	 * @return 方法执行完毕调用的画面
	 */
	@RequestMapping(value = { "/exportT2OrderModel" })
	public void exportModel(HttpServletRequest request,
			HttpServletResponse response) {
		String fileName = "T2导入Excel模板";
		String sheetName = "导入模板";
		ExcelExportUtil.downloadDataTemplate(logger, request, response,
				fileName, sheetName, CHECKSTR);
	}

	/**
	 * 删除订单
	 *
	 * @param request
	 * @param deleteData
	 *            要删除的行
	 * @return
	 */
	@RequestMapping(value = { "/deleteOrderList" }, method = { RequestMethod.POST })
	@ResponseBody
	public HttpJsonResult<String> deleteOrderList(HttpServletRequest request,
			@RequestParam(required = true) String deleteData) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();
		if (deleteData != null) {
			try {
				// 取得提交数据
				JSONArray deletejson = (JSONArray) JSON.parse(deleteData);
				List<String> deleteList = new ArrayList<String>();
				// JSONArray转化为list
				for (int i = 0; i < deletejson.length(); i++) {
					deleteList.add(deletejson.getString(i));
				}
				// 取得提交者
				String commituser = WebUtil.currentUserName(request);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("deleteList", deleteList);
				params.put("commituser", commituser);

				// 日志操作记录 start xuelin.zhao///////
				this.logAuditService.unionLog("leader_nb_t2_order_t",
						deleteList, deleteList, "5", commituser, "");
				// end

				// 订单删除
				t2OrderService.deleteT2OrderList(params);
				result.setSuccess(true);
			} catch (ParseException e) {
				logger.error("", e);
				throw new BusinessException("JSON转化失败" + e.getMessage());
			}

		}
		result.setMessage("删除成功");

		return result;
	}

	/**
	 * T+2点击全部导出、导出Excel
	 *
	 * @param report_year_week
	 *            填报周
	 * @param channel
	 *            渠道
	 * @param product_group
	 *            产品组
	 * @param order_id
	 *            订单号
	 * @param materials_id
	 *            物料号
	 * @param storage_id
	 *            库位码
	 * @param flow_flag
	 *            状态
	 * @param response
	 * @return 方法执行完毕调用的画面
	 */
	@RequestMapping(value = { "/exportT2OrderList.export" })
	void exportT2OrderList(
			@RequestParam(required = false) String exportData,
			HttpServletResponse res) {

		Map<String, Object> params = new HashMap<String, Object>();
		JSONArray exportJson = new JSONArray();
		String[] exportArray = null;
		try {
			if (exportData != null && !exportData.equals("")) {
				exportJson = (JSONArray) JSON.parse(exportData);
				exportArray = new String[exportJson.length()];
				// JSONArray转化为list
				for (int i = 0; i < exportJson.length(); i++) {
					exportArray[i] = (String) exportJson.get(i);
				}
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
			logger.error("JSON转换失败！ 错误：" + e1.getMessage());
		}
		params.put("order_list", exportArray);
		List<T2OrderItem> orderList = getDetailsData(params);

		// 1.创建一个workbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 2.在workbook中添加一个sheet，对应Excel中的一个sheet
		HSSFSheet sheet = wb.createSheet("订单列表");
		sheet.setColumnWidth(0, (int) (21.57 * 256));
		sheet.setColumnWidth(1, (int) 21.57 * 256);
		sheet.setColumnWidth(2, (int) 21.57 * 256);
		sheet.setColumnWidth(3, (int) 21.57 * 256);
		sheet.setColumnWidth(4, (int) 21.57 * 256);
		sheet.setColumnWidth(5, (int) 21.57 * 256);
		sheet.setColumnWidth(6, (int) 11.14 * 256);
		sheet.setColumnWidth(7, (int) 8.57 * 256);
		sheet.setColumnWidth(8, (int) 21.57 * 256);
		sheet.setColumnWidth(9, (int) 21.57 * 256);
		sheet.setColumnWidth(10, (int) 21.57 * 256);
		sheet.setColumnWidth(11, (int) 21.57 * 256);
		sheet.setColumnWidth(12, 9 * 256);
		sheet.setColumnWidth(13, (int) 21.57 * 256);
		sheet.setColumnWidth(14, (int) 21.57 * 256);
		sheet.setColumnWidth(15, (int) 21.57 * 256);
		sheet.setColumnWidth(16, (int) 21.57 * 256);
		sheet.setColumnWidth(17, (int) 21.57 * 256);
		sheet.setColumnWidth(18, (int) 11.14 * 256);
		sheet.setColumnWidth(19, (int) 8.57 * 256);
		sheet.setColumnWidth(20, (int) 21.57 * 256);

		// 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 4.创建单元格，设置值表头，设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		// 居中格式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

		int length = ExportData.orderListTitle.length;
		// 设置表头
		for (int i = 0; length - 1 >= i; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(ExportData.orderListTitle[i]);
			cell.setCellStyle(style);
		}
		Map<String, String> invstockchannelmap = new HashMap<String, String>();// 渠道
		invstockchannelmap = t2OrderService
				.getChannelMapByCode(invstockchannelmap);
		// 向单元格里添加数据
		for (short i = 0; i < orderList.size(); i++) {
			row = sheet.createRow(i + 1);
			row.createCell(0)
					.setCellValue(
							invstockchannelmap.get(orderList.get(i)
									.getEd_channel_id()));
			row.createCell(1).setCellValue(orderList.get(i).getCategory_id());
			row.createCell(2).setCellValue(
					orderList.get(i).getProduct_group_name());
			row.createCell(3).setCellValue(orderList.get(i).getOrder_id());
			row.createCell(4).setCellValue(orderList.get(i).getMaterials_id());
			row.createCell(5).setCellValue(orderList.get(i).getStorage_id());
			row.createCell(6)
					.setCellValue(
							String.valueOf(orderList.get(i)
									.getT2_delivery_prediction()));
			row.createCell(7).setCellValue(
					String.valueOf(orderList.get(i).getPrice()));
			row.createCell(8).setCellValue(
					String.valueOf(orderList.get(i).getAmount()));
			row.createCell(9)
					.setCellValue(orderList.get(i).getMaterials_desc());
			row.createCell(10)
				.setCellValue(orderList.get(i).getOrder_num_73());
			row.createCell(11)
				.setCellValue(orderList.get(i).getSend_flag());
			row.createCell(12).setCellValue(
					orderList.get(i).getFlow_flag_name());
			row.createCell(13).setCellValue(
					orderList.get(i).getOrder_type_name());
			row.createCell(14).setCellValue(
					orderList.get(i).getChannel_commit_user());
			row.createCell(15).setCellValue(
					orderList.get(i).getChannel_commit_time_display());
			row.createCell(16).setCellValue(
					orderList.get(i).getAudit_depart_user());
			row.createCell(17).setCellValue(
					orderList.get(i).getAudit_depart_time_display());
			row.createCell(18).setCellValue(orderList.get(i).getAudit_user());
			row.createCell(19).setCellValue(
					orderList.get(i).getAudit_time_display());
			row.createCell(20).setCellValue(orderList.get(i).getError_msg());

		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		java.util.Date date = new java.util.Date();
		String str = sdf.format(date);
		String fileName = "订单列表" + str;

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);

			ExportExcelUtil.exportCommon(is, fileName, res);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("错误", e);
		}

	}

	/**
	 * 获取品类权限数据
	 *
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = { "/getCbsCategoryByAuth" }, method = { RequestMethod.GET })
	@ResponseBody
	HttpJsonResult<List<String>> getCbsCategoryByAuth(HttpServletRequest request) {
		HttpJsonResult<List<String>> result = new HttpJsonResult<List<String>>();

//		System.out.println("begin-----------------------");
		ServiceResult<List<String>> queryResult = itemService
				.getAllCbsCategory();
//		System.out.println("end-----------------------");

		// // 权限定义产品组取得
		// ServiceResult<PrivilegeItem> privilegeData =
		// purchaseCommonService.getPrivilege(String
		// .valueOf(WebUtil.currentUserId(request)));
		// String[] cbsCategoryDataList = null;
		// cbsCategoryDataList =
		// privilegeData.getResult().getCbs_catgory().split(",");
		// // 删除没有权限的产品组
		// List<String> cbsCategoryData = new ArrayList<String>();
		// if (privilegeData.getSuccess() == true && privilegeData.getResult()
		// != null) {
		// for (String category : queryResult.getResult()) {
		// //category='空调' privilegeData.getResult().getCbs_catgory() = '商用空调'
		// //此种情况就会将 空调加到cbsCategoryData里 zzb 2017-03-28
		// /* if (privilegeData.getResult().getCbs_catgory().contains(category))
		// {
		// cbsCategoryData.add(category);
		// }*/
		// for(int i = 0;i<cbsCategoryDataList.length;i++){
		// if(category.equals(cbsCategoryDataList[i])){
		// cbsCategoryData.add(category);
		// }
		// }
		// }
		// result.setData(cbsCategoryData);
		// } else {
		// result.setData(queryResult.getResult());
		// }
		result.setData(queryResult.getResult());
		return result;
	}

	/**
	 * 渠道
	 * 
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = { "/getChannelsByAuth" }, method = { RequestMethod.GET })
	@ResponseBody
	HttpJsonResult<List<InvStockChannel>> getChennelsByAuth(
			HttpServletRequest request) {
		HttpJsonResult<List<InvStockChannel>> result = new HttpJsonResult<List<InvStockChannel>>();

		ServiceResult<List<InvStockChannel>> queryResult = stockAgeService
				.getChannels();

		// // 权限定义产品组取得
		// ServiceResult<PrivilegeItem> privilegeData = purchaseCommonService
		// .getPrivilege(String.valueOf(WebUtil.currentUserId(request)));
		// // 删除没有权限的产品组
		// List<InvStockChannel> chennelsData = new
		// ArrayList<InvStockChannel>();
		// if (privilegeData.getSuccess() == true && privilegeData.getResult()
		// != null) {
		// for (InvStockChannel invStockChannel : queryResult.getResult()) {
		// if (privilegeData.getResult().getEd_channel_id()
		// .contains(invStockChannel.getChannelCode())) {
		// chennelsData.add((invStockChannel));
		// }
		// }
		// result.setData(chennelsData);
		// } else {
		// result.setData(queryResult.getResult());
		// }
		result.setData(queryResult.getResult());
		// 690电商渠道处理
		// if (privilegeData.getResult().getEd_channel_id().contains("DS")) {
		// InvStockChannel invStockChannel = new InvStockChannel();
		// invStockChannel.setChannelCode("DS");
		// invStockChannel.setName("690电商渠道");
		// result.getData().add(invStockChannel);
		// }
		return result;
	}

	/**
	 * 产品组
	 * 
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = { "/getProductGroupByAuth" }, method = { RequestMethod.GET })
	@ResponseBody
	HttpJsonResult<List<ItemAttribute>> getProductGroup(
			HttpServletRequest request) {
		HttpJsonResult<List<ItemAttribute>> result = new HttpJsonResult<List<ItemAttribute>>();

		ServiceResult<List<ItemAttribute>> queryResult = t2OrderService
				.queryProductGroupByCbsCategory(null);
		// queryResult
		// groups.add(0, "全部");

		// 权限定义产品组取得
		// ServiceResult<PrivilegeItem> privilegeData = purchaseCommonService
		// .getPrivilege(String.valueOf(WebUtil.currentUserId(request)));
		// // 删除没有权限的产品组
		// List<ItemAttribute> productGroupData = new
		// ArrayList<ItemAttribute>();
		// if (privilegeData.getSuccess() == true && privilegeData.getResult()
		// != null) {
		// for (ItemAttribute itemAttribute : queryResult.getResult()) {
		// if (privilegeData.getResult().getProduct_group_id()
		// .contains(itemAttribute.getValue())) {
		// productGroupData.add((itemAttribute));
		// }
		// }
		// result.setData(productGroupData);
		// } else {
		// result.setData(queryResult.getResult());
		// }
		result.setData(queryResult.getResult());
		return result;
	}

	/**
	 * 通过数据分类查询流程标识list
	 *
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = { "/getByValueSetId" }, method = { RequestMethod.GET })
	@ResponseBody
	HttpJsonResult<List<DataDictionary>> getByValueSetId(
			HttpServletRequest request) {

		HttpJsonResult<List<DataDictionary>> result = new HttpJsonResult<List<DataDictionary>>();
		List<DataDictionary> list = commPurchase.getByValueSetId(
				dataDictionaryService, request.getParameter("valueSetId"));
		result.setData(list);
		return result;
	}


	@ResponseBody
    @RequestMapping(value = { "getStatus" }, method = { RequestMethod.GET })
    HttpJsonResult<List<DataDictionary>> getStatus(HttpServletRequest request) {

        HttpJsonResult<List<DataDictionary>> result = new HttpJsonResult<List<DataDictionary>>();

        List<DataDictionary> list = new ArrayList<DataDictionary>();
        DataDictionary dict = new DataDictionary();
        dict.setValue("0");
        dict.setValue_meaning("款先直发订单");
        list.add(dict);
        dict = new DataDictionary();
        dict.setValue("1");
        dict.setValue_meaning("T+2订单");
        list.add(dict);
		dict = new DataDictionary();
		dict.setValue("2");
		dict.setValue_meaning("CRM手工采购订单");
		list.add(dict);
        result.setData(list);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = {"findPurchaseLog"})
    public String findPurchaseLog(
            @RequestParam(required = false) String logName,
            @RequestParam(required = false) String logCategory,
            @RequestParam(required = false) String logMessage,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false) Integer rows,
            @RequestParam(required = false) Integer page,
            HttpServletRequest request,HttpServletResponse response){
	    String s = null;
        try {
            if (rows == null)
                rows = 20;
            if (page == null)
                page = 1;
            Map<String, Object> params = new HashMap<String, Object>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (endTime != null && !"".equals(endTime)) {
                Date eDate = sdf.parse(endTime);
                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(eDate);
                rightNow.add(Calendar.DAY_OF_YEAR, 1);
                String str = sdf.format(rightNow.getTime());
                params.put("endTime", str);
            }

            params.put("interfaceName", logName);
            params.put("interfaceCategory", logCategory);
            params.put("interfaceMessage", logMessage);
            params.put("startTime", startTime);
            params.put("m", (page - 1) * rows);
            params.put("n", rows);

            ServiceResult<List<T2OrderInterfaceLog>> result = t2OrderService.findPurchaseLog(params);

            Map<String, Object> retMap = new HashMap<String, Object>();
//            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            GsonBuilder gb =new GsonBuilder();
            gb.disableHtmlEscaping();
            retMap.put("rows", result.getResult());
            retMap.put("total", result.getPager().getRowsCount());

//            response.addHeader("Content-type", "text/html;charset=utf-8");
//            response.getWriter().write(gb.create().toJson(retMap));
//            response.getWriter().flush();
//            response.getWriter().close();
             s = gb.create().toJson(retMap);

//        } catch (IOException e) {
//            logger.error("", e);
//            throw new BusinessException("失败" + e.getMessage());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return s;
    }

    @ResponseBody
    @RequestMapping(value = { "t2OrderAudit" }, method = { RequestMethod.POST })
    public String t2OrderAudit(
//            @RequestParam(required = false) String reviewFlag,//0
//            @RequestParam(required = false) String reviewData,//WP单号
//            @RequestParam(required = false) String audit_remark,//备注
            @RequestParam(required = false) String storageId,//库位码
            @RequestParam(required = false) String materialsId,//物料号
            @RequestParam(required = false) String t2DeliveryPrediction){//T+2订单数量

        // 警告信息集合
        String MsgList = "";
        StringBuffer sb = new StringBuffer();

        //库位码传参非空验证
        if (storageId == null || "".equals(storageId)){
            MsgList = "{\"CODE\": \"N\",\"ReturnMsg\": \"\",\"Vblen\": \"库位码不能为空\"}";
            sb.append(MsgList);
            return sb.toString();
        }
        //物料号传参非空验证
        if (materialsId == null || "".equals(materialsId)){
            MsgList = "{\"CODE\": \"N\",\"ReturnMsg\": \"\",\"Vblen\": \"物料号不能为空\"}";
            sb.append(MsgList);
            return sb.toString();
        }
        //T+2传参非空验证
        if (t2DeliveryPrediction == null || "".equals(t2DeliveryPrediction)){
            MsgList = "{\"CODE\": \"N\",\"ReturnMsg\": \"\",\"Vblen\": \"T+2订单数量不能为空!\"}";
            sb.append(MsgList);
            return sb.toString();
        }

        // 渠道
        String edChannelName = "天猫渠道";
        // 定制 导入项 (0：否 1：是)
        String customization = "0";
        // 库存满足方式  在库
        String satisfyType = "1";
        // 订单类型编码  日常
        String orderType = "2";
        // 订单类别描述 款先直发订单
        String order_category = "3";

        // 根据物料号取得产品组
        String department = commPurchase.getDepartmentByMaterialCode(materialsId);

        // 渠道和产品组数据存入HashMap
        Map<String, String> productgroupmap = new HashMap<String, String>();
        // 取得所有产品组
        productgroupmap = t2OrderService.getProductMap(productgroupmap);

        // 产品组名称正确性判断
        if (productgroupmap.get(department) == null) {
            MsgList = "{\"CODE\": \"N\",\"ReturnMsg\": \"\",\"Vblen\": \"很抱歉！你所传数据的产品组【"+ department + "】不正确! 请核查！\"}";
            sb.append(MsgList);
            return sb.toString();
        }

        // 通过物料号取得品牌型号Map
        Map<String, String> itemBaseMap = commPurchase.getItemBaseMap(materialsId);
        // 在品牌型号Map中取得型号
        String materialDesc = itemBaseMap.get("material_description");
        // 型号非空时，正确性判断
        // 型号判断，为空则用根据物料号取得的materialDesc，否则要与取得的materialDesc相同
        if (materialDesc == null || "".equals(materialDesc)) {
            MsgList = "{\"CODE\": \"N\",\"ReturnMsg\": \"\",\"Vblen\": \"很抱歉！你所传数据的型号【"+ materialDesc + "】不正确! 请核查！\"}";
            sb.append(MsgList);
            return sb.toString();
        }

        List<T2OrderItem> t2OrderItems = new ArrayList<T2OrderItem>();
        T2OrderItem t2OrderItem = new T2OrderItem();

        // 订单号取得
        if ("FD,LA,LB,LC".contains(department)) {
            // 在库的场合
            if ("1".equals(satisfyType)) {
                t2OrderItem.setOrder_id(commPurchase.getWPOrderId(
                        purchaseCommonService, "C99"));
                // 补货的场合
            } else if ("2".equals(satisfyType)) {
                t2OrderItem.setOrder_id(commPurchase.getWPOrderId(
                        purchaseCommonService, "C98"));
            }
        } else {
            if ("1".equals(order_category)) {
                t2OrderItem.setOrder_id(commPurchase.getWPOrderId(
                        purchaseCommonService, "C10"));
            } else if ("2".equals(order_category)) {
                t2OrderItem.setOrder_id(commPurchase.getWPOrderId(
                        purchaseCommonService, "C11"));
            } else if ("3".equals(order_category)){
                t2OrderItem.setOrder_id(commPurchase.getWPOrderId(
                        purchaseCommonService, "C10"));
            }else {
                t2OrderItem.setOrder_id(commPurchase.getWPOrderId(
                        purchaseCommonService, "C01"));
            }
        }

        // 渠道数据存入HashMap
        Map<String, String> invstockchannelmap = new HashMap<String, String>();
        // 取得渠道数据
        invstockchannelmap = t2OrderService.getChannelMapByName(invstockchannelmap);
        String channelCode = invstockchannelmap.get(edChannelName);

        InvWarehouse invWarehouse = stockPurchaseBaseCommonService.getWhByEstorgeId(storageId).getResult();

        // 模板提供项
        t2OrderItem.setEd_channel_id(channelCode);
        t2OrderItem.setEd_channel_name(edChannelName);
        t2OrderItem.setProduct_group_id(department);
        t2OrderItem.setStorage_id(storageId);
        t2OrderItem.setStorage_name(invWarehouse.getEstorge_name());
        t2OrderItem.setMaterials_id(materialsId);
        t2OrderItem.setMaterials_desc(materialDesc);
        t2OrderItem.setT2_delivery_prediction(ConvertUtil.toDecimal(
                t2DeliveryPrediction, null));
        t2OrderItem.setCustomization(Integer.parseInt(customization));
        t2OrderItem.setOrder_category(Integer.valueOf(order_category));
        if (!StringUtil.isEmpty(satisfyType, true)) {
            t2OrderItem.setSatisfy_type_id(Integer
                    .parseInt(satisfyType));
        }
        if (!StringUtil.isEmpty(order_category, true)) {
            t2OrderItem.setOrder_type_id(Integer
                    .parseInt(order_category));
        } else {
            // 订单类型导入时，默认为“日常”
            t2OrderItem.setOrder_type_id(Integer
                    .parseInt(ORDER_TYPE_DAILY));
        }
        Map<String, String> categoryMap = new HashMap<String, String>();
        commPurchase.getCategoryMap(categoryMap);
        String categoryId = categoryMap.get(department);
        // 模板之外插入项
//        t2OrderItem.setCreate_user(createUser);
        t2OrderItem.setBrand_id(itemBaseMap.get("brand_code"));
        t2OrderItem.setCategory_id(categoryId);
//        t2OrderItem.setReport_year_week("201813");
		SimpleDateFormat t2_year_week = new SimpleDateFormat("yyyy-MM-dd");
        t2OrderItem.setReport_year_week(WSUtils.getWeekOfYear_Sunday(t2_year_week.format(new Date()),"yyyy-MM-dd", "0"));

        // 工贸编码
        t2OrderItem.setIndustry_trade_id(invWarehouse.getIndustry_trade_id());
        // 工贸描述
        t2OrderItem.setIndustry_trade_desc(invWarehouse.getIndustry_trade_desc());



        // 管理客户编码
        t2OrderItem.setCustom_id(invWarehouse.getCustom_id());
        // 管理客户描述
        t2OrderItem.setCustom_desc(invWarehouse.getCustom_desc());



        // 送达方编码
        t2OrderItem.setTransmit_id(invWarehouse.getTransmit_id());
        // 送达方描述
        t2OrderItem.setTransmit_desc(invWarehouse.getTransmit_desc());



        // 配送中心编码
        t2OrderItem.setDistribution_center_id(invWarehouse.getRrs_distribution_id());
        // 配送中心描述
        t2OrderItem
                .setDistribution_center_desc(invWarehouse.getRrs_distribution_name());

        Map<String, List<InvRrsWarehouse>> estorageCache = new HashMap<String, List<InvRrsWarehouse>>();
        List<InvRrsWarehouse> l_estorage = estorageCache.get(storageId);
        if (l_estorage == null || l_estorage.size() == 0) {
            Map<String, Object> invRrsWarehouseParams = new HashMap<String, Object>();
            invRrsWarehouseParams.put("estorge_id", storageId);
            invRrsWarehouseParams.put("t2_default", T2_DEFAULT_YES);
            ServiceResult<List<InvRrsWarehouse>> invRrsWarehouseResult = stockPurchaseBaseCommonService
                    .getRrsWhByEstorgeId(invRrsWarehouseParams);
            System.out.println(invRrsWarehouseResult.getMessage());
            if (invRrsWarehouseResult.getSuccess()
                    && invRrsWarehouseResult.getResult() != null
                    && invRrsWarehouseResult.getResult().size() > 0) {
                estorageCache.put(storageId,
                        invRrsWarehouseResult.getResult());
                l_estorage = invRrsWarehouseResult.getResult();
            }
        }
        // 到货库位编码
        String arrival_storage_id = "";
        // 到货库位描述
        String arrival_storage_desc = "";
        if (l_estorage != null && l_estorage.size() > 0) {
            // 到货库位编码
            arrival_storage_id = l_estorage.get(0).getRrs_wh_code();
            // 到货库位描述
            arrival_storage_desc = l_estorage.get(0).getRrs_wh_name();
        }

        // 到货库位编码
        t2OrderItem.setArrival_storage_id(arrival_storage_id);
        // 到货库位描述
        t2OrderItem.setArrival_storage_desc(arrival_storage_desc);

        // 电商付款方编码
        String payment_id = "";
        // 电商付款方名称
        String payment_name = "";

        if (StringUtils.isNotEmpty(channelCode)
                && (channelCode.equalsIgnoreCase("SC") || channelCode
                .equalsIgnoreCase("RS"))) {
            payment_name = "海尔集团电子商务有限公司(顺逛全产业)";
            payment_id = "C200130028";
        } else {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("productName", productgroupmap.get(department));
            List<ProductPayment> ppList = purchaseProductPaymentService
                    .findPaymentNameByCode(params);
            if (ppList.size() > 0) {
                payment_name = ppList.get(0).getPayMentName();
                payment_id = ppList.get(0).getPayMentCode();
            }
        }

        // 电商付款方编码
        t2OrderItem.setPayment_id(payment_id);
        // 电商付款方名称
        t2OrderItem.setPayment_name(payment_name);

        // 单价
        BigDecimal price = new BigDecimal(0);
        // 金额
        BigDecimal amount = new BigDecimal(0);

        // 取得样表价格
        price = new BigDecimal(itemBaseMap.get("price"));

        // 计算金额
        if (null != ConvertUtil.toDecimal(t2DeliveryPrediction, null)) {
            amount = price.multiply(ConvertUtil.toDecimal(
                    t2DeliveryPrediction, null));
        }

        // 单价
        if (null != String.valueOf(price)
                && !"".equals(String.valueOf(price))
                && !"0".equals(String.valueOf(price))) {
            t2OrderItem.setPrice(price);
        }
        // 金额
        if (null != String.valueOf(amount)
                && !"".equals(String.valueOf(amount))
                && !"0".equals(String.valueOf(amount))) {
            t2OrderItem.setAmount(amount);
        }
        //T+2订单状态 要求直接走内部供应链审核流程 5：待内部审核
        t2OrderItem.setFlow_flag(5);
		String wpOrderId = t2OrderItem.getOrder_id();
        t2OrderItems.add(t2OrderItem);
//        String wpOrderId = t2OrderService.insert(t2OrderItems);

        long startTime = System.currentTimeMillis();
        ServiceResult<Map<String, Integer>> insResult = t2OrderModel
                .insertT2Order(t2OrderItems);
        long endTime = System.currentTimeMillis();
        long totalEndTime = System.currentTimeMillis();

        if (insResult.getResult().get("success") == null || insResult.getResult().get("success") == 0){
            MsgList = "{\"CODE\": \"N\",\"ReturnMsg\": \"\",\"Vblen\": \"T+2订单创建失败!\"}";
            sb.append(MsgList);
            return sb.toString();
        }
        //前期订单数据完成  开始进行内部审核流程
        List<String> reviewList = new ArrayList<String>();
        List<String> t2ReviewList = new ArrayList<String>();
        List<T2OrderItem> pedReviewList = new ArrayList<T2OrderItem>();
        List<T2OrderItem> ensReviewList = new ArrayList<T2OrderItem>();
        // 审核通过FLAG（10:审核通过 -10：作废）
        String review_flag = "";
        String T2_report_year_week = "";
        String T3_report_year_week = "";
//        if ("0".equals(reviewFlag)) {
            review_flag = "5";
            // T+3提报周和T+2提报周三取得
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date currentDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(currentDate);
            T2_report_year_week = CommUtil.getWeekOfYear_Sunday(
                    sdf.format(cal.getTime()), null, "0");
            cal.add(Calendar.DATE, -7);
            T3_report_year_week = CommUtil.getWeekOfYear_Sunday(
                    sdf.format(cal.getTime()), null, "0");
//        } else {
//            review_flag = "-10";
//        }
        wpOrderId = "[\"" + wpOrderId + "\"]";
        // 审核状态判断
        if (wpOrderId != null && !"[]".equals(wpOrderId))
            try {
    //                 取得提交数据
                JSONArray reviewjson = (JSONArray) JSON.parse(wpOrderId);
    //                 JSONArray转化为list
                for (int i = 0; i < reviewjson.length(); i++) {
                    reviewList.add((reviewjson.getString(i)));
                }

            } catch (ParseException e) {
                e.printStackTrace();
                logger.error("JSON转换失败！ 错误：" + e.getMessage());
            }
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("order_list", reviewList);
        // 调用业务Service取得所有订单
        ServiceResult<List<T2OrderItem>> queryresult = t2OrderService
                .getT2OrderList(paramsMap);
        if (queryresult.getSuccess() && queryresult.getResult() != null) {
            for (T2OrderItem t2Order : queryresult.getResult()) {
                if (t2Order.getOrder_category() == 0) {
                    t2ReviewList.add(t2Order.getOrder_id());
                }else if (t2Order.getOrder_category() == 1) {
                    pedReviewList.add(t2Order);
                }else if (t2Order.getOrder_category() == 2) {
                    ensReviewList.add(t2Order);
                }else if (t2Order.getOrder_category() == 3){
                    pedReviewList.add(t2Order);
                }
            }
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("order_list", pedReviewList);
        params.put("audit_user", "");
        params.put("mark", "10");
        Map<String, Object> reviewMap = new HashMap<String, Object>();
        // 手工采购
        ServiceResult<List<String>> insWAOBTCresult = t2OrderService
                .insertWAOrderBillToCRM(params);
        if (insWAOBTCresult.getSuccess()
                && insWAOBTCresult.getResult() != null) {
            reviewMap.put("reviewList", insWAOBTCresult.getResult());
            reviewMap.put("audit_user", "");
            reviewMap.put("flow_flag", review_flag);
            reviewMap.put("audit_remark", "");
            reviewMap.put("T2_report_year_week", T2_report_year_week);
            reviewMap.put("T3_report_year_week", T3_report_year_week);
            // 订单审核状态更新
            ServiceResult<Boolean> reviewResult = t2OrderService
                    .reviewKXOrderList(reviewMap);
            System.out.println(reviewResult.getMessage());
            if (reviewResult.getResult() != null) {
                if (reviewResult.getSuccess() && reviewResult.getResult()) {
                    MsgList = "{\"CODE\": \"Y\",\"ReturnMsg\": " + wpOrderId + ",\"Vblen\": " + reviewResult.getMessage() + "}";
                    sb.append(MsgList);
                } else {
                    MsgList = "{\"CODE\": \"N\",\"ReturnMsg\": " + wpOrderId + ",\"Vblen\": " + reviewResult.getMessage() + "}";
                    sb.append(MsgList);
                    return sb.toString();
                }
            } else {
                MsgList = "{\"CODE\": \"N\",\"ReturnMsg\": " + wpOrderId + ",\"Vblen\": " + reviewResult.getMessage() + "}";
                sb.append(MsgList);
                return sb.toString();
            }
        }else {
            MsgList = "{\"CODE\": \"N\",\"ReturnMsg\": "+wpOrderId+",\"Vblen\": "+insWAOBTCresult.getMessage()+"}";
            sb.append(MsgList);
            return sb.toString();
        }

        return sb.toString();
    }


	@RequestMapping(value = {"/exportT2OrderInterfaceLogList.export"})
	public void exportT2OrderInterfaceLog(@RequestParam(required = false) String purchaseLogList,
								   HttpServletRequest request, HttpServletResponse response) {

		com.alibaba.dubbo.common.json.JSONArray exportJson = new com.alibaba.dubbo.common.json.JSONArray();
		ArrayList<Long> exportArray = null;
		try {
			if (purchaseLogList != null && !purchaseLogList.equals("")) {
				exportJson = (com.alibaba.dubbo.common.json.JSONArray) JSON
						.parse(purchaseLogList);
				exportArray = new ArrayList<Long>(exportJson.length());
				// JSONArray转化为list
				for (int i = 0; i < exportJson.length(); i++) {
					exportArray.add((Long) exportJson.get(i));
				}
			}
		} catch (com.alibaba.dubbo.common.json.ParseException e1) {
			e1.printStackTrace();
			logger.error("JSON转换失败！ 错误：" + e1.getMessage());
		}

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("purchaseLogList",exportArray);

		HSSFWorkbook  wb = getT2OrderInterfaceLogData(map);

		SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=new Date();
		String str=sdf.format(date);
		String fileName = "采购接口日志列表"+str;

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);

			ExportExcelUtil.exportCommon(is,fileName,response);
		} catch (IOException e) {
			logger.error("错误", e);
		}
	}

	public HSSFWorkbook getT2OrderInterfaceLogData(Map<String,Object> map) {

        ServiceResult<List<T2OrderInterfaceLog>> result = t2OrderService.findPurchaseLog(map);
		List<T2OrderInterfaceLog> List = new ArrayList<T2OrderInterfaceLog>();
		if (result.getSuccess() && result.getResult() != null) {
			List = result.getResult();
		}
		// 1.创建一个workbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 2.在workbook中添加一个sheet，对应Excel中的一个sheet
		HSSFSheet sheet = wb.createSheet("采购接口日志列表");
		int length = ExportT2OrderInterfaceLog.t2OrderInterfaceLogListTitle.length;
		for (int i = 0; i <length; i++) {
			sheet.setColumnWidth(i, (int)(21.57*256));
		}

		// 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 4.创建单元格，设置值表头，设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		// 居中格式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边
		// 设置表头
		for(int i=0;length-1>=i;i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(ExportT2OrderInterfaceLog.t2OrderInterfaceLogListTitle[i]);
			cell.setCellStyle(style);
		}

		//向单元格里添加数据
		for(short i=0;i<List.size();i++){
			row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(List.get(i).getInterfaceId());
            row.createCell(1).setCellValue(List.get(i).getInterfaceName());
            row.createCell(2).setCellValue(List.get(i).getInterfaceCategory());
			SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
			SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				row.createCell(3).setCellValue(sdf2.format(sdf1.parse(List.get(i).getInterfaceDate().toString())));
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}

//			row.createCell(3).setCellValue(List.get(i).getInterfaceDate());
            row.createCell(4).setCellValue(List.get(i).getInterfaceMessage());
		}
		return wb;
	}

}
