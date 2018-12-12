package com.haier.logistics.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.logistics.Helper.ReturnOfStoreLogisticsHandler;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceStatus;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.model.VomContentHandler;
import com.haier.eis.model.VomInOutStoreItem;
import com.haier.eis.model.VomInOutStoreOrder;
import com.haier.eis.model.VomReceivedQueue;
import com.haier.eis.model.VomShippingStatus;
import com.haier.eis.service.EisInterfaceStatusService;
import com.haier.eis.service.EisLesStockTransQueueService;
import com.haier.eis.service.EisStockTrans2ExternalService;
import com.haier.eis.service.EisVomInOutStoreItemService;
import com.haier.eis.service.EisVomInOutStoreOrderService;
import com.haier.eis.service.EisVomReceivedQueueService;
import com.haier.eis.service.EisVomShippingStatusService;
import com.haier.logistics.Helper.OutSaleLogisticsHandler;
import com.haier.logistics.services.RRSOutInStoreVomContentHandler;
import com.haier.logistics.services.RrsStatusBackVomContentHandler;
import com.haier.logistics.services.StockCommonServiceImpl;
import com.haier.logistics.services.VomOrderServiceImpl;
import com.haier.purchase.data.model.GoodsBackInfoResponse;
import com.haier.stock.model.InventoryBusinessTypes;
@Service
public class EisVOMModel {
    public static final Logger LOGGER = LoggerFactory.getLogger(EisVOMModel.class);
    @Autowired
    private ReturnOfStoreLogisticsHandler logisticsHandler;
    @Autowired
    private EisVomShippingStatusService eisVomShippingStatusService;
    @Autowired
    private EisVomInOutStoreItemService eisVomInOutStoreItemService;
    @Autowired
    private EisVomInOutStoreOrderService eisVomInOutStoreOrderService;
/*
    @Autowired
    private DataSourceTransactionManager transactionManagerEis;
*/
    @Autowired
    private EisStockTrans2ExternalService eisStockTrans2ExternalDao;
    @Autowired
    private StockCommonServiceImpl stockCommonServiceImpl;
    private List<VomContentHandler> vomContentHandlers;
    @Autowired
    private EisVomReceivedQueueService        eisVomReceivedQueueService;
    @Autowired
    private EisLesStockTransQueueService eisLesStockTransQueueService;
    @Autowired
    private EisInterfaceStatusService eisInterfaceStatusDao;
    @Autowired
    private VomOrderServiceImpl vomOrderService;
    @Autowired
    private RrsStatusBackVomContentHandler rrsStatusBackVomContentHandler;
    @Autowired
    private RRSOutInStoreVomContentHandler rrsOutInStoreVomContentHandler;
    public boolean processVomShippingStatus() {
        List<VomShippingStatus> shippingStatuses = eisVomShippingStatusService.getByProcessStatus(0);
        for (VomShippingStatus shippingStatus : shippingStatuses) {
            try {
                logisticsHandler.process(shippingStatus);
            } catch (Exception ex) {
                LOGGER.error("处理物流状态失败：", ex);
            }
        }
        return true;
    }

    /**
     * 现在只切换了销售出库，先生成les_stock_trans_queue，这样修改较少。
     * 等全部秀焕了，再生成库存交易
     * @return
     */
    public boolean processVomInOutStore() {
        List<VomInOutStoreOrder> delayOrders = eisVomInOutStoreOrderService.getByProcessStatus(VomInOutStoreOrder.PROCESS_STATUS_NEW, VomInOutStoreOrder.DELAY_YES);
        for (VomInOutStoreOrder delayOrder : delayOrders) {
            try {
                processVomInOutStore(delayOrder);
            } catch (Exception e) {
                LOGGER.error("处理延后的VomInOutStoreOrder(id=" + delayOrder.getId() + ")失败:", e);
                eisVomInOutStoreOrderService.setDelay(delayOrder.getId(), e.getMessage());
            }
        }
        while (true) {
            List<VomInOutStoreOrder> orders = eisVomInOutStoreOrderService.getByProcessStatus(
                VomInOutStoreOrder.PROCESS_STATUS_NEW, VomInOutStoreOrder.DELAY_NO);
            if (orders.size() <= 0) {
                break;
            }
            for (VomInOutStoreOrder order : orders) {
                try {
                    processVomInOutStore(order);
                } catch (Exception e) {
                    LOGGER.error("处理VomInOutStoreOrder(id=" + order.getId() + ")失败:", e);
                    eisVomInOutStoreOrderService.setDelay(order.getId(), e.getMessage());
                }
            }
        }
        return true;
    }
    private void processVomInOutStore(VomInOutStoreOrder vomInOutStoreOrder) {
        List<VomInOutStoreItem> items = eisVomInOutStoreItemService.getNotProcessByOrderId(vomInOutStoreOrder.getId());

        String secCode = getSecCode(vomInOutStoreOrder.getStoreCode());

        String remark = "";

        boolean isAllItemsProcessSuccess = true;
        for (VomInOutStoreItem item : items) {
            LesStockTransQueue stockTransQueue = new LesStockTransQueue();
            stockTransQueue.setLesBatchId(item.getId());
            stockTransQueue.setSku(item.getHrCode());
            stockTransQueue.setCorderSn(vomInOutStoreOrder.getOrderNo());
            stockTransQueue.setSecCode(secCode);
            stockTransQueue.setOutping(vomInOutStoreOrder.getCertification());
            stockTransQueue.setBillTime(vomInOutStoreOrder.getOutInDate());
            stockTransQueue.setBillType(transferVomOrderType(vomInOutStoreOrder.getOrderType(),
                vomInOutStoreOrder.getBusType()));
            stockTransQueue.setQuantity(item.getNumber());
            stockTransQueue.setLesBillNo(vomInOutStoreOrder.getExpNo() + item.getItemNo());
            stockTransQueue.setMark(vomInOutStoreOrder.getBusType() == 1 ? "H" : "S");
            stockTransQueue.setKunnrSaleTo("");
            stockTransQueue.setKunnrSendTo("");
            stockTransQueue.setBwart("");
            stockTransQueue.setCharg(item.getStorageType());
            stockTransQueue.setData("");
            stockTransQueue.setStatus(LesStockTransQueue.STATUS_UNDONE);
            stockTransQueue.setFinanceStatus(0);
            stockTransQueue.setAddTime(new Date());
            stockTransQueue.setChannelCode("WA");
            stockTransQueue.setVersionCode("");
            stockTransQueue.setZeile("000" + item.getItemNo());

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            /*TransactionStatus status = transactionManagerEis.getTransaction(def);*/
            try {
                int n = eisVomInOutStoreItemService.updateProcessStatus(item.getId(),VomInOutStoreOrder.PROCESS_STATUS_STOCK_TRANSACTION_HAS_GENERATED,VomInOutStoreOrder.PROCESS_STATUS_NEW);
                if (n <= 0) {
                    if (LOGGER.isWarnEnabled()) {
                        LOGGER.warn("处理VomInOutStoreItem(id=" + item.getId() + ")出现并发情况");
                    }
                    remark += "并发情况，itemId=" + item.getId();
                    isAllItemsProcessSuccess = false;
                } else {
                    LesStockTransQueue transQueue = eisLesStockTransQueueService.getByOrderSn(stockTransQueue.getCorderSn(), stockTransQueue.getSku());
                    //3W单子，LES会先于VOM回传，但是不需要LES回传的信息，如果存在就删掉，优先用VOM回传出库信息
                    if (vomInOutStoreOrder.getOrderType().equals(5)
                        && (vomInOutStoreOrder.getBusType().equals(1)
                            || vomInOutStoreOrder.getBusType().equals(2))
                        && vomInOutStoreOrder.getOrderNo().startsWith("WDX") && transQueue != null
                        && "YTI1".equalsIgnoreCase(transQueue.getBillType())) {
                        LOGGER
                            .info("[VOM][ProcessVomInOutStore]:3W存在重复的记录,LES先回传，删除-"
                                  + stockTransQueue.getCorderSn() + "," + stockTransQueue.getSku());
                        //删除
                        eisLesStockTransQueueService.delete(transQueue.getId());
                        //重新插入VOM回传数据
                        eisLesStockTransQueueService.insert(stockTransQueue);
                        //DTD订单出库和退货入库要写入eis_stock_trans_2_external
                        if (stockTransQueue.getCorderSn().matches("WDDTD.+")
                            || stockTransQueue.getCorderSn().matches("WDD.+")) {
                            stockTransQueue.setMerchantCode("DTD");
                            if (InventoryBusinessTypes.IN_RETURNED.getCode()
                                .equals(stockTransQueue.getBillType())) {
                                stockTransQueue.setBillType("THRC");
                            }
                            saveStockTrans2External(stockTransQueue);
                        }
                    } else {
                        if (transQueue != null) {
                            LOGGER.info("[VOM][ProcessVomInOutStore]:存在重复的记录-"
                                        + stockTransQueue.getCorderSn() + ","
                                        + stockTransQueue.getSku());
                        } else {
                            eisLesStockTransQueueService.insert(stockTransQueue);
                            //DTD订单出库和退货入库要写入eis_stock_trans_2_external
                            if (stockTransQueue.getCorderSn().matches("WDDTD.+")
                                || stockTransQueue.getCorderSn().matches("WDD.+")) {
                                stockTransQueue.setMerchantCode("DTD");
                                if (InventoryBusinessTypes.IN_RETURNED.getCode()
                                    .equals(stockTransQueue.getBillType())) {
                                    stockTransQueue.setBillType("THRC");
                                }
                                saveStockTrans2External(stockTransQueue);
                            }
                        }
                    }
                }

                /*transactionManagerEis.commit(status);*/
            } catch (Exception e) {
                isAllItemsProcessSuccess = false;
                /*transactionManagerEis.rollback(status);*/
                LOGGER.error("处理VomInOutStoreItem(id=" + item.getId() + ")出现错误：", e);

                remark += e.getMessage();
                break;
            }
        }

        if (isAllItemsProcessSuccess) {
            eisVomInOutStoreOrderService.updateProcessStatus(vomInOutStoreOrder.getId(),
                VomInOutStoreOrder.PROCESS_STATUS_STOCK_TRANSACTION_HAS_GENERATED,
                VomInOutStoreOrder.PROCESS_STATUS_NEW);
        } else {
            if (remark.length() > 255) {
                remark = remark.substring(0, 255);
            }
            eisVomInOutStoreOrderService.setDelay(vomInOutStoreOrder.getId(), remark);
        }

    }
    private String getSecCode(String rrsSecCode) {
        ServiceResult<String> result = stockCommonServiceImpl.getWhCodeByCenterCode(rrsSecCode);
        if (!result.getSuccess())
            throw new BusinessException("通过日日顺C码获取仓库编码失败：" + result.getMessage());
        if (StringUtil.isEmpty(result.getResult())) {
            throw new BusinessException("通过日日顺C码获取仓库编码失败：不可识别的C码：" + rrsSecCode);
        }

        return result.getResult() + "WA";
    }
    private String transferVomOrderType(int orderType, int busType) {
        String inventoryBusinessType;
        switch (orderType) {
            case 1://采购入库
                inventoryBusinessType = InventoryBusinessTypes.IN_PURCHASE.getCode();
                break;
            case 2://销售出库
                inventoryBusinessType = InventoryBusinessTypes.OUT_SALE.getCode();
                break;
            case 3://退货入库
            case 10://网点取货
                inventoryBusinessType = InventoryBusinessTypes.IN_RETURNED.getCode();
                break;
            case 6://调拨
                if (busType == 2) {
                    inventoryBusinessType = InventoryBusinessTypes.IN_TRANSFER.getCode();
                } else if (busType == 1) {
                    inventoryBusinessType = InventoryBusinessTypes.OUT_TRANSFER.getCode();
                } else {
                    inventoryBusinessType = InventoryBusinessTypes.UNDEFINED.getCode();
                }
                break;
            case 11://拒收入库
                inventoryBusinessType = InventoryBusinessTypes.IN_REFUSE.getCode();
                break;
            case 5://普通出库//2016-09-27 3W 新增逻辑，之前不处理
                if (busType == 1 || busType == 2) {
                    inventoryBusinessType = InventoryBusinessTypes.OUT_TRANSFER.getCode();
                } else {
                    inventoryBusinessType = InventoryBusinessTypes.UNDEFINED.getCode();
                }
                break;
            case 4://取件
            case 7://第三方运输订单
            case 8://客户调货出库
            case 9://客户调货入库
            default:
                inventoryBusinessType = InventoryBusinessTypes.UNDEFINED.getCode();

        }
        return inventoryBusinessType;
    }
    private void saveStockTrans2External(LesStockTransQueue stockTransQueue) {
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

    public boolean reprocessingContent() {
        List<VomReceivedQueue> receivedQueuesForReProcess = eisVomReceivedQueueService.getByStatus(VomReceivedQueue.STATUS_NEW);
        for (VomReceivedQueue receivedQueueForReProcess : receivedQueuesForReProcess) {
            String buType = receivedQueueForReProcess.getBuType();
            VomContentHandler vomContentHandler = getHandler(buType);
            if (vomContentHandler == null) {
                LOGGER.error("预处理VOM消息（id=" + receivedQueueForReProcess.getId()
                             + "）失败：VOM content handler（buType=" + buType
                             + "） 不存在，请查看Spring注入配置是否正确");
                eisVomReceivedQueueService.updateError(receivedQueueForReProcess.getId(), "未定义的buType");
                continue;
            }
            boolean result = vomContentHandler.reprocess(receivedQueueForReProcess);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("预处理VOM消息（id=" + receivedQueueForReProcess.getId() + ",buType="
                             + buType + ")完成，结果：" + result);
            }
        }
        return true;
    }
    private VomContentHandler getHandler(String buType) {
    	if ("rrs_statusback".equals(buType)) {
    		return  rrsStatusBackVomContentHandler;
    	}else if("rrs_outinstore".equals(buType)){
    		return rrsOutInStoreVomContentHandler;
    	}else {
    		return null;
    	}
////    	vomContentHandlers.add(new RrsStatusBackVomContentHandler());
////    	vomContentHandlers.add(new RRSOutInStoreVomContentHandler());
//        for (VomContentHandler vomContentHandler : vomContentHandlers) {
//            if (vomContentHandler.getBuType().equals(buType))
//                return vomContentHandler;
//        }
//        return null;
    }
    

    public boolean addReceivedInformation(VomReceivedQueue vomReceivedQueue) {
        Assert.notNull(vomReceivedQueue, "VOM received Information must not be null");
        String outCode = vomReceivedQueue.getOutCode();
        if (StringUtil.isEmpty(outCode)) {
            throw new BusinessException("outCode 为空");
        }
        int n = eisVomReceivedQueueService.outCode(outCode);
        if (n > 0) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("消息（outCode=" + outCode + "）已存在，不再新增");
            }
            return true;
        }
        vomReceivedQueue.setAddTime(new Date());
        return eisVomReceivedQueueService.insert(vomReceivedQueue) > 0;
    }
    
    /**
     * 处理正品退货拒单情况下验证失败的数据
     * 处理逻辑：将数据状态同步给采购系统，同步完成后修改vom_received_queue.status的值为3-已同步采购系统
     */
    public void processQualityGoods() {

        //查询上次执行后最大的id值
        EisInterfaceStatus eisInterfaceStatus = eisInterfaceStatusDao
            .getByInterfaceCode(EisInterfaceStatus.NTERFACE_CODE_VOM_RECEIVED_QUEUE_ID);
        if (eisInterfaceStatus == null) {
            LOGGER.info("[processQualityGoods]没有vom_received_queue_id这个接口变量");
            return;
        }
        List<VomReceivedQueue> vomReceivedQueueList = eisVomReceivedQueueService
            .getByIdStatus(eisInterfaceStatus.getLastId(), VomReceivedQueue.STATUS_FAIL);
        if (vomReceivedQueueList == null || vomReceivedQueueList.isEmpty()) {
            LOGGER.info("[processQualityGoods]没有需要处理的数据");
            return;
        }

        try {
            Integer lastId = 0;
            for (VomReceivedQueue vomReceivedQueue : vomReceivedQueueList) {
                lastId = vomReceivedQueue.getId();//记录每次处理结束后的id，循环处理结束后的id为本次job运行处理的最大id
                if (VomReceivedQueue.STATUS_FAIL != vomReceivedQueue.getStatus().intValue()) {
                    LOGGER.info(
                        "[processQualityGoods]状态[" + vomReceivedQueue.getStatus() + "]，已不是处理失败的状态");
                    continue;
                }
                String type = vomReceivedQueue.getType();
                if (StringUtil.isEmpty(type) || !"xml".equalsIgnoreCase(type)) {
                    LOGGER.info("[processQualityGoods]暂时只支持xml格式的报文");
                    continue;
                }
                Document document = DocumentHelper.parseText(vomReceivedQueue.getContent());
                document.setXMLEncoding("utf-8");
                Element rootElement = document.getRootElement();
                VomShippingStatus vomShippingStatus = parseContent(rootElement);
                if (!"WMS_FAILED".equalsIgnoreCase(vomShippingStatus.getStatus())) {//不是拒单不处理
                    continue;
                }
                String orderNo = vomShippingStatus.getOrderNo();
                if (StringUtil.isEmpty(orderNo, true)) {
                    LOGGER.info("[processQualityGoods]单号为空");
                    continue;
                }
                //判断是否是正品退货单子
                Map<String, Object> queryParamMap = new HashMap<String, Object>();
                queryParamMap.put("orderNo", orderNo);
                ServiceResult<GoodsBackInfoResponse> queryResult = vomOrderService
                    .getGoodsBackInfo(queryParamMap);
                if (queryResult == null || !queryResult.getSuccess()) {//判断是否是正品退货单子，判断失败，停止本次处理，下一次运行时重新处理
                    throw new BusinessException(
                        "单号" + orderNo + "，查询正品退货信息失败：" + (queryResult == null ? ""
                            : queryResult.getMessage()));
                }
                if (queryResult.getResult() == null) {//不是正品退货不处理
                    continue;
                }
                String errorMsg = vomShippingStatus.getContent();
                if (!StringUtil.isEmpty(vomReceivedQueue.getErrorMessage(), true)) {
                    errorMsg = errorMsg + "|" + vomReceivedQueue.getErrorMessage();
                }
                Map<String, Object> updateParamMap = new HashMap<String, Object>();
                updateParamMap.put("orderNo", orderNo);
                updateParamMap.put("status", "-120");//-120-VOM拒单
                updateParamMap.put("errorMsg", errorMsg);

                ServiceResult<Boolean> result = vomOrderService.synVomStatus(updateParamMap);
                if (result == null || !result.getSuccess()) {//处理失败，停止本次处理，下一次运行时重新处理
                    throw new BusinessException("单号" + orderNo + "，处理正品退货拒单失败："
                                                + (result == null ? "" : result.getMessage()));
                }
                eisVomReceivedQueueService.updateStatusById(lastId, VomReceivedQueue.STATUS_SYNCH);
            }

            eisInterfaceStatus.setLastId(lastId);
            eisInterfaceStatus.setLastTime(new Date());
            eisInterfaceStatus.setUpdateTime(new Date());
            eisInterfaceStatusDao.update(eisInterfaceStatus);

        } catch (Exception e) {
            LOGGER.error("[processQualityGoods]处理正品退货拒单情况出现异常", e);
        }

    }
    private VomShippingStatus parseContent(Element rootElement) {
        VomShippingStatus vomShippingStatus = new VomShippingStatus();
        vomShippingStatus.setStoreCode(rootElement.elementTextTrim("storecode"));
        vomShippingStatus.setOrderNo(rootElement.elementTextTrim("orderno"));
        vomShippingStatus.setExpNo(rootElement.elementTextTrim("expno"));
        vomShippingStatus.setOperator(rootElement.elementTextTrim("operator"));
        vomShippingStatus.setOperContact(rootElement.elementTextTrim("opercontact"));
        vomShippingStatus.setOperDate(
            DateUtil.parse(rootElement.elementTextTrim("operdate"), "yyyy-MM-dd HH:mm:ss"));
        vomShippingStatus.setStatus(rootElement.elementTextTrim("status"));
        vomShippingStatus.setContent(rootElement.elementTextTrim("content"));
        vomShippingStatus.setRemark(rootElement.elementTextTrim("remark"));
        vomShippingStatus.setProcessStatus(0);
        return vomShippingStatus;
    }
}
