package com.haier.afterSale.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.haier.common.ServiceResult;
import com.haier.shop.model.InvoiceConst;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderRepairLogsNew;
import com.haier.shop.model.OrderRepairsConst;
import com.haier.shop.model.OrderRepairsNew;
import com.haier.shop.model.ProductsNew;
import com.haier.shop.service.OrderProductsNewService;
import com.haier.shop.service.OrderRepairLogsNewService;
import com.haier.shop.service.OrderRepairsNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.haier.afterSale.services.ItemServiceImplNew;
import com.haier.common.BusinessException;

import com.haier.common.util.StringUtil;

@Service
public class OrderRepairCheckModel {
    private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager
                                                      .getLogger(OrderRepairCheckModel.class);
    @Autowired
    private OrderRepairsNewService orderRepairsDao;
    @Autowired
    private OrderProductsNewService orderProductsDao;
    @Autowired
    private OrderRepairLogsNewService orderRepairLogsDao;
    @Autowired
    private ItemServiceImplNew                    itemService;

    private DataSourceTransactionManager   transactionManager;




    private static Map<Integer, Set<Integer>> statusMap = new HashMap<Integer, Set<Integer>>();
    static {
        /*
        $statusFromTo[ 1--审核中] = array( 2--进行中,  5--已驳回);
        $statusFromTo[ 2--进行中] = array( 3--受理完成,  6--已终止,  8--等待确认终止,  7--线下已退款);
        $statusFromTo[ 3--受理完成] = array( 4--已完结);
        $statusFromTo[ 4--已完结] = null;
        $statusFromTo[ 5--已驳回] = null;
        $statusFromTo[ 6--已终止] = null;
        $statusFromTo[ 8--等待确认终止] = array( 6--已终止);
        $statusFromTo[ 7--线下已退款] = null;
        */
        Set<Integer> set = new HashSet<Integer>();
        set.add(OrderRepairsConst.HS_HANDLE);
        set.add(OrderRepairsConst.HS_CANCEL);
        statusMap.put(OrderRepairsConst.HS_REVIEW, set);
        set = new HashSet<Integer>();
        set.add(OrderRepairsConst.HS_CONFIRM);
        set.add(OrderRepairsConst.HS_STOP);
        set.add(OrderRepairsConst.HS_WAITSTOP);
        set.add(OrderRepairsConst.HS_REFUNDEDOFFLINE);
        statusMap.put(OrderRepairsConst.HS_HANDLE, set);
        set = new HashSet<Integer>();
        set.add(OrderRepairsConst.HS_CLOSE);
        statusMap.put(OrderRepairsConst.HS_CONFIRM, set);
        set = new HashSet<Integer>();
        set.add(OrderRepairsConst.HS_STOP);
        statusMap.put(OrderRepairsConst.HS_WAITSTOP, set);
        statusMap.put(OrderRepairsConst.HS_CLOSE, null);
        statusMap.put(OrderRepairsConst.HS_CANCEL, null);
        statusMap.put(OrderRepairsConst.HS_STOP, null);
        statusMap.put(OrderRepairsConst.HS_REFUNDEDOFFLINE, null);
    }

    public void checkPass(Integer id, String operator, String remark) throws Exception {
        //获取退换货信息
        OrderRepairsNew orderRepair = orderRepairsDao.get(id);
        if (orderRepair == null) {
            logger.error("[OrderRepairCheckModel]退换货信息不存在,orderRepairId=" + id);
            throw new BusinessException("退换货信息不存在,请核对!");
        }
        //获取网单信息
        OrderProductsNew orderProduct = orderProductsDao.get(orderRepair.getOrderProductId());
        if (orderProduct == null) {
            logger.error("[OrderRepairCheckModel]网单信息不存在,orderRepairId=" + id);
            throw new BusinessException("网单信息不存在,请核对!");
        }
        //开票状态
        int receiptStatus = orderProduct.getIsMakeReceipt() == InvoiceConst.MR_STATE_MAKED ? OrderRepairsConst.RS_MAKEED
            : OrderRepairsConst.RS_UNMAKE;
        //出库状态
        int storageStatus = !StringUtil.isEmpty(orderProduct.getOutping()) ? OrderRepairsConst.SS_STOCKOUT
            : OrderRepairsConst.SS_NOTSTOCKOUT;
        //未出库，未取消，不是延保卡
        if (storageStatus == OrderRepairsConst.SS_NOTSTOCKOUT
            && orderProduct.getStatus() != OrderProductsNew.STATUS_CANCEL_CLOSE
            && orderRepair.getTypeFlag() != OrderRepairsConst.TYPE_YBK) {
            throw new BusinessException("当前网单处于未出库状态，请先取消网单！");
        }
        //如果网单已取消后，出库状态才回传，则逆向出库状态仍为未出库
        if (storageStatus == OrderRepairsConst.SS_STOCKOUT
            && orderProduct.getStatus() == OrderProductsNew.STATUS_CANCEL_CLOSE) {
            storageStatus = OrderRepairsConst.SS_NOTSTOCKOUT;
        }
        // 如果没有退货单号，则不允许审核通过
        if (StringUtil.isEmpty(orderRepair.getRepairSn())) {
            throw new BusinessException("本申请单没有维护退换货单号，请驳回该申请，重新录入！");
        }
        //获取商品信息
        ServiceResult<ProductsNew> productResult = itemService.getProductById(orderProduct.getProductId());
        if (!productResult.getSuccess() && productResult.getResult() == null) {
            throw new BusinessException("商品信息不存在,请核对!");
        }
        ProductsNew product = productResult.getResult();
        if (product.getInspectType() != OrderRepairsConst.IT_DONOTINSPECT
            && product.getInspectType() != OrderRepairsConst.IT_NORMAL
            && product.getInspectType() != OrderRepairsConst.IT_APPOINT) {
            // 等于 0 代表商品没有维护退货质检方式，这种情况下申请不能通过
            throw new BusinessException("本商品没有维护退货质检方式，不能申请通过，请联系产品管理人员维护数据后再次提交！");
        }
        // 审核通过时，款状态自动变更为 退款中
        int paymentStatus = OrderRepairsConst.PS_REFUNDING;
        // 如果当前退换货类型是换货，则将款状态变更为 无需退款
        if (orderRepair.getTypeActual() == OrderRepairsConst.TYPE_CHANGE) {
            paymentStatus = OrderRepairsConst.PS_DONOTREFUND;
        }
        // 审核通过时，如已开票且已出库，则票状态自动变更为 待召回
        if (receiptStatus == OrderRepairsConst.RS_MAKEED
            && storageStatus == OrderRepairsConst.SS_STOCKOUT) {
            receiptStatus = OrderRepairsConst.RS_WAIT_RECALL;
        }
        // 审核通过时，如已开票且未出库，则票状态自动变更为 已召回/待冲票
        if (receiptStatus == OrderRepairsConst.RS_MAKEED
            && storageStatus == OrderRepairsConst.SS_NOTSTOCKOUT) {
            receiptStatus = OrderRepairsConst.RS_WAIT_CANCEL;
        }
        // 审核通过时，如已出库，则货状态自动变更为 待召回
        // update：由于需要手动向HP系统提交退货工单，因此屏蔽该处理 2013-02-18
        // update：由于业务方提出希望可以切回老流程，因此此处增加一个闸口 2013-03-20
        // $NEW_FLOW_SWITCH = true; // 新流程闸口，true=新；false=旧
        //        if (\Applications\Oss\Shop\Admin\OrderRepair::$NEW_FLOW_SWITCH == false) {
        //            if (storageStatus == OrderRepairsConst.SS_STOCKOUT)
        //                storageStatus = OrderRepairsConst.SS_WAIT_RECALL;
        //        }
        // 审核通过时，如已出库，且物流模式为B2C则货状态自动变更为 待召回
        if ("B2C".equals(product.getShippingMode())
            && storageStatus == OrderRepairsConst.SS_STOCKOUT) {
            storageStatus = OrderRepairsConst.SS_WAIT_RECALL;
        }

        orderRepair.setReceiptStatus(receiptStatus);
        orderRepair.setPaymentStatus(paymentStatus);
        orderRepair.setStorageStatus(storageStatus);

        //        orderRepairsDao.updateHPReturnResult(orderRepair);

        //事务处理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            hsModify(orderRepair, OrderRepairsConst.HS_HANDLE, operator, "hs_begin", remark);
            //提交事务
            transactionManager.commit(status);
        } catch (Exception ex) {
            //回滚事务
            transactionManager.rollback(status);
            logger.error("审核通过,更改状态和记录操作日志时，出现未知异常，回滚事务:", ex);
            throw ex;
        }

    }

    public void checkNotPass(Integer id, String operator, String remark) throws Exception {
        //获取退换货信息
        OrderRepairsNew orderRepair = orderRepairsDao.get(id);
        if (orderRepair == null) {
            logger.error("[OrderRepairCheckModel]退换货信息不存在,orderRepairId=" + id);
            throw new BusinessException("退换货信息不存在,请核对!");
        }
        //事务处理
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            hsModify(orderRepair, OrderRepairsConst.HS_CANCEL, operator, "hs_cancel", remark);
            //提交事务
            transactionManager.commit(status);
        } catch (Exception ex) {
            //回滚事务
            transactionManager.rollback(status);
            logger.error("审核不通过,更改状态和记录操作日志时，出现未知异常，回滚事务:", ex);
            throw ex;
        }
    }

    public void hsModify(OrderRepairsNew orderRepair, Integer modifyTo, String operator,
                         String operate, String remark) {
        /*
        $statusFromTo[ 1--审核中] = array( 2--进行中,  5--已驳回);
        $statusFromTo[ 2--进行中] = array( 3--受理完成,  6--已终止,  8--等待确认终止,  7--线下已退款);
        $statusFromTo[ 3--受理完成] = array( 4--已完结);
        $statusFromTo[ 4--已完结] = null;
        $statusFromTo[ 5--已驳回] = null;
        $statusFromTo[ 6--已终止] = null;
        $statusFromTo[ 8--等待确认终止] = array( 6--已终止);
        $statusFromTo[ 7--线下已退款] = null;
        */
        //修改前处理状态
        int modifyFrom = orderRepair.getHandleStatus();
        //判断当前状态是否可以有后续状态，如果有，后续状态是否在规定范围内
        if (statusMap.get(modifyFrom) == null || !statusMap.get(modifyFrom).contains(modifyTo)) {
            throw new BusinessException("受理状态有误!");
        }

        orderRepair.setHandleStatus(modifyTo);
        if (orderRepair.getHandleStatus() == OrderRepairsConst.HS_HANDLE
            || orderRepair.getHandleStatus() == OrderRepairsConst.HS_CANCEL) {
            orderRepair.setHandleTime(System.currentTimeMillis() / 1000);
        }
        if (orderRepair.getHandleStatus() == OrderRepairsConst.HS_CONFIRM
            || orderRepair.getHandleStatus() == OrderRepairsConst.HS_STOP) {
            orderRepair.setFinishTime(System.currentTimeMillis() / 1000);
        }
        orderRepairsDao.update(orderRepair);
        StringBuffer sb = new StringBuffer();
        sb.append("修改申请状态从").append(OrderRepairsConst.HANDLESTATUSMAP.get(modifyFrom)).append("到")
            .append(OrderRepairsConst.HANDLESTATUSMAP.get(modifyTo)).append("，备注：").append(remark);
        createOrderRepairLog(orderRepair.getId(), operator, operate, sb.toString());
    }

    private void createOrderRepairLog(Integer orderRepairId, String operator, String operate,
                                      String remark) {
        OrderRepairLogsNew orl = new OrderRepairLogsNew();
        orl.setSiteId(1);
        orl.setAddTime(((Long) (System.currentTimeMillis() / 1000)).intValue());
        orl.setOrderRepairId(orderRepairId);
        orl.setOperator(StringUtil.isEmpty(operator) ? "系统" : operator);
        orl.setOperate(operate);
        orl.setRemark(remark);

        orderRepairLogsDao.insert(orl);
    }

}
