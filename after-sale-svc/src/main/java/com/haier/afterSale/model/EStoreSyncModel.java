package com.haier.afterSale.model;

import com.haier.afterSale.helper.Handler;
import com.haier.afterSale.helper.StockBizHelper;
import com.haier.afterSale.helper.TransHandler;
import com.haier.afterSale.services.OrderThirdCenterEStoreInfoServiceImpl;
import com.haier.afterSale.services.OrderThirdCenterItemServiceImpl;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.BasStores;
import com.haier.shop.model.EStoreStock;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.MctStoreProducts;
import com.haier.shop.model.StockHolder;
import com.haier.shop.service.MctStoreProductsService;
import com.haier.stock.model.BaseStock;
import com.haier.stock.model.InvBaseStore;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStore;
import com.haier.stock.model.InvStoreSynch;
import com.haier.stock.service.InvBaseStoreService;
import com.haier.stock.service.InvStoreService;
import com.haier.stock.service.InvStoreSynchService;
import com.haier.stock.service.StockInvMachineSetService;
import com.haier.stock.model.SgStore;
import com.haier.stock.service.SgStoreService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;

import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EStoreSyncModel {
    private static Logger LOGGER = LogManager.getLogger(EStoreSyncModel.class);
    @Autowired
    private StockInvMachineSetService invMachineSetDao;
    @Autowired
    private InvBaseStoreService invBaseStoreDao;
    @Autowired
    private InvStoreService invStoreDao;
    @Autowired
    private InvStoreSynchService invStoreSynchDao;
    @Autowired
    private OrderThirdCenterItemServiceImpl itemService;
    @Autowired
    private OrderThirdCenterEStoreInfoServiceImpl eStoreInfoServiceImpl;


    private DataSourceTransactionManager transactionManager;

    //    private StoresService                storesService;
    @Autowired
    private MctStoreProductsService mctStoreProductsDao;
    @Autowired
    private SgStoreService sgStoreDao;
/*
    @Autowired
    private DataSourceTransactionManager transactionManagerShop;
*/

    public String storeStockSync(final InvStoreSynch invStoreSynch) {

        final BasStores basStores = getStoresByStoreCode(invStoreSynch.getStoreCode(), null);
        if (basStores == null) {
            String message = "店铺码[" + invStoreSynch.getStoreCode() + "]，没有对应的店铺信息";
            //记录日志
            updateStoreSynch(invStoreSynch.getId(), InvStoreSynch.STATUS_REDO, message);
            return message;
        }

        //物料检查
        ServiceResult<ItemBase> itemBaseService = itemService
            .getItemBaseBySku(invStoreSynch.getSku());
        if (!itemBaseService.getSuccess()) {
            String message = "查询物料发生异常";
            //记录日志
            updateStoreSynch(invStoreSynch.getId(), InvStoreSynch.STATUS_REDO, message);
            return message;
        } else {
            ItemBase itemBase = itemBaseService.getResult();
            String message;
            if (itemBase == null || StringUtil.isEmpty(itemBase.getMaterialCode())) {
                message = "系统不存在此物料";
                updateStoreSynch(invStoreSynch.getId(), InvStoreSynch.STATUS_REDO, message);
                return message;
            }

        }
        if (!InvSection.W10.equals(invStoreSynch.getItemProperty())) {
            String message = "不能同步此批次";
            updateStoreSynch(invStoreSynch.getId(), InvStoreSynch.STATUS_REDO, message);
            return message;
        }
        //检查更新时间
        Long updateTs = convertDate(invStoreSynch.getUpdateTime()).getTime() / 1000;
        InvStore invStore = invStoreDao.getByStoreCodeAndSku(invStoreSynch.getSku(),
            invStoreSynch.getStoreCode(), invStoreSynch.getItemProperty());
        if (invStore != null) {
            Long existUpdateTs = invStore.getStoreTs();
            if (existUpdateTs > updateTs) {
                updateStoreSynch(invStoreSynch.getId(), InvStoreSynch.STATUS_EXPIRE, "expire");
                return "expire";
            }
        }
        TransHandler proxyHandler = new TransHandler(transactionManager);
        try {
            proxyHandler.handler(new Handler() {
                List<StockHolder> stockHolderList = new ArrayList<StockHolder>();

                public boolean beforeProcess() {
                    List<InvMachineSet> machineSets = invMachineSetDao
                        .getByMainSku(invStoreSynch.getSku());
                    StockHolder stockHolder;
                    if (machineSets.size() <= 0) {
                        stockHolder = new StockHolder(invStoreSynch.getSku(),
                            invStoreSynch.getStoreCode(), invStoreSynch.getStockQty());
                        stockHolderList.add(stockHolder);
                    } else {
                        for (InvMachineSet machineSet : machineSets) {
                            String _sku = machineSet.getSubSku();
                            Integer _newQty = machineSet.getMenge().intValue()
                                              * invStoreSynch.getStockQty();
                            stockHolder = new StockHolder(_sku, invStoreSynch.getStoreCode(),
                                _newQty);
                            stockHolderList.add(stockHolder);
                        }
                    }
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("同步到EHAIER库存，检查套机结束， sku=" + invStoreSynch.getSku() + ", qty="
                                     + invStoreSynch.getStockQty() + ",storeCode="
                                     + invStoreSynch.getStoreCode());
                    }
                    return true;
                }

//                @Override
                public boolean process() throws Exception {

                    for (StockHolder holder : stockHolderList) {
                        Integer newStockQty = holder.getChangeQty();
                        if (newStockQty < 0)
                            newStockQty = 0;
                        //查找库存
                        InvBaseStore invBaseStore = invBaseStoreDao.getByItemPropertyForUpdate(
                            holder.getSku(), invStoreSynch.getStoreCode(),
                            invStoreSynch.getItemProperty());
                        //不存在添加
                        Date dateTime = new Date();
                        boolean isInsert = false;
                        if (invBaseStore == null) {
                            isInsert = true;
                            invBaseStore = new InvBaseStore();
                            invBaseStore.setSku(holder.getSku());
                            invBaseStore.setStoreCode(holder.getSecCode());
                            invBaseStore.setStockQty(newStockQty);
                            invBaseStore.setFrozenQty(0);
                            invBaseStore.setStoreTs(
                                convertDate(invStoreSynch.getUpdateTime()).getTime() / 1000);
                            invBaseStore.setCreateTime(dateTime);
                        } else {
                            //计算实际库存：实际库存至少为已经冻结的
                            newStockQty = holder.getChangeQty();
                        }
                        //设置实际库存
                        invBaseStore.setItemProperty(invStoreSynch.getItemProperty());
                        invBaseStore.setStockQty(newStockQty);
                        invBaseStore.setUpdateTime(dateTime);
                        //更新InvBaseStock
                        if (isInsert) {
                            invBaseStoreDao.insert(invBaseStore);
                        } else {
                            invBaseStoreDao.updateStockQty(invBaseStore.getId(),
                                convertDate(invStoreSynch.getUpdateTime()).getTime() / 1000,
                                newStockQty);
                        }
                        //计算销售库存数,包括涉及到的套机计算
                        BaseStock baseStock = new BaseStock();
                        baseStock.setSku(holder.getSku());
                        baseStock.setCode(invStoreSynch.getStoreCode());
                        Integer qty = invBaseStore.getStockQty() - invBaseStore.getFrozenQty();
                        baseStock.setStockQty(qty > 0 ? qty : 0);
                        baseStock.setStockType(BaseStock.STOCKTYPE_EC);
                        baseStock.setItemProperty(invStoreSynch.getItemProperty());
                        List<BaseStock> storeList = StockBizHelper
                            .regroupStockQtyBySku(invBaseStoreDao, invMachineSetDao, baseStock);

                        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                        /*TransactionStatus statusShop = transactionManagerShop.getTransaction(def);*/
                        try {
                            for (BaseStock store : storeList) {
                                //联动更新销售库存信息
                                updateStore(store.getStockQty(), store.getCode(), store.getSku(),
                                    invStoreSynch.getItemProperty(),
                                    convertDate(invStoreSynch.getUpdateTime()).getTime() / 1000);
                                //同步EC库存到商城
                                synStoreStock2Shop(store.getSku(), store.getStockQty(), basStores);

                            }
                            updateStoreSynch(invStoreSynch.getId(), InvStoreSynch.STATUS_DONE,
                                "同步成功！");
                            /*transactionManagerShop.commit(statusShop);*/
                        } catch (Exception e) {
                            /*transactionManagerShop.rollback(statusShop);*/
                            throw e;
                        }

                    }

                    updateStoreSynch(invStoreSynch.getId(), InvStoreSynch.STATUS_DONE, "同步成功！");

                    return true;

                }

                public boolean afterProcess() {
                    return true;
                }
            });
        } catch (BusinessException e) {
            updateStoreSynch(invStoreSynch.getId(), InvStoreSynch.STATUS_REDO, "同步失败");
            LOGGER.error("同步库存发生异常，BusinessException=", e);
        } catch (Exception e) {
            updateStoreSynch(invStoreSynch.getId(), InvStoreSynch.STATUS_REDO, "同步失败");
            LOGGER.error("同步库存发生异常，Exception=", e);
        }

        return "更新库存成功";
    }

    private Date convertDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("同步EC库存，时间转换错误", e);
        }

    }

    private void updateStoreSynch(Integer storeSyncId, Integer status, String message) {
        InvStoreSynch storeSynch = new InvStoreSynch();
        storeSynch.setId(storeSyncId);
        storeSynch.setStatus(status);
        storeSynch.setMessage(message);
        invStoreSynchDao.update(storeSynch);
    }

    private void updateStore(Integer stockQty, String storeCode, String sku, String itemProperty,
                             Long storeTs) {
        if (stockQty < 0) {
            stockQty = 0;
        }
        InvStore invStore = invStoreDao.getByStoreCodeAndSku(sku, storeCode, itemProperty);
        if (invStore == null) {
            invStore = new InvStore();
            invStore.setStoreCode(storeCode);
            invStore.setSku(sku);
            invStore.setStockQty(stockQty);
            invStore.setItemProperty(itemProperty);
            invStore.setStoreTs(storeTs);
            //添加产品型号、品类、产品组、店铺名称
            this.assignmentValue2InvStore(invStore);
            Integer effect = invStoreDao.insert(invStore);
            LOGGER.info("插入invStore成功，code:" + storeCode + ", sku:" + sku + ", stockQty:" + stockQty
                        + ", effect:" + effect);
        } else {
            invStore.setStockQty(stockQty);
            //添加产品型号、品类、产品组、店铺名称
            this.assignmentValue2InvStore(invStore);
            Integer effect = invStoreDao.updateInvStore(invStore);
            LOGGER.info("更新invStore成功，storeCode:" + storeCode + ", sku:" + sku + ", stockQty:"
                        + stockQty + ", effect:" + effect);
        }
    }

    /**
     * 给InvStore添加的字段赋值
     * @param invStore
     */
    private void assignmentValue2InvStore(InvStore invStore) {
        ItemBase itemBase = getItemBaseBySku(invStore.getSku());
        if (itemBase != null) {
            String productName = itemBase.getMaterialDescription();//产品型号
            invStore.setProductName(productName);
            ItemAttribute itemAttribute = getItemAttributeByValueSetIdAndValue("ProductGroup",
                itemBase.getDepartment());
            if (itemAttribute != null) {
                String productTypeName = itemAttribute.getCbsCategory();//品类
                invStore.setProductTypeName(productTypeName);
                String productGroupName = itemAttribute.getValueMeaning();//产品组
                invStore.setProductGroupName(productGroupName);
            }
        }
        //查询店铺名称
        BasStores basStores = getStoresByStoreCode(invStore.getStoreCode(), null);
        if (basStores != null) {
            String storeName = basStores.getStoreName();
            invStore.setStoreName(storeName);
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
        //        if (!result.getSuccess()) {
        //            return null;
        //        }
        //        List<BasStores> basStoresList = result.getResult();
        //        if (basStoresList == null || basStoresList.size() <= 0) {
        //            return null;
        //        }
        //        return basStoresList.get(0);//此条件下查询，只可能有一条数据
        SgStore sg = sgStoreDao.getSgStore(storeCode, BasStores.STOR_TYPE_1, storeStatus);
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

    public boolean realTimeGetStoreQty(String storeCode, String sku, String itemProperty) {
        try {
            //根据物料编码、店铺码同步EC库存
            ServiceResult<EStoreStock> result = eStoreInfoServiceImpl.getByStoreCode(storeCode, sku,
                itemProperty);
            if (result == null || !result.getSuccess()) {
                LOGGER.warn("同步EC库存失败：storeCode=" + storeCode + ",sku=" + sku);
                return false;
            }
            EStoreStock stock = result.getResult();
            if (stock == null) {
                LOGGER.warn("同步EC库存失败，查询返回结果为空：storeCode=" + storeCode + ",sku=" + sku);
                return false;
            }
            InvStoreSynch synch = new InvStoreSynch();
            synch.setSku(sku);
            synch.setStoreCode(storeCode);
            synch.setItemProperty(stock.getItemProperty());
            synch.setStockQty(stock.getItemQuantity());
            synch.setUpdateTime(DateUtil.format(stock.getUpdateTime(), "yyyy-MM-dd hh:mm:ss"));
            String message = this.storeStockSync(synch);
            LOGGER.info("实时同步库存:" + message);
        } catch (Exception e) {
            LOGGER.warn("查询EC库存失败sku=" + sku + ", storeCode=" + storeCode + ",itemProperty="
                        + itemProperty);
            return false;
        }
        return true;

    }

    /**
     * 同步EC库存到商城
     * @param
     * @param basStores
     */
    private void synStoreStock2Shop(String sku, Integer stockQty, BasStores basStores) {

        MctStoreProducts mctStoreProducts = mctStoreProductsDao
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
            mctStoreProductsDao.insert(storeProducts);
        } else {
            mctStoreProductsDao.updateStockNumById(mctStoreProducts.getStoreProductId(), stockQty);
        }
    }

    /***************************************注入****************************************************/

    /*public void setInvMachineSetDao(InvMachineSetDao invMachineSetDao) {
        this.invMachineSetDao = invMachineSetDao;
    }

    public void setInvBaseStoreDao(InvBaseStoreDao invBaseStoreDao) {
        this.invBaseStoreDao = invBaseStoreDao;
    }

    public void setInvStoreDao(InvStoreDao invStoreDao) {
        this.invStoreDao = invStoreDao;
    }

    public void setOrderThirdCenterItemService(OrderThirdCenterItemServiceImpl itemService) {
        this.itemService = itemService;
    }

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setInvStoreSynchDao(InvStoreSynchDao invStoreSynchDao) {
        this.invStoreSynchDao = invStoreSynchDao;
    }

    public void seteStoreInfoService(EStoreInfoServiceImpl eStoreInfoService) {
        this.eStoreInfoServiceImpl = eStoreInfoService;
    }*/

    //    public void setStoresService(StoresService storesService) {
    //        this.storesService = storesService;
    //    }

   /* public void setMctStoreProductsDao(MctStoreProductsDao mctStoreProductsDao) {
        this.mctStoreProductsDao = mctStoreProductsDao;
    }

    public void setTransactionManagerShop(DataSourceTransactionManager transactionManagerShop) {
        this.transactionManagerShop = transactionManagerShop;
    }

    public void setSgStoreDao(SgStoreDao sgStoreDao) {
        this.sgStoreDao = sgStoreDao;
    }*/

}
