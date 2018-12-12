package com.haier.vehicle.model;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.vehcile.Cn3wPurchaseStock;
import com.haier.purchase.data.model.vehcile.Entry3wOrder;
import com.haier.purchase.data.model.vehcile.VehicleOrderZqDetailsDTO;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderDetailService;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderZqDetailsService;

/**
 * 更新订单从表信息
 *
 * @author
 * @create 2017-09-12 9:51
 **/
@Service
public class VehicleOrderDetailModel {
	@Autowired
	private PurchaseVehicleOrderDetailService vehicleOrderDetailDao;
	@Autowired
	private PurchaseVehicleOrderZqDetailsService  vehicleOrderZqDetailsService ;
	
	public int updateDetailStatus(String itemNo, String orderStatus) {
		int res = 0;
		try {
			// 创建订单数据
			res = vehicleOrderDetailDao.updateDetailStatus(itemNo, orderStatus);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return res;
	}

	public int selectCount(String itemNo, String orderStatus) {
		return vehicleOrderDetailDao.selectCount(itemNo, orderStatus);
	}

	public int updateStatus(String itemNo, String orderStatus) {
		int res = 0;
		try {
			// 创建订单数据
			res = vehicleOrderDetailDao.updateStatus(itemNo, orderStatus);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return res;
	}

	public int updateZqStatus(String itemNo, String orderStatus) {
		int res = 0;
		try {
			// 创建订单数据
			res = vehicleOrderDetailDao.updateZqStatus(itemNo, orderStatus);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return res;
	}

	public void updateByItemNo(Map<String, String> map) {

		int res = vehicleOrderDetailDao.updateByItemNo(map);
	}

	public void addPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock) {
		vehicleOrderDetailDao.addPurchaseStock(cn3wPurchaseStock);
	}

	public void updateCn3wPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock) {
		vehicleOrderDetailDao.updateCn3wPurchaseStock(cn3wPurchaseStock);
	}

	public List<Cn3wPurchaseStock> queryCn3wPurchaseStock(
			Map<String, Object> map) {
		return vehicleOrderDetailDao.queryCn3wPurchaseStock(map);
	}

	public void addEntry3wOrder(Entry3wOrder entry3wOrder) {
		vehicleOrderDetailDao.addEntry3wOrder(entry3wOrder);
	}

	public void updateEntry3wOrder(Entry3wOrder entry3wOrder) {
		vehicleOrderDetailDao.updateEntry3wOrder(entry3wOrder);		
	}

	public List<Entry3wOrder> queryEntry3wOrder(Map<String, Object> map) {
		return vehicleOrderDetailDao.queryEntry3wOrder(map);
	}

	public void updateZqDetailStatus(String zqItemNo, String status) {
		vehicleOrderDetailDao.updateZqDetailStatus(zqItemNo, status);		
	}

	public int selectZqCount(String zqItemNo, String status) {
		return vehicleOrderDetailDao.selectZqCount(zqItemNo, status);
	}

	public void updateByZqItemNo(VehicleOrderZqDetailsDTO entity) {
		vehicleOrderZqDetailsService.updateSelectiveByZqItemNo(entity);
	}
}
