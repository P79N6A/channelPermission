package com.haier.stock.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvWarehouseDao;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.service.StockInvWarehouseService;
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

}
