package com.haier.purchase.data.services.vechile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.vehcile.AreaCenterInfoDao;
import com.haier.purchase.data.model.vehcile.AreaCenterInfoDTO;
import com.haier.purchase.data.service.vechile.PurchaseAreaCenterInfoService;

@Service
public class PurchaseAreaCenterInfoServiceImpl implements PurchaseAreaCenterInfoService {

	@Autowired
	AreaCenterInfoDao areaCenterInfoDao;
	@Override
	public int insertSelective(AreaCenterInfoDTO entity) {
		return areaCenterInfoDao.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(AreaCenterInfoDTO entity) {
		return areaCenterInfoDao.updateSelectiveById(entity);
	}

	@Override
	public AreaCenterInfoDTO getOneById(long id) {
		return areaCenterInfoDao.getOneById(id);
	}

	@Override
	public AreaCenterInfoDTO getOneByCondition(AreaCenterInfoDTO entity) {
		return areaCenterInfoDao.getOneByCondition(entity);
	}

	@Override
	public List<AreaCenterInfoDTO> getListByCondition(AreaCenterInfoDTO entity) {
		return areaCenterInfoDao.getListByCondition(entity);
	}

	@Override
	public List<AreaCenterInfoDTO> getPageByCondition(AreaCenterInfoDTO entity,
			int start, int rows) {
		return areaCenterInfoDao.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(AreaCenterInfoDTO entity) {
		return areaCenterInfoDao.getPagerCount(entity);
	}

	@Override
	public AreaCenterInfoDTO getOneByDeliveryToCode(String deliveryToCode) {
		return areaCenterInfoDao.getOneByDeliveryToCode(deliveryToCode);
	}


}