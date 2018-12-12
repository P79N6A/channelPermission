/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.svc.api.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.haier.stock.service.StockAgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.DataDictionary;
import com.haier.purchase.data.model.GateItem;
import com.haier.purchase.data.model.GateOfLimitItem;
import com.haier.purchase.data.model.GateOfStockExceedCatchItem;
import com.haier.purchase.data.model.GateOfStockExceedItem;
import com.haier.purchase.data.model.PrivilegeItem;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.stock.model.StockAgeWapped;
import com.haier.svc.api.controller.util.CommUtil;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;
import com.haier.svc.api.controller.util.date.DateCal;
import com.haier.svc.service.DataDictionaryService;
import com.haier.svc.service.GateService;
import com.haier.svc.service.PurchaseCommonService;
import com.haier.svc.service.T2OrderService;

/**
 * 
 * @Filename: CommPurchase.java
 * @Version: 1.0
 * @Author: yanrp 燕如朋
 * @Email: yanrp110428@dhc.com.cn
 *
 */
@Service
public class CommPurchase {
	// @Autowired
	// private T2OrderDao T2OrderDao;
	//
	// @Autowired
	// private InvStockChannelDao invStock;
	//
	// @Autowired
	// private ItemAttributeDao item;
	//
	// @Autowired
	// private DataDictionaryDao dataDictionaryDao;
//	@Autowired
//	private T2OrderModel t2OrderModel;

	@Autowired
	T2OrderService t2OrderService;
	@Autowired
	StockAgeService stockAgeService;
	

	/**
	 * 取得渠道map
	 *
	 * @param invstockchannelmap
	 *            渠道map
	 * @param stockCommonService
	 *            stock服务
	 */
	public Map<String, String> getChannelMap(
			Map<String, String> invstockchannelmap) {
		invstockchannelmap = t2OrderService.getChannelMapByCode(invstockchannelmap);
		return invstockchannelmap;
	}

	/**
	 * 取得品牌map
	 * 
	 * @param brandMap
	 *            品牌map
	 */
	// public void getBrandMap(Map<String, String> brandMap, ItemService_hwl
	// itemService) {
	// //调用itemService，取得品牌数据，取得品牌map
	// ServiceResult<List<ItemAttribute>> queryResult =
	// itemService.getByValueSetId("Brand");
	// if (queryResult.getSuccess() && queryResult.getResult() != null) {
	// List<ItemAttribute> itemAttributes = queryResult.getResult();
	// for (ItemAttribute item : itemAttributes) {
	// brandMap.put(item.getValue(), item.getValueMeaning());
	// }
	// }
	// }

	/**
	 * 根据物料ID取得品牌code和型号
	 * 
	 * @param material_id
	 *            物料ID
	 * @param Map
	 *            <String, String> 品牌和型号所存放的Map
	 */
	public Map<String, String> getItemBaseMap(String material_id) {
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

	/**
	 * 根据物料号取得产品组code
	 * 
	 * @param materialCode
	 * @return
	 */

	public String getDepartmentByMaterialCode(String materialCode) {
		ServiceResult<List<ItemBase>> result = t2OrderService
				.findItemBaseByMaterialId(materialCode);
		String department = "";
		if (result.getSuccess() && result.getResult().size() >= 1)
			department = result.getResult().get(0).getDepartment();
		return department;
	}

	/**
	 * 通过value_set_id在数据字典中检索需要的list
	 * 
	 * @param dataDictionaryService
	 *            需要调用的Dubbo Service
	 * @param valueSetId
	 *            需要检索的数据分类
	 * @return HttpJsonResult类的result对象，其中的data属性为所需的list
	 */
	public List<DataDictionary> getByValueSetId(
			DataDictionaryService dataDictionaryService, String valueSetId) {

		// 设置参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("valueSetId", valueSetId);
		// 在service中获取数据字典中的list
		ServiceResult<List<DataDictionary>> query = dataDictionaryService
				.getByValueSetId(params);
		// 在service中获取数据字典中的list
		List<DataDictionary> queryResult = query.getResult();
		return queryResult;
	}
	
	public HttpJsonResult<List<DataDictionary>> getValueSetId(
			DataDictionaryService dataDictionaryService, String valueSetId) {

		HttpJsonResult<List<DataDictionary>> result = new HttpJsonResult<List<DataDictionary>>();

		// 设置参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("valueSetId", valueSetId);
		// 在service中获取数据字典中的list
		ServiceResult<List<DataDictionary>> queryResult = dataDictionaryService
				.getByValueSetId(params);

		if (queryResult.getSuccess() == true && queryResult.getResult() != null
				&& queryResult.getResult().size() > 0) {
			result.setMessage("list取得成功！");
			result.setData(queryResult.getResult());
			result.setTotalCount(queryResult.getResult().size());
		} else {
			result.setMessage("list取得失败！");
			result.setData(null);
			result.setTotalCount(0);
		}
		return result;
	}

	/**
	 * 根据数据分类取得对应的map
	 * 
	 * @param dataDictionaryService
	 *            需要调用的Dubbo Service
	 * @param valueSetId
	 *            需要检索的数据分类
	 * @return
	 */
	public Map<String, String> getValueMeaningMap(
			DataDictionaryService dataDictionaryService, String valueSetId) {
		// 在service中获取数据字典中的list
		List<DataDictionary> list = getByValueSetId(dataDictionaryService,
				valueSetId);
		if (list == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		// 将list转为map
		for (DataDictionary data : list) {
			if (data.getValue() != null) {
				map.put(data.getValue(), data.getValue_meaning());
			}
		}
		return map;
	}

	/**
	 * 订单号取得
	 * 
	 * @param dataDictionaryService
	 *            需要调用的Dubbo Service
	 * @param valueSetId
	 *            需要检索的数据分类
	 * @return
	 */
	public String getWPOrderId(
			PurchaseCommonService purchaseCommonService, String businesskbn) {
		String wpOrderId = "";
		// 正向流程,WP开头. 退货流程，WD开头
		if (businesskbn.startsWith("C")) {
			// WP + businesskbn(C01:CRM自动采购，C02:CRM手工采购......)
			wpOrderId = "WP" + businesskbn.substring(1);
		} else if (businesskbn.startsWith("T")) {
			// WD + businesskbn(T01:CRM正品退货......)
			wpOrderId = "WD" + businesskbn.substring(2);
		}
		// 年月加入
		Calendar cal = Calendar.getInstance();
		// 当前年
		String currentYear = String.valueOf(cal.get(Calendar.YEAR));
		// 当前月
		String currentMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		if (currentMonth.length() == 1) {
			currentMonth = "0" + currentMonth;
		}

		wpOrderId = wpOrderId + currentYear.substring(2) + currentMonth;

		// 订单流水号取得
		ServiceResult<Integer> nextVal = purchaseCommonService.getNextVal();
		if (nextVal.getSuccess() == true) {
			String newVal = String.valueOf(nextVal.getResult());
			while (newVal.length() < 7) {
				newVal = "0" + newVal;
			}

			// 订单流水号加入
			wpOrderId = wpOrderId + newVal;
		}

		return wpOrderId;
	}

	/**
	 * 时间闸口过闸
	 * 
	 * @param gateService
	 *            闸口service
	 * @param settingId
	 *            传入开闸闸口的setting_id
	 */
	public boolean isInTimeGate(GateService gateService, String settingId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 当前日期
		Date currentDate = new Date();
		String dateStr = sdf.format(currentDate).substring(0, 11);
		// 当前周数
		Integer week = CommUtil.getWeekNum(sdf.format(currentDate));
		// 开闸周数
		Integer startWeek = 0;
		// 开闸时分秒
		String startHms = "";
		// 开闸日期
		Calendar startDate = Calendar.getInstance();
		// 关闸周数
		Integer endWeek = 0;
		// 关闸周数
		String endHms = "";
		// 关闸日期
		Calendar endDate = Calendar.getInstance();
		Calendar currentTime = Calendar.getInstance();
		Map<String, Object> params = new HashMap<String, Object>();
		// 设置是否启用参数为1，只检索启用的时间闸口
		params.put("is_enabled", "1");
		// 检索时间闸口
		ServiceResult<List<GateItem>> gateOfTimeResult = gateService
				.findGateItem(params);
		if (gateOfTimeResult.getSuccess()
				&& gateOfTimeResult.getResult() != null
				&& gateOfTimeResult.getResult().size() > 0) {
			List<GateItem> gateOfTimeList = gateOfTimeResult.getResult();
			for (Iterator<GateItem> it = gateOfTimeList.iterator(); it
					.hasNext();) {
				GateItem gateItem = it.next();
				// 循环取得开闸时间闸口和关闸时间闸口
				if (gateItem.getSetting_id().contains(settingId)) {
					startWeek = Integer.valueOf(gateItem.getSetting_week());
					startHms = gateItem.getSetting_hour() + ":"
							+ gateItem.getSetting_minute() + ":"
							+ gateItem.getSetting_second();
					gateItem = it.next();
					endWeek = Integer.valueOf(gateItem.getSetting_week());
					endHms = gateItem.getSetting_hour() + ":"
							+ gateItem.getSetting_minute() + ":"
							+ gateItem.getSetting_second();
					break;
				}
			}
			if ("".equals(startHms)) {
				// 时分秒没有取出，db中没有此闸口数据，返回过闸
				return true;
			}
		}
		// 取得开闸时间、关闸时间和当前时间
		try {
			startDate.setTime(sdf.parse(dateStr + startHms));
			endDate.setTime(sdf.parse(dateStr + endHms));
			currentTime.setTime(currentDate);
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("字符串转calendar失败！");
			System.out.println("错误代码：" + e.getMessage());
		}

		if (startWeek == endWeek) {
			// 开始周等于结束周的情况
			if (currentTime.after(startDate) && currentTime.before(endDate)) {
				// 判断时分秒是否在闸口范围内
				return true;
			}
		} else if (week == startWeek) {
			// 当天落在开闸天
			if (currentTime.after(startDate)) {
				// 判断时分秒是否在闸口范围内
				return true;
			}
		} else if (week == endWeek) {
			// 当天落在关闸天
			if (currentTime.before(endDate)) {
				// 判断时分秒是否在闸口范围内
				return true;
			}
		} else if (startWeek < endWeek) {
			// 开始周小于结束周的情况
			if (week > startWeek && week < endWeek) {
				// 判断周是否在闸口范围内
				return true;
			}
		} else if (startWeek > endWeek) {
			// 开始周大于结束周的情况
			if ((week > startWeek && week < 8) || week < endWeek) {
				// 判断周是否在闸口范围内
				return true;
			}
		}
		return false;
	}

	// @Autowired
	// private T2OrderDao T2OrderDao;
	//
	// @Autowired
	// private InvStockChannelDao invStock;
	//
	// @Autowired
	// private ItemAttributeDao item;
	//
	// @Autowired
	// private DataDictionaryDao dataDictionaryDao;

	/**
	 * 取得产品组map
	 * 
	 * @param productgroupmap
	 *            产品组map
	 * @param itemService
	 *            item服务
	 */
	public Map<String, String> getProductMap(Map<String, String> productgroupmap) {
		ServiceResult<List<ItemAttribute>> resultProductGroup = t2OrderService
				.queryProductGroupByCbsCategory(null);
		List<ItemAttribute> itemAttributes = resultProductGroup.getResult();
		if (itemAttributes != null) {
			for (ItemAttribute item : itemAttributes) {
				productgroupmap.put(item.getValue(), item.getValueMeaning());// 将value作为key，valueMeaning作为value存入map中
			}
		}
		return productgroupmap;
	}

	/**
	 * 取得品牌map
	 * 
	 * @param brandMap
	 *            品牌map
	 */
	public void getBrandMap(Map<String, String> brandMap) {
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
	 * 取得权限Map,包含渠道权限，产品组权限，品类权限
	 * 
	 * @param request
	 * @param product_group
	 *            产品组
	 * @param channel
	 *            渠道
	 */
	public ServiceResult<PrivilegeItem> getAuthMap(PurchaseCommonService purchaseCommonService,
			HttpServletRequest request, String product_group, String channel,
			String cbs_catgory, Map<String, Object> authMap) {

		String[] channelList = null;
		String[] productGroupList = null;
		String[] cbsCatgoryList = null;

        HttpSession session = request.getSession();
        String userId = String.valueOf(session.getAttribute("userId"));

		// 权限定义产品组取得
//		ServiceResult<PrivilegeItem> privilegeData = purchaseCommonService
//				.getPrivilege(String.valueOf(WebUtil.currentUserId(request)));
        ServiceResult<PrivilegeItem> privilegeData = purchaseCommonService
                .getPrivilege(userId);
		// 渠道数组设定
		if (channel == null || channel.trim().equals("")
				|| channel.equalsIgnoreCase("ALL")) {
			//channelList = privilegeData.getResult().getEd_channel_id()
					//.split(",");
		} else {
			channelList = new String[] { channel };
		}
		// 产品组数组设定
		if (product_group == null || product_group == ""
				|| product_group == "ALL") {
			productGroupList = privilegeData.getResult().getProduct_group_id()
					.split(",");
		} else {
			productGroupList = new String[] { product_group };
		}
		// 品类数组设定
		if (cbs_catgory == null || cbs_catgory == "" || cbs_catgory == "ALL"
				|| "全部".equals(cbs_catgory)) {
			cbsCatgoryList = privilegeData.getResult().getCbs_catgory()
					.split(",");
		} else {
			cbsCatgoryList = new String[] { cbs_catgory };
		}

		// 渠道数组
		authMap.put("channel", channelList);
		// 产品组数组
		authMap.put("productGroup", productGroupList);
		// 品类数组
		authMap.put("cbsCatgory", cbsCatgoryList);
		// 闸口秒杀
		authMap.put("gateLimit", privilegeData.getResult().getGate_limit());
		
		return privilegeData;
	}

	/**
	 * 取得品类map
	 * 
	 * @param categoryMap
	 *            品类map
	 */
	public void getCategoryMap(Map<String, String> categoryMap) {
		// 调用itemService，取得产品组数据，取得品类map
		ServiceResult<List<ItemAttribute>> resultProductGroup = t2OrderService
				.queryProductGroupByCbsCategory(null);
		if (resultProductGroup.getSuccess()
				&& resultProductGroup.getResult() != null) {
			List<ItemAttribute> itemAttributes = resultProductGroup.getResult();
			for (ItemAttribute item : itemAttributes) {
				categoryMap.put(item.getValue(), item.getCbsCategory());// 将value作为key，cbs_category作为value存入map中
			}
		}
	}

	/**
	 * 取得额度闸口数据，不计算合计和总计
	 * 
	 * @param params
	 * @return GateOfLimitItem List<>
	 */
	public List<GateOfLimitItem> getGateOfLimitDataWithoutSum(
			GateService gateService, 
			Map<String, Object> params, Integer findKbn) {

		ServiceResult<List<GateOfLimitItem>> result = new ServiceResult<List<GateOfLimitItem>>();
		// 当前日期取得
		String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
		// 转化成周
		String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null,
				"0");
		// 取得渠道map
		Map<String, String> invStockChannelMap = new HashMap<String, String>();
		invStockChannelMap = t2OrderService.getChannelMapByCode(invStockChannelMap);
		List<GateOfLimitItem> gateOfLimitListTmp = null;
		List<GateOfLimitItem> gateOfLimitList = new ArrayList<GateOfLimitItem>();
		// T+3月份取得
		String endDate = CommUtil.weekToSetDateDay(currentWeek, 4);
		DateCal dateCal = new DateCal(endDate);
		Integer reportMonth = Integer.valueOf(dateCal.addWeek(3)
				.substring(5, 7));
		params.put("month", reportMonth);
		// 检索参数params中传入needResult 不检索总计
		params.put("needResult", "part");
		// 在DB中检索详细信息
		result = gateService.findGateOfLimit(params);

		// 重新组织DB中检索的数据
		if (result.getSuccess() && result.getResult() != null) {
			gateOfLimitListTmp = result.getResult();
			// 不需要冲洗组织的场合
			if (findKbn == 1) {
				return result.getResult();
			}
			for (GateOfLimitItem gateOfLimitItem : gateOfLimitListTmp) {
				// 总库存,在库,本周已用
				BigDecimal totalNum = new BigDecimal(0);
				BigDecimal onwayNum = new BigDecimal(0);
				BigDecimal usedNum = new BigDecimal(0);
				PagerInfo pagerInfo = new PagerInfo(1000000, 1);
				Map<String, Object> skuParams = new HashMap<String, Object>();
				skuParams.put("channel_code",
						gateOfLimitItem.getEd_channel_id());
				skuParams.put("product_type_name",
						gateOfLimitItem.getCategory_id());
				ServiceResult<List<StockAgeWapped>> stockResult = stockAgeService
						.getStockAgeList(pagerInfo, skuParams);

				if (stockResult.getSuccess() && stockResult.getResult() != null) {
					for (StockAgeWapped stockAgeWapped : stockResult
							.getResult()) {
						totalNum = totalNum.add(stockAgeWapped.getAgeDatas()
								.get(0).getValue());
					}
				}
				// 根据品类，渠道取得在途
				ServiceResult<BigDecimal> onwaynumresult = t2OrderService
						.getOnwayNumByCateChan(
								gateOfLimitItem.getCategory_id(),
								gateOfLimitItem.getEd_channel_id());
				if (onwaynumresult.getSuccess()
						&& onwaynumresult.getResult() != null)
					onwayNum = onwaynumresult.getResult().divide(
							new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
				;
				// 根据品类，渠道取得本周已用
				ServiceResult<BigDecimal> usednumresult = t2OrderService
						.getUsedNumByCateChan(currentWeek,
								gateOfLimitItem.getCategory_id(),
								gateOfLimitItem.getEd_channel_id());
				if (usednumresult.getSuccess()
						&& usednumresult.getResult() != null)
					usedNum = usednumresult.getResult().divide(
							new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
				;
				// 总库存
				gateOfLimitItem.setTotal_num(totalNum);
				// 在途
				gateOfLimitItem.setOn_way_num(onwayNum);
				// 本周已用
				gateOfLimitItem.setUsed_num(usedNum);
				BigDecimal limitNumTemp = new BigDecimal(0);
				if (gateOfLimitItem.getLimit_num() != null) {
					limitNumTemp = new BigDecimal(
							gateOfLimitItem.getLimit_num());
				}
				BigDecimal loanNumTemp = new BigDecimal(0);
				if (gateOfLimitItem.getLoan_num() != null) {
					loanNumTemp = new BigDecimal(gateOfLimitItem.getLoan_num());
				}
				// 可用额度(可用额度=额度-总库存-在途-本周已用+拆借）
				gateOfLimitItem.setAvailable_num(limitNumTemp
						.subtract(totalNum).subtract(onwayNum)
						.subtract(usedNum).add(loanNumTemp));
				// 为渠道名称赋值
				gateOfLimitItem.setEd_channel_name(invStockChannelMap
						.get(gateOfLimitItem.getEd_channel_id()));
				// 添加到目标List中
				gateOfLimitList.add(gateOfLimitItem);
			}
		}
		return gateOfLimitList;
	}

	/**
	 * 额度闸口过闸
	 * 
	 * @param stockCommonService
	 * @param gateService
	 * @param stockService
	 * @param t2OrderService
	 * @param logger
	 * @param price
	 *            提交过闸价格
	 * @param ed_channel_id
	 *            渠道
	 * @param category_id
	 *            品类
	 * @return
	 */
	public String isInLimitGate(GateService gateService,
			T2OrderService t2OrderService, BigDecimal amount,
			String ed_channel_id, String category_id) {
		// 根据渠道和产品组取得闸口数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ed_channel_id", ed_channel_id);
		params.put("category_id", category_id);
		List<GateOfLimitItem> gateResult = getGateOfLimitDataWithoutSum(
				gateService, params, 0);
		if (gateResult != null && gateResult.size() > 0) {
			// 根据闸口数据判断
			GateOfLimitItem gateOfLimitItem = gateResult.get(0);
			if (amount.divide(new BigDecimal(10000), 2,
					BigDecimal.ROUND_HALF_UP).compareTo(
					gateOfLimitItem.getAvailable_num()) == 1) {
				return "现在可用额度为" + gateOfLimitItem.getAvailable_num() + "万元";
			}
		} else {
			Map<String, String> invStockChannelMap = new HashMap<String, String>();
			invStockChannelMap = t2OrderService
					.getChannelMapByCode(invStockChannelMap);
			return "请维护" + category_id + "在"
					+ invStockChannelMap.get(ed_channel_id) + "的额度数据";
		}
		return null;
	}

    /**
     * 库存超期闸口过闸
     * @param gateService
     * @param ed_channel_id 订单渠道
     * @param materials_desc 订单型号
     * @param category_id 订单品类
     * @param brand_id 订单品牌
     * @param storage_id 订单库位
     * @param stockCommonService
     * @param materials_id 订单SKU 
     * @param itemService 
     * @return
     */
    public String isInStockExceedGate(GateService gateService, String ed_channel_id,
                                             String materials_desc, String category_id,
                                             String brand_id, String storage_id,
                                             String materials_id) {
        //空调的场合，套机的物料转为内外机的物料
        if ("空调".equals(category_id)) {
            ServiceResult<List<String>> serviceResult = t2OrderService
                .getSubSkuListByMainSku(materials_id);
            if (null != serviceResult && serviceResult.getSuccess()
                && null != serviceResult.getResult() && !serviceResult.getResult().isEmpty()) {
                List<String> subSkus = serviceResult.getResult();
                for (String subSku : subSkus) {
                    ServiceResult<ItemBase> itemBaseResult = t2OrderService.getItemBaseBySku(subSku);
                    if (null != itemBaseResult && itemBaseResult.getSuccess()
                        && null != itemBaseResult.getResult()) {
                        ItemBase subItemBase = itemBaseResult.getResult();
                        // 内机型号取得
                        if ("T05".equals(subItemBase.getProductType())) {
                            materials_desc = subItemBase.getMaterialDescription();
                        }
                    }
                }
            }
        }
        //根据渠道取得闸口数据
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ed_channel_id", ed_channel_id);
        params.put("is_enabled", 1);
        ServiceResult<List<GateOfStockExceedItem>> gateResult = gateService
            .findGateOfStockExceed(params);
        if (gateResult.getSuccess() && gateResult.getResult() != null
            && gateResult.getResult().size() > 0) {
            List<GateOfStockExceedItem> gateList = gateResult.getResult();
            for (GateOfStockExceedItem gateOfStockExceedItem : gateList) {
                //取得品牌
                String brand_type = gateOfStockExceedItem.getBrand_type();
                //取得品类
                String category_type = gateOfStockExceedItem.getCategory_type();
                //取得型号
                String model_type = gateOfStockExceedItem.getModel_type();
                //取得库位
                String storage_type = gateOfStockExceedItem.getStorage_type();
                //取得判断方式渠道
                String judge_ed_channel_id = gateOfStockExceedItem.getJudge_ed_channel_id();
                //取得超期库龄
                String exceedAge = gateOfStockExceedItem.getExceed_age();
                //说明
                params = new HashMap<String, Object>();
                params.put("age", exceedAge);
                params.put("ed_channel_id", judge_ed_channel_id);
                //根据判断方式渠道获取缓存表的超期数据
                ServiceResult<List<GateOfStockExceedCatchItem>> catchResult = gateService
                    .findGateOfStockExceedCatch(params);
                if (catchResult.getSuccess() && catchResult.getResult() != null
                    && catchResult.getResult().size() > 0) {
                    List<GateOfStockExceedCatchItem> catchList = catchResult.getResult();
                    for (Iterator<GateOfStockExceedCatchItem> it = catchList.iterator(); it
                        .hasNext();) {
                        int checkCount = 0;
                        GateOfStockExceedCatchItem gateOfStockExceedCatchItem = it.next();
                        if ("1".equals(brand_type)
                            && gateOfStockExceedCatchItem.getBrand_id().equals(brand_id)) {
                            checkCount++;
                        } else if ("0".equals(brand_type)) {
                            checkCount++;
                        }
                        if ("1".equals(category_type)
                            && gateOfStockExceedCatchItem.getCategory_id().equals(category_id)) {
                            checkCount++;
                        } else if ("0".equals(category_type)) {
                            checkCount++;
                        }
                        if ("1".equals(model_type)
                            && gateOfStockExceedCatchItem.getMaterials_desc()
                                .equals(materials_desc)) {
                            checkCount++;
                            //bug 133 修改 京东B2B业务的超期库存貌似没有校验，导致没有闸口 START
                            //} else if ("0".equals(materials_desc)) {
                        } else if ("0".equals(model_type)) {
                            //END
                            checkCount++;
                        }
                        if ("1".equals(storage_type)
                            && gateOfStockExceedCatchItem.getStorage_id().equals(storage_id)) {
                            checkCount++;
                        } else if ("0".equals(storage_type)) {
                            checkCount++;
                        }
                        if (checkCount == 4) {
                            //闸口数据中库位为全库位，闸口关闭
                            return "超期库存存在,不能上单";
                        }
                    }
                }
            }
        }
        return null;
    }


	/**
	 * 根据订单号判断订单属于CRM、CGO或DBM
	 * 
	 * @param orderId
	 *            订单号
	 * @return "CRM","CGO","DBM"
	 */
	public String getOrderTypeByOrderId(String orderId) {
		String orderType = "";
		String orderIdKbn = orderId.substring(2, 4);
		if ("01".equals(orderIdKbn) || "98".equals(orderIdKbn)
				|| "99".equals(orderIdKbn)) {
			// CRM订单
			orderType = "CRM";
		} else if ("03".equals(orderIdKbn)) {
			// CGO订单
			orderType = "CGO";
		} else if ("04".equals(orderIdKbn)) {
			// DBM订单
			orderType = "DBM";
		}
		return orderType;
	}
}