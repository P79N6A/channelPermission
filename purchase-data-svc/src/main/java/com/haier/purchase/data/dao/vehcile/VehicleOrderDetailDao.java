package com.haier.purchase.data.dao.vehcile;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haier.purchase.data.model.vehcile.Cn3wPurchaseStock;
import com.haier.purchase.data.model.vehcile.Entry3wOrder;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDetailsDTO;

/**
 * <p>
 * Description:
 * </p>
 * ClassName:VehicleOrderDetailDao Created on 2017/9/7
 *
 * @author wsh
 * @version 1.0 Copyright (c) 2015 北京柯莱特科技有限公司 交付部
 */
public interface VehicleOrderDetailDao {

	List<VehicleOrderDetailsDTO> selectByStatus();

	List<VehicleOrderDetailsDTO> selectByWaitToSap();

	int updateSelectiveByItemNo(@Param("entity") VehicleOrderDetailsDTO entity);

	int deleteByOrderId(@Param("orderId") Long orderId);

	List<VehicleOrderDetailsDTO> getListByVbeln();

	void updateVbelnDnStatus(@Param("vbelnDn") List<String> vbelnDn);

	int updateDetailStatus(@Param("itemNo")String itemNo, @Param("orderStatus")String orderStatus);

	int selectCount(@Param("itemNo")String itemNo, @Param("orderStatus")String orderStatus);

	int updateStatus(@Param("itemNo")String itemNo, @Param("orderStatus")String orderStatus);

	int updateZqStatus(@Param("itemNo")String itemNo, @Param("orderStatus")String orderStatus);

	int updateByItemNo(Map<String, String> map);
	
	int updateVbelnSpareByItemNo(@Param("itemNo")String itemNo, @Param("vbelnSpare")String vbelnSpare);

	public void addPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock);

	public void updateCn3wPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock);

	public List<Cn3wPurchaseStock> queryCn3wPurchaseStock(
			Map<String, Object> map);

	int insertSelective(VehicleOrderDetailsDTO entity);

	int updateSelectiveById(@Param("entity")VehicleOrderDetailsDTO entity);

	VehicleOrderDetailsDTO getOneById(long id);

	VehicleOrderDetailsDTO getOneByCondition(@Param("entity")VehicleOrderDetailsDTO entity);

	List<VehicleOrderDetailsDTO> getListByCondition(
			@Param("entity")VehicleOrderDetailsDTO entity);

	List<VehicleOrderDetailsDTO> getPageByCondition(
			@Param("entity")VehicleOrderDetailsDTO entity, @Param("start") int start,
			@Param("rows") int rows);

	long getPagerCount(@Param("entity")VehicleOrderDetailsDTO entity);

	VehicleOrderDetailsDTO getOneByDeliveryToCode(
			@Param("deliveryToCode") String deliveryToCode);
	
	public void addEntry3wOrder(Entry3wOrder entry3wOrder);

	public void updateEntry3wOrder(Entry3wOrder entry3wOrder);

	public List<Entry3wOrder> queryEntry3wOrder(
			Map<String, Object> map);
	
	public List<VehicleOrderDetailsDTO> selectByWaitUpdateLbx();

	void updateZqDetailStatus(@Param("zqItemNo")String zqItemNo, @Param("orderStatus")String status);

	int selectZqCount(@Param("zqItemNo")String zqItemNo, @Param("orderStatus")String status);

	void updateByOrderNo(VehicleOrderDetailsDTO order);

	List<VehicleOrderDetailsDTO> getByVbeln(@Param("vbeln") String vbeln);

	void updateEntry3wOrderById(Cn3wPurchaseStock cn3wPurchaseStock);

	int vbelnExists(@Param("itemNo") String itemNo, @Param("vbelnSpare") String vbelnSpare);


}
