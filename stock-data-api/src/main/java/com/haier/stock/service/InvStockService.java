package com.haier.stock.service;

import com.haier.stock.model.InvStock;

import java.util.Date;
import java.util.List;
import java.util.Set;



public interface InvStockService {

    /**
     * 新增销售库存
     * @param stock
     * @return
     */
    Integer insert(InvStock stock);

    /**
     * 直接更新库存数量
     * @param stock
     * @return
     */
    Integer updateQty(InvStock stock);

    /**
     * 更新支持卖套机
     * @param sku
     * @return
     */
    Integer addSaleSubMachines(String sku);

    /**
     * 停用销售子件
     * @param sku
     * @return
     */
    Integer deleteSaleSubMachines(String sku);

    /**
     * 定时任务的库存变化记录的同步
     * @param beginTime
     * @param stockChannel
     * @param channel
     * @return
     */
    List<InvStock> getChangedListBySkuWhcode(  Date beginTime,
                                              String stockChannel,
                                              String channel);

    List<InvStock> getHAIPStockQty( Date beginTime,
                                String stockChannel);

    List<InvStock> getChangedStockQty( Date beginTime,
                                        String stockChannel,
                                       String channel);

    /**
     * 获取可用库存
     * @param skus
     * @param secCodes
     * @param channelCodes
     * @param channel
     * @return
     */
    List<InvStock> getStockQtyByWhCodesAndSkus( String skus,
                                                String secCodes,
                                                String channelCodes,
                                                String channel);

    /**
     * 获取可用库存 -可选择批次
     * @param skus
     * @param secCodes
     * @param channelCodes
     * @param itemProperty
     * @return
     */
    List<InvStock> getStockQtyByItemProperty( String skus,
                                             String secCodes,
                                               String channelCodes,
                                              Integer itemProperty);

    /**
     * 获取可用库存（主库）
     * @param skus
     * @param secCodes
     * @param channelCodes
     * @param channel
     * @return
     */
    List<InvStock> getReliableStockQtyByWhCodesAndSkus( String skus,
                                                         String secCodes,
                                                       String channelCodes,
                                                       String channel);

    /**
     * 获取可用库存（主库） -可选择批次
     * @param skus
     * @param secCodes
     * @param channelCodes
     * @param itemProperty
     * @return
     */
    List<InvStock> getReliableStockQtyByItemProperty( String skus,
                                                       String secCodes,
                                                       String channelCodes,
                                                      Integer itemProperty);

    /**
     * 获取库存大于0的记录 - 主要用于商城列表页等只关注有库存商品的场景
     * @param skus
     * @param secCodes
     * @param channelCodes
     * @return
     */
    List<InvStock> getValidStockQtyByWhCodesAndSkus(  String skus,
                                                     String secCodes,
                                                    String channelCodes);

    /**
     * 获取库存大于0的记录 - 主要用于商城列表页夺宝机专区-可按照不同批次选择
     * @param skus 物料
     * @param secCodes 库位
     * @param channelCodes 渠道
     * @param itemProperty 批次
     * @return
     */
    List<InvStock> getValidStockQtyByItemProperty(  String skus,
                                                   String secCodes,
                                                    String channelCodes,
                                                   Integer itemProperty);

    /**
     * 根据逻辑主键，获取库存对象
     * @param secCode 库位编码
     * @param sku 物料编码
     * @return
     */
    InvStock getBySecCodeAndSku(  String secCode, String sku);

    /**
     * 根据逻辑主键，获取库存对象并锁定
     * @param secCode 库位编码
     * @param sku 物料编码
     * @return
     */
    InvStock getBySecCodeAndSkuForLock( String secCode,  String sku);

    /**
     * 根据sku获取库存列表
     * @param sku
     * @return
     */
    List<InvStock> getBySku(String sku);

    List<InvStock> getBySkuAndWhCode( String sku,   String whCode);

    /**
     * 增量释放冻结数
     * @param stock
     * @return
     */
    Integer releaseFrozenQty(InvStock stock);

    /**
     * 该方法会释放冻结数，并扣减实际库存数
     * @param stoId
     * @param releaseQty
     * @return
     */
    Integer releaseCbsFrozenQty( Integer stoId,
                                  Integer releaseQty);

    /**
     * 增量冻结CRM（日日顺）库存
     * @param stock
     * @return
     */
    Integer frozenCrmFrozenQty(InvStock stock);

    /**
     * 冻结库存（非日日顺库存）
     * @param id
     * @param frozenQty
     * @return
     */
    Integer frozenStockQty( Integer id, Integer frozenQty);

    List<InvStock> getChanngedStockList(  Date updateTime,
                                        int topX);

    InvStock getMaxStock(  String sku,  Set<String> whCodes,
                          Set<String> stockChannels);
}
