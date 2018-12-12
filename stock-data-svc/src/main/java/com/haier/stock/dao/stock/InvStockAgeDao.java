package com.haier.stock.dao.stock;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.stock.model.HaierStockExceedCacheVO;
import com.haier.stock.model.InvStockAge;
import com.haier.stock.model.InvStockAgeLog;
import org.apache.ibatis.annotations.Param;


public interface InvStockAgeDao {

	int getCount(Map<String, Object> params);

	List<InvStockAge> getStockAgeList(Map<String, Object> params);
	/**
	 * 按产品统计指定渠道的库存
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> countStockGroupBySkuWithChannel(@Param("date") Date date,
															  @Param("channel") String channel);
	
	
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
	List<Map<String, Object>> countStockGroupToChannelsToProducts(@Param("startdate") Date startdate,
																  @Param("enddate") Date enddate);
	/**
	 * 预测准确率-到渠道到产品统计-总计
	 * @param
	 * @return
	 */
	List<Map<String, Object>> sumStockGroupToChannelsToProducts(@Param("startdate") Date startdate,
																@Param("enddate") Date enddate);
	/**
	 * 预测准确率-到渠道到产品统计-总计-小计
	 * @param
	 * @return
	 */
	List<Map<String, Object>> totalStockGroupToChannelsToProducts(@Param("startdate") Date startdate,
																  @Param("enddate") Date enddate);
	/**
	 * 预测准确率-到渠道到产品统计-小计
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> subSumStockGroupToChannelsToProducts(@Param("startdate") Date startdate,
																   @Param("enddate") Date enddate);
	/**
	 * 预测准确率-到产品到渠道统计
	 * @param
	 * @return
	 */
	List<Map<String, Object>> countStockGroupToProductsToChannels(@Param("startdate") Date startdate,
																  @Param("enddate") Date enddate);
	/**
	 * 预测准确率-到产品到渠道统计-总计
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> sumStockGroupToProductsToChannels(@Param("startdate") Date startdate,
																@Param("enddate") Date enddate);
	/**
	 * 预测准确率-到产品到渠道统计-总计-小计
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> totalStockGroupToProductsToChannels(@Param("startdate") Date startdate,
																  @Param("enddate") Date enddate);
	/**
	 * 预测准确率-到产品到渠道统计-小计
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> subSumStockGroupToProductsToChannels(@Param("startdate") Date startdate,
																   @Param("enddate") Date enddate);
	/**
	 * 按产品统计
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> countStockGroupBySku(@Param("date") Date date);
	/**
	 * 按渠道统计制定产品组的库存
	 * @param date
	 * @return
	 */
	List<InvStockAgeLog> countStockGroupByChannelWhthSku(@Param("date")Date date, @Param("productGroupName")String productGroupName);
	public List<InvStockAge> getBySkuAndSCode(
			@Param("sec_code") String secCode, @Param("sku") String sku);
	public Integer updateDate(InvStockAge invStockAge);
	public Integer insert(InvStockAge invStockAge);
	
	public Date getNow();
	public Integer update(InvStockAge invStockAge);
	/**
	 * 获取所有库龄记录
	 * 
	 * @return
	 */
	public List<InvStockAge> getAll();
	/**
	 * 获取sku和sec_code
	 * 
	 * @return
	 */
	public List<InvStockAge> getGroupBySecAndSku();
	/**
	 * 更新采购单价
	 * 
	 * @param invStockAge
	 * @return
	 */
	public Integer updatePrice(InvStockAge invStockAge);
	/**
	 * 获取指定的库龄记录
	 * 
	 * @param secCode
	 * @param channelCode
	 * @param sku
	 * @return
	 */
	public InvStockAge get(@Param("sec_code") String secCode,
			@Param("channel_code") String channelCode, @Param("sku") String sku);
	
	/**
	 * 更新库齡表的物料基本信息(商品名称，品类，产品组，品牌)
	 * 
	 * @param stockAge
	 * @return
	 */
	public Integer updateMtlInfoForStockAge(InvStockAge stockAge);

	List<String> getProductGroupsByProductType(
			@Param("productType") String productType);

	List<String> getProductGroups();
	/**
	 * 品类查询
	 * 
	 * @return
	 */
	List<String> getProductTypes();

	List<String> getSecCodes();
	/**
	 * 查询库龄表
	 * 
	 * @param params
	 * @return
	 */
	public List<HaierStockExceedCacheVO> findStockAgeList(Map params);

	/**
	 * 根据渠道和品类统计库存
	 * 
	 * @return
	 */
	public List<HaierStockExceedCacheVO> findStockTotal();
}

