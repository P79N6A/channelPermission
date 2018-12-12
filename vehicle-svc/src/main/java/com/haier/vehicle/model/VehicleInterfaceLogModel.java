package com.haier.vehicle.model;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.vehcile.InterfaceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.service.vechile.PurchaseVehicleInterfaceLogService;

/**
 * 日志
 *
 * @author
 * @create 2017-09-13 9:23
 **/
@Service
public class VehicleInterfaceLogModel {

	@Autowired
	private PurchaseVehicleInterfaceLogService vehicleInterfaceLogDao;

	@Transient
	public int inserrtLog(String interfaceName, String interfaceMessage) {

		int res = 0;
		try {
			// 创建订单数据
			res = vehicleInterfaceLogDao.insertLog(interfaceName,
					interfaceMessage);
		} catch (Exception ex) {
			// 记录日志
			ex.printStackTrace();
		}

		return res;
	}

	public List<InterfaceLog> findInterfaceLog(Map<String,Object> map){
		List<InterfaceLog> result = new ArrayList<InterfaceLog>();
		try {
			result = vehicleInterfaceLogDao.findInterfaceLog(map);
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}

	public int getInterfaceLogRow(Map<String, Object> map){
		int result = 0;
		try {
			result = vehicleInterfaceLogDao.getInterfaceLogRow(map);
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
}
