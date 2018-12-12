package com.haier.order.services;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.order.model.EStoreStockModel;
import com.haier.order.model.EStoreSyncModel;
import com.haier.order.model.HopStockModel;
import com.haier.order.model.StockOrderLockModel;
import com.haier.shop.model.*;
import com.haier.stock.model.*;
import com.haier.stock.model.SgStore;
import com.haier.stock.service.SgStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderCenterEStoreServiceImpl {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(OrderCenterEStoreServiceImpl.class);
    @Autowired
    private EStoreStockModel eStoreStockModel;
    @Autowired
    private EStoreSyncModel eStoreSyncModel;
    @Autowired
    private HopStockModel hopStockModel;
    @Autowired
    private OrderCenterItemServiceImplNew itemServiceImplNew;
    @Autowired
    private StockOrderLockModel stockOrderLockModel;
    @Autowired
    private SgStoreService sgStoreDao;
//    @Autowired
//    private DataSourceTransactionManager   transactionManagerStock;

    
    public ServiceResult<Boolean> acceptStoreStock(List<InvStoreSynch> storeList) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            Integer totalCount = eStoreStockModel.acceptStoreStock(storeList);
            if (totalCount == null || totalCount == 0) {
                result.setResult(false);
                result.setMessage("没有库存数据添加变化");
            } else {
                result.setResult(true);
                result.setMessage("保存库存记录成功");
            }
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("保存库存记录异常");
            log.error("保存库存记录异常" + e);
        }
        return result;
    }

    
    public void syncByStoreCode() {
        List<InvStoreSynch> invStoreSynchList = eStoreStockModel.queryStoreSynch();
        for (InvStoreSynch invStoreSync : invStoreSynchList) {
            try {
                String message = eStoreSyncModel.storeStockSync(invStoreSync);
                log.info("同步EC库存：" + message);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("同步发生异常:" + e.getMessage());
            }
        }
    }

    
    public ServiceResult<String> frozeByStoreCode(String sku, String storeCode, Integer qty,
                                                  String refNo, String source) {
        InventoryBusinessTypes billType = InventoryBusinessTypes.FROZEN_BY_ZBCC;
        ServiceResult<String> result = new ServiceResult<String>();
        try {
            // 先同步EC库存，再占用库存
            eStoreSyncModel.realTimeGetStoreQty(storeCode, sku, InvSection.W10);
            // 占用库存
            result = eStoreStockModel.frozeStockQty(sku, storeCode, refNo, qty, billType, "sys",
                    source);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("占用门店库存失败");
            log.error("占用门店库存失败：" + e);
        }
        return result;
    }

    
    public ServiceResult<Boolean> releaseByRefNo(String refNo, String sku, String storeCode,
                                                 Integer releaseQty) {
        return releaseByRefNo(refNo, sku, storeCode, releaseQty, false);
    }

    
    public ServiceResult<Boolean> releaseByRefNo(String refNo, String sku, String storeCode,
                                                 Integer releaseQty, boolean calTotalStockFlag) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            // 先同步EC库存，再释放库存
            // 同步EC库存
            boolean tempFlag = eStoreSyncModel.realTimeGetStoreQty(storeCode, sku, InvSection.W10);
            // 释放库存
            eStoreStockModel.releaseFrozenQty(refNo, sku, storeCode, releaseQty,
                    (calTotalStockFlag && !tempFlag));
            result.setSuccess(true);
            result.setResult(true);
            result.setMessage("释放库存成功");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("释放库存失败：" + e.getMessage());
            log.error("释放库存失败[sku=" + sku + "，refNo=" + refNo + "，storeCode=" + storeCode
                    + "，releaseQty=" + releaseQty + "]，" + e.getMessage());
        }
        return result;
    }

    
    public ServiceResult<Stock> getStockBySkuAndRegion(String sku, int regionId, String channelCode,
                                                       int requireQty, boolean multi) {
        return this.getStockByRegion(sku, regionId, channelCode, requireQty, multi, false);
    }

    
    public ServiceResult<Stock> getStockBySkuAndRegionForLevel(String sku, int regionId,
                                                               String channelCode, int requireQty,
                                                               boolean multi, int addressLevel) {
        return this.getStockByRegionForLevel(sku, regionId, channelCode, requireQty, multi, false,
                addressLevel);
    }

    
    public ServiceResult<Stock> getStockBySkuAndRegionTimely(String sku, int regionId,
                                                             String channelCode, int requireQty,
                                                             boolean multi) {
        return this.getStockByRegion(sku, regionId, channelCode, requireQty, multi, true);
    }

    
    public ServiceResult<Stock> getStockBySkuAndRegionTimelyForLevel(String sku, int regionId,
                                                                     String channelCode,
                                                                     int requireQty, boolean multi,
                                                                     int addressLevel) {
        return this.getStockByRegionForLevel(sku, regionId, channelCode, requireQty, multi, true,
                addressLevel);
    }

    private ServiceResult<Stock> getStockByRegion(String sku, int regionId, String channelCode,
                                                  int requireQty, boolean multi, boolean timely) {
        log.info(
                "getStockByRegion[sku=" + sku + ",regionId=" + regionId + ",channelCode" + channelCode
                        + ",requireQty=" + requireQty + ",multi=" + multi + ",timely=" + timely + "]");
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        result.setSuccess(true);
        if (requireQty <= 0) {
            requireQty = 1;
        }

        Stock stock = null;
        try {
            ServiceResult<ProductBase> productResult = itemServiceImplNew.getPorductBaseBySku(sku);
            Integer productO2OType = null;
            if (productResult.getSuccess()) {
                ProductBase p = productResult.getResult();
                if (p == null) {
                    log.error("不存在物料编码为" + sku + "的商品");
                    result.setSuccess(false);
                    result.setResult(stock);
                    result.setMessage("不存在物料编码为" + sku + "的商品");
                    return result;
                }
                productO2OType = p.getProductO2OType();
                if (productO2OType == 0) {
                    productO2OType = ProductBase.PRODUCTO2OTYPE_1;
                }
            }
            stock = new Stock();
            stock.setAvaibleQty(0);
            if (productO2OType == null) {
                result.setResult(stock);
                return result;
            }

            //商城专供商品
            if (ProductBase.PRODUCTO2OTYPE_1 == productO2OType) {
                stock = hopStockModel.getStockBySkuAndRegion(sku, regionId, channelCode, requireQty,
                        multi, true);
                if (stock != null) {
                    stock.setStockType(BaseStock.STOCKTYPE_WA);
                }
                result.setResult(stock);
                return result;
                //O2O专供商品
            } else if (ProductBase.PRODCUTO2OTYPE_3 == productO2OType) {
                if (!InvStockChannel.CHANNEL_SHANGCHENG.equals(channelCode)
                        && !InvStockChannel.CHANNEL_RRS.equals(channelCode)) {
                    result.setResult(stock);
                    result.setSuccess(true);
                    return result;
                }
                InvStore invStore = eStoreStockModel.getBySkuAndStoreCode(sku, regionId, requireQty,
                        true, false);
                if (invStore != null) {
                    stock = new Stock();
                    stock.setSku(invStore.getSku());
                    stock.setStoreCode(invStore.getStoreCode());
                    stock.setAvaibleQty(invStore.getStockQty());
                    stock.setStockType(BaseStock.STOCKTYPE_EC);
                    stock.setUpdateTime(invStore.getUpdateTime());
                }
                result.setResult(stock);
                return result;
            } else {
                result.setResult(stock);
                return result;
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(stock);
            result.setMessage("根据区县查询库存失败：" + e.getMessage());
            log.error("根据指定区县查询库存失败：", e);
        }
        return result;
    }

    private ServiceResult<Stock> getStockByRegionForLevel(String sku, int regionId,
                                                          String channelCode, int requireQty,
                                                          boolean multi, boolean timely,
                                                          int addressLevel) {
        log.info("getStockByRegionForLevel[sku=" + sku + ",regionId=" + regionId + ",channelCode="
                + channelCode + ",requireQty=" + requireQty + ",multi=" + multi + ",timely="
                + timely + "]");
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        result.setSuccess(true);
        if (requireQty <= 0) {
            requireQty = 1;
        }

        Stock stock = null;
        try {
            ServiceResult<ProductBase> productResult = itemServiceImplNew.getPorductBaseBySku(sku);
            Integer productO2OType = null;
            if (productResult.getSuccess()) {
                ProductBase p = productResult.getResult();
                if (p == null) {
                    log.error("不存在物料编码为" + sku + "的商品");
                    result.setSuccess(false);
                    result.setResult(stock);
                    result.setMessage("不存在物料编码为" + sku + "的商品");
                    return result;
                }
                productO2OType = p.getProductO2OType();
                if (productO2OType == 0) {
                    productO2OType = ProductBase.PRODUCTO2OTYPE_1;
                }
            }
            stock = new Stock();
            stock.setAvaibleQty(0);
            if (productO2OType == null) {
                result.setResult(stock);
                return result;
            }

            //商城专供商品
            if (ProductBase.PRODUCTO2OTYPE_1 == productO2OType) {
                stock = hopStockModel.getStockBySkuAndRegion(sku, regionId, channelCode, requireQty,
                        multi, true, true, "", false, addressLevel);
                if (stock != null) {
                    stock.setStockType(BaseStock.STOCKTYPE_WA);
                }
                result.setResult(stock);
                return result;
                //O2O专供商品
            } else if (ProductBase.PRODCUTO2OTYPE_3 == productO2OType) {
                if (!InvStockChannel.CHANNEL_SHANGCHENG.equals(channelCode)
                        && !InvStockChannel.CHANNEL_RRS.equals(channelCode)) {
                    result.setResult(stock);
                    result.setSuccess(true);
                    return result;
                }
                InvStore invStore;
                if (addressLevel == 4) {//街道级   
                    invStore = eStoreStockModel.getBySkuAndStoreCodeForLevel(sku, regionId,
                            requireQty, true, false, true);
                } else {//区县级 
                    invStore = eStoreStockModel.getBySkuAndStoreCode(sku, regionId, requireQty,
                            true, false);
                }
                if (invStore != null) {
                    stock = new Stock();
                    stock.setSku(invStore.getSku());
                    stock.setStoreCode(invStore.getStoreCode());
                    stock.setAvaibleQty(invStore.getStockQty());
                    stock.setStockType(BaseStock.STOCKTYPE_EC);
                    stock.setUpdateTime(invStore.getUpdateTime());
                }
                result.setResult(stock);
                return result;
            } else {
                result.setResult(stock);
                return result;
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(stock);
            result.setMessage("根据区县查询库存失败：" + e.getMessage());
            log.error("根据指定区县查询库存失败：", e);
        }
        return result;
    }

    /**
     * 根据物料和区域获取库存，付款调用
     *
     * @param sku          物料
     * @param regionId     区域ID
     * @param channelCode  渠道
     * @param requireQty   需求数量
     * @param multi        是否多层级
     * @param timely       是否可靠库存
     * @param lockFlag     下单锁标识 true:扣减下单锁 false:不扣减下单锁
     * @param cOrderSn     网单号，付款时判断是否本身有下单锁
     * @param selfLockFlag 扣减本身下单锁标识 true:不扣减本身锁定数量,操作为加 false:扣减本身锁定数量,不操作
     * @return 库存信息
     */
    
    public ServiceResult<Stock> getStockBySkuAndRegionTimelyForPay(String sku, int regionId,
                                                                   String channelCode,
                                                                   int requireQty, boolean multi,
                                                                   boolean timely, boolean lockFlag,
                                                                   String cOrderSn,
                                                                   boolean selfLockFlag) {
        //        log.info("getStockBySkuAndRegionTimelyForPay[sku=" + sku + ",regionId=" + regionId
        //                 + ",channelCode" + channelCode + ",requireQty=" + requireQty + ",multi=" + multi
        //                 + ",timely=" + timely + "]");
        //        ServiceResult<Stock> result = new ServiceResult<Stock>();
        //        result.setSuccess(true);
        //        if (requireQty <= 0) {
        //            requireQty = 1;
        //        }
        //
        //        Stock stock = null;
        //        try {
        //            ServiceResult<ProductBase> productResult = itemService.getPorductBaseBySku(sku);
        //            Integer productO2OType = null;
        //            if (productResult.getSuccess()) {
        //                ProductBase p = productResult.getResult();
        //                if (p == null) {
        //                    log.error("不存在物料编码为" + sku + "的商品");
        //                    result.setSuccess(false);
        //                    result.setResult(stock);
        //                    result.setMessage("不存在物料编码为" + sku + "的商品");
        //                    return result;
        //                }
        //                productO2OType = p.getProductO2OType();
        //                if (productO2OType == 0) {
        //                    productO2OType = ProductBase.PRODUCTO2OTYPE_1;
        //                }
        //            }
        //            stock = new Stock();
        //            stock.setAvaibleQty(0);
        //            if (productO2OType == null) {
        //                result.setResult(stock);
        //                return result;
        //            }
        //
        //            //商城专供商品
        //            if (ProductBase.PRODUCTO2OTYPE_1 == productO2OType) {
        //                stock = hopStockModel.getStockBySkuAndRegion(sku, regionId, channelCode, requireQty,
        //                    multi, timely, lockFlag, cOrderSn, selfLockFlag);
        //                if (stock != null) {
        //                    stock.setStockType(BaseStock.STOCKTYPE_WA);
        //                }
        //                result.setResult(stock);
        //                return result;
        //                //O2O专供商品
        //            } else if (ProductBase.PRODCUTO2OTYPE_3 == productO2OType) {
        //                if (!InvStockChannel.CHANNEL_SHANGCHENG.equals(channelCode)
        //                    && !InvStockChannel.CHANNEL_RRS.equals(channelCode)) {
        //                    result.setResult(stock);
        //                    result.setSuccess(true);
        //                    return result;
        //                }
        //                InvStore invStore = eStoreStockModel.getBySkuAndStoreCode(sku, regionId, requireQty,
        //                    true, false);
        //                if (invStore != null) {
        //                    stock = new Stock();
        //                    stock.setSku(invStore.getSku());
        //                    stock.setStoreCode(invStore.getStoreCode());
        //                    stock.setAvaibleQty(invStore.getStockQty());
        //                    stock.setStockType(BaseStock.STOCKTYPE_EC);
        //                    stock.setUpdateTime(invStore.getUpdateTime());
        //                }
        //                result.setResult(stock);
        //                return result;
        //            } else {
        //                result.setResult(stock);
        //                return result;
        //            }
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //            result.setSuccess(false);
        //            result.setResult(stock);
        //            result.setMessage("PC付款查询WA库存失败：" + e.getMessage());
        //            log.error("PC付款查询WA库存失败：", e);
        //        }
        //        return result;
        return this.getStockBySkuAndRegionTimelyForPayAndLevel(sku, regionId, channelCode,
                requireQty, multi, timely, lockFlag, cOrderSn, selfLockFlag, 3);
    }

    /**
     * 根据物料和区域(街道)获取库存，付款调用
     *
     * @param sku          物料
     * @param regionId     区域ID
     * @param channelCode  渠道
     * @param requireQty   需求数量
     * @param multi        是否多层级
     * @param timely       是否可靠库存
     * @param lockFlag     下单锁标识 true:扣减下单锁 false:不扣减下单锁
     * @param cOrderSn     网单号，付款时判断是否本身有下单锁
     * @param selfLockFlag 扣减本身下单锁标识 true:不扣减本身锁定数量,操作为加 false:扣减本身锁定数量,不操作
     * @param addressLevel 地区级别   区县级=3，街道级=4
     * @return 库存信息
     */
    
    public ServiceResult<Stock> getStockBySkuAndRegionTimelyForPayAndLevel(String sku, int regionId,
                                                                           String channelCode,
                                                                           int requireQty,
                                                                           boolean multi,
                                                                           boolean timely,
                                                                           boolean lockFlag,
                                                                           String cOrderSn,
                                                                           boolean selfLockFlag,
                                                                           int addressLevel) {

        log.info("getStockBySkuAndRegionTimelyForPay[sku=" + sku + ",regionId=" + regionId
                + ",channelCode" + channelCode + ",requireQty=" + requireQty + ",multi=" + multi
                + ",timely=" + timely + ",addressLevel=" + addressLevel + "]");
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        result.setSuccess(true);
        if (requireQty <= 0) {
            requireQty = 1;
        }

        Stock stock = null;
        try {
            ServiceResult<ProductBase> productResult = itemServiceImplNew.getPorductBaseBySku(sku);
            Integer productO2OType = null;
            if (productResult.getSuccess()) {
                ProductBase p = productResult.getResult();
                if (p == null) {
                    log.error("不存在物料编码为" + sku + "的商品");
                    result.setSuccess(false);
                    result.setResult(stock);
                    result.setMessage("不存在物料编码为" + sku + "的商品");
                    return result;
                }
                productO2OType = p.getProductO2OType();
                if (productO2OType == 0) {
                    productO2OType = ProductBase.PRODUCTO2OTYPE_1;
                }
            }
            stock = new Stock();
            stock.setAvaibleQty(0);
            if (productO2OType == null) {
                result.setResult(stock);
                return result;
            }

            //商城专供商品
            if (ProductBase.PRODUCTO2OTYPE_1 == productO2OType) {
                if (addressLevel == 4) {//街道级
                    stock = hopStockModel.getStockBySkuAndRegion(sku, regionId, channelCode,
                            requireQty, multi, timely, lockFlag, cOrderSn, selfLockFlag, 4);
                } else {//区县级
                    stock = hopStockModel.getStockBySkuAndRegion(sku, regionId, channelCode,
                            requireQty, multi, timely, lockFlag, cOrderSn, selfLockFlag);
                }
                if (stock != null) {
                    stock.setStockType(BaseStock.STOCKTYPE_WA);
                }
                result.setResult(stock);
                return result;
                //O2O专供商品
            } else if (ProductBase.PRODCUTO2OTYPE_3 == productO2OType) {
                if (!InvStockChannel.CHANNEL_SHANGCHENG.equals(channelCode)
                        && !InvStockChannel.CHANNEL_RRS.equals(channelCode)) {
                    result.setResult(stock);
                    result.setSuccess(true);
                    return result;
                }
                InvStore invStore;
                if (addressLevel == 4) {//街道级   
                    invStore = eStoreStockModel.getBySkuAndStoreCodeForLevel(sku, regionId,
                            requireQty, true, false, true);
                } else {//区县级 
                    invStore = eStoreStockModel.getBySkuAndStoreCode(sku, regionId, requireQty,
                            true, false);
                }
                if (invStore != null) {
                    stock = new Stock();
                    stock.setSku(invStore.getSku());
                    stock.setStoreCode(invStore.getStoreCode());
                    stock.setAvaibleQty(invStore.getStockQty());
                    stock.setStockType(BaseStock.STOCKTYPE_EC);
                    stock.setUpdateTime(invStore.getUpdateTime());
                }
                result.setResult(stock);
                return result;
            } else {
                result.setResult(stock);
                return result;
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(stock);
            result.setMessage("PC付款查询WA库存失败：" + e.getMessage());
            log.error("PC付款查询WA库存失败：", e);
        }
        return result;
    }

    
    public ServiceResult<Stock> getStockByRegionId(Integer regionId, String sku, Integer requireQty,
                                                   String channelCode) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        if (!InvStockChannel.CHANNEL_SHANGCHENG.equalsIgnoreCase(channelCode)) {
            result.setResult(null);
            result.setSuccess(false);
            return result;
        }
        Stock stock = null;
        try {

            InvStore invStore = eStoreStockModel.getBySkuAndStoreCode(sku, regionId, requireQty,
                    true, true);
            if (invStore != null) {
                if (stock == null || invStore.getStockQty() >= requireQty) {
                    stock = new Stock();
                    stock.setSku(invStore.getSku());
                    stock.setStoreCode(invStore.getStoreCode());
                    stock.setAvaibleQty(invStore.getStockQty());
                    stock.setStockType(BaseStock.STOCKTYPE_EC);
                    stock.setUpdateTime(invStore.getUpdateTime());
                    log.info("根据区县查询EC库存成功 sku==" + invStore.getSku() + ", storeCode=="
                            + invStore.getStoreCode() + ", stockQty==" + invStore.getStockQty()
                            + ", stockType==" + invStore.getStockType() + ", updateTime=="
                            + invStore.getUpdateTime());
                }
            }
            result.setSuccess(true);
            result.setResult(stock);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("根据区县查询库存异常--专用EC库存");
            log.error("根据区县查询EC库存失败，regionId==" + regionId + ", sku==" + sku + ",channelCode=="
                    + channelCode + ",requireQty==" + requireQty);
        }
        return result;
    }

    
    public ServiceResult<List<String>> getSkusByCity(int cityId, String channel) {
        ServiceResult<List<String>> result = new ServiceResult<List<String>>();
        try {
            List<String> youSkuList = hopStockModel.getStockStatusByCity(cityId, channel);
            if (!InvStockChannel.CHANNEL_SHANGCHENG.equals(channel)) {
                result.setResult(youSkuList);
                result.setSuccess(true);
                return result;
            }
            if (youSkuList == null || youSkuList.size() <= 0) {
                youSkuList = eStoreStockModel.getStoreSkuByCityId(cityId);
            } else {
                List<String> youECSkuList = eStoreStockModel.getStoreSkuByCityId(cityId);
                if (youECSkuList == null || youECSkuList.size() <= 0) {
                    result.setResult(youSkuList);
                    result.setSuccess(true);
                    return result;
                }
                for (String sku : youECSkuList) {
                    if (youSkuList.contains(sku)) {
                        continue;
                    }
                    youSkuList.add(sku);
                }
            }
            // log.info("有货物料返回：" + youSkuList);
            result.setResult(youSkuList);
            result.setSuccess(true);
            result.setMessage("查询可用物料成功");
        } catch (Exception e) {
            result.setResult(null);
            result.setSuccess(false);
            result.setMessage("查询可用物料异常");
            log.error("查询可用物料异常" + e.getMessage());
        }
        return result;
    }

    
    public ServiceResult<Stock> getStockByStoreCode(String sku, String storeCode) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        if (StringUtil.isEmpty(sku) || StringUtil.isEmpty(storeCode)) {
            result.setSuccess(false);
            result.setResult(null);
            return result;
        }
        try {
            Stock stock = eStoreStockModel.getBySkuAndStoreCode(sku, storeCode);
            result.setResult(stock);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("按照门店编码查询EC库存失败");
            log.error("按照门店编码查询EC库存失败" + e.getMessage());
        }
        return result;
    }

    private ServiceResult<Stock> getStockByStoreCodeFromEcForPay(String sku, String storeCode,
                                                                 boolean lockFlag, String cOrderSn,
                                                                 boolean selfLockFlag) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        if (StringUtil.isEmpty(sku) || StringUtil.isEmpty(storeCode)) {
            result.setSuccess(false);
            result.setResult(null);
            return result;
        }
        try {
            Stock stock = eStoreStockModel.getBySkuAndStoreCode(sku, storeCode, lockFlag, cOrderSn,
                    selfLockFlag);
            result.setResult(stock);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("按照门店编码查询EC库存失败");
            log.error("按照门店编码查询EC库存失败" + e.getMessage());
        }
        return result;
    }

    
    public ServiceResult<Stock> getRealTimeStockFromEc(String sku, String storeCode) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        if (StringUtil.isEmpty(sku) || StringUtil.isEmpty(storeCode)) {
            result.setSuccess(false);
            result.setResult(null);
            return result;
        }
        try {
            eStoreSyncModel.realTimeGetStoreQty(storeCode, sku, InvSection.W10);
            return getStockByStoreCode(sku, storeCode);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("实时查询EC库存失败");
            log.error("实时查询EC库存失败" + e.getMessage());
        }
        return result;
    }

    
    public ServiceResult<Stock> getRealTimeStockFromEcForPay(String sku, String storeCode,
                                                             boolean lockFlag, String cOrderSn,
                                                             boolean selfLockFlag) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        if (StringUtil.isEmpty(sku) || StringUtil.isEmpty(storeCode)) {
            result.setSuccess(false);
            result.setResult(null);
            return result;
        }
        try {
            eStoreSyncModel.realTimeGetStoreQty(storeCode, sku, InvSection.W10);
            return getStockByStoreCodeFromEcForPay(sku, storeCode, lockFlag, cOrderSn,
                    selfLockFlag);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("PC付款查询EC库存失败");
            log.error("根据店铺码,PC付款查询EC库存失败,storeCode=" + storeCode + ",sku=" + sku + ",cOrderSn="
                            + cOrderSn,
                    e);
        }
        return result;
    }

    
    public ServiceResult<Stock> getNewRealTimeStockFromEcForPay(String sku, Integer storeId,
                                                                boolean lockFlag, String cOrderSn,
                                                                boolean selfLockFlag) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        if (StringUtil.isEmpty(sku) || storeId == null || StringUtil.isEmpty(cOrderSn)) {
            result.setSuccess(false);
            result.setResult(null);
            return result;
        }
        try {
            SgStore sgStore = sgStoreDao.getSgStoreById(storeId);
            if (sgStore == null || StringUtil.isEmpty(sgStore.getStoreCode())) {
                result.setSuccess(false);
                result.setMessage(storeId + "查询不到对应店铺信息");
                result.setResult(null);
                return result;
            }
            result = getRealTimeStockFromEcForPay(sku, sgStore.getStoreCode(), lockFlag, cOrderSn,
                    selfLockFlag);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("PC付款查询EC库存失败");
            log.error(
                    "根据店铺ID,PC付款查询EC库存失败,storeId=" + storeId + ",sku=" + sku + ",cOrderSn=" + cOrderSn,
                    e);
        }
        return result;
    }

    
    public ServiceResult<Stock> getStockBySkuAndRegionTimely(String sku, int regionId,
                                                             String channelCode, int requireQty) {
        return this.getStockBySkuAndRegionTimely(sku, regionId, channelCode, requireQty, true);
    }

    
    public ServiceResult<Stock> getStockBySkuAndRegion(String sku, int regionId, String channelCode,
                                                       int requireQty) {
        return this.getStockBySkuAndRegion(sku, regionId, channelCode, requireQty, true);
    }

    
    public ServiceResult<Stock> getStockBySkuAndRegionForLevel(String sku, int regionId,
                                                               String channelCode, int requireQty,
                                                               int addressLevel) {
        return this.getStockBySkuAndRegionForLevel(sku, regionId, channelCode, requireQty, true,
                addressLevel);
    }

    
    public ServiceResult<List<InvStoreRegions>> getInvStoreRegionByRegionId(int regionId) {
        ServiceResult<List<InvStoreRegions>> result = new ServiceResult<List<InvStoreRegions>>();
        try {
            result.setResult(
                    eStoreStockModel.getInvStoreRegionByRegionId(regionId, InvStoreRegions.STATUS_ON));
        } catch (Exception e) {
            log.error("根据区县ID(" + regionId + ")获取店铺区域信息时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    
    public ServiceResult<List<String>> getStoreCodeByRegionId(Integer regionId) {
        ServiceResult<List<String>> result = new ServiceResult<List<String>>();
        try {
            List<String> storeCodeList = eStoreStockModel.getStoreCodeByRegionId(regionId, false);
            log.info("根据区县查询店铺编码okokok!!--" + regionId + ",---storeCodeList:" + storeCodeList);
            result.setResult(storeCodeList);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("由区县ID[" + regionId + "]查询店铺码发生异常：", e);
            result.setResult(null);
            result.setSuccess(false);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
        }
        return result;
    }

    
    public ServiceResult<List<Stock>> getEStoreBySku(String sku) {

        ServiceResult<List<Stock>> result = new ServiceResult<List<Stock>>();
        try {
            if (StringUtil.isEmpty(sku, true)) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("物料编码sku无效");
                return result;
            }
            result.setResult(eStoreStockModel.getEStoreBySkuAndStoreCodeList(sku, null));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("getEStoreBySku:查询可用库存(" + "sku:" + sku + "，出现异常：" + e.getMessage());
            log.error("查询可用库存(" + "sku:" + sku, e);
        }
        return result;

    }

    
    public ServiceResult<List<Stock>> getEStoreIncrement(Date startTime) {

        ServiceResult<List<Stock>> result = new ServiceResult<List<Stock>>();
        try {
            if (startTime == null) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("startTime无效");
                return result;
            }
            result.setResult(eStoreStockModel.getEStoreIncrement(startTime));
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("增量获取变化的库存列表错误：", e);
            result.setSuccess(false);
            result.setMessage("增量获取变化的库存列表错误" + e.getMessage());
        }
        return result;
    }

    
    public ServiceResult<List<Stock>> getEStoreBySkuAndStoreCodes(String sku,
                                                                  List<String> storeCodeList) {

        ServiceResult<List<Stock>> result = new ServiceResult<List<Stock>>();
        try {
            if (StringUtil.isEmpty(sku, true)) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("物料编码sku无效");
                return result;
            }
            if (storeCodeList == null || storeCodeList.size() <= 0) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("店铺编码无效");
                return result;
            }
            result.setResult(eStoreStockModel.getEStoreBySkuAndStoreCodeList(sku, storeCodeList));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result
                    .setMessage("getEStoreBySkuAndStoreCodes:查询可用库存(" + "sku:" + sku + ",storeCodeList:"
                            + storeCodeList.toString() + "，出现异常：" + e.getMessage());
            log.error("查询可用库存(" + "sku:" + sku + ",storeCodeList:" + storeCodeList.toString(), e);
        }
        return result;

    }

    
    public ServiceResult<List<Stock>> getEStoreByStoreCodeAndSkus(List<String> skuList,
                                                                  String storeCode) {
        ServiceResult<List<Stock>> result = new ServiceResult<List<Stock>>();
        try {
            if (StringUtil.isEmpty(storeCode, true)) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("店铺编码无效");
                return result;
            }
            if (skuList == null || skuList.size() <= 0) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("物料编码无效");
                return result;
            }
            result.setResult(eStoreStockModel.getEStoreBySkuListAndStoreCode(skuList, storeCode));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("getEStoreByStoreCodeAndSkus:查询可用库存(" + "skuList:" + skuList
                    + ",storeCode:" + storeCode + "，出现异常：" + e.getMessage());
            log.error("查询可用库存(" + "skuList:" + skuList.toString() + ",storeCode:" + storeCode, e);
        }
        return result;

    }

    
    public ServiceResult<List<InvStoreLock>> getNoReleaseByLockTime(String lockTime, Integer topx) {
        ServiceResult<List<InvStoreLock>> result = new ServiceResult<List<InvStoreLock>>();
        try {
            if (StringUtil.isEmpty(lockTime, true)) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("库存锁定时间不能为空");
                return result;
            }
            if (topx == null) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("查询行数不能为空");
                return result;
            }
            result.setResult(eStoreStockModel.getNoReleaseByLockTime(lockTime, topx));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("getNoReleaseByLockTime:查询一段时间之后没有释放的库存[EC库存]出现异常");
            log.error("查询一段时间之后没有释放的库存[EC库存]出现异常:", e);
        }
        return result;

    }

    /**
     * 获得锁定库存数量
     *
     * @param sku     物料编码
     * @param storeId 店铺ID
     * @param scode   库位
     * @param type    查询类型 1:WA 2:EC 3: 顺逛自由库存
     * @return
     */
    
    public ServiceResult<Integer> orderLockByRefNo(String sku, String storeId, String scode,
                                                   Integer type) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            Integer qty = eStoreStockModel.orderLockByRefNo(sku, storeId, scode, type, null);
            result.setResult(qty);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("获得锁定库存数量出现异常");
            log.error("获得锁定库存数量出现异常:", e);
        }
        return result;
    }

    /**
     * WA、EC－下单锁接口
     *
     * @param sku         物料编码
     * @param regionId    区县Id
     * @param channelCode 渠道
     * @param lockQty     锁定数量
     * @param storeCode   店铺码
     * @param refNo       单据号(网单号)
     * @param source      订单来源
     * @param systemCode  系统类型 1:WA、2:EC、3:SG
     * @param lockUser    锁定人
     * @return
     */
    
    public ServiceResult<Boolean> createOrderLock(String sku, Integer regionId, String channelCode,
                                                  Integer lockQty, String storeCode, String refNo,
                                                  String source, Integer systemCode,
                                                  String lockUser) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            if (systemCode == null) {
                result.setSuccess(false);
                result.setMessage("系统来源不能为空");
                return result;
            }
            if (lockQty == null || lockQty <= 0) {
                result.setSuccess(false);
                result.setMessage("锁定数量必须大于0");
                return result;
            }
            String sCode = null;
            if (systemCode.intValue() == SgConstants.STOCK_LOCK_TYPE.EC.intValue()) {
                if (StringUtils.isEmpty(sku)) {
                    result.setSuccess(false);
                    result.setMessage("物料不能为空");
                    return result;
                }
                if (StringUtils.isEmpty(storeCode)) {
                    result.setSuccess(false);
                    result.setMessage("店铺码不能为空");
                    return result;
                }
                //查询库存是否满足
                ServiceResult<Stock> ecResult = getStockByStoreCodeFromEcForPay(sku, storeCode,
                        true, "", false);
                if (ecResult.getResult().getAvaibleQty() < lockQty) {
                    result.setSuccess(false);
                    result.setMessage("库存不足");
                    return result;
                }
                //                sCode = ecResult.getResult().getSecCode();
            } else if (systemCode.equals(SgConstants.STOCK_LOCK_TYPE.WA)) {
                if (StringUtils.isEmpty(sku)) {
                    result.setSuccess(false);
                    result.setMessage("物料不能为空");
                    return result;
                }
                if (StringUtils.isEmpty(regionId.toString())) {
                    result.setSuccess(false);
                    result.setMessage("区县ID不能为空");
                    return result;
                }
                if (StringUtils.isEmpty(channelCode)) {
                    result.setSuccess(false);
                    result.setMessage("渠道不能为空");
                    return result;
                }
                //查询库存是否满足
                ServiceResult<Stock> waResult = getStockBySkuAndRegion(sku, regionId, channelCode,
                        lockQty);
                if (waResult == null || waResult.getResult() == null
                        || waResult.getResult().getAvaibleQty() < lockQty) {
                    result.setSuccess(false);
                    result.setMessage("库存不足");
                    return result;
                }
                sCode = waResult.getResult().getSecCode();
            } else {
                result.setSuccess(false);
                result.setMessage("系统来源类型不正确");
                return result;
            }
            //插入数据
            stockOrderLockModel.insertStockOrderLock(sku, storeCode, sCode, refNo, source, lockQty,
                    lockUser);
            result.setSuccess(true);
            result.setResult(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("WA、EC下单锁出现异常");
            log.error("[createOrderLock]WA、EC下单锁出现异常:", e);
        }
        return result;
    }

    /**
     * WA、EC－下单锁接口------批量
     *
     * @param List<BatchOrderLockVO> list
     *                               regionId 区县Id
     *                               channelCode 渠道
     *                               lockQty 锁定数量
     *                               storeCode 店铺码
     *                               refNo 单据号(网单号)
     *                               source 订单来源
     *                               systemCode 系统类型 1:WA、2:EC、3:SG
     *                               lockUser 锁定人
     * @return
     */
    
    public ServiceResult<Boolean> batchCreateOrderLock(List<BatchOrderLockVO> list) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        if (list == null || list.isEmpty()) {
            result.setSuccess(false);
            result.setMessage("传输VO数据为空");
            return result;
        }
        for (BatchOrderLockVO batchOrderLockVO : list) {
            try {
                String sku = batchOrderLockVO.getSku();
                Integer regionId = batchOrderLockVO.getRegionId();
                String channelCode = batchOrderLockVO.getChannelCode();
                Integer lockQty = batchOrderLockVO.getLockQty();
                String storeCode = batchOrderLockVO.getStoreCode();
                Integer systemCode = batchOrderLockVO.getSystemCode();
                if (systemCode == null) {
                    result.setSuccess(false);
                    result.setMessage("系统来源不能为空");
                    return result;
                }
                if (lockQty == null || lockQty <= 0) {
                    result.setSuccess(false);
                    result.setMessage("锁定数量必须大于0");
                    return result;
                }
                String sCode = null;
                if (systemCode.intValue() == SgConstants.STOCK_LOCK_TYPE.EC.intValue()) {
                    if (StringUtils.isEmpty(sku)) {
                        result.setSuccess(false);
                        result.setMessage("物料不能为空");
                        return result;
                    }
                    if (StringUtils.isEmpty(storeCode)) {
                        result.setSuccess(false);
                        result.setMessage("店铺码不能为空");
                        return result;
                    }
                    //查询库存是否满足
                    ServiceResult<Stock> ecResult = getStockByStoreCodeFromEcForPay(sku, storeCode,
                            true, "", false);
                    if (ecResult.getResult().getAvaibleQty() < lockQty) {
                        result.setSuccess(false);
                        result.setMessage("库存不足");
                        return result;
                    }
                    //                sCode = ecResult.getResult().getSecCode();
                } else if (systemCode.equals(SgConstants.STOCK_LOCK_TYPE.WA)) {
                    if (StringUtils.isEmpty(sku)) {
                        result.setSuccess(false);
                        result.setMessage("物料不能为空");
                        return result;
                    }
                    if (StringUtils.isEmpty(regionId.toString())) {
                        result.setSuccess(false);
                        result.setMessage("区县ID不能为空");
                        return result;
                    }
                    if (StringUtils.isEmpty(channelCode)) {
                        result.setSuccess(false);
                        result.setMessage("渠道不能为空");
                        return result;
                    }
                    //查询库存是否满足
                    ServiceResult<Stock> waResult = getStockBySkuAndRegion(sku, regionId,
                            channelCode, lockQty);
                    if (waResult == null || waResult.getResult() == null
                            || waResult.getResult().getAvaibleQty() < lockQty) {
                        result.setSuccess(false);
                        result.setMessage("库存不足");
                        return result;
                    }
                    sCode = waResult.getResult().getSecCode();
                } else {
                    result.setSuccess(false);
                    result.setMessage("系统来源类型不正确");
                    return result;
                }
                batchOrderLockVO.setsCode(sCode);
            } catch (Exception e) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("WA、EC下单锁出现异常");
                log.error("[batchCreateOrderLock]批量验证WA、EC下单锁出现异常:", e);
                return result;
            }
        }
        //开启事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            for (BatchOrderLockVO batchOrderLockVO : list) {
                //插入数据
                stockOrderLockModel.insertStockOrderLock(batchOrderLockVO.getSku(),
                        batchOrderLockVO.getStoreCode(), batchOrderLockVO.getsCode(),
                        batchOrderLockVO.getRefNo(), batchOrderLockVO.getSource(),
                        batchOrderLockVO.getLockQty(), batchOrderLockVO.getLockUser());
            }
//            transactionManagerStock.commit(status);
        } catch (Exception e) {
//            transactionManagerStock.rollback(status);
            result.setSuccess(false);
            result.setResult(null);
            log.error("[batchCreateOrderLock]批量插入WA、EC下单锁出现异常:", e);
            return result;
        }
        result.setSuccess(true);
        result.setResult(true);
        return result;
    }

    /**
     * WA、EC－下单锁接口(根据地区级别)
     *
     * @param sku          物料编码
     * @param regionId     区县Id
     * @param channelCode  渠道
     * @param lockQty      锁定数量
     * @param storeCode    店铺码
     * @param refNo        单据号(网单号)
     * @param source       订单来源
     * @param systemCode   系统类型 WA、EC、SG
     * @param lockUser     锁定人
     * @param addressLevel 地区级别   区县级=3，街道级=4
     * @return
     */
    
    public ServiceResult<Boolean> createOrderLockForLevel(String sku, Integer regionId,
                                                          String channelCode, Integer lockQty,
                                                          String storeCode, String refNo,
                                                          String source, Integer systemCode,
                                                          String lockUser, int addressLevel) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            if (systemCode == null) {
                result.setSuccess(false);
                result.setMessage("系统来源不能为空");
                return result;
            }
            if (lockQty == null || lockQty.intValue() <= 0) {
                result.setSuccess(false);
                result.setMessage("锁定数量必须大于0");
                return result;
            }
            String sCode = null;
            if (systemCode.intValue() == SgConstants.STOCK_LOCK_TYPE.EC.intValue()) {
                if (StringUtils.isEmpty(sku)) {
                    result.setSuccess(false);
                    result.setMessage("物料不能为空");
                    return result;
                }
                if (StringUtils.isEmpty(storeCode)) {
                    result.setSuccess(false);
                    result.setMessage("店铺码不能为空");
                    return result;
                }
                //查询库存是否满足
                ServiceResult<Stock> ecResult = getStockByStoreCodeFromEcForPay(sku, storeCode,
                        true, "", false);
                if (ecResult.getResult().getAvaibleQty() < lockQty) {
                    result.setSuccess(false);
                    result.setMessage("库存不足");
                    return result;
                }
                //                sCode = ecResult.getResult().getSecCode();
            } else if (systemCode.equals(SgConstants.STOCK_LOCK_TYPE.WA)) {
                if (StringUtils.isEmpty(sku)) {
                    result.setSuccess(false);
                    result.setMessage("物料不能为空");
                    return result;
                }
                if (StringUtils.isEmpty(regionId.toString())) {
                    result.setSuccess(false);
                    result.setMessage(addressLevel == 4 ? "街道ID不能为空" : "区县ID不能为空");
                    return result;
                }
                if (StringUtils.isEmpty(channelCode)) {
                    result.setSuccess(false);
                    result.setMessage("渠道不能为空");
                    return result;
                }
                //查询库存是否满足
                ServiceResult<Stock> waResult;
                if (addressLevel == 4) {
                    waResult = getStockBySkuAndRegionForLevel(sku, regionId, channelCode, lockQty,
                            addressLevel);
                } else {
                    waResult = getStockBySkuAndRegion(sku, regionId, channelCode, lockQty);
                }
                if (waResult == null || waResult.getResult() == null
                        || waResult.getResult().getAvaibleQty() < lockQty) {
                    result.setSuccess(false);
                    result.setMessage("库存不足");
                    return result;
                }
                sCode = waResult.getResult().getSecCode();
            } else {
                result.setSuccess(false);
                result.setMessage("系统来源类型不正确");
                return result;
            }
            //插入数据
            stockOrderLockModel.insertStockOrderLock(sku, storeCode, sCode, refNo, source, lockQty,
                    lockUser);
            result.setSuccess(true);
            result.setResult(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("WA、EC下单锁(根据地区级别)出现异常");
            log.error("[createOrderLockForLevel]WA、EC下单锁(根据地区级别)出现异常:", e);
        }
        return result;
    }

    /**
     * WA、EC－下单锁接口(根据地区级别)------批量插入
     *
     * @param List<BatchOrderLockVO> list
     *                               regionId 区县Id
     *                               channelCode 渠道
     *                               lockQty 锁定数量
     *                               storeCode 店铺码
     *                               refNo 单据号(网单号)
     *                               source 订单来源
     *                               systemCode 系统类型 WA、EC、SG
     *                               lockUser 锁定人
     *                               addressLevel 地区级别   区县级=3，街道级=4
     * @return 错误信息集合
     */
    
    public ServiceResult<List<OrderLockResult>> batchCreateOrderLockForLevel(List<BatchOrderLockVO> list) {
        ServiceResult<List<OrderLockResult>> result = new ServiceResult<List<OrderLockResult>>();
        if (list == null || list.isEmpty()) {
            result.setSuccess(false);
            result.setMessage("传输VO数据为空");
            return result;
        }
        List<OrderLockResult> resultList = new ArrayList<OrderLockResult>();

        for (BatchOrderLockVO batchOrderLockVO : list) {
            try {
                String sku = batchOrderLockVO.getSku();
                String productName = batchOrderLockVO.getProductName();
                Integer regionId = batchOrderLockVO.getRegionId();
                String channelCode = batchOrderLockVO.getChannelCode();
                Integer lockQty = batchOrderLockVO.getLockQty();
                String storeCode = batchOrderLockVO.getStoreCode();
                Integer systemCode = batchOrderLockVO.getSystemCode();
                if (batchOrderLockVO.getAddressLevel() == null) {
                    result.setSuccess(false);
                    OrderLockResult rs = new OrderLockResult(sku, productName, true, "地区级别不能为空");
                    resultList.add(rs);
                    continue;
                }
                if (systemCode == null) {
                    result.setSuccess(false);
                    OrderLockResult rs = new OrderLockResult(sku, productName, true, "系统来源不能为空");
                    resultList.add(rs);
                    continue;
                }
                if (lockQty == null || lockQty.intValue() <= 0) {
                    result.setSuccess(false);
                    OrderLockResult rs = new OrderLockResult(sku, productName, true, "锁定数量必须大于0");
                    resultList.add(rs);
                    continue;
                }
                int addressLevel = batchOrderLockVO.getAddressLevel();
                String sCode = null;
                if (systemCode.intValue() == SgConstants.STOCK_LOCK_TYPE.EC.intValue()) {
                    boolean tempFlag = false;
                    if (StringUtils.isEmpty(sku)) {
                        result.setSuccess(false);
                        OrderLockResult rs = new OrderLockResult(sku, productName, true, "物料不能为空");
                        resultList.add(rs);
                        tempFlag = true;
                    }
                    if (StringUtils.isEmpty(storeCode)) {
                        result.setSuccess(false);
                        OrderLockResult rs = new OrderLockResult(sku, productName, true, "店铺码不能为空");
                        resultList.add(rs);
                        tempFlag = true;
                    }
                    if (!tempFlag) {
                        //查询库存是否满足
                        ServiceResult<Stock> ecResult = getStockByStoreCodeFromEcForPay(sku,
                                storeCode, true, "", false);
                        if (ecResult == null || ecResult.getResult() == null
                                || ecResult.getResult().getAvaibleQty() < lockQty) {
                            result.setError("400", "库存不足");
                            OrderLockResult rs = new OrderLockResult(sku, productName, false,
                                    "库存不足");
                            resultList.add(rs);
                        }
                        //                sCode = ecResult.getResult().getSecCode();
                    }
                } else if (systemCode.equals(SgConstants.STOCK_LOCK_TYPE.WA)) {
                    boolean tempFlag = false;
                    if (StringUtils.isEmpty(sku)) {
                        result.setSuccess(false);
                        OrderLockResult rs = new OrderLockResult(sku, productName, true, "物料不能为空");
                        resultList.add(rs);
                        tempFlag = true;
                    }
                    if (StringUtils.isEmpty(regionId.toString())) {
                        result.setSuccess(false);
                        OrderLockResult rs = new OrderLockResult(sku, productName, true,
                                (addressLevel == 4 ? "街道ID不能为空" : "区县ID不能为空"));
                        resultList.add(rs);
                        tempFlag = true;
                    }
                    if (StringUtils.isEmpty(channelCode)) {
                        result.setSuccess(false);
                        OrderLockResult rs = new OrderLockResult(sku, productName, true, "渠道不能为空");
                        resultList.add(rs);
                        tempFlag = true;
                    }
                    if (!tempFlag) {
                        //查询库存是否满足
                        ServiceResult<Stock> waResult;
                        if (addressLevel == 4) {
                            waResult = getStockBySkuAndRegionForLevel(sku, regionId, channelCode,
                                    lockQty, addressLevel);
                        } else {
                            waResult = getStockBySkuAndRegion(sku, regionId, channelCode, lockQty);
                        }
                        if (waResult == null || waResult.getResult() == null
                                || waResult.getResult().getAvaibleQty() < lockQty) {
                            result.setError("400", "库存不足");
                            OrderLockResult rs = new OrderLockResult(sku, productName, false,
                                    "库存不足");
                            resultList.add(rs);
                        } else {
                            sCode = waResult.getResult().getSecCode();
                        }
                    }
                } else {
                    result.setSuccess(false);
                    OrderLockResult rs = new OrderLockResult(sku, productName, true, "系统来源类型不正确");
                    resultList.add(rs);
                }
                batchOrderLockVO.setsCode(sCode);
            } catch (Exception e) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("批量验证WA、EC下单锁(根据地区级别)出现异常");
                log.error("[batchCreateOrderLockForLevel]批量验证WA、EC下单锁(根据地区级别)出现异常:", e);
                return result;
            }
        }

        if (!result.getSuccess()) {
            if (resultList.size() > 0) {
                result.setResult(resultList);
            }
            return result;
        }

        //开启事务
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            for (BatchOrderLockVO batchOrderLockVO : list) {
                //插入数据
                stockOrderLockModel.insertStockOrderLock(batchOrderLockVO.getSku(),
                        batchOrderLockVO.getStoreCode(), batchOrderLockVO.getsCode(),
                        batchOrderLockVO.getRefNo(), batchOrderLockVO.getSource(),
                        batchOrderLockVO.getLockQty(), batchOrderLockVO.getLockUser());
            }
//            transactionManagerStock.commit(status);
        } catch (Exception e) {
//            transactionManagerStock.rollback(status);
            result.setSuccess(false);
            result.setMessage("批量插入WA、EC下单锁(根据地区级别)出现异常");
            log.error("[batchCreateOrderLockForLevel]批量插入WA、EC下单锁(根据地区级别)出现异常出现异常:", e);
            return result;
        }

        result.setSuccess(true);
        return result;
    }

    public void seteStoreStockModel(EStoreStockModel eStoreStockModel) {
        this.eStoreStockModel = eStoreStockModel;
    }

    public void seteStoreSyncModel(EStoreSyncModel eStoreSyncModel) {
        this.eStoreSyncModel = eStoreSyncModel;
    }

    public void setHopStockModel(HopStockModel hopStockModel) {
        this.hopStockModel = hopStockModel;
    }

    public void setItemService(OrderCenterItemServiceImplNew itemService) {
        this.itemServiceImplNew = itemService;
    }

    public void setStockOrderLockModel(StockOrderLockModel stockOrderLockModel) {
        this.stockOrderLockModel = stockOrderLockModel;
    }

    public void setSgStoreDao(SgStoreService sgStoreDao) {
        this.sgStoreDao = sgStoreDao;
    }

//    public void setTransactionManagerStock(DataSourceTransactionManager transactionManagerStock) {
//        this.transactionManagerStock = transactionManagerStock;
//    }

    
    public ServiceResult<SgStore> getSgStoreById(Integer storeId) {
        ServiceResult<SgStore> result = new ServiceResult<SgStore>();
        try {
            if (storeId == null) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("店铺ID为空");
                return result;
            }
            result.setResult(eStoreStockModel.getSgStoreById(storeId));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("根据店铺ID查询店铺信息异常：" + e.getMessage());
            log.error("根据店铺ID查询店铺信息异常：storeId=" + storeId, e);
        }
        return result;
    }

}
