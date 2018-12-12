package com.haier.stock.service;

import java.util.List;


import com.haier.stock.model.InvStockLock;

public interface StockInvStockLockService {

    public  InvStockLock getNotReleased( String refNo, String sku,
                                 String secCode);

    public InvStockLock getLast( String refNo, String sku,
                          String secCode);

    /**
     * 根据单据号获取冻结记录
     * @param refNo
     * @return
     */
    public  List<InvStockLock> getNotReleasedByRefNo( String refNo);

    public  List<InvStockLock> getNotReleaseBySkuAndWh( String whCode,
                                               String sku,
                                               String channelCode);

    /**
     * 获得需要重新分配渠道的记录
     * @return
     */
    public  List<InvStockLock> getProcessStockLock();

    /**
     * 根据单据号获取冻结记录
     * @param refNo
     * @return
     */
    public  List<InvStockLock> getNotReleasedByRefNoSku( String refNo,
                                                 String sku);

    public List<InvStockLock> getByRefNoAndSku( String refNo,  String sku);

    public  Integer insert(InvStockLock stockLock);

    public  Integer update(InvStockLock stockLock);

    /**
     * 增加释放数
     * @param id
     * @param releaseQty
     * @return
     */
    public  Integer updateReleaseQty(Integer id,  Integer releaseQty,
                              String optUser);

    /**
    * 增加占用数
    * @param id
    * @param lockQty
    * @return
    */
    public  Integer updateLockQty( Integer id,  Integer lockQty,
                          String optUser);

    /**
     * 查询一段时间后没有释放的库存
     * @param lockTime
     * @param topx
     * @return
     */
    public  List<InvStockLock> getNoReleaseByLockTime( String lockTime,
                                              Integer topx);
}
