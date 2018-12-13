package com.haier.svc.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.CrmOrderManualItem;
import com.haier.purchase.data.model.T2OrderInterfaceLog;
import com.haier.purchase.data.model.T2OrderItem;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.QueryOrderParameter;
import com.haier.stock.model.InvStockChannel;


public interface T2OrderService {

    /**
     * 获取T+2订单信息list
     * @param Map<String, Object> params
     * @return
     */
    ServiceResult<List<T2OrderItem>> getT2OrderList(Map<String, Object> params);

    /**
     * 返回总条数
     * @return
     */
    ServiceResult<Integer> getRowCnts();

    
    
    Map<String, String> getValueMeaningMap(String valueSetId);
    
    /**
     * 根据品类渠道取得在途
     * @param catagory  品类
     * @param ed_channel_id 渠道
     * @return
     */
    ServiceResult<BigDecimal> getOnwayNumByCateChan(String catagory, String ed_channel_id);

	/**
     * 根据品类渠道取得本周已用
     * @param report_year_week 本周
     * @param catagory 品类
     * @param ed_channel_id 渠道
     * @return
     */
    ServiceResult<BigDecimal> getUsedNumByCateChan(String report_year_week, String catagory,
                                                   String ed_channel_id);

	ServiceResult<Map<String, Integer>> insertT2Order(
			List<T2OrderItem> t2OrderItems);
	
	/**
	 * 根据物料ID取得品牌code和型号 & 根据物料号取得产品组code 李超
	 * @param material_id 物料ID 物料号
	 * @return
	 */
	ServiceResult<List<ItemBase>> findItemBaseByMaterialId(String material_id);
	
	public Map<String, String> getProductMap(Map<String, String> productgroupmap);
	
	public  Map<String, String> getChannelMapByCode(Map<String, String> invstockchannelmap);
	public  Map<String, String> getChannelMapByName(Map<String, String> invstockchannelmap);

	ServiceResult<List<ItemAttribute>> queryProductGroupByCbsCategory(
			String cbsCategory);

	ServiceResult<List<ItemAttribute>> getByValueSetItemId(String brand);
	
	public ServiceResult<List<String>> getSubSkuListByMainSku(String sku);

	ServiceResult<ItemBase> getItemBaseBySku(String subSku);

	/**
     * 闸口CheckError情报更新
     * @param t2Order
     * @return
     */
    ServiceResult<Boolean> updateCheckError(T2OrderItem t2Order);

    /**
     * 价格情报更新
     * @param t2Order
     * @return
     */
    ServiceResult<Boolean> updatePrice(T2OrderItem t2Order);

	/**
     * 提交订单
     * @param params
     * @return
     */
    ServiceResult<Boolean> commitT2OrderList(Map<String, Object> params);

    /**
	* 订单查询
	* @param params
	* @return
	*/
	ServiceResult<List<QueryOrderParameter>> getFindQueryOrderList(QueryOrderParameter queryOrder);
	   /**
     * 删除订单
     * @param params
     */
    void deleteT2OrderList(Map<String, Object> params);

	ServiceResult<Boolean> reviewT2OrderDepart(Map<String, Object> reviewParams);

	ServiceResult<Boolean> updateCount(Map<String, Object> params);

	/**
     * 撤销订单
     * @param params
     * @return
     */
    ServiceResult<Boolean> revokeT2OrderList(Map<String, Object> params);

	/**
     * 审核订单t+2
     * @param params
     * @return
     */
    ServiceResult<Boolean> reviewT2OrderList(Map<String, Object> params);

	/**
     * 同步T+2订单信息到OMS
     * @param l
     * @return
     */
	public ServiceResult<Boolean> syncT2OrderToOms(final List<T2OrderItem> l) ;

	/**
     * 款先直发订单，机壳不结算订单手工采购
     * @param params
     * @return
     */

    public ServiceResult<List<String>> insertWAOrderBillToCRM(Map<String, Object> params);

    /**
     * 审核订单款先
     * @param params
     * @return
     */
    ServiceResult<Boolean> reviewKXOrderList(Map<String, Object> params);

	ServiceResult<List<T2OrderInterfaceLog>> findPurchaseLog(Map<String, Object> params);

    List<T2OrderItem> getT2WdOrderId(Map<String, Object> params);

	List<InvStockChannel> getzChannelMap();
}
