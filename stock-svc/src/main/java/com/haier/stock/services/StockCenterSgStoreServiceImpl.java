package com.haier.stock.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.BatchOrderLockVO;
import com.haier.shop.model.OrderLockResult;
import com.haier.shop.model.ProductBase;
import com.haier.shop.model.SgConstants;
import com.haier.stock.model.Stock;
import com.haier.stock.model.BaseStock;
import com.haier.stock.model.InvSgStock;
import com.haier.stock.model.InvSgStockEntity;
import com.haier.stock.model.InvSgStockLock;
import com.haier.stock.model.InvSgStockLockEntity;
import com.haier.stock.service.StockCenterItemService;
import com.haier.stock.service.StockCenterSgStoreService;
@Service
public class StockCenterSgStoreServiceImpl implements StockCenterSgStoreService {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
        .getLogger(StockCenterSgStoreServiceImpl.class);
    @Autowired
    private EStoreStockModel               eStoreStockModel;
    @Autowired
    private SgStoreModel                   sgStoreModel;
    @Autowired
    private StockCenterItemService                    itemService;
    @Autowired
    private StockOrderLockModel            stockOrderLockModel;
    @Autowired
    private SgStockLockModel               sgStockLockModel;
    private DataSourceTransactionManager   transactionManagerStock;

    public StockOrderLockModel getStockOrderLockModel() {
        return stockOrderLockModel;
    }

    public void setStockOrderLockModel(StockOrderLockModel stockOrderLockModel) {
        this.stockOrderLockModel = stockOrderLockModel;
    }

    public DataSourceTransactionManager getTransactionManagerStock() {
        return transactionManagerStock;
    }

    public void setTransactionManagerStock(DataSourceTransactionManager transactionManagerStock) {
        this.transactionManagerStock = transactionManagerStock;
    }

    public SgStockLockModel getSgStockLockModel() {
        return sgStockLockModel;
    }

    public void setSgStockLockModel(SgStockLockModel sgStockLockModel) {
        this.sgStockLockModel = sgStockLockModel;
    }

    public EStoreStockModel geteStoreStockModel() {
        return eStoreStockModel;
    }

    public void seteStoreStockModel(EStoreStockModel eStoreStockModel) {
        this.eStoreStockModel = eStoreStockModel;
    }

    public SgStoreModel getSgStoreModel() {
        return sgStoreModel;
    }

    public void setSgStoreModel(SgStoreModel sgStoreModel) {
        this.sgStoreModel = sgStoreModel;
    }

    public StockCenterItemService getItemService() {
        return itemService;
    }

    public void setItemService(StockCenterItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public ServiceResult<Stock> getStockBySkuAndStreet(String sku, Integer regionId,
                                                       String channelCode, Integer requireQty) {
        ServiceResult<Stock> stock = new ServiceResult<Stock>();
        try {
            return this.getStockBySkuAndStreet(sku, regionId, channelCode, requireQty, true);
        } catch (Exception e) {
            stock.setError(null, "getStockBySkuAndStreet 查询异常");
        }
        return stock;
    }

    @Override
    public ServiceResult<Stock> getStockBySkuAndStreet(String sku, Integer streetId,
                                                       String channelCode, Integer requireQty,
                                                       Boolean multi) {
        return this.getStockBySkuAndStreet(sku, streetId, channelCode, requireQty, multi, false);
    }

    @Override
    public ServiceResult<Stock> getStockBySkuAndStreet(String sku, Integer streetId,
                                                       String channelCode, Integer requireQty,
                                                       Boolean multi, Boolean timely) {
        log.info(
            "getStockByRegion[sku=" + sku + ",streetId=" + streetId + ",channelCode" + channelCode
                 + ",requireQty=" + requireQty + ",multi=" + multi + ",timely=" + timely + "]");
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        result.setSuccess(true);
        if (requireQty <= 0) {
            requireQty = 1;
        }

        Stock stock = null;
        try {
            ServiceResult<ProductBase> productResult = itemService.getPorductBaseBySku(sku);
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

            // 商城专供商品
            if (ProductBase.PRODUCTO2OTYPE_1 == productO2OType) {
                stock = sgStoreModel.getStockBySkuAndRegion(sku, streetId, channelCode, requireQty,
                    multi, true);
                if (stock != null) {
                    stock.setStockType(BaseStock.STOCKTYPE_WA);
                }
                result.setResult(stock);
                return result;
            } else {
                result.setResult(stock);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setResult(stock);
            result.setMessage("根据区县查询库存失败：" + e.getMessage());
            log.error("根据指定区县查询库存失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<Stock> getStockBySkuAndStreetForPay(String sku, Integer streetId,
                                                             String channelCode, Integer requireQty,
                                                             boolean multi, boolean timely,
                                                             boolean lockFlag, String cOrderSn,
                                                             boolean selfLockFlag) {
        log.info("getStockBySkuAndStreetForPay[sku=" + sku + ",streetId=" + streetId
                 + ",channelCode" + channelCode + ",requireQty=" + requireQty + ",multi=" + multi
                 + ",timely=" + timely + "]");
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        result.setSuccess(true);
        if (requireQty <= 0) {
            requireQty = 1;
        }

        Stock stock = null;
        try {
            ServiceResult<ProductBase> productResult = itemService.getPorductBaseBySku(sku);
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

            // 商城专供商品
            if (ProductBase.PRODUCTO2OTYPE_1 == productO2OType) {
                stock = sgStoreModel.getStockBySkuAndRegion(sku, streetId, channelCode, requireQty,
                    multi, timely, lockFlag, cOrderSn, selfLockFlag);
                if (stock != null) {
                    stock.setStockType(BaseStock.STOCKTYPE_WA);
                }
                result.setResult(stock);
                return result;
            } else {
                result.setResult(stock);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setResult(stock);
            result.setMessage("顺逛WA付款查询库存失败：" + e.getMessage());
            log.error("顺逛WA付款查询库存失败：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Stock> getStockBySkuAndStreetWithOutLock(String sku, Integer streetId,
                                                                  String channelCode,
                                                                  Integer requireQty, Boolean multi,
                                                                  Boolean timely,
                                                                  boolean lockFlag) {
        log.info(
            "getStockByRegion[sku=" + sku + ",streetId=" + streetId + ",channelCode" + channelCode
                 + ",requireQty=" + requireQty + ",multi=" + multi + ",timely=" + timely + "]");
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        result.setSuccess(true);
        if (requireQty <= 0) {
            requireQty = 1;
        }

        Stock stock = null;
        try {
            ServiceResult<ProductBase> productResult = itemService.getPorductBaseBySku(sku);
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

            // 商城专供商品
            if (ProductBase.PRODUCTO2OTYPE_1 == productO2OType) {
                stock = sgStoreModel.getStockBySkuAndRegion(sku, streetId, channelCode, requireQty,
                    multi, true, lockFlag);
                if (stock != null) {
                    stock.setStockType(BaseStock.STOCKTYPE_WA);
                }
                result.setResult(stock);
                return result;
                // O2O专供商品
                // } else if (ProductBase.PRODCUTO2OTYPE_3 == productO2OType) {
                // if (!InvStockChannel.CHANNEL_SHANGCHENG.equals(channelCode)
                // && !InvStockChannel.CHANNEL_RRS.equals(channelCode)) {
                // result.setResult(stock);
                // result.setSuccess(true);
                // return result;
                // }
                // InvStore invStore =
                // eStoreStockModel.getBySkuAndStoreCode(sku, regionId,
                // requireQty, true, false);
                // if (invStore != null) {
                // stock = new Stock();
                // stock.setSku(invStore.getSku());
                // stock.setStoreCode(invStore.getStoreCode());
                // stock.setAvaibleQty(invStore.getStockQty());
                // stock.setStockType(BaseStock.STOCKTYPE_EC);
                // stock.setUpdateTime(invStore.getUpdateTime());
                // }
                // result.setResult(stock);
                // return result;
            } else {
                result.setResult(stock);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setResult(stock);
            result.setMessage("根据区县查询库存失败：" + e.getMessage());
            log.error("根据指定区县查询库存失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * EC库存查询
     */
    @Override
    public ServiceResult<Stock> getStockByStoreCode(String sku, String storeCode) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        try {
            if (StringUtil.isEmpty(sku) || StringUtil.isEmpty(storeCode)) {
                result.setSuccess(false);
                result.setResult(null);
                return result;
            }
            Stock stock = eStoreStockModel.getBySkuAndStoreCode(sku, storeCode, true, "", false);
            //            //返回库存前，获取被锁定的库存并减去
            //            Integer qty = eStoreStockModel.orderLockByRefNo(sku, stock.getStoreCode(), null,
            //                SgConstants.STOCK_LOCK_TYPE.EC);
            //            Integer avaibleQty = stock.getAvaibleQty() - qty;
            //            stock.setAvaibleQty(avaibleQty);
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

    @Override
    public ServiceResult<Stock> getStockByStoreCodeForPay(String sku, String storeCode,
                                                          boolean lockFlag, String cOrderSn,
                                                          boolean selfLockFlag) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        try {
            if (StringUtil.isEmpty(sku) || StringUtil.isEmpty(storeCode)) {
                result.setSuccess(false);
                result.setResult(null);
                return result;
            }
            Stock stock = eStoreStockModel.getBySkuAndStoreCode(sku, storeCode, lockFlag, cOrderSn,
                selfLockFlag);
            result.setResult(stock);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("顺逛EC付款查询库存失败");
            log.error("顺逛EC付款查询库存失败", e);
        }
        return result;
    }

    /**
     * 顺逛自有库存查询接口
     * 
     * @param sku
     *            物料编码
     * @param streetId
     *            街道ID
     * @param storeId
     *            店铺ID
     * @param requireQty
     *            请求数量
     * @return
     */
    @Override
    public ServiceResult<InvSgStock> getO2OStockBySkuAndStreet(String sku, Integer streetId,
                                                               String storeId, Integer requireQty) {
        return getO2OStockBySkuAndStreetWithOutLock(sku, streetId, storeId, requireQty, true);
    }

    @Override
    public ServiceResult<InvSgStock> getO2OStockBySkuAndStreetForPay(String sku, Integer streetId,
                                                                     String storeId,
                                                                     Integer requireQty,
                                                                     boolean lockFlag,
                                                                     String cOrderSn,
                                                                     boolean selfLockFlag) {
        ServiceResult<InvSgStock> result = new ServiceResult<InvSgStock>();
        try {
            InvSgStock stock = sgStoreModel.getO2OStockBySkuAndStreet(sku, streetId, storeId,
                requireQty, lockFlag, cOrderSn, selfLockFlag);
            result.setResult(stock);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage(be.getMessage());
            log.error("顺逛O2O付款查询库存失败" + be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("顺逛O2O付款查询库存失败");
            log.error("顺逛O2O付款查询库存失败", e);
        }
        return result;
    }

    /**
     * 顺逛自有库存查询接口，20161212修改，不减掉下单锁，计算总库存
     * 
     * @param sku
     *            物料编码
     * @param streetId
     *            街道ID
     * @param storeId
     *            店铺ID
     * @param requireQty
     *            请求数量
     * @param lockFlag
     *            下单锁标识           
     * @return
     */
    @Override
    public ServiceResult<InvSgStock> getO2OStockBySkuAndStreetWithOutLock(String sku,
                                                                          Integer streetId,
                                                                          String storeId,
                                                                          Integer requireQty,
                                                                          boolean lockFlag) {
        ServiceResult<InvSgStock> result = new ServiceResult<InvSgStock>();
        try {
            InvSgStock stock = sgStoreModel.getO2OStockBySkuAndStreet(sku, streetId, storeId,
                requireQty, lockFlag);
            result.setResult(stock);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage(be.getMessage());
            log.error("顺逛自有库存查询接口失败" + be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("顺逛自有库存查询接口失败");
            log.error("顺逛自有库存查询接口失败" + e.getMessage());
        }
        return result;
    }

    /**
     * 顺逛自有库存入库接口
     * 
     * @param sku
     *            物料编码
     * @param itemProperty
     *            批次： 10:正品
     * @param storeId
     *            店铺码
     * @param scode
     *            库位码
     * @param changeQty
     *            库存量
     * @param userName
     *            操作人
     * @return
     */
    @Override
    public ServiceResult<Boolean> inStock(String sku, String itemProperty, String storeId,
                                          String scode, Integer changeQty, String userName) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            boolean b = sgStoreModel.inStock(sku, itemProperty, storeId, scode, changeQty,
                userName);
            result.setResult(b);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage(be.getMessage());
            log.error("顺逛自有库存入库接口失败" + be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("顺逛自有库存入库接口失败");
            log.error("顺逛自有库存入库接口失败" + e.getMessage());
        }
        return result;

    }

    /**
     * 顺逛自有库存占货接口
     * 
     * @param sku
     *            物料编码
     * @param storeCode
     *            店铺ID
     * @param sCode
     *            库位码
     * @param lockQty
     *            数量
     * @param refNo
     *            关联单号
     * @param source
     *            订单来源
     * @return
     */
    @Override
    public ServiceResult<String> frozeByStoreCode(InvSgStockLock invSgStockLock) {
        ServiceResult<String> result = new ServiceResult<String>();
        try {
            if (StringUtil.isEmpty(invSgStockLock.getSku(), true)) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("物料编码不能为空");
                return result;
            }

            if (StringUtil.isEmpty(invSgStockLock.getStoreCode(), true)) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("店铺编码不能为空");
                return result;
            }

            if (StringUtil.isEmpty(invSgStockLock.getScode(), true)) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("库位编码不能为空");
                return result;
            }

            if (invSgStockLock.getLockQty() == null) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("占用数量不能为空");
                return result;
            }

            if (StringUtil.isEmpty(invSgStockLock.getRefNo(), true)) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("关联单号不能为空");
                return result;
            }

            if (invSgStockLock.getLockQty() < 1) {
                result.setSuccess(false);
                result.setMessage("占用数量不能小于1");
                result.setResult(null);
                return result;
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("顺逛自有库存占货接口失败-参数校验出现异常");
            log.error("顺逛自有库存占货接口失败" + e.getMessage());
            return result;
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {

            // 判断是否已经被占用
            Integer flag = sgStockLockModel.findSgStockLockBySkuRefNoStoreCode(
                invSgStockLock.getSku(), invSgStockLock.getRefNo(), invSgStockLock.getStoreCode());
            if (flag == null) {
                // 可用库存不能小于占用库存
                InvSgStockEntity invSgStockEntity = sgStoreModel.findInvSgStockBySkuRefNoStoreCode(
                    invSgStockLock.getSku(), invSgStockLock.getScode(),
                    invSgStockLock.getStoreCode());

                if (invSgStockEntity == null) {
                    transactionManagerStock.rollback(status);
                    result.setSuccess(false);
                    result.setResult(null);
                    result.setMessage("库存不存在");
                    return result;
                }

                if (invSgStockEntity.getStockQty().intValue() < invSgStockLock.getLockQty()
                    .intValue()) {
                    transactionManagerStock.rollback(status);
                    result.setSuccess(false);
                    result.setResult(null);
                    result.setMessage("可用库存不能小于占用库存!");
                    return result;
                }
                // 占货,插入自有库存占用表
                sgStockLockModel.insertInvSgStockLock(invSgStockLock);
                //TODO 待优化 可以将校验语句拿到sql中。
                // 更新库表,扣减自有库存表
                sgStoreModel.updateInvSgStockQty(invSgStockLock);

                // 插入日志表inv_sg_stock_log
                if (invSgStockEntity.getFrozenQty() == null) {
                    invSgStockEntity.setFrozenQty(0);
                }
                sgStoreModel.addInvSgStockLog(SgConstants.STOCK_CHANGE_TYPE_LOG.OUT,
                    SgConstants.STOCK_BILL_TYPE.SALE_LOCK, invSgStockLock.getStoreCode(),
                    invSgStockLock.getScode(), invSgStockEntity.getStockQty(),
                    invSgStockEntity.getStockQty() - invSgStockLock.getLockQty(),
                    invSgStockLock.getLockQty(), invSgStockEntity.getFrozenQty(),
                    invSgStockEntity.getFrozenQty() + invSgStockLock.getLockQty(), "system",
                    invSgStockLock.getRefNo(), invSgStockLock.getSku());
                result.setSuccess(true);
                result.setResult(null);
            } else {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("该单号，物料编码，店铺编码已占用");
            }

            transactionManagerStock.commit(status);
            return result;
        } catch (BusinessException be) {
            transactionManagerStock.rollback(status);
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage(be.getMessage());
            log.error("顺逛自有库存占货接口失败" + be.getMessage());
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("顺逛自有库存占货接口失败");
            log.error("顺逛自有库存占货接口失败" + e.getMessage());
        }
        return result;
    }

    /**
     * 下单锁库接口
     * 
     * @param sku 物料编码   不能为空
     * @param storeCode 店铺ID
     * @param streetId 街道Id
     * @param scode 库位
     * @param refNo 单据号(网单号)
     * @param source 订单来源
     * @param systemCode 系统类型 EC WA SG  
     * @param lockQty 锁定数量
     * @param lockUser 锁定人
     * @param channelCode 渠道Id
     * @return
     * 
     */
    @Override
    public ServiceResult<Integer> orderLockByRefNo(String sku, String storeCode, Integer streetId,
                                                   String scode, String refNo, String source,
                                                   Integer systemCode, Integer lockQty,
                                                   String lockUser, String channelCode) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            if (systemCode == null) {
                result.setSuccess(false);
                result.setMessage("系统来源不能为空");
                return result;
            }
            if (systemCode.intValue() == SgConstants.STOCK_LOCK_TYPE.EC.intValue()) {
                if (StringUtils.isEmpty(sku)) {
                    result.setMessage("物料编码不能为空");
                    result.setSuccess(false);
                    return result;
                }
                if (StringUtils.isEmpty(storeCode)) {
                    result.setMessage("店铺编码不能为空");
                    result.setSuccess(false);
                    return result;
                }
                //查询库存是否满足
                ServiceResult<Stock> ecResult = getStockByStoreCode(sku, storeCode);
                if (ecResult.getResult().getAvaibleQty() < lockQty) {
                    result.setMessage("库存不足");
                    result.setSuccess(false);
                    return result;
                }
                scode = ecResult.getResult().getSecCode();
            } else if (systemCode.equals(SgConstants.STOCK_LOCK_TYPE.WA)) {
                if (StringUtils.isEmpty(sku)) {
                    result.setMessage("物料编码不能为空");
                    result.setSuccess(false);
                    return result;
                }
                if (StringUtils.isEmpty(streetId.toString())) {
                    result.setMessage("街道编码不能为空");
                    result.setSuccess(false);
                    return result;
                }
                if (StringUtils.isEmpty(channelCode)) {
                    result.setMessage("渠道编码不能为空");
                    result.setSuccess(false);
                    return result;
                }
                //查询库存是否满足
                ServiceResult<Stock> waResult = getStockBySkuAndStreet(sku, streetId, channelCode,
                    lockQty);
                if (waResult == null || waResult.getResult() == null
                    || waResult.getResult().getAvaibleQty() < lockQty) {
                    result.setMessage("库存不足");
                    result.setSuccess(false);
                    return result;
                }
                scode = waResult.getResult().getSecCode();
            } else if (systemCode.equals(SgConstants.STOCK_LOCK_TYPE.SG)) {
                if (StringUtils.isEmpty(sku)) {
                    result.setMessage("店铺编码不能为空");
                    result.setSuccess(false);
                    return result;
                }
                if (StringUtils.isEmpty(streetId.toString())) {
                    result.setMessage("街道编码不能为空");
                    result.setSuccess(false);
                    return result;
                }
                if (StringUtils.isEmpty(storeCode)) {
                    result.setMessage("店铺编码不能为空");
                    result.setSuccess(false);
                    return result;
                }
                //查询库存是否满足
                ServiceResult<InvSgStock> sgResult = getO2OStockBySkuAndStreet(sku, streetId,
                    storeCode, lockQty);
                if (sgResult == null || sgResult.getResult() == null
                    || sgResult.getResult().getStockQty() < lockQty) {
                    result.setMessage("库存不足");
                    result.setSuccess(false);
                    return result;
                }
                scode = sgResult.getResult().getScode();
            } else {
                result.setSuccess(false);
                result.setMessage("系统来源类型不符");
                return result;
            }

            //插入数据
            Integer qty = stockOrderLockModel.insertStockOrderLock(sku, storeCode, scode, refNo,
                source, lockQty, lockUser);
            result.setResult(qty);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("锁定库存数量出现异常");
            log.error("[orderLockByRefNo]锁定库存数量出现异常:", e);
        }
        return result;
    }

    /**
     * 下单锁库接口---批量
     * 
     * @param sku 物料编码   不能为空
     * @param storeCode 店铺ID
     * @param streetId 街道Id
     * @param scode 库位
     * @param refNo 单据号(网单号)
     * @param source 订单来源
     * @param systemCode 系统类型 EC WA SG  
     * @param lockQty 锁定数量
     * @param lockUser 锁定人
     * @param channelCode 渠道Id
     * @return
     * 
     */
    @Override
    public ServiceResult<List<OrderLockResult>> batchOrderLockByRefNo(List<BatchOrderLockVO> list) {
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
                Integer streetId = batchOrderLockVO.getRegionId();
                String channelCode = batchOrderLockVO.getChannelCode();
                Integer lockQty = batchOrderLockVO.getLockQty();
                String storeCode = batchOrderLockVO.getStoreCode();
                Integer systemCode = batchOrderLockVO.getSystemCode();
                String scode = batchOrderLockVO.getsCode();

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
                        ServiceResult<Stock> ecResult = getStockByStoreCode(sku, storeCode);
                        if (ecResult == null || ecResult.getResult() == null
                            || ecResult.getResult().getAvaibleQty() < lockQty) {
                            result.setError("400", "库存不足");
                            OrderLockResult rs = new OrderLockResult(sku, productName, false,
                                "库存不足");
                            resultList.add(rs);
                        } else {
                            scode = ecResult.getResult().getSecCode();
                        }
                    }
                } else if (systemCode.equals(SgConstants.STOCK_LOCK_TYPE.WA)) {
                    boolean tempFlag = false;
                    if (StringUtils.isEmpty(sku)) {
                        result.setSuccess(false);
                        OrderLockResult rs = new OrderLockResult(sku, productName, true, "物料不能为空");
                        resultList.add(rs);
                        tempFlag = true;
                    }
                    if (StringUtils.isEmpty(streetId.toString())) {
                        result.setSuccess(false);
                        OrderLockResult rs = new OrderLockResult(sku, productName, true,
                            "街道编码不能为空");
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
                        ServiceResult<Stock> waResult = getStockBySkuAndStreet(sku, streetId,
                            channelCode, lockQty);
                        if (waResult == null || waResult.getResult() == null
                            || waResult.getResult().getAvaibleQty() < lockQty) {
                            result.setError("400", "库存不足");
                            OrderLockResult rs = new OrderLockResult(sku, productName, false,
                                "库存不足");
                            resultList.add(rs);
                        } else {
                            scode = waResult.getResult().getSecCode();
                        }
                    }
                } else if (systemCode.equals(SgConstants.STOCK_LOCK_TYPE.SG)) {
                    boolean tempFlag = false;
                    if (StringUtils.isEmpty(sku)) {
                        result.setSuccess(false);
                        OrderLockResult rs = new OrderLockResult(sku, productName, true, "物料不能为空");
                        resultList.add(rs);
                        tempFlag = true;
                    }
                    if (StringUtils.isEmpty(streetId.toString())) {
                        result.setSuccess(false);
                        OrderLockResult rs = new OrderLockResult(sku, productName, true,
                            "街道编码不能为空");
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
                        ServiceResult<InvSgStock> sgResult = getO2OStockBySkuAndStreet(sku,
                            streetId, storeCode, lockQty);
                        if (sgResult == null || sgResult.getResult() == null
                            || sgResult.getResult().getStockQty() < lockQty) {
                            result.setError("400", "库存不足");
                            OrderLockResult rs = new OrderLockResult(sku, productName, false,
                                "库存不足");
                            resultList.add(rs);
                        } else {
                            scode = sgResult.getResult().getScode();
                        }
                    }
                } else {
                    result.setSuccess(false);
                    OrderLockResult rs = new OrderLockResult(sku, productName, true, "系统来源类型不正确");
                    resultList.add(rs);
                }
                batchOrderLockVO.setsCode(scode);
            } catch (Exception e) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("锁定库存数量出现异常");
                log.error("[batchOrderLockByRefNo]批量验证库存数量出现异常:", e);
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
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            for (BatchOrderLockVO batchOrderLockVO : list) {
                //插入数据
                stockOrderLockModel.insertStockOrderLock(batchOrderLockVO.getSku(),
                    batchOrderLockVO.getStoreCode(), batchOrderLockVO.getsCode(),
                    batchOrderLockVO.getRefNo(), batchOrderLockVO.getSource(),
                    batchOrderLockVO.getLockQty(), batchOrderLockVO.getLockUser());
            }
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("锁定库存数量出现异常");
            log.error("[batchOrderLockByRefNo]批量插入下单锁出现异常:", e);
            return result;
        }

        result.setSuccess(true);
        return result;
    }

    /**
     * 下单锁库释放
     * @return
     */
    @Override
    public ServiceResult<Integer> releaseOrderLockByrefNo() {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            Integer flag = stockOrderLockModel.releaseOrderLockByrefNo();
            result.setResult(flag);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("下单锁库释放出现异常");
            log.error("下单锁库释放出现异常:", e);
        }

        return result;
    }

    /**
     * 下单付款成功释放库存
     * 
     * @param refNo 单据号(网单号)
     * @return
     */
    @Override
    public ServiceResult<Integer> paymentSuccessReleaseOrderLock(String refNo) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            //锁库释放，删除数据
            Integer flag = stockOrderLockModel.paymentSuccessReleaseOrderLock(refNo);
            result.setResult(flag);
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("下单付款成功释放库存出现异常");
            log.error("下单付款成功释放库存出现异常:", e);
        }
        return result;

    }

    /**
     * 库存释放接口
     * 
     * @param sku 物料编码
     * @param storeCode 店表编码
     * @param releaseQty 占用数量
     * @param refNo 单据号(网单号)
     * @param scode 库位号
     * @return
     */
    @Override
    public ServiceResult<Integer> releaseByRefNo(String sku, String storeCode, Integer releaseQty,
                                                 String refNo, String scode) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            if (StringUtils.isEmpty(sku)) {
                result.setMessage("物料编码不能为空");
                result.setSuccess(false);
                return result;
            }
            if (StringUtils.isEmpty(storeCode)) {
                result.setMessage("店铺编码不能为空");
                result.setSuccess(false);
                return result;
            }
            if (releaseQty == null || releaseQty <= 0) {
                result.setMessage("释放数量不能为空且必须大于0");
                result.setSuccess(false);
                return result;
            }
            if (StringUtils.isEmpty(refNo)) {
                result.setMessage("网单号不能为空");
                result.setSuccess(false);
                return result;
            }
        } catch (Exception e) {
            result.setResult(null);
            result.setSuccess(false);
            result.setMessage("库存释放接口-参数校验出现异常");
            log.error("库存释放接口-参数校验出现异常:", e);
            return result;
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        Integer update = 0;
        try {
            //根据refNo查询数据
            InvSgStockLockEntity stockLockEntity = sgStockLockModel.findSgStockLockByRefNo(refNo);
            if (stockLockEntity == null || releaseQty.equals(stockLockEntity.getReleaseQty())) {
                transactionManagerStock.rollback(status);
                result.setMessage("自有库存占用已释放");
                result.setSuccess(true);
                return result;
            }
            stockLockEntity.setReleaseQty(releaseQty);
            // 已占用库存不能小于需要释放的库存
            InvSgStockEntity invSgStockEntity = sgStoreModel.findInvSgStockBySkuRefNoStoreCode(
                stockLockEntity.getSku(), stockLockEntity.getScode(),
                stockLockEntity.getStoreCode());
            if (invSgStockEntity == null) {
                transactionManagerStock.rollback(status);
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("库存不存在");
                return result;
            }

            if (invSgStockEntity.getFrozenQty() == null
                || invSgStockEntity.getFrozenQty().intValue() < releaseQty.intValue()) {
                transactionManagerStock.rollback(status);
                result.setSuccess(false);
                result.setMessage("数据异常，已占用库存不满足需要释放库存");
                log.error("数据异常，已占用库存不满足需要释放库存 主键ID：" + invSgStockEntity.getId());
                return result;
            }

            //TODO 可以将上面的校验拿到sql中校验 待优化。
            //把占有库存的数量给剪掉
            sgStoreModel.updatefrozenQty(sku, storeCode, releaseQty, refNo, scode);
            //更新占货信息
            update = sgStockLockModel.updateReleaseSgStockLock(stockLockEntity);
            //记录日志
            if (update > 0) {
                //记录库存变动日志。
                sgStoreModel.addInvSgStockLog(SgConstants.STOCK_CHANGE_TYPE_LOG.OUT,
                    SgConstants.STOCK_BILL_TYPE.RELEASE_OUT, storeCode, scode,
                    invSgStockEntity.getStockQty(), invSgStockEntity.getStockQty(), 0,
                    invSgStockEntity.getFrozenQty(), invSgStockEntity.getFrozenQty() - releaseQty,
                    "system", refNo, sku);
            } else {
                transactionManagerStock.rollback(status);
                result.setResult(update);
                result.setSuccess(false);
                result.setMessage("库存释放接口出现失败");
                log.error("库存释放接口出现失败，");
                return result;
            }
            transactionManagerStock.commit(status);
            result.setResult(update);
            result.setSuccess(true);
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            result.setResult(update);
            result.setSuccess(false);
            result.setMessage("库存释放接口出现异常");
            log.error("库存释放接口出现异常:", e);
        }

        return result;
    }

    /**
     * 库存退货释放接口
     * 
     * @param sku 物料编码
     * @param storeCode 店铺ID
     * @param sCode 库位码
     * @param lockQty 数量
     * @param refNo 关联单号
     * @param source 订单来源
     * @return
     */
    @Override
    public ServiceResult<String> releaseForReturnByRefNo(InvSgStockLock invSgStockLock) {
        ServiceResult<String> result = new ServiceResult<String>();
        InvSgStockLockEntity stockLockEntity = null;
        try {
            if (invSgStockLock == null) {
                result.setSuccess(false);
                result.setMessage("入参不能为空");
                return result;
            }
            if (StringUtil.isEmpty(invSgStockLock.getSku(), true)) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("物料编码不能为空");
                return result;
            }

            if (StringUtil.isEmpty(invSgStockLock.getStoreCode(), true)) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("店铺编码不能为空");
                return result;
            }

            if (StringUtil.isEmpty(invSgStockLock.getScode(), true)) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("库位编码不能为空");
                return result;
            }

            if (invSgStockLock.getReleaseQty() == null) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("退货库存数量不能为空");
                return result;
            }

            if (StringUtil.isEmpty(invSgStockLock.getRefNo(), true)) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("关联单号不能为空");
                return result;
            }

            if (invSgStockLock.getReleaseQty().intValue() < 1) {
                result.setSuccess(false);
                result.setMessage("退货库存数量不能小于1");
                result.setResult(null);
                return result;
            }
            //根据refNo查询数据
            stockLockEntity = sgStockLockModel.findSgStockLockByRefNo(invSgStockLock.getRefNo());
            if (stockLockEntity == null
                || stockLockEntity.getLockQty().equals(stockLockEntity.getReleaseQty())) {
                result.setMessage("自有库存占用已释放");
                result.setSuccess(false);
                return result;
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("顺逛自有库存退货释放接口参数校验异常");
            log.error("顺逛自有库存退货释放接口参数校验异常" + e.getMessage());
            return result;
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            //如果传的锁定数量比查询数据的锁定数量多，给占有数量赋值
            //			if (stockLockEntity.getLockQty() < invSgStockLock.getLockQty()) {
            //				stockLockEntity.setReleaseQty(stockLockEntity.getLockQty());
            //			}
            Integer lockQty = 0; //剩余的锁定数量
            if (stockLockEntity.getReleaseQty() != null) {
                lockQty = stockLockEntity.getLockQty() - stockLockEntity.getReleaseQty(); //因为都是整数，就不用函数计算了
            } else {
                lockQty = stockLockEntity.getLockQty();
            }

            if (invSgStockLock.getReleaseQty().compareTo(lockQty) > 0) { //如果释放数量大于剩余的锁定数量
                new BusinessException("释放数量不能大于剩余锁定数量");
            } else {
                stockLockEntity.setReleaseQty(invSgStockLock.getReleaseQty());//释放传来的释放数量
            }

            //插入日志表
            InvSgStockEntity invSgStockEntity = sgStoreModel.findInvSgStockBySkuRefNoStoreCode(
                invSgStockLock.getSku(), invSgStockLock.getScode(), invSgStockLock.getStoreCode());
            // 已占用库存不能小于需要释放的库存
            if (invSgStockEntity == null) {
                transactionManagerStock.rollback(status);
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("库存不存在");
                return result;
            }
            if (invSgStockEntity.getFrozenQty() == null) {
                invSgStockEntity.setFrozenQty(0);
            }
            Integer frozenQty = invSgStockEntity.getFrozenQty();
            if (frozenQty < invSgStockLock.getReleaseQty().intValue()) {
                transactionManagerStock.rollback(status);
                result.setSuccess(false);
                result.setMessage("数据异常，已占用库存不满足需要释放库存");
                log.error("数据异常，已占用库存不满足需要释放库存 主键ID：" + invSgStockEntity.getId());
                return result;
            }
            //可用数量加数量，占有数量减数量
            sgStoreModel.updateReleaseForReturn(invSgStockLock);

            //更新释放数量
            sgStockLockModel.updateReleaseSgStockLock(stockLockEntity);
            sgStoreModel.addInvSgStockLog(SgConstants.STOCK_CHANGE_TYPE_LOG.IN,
                SgConstants.STOCK_BILL_TYPE.RETURN_GOODS_IN, invSgStockLock.getStoreCode(),
                invSgStockLock.getScode(), invSgStockEntity.getStockQty(),
                invSgStockEntity.getStockQty() + invSgStockLock.getReleaseQty(),
                invSgStockLock.getReleaseQty(), invSgStockEntity.getFrozenQty(),
                invSgStockEntity.getFrozenQty() - invSgStockLock.getReleaseQty(), "system",
                invSgStockLock.getRefNo(), invSgStockLock.getSku());

            //提交事物
            transactionManagerStock.commit(status);

            result.setSuccess(true);
            result.setResult(null);
            return result;
        } catch (BusinessException be) {
            transactionManagerStock.rollback(status);
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage(be.getMessage());
            log.error("顺逛自有库存退货释放接口失败" + be.getMessage());
        } catch (Exception e) {
            transactionManagerStock.rollback(status);
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("顺逛自有库存退货释放接口失败");
            log.error("顺逛自有库存退货释放接口失败" + e.getMessage());
        }
        return result;

    }

    /**
     * 通过顺逛自有店铺ID获取该店铺下所有sku与库存状态关系
     * @param storeId
     * @return
     */
    @Override
    public ServiceResult<Map<String, Boolean>> selfO2OStockByStoreId(Integer storeId) {
        log.info("通过顺逛自由店铺ID获取该店铺下所有sku与库存状态关系 storeId:" + storeId);
        ServiceResult<Map<String, Boolean>> result = new ServiceResult<Map<String, Boolean>>();
        try {
            if (storeId == null) {
                result.setMessage("stroeId不能为空");
                result.setSuccess(false);
                return result;
            }
            Map<String, Boolean> b = sgStoreModel.selfO2OStockByStoreId(storeId);
            result.setResult(b);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage(be.getMessage());
            log.error("通过顺逛自由店铺ID获取该店铺下所有sku与库存状态关系接口失败" + be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("通过顺逛自由店铺ID获取该店铺下所有sku与库存状态关系接口失败");
            log.error("通过顺逛自由店铺ID获取该店铺下所有sku与库存状态关系接口失败" + e.getMessage());
        }
        return result;
    }

    /**
     * 通过88码获取EC店铺下所有sku与库存状态关系
     * @param storeCode
     * @return
     */
    @Override
    public ServiceResult<Map<String, Boolean>> o2OStockByStoreId(String storeCode) {
        ServiceResult<Map<String, Boolean>> result = new ServiceResult<Map<String, Boolean>>();
        try {
            if (storeCode == null) {
                result.setMessage("storeCode不能为空");
                result.setSuccess(false);
                return result;
            }
            Map<String, Boolean> b = sgStoreModel.o2OStockByStoreId(storeCode);
            result.setResult(b);
        } catch (BusinessException be) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage(be.getMessage());
            log.error("通过顺逛自由店铺ID获取该店铺下所有sku与库存状态关系接口失败" + be.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("通过顺逛自由店铺ID获取该店铺下所有sku与库存状态关系接口失败");
            log.error("通过顺逛自由店铺ID获取该店铺下所有sku与库存状态关系接口失败" + e.getMessage());
        }
        return result;
    }
}
