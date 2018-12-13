package com.haier.vehicle.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.vehcile.AreaCenterInfoDTO;
import com.haier.purchase.data.service.vechile.PurchaseAreaCenterInfoService;
import com.haier.vehicle.service.AreaCenterInfoService;


@Service
public class AreaCenterInfoServiceImpl implements AreaCenterInfoService {

	@Autowired
	PurchaseAreaCenterInfoService purchaseAreaCenterInfoService;
	@Override
	public int insertSelective(AreaCenterInfoDTO entity) {
		return purchaseAreaCenterInfoService.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(AreaCenterInfoDTO entity) {
		return purchaseAreaCenterInfoService.updateSelectiveById(entity);
	}

	@Override
	public AreaCenterInfoDTO getOneById(long id) {
		return purchaseAreaCenterInfoService.getOneById(id);
	}

	@Override
	public AreaCenterInfoDTO getOneByCondition(AreaCenterInfoDTO entity) {
		return purchaseAreaCenterInfoService.getOneByCondition(entity);
	}

	@Override
	public List<AreaCenterInfoDTO> getListByCondition(AreaCenterInfoDTO entity) {
		return purchaseAreaCenterInfoService.getListByCondition(entity);
	}

	@Override
	public List<AreaCenterInfoDTO> getPageByCondition(AreaCenterInfoDTO entity,
			int start, int rows) {
		return purchaseAreaCenterInfoService.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(AreaCenterInfoDTO entity) {
		return purchaseAreaCenterInfoService.getPagerCount(entity);
	}

	@Override
	public AreaCenterInfoDTO getOneByDeliveryToCode(String deliveryToCode) {
		return purchaseAreaCenterInfoService.getOneByDeliveryToCode(deliveryToCode);
	}

	@Override
	public List<AreaCenterInfoDTO> getAreaCenterInfo(Map<String, Object> params) {
		return purchaseAreaCenterInfoService.getAreaCenterInfo(params);
	}

	@Override
	public Integer getAreaCenterInfoCount(Map<String, Object> params) {
		return purchaseAreaCenterInfoService.getAreaCenterInfoCount(params);
	}

	@Override
	public void updateSelectiveByDeliveryToCode(AreaCenterInfoDTO areaCenterInfoDTO) {
		purchaseAreaCenterInfoService.updateSelectiveByDeliveryToCode(areaCenterInfoDTO);
	}

	@Override
	public void openStatusAreaCenterInfo(Map<String, Object> params) {
		purchaseAreaCenterInfoService.openStatusAreaCenterInfo(params);

	}

	@Override
	public void closeStatusAreaCenterInfo(Map<String, Object> params) {
		purchaseAreaCenterInfoService.closeStatusAreaCenterInfo(params);

	}

	@Override
	public List<AreaCenterInfoDTO> getAreaCenterInfoExport(Map<String, Object> params) {
		return 		purchaseAreaCenterInfoService.getAreaCenterInfoExport(params);
	}


}