package com.haier.purchase.data.dao.purchase;

import java.util.Map;

import com.haier.purchase.data.model.GoodsBackInfoResponse;


public interface VomOrderDao {
	/**
	 * 根据id修改信息
	 * @param paramMap
	 * @return
	 */
	public Integer updateLeaderReturnStatus(Map<String, Object> paramMap);
	//查询
	public GoodsBackInfoResponse findGoodsBackInfo(Map<String, Object> paramMap);
	//更改
	public Integer updateCrmReturnInfo(Map<String, Object> paramMap);
	//查询
	public GoodsBackInfoResponse findCrmGoodsBackInfo(Map<String, Object> paramMap);
	//根据si_ou_slipNo 修改数据信息
	public void updateCgoGenuineRejectStatusVom(Map<String, Object> paramMap);
	//根据wp_order_id修改数据
	public void updateCrmGenuineRejectStatus(Map<String, Object> paramMap);
	//根据order_id 查询数据
	public String getFlowFlagBySiOuInfo(Map<String, Object> paramMap);
	//wp_order_id 查询数据
	public String getFlowFlagByCrmReturnInfo(Map<String, Object> paramMap);
}
