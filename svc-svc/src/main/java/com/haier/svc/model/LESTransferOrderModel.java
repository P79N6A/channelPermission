package com.haier.svc.model;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import com.haier.purchase.data.model.*;
import com.haier.purchase.data.service.PurchaseLesRRSOutService;
import com.haier.purchase.data.service.PurchaseLesStockInfoService;
import com.haier.purchase.data.model.SIOUInfoItem;
import com.haier.svc.bean.gettidanzwdfromlestoehaier.ZWDTABLE2;

import com.haier.svc.purchase.createscordertoles.CreateSCOrderToLESResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.svc.bean.CgoGenuineRejectItem;
import com.haier.svc.bean.CrmGenuineRejectItem;
import com.haier.svc.bean.DbmGenuineRejectItem;
import com.haier.svc.bean.LESOutRRSLedingBillTimeResponse;
import com.haier.svc.bean.LESOutRRSLedingBillTimeSubResponse;
import com.haier.svc.bean.LESTransferOutInPutOrderRequire;
import com.haier.svc.bean.LESTransferOutInPutOrderResponse;
import com.haier.svc.bean.LESTransferOutInPutOrderSubResponse;
import com.haier.svc.bean.ZBSTKD;
import com.haier.svc.bean.gettidanzwdfromlestoehaier.GetTidanZWDFromLESToEHAIER;
import com.haier.svc.bean.gettidanzwdfromlestoehaier.GetTidanZWDFromLESToEHAIER_Service;
import com.haier.svc.bean.getucunioninfofromles.GetKUCUNInfoFromLESToEHAIER;
import com.haier.svc.bean.getucunioninfofromles.GetKUCUNInfoFromLESToEHAIERResponseStockQty;
import com.haier.svc.bean.getucunioninfofromles.GetKUCUNInfoFromLESToEHAIERResponseStockTrans;
import com.haier.svc.bean.getucunioninfofromles.GetKUCUNInfoFromLESToEHAIER_Service;
import com.haier.svc.purchase.canceltidan.JdeAndLesLoseValidity;
import com.haier.svc.purchase.canceltidan.JdeAndLesLoseValidity_Service;
import com.haier.svc.purchase.createscordertoles.CreateSCOrderToLES;
import com.haier.svc.purchase.createscordertoles.CreateSCOrderToLES_Service;
import com.haier.svc.purchase.createscordertoles.InputType;
import com.haier.svc.purchase.createscordertoles.OutputType;
import com.haier.svc.purchase.querydninfofromlestoehaier.QueryDNinfoFromLEStoEhaier;
import com.haier.svc.purchase.querydninfofromlestoehaier.QueryDNinfoFromLEStoEhaier_Service;
import com.haier.svc.purchase.querydninfofromlestoehaier.ZBSTKDWD;
import com.haier.svc.service.ItemService;
import com.haier.svc.service.PurchaseBaseCommonService;
import com.haier.svc.purchase.canceltidan.JdeAndLesLoseValidity_Type.In;
import com.haier.svc.purchase.canceltidan.JdeAndLesLoseValidityResponse.Out;

@Service("lesTransferOrderModel")
public class LESTransferOrderModel {
	private static org.apache.log4j.Logger log				   = org.apache.log4j.LogManager
		.getLogger(LESTransferOrderModel.class);
	// TODO 待修改
	protected static final String		   TRANSFER_TO_LES_URL = "/EAI/RoutingProxyService/EAI_REST_POST_ServiceRoot?INT_CODE=CRM_INT_OMS_4";

	private String						   eaiUrl			   = "http://58.56.128.84:9001";
	// EAI iP
	@Value("${t2OrderResponse.location}")
	private String						   wsdlLocation;

	@Autowired
	private ItemService itemService;
	@Autowired
	private PurchaseBaseCommonService purchaseBaseCommonService;

	@Autowired
	private PurchaseLesStockInfoService purchaseLesStockInfoService;
	@Autowired
	private PurchaseLesRRSOutService purchaseLesRRSOutService;

	@SuppressWarnings("unchecked")
	public LESTransferOutInPutOrderResponse QueryTransferOutInPutOrder(LESTransferOutInPutOrderRequire order) throws Exception {

		String url = eaiUrl + TRANSFER_TO_LES_URL;
		// String resultMsg = WebCommonUtil.PostMessage(url,
		// order.toXMLMessage());
		String resultMsg = "" + "<PARAMETER>" + "<DETAIL>" + "<FLAG>S</FLAG>"
						   + "<MESSAGE>test</MESSAGE>" + "<ZFLAG1>1</ZFLAG1>" + "<SubRecords>"
						   + "<LESTransferOutInPutOrderSubResponse>" + "<BSTKD>OderNum1</BSTKD>"
						   + "<FLAG_RK>C</FLAG_RK>" + "<ERDAT_RK>2014-07-11</ERDAT_RK>"
						   + "<ERZET_RK>2014-07-11 12:31:34</ERZET_RK>" + "<FLAG_CK>C</FLAG_CK>"
						   + "<ERDAT_CK>2014-07-12</ERDAT_CK>"
						   + "<ERZET_CK>2014-07-12 22:32:22</ERZET_CK>" + "<ADD1>1</ADD1>"
						   + "<ADD2>1</ADD2>" + "<ADD3>1</ADD3>" + "<ADD4>1</ADD4>"
						   + "</LESTransferOutInPutOrderSubResponse>"
						   + "<LESTransferOutInPutOrderSubResponse>" + "<BSTKD>OderNum2</BSTKD>"
						   + "<FLAG_RK>C</FLAG_RK>" + "<ERDAT_RK>2014-07-12</ERDAT_RK>"
						   + "<ERZET_RK>2014-07-12 12:31:34</ERZET_RK>" + "<FLAG_CK>C</FLAG_CK>"
						   + "<ERDAT_CK>2014-07-22</ERDAT_CK>"
						   + "<ERZET_CK>2014-07-22 22:32:22</ERZET_CK>" + "<ADD1>2</ADD1>"
						   + "<ADD2>2</ADD2>" + "<ADD3>2</ADD3>" + "<ADD4>2</ADD4>"
						   + "</LESTransferOutInPutOrderSubResponse>"
						   + "<LESTransferOutInPutOrderSubResponse>" + "<BSTKD>OderNum3</BSTKD>"
						   + "<FLAG_RK>C</FLAG_RK>" + "<ERDAT_RK>2014-07-13</ERDAT_RK>"
						   + "<ERZET_RK>2014-07-13 12:31:34</ERZET_RK>" + "<FLAG_CK>C</FLAG_CK>"
						   + "<ERDAT_CK>2014-07-23</ERDAT_CK>"
						   + "<ERZET_CK>2014-07-23 22:32:22</ERZET_CK>" + "<ADD1>3</ADD1>"
						   + "<ADD2>3</ADD2>" + "<ADD3>3</ADD3>" + "<ADD4>3</ADD4>"
						   + "</LESTransferOutInPutOrderSubResponse>" + "</SubRecords>"
						   + "</DETAIL>" + "</PARAMETER>";
		LESTransferOutInPutOrderResponse result = new LESTransferOutInPutOrderResponse();
		Document doc = DocumentHelper.parseText(resultMsg);
		Element element = (Element) doc.getRootElement().elements().get(0);
		List<Element> list = element.elements();
		List<LESTransferOutInPutOrderSubResponse> subRecords = new ArrayList<LESTransferOutInPutOrderSubResponse>();
		LESTransferOutInPutOrderSubResponse subR = null;
		for (Element el : list) {
			if (el.getName().equals("FLAG")) {
				result.setFLAG(el.getText());
			} else if (el.getName().equals("MESSAGE")) {
				result.setMESSAGE(el.getText());
			} else if (el.getName().equals("ZFLAG1")) {
				result.setZFLAG1(el.getText());
			} else { // TODO 根据接口提供数据会改
				List<Element> element0 = el.elements();
				//System.out.println("element0:" + element0.size());
				//System.out.println("element0:" + element0.get(0).elements());
				for (int i = 0; i < element0.size(); i++) {
					List<Element> eList = element0.get(i).elements();
					subR = new LESTransferOutInPutOrderSubResponse();
					for (Element el0 : eList) {
						if (el0.getName().equals("BSTKD")) {
							//System.out.println("BSTKD:" + el0.getText());
							subR.setBSTKD(el0.getText());
						} else if (el0.getName().equals("FLAG_RK")) {
							//System.out.println("FLAG_RK:" + el0.getText());
							subR.setFLAG_RK(el0.getText());
						} else if (el0.getName().equals("ERDAT_RK")) {
							//System.out.println("ERDAT_RK:" + el0.getText());
							subR.setERDAT_RK(el0.getText());
						} else if (el0.getName().equals("ERZET_RK")) {
							//System.out.println("ERZET_RK:" + el0.getText());
							subR.setERZET_RK(el0.getText());
						} else if (el0.getName().equals("FLAG_CK")) {
							//System.out.println("FLAG_CK:" + el0.getText());
							subR.setFLAG_CK(el0.getText());
						} else if (el0.getName().equals("ERDAT_CK")) {
							//System.out.println("ZFLAG1:" + el0.getText());
							subR.setERDAT_CK(el0.getText());
						} else if (el0.getName().equals("ERZET_CK")) {
							//System.out.println("ZFLAG1:" + el0.getText());
							subR.setERZET_CK(el0.getText());
						} else if (el0.getName().equals("AD1")) {
							//System.out.println("AD1:" + el0.getText());
							subR.setAD1(el0.getText());
						} else if (el0.getName().equals("AD2")) {
							//System.out.println("AD2:" + el0.getText());
							subR.setAD2(el0.getText());
						} else if (el0.getName().equals("AD3")) {
							//System.out.println("AD3:" + el0.getText());
							subR.setAD3(el0.getText());
						} else if (el0.getName().equals("AD4")) {
							//System.out.println("AD4:" + el0.getText());
							subR.setAD4(el0.getText());
						}
					}
					subRecords.add(subR);
				}
				result.setSubRecords(subRecords);
			}
		}

		return result;
	}

	/**
	 * 从LES获取交易数据和库存数据
	 * 
	 * @param dateBegin
	 * @param dateEnd
	 * @param timeBegin
	 * @param timeEnd
	 * @param secType
	 */
	public ServiceResult<Calendar> getInventoryTranFromLes(String dateBegin, String dateEnd,
														  String timeBegin, String timeEnd,
														  String secType) {
		ServiceResult<Calendar> result = new ServiceResult<Calendar>();
		Holder<String> flag = new Holder<String>();
		Holder<String> message = new Holder<String>();
		Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans>> responseStockTrans = new Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans>>();
		Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockQty>> responseStockQtys = new Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockQty>>();
		// 调用接口
//		String path = "file:" + this.getClass()
//			.getResource(wsdlLocation + "/GetKUCUNInfoFromLESToEHAIER.wsdl").getPath();
//		URL url;
		boolean success = false;
		try {
//			url = new URL(path);
			URL url = this.getClass().getResource(
					wsdlLocation + "/GetKUCUNInfoFromLESToEHAIER.wsdl");
			GetKUCUNInfoFromLESToEHAIER_Service service = new GetKUCUNInfoFromLESToEHAIER_Service(
				url);
			GetKUCUNInfoFromLESToEHAIER soap = service.getGetKUCUNInfoFromLESToEHAIERSOAP();
			String crk = "";

			soap.getKUCUNInfoFromLESToEHAIER(crk, dateBegin, dateEnd, secType, timeBegin, timeEnd,
				flag, message, responseStockTrans, responseStockQtys);
			success = true;
			Calendar response_cal = Calendar.getInstance();
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());
			response_cal.set(Calendar.YEAR, now.get(Calendar.YEAR));
			response_cal.set(Calendar.MONTH, now.get(Calendar.MONTH));
			response_cal.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
			response_cal.set(Calendar.HOUR, now.get(Calendar.HOUR));
			response_cal.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
			result.setResult(response_cal);
		} catch (Exception e) {
			log.error("从从LES获取交易数据和库存数据时，发生未知异常", e);
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
		if (flag.value.equalsIgnoreCase("0") && success) {
			success = true;
			// 数据库操作
			for (GetKUCUNInfoFromLESToEHAIERResponseStockTrans info : responseStockTrans.value) {
				try {
					GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity entity = new GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity();
					BeanUtils.copyProperties(info,entity);
					if (purchaseLesStockInfoService.selectInOutInfo(entity) > 0)
						purchaseLesStockInfoService.updateInOutInfo(entity);
					else
						purchaseLesStockInfoService.insertInOutInfo(entity);
					if(info.getLGORT()!=null){
					   if (info.getLGORT().indexOf("WA")>=0){
					       log.info("插入(更新)les成功："+info);
					   }
					}
				} catch (Exception ex) {
					success = false;
					log.error("插入(更新)les失败"+info, ex);
					result.setSuccess(false);
					result.setMessage("插入(更新)les失败"+ex.getMessage());
				}
			}

			for (GetKUCUNInfoFromLESToEHAIERResponseStockQty info : responseStockQtys.value) {
				try {
					GetKUCUNInfoFromLESToEHAIERResponseStockQtyEntity entity = new GetKUCUNInfoFromLESToEHAIERResponseStockQtyEntity();
					BeanUtils.copyProperties(info,entity);
					if (purchaseLesStockInfoService.selectStockInfo(entity) > 0)
						purchaseLesStockInfoService.updateStockInfo(entity);
					else
						purchaseLesStockInfoService.insertStockInfo(entity);
					    if(info.getLGORT()!=null){
	                       if (info.getLGORT().indexOf("WA")>=0){
	                           log.info("插入(更新)les成功："+info);
	                       }
	                    }
				} catch (Exception ex) {
					//success = false;
					log.error("插入(更新)les失败"+info, ex);
					result.setSuccess(false);
					result.setMessage("插入(更新)les失败"+ex.getMessage());
				}
			}
			//result.setResult(success);

			// TODO 此处需要添加LES最后同步时间
		} else {
			log.warn("从LES同步出入WA库数据失败:" + message.value);
		//	result.setResult(false);
			result.setSuccess(false);
			result.setMessage("接口读取数据失败");
		}
		return result;

	}



	/**
	 * 从LES获取交易数据和库存数据
	 * 取历史数据
	 * @param dateBegin
	 * @param dateEnd
	 * @param timeBegin
	 * @param timeEnd
	 * @param secType
	 */
	public ServiceResult<Calendar> getInventoryTranFromLesTemp(String dateBegin, String dateEnd,
														   String timeBegin, String timeEnd,
														   String secType) {
		ServiceResult<Calendar> result = new ServiceResult<Calendar>();
		Holder<String> flag = new Holder<String>();
		Holder<String> message = new Holder<String>();
		Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans>> responseStockTrans = new Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans>>();
		Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockQty>> responseStockQtys = new Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockQty>>();
		// 调用接口
//		String path = "file:" + this.getClass()
//				.getResource(wsdlLocation + "/GetKUCUNInfoFromLESToEHAIER.wsdl").getPath();
//		URL url;
		boolean success = false;
		try {
//			url = new URL(path);
			URL url = this.getClass().getResource(
					wsdlLocation + "/GetKUCUNInfoFromLESToEHAIER.wsdl");
			GetKUCUNInfoFromLESToEHAIER_Service service = new GetKUCUNInfoFromLESToEHAIER_Service(
					url);
			GetKUCUNInfoFromLESToEHAIER soap = service.getGetKUCUNInfoFromLESToEHAIERSOAP();
			String crk = "";
			soap.getKUCUNInfoFromLESToEHAIER(crk, dateBegin, dateEnd, secType, timeBegin, timeEnd,
					flag, message, responseStockTrans, responseStockQtys);
			success = true;
			Calendar response_cal = Calendar.getInstance();
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());
			response_cal.set(Calendar.YEAR, now.get(Calendar.YEAR));
			response_cal.set(Calendar.MONTH, now.get(Calendar.MONTH));
			response_cal.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
			response_cal.set(Calendar.HOUR, now.get(Calendar.HOUR));
			response_cal.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
			result.setResult(response_cal);
		} catch (Exception e) {
			log.error("从从LES获取交易数据和库存数据时，发生未知异常", e);
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			return result;
		}
		if (flag.value.equalsIgnoreCase("0") && success) {
			success = true;
			// 数据库操作
			for (GetKUCUNInfoFromLESToEHAIERResponseStockTrans info : responseStockTrans.value) {
				try {
					GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity entity = new GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity();
					BeanUtils.copyProperties(info,entity);
					if (purchaseLesStockInfoService.selectInOutInfo(entity) == 0) {
						purchaseLesStockInfoService.insertInOutInfo(entity);
						if(info.getLGORT()!=null){
							if (info.getLGORT().indexOf("WA")>=0){
								log.info("插入(更新)les成功："+info);
							}
						}
					}

				} catch (Exception ex) {
					success = false;
					log.error("插入(更新)les失败"+info, ex);
					result.setSuccess(false);
					result.setMessage("插入(更新)les失败"+ex.getMessage());
				}
			}

			for (GetKUCUNInfoFromLESToEHAIERResponseStockQty info : responseStockQtys.value) {
				try {
					GetKUCUNInfoFromLESToEHAIERResponseStockQtyEntity entiry = new GetKUCUNInfoFromLESToEHAIERResponseStockQtyEntity();
					if (purchaseLesStockInfoService.selectStockInfo(entiry) == 0) {
						purchaseLesStockInfoService.insertStockInfo(entiry);
						if(info.getLGORT()!=null){
							if (info.getLGORT().indexOf("WA")>=0){
								log.info("插入(更新)les成功："+info);
							}
						}
					}

				} catch (Exception ex) {
					log.error("插入(更新)les失败"+info, ex);
					result.setSuccess(false);
					result.setMessage("插入(更新)les失败"+ex.getMessage());
				}
			}

			// TODO 此处需要添加LES最后同步时间
		} else {
			log.warn("从LES同步出入WA库数据失败:" + message.value);
			result.setSuccess(false);
			result.setMessage("接口读取数据失败");
		}
		return result;

	}

	// 出日日顺时间、提单时间
	public LESOutRRSLedingBillTimeResponse GetTidanZWDFromLESToEHAIER(String erdat,
																	  List<ZBSTKD> input) {
		// ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		LESOutRRSLedingBillTimeResponse result = new LESOutRRSLedingBillTimeResponse();
		try {
//			String path = "file:" + this.getClass()
//				.getResource(wsdlLocation + "/GetTidanZWDFromLESToEHAIER.wsdl").getPath();
//			URL url = new URL(path);
			URL url = this.getClass().getResource(
					wsdlLocation + "/GetTidanZWDFromLESToEHAIER.wsdl");
			GetTidanZWDFromLESToEHAIER_Service service = new GetTidanZWDFromLESToEHAIER_Service(
				url);
			GetTidanZWDFromLESToEHAIER soap = service.getGetTidanZWDFromLESToEHAIERSOAP();
			Holder<String> flag = new Holder<String>();
			Holder<String> faultDetail = new Holder<String>();
			Holder<String> message = new Holder<String>();

			Holder<List<ZWDTABLE2>> tempoutput = new Holder<List<ZWDTABLE2>>();
			soap.getTidanZWDFromLESToEHAIER(erdat, input, flag, faultDetail, message, tempoutput);
			result.setFLAG(flag.value);
			result.setFaultDetail(faultDetail.value);
			result.setMESSAGE(message.value);
			List<LESOutRRSLedingBillTimeSubResponse> listRr = new ArrayList<LESOutRRSLedingBillTimeSubResponse>();
			List<ZWDTABLE2> listR = tempoutput.value;
			List<ZWDTABLEEntity> entityList = new ArrayList<ZWDTABLEEntity>();
			BeanUtils.copyProperties(listR,entityList);
			// System.out.println(JsonUtil.toJson(listR));
			if (listR != null && listR.size() > 0) {
				for (int i = 0; i < listR.size(); i++) {
					LESOutRRSLedingBillTimeSubResponse re = new LESOutRRSLedingBillTimeSubResponse();
					re.setBSTKD(listR.get(i).getBSTKD());
					re.setGVS_SO(listR.get(i).getGVSSO());
					re.setKUNNR(listR.get(i).getKUNNR());
					re.setKUNWE(listR.get(i).getKUNWE());
					re.setADD4(listR.get(i).getADD4());
					re.setERDAT(listR.get(i).getERDAT());
					re.setERZET(listR.get(i).getERZET());
					re.setAD1(listR.get(i).getAD1());
					re.setAD2(listR.get(i).getAD2());
					re.setAD3(listR.get(i).getAD3());
					listRr.add(re);
					if (purchaseLesRRSOutService.isExist(entityList.get(i)) == 0) {
						purchaseLesRRSOutService.insertOutInfo(entityList.get(i));
					}
				}
			}
			result.setSubRecords(listRr);
		} catch (Exception e) {
			log.error("发生未知异常", e);
			result.setMESSAGE(e.getMessage());
		}

		return result;
	}

	public LESTransferOutInPutOrderResponse QueryDNinfoFromLEStoEhaier(String zflag,
																	   List<String> bstkd) {
		LESTransferOutInPutOrderResponse result = new LESTransferOutInPutOrderResponse();

		try {
//			String path = "file:" + this.getClass()
//				.getResource(wsdlLocation + "/QueryDNinfoFromLEStoEhaier.wsdl").getPath();
//			URL url = new URL(path);
			URL url = this.getClass().getResource(
					wsdlLocation + "/QueryDNinfoFromLEStoEhaier.wsdl");
			QueryDNinfoFromLEStoEhaier_Service service = new QueryDNinfoFromLEStoEhaier_Service(
				url);
			QueryDNinfoFromLEStoEhaier soap = service.getQueryDNinfoFromLEStoEhaierSOAP();
			String sysName = "EHAIER";
			List<com.haier.svc.purchase.querydninfofromlestoehaier.ZBSTKD> input = new ArrayList<com.haier.svc.purchase.querydninfofromlestoehaier.ZBSTKD>();
			if (bstkd != null && bstkd.size() > 0) {
				for (int i = 0; i < bstkd.size(); i++) {
					com.haier.svc.purchase.querydninfofromlestoehaier.ZBSTKD obj = new com.haier.svc.purchase.querydninfofromlestoehaier.ZBSTKD();
					obj.setBSTKD(bstkd.get(i));
					input.add(obj);
				}
			}

			Holder<String> flag = new Holder<String>();
			Holder<String> message = new Holder<String>();
			Holder<String> faultDETAIL = new Holder<String>();
			Holder<String> zflag1 = new Holder<String>();
			Holder<List<ZBSTKDWD>> output = new Holder<List<ZBSTKDWD>>();
			soap.queryDNinfoFromLEStoEhaier(sysName, zflag, input, flag, message, faultDETAIL,
				zflag1, output);
			result.setFLAG(flag.value);
			result.setMESSAGE(message.value);
			result.setFaultDETAIL(faultDETAIL.value);
			result.setZFLAG1(zflag1.value);

			List<LESTransferOutInPutOrderSubResponse> rtnList = new ArrayList<LESTransferOutInPutOrderSubResponse>();
			List<ZBSTKDWD> tempList = output.value;
			if (tempList != null && tempList.size() > 0) {
				for (int i = 0; i < tempList.size(); i++) {
					LESTransferOutInPutOrderSubResponse tempObj = new LESTransferOutInPutOrderSubResponse();
					tempObj.setBSTKD(tempList.get(i).getBSTKD());
					tempObj.setFLAG_RK(tempList.get(i).getFLAGRK());
					tempObj.setERDAT_RK(tempList.get(i).getERDATRK());
					tempObj.setERZET_RK(tempList.get(i).getERZETRK());
					tempObj.setFLAG_CK(tempList.get(i).getFLAGCK());
					tempObj.setERDAT_CK(tempList.get(i).getERDATCK());
					tempObj.setERZET_CK(tempList.get(i).getERZETCK());
					tempObj.setAD1(tempList.get(i).getAD1());
					tempObj.setAD2(tempList.get(i).getAD2());
					tempObj.setAD3(tempList.get(i).getAD3());
					tempObj.setAD4(tempList.get(i).getAD4());
					rtnList.add(tempObj);
				}
			}

			result.setSubRecords(rtnList);
		} catch (Exception e) {
			log.error("发生未知异常", e);
			// System.out.println(e.getMessage());
			result.setMESSAGE(e.getMessage());
		}

		return result;
	}

	public List<CreateSCOrderToLESResponse> CreateSCOrderToLESMethod(List<CrmGenuineRejectItem> message) {
		List<CreateSCOrderToLESResponse> rtnList = new ArrayList<CreateSCOrderToLESResponse>();

		try {
//			String path = "file:" + this.getClass()
//				.getResource(wsdlLocation + "/CreateSCOrderToLES.wsdl").getPath();
//			URL url = new URL(path);
			URL url = this.getClass().getResource(
					wsdlLocation + "/CreateSCOrderToLES.wsdl");
			CreateSCOrderToLES_Service service = new CreateSCOrderToLES_Service(url);
			CreateSCOrderToLES soap = service.getCreateSCOrderToLESSOAP();

			List<InputType> input = new ArrayList<InputType>();
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
			String day = dayFormat.format(new Date());
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
			String time = timeFormat.format(new Date());
			if (message != null && message.size() > 0) {
				for (int i = 0; i < message.size(); i++) {
					InputType tempIn = new InputType();
					tempIn.setSOURCE("ZPTH");
					tempIn.setSOURCEEXT("正品退货");
					tempIn.setSOURCESN(message.get(i).getWp_order_id());
					tempIn.setBSTKD(message.get(i).getWp_order_id());
					tempIn.setPOSEX("10");

					tempIn.setAUDAT(day);
					tempIn.setAUTIM(time);

					tempIn.setAUART("ZBCC");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("sCode", message.get(i).getStorage_id());
					ServiceResult<List<LesFiveYardInfo>> yards = itemService
						.selectLesFiveYards(params);

					if (yards.getSuccess() && yards.getResult().size() > 0) {
						LesFiveYardInfo info = yards.getResult().get(0);
						tempIn.setKUNNR(info.getCenterCode());
						tempIn.setKUNWE(info.getFiveYard());
					} else {
						log.error("can not find fiveyardinfo from table LesFiveYards using sCode:"
								  + message.get(i).getStorage_id());
					}

					tempIn.setMATNR(message.get(i).getMaterials_id());
					tempIn.setCOMTYP(message.get(i).getBrand_name());
					tempIn.setKWMENG(new BigDecimal(message.get(i).getQuantity()));
					tempIn.setMEINS("TAI");
					tempIn.setLGORT(message.get(i).getStorage_id());
					tempIn.setCHARG("10");
					tempIn.setSDABW("70");
					tempIn.setAUGRU("");
					tempIn.setBSTKDE("");
					tempIn.setPOSNRE("");
					float taxInPrice = message.get(i).getTax_in_price();
					tempIn
						.setKBETR(new BigDecimal(taxInPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
					float price = taxInPrice * message.get(i).getQuantity();
					tempIn.setKWERT(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP));
					tempIn.setSHIPCO(new BigDecimal(0));
					tempIn.setKWERZ(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP));
					tempIn.setDHRXM(message.get(i).getConcat_person());
					tempIn.setDHRPH(message.get(i).getConcat_phone());
					ServiceResult<List<WAAddress>> waInfos = purchaseBaseCommonService
						.getAllWAAddressInfo(message.get(i).getStorage_id());
					if (waInfos.getResult().size() > 0) {
						WAAddress waInfo = waInfos.getResult().get(0);
						tempIn.setSHRMOB(waInfo.getMobilePhone());
						tempIn.setSHRXM(waInfo.getContact_crm());
						tempIn.setSHRTEL(waInfo.getPhone());
						tempIn.setPROV(waInfo.getProvince());

						tempIn.setCITY(waInfo.getCity());
						tempIn.setCOUNTY(waInfo.getCounty());
						tempIn.setADDRESS(waInfo.getAddress());
						tempIn.setPSTLZ(waInfo.getZipCode());
					}
					tempIn.setPAYSTE("P1");
					tempIn.setPAYTYP("退货");

					tempIn.setYDDAT("");
					tempIn.setYDTIME("");

					tempIn.setSDMEMO("");
					tempIn.setKUNEM("");

					tempIn.setKUNZ1("");
					tempIn.setSDAEM("");
					tempIn.setURLAB("");
					tempIn.setURMEMO("");
					tempIn.setINVO("N");
					tempIn.setINVTYP("");
					tempIn.setINVACC("");
					tempIn.setINVADD("");
					tempIn.setINVBANK("");
					tempIn.setINVNUM("");

					tempIn.setERDAT(day);
					tempIn.setERZET(time);

					tempIn.setADD1("");
					tempIn.setADD2("");
					tempIn.setADD3("");
					input.add(tempIn);
				}
			}
			// System.out.println(JsonUtil.toJson(input));
			log.info("createSCOrderToLES request:" + JsonUtil.toJson(input));
			List<OutputType> tempList = soap.createSCOrderToLES(input);
			if (tempList != null && tempList.size() > 0) {
				for (int i = 0; i < tempList.size(); i++) {
					CreateSCOrderToLESResponse tempObj = new CreateSCOrderToLESResponse();
					tempObj.setFLAG(tempList.get(i).getFLAG());
					tempObj.setVBELN(tempList.get(i).getVBELN());
					tempObj.setMESSAGE(tempList.get(i).getMESSAGE());
					tempObj.setBSTKD(tempList.get(i).getBSTKD());
					rtnList.add(tempObj);
				}
			}
			// System.out.println(JsonUtil.toJson(rtnList));
			log.info("createSCOrderToLES response:" + JsonUtil.toJson(rtnList));
		} catch (Exception e) {
			log.error("发生未知异常", e);
		}

		return rtnList;
	}

	public List<CreateSCOrderToLESResponse> CreateSCOrderToLESMethodByCgo(List<SIOUInfoItem> message,
																		  CgoGenuineRejectItem wdInfo) {
		List<CreateSCOrderToLESResponse> rtnList = new ArrayList<CreateSCOrderToLESResponse>();

		try {
//			String path = "file:" + this.getClass()
//				.getResource(wsdlLocation + "/CreateSCOrderToLES.wsdl").getPath();
//			URL url = new URL(path);
			URL url = this.getClass().getResource(
					wsdlLocation + "/CreateSCOrderToLES.wsdl");
			CreateSCOrderToLES_Service service = new CreateSCOrderToLES_Service(url);
			CreateSCOrderToLES soap = service.getCreateSCOrderToLESSOAP();

			List<InputType> input = new ArrayList<InputType>();
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
			String day = dayFormat.format(new Date());
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
			String time = timeFormat.format(new Date());
			if (message != null && message.size() > 0) {
				for (int i = 0; i < message.size(); i++) {
					InputType tempIn = new InputType();
					tempIn.setSOURCE("ZPTH");
					tempIn.setSOURCEEXT("正品退货");
					tempIn.setSOURCESN(message.get(i).getOrder_id());
					tempIn.setBSTKD(message.get(i).getOrder_id());
					tempIn.setPOSEX("10");

					tempIn.setAUDAT(day);
					tempIn.setAUTIM(time);

					tempIn.setAUART("ZBCC");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("sCode", wdInfo.getStorage_id());
					ServiceResult<List<LesFiveYardInfo>> yards = itemService
						.selectLesFiveYards(params);

					if (yards.getSuccess() && yards.getResult().size() > 0) {
						LesFiveYardInfo info = yards.getResult().get(0);
						tempIn.setKUNNR(info.getCenterCode());
						tempIn.setKUNWE(info.getFiveYard());
					} else {
						log.error("can not find fiveyardinfo from table LesFiveYards using sCode:"
								  + wdInfo.getStorage_id());
					}

					tempIn.setMATNR(message.get(i).getMaterials_id());
					tempIn.setCOMTYP(wdInfo.getBrand_name());
					tempIn.setKWMENG(new BigDecimal(wdInfo.getRequest_quantity()));
					tempIn.setMEINS("TAI");
					tempIn.setLGORT(wdInfo.getStorage_id());
					tempIn.setCHARG("10");
					tempIn.setSDABW("70");
					tempIn.setAUGRU("");
					tempIn.setBSTKDE("");
					tempIn.setPOSNRE("");
					// float taxInPrice =
					// message.get(i).getPrice().floatValue();
					// tempIn.setKBETR(new BigDecimal(taxInPrice)
					// .setScale(2, BigDecimal.ROUND_HALF_UP));
					if (wdInfo.getTax_in_price() != null) {
						float price = wdInfo.getTax_in_price();
						tempIn
							.setKWERT(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP));
						tempIn
							.setKWERZ(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP));
					} else {
						tempIn.setKWERT(new BigDecimal(0));
						tempIn.setKWERZ(new BigDecimal(0));
					}
					tempIn.setSHIPCO(new BigDecimal(0));
					tempIn.setDHRXM(wdInfo.getConcat_person());
					tempIn.setDHRPH(wdInfo.getConcat_phone());
					ServiceResult<List<WAAddress>> waInfos = purchaseBaseCommonService
						.getAllWAAddressInfo(wdInfo.getStorage_id());
					if (waInfos.getResult().size() > 0) {
						WAAddress waInfo = waInfos.getResult().get(0);
						tempIn.setSHRMOB(waInfo.getMobilePhone());
						tempIn.setSHRXM(waInfo.getContact_cgodbm());
						tempIn.setSHRTEL(waInfo.getPhone());
						tempIn.setPROV(waInfo.getProvince());

						tempIn.setCITY(waInfo.getCity());
						tempIn.setCOUNTY(waInfo.getCounty());
						tempIn.setADDRESS(waInfo.getAddress());
						tempIn.setPSTLZ(waInfo.getZipCode());
					}
					tempIn.setPAYSTE("P1");
					tempIn.setPAYTYP("退货");

					tempIn.setYDDAT("");
					tempIn.setYDTIME("");

					tempIn.setSDMEMO("");
					tempIn.setKUNEM("");

					tempIn.setKUNZ1("");
					tempIn.setSDAEM("");
					tempIn.setURLAB("");
					tempIn.setURMEMO("");
					tempIn.setINVO("N");
					tempIn.setINVTYP("");
					tempIn.setINVACC("");
					tempIn.setINVADD("");
					tempIn.setINVBANK("");
					tempIn.setINVNUM("");

					tempIn.setERDAT(day);
					tempIn.setERZET(time);

					tempIn.setADD1("");
					tempIn.setADD2("");
					tempIn.setADD3("");
					input.add(tempIn);
				}
			}
			// System.out.println(JsonUtil.toJson(input));
			log.info("createSCOrderToLES request:" + JsonUtil.toJson(input));
			List<OutputType> tempList = soap.createSCOrderToLES(input);
			if (tempList != null && tempList.size() > 0) {
				for (int i = 0; i < tempList.size(); i++) {
					CreateSCOrderToLESResponse tempObj = new CreateSCOrderToLESResponse();
					tempObj.setFLAG(tempList.get(i).getFLAG());
					tempObj.setVBELN(tempList.get(i).getVBELN());
					tempObj.setMESSAGE(tempList.get(i).getMESSAGE());
					tempObj.setBSTKD(tempList.get(i).getBSTKD());
					rtnList.add(tempObj);
				}
			}
			// System.out.println(JsonUtil.toJson(rtnList));
			log.debug("createSCOrderToLES response:" + JsonUtil.toJson(rtnList));
		} catch (Exception e) {
			log.error("发生未知异常", e);
		}

		return rtnList;
	}

	public List<CreateSCOrderToLESResponse> CreateSCOrderToLESMethodByDbm(List<SIOUInfoItem> message,
																		  DbmGenuineRejectItem wdInfo) {
		List<CreateSCOrderToLESResponse> rtnList = new ArrayList<CreateSCOrderToLESResponse>();

		try {
//			String path = "file:" + this.getClass()
//				.getResource(wsdlLocation + "/CreateSCOrderToLES.wsdl").getPath();
//			URL url = new URL(path);
			URL url = this.getClass().getResource(
					wsdlLocation + "/CreateSCOrderToLES.wsdl");
			CreateSCOrderToLES_Service service = new CreateSCOrderToLES_Service(url);
			CreateSCOrderToLES soap = service.getCreateSCOrderToLESSOAP();

			List<InputType> input = new ArrayList<InputType>();
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
			String day = dayFormat.format(new Date());
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
			String time = timeFormat.format(new Date());
			if (message != null && message.size() > 0) {
				for (int i = 0; i < message.size(); i++) {
					InputType tempIn = new InputType();
					tempIn.setSOURCE("ZPTH");
					tempIn.setSOURCEEXT("正品退货");
					tempIn.setSOURCESN(message.get(i).getOrder_id());
					tempIn.setBSTKD(message.get(i).getOrder_id());
					tempIn.setPOSEX("10");

					tempIn.setAUDAT(day);
					tempIn.setAUTIM(time);

					tempIn.setAUART("ZBCC");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("sCode", wdInfo.getStorage_id());
					ServiceResult<List<LesFiveYardInfo>> yards = itemService
						.selectLesFiveYards(params);

					if (yards.getSuccess() && yards.getResult().size() > 0) {
						LesFiveYardInfo info = yards.getResult().get(0);
						tempIn.setKUNNR(info.getCenterCode());
						tempIn.setKUNWE(info.getFiveYard());
					} else {
						log.error("can not find fiveyardinfo from table LesFiveYards using sCode:"
								  + wdInfo.getStorage_id());
					}

					tempIn.setMATNR(message.get(i).getMaterials_id());
					tempIn.setCOMTYP(wdInfo.getBrand_name());
					tempIn.setKWMENG(new BigDecimal(wdInfo.getRequest_quantity()));
					tempIn.setMEINS("TAI");
					tempIn.setLGORT(wdInfo.getStorage_id());
					tempIn.setCHARG("10");
					tempIn.setSDABW("70");
					tempIn.setAUGRU("");
					tempIn.setBSTKDE("");
					tempIn.setPOSNRE("");
					// float taxInPrice =
					// message.get(i).getPrice().floatValue();
					// tempIn.setKBETR(new BigDecimal(taxInPrice)
					// .setScale(2, BigDecimal.ROUND_HALF_UP));
					float price = wdInfo.getTax_in_price();
					tempIn.setKWERT(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP));
					tempIn.setSHIPCO(new BigDecimal(0));
					tempIn.setKWERZ(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP));
					tempIn.setDHRXM(wdInfo.getConcat_person());
					tempIn.setDHRPH(wdInfo.getConcat_phone());
					ServiceResult<List<WAAddress>> waInfos = purchaseBaseCommonService
						.getAllWAAddressInfo(wdInfo.getStorage_id());
					if (waInfos.getResult().size() > 0) {
						WAAddress waInfo = waInfos.getResult().get(0);
						tempIn.setSHRMOB(waInfo.getMobilePhone());
						tempIn.setSHRXM(waInfo.getContact_cgodbm());
						tempIn.setSHRTEL(waInfo.getPhone());
						tempIn.setPROV(waInfo.getProvince());

						tempIn.setCITY(waInfo.getCity());
						tempIn.setCOUNTY(waInfo.getCounty());
						tempIn.setADDRESS(waInfo.getAddress());
						tempIn.setPSTLZ(waInfo.getZipCode());
					}
					tempIn.setPAYSTE("P1");
					tempIn.setPAYTYP("退货");

					tempIn.setYDDAT("");
					tempIn.setYDTIME("");

					tempIn.setSDMEMO("");
					tempIn.setKUNEM("");

					tempIn.setKUNZ1("");
					tempIn.setSDAEM("");
					tempIn.setURLAB("");
					tempIn.setURMEMO("");
					tempIn.setINVO("N");
					tempIn.setINVTYP("");
					tempIn.setINVACC("");
					tempIn.setINVADD("");
					tempIn.setINVBANK("");
					tempIn.setINVNUM("");

					tempIn.setERDAT(day);
					tempIn.setERZET(time);

					tempIn.setADD1("");
					tempIn.setADD2("");
					tempIn.setADD3("");
					input.add(tempIn);
				}
			}
			// System.out.println(JsonUtil.toJson(input));
			log.info("createSCOrderToLES request:" + JsonUtil.toJson(input));
			List<OutputType> tempList = soap.createSCOrderToLES(input);
			if (tempList != null && tempList.size() > 0) {
				for (int i = 0; i < tempList.size(); i++) {
					CreateSCOrderToLESResponse tempObj = new CreateSCOrderToLESResponse();
					tempObj.setFLAG(tempList.get(i).getFLAG());
					tempObj.setVBELN(tempList.get(i).getVBELN());
					tempObj.setMESSAGE(tempList.get(i).getMESSAGE());
					tempObj.setBSTKD(tempList.get(i).getBSTKD());
					rtnList.add(tempObj);
				}
			}
			// System.out.println(JsonUtil.toJson(rtnList));
			log.debug("createSCOrderToLES response:" + JsonUtil.toJson(rtnList));
		} catch (Exception e) {
			log.error("发生未知异常", e);
		}

		return rtnList;
	}

	public ServiceResult<Boolean> CancelSCOrderToLESMethod(String vbeLn) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
//			String path = "file:" + this.getClass()
//				.getResource(wsdlLocation + "/JdeAndLesLoseValidity.wsdl").getPath();
//
//			URL url = new URL(path);
			URL url = this.getClass().getResource(
					wsdlLocation + "/JdeAndLesLoseValidity.wsdl");
			JdeAndLesLoseValidity_Service service = new JdeAndLesLoseValidity_Service(url);
			JdeAndLesLoseValidity soap = service.getJdeAndLesLoseValiditySOAP();

			In in = new In();
			in.setABGRU("95");
			in.setVBELN(vbeLn);
			Out out = soap.jdeAndLesLoseValidity(in);
			if (out.getFLAG().equalsIgnoreCase("S")) {
				result.setResult(true);
			} else {
				result.setResult(false);
				result.setMessage(out.getMESSAGE());
			}
		} catch (Exception e) {
			log.error("取消销售提单时发生错误!", e);
			result.setResult(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	/**
	 * 时间转换成XMLGregorianCalendar
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	private static XMLGregorianCalendar dateToXmlDate(String date, String flag) throws Exception {

		DateFormat format = null;
		if ("date".equals(flag)) {
			format = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			format = new SimpleDateFormat("hh:mm:ss");
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(format.parse(date));

		DatatypeFactory dtf = null;
		try {
			dtf = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
		}
		XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
		dateType.setTimezone(8);
		dateType.setYear(0);
		// 由于Calendar.MONTH取值范围为0~11,需要加1
		dateType.setMonth(1);
		dateType.setDay(1);
		dateType.setHour(0);
		dateType.setMinute(0);
		dateType.setSecond(0);
		if ("date".equals(flag)) {
			dateType.setYear(cal.get(Calendar.YEAR));
			// 由于Calendar.MONTH取值范围为0~11,需要加1
			dateType.setMonth(cal.get(Calendar.MONTH) + 1);
			dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
		} else {
			dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
			dateType.setMinute(cal.get(Calendar.MINUTE));
			dateType.setSecond(cal.get(Calendar.SECOND));

		}
		return dateType;
	}

	public XMLGregorianCalendar convertToXMLGregorianCalendar(String date, String flag) {
		DateFormat format = null;
		if ("date".equals(flag)) {
			format = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			format = new SimpleDateFormat("hh:mm:ss");
		}
		GregorianCalendar cal = new GregorianCalendar();

		XMLGregorianCalendar gc = null;
		try {
			cal.setTime(format.parse(date));
			gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return gc;
	}

	public List<ZWDTABLE2> findRRSOutInfoBySo(List<String> so) {
		List<ZWDTABLE2> list = new ArrayList<ZWDTABLE2>();
		try {
			List<ZWDTABLEEntity> entityList = purchaseLesRRSOutService.findOutInfoBySO(so);
			for (ZWDTABLEEntity zwdtableEntity : entityList){
				ZWDTABLE2 zwdtable2 = new ZWDTABLE2();
				BeanUtils.copyProperties(zwdtableEntity,zwdtable2);
				list.add(zwdtable2);
			}
		}catch (Exception e){

		}
		//return purchaseLesRRSOutService.findOutInfoBySO(so);
		return list;
	}

	// 根据Les订单号查询WA出库信息
	public List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans> selectInOutInfoBySo(List<String> ids) {

		List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans> list = new ArrayList<GetKUCUNInfoFromLESToEHAIERResponseStockTrans>();
		try {
			List<GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity> entityList = purchaseLesStockInfoService.selectInOutInfoBySo(ids);
			for (GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity getKUCUNInfoFromLESToEHAIERResponseStockTransEntity : entityList){
				GetKUCUNInfoFromLESToEHAIERResponseStockTrans getKUCUNInfoFromLESToEHAIERResponseStockTrans = new GetKUCUNInfoFromLESToEHAIERResponseStockTrans();
				BeanUtils.copyProperties(getKUCUNInfoFromLESToEHAIERResponseStockTransEntity,getKUCUNInfoFromLESToEHAIERResponseStockTrans);
				list.add(getKUCUNInfoFromLESToEHAIERResponseStockTrans);
			}
		}catch (Exception e){

		}
		return list;
		//return purchaseLesStockInfoService.selectInOutInfoBySo(ids);
	}

	public List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans> selectInOutInfoByDn(List<String> dn) {

		List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans> list = new ArrayList<GetKUCUNInfoFromLESToEHAIERResponseStockTrans>();
		try {
			List<GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity> entityList = purchaseLesStockInfoService.selectInOutInfoByDn(dn);
			for (GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity getKUCUNInfoFromLESToEHAIERResponseStockTransEntity : entityList){
				GetKUCUNInfoFromLESToEHAIERResponseStockTrans getKUCUNInfoFromLESToEHAIERResponseStockTrans = new GetKUCUNInfoFromLESToEHAIERResponseStockTrans();
				BeanUtils.copyProperties(getKUCUNInfoFromLESToEHAIERResponseStockTransEntity,getKUCUNInfoFromLESToEHAIERResponseStockTrans);
				list.add(getKUCUNInfoFromLESToEHAIERResponseStockTrans);
			}
		}catch (Exception e){

		}
		return list;

		//return purchaseLesStockInfoService.selectInOutInfoByDn(dn);
	}

	public List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans> selectInOutInfoBySingleDn(String dn) {

		List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans> list = new ArrayList<GetKUCUNInfoFromLESToEHAIERResponseStockTrans>();
		try {
			List<GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity> entityList = purchaseLesStockInfoService.selectInOutInfoBySingleDn(dn);
			for (GetKUCUNInfoFromLESToEHAIERResponseStockTransEntity getKUCUNInfoFromLESToEHAIERResponseStockTransEntity : entityList){
				GetKUCUNInfoFromLESToEHAIERResponseStockTrans getKUCUNInfoFromLESToEHAIERResponseStockTrans = new GetKUCUNInfoFromLESToEHAIERResponseStockTrans();
				BeanUtils.copyProperties(getKUCUNInfoFromLESToEHAIERResponseStockTransEntity,getKUCUNInfoFromLESToEHAIERResponseStockTrans);
				list.add(getKUCUNInfoFromLESToEHAIERResponseStockTrans);
			}
		}catch (Exception e){

		}
		return list;

		//return purchaseLesStockInfoService.selectInOutInfoBySingleDn(dn);
	}

	/**
	 * 查询最后一次更新LES时间
	 * 
	 * @return
	 */
	public String selectLastSyncTime() {
		return purchaseLesStockInfoService.selectLastSyncTime();
	}

}
