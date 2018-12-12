package com.haier.stock.service;

import com.haier.stock.model.InvStockOrderLockEntity;

import java.util.List;

/**
 * [库存下单锁定表]Dao
 * <p>
 * Table: <strong>inv_stock_order_lock</strong>
 * 
 * @Filename: InvStockOrderLockDao.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 * 
 */
public interface InvStockOrderLockService {

    /**
     * 根据id取得数据
     * 
     * @param id
     * @return
     */
    public InvStockOrderLockEntity findInvStockOrderLockById(String id);

    /**
     * 根据库位，店铺，sku获取总锁定量
     * 
     * @param scode
     * @param storeCode
     * @param sku
     * @return
     */
    public Integer findLockQtyByScodeAndStoreCodeAndSku(  String scode,
                                                          String storeCode,
                                                          String sku,
                                                         String refNo);

    /**
     * 根据库位，sku获取总锁定量
     * 
     * @param scode
     * @param sku
     * @return
     */
    public List<InvStockOrderLockEntity> findLockQtyByScodeAndSku( String[] arrayScode,
                                                                  String[] arraySku);

    /**
     * 根据refNo查询数据
     * 
     * @param refNo
     * @return
     */
    public InvStockOrderLockEntity findInvStockOrderLockByRefNo( String refNo);

    /**
     * 下单锁库
     * 
     * @param stockLockEntity
     * @return
     */
    public Integer insertStockOrderLock(InvStockOrderLockEntity stockLockEntity);

    /**
     * 锁库释放，删除数据
     * 
     * @return
     */
    public Integer releaseOrderLockByRefNo();

    /**
     * 付款成功锁库释放，删除数据
     * 
     * @param refNo
     * @return
     */
    public Integer paymentSuccessReleaseOrderLock(  String refNo);

    /**
     * 更新下单锁
     * 
     * @param stockLockEntity
     * @return
     */
    public Integer updateStockOrderLock(InvStockOrderLockEntity stockLockEntity);

}