package com.haier.vehicle.service;

import java.util.List;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.vehcile.TmallCAMachineDTO;
import com.haier.vehicle.model.TmallCAMachine;

public interface TMallCAMachineService {

	ServiceResult<String> getTmallCaMachine();
	
	List<TmallCAMachine> getTmallCaMachine(String materialCode);

	/**
	 * 根据物料编码获取CA套机信息
	 * @param vbelnDn1
	 * @return
	 */
	List<TmallCAMachineDTO> getByMaterialCode(String vbelnDn1);

}
