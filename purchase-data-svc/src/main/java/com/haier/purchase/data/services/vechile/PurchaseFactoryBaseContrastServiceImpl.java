package com.haier.purchase.data.services.vechile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.vehcile.FactoryBaseContrastDao;
import com.haier.purchase.data.model.vehcile.FactoryBaseContrastDTO;
import com.haier.purchase.data.service.vechile.PurchaseFactoryBaseContrastService;

@Service
public class PurchaseFactoryBaseContrastServiceImpl implements PurchaseFactoryBaseContrastService {
	
	@Autowired
	FactoryBaseContrastDao factoryBaseContrastDao;
	
	@Override
	public List<FactoryBaseContrastDTO> findFactoryBaseContrastList(FactoryBaseContrastDTO dto) {
		return factoryBaseContrastDao.findFactoryBaseContrastList(dto);
	}
}