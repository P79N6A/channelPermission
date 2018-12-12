package com.haier.svc.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.shop.model.QueryOrderParameter;
//import com.haier.svc.bean.QueryOrderParameter;
//import com.haier.svc.bean.TaoBaoGroups;

public interface OrderOperationService {

	
    /**
	* 订单查询
	* @param params
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
	 * @param id
	 * @return
	 */
	ServiceResult<Boolean> delTaoBaoGroups(Integer[] ids);
}
