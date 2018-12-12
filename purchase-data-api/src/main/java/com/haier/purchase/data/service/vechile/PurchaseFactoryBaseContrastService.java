package com.haier.purchase.data.service.vechile;

import java.util.List;

import com.haier.purchase.data.model.vehcile.FactoryBaseContrastDTO;

public interface PurchaseFactoryBaseContrastService {
	
	List<FactoryBaseContrastDTO> findFactoryBaseContrastList(FactoryBaseContrastDTO dto);
}