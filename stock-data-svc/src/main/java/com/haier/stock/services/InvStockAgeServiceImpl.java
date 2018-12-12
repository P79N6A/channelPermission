package com.haier.stock.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvStockAgeDao;
import com.haier.stock.model.HaierStockExceedCacheVO;
import com.haier.stock.model.InvStockAge;
import com.haier.stock.service.InvStockAgeService;
@Service
public class InvStockAgeServiceImpl implements InvStockAgeService {
	@Autowired
	private InvStockAgeDao invStockAgeDao;
	@Override
	public List<InvStockAge> getAll() {
		// TODO Auto-generated method stub
		return invStockAgeDao.getAll();
	}

	@Override
	public List<InvStockAge> getGroupBySecAndSku() {
		// TODO Auto-generated method stub
		return invStockAgeDao.getGroupBySecAndSku();
	}

	@Override
	public Integer update(InvStockAge invStockAge) {
		// TODO Auto-generated method stub
		return invStockAgeDao.update( invStockAge);
	}

	@Override
	public Integer updateDate(InvStockAge invStockAge) {
		// TODO Auto-generated method stub
		return invStockAgeDao.updateDate( invStockAge);
	}

	@Override
	public Integer updatePrice(InvStockAge invStockAge) {
		// TODO Auto-generated method stub
		return invStockAgeDao.updatePrice( invStockAge);
	}

	@Override
	public Integer getCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return invStockAgeDao.getCount( params);
	}

	@Override
	public List<InvStockAge> getStockAgeList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return invStockAgeDao.getStockAgeList( params);
	}

	@Override
	public InvStockAge get(String secCode, String channelCode, String sku) {
		// TODO Auto-generated method stub
		return invStockAgeDao.get( secCode,  channelCode,  sku);
	}

	@Override
	public List<InvStockAge> getBySkuAndSCode(String secCode, String sku) {
		// TODO Auto-generated method stub
		return invStockAgeDao.getBySkuAndSCode( secCode,  sku);
	}

	@Override
	public Integer insert(InvStockAge invStockAge) {
		// TODO Auto-generated method stub
		return invStockAgeDao.insert( invStockAge);
	}

	@Override
	public Date getNow() {
		// TODO Auto-generated method stub
		return invStockAgeDao.getNow();
	}

	@Override
	public Integer updateMtlInfoForStockAge(InvStockAge stockAge) {
		// TODO Auto-generated method stub
		return invStockAgeDao.updateMtlInfoForStockAge( stockAge);
	}

	@Override
	public List<String> getProductGroupsByProductType(String productType) {
		// TODO Auto-generated method stub
		return invStockAgeDao.getProductGroupsByProductType( productType);
	}

	@Override
	public List<String> getProductGroups() {
		// TODO Auto-generated method stub
		return invStockAgeDao.getProductGroups();
	}

	@Override
	public List<String> getProductTypes() {
		// TODO Auto-generated method stub
		return invStockAgeDao.getProductTypes();
	}

	@Override
	public List<String> getSecCodes() {
		// TODO Auto-generated method stub
		return invStockAgeDao.getSecCodes();
	}

	@Override
	public List<HaierStockExceedCacheVO> findStockAgeList(Map params) {
		// TODO Auto-generated method stub
		return invStockAgeDao.findStockAgeList( params);
	}

	@Override
	public List<HaierStockExceedCacheVO> findStockTotal() {
		// TODO Auto-generated method stub
		return invStockAgeDao.findStockTotal();
	}

}
