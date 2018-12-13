package com.haier.purchase.data.dao.purchase;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.CrmOrderItem;
import com.haier.purchase.data.model.T2OrderItem;


/**
 * @author 李超
 *
 */
public interface T2OrderQueryDao {

	/**
	 * 根据条件查询获取T+2订单信息
	 * @param params
	 * @return
	 */
	List<T2OrderItem> findT2OrderMultipleList(Map<String, Object> params);
	/**
	 * 获取入库时间为空的订单
	 * @param params
	 * @return
	 */
	CrmOrderItem getIsNullWaInTime(Map<String, Object> params);
	/**
	 * 获得条数
	 * @param params 
	 * @return
	 */
	int getRowCnts(Map<String, Object> params);

	/**
	 * 手工关单
	 * @param params
	 */
	void manualCloseOrder(Map<String, Object> params);

	/**
	 * 撤销手工关单
	 * @param params
	 */
	void cancelCloseOrder(Map<String, Object> params);
	
    /**
     * OMS已冻结推送
     * @param params
     */
    public int commitAgainOrderMultiple(Map<String, Object> params);
    
    
//    List<ItemAttribute> queryProductGroupByCbsCategory(@Param("cbsCategory") String cbsCategory);
    
    /**
     * 通过ValueSetId检索ItemAttribute List
     * @param valueSetId
     * @return
     */
//    List<ItemAttribute> getByValueSetId(@Param("valueSetId") String valueSetId);
    
    /**
     * 获取PO查询信息
     * 
     * @param Map
     *            <String, Object> params
     * @return
     */
    public List<CrmOrderItem> findPOList(Map<String, Object> params);
    
    /**
     * 获取PO信息条数
     * 
     * @param params
     * @return
     */
    public int findPOListCNT(Map<String, Object> params);

    List<T2OrderItem> findT2OrderMultipleExportList(Map<String, Object> params);

	Integer getByOrderId(String orderId);
}
