package com.haier.order.services;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.order.model.PurchaseOrderModel;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.purchase.data.model.PurchaseOrderInfoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class OrderCenterPurchaseOrderServiceImpl {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(OrderCenterPurchaseOrderServiceImpl.class);
    @Autowired
    private PurchaseOrderModel purchaseOrderModel;
//	 @Autowired
//	private CbsBufferModel                 cbsBufferModel;

    public ServiceResult<List<PurchaseOrderInfoItem>> getOrderInfoByDnSi(List<String> dn_si_list) {
        ServiceResult<List<PurchaseOrderInfoItem>> result = new ServiceResult<List<PurchaseOrderInfoItem>>();
        try {
//	            result.setResult(cbsBufferModel.getOrderInfoByDnSi(dn_si_list));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("获取明细条数失败：", e);
        }
        return result;
    }

    /**
     * 根据订单明细编号查询采购订单明细信息
     *
     * @param poItemNo
     * @return
     */

    public ServiceResult<PurchaseItem> getPurchaseItemByPoItemNo(String poItemNo) {
        ServiceResult<PurchaseItem> result = new ServiceResult<PurchaseItem>();
        try {
            Assert.notNull(purchaseOrderModel, "Property 'purchaseOrderModel' is required.");
            result.setResult(purchaseOrderModel.getPurchaseItemByPoItemNo(poItemNo));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("未知错误");
            log.error(
                    "[purchase_order_service][getPurchaseItemByPoItemNo]:根据订单明细编号poItemNo查询采购订单明细信息出错",
                    e);
        }
        return result;
    }

    /**
     * 根据采购订单ID查询采购订单详情
     *
     * @param poId
     * @return
     */

    public ServiceResult<PurchaseOrder> getPurchaseOrder(int poId) {
        ServiceResult<PurchaseOrder> result = new ServiceResult<PurchaseOrder>();
        try {
            Assert.notNull(purchaseOrderModel, "Property 'systemModel' is required.");
            result.setResult(purchaseOrderModel.getPurchaseOrder(poId));
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("未知错误");
            log.error("[purchase_order_service][getPurchaseOrder]加载采购订单信息出错", e);
        }
        return result;
    }
}
