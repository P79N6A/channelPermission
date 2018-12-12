package com.haier.logistics.Model;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.GoodsBackInfoResponse;
import com.haier.purchase.data.service.OrderOperationLogService;
import com.haier.purchase.data.service.PurchaseVomOrderService;
import com.haier.shop.model.VOMCancelOrderRequire;
import com.haier.shop.model.VOMOrderResponse;
import com.haier.shop.model.VOMSynOrderRequire;
import com.haier.shop.model.VOMSynSubOrderRequire;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
@Service
public class VOMOrderModel {

	//protected static final String VOM_SYN_ORDER_URL = "/EAI/service/VOM/CommonGetWayToVOM/CommonGetWayToVOM";
	protected final String		 VOM_SYN_ORDER_URL = "/EAI/RoutingProxyService/EAI_REST_POST_ServiceRoot?INT_CODE=EAI_INT_1353";

	private Logger				 logger			   = LogManager.getLogger(VOMOrderModel.class);

	private String				 eaiUrl			   = null;
	@Autowired
	public PurchaseVomOrderService			 purchaseVomOrderService;
	@Autowired
	private OrderOperationLogService orderOperationLogService;

	public PurchaseVomOrderService getPurchaseVomOrderService() {
		return purchaseVomOrderService;
	}

	public void setPurchaseVomOrderService(PurchaseVomOrderService purchaseVomOrderService) {
		this.purchaseVomOrderService = purchaseVomOrderService;
	}

	private DataSourceTransactionManager transactionManager;

	public void setTransactionManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setEaiUrl(String eaiUrl) {
		this.eaiUrl = eaiUrl;
	}


	/**
	 * 封装请求参数(vom开提单)
	 * 
	 * @param vomSynOrderRequire
	 * @return
	 */
	public static String getVomContentXml(VOMSynOrderRequire vomSynOrderRequire) {

		StringBuffer sb = new StringBuffer();
		sb.append("<Order>");
		if (vomSynOrderRequire == null) {
			sb.append("</Order>");
			return sb.toString();
		}

		sb.append("<orderno>" + vomSynOrderRequire.getOrderNo() + "</orderno>");// 网单号
		sb.append("<sourcesn>" + vomSynOrderRequire.getSourceSn() + "</sourcesn>");// 订单号
		sb.append("<ordertype>" + vomSynOrderRequire.getOrderType() + "</ordertype>");// 订单类型
		sb.append("<bustype>" + vomSynOrderRequire.getBusType() + "</bustype>");// 业务类型
		sb.append("<expno></expno>");// 快递单号
		sb.append("<orderdate>" + vomSynOrderRequire.getOrderDate() + "</orderdate>");// 订单时间（格式：YYYY-MM-DD HH:MM:SS）
		sb.append("<storecode>" + vomSynOrderRequire.getStoreCode() + "</storecode>");// scode转
																					  // 中心代码
																					  // 仓库编码：按日日顺C码
		sb.append("<province>" + vomSynOrderRequire.getProvince() + "</province>");// 收货人所在省
		sb.append("<city>" + vomSynOrderRequire.getCity() + "</city>");// 收货人所在市
		sb.append("<county>" + vomSynOrderRequire.getCounty() + "</county>");// 收货人所在县/区
		sb.append("<addr><![CDATA[" + vomSynOrderRequire.getAddr() + "]]></addr>");// 详细地址
		sb.append("<gbcode>" + vomSynOrderRequire.getGbCode() + "</gbcode>");// 国标码
		sb.append("<name><![CDATA[" + vomSynOrderRequire.getName() + "]]></name>");// 送达方姓名：收货人姓名
		sb.append("<mobile>" + vomSynOrderRequire.getMobile() + "</mobile>");// 联系电话
		sb.append("<tel>" + vomSynOrderRequire.getTel() + "</tel>");// 固定电话
		sb.append("<postcode>" + vomSynOrderRequire.getPostCode() + "</postcode>");// 邮政编码
		sb.append("<deldate>" + vomSynOrderRequire.getDelDate() + "</deldate>");// 预约时间：预约送货时间
		sb.append("<insdate>" + vomSynOrderRequire.getInsDate() + "</insdate>");// 预约时间：预约安装时间
		sb.append("<reorder>" + vomSynOrderRequire.getReorder() + "</reorder>");// 前续订单号：关联单号
		sb.append("<freight>" + vomSynOrderRequire.getFreight() + "</freight>");// 运费
		sb.append("<billsum>" + vomSynOrderRequire.getBillSum() + "</billsum>");// 订单总金额
		sb.append("<billowe>" + vomSynOrderRequire.getBillOwe() + "</billowe>");// 代收金额：应收欠款
		sb.append("<paystate>" + vomSynOrderRequire.getPayState() + "</paystate>");// 付款状态：P1-已付款，P2-代收货款
		sb.append("<paytime>" + vomSynOrderRequire.getPayTime() + "</paytime>");// 付款时间// ---订单为cod时付款时间为空
		sb.append("<paytype>" + vomSynOrderRequire.getPayType() + "</paytype>");// 支付类别（如支付宝、现金、银联等)
		sb.append("<isinv>" + vomSynOrderRequire.getIsInv() + "</isinv>");// 是否需要开具发票：1.是   2.否
		sb.append("<invtype>" + vomSynOrderRequire.getInvType() + "</invtype>");// 发票类型
		if (!"".equals(vomSynOrderRequire.getInvRise())
			&& vomSynOrderRequire.getInvRise() != null) {
			sb.append("<invrise><![CDATA[" + vomSynOrderRequire.getInvRise() + "]]></invrise>");// 发票抬头
		} else {
			sb.append("<invrise></invrise>");
		}
		sb.append("<taxbearer>" + vomSynOrderRequire.getTaxBearer() + "</taxbearer>");// 纳税人登记号
		if (!"".equals(vomSynOrderRequire.getRecAddr())
			&& vomSynOrderRequire.getRecAddr() != null) {
			sb.append("<recaddr><![CDATA[" + vomSynOrderRequire.getRecAddr() + "]]></recaddr>");// 发票地址
		} else {
			sb.append("<recaddr></recaddr>");
		}
		sb.append("<recacc>" + vomSynOrderRequire.getRecAcc() + "</recacc>");// 发票开户行帐号
		if (!"".equals(vomSynOrderRequire.getRecBank())
			&& vomSynOrderRequire.getRecBank() != null) {
			sb.append("<recbank><![CDATA[" + vomSynOrderRequire.getRecBank() + "]]></recbank>");// 发票开户行
		} else {
			sb.append("<recbank></recbank>");
		}
		sb.append("<sname>" + vomSynOrderRequire.getSname() + "</sname>");// 发货人姓名
		sb.append("<sprovince>" + vomSynOrderRequire.getSprovince() + "</sprovince>");// 发货人所在省
		sb.append("<scity>" + vomSynOrderRequire.getScity() + "</scity>");// 发货人所在市
		sb.append("<scounty>" + vomSynOrderRequire.getScounty() + "</scounty>");// 发货人所在县/区
		sb.append("<saddr>" + vomSynOrderRequire.getSaddr() + "</saddr>");// 发货人详细地址
		sb.append("<smobile>" + vomSynOrderRequire.getSmobile() + "</smobile>");// 发货人联系电话
		sb.append("<stel>" + vomSynOrderRequire.getStel() + "</stel>");// 发货人固定电话
		sb.append("<busflag>" + vomSynOrderRequire.getBusFlag() + "</busflag>");// 订单标记 1送装一体 2.只配送 3.开箱验货

		if (!"".equals(vomSynOrderRequire.getRemark()) && vomSynOrderRequire.getRemark() != null) {
			sb.append("<remark><![CDATA[" + vomSynOrderRequire.getRemark() + "]]></remark>");// 备注
		} else {
			sb.append("<remark></remark>");
		}
		sb.append("<attributes></attributes>");// 属性备注
		sb.append("<remark1></remark1>");// 备用字段 --不填
		sb.append("<remark2></remark2>");// 备用字段 --不填
		sb.append("<remark3></remark3>");// 备用字段 --不填
		sb.append("<remark4></remark4>");// 备用字段 --不填
		sb.append("<remark5></remark5>");// 备用字段 --不填
		sb.append("<remark6></remark6>");// 备用字段 --不填
		sb.append("<remark7></remark7>");// 备用字段 --不填
		sb.append("<remark8></remark8>");// 备用字段 --不填

		// 子订单
		sb.append("<items>");
		List<VOMSynSubOrderRequire> subOrderList = vomSynOrderRequire.getSubOrderList();
		if (subOrderList.size() > 0) {
			for (VOMSynSubOrderRequire synSubOrderRequire : subOrderList) {
				sb.append("<Item>");
				sb.append("<itemno>" + synSubOrderRequire.getItemNo() + "</itemno>");
				sb.append("<storagetype>" + synSubOrderRequire.getStorageType() + "</storagetype>");// 批次 产品状态:10 正品 21 不良 22 破箱 40 样品 L0礼品
				sb.append("<productcode>" + synSubOrderRequire.getProductCode() + "</productcode>");// 客户产品编码 -- sku 套机填sub_sku
				sb.append("<hrcode>" + synSubOrderRequire.getHrCode() + "</hrcode>");// 海尔产品编码// 日日顺物流生成
				sb.append("<prodes>" + synSubOrderRequire.getProdes() + "</prodes>");// 产品描述
				sb.append("<volume>" + synSubOrderRequire.getVolume() + "</volume>");// 体积
				sb.append("<weight>" + synSubOrderRequire.getWeight() + "</weight>");// 重量
				sb.append("<number>" + synSubOrderRequire.getNumber() + "</number>");// 网单数量乘以组件数量// 不是套机填网单数量
				sb.append("<unprice>" + synSubOrderRequire.getUnprice() + "</unprice>");// 单价
				sb.append("<reitem>" + synSubOrderRequire.getReitem() + "</reitem>");// 前续订单行号(关联行号)
				sb.append("<attributes></attributes>");// 属性备注
				sb.append("<remark1></remark1>");// 备用
				sb.append("<remark2></remark2>");// 备用
				sb.append("</Item>");
			}
		} else {
			sb.append("<Item>");
			sb.append("<itemno></itemno>");
			sb.append("<storagetype></storagetype>");
			sb.append("<productcode></productcode>");
			sb.append("<hrcode></hrcode>");
			sb.append("<prodes></prodes>");
			sb.append("<volume></volume>");
			sb.append("<weight></weight>");
			sb.append("<number></number>");
			sb.append("<unprice></unprice>");
			sb.append("<reitem></reitem>");
			sb.append("<attributes></attributes>");
			sb.append("<remark1></remark1>");
			sb.append("<remark2></remark2>");
			sb.append("</Item>");
		}
		sb.append("</items>");
		sb.append("</Order>");

		return sb.toString();
	}


	private VOMOrderResponse getVomRequestResult(String xmlStr) throws DocumentException {
		VOMOrderResponse vomOrderResponse = new VOMOrderResponse();
		Document doc = DocumentHelper.parseText(xmlStr);
		Element rootElt = doc.getRootElement();
		String flag = rootElt.element("flag").getStringValue();
		String msg = rootElt.element("msg").getStringValue();
		vomOrderResponse.setFlag(flag);
		vomOrderResponse.setMsg(msg);
		return vomOrderResponse;
	}

	public static String getVomFContentXml(VOMCancelOrderRequire cancelOrderRequire) {
		StringBuffer sb = new StringBuffer();
		sb.append("<CancelCode>");
		if (cancelOrderRequire == null) {
			sb.append("</CancelCode>");
			return sb.toString();
		}
		sb.append("<orderno>" + cancelOrderRequire.getOrderNo() + "</orderno>");// 网单号
		sb.append("<canceltype>" + cancelOrderRequire.getCancelType() + "</canceltype>");// 取消类型：1.出库前取消 2.拦截订单

		if (!"".equals(cancelOrderRequire.getCancelExplain())
			&& cancelOrderRequire.getCancelExplain() != null) {//取消说明
			sb.append("<cancelexplain><![CDATA[" + cancelOrderRequire.getCancelExplain()
					  + "]]></cancelexplain>");
		} else {
			sb.append("<cancelexplain></cancelexplain>");
		}
		sb.append("</CancelCode>");

		return sb.toString();
	}

	public ServiceResult<Boolean> synVomStatus(Map<String, Object> params) {
		ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
		String orderNo = (String) params.get("orderNo");
		String prefix = orderNo.substring(0, 3);
		String status = (String) params.get("status");
		String error_msg = (String) params.get("errorMsg");
		Integer count = 0;
		String flow_flag = "";
		Integer iStatus = Integer.parseInt(status);
		try {
			if (iStatus != 140 && iStatus != -120) {
				throw new Exception("无法识别的状态:" + iStatus);
			} else {
				logger.info("同步VOM状态:" + JsonUtil.toJson(params));
			}
			if ("WD1".equals(prefix)) {//CRM
				Map<String, Object> paramMap = new HashMap<String, Object>();
				if (orderNo.indexOf("T") > 0) {
					paramMap.put("source_order_id", orderNo.substring(0, orderNo.indexOf("T")));
				} else {
					paramMap.put("source_order_id", orderNo);
				}

				flow_flag = purchaseVomOrderService.getFlowFlagByCrmReturnInfo(paramMap);
				if (iStatus == 140) {
					if ("15".equals(flow_flag)) {
						status = "20";
						paramMap.put("deliveryTime", "yes");
					} else if ("35".equals(flow_flag)) {
						status = "50";
					}
				} else if (iStatus == -120) {
					status = flow_flag;
					paramMap.put("error_msg", error_msg);
				}
				paramMap.put("flow_flag", status);
				count = purchaseVomOrderService.updateCrmReturnInfo(paramMap);
				/*serviceResult.setResult(true);
				serviceResult.setMessage("订单处理成功");*/
			} else if ("WD2".equals(prefix)) {//CGO
				Map<String, Object> paramMap = new HashMap<String, Object>();
				if (orderNo.indexOf("T") > 0) {
					paramMap.put("order_id", orderNo.substring(0, orderNo.indexOf("T")));
				} else {
					paramMap.put("order_id", orderNo);
				}
				flow_flag = purchaseVomOrderService.getFlowFlagBySiOuInfo(paramMap);
				if (iStatus == 140) {
					if ("155".equals(flow_flag)) {
						status = "170";
					}
					paramMap.put("deliveryTime", "yes");
				} else if (iStatus == -120) {
					status = flow_flag;
					paramMap.put("error_msg", error_msg);
				}

				paramMap.put("flow_flag", status);
				count = purchaseVomOrderService.updateLeaderReturnStatus(paramMap);
				/*serviceResult.setResult(true);
				serviceResult.setMessage("订单处理成功");*/
			} else if ("WD3".equals(prefix)) {//DBM
				Map<String, Object> paramMap = new HashMap<String, Object>();
				if (orderNo.indexOf("T") > 0) {
					paramMap.put("order_id", orderNo.substring(0, orderNo.indexOf("T")));
				} else {
					paramMap.put("order_id", orderNo);
				}
				flow_flag = purchaseVomOrderService.getFlowFlagBySiOuInfo(paramMap);
				if (iStatus == 140) {
					if ("155".equals(flow_flag)) {
						status = "170";
					}
					if ("140".equals(status)) {
						paramMap.put("deliveryTime", "yes");
					}
				} else if (iStatus == -120) {
					status = flow_flag;
					paramMap.put("error_msg", error_msg);
				}

				paramMap.put("flow_flag", status);
				count = purchaseVomOrderService.updateLeaderReturnStatus(paramMap);
				/*serviceResult.setResult(true);
				serviceResult.setMessage("订单处理成功");*/
			} else {
				serviceResult.setResult(false);
				serviceResult.setMessage("没有符合要求的订单");
			}

			if (count == 0) {
				serviceResult.setResult(false);
				serviceResult.setMessage("更新记录为0");
			} else {
				serviceResult.setResult(true);
				serviceResult.setMessage("订单处理成功");
			}
		} catch (Exception ex) {
			logger.error("同步VOM状态失败:" + JsonUtil.toJson(params), ex);
			serviceResult.setSuccess(false);
			serviceResult.setMessage(ex.getMessage());
			serviceResult.setResult(false);
		}
		return serviceResult;
	}

	public ServiceResult<Boolean> returnVomStatus(Map<String, Object> params) {
		ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
		String orderNo = (String) params.get("orderNo");
		String prefix = orderNo.substring(0, 3);
		String status = (String) params.get("status");
		String errorMsg = (String) params.get("errorMsg");
		String waouttime = null;
		String waintime = null;
		if (params.get("waouttime") != null) {
			waouttime = (String) params.get("waouttime");
		}
		if (params.get("waintime") != null) {
			waintime = (String) params.get("waintime");
		}
		Integer count = 0;
		try {
			logger.info("收到CBS出库状态：" + JsonUtil.toJson(params));
			params.put("error_msg", errorMsg);
			if ("WD1".equals(prefix)) {//CRM
				Map<String, Object> paramMap = new HashMap<String, Object>();
				Map<String, Object> paramsLog = new HashMap<String, Object>();//订单履历
				if (orderNo.indexOf("T") > 0) {
					paramMap.put("source_order_id", orderNo.substring(0, orderNo.length() - 2));
					paramsLog.put("order_id", orderNo.substring(0, orderNo.length() - 4));
					paramsLog.put("sub_order_id", orderNo.substring(0, orderNo.length() - 2));
				} else {
					paramMap.put("source_order_id", orderNo);
					paramsLog.put("order_id", orderNo.substring(0, orderNo.length() - 2));
					paramsLog.put("sub_order_id", orderNo);
				}
				if ("150".equals(status)) {
					status = "30";
					paramMap.put("wa_out_time", waouttime);
					paramsLog.put("content", "已出WA库!");
				} else if ("180".equals(status)) {
					status = "60";
					paramMap.put("wa_in_time", waintime);
					paramsLog.put("content", "已入WA库");
				}
				paramMap.put("flow_flag", status);
				paramsLog.put("type", "20");
				count = purchaseVomOrderService.updateCrmReturnInfo(paramMap);

			} else if ("WD2".equals(prefix)) {//CGO

				Map<String, Object> paramMap = new HashMap<String, Object>();
				Map<String, Object> paramsLog = new HashMap<String, Object>();//订单履历
				if (orderNo.indexOf("T") > 0) {
					paramMap.put("order_id", orderNo.substring(0, orderNo.length() - 2));
					paramMap.put("wa_in_time", waintime);
					paramsLog.put("order_id", orderNo.substring(0, orderNo.length() - 4));
					paramsLog.put("sub_order_id", orderNo.substring(0, orderNo.length() - 2));
				} else {
					paramMap.put("order_id", orderNo);
					paramMap.put("wa_out_time", waouttime);
					paramsLog.put("order_id", orderNo.substring(0, orderNo.length() - 2));
					paramsLog.put("sub_order_id", orderNo);
				}
				paramMap.put("flow_flag", status);
				if ("150".equals(status)) {
					paramsLog.put("content", "已出WA库");
				} else if ("180".equals(status)) {
					paramsLog.put("content", "已入WA库");
				}
				paramsLog.put("type", "140");
				count = purchaseVomOrderService.updateLeaderReturnStatus(paramMap);
				if (count > 0) {
					//TODO 2QIlog 订单履历
					insertCommitLog(paramsLog);
				}

			} else if ("WD3".equals(prefix)) {//DBM
				Map<String, Object> paramMap = new HashMap<String, Object>();
				Map<String, Object> paramsLog = new HashMap<String, Object>();//订单履历
				if (orderNo.indexOf("T") > 0) {
					paramMap.put("order_id", orderNo.substring(0, orderNo.length() - 2));
					paramsLog.put("order_id", orderNo.substring(0, orderNo.length() - 4));
					paramsLog.put("sub_order_id", orderNo.substring(0, orderNo.length() - 2));
				} else {
					paramMap.put("order_id", orderNo);
					paramMap.put("wa_out_time", waouttime);
					paramsLog.put("order_id", orderNo.substring(0, orderNo.length() - 2));
					paramsLog.put("sub_order_id", orderNo);
				}
				if ("150".equals(status)) {
					paramsLog.put("content", "已出WA库");
				} else if ("180".equals(status)) {
					paramsLog.put("content", "已入WA库");
				}
				paramsLog.put("type", "140");
				paramMap.put("flow_flag", status);
				count = purchaseVomOrderService.updateLeaderReturnStatus(paramMap);
				if (count > 0) {
					//TODO 2QIlog 订单履历
					insertCommitLog(paramsLog);
				}

			} else {
				serviceResult.setResult(false);
				serviceResult.setMessage("没有符合要求的订单");
			}

			if (count == 0) {
				serviceResult.setResult(false);
				serviceResult.setMessage("更新记录为0");
			} else {
				serviceResult.setResult(true);
				serviceResult.setMessage("订单处理成功");
			}
		} catch (Exception ex) {
			logger.error("CBS出库状态更新失败：" + JsonUtil.toJson(params), ex);
			serviceResult.setSuccess(false);
			serviceResult.setMessage(ex.getMessage());
			serviceResult.setResult(false);
		}
		return serviceResult;
	}

	/**
	* 正品退货写入志
	* @param params
	* 
	*/
	private void insertCommitLog(Map<String, Object> params) {
		Map<String, Object> paramsLog = new HashMap<String, Object>();
		paramsLog.put("order_id", params.get("order_id"));
		paramsLog.put("sub_order_id", params.get("sub_order_id"));
		paramsLog.put("type", params.get("type"));
		paramsLog.put("content", params.get("content"));
		paramsLog.put("remark", "");
		paramsLog.put("operate_user", "自动执行Vom");
		paramsLog.put("system", "定时任务");
		paramsLog.put("is_sucess", "1");
		try {
			orderOperationLogService.createOrderOperationLog(paramsLog);
		} catch (Exception e) {
			logger.error("订单履历添加失败！", e);
		}

	}

	public ServiceResult<GoodsBackInfoResponse> getGoodsBackInfo(Map<String, Object> params) {
		ServiceResult<GoodsBackInfoResponse> serviceResult = new ServiceResult<GoodsBackInfoResponse>();
		GoodsBackInfoResponse goodsBackInfoResponse = new GoodsBackInfoResponse();
		String orderNo = (String) params.get("orderNo");
		String prefix = orderNo.substring(0, 3);
		try {
			if ("WD1".equals(prefix)) {//CRM
				Map<String, Object> paramMap = new HashMap<String, Object>();
				if (orderNo.indexOf("T") > 0) {
					paramMap.put("order_id", orderNo.substring(0, orderNo.length() - 2));
				} else {
					paramMap.put("order_id", orderNo);
				}
				goodsBackInfoResponse = purchaseVomOrderService.findCrmGoodsBackInfo(paramMap);
				if (goodsBackInfoResponse == null) {
					serviceResult.setResult(null);
				} else {
					goodsBackInfoResponse.setType("1");
					serviceResult.setResult(goodsBackInfoResponse);
				}
				serviceResult.setMessage("订单处理成功");
			} else if ("WD2".equals(prefix)) {//CGO
				Map<String, Object> paramMap = new HashMap<String, Object>();
				if (orderNo.indexOf("T") > 0) {
					paramMap.put("order_id", orderNo.substring(0, orderNo.length() - 2));
				} else {
					paramMap.put("order_id", orderNo);
				}
				goodsBackInfoResponse = purchaseVomOrderService.findGoodsBackInfo(paramMap);
				if (goodsBackInfoResponse == null) {
					serviceResult.setResult(null);
				} else {
					goodsBackInfoResponse.setType("2");
					serviceResult.setResult(goodsBackInfoResponse);
				}
				serviceResult.setMessage("订单处理成功");
			} else if ("WD3".equals(prefix)) {//DBM
				Map<String, Object> paramMap = new HashMap<String, Object>();
				if (orderNo.indexOf("T") > 0) {
					paramMap.put("order_id", orderNo.substring(0, orderNo.length() - 2));
				} else {
					paramMap.put("order_id", orderNo);
				}
				goodsBackInfoResponse = purchaseVomOrderService.findGoodsBackInfo(paramMap);
				if (goodsBackInfoResponse == null) {
					serviceResult.setResult(null);
				} else {
					goodsBackInfoResponse.setType("2");
					serviceResult.setResult(goodsBackInfoResponse);
				}
				serviceResult.setMessage("订单处理成功");
			} else {
				serviceResult.setResult(null);
				serviceResult.setMessage("没有符合要求的订单");
			}
		} catch (Exception ex) {
			logger.error("参数：" + JsonUtil.toJson(params), ex);
			serviceResult.setSuccess(false);
			serviceResult.setMessage(ex.getMessage());
			serviceResult.setResult(null);
		}
		return serviceResult;
	}

	/**
	 * 正品退货终止退货订单
	 * @param params
	 * @return
	 */
	/**
	 * 正品退货终止退货订单
	 * @param params
	 * @return
	 */
	public Boolean stopCgoGenuineRejectList(Map<String, Object> params) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			List<Map<String, Object>> successMapList = (List<Map<String, Object>>) params
				.get("successMap");
			if (successMapList != null && !successMapList.isEmpty()) {
				for (Map<String, Object> map : successMapList) {
					purchaseVomOrderService.updateCgoGenuineRejectStatusVom(map);
				}
			}
			transactionManager.commit(status);
		} catch (Exception ex) {
			transactionManager.rollback(status);
			logger.error("[VOMOrderModel][stopCgoGenuineRejectList]:订单终止退货失败:", ex);
			return false;
		}
		return true;
	}

	/**
	 * 正品退货终止退货订单
	 * @param params
	 * @return
	 */
	public Boolean stopCrmGenuineRejectList(Map<String, Object> params) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			List<Map<String, Object>> successMapList = (List<Map<String, Object>>) params
				.get("successMap");
			if (successMapList != null && !successMapList.isEmpty()) {
				for (Map<String, Object> map : successMapList) {
					purchaseVomOrderService.updateCrmGenuineRejectStatus(map);
				}
			}
			transactionManager.commit(status);
		} catch (Exception ex) {
			transactionManager.rollback(status);
			logger.error("[VOMOrderModel][stopCrmGenuineRejectList]:订单终止退货失败:", ex);
			return false;
		}
		return true;
	}
}
