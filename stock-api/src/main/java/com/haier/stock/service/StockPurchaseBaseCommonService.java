package com.haier.stock.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.InvBudgetOrg;
import com.haier.purchase.data.model.WAAddress;
import com.haier.stock.model.InvRrsWarehouse;
import com.haier.stock.model.InvWarehouse;


public interface StockPurchaseBaseCommonService {

	/**
	 * 
	 * @Title: getWhByEstorgeId
	 * @Description:通过estorge_id索引
	 */
	public ServiceResult<InvWarehouse> getWhByEstorgeId(String estorge_id);
	
	/**
	 * 
	 * @Title: getRrsWhByEstorgeId
	 * @Description:默认通过estorge_id字段查询，可选查询字段有t2_default， 
	 *                                                    rrs_wh_code，查询表：inv_rrs_warehouse
	 */
	public ServiceResult<List<InvRrsWarehouse>> getRrsWhByEstorgeId(
			Map<String, Object> params);

	/**
	 * 查询所有预算体
	 * 
	 * @Description:查询表inv_budgetorg
	 */
	public ServiceResult<InvBudgetOrg> getAllBudgetOrg(Map params);

	/**
	 * 获取所有数据
	 * 
	 * @Description:通过estorge_id索引
	 */

	public ServiceResult<InvWarehouse> getAllWhByEstorgeId(String estorge_id);

	/**
	 * @Title: getRrsWhByEstorgeId
	 * @Description:默认通过estorge_id字段查询，可选查询字段有t2_default， 
	 *                                                    rrs_wh_code，查询表：inv_rrs_warehouse
	 */
	public ServiceResult<List<InvRrsWarehouse>> getAllRrsWhByEstorgeId(
			Map<String, Object> params);

	/**
	 * 查询所有数据
	 * 
	 * @Description:通过waCode查询表inv_warehouse
	 */
	public ServiceResult<List<WAAddress>> getAllWAAddressInfo(String waCode);


}
