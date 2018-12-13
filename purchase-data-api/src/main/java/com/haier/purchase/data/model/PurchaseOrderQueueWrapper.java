package com.haier.purchase.data.model;

import java.io.Serializable;

/**
 * 采购订单
 */
public class PurchaseOrderQueueWrapper implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long  serialVersionUID = -3523043018685222062L;

    private PurchaseOrderQueue purchaseOrderQueue;
    private PurchaseOrder      purchaseOrder;
    private PurchaseItem       purchaseItem;

    public PurchaseOrderQueue getPurchaseOrderQueue() {
        return purchaseOrderQueue;
    }

    public void setPurchaseOrderQueue(PurchaseOrderQueue purchaseOrderQueue) {
        this.purchaseOrderQueue = purchaseOrderQueue;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public PurchaseItem getPurchaseItem() {
        return purchaseItem;
    }

    public void setPurchaseItem(PurchaseItem purchaseItem) {
        this.purchaseItem = purchaseItem;
    }

    @Override
    public String toString() {
        return "PurchaseOrderQueueWrapper [purchaseOrderQueue=" + purchaseOrderQueue
               + ", purchaseOrder=" + purchaseOrder + ", purchaseItem=" + purchaseItem + "]";
    }

}
