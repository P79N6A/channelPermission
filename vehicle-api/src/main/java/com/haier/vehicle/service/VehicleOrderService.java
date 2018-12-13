package com.haier.vehicle.service;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.vehcile.Cn3wPurchaseStock;
import com.haier.purchase.data.model.vehcile.ExportVehicleDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderHistoryDTO;
import com.haier.purchase.data.model.vehcile.VehiclePushToSAP;

public interface VehicleOrderService {
	
	public int insertSelective(VehicleOrderDTO entity);

	
	public int updateSelectiveById(VehicleOrderDTO entity);

	
	public VehicleOrderDTO getOneById(long id);

	
	public VehicleOrderDTO getOneByCondition(VehicleOrderDTO entity);
	
	public List<VehicleOrderDTO> getListByCondition(VehicleOrderDTO entity);

	
	public List<VehicleOrderDTO> getPageByCondition(VehicleOrderDTO entity,
			int start, int rows);

	
	public long getPagerCount(VehicleOrderDTO entity);

	
	public VehicleOrderDTO getOneByDeliveryToCode(String deliveryToCode);

	
	public String getVehicleOrderNo(String begin);

	public int updateSelectiveByOrderNo(VehicleOrderDTO entity);
	
	public List<ExportVehicleDTO> selectVehicleExport(Map<String, Object> params);

	public String getWhCode (String code);

	public List<Cn3wPurchaseStock> findPushToSAPList(Map<String, Object> params);

	public Integer findPushToSAPListCount(Map<String, Object> params);
	
	public Map<String,Object> cancelOrder(VehicleOrderDTO order);


	/**
	 * 列表查询，重置菜鸟预约DN用
	 * @param condition
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<VehicleOrderHistoryDTO> getChangeDNPageByCondition(VehicleOrderDTO condition, int page, int rows);

	/**
	 * 列表查询，重置菜鸟预约DN用
	 * @param condition
	 * @return
	 */
	public Long getChangeDNPagerCount(VehicleOrderDTO condition);


	public List<VehiclePushToSAP> findPushToSAPList2(Map<String, Object> params);


	public int findPushToSAPListCount2(Map<String, Object> params);

}
