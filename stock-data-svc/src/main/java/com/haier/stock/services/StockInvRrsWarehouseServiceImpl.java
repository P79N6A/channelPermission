package com.haier.stock.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvRrsWarehouseDao;
import com.haier.stock.model.InvRrsWarehouse;
import com.haier.stock.service.StockInvRrsWarehouseService;
@Service
public class StockInvRrsWarehouseServiceImpl implements  StockInvRrsWarehouseService{
	@Autowired
	private  InvRrsWarehouseDao invRrsWarehouseDao;
	/**
	 * 
	 * @Title: getRrsWhByEstorgeOriginal
	 * @Description:对外暴露服务的dao层接口，默认通过estorge_id字段查询，可选查询字段有t2_default， 
	 *                                                                  rrs_wh_code
	 */
	@Override
	public List<InvRrsWarehouse> getRrsWhByEstorgeOriginal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return invRrsWarehouseDao.getRrsWhByEstorgeOriginal(params);
	}
	/**
	 * 对外暴露服务 查询所有数据
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<InvRrsWarehouse> getAllRrsWhByEstorgeOriginal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return invRrsWarehouseDao.getAllRrsWhByEstorgeOriginal(params);
	}

	@Override
	public List<InvRrsWarehouse> getRrsWhByEstorgeId(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return invRrsWarehouseDao.getRrsWhByEstorgeId(params);
	}

}
