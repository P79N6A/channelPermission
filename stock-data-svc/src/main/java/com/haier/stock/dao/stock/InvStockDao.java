package com.haier.stock.dao.stock;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.haier.stock.model.InvStock;
import com.haier.stock.model.InvStore;
import org.apache.ibatis.annotations.Param;


public interface InvStockDao {

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
    List<InvStock> getChangedListBySkuWhcode(@Param("beginTime") Date beginTime,
                                             @Param("stockChannel") String stockChannel,
                                             @Param("channel") String channel);

    List<InvStock> getHAIPStockQty(@Param("beginTime") Date beginTime,
                                   @Param("stockChannel") String stockChannel);

    List<InvStock> getChangedStockQty(@Param("beginTime") Date beginTime,
                                      @Param("stockChannel") String stockChannel,
                                      @Param("channel") String channel);

    /**
     * 获取可用库存
     * @param skus
     * @param secCodes
     * @param channelCodes
     * @param channel
     * @return
     */
    List<InvStock> getStockQtyByWhCodesAndSkus(@Param("skus") String skus,
                                               @Param("whCodes") String secCodes,
                                               @Param("channelCodes") String channelCodes,
                                               @Param("channel") String channel);

    /**
     * 获取可用库存 -可选择批次
     * @param skus
     * @param secCodes
     * @param channelCodes
     * @param itemProperty
     * @return
     */
    List<InvStock> getStockQtyByItemProperty(@Param("skus") String skus,
                                             @Param("whCodes") String secCodes,
                                             @Param("channelCodes") String channelCodes,
                                             @Param("itemProperty") Integer itemProperty);

    /**
     * 获取可用库存（主库）
     * @param skus
     * @param secCodes
     * @param channelCodes
     * @param channel
     * @return
     */
    List<InvStock> getReliableStockQtyByWhCodesAndSkus(@Param("skus") String skus,
                                                       @Param("whCodes") String secCodes,
                                                       @Param("channelCodes") String channelCodes,
                                                       @Param("channel") String channel);

    /**
     * 获取可用库存（主库） -可选择批次
     * @param skus
     * @param secCodes
     * @param channelCodes
     * @param itemProperty
     * @return
     */
    List<InvStock> getReliableStockQtyByItemProperty(@Param("skus") String skus,
                                                     @Param("whCodes") String secCodes,
                                                     @Param("channelCodes") String channelCodes,
                                                     @Param("itemProperty") Integer itemProperty);

    /**
     * 获取库存大于0的记录 - 主要用于商城列表页等只关注有库存商品的场景
     * @param skus
     * @param secCodes
     * @param channelCodes
     * @return
     */
    List<InvStock> getValidStockQtyByWhCodesAndSkus(@Param("skus") String skus,
                                                    @Param("whCodes") String secCodes,
                                                    @Param("channelCodes") String channelCodes);

    /**
     * 获取库存大于0的记录 - 主要用于商城列表页夺宝机专区-可按照不同批次选择
     * @param skus 物料
     * @param secCodes 库位
     * @param channelCodes 渠道
     * @param itemProperty 批次
     * @return
     */
    List<InvStock> getValidStockQtyByItemProperty(@Param("skus") String skus,
                                                  @Param("whCodes") String secCodes,
                                                  @Param("channelCodes") String channelCodes,
                                                  @Param("itemProperty") Integer itemProperty);

    /**
     * 根据逻辑主键，获取库存对象
     * @param secCode 库位编码
     * @param sku 物料编码
     * @return
     */
    InvStock getBySecCodeAndSku(@Param("secCode") String secCode, @Param("sku") String sku);

    /**
     * 根据逻辑主键，获取库存对象并锁定
     * @param secCode 库位编码
     * @param sku 物料编码
     * @return
     */
    InvStock getBySecCodeAndSkuForLock(@Param("secCode") String secCode, @Param("sku") String sku);

    /**
     * 根据sku获取库存列表
     * @param sku
     * @return
     */
    List<InvStock> getBySku(String sku);

    List<InvStock> getBySkuAndWhCode(@Param("sku") String sku, @Param("whCode") String whCode);

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
    Integer releaseCbsFrozenQty(@Param("stoId") Integer stoId,
                                @Param("releaseQty") Integer releaseQty);

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
    Integer frozenStockQty(@Param("stoId") Integer id, @Param("frozenQty") Integer frozenQty);

    List<InvStock> getChanngedStockList(@Param("updateTime") Date updateTime,
                                        @Param("topX") int topX);

    InvStock getMaxStock(@Param("sku") String sku, @Param("whCodes") Set<String> whCodes,
                         @Param("stockChannels") Set<String> stockChannels);

}
