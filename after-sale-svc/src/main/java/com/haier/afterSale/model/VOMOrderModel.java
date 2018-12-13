package com.haier.afterSale.model;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.haier.afterSale.util.AESUtil;
import com.haier.afterSale.util.CommUtil;
import com.haier.afterSale.util.DateFormatUtil;
import com.haier.afterSale.util.Httpclient;
import com.haier.afterSale.util.ObjectUtil;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.GoodsBackInfoResponse;
import com.haier.purchase.data.service.OrderOperationLogService;
import com.haier.purchase.data.service.PurchaseVomOrderService;
import com.haier.shop.model.VOMOrderResponse;
import com.haier.shop.model.VOMSynOrderRequire;
import com.haier.shop.model.VOMSynSubOrderRequire;
import com.haier.stock.model.VomInterData;
@Service
public class VOMOrderModel {
//	protected static final String VOM_SYN_ORDER_URL = "http://58.56.128.84:9001/EAI/service/VOM/CommonGetWayToVOM/CommonGetWayToVOM";
//	protected final String		 VOM_SYN_ORDER_URL = "/EAI/RoutingProxyService/EAI_REST_POST_ServiceRoot?INT_CODE=EAI_INT_1353";

	private Logger				 logger			   = LogManager.getLogger(VOMOrderModel.class);

	private String				 eaiUrl			   = null;
	@Autowired
	public PurchaseVomOrderService			 purchaseVomOrderService;
	@Autowired
	private OrderOperationLogService orderOperationLogService;
    @Value("${url.vomSynOrderEaiUrl}")
	private String vomUrl;
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
	 * 订单同步接口(正向|逆向)
	 * @param synOrderRequire
	 * @return
	 */
	public VOMOrderResponse synOrderInfo(VOMSynOrderRequire synOrderRequire) {

		String contentXml = getVomContentXml(synOrderRequire);
		logger.info("-------------------调用VOM逆向入库接口contentXml：" + contentXml);
		StringBuffer sb = new StringBuffer();
		sb.append("CBS").append(DateFormatUtil.getCurrentDateWithFormat("yyyyMMddHHmmss")).append(CommUtil.getRandomString(8));
		String interXml;
		VOMOrderResponse vomOrderResponse = new VOMOrderResponse();
		try {
			interXml = getVomInterXml(contentXml, sb.toString());
			logger.info("-------------------调用VOM逆向入库接口interXml：" + interXml);
			String responseStr = Httpclient.send(vomUrl, interXml,"text/html; charset=utf-8");
			logger.info("-------------------调用VOM逆向入库接口返回参数responseStr：" + responseStr);
			vomOrderResponse = getVomRequestResult(responseStr);
		} catch (Exception e) {
			logger.error("", e);
			vomOrderResponse.setMsg(e.getMessage());
			vomOrderResponse.setFlag("F");
		}
		return vomOrderResponse;
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
//		sb.append("<expno></expno>");// 快递单号
        sb.append("<orderdate>" + vomSynOrderRequire.getOrderDate() + "</orderdate>");// 订单时间（格式：YYYY-MM-DD HH:MM:SS）
        sb.append("<storecode>" + vomSynOrderRequire.getStoreCode() + "</storecode>");// scode转
        // 中心代码
        // 仓库编码：按日日顺C码
        sb.append("<province>" + vomSynOrderRequire.getProvince() + "</province>");// 收货人所在省
        sb.append("<city>" + vomSynOrderRequire.getCity() + "</city>");// 收货人所在市
        sb.append("<county>" + vomSynOrderRequire.getCounty() + "</county>");// 收货人所在县/区
//		sb.append("<addr><![CDATA[" + vomSynOrderRequire.getAddr() + "]]></addr>");// 详细地址
        sb.append("<addr>" + vomSynOrderRequire.getAddr() + "</addr>");// 详细地址
        sb.append("<gbcode>" + vomSynOrderRequire.getGbCode() + "</gbcode>");// 国标码
//		sb.append("<name><![CDATA[" + vomSynOrderRequire.getName() + "]]></name>");// 送达方姓名：收货人姓名
        sb.append("<name>" + vomSynOrderRequire.getName() + "</name>");// 送达方姓名：收货人姓名
        sb.append("<mobile>" + vomSynOrderRequire.getMobile() + "</mobile>");// 联系电话
        sb.append("<tel>" + vomSynOrderRequire.getTel() + "</tel>");// 固定电话
        sb.append("<postcode>" + vomSynOrderRequire.getPostCode() + "</postcode>");// 邮政编码
//		sb.append("<deldate>" + vomSynOrderRequire.getDelDate() + "</deldate>");// 预约时间：预约送货时间
//		sb.append("<insdate>" + vomSynOrderRequire.getInsDate() + "</insdate>");// 预约时间：预约安装时间
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
//			sb.append("<invrise><![CDATA[" + vomSynOrderRequire.getInvRise() + "]]></invrise>");// 发票抬头
            sb.append("<invrise>" + vomSynOrderRequire.getInvRise() + "</invrise>");// 发票抬头
        } else {
            sb.append("<invrise></invrise>");
        }
//		sb.append("<taxbearer>" + vomSynOrderRequire.getTaxBearer() + "</taxbearer>");// 纳税人登记号
//		if (!"".equals(vomSynOrderRequire.getRecAddr())
//			&& vomSynOrderRequire.getRecAddr() != null) {
////			sb.append("<recaddr><![CDATA[" + vomSynOrderRequire.getRecAddr() + "]]></recaddr>");// 发票地址
//			sb.append("<recaddr>" + vomSynOrderRequire.getRecAddr() + "</recaddr>");// 发票地址
//		} else {
//			sb.append("<recaddr></recaddr>");
//		}
//		sb.append("<recacc>" + vomSynOrderRequire.getRecAcc() + "</recacc>");// 发票开户行帐号
//		if (!"".equals(vomSynOrderRequire.getRecBank())
//			&& vomSynOrderRequire.getRecBank() != null) {
////			sb.append("<recbank><![CDATA[" + vomSynOrderRequire.getRecBank() + "]]></recbank>");// 发票开户行
//			sb.append("<recbank>" + vomSynOrderRequire.getRecBank() + "</recbank>");// 发票开户行
//		} else {
//			sb.append("<recbank></recbank>");
//		}
//		sb.append("<sname>" + vomSynOrderRequire.getSname() + "</sname>");// 发货人姓名
//		sb.append("<sprovince>" + vomSynOrderRequire.getSprovince() + "</sprovince>");// 发货人所在省
//		sb.append("<scity>" + vomSynOrderRequire.getScity() + "</scity>");// 发货人所在市
//		sb.append("<scounty>" + vomSynOrderRequire.getScounty() + "</scounty>");// 发货人所在县/区
//		sb.append("<saddr>" + vomSynOrderRequire.getSaddr() + "</saddr>");// 发货人详细地址
//		sb.append("<smobile>" + vomSynOrderRequire.getSmobile() + "</smobile>");// 发货人联系电话
//		sb.append("<stel>" + vomSynOrderRequire.getStel() + "</stel>");// 发货人固定电话
//		sb.append("<busflag>" + vomSynOrderRequire.getBusFlag() + "</busflag>");// 订单标记 1送装一体 2.只配送 3.开箱验货

        if (!"".equals(vomSynOrderRequire.getRemark()) && vomSynOrderRequire.getRemark() != null) {
//			sb.append("<remark><![CDATA[" + vomSynOrderRequire.getRemark() + "]]></remark>");// 备注
            sb.append("<remark>" + vomSynOrderRequire.getRemark() + "</remark>");// 备注
        } else {
            sb.append("<remark></remark>");
        }
        sb.append("<attributes><shcode>"+vomSynOrderRequire.getNetPointCode()+"</shcode></attributes>");// 属性备注 86码
        sb.append("<remark1>HB</remark1>");// 备用字段 --不填
//		sb.append("<remark2></remark2>");// 备用字段 --不填
//		sb.append("<remark3></remark3>");// 备用字段 --不填
//		sb.append("<remark4></remark4>");// 备用字段 --不填
//		sb.append("<remark5></remark5>");// 备用字段 --不填
//		sb.append("<remark6></remark6>");// 备用字段 --不填
//		sb.append("<remark7></remark7>");// 备用字段 --不填
        sb.append("<remark8>1</remark8>");// 备用字段 --不填

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

	public static String getVomInterXml(String content, String notifyId) throws Exception {
		VomInterData vomInterData = new VomInterData();
		List<VomInterData> list = new ArrayList<VomInterData>();
		// notifyId
		vomInterData.setNotifyid(notifyId);
		// notifyTime
		String dateStr = DateFormatUtil.getCurrentDateWithFormat("yyyy-MM-dd HH:mm:ss");
		vomInterData.setNotifytime(dateStr);
		// butype
		vomInterData.setButype("rrs_order");
		// content
		String aesContent = AESUtil.encrypt(content,"KeLy8g7qjmnbgWP1").replaceAll("\n", "");
//		String aesContent = AESUtil.Decrypt(content, "KeLy8g7qjmnbgWP1").replaceAll("\n", "");
		String encodeContent = URLEncoder.encode(aesContent, "utf-8");
		vomInterData.setContent(encodeContent);
		// sign
		//String md5Str = MD5util.encrypt(content + "RRS,123 ");//测试秘钥：Haier,123     生产环境秘钥：RRS,123
	//	String base64Str = Base64Util.encode(md5Str.getBytes()).replaceAll("\r\n", "");

		String base64Str=Base64.encodeBase64String(DigestUtils.md5Hex(content + "RRS,123").getBytes());
		//String base64Str=Base64.encodeBase64String(DigestUtils.md5Hex("12345678日日顺").getBytes());
		//String xxx=DigestUtils.md5Hex("12345678日日顺");
		vomInterData.setSign(base64Str);
		// 接口方法名
		vomInterData.setButype("rrs_order");
		// 根据系统区分来源
		vomInterData.setSource("HAIERSC");
		// 报文格式
		vomInterData.setType("xml");

		list.add(vomInterData);

		return ObjectUtil.getObjectToURLString(list);

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


}
