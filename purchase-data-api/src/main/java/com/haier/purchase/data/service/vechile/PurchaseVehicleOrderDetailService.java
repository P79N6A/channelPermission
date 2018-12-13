package com.haier.purchase.data.service.vechile;

import java.util.List;
import java.util.Map;

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
public interface PurchaseVehicleOrderDetailService extends
		BasService<VehicleOrderDetailsDTO> {

	List<VehicleOrderDetailsDTO> selectByStatus();

	List<VehicleOrderDetailsDTO> selectByWaitToSap();

	int updateSelectiveByItemNo(VehicleOrderDetailsDTO entity);

	int deleteByOrderId(Long orderId);

	List<VehicleOrderDetailsDTO> getListByVbeln();

	void updateVbelnDnStatus(List<String> vbelnDn);

	int updateDetailStatus(String itemNo, String orderStatus);

	int selectCount(String itemNo, String orderStatus);

	int updateStatus(String itemNo, String orderStatus);

	int updateZqStatus(String itemNo, String orderStatus);

	int updateByItemNo(Map<String, String> map);

	public void addPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock);

	public void updateCn3wPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock);

	public List<Cn3wPurchaseStock> queryCn3wPurchaseStock(
			Map<String, Object> map);

	public void addEntry3wOrder(Entry3wOrder entry3wOrder);

	public void updateEntry3wOrder(Entry3wOrder entry3wOrder);

	public List<Entry3wOrder> queryEntry3wOrder(Map<String, Object> map);
	
	public List<VehicleOrderDetailsDTO>  selectByWaitUpdateLbx();

	void updateZqDetailStatus(String zqItemNo, String status);

	int selectZqCount(String zqItemNo, String status);

	void updateByOrderNo(VehicleOrderDetailsDTO order);

	List<VehicleOrderDetailsDTO> getByVbeln(String vbeln);

	void updateEntry3wOrderById(Cn3wPurchaseStock cn3wPurchaseStock);

	int updateVbelnSpareByItemNo(String itemNo, String vbelnSpare);

	boolean vbelnExists(String itemNo, String vbelnSpare);
}
