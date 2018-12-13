package com.haier.svc.api.controller.purchase;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.dubbo.common.json.JSON;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.ConvertUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.purchase.data.model.BdmOrder;
import com.haier.purchase.data.model.PredictingStockItem;
import com.haier.purchase.data.model.PrivilegeItem;
import com.haier.purchase.data.model.RuntimeParametersVO;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.service.StockCommonService;
import com.haier.svc.api.controller.util.CommUtil;
import com.haier.svc.api.controller.util.ExcelCallbackInterfaceUtil;
import com.haier.svc.api.controller.util.ExcelExportUtil;
import com.haier.svc.api.controller.util.ExcelReader;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;
import com.haier.svc.api.service.CommPurchase;
import com.haier.svc.service.BdmOrderService;
import com.haier.svc.service.DataDictionaryService;
import com.haier.svc.service.GateService;
import com.haier.svc.service.ItemService;
import com.haier.svc.service.LogAuditService;
import com.haier.svc.service.PredictingStockService;
import com.haier.svc.service.PurchaseCommonService;
import com.haier.svc.service.RuntimeService;
import com.haier.svc.service.T2OrderService;

import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/4/23 15:28
 * @modificed by:
 */
@RequestMapping(value = "/purchase")
@Controller
public class StockPredictingController {

  private final static org.apache.log4j.Logger logger = LogManager
      .getLogger(StockPredictingController.class);

  //导入模板表头信息
  private static final String checkStr = "序号,提报周,渠道,产品组,物料编号,品牌,型号,T+3周,T+4周,T+5周,T+6周,T+7周,"
  		+ "T+8周,T+9周,T+10周,T+11周,T+12周,T+13周,状态,失败信息";

  @Resource(name = "purchaseCommonService")
  private PurchaseCommonService purchaseCommonService;

  @Resource(name = "t2OrderService")
  private T2OrderService t2OrderService;

  @Resource(name = "stockCommonService")
  private StockCommonService stockCommonService;

  @Resource(name = "dataDictionaryService")
  private DataDictionaryService dataDictionaryService;

  @Resource(name = "predictingStockService")
  private PredictingStockService predictingStockService;

  @Resource(name = "gateService")
  private GateService gateService;

  @Resource(name = "logAuditService")
  private LogAuditService logAuditService;

  @Resource(name = "itemService")
  private ItemService itemService;

  @Resource(name = "runtimeService")
  private RuntimeService runtimeService;

  @Resource(name = "bdmOrderService")
  private BdmOrderService bdmOrderService;
  
  @Autowired
	CommPurchase commPurchase;

  /**
   * T+3订单状态
   */
  private static final String T3_ORDER_STATUS = "predict_stock_flow_flag";

  /**
   * 预测备料查询页面调转
   *
   * @param modelMap 调转页面数据存放map
   */
  @RequestMapping(value = {"predictingStockList"}, method = {RequestMethod.GET})
  String predictingStockList(HttpServletRequest request, Map<String, Object> modelMap) {
	  System.out.println("预测备料查询页面调转");
    try {
		//当前日期取得
		String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
		//转化成周
		String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null, "1");
		modelMap.put("currentweek", currentWeek);
		//取得填报负责人
		String createUser = String.valueOf(WebUtil.currentUserName(request));
		modelMap.put("createuser", createUser);
		Map<String, Object> authMap = new HashMap<>();
		commPurchase.getAuthMap(request, "", "", "", authMap);
		try {
			modelMap.put("authMap", JSON.json(authMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
    return "purchase/predictingStockList";
  }
  
  /**
	 * 跳转查看页面
	 * @param report_year_week 填报周
	 * @param start_week 开始周
	 * @param end_week 结束周
	 * @param product_group_id 产品组ID
	 * @param ed_channel_id 渠道ID
	 * @param create_user 填报人
	 * @return predictingStockDetailList
	 */
	@RequestMapping(value = { "gotoQueryDetail" }, method = { RequestMethod.GET })
	String countStockAgesWithChannelsBySku(HttpServletRequest request,
										   @RequestParam(required = false) String report_year_week,
										   @RequestParam(required = false) String start_week,
										   @RequestParam(required = false) String end_week,
										   @RequestParam(required = false) String product_group_id,
										   @RequestParam(required = false) String ed_channel_id,
										   @RequestParam(required = false) String create_user,
										   Map<String, Object> modelMap) {
		modelMap.put("report_year_week", report_year_week);
		modelMap.put("start_week", start_week);
		modelMap.put("end_week", end_week);
		modelMap.put("product_group_id", product_group_id);
		modelMap.put("ed_channel_id", ed_channel_id);
		modelMap.put("create_user", create_user);
		String url = request.getHeader("referer");
		modelMap.put("url", url);
		return "purchase/predictingStockDetailList";
	}
	
	/**
	 * 在查看页面中为datagrid加载值
	 * @param report_year_week 填报周
	 * @param start_week 开始周
	 * @param end_week 结束周
	 * @param product_group_id 产品组ID
	 * @param ed_channel_id 渠道ID
	 * @param create_user 填报人
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = { "findPredictingStockDetailList" }, method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> find(@RequestParam(required = false) String report_year_week,
					 @RequestParam(required = false) String start_week,
					 @RequestParam(required = false) String end_week,
					 @RequestParam(required = false) String product_group_id,
					 @RequestParam(required = false) String ed_channel_id,
					 @RequestParam(required = false) String flow_flag,
					 @RequestParam(required = false) Integer rows,
					 @RequestParam(required = false) Integer page, HttpServletRequest request,
					 HttpServletResponse response,
					 Map<String, Object> modelMap) throws IOException {
		try {
			if (rows == null)
				rows = 20;
			if (page == null)
				page = 1;
			// 权限Map
			Map<String, Object> authMap = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(product_group_id))
				authMap.put("productGroup", new String[]{product_group_id});
			if(StringUtils.isNotBlank(ed_channel_id))
				authMap.put("channel", new String[]{ed_channel_id});
			//取得产品组权限List和渠道权限List
			commPurchase.getAuthMap( request, product_group_id, ed_channel_id,
				null, authMap);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("report_year_week", report_year_week);
			params.put("start_week", start_week);
			params.put("end_week", end_week);
			params.put("product_group_id", authMap.get("productGroup"));
			params.put("ed_channel_id", authMap.get("channel"));
			params.put("m", (page - 1) * rows);
			params.put("n", rows);
			//flow_flag转化为数组
			String[] flow_flag_list = null;
			if (flow_flag != null && !"".equals(flow_flag)) {
				if (flow_flag.contains("ALL")) {
					flow_flag_list = null;
				} else {
					flow_flag_list = flow_flag.split(",");
				}
			}
			params.put("flow_flag_list", flow_flag_list);

			ServiceResult<List<PredictingStockItem>> result = new ServiceResult<List<PredictingStockItem>>();

			//获取流程状态map
			Map<String, String> flowFlagMap = CommPurchase.getValueMeaningMap(dataDictionaryService,
				T3_ORDER_STATUS);

			//定义产品组HashMap
			Map<String, String> productgroupmap = new HashMap<String, String>();
			//定义渠道HashMap
			Map<String, String> invstockchannelmap = new HashMap<String, String>();
			//定义品牌HashMap
			Map<String, String> brandMap = new HashMap<String, String>();
			//取得产品组
		      getProductMap(productgroupmap, t2OrderService);
		      //取得渠道
		      getChannelMap(invstockchannelmap, stockCommonService);
			invstockchannelmap.put("DS", "690电商渠道");
			//取得品牌
			getBrandMap(brandMap, t2OrderService);
			//在DB中检索详细信息
			result = predictingStockService.getPredictingStockDetail(params);

			//以详细信息为主题重新组织不同DB中检索出的数据
			List<PredictingStockItem> items = new ArrayList<PredictingStockItem>();
			if (result.getSuccess() && result.getResult() != null) {
				items = result.getResult();
				int index = 0;
				for (PredictingStockItem item : items) {
					index++;

					//获取品牌code
					String brandCode = getItemBaseMap(item.getMaterials_id(), t2OrderService).get("brand_code");

					item.setId(index);
					//根据渠道id取得渠道名
					item.setEd_channel_name(invstockchannelmap.get(item.getEd_channel_id()));
					//根据工作组id取得工作组名
					item.setProduct_group_name(productgroupmap.get(item.getProduct_group_id()));
					//编辑提报周
					item.setReport_year_week(CommUtil.weekTodate(item.getReport_year_week()));
					//根据物料号取得品牌
					item.setBrand_name(brandMap.get(brandCode));
					//根据物料号取得型号
					item.setMaterial_description(
						getItemBaseMap(item.getMaterials_id(), t2OrderService)
							.get("material_description"));
					//流程状态code转换
					item.setFlow_flag_name(flowFlagMap.get(String.valueOf(item.getFlow_flag())));
				}
			}

			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("total", result.getPager().getRowsCount());
			retMap.put("rows", items);
			return retMap;
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		}
		return new HashMap();
	}
	
	/**
	 * 预测备料通过日期获取当前周
	 * @param  日期（YYYY-MM-DD）
	 * @return 例子： 2014年01周
	 */
	@RequestMapping(value = { "/findateWeek" }, method = { RequestMethod.GET })
	@ResponseBody
	public HttpJsonResult<String> findateWeek(HttpServletRequest request,
											  HttpServletResponse response) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();
		String date = request.getParameter("date");
		String type = request.getParameter("type");
		String dataweekString = "";
		if ("0".equals(type)) {
			//周四为一周第一天
			dataweekString = CommUtil.getWeekOfYear_Sunday(date, null, "1");
		} else {
			//周日为一周第一天
			dataweekString = CommUtil.getWeekOfYear_Sunday_Normal(date, null, "1");
		}
		result.setData(dataweekString);
		return result;
	}

	/**
	 * 查看页面导出Excel
	 * @author DHC 刘骥飞
	 * @param request
	 * @param response
	 * @param report_year_week 填报周
	 * @param start_week 开始周
	 * @param end_week 结束周
	 * @param product_group_id 产品组ID
	 * @param ed_channel_id 渠道ID
	 * @param create_user 填报人
	 * @return 方法执行完毕调用的画面
	 */
	@RequestMapping(value = { "/exportPurchasePredictStockDetailList" })
	void exportBaseInventoryList(HttpServletRequest request, HttpServletResponse response,
								 @RequestParam(required = false) String report_year_week_hidden,
								 @RequestParam(required = false) String start_week_hidden,
								 @RequestParam(required = false) String end_week_hidden,
								 @RequestParam(required = false) String product_group_id_hidden,
								 @RequestParam(required = false) String ed_channel_id_hidden,
								 @RequestParam(required = false) String flow_flag_hidden,
								 Map<String, Object> modelMap) {
		// 权限Map
		Map<String, Object> authMap = new HashMap<String, Object>();
		//取得产品组权限List和渠道权限List
		commPurchase.getAuthMap(request, product_group_id_hidden,
			ed_channel_id_hidden, null, authMap);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("report_year_week", report_year_week_hidden);
		params.put("start_week", start_week_hidden);
		params.put("end_week", end_week_hidden);
		params.put("product_group_id", authMap.get("productGroup"));
		params.put("ed_channel_id", authMap.get("channel"));
		params.put("m", 0);
		params.put("n", 30000);

		//flow_flag转化为数组
		String[] flow_flag_list = null;
		if (flow_flag_hidden != null && !"".equals(flow_flag_hidden)) {
			if (flow_flag_hidden.contains("ALL")) {
				flow_flag_list = null;
			} else {
				flow_flag_list = flow_flag_hidden.split(",");
			}
		}
		params.put("flow_flag_list", flow_flag_list);
		final List<PredictingStockItem> items = getDetailsData(params).getResult();
		modelMap.put("rowList", items);
		String fileName = "预测备料数据导出";
		String sheetName = "导出数据";
		String[] sheetHead = new String[] { "序号", "提报周", "渠道", "产品组", "物料编号", "品牌", "型号", "T+3周",
				"T+4周", "T+5周", "T+6周", "T+7周", "T+8周", "T+9周",  "T+10周", 
				"T+11周",  "T+12周",  "T+13周", "状态", "失败信息" };
		ExcelCallbackInterfaceUtil eciu = new ExcelCallbackInterfaceUtil() {

			@Override
			public void setExcelBodyTotal(OutputStream os, WritableSheet sheet,
										  int temp) throws Exception {
				setExcelBodyTotalForPredictingStock(sheet, temp, items);
			}

		};
		try {
			ExcelExportUtil.exportEntity(logger, request, response, fileName, sheetName, sheetHead, eciu);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	/**
 	 * 导出具体数据，实现回调函数
 	 * @param sheet
 	 * @param temp 行号
 	 * @param list 传入需要导出的entity list
 	 */
 	private void setExcelBodyTotalForPredictingStock(WritableSheet sheet, int temp,
 													 List<PredictingStockItem> list) throws Exception {
 
 		WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
 			UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
 		WritableCellFormat textFormat = new WritableCellFormat(font);
 		textFormat.setAlignment(Alignment.CENTRE);
 		textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
 
 		DisplayFormat displayFormat = NumberFormats.INTEGER;
 		WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
 		numberFormat.setAlignment(jxl.format.Alignment.RIGHT);
 		numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
 
 		int index = 0;
 		for (PredictingStockItem predictingStockItem : list) {
 			index++;
 			//jxl.write.Number(列号,行号 ,内容 )
 			// "序号", "提报周", "渠道", "产品组", "物料编号", "品牌", "型号", "T+3周", "T+4周", "T+5周", "T+6周", "T+7周", "T+8周", "T+9周", "T+10周", "T+11周", "T+12周", "T+13周"
 			sheet.setColumnView(0, 10);
 			sheet.addCell(new Label(0, temp, String.valueOf(index), textFormat));
 			sheet.setColumnView(1, 30);
 			sheet
 				.addCell(new Label(1, temp, predictingStockItem.getReport_year_week(), textFormat));
 			sheet.setColumnView(2, 15);
 			sheet.addCell(new Label(2, temp, predictingStockItem.getEd_channel_name(), textFormat));
 			sheet.setColumnView(3, 15);
 			sheet.addCell(
 				new Label(3, temp, predictingStockItem.getProduct_group_name(), textFormat));
 			sheet.setColumnView(4, 15);
 			sheet.addCell(new Label(4, temp, predictingStockItem.getMaterials_id(), textFormat));
 			sheet.setColumnView(5, 15);
 			sheet.addCell(new Label(5, temp, predictingStockItem.getBrand_name(), textFormat));
 			sheet.setColumnView(6, 30);
 			sheet.addCell(
 				new Label(6, temp, predictingStockItem.getMaterial_description(), textFormat));
 			sheet.setColumnView(7, 15);
 			sheet.addCell(new jxl.write.Number(7, temp,
 				predictingStockItem.getT3_require_prediction(), numberFormat));
 			sheet.setColumnView(8, 15);
 			sheet.addCell(new jxl.write.Number(8, temp,
 				predictingStockItem.getT4_require_prediction(), numberFormat));
 			sheet.setColumnView(9, 15);
 			sheet.addCell(new jxl.write.Number(9, temp,
 				predictingStockItem.getT5_require_prediction(), numberFormat));
 			sheet.setColumnView(10, 15);
 			sheet.addCell(new jxl.write.Number(10, temp,
 				predictingStockItem.getT6_require_prediction(), numberFormat));
 			sheet.setColumnView(11, 15);
 			sheet.addCell(new jxl.write.Number(11, temp,
 				predictingStockItem.getT7_require_prediction(), numberFormat));
 			sheet.setColumnView(12, 15);
 			sheet.addCell(new jxl.write.Number(12, temp,
 				predictingStockItem.getT8_require_prediction(), numberFormat));
 			sheet.setColumnView(13, 15);
 			sheet.addCell(new jxl.write.Number(13, temp,
 				predictingStockItem.getT9_require_prediction(), numberFormat));
 			sheet.setColumnView(14, 15);
 			sheet.addCell(new jxl.write.Number(14, temp,
 				predictingStockItem.getT10_require_prediction(), numberFormat));
 			sheet.setColumnView(15, 15);
 			sheet.addCell(new jxl.write.Number(15, temp,
 				predictingStockItem.getT11_require_prediction(), numberFormat));
 			sheet.setColumnView(16, 15);
 			sheet.addCell(new jxl.write.Number(16, temp,
 				predictingStockItem.getT12_require_prediction(), numberFormat));
 			sheet.setColumnView(17, 15);
 			sheet.addCell(new jxl.write.Number(17, temp,
 				predictingStockItem.getT13_require_prediction(), numberFormat));
 			sheet.setColumnView(18, 15);
 			sheet.addCell(new Label(18, temp, predictingStockItem.getFlow_flag_name(), textFormat));
 			sheet.setColumnView(19, 30);
 			sheet.addCell(new Label(19, temp, predictingStockItem.getError_msg(), textFormat));
 			temp++;
 		}
 	}
	
	/**
	 * 查看页面数据处理（将不同DB中检索出的数据组织在一起）
	 * @param Map<String, Object> params 检索条件中的参数
	 * @return result 需要显示或导出数据结果
	 */
	public ServiceResult<List<PredictingStockItem>> getDetailsData(Map<String, Object> params) {

		ServiceResult<List<PredictingStockItem>> result = new ServiceResult<List<PredictingStockItem>>();

		//获取流程状态map
		Map<String, String> flowFlagMap = CommPurchase.getValueMeaningMap(dataDictionaryService,
			T3_ORDER_STATUS);

		//定义产品组HashMap
		Map<String, String> productgroupmap = new HashMap<String, String>();
		//定义渠道HashMap
		Map<String, String> invstockchannelmap = new HashMap<String, String>();
		//定义品牌HashMap
		Map<String, String> brandMap = new HashMap<String, String>();
		//取得产品组
		getProductMap(productgroupmap, t2OrderService);
		//取得渠道
		getChannelMap(invstockchannelmap, stockCommonService);
		invstockchannelmap.put("DS", "690电商渠道");
		//取得品牌
		getBrandMap(brandMap, t2OrderService);
		//在DB中检索详细信息
		result = predictingStockService.getPredictingStockDetail(params);

		//以详细信息为主题重新组织不同DB中检索出的数据
		List<PredictingStockItem> items = new ArrayList<PredictingStockItem>();
		if (result.getSuccess() && result.getResult() != null) {
			items = result.getResult();
			int index = 0;
			for (PredictingStockItem item : items) {
				index++;

				//获取品牌code
				String brandCode = getItemBaseMap(item.getMaterials_id(), t2OrderService).get("brand_code");

				item.setId(index);
				//根据渠道id取得渠道名
				item.setEd_channel_name(invstockchannelmap.get(item.getEd_channel_id()));
				//根据工作组id取得工作组名
				item.setProduct_group_name(productgroupmap.get(item.getProduct_group_id()));
				//编辑提报周
				item.setReport_year_week(CommUtil.weekTodate(item.getReport_year_week()));
				//根据物料号取得品牌
				item.setBrand_name(brandMap.get(brandCode));
				//根据物料号取得型号
				item.setMaterial_description(getItemBaseMap(item.getMaterials_id(), t2OrderService).get("material_description"));
				//流程状态code转换
				item.setFlow_flag_name(flowFlagMap.get(String.valueOf(item.getFlow_flag())));
			}
		}
		result.setResult(items);

		return result;
	}
	
  /**
   * 获取预测备料查询表单
   *
   * @param reportYearWeek 提报周
   * @param channel 渠道
   * @param productGroup 产品组
   * @param createUser 提报人
   * @param rows 行数
   * @param page 页数
   * @param page 页数
   */
  @ResponseBody
  @RequestMapping(value = {"findPredictingStockList"}, method = {RequestMethod.POST})
  public Map<String, Object> find(@RequestParam(required = false, name = "report_year_week") String reportYearWeek,
      @RequestParam(required = false) String channel,
      @RequestParam(required = false, name = "product_group") String productGroup,
      @RequestParam(required = false, name = "create_user") String createUser,
      @RequestParam(required = false) Integer rows,
      @RequestParam(required = false) Integer page, HttpServletRequest request,
      HttpServletResponse response) {
    try {
      if (rows == null) {
        rows = 20;
      }
      if (page == null) {
        page = 1;
      }
      // 权限Map
      Map<String, Object> authMap = new HashMap<String, Object>();
      if(StringUtils.isNotBlank(channel)){
    	  authMap.put("channel", new String[]{channel});
      }
      if(StringUtils.isNotBlank(productGroup)){
    	  authMap.put("productGroup", new String[]{productGroup});
      }
      //取得产品组权限List和渠道权限List
      commPurchase.getAuthMap(request, productGroup, channel, null,
          authMap);

      Map<String, Object> params = new HashMap<String, Object>();
      params.put("report_year_week", reportYearWeek.replace("年", "").replace("周", ""));
      params.put("ed_channel_id", authMap.get("channel"));
      params.put("product_group_id", authMap.get("productGroup"));
      params.put("create_user", createUser);
      params.put("m", (page - 1) * rows);
      params.put("n", rows);

      //渠道和产品组数据存入HashMap
      Map<String, String> productGroupMap = new HashMap<String, String>();//产品组
      Map<String, String> invStockChannelMap = new HashMap<String, String>();//渠道
      //取得产品组
      getProductMap(productGroupMap, t2OrderService);
      //取得渠道
      getChannelMap(invStockChannelMap, stockCommonService);
      invStockChannelMap.put("DS", "690电商渠道");
      //获取流程状态map
      Map<String, String> flowFlagMap = CommPurchase.getValueMeaningMap(dataDictionaryService,
          T3_ORDER_STATUS);
      //调用业务Service
      ServiceResult<List<PredictingStockItem>> result = predictingStockService
          .getPredictingStockList(params);
      if (result.getSuccess() && result.getResult() != null) {
        List<PredictingStockItem> predictstocklist = result.getResult();
        for (PredictingStockItem item : predictstocklist) {
          //根据渠道id取得渠道名
          item.setEd_channel_name(invStockChannelMap.get(item.getEd_channel_id()));
          //根据工作组id取得工作组名
          item.setProduct_group_name(productGroupMap.get(item.getProduct_group_id()));
          //编辑提报周
          item.setReport_year_week_display(
              CommUtil.weekTodate(item.getReport_year_week()));
          item.setFlow_flag_name(flowFlagMap.get(String.valueOf(item.getFlow_flag())));
        }
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("total", result.getPager().getRowsCount());
        retMap.put("rows", predictstocklist);
        return retMap;
//        response.getWriter().write(JsonUtil.toJson(retMap));
//        response.getWriter().flush();
//        response.getWriter().close();
      }
    } catch (Exception e) {
      logger.error("", e);
      e.printStackTrace();
//      throw new BusinessException("失败" + e.getMessage());
    }
    return new HashMap();
  }

  @RequestMapping(value = {"/importPredictingStock"}, method = {RequestMethod.GET})
  public String importPurchaseOrder(HttpServletRequest request,
      @RequestParam(required = false) String report_year_week,
      HttpServletResponse response, Map<String, Object> modelMap) {
    modelMap.put("report_year_week", report_year_week);
    String url = request.getHeader("referer");
    modelMap.put("url", url);
    return "purchase/importPredictingStock";
  }

  @RequestMapping(value = {"/importPredictingStockData"})
  @ResponseBody
  public HttpJsonResult<Map<String, Object>> importPredictingStockData(HttpServletRequest request,
      HttpServletResponse response,
      @RequestParam(required = false) String report_year_week,
      Map<String, Object> modelMap) {

    HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
    // 转型为MultipartHttpRequest
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

    // 权限Map
    Map<String, Object> authMap = new HashMap<String, Object>();
    //取得产品组权限List和渠道权限List
//    CommPurchase.getAuthMap(purchaseCommonService, request, null, null, null, authMap);
    //闸口不秒杀的场合，时间闸口check
//    if ("0".equals(authMap.get("gateLimit"))) {
//      //时间闸口check
//      if (!CommPurchase.isInTimeGate(gateService, "01")) {
//        result.setMessage("不在渠道上报期间内");
//        return result;
//      }
//    }

    // 获得文件
    //List<MultipartFile> files = multipartRequest.getFiles("file");
    MultipartFile file = multipartRequest.getFile("file");
    if (file == null) {
      result.setMessage("没有选择导入文件，请选择导入文件后再点击导入操作！");
      return result;
    }
    //警告信息集合
    String MsgList = "";
    StringBuffer sb = new StringBuffer();

    String fileName = file.getOriginalFilename();
    int index = fileName.lastIndexOf(".");
    String fileExtName = fileName.substring(index + 1);
    if (!fileExtName.equalsIgnoreCase("xls")) {
      result.setMessage("选择导入文件扩展名必须为xls!");
      return result;
    }
    int insCount = 0;
    int updCount = 0;
    int nullRow = 0;

    //判断用 产品组code
    String department = "";
    //判断用 产品组名称
    String departmentName = "";
    //判断用 渠道code
    String channelCode = "";
    //品类
    String categoryId = "";

    try {
      List<String[]> list = ExcelReader.readExcel(file.getInputStream(), 1);
      if (list.size() <= 1) {
        result.setMessage("很抱歉！你导入的Excel没有数据记录，请查看重新整理导入！");
        return result;
      }
      //验证模板表头信息
      String[] firstLineData = list.get(0);
      boolean flag = this.checkDataTemplate(firstLineData, checkStr);
      if (!flag) {
        result.setMessage("很抱歉！你导入的Excel数据格式与系统模板格式存在差异，请下载模板后重新导入！");
        return result;
      }
      //提报周   逻辑主KEY
      String reportYearWeek = report_year_week;
      //修改人 create_user
      String createUser = request.getSession().getAttribute("userName").toString();
      //品类数据存入HashMap
      Map<String, String> categoryMap = new HashMap<String, String>();
      //渠道数据存入HashMap
      Map<String, String> invstockchannelmap = new HashMap<String, String>();//渠道

      //调用stockCommonService，取得渠道数据
      ServiceResult<List<InvStockChannel>> resultChannel = stockCommonService.getChannels();
      if (resultChannel.getSuccess() && resultChannel.getResult() != null) {
        List<InvStockChannel> invStockChannel = resultChannel.getResult();
        for (InvStockChannel invstockchannel : invStockChannel) {
          invstockchannelmap.put(invstockchannel.getName(),
              invstockchannel.getChannelCode());//将name作为key，channelCode作为value存入map中
        }
      }
      invstockchannelmap.put("690电商渠道", "DS");

      //读取数据
      for (int i = 1; i < list.size(); i++) {
        String[] str = list.get(i);
        //提报周
        //String reportYearWeekImport = StringUtil.nullSafeString(str[1]).trim();
        //渠道 逻辑主KEY
        String edChannelName = StringUtil.nullSafeString(str[2]).trim();
        //产品组 逻辑主KEY
        String productGroupName = StringUtil.nullSafeString(str[3].trim());
        //物料号 逻辑主KEY
        String materialsId = StringUtil.nullSafeString(str[4]).trim();
        //品牌 非导入项
        String brand = StringUtil.nullSafeString(str[5].trim());
        //型号 非导入项
        String materialsDesc = StringUtil.nullSafeString(str[6]).trim();

        //T+3周需求预测
        String t3RequirePrediction = StringUtil.nullSafeString(str[7]).trim();
        String t4RequirePrediction = StringUtil.nullSafeString(str[8]).trim();
        String t5RequirePrediction = StringUtil.nullSafeString(str[9]).trim();
        String t6RequirePrediction = StringUtil.nullSafeString(str[10]).trim();
        String t7RequirePrediction = StringUtil.nullSafeString(str[11]).trim();
        String t8RequirePrediction = StringUtil.nullSafeString(str[12]).trim();
        String t9RequirePrediction = StringUtil.nullSafeString(str[13]).trim();
        String t10RequirePrediction = StringUtil.nullSafeString(str[14]).trim();
        String t11RequirePrediction = StringUtil.nullSafeString(str[15]).trim();
        String t12RequirePrediction = StringUtil.nullSafeString(str[16]).trim();
        String t13RequirePrediction = StringUtil.nullSafeString(str[17]).trim();

        boolean isAllNull = StringUtil.isEmpty(edChannelName, true)
            && StringUtil.isEmpty(productGroupName, true)
            && StringUtil.isEmpty(materialsId, true)
            && StringUtil.isEmpty(brand, true)
            && StringUtil.isEmpty(materialsDesc, true)
            && StringUtil.isEmpty(t3RequirePrediction, true)
            && StringUtil.isEmpty(t4RequirePrediction, true)
            && StringUtil.isEmpty(t5RequirePrediction, true)
            && StringUtil.isEmpty(t6RequirePrediction, true)
            && StringUtil.isEmpty(t7RequirePrediction, true)
            && StringUtil.isEmpty(t8RequirePrediction, true)
            && StringUtil.isEmpty(t9RequirePrediction, true)
            && StringUtil.isEmpty(t10RequirePrediction, true)
            && StringUtil.isEmpty(t11RequirePrediction, true)
            && StringUtil.isEmpty(t12RequirePrediction, true)
            && StringUtil.isEmpty(t13RequirePrediction, true);
        if (isAllNull) {
          nullRow++;
          continue;
        }
        //导入模板内容的非空判断****************START*******************************
        //渠道check
        if (StringUtil.isEmpty(edChannelName, true)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【渠道】不能为空! 请核查后重新导入！";

          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }

        //物料号check
        if (StringUtil.isEmpty(materialsId, true)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的物料号不能为空! 请核查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }
        //导入模板内容的非空判断****************END************************************

        //导入模板内容的合理性判断及权限判断**************START*************************
        //用渠道名称取得渠道code
        channelCode = invstockchannelmap.get(edChannelName);
        //渠道的正确性判断
        if ("".equals(channelCode) || channelCode == null) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的渠道【" + edChannelName
              + "】无法识别！请检查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }

        //产品组code取得
        department = getDepartmentByMaterialCode(materialsId, t2OrderService);
        //渠道和产品组数据存入HashMap
        Map<String, String> productgroupmap = new HashMap<String, String>();
        //取得产品组
        getProductMap(productgroupmap, t2OrderService);
        // //品类取得 根据产品组code
        getChannelMap(categoryMap, stockCommonService);
        categoryId = categoryMap.get(department);
        //判断用 产品组名称
        departmentName = productgroupmap.get(department);

        //产品组名称判断，为空则用根据物料号取得的departmentName，否则要与取得的departmentName相同
        if (!StringUtil.isEmpty(productGroupName, true)
            && !productGroupName.equals(departmentName)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的产品组【" + productGroupName
              + "】不正确! 请核查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }
        //物料号可识别判断
        if ("".equals(department) || department == null) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的物料号【" + materialsId
              + "】无法识别！请检查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }

        //bdm样表闸口校验
        try {
          ServiceResult<RuntimeParametersVO> vo = runtimeService
              .getRuntimeParameterByKey("is_bdm_enable");

          if (vo.getSuccess() && vo.getResult() != null
              && vo.getResult().getValue().equalsIgnoreCase("1")) {
            Map<String, Object> bdmMap = new HashMap<String, Object>();
            bdmMap.put("itemcode", materialsId);
            ServiceResult<List<BdmOrder>> bdmResult = bdmOrderService
                .findBdmOrder(bdmMap);
            //logger.info("BdmOrder:"+JsonUtil.toJson(bdmResult));
            if (!bdmResult.getSuccess() || bdmResult.getResult() == null
                || bdmResult.getResult().size() == 0) {
              MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的物料号【" + materialsId
                  + "】在BDM样表中无数据！请检查后重新导入！";
              if (StringUtil.isEmpty(MsgList, true)) {
                sb.append(MsgList);
              } else {
                MsgList = MsgList + "<br>";
                sb.append(MsgList);
              }
              continue;
            }
          }
        } catch (Exception ex) {
          logger.error("校验BDM样表时发生异常！", ex);
        }

        //导入模板内容的数值形式判断**************START*****************************
        //T+N周预测数据的数值型判断
        if (!CommUtil.canToInt(t3RequirePrediction)
            || (!StringUtil.isEmpty(t3RequirePrediction, true)
            && Integer.parseInt(t3RequirePrediction) < 0)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【T+3周】应该是空或者大于等于0的整数! 请核查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }

        if (!CommUtil.canToInt(t4RequirePrediction)
            || (!StringUtil.isEmpty(t4RequirePrediction, true)
            && Integer.parseInt(t4RequirePrediction) < 0)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【T+4周】应该是空或者大于等于0的整数! 请核查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }
        if (!CommUtil.canToInt(t5RequirePrediction)
            || (!StringUtil.isEmpty(t5RequirePrediction, true)
            && Integer.parseInt(t5RequirePrediction) < 0)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【T+5周】应该是空或者大于等于0的整数! 请核查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }
        if (!CommUtil.canToInt(t6RequirePrediction)
            || (!StringUtil.isEmpty(t6RequirePrediction, true)
            && Integer.parseInt(t6RequirePrediction) < 0)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【T+6周】应该是空或者大于等于0的整数! 请核查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }
        if (!CommUtil.canToInt(t7RequirePrediction)
            || (!StringUtil.isEmpty(t7RequirePrediction, true)
            && Integer.parseInt(t7RequirePrediction) < 0)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【T+7周】应该是空或者大于等于0的整数! 请核查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }
        if (!CommUtil.canToInt(t8RequirePrediction)
            || (!StringUtil.isEmpty(t8RequirePrediction, true)
            && Integer.parseInt(t8RequirePrediction) < 0)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【T+8周】应该是空或者大于等于0的整数! 请核查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }
        if (!CommUtil.canToInt(t9RequirePrediction)
            || (!StringUtil.isEmpty(t9RequirePrediction, true)
            && Integer.parseInt(t9RequirePrediction) < 0)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【T+9周】应该是空或者大于等于0的整数! 请核查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }
        if (!CommUtil.canToInt(t10RequirePrediction)
            || (!StringUtil.isEmpty(t10RequirePrediction, true)
            && Integer.parseInt(t10RequirePrediction) < 0)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【T+10周】应该是空或者大于等于0的整数! 请核查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }
        if (!CommUtil.canToInt(t11RequirePrediction)
            || (!StringUtil.isEmpty(t11RequirePrediction, true)
            && Integer.parseInt(t11RequirePrediction) < 0)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【T+11周】应该是空或者大于等于0的整数! 请核查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }
        if (!CommUtil.canToInt(t12RequirePrediction)
            || (!StringUtil.isEmpty(t12RequirePrediction, true)
            && Integer.parseInt(t12RequirePrediction) < 0)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【T+12周】应该是空或者大于等于0的整数! 请核查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }
        if (!CommUtil.canToInt(t13RequirePrediction)
            || (!StringUtil.isEmpty(t13RequirePrediction, true)
            && Integer.parseInt(t13RequirePrediction) < 0)) {
          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【T+13周】应该是空或者大于等于0的整数! 请核查后重新导入！";
          if (StringUtil.isEmpty(MsgList, true)) {
            sb.append(MsgList);
          } else {
            MsgList = MsgList + "<br>";
            sb.append(MsgList);
          }
          continue;
        }
        //导入模板内容的数值形式判断**************END*******************************

//        String[] authChannel = (String[]) authMap.get("channel");
//        String[] authproductGroup = (String[]) authMap.get("productGroup");
//        List<String> channelList = Arrays.asList(authChannel);
//        List<String> productGroupList = Arrays.asList(authproductGroup);
        //判断渠道id是否有权限
//        if (!channelList.contains(channelCode)) {
//          MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的渠道【" + edChannelName
//              + "】，你没有权限导入！请检查后重新导入！";
//          if (StringUtil.isEmpty(MsgList, true)) {
//            sb.append(MsgList);
//          } else {
//            MsgList = MsgList + "<br>";
//            sb.append(MsgList);
//          }
//          continue;
//        }

        // 判断产品组id是否有权限
//        if (!productGroupList.contains(department)) {
//
//          if (productGroupName != null && !"".equals(productGroupName)) {
//            MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的产品组【" + productGroupName
//                + "】，你没有权限导入! 请核查后重新导入！";
//          } else {
//            MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的物料号【" + materialsId + "】对应的产品组ID【"
//                + department + "】，你没有权限导入! 请核查后重新导入！";
//          }
//
//          if (StringUtil.isEmpty(MsgList, true)) {
//            sb.append(MsgList);
//          } else {
//            MsgList = MsgList + "<br>";
//            sb.append(MsgList);
//          }
//          continue;
//        }

        //导入模板内容的合理性判断及权限判断**************END*******************************

        PredictingStockItem predictingStockItem = new PredictingStockItem();
        //模板提供项
        predictingStockItem.setEd_channel_id(channelCode);
        predictingStockItem.setProduct_group_id(department);
        predictingStockItem.setMaterials_id(materialsId);
        predictingStockItem.setCategory_id(categoryId);
        //T+3周需求预测
        predictingStockItem
            .setT3_require_prediction(ConvertUtil.toInt(t3RequirePrediction, 0));
        predictingStockItem
            .setT4_require_prediction(ConvertUtil.toInt(t4RequirePrediction, 0));
        predictingStockItem
            .setT5_require_prediction(ConvertUtil.toInt(t5RequirePrediction, 0));
        predictingStockItem
            .setT6_require_prediction(ConvertUtil.toInt(t6RequirePrediction, 0));
        predictingStockItem
            .setT7_require_prediction(ConvertUtil.toInt(t7RequirePrediction, 0));
        predictingStockItem
            .setT8_require_prediction(ConvertUtil.toInt(t8RequirePrediction, 0));
        predictingStockItem
            .setT9_require_prediction(ConvertUtil.toInt(t9RequirePrediction, 0));
        predictingStockItem
            .setT10_require_prediction(ConvertUtil.toInt(t10RequirePrediction, 0));
        predictingStockItem
            .setT11_require_prediction(ConvertUtil.toInt(t11RequirePrediction, 0));
        predictingStockItem
            .setT12_require_prediction(ConvertUtil.toInt(t12RequirePrediction, 0));
        predictingStockItem
            .setT13_require_prediction(ConvertUtil.toInt(t13RequirePrediction, 0));
        //模板之外插入项
        predictingStockItem.setReport_year_week(reportYearWeek);

        predictingStockItem.setCreate_user(createUser);

        //验证记录是否存在，如果不存在则不作处理
        ServiceResult<Boolean> isExistResult = predictingStockService
            .isExistPredictingStock(department, materialsId, reportYearWeek, channelCode);
        if (!(isExistResult.getSuccess() && isExistResult.getResult())) {

          //创建T+3预测备料信息表单
          ServiceResult<Boolean> insResult = predictingStockService
              .insertPredictingStock(predictingStockItem);
          if (insResult.getSuccess() && insResult.getResult()) {
            insCount++;
          } else {
            MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据插入DB时失败！";
            if (StringUtil.isEmpty(MsgList, true)) {
              sb.append(MsgList);
            } else {
              MsgList = MsgList + "<br>";
              sb.append(MsgList);
            }
            continue;
          }
        } else {
          //更新T+3预测备料信息表单
          ServiceResult<Boolean> updResult = predictingStockService
              .updatePredictingStock(predictingStockItem);
          if (updResult.getSuccess() && updResult.getResult()) {
            updCount++;
          } else {
            MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据更新DB时失败！";
            if (StringUtil.isEmpty(MsgList, true)) {
              sb.append(MsgList);
            } else {
              MsgList = MsgList + "<br>";
              sb.append(MsgList);
            }
            continue;
          }
        }
      }

      //日志操作记录   xuelin.zhao start/////////////////////
      this.logAuditService.unionLog("", null, null, "0", createUser, "");
      //end //////////////////////////////////////////

      modelMap.put("total", list.size() - 1 - nullRow);
      modelMap.put("updSuccess", updCount);
      modelMap.put("insSuccess", insCount);
      modelMap.put("ignore", list.size() - 1 - nullRow - updCount - insCount);
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
  
  
//  @RequestMapping(value = { "/getProductGroupByAuth" }, method = { RequestMethod.GET })
//	@ResponseBody
	HttpJsonResult<List<ItemAttribute>> getProductGroup(HttpServletRequest request) {
		HttpJsonResult<List<ItemAttribute>> result = new HttpJsonResult<List<ItemAttribute>>();
		ServiceResult<List<ItemAttribute>> queryResult = itemService
			.queryProductGroupByCbsCategory(null);
		//        queryResult
		//        groups.add(0, "全部");

		// 权限定义产品组取得
		ServiceResult<PrivilegeItem> privilegeData = purchaseCommonService
			.getPrivilege(String.valueOf(WebUtil.currentUserId(request)));
		// 删除没有权限的产品组
		List<ItemAttribute> productGroupData = new ArrayList<ItemAttribute>();
		if (privilegeData.getSuccess() == true && privilegeData.getResult() != null) {
			for (ItemAttribute itemAttribute : queryResult.getResult()) {
				if (privilegeData.getResult().getProduct_group_id()
					.contains(itemAttribute.getValue())) {
					productGroupData.add((itemAttribute));
				}
			}
			result.setData(productGroupData);
		} else {
			result.setData(queryResult.getResult());
		}
		return result;
	}

//	@RequestMapping(value = { "/getChannelsByAuth" }, method = { RequestMethod.GET })
//	@ResponseBody
	HttpJsonResult<List<InvStockChannel>> getChennelsByAuth(HttpServletRequest request) {
		HttpJsonResult<List<InvStockChannel>> result = new HttpJsonResult<List<InvStockChannel>>();
		ServiceResult<List<InvStockChannel>> queryResult = stockCommonService.getChannels();

		// 权限定义产品组取得
		ServiceResult<PrivilegeItem> privilegeData = purchaseCommonService
			.getPrivilege(String.valueOf(WebUtil.currentUserId(request)));
		// 删除没有权限的产品组
		List<InvStockChannel> chennelsData = new ArrayList<InvStockChannel>();
		if (privilegeData.getSuccess() == true && privilegeData.getResult() != null) {
			for (InvStockChannel invStockChannel : queryResult.getResult()) {
				if (privilegeData.getResult().getEd_channel_id()
					.contains(invStockChannel.getChannelCode())) {
					chennelsData.add((invStockChannel));
				}
			}
			result.setData(chennelsData);
		} else {
			result.setData(queryResult.getResult());
		}
		//690电商渠道处理
		if (privilegeData.getResult().getEd_channel_id().contains("DS")) {
			InvStockChannel invStockChannel = new InvStockChannel();
			invStockChannel.setChannelCode("DS");
			invStockChannel.setName("690电商渠道");
			result.getData().add(invStockChannel);
		}
		return result;
	}

  /**
   * 导出Excel模板
   * @param response
   * @return 方法执行完毕调用的画面
   */
  @RequestMapping(value = { "/exportT3PredictModel" })
  public void exportModel(HttpServletRequest request, HttpServletResponse response) {
    String fileName = "预测备料导入模板";
    String sheetName = "导入模板";
    ExcelExportUtil.downloadDataTemplate(logger, request, response, fileName, sheetName,
        checkStr);
  }

  /**
   * 预测备料提报画面初期设定
   * @param modelMap
   * @return
   */
  @RequestMapping(value = { "predictingStockReport" }, method = { RequestMethod.GET })
  String predictingStockReport(Map<String, Object> modelMap, HttpServletRequest request) {
    String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");//当前日期取得
    String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null, "1");//转化成周
    modelMap.put("report_year_week", currentWeek);
    Map<String, Object> authMap = new HashMap<>();
	commPurchase.getAuthMap(request, "", "", "", authMap);
	try {
		modelMap.put("authMap", JSON.json(authMap));
	} catch (IOException e) {
		e.printStackTrace();
	}
    return "purchase/predictingStockReport";
  }

  /**
   * 预测备料提报画面查询处理
   * @param reportYearWeek
   * @param productGroupId
   * @param modelMap
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = { "/findPredictingStockReport" }, method = { RequestMethod.POST })
  @ResponseBody
  public Map<String, Object> findReport(@RequestParam(required = false) String reportYearWeek,
      @RequestParam(required = false) String productGroupId, Integer page, Integer rows,
      Map<String, Object> modelMap, HttpServletRequest request,
      HttpServletResponse response) {
    try {
      // 权限Map
      Map<String, Object> authMap = new HashMap<String, Object>();
//      if(StringUtils.isNotBlank(productGroupId)){
//    	  authMap.put("productGroup", new String[]{productGroupId});
//      }
      //取得产品组权限List和渠道权限List
      commPurchase.getAuthMap( request, productGroupId, null, null,
          authMap);

      Map<String, Object> params = new HashMap<String, Object>();
      params.put("report_year_week", reportYearWeek.replace("年", "").replace("周", ""));
      params.put("product_group_id", authMap.get("productGroup"));
      params.put("ed_channel_id", authMap.get("channel"));

      ServiceResult<List<PredictingStockItem>> result = predictingStockService
          .getPredictingStockReport(params);
      //渠道和产品组数据存入HashMap
      Map<String, String> productgroupmap = new HashMap<String, String>();//产品组
      Map<String, String> invstockchannelmap = new HashMap<String, String>();//渠道
      //取得产品组
      getProductMap(productgroupmap, t2OrderService);
      //取得渠道
      getChannelMap(invstockchannelmap, stockCommonService);
      //获取流程状态map
      Map<String, String> flowFlagMap = CommPurchase.getValueMeaningMap(dataDictionaryService,
          T3_ORDER_STATUS);
      flowFlagMap.clear();
      flowFlagMap.put("0", "未导入");
      flowFlagMap.put("1", "已导入");
      flowFlagMap.put("2", "已同步");
      flowFlagMap.put("3", "同步失败");
      if (result.getSuccess() && result.getResult() != null) {
        List<PredictingStockItem> predictstocklist = result.getResult();
        for (PredictingStockItem item : predictstocklist) {
          //编辑提报周
          item.setReport_year_week_display(
              CommUtil.weekTodate(item.getReport_year_week()));
          //根据工作组id取得工作组名
          item.setProduct_group_name(productgroupmap.get(item.getProduct_group_id()));
          item.setFlow_flag_name(flowFlagMap.get(String.valueOf(item.getFlow_flag())));
        }
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("rows", result.getResult());
        retMap.put("total", result.getResult().size());
        return retMap;
      }

    } catch (Exception e) {
      logger.error("", e);
      e.printStackTrace();
    }
    return new HashMap();
  }

  /**
   * 预测填报画面提报处理
   * @param request
   * @param response
   * @return
   */
  @ResponseBody
  @RequestMapping(value = { "/reportPredictingStock" })
  public HttpJsonResult<String> report(HttpServletRequest request,
      HttpServletResponse response, String product_group_id) {
    HttpJsonResult<String> resultClient = new HttpJsonResult<String>();
    // 权限Map
    Map<String, Object> authMap = new HashMap<String, Object>();
    //取得产品组权限List和渠道权限List
    commPurchase.getAuthMap( request, product_group_id, null, null, authMap);
    //闸口不秒杀的场合，时间闸口check
//    if ("0".equals(authMap.get("gateLimit"))) {
      //时间闸口check
      if (!CommPurchase.isInTimeGate(gateService, "03")) {
        resultClient.setData("不在提交期间内");
        return resultClient;
      }
//    }
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("report_year_week", request.getParameter("report_year_week"));
    params.put("productGroupId", authMap.get("productGroup"));
    params.put("ed_channel_id", authMap.get("channel"));
    //增加提交人 xuelin.zhao
    params.put("audit_user", request.getSession().getAttribute("userName").toString());

    ServiceResult<String> result = predictingStockService.reportByHand(params);
    if (result.getSuccess() && result.getResult() != null) {
      resultClient.setData(result.getResult());
    }
    return resultClient;
  }

  @RequestMapping(value = { "predictingStockHistoryList" }, method = { RequestMethod.GET })
  String predictingStockHistoryList(HttpServletRequest request, Map<String, Object> modelMap) {
	  Map<String, Object> authMap = new HashMap<>();
		commPurchase.getAuthMap(request, "", "", "", authMap);
		try {
			modelMap.put("authMap", JSON.json(authMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
    return "purchase/predictingStockHistoryList";
  }

  /**
	 * 历史查看页面导出Excel
	 * @author DHC 刘骥飞
	 * @param request
	 * @param response
	 * @param report_year_week 填报周
	 * @param start_week 开始周
	 * @param end_week 结束周
	 * @param product_group_id 产品组ID
	 * @param ed_channel_id 渠道ID
	 * @param create_user 填报人
	 * @return 方法执行完毕调用的画面
	 */
	@RequestMapping(value = { "/exportPredictStockDetailList" })
	void exportPredictStockDetailList(HttpServletRequest request, HttpServletResponse response,
									  @RequestParam(required = false) String report_year_week_hidden,
									  @RequestParam(required = false) String start_week_hidden,
									  @RequestParam(required = false) String end_week_hidden,
									  @RequestParam(required = false) String product_group_id_hidden,
									  @RequestParam(required = false) String ed_channel_id_hidden,
									  @RequestParam(required = false) String flow_flag_hidden,
									  Map<String, Object> modelMap) {
		// 权限Map
		Map<String, Object> authMap = new HashMap<String, Object>();
//		if(StringUtils.isNotBlank(product_group_id_hidden)){
//			authMap.put("productGroup", new String[]{product_group_id_hidden});
//		}
//		if(StringUtils.isNotBlank(ed_channel_id_hidden)){
//			authMap.put("channel", new String[]{ed_channel_id_hidden});
//		}
		//取得产品组权限List和渠道权限List
		commPurchase.getAuthMap( request, product_group_id_hidden,
			ed_channel_id_hidden, null, authMap);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("report_year_week", report_year_week_hidden);
		params.put("start_week", start_week_hidden);
		params.put("end_week", end_week_hidden);
		params.put("product_group_id", authMap.get("productGroup"));
		params.put("ed_channel_id", authMap.get("channel"));
		params.put("m", 0);
		params.put("n", 30000);

		//flow_flag转化为数组
		String[] flow_flag_list = null;
		if (flow_flag_hidden != null && !"".equals(flow_flag_hidden)) {
			if (flow_flag_hidden.contains("ALL")) {
				flow_flag_list = null;
			} else {
				flow_flag_list = flow_flag_hidden.split(",");
			}
		}
		params.put("flow_flag_list", flow_flag_list);
		final List<PredictingStockItem> items = getDetailsData(params).getResult();
		modelMap.put("rowList", items);
		String fileName = "预测备料数据导出";
		String sheetName = "导出数据";
		String[] sheetHead = new String[] { "序号", "提报周", "渠道", "产品组", "物料编号", "品牌", "型号", "T+3周",
				"T+4周", "T+5周", "T+6周",  "T+7周", "T+8周", "T+9周", "T+10周", 
				"T+11周", "T+12周", "T+13周",  "状态", "填报时间", "填报人", "提交时间",
											"提交人", "失败信息" };
		try {
			ExcelExportUtil.exportEntity(logger, request, response, fileName, sheetName, sheetHead,
				new ExcelCallbackInterfaceUtil() {

					@Override
					public void setExcelBodyTotal(OutputStream os, WritableSheet sheet,
												  int temp) throws Exception {
						setExcelBodyTotalForPredictingStockHistory(sheet, temp, items);
					}

				});
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	/**
 	 * 导出具体数据，实现回调函数
 	 * @param sheet
 	 * @param temp 行号
 	 * @param list 传入需要导出的entity list
 	 */
 	private void setExcelBodyTotalForPredictingStockHistory(WritableSheet sheet, int temp,
 															List<PredictingStockItem> list) throws Exception {
 
 		WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
 			UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
 		WritableCellFormat textFormat = new WritableCellFormat(font);
 		textFormat.setAlignment(Alignment.CENTRE);
 		textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
 
 		DisplayFormat displayFormat = NumberFormats.INTEGER;
 		WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
 		numberFormat.setAlignment(jxl.format.Alignment.RIGHT);
 		numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
 
 		int index = 0;
 		for (PredictingStockItem predictingStockItem : list) {
 			index++;
 			//jxl.write.Number(列号,行号 ,内容 )
 			// "序号", "提报周", "渠道", "产品组", "物料编号", "品牌", "型号", "T+3周", "T+4周", "T+5周", "T+6周", "T+7周", "T+8周", "T+9周", "T+10周", "T+11周", "T+12周", "T+13周"
 			sheet.setColumnView(0, 10);
 			sheet.addCell(new Label(0, temp, String.valueOf(index), textFormat));
 			sheet.setColumnView(1, 30);
 			sheet
 				.addCell(new Label(1, temp, predictingStockItem.getReport_year_week(), textFormat));
 			sheet.setColumnView(2, 15);
 			sheet.addCell(new Label(2, temp, predictingStockItem.getEd_channel_name(), textFormat));
 			sheet.setColumnView(3, 15);
 			sheet.addCell(
 				new Label(3, temp, predictingStockItem.getProduct_group_name(), textFormat));
 			sheet.setColumnView(4, 15);
 			sheet.addCell(new Label(4, temp, predictingStockItem.getMaterials_id(), textFormat));
 			sheet.setColumnView(5, 15);
 			sheet.addCell(new Label(5, temp, predictingStockItem.getBrand_name(), textFormat));
 			sheet.setColumnView(6, 30);
 			sheet.addCell(
 				new Label(6, temp, predictingStockItem.getMaterial_description(), textFormat));
 			sheet.setColumnView(7, 15);
 			sheet.addCell(new jxl.write.Number(7, temp,
 				predictingStockItem.getT3_require_prediction()==null?0:predictingStockItem.getT3_require_prediction(), numberFormat));
 			sheet.setColumnView(8, 15);
 			sheet.addCell(new jxl.write.Number(8, temp,
 				predictingStockItem.getT4_require_prediction()==null?0:predictingStockItem.getT4_require_prediction(), numberFormat));
 			sheet.setColumnView(9, 15);
 			sheet.addCell(new jxl.write.Number(9, temp,
 				predictingStockItem.getT5_require_prediction()==null?0:predictingStockItem.getT5_require_prediction(), numberFormat));
 			sheet.setColumnView(10, 15);
 			sheet.addCell(new jxl.write.Number(10, temp,
 				predictingStockItem.getT6_require_prediction()==null?0:predictingStockItem.getT6_require_prediction(), numberFormat));
 			sheet.setColumnView(11, 15);
 			sheet.addCell(new jxl.write.Number(11, temp,
 				predictingStockItem.getT7_require_prediction()==null?0:predictingStockItem.getT7_require_prediction(), numberFormat));
 			sheet.setColumnView(12, 15);
 			sheet.addCell(new jxl.write.Number(12, temp,
 				predictingStockItem.getT8_require_prediction()==null?0:predictingStockItem.getT8_require_prediction(), numberFormat));
 			sheet.setColumnView(13, 15);
 			sheet.addCell(new jxl.write.Number(13, temp,
 				predictingStockItem.getT9_require_prediction()==null?0:predictingStockItem.getT9_require_prediction(), numberFormat));
 			sheet.setColumnView(14, 15);
 			sheet.addCell(new jxl.write.Number(14, temp,
 				predictingStockItem.getT10_require_prediction()==null?0:predictingStockItem.getT10_require_prediction(), numberFormat));
 			sheet.setColumnView(15, 15);
 			sheet.addCell(new jxl.write.Number(15, temp,
 				predictingStockItem.getT11_require_prediction()==null?0:predictingStockItem.getT11_require_prediction(), numberFormat));
 			sheet.setColumnView(16, 15);
 			sheet.addCell(new jxl.write.Number(16, temp,
 				predictingStockItem.getT12_require_prediction()==null?0:predictingStockItem.getT12_require_prediction(), numberFormat));
 			sheet.setColumnView(17, 15);
 			sheet.addCell(new jxl.write.Number(17, temp,
 				predictingStockItem.getT13_require_prediction()==null?0:predictingStockItem.getT13_require_prediction(), numberFormat));
 			sheet.setColumnView(18, 15);
 			sheet.addCell(new Label(18, temp, predictingStockItem.getFlow_flag_name(), textFormat));
 			sheet.setColumnView(19, 30);
 			sheet.addCell(
 				new Label(19, temp, predictingStockItem.getCreate_time_display(), textFormat));
 			sheet.setColumnView(20, 30);
 			sheet.addCell(new Label(20, temp, predictingStockItem.getCreate_user(), textFormat));
 			sheet.setColumnView(21, 30);
 			sheet.addCell(
 				new Label(21, temp, predictingStockItem.getAudit_time_display(), textFormat));
 			sheet.setColumnView(22, 30);
 			sheet.addCell(new Label(22, temp, predictingStockItem.getAudit_user(), textFormat));
 			sheet.setColumnView(23, 30);
 			sheet.addCell(new Label(23, temp, predictingStockItem.getError_msg(), textFormat));
 			temp++;
 		}
 	}	
	
  /**
   * 判断导入文档表头是否正确
   */
  public boolean checkDataTemplate(String[] firstLineData, String checkStr) {
    boolean flag = false;
    StringBuffer sb = new StringBuffer();
    for (String str : firstLineData) {
      if (sb.length() > 0) {
        sb.append(",");
      }
      sb.append(str.trim());
    }
    String nullStr = sb.toString().replace(checkStr, "").replace(",", "");
    if (nullStr == null || "".equals(nullStr)) {
      flag = true;
    }
    return flag;
  }

  public static String getDepartmentByMaterialCode(String materialCode,
      T2OrderService t2OrderService) {
    ServiceResult<List<ItemBase>> result = t2OrderService
        .findItemBaseByMaterialId(materialCode);
    String department = "";
    if (result.getSuccess() && result.getResult().size() >= 1) {
      department = result.getResult().get(0).getDepartment();
    }
    return department;
  }

  /**
   * 取得产品组map
   *
   * @param productgroupmap 产品组map
   * @param t2OrderService item服务
   */
  public static void getProductMap(Map<String, String> productgroupmap,
      T2OrderService t2OrderService) {
    //调用itemService，取得产品组数据
    ServiceResult<List<ItemAttribute>> resultProductGroup = t2OrderService
        .queryProductGroupByCbsCategory(null);
    if (resultProductGroup.getSuccess() && resultProductGroup.getResult() != null) {
      List<ItemAttribute> itemAttributes = resultProductGroup.getResult();
      for (ItemAttribute item : itemAttributes) {
        productgroupmap
            .put(item.getValue(), item.getValueMeaning());//将value作为key，valueMeaning作为value存入map中
      }
    }
  }

  /**
   * 取得渠道map
   *
   * @param invstockchannelmap 渠道map
   * @param stockCommonService stock服务
   */
  public static void getChannelMap(Map<String, String> invstockchannelmap,
      StockCommonService stockCommonService) {
    //调用stockCommonService，取得渠道数据
    ServiceResult<List<InvStockChannel>> resultChannel = stockCommonService.getChannels();
    if (resultChannel.getSuccess() && resultChannel.getResult() != null) {
      List<InvStockChannel> invStockChannel = resultChannel.getResult();
      for (InvStockChannel invstockchannel : invStockChannel) {
        invstockchannelmap.put(invstockchannel.getChannelCode(),
            invstockchannel.getName());//将channelcode作为key，name作为value存入map中
      }
    }
  }
  /**
	 * 取得品牌map
	 * 
	 * @param brandMap
	 *            品牌map
	 */
	public static void getBrandMap(Map<String, String> brandMap, T2OrderService t2OrderService) {
		// 调用itemService，取得品牌数据，取得品牌map
		ServiceResult<List<ItemAttribute>> queryResult = t2OrderService
				.getByValueSetItemId("Brand");
		if (queryResult.getSuccess() && queryResult.getResult() != null) {
			List<ItemAttribute> itemAttributes = queryResult.getResult();
			for (ItemAttribute item : itemAttributes) {
				brandMap.put(item.getValue(), item.getValueMeaning());
			}
		}
	}
	
	/**
	 * 根据物料ID取得品牌code和型号
	 * 
	 * @param material_id
	 *            物料ID
	 * @param Map
	 *            <String, String> 品牌和型号所存放的Map
	 */
	public static Map<String, String> getItemBaseMap(String material_id, T2OrderService t2OrderService) {
		Map<String, String> itemBaseMap = new HashMap<String, String>();

		// 调用itemService，取得品牌数据
		ServiceResult<List<ItemBase>> result = t2OrderService
				.findItemBaseByMaterialId(material_id);

		if (result.getSuccess() && result.getResult() != null
				&& result.getResult().size() > 0) {
			// 将物料ID、品牌code、型号、品牌名称put into Map
			ItemBase item = result.getResult().get(0);
			itemBaseMap.put("material_id", material_id);
			itemBaseMap.put("brand_code", item.getProBand());
			itemBaseMap.put("material_description",
					item.getMaterialDescription());
			if (null == item.getPrice()) {
				itemBaseMap.put("price", "0");
			} else {
				itemBaseMap.put("price", String.valueOf(item.getPrice()));
			}
		}
		return itemBaseMap;
	}
}
