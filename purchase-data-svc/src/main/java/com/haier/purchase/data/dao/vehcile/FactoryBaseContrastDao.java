package com.haier.purchase.data.dao.vehcile;

import java.util.List;

import com.haier.purchase.data.model.vehcile.FactoryBaseContrastDTO;

public interface FactoryBaseContrastDao {
	
	List<FactoryBaseContrastDTO> findFactoryBaseContrastList(FactoryBaseContrastDTO dto);
}