package com.haier.stock.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.shop.model.BatchOrderLockVO;
import com.haier.shop.model.OrderLockResult;
import com.haier.stock.model.Stock;
import com.haier.stock.model.InvSgStock;
import com.haier.stock.model.InvSgStockLock;

/**
 * 库存查询接口
 * 
 * @Filename:
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 * 
 */
public interface StockCenterSgStoreService {

    /**
     * WA-- 查询街道下指定物料库位情况， 默认多层级true  顺逛单品页调用方法.
     * 
     * @param sku 物料号
     * @param streetId 街道编码
     * @param channelCode 渠道编码
     * @param requireQty 数量
     * @return
     */
    ServiceResult<Stock> getStockBySkuAndStreet(String sku, Integer streetId, String channelCode,
                                                Integer requireQty);

    /**
     * EC-- 查询指定storeCode, sku的库存情况  顺逛单品页调用方法.
     * 
     * @param sku 物料编码
     * @param storeCode 商店编码
     * @return
     */
    ServiceResult<Stock> getStockByStoreCode(String sku, String storeCode);

    /**
     * 顺逛自有库存查询接口  顺逛单品页调用方法.
     * 
     * @param sku 物料编码
     * @param streetId 街道ID
     * @param storeId 店铺ID
     * @param requireQty 请求数量
     * @return
     */
    ServiceResult<InvSgStock> getO2OStockBySkuAndStreet(String sku, Integer streetId,
                                                        String storeId, Integer requireQty);

    /**
     * 顺逛WA付款-- 查询街道下指定物料库位情况， 默认多层级true  顺逛单品页调用方法.
     * 
     * @param sku 物料号
     * @param streetId 街道编码
     * @param channelCode 渠道编码
     * @param requireQty 数量
     * @param multi 是否多层级
     * @param timely 是否可靠库存
     * @param lockFlag 下单锁标识 true:扣减下单锁 false:不扣减下单锁
     * @param cOrderSn 网单号，付款时判断是否本身有下单锁
     * @param selfLockFlag 扣减本身下单锁标识 true:不扣减本身锁定数量,操作为加 false:扣减本身锁定数量,不操作 
     * @return
     */
    ServiceResult<Stock> getStockBySkuAndStreetForPay(String sku, Integer streetId,
                                                      String channelCode, Integer requireQty,
                                                      boolean multi, boolean timely,
                                                      boolean lockFlag, String cOrderSn,
                                                      boolean selfLockFlag);

    /**
     * 顺逛EC付款-- 查询指定storeCode, sku的库存情况  顺逛单品页调用方法.
     * 
     * @param sku 物料编码
     * @param storeCode 商店编码
     * @param lockFlag 下单锁标识 true:扣减下单锁 false:不扣减下单锁
     * @param cOrderSn 网单号，付款时判断是否本身有下单锁
     * @param selfLockFlag 扣减本身下单锁标识 true:不扣减本身锁定数量,操作为加 false:扣减本身锁定数量,不操作
     * @return
     */
    ServiceResult<Stock> getStockByStoreCodeForPay(String sku, String storeCode, boolean lockFlag,
                                                   String cOrderSn, boolean selfLockFlag);

    /**
     * 顺逛O2O付款  顺逛单品页调用方法.
     * 
     * @param sku 物料编码
     * @param streetId 街道ID
     * @param storeId 店铺ID
     * @param requireQty 请求数量
     * @param lockFlag 下单锁标识 true:扣减下单锁 false:不扣减下单锁
     * @param cOrderSn 网单号，付款时判断是否本身有下单锁
     * @param selfLockFlag 扣减本身下单锁标识 true:不扣减本身锁定数量,操作为加 false:扣减本身锁定数量,不操作
     * @return
     */
    ServiceResult<InvSgStock> getO2OStockBySkuAndStreetForPay(String sku, Integer streetId,
                                                              String storeId, Integer requireQty,
                                                              boolean lockFlag, String cOrderSn,
                                                              boolean selfLockFlag);

    /**
     * 顺逛自有库存查询接口，占库获取库位调用，20161212修改，不减掉下单锁，计算总库存
     * 
     * @param sku 物料编码
     * @param streetId 街道ID
     * @param storeId 店铺ID
     * @param requireQty 请求数量
     * @param lockFlag 下单锁标识
     * @return
     */
    ServiceResult<InvSgStock> getO2OStockBySkuAndStreetWithOutLock(String sku, Integer streetId,
                                                                   String storeId,
                                                                   Integer requireQty,
                                                                   boolean lockFlag);

    /**
     * 顺逛自有库存入库接口
     * 
     * @param sku 物料编码
     * @param itemProperty 批次： SgConstants.ITEM_PROPERTY.W10 10:正品 默认先都传正品，以后扩展用。
     * @param storeId 店铺码
     * @param scode 库位码
     * @param changeQty 变动数量
     * @param userName 操作人
     * @return
     */
    ServiceResult<Boolean> inStock(String sku, String itemProperty, String storeId, String scode,
                                   Integer changeQty, String userName);

    /**
     * 顺逛自有库存占货接口
     * 
     * @param sku 物料编码
     * @param storeCode 店铺ID
     * @param sCode 库位码
     * @param lockQty 数量
     * @param refNo 关联单号
     * @param source 订单来源
     * @return
     */
    ServiceResult<String> frozeByStoreCode(InvSgStockLock invSgStockLock);

    /**
     * 库存退货释放接口
     * 
     * @param sku 物料编码
     * @param storeCode 店铺ID
     * @param releaseQty 占用数量
     * @param scode 单据号(网单号)
     * @return
     */
    ServiceResult<String> releaseForReturnByRefNo(InvSgStockLock invSgStockLock);

    /**
     * 下单锁库接口
     * 
     * @param sku 物料编码
     * @param storeCode 店铺ID
     * @param streetId 街道ID
     * @param scode 库位
     * @param refNo 单据号(网单号)
     * @param source 订单来源
     * @param systemCode 系统类型
     * @param lockQty 锁定数量
     * @param lockTime 锁定时间
     * @param lockUser 锁定人
     * @return
     * 
     */
    ServiceResult<Integer> orderLockByRefNo(String sku, String storeCode, Integer streetId,
                                            String scode, String refNo, String source,
                                            Integer systemCode, Integer lockQty, String lockUser,
                                            String channelCode);

    /**
     * 下单锁库释放
     * 
     * @return
     */
    ServiceResult<Integer> releaseOrderLockByrefNo();

    /**
     * 下单付款(占货)成功释放库存
     * 
     * @param refNo 单据号(网单号)
     * @return
     */
    ServiceResult<Integer> paymentSuccessReleaseOrderLock(String refNo);

    /**
     * 发货库存释放接口
     * 
     * @param sku 物料编码
     * @param storeCode 店表编码
     * @param releaseQty 占用数量
     * @param refNo 单据号(网单号)
     * @param scode 库位号
     * @return
     */
    ServiceResult<Integer> releaseByRefNo(String sku, String storeCode, Integer releaseQty,
                                          String refNo, String scode);

    /**
     * 通过顺逛自有店铺ID获取该店铺下所有sku与库存状态关系
     * @param storeId 店铺ID
     * @return
     */
    ServiceResult<Map<String, Boolean>> selfO2OStockByStoreId(Integer storeId);

    /**
     * 通过88码获取EC店铺下所有sku与库存状态关系
     * @param storeCode
     * @return
     */
    ServiceResult<Map<String, Boolean>> o2OStockByStoreId(String storeCode);

    /**
     * WA-- 查询街道下指定物料库位情况  顺逛WA占货定时任务调用
     * 
     * @param sku 物料号
     * @param streetId 街道编码
     * @param channelCode 渠道编码
     * @param requireQty 数量
     * @param multi 是否多层级
     * @return
     */
    ServiceResult<Stock> getStockBySkuAndStreet(String sku, Integer streetId, String channelCode,
                                                Integer requireQty, Boolean multi);

    /**
     * WA-- 查询街道下指定物料库位情况  顺逛WA占货定时任务调用
     * 
     * @param sku 物料号
     * @param streetId 街道编码
     * @param channelCode 渠道编码
     * @param requireQty 数量
     * @param multi 是否多层级
     * @param timely 是否可靠库存
     * @param lockFlag 下单锁标识
     * @return
     */
    ServiceResult<Stock> getStockBySkuAndStreetWithOutLock(String sku, Integer streetId,
                                                           String channelCode, Integer requireQty,
                                                           Boolean multi, Boolean timely,
                                                           boolean lockFlag);

    /**
     * WA-- 查询街道下指定物料库位情况   目前没系统调用。
     * 
     * @param sku 物料号
     * @param streetId 街道编码
     * @param channelCode 渠道编码
     * @param requireQty 数量
     * @param multi 是否多层级
     * @param timely 是否可靠库存
     * @return
     */
    ServiceResult<Stock> getStockBySkuAndStreet(String sku, Integer streetId, String channelCode,
                                                Integer requireQty, Boolean multi, Boolean timely);

    /**
     * 下单锁库接口---批量
     * @param List<BatchOrderLockVO> list
     * @param sku 物料编码
     * @param storeCode 店铺ID
     * @param streetId 街道ID
     * @param scode 库位
     * @param refNo 单据号(网单号)
     * @param source 订单来源
     * @param systemCode 系统类型
     * @param lockQty 锁定数量
     * @param lockTime 锁定时间
     * @param lockUser 锁定人
     * @return
     * 
     */
    ServiceResult<List<OrderLockResult>> batchOrderLockByRefNo(List<BatchOrderLockVO> list);

}
