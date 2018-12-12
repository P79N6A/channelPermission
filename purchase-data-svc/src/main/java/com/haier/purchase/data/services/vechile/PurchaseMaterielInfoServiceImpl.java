package com.haier.purchase.data.services.vechile;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.vehcile.MaterielInfoDao;
import com.haier.purchase.data.model.vehcile.MaterielInfoDTO;
import com.haier.purchase.data.service.vechile.PurchaseMaterielInfoService;

@Service
public class PurchaseMaterielInfoServiceImpl implements PurchaseMaterielInfoService {

	@Autowired
	MaterielInfoDao materielInfoDao;
	
	@Override
	public int insertSelective(MaterielInfoDTO entity) {
		return materielInfoDao.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(MaterielInfoDTO entity) {
		return materielInfoDao.updateSelectiveById(entity);
	}

	@Override
	public List<MaterielInfoDTO> selectByKeys(List<String> keys) {
		return materielInfoDao.selectByKeys(keys);
	}

	@Override
	public MaterielInfoDTO getOneById(long id) {
		return materielInfoDao.getOneById(id);
	}

	@Override
	public MaterielInfoDTO getOneByCondition(MaterielInfoDTO entity) {
		return materielInfoDao.getOneByCondition(entity);
	}

	@Override
	public List<MaterielInfoDTO> getListByCondition(MaterielInfoDTO entity) {
		return materielInfoDao.getListByCondition(entity);
	}

	@Override
	public List<MaterielInfoDTO> getPageByCondition(MaterielInfoDTO entity,
			int start, int rows) {
		return materielInfoDao.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(MaterielInfoDTO entity) {
		return materielInfoDao.getPagerCount(entity);
	}

	@Override
	public MaterielInfoDTO getOneByDeliveryToCode(String deliveryToCode) {
		return materielInfoDao.getOneByDeliveryToCode(deliveryToCode);
	}

	@Override
	public List<MaterielInfoDTO> getVehicleInfo(Integer status) {
		return materielInfoDao.getVehicleInfo(status);
	}

	@Override
	public void updateMaterielInfo(Double volume, String materielCode) {
		materielInfoDao.updateMaterielInfo(volume, materielCode);		
	}
	
	@Override
	public void deleteMateriel(){
		materielInfoDao.deleteMateriel();
	}
	
	@Override
	public int batchAdd(List<MaterielInfoDTO> infoList){
		return materielInfoDao.batchAdd(infoList);
	}

	@Override
	public void truncateMaterielInfo() {
		materielInfoDao.truncateMaterielInfo();
	}
	
	@Override
	public List<MaterielInfoDTO>  getListByParams( MaterielInfoDTO entity){
		return materielInfoDao.getListByParams(entity);
	}
}