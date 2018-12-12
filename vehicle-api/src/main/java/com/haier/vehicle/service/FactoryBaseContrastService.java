package com.haier.vehicle.service;

import java.util.List;

import com.haier.purchase.data.model.vehcile.FactoryBaseContrastDTO;

public interface FactoryBaseContrastService {
	
	List<FactoryBaseContrastDTO> findFactoryBaseContrastList(FactoryBaseContrastDTO dto);
}