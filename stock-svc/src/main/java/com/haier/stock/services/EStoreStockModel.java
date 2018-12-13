package com.haier.stock.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.haier.stock.service.*;
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
import com.haier.shop.model.BasStores;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.MctStoreProducts;
import com.haier.shop.model.ProductBase;
import com.haier.shop.model.ProductsNew;
import com.haier.shop.model.SgConstants;
import com.haier.stock.model.Stock;
import com.haier.shop.model.StockHolder;
import com.haier.shop.service.MctStoreProductsService;
import com.haier.stock.datasource.ReadWriteRoutingDataSourceHolder;
import com.haier.stock.model.BaseStock;
import com.haier.stock.model.InvBaseStore;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStore;
import com.haier.stock.model.InvStoreLock;
import com.haier.stock.model.InvStoreRegions;
import com.haier.stock.model.InvStoreSynch;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.services.Helper.StockBizHelper;
import com.haier.stock.model.SgStore;
import com.haier.stock.service.SgFlagShipStoreAuthorizationService;
@Service
public class EStoreStockModel {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
        .getLogger(EStoreStockModel.class);
    @Autowired
    private InvStoreSynchService invStoreSynchService;
    @Autowired
    private InvStoreService invStoreService;
    @Autowired
    private InvStoreRegionsService invStoreRegionsService;
    @Autowired
    private StockInvMachineSetService stockInvMachineSetService;
    @Autowired
    private InvStoreLockService invStoreLockService;
    private DataSourceTransactionManager    transactionManagerStock;
    @Autowired
    private InvBaseStoreService invBaseStoreService;
    @Autowired
    private StockCenterItemService                     itemService;
    public static final Integer             topx = 3000;
    //    private StoresService                   storesService;
    @Autowired
    private MctStoreProductsService mctStoreProductsService;
    private DataSourceTransactionManager    transactionManagerShop;
    @Autowired
    private SgFlagShipStoreAuthorizationService sgFlagShipStoreAuthorizationService;
    @Autowired
    private SgStoreService sgStoreService;
    @Autowired
    private InvStockOrderLockService invStockOrderLockService;

    /**
     * 接收EStore库存
     * @param storeList
     * @return
     */
    public Integer acceptStoreStock(List<InvStoreSynch> storeList) {
        Integer totalCount = 0;
        Integer count = 0;
        Integer temp = storeList.size();
        for (int i = 0; i < storeList.size(); i = i + 500) {
            if (temp <= 500) {
                count = invStoreSynchService.batchInsert(storeList.subList(i, storeList.size()));
            } else {
                count = invStoreSynchService.batchInsert(storeList.subList(i, i + 500));
            }
            totalCount = totalCount + count;
            temp = temp - 500;
        }
        return totalCount;
    }

    /**
     * 查询待计算的库存
     * @return
     */
    public List<InvStoreSynch> queryStoreSynch() {
        return invStoreSynchService.queryStoreList(topx);
    }

    /**
     * 通过sku,storeCode查询库存
     * @param sku
     * @param
     * @return
     */
    public Stock getBySkuAndStoreCode(String sku, String storeCode) {
        return getBySkuAndStoreCode(sku, storeCode, false, "", false);
    }

    public Stock getBySkuAndStoreCode(String sku, String storeCode, boolean lockFlag,
                                      String cOrderSn, boolean selfLockFlag) {
        if (selfLockFlag && StringUtil.isEmpty(cOrderSn)) {
            throw new BusinessException("支付时网单号不能为空,sku=" + sku + ",storeCode=" + storeCode);
        }
        List<InvStore> storeList = invStoreService.getByStoreCodesAndSku(sku, storeCode,
            InvSection.W10);
        Integer qty = 0;
        if (storeList != null && storeList.size() > 0) {
            InvStore store = storeList.get(0);
            qty = store.getStockQty();

        }
        Stock stock = new Stock();
        stock.setSku(sku);
        stock.setStoreCode(storeCode);
        stock.setStockType(BaseStock.STOCKTYPE_EC);
        stock.setAvaibleQty(qty);

        if (lockFlag) {
            if (selfLockFlag && !StringUtil.isEmpty(cOrderSn)) {
                qty = orderLockByRefNo(sku, storeCode, null, SgConstants.STOCK_LOCK_TYPE.EC,
                    cOrderSn);
            } else {
                qty = orderLockByRefNo(sku, storeCode, null, SgConstants.STOCK_LOCK_TYPE.EC, null);
            }
            Integer avaibleQty = stock.getAvaibleQty() - qty;
            //            avaibleQty = avaibleQty.intValue() < 0 ? 0 : avaibleQty;
            stock.setAvaibleQty(avaibleQty);
        }
        //        if (selfLockFlag && !StringUtil.isEmpty(cOrderSn)) {
        //            InvStockOrderLockEntity selfLock = invStockOrderLockService
        //                .findInvStockOrderLockByRefNo(cOrderSn);
        //            if (selfLock != null && selfLock.getLockQty() != null
        //                && selfLock.getLockTime().compareTo(new Date()) > 0) {
        //                Integer avaibleQty = stock.getAvaibleQty() + selfLock.getLockQty();
        //                stock.setAvaibleQty(avaibleQty);
        //            }
        //        }
        Integer avaibleQty = stock.getAvaibleQty();
        avaibleQty = avaibleQty.intValue() < 0 ? 0 : avaibleQty;
        stock.setAvaibleQty(avaibleQty);

        return stock;
    }

    /**
     * 按照物料门店码查询库存
     * @param sku 物料
     * @param regionId 区县id
     * @param qty 数量
     * @param isTimely
     * @param backendMgt 是否是分配库位
     * @param
     * @return
     */
    public InvStore getBySkuAndStoreCode(String sku, Integer regionId, Integer qty,
                                         boolean isTimely, boolean backendMgt) {
        return getBySkuAndStoreCode(sku, regionId, qty, isTimely, backendMgt, true);
    }

    /**
     * 按照物料门店码查询库存
     * @param sku 物料
     * @param regionId 区县id
     * @param qty 数量
     * @param isTimely
     * @param backendMgt 是否是分配库位
     * @param
     * @param lockFlag 下单锁标识 true:扣减下单锁 false:不扣减下单锁
     * @return
     */
    public InvStore getBySkuAndStoreCode(String sku, Integer regionId, Integer qty,
                                         boolean isTimely, boolean backendMgt, boolean lockFlag) {
        //一区一品一客逻辑（增加产品组查询店铺8码）
        ServiceResult<ItemBase> itemBaseService = itemService.getItemBaseBySku(sku);
        if (!itemBaseService.getSuccess()) {
            log.info("查询物料发生异常");
            return null;
        }
        ItemBase itemBase = itemBaseService.getResult();
        if (itemBase == null || StringUtil.isEmpty(itemBase.getMaterialCode())) {
            log.info("系统不存在此物料");
            return null;
        }
        //查询启用的区县编码
        Integer storeStatus = null;
        if (backendMgt) {
            storeStatus = null;
        } else {
            storeStatus = BasStores.STORE_STATUS_3;//状态:3.营业中
        }
        //查询条件增加产品组
        List<BasStores> basStoresList = getStoresByRegionId(regionId, storeStatus,
            itemBase.getDepartment());
        if (basStoresList == null || basStoresList.size() <= 0) {
            log.info("没有查询到门店信息,regionId[" + regionId + "]");
            return null;
        }
        //获取门店编码
        Set<String> basStoreSet = new HashSet<String>();
        for (BasStores basStores : basStoresList) {
            basStoreSet.add(basStores.getStoreCode());
        }
        List<String> storeCodeList = new ArrayList<String>(basStoreSet);
        String storeCodes = StockBizHelper.stringWithSingleQuote(storeCodeList);
        String itemProperty = InvSection.W10;
        List<InvStore> storeList = new ArrayList<InvStore>();
        if (isTimely) {
            log.info("查询实时库存sku[" + sku + "]regionId[" + regionId + "]");
            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
            try {
                storeList = invStoreService.getByStoreCodesAndSku(sku, storeCodes, itemProperty);
            } finally {
                ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
            }
        } else {
            storeList = invStoreService.getByStoreCodesAndSku(sku, storeCodes, itemProperty);
        }
        InvStore maxStore = null;
        if (lockFlag) {
            //找出库存数量最大的店铺返回
            for (InvStore entity : storeList) {
                Integer tempQty = entity.getStockQty();

                Integer tempLockQty = orderLockByRefNo(sku, entity.getStoreCode(), null,
                    SgConstants.STOCK_LOCK_TYPE.EC, null);
                tempQty = tempQty - tempLockQty;
                tempQty = tempQty < 0 ? 0 : tempQty;
                entity.setStockQty(tempQty);

                if (qty.intValue() <= tempQty.intValue()) {
                    log.info("从门店找到合适库存，sku[" + sku + "]regionId[" + regionId + "]qty[" + qty
                             + "]storeCode[" + entity.getStoreCode() + "]");
                    return entity;
                } else {
                    if (maxStore == null) {
                        tempLockQty = orderLockByRefNo(sku, entity.getStoreCode(), null,
                            SgConstants.STOCK_LOCK_TYPE.EC, null);
                        tempQty = tempQty - tempLockQty;
                        tempQty = tempQty < 0 ? 0 : tempQty;
                        entity.setStockQty(tempQty);
                        maxStore = entity;
                    } else {
                        tempLockQty = orderLockByRefNo(sku, entity.getStoreCode(), null,
                            SgConstants.STOCK_LOCK_TYPE.EC, null);
                        tempQty = tempQty - tempLockQty;
                        tempQty = tempQty < 0 ? 0 : tempQty;
                        entity.setStockQty(tempQty);
                        if (maxStore.getStockQty().intValue() < entity.getStockQty().intValue()) {
                            maxStore = entity;
                        }
                    }
                }
            }
        } else {
            for (InvStore entity : storeList) {
                Integer tempQty = entity.getStockQty();
                if (qty <= tempQty) {
                    log.info("从门店找到合适库存，sku[" + sku + "]regionId[" + regionId + "]qty[" + qty
                             + "]storeCode[" + entity.getStoreCode() + "]");
                    return entity;
                } else {
                    if (maxStore == null) {
                        maxStore = entity;
                    } else {
                        if (maxStore.getStockQty() < entity.getStockQty()) {
                            maxStore = entity;
                        }
                    }
                }
            }
        }

        return maxStore;

    }

    /**
     * 按照物料门店码查询库存
     * @param sku 物料
     * @param streetId 街道id
     * @param qty 数量
     * @param isTimely
     * @param backendMgt 是否是分配库位
     * @param
     * @param lockFlag 下单锁标识 true:扣减下单锁 false:不扣减下单锁
     * @return
     */
    public InvStore getBySkuAndStoreCodeForLevel(String sku, Integer streetId, Integer qty,
                                                 boolean isTimely, boolean backendMgt,
                                                 boolean lockFlag) {
        if (StringUtil.isEmpty(sku, true)) {
            log.info("参数sku为空");
            return null;
        }
        ServiceResult<ProductsNew> productBySku = itemService.getProductBySku(sku);
        if (!productBySku.getSuccess()) {
            log.info("根据sku获取产品对象发生异常,sku=" + sku);
            return null;
        }
        ProductsNew product = productBySku.getResult();
        if (product == null) {
            log.info("系统不存在此物料,sku=" + sku);
            return null;
        }
        ServiceResult<ItemBase> itemBaseBySku = itemService.getItemBaseBySku(sku);
        if (!itemBaseBySku.getSuccess()) {
            log.info("根据物料sku取得物料基本信息发生异常,sku=" + sku);
            return null;
        }
        ItemBase itemBase = itemBaseBySku.getResult();
        if (itemBase == null) {
            log.info("系统不存在此物料,sku=" + sku);
            return null;
        }

        //查询启用的区县编码
        Integer storeStatus = null;
        if (backendMgt) {
            storeStatus = null;
        } else {
            storeStatus = BasStores.STORE_STATUS_3;//状态:3.营业中
        }

        //根据街道id，品牌id，产品组获取店铺88码
        List<String> storeCodeByStreet = sgFlagShipStoreAuthorizationService.getStoreCodeByStreet(
            streetId, product.getBrandId(), itemBase.getDepartment(), storeStatus,
            BasStores.STOR_TYPE_1);

        if (storeCodeByStreet == null || storeCodeByStreet.size() <= 0) {
            log.info("没有查询到门店信息,streetId[" + streetId + "]getBrandId[" + product.getBrandId()
                      + "]Department[" + itemBase.getDepartment() + "]");
            return null;
        }
        //获取门店编码
        Set<String> basStoreSet = new HashSet<String>();
        for (String storeCode : storeCodeByStreet) {
            basStoreSet.add(storeCode);
        }
        List<String> storeCodeList = new ArrayList<String>(basStoreSet);
        String storeCodes = StockBizHelper.stringWithSingleQuote(storeCodeList);
        String itemProperty = InvSection.W10;
        List<InvStore> storeList = new ArrayList<InvStore>();
        if (isTimely) {
            log.info("查询实时库存sku[" + sku + "]streetId[" + streetId + "]");
            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
            try {
                storeList = invStoreService.getByStoreCodesAndSku(sku, storeCodes, itemProperty);
            } finally {
                ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
            }
        } else {
            storeList = invStoreService.getByStoreCodesAndSku(sku, storeCodes, itemProperty);
        }
        InvStore maxStore = null;
        if (lockFlag) {
            //找出库存数量最大的店铺返回
            for (InvStore entity : storeList) {
                Integer tempQty = entity.getStockQty();

                Integer tempLockQty = orderLockByRefNo(sku, entity.getStoreCode(), null,
                    SgConstants.STOCK_LOCK_TYPE.EC, null);
                tempQty = tempQty - tempLockQty;
                tempQty = tempQty < 0 ? 0 : tempQty;
                entity.setStockQty(tempQty);

                if (qty.intValue() <= tempQty.intValue()) {
                    log.info("从门店找到合适库存，sku[" + sku + "]streetId[" + streetId + "]qty[" + qty
                             + "]storeCode[" + entity.getStoreCode() + "]");
                    return entity;
                } else {
                    if (maxStore == null) {
                        tempLockQty = orderLockByRefNo(sku, entity.getStoreCode(), null,
                            SgConstants.STOCK_LOCK_TYPE.EC, null);
                        tempQty = tempQty - tempLockQty;
                        tempQty = tempQty < 0 ? 0 : tempQty;
                        entity.setStockQty(tempQty);
                        maxStore = entity;
                    } else {
                        tempLockQty = orderLockByRefNo(sku, entity.getStoreCode(), null,
                            SgConstants.STOCK_LOCK_TYPE.EC, null);
                        tempQty = tempQty - tempLockQty;
                        tempQty = tempQty < 0 ? 0 : tempQty;
                        entity.setStockQty(tempQty);
                        if (maxStore.getStockQty().intValue() < entity.getStockQty().intValue()) {
                            maxStore = entity;
                        }
                    }
                }
            }
        } else {
            for (InvStore entity : storeList) {
                Integer tempQty = entity.getStockQty();
                if (qty <= tempQty) {
                    log.info("从门店找到合适库存，sku[" + sku + "]streetId[" + streetId + "]qty[" + qty
                             + "]storeCode[" + entity.getStoreCode() + "]");
                    return entity;
                } else {
                    if (maxStore == null) {
                        maxStore = entity;
                    } else {
                        if (maxStore.getStockQty() < entity.getStockQty()) {
                            maxStore = entity;
                        }
                    }
                }
            }
        }

        return maxStore;
    }

    public List<String> getStoreSkuByCityId(Integer cityId) {
        Long startTime = System.currentTimeMillis();
        String logInfo = "";
        long totalExecuteTime = 0;
        //查询启用的区县编码
        List<InvStoreRegions> storeRegionsList = invStoreRegionsService.getByCityId(cityId,
            InvStoreRegions.STATUS_ON);
        logInfo += "查询启用的区县编码, 记录数(" + (storeRegionsList == null ? 0 : storeRegionsList.size())
                   + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        if (storeRegionsList == null || storeRegionsList.size() <= 0) {
            return null;
        }
        //获取门店编码
        List<String> storeCodeList = new ArrayList<String>();
        for (InvStoreRegions storeRegions : storeRegionsList) {
            if (storeCodeList.contains(storeRegions.getStoreCode())) {
                continue;
            }
            storeCodeList.add(storeRegions.getStoreCode());
        }
        logInfo += "获取门店编码, 记录数(" + (storeCodeList == null ? 0 : storeCodeList.size()) + "), 耗时("
                   + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        if (storeCodeList.size() <= 0) {
            log.info("没有查询到门店信息,cityId[" + cityId + "]");
            return null;
        }
        String[] array = new String[storeCodeList.size()];
        String storeCodes = StockBizHelper.concat(storeCodeList.toArray(array));
        //获取销售库存对应的门店编码
        List<InvStore> storeList = invStoreService
            .getByStoreCode(StockBizHelper.stringWithSingleQuote(storeCodes), InvSection.W10);
        List<String> ecSkus = new ArrayList<String>();
        if (storeList != null && storeList.size() > 0) {
            for (InvStore store : storeList) {
                if (ecSkus.contains(store.getSku())) {
                    continue;
                }
                ecSkus.add(store.getSku());
            }
        }
        logInfo += "获取销售库存对应的门店编码, 记录数(" + (ecSkus == null ? 0 : ecSkus.size()) + "), 耗时("
                   + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        //获取上架的商品列表
        List<ProductBase> pList = this.getAllOnSaleProducts();
        logInfo += "获取上架的商品列表, 记录数(" + (pList == null ? 0 : pList.size()) + "), 耗时("
                   + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        if (pList == null || pList.size() == 0) {
            return null;
        }
        Map<String, Object> productBaseSkuMap = new HashMap<String, Object>();
        for (ProductBase base : pList) {
            productBaseSkuMap.put(base.getSku(), base.getSku());
        }
        List<String> youECSkuList = new ArrayList<String>();
        for (String sku : ecSkus) {
            if (productBaseSkuMap.get(sku) == null) {
                continue;
            }
            youECSkuList.add(sku);
        }
        logInfo += "获取EC的上架的商品列表, 记录数(" + (youECSkuList == null ? 0 : youECSkuList.size())
                   + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        //        String[] infoArray = logInfo.split(";");
        //        for (String s : infoArray) {
        //            log.info(s);
        //        }
        log.info("根据cityId（" + cityId + "），查询EC上架商品列表，总计耗时：" + totalExecuteTime + "ms。");
        return youECSkuList;
    }

    //获取上架的商品列表
    public List<ProductBase> getAllOnSaleProducts() {
        ServiceResult<List<ProductBase>> result = itemService.getAllOnSaleProducts();
        if (result == null || !result.getSuccess()) {
            return null;
        }
        return result.getResult();
    }

    public ServiceResult<String> frozeStockQty(String sku, String storeCode, String refNo,
                                               Integer frozenQty, InventoryBusinessTypes billType,
                                               String optUser, String source) {
        ServiceResult<String> result = new ServiceResult<String>();
        if (frozenQty < 1) {
            result.setMessage("占用数量不能小于1");
            result.setResult(null);
            result.setSuccess(false);
            return result;
        }

        //检查单据号是否已经冻结
        List<InvStoreLock> storeLocks = invStoreLockService.getNotReleased(refNo, sku, storeCode,
            InvSection.W10);
        if (storeLocks.size() > 0) {
            int qty = 0;
            for (InvStoreLock lock : storeLocks) {
                int freeQty = lock.getLockQty() - lock.getReleaseQty();
                qty += freeQty < 0 ? 0 : freeQty;
            }
            if (qty == frozenQty) {
                result.setMessage("已存在此单据的冻结记录");
                result.setSuccess(true);
                result.setResult(null);
                log.info("门店占用库存:单据号=" + refNo + "已存在冻结记录，冻结成功");
            } else {
                result.setMessage("已存在此单据的冻结记录， 记录数量不一致");
                result.setSuccess(false);
                result.setResult(null);
                log.info("门店占用库存:单据号=" + refNo + "已存在冻结记录， 记录数量不一致");
            }
            return result;
        }

        //2017-03-29 占库去掉旗舰店限制
        //        BasStores basStores = getStoresByStoreCode(storeCode, null);
        BasStores basStores = getStoresByStoreCodeNoLimit(storeCode, null);
        if (basStores == null) {
            result.setMessage("门店信息不存在");
            result.setResult(null);
            result.setSuccess(false);
            return result;
        }

        List<StockHolder> stockHolders = new ArrayList<StockHolder>();
        //如果是套机需要解套，冻结子件
        List<InvMachineSet> machineSets = stockInvMachineSetService.getByMainSku(sku);
        if (machineSets.size() <= 0)
            stockHolders.add(StockHolder.newInstance(sku, storeCode, frozenQty));
        for (InvMachineSet machineSet : machineSets) {
            String _sku = machineSet.getSubSku();
            Integer _frozenQty = machineSet.getMenge().intValue() * frozenQty;
            stockHolders.add(StockHolder.newInstance(_sku, basStores.getStoreCode(), _frozenQty));
        }

        boolean checkSucc = machineSets.size() <= 0 || verifyFrozenHolders(stockHolders);
        if (!checkSucc) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("有无效套机组合, sku=" + sku);
            log.info("门店占货:有无效套机组合, sku=" + sku);
            return result;
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        TransactionStatus statusShop = transactionManagerShop.getTransaction(def);
        try {
            Map<String, BaseStock> skuStockMap = new HashMap<String, BaseStock>();
            for (StockHolder stockHolder : stockHolders) {
                InvBaseStore baseStore = frozenStoreStock(refNo, stockHolder.getSku(),
                    stockHolder.getSecCode(), source, InvSection.W10, billType.getCode(),
                    stockHolder.getChangeQty(), optUser);

                //计算此子件对应的套机
                BaseStock baseStock = new BaseStock();
                baseStock.setSku(stockHolder.getSku());
                baseStock.setCode(stockHolder.getSecCode());
                Integer qty = baseStore.getStockQty() - baseStore.getFrozenQty();
                baseStock.setStockQty(qty > 0 ? qty : 0);
                baseStock.setStockType(BaseStock.STOCKTYPE_EC);
                baseStock.setItemProperty(InvSection.W10);
                List<BaseStock> storeList = StockBizHelper.regroupStockQtyBySku(invBaseStoreService,
                    stockInvMachineSetService, baseStock);
                for (BaseStock store : storeList) {
                    if (!skuStockMap.containsKey(store.getSku())) {
                        skuStockMap.put(store.getSku(), store);
                    } else
                        if (skuStockMap.get(store.getSku()).getStockQty() > store.getStockQty()) {
                        skuStockMap.put(store.getSku(), store);
                    }
                }
            }
            //更新InvStock以及相关到货通知
            for (Map.Entry<String, BaseStock> stockEntry : skuStockMap.entrySet()) {
                //联动更新销售库存信息
                BaseStock store = stockEntry.getValue();
                updateStore(store.getStockQty(), store.getCode(), store.getSku(), InvSection.W10,
                    new Date().getTime() / 1000);

                //同步EC库存到商城
                synStoreStock2Shop(store.getSku(), store.getStockQty(), basStores);
            }

            transactionManagerShop.commit(statusShop);
            transactionManagerStock.commit(status);
            result.setResult(storeCode);
        } catch (BusinessException e) {
            transactionManagerShop.rollback(statusShop);
            transactionManagerStock.rollback(status);
            boolean isSuc = ExceptionCode.USER_CONCURRENCE_CODE.equals(e.getCode());
            String message;
            if (isSuc) {
                message = ExceptionCode.USER_CONCURRENCE_CODE + ":" + e.getMessage();
                log.info(message);
            } else {
                message = e.getMessage();
                log.info(message);
            }
            result.setSuccess(isSuc);
            result.setResult(null);
            result.setMessage(message);
        } catch (Exception e) {
            transactionManagerShop.rollback(statusShop);
            transactionManagerStock.rollback(status);
            log.error("门店库存占用库存失败：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("门店库存占用库存失败：" + e.getMessage());
        }
        return result;
    }

    private void updateStore(Integer stockQty, String storeCode, String sku, String itemProperty,
                             Long storeTs) {
        if (stockQty < 0) {
            stockQty = 0;
        }
        InvStore invStore = invStoreService.getByStoreCodeAndSku(sku, storeCode, itemProperty);
        if (invStore == null) {
            invStore = new InvStore();
            invStore.setStoreCode(storeCode);
            invStore.setSku(sku);
            invStore.setStockQty(stockQty);
            invStore.setItemProperty(itemProperty);
            invStore.setStoreTs(storeTs);
            //添加产品型号、品类、产品组、店铺名称
            this.assignmentValue2InvStore(invStore);
            Integer effect = invStoreService.insert(invStore);
            log.info("插入invStore成功，code:" + storeCode + ", sku:" + sku + ", stockQty:" + stockQty
                     + ", effect:" + effect);
        } else {
            invStore.setStockQty(stockQty);
            //添加产品型号、品类、产品组、店铺名称
            this.assignmentValue2InvStore(invStore);
            Integer effect = invStoreService.updateInvStore(invStore);
            log.info("更新invStore成功，storeCode:" + storeCode + ", sku:" + sku + ", stockQty:"
                     + stockQty + ", effect:" + effect);
        }
    }

    private InvBaseStore frozenStoreStock(String refNo, String sku, String storeCode, String source,
                                          String itemProperty, String billType, Integer frozenQty,
                                          String optUser) {
        InvBaseStore baseStore = invBaseStoreService.getByItemPropertyForUpdate(sku, storeCode,
            itemProperty);
        if (baseStore == null) {
            throw new BusinessException("找不到" + storeCode + "对应的物料编码" + sku);
        }
        Integer oldFrozenQty = baseStore.getFrozenQty();
        Integer newFrozenQty = oldFrozenQty + frozenQty;
        Date time = new Date();
        baseStore.setFrozenQty(newFrozenQty);
        baseStore.setUpdateTime(time);
        int effectRows = invBaseStoreService.updateQtyForFrozen(baseStore);
        if (effectRows < 1) {
            throw new BusinessException("库位" + storeCode + ",物料编码:" + sku + "，库存不足");
        }
        //如果发现冻结记录已经存在，此时属于并发情况
        InvStoreLock stockLock = new InvStoreLock();
        stockLock.setSku(sku);
        stockLock.setStoreCode(storeCode);
        stockLock.setRefNo(refNo);
        stockLock.setLockQty(frozenQty);
        stockLock.setReleaseQty(0);
        stockLock.setLockTime(new Date());
        stockLock.setOptUser(optUser);
        stockLock.setSource(source);
        stockLock.setItemProperty(InvSection.W10);
        int effect = invStoreLockService.insert(stockLock);
        if (effect <= 0) {
            throw new BusinessException(
                "[门店占用库存]已经存在该网单的冻结记录, refNo=" + refNo + ",sku=" + sku + ", storeCode=" + storeCode,
                ExceptionCode.USER_CONCURRENCE_CODE);
        }
        return baseStore;
    }

    private boolean verifyFrozenHolders(List<StockHolder> holders) {
        if (holders == null || holders.size() <= 0) {
            log.info("门店占货: 空的对象，无需检查");
            return false;
        }
        List<String> checkList = new ArrayList<String>();
        boolean checkSucc = true;
        for (StockHolder holder : holders) {
            String pKey = holder.getSku() + holder.getSecCode();
            if (checkList.contains(pKey)) {
                checkSucc = false;
                break;
            } else {
                checkList.add(pKey);
            }
        }
        return checkSucc;

    }

    /**
     * 释放EC占用库存，默认操作人为sys
     * @param refNo
     * @param sku
     * @param storeCode
     * @param releaseQty
     */
    public void releaseFrozenQty(String refNo, String sku, String storeCode, Integer releaseQty,
                                 boolean calTotalStockFlag) {

        this.releaseFrozenQty(refNo, sku, storeCode, releaseQty, "sys", calTotalStockFlag);
    }

    /**
     * 释放EC占用库存
     * @param refNo
     * @param sku
     * @param storeCode
     * @param
     * @param
     * @param optUser
     * @return
     */
    private void releaseFrozenQty(String refNo, String sku, String storeCode, Integer releaseQty,
                                  String optUser, boolean calTotalStockFlag) {

        if (releaseQty == null || releaseQty < 1) {
            throw new BusinessException("释放数量不能小于1");
        }
        if (StringUtil.isEmpty(refNo)) {
            throw new BusinessException("要释放的单据号不能为空");
        }
        //处理每种物料总计需要释放的库存数量（如果是套机，将套机拆分成子件）
        List<StockHolder> stockHolders = new ArrayList<StockHolder>();
        List<InvMachineSet> machineSets = stockInvMachineSetService.getByMainSku(sku);
        if (machineSets.size() <= 0) {
            stockHolders.add(StockHolder.newInstance(sku, null, releaseQty));
        } else {
            for (InvMachineSet machineSet : machineSets) {
                Integer num = machineSet.getMenge().intValue();
                stockHolders
                    .add(StockHolder.newInstance(machineSet.getSubSku(), null, releaseQty * num));
            }
        }
        //通过占用记录表，确定实际要释放的库存记录
        List<StockHolder> stockHoldersForRelease = new ArrayList<StockHolder>();
        for (StockHolder stockHolder : stockHolders) {
            List<InvStoreLock> storeLocks = invStoreLockService.getNotReleased(refNo, stockHolder.getSku(),
                storeCode, InvSection.W10);
            Integer qty = stockHolder.getChangeQty();
            if (storeLocks.size() <= 0) {
                log.info("不存在占用记录[refno=" + refNo + "，sku=" + sku + "，storeCode=" + storeCode
                         + "]，不能释放（释放数：" + qty + "）");
                return;
            }
            for (InvStoreLock stockLock : storeLocks) {
                if (qty <= 0)
                    break;
                Integer _releaseQty = stockLock.getLockQty() - stockLock.getReleaseQty();
                if (_releaseQty > qty)
                    _releaseQty = qty;
                qty -= _releaseQty;
                stockHoldersForRelease.add(StockHolder.newInstance(stockLock.getSku(),
                    stockLock.getStoreCode(), _releaseQty));
            }
            if (qty > 0) {
                throw new BusinessException("要释放的数量大于原冻结数量，释放数量为：" + stockHolder.getChangeQty());
            }
        }
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManagerStock.getTransaction(def);
        TransactionStatus statusShop = transactionManagerShop.getTransaction(def);
        try {
            for (StockHolder holder : stockHoldersForRelease) {
                //更新店铺库存占用记录
                releaseStoreLockQty(holder.getSku(), holder.getSecCode(), refNo, holder.getChangeQty(), optUser);
                //更新店铺库存
                InvBaseStore baseStore = releaseBaseStoreQty(holder.getSku(), holder.getSecCode(),
                    holder.getChangeQty(), calTotalStockFlag);
                if (baseStore == null) {
                    throw new BusinessException(
                        "冻结记录释放失败, sku:" + holder.getSku() + ", storeCode:" + holder.getSecCode());
                }
                //更新店铺销售库存
                releaseStoreQty(holder.getSku(), holder.getSecCode(),
                    baseStore.getStockQty() - baseStore.getFrozenQty(), baseStore.getItemProperty(),
                    baseStore.getStoreTs());
            }

            transactionManagerShop.commit(statusShop);
            transactionManagerStock.commit(status);
        } catch (Exception e) {
            transactionManagerShop.rollback(statusShop);
            transactionManagerStock.rollback(status);
            log.error("释放店铺库存出现异常：" + e.getMessage());
            throw new BusinessException("释放店铺库存出现异常：" + e.getMessage());
        }

    }

    /**
     * 更新店铺销售库存
     * @param sku
     * @param storeCode
     * @param qty
     * @param itemProperty
     * @param storeTs
     */
    private void releaseStoreQty(String sku, String storeCode, Integer qty, String itemProperty,
                                 Long storeTs) {
        //计算销售库存数,包括涉及到的套机计算
        BaseStock baseStock = new BaseStock();
        baseStock.setSku(sku);
        baseStock.setCode(storeCode);
        baseStock.setStockQty(qty > 0 ? qty : 0);
        baseStock.setItemProperty(itemProperty);
        baseStock.setStockType(BaseStock.STOCKTYPE_EC);
        baseStock.setItemProperty(InvSection.W10);
        List<BaseStock> storeList = StockBizHelper.regroupStockQtyBySku(invBaseStoreService,
            stockInvMachineSetService, baseStock);
        for (BaseStock store : storeList) {
            //联动更新销售库存信息
            Integer stockQty = store.getStockQty();
            if (stockQty < 0) {
                stockQty = 0;
            }
            InvStore invStore = invStoreService.getByStoreCodeAndSku(store.getSku(), storeCode,
                itemProperty);
            if (invStore == null) {
                invStore = new InvStore();
                invStore.setStoreCode(storeCode);
                invStore.setSku(store.getSku());
                invStore.setStockQty(stockQty);
                invStore.setItemProperty(itemProperty);
                invStore.setStoreTs(storeTs);
                //添加产品型号、品类、产品组、店铺名称
                this.assignmentValue2InvStore(invStore);
                Integer effect = invStoreService.insert(invStore);
                log.info("插入invStore成功，code:" + storeCode + ", sku:" + store.getSku()
                         + ", stockQty:" + stockQty + ", effect:" + effect);
            } else {
                invStore.setStockQty(stockQty);
                //添加产品型号、品类、产品组、店铺名称
                this.assignmentValue2InvStore(invStore);
                Integer effect = invStoreService.updateInvStore(invStore);
                log.info("更新invStore成功，storeCode:" + storeCode + ", sku:" + store.getSku()
                         + ", stockQty:" + stockQty + ", effect:" + effect);
            }
            //同步EC库存到商城
            BasStores basStores = getStoresByStoreCode(storeCode, null);
            if (basStores != null) {//释放库存时，不分店铺类型，但是店铺不是旗舰店，则不会同步库存，也不再同步给商城
                synStoreStock2Shop(store.getSku(), stockQty, basStores);
            }

        }
    }

    /**
     * 给InvStore添加的字段赋值
     * @param
     */
    private void assignmentValue2InvStore(InvStore invStore) {
        ItemBase itemBase = getItemBaseBySku(invStore.getSku());
        if (itemBase != null) {
            String productName = itemBase.getMaterialDescription();//产品型号
            if (!StringUtil.isEmpty(productName) && StringUtil.isEmpty(invStore.getProductName())) {
                invStore.setProductName(productName);
            }
            ItemAttribute itemAttribute = getItemAttributeByValueSetIdAndValue("ProductGroup",
                itemBase.getDepartment());
            if (itemAttribute != null) {
                String productTypeName = itemAttribute.getCbsCategory();//品类
                if (!StringUtil.isEmpty(productTypeName)
                    && StringUtil.isEmpty(invStore.getProductTypeName())) {
                    invStore.setProductTypeName(productTypeName);
                }
                String productGroupName = itemAttribute.getValueMeaning();//产品组
                if (!StringUtil.isEmpty(productGroupName)
                    && StringUtil.isEmpty(invStore.getProductGroupName())) {
                    invStore.setProductGroupName(productGroupName);
                }
            }
        }
        //查询店铺名称
        BasStores basStores = getStoresByStoreCode(invStore.getStoreCode(), null);
        if (basStores != null) {
            String storeName = basStores.getStoreName();
            if (!StringUtil.isEmpty(storeName) && StringUtil.isEmpty(invStore.getStoreName())) {
                invStore.setStoreName(storeName);
            }
        }
    }

    /**
     * 由店铺码查询店铺信息
     * @param storeCode
     * @param storeStatus
     * @return
     */
    private BasStores getStoresByStoreCode(String storeCode, Integer storeStatus) {

        //        BasStores basStores = new BasStores();
        //        basStores.setStoreCode(storeCode);
        //        basStores.setStatus(storeStatus);
        //        basStores.setStoreType(BasStores.STOR_TYPE_1);//店铺类型:1旗舰店
        //        ServiceResult<List<BasStores>> result = storesService.getBasStoresByCondition(basStores,
        //            null);//不需要分页
        //        if (!result.getSuccess() || result == null) {
        //            return null;
        //        }
        //        List<BasStores> basStoresList = result.getResult();
        //        if (basStoresList == null || basStoresList.size() <= 0) {
        //            return null;
        //        }
        //        return basStoresList.get(0);//此条件下查询，只可能有一条数据
        SgStore sg = sgStoreService.getSgStore(storeCode, BasStores.STOR_TYPE_1, storeStatus);
        if (sg == null) {
            return null;
        }
        BasStores basStores = new BasStores();
        basStores.setStoreId(sg.getOwnerId());
        basStores.setStoreCode(sg.getStoreCode());
        basStores.setStoreName(sg.getStoreName());
        return basStores;

    }

    /**
     * 由店铺码查询店铺信息，不限制旗舰店
     * @param storeCode
     * @param storeStatus
     * @return
     */
    private BasStores getStoresByStoreCodeNoLimit(String storeCode, Integer storeStatus) {

        SgStore sg = sgStoreService.getSgStore(storeCode, null, storeStatus);
        if (sg == null) {
            return null;
        }
        BasStores basStores = new BasStores();
        basStores.setStoreId(sg.getOwnerId());
        basStores.setStoreCode(sg.getStoreCode());
        basStores.setStoreName(sg.getStoreName());
        return basStores;
    }

    private List<BasStores> getStoresByRegionId(Integer regionId, Integer storeStatus,
                                                String department) {
        return this.getStoresByRegionId(regionId, storeStatus, department, true);
    }

    /**
     * 由区县id查询店铺信息
     * @param regionId
     * @param storeStatus
     * @param department
     * @param enableRegionOn
     * @return
     */
    private List<BasStores> getStoresByRegionId(Integer regionId, Integer storeStatus,
                                                String department, boolean enableRegionOn) {

        //        BasStoreRegions basStoreRegions = new BasStoreRegions();
        //        basStoreRegions.setRegionId(regionId);
        //        basStoreRegions.setGroupCode(department);//产品组
        //        if (enableRegionOn) {
        //            basStoreRegions.setStatus(BasStoreRegions.STORE_REGION_STATUS_1);//状态0不使用1使用
        //        }
        //
        //        BasStores basStores = new BasStores();
        //        basStores.setStatus(storeStatus);
        //        basStores.setStoreType(BasStores.STOR_TYPE_1);//店铺类型:1旗舰店
        //        ServiceResult<List<BasStores>> result = storesService.getBasStoresListByCondition(basStores,
        //            basStoreRegions);
        //        if (!result.getSuccess() || result == null) {
        //            return null;
        //        }
        //        return result.getResult();

        List<SgStore> sgList = sgStoreService.getSgStoreList(regionId, BasStores.STOR_TYPE_1,
            storeStatus, department);
        if (sgList == null || sgList.isEmpty()) {
            return null;
        }
        List<BasStores> basStoreList = new ArrayList<BasStores>(sgList.size());
        for (SgStore sg : sgList) {
            BasStores basStore = new BasStores();
            basStore.setStoreId(sg.getOwnerId());
            basStore.setStoreCode(sg.getStoreCode());
            basStore.setStoreName(sg.getStoreName());
            basStoreList.add(basStore);
        }
        return basStoreList;
    }

    /**
     * 由区县id查询店铺码
     * @param regionId 区县id
     * @return 店铺码
     */
    @Deprecated
    public String getStoreCodeByRegionId(Integer regionId) {
        List<BasStores> basStoresList = getStoresByRegionId(regionId, null, null);
        if (basStoresList == null || basStoresList.size() <= 0) {
            return null;
        }
        return basStoresList.get(0).getStoreCode();
    }

    /**
     * 由区县id查询店铺码
     * @param regionId
     * @param regionStatus
     * @return
     */
    public List<String> getStoreCodeByRegionId(Integer regionId, boolean regionStatus) {
        List<BasStores> basStoresList = getStoresByRegionId(regionId, null, null, regionStatus);
        if (basStoresList == null || basStoresList.size() <= 0) {
            return null;
        }
        Set<String> storeCodeSet = new HashSet<String>();
        for (BasStores basStores : basStoresList) {
            storeCodeSet.add(basStores.getStoreCode());
        }
        List<String> storeCodeList = new ArrayList<String>(storeCodeSet);
        return storeCodeList;
    }

    /**
     * 通过sku查询item_base
     * @param sku
     * @return
     */
    private ItemBase getItemBaseBySku(String sku) {
        ServiceResult<ItemBase> result = itemService.getItemBaseBySku(sku);
        if (result == null || !result.getSuccess())
            return null;
        return result.getResult();
    }

    /**
    * 查询ItemAttribute
    * @param valueSetId
    * @param value
    * @return
    */
    private ItemAttribute getItemAttributeByValueSetIdAndValue(String valueSetId, String value) {
        ServiceResult<ItemAttribute> result = itemService
            .getItemAttributeByValueSetIdAndValue(valueSetId, value);
        if (result == null || !result.getSuccess())
            return null;
        return result.getResult();
    }

    /**
     * 更新店铺库存占用记录
     * @param sku
     * @param storeCode
     * @param refNo
     * @param releaseQty
     * @param optUser
     */
    private void releaseStoreLockQty(String sku, String storeCode, String refNo, Integer releaseQty,
                                     String optUser) {
        InvStoreLock storeLock = invStoreLockService.getNotReleasedForUpdate(refNo, sku, storeCode,
            InvSection.W10);
        if (storeLock == null) {
            throw new BusinessException("占用记录不存在！");
        } else {
            int notReleaseQty = storeLock.getLockQty() - storeLock.getReleaseQty();
            boolean isSuccess = false;
            if (notReleaseQty >= releaseQty) {
                Integer effectNum = invStoreLockService.updateReleaseQty(storeLock.getId(), releaseQty,
                    optUser, InvSection.W10);
                if (effectNum >= 1) {
                    isSuccess = true;
                }
            }
            if (!isSuccess) {
                throw new BusinessException("占用记录（sku=" + sku + "，storeCode=" + storeCode
                                            + "，refNo=" + refNo + "），可释放数量小于要释放的数量");
            }
        }
    }

    /**
     * 更新店铺库存
     * @param sku
     * @param storeCode
     * @param releaseQty
     */
    private InvBaseStore releaseBaseStoreQty(String sku, String storeCode, Integer releaseQty,
                                             boolean calTotalStockFlag) {

        InvBaseStore baseStore = invBaseStoreService.getStore(sku, storeCode, InvSection.W10);
        if (baseStore == null) {
            throw new BusinessException("店铺库存不存在");
        }
        Integer effectNum = 0;
        if (calTotalStockFlag) {
            effectNum = invBaseStoreService.releaseStockQtyAndFrozenQty(baseStore.getId(), releaseQty);
        } else {
            effectNum = invBaseStoreService.releaseFrozenQty(baseStore.getId(), releaseQty);
        }

        if (effectNum < 1) {
            throw new BusinessException("扣减店铺库存占用数量失败");
        }
        return invBaseStoreService.getStore(sku, storeCode, InvSection.W10);

    }

    public List<InvStoreRegions> getInvStoreRegionByRegionId(Integer regionId, Integer status) {
        return invStoreRegionsService.getByRegionId(regionId, status,
            InvStoreRegions.HPREMARK_STATUS_ON);
    }

    /**
     * 同步EC库存到商城
     * @param
     * @param basStores
     */
    private void synStoreStock2Shop(String sku, Integer stockQty, BasStores basStores) {

        MctStoreProducts mctStoreProducts = mctStoreProductsService
            .getByStoreIdStoreCodeSku(basStores.getStoreId(), basStores.getStoreCode(), sku);
        if (mctStoreProducts == null) {
            MctStoreProducts storeProducts = new MctStoreProducts();
            storeProducts.setStoreId(basStores.getStoreId());
            storeProducts.setStoreCode(basStores.getStoreCode());
            storeProducts.setSku(sku);
            storeProducts.setStockNum(stockQty);
            storeProducts.setOnSale(MctStoreProducts.OFF_SALE);//默认为下架
            storeProducts.setCreateUserId(0);
            storeProducts.setLastUpdateUserId(0);
            storeProducts.setLastUpdateTime(new Date());
            storeProducts.setIsAuto(MctStoreProducts.IS_AUTO_INIT);//初始
            mctStoreProductsService.insert(storeProducts);
        } else {
            mctStoreProductsService.updateStockNumById(mctStoreProducts.getStoreProductId(), stockQty);
        }
    }

    public List<Stock> getEStoreBySkuAndStoreCodeList(String sku, List<String> storeCodeList) {

        List<InvStore> invStores = new ArrayList<InvStore>();
        if (storeCodeList != null && storeCodeList.size() > 0) {
            if (storeCodeList.size() <= 50) {
                invStores = invStoreService.getEStoreBySkuAndStoreCodeList(sku,
                    StockBizHelper.stringWithSingleQuote(storeCodeList), InvSection.W10);
            } else {
                int temp = storeCodeList.size();
                for (int i = 0; i < storeCodeList.size(); i = i + 50) {
                    if (temp <= 50) {
                        List<InvStore> tempInvStore = invStoreService
                            .getEStoreBySkuAndStoreCodeList(sku,
                                StockBizHelper.stringWithSingleQuote(
                                    storeCodeList.subList(i, storeCodeList.size())),
                            InvSection.W10);
                        invStores.addAll(tempInvStore);
                    } else {
                        List<InvStore> tempInvStore = invStoreService.getEStoreBySkuAndStoreCodeList(
                            sku,
                            StockBizHelper.stringWithSingleQuote(storeCodeList.subList(i, i + 50)),
                            InvSection.W10);
                        invStores.addAll(tempInvStore);
                    }
                    temp = temp - 50;
                }
            }
        } else {
            invStores = invStoreService.getEStoreBySkuAndStoreCodeList(sku, null, InvSection.W10);
        }

        if (invStores == null || invStores.size() <= 0) {
            return null;
        }
        List<Stock> stocks = new ArrayList<Stock>();
        for (InvStore invStore : invStores) {
            Stock stock = new Stock();
            stock.setSku(invStore.getSku());
            stock.setStoreCode(invStore.getStoreCode());
            stock.setStockType(BaseStock.STOCKTYPE_EC);
            stock.setAvaibleQty(invStore.getStockQty());
            stock.setUpdateTime(invStore.getUpdateTime());
            stocks.add(stock);
        }
        return stocks;

    }

    public List<Stock> getEStoreBySkuListAndStoreCode(List<String> skuList, String storeCode) {

        List<InvStore> invStores = new ArrayList<InvStore>();
        if (skuList != null && skuList.size() > 0) {
            if (skuList.size() <= 50) {
                invStores = invStoreService.getEStoreBySkuListAndStoreCode(
                    StockBizHelper.stringWithSingleQuote(skuList), storeCode, InvSection.W10);
            } else {
                int temp = skuList.size();
                for (int i = 0; i < skuList.size(); i = i + 50) {
                    if (temp <= 50) {
                        List<InvStore> tempInvStore = invStoreService
                            .getEStoreBySkuListAndStoreCode(StockBizHelper.stringWithSingleQuote(
                                skuList.subList(i, skuList.size())), storeCode, InvSection.W10);
                        invStores.addAll(tempInvStore);
                    } else {
                        List<InvStore> tempInvStore = invStoreService.getEStoreBySkuListAndStoreCode(
                            StockBizHelper.stringWithSingleQuote(skuList.subList(i, i + 50)),
                            storeCode, InvSection.W10);
                        invStores.addAll(tempInvStore);
                    }
                    temp = temp - 50;
                }
            }
        } else {
            invStores = invStoreService.getEStoreBySkuListAndStoreCode(null, storeCode, InvSection.W10);
        }
        if (invStores == null || invStores.size() <= 0) {
            return null;
        }
        List<Stock> stocks = new ArrayList<Stock>();
        for (InvStore invStore : invStores) {
            Stock stock = new Stock();
            stock.setSku(invStore.getSku());
            stock.setStoreCode(invStore.getStoreCode());
            stock.setStockType(BaseStock.STOCKTYPE_EC);
            stock.setAvaibleQty(invStore.getStockQty());
            stock.setUpdateTime(invStore.getUpdateTime());
            stocks.add(stock);
        }
        return stocks;

    }

    /**
     * TB卖EStore库存，由时间查询增量库存
     * @param startTime
     * @return
     */
    public List<Stock> getEStoreIncrement(Date startTime) {

        List<InvStore> invStores = invStoreService.getChangedStockQty(startTime);
        if (invStores == null || invStores.size() <= 0) {
            return null;
        }
        List<Stock> stocks = new ArrayList<Stock>();
        for (InvStore invStore : invStores) {
            Stock stock = new Stock();
            stock.setSku(invStore.getSku());
            stock.setStoreCode(invStore.getStoreCode());
            stock.setStockType(BaseStock.STOCKTYPE_EC);
            stock.setAvaibleQty(invStore.getStockQty());
            stock.setUpdateTime(invStore.getUpdateTime());
            stocks.add(stock);
        }
        return stocks;
    }

    /**
     * 查询一段时间之后没有释放的库存
     * @param lockTime
     * @param topx
     * @return
     */
    public List<InvStoreLock> getNoReleaseByLockTime(String lockTime, Integer topx) {

        return invStoreLockService.getNoReleaseByLockTime(lockTime, topx);

    }

   /* public void setInvMachineSetDao(InvMachineSetDao stockInvMachineSetService) {
        this.stockInvMachineSetService = stockInvMachineSetService;
    }

    public void setInvStoreLockDao(InvStoreLockDao invStoreLockService) {
        this.invStoreLockService = invStoreLockService;
    }

    public void setTransactionManagerStock(DataSourceTransactionManager transactionManagerStock) {
        this.transactionManagerStock = transactionManagerStock;
    }

    public void setInvBaseStoreDao(InvBaseStoreDao invBaseStoreService) {
        this.invBaseStoreService = invBaseStoreService;
    }

    public void setInvStoreSynchDao(InvStoreSynchDao invStoreSynchService) {
        this.invStoreSynchDao = invStoreSynchDao;
    }

    public void setInvStoreDao(InvStoreDao invStoreDao) {
        this.invStoreDao = invStoreDao;
    }

    public void setInvStoreRegionsDao(InvStoreRegionsDao invStoreRegionsService) {
        this.invStoreRegionsService = invStoreRegionsService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }*/

    //    public void setStoresService(StoresService storesService) {
    //        this.storesService = storesService;
    //    }

   /* public void setMctStoreProductsDao(MctStoreProductsDao mctStoreProductsService) {
        this.mctStoreProductsService = mctStoreProductsService;
    }

    public void setTransactionManagerShop(DataSourceTransactionManager transactionManagerShop) {
        this.transactionManagerShop = transactionManagerShop;
    }

    public void setSgFlagShipStoreAuthorizationDao(SgFlagShipStoreAuthorizationDao sgFlagShipStoreAuthorizationService) {
        this.sgFlagShipStoreAuthorizationService = sgFlagShipStoreAuthorizationService;
    }

    public void setSgStoreDao(SgStoreDao sgStoreService) {
        this.sgStoreService = sgStoreService;
    }
*/
    /**
     * 获得锁定库存数量
     * @param sku 物料编码
     * @param storeId 店铺ID
     * @param scode 库位
     * @param type 查询类型 1:WA 2:EC 3: 顺逛自由库存
     * @param refNo 网单号（如果有值，代表查询下单锁中除去此单的剩余的下单锁总数）
     * @return
     */
    public Integer orderLockByRefNo(String sku, String storeId, String scode, Integer type,
                                    String refNo) {
        if (StringUtils.isEmpty(sku)) {
            throw new BusinessException("物料编码不能为空");
        }

        if (!SgConstants.STOCK_LOCK_TYPE.WA.equals(type)) { //非WA需要传店铺ID
            if (StringUtils.isEmpty(storeId)) {
                throw new BusinessException("店铺ID不能为空");
            }
        }
        if (!SgConstants.STOCK_LOCK_TYPE.EC.equals(type)) { //非EC需要传库位
            if (StringUtils.isEmpty(scode)) {
                throw new BusinessException("库位不能为空");
            }
        }
        Integer lockQty = invStockOrderLockService.findLockQtyByScodeAndStoreCodeAndSku(scode, storeId,
            sku, refNo);
        return lockQty;
    }

    public SgStore getSgStoreById(Integer storeId) {
        return sgStoreService.getSgStoreById(storeId);
    }

}
