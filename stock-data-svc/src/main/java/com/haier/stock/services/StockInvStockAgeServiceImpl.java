package com.haier.stock.services;

import com.haier.common.ServiceResult;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.stock.model.InvStockAgeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.stock.dao.stock.InvStockAgeDao;
import com.haier.stock.model.InvStockAge;
import com.haier.stock.service.StockInvStockAgeService;
@Service
public class StockInvStockAgeServiceImpl implements StockInvStockAgeService{
	@Autowired
	private InvStockAgeDao invStockAgeDao;
	@Override
	public int getCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return invStockAgeDao.getCount(params);
	}

	@Override
	public List<InvStockAge> getStockAgeList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return invStockAgeDao.getStockAgeList(params);
	}

	@Override
	public Integer getStockAgeListCounts(Map<String, Object> params) {
		return invStockAgeDao.getCount(params);
	}

	@Override
	public List<Map<String, Object>> countStockGroupBySkuWithChannel(Date date, String channel){
		return invStockAgeDao.countStockGroupBySkuWithChannel(date, channel);
	}

	@Override
	public Integer getRowCnt() {
		// TODO Auto-generated method stub
		return invStockAgeDao.getRowCnt();
	}

	@Override
	public List<Map<String, Object>> getStockAgeListTo2(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return invStockAgeDao.getStockAgeListTo2(paramMap);
	}
	/**
	 * 预测准确率-到渠道到产品统计
	 * @param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> countStockGroupToChannelsToProducts(Date startdate,Date enddate){
		return invStockAgeDao.countStockGroupToChannelsToProducts(startdate,enddate);
	}
	/**
	 * 预测准确率-到渠道到产品统计-总计
	 * @param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> sumStockGroupToChannelsToProducts(Date startdate, Date enddate){
		return invStockAgeDao.sumStockGroupToChannelsToProducts(startdate,enddate);
		}
	/**
	 * 预测准确率-到渠道到产品统计-总计-小计
	 * @param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> totalStockGroupToChannelsToProducts(Date startdate, Date enddate){
		return invStockAgeDao.totalStockGroupToChannelsToProducts(startdate,enddate);
	}
	/**
	 * 预测准确率-到渠道到产品统计-小计
	 * @param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> subSumStockGroupToChannelsToProducts(Date startdate, Date enddate){
		return invStockAgeDao.subSumStockGroupToChannelsToProducts(startdate,enddate);
	}
	/**
	 * 预测准确率-到产品到渠道统计
	 * @param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> countStockGroupToProductsToChannels(Date startdate, Date enddate){
		return invStockAgeDao.countStockGroupToProductsToChannels(startdate,enddate);
	}
	/**
	 * 预测准确率-到产品到渠道统计-总计
	 * @param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> sumStockGroupToProductsToChannels(Date startdate, Date enddate){
		return invStockAgeDao.sumStockGroupToProductsToChannels(startdate,enddate);
	}
	/**
	 * 预测准确率-到产品到渠道统计-总计-小计
	 * @param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> totalStockGroupToProductsToChannels(Date startdate, Date enddate){
		return invStockAgeDao.totalStockGroupToProductsToChannels(startdate,enddate);
	}
	/**
	 * 预测准确率-到产品到渠道统计-小计
	 * @param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> subSumStockGroupToProductsToChannels(Date startdate, Date enddate){
		return invStockAgeDao.subSumStockGroupToProductsToChannels(startdate,enddate);
	}
	/**
	 * 按产品统计
	 * @param date
	 * @return
	 */
	@Override
	public List<Map<String, Object>> countStockGroupBySku(Date date){
		return invStockAgeDao.countStockGroupBySku(date);
	}
	/**
	 * 按渠道统计制定产品组的库存
	 * @param date
	 * @return
	 */
	@Override
	public List<InvStockAgeLog> countStockGroupByChannelWhthSku(Date date, String productGroupName){
		return invStockAgeDao.countStockGroupByChannelWhthSku(date,productGroupName);
	}
	@Override
	public List<InvStockAge> getBySkuAndSCode(String secCode,String sku){
		return invStockAgeDao.getBySkuAndSCode(secCode,sku);
	}
	@Override
	public Integer updateDate(InvStockAge invStockAge){
		return invStockAgeDao.updateDate(invStockAge);
	}
	@Override
	public Integer insert(InvStockAge invStockAge){
		return invStockAgeDao.insert(invStockAge);
	}

	@Override
	public Date getNow() {
		
		return invStockAgeDao.getNow();
	}

	@Override
	public Integer update(InvStockAge invStockAge) {
		
		return invStockAgeDao.update(invStockAge);
	}

	@Override
	public List<InvStockAge> getStockAgeListByMap(Map param) {
		return invStockAgeDao.getStockAgeListByMap(param);
	}

	@Override
	public ServiceResult<List<String>> getProductGroupsInStockAge(String productType) {
		ServiceResult<List<String>> rs = new ServiceResult<List<String>>();
		try {
			rs.setResult(invStockAgeDao.getProductGroupsByProductType(productType));
		} catch (Exception e) {
			rs.setSuccess(false);
			rs.setError("获取库龄报表中的产品组信息出现未知异常：", e.getMessage());
		}
		return rs;
	}
}
