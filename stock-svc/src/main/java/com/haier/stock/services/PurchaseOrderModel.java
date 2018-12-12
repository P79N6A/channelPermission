package com.haier.stock.services;

import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.purchase.data.service.PurchaseItemService;
import com.haier.purchase.data.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.haier.common.BusinessException;
import com.haier.common.util.StringUtil;
@Service
public class PurchaseOrderModel {
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	@Autowired
	  private PurchaseItemService purchaseItemService;
	  /**
     * 根据采购订单明细编号查询记录信息
     * 
     * @param poItemNo
     * @return
     */
    public PurchaseItem getPurchaseItemByPoItemNo(String poItemNo) {
        Assert.notNull(purchaseItemService, "Property 'purchaseItemService' is required.");
        if (StringUtil.isEmpty(poItemNo, true))
            throw new BusinessException(
                "[puchase_order_model][getPurchaseItemByPoItemNo]:方法参数采购订单明细编号poItemNo不能为空");
        return purchaseItemService.getPurchaseItemByPoItemNo(poItemNo);

    }

    /**
     * 根据采购订单ID查询查询订单详情
     * 
     * @param poId:采购订单ID
     * @return
     */
    public PurchaseOrder getPurchaseOrder(int poId) {
        Assert.notNull(purchaseOrderService, "Property 'purchaseOrderService' is required.");
        return purchaseOrderService.get(poId);
    }
}
