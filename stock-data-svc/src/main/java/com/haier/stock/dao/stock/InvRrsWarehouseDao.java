package com.haier.stock.dao.stock;

import java.util.List;
import java.util.Map;

import com.haier.stock.model.InvRrsWarehouse;


/**
 * 
 * 
 * @Description:
 * @author 郝文佳 wenjia.hao@dhc.com.cn
 * @date 2015年9月14日 上午11:49:08
 * 
 */

public interface InvRrsWarehouseDao {

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

	List<InvRrsWarehouse> getPurRrsWhByEstorgeId(Map<String, Object> params);

	Integer countTotal(Map<String, Object> params);

	Integer checkMainKey(Map<String, Object> params);

	void insertInvRrsWarehouse(Map<String, Object> params);

	void updateInvRrsWarehouse(Map<String, Object> params);

	void deleteInvRrsWarehouse(Map<String, Object> params);

	Integer countT2Status(Map<String, Object> params);

	List<InvRrsWarehouse>  selectRrsWhByEstorgeExport(Map<String, Object> params);
}
