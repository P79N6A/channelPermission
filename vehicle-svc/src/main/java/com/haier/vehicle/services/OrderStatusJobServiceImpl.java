package com.haier.vehicle.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Holder;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.vehcile.AreaCenterInfoDTO;
import com.haier.purchase.data.model.vehcile.Cn3wPurchaseStock;
import com.haier.purchase.data.model.vehcile.DepartmentInfoDTO;
import com.haier.purchase.data.model.vehcile.EisInterfaceFinance;
import com.haier.purchase.data.model.vehcile.Entry3wOrder;
import com.haier.purchase.data.model.vehcile.MaterielInfoDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDetailsDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderZqDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderZqDetailsDTO;
import com.haier.purchase.data.model.vehcile.VehicleProductPaymentDTO;
import com.haier.purchase.data.service.PurchaseProductPaymentService;
import com.haier.purchase.data.service.vechile.PurchaseAreaCenterInfoService;
import com.haier.purchase.data.service.vechile.PurchaseDepartmentInfoService;
import com.haier.purchase.data.service.vechile.PurchaseMaterielInfoService;
import com.haier.purchase.data.service.vechile.PurchaseVehicleInterfaceLogService;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderDetailService;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderService;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderZqDetailsService;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderZqService;
import com.haier.purchase.data.service.vechile.PurchaseVehicleProductPaymentService;
import com.haier.system.model.PlanInDate;
import com.haier.system.model.PlanInDateEnum;
import com.haier.system.service.PlanInDateService;
import com.haier.vehicle.model.SaleBIllDetail;
import com.haier.vehicle.model.SaleBillMaster;
import com.haier.vehicle.service.FactoryBaseContrastService;
import com.haier.vehicle.service.OesMaterielService;
import com.haier.vehicle.service.OrderStatusJobService;
import com.haier.vehicle.service.VehicleLogInService;
import com.haier.vehicle.util.DateUtil;
import com.haier.vehicle.util.HttpUtils;
import com.haier.vehicle.util.XmlUtils;
import com.haier.vehicle.wsdl.purchasefromgvs.ObjectFactory;
import com.haier.vehicle.wsdl.purchasefromgvs.TransDNInfoFromEHAIERToGVS;
import com.haier.vehicle.wsdl.purchasefromgvs.TransDNInfoFromEHAIERToGVS_Service;
import com.haier.vehicle.wsdl.purchasefromgvs.ZMMS0003;
import com.haier.vehicle.wsdl.purchasefromgvs.ZSDS0002;
import com.haier.vehicle.wsdl.queryDNFrom.QueryDNFromLEStoAPP;
import com.haier.vehicle.wsdl.queryDNFrom.QueryDNFromLEStoAPP_Service;
import com.haier.vehicle.wsdl.queryDNFrom.ZINTWXWTLOG;

/**
 * 订单获取状态Job Created by lupeng on 2017/9/8.
 */

// @Configuration
// @EnableScheduling
@Service
public class OrderStatusJobServiceImpl implements OrderStatusJobService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PurchaseVehicleOrderDetailService vehicleOrderDetailDao;
	@Autowired
	private PurchaseVehicleOrderZqDetailsService vehicleOrderZqDetailsService;
	@Autowired
	private VehicleJobServiceImpl jobService;
	@Autowired
	private PurchaseVehicleOrderZqService vehicleOrderZqDao;
	@Autowired
	private PurchaseVehicleOrderZqDetailsService vehicleOrderZqDetailsDao;
	@Autowired
	private VehicleInsertSaleBillToCRMServiceImpl vehicleInsertSaleBillToCRMService;

	@Autowired
	private PurchaseDepartmentInfoService departmentInfoDao;

	@Autowired
	private PurchaseVehicleProductPaymentService vehicleProductPaymentDao;

	@Autowired
	private PurchaseAreaCenterInfoService areaCenterInfoDao;

	@Autowired
	private VehicleGetPriceFromCRMServiceImpl vehicleGetPriceFromCRMService;

	@Autowired
	private PurchaseMaterielInfoService materielInfoDao;

	@Autowired
	private PurchaseVehicleOrderService vehicleOrderDao;
	@Autowired
	private PurchaseVehicleInterfaceLogService vehicleInterfaceLogService;
	@Autowired
	private PlanInDateService planInDateService;
	@Autowired
	private FactoryBaseContrastService factoryBaseContrastService;
	@Autowired
	private OesMaterielService oesMaterielService;
	@Autowired
	private PurchaseProductPaymentService purchaseProductPaymentService;
	@Autowired
	private VehicleLogInService vehicleLogInService;

	@Value("${materielPath}")
	private String materielPath;

	public String getUserId() {
		return "8800017840";
	}

	public String getLogIn() {
		return "true";
	}

	@Value("${wsdlPath}")
	private String wsdlPath;
	@Value("${entryOrderUrl}")
	private String entryOrderUrl;
	// 入库单状态,NEW-未开始处理, ACCEPT-仓库接单 , PARTFULFILLED-部分收货完成, FULFILLED-收货完成,
	// EXCEPTION-异常, CANCELED-取消, CLOSED-关闭, REJECT-拒单, CANCELEDFAIL-取消失败
	private static String NEW = "NEW";
	private static String ACCEPT = "ACCEPT";
	private static String PARTFULFILLED = "PARTFULFILLED";
	private static String FULFILLED = "FULFILLED";

	public void vehicleTest() {
		System.out.println("整车直发-测试vehicleTest定时任务，当前时间：" + new Date());
	}

//	@Scheduled(cron = "00 59 10 ? * *")
	@Override
	public void updateZqVbeln() {
		List<VehicleOrderZqDetailsDTO> list = vehicleOrderZqDetailsService
				.selectByStatus();
		if (list == null || list.size() == 0) {
			log.info("[updateZqVbeln]更新85单号,没有需要处理的数据");
			return;
		}
		List<String> itemNoList = new ArrayList<String>();
		if (list != null && list.size() > 0) {
			for (VehicleOrderZqDetailsDTO vehicleOrderZqDetailsDTO : list) {
				itemNoList.add(vehicleOrderZqDetailsDTO.getZqItemNo());
			}
			if (itemNoList != null && itemNoList.size() > 0) {
				jobService.queryStatusFromGvs(itemNoList,"ZQ");
			}
		}
	}
	
//	@Scheduled(cron = "20 57 12 ? * *")
	@Override
	public void updateZkVbeln() {
		List<VehicleOrderDetailsDTO> list = vehicleOrderDetailDao
				.selectByStatus();
		if (list == null || list.size() == 0) {
			log.info("[updateZkVbeln]更新85单号,没有需要处理的数据");
			return;
		}
		List<String> itemNoList = new ArrayList<String>();
		if (list != null && list.size() > 0) {
			for (VehicleOrderDetailsDTO vehicleOrderDetailsDTO : list) {
				itemNoList.add(vehicleOrderDetailsDTO.getItemNo());
			}
			if (itemNoList != null && itemNoList.size() > 0) {
				jobService.queryStatusFromGvs(itemNoList,"ZK");
			}
		}
	}

	/**
	 * 物流判断直发单接口
	 */
	@Override
	public void updateVbelnDnStatus() {

		List<VehicleOrderDetailsDTO> dTOs = vehicleOrderDetailDao
				.getListByVbeln();
		if (dTOs.size() > 0) {

			List<String> vbelnDns = new ArrayList<String>();
			for (VehicleOrderDetailsDTO vehicleOrderDetailsDTO : dTOs) {
				if (vehicleOrderDetailsDTO.getVbelnDn1() != null
						&& !"".equals(vehicleOrderDetailsDTO.getVbelnDn1())) {
					String message = jobService.saveDNS85(
							vehicleOrderDetailsDTO.getVbelnDn1(),
							vehicleOrderDetailsDTO.getMaterielCode(), "700");
					if (message != null && "1".equals(message)) {
						vbelnDns.add(vehicleOrderDetailsDTO.getVbelnDn1());
					}
				}
			}

			if (vbelnDns.size() > 0) {
				vehicleOrderDetailDao.updateVbelnDnStatus(vbelnDns);
			}
		}

	}

	/**
	 * 通过物料号修改对应的体积
	 */
	@Override
	public void saleMaterielBill() {
		// 1.查询状态为2的物料信息(物料号)
		List<MaterielInfoDTO> listMaterielInfo = materielInfoDao
				.getVehicleInfo(2);
		List<MaterielInfoDTO> listMaterielInfo2 = new ArrayList<MaterielInfoDTO>();

		String itemNo = "";

		for (int i = 0; i < listMaterielInfo.size(); i++) {
			itemNo = listMaterielInfo.get(i).getMaterielCode();
			String soap = jobService.queryStatusFromMateriel(itemNo);
			if (!",".equals(soap)) {

				String[] soaps = soap.split(",");

				try {

					if ("S".equals(soaps[1])) {

						Document document = DocumentHelper.parseText(soaps[0]);
						Element root = document.getRootElement();
						Element rowSet = root.element("ROWSET");

						List<Element> rows = rowSet.elements("ROW");

						for (Element row : rows) {
							MaterielInfoDTO materielInfoDTO2 = new MaterielInfoDTO();
							materielInfoDTO2.setMaterielCode(row.element(
									"MATERIAL_CODE").getText());
							materielInfoDTO2.setVolume(new BigDecimal(
									Double.valueOf(row.element("VOLUME")
											.getText()) / 1000000).setScale(6,
									BigDecimal.ROUND_HALF_UP).doubleValue());
							System.out.println(materielInfoDTO2
									.getMaterielCode()
									+ "."
									+ materielInfoDTO2.getVolume());
							listMaterielInfo2.add(materielInfoDTO2);
						}

						for (MaterielInfoDTO dto : listMaterielInfo2) {
							materielInfoDao.updateMaterielInfo(dto.getVolume(),
									dto.getMaterielCode());
						}
					}

				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * 物流85和LBX对应关系
	 */
	public void pushLogistics() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pushStatus", 0);
		List<Entry3wOrder> enrty3wOrderList = vehicleOrderDetailDao
				.queryEntry3wOrder(map);
		if (enrty3wOrderList == null || enrty3wOrderList.size() == 0) {
			log.info("[pushLogistics]物流推送,没有需要处理的数据");
			return;
		}
		URL url = this.getClass().getResource(
				wsdlPath + "/QueryDNFromLEStoAPP.wsdl");
		QueryDNFromLEStoAPP_Service service = new QueryDNFromLEStoAPP_Service(
				url);
		QueryDNFromLEStoAPP soap = service.getQueryDNFromLEStoAPPSOAP();
		for (Entry3wOrder entry : enrty3wOrderList) {
			com.haier.vehicle.wsdl.queryDNFrom.ObjectFactory objectFactory = new com.haier.vehicle.wsdl.queryDNFrom.ObjectFactory();
			ZINTWXWTLOG zintwxwtlog = objectFactory.createZINTWXWTLOG();
			java.util.List<com.haier.vehicle.wsdl.queryDNFrom.ZINTWXWTLOG> input = new ArrayList<com.haier.vehicle.wsdl.queryDNFrom.ZINTWXWTLOG>();
			zintwxwtlog.setMANDT("700");
			zintwxwtlog.setBSTNK(entry.getEntryOrderCode() + "D");// 85单号
			zintwxwtlog.setBSTKD(entry.getEntryOrderCode() + "D");
			zintwxwtlog.setPOSNR("ZK");
			zintwxwtlog.setTKNUM("ZC");// 订单类别(整车--ZC 款先拼车--PC T+2--T2)
			zintwxwtlog.setSOURCE("TMALL");
			zintwxwtlog.setSOURCESN(entry.getEntryOrderId());// lbx
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = sdf.format(date);
			String rqStr = dateStr.replaceAll("-", ".").substring(0, 10);
			String sjStr = dateStr.substring(10);
			zintwxwtlog.setCRDAT(rqStr);
			zintwxwtlog.setCRZET(sjStr);
			zintwxwtlog.setNAME1("CBS");
			zintwxwtlog.setMESSAGE("日日顺入3W");
			input.add(zintwxwtlog);
			Holder<String> flag = new Holder<String>();
			Holder<String> message = new Holder<String>();
			String callcode = "CBS#239";
			String source = "CBS";
			soap.queryDNFromLEStoAPP(callcode, source, input, flag, message);
			vehicleInterfaceLogService.insertLog("物流85和LBX对应关系推送入参", JSONObject
					.toJSON(input).toString());
			vehicleInterfaceLogService.insertLog("物流85和LBX对应关系推送出参",
					message.value.toString());
			if ("S".equals(flag.value)) {
				// 状态修改为已推送
				entry.setPushStatus("1");
				vehicleOrderDetailDao.updateEntry3wOrder(entry);
			} else {

			}
		}
	}

	public void addLbx2() {
		List<VehicleOrderDetailsDTO> orderDetailList = vehicleOrderDetailDao
				.selectByWaitUpdateLbx();
		if (orderDetailList == null || orderDetailList.size() == 0) {
			log.info("[addLbx]更新lbx,没有需要处理的数据");
			return;
		}
		for (VehicleOrderDetailsDTO dto : orderDetailList) {
			// 调用接口获取数据 TODO
			VehicleOrderZqDetailsDTO zqDetail = new VehicleOrderZqDetailsDTO();
			zqDetail.setZqItemNo(dto.getItemNo().replaceAll("ZK", "ZQ"));
			zqDetail = vehicleOrderZqDetailsService.getOneByCondition(zqDetail);
			Entry3wOrder entry3wOrder = null;
			try {
				if (zqDetail != null && zqDetail.getVbelnDn5() != null
						&& !"".equals(zqDetail.getVbelnDn5())) {
					entry3wOrder = getEntryOrder(zqDetail.getVbelnDn5());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (entry3wOrder != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("entryOrderCode", dto.getVbelnDn1());
				List<Entry3wOrder> eoList = vehicleOrderDetailDao
						.queryEntry3wOrder(map);
				entry3wOrder.setEntryOrderCode(dto.getVbelnDn1());
				// 判断当前85单号是否存在于entry_3w_order
				if (eoList != null && eoList.size() > 0) {

					vehicleOrderDetailDao.updateEntry3wOrder(entry3wOrder);
				} else {
					entry3wOrder.setPushStatus("0");
					vehicleOrderDetailDao.addEntry3wOrder(entry3wOrder);
				}

				// status=FULFILLED时将entryOrderId更新到中lbx，将operateTime更新到zspdt中
				dto.setLbx(entry3wOrder.getEntryOrderId());
				if (FULFILLED.equals(entry3wOrder.getStatus())) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					if (entry3wOrder.getOperateTime() != null
							&& !"".equals(entry3wOrder.getOperateTime())) {
						try {
							dto.setZspdt(sdf.parse(entry3wOrder
									.getOperateTime()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				vehicleOrderDetailDao.updateSelectiveByItemNo(dto);
			}
		}
	}

	/**
	 * 更新lbx、入库时间
	 */
//	@Scheduled(cron = "0 43 13 ? * *")
	public void addLbx() {
		List<VehicleOrderDetailsDTO> orderDetailList = vehicleOrderDetailDao
				.selectByWaitUpdateLbx();
		if (orderDetailList == null || orderDetailList.size() == 0) {
			log.info("[addLbx]更新lbx,没有需要处理的数据");
			return;
		}
		for (VehicleOrderDetailsDTO dto : orderDetailList) {
				Entry3wOrder entry3wOrder = null;
				try {
					if (dto.getVbelnSpare() != null && !"".equals(dto.getVbelnSpare())){
						entry3wOrder = getEntryOrder(dto.getVbelnSpare());
					} else if (dto.getVbelnDn1() != null && !"".equals(dto.getVbelnDn1())) {
						entry3wOrder = getEntryOrder(dto.getVbelnDn1());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (entry3wOrder != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("entryOrderCode", dto.getVbelnDn1());
					List<Entry3wOrder> eoList = vehicleOrderDetailDao
							.queryEntry3wOrder(map);
					entry3wOrder.setEntryOrderCode(dto.getVbelnDn1());
					// 判断当前85单号是否存在于entry_3w_order
					if (eoList != null && eoList.size() > 0) {
						//判断获取的数据LBX与表中LBX是否一致，不一致时要将pushStatus修改为0（需要重新推送物流）
						if(!eoList.get(0).getEntryOrderId().equals(entry3wOrder.getEntryOrderId())){
							entry3wOrder.setPushStatus("0");
						}
						vehicleOrderDetailDao.updateEntry3wOrder(entry3wOrder);
					} else {
						entry3wOrder.setPushStatus("0");
						vehicleOrderDetailDao.addEntry3wOrder(entry3wOrder);
					}

					// status=FULFILLED时将entryOrderId更新到中lbx，将operateTime更新到zspdt中
					dto.setLbx(entry3wOrder.getEntryOrderId());
					if (FULFILLED.equals(entry3wOrder.getStatus())) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						if (entry3wOrder.getOperateTime() != null
								&& !"".equals(entry3wOrder.getOperateTime())) {
							try {
								dto.setZspdt(sdf.parse(entry3wOrder
										.getOperateTime()));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
					}
					vehicleOrderDetailDao.updateSelectiveByItemNo(dto);
				}
		}
	}

	/**
	 * 通过85单号调用接口获取lbx、入库时间
	 * 
	 * @throws MalformedURLException
	 */
	public Entry3wOrder getEntryOrder(String dnId) throws Exception {
		String urlPath = new String(entryOrderUrl);
		String param = "dnId=" + dnId;
		// 建立连接
		URL url = new URL(urlPath);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		// 设置参数
		httpConn.setDoOutput(true); // 需要输出
		httpConn.setDoInput(true); // 需要输入
		httpConn.setUseCaches(false); // 不允许缓存
		httpConn.setRequestMethod("POST"); // 设置POST方式连接
		// 设置请求属性
		httpConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
		httpConn.setRequestProperty("Charset", "UTF-8");
		// 连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
		// httpConn.connect();
		// 建立输入流，向指向的URL传入参数
		DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
		dos.writeBytes(param);
		dos.flush();
		dos.close();
		// 获得响应状态
		int resultCode = httpConn.getResponseCode();
		if (HttpURLConnection.HTTP_OK == resultCode) {
			StringBuffer sb = new StringBuffer();
			String readLine = new String();
			BufferedReader responseReader = new BufferedReader(
					new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}
			responseReader.close();
			Gson json = new Gson();
			if (sb != null && !"".equals(sb)) {
				Map<String, Object> map = XmlUtils.xmlStrToMap(json
						.fromJson(sb.toString(), ServiceResult.class)
						.getResult().toString());
				if ("success".equals(map.get("flag").toString())) {
					Entry3wOrder entry3wOrder = (Entry3wOrder) XmlUtils
							.mapToBean(XmlUtils.xmlStrToMap(map.get(
									"entryOrder").toString()),
									Entry3wOrder.class);
					entry3wOrder.setFlag(map.get("flag").toString());
					entry3wOrder.setCode(json.fromJson(sb.toString(),
							ServiceResult.class).getCode());
					entry3wOrder.setMessage(json.fromJson(sb.toString(),
							ServiceResult.class).getMessage());
					if (map.get("totalLines") != null) {
						entry3wOrder.setTotalLines(Integer.parseInt(map.get(
								"totalLines").toString()));
					}
					return entry3wOrder;
				} else {
					vehicleInterfaceLogService.insertLog(
							"通过85单号调用接口获取lbx、入库时间接口出参",
							JSONObject.toJSON(map.get("message").toString())
									.toString());
				}
			}
		}
		return null;
	}

	/**
	 * 用户签收后推送SAP
	 */
	// @Scheduled(cron = "0 36/30 * * * ?")
	@Override
	public void cnPurchaseStorageToSap() {
		List<VehicleOrderDetailsDTO> orderDetailList = vehicleOrderDetailDao
				.selectByWaitToSap();
		if (orderDetailList == null || orderDetailList.size() == 0) {
			log.info("[cnPurchaseStorageToSap]推送SAP,没有需要处理的数据");
			return;
		}
		String[] message = new String[1];
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < orderDetailList.size(); i++) {
			try {
				VehicleOrderZqDetailsDTO zq = new VehicleOrderZqDetailsDTO();
				zq.setZqItemNo(orderDetailList.get(i).getItemNo()
						.replaceAll("ZK", "ZQ"));
				zq = vehicleOrderZqDetailsService.getOneByCondition(zq);
				if (zq != null && zq.getVbelnDn5() != null
						&& !"".equals(zq.getVbelnDn5())) {
					dealAndPushDataV2(orderDetailList.get(i), message, sdf,
							zq.getVbelnDn5());
				} else {
					message[0] = "[推送SAP，整车订单编号["
							+ orderDetailList.get(i).getItemNo() + "未获取到二次85单号";
					log.error(message[0]);
				}
			} catch (Exception e) {
				message[0] = "[推送SAP，整车订单编号["
						+ orderDetailList.get(i).getOrderNo() + "]发生异常：";
				log.error(message[0], e);
				sb.append("[" + (i + 1) + "]:" + message[0] + e.getMessage()
						+ ";");
			}
		}
	}

	private boolean dealAndPushDataV2(VehicleOrderDetailsDTO orderDetail,
			String[] message, SimpleDateFormat sdf, String vbelnDn5) {
		if (orderDetail == null)
			return false;
		boolean flag = true;
		int rsStatus;
		String rsMessage;
		ObjectFactory objectFactory = new ObjectFactory();
		ZMMS0003 request = objectFactory.createZMMS0003();
		Cn3wPurchaseStock cn3wPurchaseStock = new Cn3wPurchaseStock();
		String format = sdf.format(orderDetail.getZspdt());
		request.setZSPDT(format.substring(0, 10));// 订单入库日期(采购订单如何获取)
		request.setMATNR(orderDetail.getMaterielCode());// 物料编码
		request.setMENGE(new BigDecimal(orderDetail.getQty()));// 交货数量
		// vehicle_order.delivery_code=area_center_info.delivery_to_code关联出来的数据取wh_code
		VehicleOrderDTO order = new VehicleOrderDTO();
		order.setOrderNo(orderDetail.getOrderNo());
		List<VehicleOrderDTO> orderList = vehicleOrderDao
				.getListByCondition(order);
		if (orderList != null && orderList.size() > 0) {
			AreaCenterInfoDTO areaInfo = new AreaCenterInfoDTO();
			areaInfo.setDeliveryToCode(orderList.get(0).getDeliveryCode());
			List<AreaCenterInfoDTO> areaInfoList = areaCenterInfoDao
					.getListByCondition(areaInfo);
			if (areaInfoList != null && areaInfoList.size() > 0) {
				request.setLGORT(areaInfoList.get(0).getWhCode());// 库位
			} else {
				rsStatus = EisInterfaceFinance.STATUS_FAILED;
				cn3wPurchaseStock.setStatus(2);
				cn3wPurchaseStock.setMessage("未获取到库位数据，需要重新处理");
			}
		} else {
			rsStatus = EisInterfaceFinance.STATUS_FAILED;
			cn3wPurchaseStock.setStatus(2);
			cn3wPurchaseStock.setMessage("未获取到库位数据，需要重新处理");
		}
		request.setVBELN(vbelnDn5);
		request.setZSPNB("");
		request.setLIFNR("1");// 供应商
		String lbx = orderDetail.getLbx();
		// 2017-08-14 截取后15位
		if (lbx.length() > 15) {
			request.setZLSGI(lbx.substring(lbx.length() - 15, lbx.length()));
		} else {
			request.setZLSGI(lbx);
		}
		request.setZFGLG("10");// 批次编号
		request.setPOSNR("1");// 明细号

		URL url = this.getClass().getResource(
				wsdlPath + "/TransDNInfoFromEHAIERToGVS.wsdl");
		TransDNInfoFromEHAIERToGVS_Service service = new TransDNInfoFromEHAIERToGVS_Service(
				url);
		TransDNInfoFromEHAIERToGVS soap = service
				.getTransDNInfoFromEHAIERToGVSSOAP();

		Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();
		Holder<Integer> exSUBRC = new Holder<Integer>();
		List<ZMMS0003> tZMMS0003 = new ArrayList<ZMMS0003>();
		tZMMS0003.add(request);

		Long startTime = System.currentTimeMillis();

		try {
			soap.transDNInfoFromEHAIERToGVS(tZMMS0003, exSUBRC, tMSG);
			// JSONObject object = (JSONObject) JSONObject.toJSON(tZMMS0003);

			vehicleInterfaceLogService.insertLog("推送SAP接口入参", JSONObject
					.toJSON(tZMMS0003).toString());
			// object = (JSONObject) JSONObject.toJSON(tMSG);
			vehicleInterfaceLogService.insertLog("推送SAP接口出参", JSONObject
					.toJSON(tMSG).toString());
			String msg = JsonUtil.toJson(tMSG.value);
			// dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
			// dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
			// dataLog.setResponseTime(DateUtil.currentDateTime());
			// dataLog.setResponseData(msg);

			List<ZSDS0002> results = tMSG.value;
			if (results == null || results.size() <= 0) {
				rsStatus = EisInterfaceFinance.STATUS_FAILED;
				rsMessage = "EAI 返回空";
				// dataLog.setResponseData("");
			} else {
				// boolean flag = true;
				for (ZSDS0002 zsds0002 : results) {
					if (!flag)
						break;
					flag = !"E".equalsIgnoreCase(zsds0002.getTYPE());
				}
				cn3wPurchaseStock.setCnStockSyncsId(orderDetail.getItemNo());
				cn3wPurchaseStock.setAddTime(new Date());
				cn3wPurchaseStock.setProcessTime(new Date());
				if (flag) {
					rsStatus = EisInterfaceFinance.STATUS_SUCCESS;
					cn3wPurchaseStock.setStatus(1);
					cn3wPurchaseStock.setPushData(JsonUtil.toJson(tZMMS0003));
					cn3wPurchaseStock.setReturnData(msg);
					cn3wPurchaseStock.setMessage("V2-处理并向SAP推送数据成功");
				} else {
					rsStatus = EisInterfaceFinance.STATUS_FAILED;
					cn3wPurchaseStock.setStatus(2);
					cn3wPurchaseStock.setPushData(JsonUtil.toJson(tZMMS0003));
					cn3wPurchaseStock.setReturnData(msg);
					cn3wPurchaseStock.setMessage("处理并向SAP推送数据失败，需要重新处理");
				}
				rsMessage = msg;
			}
		} catch (Exception e) {
			flag = false;
			rsStatus = EisInterfaceFinance.STATUS_FAILED;
			rsMessage = "调用EAI接口失败";
			cn3wPurchaseStock.setStatus(2);
			cn3wPurchaseStock.setPushData(JsonUtil.toJson(tZMMS0003));
			cn3wPurchaseStock.setReturnData("");
			cn3wPurchaseStock
					.setMessage("V2-处理并向SAP推送数据[菜鸟采购入库]异常失败，停止处理,异常信息："
							+ e.getMessage());
			log.error("调用EAI接口 transReBackInfoFromEHAIERToGVS 失败：", e);
		}
		message[0] = message[0] + rsMessage;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cnStockSyncsId", orderDetail.getOrderNo());
		List<Cn3wPurchaseStock> list = jobService.queryCn3wPurchaseStock(map);
		if (list != null && list.size() > 0) {
			jobService.updateCn3wPurchaseStock(cn3wPurchaseStock);
		} else {
			jobService.addPurchaseStock(cn3wPurchaseStock);
		}
		return flag;
	}

	// 每三十分钟执行
//	@Scheduled(cron = "30 30 10 ? * *")
//	@Override
	public void saleBill() throws BusinessException {
		PlanInDate planInDate = planInDateService
				.getPlanInDateByTypeId(PlanInDateEnum.ZCDH.getCode());
		if (planInDate == null || planInDate.getValue() == null
				|| "".equals(planInDate.getValue())) {

			vehicleInterfaceLogService.insertLog("整车直发CRM扣款异常",
					"整车直发到货周未设置,请设置到货周!");
			throw new BusinessException("整车直发到货周未设置,请设置到货周!");
		}
		VehicleOrderZqDTO vehicleOrderZqDTO = new VehicleOrderZqDTO();
		vehicleOrderZqDTO.setStatus("06");
		// 取主表信息
		List<VehicleOrderZqDTO> listMaster = vehicleOrderZqDao
				.listByCondition(vehicleOrderZqDTO);
		for (VehicleOrderZqDTO vehicleOrderZqDTOMaster : listMaster) {

			// 基础数据
			DepartmentInfoDTO dep = departmentInfoDao
					.getOneByDeliveryToCode(vehicleOrderZqDTOMaster
							.getDeliveryCode());

			VehicleOrderZqDetailsDTO vehicleOrderZqDetailsDTO = new VehicleOrderZqDetailsDTO();
			vehicleOrderZqDetailsDTO.setZqOrderNo(vehicleOrderZqDTOMaster
					.getZqOrderNo());
			vehicleOrderZqDetailsDTO.setStatus("00");
			List<VehicleOrderZqDetailsDTO> listDetail = vehicleOrderZqDetailsDao
					.listByCondition(vehicleOrderZqDetailsDTO);
			// 需要处理的条数
			int flagCount = listDetail.size();
			// 处理成功的条数
			int successCount = 0;
			for (VehicleOrderZqDetailsDTO vehicleOrderZqDetailsDTODetail : listDetail) {

				// 基础数据
				VehicleProductPaymentDTO vehicleProductPaymentDTO = new VehicleProductPaymentDTO();
				vehicleProductPaymentDTO
						.setPaymentCode(vehicleOrderZqDetailsDTODetail
								.getProductGroup());
				// 基础数据
				AreaCenterInfoDTO entity = new AreaCenterInfoDTO();
				entity.setDeliveryToCode(vehicleOrderZqDTOMaster
						.getDeliveryCode());
				AreaCenterInfoDTO area = areaCenterInfoDao
						.getOneByDeliveryToCode(vehicleOrderZqDTOMaster
								.getDeliveryCode());

				SaleBillMaster master = new SaleBillMaster();
				// 主表赋值
				master.setBillCode(vehicleOrderZqDetailsDTODetail.getZqItemNo());
				master.setCorpCode(dep.getOrganizationCode());
				master.setRegionID(vehicleOrderZqDTOMaster.getAreaCode());
				master.setBillType("ZGOZ");
				master.setPickType("6");
				master.setCustCode(vehicleOrderZqDetailsDTODetail
						.getPaymentCode());
				master.setDestCode(vehicleOrderZqDTOMaster.getDeliveryCode());
				master.setBaseCode(vehicleOrderZqDTOMaster.getJdCode());
				master.setWhCode(area.getWarehouseCode() + "81");
				master.setCustMgr("01417887");
				master.setProMgr("01417887");
				master.setProDeputy("01417887");
				master.setPlanInDate(DateUtil.getT2Date(PlanInDateEnum.ZCDH
						.getCode()));
				master.setOrderCode("");
				master.setProjectCode("");
				master.setBudgetSort("30");
				master.setBudgetOrg(vehicleOrderZqDetailsDTODetail
						.getBudgetOrg());
				master.setInvSort(vehicleOrderZqDetailsDTODetail
						.getProductGroup());
				master.setBrandCode(vehicleOrderZqDetailsDTODetail.getBrand());
				master.setSaleOrgCode(dep.getOrganizationCode());
				master.setUserMemo("");
				master.setMaker("CBS["
						+ vehicleOrderZqDetailsDTODetail.getCreateBy() + "]");
				master.setAudiMan("");
				master.setAudiFlag("1");
				master.setBName("");
				master.setAddress("");
				master.setTel("");
				master.setZipCode("");
				master.setReGetmoney("");
				master.setFlag("4");
				master.setADD1(area.getRrsCenterCode());//
				// 当已成功处理的数据等于需要处理的数据-1时传Y
				if (successCount == flagCount - 1) {
					master.setADD2("Y");
				} else {
					master.setADD2("");
				}

				master.setADD3("");
				master.setADD4("");
				master.setADD5("");
				master.setAtpPlanInDateprivate(DateUtil
						.getT2Date(PlanInDateEnum.ZCDH.getCode()));
				SaleBIllDetail detail = new SaleBIllDetail();
				// 从表赋值
				Map<String, String> map = new HashMap<String, String>();
				map.put("custCode",
						vehicleOrderZqDetailsDTODetail.getPaymentCode());
				map.put("regionCode", vehicleOrderZqDTOMaster.getJdCode());
				map.put("invCode",
						vehicleOrderZqDetailsDTODetail.getMaterielCode());
				map.put("corpCode", vehicleOrderZqDTOMaster.getCorpCode());
				String priceJson = vehicleGetPriceFromCRMService
						.getPriceFromCrm(map);
				JSONObject json = JSONObject.parseObject(priceJson);
				detail.setInvCode(vehicleOrderZqDetailsDTODetail
						.getMaterielCode());
				detail.setInvSort(vehicleOrderZqDetailsDTODetail
						.getProductGroup());
				detail.setInvBrand(vehicleOrderZqDetailsDTODetail.getBrand());
				detail.setInvState("10");
				detail.setQty(vehicleOrderZqDetailsDTODetail.getQty()
						.toString());
				detail.setUnitPrice(String
						.valueOf(vehicleOrderZqDetailsDTODetail.getUnitPrice()));
				detail.setSumMoney(vehicleOrderZqDetailsDTODetail
						.getUnitPrice()
						* vehicleOrderZqDetailsDTODetail.getQty() + "");
				detail.setStockPrice(json.get("reStockPrice").toString());
				detail.setRetailPrice(json.get("reRetailPrice").toString());
				detail.setActPrice(json.get("reActPrice").toString());
				detail.setBateRate(json.get("reBateRate").toString());
				detail.setBateMoney(json.get("reBateMoney").toString());
				detail.setVerCode("");
				detail.setVerMoney("0");
				detail.setProLossMoney("0");
				detail.setLossRate(json.get("reLossRate").toString());
				detail.setIsFL(json.get("reIsFL").toString());
				detail.setIsKPO(json.get("reIsKPO").toString());
				detail.setInvMemo("");
				detail.setADD1("");
				detail.setADD2("");
				detail.setADD3("");
				ServiceResult<String> result = vehicleInsertSaleBillToCRMService
						.sendSaleBillToCRM(master, detail);
				vehicleOrderZqDetailsDao.updateMessageDetail(
						vehicleOrderZqDetailsDTODetail.getZqItemNo(),
						result.getMessage());
				if (result.getSuccess()) {
					successCount++;
					vehicleOrderZqDetailsDao
							.updateStatusDetail(vehicleOrderZqDetailsDTODetail
									.getZqItemNo());
					VehicleOrderZqDetailsDTO zqDetailsDTO = new VehicleOrderZqDetailsDTO();
					zqDetailsDTO.setZqItemNo(vehicleOrderZqDetailsDTODetail
							.getZqItemNo());
					Map<String, String> tempMap = (Map<String, String>) JSONObject
							.parse(result.getResult());
					zqDetailsDTO.setRelevantOrderNo1(tempMap.get("vbeln"));
					zqDetailsDTO.setRelevantOrderNo2(tempMap.get("vbelnDn"));
					vehicleOrderZqDetailsDao
							.updateSelectiveByZqItemNo(zqDetailsDTO);

				}
			}

			vehicleOrderZqDetailsDTO = new VehicleOrderZqDetailsDTO();
			vehicleOrderZqDetailsDTO.setZqOrderNo(vehicleOrderZqDTOMaster
					.getZqOrderNo());
			List<VehicleOrderZqDetailsDTO> zqlistDetail = vehicleOrderZqDetailsDao
					.listByCondition(vehicleOrderZqDetailsDTO);
			// 获取子表装状态为100的个数
			vehicleOrderZqDetailsDTO.setStatus("100");
			List<VehicleOrderZqDetailsDTO> zqlistDetail2 = vehicleOrderZqDetailsDao
					.listByCondition(vehicleOrderZqDetailsDTO);
			if (zqlistDetail.size() == zqlistDetail2.size()) {
				vehicleOrderZqDao.updateStatus(zqlistDetail.get(0)
						.getZqOrderNo());
			}

		}
	}

//	@Scheduled(cron = "30 01 14 ? * *")
	@Override
	public void gainMateriel() {
		ServiceResult<String> result1 = new ServiceResult<String>();
		// 总条数
		int rowcount = 0;
		// 查询次数
		int getcount = 0;
		String result = vehicleLogInService.getLoginMessage(this.getLogIn(),
				this.getUserId());
		String response = null;
		Map map2 = null;
		Map json = (Map) JSONObject.parse(result);
		if (json != null && "220".equals(json.get("code").toString())) {
			result1.setSuccess(false);
			result1.setMessage(json.get("message").toString());
		} else {
			String longfeiCATNAME = json.get("data").toString();
			Map longfeiCATNAMEJson = (Map) JSON.parse(longfeiCATNAME);
			String url = materielPath; // 365地址 POM文件配置
			JSONObject js = new JSONObject();

			js.put("userid", getUserId());
			js.put("lfSession",
					longfeiCATNAMEJson
							.get("lfSession")
							.toString()
							.substring(
									1,
									longfeiCATNAMEJson.get("lfSession")
											.toString().length() - 1));
			// 插入总条数
			int count = 0;
			for (int k = 0; k < 2; k++) {
				if (getcount == 0) {
					js.put("appFlag", "true");
					js.put("pageNo", 1);
					js.put("pageSize", 1000);
					Map<String, String> map = new HashMap<String, String>();
					map.put("Content-type", "application/json");
					response = HttpUtils.sendRequest(url, map,
							js.toJSONString(), HttpUtils.HTTP_METHOD_POST,
							false, null);
					Map map1 = (Map) JSONObject.parse(response);
					if ("200".equals(map1.get("code").toString())) {
						map2 = (Map) JSONObject.parse(map1.get("data").toString());
						List<JSONObject> list = (List<JSONObject>) JSONObject
								.parse(JSON.toJSONString(map2.get("items")));
						List<MaterielInfoDTO> miList = getMaterielInfoList(list);
						if (miList != null && miList.size() > 0) {
							// 只在第一次清空表
							materielInfoDao.truncateMaterielInfo();
							count += materielInfoDao.batchAdd(miList);
						}
						// for(int q = 0;q<itemList.size();q++){
						// itemList.get(q).getWLCODE();
						// mi.setMaterielCode(item.getWLCODE());
						// mi.setMaterielName(item.getWLNAME());
						// mi.setStatus(1);
						// mi.setProductGroupCode(productGroupCode);
						// }
						rowcount = Integer.parseInt(map2.get("rowcount")
								.toString());

					} else {
						result1.setSuccess(false);
						result1.setMessage(map1.get("errorMsg").toString());
					}
					getcount++;
					// System.out.println("查询次数："+getcount);
				} else {
					js.put("appFlag", "true");
					int pageTottle = 0;
					if (rowcount % 1000 == 0) {
						pageTottle = rowcount / 1000;
					} else {
						pageTottle = rowcount / 1000 + 1;
					}
					// System.out.println("pageTottle = "+pageTottle);
					for (int i = 1; i < pageTottle; i++) {
						Map<String, String> map = new HashMap<String, String>();
						js.put("pageNo", i + 1);
						map.put("Content-type", "application/json");
						response = HttpUtils.sendRequest(url, map,
								js.toJSONString(), HttpUtils.HTTP_METHOD_POST,
								false, null);
						Map map1 = (Map) JSONObject.parse(response);
						if ("200".equals(map1.get("code").toString())) {
							map2 = (Map) JSONObject.parse(map1.get("data")
									.toString());
							List<JSONObject> list = (List<JSONObject>) JSONObject
									.parse(JSON.toJSONString(map2.get("items")));
							List<MaterielInfoDTO> miList = getMaterielInfoList(list);
							if (miList != null && miList.size() > 0) {
								count += materielInfoDao.batchAdd(miList);
							}
						} else {
							result1.setSuccess(false);
							result1.setMessage(map1.get("errorMsg").toString());
							vehicleInterfaceLogService.insertLog(
									"365整车物料样表数据获取", map1.get("errorMsg")
											.toString());
						}
						getcount++;
						// System.out.println("查询次数："+getcount);
					}
				}
			}
			vehicleInterfaceLogService.insertLog("365整车物料样表数据获取", "获取总条数："
					+ rowcount + "插入总条数：" + count);
			// System.out.println("获取总条数："+rowcount);
			// System.out.println("插入总条数："+count);
		}
	}

	private List<MaterielInfoDTO> getMaterielInfoList(List<JSONObject> list) {
		List<MaterielInfoDTO> miList = new ArrayList<MaterielInfoDTO>();
		MaterielInfoDTO miDto = null;
		int i = 0;
		for (JSONObject obj : list) {
			miDto = new MaterielInfoDTO();
			if (obj.get("WLCODE") != null) {
				miDto.setMaterielCode(obj.get("WLCODE").toString());
			}
			if (obj.get("WLNAME") != null) {
				miDto.setMaterielName(obj.get("WLNAME").toString());
			}
			if (obj.getString("INVSORT") != null) {
				miDto.setProductGroupCode(obj.getString("INVSORT").toString());
			}
			if (obj.getString("INVNAME") != null) {
				miDto.setProductGroupName(obj.getString("INVNAME").toString());
			}
			if (obj.getString("BAND") != null) {
				miDto.setBrandCode(obj.getString("BAND").toString());
			}
			if (obj.getString("BANDNAME") != null) {
				miDto.setBrandName(obj.getString("BANDNAME").toString());
			}
			if (obj.getString("GBVOLUME") != null
					&& !"".equals(obj.getString("GBVOLUME"))) {
				miDto.setVolume(new BigDecimal(Double.valueOf(obj.getString(
						"GBVOLUME").toString()) / 1000000).setScale(6,
						BigDecimal.ROUND_HALF_UP).doubleValue());
			}
			if (obj.getString("BASECODE") != null) {
				miDto.setBaseCode(obj.getString("BASECODE").toString());
			}
			if (obj.getString("BASENAME") != null) {
				miDto.setBaseName(obj.getString("BASENAME").toString());
			}
			if (obj.getString("TMCS2S_SENDTO") != null) {
				miDto.setDeliveryToCode(obj.getString("TMCS2S_SENDTO")
						.toString());
			}
			if (obj.getString("MAXQTY") != null
					&& !"".equals(obj.getString("MAXQTY"))) {
				miDto.setMaxqty(Integer.parseInt(obj.getString("MAXQTY")
						.toString()));
			}
			if (obj.getString("MINQTY") != null
					&& !"".equals(obj.getString("MINQTY"))) {
				miDto.setMinqty(Integer.parseInt(obj.getString("MINQTY")
						.toString()));
			}
			// TODO
			miDto.setActiveFlag("1");
			miDto.setStatus(1);
			miDto.setCreatedBy("job");
			miDto.setLastUpdBy("job");
			miList.add(miDto);
			i++;
		}
		return miList;
	}

	public static void main(String[] args) {
		Double a = 0.0;
		System.out.println(a / 1000);
		// if(15003%1000 == 0){
		// System.out.println(15003/1000);
		// }else{
		// System.out.println(15003/1000 + 1);
		// }
	}
}
