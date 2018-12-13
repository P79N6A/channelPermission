package com.haier.logistics.Model;

import com.alibaba.fastjson.JSON;
import com.haier.common.BusinessException;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.logistics.Util.XmlUtil;
import com.haier.shop.model.AllotNetPoint;
import com.haier.shop.model.ApiLogs;
import com.haier.shop.model.HpAllotNetPoint;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.service.AllotNetPointService;
import com.haier.shop.service.ApiLogsService;
import com.haier.shop.service.OrderProductsNewService;
import com.haier.shop.service.ShopOrderOperateLogsService;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class HpAllotNetPointModel {
    @Autowired
    private ApiLogsService apiLogsService;
    @Autowired
    private AllotNetPointService allotNetPointService;
    @Autowired
    private OrderProductsNewService orderProductsNewService;
    @Autowired
    private ShopOrderOperateLogsService  shopOrderOperateLogsService;
    private static org.apache.log4j.Logger log     = org.apache.log4j.LogManager
            .getLogger(HpAllotNetPointModel.class);
    private String                         FAILED  = "N";
    private String                         SUCCESS = "Y";
    public String saveNetPoint(String requestXml){
        long startTime = System.currentTimeMillis();
        HpAllotNetPoint hpAllotNetPoint = parseNetPointXML(requestXml);
        if (hpAllotNetPoint == null) {
            saveApiLogs("", requestXml, "", "解析HP分配网点信息时xml数据错误", ApiLogs.FLAG_FAIL);
            return responseMsg(FAILED, "", "解析HP分配网点信息时xml数据错误");
        }
        //保存xml数据
        int apiLogsId = saveApiLogs("", requestXml, "", "接收xml数据成功", ApiLogs.FLAG_SUCCESS);
        List<AllotNetPoint> allotNetPoints = hpAllotNetPoint.getItem();
        if (allotNetPoints == null || allotNetPoints.isEmpty()) {
            saveApiLogs("", requestXml, "", "解析出数据但为空", ApiLogs.FLAG_FAIL);
            return responseMsg(FAILED, "", "解析HP分配网点信息时xml数据错误");
        }
        //验证数据
        Set<String> netPointSet = new HashSet<String>();
        List<Map<String, Object>> netPointList = allotNetPointService.getNetPoints();
        for (Map<String, Object> map : netPointList) {
            netPointSet.add(map.get("netPointN8").toString());
        }
        StringBuffer response = new StringBuffer();
        for (AllotNetPoint netPoint : allotNetPoints) {

            if (StringUtil.isEmpty(netPoint.getORDER_NO(), true)) {
                response.append(responseMsg(FAILED, netPoint.getORDER_NO(), "网单号为空"));
                netPoint.setStatus(AllotNetPoint.STATUS_FAIL);
                netPoint.setMessage("网单号为空");
                continue;
            }
            OrderProductsNew orderProducts = orderProductsNewService.getByCOrderSn(netPoint.getORDER_NO());
            if (null == orderProducts||null == orderProducts.getId()){
                response.append(responseMsg(FAILED, netPoint.getORDER_NO(), "网单不存在"));
                netPoint.setStatus(AllotNetPoint.STATUS_FAIL);
                netPoint.setMessage("查询网单数据为空");
                continue;
            }
            if (null != orderProducts.getStatus() &&
                orderProducts.getStatus().intValue() == OrderProductsNew.STATUS_CANCEL_CLOSE.intValue()){
                response.append(responseMsg(FAILED, netPoint.getORDER_NO(), "网单已关闭"));
                netPoint.setStatus(AllotNetPoint.STATUS_FAIL);
                netPoint.setMessage("网单已关闭");
                continue;
            }
            //【接受HP回传接口】每一个网单记录一次日志 xinm 2016-6-14
            saveApiLogs(netPoint.getORDER_NO(), JsonUtil.toJson(netPoint), "", "接收xml数据,单条解析成功",
                    ApiLogs.FLAG_SUCCESS);

            String customerCode = netPoint.getCUSTOMER_CODE();
            if (StringUtil.isEmpty(customerCode, true)) {
                String procRemark = netPoint.getPROC_REMARK();
                if ("型号解析错误".equals(procRemark)) {
                    procRemark = procRemark + "(请商城相关人员尽快到HP系统中维护此型号)";
                } else {
                    procRemark = procRemark + "网点8码为空";
                }
                //记录订单操作日志
                if (orderProducts != null) {
                    saveOrderProductLog(orderProducts, procRemark, "HP改配网点", 0);
                }
                response.append(responseMsg(FAILED, netPoint.getORDER_NO(), procRemark));
                netPoint.setStatus(AllotNetPoint.STATUS_FAIL);
                netPoint.setMessage(procRemark);
                continue;
            } else {
                if (!netPointSet.contains(customerCode)) {
                    response.append(responseMsg(FAILED, netPoint.getORDER_NO(),
                            customerCode + "网点在海尔商城不存在，请及时维护"));
                    netPoint.setStatus(AllotNetPoint.STATUS_FAIL);
                    //记录订单操作日志
                    if (orderProducts != null) {
                        saveOrderProductLog(orderProducts, customerCode + "网点在海尔商城不存在，请及时维护",
                                "HP改配网点", 0);
                    }
                    netPoint.setMessage(customerCode + "网点在海尔商城不存在，请及时维护");
                    continue;
                }
            }

            if (StringUtil.isEmpty(netPoint.getENTER_TIME(), true)) {
                response.append(responseMsg(FAILED, netPoint.getORDER_NO(), "登记时间为空"));
                netPoint.setStatus(AllotNetPoint.STATUS_FAIL);
                netPoint.setMessage("登记时间为空");
                continue;
            } else {
                try {
                    DateParseToString(netPoint.getENTER_TIME());
                } catch (Exception e) {
                    response.append(responseMsg(FAILED, netPoint.getORDER_NO(), "登记时间格式有误"));
                    netPoint.setStatus(AllotNetPoint.STATUS_FAIL);
                    netPoint.setMessage("登记时间格式有误");
                    continue;
                }
            }

            if (StringUtil.isEmpty(netPoint.getASSIGN_DATE(), true)) {
                response.append(responseMsg(FAILED, netPoint.getORDER_NO(), "派工成功时间为空"));
                netPoint.setStatus(AllotNetPoint.STATUS_FAIL);
                netPoint.setMessage("派工成功时间为空");
                continue;
            } else {
                try {
                    DateParseToString(netPoint.getASSIGN_DATE());
                } catch (Exception e) {
                    response.append(responseMsg(FAILED, netPoint.getORDER_NO(), "派工成功时间格式有误"));
                    netPoint.setStatus(AllotNetPoint.STATUS_FAIL);
                    netPoint.setMessage("派工成功时间格式有误");
                    continue;
                }
            }

            netPoint.setStatus(AllotNetPoint.STATUS_INITIAL);
            response.append(responseMsg(SUCCESS, netPoint.getORDER_NO(), "成功！"));
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //TransactionStatus status = transactionManager.getTransaction(def);
        try {
            Integer temp = allotNetPoints.size();
            int bathchNum = 1000;
            for (int i = 0; i < allotNetPoints.size(); i = i + bathchNum) {
                if (temp <= bathchNum) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("apiLogsId", apiLogsId);
                    params.put("allotNetPointList",
                            allotNetPoints.subList(i, allotNetPoints.size()));
                    allotNetPointService.batchInsert(params);
                } else {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("apiLogsId", apiLogsId);
                    params.put("allotNetPointList", allotNetPoints.subList(i, i + bathchNum));
                    allotNetPointService.batchInsert(params);
                }
                temp = temp - bathchNum;
            }
            //更新返回的结果到日志表
            apiLogsService.updateReturnDataById(apiLogsId, response.toString());
            //transactionManager.commit(status);
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            log.info("保存HP分配网点信息条数:" + allotNetPoints.size() + ",总共执行时间:" + time + "毫秒,平均每条"
                    + time / allotNetPoints.size() + "毫秒");
            return response.toString();
        } catch (Exception e) {
            //回滚事务
            //transactionManager.rollback(status);
            saveApiLogs("", requestXml, "", "保存HP分配网点信息失败", ApiLogs.FLAG_FAIL);
            log.error("保存HP分配网点信息失败", e);
            throw new BusinessException("保存HP分配网点信息失败");
        }
    }

    /**
     * 从VOM解析网单数据保存
     * @param content json格式数据
     * @return 处理结果
     */
    public String saveNetPointFromVom(String content){
        //数据不再保存apilogs,因为从VOM推送过来的时候已经解析到vom_shipping_status表中
        //apilogid改为保存vom_shipping_status的id
        //解析网点数据信息
        AllotNetPoint netPoint = JSON.parseObject(content,AllotNetPoint.class);
        //默认初始状态
        netPoint.setStatus(AllotNetPoint.STATUS_INITIAL);
        //缓存处理信息
        StringBuffer response = new StringBuffer();
        OrderProductsNew orderProducts = orderProductsNewService.getByCOrderSn(netPoint.getORDER_NO());
        if (null == orderProducts||null == orderProducts.getId()){
            response.append("查询网单不存在");
            //失败状态
            netPoint.setStatus(AllotNetPoint.STATUS_FAIL);
        }

        if (null != orderProducts.getStatus() &&
            orderProducts.getStatus().intValue() == OrderProductsNew.STATUS_CANCEL_CLOSE.intValue()){
            response.append("网单已关闭,无法分配网点");
            netPoint.setStatus(AllotNetPoint.STATUS_FAIL);
        }

        //网点8码数据验证,查看网点8码是否存在
        Set<String> netPointSet = new HashSet<String>();
        List<Map<String, Object>> netPointList = allotNetPointService.getNetPoints();
        for (Map<String, Object> map : netPointList) {
            netPointSet.add(map.get("netPointN8").toString());
        }
        if (!netPointSet.contains(netPoint.getCUSTOMER_CODE())) {
            response.append(netPoint.getCUSTOMER_CODE() + "网点在海尔商城不存在，请及时维护");
            netPoint.setStatus(AllotNetPoint.STATUS_FAIL);
            //记录订单操作日志
            if (orderProducts != null) {
                saveOrderProductLog(orderProducts, netPoint.getCUSTOMER_CODE() + "网点在海尔商城不存在，请及时维护",
                    "HP改配网点", 0);
            }
        }

        //记录处理结果
        netPoint.setMessage(response.toString());
        //保存网点数据
        int num = allotNetPointService.insert(netPoint);
        if (num == 1){
            return "保存网点数据成功";
        }
        return response.toString();


    }
    /**
     * 解析HP分配网点信息
     * @param requestXml
     * @return
     */
    private HpAllotNetPoint parseNetPointXML(String requestXml) {

        HpAllotNetPoint hpAllotNetPoint = null;
        try {
            hpAllotNetPoint = (HpAllotNetPoint) XmlUtil.jAXBunmarshalString(requestXml,
                    HpAllotNetPoint.class);
        } catch (Exception e) {
            log.error("解析HP分配网点信息时xml数据错误", e);
        }
        return hpAllotNetPoint;
    }
    /**
     * 记录日志
     * @param cOrderSn 网单编号
     * @param requestXml 接收的数据
     * @param responseXml 处理结果
     * @param message 处理消息
     * @param flag 成功与失败标识 1-成功；0-失败
     */
    public int saveApiLogs(String cOrderSn, String requestXml, String responseXml, String message,
                           int flag){

        ApiLogs apiLogs = new ApiLogs();
        apiLogs.setFlag(flag);
        apiLogs.setResultCode("");
        apiLogs.setPushData("");
        apiLogs.setPullData(requestXml);
        apiLogs.setReturnData(responseXml);
        apiLogs.setCOrderSn(cOrderSn);
        apiLogs.setType(ApiLogs.TYPE_HPPULL);
        apiLogs.setAddTime(new Date().getTime() / 1000);
        apiLogs.setAddDateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        apiLogs.setMessage(message);
        System.out.println("插入前主键值为 :"+apiLogs.getId());
        int x=apiLogsService.insert(apiLogs);
        apiLogs.setId(x);
        System.out.println("插入后主键值为 :"+apiLogs.getId());
        return apiLogs.getId();
    }
    private String responseMsg(String flag, String cOrderSn, String msg) {
        return "\n<ITEM>" + "<FLAG>" + flag + "</FLAG>" + "<MSG>" + msg + "</MSG>" + "<ORDERNUM>"
                + cOrderSn + "</ORDERNUM>" + "</ITEM>";
    }
    /**
     * 转换时间格式为字符型
     * @param dateStr
     * @return
     */
    private String DateParseToString(String dateStr) {
        if (StringUtil.isEmpty(dateStr, true)) {
            return "";
        }
        Date date = DateUtil.parse(dateStr, "yyyy-MM-dd'T'HH:mm:ss");
        return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
    }
    /**
     * 记录网单操作日志
     * @param orderProducts
     * @param remark
     * @param changeLog
     * @param paymentStatus
     */
    private void saveOrderProductLog(OrderProductsNew orderProducts, String remark, String changeLog,
                                     Integer paymentStatus) {
        OrderOperateLogs orderOperateLog = new OrderOperateLogs();
        orderOperateLog.setSiteId(1);
        orderOperateLog.setOrderId(orderProducts.getOrderId());
        orderOperateLog.setOrderProductId(orderProducts.getId());
        orderOperateLog.setNetPointId(orderProducts.getNetPointId());
        orderOperateLog.setOperator("系统");
        orderOperateLog.setChangeLog(changeLog);
        orderOperateLog.setRemark(remark);
        orderOperateLog.setStatus(orderProducts.getStatus());
        orderOperateLog.setPaymentStatus(paymentStatus);
        shopOrderOperateLogsService.insert(orderOperateLog);
    }
}
