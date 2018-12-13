package com.haier.order.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.model.*;
//import com.haier.svc.bean.QueryOrderParameter;
//import com.haier.svc.bean.TaoBaoGroups;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.system.model.SyncOrderConfigs;

public interface OrderCenterOrderOperationService {


    /**
	* 订单查询
	* @param queryOrder
	* @return
	*/
	ServiceResult<List<QueryOrderParameter>> getFindQueryOrderList(QueryOrderParameter queryOrder);

	/**
	 * 订单查询导出
	 * @param queryOrder
	 * @return
	 */
	ServiceResult<List<QueryOrderParameter>> getExportOrderList(QueryOrderParameter queryOrder);


	/**
	 * 通过SKU 获取定金尾款
	 * @param sku
	 * @return
	 */
	ServiceResult<List<Map<String,Object>>> getTaoBaoGroupsListBySku(String sku, Integer page, Integer rows);


	/**
	 * 通过SKU和团购名称查询
	 * @param sku
	 * @param groupName
	 * @return
	 */
	ServiceResult<Map<String,Object>> getTaoBaoGroupsBySkuAndName(String sku, String groupName);

	/**
	 * 添加
	 * @param map
	 * @return
	 */
	ServiceResult<Boolean> addTaoBaoGroups(Map<String,Object> map);

	/**
	 * 修改
	 * @param map
	 * @return
	 */
	ServiceResult<Boolean> updateTaoBaoGroups(Map<String,Object> map);

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	ServiceResult<Boolean> delTaoBaoGroups(Integer[] ids);
	
	/**
	 * 通过id查询
	 * @return
	 */
	ServiceResult<Map<String,Object>> getTaoBaoGroupsById(Integer id);
	
	/**
	 * 通过id查询
	 * @return
	 */
	ServiceResult<List<Map<String, Object>>> productSpecsFormat(Integer id);

	ServiceResult<List<OrderProductsVo>> searchList(OrderProductsVo vo);

	ServiceResult<List<InvChannel2OrderSource>> getSource();
	Map<String,Object> copyProductView(String productId,String orderSn);
	
	 List<OrderPriceProductGroupIndustry> getProductGroupIndustryList();
	 List<SyncOrderConfigs> selectSyncOrderonfigs();

	List<OrderPriceSourceChannel> selectOrderPriceSourceChannel();
	 Json copyProductSave(JSONObject params,String username);

}
