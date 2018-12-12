package com.haier.stock.services.Helper;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.shop.model.OrderProductsNew;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.service.PurchaseOrderService;
import com.haier.stock.service.StockCommonService;
import com.haier.stock.service.StockTransferLineService;
import com.haier.stock.service.TransferLineService;
import com.haier.stock.services.OrderServiceImpl;
import com.haier.stock.services.PurchaseOrderServiceImpl;
//import com.haier.svc.bean.system.PurchaseItem;
//import com.haier.svc.bean.system.PurchaseOrder;
/*
* 作者:张波
* 2017/12/22
* */
public class EisBuzHelper {
    public static OrderProductsNew getOrderProducts(OrderServiceImpl orderService, String cOrderSn) {
        ServiceResult<OrderProductsNew> rs = orderService.getOrderProductByCOrderSn(cOrderSn);
        if (!rs.getSuccess()) {
            throw new BusinessException("向订单服务请求网单信息出现错误：" + rs.getMessage());
        }
        OrderProductsNew orderProduct = rs.getResult();
        return orderProduct;
    }
    
    public static InvTransferLine getTransferLine(TransferLineService transferLineService,
    		String lineNum) {
    	ServiceResult<InvTransferLine> rs = transferLineService
    			.getInvTransferLineByLineNum(lineNum);
    	if (!rs.getSuccess())
    		throw new BusinessException("通过调拨单号获取调拨单失败：" + rs.getMessage());
    	return rs.getResult();
    }
    

    public static InvSection getInvSection(StockCommonService stockService, String secCode) {
        ServiceResult<InvSection> sectionResult = stockService.getSectionByCode(secCode);
        return sectionResult.getResult();
    }
    
    public static PurchaseItem getPurchaseItem(PurchaseOrderServiceImpl purchaseOrderServiceImpl,
                                               String poItemNo) {
    	ServiceResult<PurchaseItem> pi_result = purchaseOrderServiceImpl
    			.getPurchaseItemByPoItemNo(poItemNo);
    	if (!pi_result.getSuccess())
    		throw new BusinessException("向采购服务获取采购网单时发生未知错误：" + pi_result.getMessage());
    	return pi_result.getResult();
    }
    
    public static PurchaseOrder getPurchaseOrder(PurchaseOrderServiceImpl purchaseOrderServiceImpl,
    		Integer poId) {
    	ServiceResult<PurchaseOrder> rs = purchaseOrderServiceImpl.getPurchaseOrder(poId);
    	if (!rs.getSuccess())
    		throw new BusinessException("向采购服务获取采购订单时发生错误：" + rs.getMessage());
    	return rs.getResult();
    }

}
