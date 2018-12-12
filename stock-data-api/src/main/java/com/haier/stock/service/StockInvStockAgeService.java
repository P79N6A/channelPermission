package com.haier.stock.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.stock.model.InvStockAge;
import com.haier.stock.model.InvStockAgeLog;

public interface StockInvStockAgeService {
	public int getCount(Map<String, Object> params);

	public List<InvStockAge> getStockAgeList(Map<String, Object> params);
	public List<Map<String, Object>> countStockGroupBySkuWithChannel(Date date, String channel);

	Integer getRowCnt();

	 /**
     * 查询库存统计列表
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getStockAgeListTo2(Map<String, Object> paramMap);
	/**
	 * 预测准确率-到渠道到产品统计
	 * @param
	 * @return
	 */
	List<Map<String, Object>> countStockGroupToChannelsToProducts(Date startdate,Date enddate);
	/**
	 * 预测准确率-到渠道到产品统计-总计
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> sumStockGroupToChannelsToProducts(Date startdate, Date enddate);
	/**
	 * 预测准确率-到渠道到产品统计-总计-小计
	 * @param
	 * @return
	 */
	List<Map<String, Object>> totalStockGroupToChannelsToProducts(Date startdate, Date enddate);
	/**
	 * 预测准确率-到渠道到产品统计-小计
	 * @param
	 * @return
	 */
	List<Map<String, Object>> subSumStockGroupToChannelsToProducts(Date startdate, Date enddate);
	/**
	 * 预测准确率-到产品到渠道统计
	 * @param
	 * @return
	 */
	List<Map<String, Object>> countStockGroupToProductsToChannels(Date startdate, Date enddate);
	/**
	 * 预测准确率-到产品到渠道统计-总计
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> sumStockGroupToProductsToChannels(Date startdate, Date enddate);
	/**
	 * 预测准确率-到产品到渠道统计-总计-小计
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> totalStockGroupToProductsToChannels(Date startdate, Date enddate);
	/**
	 * 预测准确率-到产品到渠道统计-小计
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> subSumStockGroupToProductsToChannels(Date startdate, Date enddate);
	/**
	 * 按产品统计
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> countStockGroupBySku(Date date);
	/**
	 * 按渠道统计制定产品组的库存
	 * @param date
	 * @return
	 */
	List<InvStockAgeLog> countStockGroupByChannelWhthSku(Date date, String productGroupName);
	public List<InvStockAge> getBySkuAndSCode(String secCode,String sku);
	public Integer updateDate(InvStockAge invStockAge);
	public Integer insert(InvStockAge invStockAge);
	
	public Date getNow();
	
	/**
	 * 更新
	 * 
	 * @param invStockAge
	 * @return
	 */
	public Integer update(InvStockAge invStockAge);
}
