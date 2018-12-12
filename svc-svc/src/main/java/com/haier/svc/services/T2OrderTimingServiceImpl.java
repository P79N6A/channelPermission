package com.haier.svc.services;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.purchase.data.model.CnT2PurchaseStock;
import com.haier.purchase.data.model.CrmOrderItem;
import com.haier.purchase.data.model.CrmOrderManualDetailItem;
import com.haier.purchase.data.model.CrmOrderVO;
import com.haier.purchase.data.model.RuntimeParametersVO;
import com.haier.purchase.data.model.T2OrderItem;
import com.haier.purchase.data.service.CnT2PurchaseStockService;
import com.haier.purchase.data.service.PurchaseCrmOrderManualService;
import com.haier.purchase.data.service.PurchaseCrmOrderService;
import com.haier.purchase.data.service.PurchaseT2OrderService;
import com.haier.shop.service.ShopOrderRepairLogsService;
import com.haier.svc.bean.LESOutRRSLedingBillTimeResponse;
import com.haier.svc.bean.LESOutRRSLedingBillTimeSubResponse;
import com.haier.svc.bean.ZBSTKD;
import com.haier.svc.bean.gettidanzwdfromlestoehaier.ZWDTABLE2;
import com.haier.svc.bean.getucunioninfofromles.GetKUCUNInfoFromLESToEHAIERResponseStockTrans;
import com.haier.svc.model.CRMOrderModel;
import com.haier.svc.model.CrmOrderManualModel;
import com.haier.svc.model.LESTransferOrderModel;
import com.haier.svc.model.OmsSyncModel;
import com.haier.svc.model.RuntimeParametersModel;
import com.haier.svc.model.T2OrderModel;
import com.haier.svc.purchase.purchasefromgvs.ObjectFactory;
import com.haier.svc.purchase.purchasefromgvs.TransDNInfoFromEHAIERToGVS;
import com.haier.svc.purchase.purchasefromgvs.TransDNInfoFromEHAIERToGVS_Service;
import com.haier.svc.purchase.purchasefromgvs.ZMMS0003;
import com.haier.svc.purchase.purchasefromgvs.ZSDS0002;
import com.haier.svc.purchase.querywaorderbillfromihs.QueryWAOrderBillFromIHS;
import com.haier.svc.purchase.querywaorderbillfromihs.QueryWAOrderBillFromIHS_Service;
import com.haier.svc.purchase.querywaorderbillfromihs.VWWAOrderBillYTJOutput;
import com.haier.svc.service.job.T2OrderTimingService;
//import com.haier.svc.dao.base.BaseErrDao;

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
                    purchaseCrmOrderManualService.updateStatusFromCRM(params);
                } catch (Exception ex) {
                    log.error("CRM同步采购单发生异常：" + JsonUtil.toJson(info), ex);
                }
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
//	@Scheduled(cron = "0 0/5 * * * ? ")
    @Override
    public void syncCrmT2Status() {
        // System.out.println("syncCrmT2Status");
        setCrmT2toOutRRS();
        setCrmT2toInWA();
    }
    //	@Scheduled(cron = "0 23,53 * * * ? ")
    private void setCrmT2toOutRRS() {
        // 查询状态为已入日日顺库的订单
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("flow_flag", "60");
        params.put("flow_flag", "70");
        params.put("addition_condition",
                " or (rrs_out_time is null and t2.flow_flag = 80)");
        List<String> orders = t2OrderModel.get85DNFromHaierT2(params);
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
                        end - 1);
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
        params = new HashMap();
        List flow_flags = new ArrayList();
        flow_flags.add("60");
        flow_flags.add("70");
        params.put("flow_flag", flow_flags);
        List<CrmOrderManualDetailItem> l = crmOrderManualModel
                .getCrmOrderManualList(params);
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
        params = new HashMap();
        List flow_flags = new ArrayList();
        flow_flags.add("60");
        flow_flags.add("70");
        params.put("flow_flag", flow_flags);
        List<CrmOrderManualDetailItem> l = crmOrderManualModel
                .getCrmOrderManualList(params);
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
                    List<CrmOrderVO> l_vo = crmOrderModel.findCRMOrder(params);
                    if (l_vo.size() == 0) {
                        continue;
                    }

                    String dn_normal = normalDN(waInfo.getBSTNK());
                    params = new HashMap();
                    params.put("flow_flag", "80");
                    params.put("error_msg", "");
                    params.put("dn_id", dn_normal);
                    params.put("wa_in_time",
                            waInfo.getCPUDT() + " " + waInfo.getCPUTM());
                    crmOrderManualModel.updateStatusFromCRM(params);
                    // crmOrderManualModel.updateTimeFromCRM(params);

                    log.info("CRM手工采购更新入WA库状态:" + JsonUtil.toJson(params));
                }
            }
        }
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

    /**
     * 入WA库信息查询
     * 从LES获取交易数据和库存数据
     */
//	@Scheduled(cron = "0/5 * * * * ? ")
    @Override
    public void GetInventoryTranFromLes() {
        String last_error_time = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());

        RuntimeParametersVO vo = runtimeParametersModel
                .getRuntimeParameterByKey("last_les_sync_time");

        if (vo != null)
            last_error_time = vo.getValue();
        Map<String, String> kvMap = new HashMap<String, String>();

        boolean isHasValidTime = true;
        Calendar begin_cal = Calendar.getInstance();
        Calendar end_cal = Calendar.getInstance();
        if (StringUtils.isNotEmpty(last_error_time)) {
            try {
                begin_cal.setTime(sdf.parse(last_error_time));
                end_cal.setTime(begin_cal.getTime());
                end_cal.add(Calendar.MINUTE, 10);
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
                end_cal.setTime(begin_cal.getTime());
                end_cal.add(Calendar.MINUTE, 10);
            } catch (Exception ex) {
                log.error("job1__:无法获得最后同步时间", ex);
            }
        }
        begin_cal.set(Calendar.SECOND, 0);
        end_cal.set(Calendar.SECOND, 0);

        String dateEnd = new SimpleDateFormat("yyyy.MM.dd").format(end_cal
                .getTime());
        String timeEnd = new SimpleDateFormat("HH:mm:ss").format(end_cal
                .getTime());

        String dateBegin = new SimpleDateFormat("yyyy.MM.dd").format(begin_cal
                .getTime());
        String timeBegin = new SimpleDateFormat("HH:mm:ss").format(begin_cal
                .getTime());
        log.info("job1__:dateBegin:" + dateBegin + " dateEnd:" + dateEnd
                + " timeBegin:" + timeBegin + " timeEnd:" + timeEnd);
        String secType = "";
        // dateBegin = "2014.11.28";
        // dateEnd = "2014.11.28";
        // timeBegin = "10:15:00";
        // timeEnd = "10:40:00";

        ServiceResult<Calendar> r = lesTransferOrderModel
                .getInventoryTranFromLes(dateBegin, dateEnd, timeBegin,
                        timeEnd, secType);
        if (!r.getSuccess() || r.getResult() == null) {
            return;
        }

        // 更新最后一次同步时间

		/*if (end_cal.after(now)) {
			end_cal.set(Calendar.YEAR, now.get(Calendar.YEAR));
			end_cal.set(Calendar.MONTH, now.get(Calendar.MONTH));
			end_cal.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
			end_cal.set(Calendar.HOUR, now.get(Calendar.HOUR));
			end_cal.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
		}*/

        if(end_cal.after(r.getResult())){
            end_cal = r.getResult();
        }

        kvMap.put("last_les_sync_time", sdf.format(end_cal.getTime()));
        runtimeParametersModel.saveRuntimeParameters(kvMap);
    }

    /**
     * 推送sap
     */
//    @Scheduled(cron = "0 25/30 * * * ?")
    @Override
	public void cnPurchaseStorageToSap() {
		List<T2OrderItem> t2OrderItemList = purchaseT2OrderService.findT2OrdersToSap();
		// List<CnT2PurchaseStock> cn3wPurchaseStockList = cn3wPurchaseStockDao
		// .getPreparedProcessData();
		if (t2OrderItemList == null || t2OrderItemList.size() == 0) {
			log.info("[cnPurchaseStorageToSap]推送SAP,没有需要处理的数据");
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
		int rsStatus;
		String rsMessage;
		ObjectFactory objectFactory = new ObjectFactory();
		ZMMS0003 request = objectFactory.createZMMS0003();
		CnT2PurchaseStock cnT2PurchaseStock = new CnT2PurchaseStock();
		cnT2PurchaseStock.setCnStockSyncsId(t2OrderItem.getOrder_id());
		String format = sdf.format(t2OrderItem.getWaInTime());
		request.setZSPDT(format.substring(0, 10));// 订单入库日期(采购订单如何获取)
		request.setMATNR(t2OrderItem.getMaterials_id());// 物料编码
		request.setMENGE(t2OrderItem.getT2_delivery_prediction());// 交货数量
		request.setLGORT(t2OrderItem.getStorage_id());// 库位
		request.setVBELN(t2OrderItem.getDn_id());
		request.setZSPNB("");
		request.setLIFNR("1");// 供应商
		request.setZLSGI(t2OrderItem.getMblnr());//出库过账凭证
		request.setZFGLG("10");// 批次编号
		request.setPOSNR("1");// 明细号
		
		TransDNInfoFromEHAIERToGVS soap = new TransDNInfoFromEHAIERToGVS_Service(
				getWSDLURL("TransDNInfoFromEHAIERToGVS.wsdl")).getTransDNInfoFromEHAIERToGVSSOAP();
		Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();
		Holder<Integer> exSUBRC = new Holder<Integer>();
		List<ZMMS0003> tZMMS0003 = new ArrayList<ZMMS0003>();
		tZMMS0003.add(request);

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
				cnT2PurchaseStock.setAddTime(new Date());
				cnT2PurchaseStock.setProcessTime(new Date());
				if (flag) {
					rsStatus = EisInterfaceFinance.STATUS_SUCCESS;
					cnT2PurchaseStock.setStatus(1);
					cnT2PurchaseStock.setPushData(JsonUtil.toJson(tZMMS0003));
					cnT2PurchaseStock.setReturnData(msg);
					cnT2PurchaseStock.setMessage("处理并向SAP推送数据成功");
				} else {
					rsStatus = EisInterfaceFinance.STATUS_FAILED;
					cnT2PurchaseStock.setStatus(0);
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
			cnT2PurchaseStock.setStatus(2);
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
		List<CnT2PurchaseStock> list = cnT2PurchaseStockService.queryCnT2PurchaseStock(map);
		if (list != null && list.size() > 0) {
			cnT2PurchaseStockService.updateCnT2PurchaseStock(cnT2PurchaseStock);
		} else {
			cnT2PurchaseStockService.addPurchaseStock(cnT2PurchaseStock);
		}
		return flag;
	}
	
	public URL getWSDLURL(String wsdlFile) {
		try {
			URL url = this.getClass().getResource(
					wsdlLocation + wsdlFile);
			return url;
		} catch (Exception e) {
			log.error("WSDL文件路径配置错误或WSDL文件不存在：" + wsdlFile);
			// throw new BusinessException("解析WSDL文件失败，配置错误");
		}
		return null;
	}


}
