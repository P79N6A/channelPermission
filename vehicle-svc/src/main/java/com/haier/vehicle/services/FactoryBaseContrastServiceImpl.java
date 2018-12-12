package com.haier.vehicle.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.vehcile.FactoryBaseContrastDTO;
import com.haier.purchase.data.service.vechile.PurchaseFactoryBaseContrastService;
import com.haier.vehicle.service.FactoryBaseContrastService;

@Service
public class FactoryBaseContrastServiceImpl implements FactoryBaseContrastService {
	
	@Autowired
	PurchaseFactoryBaseContrastService purchaseFactoryBaseContrastService;
	
	@Override
	public List<FactoryBaseContrastDTO> findFactoryBaseContrastList(FactoryBaseContrastDTO dto) {
		return purchaseFactoryBaseContrastService.findFactoryBaseContrastList(dto);
	}
}