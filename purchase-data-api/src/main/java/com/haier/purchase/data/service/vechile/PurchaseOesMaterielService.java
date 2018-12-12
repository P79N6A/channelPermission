package com.haier.purchase.data.service.vechile;

import java.util.List;

import com.haier.purchase.data.model.vehcile.OesMaterielDTO;

public interface PurchaseOesMaterielService {
	
	List<OesMaterielDTO> findOesMaterielList(OesMaterielDTO dto);
}