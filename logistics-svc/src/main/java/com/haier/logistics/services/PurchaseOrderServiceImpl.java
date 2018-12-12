package com.haier.logistics.services;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.logistics.Model.PurchaseOrderModel;
import com.haier.logistics.service.PurchaseOrderService;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.model.PurchaseOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;



@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
	 private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
             .getLogger(PurchaseOrderServiceImpl.class);
	 @Autowired
	 private PurchaseOrderModel purchaseOrderModel;
	/* @Autowired
	private CbsBufferModel                 cbsBufferModel;*/
	 /**
	     * 根据订单明细编号查询采购订单明细信息
	     * @param poItemNo
	     * @return
	     * @see
	     */
	    @Override
	    public ServiceResult<PurchaseItem> getPurchaseItemByPoItemNo(String poItemNo) {
	        ServiceResult<PurchaseItem> result = new ServiceResult<PurchaseItem>();
	        try {
	            Assert.notNull(purchaseOrderModel, "Property 'PurchaseOrderModel' is required.");
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
	 * @param poId
	 * @return
	 * @see
	 */
	@Override
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
