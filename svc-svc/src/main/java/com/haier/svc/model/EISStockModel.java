package com.haier.svc.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.common.util.DateUtil;
import com.haier.eis.model.EisStockBusinessQueue;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.EisStockBusinessQueueService;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.service.PurchaseOrderService;
import com.haier.shop.model.OrderProductsNew;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.service.OrderService;
import com.haier.stock.service.TransferLineService;
import com.haier.stock.service.VomOrderService;
import com.haier.svc.helper.EisBuzHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
/**
 * Model of EisVOMService
 * Created by 钊 on 2014/7/21.
 */
@Service("eisStockModel")
public class EISStockModel {
    private Logger              logger   = LoggerFactory.getLogger(EISStockModel.class);
    @Autowired
    private LesStockTransQueueService lesStockTransQueueService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private VomOrderService vomOrderService;
    @Autowired
    private TransferLineService transferLineService;
    @Autowired
    private EisStockBusinessQueueService eisStockBusinessQueueService;


    public boolean processStockBusinessQueues() {
        List<EisStockBusinessQueue> stockBusinessQueues = eisStockBusinessQueueService.getTops(500);
        if (stockBusinessQueues.size() == 0)
            return true;
        for (EisStockBusinessQueue queue : stockBusinessQueues) {
            LesStockTransQueue stockTransQueue = lesStockTransQueueService
                    .getById(queue.getStockTransQueueId());
            if (stockTransQueue == null) {
                logger.error("处理库存业务队列错误：未知的 stock_trans_queue_id:" + queue.getId());
                eisStockBusinessQueueService.delete(queue);
                continue;
            }
            InventoryBusinessTypes bizType = InventoryBusinessTypes
                    .getByCode(stockTransQueue.getBillType());

            try {
                eisStockBusinessQueueService.delete(queue);
                switch (bizType) {
                    case IN_PURCHASE:
                       //确定是否为日日单
                        String pdCorderSn = stockTransQueue.getBstkd();
                        boolean isProduceDaily = false;
                        OrderProductsNew orderProduct;
                        if (!StringUtil.isEmpty(pdCorderSn) && pdCorderSn.startsWith("WD")) {
                            orderProduct = EisBuzHelper.getOrderProducts(orderService, pdCorderSn);
                            if (orderProduct != null) {
                                Integer pdOrderStatus = orderProduct.getPdOrderStatus();
                                if (pdOrderStatus != null && pdOrderStatus > 0) {
                                    isProduceDaily = true;
                                    processProduceDaily(stockTransQueue);
                                }
                            }
                        }
                        if (!isProduceDaily) {
                            processPurchaseOrder(stockTransQueue.getCorderSn(),
                                    stockTransQueue.getQuantity(), stockTransQueue.getBillTime());
                        }
                        break;
                    case IN_TRANSFER:
                    case OUT_TRANSFER:
                       dealTransferLine(bizType, stockTransQueue.getCorderSn(),
                               stockTransQueue.getQuantity(), stockTransQueue.getBillTime());
                       break;
                    case OUT_SALE:
                        returnVomStatus(stockTransQueue, "150");//已出库
                        break;
                    case IN_REFUSE:
                        break;
                    case IN_RETURNED:
                        //获取正品退货关联的渠道
                        //根据单据号判断是否以‘TH*'等结尾来判断是否为销售网单退货，如果不是销售网单则为退货入库逆向网单
                        String refNo = stockTransQueue.getCorderSn();
                        boolean isReturnOfStore = refNo.indexOf("TH") == 14
                                || refNo.indexOf("RB") == 14
                                || refNo.indexOf("IB") == 14
                                || refNo.indexOf("CX") == 14;
                        if (isReturnOfStore) {
                            ServiceResult<Boolean> myResult = orderService.repairOrderIn(
                                    stockTransQueue.getCorderSn(), stockTransQueue.getOutping());
                            if (!myResult.getSuccess() || !myResult.getResult()) {
                                throw new BusinessException(
                                        "处理退货入库记录[" + stockTransQueue.getCorderSn() + "]失败："
                                                + myResult.getMessage());
                            }
                        } else {
                            returnVomStatus(stockTransQueue, "180");//已入库
                        }
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                logger.error("库存变化后，处理关联业务出现错误：" + e.getMessage());
            }
        }
        return true;
    }


    private void dealTransferLine(InventoryBusinessTypes bizType, String corderSn, Integer qty,
                                  Date billTime) {
        // 调拨单出入库处理：获取调拨单获取渠道信息，调拨出库需要释放商城的冻结库存，
        ServiceResult<InvTransferLine> rs = transferLineService
                .getInvTransferLineByLineNum(corderSn);
        if (!rs.getSuccess())
            throw new BusinessException("通过调拨单号获取调拨单失败：" + rs.getMessage());
        InvTransferLine transferLine = rs.getResult();
        if (transferLine == null) {
            logger.warn("调拨单已不存在，调拨单号：" + corderSn);
            return;
        }

        String remark = "";

        //        String channelCode = transferLine.getChannelId();
        //        if (StringUtil.isEmpty(channelCode)) {
        //            logger.warn("调拨单关联的渠道编码为空,调拨单号：" + corderSn);
        //            remark = "调拨单关联的渠道编码为空";
        //        }

        if (!transferLine.getTransferQty().equals(qty)) {
            logger.info("LES回传的出\\入库数量与调拨单不符");
            remark = "LES回传的出\\入库数量与调拨单不符";
        }
        if (InventoryBusinessTypes.IN_TRANSFER == bizType) {
            //调拨入库：更新调拨单状态为已完成
            if (!InvTransferLine.LINE_STATUS_STORE_IN.equals(transferLine.getLineStatus())) {
                logger.warn("LES调拨入库完成后调拨单(" + transferLine.getLineNum() + ")状态不是待入库！");
                return;
            }
            remark = "已完成";
            ServiceResult<Integer> rs2 = transferLineService
                    .updateTransferLineAfterLesInput(transferLine, billTime, remark);
            if (!rs2.getSuccess())
                throw new BusinessException("更新调拨单为已完成出现错误：" + rs2.getMessage());
        } else {
            //调拨出库：更新调拨单状态为待入库
            if (!InvTransferLine.LINE_STATUS_STORE_OUT.equals(transferLine.getLineStatus())) {
                logger.warn("LES调拨入库完成后调拨单(" + transferLine.getLineNum() + ")状态不是待出库！");
                return;
            }
            remark = "待入库";
            ServiceResult<Integer> rs3 = transferLineService
                    .updateTransferLineAfterLesOut(transferLine, billTime, remark);
            if (!rs3.getSuccess())
                throw new BusinessException("调拨单出库完成，释放冻结库存发生错误：" + rs3.getMessage());
        }
    }

    /**
     * 处理3PL采购订单状态
     *
     * @param poNo
     * @param quantity
     * @param billTime
     */
    private void processPurchaseOrder(String poNo, Integer quantity, Date billTime) {
        PurchaseItem purchaseItem_ = EisBuzHelper.getPurchaseItem(purchaseOrderService, poNo);
        if (purchaseItem_ == null) {
            logger.error("关联的采购网单不存在，poItemNo：" + poNo);
            return;
        }
        //更新采购订单表与采购订单明细表
        //设置采购实际入库数量
        purchaseItem_.setInputQty(quantity);
        //设置网单入库时间
        purchaseItem_.setInputTime(billTime);

        ServiceResult<Boolean> flag_result = purchaseOrderService
                .updatePurchaseOrderAfterInput(purchaseItem_);
        if (!flag_result.getSuccess())
            throw new BusinessException(
                    "update purchaseItem status failer：" + flag_result.getSuccess());
    }

    /**
     * 正品退货
     *
     * @param stockTransQueue les_stock_trans_queue
     * @param status
     */
    private boolean returnVomStatus(LesStockTransQueue stockTransQueue, String status) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNo", stockTransQueue.getCorderSn());
        paramMap.put("status", status);
        String billTime = DateUtil.format(stockTransQueue.getBillTime(), "yyyy-MM-dd HH:mm:ss");
        if ("150".equals(status)) {//已出库
            paramMap.put("waouttime", billTime);
        } else {//180-已入库
            paramMap.put("waintime", billTime);
        }
        ServiceResult<Boolean> result = vomOrderService.returnVomStatus(paramMap);
        if (!result.getSuccess() || !result.getResult()) {
            throw new BusinessException(
                    "处理正品退货记录[" + stockTransQueue.getCorderSn() + "]失败：" + result.getMessage());
        }

        return true;
    }
    /**
     * 日日单库存入库后，更新网单状态
     *
     * @param stockTransQueue
     */
    private void processProduceDaily(LesStockTransQueue stockTransQueue) {

        String corderSn = stockTransQueue.getBstkd();
        ServiceResult<Boolean> rs = purchaseOrderService.updatePurchaseProduceDailyOrderAfterInput(
                stockTransQueue.getSku(), corderSn, stockTransQueue.getQuantity(),
                stockTransQueue.getBillTime());
        if (!rs.getSuccess() || !rs.getResult())
            throw new BusinessException(
                    "更新日日单采购单（" + stockTransQueue.getCorderSn() + "）的入WA库信息失败：" + rs.getMessage());
    }
}
