package com.haier.purchase.data.services.vechile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.vehcile.OesMaterielDao;
import com.haier.purchase.data.model.vehcile.OesMaterielDTO;
import com.haier.purchase.data.service.vechile.PurchaseOesMaterielService;

@Service
public class PurchaseOesMaterielServiceImpl implements PurchaseOesMaterielService{
	
	@Autowired
	OesMaterielDao oesMaterielDao;
	
	public List<OesMaterielDTO> findOesMaterielList(OesMaterielDTO dto){
		return oesMaterielDao.findOesMaterielList(dto);
	}
}