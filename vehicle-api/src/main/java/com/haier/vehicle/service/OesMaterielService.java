package com.haier.vehicle.service;

import java.util.List;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.vehcile.OesMaterielDTO;

public interface OesMaterielService {
	
	ServiceResult<List<OesMaterielDTO>> findOesMaterielList(OesMaterielDTO dto);
}