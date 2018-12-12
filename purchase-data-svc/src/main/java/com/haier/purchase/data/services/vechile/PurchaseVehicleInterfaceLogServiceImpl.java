package com.haier.purchase.data.services.vechile;

import com.haier.purchase.data.model.vehcile.InterfaceLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.vehcile.VehicleInterfaceLogDao;
import com.haier.purchase.data.service.vechile.PurchaseVehicleInterfaceLogService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2017年09月13日}
 *
 * @author <a href="mailto: Zhangzengbao32@camelotchina.com">zzb</a>
 * @version 1.0 Copyright (c) 2016 北京柯莱特科技有限公司 交付部
 */
@Service
public class PurchaseVehicleInterfaceLogServiceImpl implements PurchaseVehicleInterfaceLogService {

	@Autowired
	VehicleInterfaceLogDao vehicleInterfaceLogDao;

	@Override
	public int insertLog(String interfaceName, String interfaceMessage) {
		return vehicleInterfaceLogDao
				.insertLog(interfaceName, interfaceMessage);
	}

	@Override
	public List<InterfaceLog> findInterfaceLog(Map<String, Object> map) {
		return vehicleInterfaceLogDao.findInterfaceLog(map);
	}

	@Override
	public int getInterfaceLogRow(Map<String, Object> map) {
		return vehicleInterfaceLogDao.getInterfaceLogRow(map);
	}

}
