package com.haier.purchase.data.services.vechile;

import java.util.List;
import java.util.Map;

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


	@Override
	public List<AreaCenterInfoDTO> getAreaCenterInfo(Map<String, Object> params) {
		return areaCenterInfoDao.getAreaCenterInfo(params);
	}

	@Override
	public Integer getAreaCenterInfoCount(Map<String, Object> params) {
		return areaCenterInfoDao.getAreaCenterInfoCount(params);

	}

	@Override
	public void updateSelectiveByDeliveryToCode(AreaCenterInfoDTO areaCenterInfoDTO) {
		areaCenterInfoDao.updateSelectiveByDeliveryToCode(areaCenterInfoDTO);
	}

	@Override
	public void openStatusAreaCenterInfo(Map<String, Object> params) {
		areaCenterInfoDao.openStatusAreaCenterInfo(params);

	}

	@Override
	public void closeStatusAreaCenterInfo(Map<String, Object> params) {
		areaCenterInfoDao.closeStatusAreaCenterInfo(params);

	}

	@Override
	public List<AreaCenterInfoDTO> getAreaCenterInfoExport(Map<String, Object> params) {
		return areaCenterInfoDao.getAreaCenterInfoExport(params);
	}
}