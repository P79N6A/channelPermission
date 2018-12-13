package com.haier.svc.api.controller.purchase;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.alibaba.dubbo.common.json.ParseException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.ConvertUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.purchase.data.model.GateOfDataPrivilegeItem;
import com.haier.purchase.data.model.GateOfLimitItem;
import com.haier.purchase.data.model.WwwStockSaveRequire;
import com.haier.shop.model.ItemAttribute;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.StockAgeWapped;
import com.haier.stock.service.StockCommonService;
import com.haier.svc.api.controller.T2OrderController;
import com.haier.svc.api.controller.stock.mode.StockAgeModel;
import com.haier.svc.api.controller.util.CommUtil;
import com.haier.svc.api.controller.util.ExcelCallbackInterfaceUtil;
import com.haier.svc.api.controller.util.ExcelExportUtil;
import com.haier.svc.api.controller.util.ExcelReader;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;
import com.haier.svc.api.controller.util.date.DateCal;
import com.haier.svc.api.service.CommPurchase;
import com.haier.svc.service.DataDictionaryService;
import com.haier.svc.service.GateService;
import com.haier.svc.service.ItemService;
import com.haier.svc.service.LogAuditService;
import com.haier.svc.service.T2OrderService;
import com.haier.svc.service.WwwStockGetService;
import com.haier.system.model.SysUser;
import com.haier.system.service.SystemCenterService;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import net.sf.json.JSONArray;

/**
 * 额度查询
 *
 * @author:
 * @description:
 * @date:created in 2018/4/23 14:33
 * @modificed by:
 */
@Controller
@RequestMapping(value = "gate")
public class GateController {

	private final static org.apache.log4j.Logger logger = LogManager.getLogger(T2OrderController.class);

	@Resource(name = "gateService")
	private GateService gateService;

	@Resource(name = "t2OrderService")
	private T2OrderService t2OrderService;

	@Autowired
	CommPurchase commPurchase;

	@Autowired
	private StockAgeModel stockAgeModel;

	@Autowired
	private ItemService itemService;

	@Resource
	private WwwStockGetService wwwStockGetService;

	@Autowired
	private DataDictionaryService dataDictionaryService;

	@Autowired
	private SystemCenterService systemService;

	@Resource
	LogAuditService logAuditService;

	// 是否区分
	private static final String TRUE_FALSE_DISTINCT = "true_or_false";
	// 数据权限录入
	private final String ADD_DATA_PRIVILEGE = "add";
	// 数据权限修改
	private final String MODIFY_DATA_PRIVILEGE = "modify";

	@Resource
	private StockCommonService stockCommonService;
	private static final String CHECKSTR = "No.,品类,渠道,1月,2月,3月,4月,5月,6月,7月,8月,9月,10月,11月,12月";

	/**
	 * 额度闸口查询页面调转
	 */
	@RequestMapping(value = { "gateOfLimitForSearch" }, method = { RequestMethod.GET })
	String gateOfLimitForSearch(Map<String, Object> modelMap) {
		// 当前日期取得
		String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
		// 转化成周
		String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null, "1");
		modelMap.put("currentWeek", currentWeek);
		return "purchase/gateOfLimitForSearch";
	}

	/**
	 * 额度闸口设置页面调转
	 *
	 * @param request
	 * @param modelMap
	 *            调转页面数据存放map
	 * @return
	 */
	@RequestMapping(value = { "gateOfLimit" }, method = { RequestMethod.GET })
	String gateOfLimit(HttpServletRequest request, Map<String, Object> modelMap) {
		// 当前日期取得
		String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
		// 转化成周
		String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null, "1");
		modelMap.put("currentweek", currentWeek);
		return "purchase/gateOfLimit";
	}

	/**
	 * 额度闸口查询页面中为datagrid加载值
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws IOException
	 */
	@RequestMapping(value = { "findGateOfLimitList" })
	@ResponseBody
	public Map<String, Object> findGateOfLimitList(@RequestParam(required = false) String report_year_week,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("report_year_week", report_year_week.replace("年", "").replace("周", ""));
			Map<String, Object> resultMap = getGateOfLimitData(params);
			String totalLimit = (String) resultMap.get("totalLimit");
			@SuppressWarnings("unchecked")
			List<GateOfLimitItem> resultList = (List<GateOfLimitItem>) resultMap.get("resultList");
			retMap.put("rows", resultList);
			retMap.put("total", totalLimit);

		} catch (Exception e) {
			logger.error("错误代码：", e);
		}
		return retMap;
	}

	/**
	 * 导出额度文件
	 *
	 * @param response
	 * @param request
	 * @param modelMap
	 * @return 方法执行完毕调用的画面
	 */
	@RequestMapping(value = { "/exportGateOfLimit.export" })
	String exportGateOfLimit(@RequestParam(required = false) String report_year_week, HttpServletResponse response,
			HttpServletRequest request, Map<String, Object> modelMap) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("report_year_week", report_year_week.replace("年", "").replace("周", ""));
		@SuppressWarnings("unchecked")
		List<GateOfLimitItem> limitList = (List<GateOfLimitItem>) getGateOfLimitData(params).get("resultList");
		modelMap.put("report_year_week", report_year_week);
		modelMap.put("rowList", limitList);
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		String fileName = "额度文件";
		try {
			fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xls\"");
		return "purchase/gateOfHistoryLimitExport";
	}

	/**
	 * 额度闸口数据处理，计算合计和总计
	 *
	 * @param params
	 * @return GateOfLimitItem List<>
	 */
	private Map<String, Object> getGateOfLimitData(Map<String, Object> params) {
		// long totalAge = 0;
		// long totalStart = System.currentTimeMillis();
		ServiceResult<List<GateOfLimitItem>> result = new ServiceResult<List<GateOfLimitItem>>();
		// 取得渠道map
		Map<String, String> invStockChannelMap = new HashMap<String, String>();
		invStockChannelMap = commPurchase.getChannelMap(invStockChannelMap);
		List<GateOfLimitItem> gateOfLimitListTmp = null;
		List<GateOfLimitItem> gateOfLimitList = new ArrayList<GateOfLimitItem>();
		// T+3月份取得
		String endDate = CommUtil.weekToSetDateDay(params.get("report_year_week").toString(), 4);
		DateCal dateCal = new DateCal(endDate);
		Integer reportMonth = Integer.valueOf(dateCal.addWeek(3).substring(5, 7));
		params.put("month", reportMonth);
		// 在DB中检索详细信息
		result = gateService.findGateOfLimit(params);

		String totalLimit = "";
		// 重新组织DB中检索的数据
		if (result.getSuccess() && result.getResult() != null) {
			gateOfLimitListTmp = result.getResult();
			// 合计行
			GateOfLimitItem gateOfLimitSumByChan = new GateOfLimitItem();
			gateOfLimitSumByChan.setEd_channel_name("合计");
			// 品类temp，渠道temp
			String catagoryTmp = "";
			// 个数记录
			Integer count = 0;
			// 渠道分总库存合计
			Map<String, BigDecimal> totalStock = new HashMap<String, BigDecimal>();
			// 渠道分在途合计
			Map<String, BigDecimal> onWay = new HashMap<String, BigDecimal>();
			// 渠道分本周已用合计
			Map<String, BigDecimal> used = new HashMap<String, BigDecimal>();
			// 渠道分总库存合计
			BigDecimal totalStockNum = new BigDecimal(0);
			// 渠道分在途合计
			BigDecimal totalOnWayNum = new BigDecimal(0);
			// 渠道分本周已用合计
			BigDecimal totalUsedNum = new BigDecimal(0);

			for (GateOfLimitItem gateOfLimitItem : gateOfLimitListTmp) {

				// 总库存,在库,本周已用
				BigDecimal totalNum = new BigDecimal(0);
				BigDecimal onwayNum = new BigDecimal(0);
				BigDecimal usedNum = new BigDecimal(0);
				BigDecimal wwwNum = new BigDecimal(0);
				if (count == 0) {
					catagoryTmp = gateOfLimitItem.getCategory_id();
				}
				// 渠道或者品类变化
				if (!catagoryTmp.equals(gateOfLimitItem.getCategory_id())) {
					if ("".equals(totalLimit)) {
						totalLimit = gateOfLimitSumByChan.getLimit_num();
					}
					if ("总计".equals(catagoryTmp)) {
						gateOfLimitSumByChan.setCategory_id("总计");
					}
					// 合计加入目标List中
					gateOfLimitList.add(gateOfLimitSumByChan);
					// gateOfLimitSumByChan初期化
					gateOfLimitSumByChan = new GateOfLimitItem();
					gateOfLimitSumByChan.setEd_channel_name("合计");

					catagoryTmp = gateOfLimitItem.getCategory_id();
				}
				count++;
				// 根据品类，渠道取得总库存
				if (!"总计".equals(gateOfLimitItem.getCategory_id())) {
					PagerInfo pagerInfo = new PagerInfo(1000000, 1);
					Map<String, Object> skuParams = new HashMap<String, Object>();
					skuParams.put("channel_code", gateOfLimitItem.getEd_channel_id());
					skuParams.put("product_type_name", gateOfLimitItem.getCategory_id());
					// long ageStart = System.currentTimeMillis();
					ServiceResult<List<StockAgeWapped>> stockResult = stockAgeModel.getStockAgeListByPurchase(pagerInfo,
							skuParams);
					// long ageEnd = System.currentTimeMillis();
					// totalAge += ageEnd - ageStart;
					// System.out.println("ageTime:" + (ageEnd - ageStart));

					if (stockResult.getSuccess() && stockResult.getResult() != null) {
						for (StockAgeWapped stockAgeWapped : stockResult.getResult()) {
							totalNum = totalNum.add(stockAgeWapped.getAgeDatas().get(0).getValue());
						}
					}
					// 天猫渠道 总库存增加 www库存 zzb 2017-04-19
					if ("TB".equals(gateOfLimitItem.getEd_channel_id())) {
						List<WwwStockSaveRequire> materialList = wwwStockGetService
								.select(gateOfLimitItem.getCategory_id(), gateOfLimitItem.getEd_channel_id());
						for (WwwStockSaveRequire wwwStockSaveRequire : materialList) {
							Double value = Double.valueOf(wwwStockSaveRequire.getPrice())
									* Double.valueOf(wwwStockSaveRequire.getAmount());
							BigDecimal value1 = new BigDecimal(value);
							wwwNum = wwwNum.add(value1);
						}
					}
					// wa + www 库存 单位：万元
					totalNum = totalNum.add(wwwNum.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP));
					// 根据品类，渠道取得在途
					ServiceResult<BigDecimal> onwaynumresult = t2OrderService.getOnwayNumByCateChan(
							gateOfLimitItem.getCategory_id(), gateOfLimitItem.getEd_channel_id());
					if (onwaynumresult.getSuccess() && onwaynumresult.getResult() != null)
						onwayNum = onwaynumresult.getResult().divide(new BigDecimal(10000), 2,
								BigDecimal.ROUND_HALF_UP);
					// 根据品类，渠道取得本周已用
					ServiceResult<BigDecimal> usednumresult = t2OrderService.getUsedNumByCateChan(
							params.get("report_year_week").toString(), gateOfLimitItem.getCategory_id(),
							gateOfLimitItem.getEd_channel_id());
					if (usednumresult.getSuccess() && usednumresult.getResult() != null)
						usedNum = usednumresult.getResult().divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
				}
				// 渠道分总库存更新
				totalStockNum = totalStock.get(gateOfLimitItem.getEd_channel_id()) == null ? new BigDecimal(0)
						: totalStock.get(gateOfLimitItem.getEd_channel_id());

				totalStockNum = totalStockNum.add(totalNum);
				totalStock.put(gateOfLimitItem.getEd_channel_id(), totalStockNum);
				// 渠道分在途更新
				totalOnWayNum = onWay.get(gateOfLimitItem.getEd_channel_id()) == null ? new BigDecimal(0)
						: onWay.get(gateOfLimitItem.getEd_channel_id());

				totalOnWayNum = totalOnWayNum.add(onwayNum);
				onWay.put(gateOfLimitItem.getEd_channel_id(), totalOnWayNum);
				// 渠道分已用更新
				totalUsedNum = used.get(gateOfLimitItem.getEd_channel_id()) == null ? new BigDecimal(0)
						: used.get(gateOfLimitItem.getEd_channel_id());

				totalUsedNum = totalUsedNum.add(usedNum);
				used.put(gateOfLimitItem.getEd_channel_id(), totalUsedNum);
				// 总库存
				gateOfLimitItem.setTotal_num(totalNum);
				// 在途
				gateOfLimitItem.setOn_way_num(onwayNum);
				// 本周已用
				gateOfLimitItem.setUsed_num(usedNum);

				BigDecimal limitNumTemp = new BigDecimal(0);
				if (gateOfLimitItem.getLimit_num() != null) {
					limitNumTemp = new BigDecimal(gateOfLimitItem.getLimit_num());
				}
				BigDecimal loanNumTemp = new BigDecimal(0);
				if (gateOfLimitItem.getLoan_num() != null) {
					loanNumTemp = new BigDecimal(gateOfLimitItem.getLoan_num());
				}

				// 可用额度(可用额度=额度-总库存-在途-本周已用+拆借）
				gateOfLimitItem.setAvailable_num(
						limitNumTemp.subtract(totalNum).subtract(onwayNum).subtract(usedNum).add(loanNumTemp));
				// 为渠道名称赋值
				gateOfLimitItem.setEd_channel_name(invStockChannelMap.get(gateOfLimitItem.getEd_channel_id()));
				// 添加到目标List中
				gateOfLimitList.add(gateOfLimitItem);
				// 指标
				String targetStr = gateOfLimitItem.getTarget_num();
				BigDecimal targetNum = targetStr == null ? new BigDecimal(0) : new BigDecimal(targetStr);
				String targetStrChan = gateOfLimitSumByChan.getTarget_num();
				BigDecimal targetNumChan = targetStrChan == null ? new BigDecimal(0) : new BigDecimal(targetStrChan);
				gateOfLimitSumByChan.setTarget_num(String.valueOf(targetNum.add(targetNumChan)));
				// 额度
				String limitSumStr = gateOfLimitItem.getLimit_num();
				BigDecimal limitNum = limitSumStr == null ? new BigDecimal(0) : new BigDecimal(limitSumStr);
				String limitSumStrChan = gateOfLimitSumByChan.getLimit_num();
				BigDecimal limitNumChan = limitSumStrChan == null ? new BigDecimal(0) : new BigDecimal(limitSumStrChan);
				gateOfLimitSumByChan.setLimit_num(String.valueOf(limitNum.add(limitNumChan)));
				// 总库存
				if (gateOfLimitSumByChan.getTotal_num() == null) {
					gateOfLimitSumByChan.setTotal_num(gateOfLimitItem.getTotal_num());
				} else {
					gateOfLimitSumByChan
							.setTotal_num(gateOfLimitItem.getTotal_num().add(gateOfLimitSumByChan.getTotal_num()));
				}

				// 在途
				if (gateOfLimitSumByChan.getOn_way_num() == null) {
					gateOfLimitSumByChan.setOn_way_num(gateOfLimitItem.getOn_way_num());
				} else {
					gateOfLimitSumByChan
							.setOn_way_num(gateOfLimitItem.getOn_way_num().add(gateOfLimitSumByChan.getOn_way_num()));
				}

				// 拆借
				BigDecimal loanNumChan = gateOfLimitSumByChan.getLoan_num() == null ? new BigDecimal(0)
						: new BigDecimal(gateOfLimitSumByChan.getLoan_num());
				BigDecimal loanNum = gateOfLimitItem.getLoan_num() == null ? new BigDecimal(0)
						: new BigDecimal(gateOfLimitItem.getLoan_num());
				gateOfLimitSumByChan.setLoan_num(String.valueOf((loanNumChan.add(loanNum))));
				// 本周已用
				if (gateOfLimitSumByChan.getUsed_num() == null) {
					gateOfLimitSumByChan.setUsed_num(gateOfLimitItem.getUsed_num());
				} else {
					gateOfLimitSumByChan
							.setUsed_num(gateOfLimitItem.getUsed_num().add(gateOfLimitSumByChan.getUsed_num()));
				}

				// 可用额度(可用额度=额度-总库存-在途-本周已用+拆借）
				gateOfLimitSumByChan.setAvailable_num(new BigDecimal(gateOfLimitSumByChan.getLimit_num())
						.subtract(gateOfLimitSumByChan.getTotal_num()).subtract(gateOfLimitSumByChan.getOn_way_num())
						.subtract(gateOfLimitSumByChan.getUsed_num())
						.add(new BigDecimal(gateOfLimitSumByChan.getLoan_num())));
				if (count == gateOfLimitListTmp.size()) {
					if ("总计".equals(catagoryTmp)) {
						gateOfLimitSumByChan.setCategory_id("总计");
					}
					// 最后一条，加入目标List中
					gateOfLimitList.add(gateOfLimitSumByChan);
				}
			}

			// 总计的总库存,在途,已用设定
			GateOfLimitItem gateOfLimitItemTotal = new GateOfLimitItem();
			// 总库存合计
			totalStockNum = new BigDecimal(0);
			// 在途合计
			totalOnWayNum = new BigDecimal(0);
			// 本周已用合计
			totalUsedNum = new BigDecimal(0);
			for (GateOfLimitItem gateOfLimitItem : gateOfLimitList) {
				if ("总计".equals(gateOfLimitItem.getCategory_id())) {
					if (!"合计".equals(gateOfLimitItem.getEd_channel_name())) {
						// 渠道分合计总库存
						gateOfLimitItem.setTotal_num(totalStock.get(gateOfLimitItem.getEd_channel_id()));
						totalStockNum = totalStockNum.add(totalStock.get(gateOfLimitItem.getEd_channel_id()));
						// 渠道分合计在途
						gateOfLimitItem.setOn_way_num(onWay.get(gateOfLimitItem.getEd_channel_id()));
						totalOnWayNum = totalOnWayNum.add(onWay.get(gateOfLimitItem.getEd_channel_id()));
						// 渠道分合计本周已用
						gateOfLimitItem.setUsed_num(used.get(gateOfLimitItem.getEd_channel_id()));
						totalUsedNum = totalUsedNum.add(used.get(gateOfLimitItem.getEd_channel_id()));

						BigDecimal limitNumTemp = new BigDecimal(0);
						if (gateOfLimitItem.getLimit_num() != null) {
							limitNumTemp = new BigDecimal(gateOfLimitItem.getLimit_num());
						}
						BigDecimal loanNumTemp = new BigDecimal(0);
						if (gateOfLimitItem.getLoan_num() != null) {
							loanNumTemp = new BigDecimal(gateOfLimitItem.getLoan_num());
						}
						// 可用额度(可用额度=额度-总库存-在途-本周已用+拆借）

						gateOfLimitItem.setAvailable_num(limitNumTemp.subtract(gateOfLimitItem.getTotal_num())
								.subtract(gateOfLimitItem.getOn_way_num()).subtract(gateOfLimitItem.getUsed_num())
								.add(loanNumTemp));
					} else {
						gateOfLimitItemTotal = gateOfLimitItem;
					}

				}
			}
			gateOfLimitItemTotal.setTotal_num(totalStockNum);
			gateOfLimitItemTotal.setOn_way_num(totalOnWayNum);
			gateOfLimitItemTotal.setUsed_num(totalUsedNum);

			BigDecimal limitNumTemp = new BigDecimal(0);
			if (gateOfLimitItemTotal.getLimit_num() != null) {
				limitNumTemp = new BigDecimal(gateOfLimitItemTotal.getLimit_num());
			}
			BigDecimal loanNumTemp = new BigDecimal(0);
			if (gateOfLimitItemTotal.getLoan_num() != null) {
				loanNumTemp = new BigDecimal(gateOfLimitItemTotal.getLoan_num());
			}

			// 可用额度(可用额度=额度-总库存-在途-本周已用+拆借）
			gateOfLimitItemTotal.setAvailable_num(limitNumTemp.subtract(gateOfLimitItemTotal.getTotal_num())
					.subtract(gateOfLimitItemTotal.getOn_way_num()).subtract(gateOfLimitItemTotal.getUsed_num())
					.add(loanNumTemp));

			gateOfLimitItemTotal.setCategory_id(null);
		} else {
			logger.error("额度闸口数据检索失败！");
		}

		// long totalEnd = System.currentTimeMillis();
		// System.out.println("totalAgeTime:" + totalAge);
		// System.out.println("totalTime:" + (totalEnd - totalStart));

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("totalLimit", totalLimit);
		resultMap.put("resultList", gateOfLimitList);
		return resultMap;
	}

	/**
	 * 修改页面中为datagrid加载值
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws IOException
	 */
	@RequestMapping(value = { "/findGateOfLimitListWithoutSum" }, method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> findGateOfLimitListWithoutSum(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> modelMap) throws IOException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			List<GateOfLimitItem> dataList = commPurchase.getGateOfLimitDataWithoutSum(gateService, params, 0);
			retMap.put("rows", dataList);
		} catch (Exception e) {
			logger.error("错误代码：", e);
		}
		return retMap;
	}

	/**
	 * 导出Excel模板
	 *
	 * @param response
	 * @return 方法执行完毕调用的画面
	 */
	@RequestMapping(value = { "/exportLimitModel.export" })
	public void exportModel(HttpServletRequest request, HttpServletResponse response) {
		String fileName = "指标导入Excel模板";
		String sheetName = "导入模板";
		String[] sheetHead = new String[] { "No.", "品类", "渠道", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月",
				"10月", "11月", "12月" };
		Map<String, String> invstockchannelmap = new HashMap<String, String>();// 渠道
		// 取得渠道
		invstockchannelmap = commPurchase.getChannelMap(invstockchannelmap);
		final List<GateOfLimitItem> items = gateService.findAllGateOfLimit().getResult();
		for (GateOfLimitItem item : items) {
			// 根据渠道id取得渠道名
			item.setEd_channel_name(invstockchannelmap.get(item.getEd_channel_id()));
		}
		try {
			ExcelExportUtil.exportEntity(logger, request, response, fileName, sheetName, sheetHead,
					new ExcelCallbackInterfaceUtil() {

						@Override
						public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp) throws Exception {
							setExcelBodyTotalForLimit(sheet, temp, items);
						}

					});
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	/**
	 * 导出具体数据，实现回调函数
	 *
	 * @param sheet
	 * @param temp
	 *            行号
	 * @param list
	 *            传入需要导出的entity list
	 */
	private void setExcelBodyTotalForLimit(WritableSheet sheet, int temp, List<GateOfLimitItem> list) throws Exception {

		WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
		WritableCellFormat textFormat = new WritableCellFormat(font);
		textFormat.setAlignment(Alignment.CENTRE);
		textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

		int index = 0;
		for (GateOfLimitItem gateOfLimitItem : list) {
			index++;
			// jxl.write.Number(列号,行号 ,内容 )
			// "序号", "提报周", "渠道", "产品组", "物料编号", "品牌", "型号", "T+3周", "T+4周",
			// "T+5周", "T+6周", "T+7周", "T+8周", "T+9周", "T+10周", "T+11周",
			// "T+12周", "T+13周"
			sheet.setColumnView(0, 10);
			sheet.addCell(new Label(0, temp, String.valueOf(index), textFormat));
			sheet.setColumnView(1, 30);
			sheet.addCell(new Label(1, temp, gateOfLimitItem.getCategory_id(), textFormat));
			sheet.setColumnView(2, 15);
			sheet.addCell(new Label(2, temp, gateOfLimitItem.getEd_channel_name(), textFormat));
			sheet.setColumnView(3, 15);
			sheet.addCell(new Label(3, temp, gateOfLimitItem.getTarget_num_month1().toString(), textFormat));
			sheet.setColumnView(4, 15);
			sheet.addCell(new Label(4, temp, gateOfLimitItem.getTarget_num_month2().toString(), textFormat));
			sheet.setColumnView(5, 15);
			sheet.addCell(new Label(5, temp, gateOfLimitItem.getTarget_num_month3().toString(), textFormat));
			sheet.setColumnView(6, 15);
			sheet.addCell(new Label(6, temp, gateOfLimitItem.getTarget_num_month4().toString(), textFormat));
			sheet.setColumnView(7, 15);
			sheet.addCell(new Label(7, temp, gateOfLimitItem.getTarget_num_month5().toString(), textFormat));
			sheet.setColumnView(8, 15);
			sheet.addCell(new Label(8, temp, gateOfLimitItem.getTarget_num_month6().toString(), textFormat));
			sheet.setColumnView(9, 15);
			sheet.addCell(new Label(9, temp, gateOfLimitItem.getTarget_num_month7().toString(), textFormat));
			sheet.setColumnView(10, 15);
			sheet.addCell(new Label(10, temp, gateOfLimitItem.getTarget_num_month8().toString(), textFormat));
			sheet.setColumnView(11, 15);
			sheet.addCell(new Label(11, temp, gateOfLimitItem.getTarget_num_month9().toString(), textFormat));
			sheet.setColumnView(12, 15);
			sheet.addCell(new Label(12, temp, gateOfLimitItem.getTarget_num_month10().toString(), textFormat));
			sheet.setColumnView(13, 15);
			sheet.addCell(new Label(13, temp, gateOfLimitItem.getTarget_num_month11().toString(), textFormat));
			sheet.setColumnView(14, 15);
			sheet.addCell(new Label(14, temp, gateOfLimitItem.getTarget_num_month12().toString(), textFormat));

			temp++;
		}
	}

	/**
	 * 额度闸口导入
	 */
	@RequestMapping(value = { "/importGateLimit" })
	public @ResponseBody HttpJsonResult<Map<String, Object>> importGateLimit(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> modelMap) {

		HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
		// 转型为MultipartHttpRequest
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

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
		int insCount = 0;
		int updCount = 0;
		int nullRow = 0;
		int totalCount = 0;
		List<Map<String, Object>> insertObj = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> updateObj = new ArrayList<Map<String, Object>>();
		// 判断用 渠道code
		String channelCode = "";
		try {
			List<String[]> list = ExcelReader.readExcel(file.getInputStream(), 1);
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
			// 修改人 modify_user
			String modifyUser = String.valueOf(WebUtil.currentUserName(request));

			// 品类数据存入HashMap
			Map<String, String> categoryMap = new HashMap<String, String>();

			// 渠道数据存入HashMap
			Map<String, String> invstockchannelmap = new HashMap<String, String>();

			// 调用stockCommonService，取得渠道数据
			ServiceResult<List<InvStockChannel>> resultChannel = stockCommonService.getChannels();
			if (resultChannel.getSuccess() && resultChannel.getResult() != null) {
				List<InvStockChannel> invStockChannel = resultChannel.getResult();
				for (InvStockChannel invstockchannel : invStockChannel) {
					invstockchannelmap.put(invstockchannel.getName(), invstockchannel.getChannelCode());// 将name作为key，channelCode作为value存入map中
				}
			}

			long startTime = System.currentTimeMillis();
			// 读取数据
			for (int i = 1; i < list.size(); i++) {
				String[] str = list.get(i);
				// 品类
				String category = StringUtil.nullSafeString(str[1]).trim();
				// 渠道
				String edChannelName = StringUtil.nullSafeString(str[2]).trim();
				// 指标
				String[] monthTargetNum = new String[12];

				boolean isAllNull = StringUtil.isEmpty(category, true) && StringUtil.isEmpty(edChannelName, true);
				for (int j = 0; j < 12; j++) {
					monthTargetNum[j] = StringUtil.nullSafeString(str[j + 3]).trim();
					if (isAllNull) {
						if (!StringUtil.isEmpty(monthTargetNum[j], true)) {
							isAllNull = false;
							break;
						}
					}
				}
				if (isAllNull) {
					nullRow++;
					continue;
				}
				// 导入模板内容的非空判断****************START*******************************
				// 品类
				if (StringUtil.isEmpty(category, true)) {
					MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【品类】不能为空! 请核查后重新导入！";

					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br>";
						sb.append(MsgList);
					}
					continue;
				}

				// 渠道
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

				// 导入模板内容的非空判断****************END*********************************

				// 导入模板内容的合理性判断及权限判断**************START**********************
				// 用渠道名称取得渠道code及渠道的正确性判断
				channelCode = invstockchannelmap.get(edChannelName);
				// 渠道的正确性判断
				if ("".equals(channelCode) || channelCode == null) {
					MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的渠道【" + edChannelName + "】无法识别！请检查后重新导入！";
					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br>";
						sb.append(MsgList);
					}
					continue;
				}

				// 品类取得 根据产品code
				commPurchase.getCategoryMap(categoryMap);
				if (!categoryMap.containsValue(category)) {
					MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的品类【" + category + "】无法识别！请检查后重新导入！";
					if (StringUtil.isEmpty(MsgList, true)) {
						sb.append(MsgList);
					} else {
						MsgList = MsgList + "<br>";
						sb.append(MsgList);
					}
					continue;
				}

				// 指标正确性判断
				Boolean isFloat = false;
				for (int j = 0; j < 12; j++) {
					if (StringUtil.isEmpty(monthTargetNum[j], true) || !CommUtil.canToFloat(monthTargetNum[j])
							|| ((new BigDecimal(monthTargetNum[j]).compareTo(new BigDecimal(0))) == -1)) {
						MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【:" + j + "】月指标应该是空或者大于等于零的数值! 请核查后重新导入！";
						if (StringUtil.isEmpty(MsgList, true)) {
							sb.append(MsgList);
						} else {
							MsgList = MsgList + "<br>";
							sb.append(MsgList);
						}
						isFloat = true;
						break;
					}
				}
				if (isFloat) {
					continue;
				}
				long tempStart = System.currentTimeMillis();
				// 导入模板内容的数值形式判断**************END*******************************
				for (int j = 0; j < 12; j++) {
					totalCount++;
					Map<String, Object> params = new HashMap<String, Object>();
					// params.put("id",
					// CommPurchase.getWPOrderId(purchaseCommonService, "G01"));
					// 模板提供项
					params.put("category_id", category);
					params.put("ed_channel_id", channelCode);
					params.put("target_num", ConvertUtil.toDecimal(monthTargetNum[j], new BigDecimal(0)));
					params.put("month", j + 1);
					params.put("modify_user", modifyUser);

					// 验证记录是否存在，如果不存在则不作处理
					ServiceResult<Boolean> isExistResult = gateService.isExistGateOfLimit(category, channelCode, j + 1);
					if (!(isExistResult.getSuccess() && isExistResult.getResult())) {

						// 录入额度闸口数据
						insertObj.add(params);
						// ServiceResult<Boolean> insResult =
						// gateService.insertGateOfLimit(params);
						// if (insResult.getSuccess() && insResult.getResult())
						// {
						// insCount++;
						// } else {
						// MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据插入DB时失败！";
						// if (StringUtil.isEmpty(MsgList, true)) {
						// sb.append(MsgList);
						// } else {
						// MsgList = MsgList + "<br>";
						// sb.append(MsgList);
						// }
						// continue;
						// }
					} else {
						// 更新额度闸口数据
						updateObj.add(params);
						// ServiceResult<Boolean> updResult =
						// gateService.updateGateOfLimit(params);
						// if (updResult.getSuccess() && updResult.getResult())
						// {
						// updCount++;
						// } else {
						// MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据更新DB时失败！";
						// if (StringUtil.isEmpty(MsgList, true)) {
						// sb.append(MsgList);
						// } else {
						// MsgList = MsgList + "<br>";
						// sb.append(MsgList);
						// }
						// continue;
						// }
					}
				}
				long tempEnd = System.currentTimeMillis();
				logger.info("Time:" + (tempEnd - tempStart));
			}

			long endTime = System.currentTimeMillis();
			logger.info("BuildTime:" + (endTime - startTime));

			// 日志操作记录 xuelin.zhao start/////////////////////
			// this.logAuditService.unionLog("haier_limit_gate_t", null, null,
			// "0", modifyUser, "");
			// end //////////////////////////////////////////

			if (StringUtils.isNotBlank(sb)) {
				modelMap.put("warn", sb.toString());
				result.setData(modelMap);
				return result;
			}
			startTime = System.currentTimeMillis();
			ServiceResult<Map<String, Integer>> insResult = gateService.insertGateOfLimit(insertObj);
			ServiceResult<Map<String, Integer>> updateResult = gateService.updateGateOfLimit(updateObj);
			insResult.getResult().get("success");
			updateResult.getResult().get("success");

			endTime = System.currentTimeMillis();
			logger.info("DBTime:" + (endTime - startTime));
			// 导入文件不存在，DB里面存在的记录删除处理
			gateService.deleteGateOfLimit();
			// Delete_flag重置
			gateService.updateDeleteFlag();

			modelMap.put("total", list.size() - nullRow - 1);
			modelMap.put("updSuccess", updateResult.getResult().get("success"));
			modelMap.put("insSuccess", insResult.getResult().get("success"));
			modelMap.put("ignore",
					totalCount - updateResult.getResult().get("success") - insResult.getResult().get("success"));
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
	 * 指标修改页面调转
	 *
	 * @param request
	 * @param modelMap
	 *            调转页面数据存放map
	 * @return
	 */
	@RequestMapping(value = { "/gotoChangeTarget" }, method = { RequestMethod.GET })
	String gotoChangeTarget(HttpServletRequest request, Map<String, Object> modelMap) {
		// 当前日期取得
		String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
		// 转化成周
		String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null, "1");
		modelMap.put("currentweek", currentWeek);
		return "purchase/gateOfLimitTarget";
	}

	/**
	 * 个别额度修改页面调转
	 *
	 * @param request
	 * @param modelMap
	 *            调转页面数据存放map
	 * @return
	 */
	@RequestMapping(value = { "/gotoChangeLimitIndividual" }, method = { RequestMethod.GET })
	String gotoChangeLimitIndividual(HttpServletRequest request, Map<String, Object> modelMap) {
		// 当前日期取得
		String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
		// 转化成周
		String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null, "1");
		modelMap.put("currentweek", currentWeek);
		return "purchase/gateOfLimitIndividual";
	}

	/**
	 * 拆借修改页面调转
	 *
	 * @param request
	 * @param modelMap
	 *            调转页面数据存放map
	 * @return
	 */
	@RequestMapping(value = { "/gotoChangeLimitLoan" }, method = { RequestMethod.GET })
	String gotoChangeLimitLoan(HttpServletRequest request, Map<String, Object> modelMap) {
		// 当前日期取得
		String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
		// 转化成周
		String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null, "1");
		modelMap.put("currentweek", currentWeek);
		return "purchase/gateOfLimitLoan";
	}

	/**
	 * 指标修改页面保存
	 *
	 * @param request
	 */
	@RequestMapping(value = { "saveGateOfLimitTarget" }, method = { RequestMethod.POST })
	@ResponseBody
	HttpJsonResult<String> saveGateOfLimitTarget(@RequestParam(required = false) String allData,
			HttpServletRequest request, HttpServletResponse response) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();
		logger.debug("GateController: saveGateOfLimitTarget");

		// 取得提交者
		String user = request.getSession().getAttribute("userName").toString();
		List<GateOfLimitItem> gateOfLimitList = new ArrayList<GateOfLimitItem>();
		try {
			GateOfLimitItem[] gateOfStockExceedArray = JSON.parse(allData, GateOfLimitItem[].class);
			gateOfLimitList = Arrays.asList(gateOfStockExceedArray);
		} catch (Exception e) {
			logger.error("saveGateOfLimitTarget中json转换为List<GateOfLimitItem>失败");
			logger.error("错误代码:" + e.getMessage());
			result.setMessage("json解析失败！");
			result.setTotalCount(0);
			return result;
		}
		// 总指标
		BigDecimal totalTarget = new BigDecimal(0);
		// 总额度
		BigDecimal total_num = new BigDecimal(0);
		// 计算总指标、总额度
		for (GateOfLimitItem gateOfLimitItem : gateOfLimitList) {
			totalTarget = totalTarget.add(new BigDecimal(
					org.apache.commons.lang.StringUtils.defaultIfEmpty(gateOfLimitItem.getTarget_num(), "0")));
			total_num = total_num.add(new BigDecimal(
					org.apache.commons.lang.StringUtils.defaultIfEmpty(gateOfLimitItem.getLimit_num(), "0")));
		}
		// 计算指标比例，并分配额度
		for (GateOfLimitItem gateOfLimitItem : gateOfLimitList) {
			BigDecimal limitNum = total_num.multiply(new BigDecimal(gateOfLimitItem.getTarget_num()))
					.divide(totalTarget, 2, BigDecimal.ROUND_HALF_UP);
			// 设置修改人
			gateOfLimitItem.setModify_user(user);
			gateOfLimitItem.setLimit_num(String.valueOf(limitNum));

		}

		// 日志操作记录 start xuelin.zhao//////////////////////////////
		getGateOfLimitItem(user, "haier_limit_gate_t_target", gateOfLimitList);
		// end/////////////////////////////////////////////////////

		// 执行插入service
		ServiceResult<Boolean> modifyResult = gateService.modifyGateOfLimitById(gateOfLimitList);
		if (modifyResult.getSuccess() && modifyResult.getResult() != null && modifyResult.getResult() == true) {
			// DB操作成功
			result.setTotalCount(1);
			result.setMessage("保存成功！");
		} else {
			// DB操作失败
			result.setTotalCount(0);
			result.setMessage("保存失败！");
		}
		return result;
	}

	/**
	 * 获得额度闸口中已修改的原始数据 xuelin.zhao user 修改人 type 类别 gateOfLimitList 页面中修改的数据list
	 */
	private void getGateOfLimitItem(String user, String type, List<GateOfLimitItem> gateOfLimitList) {
		// 日志操作记录 start xuelin.zhao//////////////////////////////
		Map<String, Object> params = new HashMap<String, Object>();
		// 取得渠道map
		Map<String, String> invStockChannelMap = new HashMap<String, String>();
		invStockChannelMap = commPurchase.getChannelMap(invStockChannelMap);
		ServiceResult<List<GateOfLimitItem>> resultTemp = new ServiceResult<List<GateOfLimitItem>>();
		// 当前日期取得
		String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
		// 转化成周
		String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null, "0");

		List<GateOfLimitItem> gateOfLimitListTmp = null;
		List<GateOfLimitItem> gateOfLimitListLog = new ArrayList<GateOfLimitItem>();
		// T+3月份取得
		String endDate = CommUtil.weekToSetDateDay(currentWeek, 4);
		DateCal dateCal = new DateCal(endDate);
		Integer reportMonth = Integer.valueOf(dateCal.addWeek(3).substring(5, 7));
		params.put("month", reportMonth);
		// 检索参数params中传入needResult 不检索总计
		params.put("needResult", "part");
		// 在DB中检索详细信息
		resultTemp = gateService.findGateOfLimit(params);

		// 重新组织DB中检索的数据
		if (resultTemp.getSuccess() && resultTemp.getResult() != null) {
			gateOfLimitListTmp = resultTemp.getResult();
			for (GateOfLimitItem gateOfLimitItem : gateOfLimitListTmp) {
				for (GateOfLimitItem gateOfLimitItemU : gateOfLimitList) {
					if (gateOfLimitItemU.getEd_channel_id().equals(gateOfLimitItem.getEd_channel_id())
							&& gateOfLimitItemU.getCategory_id().equals(gateOfLimitItem.getCategory_id())) {
						// 为渠道名称赋值
						gateOfLimitItem.setEd_channel_name(invStockChannelMap.get(gateOfLimitItem.getEd_channel_id()));
						gateOfLimitListLog.add(gateOfLimitItem);
					}
				}

			}
			this.logAuditService.unionLog(type, gateOfLimitListLog, gateOfLimitList, "1", user, "");
		}
	}

	/**
	 * 个别额度修改页面保存
	 *
	 * @param request
	 */
	@RequestMapping(value = { "saveGateOfLimitIndividual" }, method = { RequestMethod.POST })
	@ResponseBody
	HttpJsonResult<String> saveGateOfLimitIndividual(@RequestParam(required = false) String allData,
			HttpServletRequest request, HttpServletResponse response) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();
		logger.debug("GateController: saveGateOfLimitIndividual");

		// 取得提交者
		String user = WebUtil.currentUserName(request);
		List<GateOfLimitItem> gateOfLimitList = new ArrayList<GateOfLimitItem>();
		try {
			GateOfLimitItem[] gateOfStockExceedArray = JSON.parse(allData, GateOfLimitItem[].class);
			gateOfLimitList = Arrays.asList(gateOfStockExceedArray);
		} catch (Exception e) {
			logger.error("saveGateOfLimitTarget中json转换为List<GateOfLimitItem>失败");
			logger.error("错误代码:" + e.getMessage());
			result.setMessage("json解析失败！");
			result.setTotalCount(0);
			return result;
		}

		// 修改entity数据
		for (GateOfLimitItem gateOfLimitItem : gateOfLimitList) {
			gateOfLimitItem.setModify_user(user);
		}

		// 日志操作记录 start xuelin.zhao//////////////////////////////
		getGateOfLimitItem(user, "haier_limit_gate_t_limit", gateOfLimitList);
		// end/////////////////////////////////////////////////////

		// 执行插入service
		ServiceResult<Boolean> modifyResult = gateService.modifyGateOfLimitById(gateOfLimitList);
		if (modifyResult.getSuccess() && modifyResult.getResult() != null && modifyResult.getResult() == true) {
			// 总额度修改
			HashMap<String, Object> params = new HashMap<String, Object>();
			List<GateOfLimitItem> gateOfLimitListForSum = commPurchase.getGateOfLimitDataWithoutSum(gateService, params,
					1);
			BigDecimal limitSum = new BigDecimal(0);
			for (GateOfLimitItem gateOfLimitItem : gateOfLimitListForSum) {
				limitSum = limitSum.add(new BigDecimal(gateOfLimitItem.getLimit_num()));
			}
			params.put("limit_sum_num", limitSum);
			ServiceResult<Boolean> tempResult = gateService.updateLimitSum(params);
			if (tempResult.getSuccess() && tempResult.getResult() != null && tempResult.getResult() == true) {
				// DB操作成功
				result.setTotalCount(1);
				result.setMessage("保存成功！");
			} else {
				// DB操作失败
				result.setTotalCount(0);
				result.setMessage("保存失败！");
			}
		} else {
			// DB操作失败
			result.setTotalCount(0);
			result.setMessage("保存失败！");
		}
		return result;
	}

	/**
	 * 拆借修改页面保存
	 *
	 * @param request
	 */
	@RequestMapping(value = { "saveGateOfLimitLoan" }, method = { RequestMethod.POST })
	@ResponseBody
	HttpJsonResult<String> saveGateOfLimitLoan(@RequestParam(required = false) String allData,
			HttpServletRequest request, HttpServletResponse response) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();
		logger.debug("GateController: saveGateOfLimitLoan");

		// 取得提交者
		String user = WebUtil.currentUserName(request);
		List<GateOfLimitItem> gateOfLimitList = new ArrayList<GateOfLimitItem>();
		try {
			GateOfLimitItem[] gateOfStockExceedArray = JSON.parse(allData, GateOfLimitItem[].class);
			gateOfLimitList = Arrays.asList(gateOfStockExceedArray);
		} catch (Exception e) {
			logger.error("saveGateOfLimitTarget中json转换为List<GateOfLimitItem>失败");
			logger.error("错误代码:" + e.getMessage());
			result.setMessage("json解析失败！");
			result.setTotalCount(0);
			return result;
		}
		// 修改entity数据
		for (GateOfLimitItem gateOfLimitItem : gateOfLimitList) {
			if ("".equals(gateOfLimitItem.getLoan_num())) {
				gateOfLimitItem.setLoan_num(null);
			}
			gateOfLimitItem.setModify_user(user);
		}

		// 日志操作记录 start xuelin.zhao//////////////////////////////
		getGateOfLimitItem(user, "haier_limit_gate_t_loan", gateOfLimitList);
		// end/////////////////////////////////////////////////////

		// 执行插入service
		ServiceResult<Boolean> modifyResult = gateService.modifyLoanNum(gateOfLimitList);
		if (modifyResult.getSuccess() && modifyResult.getResult() != null && modifyResult.getResult() == true) {
			// DB操作成功
			result.setTotalCount(1);
			result.setMessage("保存成功！");
		} else {
			// DB操作失败
			result.setTotalCount(0);
			result.setMessage("保存失败！");
		}
		return result;
	}

	/**
	 * 总额度修改页面保存
	 *
	 * @param request
	 */
	@RequestMapping(value = { "saveGateOfLimitTotal" }, method = { RequestMethod.POST })
	@ResponseBody
	HttpJsonResult<String> saveGateOfLimitTotal(@RequestParam(required = false) String modifyTotalLimit,
			HttpServletRequest request, HttpServletResponse response) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();
		logger.debug("GateController: saveGateOfLimitTarget");
		Map<String, Object> params = new HashMap<String, Object>();
		// 取得提交者
		String user = WebUtil.currentUserName(request);
		List<GateOfLimitItem> gateOfLimitList = commPurchase.getGateOfLimitDataWithoutSum(gateService, params, 1);

		// 总指标
		BigDecimal totalTarget = new BigDecimal(0);
		// 计算总指标
		for (GateOfLimitItem gateOfLimitItem : gateOfLimitList) {
			totalTarget = totalTarget.add(new BigDecimal(gateOfLimitItem.getTarget_num()));
		}
		// 定义剩余额度
		BigDecimal remainLimit = new BigDecimal(modifyTotalLimit);
		// 计算指标比例，并分配额度
		for (Iterator<GateOfLimitItem> it = gateOfLimitList.iterator(); it.hasNext();) {
			GateOfLimitItem gateOfLimitItem = it.next();
			// 根据指标计算额度
			BigDecimal limitNum = new BigDecimal(modifyTotalLimit)
					.multiply(new BigDecimal(gateOfLimitItem.getTarget_num()))
					.divide(totalTarget, 2, BigDecimal.ROUND_HALF_UP);
			// 设置修改人
			gateOfLimitItem.setModify_user(user);
			if (it.hasNext()) {
				gateOfLimitItem.setLimit_num(String.valueOf(limitNum));
				// 计算剩余额度
				remainLimit = remainLimit.subtract(limitNum);
			} else {
				gateOfLimitItem.setLimit_num(remainLimit.toString());
			}

		}

		// 日志操作录入 start xuelin.zhao/////////////////////
		this.logAuditService.unionLog("haier_limit_gate_t_limit_sum", request.getParameter("currentTotalLimit"),
				modifyTotalLimit, "1", user, "");
		// end///////////////////////////////////////////

		// 执行插入service
		ServiceResult<Boolean> modifyResult = gateService.modifyGateOfLimitById(gateOfLimitList);
		if (modifyResult.getSuccess() && modifyResult.getResult() != null && modifyResult.getResult() == true) {
			// 总额度修改
			params = new HashMap<String, Object>();
			params.put("limit_sum_num", modifyTotalLimit);
			ServiceResult<Boolean> tempResult = gateService.updateLimitSum(params);
			if (tempResult.getSuccess() && tempResult.getResult() != null && tempResult.getResult() == true) {
				// DB操作成功
				result.setTotalCount(1);
				result.setMessage("保存成功！");
			} else {
				// DB操作失败
				result.setTotalCount(0);
				result.setMessage("保存失败！");
			}
		} else {
			// DB操作失败
			result.setTotalCount(0);
			result.setMessage("保存失败！");
		}
		return result;
	}

	/**
	 * 跳转数据权限设置查询页面
	 *
	 * @param request
	 */
	@RequestMapping(value = { "/gotoGateOfDataPrivilege" }, method = { RequestMethod.GET })
	String gotoGateOfDataPrivilege(HttpServletRequest request, Map<String, Object> modelMap) {
		logger.debug("GateController: gotoGateOfDataPrivilege");
		return "purchase/gateOfDataPrivilegeDetail";
	}

	/**
	 * 数据权限设置查询页面中为datagrid加载值
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws IOException
	 */
	@RequestMapping(value = { "findGateOfDataPrivilege" }, method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> findGateOfDataPrivilege(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> modelMap) throws IOException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();

		ServiceResult<List<GateOfDataPrivilegeItem>> result = new ServiceResult<List<GateOfDataPrivilegeItem>>();

		// 取得渠道map
		Map<String, String> invStockChannelMap = new HashMap<String, String>();
		invStockChannelMap = commPurchase.getChannelMap(invStockChannelMap);
		invStockChannelMap.put("DS", "690电商渠道");
		// 取得产品组map
		Map<String, String> productgroupmap = new HashMap<String, String>();
		commPurchase.getProductMap(productgroupmap);
		// 是否map
		Map<String, String> trueOrFalseMap = commPurchase.getValueMeaningMap(dataDictionaryService,
				TRUE_FALSE_DISTINCT);
		ServiceResult<List<SysUser>> resultTemp = systemService.findUser(null, null, null, null);
		Map<Integer, String> userInfo = new HashMap<Integer, String>();
		if (resultTemp.getSuccess() && resultTemp.getResult() != null) {
			for (SysUser sysUser : resultTemp.getResult()) {
				userInfo.put(sysUser.getUserId(), sysUser.getUserName());
			}
		}
		// 在DB中检索详细信息
		result = gateService.findGateOfDataPrivilege(params);
		// 重新组织DB中检索的数据
		if (result.getSuccess() && result.getResult() != null) {
			for (GateOfDataPrivilegeItem gateOfDataPrivilegeItem : result.getResult()) {
				String ed_channel_name = "";
				String product_group_name = "";
				String user_name_contact = "";
				if (gateOfDataPrivilegeItem.getEd_channel_id() != null) {
					String[] ids1 = gateOfDataPrivilegeItem.getEd_channel_id().split(",");
					for (String s : ids1) {
						s = invStockChannelMap.get(s);
						if ("".equals(ed_channel_name)) {
							ed_channel_name += s;
						} else {
							ed_channel_name = ed_channel_name + "," + s;
						}
					}
				}
				if (gateOfDataPrivilegeItem.getProduct_group_id() != null) {
					String[] ids2 = gateOfDataPrivilegeItem.getProduct_group_id().split(",");

					for (String s : ids2) {
						s = productgroupmap.get(s);
						if ("".equals(product_group_name)) {
							product_group_name += s;
						} else {
							product_group_name = product_group_name + "," + s;
						}
					}
				}
				if (gateOfDataPrivilegeItem.getUser_id() != null && !"".equals(gateOfDataPrivilegeItem.getUser_id())) {
					String[] userIdList = gateOfDataPrivilegeItem.getUser_id().split(",");
					for (String userId : userIdList) {
						String userName = userInfo.get(Integer.valueOf(userId));
						if ("".equals(user_name_contact)) {
							user_name_contact += userName;
						} else {
							user_name_contact = user_name_contact + "," + userName;
						}
					}

					// 用户名
					gateOfDataPrivilegeItem.setUser_name(user_name_contact);

				}
				// 为渠道名称赋值
				gateOfDataPrivilegeItem.setEd_channel_name(ed_channel_name);
				// 为产品组名称赋值
				gateOfDataPrivilegeItem.setProduct_group_name(product_group_name);
				gateOfDataPrivilegeItem.setGate_limit_name(trueOrFalseMap.get(gateOfDataPrivilegeItem.getGate_limit()));
			}
			retMap.put("rows", result.getResult());
			// response.getWriter().write(JsonUtil.toJson(retMap));
			// response.getWriter().flush();
			// response.getWriter().close();
		} else {
			logger.error("数据权限设置闸口数据检索失败！");
		}

		return retMap;
	}

	/**
	 * 数据权限设置修改页面
	 *
	 * @param request
	 */
	@RequestMapping(value = { "gotoEditGateOfDataPrivilege" }, method = { RequestMethod.GET })
	String gotoEditGateOfDataPrivilege(@RequestParam(required = false) String operatorType,
			@RequestParam(required = false) String role_id, @RequestParam(required = false) String role_name,
			@RequestParam(required = false) String gate_limit, @RequestParam(required = false) String productGroupIds,
			@RequestParam(required = false) String channelIds, HttpServletRequest request,
			Map<String, Object> modelMap) {
		logger.debug("GateController: gotoEditGateOfDataPrivilege");

		// 向前台传递产品组初始值
		if (productGroupIds != null && productGroupIds.length() > 0) {
			List<String> selectProductGroupIds = Arrays.asList(productGroupIds.split(","));
			modelMap.put("selectProductGroupIds", JsonUtil.toJson(selectProductGroupIds));
		}
		// 向前台传递渠道初始值
		if (channelIds != null && channelIds.length() > 0) {
			List<String> selectChannelIds = Arrays.asList(channelIds.split(","));
			modelMap.put("selectChannelIds", JsonUtil.toJson(selectChannelIds));
		}
		// 品类不存在的产品组从List中删除
		List<ItemAttribute> productGroupList = getAllProductGroupList(request);
		List<ItemAttribute> productGroup = new ArrayList<ItemAttribute>();
		for (ItemAttribute item : productGroupList) {
			if (item.getCbsCategory() != null && !"".equals(item.getCbsCategory())) {
				productGroup.add(item);
			}
		}
		modelMap.put("channelsList", getAllChannelsList(request));
		modelMap.put("productGroupList", productGroup);
		modelMap.put("operatorType", operatorType);
		modelMap.put("role_id", role_id);
		modelMap.put("roleName", role_name);
		if (!"undefined".equals(gate_limit)) {
			modelMap.put("gate_limit", gate_limit);
		}
		return "purchase/gateOfDataPrivilegeEdit";
	}

	/**
	 * 获取全部产品组list
	 *
	 * @return ItemAttribute List<>
	 */
	List<ItemAttribute> getAllProductGroupList(HttpServletRequest request) {
		List<ItemAttribute> result = itemService.queryProductGroupByCbsCategory(null).getResult();
		return result;
	}

	/**
	 * 获取全部渠道list
	 *
	 * @return InvStockChannel List<>
	 */
	List<InvStockChannel> getAllChannelsList(HttpServletRequest request) {
		List<InvStockChannel> result = stockCommonService.getChannels().getResult();
		// 690电商渠道处理
		InvStockChannel invStockChannel = new InvStockChannel();
		invStockChannel.setChannelCode("DS");
		invStockChannel.setName("690电商渠道");
		result.add(invStockChannel);
		return result;
	}

	/**
	 * 数据权限设置查询页面中删除数据
	 *
	 * @param removeIds
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = { "removeGateOfDataPrivilege" }, method = { RequestMethod.POST })
	@ResponseBody
	public HttpJsonResult<String> findGateOfDataPrivilege(@RequestParam(required = false) String removeIds,
			HttpServletRequest request, HttpServletResponse response) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();

		List<Long> removeIdList = new ArrayList<>();
		try {
			String[] removeIdArray = JSON.parse(removeIds, String[].class);
			for (String s : removeIdArray) {
				try {
					removeIdList.add(Long.valueOf(s));
				} catch (Exception ee) {

				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 转化为list
		// 处理需要传递删除数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("role_id_list", removeIdList);
		// 订单提交更新
		ServiceResult<Boolean> removeResult = gateService.removeGateOfDataPrivilege(params);
		if (removeResult.getSuccess() && removeResult.getResult() != null && removeResult.getResult() == true) {
			// DB操作成功
			result.setTotalCount(1);
			result.setMessage("删除成功！");
		} else {
			// DB操作失败
			result.setTotalCount(0);
			result.setMessage("删除失败！");
		}
		return result;
	}

	/**
	 * 跳转数据权限用户设置页面
	 *
	 * @param request
	 */
	@RequestMapping(value = { "gotoGateOfUserPrivilege" }, method = { RequestMethod.GET })
	String gotoGateOfUserPrivilege(HttpServletRequest request, Map<String, Object> modelMap) {
		logger.debug("GateController: gotoGateOfUserPrivilege");

		ServiceResult<List<GateOfDataPrivilegeItem>> resultRole = gateService.findGateOfDataPrivilege(null);
		if (resultRole.getSuccess() && resultRole.getResult() != null) {
			modelMap.put("roleList", JsonUtil.toJson(resultRole.getResult()));
		}
		return "purchase/gateOfUserPrivilegeDetail";
	}

	/**
	 * 数据权限用户设置页面保存
	 *
	 * @param request
	 */
	@RequestMapping(value = { "saveGateOfUserPrivilege" }, method = { RequestMethod.POST })
	@ResponseBody
	HttpJsonResult<String> saveGateOfUserPrivilege(@RequestParam(required = false) String allData,
			HttpServletRequest request, HttpServletResponse response) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();
		logger.debug("GateController: saveGateOfUserPrivilege");

		// 取得提交者
		String user = request.getSession().getAttribute("userName").toString();
		List<GateOfDataPrivilegeItem> gateOfDataPrivilegeItemList = new ArrayList<GateOfDataPrivilegeItem>();
		try {
			GateOfDataPrivilegeItem[] gateOfDataPrivilegeItemArray = JSON.parse(allData,
					GateOfDataPrivilegeItem[].class);
			gateOfDataPrivilegeItemList = Arrays.asList(gateOfDataPrivilegeItemArray);
		} catch (Exception e) {
			logger.error("saveGateOfUserPrivilege中json转换为List<GateOfDataPrivilegeItem>失败");
			logger.error("错误代码:" + e.getMessage());
			result.setMessage("json解析失败！");
			result.setTotalCount(0);
			return result;
		}
		// 执行插入service
		ServiceResult<Boolean> insertResult = gateService.saveGateOfUserPrivilege(gateOfDataPrivilegeItemList);
		if (insertResult.getSuccess() && insertResult.getResult() != null && insertResult.getResult() == true) {
			// DB操作成功
			result.setTotalCount(1);
			result.setMessage("保存成功！");
		} else {
			// DB操作失败
			result.setTotalCount(0);
			result.setMessage("保存失败！");
		}
		return result;
	}

	/**
	 * 数据权限设置修改页面保存
	 *
	 * @param request
	 */
	@RequestMapping(value = { "saveGateOfDataPrivilege" }, method = { RequestMethod.POST })
	@ResponseBody
	HttpJsonResult<String> saveGateOfDataPrivilege(@RequestParam(required = false) String operatorType,
			@RequestParam(required = false) String role_id, @RequestParam(required = false) String role_name,
			@RequestParam(required = false) String gate_limit, @RequestParam(required = false) String productGroupIds,
			@RequestParam(required = false) String channelIds, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("GateController: saveGateOfDataPrivilege");

		HttpJsonResult<String> result = new HttpJsonResult<String>();
		GateOfDataPrivilegeItem gateOfDataPrivilegeItem = new GateOfDataPrivilegeItem();
		String product_group_id = "";
		String ed_channel_id = "";
		String cbs_catgory = "";
		String[] productGroupIdArray = {};
		String[] edChannelIdArray = {};
		gateOfDataPrivilegeItem.setRole_id(role_id);
		gateOfDataPrivilegeItem.setRole_name(role_name);
		gateOfDataPrivilegeItem.setGate_limit(gate_limit);
		try {
			if (ADD_DATA_PRIVILEGE.equals(operatorType)) {
				// 岗位添加场合，岗位名存在Check
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("role_name", role_name);
				ServiceResult<List<GateOfDataPrivilegeItem>> resultCheck = new ServiceResult<List<GateOfDataPrivilegeItem>>();
				// 在DB中检索详细信息
				resultCheck = gateService.findGateOfDataPrivilege(params);
				if (resultCheck.getSuccess() && resultCheck.getResult() != null) {
					if (resultCheck.getResult().size() == 1) {
						// DB操作失败
						result.setTotalCount(0);
						result.setMessage("输入的岗位名已经存在，请重新输入！");
						return result;
					}
				} else {
					logger.error("数据权限设置闸口数据检索失败！");
				}
			}
			// 从前台获取产品组ID并set入entity
			productGroupIdArray = (String[]) JSON.parse(productGroupIds, String[].class);
			for (String s : productGroupIdArray) {
				if ("".equals(product_group_id)) {
					product_group_id += s;
				} else {
					product_group_id = product_group_id + "," + s;
				}
			}
			gateOfDataPrivilegeItem.setProduct_group_id(product_group_id);
			// 从前台获取渠道ID并set入entity
			edChannelIdArray = (String[]) JSON.parse(channelIds, String[].class);
			for (String s : edChannelIdArray) {
				if ("".equals(ed_channel_id)) {
					ed_channel_id += s;
				} else {
					ed_channel_id = ed_channel_id + "," + s;
				}
			}
			gateOfDataPrivilegeItem.setEd_channel_id(ed_channel_id);
		} catch (Exception e) {
			logger.error("错误代码:" + e.getMessage());
			result.setMessage("json解析失败！");
			result.setTotalCount(0);
			return result;
		}
		// 根据产品组取得品类
		if (!"".equals(product_group_id)) {
			// 取得品类map
			Map<String, String> categoryMap = new HashMap<String, String>();
			commPurchase.getCategoryMap(categoryMap);
			for (String s : productGroupIdArray) {
				// 按商用空调 家用空调顺序添加 商用空调的品类是商用空调 走完一次循环后cbs_catgory = '商用空调'
				// 在走家用空调循环时候 因为家用空调的品类是空调
				// 所以cbs_catgory.contains(s)（'商用空调'.contains('空调')） == true
				// 这样就会走第一个if 不做任何处理 zzb 2017-03-28
				s = categoryMap.get(s);
				// 屏蔽 加入重复数据后续逻辑处理 zzb 2017-03-28
				/*
				 * if (cbs_catgory.contains(s) || "".equals(s)) {
				 * 
				 * } else
				 */
				if ("".equals(cbs_catgory)) {
					cbs_catgory += s;
				} else {
					cbs_catgory = cbs_catgory + "," + s;
				}
			}
			// 此时拿到的cbs_catgory有重复数据需要去重 zzb 2017-03-28
			String[] cbsCatgoryArray = null;
			Set<String> set = new HashSet<String>();
			if (cbs_catgory.indexOf(",") > 0) {
				cbsCatgoryArray = cbs_catgory.split(",");
				for (int i = 0; i < cbsCatgoryArray.length; i++) {
					set.add(cbsCatgoryArray[i]);
				}
				cbs_catgory = StringUtils.join(set.toArray(new String[set.size()]), ",");
			}

			gateOfDataPrivilegeItem.setCbs_catgory(cbs_catgory);
		}

		// 执行插入service
		ServiceResult<Boolean> saveResult = gateService.saveGateOfDataPrivilege(operatorType, gateOfDataPrivilegeItem);
		if (saveResult.getSuccess() && saveResult.getResult() != null && saveResult.getResult() == true) {
			// DB操作成功
			result.setTotalCount(1);
			result.setMessage("保存成功！");
		} else {
			// DB操作失败
			result.setTotalCount(0);
			result.setMessage("保存失败！");
		}
		return result;
	}

	/**
	 * 用户设定页面中为datagrid加载值
	 *
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws IOException
	 */
	@RequestMapping(value = { "findGateOfUserPrivilege" }, method = { RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> findGateOfUserPrivilege(@RequestParam(required = false) String user_name, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("user_name", user_name);

			ServiceResult<List<SysUser>> resultTemp = systemService.findUser(user_name, null, null, null);
			List<Integer> userIdList = new ArrayList<Integer>();
			Map<Integer, String> userInfo = new HashMap<Integer, String>();
			ServiceResult<List<GateOfDataPrivilegeItem>> result = new ServiceResult<List<GateOfDataPrivilegeItem>>();
			if (resultTemp.getSuccess() && resultTemp.getResult() != null) {
				for (SysUser sysUser : resultTemp.getResult()) {
					userIdList.add(sysUser.getUserId());
					userInfo.put(sysUser.getUserId(), sysUser.getUserName());
				}
			}
			params.put("user_id_list", userIdList);
			// 在DB中检索详细信息
			result = gateService.findGateOfUserPrivilege(params);
			// 重新组织DB中检索的数据
			if (result.getSuccess() && result.getResult() != null) {
				for (GateOfDataPrivilegeItem gateOfDataPrivilegeItem : result.getResult()) {
					// 用户名
					gateOfDataPrivilegeItem
							.setUser_name(userInfo.get(Integer.valueOf(gateOfDataPrivilegeItem.getUser_id())));
					userInfo.remove(Integer.valueOf(gateOfDataPrivilegeItem.getUser_id()));
				}
				List<Integer> keyList = new ArrayList<Integer>(userInfo.keySet());
				for (int i = 0; i < keyList.size(); i++) {
					GateOfDataPrivilegeItem gateOfDataPrivilegeItem = new GateOfDataPrivilegeItem();
					gateOfDataPrivilegeItem.setUser_id(String.valueOf(keyList.get(i)));
					gateOfDataPrivilegeItem.setUser_name(userInfo.get(keyList.get(i)));
					result.getResult().add(gateOfDataPrivilegeItem);
				}
				retMap.put("rows", result.getResult());
			} else {
				logger.error("数据权限设置闸口数据检索失败！");
			}

			return retMap;
	}
}
