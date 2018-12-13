package com.haier.svc.services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import com.haier.purchase.data.model.*;
import com.haier.purchase.data.model.CrmGenuineRejectItem;
import com.haier.purchase.data.service.*;
import com.haier.svc.bean.*;
import com.haier.svc.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.purchase.data.model.vehcile.Entry3wOrder;
import com.haier.purchase.data.model.vehcile.OrderLines;
import com.haier.purchase.data.model.vehcile.TmallCAMachineDTO;
import com.haier.svc.bean.gettidanzwdfromlestoehaier.ZWDTABLE2;
import com.haier.svc.bean.getucunioninfofromles.GetKUCUNInfoFromLESToEHAIERResponseStockTrans;
import com.haier.svc.purchase.purchasefromgvs.ObjectFactory;
import com.haier.svc.purchase.purchasefromgvs.TransDNInfoFromEHAIERToGVS;
import com.haier.svc.purchase.purchasefromgvs.TransDNInfoFromEHAIERToGVS_Service;
import com.haier.svc.purchase.purchasefromgvs.ZMMS0003;
import com.haier.svc.purchase.purchasefromgvs.ZSDS0002;
import com.haier.svc.purchase.queryDNFrom.QueryDNFromLEStoAPP;
import com.haier.svc.purchase.queryDNFrom.QueryDNFromLEStoAPP_Service;
import com.haier.svc.purchase.queryDNFrom.ZINTWXWTLOG;
import com.haier.svc.purchase.querywaorderbillfromihs.QueryWAOrderBillFromIHS;
import com.haier.svc.purchase.querywaorderbillfromihs.QueryWAOrderBillFromIHS_Service;
import com.haier.svc.purchase.querywaorderbillfromihs.VWWAOrderBillYTJOutput;
import com.haier.svc.service.T2OrderService;
import com.haier.svc.service.job.T2OrderTimingService;
import com.haier.svc.util.CommUtil;
import com.haier.svc.util.DateCal;
import com.haier.svc.util.WSUtils;
//import com.haier.svc.dao.base.BaseErrDao;
import com.haier.svc.util.XmlUtils2;

//@Configuration
//@EnableScheduling
@Service
public class T2OrderTimingServiceImpl implements T2OrderTimingService{

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(T2OrderTimingServiceImpl.class);

    @Value("${t2OrderResponse.location}")
    private String wsdlLocation;

    private String Begin = "2016-09-20 00:00:00";// 获取crm订单数据参数，开始时间
    private String End = "2020-09-20 00:00:00";// 获取crm订单数据参数，结束时间
    @Autowired
    private PurchaseCrmOrderService purchaseCrmOrderService;
    @Autowired
    private PurchaseT2OrderService purchaseT2OrderService;
    @Autowired
    private PurchaseCrmOrderManualService purchaseCrmOrderManualService;
    @Autowired
    T2OrderModel t2OrderModel;
    @Autowired
    LESTransferOrderModel lesTransferOrderModel;
    @Autowired
    CRMOrderModel crmOrderModel;
    @Autowired
    CrmOrderManualModel crmOrderManualModel;
    @Autowired
    OmsSyncModel	omsSyncModel;
    @Autowired
    RuntimeParametersModel runtimeParametersModel;
    @Autowired
    CnT2PurchaseStockService cnT2PurchaseStockService;
    @Autowired
    private PurchaseTmallCAMachineService tmallCAService;
    @Autowired
    private T2OrderService t2OrderService;
    @Autowired
    private EaiHandlerModel eaiHandlerModel;
    @Autowired
    private EisVOMModel eisVOMModel;
    @Autowired
    private EISStockModel eisStockModel;
    @Autowired
    private OrderOperationLogService orderOperationLogService;
    @Autowired
    private CrmGenuineRejectDataService crmGenuineRejectDataService;
    @Autowired
    private PurchaseT2OrderQueryService purchaseT2OrderQueryService;
	private static String FULFILLED = "FULFILLED";
	@Value("${entryOrderUrl}")
	private String entryOrderUrl;
	@Autowired
	private GateModel gateModel;

//    @Autowired
//	  private ShopOrderRepairLogsService shopOrderRepairLogsService;//退货日志  

	
//	@Transactional(value="txManager1")
    @Override
    public void test(){
        System.out.println("测试定时任务，当前时间："+ new Date());
//        OrderRepairLogs log = new OrderRepairLogs(); //new出来一个退货日志
//		log.setId(shopOrderRepairLogsService.getNextValId());//主键
//		    log.setOrderRepairId(25934630);//退货id
//		    log.setOperator("系统");
//		    log.setOperate("登记");
//		    log.setRemark("淘宝海尔官方旗舰店同步退换货申请");
//		    shopOrderRepairLogsService.insert(log); //记录退货操作流程 日志
    }
    @Override
    public void testAdd(){
        System.out.println("测试添加定时任务，当前时间："+ new Date());
    }

    /**
     * 自动更新CRM订单数据
     *
     * @return
     */

//	@Scheduled(cron = "0 13,43 * * * ? ")
    @Override
    public ServiceResult<Boolean> syncCRMOrderType() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        VWWAOrderBillYTJOutput curData = null;
        try {

//            String path = "file:"
//                    + this.getClass()
//                    .getResource(
//                            wsdlLocation
//                                    + "/QueryWAOrderBillFromIHS.wsdl")
//                    .getPath();
//            System.out.println("wsdl路径=====================" + path);
//            URL url = new URL(path);
        	URL url = this.getClass().getResource(
        			wsdlLocation + "/QueryWAOrderBillFromIHS.wsdl");
            QueryWAOrderBillFromIHS_Service service = new QueryWAOrderBillFromIHS_Service(
                    url);
            QueryWAOrderBillFromIHS soap = service
                    .getQueryWAOrderBillFromIHSSOAP();

            String SysName = "EHAIER";
            String BillCode = "";
            // 款先没有dn的列表
            List<CrmOrderItem> t2list = purchaseT2OrderService.getT2ListToCrm();
            Calendar calendar = Calendar.getInstance();
//			calendar.set(Calendar.HOUR_OF_DAY,
//					calendar.get(Calendar.HOUR_OF_DAY) - 2);// 当前时间往前推两个小时
            calendar.add(Calendar.DATE, -2);
            Date BeginDate = calendar.getTime();
            Date EndDate = new Date();
            List<VWWAOrderBillYTJOutput> output = soap.queryWAOrderBillFromIHS(
                    SysName, BeginDate, EndDate, BillCode);// 之前未修改订单

            List<VWWAOrderBillYTJOutput> output1 = null;
            log.info("没有获取到dn的款先订单数量：" + t2list.size());
            if (t2list.size() > 0) {
                String markQ = "Q";
                String oldId = "WP10";
                String newId = "WN";
                for (int i = 0; i < t2list.size(); i++) {
                    String orderId = t2list.get(i).getOrder_id();
                    String billcode = markQ + orderId.replace(oldId, newId);// wp单号转化为Q单
                    BillCode += billcode + ",";
                }
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                BeginDate = sdf.parse(Begin);
                EndDate = sdf.parse(End);
                log.info("款先BillCode信息：" + BillCode);
                output1 = soap.queryWAOrderBillFromIHS(SysName, BeginDate,
                        EndDate, BillCode.substring(0, BillCode.length() - 1));// 款先直发订单
                output.addAll(output1);// 返回订单信息
            }

            for (VWWAOrderBillYTJOutput info : output) {
                try {
                    log.info("正在更新CRM订单状态信息:" + JsonUtil.toJson(info));

                    curData = info;

                    String billCode1 = info.getBillCode();
                    XMLGregorianCalendar date = info.getBillDate();
                    String flag = info.getFlag();
                    String detail = info.getFaultDetail();
                    String message = info.getMessage();
                    String returninfo = new String("billCode:" + billCode1
                            + " date:" + date + " flag:" + flag + " detail:"
                            + detail + " message:" + message);
                    // System.out.print(returninfo);
                    log.info("QueryWAOrderBillFromIHS response:" + returninfo);
                    // todo 数据库更新
                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("billCode", info.getBillCode());
                    Date dDate = new Date(date.getYear() - 1900,
                            date.getMonth() - 1, date.getDay(), date.getHour(),
                            date.getMinute(), date.getSecond());
                    DateFormat format = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    String reTime = format.format(dDate);
                    params.put("billDate", reTime);
                    params.put("so", info.getSO());
                    params.put("dn", info.getDN());
                    params.put("wdCode", info.getWDCode());
                    params.put("invCode", info.getInvCode());
                    params.put("unitPrice", info.getUnitPrice());
                    params.put("qty", info.getQty());
                    params.put("sumMoney", info.getSumMoney());
                    params.put("bateRate", info.getBateRate());
                    params.put("detail", info.getFaultDetail());
                    params.put("message", info.getMessage());
                    int status = 0;
                    if (info.getFlag().equals("作废"))
                        status = 3;
                    params.put("status", status);

                    String sub_order_id = "";
                    String source_order_id = "";
                    if (info.getBillCode().toUpperCase().startsWith("WM")) {// WM单为WP01缩写，因为CRM单号最大长度为15位，因此用WM表示拆单的手工或自动采购
                        sub_order_id = "WP01" + info.getBillCode().substring(2);
                        if (sub_order_id.length() <= 15) {// 判断长度是否是拆单的情况
                            source_order_id = sub_order_id;
                        } else {
                            source_order_id = sub_order_id.substring(0,
                                    sub_order_id.length() - 2);
                            Integer index = Integer.parseInt(sub_order_id
                                    .substring(sub_order_id.length() - 2,
                                            sub_order_id.length()));
                            sub_order_id = source_order_id + "-" + index;
                        }
                        params.put("source_order_id", source_order_id);
                        params.put("sub_order_id", sub_order_id);
                    } else if (info.getBillCode().toUpperCase()
                            .startsWith("WN")
                            || info.getBillCode().toUpperCase()
                            .startsWith("QWN")) {// WQ为款先直发单QWP10的缩写
                        String temp_order = info.getBillCode();
                        temp_order = temp_order.substring(temp_order
                                .indexOf("WN") + "WN".length());
                        source_order_id = "WP10" + temp_order;
                        params.put("source_order_id", source_order_id);
                        params.put("sub_order_id", source_order_id);
                    } else {
                        sub_order_id = info.getWDCode();
                        source_order_id = info.getWDCode();
                        params.put("source_order_id", info.getWDCode());
                        params.put("sub_order_id", sub_order_id);
                        if (sub_order_id.indexOf("KWN") != -1) {// 处理假款先订单，正常流程不会进入
                            params.put("source_order_id",
                                    source_order_id.replace("KWN", "WP10"));
                            params.put("sub_order_id",
                                    sub_order_id.replace("KWN", "WP10"));
                        }
                    }

                    if (purchaseCrmOrderService.selectCRMOrder(params) == 0) {
                        purchaseCrmOrderService.insertCRMOrder(params);
                    } else {
                        purchaseCrmOrderService.updateCRMOrder(params);
                    }

                    params = new HashMap();

                    if (info.getBillCode().indexOf("WM") > -1
                            || info.getBillCode().indexOf("WN") > -1) {
                        if (StringUtils.isNotEmpty(info.getWDCode())) {
                            params.put("order_id", source_order_id);
                            params.put("wp_order_id", info.getWDCode());
                        } else {
                            params.put("order_id", source_order_id);
                            params.put("wp_order_id", info.getBillCode());
                        }
                    }

                    params.put("flow_flag", 70);
                    params.put("mustHaveDNorSO", "1");

                    purchaseT2OrderService.updateStatus(params);
//                    purchaseCrmOrderManualService.updateStatusFromCRM(params);
                } catch (Exception ex) {
                    log.error("CRM同步采购单发生异常：" + JsonUtil.toJson(info), ex);
                }
            }
            try {
				purchaseCrmOrderManualService.updateCrmOrderManualAfterSync();
				purchaseT2OrderService.updateStatus80FromLES();
			} catch (Exception e) {
				log.warn("更新CRM手工订单出错，程序继续运行。", e);
				e.printStackTrace();
			}
            result.setResult(true);
        } catch (Exception e) {
            log.error("从CRM获取采购单时，发生未知异常", e);
            if (curData != null) {
                log.error("CRM数据:" + JsonUtil.toJson(curData));
            }
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        System.out.println("同步CRM完成=====================");
        return result;
    }

    /**
     * 同步crmT+2订单状态
     */
//	@Scheduled(cron = "0 23,53 * * * ? ")
//	@Scheduled(cron = "0 1/5 * * * ? ")
    @Override
    public void syncCrmT2Status() {
        setCrmT2toOutRRS();
        setCrmT2toInWA();
    }
    //	@Scheduled(cron = "0 23,53 * * * ? ")
    private void setCrmT2toOutRRS() {
        // 查询状态为已入日日顺库的订单
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flow_flag", "60,70");
        params.put("addition_condition",
                " or (rrs_out_time is null and t2.flow_flag = 80)");
        List<String> orders = t2OrderModel.get85DNFromHaierT2(params);
        
        params = new HashMap();
        List flow_flags = new ArrayList();
        flow_flags.add("60");
        flow_flags.add("70");
        params.put("flow_flag", flow_flags);
        List<CrmOrderManualDetailItem> l = crmOrderManualModel
                .getCrmOrderManualList(params);
        if(l != null && l.size() > 0){
        	for(CrmOrderManualDetailItem c : l){
        		orders.add(c.getDn_id());
        	}
        }
        
        // System.out.println(JsonUtil.toJson(orders));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 每次查询数据的最大数量
        final Integer maxPerRequest = 5000;
        // 根据so单号查询rrs出库信息
        if (orders != null && !orders.isEmpty()) {
            log.info(orders.size() + "个CRM订单等待更新出日日顺库状态");

            for (int i = 0; i * maxPerRequest < orders.size(); i++) {
                List<ZBSTKD> l_zbstkd = new ArrayList<ZBSTKD>();
                // 计算本轮结束序号
                int end = (i + 1) * maxPerRequest > orders.size() ? orders
                        .size() : (i + 1) * maxPerRequest;
                List<String> subOrders = orders.subList(i * maxPerRequest,
                        end);
                for (String order : subOrders) {
                    ZBSTKD zbstkd = new ZBSTKD();
                    zbstkd.setBSTKD(order + "D");
                    l_zbstkd.add(zbstkd);
                }
                // ----------------------------------------调用GetTidanZWDFromLESToEHAIER更新状态start-----------------------
                LESOutRRSLedingBillTimeResponse outInfos = lesTransferOrderModel
                        .GetTidanZWDFromLESToEHAIER("", l_zbstkd);

                // 根据出库信息更细t2状态为已出日日顺库
                if (outInfos.getSubRecords() != null
                        && !outInfos.getSubRecords().isEmpty()) {
                    // System.out.println(JsonUtil.toJson(outInfos));

                    for (LESOutRRSLedingBillTimeSubResponse outInfo : outInfos
                            .getSubRecords()) {
                        params = new HashMap();
                        params.put("dn_id", normalDN(outInfo.getBSTKD()));

                        if (outInfo.getAD3() != null
                                && outInfo.getAD3().trim()
                                .equalsIgnoreCase("X")) {// LES已作废

                            params = new HashMap();
                            params.put("so_id", outInfo.getGVS_SO());
                            params.put("flow_flag", -60);
                            crmOrderModel.updateStatusForSOPO(params);

                            Integer max_flow_flag = crmOrderModel
                                    .getMaxFlowFlagInOrder(params);
                            if (max_flow_flag != null && max_flow_flag != 0
                                    && max_flow_flag == -60) {
                                log.info("所有的子订单均为LES已作废:"
                                        + JsonUtil.toJson(outInfo));
                                params.put("flow_flag", -60);
                                t2OrderModel.updateStatusToInWAByDN(params);
                            } else {
                                log.info("子订单最大状态:" + max_flow_flag);
                            }
                        } else {
                            params.put("flow_flag", 70);
                            t2OrderModel.updateStatusToInWAByDN(params);
                            // t2OrderModel.updateOrderStatusToOutRRS(outInfo.getGVS_SO(),
                            // outInfo.getERDAT() + " " + outInfo.getERZET());
                            params = new HashMap();
                            params.put("so_id", outInfo.getGVS_SO());
                            params.put("flow_flag", 70);

                            Date date = null;
                            try {
                                date = sdf.parse(outInfo.getAD1() + " "
                                        + outInfo.getAD2());
                                params.put("rrs_out_time", sdf2.format(date));
                                crmOrderModel.updateStatusForSOPO(params);
                                crmOrderManualModel.updateTimeFromCRM(params);

                                log.info("CRM更新出日日顺库状态:"
                                        + JsonUtil.toJson(params));
                            } catch (ParseException e) {
                                log.error(
                                        "CRM出日日顺时间更新错误:"
                                                + JsonUtil.toJson(params), e);
                            }
                        }
                    }
                }
                // ----------------------------------------调用GetTidanZWDFromLESToEHAIER更新状态end-----------------------
            }

        }

        // 更新CRM手工采购表状态
        
        if (l != null && !l.isEmpty()) {
            List<String> so = new ArrayList<String>();
            for (CrmOrderManualDetailItem item : l) {
                so.add(item.getSo_id());
            }
            List<ZWDTABLE2> outInfos = lesTransferOrderModel
                    .findRRSOutInfoBySo(so);
            // 根据出库信息更细t2状态为已出日日顺库
            if (outInfos != null && !outInfos.isEmpty()) {
                // System.out.println(JsonUtil.toJson(outInfos));
                for (ZWDTABLE2 outInfo : outInfos) {
                    params = new HashMap();
                    params.put("flow_flag", "70");
                    params.put("error_msg", "");
                    params.put("so_id", outInfo.getGVSSO());
                    if(org.apache.commons.lang.StringUtils.isBlank(outInfo.getGVSSO()))
	                    continue;
                    Date date = null;
                    try {
                        crmOrderManualModel.updateStatusFromCRM(params);
                        // 日期时间为空时报错，所以加上判断
                        if (outInfo.getAD1() != null
                                && outInfo.getAD1().length() > 7
                                && outInfo.getAD2() != null
                                && outInfo.getAD2().length() > 5) {
                            date = sdf.parse(outInfo.getAD1() + " "
                                    + outInfo.getAD2());
                            params.put("rrs_out_time", sdf2.format(date));
                            crmOrderManualModel.updateTimeFromCRM(params);
                        }

                        log.info("CRM手工采购更新出日日顺库状态:" + JsonUtil.toJson(params));
                    } catch (ParseException e) {
                        log.error("CRM出日日顺时间更新错误:" + JsonUtil.toJson(params), e);
                    }
                }
            }
        }
    }

    //	@Scheduled(cron = "0 27,57 * * * ? ")
    private void setCrmT2toInWA() {
        // 查询状态为已出日日顺库的订单
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flow_flag", "60,70");
        List<String> l_dn = t2OrderModel.get85DNFromHaierT2(params);
        
        params = new HashMap();
        List flow_flags = new ArrayList();
        flow_flags.add("60");
        flow_flags.add("70");
        params.put("flow_flag", flow_flags);
        List<CrmOrderManualDetailItem> l = crmOrderManualModel
                .getCrmOrderManualList(params);
        
        if(l != null && l.size() > 0){
        	for(CrmOrderManualDetailItem c : l){
        		l_dn.add(c.getDn_id());
        	}
        }
        // List<T2OrderItem> orders =
        // t2OrderModel.findT2OrderMultipleList(params);
        // System.out.println(JsonUtil.toJson(orders));
        // 根据so单号查询wa入库信息
        if (l_dn != null && !l_dn.isEmpty()) {
            log.info(l_dn.size() + "个CRM订单等待更新入WA库状态");
            for (int i = 0; i < l_dn.size(); i++) {
                if (!l_dn.get(i).toLowerCase().endsWith("d")) {
                    String dn = l_dn.get(i);
                    l_dn.set(i, dn + "D");
                }
            }
            List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans> waInfos = lesTransferOrderModel
                    .selectInOutInfoByDn(l_dn);
            // 根据出库信息更细t2状态为已入WA库
            if (waInfos != null && !waInfos.isEmpty()) {
                // System.out.println(JsonUtil.toJson(waInfos));
                for (GetKUCUNInfoFromLESToEHAIERResponseStockTrans waInfo : waInfos) {
                    try {
                        if (!waInfo.getSHKZG().equalsIgnoreCase("S")
                                || !waInfo.getLGORT().toUpperCase()
                                .endsWith("WA"))
                            continue;

                        String dn_normal = normalDN(waInfo.getBSTNK());
                        // t2OrderModel.updateOrderStatusToInWA(waInfo.getVBELNSO(),
                        // waInfo.getCPUDT() + " " + waInfo.getCPUTM());
                        if (StringUtils.isNotEmpty(dn_normal)) {
                            params = new HashMap();
                            params.put("dn_id", dn_normal);
                            params.put("flow_flag", 80);
                            t2OrderModel.updateStatusToInWAByDN(params);

                            params = new HashMap();
                            params.put("dn_id", dn_normal);
                            params.put("flow_flag", 80);
                            params.put("wa_in_time", waInfo.getCPUDT() + " "
                                    + waInfo.getCPUTM());
                            params.put("wa_in_num", waInfo.getLFIMG()
                                    .intValue());
                            params.put("mustHaveDNorSO", "1");
                            crmOrderModel.updateStatusForSOPO(params);

                            crmOrderManualModel.updateStatusFromCRM(params);
                            crmOrderManualModel.updateTimeFromCRM(params);

                            log.info("CRM订单更新入WA库状态:" + JsonUtil.toJson(waInfo));
                        } else {
                            log.warn("DN号为空:" + JsonUtil.toJson(waInfo));
                        }
                    } catch (Exception ex) {
                        log.error("CRM订单更新入WA库状态失败：" + JsonUtil.toJson(waInfo),
                                ex);
                    }
                }
            }
        }

        // 更新CRM手工采购表状态
        
        if (l != null && !l.isEmpty()) {
            List<String> so = new ArrayList<String>();
            for (CrmOrderManualDetailItem item : l) {
                so.add(item.getDn_id());
                so.add(item.getDn_id() + "D");
            }
            List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans> waInfos = lesTransferOrderModel
                    .selectInOutInfoByDn(so);

            // 根据出库信息更细t2状态为已入WA库
            if (waInfos != null && !waInfos.isEmpty()) {
                // System.out.println(JsonUtil.toJson(waInfos));
                for (GetKUCUNInfoFromLESToEHAIERResponseStockTrans waInfo : waInfos) {
                    if (!waInfo.getSHKZG().equalsIgnoreCase("S"))
                        continue;

                    params = new HashMap();
                    String normal_dn = normalDN(waInfo.getBSTNK());
                    if (!StringUtils.isNotEmpty(normal_dn)) {
                        continue;
                    }
                    params.put("dn_id", normal_dn);
                    params.put("arrival_storage_id", waInfo.getLGORT());
                    if (StringUtils.isEmpty(normalDN(waInfo.getBSTNK()))) {
                        continue;
                    }
//                    List<CrmOrderVO> l_vo = crmOrderModel.findCRMOrder(params);
//                    if (l_vo.size() == 0) {
//                        continue;
//                    }

                    String dn_normal = normalDN(waInfo.getBSTNK());
                    params = new HashMap();
                    params.put("flow_flag", "80");
                    params.put("error_msg", "");
                    params.put("dn_id", dn_normal);
                    params.put("wa_in_time",
                            waInfo.getCPUDT() + " " + waInfo.getCPUTM());
                    crmOrderManualModel.updateStatusFromCRM(params);
                     crmOrderManualModel.updateTimeFromCRM(params);

                    log.info("CRM手工采购更新入WA库状态:" + JsonUtil.toJson(params));
                }
            }
        }
        purchaseT2OrderService.updateStatus80FromLES();
    }

    /**
     * 如果DN单号后面有D则去掉
     *
     * @param dn
     * @return
     */
    private String normalDN(String dn) {
        String r = dn;
        if (dn.toLowerCase().endsWith("d")) {
            r = dn.substring(0, dn.length() - 1);
        }
        return r.trim();
    }

    /**
     * OMS订单查询（获取T+2OMS返回信息）
     */
//	@Scheduled(cron = "0 17,47 * * * ? ")
    @Override
    public void syncData() {
        List<String> orderIds = omsSyncModel.getWaitForSyncOrderIds();
        //System.out.println(orderIds.size() + " orders waiting for sync.");
        log.info(orderIds.size() + " orders waiting for sync.");

        long startTime = System.currentTimeMillis();
        omsSyncModel.getOmsOrders(orderIds, 100);
        omsSyncModel.updateOrderStatusModel(10);
        long endTime = System.currentTimeMillis();
        log.info("All Oms Sync Finished in " + (endTime - startTime) + " millisecondes");
    }

    //任务运行间隔短，设置一个是否正在运行的标志。多实例无效。
    static Boolean GetInventoryTranFromLesTwoRunning = false;

    @Override
    public ServiceResult<Calendar> GetInventoryTranFromLesTwo() {
        ServiceResult<Calendar> res = new ServiceResult<>();
        if(GetInventoryTranFromLesTwoRunning == true) return null;
        try{
            String last_error_time = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Map<String, Object> params=new HashMap<String, Object>();
            CrmOrderItem crmOrderItem= purchaseT2OrderQueryService.getIsNullWaInTime(params);
            RuntimeParametersVO vo = runtimeParametersModel.getRuntimeParameterByKey("last_les_sync_time_two");
            String crmOrderItemTime = crmOrderItem.getRrs_out_time_display();
            String voTime=vo.getValue();
            Date nowDate = new Date();

//            Date fDate=sdf2.parse(crmOrderItemTime);
            Date oDate=sdf.parse(voTime);
            long min=(nowDate.getTime()-oDate.getTime())/(1000*60);

            if(crmOrderItem.getOrder_id().equals(vo.getWp_order_id())&&min>20){
                last_error_time = vo.getValue();
            }else{
                last_error_time=crmOrderItem.getRrs_out_time_display();
                RuntimeParametersVO voNew=new RuntimeParametersVO();
                voNew.setKey("last_les_sync_time_two");
                voNew.setWp_order_id(crmOrderItem.getOrder_id());
                voNew.setValue(crmOrderItem.getRrs_out_time_display().replaceAll("-","."));
                runtimeParametersModel.updateRuntimeWpOrderId(voNew);
                last_error_time=last_error_time.replaceAll("-",".");
            }

            Map<String, String> kvMap = new HashMap<String, String>();

            boolean isHasValidTime = true;
            Calendar begin_cal = Calendar.getInstance();
            Calendar end_cal = Calendar.getInstance();
            if (StringUtils.isNotEmpty(last_error_time)) {
                try {
                    begin_cal.setTime(sdf.parse(last_error_time));
//	                end_cal.setTime(begin_cal.getTime());
//	                end_cal.add(Calendar.MINUTE, 10);
                } catch (ParseException e) {
                    log.error("job1__:读取LES最后同步时间出错", e);
                    isHasValidTime = false;
                }
            } else {
                isHasValidTime = false;
            }
            if (!isHasValidTime) {
                log.warn("job1__:未找到LES最后同步时间,默认为当前时间");
                try {
                    String syncTime = lesTransferOrderModel.selectLastSyncTime();
                    log.info("job1__最后更新时间:" + syncTime);
                    begin_cal.setTime(sdf2.parse(syncTime));
//	                end_cal.setTime(begin_cal.getTime());
//	                end_cal.add(Calendar.MINUTE, 10);
                } catch (Exception ex) {
                    log.error("job1__:无法获得最后同步时间", ex);
                }
            }
            begin_cal.set(Calendar.SECOND, 0);
//	        end_cal.set(Calendar.SECOND, 0);

//	        begin_cal.add(Calendar.MINUTE, -22);
            //从开始时间一直执行到当前时间
//	        Long be = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-03-14 10:45:00").getTime();
//        	for(Long time = be; time <= be + 30 * 60 * 1000;){
            for(Long time = begin_cal.getTime().getTime(); time <= end_cal.getTime().getTime();){

                String dateBegin = new SimpleDateFormat("yyyy.MM.dd").format(time);
                String timeBegin = new SimpleDateFormat("HH:mm:ss").format(time);

                //时间加10分钟
                time += 10 * 60 * 1000;

                String dateEnd = new SimpleDateFormat("yyyy.MM.dd").format(time);
                String timeEnd = new SimpleDateFormat("HH:mm:ss").format(time);

                log.info("job1__:dateBegin:" + dateBegin + " dateEnd:" + dateEnd
                        + " timeBegin:" + timeBegin + " timeEnd:" + timeEnd);
                String secType = "";
                // dateBegin = "2014.11.28";
                // dateEnd = "2014.11.28";
                // timeBegin = "10:15:00";
                // timeEnd = "10:40:00";

                res = lesTransferOrderModel
                        .getInventoryTranFromLes(dateBegin, dateEnd, timeBegin,
                                timeEnd, secType);
                if (!res.getSuccess() || res.getResult() == null) {
                    kvMap.put("last_les_sync_time_two",dateBegin+" "+timeBegin);
                    runtimeParametersModel.saveRuntimeParameters(kvMap);
                    return res;
                }

                purchaseCrmOrderManualService.updateStatus80FromLES();
                purchaseT2OrderService.updateStatus80FromLES();
                purchaseCrmOrderManualService.updateTimeInWAFromLES();

                // 更新最后一次同步时间

	        	/*if (end_cal.after(now)) {
				end_cal.set(Calendar.YEAR, now.get(Calendar.YEAR));
				end_cal.set(Calendar.MONTH, now.get(Calendar.MONTH));
				end_cal.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
				end_cal.set(Calendar.HOUR, now.get(Calendar.HOUR));
				end_cal.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
			}*/

                if(end_cal.after(res.getResult())){
                    end_cal = res.getResult();
                }

                kvMap.put("last_les_sync_time_two",dateEnd+" "+timeEnd);
                runtimeParametersModel.saveRuntimeParameters(kvMap);
            }
            return res;
        }catch(Exception e){
            log.warn(e);
            res.setMessage(e.getMessage());
            return res;
        }finally{
            GetInventoryTranFromLesTwoRunning = false;
        }
    }




    //任务运行间隔短，设置一个是否正在运行的标志。多实例无效。
    static Boolean GetInventoryTranFromLesRunning = false;
    /**
     * 入WA库信息查询
     * 从LES获取交易数据和库存数据
     */
//	@Scheduled(cron = "0 1/5 * * * ? ")
    @Override
    public ServiceResult<Calendar> GetInventoryTranFromLes() {
    	ServiceResult<Calendar> res = new ServiceResult<>();
    	if(GetInventoryTranFromLesRunning == true) return null;
    	try{
	        String last_error_time = "";
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Calendar now = Calendar.getInstance();
	        now.setTime(new Date());
	
	        RuntimeParametersVO vo = runtimeParametersModel.getRuntimeParameterByKey("last_les_sync_time");
	
	        if (vo != null)
	            last_error_time = vo.getValue();
	        Map<String, String> kvMap = new HashMap<String, String>();
	
	        boolean isHasValidTime = true;
	        Calendar begin_cal = Calendar.getInstance();
	        Calendar end_cal = Calendar.getInstance();
	        if (StringUtils.isNotEmpty(last_error_time)) {
	            try {
	                begin_cal.setTime(sdf.parse(last_error_time));
//	                end_cal.setTime(begin_cal.getTime());
//	                end_cal.add(Calendar.MINUTE, 10);
	            } catch (ParseException e) {
	                log.error("job1__:读取LES最后同步时间出错", e);
	                isHasValidTime = false;
	            }
	        } else {
	            isHasValidTime = false;
	        }
	        if (!isHasValidTime) {
	            log.warn("job1__:未找到LES最后同步时间,默认为当前时间");
	            try {
	                String syncTime = lesTransferOrderModel.selectLastSyncTime();
	                log.info("job1__最后更新时间:" + syncTime);
	                begin_cal.setTime(sdf2.parse(syncTime));
//	                end_cal.setTime(begin_cal.getTime());
//	                end_cal.add(Calendar.MINUTE, 10);
	            } catch (Exception ex) {
	                log.error("job1__:无法获得最后同步时间", ex);
	            }
	        }
	        begin_cal.set(Calendar.SECOND, 0);
//	        end_cal.set(Calendar.SECOND, 0);
	
//	        begin_cal.add(Calendar.MINUTE, -22);
	        //从开始时间一直执行到当前时间
//	        Long be = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-03-14 10:45:00").getTime();
//        	for(Long time = be; time <= be + 30 * 60 * 1000;){
    		for(Long time = begin_cal.getTime().getTime(); time <= end_cal.getTime().getTime();){

	        	String dateBegin = new SimpleDateFormat("yyyy.MM.dd").format(time);
	        	String timeBegin = new SimpleDateFormat("HH:mm:ss").format(time);
	        	
	        	//时间加10分钟
	        	time += 10 * 60 * 1000;
	        	
	        	String dateEnd = new SimpleDateFormat("yyyy.MM.dd").format(time);
	        	String timeEnd = new SimpleDateFormat("HH:mm:ss").format(time);
	        	
	        	log.info("job1__:dateBegin:" + dateBegin + " dateEnd:" + dateEnd
	        			+ " timeBegin:" + timeBegin + " timeEnd:" + timeEnd);
	        	String secType = "";
	        	// dateBegin = "2014.11.28";
	        	// dateEnd = "2014.11.28";
	        	// timeBegin = "10:15:00";
	        	// timeEnd = "10:40:00";
	        	
	        	res = lesTransferOrderModel
	        			.getInventoryTranFromLes(dateBegin, dateEnd, timeBegin,
	        					timeEnd, secType);
	        	if (!res.getSuccess() || res.getResult() == null) {
                    kvMap.put("last_les_sync_time",dateBegin+" "+timeBegin);
                    runtimeParametersModel.saveRuntimeParameters(kvMap);
	        		return res;
	        	}
	        	
	        	purchaseCrmOrderManualService.updateStatus80FromLES();
	        	purchaseT2OrderService.updateStatus80FromLES();
	        	purchaseCrmOrderManualService.updateTimeInWAFromLES();
	        	
	        	// 更新最后一次同步时间
	        	
	        	/*if (end_cal.after(now)) {
				end_cal.set(Calendar.YEAR, now.get(Calendar.YEAR));
				end_cal.set(Calendar.MONTH, now.get(Calendar.MONTH));
				end_cal.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
				end_cal.set(Calendar.HOUR, now.get(Calendar.HOUR));
				end_cal.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
			}*/
	        	
	        	if(end_cal.after(res.getResult())){
	        		end_cal = res.getResult();
	        	}
	        	
	        	kvMap.put("last_les_sync_time",dateEnd+" "+timeEnd);
	        	runtimeParametersModel.saveRuntimeParameters(kvMap);
	        }
    		return res;
    	}catch(Exception e){
            log.warn(e);
    		res.setMessage(e.getMessage());
    		return res;
    	}finally{
    		GetInventoryTranFromLesRunning = false;
    	}
    }

    /**
     * 推送sap
     */
//    @Scheduled(cron = "0 25/30 * * * ?")
    @Override
	public void cnPurchaseStorageToSap() {
		List<T2OrderItem> t2OrderItemList =  purchaseT2OrderService.findT2OrdersToSap();
//		T2OrderItem ti = new T2OrderItem();
//		try {
//			ti.setOrder_id("WP0218040048976");
//			ti.setWaInTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-04-16 19:06:44"));
//			ti.setMaterials_id("FB28LMM13");
//			ti.setT2_delivery_prediction(new BigDecimal("20"));
//			ti.setStorage_id("HSWA");
//			ti.setDn_id("8573069970");
//			ti.setMblnr("3131736864");
//			t2OrderItemList.add(ti);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		//把要推送的CRM手工订单也查出来，一起推送		张铭	
		try{
			List<CrmOrderManualDetailItem> crmManualOrderItemList = purchaseCrmOrderManualService.findOrdersToSap();
			if(t2OrderItemList == null){
				t2OrderItemList = new ArrayList<>();
			}

			if(crmManualOrderItemList != null && crmManualOrderItemList.size() > 0){
				for(CrmOrderManualDetailItem item : crmManualOrderItemList){
//					if(!"CA".equals(item.getProduct_group_id())) continue;
					T2OrderItem ti = new T2OrderItem();
					ti.setOrder_id(item.getWp_order_id());
					ti.setWaInTime(item.getWa_in_time());
					ti.setMaterials_id(item.getMaterials_id());
					ti.setT2_delivery_prediction(new BigDecimal(item.getQty()));
					ti.setStorage_id(item.getEstorge_id());
					ti.setDn_id(item.getDn_id());
					ti.setMblnr(item.getBillOrderId());
					ti.setProduct_group_id(item.getProduct_group_id());

					t2OrderItemList.add(ti);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (t2OrderItemList == null || t2OrderItemList.size() == 0) {
//			log.info("[cnPurchaseStorageToSap]推送SAP,没有需要处理的数据");
			return;
		}
		String[] message = new String[1];
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (int i = 0; i < t2OrderItemList.size(); i++) {
			try {
				dealAndPushDataV2(t2OrderItemList.get(i), message, sdf);
			} catch (Exception e) {
				message[0] = "[推送SAP，订单编号["
						+ t2OrderItemList.get(i).getOrder_id() + "]发生异常：";
				log.error(message[0], e);
				sb.append("[" + (i + 1) + "]:" + message[0] + e.getMessage()
						+ ";");
			}
		}
	}
	
	
	private boolean dealAndPushDataV2(T2OrderItem t2OrderItem,
			String[] message, SimpleDateFormat sdf) {
		if (t2OrderItem == null)
			return false;
		boolean flag = true;
		
		int index = 1;
		String rsMessage;
		int rsStatus;

		CnT2PurchaseStock cnT2PurchaseStock = new CnT2PurchaseStock();
		cnT2PurchaseStock.setProcessTime(new Date());
		cnT2PurchaseStock.setCnStockSyncsId(t2OrderItem.getOrder_id());
        cnT2PurchaseStock.setCnStockDnId(t2OrderItem.getDn_id());
//		
		String format = sdf.format(t2OrderItem.getWaInTime()).substring(0, 10);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		//当前月份
		String now = df.format(new Date()).substring(4, 6);
		//如果入库日期是本月则填入入库时间，否则填入当前时间

        TransDNInfoFromEHAIERToGVS soap = new TransDNInfoFromEHAIERToGVS_Service(
                getWSDLURL("TransDNInfoFromEHAIERToGVS.wsdl")).getTransDNInfoFromEHAIERToGVSSOAP();
        Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();
        Holder<Integer> exSUBRC = new Holder<Integer>();
        List<ZMMS0003> tZMMS0003 = new ArrayList<ZMMS0003>();

		if("CA".equals(t2OrderItem.getProduct_group_id())
                ||"BB".equals(t2OrderItem.getProduct_group_id())
                    ||"BA".equals(t2OrderItem.getProduct_group_id())
                        ||"BD".equals(t2OrderItem.getProduct_group_id())){
			String mainCode = t2OrderItem.getMaterials_id();
			if(org.apache.commons.lang.StringUtils.isBlank(mainCode)){
				log.error("空调产品物料号应为主物料号！");
				return false;
			}
			List<TmallCAMachineDTO> list = tmallCAService.getByMaterialCode(mainCode);
			if(list == null || list.size() == 0){
				log.error("根据主物料号[" + mainCode + "]查询天猫CA套机接口失败！");
				return false;
			}
			for(int i = 0; i < list.size(); i++){
                ObjectFactory objectFactory = new ObjectFactory();
                ZMMS0003 request = objectFactory.createZMMS0003();
                if(now.equals(format.substring(5, 7))){
                    request.setZSPDT(format);// 订单入库日期
                }else{
                    df = new SimpleDateFormat("yyyy-MM-dd");
                    request.setZSPDT(df.format(new Date()));
                }
                request.setMATNR(list.get(i).getCGB_SUBGBID());// 物料编码
                request.setMENGE(t2OrderItem.getT2_delivery_prediction().stripTrailingZeros());// 交货数量
                request.setLGORT(t2OrderItem.getStorage_id());// 库位
                request.setVBELN(t2OrderItem.getDn_id());
                request.setZSPNB("");
                request.setLIFNR("1");// 供应商
                request.setZLSGI(t2OrderItem.getMblnr());//出库过账凭证
                request.setZFGLG("10");// 批次编号
                request.setPOSNR("000" + index++);// 明细号
                tZMMS0003.add(request);
			}
		}else{
            ObjectFactory objectFactory = new ObjectFactory();
            ZMMS0003 request = objectFactory.createZMMS0003();
            if(now.equals(format.substring(5, 7))){
                request.setZSPDT(format);// 订单入库日期
            }else{
                df = new SimpleDateFormat("yyyy-MM-dd");
                request.setZSPDT(df.format(new Date()));
            }
            request.setMATNR(t2OrderItem.getMaterials_id());// 物料编码
            request.setMENGE(t2OrderItem.getT2_delivery_prediction().stripTrailingZeros());// 交货数量
            request.setLGORT(t2OrderItem.getStorage_id());// 库位
            request.setVBELN(t2OrderItem.getDn_id());
            request.setZSPNB("");
            request.setLIFNR("1");// 供应商
            request.setZLSGI(t2OrderItem.getMblnr());//出库过账凭证
            request.setZFGLG("10");// 批次编号
            request.setPOSNR("000" + index);// 明细号
            tZMMS0003.add(request);
        }

		Long startTime = System.currentTimeMillis();
		
		try {
			soap.transDNInfoFromEHAIERToGVS(tZMMS0003, exSUBRC, tMSG);
			String msg = JsonUtil.toJson(tMSG.value);
			
			List<ZSDS0002> results = tMSG.value;
			if (results == null || results.size() <= 0) {
				rsStatus = EisInterfaceFinance.STATUS_FAILED;
				rsMessage = "EAI 返回空";
				// dataLog.setResponseData("");
			} else {
//				 boolean flag = true;
				for (ZSDS0002 zsds0002 : results) {
					if (!flag)
						break;
					flag = !"E".equalsIgnoreCase(zsds0002.getTYPE());
				}
				cnT2PurchaseStock.setCnStockSyncsId(t2OrderItem.getOrder_id());
                cnT2PurchaseStock.setCnStockDnId(t2OrderItem.getDn_id());
                cnT2PurchaseStock.setAddTime(new Date());
				cnT2PurchaseStock.setProcessTime(new Date());
				if (flag) {
					rsStatus = EisInterfaceFinance.STATUS_SUCCESS;
					cnT2PurchaseStock.setStatus(rsStatus);
					cnT2PurchaseStock.setPushData(JsonUtil.toJson(tZMMS0003));
					cnT2PurchaseStock.setReturnData(msg);
					cnT2PurchaseStock.setMessage("处理并向SAP推送数据成功");
				} else {
					rsStatus = EisInterfaceFinance.STATUS_FAILED;
					cnT2PurchaseStock.setStatus(rsStatus);
					cnT2PurchaseStock.setPushData(JsonUtil.toJson(tZMMS0003));
					cnT2PurchaseStock.setReturnData(msg);
					cnT2PurchaseStock.setMessage("处理并向SAP推送数据失败，需要重新处理");
				}
				rsMessage = msg;
			}
		} catch (Exception e) {
			flag = false;
			rsStatus = EisInterfaceFinance.STATUS_FAILED;
			rsMessage = "调用EAI接口失败";
			cnT2PurchaseStock.setStatus(rsStatus);
			cnT2PurchaseStock.setPushData(JsonUtil.toJson(tZMMS0003));
			cnT2PurchaseStock.setReturnData("");
			cnT2PurchaseStock
			.setMessage("V2-处理并向SAP推送数据[菜鸟采购入库]异常失败，停止处理,异常信息："
					+ e.getMessage());
			log.error("调用EAI接口 transReBackInfoFromEHAIERToGVS 失败：", e);
			// throw new BusinessException("调用EAI接口
			// transReBackInfoFromEHAIERToGVS 失败");
		}
		// dataLog.setCreateTime(DateUtil.currentDateTime());
		message[0] = message[0] + rsMessage;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cnStockSyncsId", t2OrderItem.getOrder_id());
        map.put("cnStockDnId", t2OrderItem.getOrder_id());
        if("CA".equals(t2OrderItem.getProduct_group_id())){
			map.put("pushData", t2OrderItem.getMaterials_id());
		}
		
		List<CnT2PurchaseStock> list = cnT2PurchaseStockService.queryCnT2PurchaseStock(map);
		if (list != null && list.size() > 0) {
			cnT2PurchaseStock.setId(list.get(0).getId());
			cnT2PurchaseStockService.updateCnT2PurchaseStockById(cnT2PurchaseStock);
		} else {
			cnT2PurchaseStockService.addPurchaseStock(cnT2PurchaseStock);
		}
			
		return flag;
	}
	
	public URL getWSDLURL(String wsdlFile) {
		try {
			URL url = this.getClass().getResource(
					wsdlLocation + "/" + wsdlFile);
			return url;
		} catch (Exception e) {
			log.error("WSDL文件路径配置错误或WSDL文件不存在：" + wsdlFile);
			// throw new BusinessException("解析WSDL文件失败，配置错误");
		}
		return null;
	}
	
	/**
	 * 更新lbx、入库时间
	 */
//	@Scheduled(cron = "0 43 13 ? * *")
//  @Scheduled(cron = "0 57 17 ? * *")//测试
	public void addLbx() {
//		List<VehicleOrderDetailsDTO> orderDetailList = vehicleOrderDetailDao
//				.selectByWaitUpdateLbx();
		List<CrmOrderItem> crmOrderList = purchaseCrmOrderService.findWaitUpdateLbxList();
		if (crmOrderList == null || crmOrderList.size() == 0) {
			log.info("[addLbx]更新lbx,没有需要处理的数据");
			return;
		}
		for (CrmOrderItem dto : crmOrderList) {
				Entry3wOrder entry3wOrder = null;
				try {
					entry3wOrder = getEntryOrder(dto.getDn_id());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (entry3wOrder != null) {
					dto.setLbx(entry3wOrder.getEntryOrderId());
					dto.setLbxStatus(entry3wOrder.getStatus());
					dto.setPushStatus("0");
					if (FULFILLED.equals(entry3wOrder.getStatus())) {
//                        SimpleDateFormat sdf = new SimpleDateFormat(
//                                "yyyy-MM-dd HH:mm:ss");
                        if (entry3wOrder.getOperateTime() != null
                                && !"".equals(entry3wOrder.getOperateTime())) {
                                dto.setInTime(entry3wOrder
                                        .getOperateTime());
                        }
                    }
					purchaseCrmOrderService.updateLbxs(dto);
				}
		}
	}
	
	/**
	 * 通过dn单号调用接口获取lbx、入库时间
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
				Map<String, Object> map = XmlUtils2.xmlStrToMap(json
						.fromJson(sb.toString(), ServiceResult.class)
						.getResult().toString());
				if ("success".equals(map.get("flag").toString())) {
					Entry3wOrder entry3wOrder = (Entry3wOrder) XmlUtils2
							.mapToBean(XmlUtils2.xmlStrToMap(map.get(
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
                    String str = map.get("orderLines").toString().replace("<orderLine>","").replace("</orderLine>","");
                    OrderLines orderLines = (OrderLines) XmlUtils2.xmlStrToBean(str,OrderLines.class);

                    if (orderLines.getItemCode() != null || "".equals(orderLines.getItemCode())){
                        entry3wOrder.setItemCode(orderLines.getItemCode());
                    }
                    if (orderLines.getPlanQty() != null || "".equals(orderLines.getPlanQty())){
                        entry3wOrder.setPlanQty(Integer.parseInt(orderLines.getPlanQty()));
                    }
					return entry3wOrder;
				} else {
					Map<String,Object> logMap = new HashMap<>();
					logMap.put("interfaceName","通过85单号调用接口获取lbx、入库时间接口出参");
					logMap.put("interfaceCategory","天猫自动款先订单");
					logMap.put("interfaceDate",new Date());
					logMap.put("interfaceMessage",JSONObject.toJSON(map.get("message").toString())
							.toString());
//					#{interfaceName},#{interfaceCategory},#{interfaceDate},#{interfaceMessage})
					purchaseT2OrderService.insertT2OrderInterfaceLog(map);
				}
			}
		}
		return null;
	}
	
	/**
	 * 物流85和LBX对应关系
	 */
//	@Scheduled(cron = "0 46 19 ? * *")
	public void pushLogistics() {
		List<CrmOrderItem> crmOrderList = purchaseCrmOrderService.findWaitToSapList();
		if (crmOrderList == null || crmOrderList.size() == 0) {
//			log.info("[pushLogistics]物流推送,没有需要处理的数据");
			return;
		}
		URL url = this.getClass().getResource(
				wsdlLocation + "/QueryDNFromLEStoAPP.wsdl");
		QueryDNFromLEStoAPP_Service service = new QueryDNFromLEStoAPP_Service(
				url);
		QueryDNFromLEStoAPP soap = service.getQueryDNFromLEStoAPPSOAP();
		for (CrmOrderItem entry : crmOrderList) {
			com.haier.svc.purchase.queryDNFrom.ObjectFactory objectFactory = new com.haier.svc.purchase.queryDNFrom.ObjectFactory();
			ZINTWXWTLOG zintwxwtlog = objectFactory.createZINTWXWTLOG();
			java.util.List<com.haier.svc.purchase.queryDNFrom.ZINTWXWTLOG> input = new ArrayList<com.haier.svc.purchase.queryDNFrom.ZINTWXWTLOG>();
			zintwxwtlog.setMANDT("700");
			zintwxwtlog.setBSTNK(entry.getDn_id() + "D");// 85单号
			zintwxwtlog.setBSTKD(entry.getDn_id() + "D");
			zintwxwtlog.setPOSNR("ZK");
			zintwxwtlog.setTKNUM("PC");// 订单类别(整车--ZC 款先拼车--PC T+2--T2)
			zintwxwtlog.setSOURCE("TMALL");
			zintwxwtlog.setSOURCESN(entry.getLbx());// lbx
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
			Map<String,Object> logMap = new HashMap<>();
			logMap.put("interfaceName","物流85和LBX对应关系推送入参");
			logMap.put("interfaceCategory","天猫自动款先订单");
			logMap.put("interfaceDate",new Date());
			logMap.put("interfaceMessage",JSONObject.toJSON(input).toString());
			purchaseT2OrderService.insertT2OrderInterfaceLog(logMap);
			logMap.put("interfaceName","物流85和LBX对应关系推送出参");
			logMap.put("interfaceMessage",message.value.toString());
			purchaseT2OrderService.insertT2OrderInterfaceLog(logMap);
			if ("S".equals(flag.value)) {
				// 状态修改为已推送
				entry.setPushStatus("1");
				purchaseCrmOrderService.updateLbxs(entry);
			} else {

			}
		}
	}
	@Override
	public void t2OrderAutoAudit() {
		//获取待内部审核状态的订单
		Map params = new HashMap();
		params.put("flow_flag", new Integer[]{5});
		List<T2OrderItem> list = purchaseT2OrderService.findT2Orders(params);
		
		//组装参数
		params.clear();
		List<String> t2ReviewList = new ArrayList<>();
		for(T2OrderItem item : list){
			t2ReviewList.add(item.getOrder_id());
		}
		params.put("reviewList", t2ReviewList);
		params.put("audit_user", "系统自动");
		params.put("flow_flag", 10);
		params.put("audit_remark", "系统自动审核");
		// 订单审核状态更新
		ServiceResult<Boolean> reviewResult = t2OrderService.reviewT2OrderList(params);
	}

	@Override
	public void syncLimitSum() {
		log.info("总额度滚动");
		// T+3月份取得

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String yearWeek = WSUtils.getWeekOfYear_Sunday(
				sdf2.format(c.getTime()), "yyyy-MM-dd", "0");
		String endDate = CommUtil.weekToSetDateDay(yearWeek, 4);
		DateCal dateCal = new DateCal(endDate);
		Integer reportMonth = Integer.valueOf(dateCal.addWeek(3)
				.substring(5, 7));
		Map params = new HashMap();
		params.put("month", reportMonth);
		// 总额度自动滚动
		// 当月总额度取得
		List<GateLimitSumItem> result = gateModel.findLimitSum(params);
		GateLimitSumItem nowMonthData = result.get(0);
		// 已经滚动的场合,退出
		if (nowMonthData.getUse_flag() == 1) {
			return;
		}
		// 前月总额度取得
		Integer preMonth;
		if (reportMonth == 1) {
			preMonth = 12;
		} else {
			preMonth = reportMonth - 1;
		}
		params.put("month", preMonth);
		List<GateLimitSumItem> resultPre = gateModel.findLimitSum(params);
		GateLimitSumItem preMonthData = resultPre.get(0);
		// 当月数据更新
		nowMonthData.setLimit_sum_num(preMonthData.getLimit_sum_num());
		nowMonthData.setUse_flag(1);
		gateModel.updateLimitSumByMonth(nowMonthData);
		// 上月数据更新
		preMonthData.setUse_flag(0);
		gateModel.updateLimitSumByMonth(preMonthData);
		// 个别额度再设定
		params = new HashMap();
		params.put("month", reportMonth);
		// 检索参数params中传入needResult 不检索总计
		params.put("needResult", "part");
		// 在DB中检索详细信息
		List<GateOfLimitItem> gateOfLimitList = gateModel
				.selectGateOfLimit(params);
		// 总指标
		BigDecimal totalTarget = new BigDecimal(0);
		// 计算总指标
		for (GateOfLimitItem gateOfLimitItem : gateOfLimitList) {
			totalTarget = totalTarget.add(new BigDecimal(gateOfLimitItem
					.getTarget_num()));
		}
		// 定义剩余额度
		BigDecimal remainLimit = nowMonthData.getLimit_sum_num();
		// 计算指标比例，并分配额度
		for (Iterator<GateOfLimitItem> it = gateOfLimitList.iterator(); it
				.hasNext();) {
			GateOfLimitItem gateOfLimitItem = it.next();
			// 根据指标计算额度
			BigDecimal limitNum = nowMonthData.getLimit_sum_num()
					.multiply(new BigDecimal(gateOfLimitItem.getTarget_num()))
					.divide(totalTarget, 2, BigDecimal.ROUND_HALF_UP);
			// 设置修改人
			gateOfLimitItem.setModify_user("systemAuto");
			if (it.hasNext()) {
				gateOfLimitItem.setLimit_num(String.valueOf(limitNum));
				// 计算剩余额度
				remainLimit = remainLimit.subtract(limitNum);
			} else {
				gateOfLimitItem.setLimit_num(remainLimit.toString());
			}

		}

		// 更新处理
		gateModel.updateGateOfLimitById(gateOfLimitList);

	}

    /**
     * 定时更新信息不全的物料基本信息和库龄表的物料信息(Job触发)
     * @return
     */
    @Override
//    @Scheduled(cron = "*/60 * * * * ?")
    public void syncUpdateMtlInfoBySku() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            eaiHandlerModel.updateMtlInfoBySku();
            result.setResult(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("定时更新信息不全的物料基本信息和库龄表的物料信息时发生未知异常");
            log.error("定时更新信息不全的物料基本信息和库龄表的物料信息时发生未知异常：" + e);
        }
    }

    @Override
    public ServiceResult<Boolean> processVomReceivedQueueFailStatus() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            //目前只处理正品退货
            eisVOMModel.processQualityGoods();
            result.setResult(true);
        } catch (Exception e) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("处理正品退货拒单情况下失败的数据出现异常：" + e.getMessage());
            log.error("处理正品退货拒单情况下失败的数据出现异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> processStockBusinessQueues() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            eisStockModel.processStockBusinessQueues();
        } catch (Exception e) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("处理库存变化关联的业务出现未知异常：" + e.getMessage());
        }
        return result;
    }

    /**
     * 如果SO单号后面没有S则加上
     *
     * @param so
     * @return
     */
    private String suffixSO(String so) {
        String r = so;
        if (!so.toLowerCase().endsWith("s")) {
            r += "S";
        }
        return r;
    }

    @Override
    public void updateCrmRejectOrderInRRS() {

        // 查询状态为已出wa库的订单
        Map<String, Object> params = new HashMap<String, Object>();
        List<String> flag = new ArrayList<String>();
        flag.add("30");
        params.put("flow_flag", flag);
        List<CrmGenuineRejectItem> outWA = crmGenuineRejectDataService
                .getCrmGenuineRejectList(params);
        log.info("待同步件数：" + outWA.size());
        // System.out.println(JsonUtil.toJson(outWA));
        for (CrmGenuineRejectItem item : outWA) {
            List<String> bstkd = new ArrayList<String>();
            bstkd.add(suffixSO(item.getSo_id()));
            // 查询出RRS库状态
            LESTransferOutInPutOrderResponse rrsStatus = lesTransferOrderModel
                    .QueryDNinfoFromLEStoEhaier("1", bstkd);
            log.info("CRM同步入日日顺时间：" + JsonUtil.toJson(rrsStatus));
            // System.out.println(JsonUtil.toJson(rrsStatus));
            if (rrsStatus.getFLAG() == null
                    || !rrsStatus.getFaultDETAIL().equalsIgnoreCase("S")) {
                // orderOperationLog(item.getWp_order_id(), 0,
                // "正品退货更新状态从已出wa库到已入日日顺库失败，查询RRS库状态失败");
                continue;
            }
            List<LESTransferOutInPutOrderSubResponse> list = rrsStatus
                    .getSubRecords();
            if (list == null || list.isEmpty()) {
				/*
				 * orderOperationLog(item.getWp_order_id(), 0,
				 * "正品退货更新状态从已出wa库到已入日日顺库失败，中转出入库,入日日顺时间(LES)没有数据");
				 */
                continue;
            }
            int i = 0;
            List<CrmGenuineRejectItem> crmItem = new ArrayList<CrmGenuineRejectItem>();
            for (LESTransferOutInPutOrderSubResponse order : list) {
                if (!order.getFLAG_RK().equalsIgnoreCase("C")) {
                    continue;
                }
                String timeStr = order.getERZET_RK();
                if (timeStr.contains(".")) {
                    timeStr = timeStr.substring(0, timeStr.indexOf("."));
                }
                // 更新订单状态为已入日日顺库
                crmGenuineRejectDataService.updateStatusToInRRS(item.getSo_id(),
                        order.getERDAT_RK() + " " + timeStr);
                i++;
            }
            if (i > 0) {
                // TODO 正品退货 更新状态写入日志
                orderOperationLog(item.getWp_order_id(), 1,
                        "正品退货更新状态从已出wa库到已入日日顺库成功");
            }
        }
    }


    private void orderOperationLog(String order_id, int success, String remark) {
        Map<String, Object> logMap = new HashMap<String, Object>();
        logMap.put("order_id", order_id);
        logMap.put("type", 30);
        logMap.put("operate_user", "定时任务");
        logMap.put("is_sucess", success);
        logMap.put("content", "CRM更新状态从已出wa库到已入日日顺库");
        logMap.put("remark", remark);
        logMap.put("system", "采购平台");
        try {
            orderOperationLogService.createOrderOperationLog(logMap);
        } catch (Exception e) {
            log.error("操作日志写入失败：", e);
        }

    }

}
