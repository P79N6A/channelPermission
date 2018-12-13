package com.haier.stock.service;

import com.haier.common.ServiceResult;
import com.haier.stock.model.HaierStockExceedCacheVO;
import com.haier.stock.model.InvStockAge;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InvStockAgeService {
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
	 * 更新
	 * 
	 *  invStockAge
	 * @return
	 */
	public Integer update(InvStockAge invStockAge);

	/**
	 * 更新
	 * 
	 *  invStockAge
	 * @return
	 */
	
	public Integer updateDate(InvStockAge invStockAge);

	/**
	 * 更新采购单价
	 * 
	 *  invStockAge
	 * @return
	 */
	
	public Integer updatePrice(InvStockAge invStockAge);

	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	public Integer getCount(Map<String, Object> params);

	/**
	 * 获取库龄记录：分页&条件
	 * 
	 *  params
	 * @return
	 */
	public List<InvStockAge> getStockAgeList(Map<String, Object> params);

	/**
	 * 获取指定的库龄记录
	 * 
	 *  secCode
	 *  channelCode
	 *  sku
	 * @return
	 */
	public InvStockAge get( String secCode,
			 String channelCode,  String sku);

	/**
	 * 获取指定的库龄记录
	 * 
	 *  secCode
	 *  sku
	 * @return
	 */
	public List<InvStockAge> getBySkuAndSCode(
			 String secCode,  String sku);

	
	public Integer insert(InvStockAge invStockAge);

	public Date getNow();

	/**
	 * 更新库齡表的物料基本信息(商品名称，品类，产品组，品牌)
	 * 
	 *  stockAge
	 * @return
	 */
	
	public Integer updateMtlInfoForStockAge(InvStockAge stockAge);

	List<String> getProductGroupsByProductType(
			 String productType);

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
	 *  params
	 * @return
	 */
	public List<HaierStockExceedCacheVO> findStockAgeList(Map params);

	/**
	 * 根据渠道和品类统计库存
	 * 
	 * @return
	 */
	public List<HaierStockExceedCacheVO> findStockTotal();

	public ServiceResult<Integer> updatePriceForStockAge(InvStockAge stockAge);
}
