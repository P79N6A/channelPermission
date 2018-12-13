package com.haier.stock.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.stock.model.InvRrsWarehouse;

public interface StockInvRrsWarehouseService {
	/**
	 * 
	 * @Title: getRrsWhByEstorgeOriginal
	 * @Description:对外暴露服务的dao层接口，默认通过estorge_id字段查询，可选查询字段有t2_default， 
	 *                                                                  rrs_wh_code
	 */
	public List<InvRrsWarehouse> getRrsWhByEstorgeOriginal(
			Map<String, Object> params);

	/**
	 * 对外暴露服务 查询所有数据
	 * 
	 * @param params
	 * @return
	 */

	public List<InvRrsWarehouse> getAllRrsWhByEstorgeOriginal(
			Map<String, Object> params);
	
	
	 List<InvRrsWarehouse> getRrsWhByEstorgeId(Map<String, Object> params);

	public List<InvRrsWarehouse> getPurRrsWhByEstorgeId(Map<String, Object> param);

	public Integer countTotalService(Map<String, Object> param);

	public Integer checkMainKey(Map<String, Object> param);

	public void insertInvRrsWarehouseService(Map<String, Object> param);

	public void updateInvRrsWarehouseService(Map<String, Object> param);

	public Integer countT2StatusService(Map<String, Object> param);

	public void deleteInvRrsWarehouseService(Map<String, Object> param);

	public List<InvRrsWarehouse> selectInvRrsWarehouseExportService(Map<String, Object> param);

}
