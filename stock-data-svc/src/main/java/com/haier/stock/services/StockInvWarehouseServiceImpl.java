package com.haier.stock.services;

import com.haier.common.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvWarehouseDao;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.service.StockInvWarehouseService;

import java.util.List;
import java.util.Map;

@Service
public class StockInvWarehouseServiceImpl implements StockInvWarehouseService{
	@Autowired
	private InvWarehouseDao invWarehouseDao;
	  /**
     * 
     * @title getWhByEstorgeId
     * @description 暴露的接口dao层，通过estorge_id查询数据
     */
	@Override
	public InvWarehouse getWhByEstorgeId(String estorge_id) {
		// TODO Auto-generated method stub
		return invWarehouseDao.getWhByEstorgeId(estorge_id);
	}
	  /**
     * 
     * @title getAllWhByEstorgeId
     * @description 暴露的接口dao层，通过estorge_id查询所有数据
     */
	@Override
	public InvWarehouse getAllWhByEstorgeId(String estorge_id) {
		// TODO Auto-generated method stub
		return invWarehouseDao.getAllWhByEstorgeId(estorge_id);
	}

	@Override
	public InvWarehouse getByWhCode(String whCode) {
		// TODO Auto-generated method stub
		return invWarehouseDao.getByWhCode(whCode);
	}

	@Override
	public String getWhCodeByCenterCode(String centerCode) {
		// TODO Auto-generated method stub
		return invWarehouseDao.getWhCodeByCenterCode(centerCode);
	}

	@Override
	public Integer getInvWarehouseCount(Map<String, Object> params) {
		return invWarehouseDao.getInvWarehouseCount(params);
	}

	@Override
	public List<InvWarehouse> getInvWarehouseInfo(Map<String, Object> param) {
		return invWarehouseDao.getInvWarehouseInfo(param);
	}

	@Override
	public int checkMainKey(Map<String, Object> params) {
		return  invWarehouseDao.checkMainKey(params);
	}

	@Override
	public void createInvWarehouse(Map<String, Object> params) {
		invWarehouseDao.createInvWarehouse(params);
	}

	@Override
	public void updateInvWarehouse(Map<String, Object> params) {
		invWarehouseDao.updateInvWarehouse(params);
	}

	@Override
	public void deleteInvWareHouse(Map<String, Object> params) {
		invWarehouseDao.deleteInvWareHouse(params);
	}

	@Override
	public void openStatusInvWarehouse(Map<String, Object> params) {
		invWarehouseDao.openStatusInvWarehouse(params);
	}

	@Override
	public void closeStatusInvWarehouse(Map<String, Object> params) {
		invWarehouseDao.closeStatusInvWarehouse(params);
	}

	@Override
	public List<InvWarehouse> getInvWarehouseExport(Map<String, Object> param) {
		return invWarehouseDao.getInvWarehouseExport(param);
	}
}
