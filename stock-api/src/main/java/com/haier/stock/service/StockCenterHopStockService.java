package com.haier.stock.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.shop.model.Stock;
/*import com.haier.svc.bean.pop.InvStockLock;
import com.haier.svc.bean.pop.InventoryBusinessTypes;
import com.haier.svc.bean.pop.Stock;*/
import com.haier.stock.model.InvStockLock;
import com.haier.stock.model.InventoryBusinessTypes;

public interface StockCenterHopStockService {

    /**
     * 增量获取变化库存
     * @param startTime 时间
     * @param channel 渠道
     * @return list of Stock:updateTime >= startTime 
     */
    ServiceResult<List<Stock>> getStockIncrement(Date startTime, String channel);

    /**
     * 获取指定sku的库存列表（数量＝0不返回）
     * @param sku 物料
     * @param channel 渠道
     * @return list of stock
     */
    ServiceResult<List<Stock>> getStockListBySku(String sku, String channel);

    /**
     * 获取指定sku的库存列表（返回全部）
     * @param sku 物料
     * @param channel 渠道
     * @return list of stock
     */
    ServiceResult<List<Stock>> getAllStockListBySku(String sku, String channel);

    @Deprecated
    ServiceResult<Date> stockStatusInCity(Date startTime);

    /**
     * 获取此物料的指定库位的库存列表
     * @param sku 物料
     * @param secCodeList 库位列表
     * @param channel 渠道
     * @return list of Stock
     */
    ServiceResult<List<Stock>> getStockBySkuAndSecCodes(String sku, List<String> secCodeList,
                                                        String channel);

    /**
     * 获取此物料的指定库位的库存列表（返回全部）
     * @param sku 物料
     * @param secCodeList 库位列表
     * @param channel 渠道
     * @return list of Stock
     */
    ServiceResult<List<Stock>> getAllStockBySkuAndSecCodes(String sku, List<String> secCodeList,
                                                           String channel);

    /**
     * 获取此库位下的指定物料的库存列表
     * @param secCode 库位
     * @param skuList 物料列表
     * @param channel 渠道
     * @return list of Stock
     */
    ServiceResult<List<Stock>> getStockBySecCodeAndSkus(String secCode, List<String> skuList,
                                                        String channel);

    /**
     * 获取对应库存下物料的库存数
     * @param secCode 库位
     * @param sku 物料
     * @param channel 渠道
     * @return list of Stock
     */
    ServiceResult<Stock> getStockBySecCodeAndSku(String secCode, String sku, String channel);

    /**
     * 查询指定城市下所有有库存的物料列表
     * @param cityId 城市id
     * @param channel 渠道
     * @return 物料列表
     */
    ServiceResult<List<String>> getStockStatusByCity(int cityId, String channel);

    /**
     * 检查指定的Sku（商城渠道）是否有库存
     * 1、商城渠道
     * 2、任何库位有库存都算有库存
     * @param sku 物料编码
     * @return 是否有库存
     */
    ServiceResult<Boolean> checkIfSkuHasStock(String sku);

    /**
     * 根据物料编码和区域ID查询库存
     * 从库
     * @param sku 物料
     * @param regionId 区域标识
     * @param channel 渠道
     * @param requireQty 需求数量
     * @return 库存信息
     */
    ServiceResult<Stock> getStockBySkuAndRegion(String sku, int regionId, String channel,
                                                int requireQty);

    /**
     * 根据物料和区域ID查询可靠的库存
     * 主库
     * @param sku 物料
     * @param regionId 区域id
     * @param channel 渠道
     * @param requireQty 需求数量
     * @return 库存信息
     */
    ServiceResult<Stock> getReliableStockBySkuAndRegion(String sku, int regionId, String channel,
                                                        int requireQty);
    
    /**
     * 根据物料和街道ID查询可靠的库存
     * 主库
     * @param sku 物料
     * @param addressId 区县或街道编码 ，和addressLevel传参意义一致
     * @param channel 渠道
     * @param requireQty 需求数量
     * @param addressLevel 地区级别   区县级=3，街道级=4
     * @return 库存信息
     */
    ServiceResult<Stock> getReliableStockBySkuAndRegionForLevel(String sku, int addressId, String channel,
			int requireQty, int addressLevel);

    /**
     * 根据物料编码和区域ID查询库存
     * 从库
     * @param sku 物料
     * @param regionId 区县标识
     * @param channel 渠道
     * @param requireQty 需求数量
     * @param isNeedMultipleSecCode 是否需要多层级
     * @return 库存信息
     */
    ServiceResult<Stock> getStockBySkuAndRegion(String sku, int regionId, String channel,
                                                int requireQty, boolean isNeedMultipleSecCode);

    /**
     * 20161212修改，根据物料编码和区域ID查询库存，不减掉下单锁，计算所有库存
     * 从库
     * @param sku 物料
     * @param regionId 区县标识
     * @param channel 渠道
     * @param requireQty 需求数量
     * @param isNeedMultipleSecCode 是否需要多层级
     * @param lockFlag 下单锁标识
     * @return 库存信息
     */
    ServiceResult<Stock> getStockBySkuAndRegionWithOutLock(String sku, int regionId, String channel,
                                                           int requireQty,
                                                           boolean isNeedMultipleSecCode,
                                                           boolean lockFlag);

    /**
     * 20170220修改，根据物料编码和区域ID或街道ID查询库存，不减掉下单锁，计算所有库存
     * 从库
     * @param sku 物料
     * @param addressId 区县或街道编码 ，和addressLevel传参意义一致
     * @param channel 渠道
     * @param requireQty 需求数量
     * @param isNeedMultipleSecCode 是否需要多层级
     * @param lockFlag 下单锁标识
     * @return 库存信息
     */
    ServiceResult<Stock> getStockBySkuAndRegionWithOutLockForLevel(String sku, int addressId, String channel,
                                                           int requireQty,
                                                           boolean isNeedMultipleSecCode,
                                                           boolean lockFlag,int addressLevel);
    
    /**
     * 根据物料和区域ID查询可靠的库存
     * 主库
     * @param sku 物料
     * @param regionId 区域ID
     * @param channel 渠道
     * @param requireQty 数量
     * @param isNeedMultipleSecCode 是否需要多层级
     * @return 库存信息
     */
    ServiceResult<Stock> getReliableStockBySkuAndRegion(String sku, int regionId, String channel,
                                                        int requireQty,
                                                        boolean isNeedMultipleSecCode);

    /**
     * 返回城市下所有可用sku List
     * @param channel 渠道
     * @return 各城市下有库存的物料列表
     */
    ServiceResult<Map<Integer, List<String>>> getCitySkusByChannel(String channel);

    /**
     * 批量获取库存
     * @param skuList 物料列表
     * @param secCodeList 库位列表
     * @param channel 渠道
     * @return 库存列表
     */
    ServiceResult<List<Stock>> getStockList(List<String> skuList, List<String> secCodeList,
                                            String channel);

    /**
     * 冻结库存  -- 按照仓库冻结
     * @param sku 物料
     * @param secCode 库位
     * @param frozenQty 占用数量
     * @param refNo 业务单据号
     * @param channelCode 渠道编码
     * @param billType 交易类型
     * @param useRRS 是否使用日日顺库存
     * @return 所占用库存的库位
     */
    ServiceResult<String> frozeStockQty(String sku, String secCode, Integer frozenQty, String refNo,
                                        String channelCode, InventoryBusinessTypes billType,
                                        boolean useRRS);

    /**
     * 冻结库存 -- 默认不冻结RRS库存
     * @param sku 物料
     * @param secCode 库位
     * @param frozenQty 占用数量
     * @param refNo 业务单据号
     * @param channelCode 渠道
     * @param billType 交易类型
     * @return 所占用库存的库位
     */
    ServiceResult<String> frozeStockQty(String sku, String secCode, Integer frozenQty, String refNo,
                                        String channelCode, InventoryBusinessTypes billType);

    /**
     * 根据库存属性（批次）冻结库存
     * @param sku 物料编码
     * @param lesSecCode 中心仓库编码
     * @param property 属性（批次）
     * 40 样品
     * 22 开箱正品
     * 21 不良品
     * @param frozenQty 冻结数量
     * @param refNo 交易号
     * @param billType 交易类型
     * @return 所占用库存的库位
     */
    ServiceResult<String> frozeStockQtyByProperty(String sku, String lesSecCode, Integer property,
                                                  Integer frozenQty, String refNo,
                                                  InventoryBusinessTypes billType);

    /**
     * 释放冻结库存
     * @param sku 物料
     * @param releaseQty 释放数量
     * @param refNo 单据号
     * @param billType 交易类型
     * @return 释放结果
     */
    ServiceResult<Boolean> releaseFrozenStockQty(String sku, Integer releaseQty, String refNo,
                                                 InventoryBusinessTypes billType);

    /**
     * 根据物料和区域获取库存   -按批次    --不支持多层级
     * @param sku 物料
     * @param regionId 区域id
     * @param channelCode 渠道
     * @param requireQty 需求数量
     * @param isReliable 是否获取可靠库存
     * @param itemProperty 批次
     * @return 库存信息
     */
    ServiceResult<Stock> getStockBySkuAndRegion(String sku, int regionId, String channelCode,
                                                int requireQty, boolean isReliable,
                                                Integer itemProperty);

    /**
     * 获取城下的有库存物料列表  -按批次   不支持多层级
     * @param cityId 城市ID
     * @param channel 渠道
     * @param itemProperty 批次
     * @return 物料列表
     */
    ServiceResult<List<String>> getStockStatusByCity(int cityId, String channel,
                                                     Integer itemProperty);

    /**
     * 查询一段时间之后没有释放的库存
     * @param lockTime
     * @param topx
     * @return
     */
    ServiceResult<List<InvStockLock>> getNoReleaseByLockTime(String lockTime, Integer topx);

}
