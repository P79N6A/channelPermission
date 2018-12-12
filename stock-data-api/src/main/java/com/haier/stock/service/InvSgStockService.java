package com.haier.stock.service;

import com.haier.stock.model.InvSgStockEntity;
import com.haier.stock.model.InvSgStockLock;
import com.haier.stock.model.InvStore;

import java.util.Date;
import java.util.List;

/**
 * [顺逛自有库存表]Dao
 * <p>Table: <strong>inv_sg_stock</strong>
 * @Filename: InvSgStockDao.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
public interface InvSgStockService {

	/**
     * 根据id取得数据
     * @param  id
     * @return
     */
     public InvSgStockEntity findInvSgStockById(String id);
     /**
      * 根据库位list,sku,请求数量获取库存信息。（传入请求数量则返回满足数量的库存，空时不考虑库存数量）
      * @param sCodes 库位list
      * @param sku 物料编码
      * @param requireQty 请求数量(可以为空)
      * @return 仅返回第一条满足库存
      */
	public InvSgStockEntity findInvSgStockByScodesAndSkuAndRequireQty(List<String> sCodes, String sku, Integer requireQty);
	
	/**
     * 新增数据
     * @param  invSgStock
     * @return
     */
     public Integer insertInvSgStock(InvSgStockEntity invSgStock);
     
     /**
      * 更新数据
      * @param  invSgStock
      * @return
      */
      public Integer updateInvSgStock(InvSgStockEntity invSgStock);
      
      /**
       * 根据sku，refNo，storeCode查询数据
       * @param sku
       * @param refNo
       * @param storeCode
       * @return
       */
      public InvSgStockEntity findInvSgStockBySkuRefNoStoreCode( String sku, String scode, String storeCode);

      public Integer updateInvSgStockQty(InvSgStockLock invSgStockLock);
      
      /**
       * 可用数量加数量，占有数量见数量
       * @param invSgStockLock
       * @return
       */
      public Integer updateReleaseForReturn(InvSgStockLock invSgStockLock);
     
      /**
       * 更新占有库存数量
       * @param sku 物料编码
	   * @param storeCode 店表编码
	   * @param releaseQty 占用数量
	   * @param refNo 单据号(网单号)
	   * @param scode 库位号
       * @return
       */
	  public Integer updatefrozenQty(String sku,  String storeCode,
			   Integer releaseQty, String refNo,  String scode);
      
      
      /**
       * 根据storeId获取库存信息
       * @param storeId
       * @return
       */
      public List<InvSgStockEntity> findInvSgStockByStoreId(Integer storeId);
      
      /**
       * 通过88码获取EC店铺下所有库存
       * @param storeCode
       * @return
       */
      public List<InvStore> findInvStockByStoreCode(String storeCode, String itemProperty);
      
      
      public List<InvSgStockEntity>  fingSgStockByLastTime(Date updateTime,
              int topX);
      
}