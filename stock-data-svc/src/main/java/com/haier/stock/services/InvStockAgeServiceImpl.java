package com.haier.stock.services;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.mysql.jdbc.StringUtils;
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

		if (invStockAge == null) {
			throw new BusinessException("库齡对象不能为空。");
		}
		if (invStockAge.getPrice() == null) {
			throw new BusinessException("采购单价不能为空。");
		}
		if (StringUtils.isNullOrEmpty(invStockAge.getSku())) {
			throw new BusinessException("物料编码不能为空。");
		}
		if (StringUtils.isNullOrEmpty(invStockAge.getSecCode())) {
			throw new BusinessException("库位编码不能为空。");
		}
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

	@Override
	public ServiceResult<Integer> updatePriceForStockAge(InvStockAge stockAge) {
		ServiceResult<Integer> result = new ServiceResult<Integer>();
		try {
			result.setResult(this.updatePrice(stockAge));
		} catch (BusinessException be) {
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("出现未知异常:" + e.getMessage());
		}
		return result;
	}

}
