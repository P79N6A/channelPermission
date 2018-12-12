package com.haier.afterSale.service;

import java.util.Date;
import java.util.List;

import com.haier.common.ServiceResult;
import com.haier.shop.model.BatchOrderLockVO;
import com.haier.shop.model.OrderLockResult;
import com.haier.shop.model.Stock;
import com.haier.stock.model.InvStoreLock;
import com.haier.stock.model.InvStoreRegions;
/*import com.haier.svc.bean.pop.BatchOrderLockVO;
import com.haier.svc.bean.pop.InvStoreLock;
import com.haier.svc.bean.pop.InvStoreRegions;
import com.haier.svc.bean.pop.InvStoreSynch;
import com.haier.svc.bean.pop.OrderLockResult;
import com.haier.svc.bean.pop.SgStore;
import com.haier.svc.bean.pop.Stock;*/
import com.haier.stock.model.InvStoreSynch;
import com.haier.stock.model.SgStore;

public interface EStoreService {

    /**
     * O2O-- 接收EStore库存
     * @param storeList
     * @return
     */
    ServiceResult<Boolean> acceptStoreStock(List<InvStoreSynch> storeList);

    /**
     * O2O-- EStore生成库存
     * @return
     */
    void syncByStoreCode();

    /**
     * O2O-- EStore占用库存，默认销售出库占用
     * @param sku 物料编码
     * @param storeCode 商店编码
     * @param qty 数量
     * @param refNo 网单号
     * @param source 来源
     * @return 占用成功返回当前占用的storeCode
     */
    ServiceResult<String> frozeByStoreCode(String sku, String storeCode, Integer qty, String refNo,
                                           String source);

    /**
     * O2O-- 释放占用库存，需指定详细信息才能释放
     * @param refNo 单号
     * @param sku 物料号
     * @param storeCode 商店编码 
     * @param releaseQty 数量
     * @return 释放成功返回 true，失败返回false
     */
    ServiceResult<Boolean> releaseByRefNo(String refNo, String sku, String storeCode,
                                          Integer releaseQty);

    /**
     * O2O-- 释放占用库存，需指定详细信息才能释放
     * @param refNo 单号
     * @param sku 物料号
     * @param storeCode 商店编码 
     * @param releaseQty 数量
     * @param calTotalStockFlag 是否扣减总库存数,false:不扣减 true:扣减
     * @return 释放成功返回 true，失败返回false
     */
    ServiceResult<Boolean> releaseByRefNo(String refNo, String sku, String storeCode,
                                          Integer releaseQty, boolean calTotalStockFlag);

    /**
     * O2O-- 查询区域下指定物料的库位情况，此接口专为分配库位
     * @param sku 物料
     * @param regionId 区域编码
     * @param channelCode 渠道 默认商城
     * @param requireQty 数量 不得小于1
     * @param itemProperty 默认10批次
     * @param multi 是否支持多层级（大家电）
     * 默认 多层级 ， 读库
     * @return
     */
    ServiceResult<Stock> getStockBySkuAndRegion(String sku, int regionId, String channelCode,
                                                int requireQty, boolean multi);

    /**
     * O2O-- 查询该地区级别下指定物料的库位情况，此接口专为分配库位
     * @param sku 物料
     * @param addressId 区县或街道编码 ，和addressLevel传参意义一致
     * @param channelCode 渠道 默认商城
     * @param requireQty 数量 不得小于1
     * @param itemProperty 默认10批次
     * @param multi 是否支持多层级（大家电）
     * @param addressLevel 地区级别  区县级=3，街道级=4
     * 默认 多层级 ， 读库
     * @return
     */
    ServiceResult<Stock> getStockBySkuAndRegionForLevel(String sku, int addressId,
                                                        String channelCode, int requireQty,
                                                        boolean multi, int addressLevel);

    /**
     * O2O-- 查询区县下指定物料库位情况， 默认多层级true
     * @param sku 物料号
     * @param regionId 区县编码 
     * @param channelCode 渠道编码
     * @param requireQty 数量
     * @return
     */
    ServiceResult<Stock> getStockBySkuAndRegion(String sku, int regionId, String channelCode,
                                                int requireQty);

    /**
     * O2O-- 查询该地区级别下指定物料库位情况， 默认多层级true
     * @param sku 物料号
     * @param addressId 区县或街道编码 ，和addressLevel传参意义一致
     * @param channelCode 渠道编码
     * @param requireQty 数量
     * @param addressLevel 地区级别   区县级=3，街道级=4
     * @return
     */
    ServiceResult<Stock> getStockBySkuAndRegionForLevel(String sku, int addressId,
                                                        String channelCode, int requireQty,
                                                        int addressLevel);

    /**
     * O2O-- 实时查询区县下指定物料库位情况， 默认多层级true
     * @param sku
     * @param regionId
     * @param channelCode
     * @param requireQty
     * @return
     */
    ServiceResult<Stock> getStockBySkuAndRegionTimely(String sku, int regionId, String channelCode,
                                                      int requireQty);

    /**
     * O2O--查询区县下库存
     * @param sku 物料
     * @param regionId 区县
     * @param channelCode 渠道，默认商城渠道
     * @param requireQty 数量 不得小于1
     * @param multi 是否采用多层级（大家电）
     * @param timely 实时查询
     * @return
     */
    ServiceResult<Stock> getStockBySkuAndRegionTimely(String sku, int regionId, String channelCode,
                                                      int requireQty, boolean multi);

    /**
     * O2O--查询指定地区级别下库存
     * @param sku 物料
     * @param addressId 区县或街道编码 ，和addressLevel传参意义一致
     * @param channelCode 渠道，默认商城渠道
     * @param requireQty 数量 不得小于1
     * @param multi 是否采用多层级（大家电）
     * @param timely 实时查询
     * @param addressLevel 地区级别   区县级=3，街道级=4
     * @return
     */
    ServiceResult<Stock> getStockBySkuAndRegionTimelyForLevel(String sku, int addressId,
                                                              String channelCode, int requireQty,
                                                              boolean multi, int addressLevel);

    /**
     * PC付款查询WA库存－根据物料和区域获取库存
     * 
     * @param sku 物料
     * @param regionId 区域ID
     * @param channelCode 渠道
     * @param requireQty 需求数量
     * @param multi 是否多层级
     * @param timely 是否可靠库存
     * @param lockFlag 下单锁标识 true:扣减下单锁 false:不扣减下单锁
     * @param cOrderSn 网单号，付款时判断是否本身有下单锁
     * @param selfLockFlag 扣减本身下单锁标识 true:不扣减本身锁定数量,操作为加 false:扣减本身锁定数量,不操作 
     * @return 库存信息
     */
    ServiceResult<Stock> getStockBySkuAndRegionTimelyForPay(String sku, int regionId,
                                                            String channelCode, int requireQty,
                                                            boolean multi, boolean timely,
                                                            boolean lockFlag, String cOrderSn,
                                                            boolean selfLockFlag);

    /**
     * PC付款查询WA库存－根据物料和区域（街道）获取库存
     * 
     * @param sku 物料
     * @param addressId 区县或街道编码 ，和addressLevel传参意义一致
     * @param channelCode 渠道
     * @param requireQty 需求数量
     * @param multi 是否多层级
     * @param timely 是否可靠库存
     * @param lockFlag 下单锁标识 true:扣减下单锁 false:不扣减下单锁
     * @param cOrderSn 网单号，付款时判断是否本身有下单锁
     * @param selfLockFlag 扣减本身下单锁标识 true:不扣减本身锁定数量,操作为加 false:扣减本身锁定数量,不操作 
     * @param addressLevel 地区级别   区县级=3，街道级=4
     * @return 库存信息
     */
    ServiceResult<Stock> getStockBySkuAndRegionTimelyForPayAndLevel(String sku, int addressId,
                                                                    String channelCode,
                                                                    int requireQty, boolean multi,
                                                                    boolean timely,
                                                                    boolean lockFlag,
                                                                    String cOrderSn,
                                                                    boolean selfLockFlag,
                                                                    int addressLevel);

    /**
     * O2O-- 查询城市下有库存物料编号
     * @param cityId
     * @param channel 渠道默认商城
     * @return
     */
    ServiceResult<List<String>> getSkusByCity(int cityId, String channel);

    /**
     * O2O-- 查询指定storeCode, sku的库存情况
     * @param sku 物料编码
     * @param storeCode 商店编码
     * @return
     */
    ServiceResult<Stock> getStockByStoreCode(String sku, String storeCode);

    /**
     * 根据区县ID查询店铺区域信息
     * @param regionId
     * @return
     */
    @Deprecated
    ServiceResult<List<InvStoreRegions>> getInvStoreRegionByRegionId(int regionId);

    /**
     * 根据RegionId查询库存（只支持SC渠道）
     * @param regionId
     * @return
     */
    ServiceResult<Stock> getStockByRegionId(Integer regionId, String sku, Integer qty,
                                            String channelCode);

    /**
     * 通过门店和物料号查询库存
     * @param sku
     * @param storeCode
     * @return
     */
    ServiceResult<Stock> getRealTimeStockFromEc(String sku, String storeCode);

    /**
     * PC付款查询EC库存－通过门店和物料号查询库存
     * @param sku
     * @param storeCode
     * @param lockFlag 下单锁标识 true:扣减下单锁 false:不扣减下单锁
     * @param cOrderSn 网单号，付款时判断是否本身有下单锁
     * @param selfLockFlag 扣减本身下单锁标识 true:不扣减本身锁定数量,操作为加 false:扣减本身锁定数量,不操作 
     * @return
     */
    ServiceResult<Stock> getRealTimeStockFromEcForPay(String sku, String storeCode,
                                                      boolean lockFlag, String cOrderSn,
                                                      boolean selfLockFlag);

    /**
     * PC付款查询EC库存
     * @param sku
     * @param storeId
     * @param lockFlag 下单锁标识 true:扣减下单锁 false:不扣减下单锁
     * @param cOrderSn 网单号，付款时判断是否本身有下单锁
     * @param selfLockFlag 扣减本身下单锁标识 true:不扣减本身锁定数量,操作为加 false:扣减本身锁定数量,不操作 
     * @return
     */
    ServiceResult<Stock> getNewRealTimeStockFromEcForPay(String sku, Integer storeId,
                                                         boolean lockFlag, String cOrderSn,
                                                         boolean selfLockFlag);

    /**
     * 由区县id查询店铺码
     * @param regionId 区县id
     * @return 店铺码
     */
    ServiceResult<List<String>> getStoreCodeByRegionId(Integer regionId);

    /**
     * TB卖EStore库存，由物料编码查询库存
     * @param sku
     * @return
     */
    ServiceResult<List<Stock>> getEStoreBySku(String sku);

    /**
     * TB卖EStore库存，由时间查询增量库存
     * @param startTime
     * @return
     */
    ServiceResult<List<Stock>> getEStoreIncrement(Date startTime);

    /**
     * TB卖EStore库存，由单一物料编码和多个店铺码查询库存
     * @param sku
     * @param storeCodeList
     * @return
     */
    ServiceResult<List<Stock>> getEStoreBySkuAndStoreCodes(String sku, List<String> storeCodeList);

    /**
     * TB卖EStore库存，由多个物料编码和单一店铺码查询库存
     * @param storeCode
     * @param skuList
     * @return
     */
    ServiceResult<List<Stock>> getEStoreByStoreCodeAndSkus(List<String> skuList, String storeCode);

    /**
     * 查询一段时间之后没有释放的库存
     * @param lockTime
     * @param topx
     * @return
     */
    ServiceResult<List<InvStoreLock>> getNoReleaseByLockTime(String lockTime, Integer topx);

    /**
     * 获得锁定库存数量
     * @param sku 物料编码
     * @param storeId 店铺ID
     * @param scode 库位
     * @param type 查询类型 1:WA 2:EC 3: 顺逛自由库存
     * @return
     */
    ServiceResult<Integer> orderLockByRefNo(String sku, String storeId, String scode, Integer type);

    /**
     * WA、EC－下单锁接口
     * @param sku 物料编码
     * @param regionId 区县Id
     * @param channelCode 渠道
     * @param lockQty 锁定数量
     * @param storeCode 店铺码
     * @param refNo 单据号(网单号)
     * @param source 订单来源
     * @param systemCode 系统类型 WA、EC、SG  
     * @param lockUser 锁定人
     * @return
     */
    ServiceResult<Boolean> createOrderLock(String sku, Integer regionId, String channelCode,
                                           Integer lockQty, String storeCode, String refNo,
                                           String source, Integer systemCode, String lockUser);

    /**
     * WA、EC－下单锁接口------批量
     * @param List<BatchOrderLockVO> list
     * regionId 区县Id
     * channelCode 渠道
     * lockQty 锁定数量
     * storeCode 店铺码
     * refNo 单据号(网单号)
     * source 订单来源
     * systemCode 系统类型 1:WA、2:EC、3:SG  
     * lockUser 锁定人
     * @return
     */
    ServiceResult<Boolean> batchCreateOrderLock(List<BatchOrderLockVO> list);

    /**
     * WA、EC－下单锁接口(根据地区级别)
     * @param sku 物料编码
     * @param addressId 区县或街道编码 ，和addressLevel传参意义一致
     * @param channelCode 渠道
     * @param lockQty 锁定数量
     * @param storeCode 店铺码
     * @param refNo 单据号(网单号)
     * @param source 订单来源
     * @param systemCode 系统类型 WA、EC、SG  
     * @param lockUser 锁定人
     * @param addressLevel 地区级别   区县级=3，街道级=4
     * @return
     */
    ServiceResult<Boolean> createOrderLockForLevel(String sku, Integer addressId,
                                                   String channelCode, Integer lockQty,
                                                   String storeCode, String refNo, String source,
                                                   Integer systemCode, String lockUser,
                                                   int addressLevel);

    /**
     * WA、EC－下单锁接口(根据地区级别)------批量插入
     * @param List<BatchOrderLockVO> list
     * regionId 区县Id
     * channelCode 渠道
     * lockQty 锁定数量
     * storeCode 店铺码
     * refNo 单据号(网单号)
     * source 订单来源
     * systemCode 系统类型 WA、EC、SG  
     * lockUser 锁定人
     * addressLevel 地区级别   区县级=3，街道级=4
     * @return
     */
    ServiceResult<List<OrderLockResult>> batchCreateOrderLockForLevel(List<BatchOrderLockVO> list);

    /**
     * 根据店铺ID获取店铺信息
     * @param storeId
     * @return
     */
    ServiceResult<SgStore> getSgStoreById(Integer storeId);

}
