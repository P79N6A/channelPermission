package com.haier.stock.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.JdStorageDao;
import com.haier.stock.model.JdStorage;
import com.haier.stock.service.StockJdStorageService;
@Service
public class StockJdStorageServiceImpl implements  StockJdStorageService{
	@Autowired
	private  JdStorageDao jdStorageDao;
	/**
	 * 
	 * @Title: getRrsWhByEstorgeOriginal
	 * @Description:对外暴露服务的dao层接口，默认通过estorge_id字段查询，适用jd
	 */
	@Override
	public List<JdStorage> getAllRrsWhByEstorgeOriginal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return jdStorageDao.getAllRrsWhByEstorgeOriginal(params);
	}
}
