package com.haier.logistics.Helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.model.OrdShippingStatusQueue;
import com.haier.eis.model.VomShippingStatus;
import com.haier.eis.service.EisStockTrans2ExternalService;
import com.haier.eis.service.EisVomShippingStatusService;

import com.haier.eis.service.OrdShippingStatusQueueService;
import com.haier.logistics.Model.OrderModel;
import com.haier.logistics.service.EisInterfaceDataLogApiService;
import com.haier.logistics.service.OrderRebackService;
import com.haier.logistics.service.OrderService;
import com.haier.logistics.service.StockCommonService;
import com.haier.logistics.service.VomOrderService;
import com.haier.logistics.services.HpDispatchServiceImpl;
import com.haier.purchase.data.model.GoodsBackInfoResponse;
import com.haier.shop.model.AllotNetPoint;
import com.haier.shop.model.DateFormatUtilNew;
import com.haier.shop.model.ExpressInfos;
import com.haier.shop.model.Json;
import com.haier.shop.model.LesShippingInfos;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.service.ExpressInfosService;
import com.haier.shop.service.LesShippingInfosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

@Service
public class OutSaleLogisticsHandler extends LogisticsHandler{
    @Autowired
    private EisStockTrans2ExternalService eisStockTrans2ExternalDao;
    @Autowired
    private EisVomShippingStatusService vomShippingStatusDao;
    @Autowired
    private VomOrderService vomOrderService;
    @Autowired
    private StockCommonService stockCommonService;
    @Autowired
    private LesShippingInfosService lesShippingInfosDao;
    @Autowired
    private OrdShippingStatusQueueService ordShippingStatusQueueDao;
    @Autowired
    private ExpressInfosService expressInfosDao;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UnableToProcessLogisticsHandler unableToProcessLogisticsHandler;
    @Autowired
    private OrderRebackService orderRebackService;
    @Autowired
    private HpDispatchServiceImpl hpDispatchService;
    @Autowired
    private OrderModel orderModel;
    public static final String SHIPPING_MODE_B2C   = "B2C";
    public static final String SHIPPING_MODE_B2B2C = "B2B2C";
    private static final String INVOICE_NAME = "日日顺物流";
    private static String INTERFACE_HP_HOP_SHOP_API = "hp_hop_shop_api";
    private static final Logger LOGGER = LoggerFactory.getLogger(OutSaleLogisticsHandler.class);
    public void process(VomShippingStatus shippingStatus) throws BusinessException {

        String refNo = shippingStatus.getOrderNo();
        String status = shippingStatus.getStatus();

        //DTD订单用户签收节点写入eis_stock_trans_2_external表中
        if (refNo.matches("WDDTD.+") || refNo.matches("WDD.+")) {
            if ("KQ".equals(status) || "HQ".equals(status) || "Q1".equals(status)
                    || "Q0".equals(status)) {
                saveStockTrans2External(shippingStatus, "YHQM");
            } else if ("WMS_ACCEPT".equals(status)) {
                saveStockTrans2External(shippingStatus, "ACCT");
            } else if ("WMS_FAILED".equals(status)) {
                saveStockTrans2External(shippingStatus, "FAIL");
            }
            setProcessSuccess(shippingStatus.getId(), "");
            return;
        }

        OrderProductsNew orderProducts = EisBuzHelper.getOrderProducts(orderService, refNo);//yes
        if (orderProducts == null) {
            //处理正品退货
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderNo", refNo);
            ServiceResult<GoodsBackInfoResponse> result = vomOrderService.getGoodsBackInfo(paramMap);
            if (!result.getSuccess()) {
                throw new BusinessException("处理正品退货[" + refNo + "]失败：" + result.getMessage());
            }
            GoodsBackInfoResponse goodsBackInfoResponse = result.getResult();
            if (goodsBackInfoResponse != null) {
                synVomStatus(shippingStatus);
                return;
            }

            if (LOGGER.isDebugEnabled()) {
                System.out.println(getClass().getName());
                LOGGER.debug(getClass().getName() + "处理完毕，不是销售出库记录，请求下一个处理者["
                        + unableToProcessLogisticsHandler.getClass().getName() + "]处理");
            }
            unableToProcessLogisticsHandler.process(shippingStatus);
            return;
        }

        String prefix = getClass().getName() + "处理[" + refNo + "," + status + "]:";

        OrdersNew order = getOrder(orderProducts.getOrderId());//yes
        if (order == null) {
            LOGGER.error(prefix + "数据错误：" + refNo + "关联的订单不存在");
            setProcessError(shippingStatus.getId(), "数据错误：" + refNo + "关联的订单不存在");
            return;
        }

        String storeCode = shippingStatus.getStoreCode();
        //****2018/8/24网单二期修改,当status为OO的时候，storeCode对应网单编码信息*start****
        //OO-已派工，附网点编码
        if ("OO".equalsIgnoreCase(status)){
            if (null != orderProducts.getStatus() &&
                orderProducts.getStatus().intValue() == OrderProductsNew.STATUS_CANCEL_CLOSE.intValue()){
                setProcessFailed(shippingStatus.getId(),"网单被取消关闭，无法派工");
                return;
            }

            //保存源数据
            AllotNetPoint allotNetPoint = new AllotNetPoint();
            allotNetPoint.setCUSTOMER_CODE(storeCode);
            allotNetPoint.setORDER_NO(refNo);
            allotNetPoint.setPROC_REMARK(shippingStatus.getContent());
            //派工时间

            allotNetPoint.setASSIGN_DATE(DateUtil.format(shippingStatus.getOperDate(),"yyyy-MM-dd HH:mm:ss"));
            //登记时间
            allotNetPoint.setENTER_TIME(DateUtil.format(shippingStatus.getAddTime(),"yyyy-MM-dd HH:mm:ss"));
            //VOM回传时间
            allotNetPoint.setCREATED_DATE(DateUtil.format(shippingStatus.getAddTime(),"yyyy-MM-dd HH:mm:ss"));
            //派工失败时间，这个为什么是必填？？？
            allotNetPoint.setSB_DATE(DateUtil.format(shippingStatus.getOperDate(),"yyyy-MM-dd HH:mm:ss"));
            //这个id目前关联vom_shopping_status表
            allotNetPoint.setApiLogsId(shippingStatus.getId());
            allotNetPoint.setCreateTime(new Date());
            allotNetPoint.setUpdateTime(new Date());
            String json = JSON.toJSONString(allotNetPoint);

            ServiceResult<String> saveResult = hpDispatchService.saveNetPointFromVom(json);
           if (saveResult.getSuccess()){
               setProcessSuccess(shippingStatus.getId(), "");
           }else {
               setProcessFailed(shippingStatus.getId(),saveResult.getResult());
           }
           return;
        }
        //****2018/8/24网单二期修改,当status为OO的时候，storeCode对应网单编码信息*end******

        String secCode = getSecCode(storeCode);
        if (StringUtil.isEmpty(secCode)) {
            LOGGER.error(prefix + "日日顺库存[" + storeCode + "]对应的WA库位不存在,请检查库位信息配置");
            setProcessFailed(shippingStatus.getId(),
                    "日日顺库存[" + storeCode + "]对应的WA库位不存在,请检查库位信息配置");
            return;
        }

        String shippingMode = orderProducts.getShippingMode();
        if (!SHIPPING_MODE_B2C.equals(shippingMode) && !SHIPPING_MODE_B2B2C.equals(shippingMode)) {
            shippingMode = SHIPPING_MODE_B2B2C;
        }
        try {
            //同步物流信息到商城
            syncToLesShippingInfos(orderProducts, status, shippingStatus.getOperDate(),
                    shippingStatus.getContent());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(prefix + "同步物流数据到商城完成");
            }

            //接单
            if ("WMS_ACCEPT".equals(status)) {
                updateCreateOrderToVomStatus(orderProducts, true);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(prefix + "更新开提单状态为成功完成");
                }
            }
            String message = "<receiveFlag>" + EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS
                    + "</receiveFlag>";
            Long startTime = System.currentTimeMillis();
            //回传预约送货时间
            /*if ("YE".equals(status) || "YD".equals(status)) {
                String hpReservationDate=shippingStatus.getContent();
//                OrderProductsNew orderProduct = orderRebackService.getOrderProductsByCOrderSn(orderProducts.getCOrderSn());
                //保存预约送货时间
                String reservationDate = hpReservationDate.substring(hpReservationDate.indexOf("：")+1);
                long reserveTime = DateUtil.parse(reservationDate,"yyyy-MM-dd HH:mm:ss").getTime()/1000;
                if (reserveTime != orderProducts.getHpReservationDate()){
                    orderProducts.setHpReservationDate(Integer.parseInt(String.valueOf(reserveTime)));
                    boolean flag =orderRebackService.saveHpReservationDateRelation(
                            orderProducts, hpReservationDate,
                            "HP回传网单数据");//保存订单日志
                    if (flag) {
                        orderRebackService.sendSms(orderProducts);
                    }
                    this.recordLog(JsonUtil.toJson(orderProducts), message,
                            System.currentTimeMillis() - startTime, INTERFACE_HP_HOP_SHOP_API,
                            orderProducts.getCOrderSn());
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(prefix + "保存回传预约送货完成");
                    }
                    setProcessSuccess(shippingStatus.getId(),"保存此HP回传预约送货时间成功");
                }else {
                    setProcessSuccess(shippingStatus.getId(),"已保存此HP回传预约送货时间,无需处理");
                }
                return;
            }*/

            //拒单
            if ("WMS_FAILED".equals(status)) {
                updateCreateOrderToVomStatus(orderProducts, false);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(prefix + "更新开提单状态为失败完成");
                }
            }

            //仓库出库
            if ("CK".equals(status)) {
                addOrderShippingStatusQueue(shippingStatus, orderProducts, order.getSource());
                if (StringUtil.isEmpty(orderProducts.getTsCode())) {//普通出库
                    processAfterLesShipped(shippingStatus, orderProducts, order, 0);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(prefix + "加入出库状态队列完成");
                        LOGGER.debug(prefix + "更新网单出库状态完成");
                    }
                } else {//转运第二次出库
                    processAfterLesShipped(shippingStatus, orderProducts, order, 2);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug(prefix + "加入出库状态队列完成");
                        LOGGER.debug(prefix + "第二次转运出库，更新网单出库状态完成");
                    }
                }
            }

            //转运第一次转运出库
            if ("ZC".equals(status)) {
                //出库状态队列
                processAfterLesShipped(shippingStatus, orderProducts, order, 0);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(prefix + "第一次转运出库，更新网单出库状态完成");
                }
            }

            //转运第一次入库
            if ("ZR".equals(status)) {
                processAfterLesShipped(shippingStatus, orderProducts, order, 1);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(prefix + "第一次转运入库，更新网单出库状态完成");
                }
            }

            //转运第二次出库
             if ("C1".equals(status)) {//为什么要屏蔽这部分代码
                 addOrderShippingStatusQueue(shippingStatus, orderProducts, order.getSource());
                 processAfterLesShipped(shippingStatus, orderProducts, order, 2);
                 if (LOGGER.isDebugEnabled()) {
                     LOGGER.debug(prefix + "加入出库状态队列完成");
                     LOGGER.debug(prefix + "第二次转运出库，更新网单出库状态完成");
                 }
             }

            if (SHIPPING_MODE_B2B2C.equals(shippingMode)
                    && (("W0").equals(status) || "WQ".equals(status) || "A2".equals(status)
                    || "A6".equals(status) || "G0".equals(status))) {
                //记录到达网点时间
                updateOrderWorkflowNetPointArriveTime(orderProducts.getId(),
                        shippingStatus.getOperDate());
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(prefix + "更新全流程到达网点时间完成");
                }
            }

            if (SHIPPING_MODE_B2C.equals(shippingMode) && "K0".equals(status)) {
                // 从 nodeDesc 中解析出快递公司和快递单号，存入 ExpressInfos 表
                addExpressInfos(orderProducts, shippingStatus.getContent());
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(prefix + "新增ExpressInfos完成");
                }
            }

            //2018/9/5 新增到达网点和网店出库节点处理W1 和 W2*********start**
            /*if ("W1".equalsIgnoreCase(shippingStatus.getStatus())){
                String mes[] = new String[1];
                boolean suc = orderModel.updateOrderWorkflowNetPointAcceptTime(
                    orderProducts.getCOrderSn(),shippingStatus.getOperDate() , mes);
                if (suc){
                    setProcessSuccess(shippingStatus.getId(),"网点收货处理成功");
                }else {
                    setProcessFailed(shippingStatus.getId(), StringUtils.isEmpty(mes[0])?"网点收货处理失败":mes[0]);
                }
                return;
            }
            if ("W2".equalsIgnoreCase(shippingStatus.getStatus())){
                String mes[] = new String[1];
                boolean suc = orderModel.updateOrderWorkflowNetPointShipTime(
                    orderProducts.getCOrderSn(),shippingStatus.getOperDate(), mes);
                if (suc){
                    setProcessSuccess(shippingStatus.getId(),"网点出库配送处理成功");
                }else {
                    setProcessFailed(shippingStatus.getId(), StringUtils.isEmpty(mes[0])?"网点出库配送处理失败":mes[0]);
                }
                return;
            }*/

            //2018/9/5 新增到达网点和网店出库节点处理W1 和 W2*********end****

            //KQ-快递签收 HQ-回访签收 Q1-直接短信签收 Q0-直接返单签收 A1-用户APP签收 W3-签收成功 Q3-微信用户签收 U1-顺丰用户签收
            if ((SHIPPING_MODE_B2C.equals(shippingMode) && "KQ".equals(status))
                    || "HJ".equals(status) || "HQ".equals(status) || "Q1".equals(status)
                    || "Q0".equals(status) || "A1".equals(status) || "W3".equals(status)
                    || "Q3".equals(status) || "U1".equals(status)) {
                //关闭网单
                completeCloseOrder(orderProducts, shippingStatus.getOperDate());

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(prefix + "完成关闭网单完成");
                }

            }

        } catch (Exception e) {
            LOGGER.error(getClass().getName() + "处理[" + refNo + "," + status + "]：同步物流数据到商城失败：", e);
            setProcessFailed(shippingStatus.getId(), "同步物流数据到商城失败" + e.getMessage());
            return;
        }
        setProcessSuccess(shippingStatus.getId(), "");
        LOGGER.info(prefix + "成功");
    }
    private void saveStockTrans2External(VomShippingStatus shippingStatus, String billType){
        LesStockTransQueue stockTransQueue = new LesStockTransQueue();
        stockTransQueue.setMerchantCode("DTD");
        stockTransQueue.setSku("");
        stockTransQueue.setSecCode("");
        stockTransQueue.setCorderSn(shippingStatus.getOrderNo());
        stockTransQueue.setOutping(shippingStatus.getExpNo());
        stockTransQueue.setBillTime(shippingStatus.getOperDate());
        stockTransQueue.setBillType(billType);
        stockTransQueue.setQuantity(0);
        stockTransQueue.setMark("H");
        stockTransQueue.setKunnrSaleTo(shippingStatus.getStoreCode());
        stockTransQueue.setKunnrSendTo("");
        stockTransQueue.setTknum("");
        stockTransQueue.setBwart("");
        stockTransQueue.setCharg("10");
        stockTransQueue.setBstkd("");
        stockTransQueue.setLesBillNo("VOM" + shippingStatus.getId());
        stockTransQueue.setAddTime(new Date());

        String lesBillNo = stockTransQueue.getLesBillNo();
        if (!StringUtil.isEmpty(lesBillNo)) {
            LesStockTransQueue transQueue2 = eisStockTrans2ExternalDao.getByLesBillNo(lesBillNo);
            if (transQueue2 != null) {
                LOGGER.info("eis_stock_trans_2_external中记录已经存在，不再插入，lesBillNo：" + lesBillNo);
                return;
            }
        }
        eisStockTrans2ExternalDao.insert(stockTransQueue);
    }
    protected int setProcessSuccess(Integer id, String msg) {
        return setProcessStatus(id, VomShippingStatus.PROCESS_STATUS_DOWN,
                VomShippingStatus.PROCESS_STATUS_WAIT, msg);
    }
    /*protected int setProcessStatus(Integer id, Integer statusUpdateTo, Integer statusUpdateFrom,
                                   String msg) {
        msg = dealMsg(msg);
        return vomShippingStatusDao.updateProcessStatus(id, statusUpdateTo, statusUpdateFrom, msg);
    }*/
    private void synVomStatus(VomShippingStatus shippingStatus) {
        String refNo = shippingStatus.getOrderNo();
        String status = shippingStatus.getStatus();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNo", refNo);
        if ("WMS_ACCEPT".equals(status)) {//接单
            paramMap.put("status", "140");//140-同步到VOM
        } else if ("WMS_FAILED".equals(status)) {//拒单
            paramMap.put("status", "-120");//-120-VOM拒单
            paramMap.put("errorMsg", shippingStatus.getContent());//拒单原因
        } else {
            setProcessSuccess(shippingStatus.getId(), "");
            return;
        }

                ServiceResult<Boolean> result = vomOrderService.synVomStatus(paramMap);
        if (!result.getSuccess() || !result.getResult()) {
            setProcessFailed(shippingStatus.getId(),
                    "处理正品退货[" + refNo + "]出现错误：" + result.getMessage());
        } else {

            setProcessSuccess(shippingStatus.getId(), "");
        }
    }
    private String getSecCode(String rrsSecCode) {
        ServiceResult<String> result = stockCommonService.getWhCodeByCenterCode(rrsSecCode);
        if (!result.getSuccess())
            throw new BusinessException("通过日日顺C码获取仓库编码失败：" + result.getMessage());
        if (StringUtil.isEmpty(result.getResult())) {
            throw new BusinessException("通过日日顺C码获取仓库编码失败:不可识别的C码（" + rrsSecCode + "）");
        }
        return result.getResult() + "WA";
    }
    private void updateCreateOrderToVomStatus(OrderProductsNew orderProducts, boolean isSuccess) {
        ServiceResult<Boolean> result = orderService.updateLesStatusToOrder(orderProducts.getId(),
                isSuccess);
        if (!result.getSuccess()) {
            throw new BusinessException("更新网单的开提单状态失败：" + result.getMessage());
        } else if (!result.getResult()) {
            throw new BusinessException("更新网单的开提单状态失败");
        }
    }
    private void syncToLesShippingInfos(OrderProductsNew orderProducts, String nodeCode, Date nodeTime,
                                        String msg) {
        List<LesShippingInfos> lesShippingInfos = lesShippingInfosDao
                .getByCorderSn(orderProducts.getCOrderSn());
        boolean isExist = false;
        for (LesShippingInfos lesShippingInfo : lesShippingInfos) {
            if (lesShippingInfo.getNodeCode().equals(nodeCode)) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            LesShippingInfos lesShippingInfo = new LesShippingInfos();
            lesShippingInfo.setOrderId(orderProducts.getOrderId());
            lesShippingInfo.setOrderProductId(orderProducts.getId());
            lesShippingInfo.setcOrderSn(orderProducts.getCOrderSn());
            lesShippingInfo.setNodeTime(DateUtil.format(nodeTime, "yyyy-MM-dd HH:mm:ss"));
            lesShippingInfo.setDeliveryType("2");
            lesShippingInfo.setNodeCode(nodeCode);
            lesShippingInfo.setNodeDesc(msg);
            lesShippingInfo.setLogId(1);
            lesShippingInfo.setSyncTBStatus(0);
            lesShippingInfosDao.insert(lesShippingInfo);
        }
    }
    private OrdersNew getOrder(Integer orderId) {
        ServiceResult<OrdersNew> result = orderService.getOrder(orderId);
        if (!result.getSuccess()) {
            throw new BusinessException("获取Orders失败：" + result.getMessage());
        }
        return result.getResult();
    }
    private void addOrderShippingStatusQueue(VomShippingStatus shippingStatus, OrderProductsNew op,
                                             String source) {
        Integer orderId = op.getOrderId();
        Integer orderProductId = op.getId();
        OrdShippingStatusQueue queue = new OrdShippingStatusQueue();
        queue.setCount(0);
        queue.setExpressName(INVOICE_NAME);
        queue.setExpressNumber(shippingStatus.getExpNo());
        queue.setLesShipTime(shippingStatus.getOperDate());
        queue.setOrderId(orderId);
        queue.setOrderProductId(orderProductId);
        queue.setcOrderSn(op.getCOrderSn());
        queue.setOrderSource(source);
        queue.setOutping(shippingStatus.getExpNo());
        queue.setStatus(OrdShippingStatusQueue.STATUS_UN_PROCESSED);
        try {
            ordShippingStatusQueueDao.insert(queue);
        } catch (Exception ex) {
            LOGGER.error("新增发货状态同步队列时发生未知异常：" + ex.getMessage());
        }
    }
    private void processAfterLesShipped(VomShippingStatus shippingStatus,
                                        OrderProductsNew orderProduct, OrdersNew order, int type) {
        ServiceResult<Boolean> myResult = orderService.processAfterLesShipped(order, orderProduct,
                type, shippingStatus.getExpNo(), shippingStatus.getOperDate(),
                shippingStatus.getExpNo(), INVOICE_NAME);
        if (!myResult.getSuccess() || !myResult.getResult()) {
            throw new BusinessException("LES出库后更新网单状态失败：" + myResult.getMessage());
        }
    }
    private void updateOrderWorkflowNetPointArriveTime(Integer orderProductId,
                                                       Date netPointArriveTime) {
        ServiceResult<Boolean> result = orderService
                .updateOrderWorkflowNetPointArriveTime(orderProductId, netPointArriveTime);
        if (!result.getSuccess()) {
            throw new BusinessException("更新订单全流程中的到达网单时间失败：" + result.getMessage());
        }
        if (!result.getResult()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("为更新到达网点时间，原时间已经更新");
            }
        }
    }
    public static final String EMS = "EMS";
    public static final String SF  = "顺丰快递";
    private void addExpressInfos(OrderProductsNew orderProducts, String nodeDesc) {
        if (StringUtil.isEmpty(nodeDesc)) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("无法解析快递信息，节点信息为空");
            }
            return;
        }
        String expressCompany;
        String expressNumber;
        if (nodeDesc.lastIndexOf(EMS) > 0) {
            expressCompany = "EMS";
        } else if (nodeDesc.lastIndexOf(SF) > 0) {
            expressCompany = "SF";
        } else {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("节点信息'" + nodeDesc + "'中不包括'" + EMS + "'和'" + SF + "'");
            }
            return;
        }

        if (nodeDesc.contains(":")) {//英文冒号
            expressNumber = nodeDesc.substring(nodeDesc.lastIndexOf(":") + 1);
        } else if (nodeDesc.contains("：")) {//中文冒号
            expressNumber = nodeDesc.substring(nodeDesc.lastIndexOf("：") + 1);
        } else {
            expressNumber = "";
        }

        expressNumber = expressNumber.trim();

        ExpressInfos expressInfo = new ExpressInfos();
        expressInfo.setAddTime(new Date().getTime() / 1000);
        expressInfo.setOrderId(orderProducts.getOrderId());
        expressInfo.setOrderProductId(orderProducts.getId());
        expressInfo.setCorderSn(orderProducts.getCOrderSn());
        expressInfo.setExpressCompany(expressCompany);
        expressInfo.setExpressNumber(expressNumber);
        expressInfosDao.insert(expressInfo);
    }
    private void completeCloseOrder(OrderProductsNew orderProducts, Date signTime) {
        ServiceResult<Boolean> result = orderService
                .completeCloseOrderProduct(orderProducts.getCOrderSn(), signTime);
        if (!result.getSuccess()) {
            throw new BusinessException("完成关闭网单失败：" + result.getMessage());
        }
    }
    /**
     * 记录接口日志
     * @param requestData
     * @param responseData
     * @param elapsedTime
     */
    @Autowired
    private EisInterfaceDataLogApiService eisInterfaceDataLogApiService;
    private void recordLog(String requestData, String responseData, Long elapsedTime,
                           String interfaceCode, String cOrderSn) {
        //记录接口日志
        try {
            EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
            dataLog.setForeignKey(cOrderSn);
            dataLog.setInterfaceCode(interfaceCode);
            dataLog.setRequestData(requestData);
            dataLog.setRequestTime(new Date());
            dataLog.setErrorMessage("");
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setResponseData(responseData);
            dataLog.setResponseTime(new Date());
            dataLog.setElapsedTime(elapsedTime);
            eisInterfaceDataLogApiService.record(dataLog);
        } catch (Exception e) {
            LOGGER.error("recordLog:接口发生异常，接口名称是:" + interfaceCode + "记录接口日志失败:" + e);
        }
    }
}
