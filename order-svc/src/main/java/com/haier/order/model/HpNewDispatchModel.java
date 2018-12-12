package com.haier.order.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.order.dateSource.ReadWriteRoutingDataSourceHolder;
import com.haier.order.multithread.IExcute;
import com.haier.order.multithread.MultiThreadTool;
import com.haier.order.multithread.ThreadHelper;
import com.haier.order.util.DateFormatUtil;
import com.haier.order.util.OrderSnUtil;
import com.haier.order.util.SpringContextUtil;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import com.haier.stock.model.StoreInfo;
import com.haier.stock.service.StoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

@Service("hpNewDispatchModel")
public class HpNewDispatchModel {
    private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager
            .getLogger(HpNewDispatchModel.class);
    @Autowired
    private HpDispatchService hpDispatchService;
    @Autowired
    private OrderProductsNewService orderProductsNewService;
    @Autowired
    private OrdersNewService ordersNewService;
    @Autowired
    private ShopOrderOperateLogsService shopOrderOperateLogsService;
    @Autowired
    private ReservationShippingService reservationShippingService;
    @Autowired
    private ShopOrderWorkflowsService shopOrderWorkflowsService;
    @Autowired
    private MemberInvoicesService memberInvoicesService;
    @Autowired
    private OrderProductsAttributesService orderProductsAttributesService;
    @Autowired
    private StoreInfoService storeInfoService;
    @Autowired
    private CountTimePubModel countTimePubModel;
    @Autowired
    private HpToDispatchingService hpToDispatchingService;

//    private DataSourceTransactionManager transactionManager;

    private static final Integer TOPX = 1000;
    private static final Integer SYNC_HP = 2;                                                  // 同步到HP
    private static final Integer ELECTRICINVOICE = 1;                                                  // 电子发票
    private static final String INFACT_METHOD = "rrs.order.accept.acceptwodatatwo";                 // 正式method
    private static final String INFACT_TOKEN = "MmIzMGE0YzUtMzM3NC00YzQ3LTgxMTQtZjdkNGY4YWVhZWYx"; // 正式token
    //    private static final Integer  IS_OTO          = 1;                                                  //是否oto指定派工
    //推送HP字段
    private static final String[] TITLES = {"apply_id", "product_model",
            "service_type_code", "require_service_date",
            "customer_name", "mobile_phone", "phone",
            "district_code", "address",
            "require_service_desc", "parent_apply_id",
            "source_code", "machine_type",
            "source_code_desc", "order_type",
            "order_count", "poi_id", "order_date",
            "recvbl_amt", "suite_order_count",
            "invoice_flag", "attribute7", "attribute4",
            "attribute2"};

    //*
    public void sendHpNewBatchToDispatch() {
        List<HPQueues> hpQueueList = new ArrayList<HPQueues>();
        hpQueueList = getHpQueueUnSendInfo(TOPX);
        if (hpQueueList == null || hpQueueList.size() == 0) {
            logger.info("待推送HP数据为空，无需发送！");
            return;
        }
        long startTime = System.currentTimeMillis();
        execute(hpQueueList);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        logger.info("推送HP条数:" + hpQueueList.size() + ",总共执行时间:" + time + "毫秒,平均每条"
                + time / hpQueueList.size() + "毫秒");
    }

    //*
    private void execute(List<HPQueues> hpQueueList) {
        ThreadHelper threadHelper = (ThreadHelper) SpringContextUtil.getBean("threadHelper");
        //加入多线程执行
        ExecuteHpNewDispatch executeHpNewDispatch = new ExecuteHpNewDispatch();
        //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
        int splitSize = 30;
        int size = hpQueueList.size();
        if (size > 10 && size <= splitSize) {
            splitSize = size / 2 + 1;
        }
        new MultiThreadTool<HPQueues>().processJobs(executeHpNewDispatch, threadHelper, logger,
                hpQueueList, splitSize, 3);
    }

    //2
    public void sendHpToDispatchByMultiThread(List<HPQueues> hpQueueList) {
        if (hpQueueList == null || hpQueueList.size() == 0) {
            logger.info("[HpNewDispatchModel]待发送HP数据为空，无需发送！");
            return;
        }

        //获取区域数据
        Map<Integer, Object> areaMap = new HashMap<Integer, Object>();
        getAreaInfo(areaMap);

        //获取物料编码数据
        Map<String, Object> skuMap = new HashMap<String, Object>();
        getSkuInfo(skuMap);

        //根据订单ID获取订单下的网单数
        Map<Integer, Object> orderCountMap = new HashMap<Integer, Object>();
        getOrderCountInfo(hpQueueList, orderCountMap);
        //key=网单号,value=推送信息的Map
        Map<String, Map<String, Object>> transferMap = new HashMap<String, Map<String, Object>>();
        for (HPQueues hpQueue : hpQueueList) {
            //网单信息
            OrderProductsNew orderProduct = orderProductsNewService.get(hpQueue.getOrderProductId());
            if (orderProduct == null
                    || orderProduct.getStatus().intValue() == OrderProductStatus.COMPLETED_CLOSE
                    .getCode().intValue()
                    || orderProduct.getStatus().intValue() == OrderProductStatus.CANCEL_CLOSE.getCode()
                    .intValue()) {
                hpQueue.setCount(50);
                hpQueue.setVomCount(50);
                hpQueue.setLastMessage("网单取消或关闭,不再推送");
                updateHPQueue(hpQueue);
                continue;
            }
            //订单信息
            OrdersNew order = ordersNewService.get(orderProduct.getOrderId());
            if (order == null
                    || order.getOrderStatus().intValue() == OrderStatus.OS_CANCEL.getCode().intValue()
                    || order.getOrderStatus().intValue() == OrderStatus.OS_COMPLETE.getCode()
                    .intValue()) {
                hpQueue.setCount(50);
                hpQueue.setVomCount(50);
                hpQueue.setLastMessage("订单取消或关闭,不再推送");
                updateHPQueue(hpQueue);
                continue;
            }
            //订单状态为"未确认",不推送
            if (order.getOrderStatus().intValue() == OrderStatus.OS_UN_CONFIRM.getCode()
                    .intValue()) {
                hpQueue.setLastMessage("订单状态未确认,不推送");
                updateHPQueue(hpQueue);
                continue;
            }
            //验证信息
            if (!checkMessage(orderProduct, order)) {
                continue;
            }
            //翻译信息
            Map<String, Object> dtoMap = translateMessage(orderProduct, order, areaMap, skuMap,
                    orderCountMap);
            if (dtoMap == null) {
                hpQueue.setCount(hpQueue.getCount() + 1);
                hpQueue.setLastMessage("组织信息出错，具体去后台查询报错日志");
                updateHPQueue(hpQueue);
                continue;
            }
            dtoMap.put("log_order_id", order.getId());
            dtoMap.put("log_orderproduct_id", orderProduct.getId());
            dtoMap.put("log_netpoint_id", orderProduct.getNetPointId());
            dtoMap.put("log_status", orderProduct.getStatus());
            dtoMap.put("log_paymentstatus", order.getPaymentStatus());
            dtoMap.put("hpQueue", hpQueue);

            transferMap.put(orderProduct.getCOrderSn(), dtoMap);
        } //end for
        if (transferMap.isEmpty()) {
            return;
        }
        //调用接口
        hpNewDispatchInterface(transferMap);

    }

    /**
     * 验证信息
     *
     * @return
     */
    //7
    private boolean checkMessage(OrderProductsNew orderProduct, OrdersNew order) {
        try {
            if (StringUtil.isEmpty(orderProduct.getSCode())) {
                insertOperateLog(orderProduct, order, "系统", "同步到HP",
                        "有异常,网单中的库位编码不应为空，原因应该是没有库位覆盖,即没有库存");
                return false;
            }
            MemberInvoices memberInvoice = memberInvoicesService.getByOrderId(Math.toIntExact(Integer.valueOf(order.getId())));
            String remark = replaceCharactor(order.getRemark());
            if (memberInvoice != null
                    && memberInvoice.getElectricFlag().intValue() == ELECTRICINVOICE.intValue()) {
                remark = remark + " 该网单为电子发票。";
            }
            //2017-05-24 isTimeoutFree＝1 超时免单  ＝3主动超时免单
            if (orderProduct.getIsTimeoutFree() != null
                    && (orderProduct.getIsTimeoutFree().intValue() == 1
                    || orderProduct.getIsTimeoutFree().intValue() == 3)) {
                remark = "超时免单," + remark;
            }
            remark = remark.replace(",", "，");
            if (isLength(remark) >= 1000) {
                insertOperateLog(orderProduct, order, "系统", "同步到HP",
                        "要求服务描述（订单备注）超出HP的1000个字符限制，同步取消");
                return false;
            }
            order.setRemark(remark);
            String consignee = replaceCharactor(order.getConsignee());
            consignee = consignee.replace(",", "，");
            if (isLength(consignee) >= 32) {
                insertOperateLog(orderProduct, order, "系统", "同步到HP", "收货人姓名超出HP的32个字符限制，姓名自动截位处理");
                consignee = consignee.substring(0, 10);
            }
            order.setConsignee(consignee);
            String addressRegionName = replaceCharactor(order.getRegionName()) + " "
                    + replaceCharactor(order.getAddress());
            addressRegionName = addressRegionName.replace(",", "，");
            if (isLength(addressRegionName) >= 150) {
                insertOperateLog(orderProduct, order, "系统", "同步到HP", "收货地址超出HP的150个字符限制，地址自动截位处理");
                addressRegionName = addressRegionName.substring(0, 49);
            }
            order.setAddress(addressRegionName);
        } catch (Exception e) {
            logger.error("[HpNewDispatchModel_checkMessage]验证信息时出现异常:", e);
            return false;
        }
        return true;
    }

    /**
     * 翻译信息
     *
     * @return
     */
    //8
    private Map<String, Object> translateMessage(OrderProductsNew orderProduct, OrdersNew order,
                                                 Map<Integer, Object> areaMap,
                                                 Map<String, Object> skuMap,
                                                 Map<Integer, Object> orderCountMap) {
        Map<String, Object> dtoMap = new HashMap<String, Object>();
        try {
            dtoMap.put("apply_id", orderProduct.getCOrderSn());// 网单号
            dtoMap.put("product_model", orderProduct.getSku());// SKU,默认使用网单表的SKU
            if ("TOPXB".equalsIgnoreCase(order.getSource())
                    || "TOPFENXIAOXB".equalsIgnoreCase(order.getSource())) {
                //如果是新宝产品，需要给HP系统转换成HP的编码
                //注：订单来源为新宝，可能不全是新宝品牌的产品
                dtoMap.put("product_model", getSkuHp(orderProduct.getSku(), skuMap));
            }
            dtoMap.put("service_type_code", "T02");// 要求服务类型
            //预约信息
            ReservationShipping reservationShipping = reservationShippingService.get(Math.toIntExact(Integer.valueOf(order.getId())));
            String toUserDate = null;
            if (reservationShipping != null) {
                String date = reservationShipping.getDate(), time = reservationShipping.getTime();
                if (StringUtil.isEmpty(time)) {
                    time = "000000";
                }
                toUserDate = DateFormatUtil.formatByType("yyyy-MM-dd HH:mm:ss",
                        DateFormatUtil.parseByType("yyyyMMddHHmmss", date + time));
            } else {
                if (order.getTaobaoGroupId().intValue() == 0) {
                    //全流程信息
                    OrderWorkflows orderWorkflow = shopOrderWorkflowsService
                            .getByOrderProductId(Math.toIntExact(orderProduct.getId()));
                    Long mustNetPointAcceptTime = 0l;
                    if (orderWorkflow != null) {
                        mustNetPointAcceptTime = orderWorkflow.getMustNetPointAcceptTime();
                    }
                    if (mustNetPointAcceptTime == 0l) {
                        mustNetPointAcceptTime = countTimePubModel.countMustNetPointAcceptTime(
                                order, orderProduct, mustNetPointAcceptTime);
                    }
                    if (mustNetPointAcceptTime > 0l) {
                        toUserDate = DateFormatUtil.formatByType("yyyy-MM-dd",
                                new Date(mustNetPointAcceptTime * 1000l)) + " 20:00:00";
                    }
                }
            }
            dtoMap.put("require_service_date", toUserDate != null ? toUserDate : "");// 要求服务时间//用户预约时间或者最晚送达用户时间
            dtoMap.put("customer_name", order.getConsignee());// 用户姓名（不超过16个汉字）

            String mobile_phone = changeNullToEmpty(order.getMobile()).trim()
                    .replaceAll("[\u4e00-\u9fa5]+", "");
            if (mobile_phone.length() > 12) {
                mobile_phone = mobile_phone.substring(0, 12);
            }
            dtoMap.put("mobile_phone", mobile_phone);// 手机号码（11位）

            String phone = ToDBC(
                    changeNullToEmpty(order.getPhone()).trim().replaceAll("[\u4e00-\u9fa5]+", ""));
            if ("-".equals(phone) || "--".equals(phone)) {
                phone = "";
            } else if (phone.contains("--")) {
                String temp[] = phone.split("--");
                phone = temp[0];
            } else if (phone.contains("-")) {
                String temp[] = phone.split("-");
                if (temp.length > 1) {
                    phone = temp[1];
                } else {
                    phone = temp[0];
                }
            }
            if (phone.length() > 12) {
                phone = phone.substring(0, 12);
            }
            dtoMap.put("phone", phone);// 电话号码（7-8位电话号码）

            dtoMap.put("district_code", getAreaCode(order.getRegion(), areaMap));// 地区编码（六位国标码）
            dtoMap.put("address", order.getAddress());// 客户地址（不超过50个汉字）
            dtoMap.put("require_service_desc",
                    StringUtil.isEmpty(order.getRemark()) ? "无" : order.getRemark());// 要求服务描述（不超过500个汉字）
            dtoMap.put("parent_apply_id", order.getOrderSn());// 订单号
            if ("TOPXB".equalsIgnoreCase(order.getSource())
                    || "TOPFENXIAOXB".equalsIgnoreCase(order.getSource())) {
                dtoMap.put("source_code", "13");// 第三方系统编码，新宝网单 固定为 13
            } else if ("RRS".equalsIgnoreCase(order.getSource())) {
                dtoMap.put("source_code", "25");// 第三方系统编码，日日顺网单 固定为 25
            } else {
                dtoMap.put("source_code", "1");// 第三方系统编码，固定为 1 ，代表网单
            }
            if ("COS".equalsIgnoreCase(order.getSource())) {
                dtoMap.put("machine_type", "40");
            } else if ("DBJ".equalsIgnoreCase(order.getSource())) {
                dtoMap.put("machine_type", "41");
            } else {
                dtoMap.put("machine_type", "10");
            }
            dtoMap.put("source_code_desc", order.getSource());// 第三方系统描述，订单来源
            String orderType = null;
            if (OrderType.TYPE_GROUP_ADVANCE.getValue().intValue() == order.getOrderType().intValue()
                    || OrderType.TYPE_GROUP_ADVANCE_TAIL.getValue().intValue() == order.getOrderType().intValue()) {
                orderType = "P3";
            } else if ("cod".equalsIgnoreCase(order.getPaymentCode())) {
                orderType = "P2";
            } else {
                orderType = "P1";
            }
            dtoMap.put("order_type", changeNullToEmpty(orderType));// 订单类型：P1：普通订单；P2：货到付款订单；P3：定金订单
            dtoMap.put("order_count", orderProduct.getNumber());// 订单产品数量//台数
            dtoMap.put("poi_id", "");// POI点//标建对应的rowId(HP地址库映射字段)
            long mysqlTime = 0;
            if (order.getIsCod().intValue() == 1 && order.getCodConfirmTime().intValue() > 0) {
                mysqlTime = order.getCodConfirmTime().longValue();
            } else if ((OrderType.TYPE_GROUP_ADVANCE.getValue().intValue() == order.getOrderType().intValue()
                    || OrderType.TYPE_GROUP_ADVANCE_TAIL.getValue().intValue() == order.getOrderType().intValue())
                    && order.getTailPayTime() > 0l) {
                mysqlTime = order.getTailPayTime();
            } else if (order.getPayTime().intValue() > 0) {
                mysqlTime = order.getPayTime().intValue();
            }
            if (mysqlTime == 0 && order.getAddTime().intValue() > 0) {
                mysqlTime = order.getAddTime().longValue();
            }
            dtoMap.put("order_date", DateFormatUtil.format(new Date(mysqlTime * 1000L)));// 下单时间//订单的下单时间 即HP 登记时间
            String money = "0";
            if ("cod".equalsIgnoreCase(order.getPaymentCode())
                    && orderProduct.getProductAmount().compareTo(BigDecimal.ZERO) > 0) {
                money = orderProduct.getProductAmount().toString();
            }
            dtoMap.put("recvbl_amt", money);// 所需支付金额//网单金额,只有货到付款订单才传网单金额
            dtoMap.put("suite_order_count", getOrderCountNumber(Math.toIntExact(Integer.valueOf(order.getId())), orderCountMap));//大家电网单个数(成套订单数量)
            dtoMap.put("invoice_flag", orderProduct.getMakeReceiptType());//开票类型

            //            List<Regions> regionsList = regionsDao.getByRegionId(order.getRegion(), IS_OTO);
            //            if (regionsList != null && regionsList.size() > 0) {
            //                dtoMap.put("attribute7", "O2O");//O2O_new
            //            }
            //2017-01-04自营转单
            OrderProductsAttributes opa = orderProductsAttributesService
                    .getByOrderProductId(Math.toIntExact(orderProduct.getId()));
            if (opa != null && opa.getIsDispatching() != null
                    && opa.getIsDispatching().intValue() == 1) {
                dtoMap.put("attribute7", "O2O");//O2O_new
            }

            //2017-02-23 添加上岗账号参数
            if (!StringUtil.isEmpty(order.getCkCode(), true)) {
                StoreInfo storeInfo = storeInfoService
                        .getByOwerId(Integer.parseInt(order.getCkCode().trim()));
                if (storeInfo != null && !StringUtil.isEmpty(storeInfo.getHrCode(), true)) {
                    dtoMap.put("attribute4", storeInfo.getHrCode().trim());
                }
            }

            //            //2017-05-23,3H
            //            //        商品：CE0JK300M
            //            //        3小时急速达时间：6月1日 00:00-6月9日12:00 的定金尾款订单
            //            //        订单来源：顺逛APP、顺逛WAP、顺逛PC、商城PC、商城移动
            //            //SELECT `id` FROM `Regions` WHERE `regionType`=4 and `activeFlag`=1 and `shippingTime` in (0,1,2)
            //            long tempTime = orderProduct.getCPayTime();
            //            if ((OrderType.TYPE_GROUP_ADVANCE.getValue().intValue() == order.getOrderType()
            //                .intValue()) && tempTime >= 1496246400l && tempTime <= 1496980800l) {
            //                if (OrderSnUtil.get3HMark(order.getSource(), orderProduct.getSku())) {
            //                    Regions streetRegion = regionsDao.get(order.getStreet());
            //                    if (streetRegion != null && streetRegion.getActiveFlag()
            //                        && streetRegion.getRegionType().intValue() == 4
            //                        && ("0".equals(streetRegion.getShippingTime())
            //                            || "1".equals(streetRegion.getShippingTime())
            //                            || "2".equals(streetRegion.getShippingTime()))) {
            //                        dtoMap.put("attribute2", "3H");
            //                    }
            //                }
            //            }

            //2017-07-03,3H
            //定金支付时间为：6月30日9:00-7月9日9:00
            long tempTime = orderProduct.getCPayTime();
            if ((OrderType.TYPE_GROUP_ADVANCE.getValue().intValue() == order.getOrderType().intValue()) && tempTime >= 1498784400l && tempTime <= 1499562000l) {
                if (OrderSnUtil.get3HMark(order.getSource(), orderProduct.getSku(),
                        order.getRegion())) {
                    dtoMap.put("attribute2", "3H");
                }
            }

        } catch (Exception e) {
            logger.error("[HpNewDispatchModel][translateMessage]网单号：" + orderProduct.getCOrderSn()
                            + ",翻译信息时出现异常:",
                    e);
            return null;
        }
        return dtoMap;
    }

    /**
     * 调用接口
     */
    //8
    private void hpNewDispatchInterface(Map<String, Map<String, Object>> transferMap) {
        //推送网单集合
        Set<String> requestOrderSnSet = new HashSet<String>();
        Map<String, StringBuffer> hpContentMap = setHpMap(transferMap, requestOrderSnSet);
        String[] eisLogId = new String[1];
        HpResponse hpResponse = null;
        try {
            hpResponse = post("", changeMapToString(hpContentMap), eisLogId);
        } catch (Exception e) {
            logger.error("[HpNewDispatchModel][post]格式化返回信息出错", e);
            return;
        }

        if (hpResponse == null || !"ok".equalsIgnoreCase(hpResponse.getStatus())) {
            dealErrorInfo(transferMap, "调用HP接口失败,返回false");
            return;
        }
        List<HpResponse.HpDetailResponse> detailResponseList = hpResponse.getData();
        if (detailResponseList == null || detailResponseList.size() == 0) {
            dealErrorInfo(transferMap, "调用HP接口失败,明细为空");
            return;
        }
        //返回网单集合
        Set<String> responseOrderSnSet = new HashSet<String>();
        for (HpResponse.HpDetailResponse hpDetailResponse : detailResponseList) {
            String cOrderSn = hpDetailResponse.getApply_id();
            responseOrderSnSet.add(cOrderSn);
            Map<String, Object> detailMap = transferMap.get(cOrderSn);
            if (detailMap == null) {
                continue;
            }
            dealHpBackData(hpDetailResponse, detailMap, eisLogId[0]);
        } //end for
        //推送网单集合中没有返回的，记录日志
        requestOrderSnSet.removeAll(responseOrderSnSet);
        if (!requestOrderSnSet.isEmpty()) {
            Map<String, Map<String, Object>> removeAllMap = new HashMap<String, Map<String, Object>>();
            for (String cOrderSn : requestOrderSnSet) {
                removeAllMap.put(cOrderSn, transferMap.get(cOrderSn));
            }
            dealErrorInfo(removeAllMap, "调用HP接口失败,返回信息中没有此单号");
        }
    }

    //9
    private Map<String, StringBuffer> setHpMap(Map<String, Map<String, Object>> transferMap,
                                               Set<String> requestOrderSnSet) {
        Map<String, StringBuffer> hpContentMap = new HashMap<String, StringBuffer>();
        for (Map.Entry<String, Map<String, Object>> set : transferMap.entrySet()) {
            requestOrderSnSet.add(set.getKey());
            Map<String, Object> valueMap = set.getValue();
            StringBuffer sbuffer = null;
            for (String strTitle : TITLES) {
                if (hpContentMap.containsKey(strTitle)) {
                    sbuffer = hpContentMap.get(strTitle);
                } else {
                    sbuffer = new StringBuffer();
                    hpContentMap.put(strTitle, sbuffer);
                }
                Object obj = valueMap.get(strTitle);
                if (obj == null || "".equals(obj.toString())) {
                    sbuffer.append("null").append(",");
                } else {
                    sbuffer.append(valueMap.get(strTitle)).append(",");
                }
            }
        }
        return hpContentMap;
    }

    //10
    private void dealHpBackData(HpResponse.HpDetailResponse hpDetailResponse,
                                Map<String, Object> detailMap, String eisLogId) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus transactionStatus = transactionManager.getTransaction(def);
        try {
            Integer orderId = (Integer) detailMap.remove("log_order_id");
            Integer orderProductId = (Integer) detailMap.remove("log_orderproduct_id");
            Integer netPointId = (Integer) detailMap.remove("log_netpoint_id");
            Integer status = (Integer) detailMap.remove("log_status");
            Integer paymentStatus = (Integer) detailMap.remove("log_paymentstatus");
            HPQueues hpQueue = (HPQueues) detailMap.remove("hpQueue");

            String successFlag = hpDetailResponse.getSuccess_flag();
            if (!StringUtil.isEmpty(successFlag) && "1".equals(successFlag)) {
                hpQueue.setCount(hpQueue.getCount() + 1);
                hpQueue.setSuccess(1);
                hpQueue.setSuccessTime((int) (System.currentTimeMillis() / 1000));
                hpQueue.setLastMessage("POST数据:" + JsonUtil.toJson(detailMap) + ",返回数据:"
                        + JsonUtil.toJson(hpDetailResponse) + ",总日志ID:" + eisLogId);
                updateHPQueue(hpQueue);
                hpDispatchService.updateOrderProductStatus(orderProductId, SYNC_HP);
                //更新全流程表sendHpTime字段，当前系统时间
                hpDispatchService.updateSyncHpTime(orderProductId, System.currentTimeMillis() / 1000);
                OrderOperateLogs log = createOrderOperateLog(orderId, orderProductId, netPointId,
                        "成功,返回数据:" + JsonUtil.toJson(hpDetailResponse), status, paymentStatus);
                shopOrderOperateLogsService.insert(log);
            } else {
                //              "error_type": "006",
                //              "wo_desc": "重复派单",
                //                if (!StringUtil.isEmpty(hpDetailResponse.getWo_desc())&&"重复派单".equals(hpDetailResponse.getWo_desc())) {
                if (!StringUtil.isEmpty(hpDetailResponse.getError_type())
                        && "006".equals(hpDetailResponse.getError_type())) {
                    hpQueue.setCount(hpQueue.getCount() + 1);
                    hpQueue.setSuccess(1);
                    hpQueue.setSuccessTime((int) (System.currentTimeMillis() / 1000));
                    hpQueue.setLastMessage("POST数据:" + JsonUtil.toJson(detailMap) + ",返回数据:"
                            + JsonUtil.toJson(hpDetailResponse));
                    updateHPQueue(hpQueue);
                    hpDispatchService.updateOrderProductStatus(orderProductId, SYNC_HP);
                    //更新全流程表sendHpTime字段，当前系统时间
                    hpDispatchService.updateSyncHpTime(orderProductId,
                            System.currentTimeMillis() / 1000);
                    OrderOperateLogs log = createOrderOperateLog(orderId, orderProductId,
                            netPointId, "成功,重复派单类型,返回数据:" + JsonUtil.toJson(hpDetailResponse)
                                    + ",总日志ID:" + eisLogId,
                            status, paymentStatus);
                    shopOrderOperateLogsService.insert(log);
                } else {
                    hpQueue.setCount(hpQueue.getCount() + 1);
                    hpQueue.setLastMessage("POST数据:" + JsonUtil.toJson(detailMap) + ",返回数据:"
                            + JsonUtil.toJson(hpDetailResponse));
                    updateHPQueue(hpQueue);
                    OrderOperateLogs log = createOrderOperateLog(orderId, orderProductId,
                            netPointId, hpDetailResponse.getWo_desc() + ",返回数据:"
                                    + JsonUtil.toJson(hpDetailResponse) + ",总日志ID:" + eisLogId,
                            status, paymentStatus);
                    shopOrderOperateLogsService.insert(log);
                }
            }
            //提交事务
//            transactionManager.commit(transactionStatus);
        } catch (Exception e) {
            //回滚事务
//            transactionManager.rollback(transactionStatus);
            logger.error("[HpNewDispatchModel]推送HP派工出错，网单号:" + hpDetailResponse.getApply_id(), e);
        }
    }

    /**
     * 操作日志
     */
    //11
    private void insertOperateLog(OrderProductsNew orderProduct, OrdersNew order, String operator,
                                  String changeLog, String remark) {
        try {
            OrderOperateLogs log = new OrderOperateLogs();
            log.setSiteId(1);
            log.setOrderId(Math.toIntExact(Integer.valueOf(order.getId())));
            log.setOrderProductId(Math.toIntExact(orderProduct.getId()));
            log.setNetPointId(orderProduct.getNetPointId());
            log.setOperator(StringUtil.isEmpty(operator) ? "系统" : operator);
            log.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
            log.setRemark(StringUtil.isEmpty(remark) ? ""
                    : (remark.length() > 255 ? remark.substring(0, 255) : remark));
            log.setStatus(Integer.valueOf(orderProduct.getStatus()));
            log.setPaymentStatus(order.getPaymentStatus());
            shopOrderOperateLogsService.insert(log);
        } catch (Exception e) {
            logger.error(
                    "[HpNewDispatchModel_insertOperateLog]记录操作日志出错，网单ID:" + orderProduct.getId(), e);
        }
    }

    private OrderOperateLogs createOrderOperateLog(Integer orderId, Integer orderProductId,
                                                   Integer netPointId, String remark,
                                                   Integer status, Integer paymentStatus) {
        OrderOperateLogs log = new OrderOperateLogs();
        log.setSiteId(1);
        log.setOrderId(orderId);
        log.setOrderProductId(orderProductId);
        log.setNetPointId(netPointId);
        log.setOperator("系统");
        log.setChangeLog("同步到HP");
        log.setRemark(StringUtil.isEmpty(remark) ? ""
                : (remark.length() > 255 ? remark.substring(0, 255) : remark));
        log.setStatus(status);
        log.setPaymentStatus(paymentStatus);
        return log;
    }

    /**
     * 获取待发送HP数据
     *
     * @param topX 要获取的条数
     * @return
     */
    //12
    private List<HPQueues> getHpQueueUnSendInfo(Integer topX) {
        List<HPQueues> list = null;
        try {
            list = hpDispatchService.getHpQueueUnSendInfo(topX);
        } catch (Exception e) {
            logger.error("[HpNewDispatchModel_getHpQueueUnSendInfo]获取待发送HP数据时发生未知异常:", e);
        }
        return list;
    }

    /**
     * 更新HPQueue表信息
     */
    //6
    private void updateHPQueue(HPQueues hpQueue) {
        try {
            hpDispatchService.update(hpQueue);
        } catch (Exception e) {
            logger.error("[HpNewDispatchModel_updateHPQueue]更新HP队列数据时发生未知异常:", e);
        }
    }

    /**
     * 批量更新HPQueue表信息
     */
    //13
    private void updateHPQueueBatch(String ids, String lastMessage) {
        try {
            hpDispatchService.updateHPQueueBatch(ids, lastMessage);
        } catch (Exception e) {
            logger.error("[HpNewDispatchModel_updateHPQueueBatch]批量更新HP队列数据时发生未知异常:", e);
        }
    }

    /**
     * 调用HP接口
     */
    //14
    private HpResponse post(String foreignKey, String content, String[] eisLogId) throws Exception {
        ServiceResult<String> result = hpToDispatchingService.sendHpDispatchNew(foreignKey,
                content);
        if (result == null) {
            logger.error("调用HP接口失败！返回结果为NULL");
            return null;
        }
        if (result.getSuccess()) {
            String json = result.getResult();
            json = URLDecoder.decode(json, "utf-8");
            Type type = new TypeToken<HpResponse>() {
            }.getType();
            Gson gson = new Gson();
            HpResponse ret = gson.fromJson(json, type);
            eisLogId[0] = result.getMessage();
            return ret;
        } else {
            logger.error("调用HP接口失败！返回结果：" + result);
        }
        return null;
    }

    //9
    private void dealErrorInfo(Map<String, Map<String, Object>> transferMap, String changeLog) {
        StringBuffer sbuffer = new StringBuffer();
        List<OrderOperateLogs> orderOperateLogsList = new ArrayList<OrderOperateLogs>();
        for (Map.Entry<String, Map<String, Object>> set : transferMap.entrySet()) {
            Map<String, Object> valueMap = set.getValue();
            Integer orderId = (Integer) valueMap.get("log_order_id");
            Integer orderProductId = (Integer) valueMap.get("log_orderproduct_id");
            Integer netPointId = (Integer) valueMap.get("log_netpoint_id");
            Integer status = (Integer) valueMap.get("log_status");
            Integer paymentStatus = (Integer) valueMap.get("log_paymentstatus");
            orderOperateLogsList.add(createOrderOperateLog(orderId, orderProductId, netPointId,
                    changeLog, status, paymentStatus));
            HPQueues hpQueue = (HPQueues) valueMap.get("hpQueue");
            sbuffer.append(hpQueue.getId()).append(",");
        }
        String ids = sbuffer.deleteCharAt(sbuffer.toString().length() - 1).toString();
        updateHPQueueBatch(ids, changeLog);
        shopOrderOperateLogsService.batchInsert(orderOperateLogsList);
    }

    //1
    private class ExecuteHpNewDispatch implements IExcute {
        @SuppressWarnings("unchecked")
        @Override
        public void execute(Object obj) {
            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
            List<HPQueues> hpQueuesList = (List<HPQueues>) obj;
            try {
                sendHpToDispatchByMultiThread(hpQueuesList);
            } finally {
                ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
            }
        }
    }

    /**
     * 拼接要传给HP接口的POST数据
     */
    //15
    private String changeMapToString(Map<String, StringBuffer> hpContentMap) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("timestamp=").append(URLEncoder.encode(System.currentTimeMillis() + "", "UTF-8"))
                .append("&").append("method=").append(URLEncoder.encode(INFACT_METHOD, "UTF-8"))
                .append("&").append("access_token=").append(URLEncoder.encode(INFACT_TOKEN, "UTF-8"))
                .append("&");
        for (Map.Entry<String, StringBuffer> entry : hpContentMap.entrySet()) {
            sb.append(entry.getKey()).append("=")
                    .append(URLEncoder.encode(entry.getValue()
                            .deleteCharAt(entry.getValue().toString().length() - 1).toString(), "UTF-8"))
                    .append("&");
        }
        return sb.deleteCharAt(sb.toString().length() - 1).toString();
    }

    /**
     * 获取区域数据
     */
    //3
    private void getAreaInfo(Map<Integer, Object> areaMap) {
        if (areaMap.isEmpty()) {
            List<Map<String, Object>> tempAreaList = hpDispatchService.getRegions();
            if (tempAreaList == null || tempAreaList.isEmpty()) {
                logger.error("Regions表为空！");
                return;
            }
            for (Map<String, Object> map : tempAreaList) {
                areaMap.put(Integer.parseInt(map.get("id").toString()), map.get("code"));
            }
        }
    }

    /**
     * 获取物料编码数据
     */
    //4
    private void getSkuInfo(Map<String, Object> skuMap) {
        if (skuMap.isEmpty()) {
            List<Map<String, Object>> tempSkuList = hpDispatchService.getSkuMappings();
            if (tempSkuList == null || tempSkuList.isEmpty()) {
                logger.error("SkuMappings表为空！");
                return;
            }
            for (Map<String, Object> map : tempSkuList) {
                skuMap.put(map.get("sku").toString(), map.get("skuH"));
            }
        }
    }

    /**
     * 根据网单ID获取订单下的网单数
     */
    //5
    private void getOrderCountInfo(List<HPQueues> hpQueueList, Map<Integer, Object> orderCountMap) {
        if (orderCountMap.isEmpty()) {
            StringBuffer orderProductIds = new StringBuffer();
            for (HPQueues hpQueue : hpQueueList) {
                orderProductIds.append(hpQueue.getOrderProductId()).append(",");
            }
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderProductIds",
                    orderProductIds.deleteCharAt(orderProductIds.length() - 1).toString().split(","));
            List<Map<String, Object>> orderCountList = new ArrayList<Map<String, Object>>();
            orderCountList = hpDispatchService.getOrderCountList(paramMap);
            for (Map<String, Object> map : orderCountList) {
                orderCountMap.put(Integer.parseInt(map.get("id").toString()),
                        map.get("countId").toString());
            }
        }
    }

    /**
     * 翻译六位国标码
     */
    private String getAreaCode(Integer region, Map<Integer, Object> areaMap) {
        Object obj = areaMap.get(region);
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    /**
     * 获取订单下的网单数
     */
    //
    private String getOrderCountNumber(Integer orderId, Map<Integer, Object> orderCountMap) {
        Object obj = orderCountMap.get(orderId);
        if (obj == null) {
            return "";
        }
        return obj.toString();
    }

    /**
     * HP的sku
     */
    //
    private String getSkuHp(String sku, Map<String, Object> skuMap) {
        Object hpSku = skuMap.get(sku);
        if (hpSku == null) {
            return sku;
        }
        return hpSku.toString();
    }

    /**
     * 全角转半角
     *
     * @param input String.
     * @return 半角字符串
     */
    //
    private String ToDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        return new String(c);
    }

    //
    private String changeNullToEmpty(String str) {
        if (str == null || "".equals(str)) {
            return "";
        }
        return str.trim();
    }

    private String replaceCharactor(String obj) {
        return changeNullToEmpty(obj).replace("&", "").replace("=", "").replace("'", "")
                .replace("‘", "").replace("’", "").replace("“", "").replace("”", "").replace("\"", "")
                .replace(";", "").replace("；", "");
    }

    /**
     * 返回字符串长度
     */
    private int isLength(Object obj) {
        try {
            if (null == obj) {
                return 0;
            } else {
                return obj.toString().trim().getBytes("utf-8").length;
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("[HpNewDispatchModel_isLength]汉字转化时发生未知异常:", e);
            return 0;
        }
    }

    @SuppressWarnings("unused")
    private class HpResponse {
        private String status;
        private String code;
        private String msg;
        private Integer total_results;
        private Integer page_now;
        private Integer page_size;
        private Integer total_pages;
        private List<HpDetailResponse> data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Integer getTotal_results() {
            return total_results;
        }

        public void setTotal_results(Integer total_results) {
            this.total_results = total_results;
        }

        public Integer getPage_now() {
            return page_now;
        }

        public void setPage_now(Integer page_now) {
            this.page_now = page_now;
        }

        public Integer getPage_size() {
            return page_size;
        }

        public void setPage_size(Integer page_size) {
            this.page_size = page_size;
        }

        public Integer getTotal_pages() {
            return total_pages;
        }

        public void setTotal_pages(Integer total_pages) {
            this.total_pages = total_pages;
        }

        public List<HpDetailResponse> getData() {
            return data;
        }

        public void setData(List<HpDetailResponse> data) {
            this.data = data;
        }

        private class HpDetailResponse {
            private String apply_id;
            private String wo_id;
            private String wo_status;
            private String success_flag;
            private String error_type;
            private String wo_desc;
            private String source_code;

            public String getApply_id() {
                return apply_id;
            }

            public void setApply_id(String apply_id) {
                this.apply_id = apply_id;
            }

            public String getWo_id() {
                return wo_id;
            }

            public void setWo_id(String wo_id) {
                this.wo_id = wo_id;
            }

            public String getWo_status() {
                return wo_status;
            }

            public void setWo_status(String wo_status) {
                this.wo_status = wo_status;
            }

            public String getSuccess_flag() {
                return success_flag;
            }

            public void setSuccess_flag(String success_flag) {
                this.success_flag = success_flag;
            }

            public String getError_type() {
                return error_type;
            }

            public void setError_type(String error_type) {
                this.error_type = error_type;
            }

            public String getWo_desc() {
                return wo_desc;
            }

            public void setWo_desc(String wo_desc) {
                this.wo_desc = wo_desc;
            }

            public String getSource_code() {
                return source_code;
            }

            public void setSource_code(String source_code) {
                this.source_code = source_code;
            }

        }
    }
}
