package com.haier.stock.service;

import com.haier.stock.model.InvStoreLock;

import java.util.List;


public interface InvStoreLockService {

    public Integer insert(InvStoreLock storeLock);

    /**
     * 通过单据号、物料编码和店铺码查询没有释放的库存
     * @param refNo
     * @param sku
     * @param storeCode
     * @return
     */
    public List<InvStoreLock> getNotReleased( String refNo,  String sku,
                                             String storeCode,
                                              String itemProperty);

    /**
     * 通过单据号、物料编码和店铺码查询没有释放的库存（只取一条）
     * @param refNo
     * @param sku
     * @param storeCode
     * @return
     */
    public InvStoreLock getNotReleasedForUpdate(  String refNo,
                                               String sku,
                                                 String storeCode,
                                                  String itemProperty);

    /**
     * 更新店铺库存占用表中的库存释放数量
     * @param id
     * @param releaseQty
     * @param optUser
     * @return
     */
    public Integer updateReleaseQty( Integer id,
                                      Integer releaseQty,
                                    String optUser,
                                    String itemProperty);

    /**
     * 查询一段时间后没有释放的库存
     * @param lockTime
     * @param topx
     * @return
     */
    public List<InvStoreLock> getNoReleaseByLockTime( String lockTime,
                                                      Integer topx);

}
