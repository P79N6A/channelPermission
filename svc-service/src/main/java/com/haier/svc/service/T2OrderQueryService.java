package com.haier.svc.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.CrmOrderItem;
import com.haier.purchase.data.model.T2OrderItem;
import com.haier.shop.model.ItemAttribute;
import com.haier.stock.model.InvStockChannel;

/**
 * @author 李超
 *
 */
public interface T2OrderQueryService {
	
    /**
     * 综合查询获取T+2订单信息list
     * @param Map<String, Object> params
     * @return
     */
    ServiceResult<List<T2OrderItem>> findT2OrderMultipleList(Map<String, Object> params);
	
	
	/**
	 * 获取T+2订单信息list
	 * @param params
	 * @return
	 */
	ServiceResult<List<T2OrderItem>> getT2OrderList( Map<String, Object> params);

	/**
	 * 获取T+2订单导出信息list
	 * @param params
	 * @return
	 */
	List<T2OrderItem> getT2OrderListExportData( Map<String, Object> params);

	/**
	 * 手工关单
	 * @param params
	 */
	void manualCloseOrderList(Map<String, Object> params);
	
	/**
	 * 撤销手工关单
	 * @param params
	 */
	void cancelCloseOrderList(Map<String, Object> params);
	
	/**
	 * 已冻结推送
	 * @param params
	 * @return
	 */
	int commitAgainOrderMultiple(Map<String, Object> params);
	
    /**
     * 根据CBS品类查询产品组
     * @param cbsCategory
     * @return
     */
    ServiceResult<List<ItemAttribute>> queryProductGroupByCbsCategory(String cbsCategory);
    
    /**
     * 获取渠道列表
     * 
     * @return
     */
    ServiceResult<List<InvStockChannel>> getChannels();
    
    /**
     * 通过ValueSetId检索ItemAttribute List
     * @param valueSetId
     * @return
     */
    ServiceResult<List<ItemAttribute>> getByValueSetId(String valueSetId);
    
    /**
     * 获取PO查询信息
     * @param Map<String, Object> params
     * @return
     */
    ServiceResult<List<CrmOrderItem>> getPOList(Map<String, Object> params);

	List<CrmOrderItem> getPOExportList(Map<String, Object> params);
}
