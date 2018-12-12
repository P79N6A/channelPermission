package com.haier.purchase.data.dao.vehcile;

import java.util.List;

import com.haier.purchase.data.model.vehcile.OesMaterielDTO;

public interface OesMaterielDao {
	
	List<OesMaterielDTO> findOesMaterielList(OesMaterielDTO dto);
}