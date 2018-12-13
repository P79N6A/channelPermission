package com.haier.svc.services;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Holder;

import com.haier.purchase.data.model.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.service.PurchaseCgoService;
import com.haier.purchase.data.service.PurchaseDataDictionaryService;
import com.haier.purchase.data.service.PurchaseT2OrderService;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.QueryOrderParameter;
import com.haier.shop.service.ShopItemAttributeService;
import com.haier.shop.service.ShopItemBaseService;
import com.haier.shop.service.ShopOrdersService;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.InvRrsWarehouse;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.service.StockInvMachineSetService;
import com.haier.stock.service.StockInvRrsWarehouseService;
import com.haier.stock.service.StockInvStockChannelService;
import com.haier.stock.service.StockInvWarehouseService;
import com.haier.svc.bean.GVSOrderPriceRequire;
import com.haier.svc.bean.GVSOrderPriceResponse;
import com.haier.svc.bean.OMST2OrderCancelRequire;
import com.haier.svc.bean.OMST2OrderCancelResponse;
import com.haier.svc.purchase.cancelorderfromehaiertooms.CancelOrderFromEHaierToOMS;
import com.haier.svc.purchase.cancelorderfromehaiertooms.CancelOrderFromEHaierToOMS_Service;
import com.haier.svc.purchase.cancelorderfromehaiertooms.RequestData;
import com.haier.svc.purchase.cancelorderfromehaiertooms.ResponseData;
import com.haier.svc.purchase.crmmanual.DetailType;
import com.haier.svc.purchase.crmmanual.InsertWAOrderBillFromEhaierToCRM;
import com.haier.svc.purchase.crmmanual.InsertWAOrderBillFromEhaierToCRM_Service;
import com.haier.svc.purchase.crmmanual.MasterType;
import com.haier.svc.service.PurchaseBaseCommonService;
import com.haier.svc.service.T2OrderService;
import com.haier.svc.util.AsynchronousJobRunner;
import com.haier.svc.util.HttpUtils;
import com.haier.svc.util.T2ThreadJob;
import com.haier.svc.util.WSUtils;
import com.haier.svc.web.util.purchase.HttpJsonResult;

@Service
public class T2OrderServiceImpl implements T2OrderService {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(T2OrderServiceImpl.class);

	private static int FLOW_FLAG_NOCOMMITED = 0;

	@Value("${t2OrderResponse.location}")
	private String wsdlLocation;
	//获取价格地址
	@Value("${t2Order.kxPath.priceUrl}")
	private String priceUrl;
	//款先下发CRM订单接口
	@Value("${t2Order.kxPath.crm_int_oms_4}")
	private String crm_int_oms_4;

	private static Object lockAudit = new Object();

//	private String eaiUrl = null;

	protected static final String PRICE_FROM_GVS_URL = "/EAI/RoutingProxyService/EAI_REST_POST_ServiceRoot?INT_CODE=EAI_INT_1020";

	private static String DELETE_FLAG_UNDELETE = "0";

	private static final String T2_DEFAULT_YES = "1";

	@Autowired
	private PurchaseT2OrderService purchaseT2OrderService;

	@Autowired
	protected PurchaseCgoService purchaseCgoService;

	@Autowired
	private ShopItemAttributeService shopItemAttributeService;

	@Autowired
	private StockInvStockChannelService stockInvStockChannelService;

	@Autowired
	private PurchaseDataDictionaryService purchaseDataDictionaryService;

	@Autowired
	private ShopItemBaseService shopItemBaseService;

	@Autowired
	private StockInvMachineSetService stockInvMachineSetService;

	@Autowired
	private ShopOrdersService shopOrdersService;

	@Autowired
	private StockInvWarehouseService stockInvWarehouseService;

	@Autowired
	private StockInvRrsWarehouseService stockInvRrsWarehouseService;

	@Autowired
	private PurchaseBaseCommonService purchaseBaseCommonService;

	/**
	 * 获取T+2订单信息list
	 * 
	 * @param Map
	 *            <String, Object> params
	 * @return
	 */
	@Override
	public ServiceResult<List<T2OrderItem>> getT2OrderList(
			Map<String, Object> params) {
		ServiceResult<List<T2OrderItem>> result = new ServiceResult<List<T2OrderItem>>();
		try {
			result.setResult(purchaseT2OrderService.findT2Orders(params));
			PagerInfo pi = new PagerInfo();
			pi.setRowsCount(purchaseT2OrderService.findT2OrdersCNT(params));
			result.setPager(pi);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获取T+2订单信息list失败：", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Integer> getRowCnts() {
		// TODO Auto-generated method stub
		return null;
	}

	public HttpJsonResult<List<DataDictionary>> getByValueSetId(
			String valueSetId) {

		HttpJsonResult<List<DataDictionary>> result = new HttpJsonResult<List<DataDictionary>>();

		// 设置参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("valueSetId", valueSetId);
		// 在service中获取数据字典中的list
		ServiceResult<List<DataDictionary>> queryResult = new ServiceResult<>();
		queryResult.setResult(purchaseDataDictionaryService
				.getByValueSetId(params));
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
	public Map<String, String> getValueMeaningMap(String valueSetId) {
		// 在service中获取数据字典中的list
		List<DataDictionary> list = getByValueSetId(valueSetId).getData();
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
	 * 根据品类渠道取得在途
	 * 
	 * @param category_id
	 *            品类
	 * @param ed_channel_id
	 *            渠道
	 * @return
	 * @see com.haier.cbs.purchase.service.T2OrderService#getOnwayNumByCateChan(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public ServiceResult<BigDecimal> getOnwayNumByCateChan(String category_id,
			String ed_channel_id) {
		ServiceResult<BigDecimal> result = new ServiceResult<BigDecimal>();
		try {
			// crm在途
			List<T2OrderItem> crmOnway = purchaseT2OrderService
					.getOnwayNumByCateChan(category_id, ed_channel_id);
			// cgo和dbm在途
			List<CgoOrderItem> cgoOnway = purchaseCgoService
					.getOnwayNumByCateChan(category_id, ed_channel_id);

			BigDecimal crmOnwayAmount = new BigDecimal(0);
			if (crmOnway != null && crmOnway.size() > 0
					&& crmOnway.get(0).getAmount() != null) {
				crmOnwayAmount = crmOnway.get(0).getAmount();
			}

			BigDecimal cgoOnwayAmount = new BigDecimal(0);
			if (cgoOnway != null && cgoOnway.size() > 0
					&& cgoOnway.get(0).getAmount() != null) {
				cgoOnwayAmount = cgoOnway.get(0).getAmount();
			}
			crmOnwayAmount.add(cgoOnwayAmount);
			result.setResult(crmOnwayAmount);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResult(null);
			result.setMessage("根据品类:" + category_id + ",渠道:" + ed_channel_id
					+ "取得在途：" + e.getMessage());
			log.error("根据品类:" + category_id + ",渠道:" + ed_channel_id + "取得在途：",
					e);
		}
		return result;
	}

	/**
	 * 根据品类渠道取得本周已用
	 * 
	 * @param report_year_week
	 *            本周
	 * @param catagory
	 *            品类
	 * @param ed_channel_id
	 *            渠道
	 * @return
	 * @see com.haier.cbs.purchase.service.T2OrderService#getUsedNumByCateChan(java.lang.String,java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public ServiceResult<BigDecimal> getUsedNumByCateChan(
			String report_year_week, String category_id, String ed_channel_id) {
		ServiceResult<BigDecimal> result = new ServiceResult<BigDecimal>();
		try {
			// crm本周已用
			List<T2OrderItem> crmUsed = purchaseT2OrderService
					.getUsedNumByCateChan(report_year_week, category_id,
							ed_channel_id);
			// cgo和dbm本周已用
			List<CgoOrderItem> cgoUsed = purchaseCgoService
					.getUsedNumByCateChan(report_year_week, category_id,
							ed_channel_id);

			BigDecimal crmUsedAmount = new BigDecimal(0);
			if (crmUsed != null && crmUsed.size() > 0
					&& crmUsed.get(0).getAmount() != null) {
				crmUsedAmount = crmUsed.get(0).getAmount();
			}

			BigDecimal cgoUsedAmount = new BigDecimal(0);
			if (cgoUsed != null && cgoUsed.size() > 0
					&& cgoUsed.get(0).getAmount() != null) {
				cgoUsedAmount = cgoUsed.get(0).getAmount();
			}
			crmUsedAmount.add(cgoUsedAmount);
			result.setResult(crmUsedAmount);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResult(null);
			result.setMessage("根据提报周:" + report_year_week + ",品类:"
					+ category_id + ",渠道:" + ed_channel_id + "取得本周已用："
					+ e.getMessage());
			log.error("根据提报周:" + report_year_week + ",品类:" + category_id
					+ ",渠道:" + ed_channel_id + "取得本周已用：", e);
		}
		return result;
	}

	@Override
	public ServiceResult<Map<String, Integer>> insertT2Order(
			List<T2OrderItem> t2OrderItems) {
		ServiceResult<Map<String, Integer>> result = new ServiceResult<Map<String, Integer>>();
		try {
			int success = 0;
			int failure = 0;
			if (t2OrderItems == null)
				throw new BusinessException(
						"[T2_Order_model][T2OrderItem]:insertT2Order对象不能为空");

			// DefaultTransactionDefinition def = new
			// DefaultTransactionDefinition();
			// def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			// TransactionStatus status =
			// transactionManager.getTransaction(def);
			for (T2OrderItem t2OrderItem : t2OrderItems) {
				try {
					if (t2OrderItem.getFlow_flag() == null || t2OrderItem.getFlow_flag() == 0) {
                        // 提报状态（0：未提交）
                        t2OrderItem.setFlow_flag(FLOW_FLAG_NOCOMMITED);
                    }
					t2OrderItem.setDelete_flag(DELETE_FLAG_UNDELETE);
					purchaseT2OrderService.insert(t2OrderItem);
					success++;
				} catch (Exception ex) {
					// System.out.println(ex.getMessage());
					// 回滚事务
					// transactionManager.rollback(status);
					// 记录日志
					log.error(
							"[T2_Order_model][insertT2Order]:创建T+2订单表单发生未知异常:",
							ex);
					failure++;
				}
			}
			// 提交事务
			// transactionManager.commit(status);
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("success", success);
			map.put("failure", failure);
			result.setResult(map);
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("创建T+2订单表单失败，" + e.getMessage());
			log.error("[T2_Order_service][insertT2Order]:T+2订单表单创建失败", e);
		}
		return result;
	}

	/**
	 * 取得产品组map
	 * 
	 * @param productgroupmap
	 *            产品组map
	 * @param itemService
	 *            item服务
	 */
	@Override
	public Map<String, String> getProductMap(Map<String, String> productgroupmap) {
		List<ItemAttribute> itemAttributes = shopItemAttributeService
				.queryProductGroupByCbsCategory("");
		if (itemAttributes != null) {
			for (ItemAttribute item : itemAttributes) {
				productgroupmap.put(item.getValue(), item.getValueMeaning());// 将value作为key，valueMeaning作为value存入map中
			}
		}
		return productgroupmap;
	}

	/**
	 * 取得渠道map
	 * 
	 * @param invstockchannelmap
	 *            渠道map
	 * @param stockCommonService
	 *            stock服务
	 */
	@Override
	public Map<String, String> getChannelMapByCode(
			Map<String, String> invstockchannelmap) {
		// 调用stockCommonService，取得渠道数据
		List<InvStockChannel> InvStockChannel = stockInvStockChannelService
				.getAll();
		if (InvStockChannel != null) {
			for (InvStockChannel invstockchannel : InvStockChannel) {
				invstockchannelmap.put(invstockchannel.getChannelCode(),
						invstockchannel.getName());// 将channelcode作为key，name作为value存入map中
			}
		}
		return invstockchannelmap;
	}

	public Map<String, String> getChannelMapByName(
			Map<String, String> invstockchannelmap) {
		// 调用stockCommonService，取得渠道数据
		List<InvStockChannel> InvStockChannel = stockInvStockChannelService
				.getAll();
		if (InvStockChannel != null) {
			for (InvStockChannel invstockchannel : InvStockChannel) {
				invstockchannelmap.put(invstockchannel.getName(),
						invstockchannel.getChannelCode());// 将channelcode作为key，name作为value存入map中
			}
		}
		return invstockchannelmap;
	}

	/**
	 * 根据物料ID取得品牌code和型号 & 根据物料号取得产品组code 李超
	 * 
	 * @param material_id
	 *            物料ID 物料号
	 * @return
	 */
	@Override
	public ServiceResult<List<ItemBase>> findItemBaseByMaterialId(
			String material_id) {
		ServiceResult<List<ItemBase>> result = new ServiceResult<List<ItemBase>>();
		try {
			result.setResult(shopItemBaseService
					.findItemBaseByMaterialId(material_id));
		} catch (Exception e) {
			log.error("查询品牌和型号，发生未知异常：", e);
			result.setMessage("查询品牌和型号，发生未知异常：" + e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	@Override
	public ServiceResult<List<String>> getSubSkuListByMainSku(String sku) {
		// TODO Auto-generated method stub
		ServiceResult<List<String>> result = new ServiceResult<List<String>>();
		List<InvMachineSet> machineSets = stockInvMachineSetService
				.getByMainSku(sku);
		List<String> skuList = new ArrayList<String>();
		if (null != machineSets && !machineSets.isEmpty()) {
			for (InvMachineSet invMachineSet : machineSets) {
				skuList.add(invMachineSet.getSubSku());
			}
		}
		try {
			result.setResult(skuList);
		} catch (BusinessException be) {
			log.warn(be.getMessage());
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			log.error("获取所有订单渠道数据时出现未知异常：", e);
			result.setSuccess(false);
			result.setMessage("出现未知异常:" + e.getMessage());
		}
		return result;

	}

	@Override
	public ServiceResult<List<ItemAttribute>> queryProductGroupByCbsCategory(
			String cbsCategory) {
		ServiceResult<List<ItemAttribute>> result = new ServiceResult<List<ItemAttribute>>();
		result.setResult(shopItemAttributeService
				.queryProductGroupByCbsCategory(cbsCategory));
		return result;
	}

	@Override
	public ServiceResult<List<ItemAttribute>> getByValueSetItemId(String brand) {
		ServiceResult<List<ItemAttribute>> result = new ServiceResult<List<ItemAttribute>>();
		result.setResult(shopItemAttributeService.getByValueSetId(brand));
		return result;
	}

	/**
	 * 根据物料SKU取得物料基本信息 李超
	 * 
	 * @param subSku
	 *            物料
	 * @return
	 */
	@Override
	public ServiceResult<ItemBase> getItemBaseBySku(String subSku) {
		ServiceResult<ItemBase> result = new ServiceResult<ItemBase>();
		try {

			ItemBase param = new ItemBase();
			param.setMaterialCode(subSku);
			param.setDeleteFlag(0);
			List<ItemBase> list = shopItemBaseService.getBySku(param);
			if (list == null || list.size() == 0) {
				return null;
			}
			ItemBase item = list.get(0);

			result.setResult(item);
		} catch (Exception e) {
			log.error("根据物料SKU取得物料基本信息时发生未知异常：", e);
			result.setMessage("根据物料SKU取得物料基本信息时发生未知异常");
			result.setSuccess(false);
		}
		return result;
	}

	@Override
	// @Transactional
	public ServiceResult<Boolean> updateCheckError(T2OrderItem t2Order) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			// 错误情报更新
			try {
				// 情报更新
				purchaseT2OrderService.updateByQty(t2Order);
				result.setResult(true);
			} catch (Exception ex) {
				// 记录日志
				log.error("[T2OrderModel][updateByQty]:不能提报订单的错误信息更新失败:", ex);
				result.setResult(false);
			}

		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("闸口CheckError情报更新失败：", e);
		}
		return result;
	}

	/**
	 * 价格情报更新
	 * 
	 * @param t2Order
	 * @see com.haier.cbs.purchase.service.T2OrderService#updateCheckError(T2OrderItem)
	 */
	@Override
	public ServiceResult<Boolean> updatePrice(T2OrderItem t2Order) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			// 价格更新
			try {
				// 情报更新
				purchaseT2OrderService.updatePrice(t2Order);
				result.setResult(true);
				// 提交事务
			} catch (Exception ex) {
				// 记录日志
				log.error("[T2OrderModel][updatePrice]:价格情报更新失败:", ex);
				result.setResult(false);
			}

		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("价格更新失败：", e);
		}
		return result;
	}

	/**
	 * 提交订单
	 * 
	 * @param commitT2OrderList
	 * @return
	 * @see 
	 *      com.haier.cbs.purchase.service.T2OrderService#commitT2OrderList(List<
	 *      T2OrderItem>)
	 */
	@Override
	public ServiceResult<Boolean> commitT2OrderList(Map<String, Object> params) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			if ("0".equals(params.get("gateLimit"))) {
				// 备料准确率闸口判定
				Boolean stockPrecisionCheck = checkStockPrecision(params);
				if (!stockPrecisionCheck) {
					result.setResult(true);
					return result;
				}
			}
			// 提交订单
			try {
				// 情报更新
				purchaseT2OrderService.updateOrderStatus(params);
			} catch (Exception ex) {
				// 记录日志
				log.error("[T2OrderModel][commitT2OrderList]:订单提交失败:", ex);
			}
			result.setResult(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("提交订单失败：", e);
		}
		return result;
	}

	/**
	 * 备料准确率闸口判定
	 * 
	 * @param params
	 * @return
	 */
	private Boolean checkStockPrecision(Map<String, Object> params) {
		// 渠道和品类对应的已提交OMS数量取得
		List<T2OrderItem> t2OrderListSum = purchaseT2OrderService
				.findT2OrdersSum(params);
		if (t2OrderListSum == null || t2OrderListSum.size() == 0) {
			return true;
		}
		// T+2已经提交数量
		BigDecimal t2Qty = t2OrderListSum.get(0).getT2_delivery_prediction();
		// T+3已经提交数量
		Integer t3Qty = t2OrderListSum.get(0).getCount();
		// 备料准确率
		BigDecimal acc_rate = t2OrderListSum.get(0).getAmount();
		T2OrderItem commitT2OrderItem = new T2OrderItem();
		commitT2OrderItem.setOrder_id(params.get("order_id").toString());
		if (t3Qty == null) {
			commitT2OrderItem.setError_msg("上周没有进行预测备料，本周不能上单。");
			try {
				// 情报更新
				purchaseT2OrderService.updateByQty(commitT2OrderItem);
				return true;
			} catch (Exception ex) {
				// 记录日志
				log.error("[T2OrderModel][updateByQty]:不能提报订单的错误信息更新失败:", ex);
				return false;
			}

		}
		if (t2Qty == null) {
			t2Qty = new BigDecimal(0);
		}
		// T+2订单最大数量取得
		Double maxQty = Math.floor((new BigDecimal(t3Qty)).multiply(
				(new BigDecimal(2)).subtract(acc_rate)).doubleValue());

		if (maxQty.compareTo(t2Qty
				.add(new BigDecimal(params.get("t2_delivery_prediction")
						.toString())).doubleValue()) >= 0) {
			return true;
		} else {
			commitT2OrderItem.setError_msg("根据上周的预测备料数量,本周现在能提交最大数量为"
					+ (maxQty.intValue() - t2Qty.intValue()));
			try {
				// 情报更新
				purchaseT2OrderService.updateByQty(commitT2OrderItem);
				return true;
			} catch (Exception ex) {
				// 记录日志
				log.error("[T2OrderModel][updateByQty]:不能提报订单的错误信息更新失败:", ex);
				return false;
			}
		}
	}

	@Override
	public ServiceResult<List<QueryOrderParameter>> getFindQueryOrderList(
			QueryOrderParameter queryOrder) {
		ServiceResult<List<QueryOrderParameter>> result = new ServiceResult<List<QueryOrderParameter>>();
		try {
			if (queryOrder == null) {
				result.setSuccess(false);
				result.setMessage("【getFindQueryOrderList】获取订单信息服务参数并为null");
				log.error("【getFindQueryOrderList】获取订单信息服务参数并为null");
				return result;
			}
			List<QueryOrderParameter> findQueryOrderList = shopOrdersService
					.getFindQueryOrderList(queryOrder);
			Integer count = shopOrdersService.getRowCnts();
			result.setResult(findQueryOrderList);
			PagerInfo pi = new PagerInfo();
			pi.setRowsCount(count != null ? count : 0);
			result.setPager(pi);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获取订单信息list失败：", e);
		}
		return result;
	}

	/**
	 * 产品部审核订单
	 * 
	 * @param params
	 */
	@Override
	// @Transactional
	public ServiceResult<Boolean> reviewT2OrderDepart(Map<String, Object> params) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			try {
				// 情报更新
				purchaseT2OrderService.reviewT2OrderDepart(params);
				result.setResult(true);
			} catch (Exception ex) {
				// 记录日志
				log.error("[T2OrderModel][reviewT2OrderDepart]:产品部审核订单审核失败:",
						ex);
				result.setResult(false);
			}

		} catch (Exception e) {
			result.setSuccess(false);
			log.error("t2产品部审核订单失败：", e);
		}
		return result;
	}

	@Override
	// @Transactional
	public void deleteT2OrderList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			// 删除订单
			purchaseT2OrderService.deleteOrderStatus(params);
			result.setResult(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("删除订单失败：", e);
		}
	}

	/**
	 * 订单数量修改
	 * 
	 * @param params
	 * @see com.haier.cbs.purchase.service.T2OrderService#updateCount(java.util.Map)
	 */
	@Override
	public ServiceResult<Boolean> updateCount(Map<String, Object> params) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		// 订单数量修改
		int updCount = 0;
		try {
			// 订单数量修改
			updCount = purchaseT2OrderService.updateCount(params);
		} catch (Exception ex) {
			// 记录日志
			log.error("[T2OrderModel][updateCount]:订单数量修改失败:", ex);
		}
		if (updCount == 1) {
			// 修改成功
			result.setResult(true);
		} else {
			// 状态已被修改，修改失败
			result.setResult(false);
		}
		return result;
	}

	/**
	 * 撤销订单
	 * 
	 * @param params
	 * @return
	 */
	@Override
	// @Transactional
	public ServiceResult<Boolean> revokeT2OrderList(Map<String, Object> params) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			// OMS订单号取得
			List<T2OrderItem> t2OrderItemList = purchaseT2OrderService
					.findCancelT2OrderForOms(params);
			for (T2OrderItem t2OrderItem : t2OrderItemList) {
				if (10 == t2OrderItem.getFlow_flag()
						|| 5 == t2OrderItem.getFlow_flag()) {
					// 内部审核通过的订单直接撤销
					try {
						// 情报更新
						purchaseT2OrderService.updateRevokeFlag(params);
						result.setResult(true);
					} catch (Exception ex) {
						// 记录日志
						log.error("[T2OrderDao][revokeT2OrderList]:订单撤销失败:", ex);
						result.setResult(false);
					}

				} else {
					// OMS待审的订单需要OMS确认
					OMST2OrderCancelRequire order = new OMST2OrderCancelRequire();
					// OMS订单号
					order.setOms_id(t2OrderItem.getOms_order_id());
					// OMS撤销订单

					OMST2OrderCancelResponse oMST2OrderCancelResponse = new OMST2OrderCancelResponse();
					try {
						// String path = "file:"
						// + this.getClass()
						// .getResource(
						// wsdlLocation
						// + "/CancelOrderFromEHaierToOMS.wsdl")
						// .getPath();
						// URL url = new URL(path);
						URL url = this.getClass().getResource(
								wsdlLocation
										+ "/CancelOrderFromEHaierToOMS.wsdl");
						CancelOrderFromEHaierToOMS_Service service = new CancelOrderFromEHaierToOMS_Service(
								url);

						CancelOrderFromEHaierToOMS soap = service
								.getCancelOrderFromEHaierToOMSSOAP();
						RequestData requestData = new RequestData();
						requestData.setTitleSoCodeId(order.getOms_id());
						requestData.setSysName("EHAIER");
						// System.out.println("T+2订单撤销" + " id : " +
						// order.getOms_id());
						log.error("T+2订单撤销 " + " id : " + order.getOms_id());
						ResponseData output = soap
								.cancelOrderFromEHaierToOMS(requestData);
						// System.out.println("T+2订单撤销" + " id : " +
						// order.getOms_id() + " ResponseData:"
						// + JsonUtil.toJson(output));
						log.error("T+2订单撤销 " + " id : " + order.getOms_id()
								+ " ResponseData:" + JsonUtil.toJson(output));
						oMST2OrderCancelResponse.setReason(output.getMessage());
						oMST2OrderCancelResponse.setStatus(output.getStatus());
					} catch (Exception e) {
						log.error("T+2订单撤销时，发生未知异常", e);
					}
					if ("S".equals(oMST2OrderCancelResponse.getStatus())) {
						// 撤销订单
						try {
							// 情报更新
							purchaseT2OrderService.updateRevokeFlag(params);

						} catch (Exception ex) {
							// 记录日志
							log.error("[t2OrderDao][updateRevokeFlag]:订单撤销失败:",
									ex);
							result.setResult(false);
						}

					} else {
						// 撤销失败信息更新
						t2OrderItem.setError_msg(oMST2OrderCancelResponse
								.getReason());
						try {
							// 情报更新
							purchaseT2OrderService.updateErrMsg(t2OrderItem);

						} catch (Exception ex) {
							// 记录日志
							log.error(
									"[T2OrderModel][revokeT2OrderList]:撤销失败MSG更新失败:",
									ex);
							result.setResult(false);
						}
					}
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("撤销失败：", e);
		}
		return result;
	}

	/**
	 * 审核订单t+2
	 * 
	 * @param params
	 * @return
	 * @see com.haier.cbs.purchase.service.T2OrderService#reviewT2OrderList(java.util.Map)
	 */
	@Override
	// @Transactional
	public ServiceResult<Boolean> reviewT2OrderList(Map<String, Object> params) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Boolean reviewResult = true;
			// 订单情报取得
			List<T2OrderItem> reviewedOrderList = null;
			List<T2OrderItem> orders = new ArrayList<T2OrderItem>();
			synchronized (lockAudit) {

				// 审核不过的场合，状态更新
				if ("-10".equals(params.get("flow_flag").toString())) {
					// 审核订单
					try {
						// 情报更新
						purchaseT2OrderService.reviewT2Order(params);
						reviewResult = true;
					} catch (Exception ex) {
						// 记录日志
						log.error("[T2OrderDao][reviewT2Order]:订单审核失败:", ex);
						reviewResult = false;
					}
					if (!reviewResult) {
						result.setResult(false);
					}
					// 审核通过场合
				} else {
					// 订单情报取得
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					params.put("flow_flag", 5);
					params.put("sync_status", "-20,-10,0");
					params.put("report_year_week", WSUtils
							.getWeekOfYear_Sunday(sdf.format(new Date()),
									"yyyy-MM-dd", "0"));
//					log.info("正在查询要同步的T+2订单");
					reviewedOrderList = purchaseT2OrderService
							.findT2OrderForOms(params);
//					log.info("查询待同步的T+2订单完毕,数量:" + reviewedOrderList.size());
//					log.info("正在修改T+2订单同步状态");
					for (T2OrderItem reviewedOrder : reviewedOrderList) {
						String msg = checkStorage(reviewedOrder);// check库位
																	// 返回空为check成功
						if ("".equals(msg)) {
							if (1 == reviewedOrder.getOrder_category()) {
								T2OrderItem reviewedItem = new T2OrderItem();
								reviewedItem.setStorage_id(reviewedOrder
										.getStorage_id());
								reviewedItem.setProduct_group_id(reviewedOrder
										.getProduct_group_id());
								reviewedItem.setEd_channel_id(reviewedOrder
										.getEd_channel_id());
								reviewedItem.setCustom_id(reviewedOrder
										.getCustom_id());
								reviewedItem
										.setArrival_storage_id(reviewedOrder
												.getArrival_storage_id());
								reviewedItem
										.setT2_delivery_prediction(reviewedOrder
												.getT2_delivery_prediction());
								reviewedItem.setOrder_id(reviewedOrder
										.getOrder_id().replace("WP10", "KWN"));
								reviewedItem.setMaterials_id(reviewedOrder
										.getMaterials_id());
								reviewedItem.setCategory_id(reviewedOrder
										.getCategory_id());
								reviewedItem.setPayment_id(reviewedOrder
										.getPayment_id());
								reviewedItem.setOrder_category(reviewedOrder
										.getOrder_category());
								reviewedItem.setCancelOrderId(reviewedOrder
										.getCancelOrderId());
								reviewedItem.setCancelFlag(reviewedOrder
										.getCancelFlag());
								reviewedItem.setAudit_user(String.valueOf(params
										.get("audit_user")));
								reviewedItem.setAudit_remark(String.valueOf(params
										.get("audit_remark")));

								orders.add(reviewedItem);
							}
							reviewedOrder.setAudit_user(String.valueOf(params
									.get("audit_user")));
							reviewedOrder.setAudit_remark(String.valueOf(params
									.get("audit_remark")));
							reviewedOrder.setFlow_flag(5);// 审核通过
							reviewedOrder.setSync_status(10);// 传输中
							reviewedOrder.setError_msg("正在向OMS发送数据");
							if (1 != reviewedOrder.getOrder_category()) {
								orders.add(reviewedOrder);
							}
						} else {
							reviewedOrder.setAudit_user(String.valueOf(params
									.get("audit_user")));
							reviewedOrder.setAudit_remark(String.valueOf(params
									.get("audit_remark")));
							reviewedOrder.setFlow_flag(reviewedOrder
									.getFlow_flag());// 不更新
							reviewedOrder.setSync_status(reviewedOrder
									.getSync_status());// 不更新
							reviewedOrder.setError_msg(msg);
						}
						try {
							// 情报更新
							purchaseT2OrderService
									.updateSyncStatusByOms(reviewedOrder);
						} catch (Exception ex) {
							// 回滚事务
							// 记录日志
							log.error(
									"[T2OrderDao][updateSyncStatusByOms]:更新提报OMS的状态更新失败:",
									ex);
						}
					}
//					log.info("T+2订单同步状态修改完毕,数量:" + orders.size());
				}
			}
			return syncT2OrderToOms(orders);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("审核订单失败：", e);
		}
		return result;
	}

	@Override
	// @Transactional
	public ServiceResult<Boolean> syncT2OrderToOms(final List<T2OrderItem> l) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			AsynchronousJobRunner jr = new AsynchronousJobRunner();
			AsynchronousJobRunner.Configuration config = new AsynchronousJobRunner.Configuration();
			if (l.size() < 10) {
				config.jobCount = 1;
			} else {
				config.jobCount = 10;
			}
			config.isBlock = false;
			config.isMonitor = true;
			config.monitorCallback = new AsynchronousJobRunner.Configuration.IMonitorCallback() {
				private List<T2OrderItem> successedItem = new ArrayList<T2OrderItem>();

				@Override
				public void finishJob(Object returnValue) {
					if (returnValue != null) {
						Map map = (Map) returnValue;
						boolean isSuccess = (Boolean) map.get("returnValue");
						List<T2OrderItem> successedItem_item = (List<T2OrderItem>) map
								.get("successedItem");
						synchronized (successedItem) {
							successedItem.addAll(successedItem_item);
						}
					}
				}

				@Override
				public void finishedAllJobs() {
					for (int i = 0; i < l.size(); i++) {
						T2OrderItem item = l.get(i);
						for (T2OrderItem sitem : successedItem) {
							if (sitem.getOrder_id().equals(item.getOrder_id())) {
								l.remove(i);
								i--;
								break;
							}
						}
					}
					if (l.size() > 0) {
						log.warn("[T+2订单异步传输]以下单据未进行传输:" + JsonUtil.toJson(l));
						for (T2OrderItem reviewedOrder : l) {
							try {
								reviewedOrder.setFlow_flag(5);
								reviewedOrder.setSync_status(-10);// 待重传
								reviewedOrder.setError_msg("未传输");
								try {
									// 情报更新
									purchaseT2OrderService
											.updateByOms(reviewedOrder);
									if (reviewedOrder.getFlow_flag() != null) {
										purchaseT2OrderService
												.updateOmsFlowFlagOnly(reviewedOrder);
									}
								} catch (Exception ex) {
									// 记录日志
									log.error(
											"[T2OrderModel][updateByOms]:提报OMS的状态更新失败:",
											ex);
								}
								// t2OrderModel.updateByOms(reviewedOrder);
							} catch (Exception ex) {
								log.error("", ex);
								log.error("更新T+2订单状态失败,请务必手动更新"
										+ JsonUtil.toJson(reviewedOrder));
							}
						}
					}
				}
			};
			// TODO 定时任务待处理
			jr.startJob(config, l, T2ThreadJob.class);
			result.setSuccess(true);
			result.setResult(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResult(false);
			result.setMessage("" + e.getMessage());
		}
		return result;
	}

	/**
	 * 库位的合理性判断
	 * 
	 * @param reviewedOrder
	 * @return 返回空则check成功
	 */
	private String checkStorage(T2OrderItem reviewedOrder) {
		// 通过电商库位码获取仓库
		try {
			InvWarehouse invWarehouse = stockInvWarehouseService
					.getAllWhByEstorgeId(reviewedOrder.getStorage_id());
			if (invWarehouse != null) {
				// 送达方编码
				if ("".equals(invWarehouse.getTransmit_id())) {
					return "WA库对应的仓库送达方为空，不传OMS，状态不变";
				}
				// 电商付款方编码
				if ("".equals(invWarehouse.getPayment_id())) {
					return "WA库对应的仓库电商付款方为空，不传OMS，状态不变";
				}
			} else {
				return "WA库对应的仓库为空，不传OMS，状态不变";
			}

			// 通过电商库位码获取日日顺仓库List
			Map<String, Object> invRrsWarehouseParams = new HashMap<String, Object>();
			invRrsWarehouseParams.put("estorge_id",
					reviewedOrder.getStorage_id());
			invRrsWarehouseParams.put("t2_default", T2_DEFAULT_YES);
			List<InvRrsWarehouse> invRrsWarehouseList = stockInvRrsWarehouseService
					.getAllRrsWhByEstorgeOriginal(invRrsWarehouseParams);
			if (invRrsWarehouseList == null || invRrsWarehouseList.size() == 0) {
				return "WA库对应的日日顺库为空，不传OMS，状态不变";
			}

			// 库位的合理性判断**************END**********************************************
			return "";
		} catch (Exception ex) {
			log.error("", ex);
			return "";
		}
	}

	/**
	 * 款先直发订单，机壳不结算订单推送crm
	 * 
	 * @param params
	 * @return
	 */
	@Override
	// @Transactional
	public ServiceResult<List<String>> insertWAOrderBillToCRM(
			Map<String, Object> params) {
		ServiceResult<List<String>> result = new ServiceResult<List<String>>();
		Holder<String> billCode = new Holder<String>();
		Holder<String> flag = new Holder<String>();
		Holder<String> message = new Holder<String>();
		Holder<String> vbeln = new Holder<String>();
		Holder<String> vbelnDN = new Holder<String>();
		// String path = "file:"
		// + this.getClass()
		// .getResource(
		// wsdlLocation
		// + "/InsertWAOrderBillFromEhaierToCRM.wsdl")
		// .getPath();
		try {
			// URL url = new URL(path);
			URL url = this.getClass().getResource(
					wsdlLocation + "/InsertWAOrderBillFromEhaierToCRM.wsdl");
			InsertWAOrderBillFromEhaierToCRM_Service service = new InsertWAOrderBillFromEhaierToCRM_Service(
					url);
			InsertWAOrderBillFromEhaierToCRM soap = service
					.getInsertWAOrderBillFromEhaierToCRMSOAP();
			// 根据订单号取得所有订单
			List<T2OrderItem> orderItemList = (List<T2OrderItem>) params
					.get("order_list");
			// 手工采购成功订单号
			List<String> scccessList = new ArrayList<String>();
			// 通过电商库位码获取仓库
			InvWarehouse invWarehouse = null;
			// ServiceResult<InvWarehouse> invWarehouseResult = new
			// ServiceResult<InvWarehouse>();
			// 根据地区编码和产品组取得预算体信息
			Map<String, Object> invBudgetOrgMap = new HashMap<String, Object>();
			ServiceResult<InvBudgetOrg> invBudgetOrg = new ServiceResult<InvBudgetOrg>();
			// 日日顺库位
			List<InvRrsWarehouse> rrsCodeList = new ArrayList<InvRrsWarehouse>();
			String markQ = "";
			String oldId = "";
			String newId = "";
			String billType = "";
			String pickType = "";
			String budgetSort = "";
			String invState = "";
			String mark = (String) params.get("mark");
			// 款先直发
			if ("10".equals(mark)) {
				markQ = "Q";
				oldId = "WP10";
				newId = "WN";
				billType = "ZGOZ";
				pickType = "6";
				budgetSort = "30";
				invState = "10";
			}
			// 机壳不结算
			if ("11".equals(mark)) {
				billType = "ZLPF";
				pickType = "2";
				budgetSort = "25";
				invState = "90";
			}
			GVSOrderPriceRequire order = new GVSOrderPriceRequire();

			// 预计到货日期
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.SUNDAY);
			if (cal.get(Calendar.DAY_OF_WEEK) > 3) {
				cal.set(Calendar.DATE,
						cal.get(Calendar.DATE) + 17 + 10
								- cal.get(Calendar.DAY_OF_WEEK));
			} else {
				cal.set(Calendar.DATE,
						cal.get(Calendar.DATE) + 17 + 3
								- cal.get(Calendar.DAY_OF_WEEK));
			}
			String predict_arrive_date = new SimpleDateFormat("yyyy-MM-dd")
					.format(cal.getTime());

			for (T2OrderItem t2OrderItem : orderItemList) {
				List<DetailType> l_detail = new ArrayList<DetailType>();
				String storage_id = t2OrderItem.getStorage_id();
				String product_group_id = t2OrderItem.getProduct_group_id();
				// 通过电商库位码获取仓库
				invWarehouse = stockInvWarehouseService
						.getAllWhByEstorgeId(storage_id);
				MasterType masterType = new MasterType();
				masterType.setBillCode(markQ
						+ t2OrderItem.getOrder_id().replace(oldId, newId));// 销售单号

				if (invWarehouse != null) {
					masterType.setCorpCode(invWarehouse.getSale_org_id());// 分公司编码
					masterType.setRegionID(invWarehouse.getArea_id());// CRM地区编码(工贸编码)
					masterType.setSaleOrgCode(invWarehouse.getSale_org_id());// 销售组织编码

					// 根据地区编码和产品组取得预算体信息
					invBudgetOrgMap.put("product_group_id", product_group_id);
					invBudgetOrgMap.put("area_id", invWarehouse.getArea_id());
					invBudgetOrg = purchaseBaseCommonService
							.getAllBudgetOrg(invBudgetOrgMap);

					// 根据销售组织，付款方，物料编码，地区编码取得价格信息
					order.setCorpCode(invWarehouse.getSale_org_id());// 销售组织
					order.setCustCode(invWarehouse.getPayment_id());// 付款方
					order.setRegionCode(invWarehouse.getArea_id());// 地区编码
					order.setInvCode(t2OrderItem.getMaterials_id());// 物料编码
				}
				// 根据销售组织，付款方，物料编码，地区编码取得价格信息
				GVSOrderPriceResponse priceResponse = quirePrice(order);

				masterType.setBillType(billType);// 订单类型
				masterType.setPickType(pickType);// 配送方式
				masterType.setADD1(invWarehouse.getCenterCode());// 配送中心编码 C码

				masterType.setCustCode(t2OrderItem.getPayment_id());// 付款方编码
				masterType.setDestCode(t2OrderItem.getTransmit_id());// 送达方编码
				masterType.setBaseCode("");// 基地编码
				masterType.setCustMgr("00022923");// 客户经理
				masterType.setProMgr("00022923");// 产品经理
				masterType.setProDeputy("00022923");// 产品代表

				masterType.setPlanInDate(WSUtils.stringToXmlCalendar(
						predict_arrive_date, "yyyy-MM-dd"));// 预计到货日期
				masterType.setOrderCode("");
				masterType.setProjectCode("");
				masterType.setBudgetSort(budgetSort);// 预算体
				if (invBudgetOrg.getSuccess()
						&& invBudgetOrg.getResult() != null) {
					masterType.setBudgetOrg(invBudgetOrg.getResult()
							.getBudgetorg_id());// 预算种类
				}
				masterType.setInvSort(product_group_id);// 产品组编码
				masterType.setBrandCode(t2OrderItem.getBrand_id());// 品牌编码

				masterType.setMaker("CBS[" + params.get("audit_user") + "]");// 开单人
				masterType.setUserMemo("");
				masterType.setAudiMan("");// 审核人
				masterType.setAudiFlag(0);// 审核标识
				masterType.setBName("");
				masterType.setAddress("");
				masterType.setTel("");
				masterType.setZipCode("");
				masterType.setReGetmoney("");
				masterType.setUpAccount("");
				masterType.setFlag("");

				// 子记录
				DetailType dt = new DetailType();
				// 采购价
				if (priceResponse.getReStockPrice() != null
						&& !"".equals(priceResponse.getReStockPrice())) {
					dt.setStockPrice(new BigDecimal(priceResponse
							.getReStockPrice()));// 采购价
				} else {
					dt.setStockPrice(new BigDecimal(new Float(0)));
				}
				// 供价
				if (priceResponse.getReRetailPrice() != null
						&& !"".equals(priceResponse.getReRetailPrice())) {
					dt.setRetailPrice(new BigDecimal(priceResponse
							.getReRetailPrice()));// 供价
				} else {
					dt.setRetailPrice(new BigDecimal(new Float(0)));
				}
				// 执行价
				if (priceResponse.getReActPrice() != null
						&& !"".equals(priceResponse.getReActPrice())) {
					dt.setActPrice(new BigDecimal(priceResponse.getReActPrice()));// 执行价
				} else {
					dt.setActPrice(new BigDecimal(new Float(0)));
				}
				// 直扣
				if (priceResponse.getReBateRate() != null
						&& !"".equals(priceResponse.getReBateRate())) {
					dt.setBateRate(new BigDecimal(priceResponse.getReBateRate()));// 直扣
				} else {
					dt.setBateRate(new BigDecimal(new Float(0)));
				}
				// 台返金额
				if (priceResponse.getReBateMoney() != null
						&& !"".equals(priceResponse.getReBateMoney())) {
					dt.setBateMoney(new BigDecimal(priceResponse
							.getReBateMoney()));// 台返金额
				} else {
					dt.setBateMoney(new BigDecimal(new Float(0)));
				}
				// 折价损失
				if (priceResponse.getReLossRate() != null
						&& !"".equals(priceResponse.getReLossRate())) {
					dt.setLossRate(new BigDecimal(priceResponse.getReLossRate()));// 折扣
				} else {
					dt.setProLossMoney(new BigDecimal(new Float(0)));
				}
				// 价格情报设定
				if (priceResponse.getReUnitPrice() != null
						&& !"".equals(priceResponse.getReUnitPrice())) {
					// 开票单价
					dt.setUnitPrice(new BigDecimal(priceResponse
							.getReUnitPrice()));// 开票单价
				} else {
					dt.setUnitPrice(new BigDecimal(new Float(0)));
				}
				dt.setIsFL(StringUtils.parseInteger(StringUtils
						.isNotEmpty(priceResponse.getReIsFL()) ? "0"
						: priceResponse.getReIsFL()));// 是否返利
				dt.setIsKPO(StringUtils.parseInteger(StringUtils
						.isNotEmpty(priceResponse.getReIsKPO()) ? "0"
						: priceResponse.getReIsKPO()));// 是否商用空调
				dt.setProLossMoney(new BigDecimal(new Float(0)));// 折价损失

				dt.setBillCode(markQ
						+ t2OrderItem.getOrder_id().replace(oldId, newId));// 销售单号
				dt.setInvBrand(t2OrderItem.getBrand_id());// 品牌编码
				dt.setInvCode(t2OrderItem.getMaterials_id());// 物料编码
				dt.setInvMemo("");// 备注
				dt.setInvSort(product_group_id);// 产品组编码
				dt.setInvState(invState);// 批次
				dt.setQty(Integer.parseInt(t2OrderItem
						.getT2_delivery_prediction().toString()));// 数量
				dt.setSumMoney(dt.getUnitPrice().multiply(
						new BigDecimal(dt.getQty())));// 价税合计
				dt.setVerCode("");// 特价版本
				dt.setVerMoney(new BigDecimal(0));// 特价金额
				l_detail.add(dt);

				// 通过WA库位码获取日日顺库位
				rrsCodeList = getRrsList(storage_id);
				Boolean successFlag = false;
				try {
					if ("Q".equals(markQ)) {
						if ("XXO1".equals(storage_id)) {
							masterType.setWhCode("SZ81");
						} else if ("CQO1".equals(storage_id)) {
							masterType.setWhCode("CQ81");
						} else {
							masterType.setWhCode(storage_id.replace("O1", "81")
									.replace("O2", "81"));
						}
//						log.info("*****调用接口入参masterType："
//								+ JsonUtil.toJson(masterType));
//						log.info("*****调用接口入参l_detail："
//								+ JsonUtil.toJson(l_detail));

                        insertInterface("款先订单CRM手工采购入参masterType","款先直发订单",JsonUtil.toJson(masterType));
                        insertInterface("款先订单CRM手工采购入参l_detail","款先直发订单",JsonUtil.toJson(l_detail));

						soap.insertWAOrderBillFromEhaierToCRM("EHAIER",
								masterType, l_detail, billCode, flag, message,
								vbeln, vbelnDN);

                        insertInterface("款先订单CRM手工采购出参","款先直发订单",message.value);
                        result.setMessage(message.value);

//                        log.info("CRM开单结果：" + flag.value);
						// CRM开单成功
						if (flag.value.equalsIgnoreCase("Y")) {
							successFlag = true;
						}

					} else {
						for (InvRrsWarehouse rrsCode : rrsCodeList) {
							// 日日顺库位编码
							masterType.setWhCode(rrsCode.getRrs_wh_code());

//							log.info("*****调用接口入参masterType："
//									+ JsonUtil.toJson(masterType));
//							log.info("*****调用接口入参l_detail："
//									+ JsonUtil.toJson(l_detail));

                            insertInterface("款先订单CRM手工采购入参masterType","款先直发订单",JsonUtil.toJson(masterType));
                            insertInterface("款先订单CRM手工采购入参l_detail","款先直发订单",JsonUtil.toJson(l_detail));


                            soap.insertWAOrderBillFromEhaierToCRM("EHAIER",
									masterType, l_detail, billCode, flag,
									message, vbeln, vbelnDN);

                            insertInterface("款先订单CRM手工采购出参","款先直发订单",message.value);
                            result.setMessage(message.value);
//							log.info("CRM开单结果：" + flag.value);
							// 测试
							/*
							 * flag = new Holder<String>("Y"); message = new
							 * Holder<String>("库存test"); if
							 * ("CQT2".equals(rrsCode.getRrs_wh_code())) { flag
							 * = new Holder<String>("Y"); } else { message = new
							 * Holder<String>("test库存"); }
							 */

							// CRM开单成功
							if (flag.value.equalsIgnoreCase("Y")) {
								successFlag = true;
								break;
							} else {
								// 库存原因，使用下一个库位
								if (message.value.indexOf("库存") > 0) {
									continue;
								} else {
									break;
								}
							}
						}
					}
				} catch (Exception e) {
					// 调用接口失败，捕获异常
					t2OrderItem.setAudit_user(String.valueOf(params
							.get("audit_user")));
					t2OrderItem.setAudit_remark(String.valueOf(params
							.get("audit_remark")));
					t2OrderItem.setFlow_flag(t2OrderItem.getFlow_flag());// 不更新
					t2OrderItem.setSync_status(0);// 不更新
					t2OrderItem.setError_msg("调用CRM接口失败");// 错误信息
					try {
						// 情报更新
						purchaseT2OrderService
								.updateSyncStatusByOms(t2OrderItem);
					} catch (Exception ex) {
						// 记录日志
						log.error(
								"[T2OrderModel][updateSyncStatusByOms]:更新提报OMS的状态更新失败:",
								ex);
					}
				}
				// 结果
				if (successFlag) {
					// 成功
					scccessList.add(t2OrderItem.getOrder_id());

					t2OrderItem.setSync_status(0);// 不更新
					t2OrderItem
							.setPredict_arrive_date_display(predict_arrive_date);
					try {
						// 情报更新
						purchaseT2OrderService
								.updateSyncStatusByOms(t2OrderItem);
					} catch (Exception ex) {
						log.error(
								"[T2OrderModel][updateSyncStatusByOms]:更新提报OMS的状态更新失败:",
								ex);
					}
				} else {
					// 调用接口错误信息写入表中
					if (message.value != null) {
						// 失败
						t2OrderItem.setAudit_user(String.valueOf(params
								.get("audit_user")));
						t2OrderItem.setAudit_remark(String.valueOf(params
								.get("audit_remark")));
						t2OrderItem.setFlow_flag(t2OrderItem.getFlow_flag());// 不更新
						t2OrderItem.setSync_status(0);// 不更新
						t2OrderItem.setError_msg(message.value);// 错误信息
						try {
							// 情报更新
							purchaseT2OrderService
									.updateSyncStatusByOms(t2OrderItem);
						} catch (Exception ex) {
							// 记录日志
							log.error(
									"[T2OrderModel][updateSyncStatusByOms]:更新提报OMS的状态更新失败:",
									ex);
						}
					}
					if ("Q".equals(markQ)) {
						throw new Exception("款先直发订单审核失败："
								+ t2OrderItem.getOrder_id());
					} else {
						throw new Exception("机壳不结算订单审核失败："
								+ t2OrderItem.getOrder_id());
					}
				}
			}
			result.setResult(scccessList);
		} catch (Exception ex) {
			log.error("T+2:insertWAOrderBillToCRM", ex);
			result.setSuccess(false);
		}
		return result;
	}

	public GVSOrderPriceResponse quirePrice(GVSOrderPriceRequire order)
			throws Exception {

		String url = priceUrl;
		String data = "<INPUT><INPUTROW><CustCode>" + order.getCustCode()
				+ "</CustCode><RegionCode>" + order.getRegionCode()
				+ "</RegionCode><InvCode>" + order.getInvCode()
				+ "</InvCode><CorpCode>" + order.getCorpCode()
				+ "</CorpCode></INPUTROW></INPUT>";

        insertInterface("款先订单获取价格信息入参","款先直发订单",data);

		String resultMsg = HttpUtils.sendRequest(url, null, data,
				HttpUtils.HTTP_METHOD_POST, false, null);
		GVSOrderPriceResponse result = new GVSOrderPriceResponse();
		Document doc = DocumentHelper.parseText(resultMsg);
		Element element = (Element) doc.getRootElement().elements().get(0);
		List<Element> list = element.elements();
		for (Element el : list) {
			if (el.getName().equals("ReInvCode")) {
				result.setReInvCode(el.getText());
			} else if (el.getName().equals("ReUnitPrice")) {
				result.setReUnitPrice(el.getText());
			} else if (el.getName().equals("ReStockPrice")) {
				result.setReStockPrice(el.getText());
			} else if (el.getName().equals("ReRetailPrice")) {
				result.setReRetailPrice(el.getText());
			} else if (el.getName().equals("ReActPrice")) {
				result.setReActPrice(el.getText());
			} else if (el.getName().equals("ReBateRate")) {
				result.setReBateRate(el.getText());
			} else if (el.getName().equals("ReBateMoney")) {
				result.setReBateMoney(el.getText());
			} else if (el.getName().equals("ReLossRate")) {
				result.setReLossRate(el.getText());
			} else if (el.getName().equals("ReIsFL")) {
				result.setReIsFL(el.getText());
			} else if (el.getName().equals("ReIsKPO")) {
				result.setReIsKPO(el.getText());
			}
		}
        insertInterface("款先订单获取价格信息出参","款先直发订单",resultMsg);
		return result;
	}

	/**
	 * 审核订单（款先）
	 * 
	 * @param params
	 * @return
	 * @see com.haier.cbs.purchase.service.T2OrderService#reviewT2OrderList(java.util.Map)
	 */
	@Override
	public ServiceResult<Boolean> reviewKXOrderList(Map<String, Object> params) {
		// System.out.println("-------------进入款先直发订单推送oms-----------------");
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Boolean reviewResult = true;
			// 订单情报取得
			List<T2OrderItem> reviewedOrderList = null;
			synchronized (lockAudit) {

				// System.out.println("flow_flag"+params.get("flow_flag").toString());
				// 审核不过的场合，状态更新
				if ("-10".equals(params.get("flow_flag").toString())) {
					// 审核订单
					try {
						// 情报更新
						purchaseT2OrderService.reviewT2Order(params);
						result.setResult(true);
					} catch (Exception ex) {
						result.setResult(false);
						// 记录日志
						log.error("[T2OrderDao][reviewT2OrderList]:订单审核失败:", ex);
					}
					// 审核通过场合
				} else {
					// 订单情报取得
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					params.put("flow_flag", 5);
					params.put("sync_status", "-20,-10,0");
					params.put("report_year_week", WSUtils
							.getWeekOfYear_Sunday(sdf.format(new Date()),
									"yyyy-MM-dd", "0"));
//					log.info("正在查询要同步的款先订单");
					reviewedOrderList = purchaseT2OrderService
							.findT2OrderForOms(params);
//					log.info("查询待同步的款先订单完毕,数量:" + reviewedOrderList.size());
//					log.info("正在修改款先订单同步状态");
					String markQ = "Q";
					String oldId = "WP10";
					String newId = "WN";
					// 预计入库日期
					Calendar cal = Calendar.getInstance();
					cal.setFirstDayOfWeek(Calendar.SUNDAY);
					if (cal.get(Calendar.DAY_OF_WEEK) > 4) {// 3变更4 2016.10.12
						cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 17 + 10
								- cal.get(Calendar.DAY_OF_WEEK));
					} else {
						cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 17 + 3
								- cal.get(Calendar.DAY_OF_WEEK));
					}
					String predict_arrive_date = new SimpleDateFormat(
							"yyyy-MM-dd").format(cal.getTime());

					for (T2OrderItem reviewedOrder : reviewedOrderList) {
						String msg = checkStorage(reviewedOrder);// check库位
																	// 返回空为check成功
						ServiceResult<InvWarehouse> invWarehouseResult = new ServiceResult<InvWarehouse>();
						// System.out.println("msg==="+msg);
						if ("".equals(msg)) {
							if (1 == reviewedOrder.getOrder_category() || 3 == reviewedOrder.getOrder_category()) {
								// System.out.println("数据整理，准备推送OMS");

								String order_id = reviewedOrder.getOrder_id();
								String storage_id = reviewedOrder
										.getStorage_id();
								String BillCode = reviewedOrder.getOrder_id()
										.replace("WP10", "KWN");// BillCode,采购订单号
																// KWN码
								String CorpCode = "2000";// CorpCode,公司编码
															// 默认传2000
								invWarehouseResult = purchaseBaseCommonService
										.getAllWhByEstorgeId(storage_id);
								String RegionID = "";
								String CustCode = "";
								String MGCustCode = "";
								String CorpDest = "";
								String CustDest = "";
								if (invWarehouseResult.getSuccess()
										&& invWarehouseResult.getResult() != null) {
									RegionID = invWarehouseResult.getResult()
											.getArea_id();// CRM地区编码(工贸编码)RegionID
															// ,地区编码
									CustCode = invWarehouseResult.getResult()
											.getPayment_id();// 客户售达方
									MGCustCode = invWarehouseResult.getResult()
											.getCustom_id();// 管理客户编码 3W业绩管理客户B码
									CorpDest = invWarehouseResult.getResult()
											.getCenterCode();// 日日顺配送中心编码
									CustDest = invWarehouseResult.getResult()
											.getTransmit_id();// 客户送达方 3W送达方
								}
								String BillType = "J001";// billType,订单类型
															// 默认传J001
								String PickType = "6";// PickType,提货方式 默认传6

								String WhCode = "";// 仓库编码 81库
								if ("XXO1".equals(storage_id)) {
									WhCode = "SZ81";
								} else if ("CQO1".equals(storage_id)) {
									WhCode = "CQ81";
								} else {
									WhCode = storage_id.replace("O1", "81")
											.replace("O2", "81");
								}
								String CustMgr = "00022923";// 客户经理
								String ProMgr = "00022923";// 产品经理
								String ProDeputy = "00022923";// 产品代表
								String PlanInDate = predict_arrive_date;// 预计入库日期
								String OrderCode = "";// 客户订单号，默认传空
								String Maker = "CBS["
										+ params.get("audit_user") + "]";// 开单人
								String LockDay = "0";// 锁定天数 默认传0
								String InvCode = reviewedOrder
										.getMaterials_id();// 产品编码
								String InvSort = reviewedOrder
										.getProduct_group_id();// 产品组
								String Qty = reviewedOrder
										.getT2_delivery_prediction().toString();// 数量;
								String ReleBillCode = markQ
										+ reviewedOrder.getOrder_id().replace(
												oldId, newId);// Q单号,关联单号 QWN码
								String UserMemo = "";// 用户备注，默认空
								String Flag = "2";// 订单标识 默认传2
								String ADD1 = "";
								String ADD2 = "";
								String ADD3 = "";
								// 组装xml格式参数
								StringBuffer sb = new StringBuffer();
								// sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body>");
								sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:fac=\"http://facade.invoice.einv.mpc.com/\">");
								sb.append("<soapenv:Header/><soapenv:Body>");
								sb.append("<PARAMETER>");
								sb.append("<!--Zero or more repetitions:-->");
								sb.append("<DETAIL>");
								sb.append("<BillCode>" + BillCode
										+ "</BillCode>");
								sb.append("<CorpCode>" + CorpCode
										+ "</CorpCode>");
								sb.append("<RegionID>" + RegionID
										+ "</RegionID>");
								sb.append("<BillType>" + BillType
										+ "</BillType>");
								sb.append("<PickType>" + PickType
										+ "</PickType>");
								sb.append("<CorpDest>" + CorpDest
										+ "</CorpDest>");
								sb.append("<WhCode>" + WhCode + "</WhCode>");
								sb.append("<CustMgr>" + CustMgr + "</CustMgr>");
								sb.append("<ProMgr>" + ProMgr + "</ProMgr>");
								sb.append("<ProDeputy>" + ProDeputy
										+ "</ProDeputy>");
								sb.append("<PlanInDate>" + PlanInDate
										+ "</PlanInDate>");
								sb.append("<OrderCode>" + OrderCode
										+ "</OrderCode>");
								sb.append("<CustCode>" + CustCode
										+ "</CustCode>");
								sb.append("<CustDest>" + CustDest
										+ "</CustDest>");
								sb.append("<MGCustCode>" + MGCustCode
										+ "</MGCustCode>");
								sb.append("<Maker>" + Maker + "</Maker>");
								sb.append("<LockDay>" + LockDay + "</LockDay>");
								sb.append("<InvCode>" + InvCode + "</InvCode>");
								sb.append("<InvSort>" + InvSort + "</InvSort>");
								sb.append("<Qty>" + Qty + "</Qty>");
								sb.append("<ReleBillCode>" + ReleBillCode
										+ "</ReleBillCode>");
								sb.append("<UserMemo>" + UserMemo
										+ "</UserMemo>");
								sb.append("<Flag>" + Flag + "</Flag>");
								sb.append("<ADD1>" + ADD1 + "</ADD1>");
								sb.append("<ADD2>" + ADD2 + "</ADD2>");
								sb.append("<ADD3>" + ADD3 + "</ADD3>");
								sb.append("</DETAIL>");
								sb.append("</PARAMETER>");
								// sb.append("</soap:Body></soap:Envelope>");
								sb.append("</soapenv:Body></soapenv:Envelope>");
								// System.out.println("数据整理完成，开始调用推送OMS接口");
								// 调用接口，推送oms评审

                                insertInterface("款先订单推送OMS入参","款先直发订单",sb.toString());

								ServiceResult<Boolean> omsResult = sendToOMS(
										sb.toString(), order_id);

                                insertInterface("款先订单推送OMS出参","款先直发订单",omsResult.getMessage());
                                result.setMessage(omsResult.getMessage());
								if (omsResult.getSuccess()) {
									reviewedOrder.setAudit_user(String
											.valueOf(params.get("audit_user")));
									reviewedOrder
											.setAudit_remark(String.valueOf(params
													.get("audit_remark")));
									reviewedOrder.setFlow_flag(10);// 内部审核通过
									reviewedOrder.setSync_status(20);// 传输成功
									reviewedOrder.setError_msg(omsResult
											.getMessage());
									result.setSuccess(true);
									result.setResult(true);
								} else {
									reviewedOrder.setAudit_user(String
											.valueOf(params.get("audit_user")));
									reviewedOrder
											.setAudit_remark(String.valueOf(params
													.get("audit_remark")));
									reviewedOrder.setFlow_flag(5);// OMS审核失败（OMS相关校验校验失败）
									reviewedOrder.setSync_status(0);// 传输成功
									reviewedOrder.setError_msg(omsResult
											.getMessage());
									result.setSuccess(false);
                                    result.setResult(false);
								}
							}
						} else {
							// System.out.println("进入else，不推送OMS");
							reviewedOrder.setAudit_user(String.valueOf(params
									.get("audit_user")));
							reviewedOrder.setAudit_remark(String.valueOf(params
									.get("audit_remark")));
							reviewedOrder.setFlow_flag(reviewedOrder
									.getFlow_flag());// 不更新
							reviewedOrder.setSync_status(reviewedOrder
									.getSync_status());// 不更新
							reviewedOrder.setError_msg(msg);
						}
						try {
							// 情报更新
							purchaseT2OrderService
									.updateSyncStatusByOms(reviewedOrder);
						} catch (Exception ex) {
							// 记录日志
							log.error(
									"[T2OrderDao][updateSyncStatusByOms]:更新提报OMS的状态更新失败:",
									ex);
						}
					}
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
            result.setResult(false);
			result.setMessage(e.getMessage());
			log.error("审核订单失败：", e);
		}
		return result;
	}

	/**
	 * 通过电商库位码获取日日顺仓库List
	 * 
	 * @param estorge_id
	 *            WA库位码
	 * @return List<InvRrsWarehouse>
	 */
	private List<InvRrsWarehouse> getRrsList(String estorge_id) {
		//
		Map<String, Object> invRrsWarehouseParams = new HashMap<String, Object>();
		invRrsWarehouseParams.put("estorge_id", estorge_id);
		ServiceResult<List<InvRrsWarehouse>> invRrsResult = purchaseBaseCommonService
				.getAllRrsWhByEstorgeId(invRrsWarehouseParams);

		if (invRrsResult.getSuccess() && invRrsResult.getResult() != null
				&& invRrsResult.getResult().size() > 0) {
			return invRrsResult.getResult();
		} else {
			return null;
		}
	}

	/**
	 * 款先订单推送OMS评审
	 * 
	 * @return
	 */
	// @Transactional
	public ServiceResult<Boolean> sendToOMS(String requestStr, String order_id) {
		// System.out.println("data="+requestStr);
//		log.info("款先推送OMS数据：" + requestStr);
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			URL url = new URL(crm_int_oms_4);
			byte[] data = requestStr.getBytes("utf-8");
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			httpConn.setRequestProperty("Content-Length",
					String.valueOf(data.length));
			httpConn.setRequestProperty("Content-Type",
					"text/xml; charset=utf-8");
			httpConn.setRequestProperty("SOAPAction", crm_int_oms_4);
			httpConn.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT //5.1)AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.46 Safari/535.11");
			// httpConn.setConnectTimeout(5000);//连接超时5秒
			// httpConn.setReadTimeout(10000);//读取数据超时10秒
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			OutputStream out = httpConn.getOutputStream();
			out.write(data);
			out.close();
			byte[] datas = readInputStream(httpConn.getInputStream());
			String resultto = new String(datas, "utf-8");
			// System.out.println(resultto);
			Document doc = DocumentHelper.parseText(resultto);
			// System.out.println(doc.getRootElement().elements().size());
			Element el = doc.getRootElement();// Body
			@SuppressWarnings("unchecked")
			List<Element> list = el.elements();
			Map<String, String> map = new HashMap<String, String>();
			for (Element element : list) {
				map.put(element.getName(), element.getText());
			}
			// System.out.println(JSON.toJSON(map));
			String a = map.get("Body");
//			log.info("oms审批结果：" + a);
			if (a.indexOf("<CODE>") == -1) {
				// String flag=a.substring(a.indexOf("<Flag>", 1)+6,
				// a.indexOf("</Flag>", 1));
				String returnmsg = a.substring(
						a.indexOf("<ReturnMsg>", 1) + 11,
						a.indexOf("</ReturnMsg>", 1));
				String OMSOrderId = a.substring(a.indexOf("<Vbeln>", 1) + 7,
						a.indexOf("</Vbeln>", 1));
//				log.info("OMS审批返回R单号：" + OMSOrderId);
				try {
					T2OrderItem t2OrderItem = new T2OrderItem();
					t2OrderItem.setOms_order_id(OMSOrderId);
					t2OrderItem.setOrder_id(order_id);
					try {
						// 情报更新
						purchaseT2OrderService.updateByOms(t2OrderItem);
						if (t2OrderItem.getFlow_flag() != null) {
							purchaseT2OrderService
									.updateOmsFlowFlagOnly(t2OrderItem);
						}
					} catch (Exception ex) {
						// 记录日志
						log.error("[T2OrderModel][updateByOms]:提报OMS的状态更新失败:",
								ex);
					}
				} catch (Exception e) {
					log.error("审批返回R单更新到oms订单号异常：" + e.getMessage());
				}
				result.setSuccess(true);
				result.setMessage(returnmsg);
			} else {
				String msg = a.substring(a.indexOf("<MSG>", 1) + 5,
						a.indexOf("</MSG>", 1));
				result.setSuccess(false);
				result.setMessage(msg);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("推送OMS异常");
			log.error("款先推OMS异常：" + e.getMessage());
		}
		return result;
	}

	private byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();// 二进制数据
		outStream.close();
		inStream.close();
		return data;
	}

	private void insertInterface(String interfaceName,String interfaceCategory,Object interfaceMessage){
	    try {
            Map<String,Object> map = new HashMap<>();
            map.put("interfaceName",interfaceName);
            map.put("interfaceCategory",interfaceCategory);
            map.put("interfaceDate",new Date());
            map.put("interfaceMessage",interfaceMessage);
            purchaseT2OrderService.insertT2OrderInterfaceLog(map);
        } catch (Exception e){
            e.printStackTrace();
            log.error("添加接口日志异常：" + e.getMessage());
        }

    }

    @Override
    public ServiceResult<List<T2OrderInterfaceLog>> findPurchaseLog(Map<String, Object> params) {
        ServiceResult<List<T2OrderInterfaceLog>> result = new ServiceResult<List<T2OrderInterfaceLog>>();
        try {
            result.setResult(purchaseT2OrderService.findPurchaseLog(params));
            int pagecount = purchaseT2OrderService.getPurchaseLogRow(params);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(pagecount);
            result.setPager(pi);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

	@Override
	public List<T2OrderItem> getT2WdOrderId(Map<String, Object> params) {
		return purchaseT2OrderService.getT2WdOrderId(params);
	}

	@Override
	public List<InvStockChannel> getzChannelMap() {
		List<InvStockChannel> InvStockChannel = stockInvStockChannelService
				.getAll();
		return InvStockChannel;
	}
}
