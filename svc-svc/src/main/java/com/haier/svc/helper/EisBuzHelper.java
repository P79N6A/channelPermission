package com.haier.svc.helper;


import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.service.PurchaseOrderService;
import com.haier.shop.model.OrderProductsNew;
import com.haier.stock.service.OrderService;

public class EisBuzHelper {


    public static PurchaseItem getPurchaseItem(PurchaseOrderService purchaseOrderService,
                                               String poItemNo) {
        ServiceResult<PurchaseItem> pi_result = purchaseOrderService
            .getPurchaseItemByPoItemNo(poItemNo);
        if (!pi_result.getSuccess())
            throw new BusinessException("向采购服务获取采购网单时发生未知错误：" + pi_result.getMessage());
        return pi_result.getResult();
    }


    public static OrderProductsNew getOrderProducts(OrderService orderService, String cOrderSn) {
        ServiceResult<OrderProductsNew> rs = orderService.getOrderProductByCOrderSn(cOrderSn);
        if (!rs.getSuccess()) {
            throw new BusinessException("向订单服务请求网单信息出现错误：" + rs.getMessage());
        }
        OrderProductsNew orderProduct = rs.getResult();
        return orderProduct;
    }

}
