package com.haier.svc.api.controller.stock.mode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.interconn.monitor.StringUtil;
import com.haier.order.model.ExceptionCode;
import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.CBSKCType;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.ProductsNew;
import com.haier.shop.model.QueryLesStockOutType;
import com.haier.shop.model.QueryLesStockToCbs;
import com.haier.shop.model.SgRealtimeStock;
import com.haier.shop.model.StockChangeQueue;
import com.haier.shop.model.StockHolder;
import com.haier.shop.model.StorageProducts;
import com.haier.shop.model.Storages;
import com.haier.shop.service.BigStoragesRelaService;
import com.haier.shop.service.SgRealtimeStockService;
import com.haier.shop.service.StockChangeQueueService;
import com.haier.shop.service.StorageProductsService;
import com.haier.shop.service.StoragesService;
import com.haier.stock.datasource.ReadWriteRoutingDataSourceHolder;
import com.haier.stock.model.InvBaseStock;
import com.haier.stock.model.InvBaseStockDiff;
import com.haier.stock.model.InvBaseStockDiffLog;
import com.haier.stock.model.InvBaseStockLog;
import com.haier.stock.model.InvDbmBase;
import com.haier.stock.model.InvDbmBaseSendAddress;
import com.haier.stock.model.InvLesStock;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvSgStockEntity;
import com.haier.stock.model.InvStock;
import com.haier.stock.model.InvStock2Channel;
import com.haier.stock.model.InvStockBatch;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvStockInOut;
import com.haier.stock.model.InvStockLock;
import com.haier.stock.model.InvStockQtyDifLog;
import com.haier.stock.model.InvStockTransaction;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.model.SgFlagShipStoreAuthorization;
import com.haier.stock.service.InvBaseStockDiffLogService;
import com.haier.stock.service.InvBaseStockDiffService;
import com.haier.stock.service.InvBaseStockLogService;
import com.haier.stock.service.InvDbmBaseSendAddressService;
import com.haier.stock.service.InvDbmBaseService;
import com.haier.stock.service.InvLesStockService;
import com.haier.stock.service.InvSgStockService;
import com.haier.stock.service.InvStock2ChannelService;
import com.haier.stock.service.InvStockChangeService;
import com.haier.stock.service.InvStockQtyDifLogService;
import com.haier.stock.service.InvStockService;
import com.haier.stock.service.SgFlagShipStoreAuthorizationService;
import com.haier.stock.service.StockAgeService;
import com.haier.stock.service.StockCenterItemService;
import com.haier.stock.service.StockCenterLESService;
import com.haier.stock.service.StockCommonService;
import com.haier.stock.service.StockInvBaseStockService;
import com.haier.stock.service.StockInvMachineSetService;
import com.haier.stock.service.StockInvSectionService;
import com.haier.stock.service.StockInvStockBatchService;
import com.haier.stock.service.StockInvStockChannelService;
import com.haier.stock.service.StockInvStockLockService;

/**
 * Created by Administrator on 2017/12/14 0014.
 * @param <T>
 */
@Service
public class StockModel {
	  private static org.apache.log4j.Logger log      = org.apache.log4j.LogManager
		        .getLogger(StockModel.class);
		    private static final String            LOG_MARK = "[Stock][StockMode] ";
    @Autowired
    private BigStoragesRelaService bigStoragesRelaService;
    @Autowired
    private StockAgeService stockAgeService;
    @Autowired
    private StoragesService storagesService;
    @Autowired
    private StockCommonService   stockCommonService;
    /**
     * 获取库位信息
     * @param sCode 库位
     * @return 库位信息
     */
    public Storages getStorages(String sCode) {
        return storagesService.getByCode(sCode);
    }
    @Autowired
    private StockInvStockLockService         stockInvStockLockService;
    @Autowired
    private StockInvSectionService            stockInvSectionService;
    @Autowired
    private StockInvMachineSetService         stockInvMachineSetService;
//    private DataSourceTransactionManager transactionManagerStock;
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    private StockInvBaseStockService          stockInvBaseStockService;
    @Autowired
    private StockChangeQueueService      stockChangeQueueDao;
    @Autowired
    private StockCenterItemService              itemService;
    @Autowired
    private StockInvStockChannelService       stockInvStockChannelService;
    @Autowired
    private InvStockChangeService        invStockChangeService;
    @Autowired
    private StockInvStockBatchService         stockInvStockBatchService;
    @Autowired
    private InvStockService              invStockService;
    @Autowired
    private InvBaseStockLogService       invBaseStockLogService;
    @Autowired
    private InvLesStockService           invLesStockService;
    @Autowired
    private StorageProductsService       storageProductsService;
    @Autowired
    private InvSgStockService      invSgStockService;
    @Autowired
    private InvStock2ChannelService      invStock2ChannelService;
    @Autowired
    private InvDbmBaseService           invDbmBaseService;
    @Autowired
    private InvStockQtyDifLogService     invStockQtyDifLogService;
    @Autowired
    private SgRealtimeStockService sgRealtimeStockService;
    @Autowired
    private InvDbmBaseSendAddressService invDbmBaseSendAddressService;
    @Autowired
    private SgFlagShipStoreAuthorizationService sgFlagShipStoreAuthorizationService;
    @Autowired
    private InvBaseStockDiffService invBaseStockDiffService;
    @Autowired
    private StockCenterLESService          stockCenterLESService;
    @Autowired
    private InvBaseStockDiffLogService invBaseStockDiffLogService;
    public List<BigStoragesRela> getBigStoragesRelaList() {
        return bigStoragesRelaService.getList();
    }
    private static final int max_day = 5;
    
    /**
     * CBS库存业务--单库位冻结
     * @param sku 物料
     * @param secCode 库位
     * @param refNo 单据号
     * @param frozenQty 占用数
     * @param billType 交易类型
     * @param optUser 操作人
     * @return 执行结果
     */
//    @Transactional
    public ServiceResult<Boolean> frozeStockQty(String sku, String secCode, String refNo,
                                                Integer frozenQty, InventoryBusinessTypes billType,
                                                String optUser) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        if (frozenQty < 1) {
            result.setMessage("冻结数量不能小于1");
            result.setResult(false);
            result.setSuccess(false);
            return result;
        }

        //检查单据号是否已经冻结
        List<InvStockLock> locks = stockInvStockLockService.getNotReleasedByRefNo(refNo);
        if (locks.size() > 0) {
        int qty = 0;
            for (InvStockLock lock : locks) {
                int freeQty = lock.getLockQty() - lock.getRealeaseQty();
                qty += freeQty < 0 ? 0 : freeQty;
            }
            if (qty == frozenQty) {
                result.setMessage("已存在此单据的冻结记录");
                result.setSuccess(true);
                result.setResult(true);
                log.info("frozeStockQty:单据号=" + refNo + "已存在冻结记录，冻结成功");
            } else {
                result.setMessage("已存在此单据的冻结记录， 记录数量不一致");
                result.setSuccess(false);
                result.setResult(false);
                log.info("frozeStockQty:单据号=" + refNo + "已存在冻结记录， 记录数量不一致");
            }
            return result;
        }

        InvSection section = stockInvSectionService.getBySecCode(secCode);
        if (section == null) {
            result.setMessage("库位信息不存在");
            result.setResult(false);
            result.setSuccess(false);
            return result;
        }

        List<StockHolder> stockHolders = new ArrayList<StockHolder>();
        //如果是套机需要解套，冻结子件
        List<InvMachineSet> machineSets = stockInvMachineSetService.getByMainSku(sku);
        if (machineSets.size() <= 0)
            stockHolders.add(StockHolder.newInstance(sku, secCode, frozenQty));
        for (InvMachineSet machineSet : machineSets) {
            String _sku = machineSet.getSubSku();
            Integer _frozenQty = machineSet.getMenge().intValue() * frozenQty;
            stockHolders.add(StockHolder.newInstance(_sku, section.getSecCode(), _frozenQty));
        }

        boolean checkSucc = machineSets.size() <= 0 || verifyFrozenHolders(stockHolders);
        if (!checkSucc) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("有无效套机组合, sku=" + sku);
            log.warn("frozeStockQty:有无效套机组合, sku=" + sku);
            return result;
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManagerStock.getTransaction(def);
        TransactionStatus statusShop = dataSourceTransactionManager.getTransaction(def);
        try {
            Map<String, InvStock> skuStockMap = new HashMap<String, InvStock>();
            List<InvStock> stockList;
            for (StockHolder stockHolder : stockHolders) {
                //并发情况重复占用库存，抛出异常的原因在于
                //1. 占用时失败，回滚事务，撤销写日志，撤销更新库存
                //1.1. 假如把占用库存放在第一步执行，无法避免并发情况重复插入数据，因为处于事务操作中，在不同事务中无法知晓彼此是否占用
                //1.2. 阻止并发操作基于对数据库表 inv_base_stock的物理锁，当一个事务提交之后，另一个事务才会检测到同一单是否被占用
                //2. 不执行此句代码之后的操作，比如更新库存变化记录，无需通知
                InvBaseStock baseStock = basicFrozeStockQty(stockHolder.getSku(), stockHolder.getSecCode(),
                    refNo, stockHolder.getChangeQty(), billType, section.getChannelCode(), optUser);

                //计算此子件对应的套机
                stockList = regroupStockQtyBySku(stockHolder.getSku(), baseStock.getSecCode(),
                    baseStock.getStockQty() - baseStock.getFrozenQty());
                for (InvStock stock : stockList) {
                    if (!skuStockMap.containsKey(stock.getSku())) {
                        skuStockMap.put(stock.getSku(), stock);
                    } else
                        if (skuStockMap.get(stock.getSku()).getStockQty() > stock.getStockQty()) {
                        skuStockMap.put(stock.getSku(), stock);
                    }
                }
            }
            //更新InvStock以及相关到货通知
            InvSection stockSection;
            for (Map.Entry<String, InvStock> stockEntry : skuStockMap.entrySet()) {
                stockSection = this.stockInvSectionService.getBySecCode(stockEntry.getValue().getSecCode());
                StockBizHelper.linkageUpdateStockForSales(itemService, stockInvSectionService, invStockService,
                    stockInvStockChannelService, stockChangeQueueDao, invStockChangeService,
                    stockEntry.getValue(), stockSection, true);
            }
            dataSourceTransactionManager.commit(statusShop);
//            transactionManagerStock.commit(status);
            result.setResult(true);
        } catch (BusinessException e) {
            dataSourceTransactionManager.rollback(statusShop);
//            transactionManagerStock.rollback(status);
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
            result.setResult(isSuc);
            result.setMessage(message);
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(statusShop);
//            transactionManagerStock.rollback(status);
            log.error("冻结库存失败：", e);
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("冻结库存失败：" + e.getMessage());
        }
        return result;
    }
    
    
    /*****************************库存：冻结，释放*******************************************/
    /***
     * CBS库存业务--多库位冻结
     * @param sku 物料
     * @param whCode 仓库编码
     * @param channelCode 渠道
     * @param refNo 单据号
     * @param frozenQty 占用数量
     * @param billType 交易类型
     * @return 占用库存的库位
     */
    @Transactional
    public ServiceResult<String> frozeStockQtyByWhCode(String sku, String whCode,
                                                       String channelCode, String refNo,
                                                       Integer frozenQty,
                                                       InventoryBusinessTypes billType,
                                                       boolean isLinkage) {
        ServiceResult<String> result = new ServiceResult<String>();
        if (frozenQty < 1) {
            result.setMessage("冻结数量不能小于1");
            result.setSuccess(false);
            return result;
        }

        InvStockChannel stockChannel = stockInvStockChannelService.getByCode(channelCode);
        if (stockChannel == null) {
            result.setSuccess(false);
            result.setMessage("无法识别的渠道编码");
            return result;
        }

        List<StockHolder> stockHolders = new ArrayList<StockHolder>();
        List<InvMachineSet> machineSets = stockInvMachineSetService.getByMainSku(sku);
        if (machineSets.size() <= 0) {
            stockHolders.add(StockHolder.newInstance(sku, null, frozenQty));
        }
        for (InvMachineSet machineSet : machineSets) {
            Integer num = machineSet.getMenge().intValue();
            String _sku = machineSet.getSubSku();
            stockHolders.add(StockHolder.newInstance(_sku, null, frozenQty * num));
        }

        //已经存在此单据的冻结记录：检查原冻结记录和要冻结的记录是否一致，一致返回成功，不一致返回失败
        ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
        List<InvStockLock> locks = null;
        try {
            locks = stockInvStockLockService.getNotReleasedByRefNo(refNo);
        } finally {
            ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
        }

        if (locks != null && locks.size() > 0) {
            boolean isConsistent = true;
            for (StockHolder stockHolder : stockHolders) {
                int qty = 0;
                for (InvStockLock lock : locks) {
                    if (!lock.getSku().equals(stockHolder.getSku())) {
                        continue;
                    }
                    int freeQty = lock.getLockQty() - lock.getRealeaseQty();
                    qty += freeQty < 0 ? 0 : freeQty;
                }
                if (qty != stockHolder.getChangeQty()) {
                    isConsistent = false;
                    break;
                }
            }
            if (isConsistent) {
                result.setMessage("已存在此单据的冻结记录");
                result.setSuccess(true);
                log.info("frozeStockQtyByWhCode:单据号=" + refNo + "已存在冻结记录，冻结成功");
            } else {
                result.setMessage("已存在此单据的冻结记录，且记录数量不一致");
                result.setSuccess(false);
                log.info("frozeStockQtyByWhCode:单据号=" + refNo + "已存在冻结记录，且记录数量不一致");
            }

            return result;
        }

        //确定可用库位列表,库位使用的顺序按照InvStockChannel中的stockChannelCodes的顺序
        Map<String, List<InvSection>> sections = new LinkedHashMap<String, List<InvSection>>();
        String stockChannelCodes = stockChannel.getStockChannelCodes();
        /**
         * 采购销售联动，销售RRS
         */
        List<String> stockChannelList = new ArrayList<String>(
            Arrays.asList(stockChannelCodes.split(",")));
        boolean containsRRS = stockChannelList.contains(InvSection.CHANNEL_CODE_RRS);
        if (isLinkage && !containsRRS) {
            stockChannelList.add(InvSection.CHANNEL_CODE_RRS);
        }
        for (String stockChannelCode : stockChannelList) {
            String tempWhCode = StockBizHelper.findWhCodes(stockChannelCode, whCode);
            List<InvSection> _sections = stockInvSectionService.getByChannelCode(tempWhCode,
                stockChannelCode);
            for (InvSection section : _sections) {
                String lesSecCode = section.getLesSecCode();
                if (!sections.containsKey(lesSecCode)) {
                    List<InvSection> secs = new ArrayList<InvSection>();
                    secs.add(section);
                    sections.put(lesSecCode, secs);
                } else {
                    sections.get(lesSecCode).add(section);
                }
            }
        }

        String usedLesSecCode = "";
        boolean isSuccess = false;
        List<StockHolder> holders = null;
        //确定占用库存的列表
        for (Entry<String, List<InvSection>> entry : sections.entrySet()) {
            if (isSuccess)
                break;
            holders = new ArrayList<StockHolder>();
            usedLesSecCode = entry.getKey();
            List<InvSection> _sections = entry.getValue();
            for (StockHolder holder : stockHolders) {
                Integer restFrozenQty = holder.getChangeQty();
                for (InvSection section : _sections) {
                    InvBaseStock baseStock = (InvBaseStock) stockInvBaseStockService.get(holder.getSku(),
                        section.getSecCode());
                    Integer availableQty = baseStock == null ? 0
                        : baseStock.getStockQty() - baseStock.getFrozenQty();
                    if (availableQty <= 0)
                        continue;
                    Integer _frozenQty = restFrozenQty > availableQty ? availableQty
                        : restFrozenQty;
                    restFrozenQty -= _frozenQty;
                    holders
                        .add(StockHolder.newInstance(holder.getSku(), section.getSecCode(), _frozenQty));
                    if (restFrozenQty <= 0)
                        break;
                }
                if (restFrozenQty > 0) {
                    isSuccess = false;
                    break;
                } else {
                    isSuccess = true;
                }
            }
        }

        if (!isSuccess) {
            result.setSuccess(false);
            result.setMessage("占用库存失败：库存不足");
            return result;
        }

        boolean checkSucc = machineSets.size() <= 0 || verifyFrozenHolders(holders);
        if (!checkSucc) {
            result.setSuccess(false);
            result.setMessage("有无效套机组合, sku=" + sku);
            log.warn("frozeStockQtyByWhCode:有无效套机组合, sku=" + sku);
            return result;
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

//        TransactionStatus status = transactionManagerStock.getTransaction(def);
        TransactionStatus statusShop = dataSourceTransactionManager.getTransaction(def);

        try {
            Map<String, InvStock> skuStockMap = new HashMap<String, InvStock>();
            List<InvStock> stockList;
            for (StockHolder holder : holders) {
                InvBaseStock baseStock = basicFrozeStockQty(holder.getSku(), holder.getSecCode(), refNo,
                    holder.getChangeQty(), billType, channelCode, channelCode);
                //计算此子件对应的套机
                stockList = regroupStockQtyBySku(holder.getSku(), baseStock.getSecCode(),
                    baseStock.getStockQty() - baseStock.getFrozenQty());
                for (InvStock stock : stockList) {
                    String pKey = stock.getSku() + stock.getSecCode();
                    if (!skuStockMap.containsKey(pKey)) {
                        skuStockMap.put(pKey, stock);
                    } else if (skuStockMap.get(pKey).getStockQty() > stock.getStockQty()) {
                        skuStockMap.put(pKey, stock);
                    }
                }
            }
            //更新InvStock以及相关到货通知
            InvSection stockSection;
            for (Map.Entry<String, InvStock> stockEntry : skuStockMap.entrySet()) {
                stockSection = this.stockInvSectionService.getBySecCode(stockEntry.getValue().getSecCode());
                StockBizHelper.linkageUpdateStockForSales(itemService, stockInvSectionService, invStockService,
                    stockInvStockChannelService, stockChangeQueueDao, invStockChangeService,
                    stockEntry.getValue(), stockSection, true);
            }
            dataSourceTransactionManager.commit(statusShop);
//            transactionManagerStock.commit(status);
        } catch (BusinessException e) {
            dataSourceTransactionManager.rollback(statusShop);
//            transactionManagerStock.rollback(status);
            boolean isSuc = ExceptionCode.USER_CONCURRENCE_CODE.equals(e.getCode());
            String message;
            if (isSuc) {
                message = ExceptionCode.USER_CONCURRENCE_CODE + ":" + e.getMessage();
                log.info(message);
            } else {
                message = "冻结库存不成功";
                log.info(message);
            }
            result.setSuccess(isSuc);
            result.setMessage(message);
        } catch (Exception _e) {
            dataSourceTransactionManager.rollback(statusShop);
//            transactionManagerStock.rollback(status);
            result.setSuccess(false);
            result.setMessage("冻结库存出现未知错误：" + _e.getMessage());
            log.error("冻结库存时出现未知错误：", _e);
            return result;
        }

        result.setResult(usedLesSecCode);
        return result;

    }
    
    @Transactional
    private boolean verifyFrozenHolders(List<StockHolder> holders) {
        if (holders == null || holders.size() <= 0) {
            log.info("verifyFrozenHolders: 空的对象，无需检查");
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

    private InvBaseStock basicFrozeStockQty(String sku, String secCode, String refNo,
                                            Integer frozenQty, InventoryBusinessTypes billType, String string, String string2) {
        return this.basicFrozeStockQty(sku, secCode, refNo, frozenQty, billType, "", "sys");
    }
    

    /*************************************************记录库存变化通知*******************************/
    @Transactional
    private List<InvStock> regroupStockQtyBySku(String sku, String secCode, Integer stockQty) {
        List<InvStock> stocks = new ArrayList<InvStock>();
        List<InvMachineSet> machineSets = stockInvMachineSetService.getBySubSku(sku);
        if (machineSets.size() <= 0) {
            InvStock bomStock = new InvStock();
            bomStock.setSecCode(secCode);
            bomStock.setSku(sku);
            bomStock.setStockQty(stockQty);
            stocks.add(bomStock);
        } else {
            Map<String, List<InvMachineSet>> keyMap = new HashMap<String, List<InvMachineSet>>();
            List<String> saleSubList = new ArrayList<String>();
            for (InvMachineSet machineSet : machineSets) {
                String mainSku = machineSet.getMainSku();
                List<InvMachineSet> bomInfos = stockInvMachineSetService.getByMainSku(mainSku);
                keyMap.put(mainSku, bomInfos);
                if (saleSubList.contains(machineSet.getSubSku())) {
                    continue;
                } else {
                    saleSubList.add(machineSet.getSubSku());
                }
                if (machineSet.getIsSaleSub() == InvMachineSet.STATUS_SALE_SUB_MACHINE
                    && machineSet.getSubSku().equals(sku)) {
                    InvStock subStock = new InvStock();
                    subStock.setSecCode(secCode);
                    subStock.setSku(machineSet.getSubSku());
                    subStock.setStockQty(stockQty);
                    stocks.add(subStock);
                }
            }
            for (Entry<String, List<InvMachineSet>> entry : keyMap.entrySet()) {
                InvStock bomStock = new InvStock();
                bomStock.setSecCode(secCode);
                bomStock.setSku(entry.getKey());
                calculateBomStockByBase(bomStock, entry.getValue());
                stocks.add(bomStock);
            }
        }
        return stocks;
    }
    @Transactional
    private void calculateBomStockByBase(InvStock invStock, List<InvMachineSet> bomInfos) {
        int minStock = -1;
        for (InvMachineSet bominfo : bomInfos) {
            String subSku = bominfo.getSubSku();
            InvBaseStock subStock = (InvBaseStock) stockInvBaseStockService.get(subSku,
                invStock.getSecCode());
            if (subStock == null) {
                minStock = 0;
                break;
            }
            int freeStockQty = subStock.getStockQty() - subStock.getFrozenQty();
            if (freeStockQty <= 0) {
                minStock = 0;
                break;
            }
            int num = bominfo.getMenge() == null ? 0 : bominfo.getMenge().intValue();
            if (num <= 0) {
                log.error("套机装配关系（" + bominfo.getId() + "）错误，组件数量不正确");
                minStock = 0;
                break;
            }
            //套机数量为最小数量
            if (minStock == -1) {
                minStock = freeStockQty / num;
            } else if (minStock > freeStockQty / num)
                minStock = freeStockQty / num;
        }
        invStock.setStockQty(minStock);
    }
    
    /**
     * CBS库存业务--默认系统被动调用释放
     * @param sku 物料
     * @param refNo 单据号
     * @param releaseQty 释放数量
     * @param billType 交易类型
     * @return 结果
     */
    public ServiceResult<Boolean> releaseFrozenStockQty(String sku, String refNo,
                                                        Integer releaseQty,
                                                        InventoryBusinessTypes billType) {
        return this.releaseFrozenStockQty(sku, refNo, releaseQty, billType, "sys");
    }
    
    /**
     * CBS库存业务--释放冻结库存: 按照网单号（refNo）释放冻结库存
     * @param sku 物料
     * @param refNo 单据号
     * @param releaseQty 释放数量
     * @param billType 交易类型
     * @return 结果
     */
//    @Transactional(rollbackFor = Exception.class)
    public ServiceResult<Boolean> releaseFrozenStockQty(String sku, String refNo,
                                                        Integer releaseQty,
                                                        InventoryBusinessTypes billType,
                                                        String optUser) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();

        if (releaseQty == null || releaseQty < 1) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("释放数量不能小于1");
            return result;
        }

        if (StringUtil.isEmpty(refNo)) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("要释放的单据号不能为空");
            return result;
        }

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

        //根据占用记录，确定要释放的库存记录
        List<StockHolder> stockHoldersForRelease = new ArrayList<StockHolder>();
        for (StockHolder stockHolder : stockHolders) {
            List<InvStockLock> stockLocks = stockInvStockLockService.getNotReleasedByRefNoSku(refNo,
                stockHolder.getSku());
            Integer qty = stockHolder.getChangeQty();

            if (stockLocks.size() <= 0) {
                result.setError("no_release_record",
                    "不存在此单的占用记录(refno=" + refNo + ")，不能释放(释放数：" + qty + ")");
                result.setSuccess(true);
                result.setResult(true);
                return result;
            }

            for (InvStockLock stockLock : stockLocks) {
                if (qty <= 0)
                    break;
                Integer _releaseQty;
                if (stockLock.getLockQty() <= stockLock.getRealeaseQty())
                    continue;
                else
                    _releaseQty = stockLock.getLockQty() - stockLock.getRealeaseQty();
                if (_releaseQty > qty)
                    _releaseQty = qty;
                qty -= _releaseQty;
                stockHoldersForRelease.add(StockHolder.newInstance(stockLock.getSku(),
                    stockLock.getSecCode(), _releaseQty));
            }

            if (qty.equals(releaseQty)) {
                result.setError("repeated_released",
                    "此单据已经释放过(refno=" + refNo + ")，不能重复释放(释放数：" + qty + ")");
                result.setSuccess(true);
                result.setResult(true);
                return result;
            }

            if (qty > 0) {
                result.setSuccess(false);
                result.setResult(false);
                result.setMessage("要释放的数量大于原冻结数量, " + qty);
                return result;
            }
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

//        TransactionStatus status = transactionManagerStock.getTransaction(def);
//        TransactionStatus statusShop = dataSourceTransactionManager.getTransaction(def);

        try {
            Map<String, InvStock> skuStockMap = new HashMap<String, InvStock>();
            List<InvStock> stockList;
            for (StockHolder holder : stockHoldersForRelease) {

                updateStockLockAfterReleaseQty(holder.getSku(), holder.getSecCode(), refNo, holder.getChangeQty(),
                    optUser);

                InvBaseStock baseStock = releaseBaseStockQty(holder.getSku(), holder.getSecCode(), refNo,
                    holder.getChangeQty(), billType.getCode());
                if (baseStock == null) {
                    throw new BusinessException(
                        "冻结记录释放失败, sku:" + holder.getSku()+ ", secCode:" + holder.getSecCode());
                }
                //计算此子件对应的套机
                stockList = regroupStockQtyBySku(holder.getSku(), baseStock.getSecCode(),
                    baseStock.getStockQty() - baseStock.getFrozenQty());
                for (InvStock stock : stockList) {
                    String pKey = stock.getSku() + stock.getSecCode();
                    if (!skuStockMap.containsKey(pKey)) {
                        skuStockMap.put(pKey, stock);
                    } else if (skuStockMap.get(pKey).getStockQty() < stock.getStockQty()) {
                        skuStockMap.put(pKey, stock);
                    }
                }

            }
            //更新InvStock以及相关到货通知
            InvSection stockSection;
            for (Map.Entry<String, InvStock> stockEntry : skuStockMap.entrySet()) {
                stockSection = this.stockInvSectionService.getBySecCode(stockEntry.getValue().getSecCode());
                StockBizHelper.linkageUpdateStockForSales(itemService, stockInvSectionService, invStockService,
                    stockInvStockChannelService, stockChangeQueueDao, invStockChangeService,
                    stockEntry.getValue(), stockSection, true);
            }
//            dataSourceTransactionManager.commit(statusShop);
//            transactionManagerStock.commit(status);
            result.setSuccess(true);
            result.setResult(true);
            return result;
        } catch (Exception e) {
//            dataSourceTransactionManager.rollback(statusShop);
//            transactionManagerStock.rollback(status);
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            return result;
        }
    }
    
    
    private void updateStockLockAfterReleaseQty(String sku, String sec_code, String refNo,
	            Integer releaseQty) {
	this.updateStockLockAfterReleaseQty(sku, sec_code, refNo, releaseQty, "sys");
	}
    @Transactional
    private void updateStockLockAfterReleaseQty(String sku, String sec_code, String refNo,
    		Integer releaseQty, String optUser) {
    	InvStockLock stockLock = stockInvStockLockService.getNotReleased(refNo, sku, sec_code);
    	if (stockLock == null) {
    		throw new BusinessException("占用记录不存在！");
    	} else {
    		int notReleaseQty = stockLock.getLockQty() - stockLock.getRealeaseQty();
    		boolean isSuccess = false;
    		if (notReleaseQty >= releaseQty) {
    			Integer effectNum = stockInvStockLockService.updateReleaseQty(stockLock.getId(), releaseQty,
    					optUser);
    			if (effectNum >= 1) {
    				isSuccess = true;
    			}
    		}
    		if (!isSuccess) {
    			throw new BusinessException(
    					"占用记录（" + sku + "," + sec_code + "," + refNo + ")可释放数量小于要释放的数量");
    		}
    	}
    }
    
    /**
     * 释放基础库存
     * @param sku 物料
     * @param secCode 库位
     * @param refNo 单据号
     * @param releaseQty 释放数量
     * @param billType 交易类型
     */
    @Transactional
    private InvBaseStock releaseBaseStockQty(String sku, String secCode, String refNo,
                                             Integer releaseQty, String billType) {
        InvBaseStock baseStock = (InvBaseStock) stockInvBaseStockService.get(sku, secCode);
        if (baseStock == null) {
            throw new BusinessException("基础库存记录不存在");
        }
        Integer oldFrozenQty = baseStock.getFrozenQty();
        Date time = new Date();
        Integer effectNum = stockInvBaseStockService.releaseQty(baseStock.getStoId(), releaseQty, time);
        if (effectNum < 1) {
            throw new BusinessException("扣减基础库存占用数量失败");
        }

        //记录库存变化日志
        InvBaseStockLog baseStockLog = new InvBaseStockLog();
        baseStockLog.setSku(baseStock.getSku());
        baseStockLog.setSecCode(baseStock.getSecCode());
        baseStockLog.setLesSecCode(baseStock.getLesSecCode());
        baseStockLog.setOldFrozenQty(oldFrozenQty);
        baseStockLog.setOldStockQty(baseStock.getStockQty());
        baseStockLog.setNewFrozenQty(oldFrozenQty - releaseQty);
        baseStockLog.setNewStockQty(baseStock.getStockQty());
        baseStockLog.setBillType(billType);
        baseStockLog.setRefNo(refNo);
        baseStockLog.setMark("S");
        baseStockLog.setCreateTime(time);
        //给添加的字段赋值
        assignmentValue2InvBaseStockLog(baseStockLog);
        invBaseStockLogService.insert(baseStockLog);
        return (InvBaseStock) stockInvBaseStockService.get(sku, secCode);
    }
    
    /**
     * 给InvBaseStockLog添加的字段赋值
     * @param baseStockLog 库存日志
     */
    private void assignmentValue2InvBaseStockLog(InvBaseStockLog baseStockLog) {
        ItemBase itemBase = getItemBaseBySku(baseStockLog.getSku());
        if (itemBase != null) {
            String productName = itemBase.getMaterialDescription();//产品型号
            baseStockLog.setProductName(productName);
        }
        InvSection invSection = stockInvSectionService.getBySecCode(baseStockLog.getSecCode());
        if (invSection != null) {
            String secName = invSection.getSecName();//库位名称
            baseStockLog.setSecName(secName);
        }
    }

    /**
     * 通过sku查询item_base
     * @param sku 物料
     * @return 物料信息
     */
    public ItemBase getItemBaseBySku(String sku) {
        ServiceResult<ItemBase> result = itemService.getItemBaseBySku(sku);
        if (result == null || !result.getSuccess())
            return null;
        return result.getResult();
    }
    
    
    /**
     * 检查指定的Sku（商城渠道）是否有库存
     * 1、商城渠道
     * 2、任何库位有库存都算有库存
     * @param sku 物料编码
     * @return 是否有库存
     */
    public Boolean checkIfSkuHasStock(String sku) {
        //检查数据的有效性
        if (StringUtil.isEmpty(sku)) {
            log.error("检查sku是否可售是发生异常：输入参数为null或者为空。");
            return false;
        }
        //商城对应的渠道配置
        InvStockChannel stockChannel = stockInvStockChannelService
            .getByCode(InvStockChannel.CHANNEL_SHANGCHENG);
        if (stockChannel == null) {
            log.error("检查sku是否可售是发生异常：商城渠道没有设置可用的库存渠道。");
            return false;
        }
        //获取所有商品的库存
        List<InvStock> stockList = invStockService.getValidStockQtyByWhCodesAndSkus(
            StockBizHelper.stringWithSingleQuote(sku), null,
            StockBizHelper.stringWithSingleQuote(stockChannel.getStockChannelCodes()));
        //逐条检测sku是否可售
        if (stockList == null || stockList.size() == 0) {
            return false;
        }
        //检查商品是否有库存
        for (InvStock stock : stockList) {
            if (stock.getSku().equals(sku) && stock.getStockQty().compareTo(0) > 0) {
                return true;
            }
        }
        //返回sku是否可售的信息
        return false;
    }
    

    /**
     * 查询一段时间之后没有释放的库存
     * @param lockTime
     * @param topx
     * @return
     */
    public List<InvStockLock> getNoReleaseByLockTime(String lockTime, Integer topx) {

        return stockInvStockLockService.getNoReleaseByLockTime(lockTime, topx);

    }
    
    /**
     * CBS库存业务--根据库存交易行，更新库存
     * 15年10月20:删除更新老商城的库存
     * @param stockTransaction 库存交易
     * @return 结果
     */
    public ServiceResult<Boolean> updateCbsStockByStockTransaction(InvStockTransaction stockTransaction) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        result.setResult(true);
        String remark = "";

        InvBaseStock baseStock;
        Integer qty = stockTransaction.getQuantity();
        Integer releaseQty = 0;
        Integer frozeQty = 0;

        InventoryBusinessTypes businessTypes = InventoryBusinessTypes
            .getByCode(stockTransaction.getBillType());
        if (businessTypes == null) {
            businessTypes = InventoryBusinessTypes.UNDEFINED;
        }

        //是否占用库存
        boolean isFroze = stockTransaction.getIsFrozen().equals(1);
        //是否释放占用的库存
        boolean isRelease = stockTransaction.getIsRelease().equals(1);

        //商品属性为空判断，默认W10
        String itemProperty = stockTransaction.getItemProperty();
        itemProperty = StringUtil.isEmpty(itemProperty) ? InvSection.W10 : itemProperty;
        boolean isEnterInW10 = InvSection.W10.equals(itemProperty);

        //Reversal transaction 冲正交易处理
        String mark = stockTransaction.getMark();
        boolean isReversalTransaction = !"".equals(businessTypes.getMark())
                                        && !businessTypes.getMark().equalsIgnoreCase(mark);
        if (isReversalTransaction) {
            if (isFroze) {
                //正向单据占用了库存，逆向单据需要释放库存
                isRelease = true;
                isFroze = false;
            } else if (isRelease) {
                //正向单据释放了库存，逆向单据需要占用
                isFroze = true;
                isRelease = false;
            }
        }

        InvStockLock stockLock = stockInvStockLockService.getNotReleased(stockTransaction.getCorderSn(),stockTransaction.getSku(), stockTransaction.getSecCode());

        if (isRelease) {
            if (stockLock == null) {
                remark = "冻结记录不存在，不再释放库存";
            } else if ((stockLock.getLockQty() - stockLock.getRealeaseQty()) < qty) {
                remark = "出库数量大于原占用数量，只释放原占用数";
                releaseQty = stockLock.getLockQty() - stockLock.getRealeaseQty();
            } else {
                releaseQty = qty;
            }
        }

        if (isFroze) {
            if (stockLock != null) {
                frozeQty = qty - (stockLock.getLockQty() - stockLock.getRealeaseQty());
                remark = "已存在占用记录，修正占用记录数量,增加占用数量：" + frozeQty;
            } else {
                frozeQty = qty;
            }
            if (frozeQty < 0) {
                frozeQty = 0;
            }
        }

        InvSection section = stockInvSectionService.getBySecCode(stockTransaction.getSecCode());
        if (section == null) {
            result.setResult(false);
            result.setMessage("库位编码不正确");
            return result;
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            if (!InvSection.CHANNEL_CODE_RRS.equalsIgnoreCase(section.getChannelCode())) {
                //更新库存批次
                updateStockBatch(stockTransaction.getSku(), section.getSecCode(),
                    stockTransaction.getCorderSn(), businessTypes, mark, qty);
            }

            //释放库存
            if (releaseQty > 0) {
                updateStockLockAfterReleaseQty(stockTransaction.getSku(), section.getSecCode(),
                    stockTransaction.getCorderSn(), releaseQty);
            }

            //更新库存
            baseStock = updateStockBase(stockTransaction.getSku(), section.getLesSecCode(),
                section.getSecCode(), qty, releaseQty, businessTypes, mark,
                stockTransaction.getCorderSn());

            //占用库存
            if (frozeQty > 0) {
                if (stockLock != null) {
                    stockInvStockLockService.updateLockQty(stockLock.getId(), frozeQty, "sys");
                    Integer oldFrozenQty = baseStock.getFrozenQty();
                    Integer newFrozenQty = oldFrozenQty + frozeQty;
                    Date time = new Date();
                    recordBaseStockLog(baseStock, baseStock.getStockQty(), newFrozenQty,
                        stockTransaction.getCorderSn(), "H", businessTypes, time);
                    baseStock.setFrozenQty(newFrozenQty);
                    baseStock.setUpdateTime(time);
                    int effectRows = stockInvBaseStockService.updateQtyForFrozen(baseStock);
                    if (effectRows < 1) {
                        throw new BusinessException("库存不足，占用失败");
                    }
                    stockInvStockLockService.updateLockQty(stockLock.getId(),
                        stockLock.getLockQty() + frozeQty, "sys");
                } else
                    baseStock = basicFrozeStockQty(stockTransaction.getSku(), section.getSecCode(),
                        stockTransaction.getCorderSn(), frozeQty,
                        InventoryBusinessTypes.FROZEN_BY_ZBCC, mark, mark);
            }

            //正品需要更新销售库:inv_stock
            if (isEnterInW10) {
                //计算套机库存
                List<InvStock> stockList = regroupStockQtyBySku(baseStock.getSku(),
                    baseStock.getSecCode(), baseStock.getStockQty() - baseStock.getFrozenQty());

                for (InvStock invStock : stockList) {
                    StockBizHelper.linkageUpdateStockForSales(itemService, stockInvSectionService,
                        invStockService, stockInvStockChannelService, stockChangeQueueDao, invStockChangeService,
                        invStock, section, true);
                }
            }

//            transactionManagerStock.commit(status);
            result.setMessage(remark);
            result.setResult(true);
            result.setSuccess(true);
        } catch (Exception e) {
//            transactionManagerStock.rollback(status);
            result.setResult(false);
            result.setSuccess(false);

            if (e instanceof BusinessException) {
                result.setMessage(e.getMessage());
            } else {
                result.setMessage("位置错误!");
                log.error(LOG_MARK + "计算库存(" + stockTransaction.getSku() + ","
                          + stockTransaction.getSecCode() + "," + stockTransaction.getCorderSn()
                          + "," + stockTransaction.getBillType() + "," + stockTransaction.getMark()
                          + ",remark:" + (StringUtil.isEmpty(mark) ? "." : mark) + ")失败:",
                    e);
            }
        }

        return result;
    }
    /**
     * 批次设置
     */
    private void updateStockBatch(String sku, String secCode, String refNo,
                                  InventoryBusinessTypes bizType, String mark, Integer qty) {
        if ("S".equals(mark)) {//入库,自动新增批次，不考虑多次入库一个批次的情况
            String batchNum;
            String yyMMdd = DateUtil.format(new Date(), "yyMMdd");
            String lastBatchNum = stockInvStockBatchService.getLastBatchNum();
            if (!StringUtil.isEmpty(lastBatchNum) && yyMMdd.equals(lastBatchNum.substring(0, 6))) {
                int num = Integer.parseInt(lastBatchNum.substring(6)) + 1;
                batchNum = String.valueOf(num);
                while (batchNum.length() < 6) {
                    batchNum = "0" + batchNum;
                }
                batchNum = yyMMdd + batchNum;
            } else {
                batchNum = yyMMdd + "000001";
            }
            InvStockBatch stockBatch = new InvStockBatch();
            stockBatch.setBatchNum(batchNum);
            stockBatch.setBilltype(bizType.getCode());
            stockBatch.setOutQty(0);
            stockBatch.setRefno(refNo);
            stockBatch.setSecCode(secCode);
            stockBatch.setSku(sku);
            Date now = new Date();
            stockBatch.setAddTime(now);
            stockBatch.setUpdateTime(now);
            stockBatch.setStockQty(qty);
            stockInvStockBatchService.insert(stockBatch);
        } else if ("H".equals(mark)) {//出库
            while (qty > 0) {
                Integer _qty;
                InvStockBatch stockBatch = stockInvStockBatchService.getFrontAvailable(sku, secCode);
                if (stockBatch == null) {
                    return;
                }
                Integer stockQty = stockBatch.getStockQty();
                _qty = stockQty >= qty ? qty : stockQty;
                qty -= _qty;
                stockBatch.setStockQty(_qty);
                stockBatch.setOutQty(_qty);
                stockBatch.setUpdateTime(new Date());
                stockInvStockBatchService.updateQty(stockBatch);
            }
        }
    }
    /**
     * 更新基础库存
     */
    private InvBaseStock updateStockBase(String sku, String lesSecCode, String secCode, Integer qty,
                                         Integer releaseQty, InventoryBusinessTypes bizType,
                                         String mark, String refNo) {
        boolean isInsert = false;

        int stockQty;//当前库存
        int newStockQty;//新的库存
        int frozenQty;//当前冻结库存
        int newFrozenQty;//新的冻结库存

        InvBaseStock baseStock = (InvBaseStock) stockInvBaseStockService.get(sku, secCode);

        if (baseStock == null) {
            baseStock = new InvBaseStock();
            baseStock.setSku(sku);
            baseStock.setSecCode(secCode);
            baseStock.setLesSecCode(lesSecCode);
            baseStock.setCreateTime(new Date());
            baseStock.setFrozenQty(0);
            baseStock.setStockQty(0);
            baseStock.setUpdateTime(new Date());
            isInsert = true;
            //给添加的字段赋值
            assignmentValue2InvBaseStock(baseStock);
        }

        stockQty = baseStock.getStockQty();
        frozenQty = baseStock.getFrozenQty();

        Integer signedQty = qty;
        if ("H".equalsIgnoreCase(mark)) {
            signedQty = -qty;
        }
        newStockQty = stockQty + signedQty;
        newFrozenQty = frozenQty - releaseQty;

        if (newStockQty < 0) {
            throw new BusinessException("当前库存不足，无法出库，应出 " + qty + " ,可用库存：" + stockQty);
        }

        Date now = new Date();

        if (isInsert) {
            baseStock.setStockQty(newStockQty);
            baseStock.setFrozenQty(newFrozenQty);
            baseStock.setCreateTime(now);
            baseStock.setUpdateTime(now);
            stockInvBaseStockService.insert(baseStock);
        } else {
            Integer n = stockInvBaseStockService.updateQty(baseStock.getStoId(), signedQty, releaseQty, now);
            if (n <= 0) {
                throw new BusinessException("数据错误或者当前库存不满足");
            }
        }

        baseStock = (InvBaseStock) stockInvBaseStockService.get(sku, secCode);//重新获取记录

        //记录库存变化日志
        InvBaseStockLog baseStockLog = new InvBaseStockLog();
        baseStockLog.setSku(sku);
        baseStockLog.setSecCode(secCode);
        baseStockLog.setLesSecCode(lesSecCode);
        baseStockLog.setOldFrozenQty(frozenQty);
        baseStockLog.setOldStockQty(baseStock.getStockQty() - signedQty);
        baseStockLog.setNewFrozenQty(baseStock.getFrozenQty());
        baseStockLog.setNewStockQty(baseStock.getStockQty());
        baseStockLog.setBillType(bizType.getCode());
        baseStockLog.setRefNo(refNo);
        baseStockLog.setMark(mark);
        baseStockLog.setCreateTime(now);
        //给添加的字段赋值
        assignmentValue2InvBaseStockLog(baseStockLog);
        invBaseStockLogService.insert(baseStockLog);
        return baseStock;
    }
    

    private void recordBaseStockLog(InvBaseStock baseStock, Integer newStockQty,
                                    Integer newFrozenQty, String refNo, String mark,
                                    InventoryBusinessTypes bizType, Date dateTime) {
        //记录库存变化日志
        InvBaseStockLog baseStockLog = new InvBaseStockLog();
        baseStockLog.setSku(baseStock.getSku());
        baseStockLog.setSecCode(baseStock.getSecCode());
        baseStockLog.setLesSecCode(baseStock.getLesSecCode());
        baseStockLog.setOldFrozenQty(baseStock.getFrozenQty());
        baseStockLog.setOldStockQty(baseStock.getStockQty());
        baseStockLog.setNewFrozenQty(newFrozenQty);
        baseStockLog.setNewStockQty(newStockQty);
        baseStockLog.setBillType(bizType.getCode());
        baseStockLog.setRefNo(refNo);
        baseStockLog.setMark(mark);
        baseStockLog.setCreateTime(dateTime);
        //给添加的字段赋值
        assignmentValue2InvBaseStockLog(baseStockLog);
        invBaseStockLogService.insert(baseStockLog);
    }
    
    /**
     * 给InvBaseStock添加的字段赋值
     * @param baseStock 基础库存信息
     */
    private void assignmentValue2InvBaseStock(InvBaseStock baseStock) {
        ItemBase itemBase = getItemBaseBySku(baseStock.getSku());
        if (itemBase != null) {
            String productName = itemBase.getMaterialDescription();//产品型号
            baseStock.setProductName(productName);
            ItemAttribute itemAttribute = getItemAttributeByValueSetIdAndValue("ProductGroup",
                itemBase.getDepartment());
            if (itemAttribute != null) {
                String productTypeName = itemAttribute.getCbsCategory();//品类
                baseStock.setProductTypeName(productTypeName);
                String productGroupName = itemAttribute.getValueMeaning();//产品组
                baseStock.setProductGroupName(productGroupName);
            }
        }
        InvSection invSection = stockInvSectionService.getBySecCode(baseStock.getSecCode());
        if (invSection != null) {
            String secName = invSection.getSecName();//库位名称
            baseStock.setSecName(secName);
            String itemProperty = invSection.getItemProperty();//批次
            baseStock.setItemProperty(itemProperty);
        }
    }
    
    /**
     * 查询ItemAttribute
     * @param valueSetId 属性标识
     * @param value 属性值
     * @return 属性信息
     */
    private ItemAttribute getItemAttributeByValueSetIdAndValue(String valueSetId, String value) {
        ServiceResult<ItemAttribute> result = itemService
            .getItemAttributeByValueSetIdAndValue(valueSetId, value);
        if (result == null || !result.getSuccess())
            return null;
        return result.getResult();
    }
    
    /**
     * CBS库存业务--同步RRS库存，更新 inv_les_stock,并同步RRS库存
     * @param sku 物料
     * @param secCode 库位
     * @param channelCode 渠道
     * @param newQty 数量
     */
    @Deprecated
    public void updateLesStock(String sku, String secCode, String channelCode, Integer newQty) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

//        TransactionStatus status = transactionManagerStock.getTransaction(def);
        TransactionStatus statusShop = dataSourceTransactionManager.getTransaction(def);
        try {
            //查询les库存记录是否存在，如果不存在，则需新增
            InvLesStock lesStock = invLesStockService.getBySecCodeAndSku(secCode, sku);
            if (lesStock == null) {
                lesStock = new InvLesStock();
                lesStock.setSecCode(secCode);
                lesStock.setSku(sku);
                lesStock.setStockQty(newQty);
                invLesStockService.insert(lesStock);
            } else {//更新库存
                lesStock.setStockQty(newQty);
                invLesStockService.update(lesStock);
            }
            //同步日日顺可用库存
            if (InvSection.CHANNEL_CODE_RRS.equalsIgnoreCase(channelCode)) {
                //解套
                List<InvMachineSet> machineSets = stockInvMachineSetService.getByMainSku(sku);
                if (machineSets.size() <= 0)
                    syncRRSStockQtyFromLes(sku, secCode, newQty);
                else
                    for (InvMachineSet machineSet : machineSets) {
                        String _sku = machineSet.getSubSku();
                        Integer _newQty = machineSet.getMenge().intValue() * newQty;
                        syncRRSStockQtyFromLes(_sku, secCode, _newQty);
                    }
            }
            dataSourceTransactionManager.commit(statusShop);
//            transactionManagerStock.commit(status);
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(statusShop);
//            transactionManagerStock.rollback(status);
            log.error("更新LES库存信息失败：", e);
            throw new BusinessException("更新LES库存信息失败：" + e.getMessage());
        }
    }
    /**
     * CBS库存业务--同步日日顺库存
     * @param sku 物料
     * @param secCode 库位
     * @param newQty 数量
     */
    private void syncRRSStockQtyFromLes(String sku, String secCode, Integer newQty) {
        //如果日日顺库位不存在，新增库位信息
        InvSection section = stockInvSectionService.getBySecCode(secCode);
        if (section == null)
            insertInvSection(secCode, InvSection.CHANNEL_CODE_RRS, InvSection.W10);
        Date dateTime = new Date();
        InvBaseStock baseStock = stockInvBaseStockService.getForUpdate(sku, secCode);
        boolean isInsert = false;
        if (baseStock == null) {
            isInsert = true;
            baseStock = new InvBaseStock();
            baseStock.setSku(sku);
            baseStock.setLesSecCode(secCode);
            baseStock.setSecCode(secCode);
            baseStock.setStockQty(0);
            baseStock.setFrozenQty(0);
            baseStock.setCreateTime(dateTime);
            //给添加的字段赋值
            assignmentValue2InvBaseStock(baseStock);
        }
        //日日顺实际库存=LES同步的可用库存+CBS冻结库存数量
        Integer newStockQty = newQty + baseStock.getFrozenQty();// 库存数量=同步数量（可用库存）+开提单占用数量
        if (newStockQty < 0)
            newStockQty = 0;
        recordBaseStockLog(baseStock, newStockQty, baseStock.getFrozenQty(), "", "",
            InventoryBusinessTypes.SYNC_STOCK, dateTime);
        baseStock.setStockQty(newStockQty);
        baseStock.setUpdateTime(dateTime);
        //更新InvBaseStock
        if (isInsert)
            stockInvBaseStockService.insert(baseStock);
        else
            stockInvBaseStockService.updateStockQty(baseStock.getStoId(), newStockQty, dateTime);
        //直接是可以数
        List<InvStock> stockList = regroupStockQtyBySku(sku, secCode, baseStock.getStockQty());
        //同步库存到商城
        InvSection stockSection;
        for (InvStock stock : stockList) {
            stockSection = this.stockInvSectionService.getBySecCode(stock.getSecCode());
            //联动更新销售库存信息
            StockBizHelper.linkageUpdateStockForSales(itemService, stockInvSectionService, invStockService,
                stockInvStockChannelService, stockChangeQueueDao, invStockChangeService, stock, stockSection,
                true);

        }
    }
    
    private InvSection insertInvSection(String secCode, String channelCode, String itemProperty) {
        //新增库位
        InvSection section = new InvSection();
        section.setChannelCode(channelCode);
        section.setCorpCode("");
        section.setCustCode("");
        section.setCreateUser("系统");
        section.setCustCode("");
        if (StringUtil.isEmpty(itemProperty)) {
            itemProperty = InvSection.W10;
        }
        section.setItemProperty(itemProperty);
        section.setRegionCode("");
        section.setLesSecCode(secCode);
        section.setSecCode(secCode);
        section.setSecName("");
        section.setStatus(InvSection.STATUS_IS_VALID);
        section.setUpdateUser("系统");
        if (InvSection.CHANNEL_CODE_GD.equals(channelCode)) {
            section.setWhCode(InvSection.CHANNEL_CODE_GD);
        } else {
            section.setWhCode(secCode.length() > 2 ? secCode.substring(0, 2) : secCode);
        }
        section.setEhaierDeliverCode("");
        section.setLes0eCode("");
        stockInvSectionService.insert(section);
        return section;
    }
    
    /**
     * CBS库存业务--基地库存入库
     * @param sku 物料
     * @param secCode 库位
     * @param channelCode 渠道
     * @param newQty 数量
     */
    public void updateJiDiStock(String sku, String secCode, String channelCode, Integer newQty) {

        if (!InvSection.CHANNEL_CODE_GD.equals(channelCode)) {
            log.info("updateJiDiStock:渠道编码channelCode=" + channelCode + "，无法同步");
            return;
        }
        //套机解套
        List<InvMachineSet> machineSets = stockInvMachineSetService.getByMainSku(sku);
        List<StockHolder> stockHolderList = new ArrayList<StockHolder>();
        StockHolder stockHolder;
        if (machineSets.size() <= 0) {
            stockHolder = new StockHolder(sku, secCode, newQty);
            stockHolderList.add(stockHolder);
        } else {
            for (InvMachineSet machineSet : machineSets) {
                String _sku = machineSet.getSubSku();
                Integer _newQty = machineSet.getMenge().intValue() * newQty;
                stockHolder = new StockHolder(_sku, secCode, _newQty);
                stockHolderList.add(stockHolder);
            }
        }
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

//        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            for (StockHolder holder : stockHolderList) {
                Integer newStockQty = holder.getChangeQty();
                if (newStockQty < 0)
                    newStockQty = 0;
                //查找库位
                InvSection section = stockInvSectionService.getBySecCode(holder.getSecCode());
                //不存在添加
                if (section == null) {
                    insertInvSection(secCode, InvSection.CHANNEL_CODE_GD, InvSection.W10);
                }
                //查找库存
                InvBaseStock baseStock = stockInvBaseStockService.getForUpdate(sku, holder.getSecCode());
                //不存在添加
                Date dateTime = new Date();
                boolean isInsert = false;
                if (baseStock == null) {
                    isInsert = true;
                    baseStock = new InvBaseStock();
                    baseStock.setSku(holder.getSku());
                    baseStock.setLesSecCode(holder.getSecCode());
                    baseStock.setSecCode(holder.getSecCode());
                    baseStock.setStockQty(newStockQty);
                    baseStock.setFrozenQty(0);
                    baseStock.setCreateTime(dateTime);
                    //给添加的字段赋值
                    assignmentValue2InvBaseStock(baseStock);
                } else {
                    //计算实际库存：实际库存至少为已经冻结的
                    newStockQty = holder.getChangeQty() >= baseStock.getFrozenQty() ? holder.getChangeQty()
                        : baseStock.getFrozenQty();
                }
                //记录同步日志
                recordBaseStockLog(baseStock, newStockQty, baseStock.getFrozenQty(), "", "",
                    InventoryBusinessTypes.SYNC_STOCK, dateTime);
                //设置实际库存
                baseStock.setStockQty(newStockQty);
                baseStock.setUpdateTime(dateTime);
                //更新InvBaseStock
                if (isInsert)
                    stockInvBaseStockService.insert(baseStock);
                else
                    stockInvBaseStockService.updateStockQty(baseStock.getStoId(), newStockQty, dateTime);
                //计算销售库存数,包括涉及到的套机计算
                List<InvStock> stockList = regroupStockQtyBySku(sku, secCode,
                    baseStock.getStockQty() - baseStock.getFrozenQty());
                InvSection stockSection;
                for (InvStock stock : stockList) {
                    stockSection = this.stockInvSectionService.getBySecCode(stock.getSecCode());
                    //联动更新销售库存信息
                    StockBizHelper.linkageUpdateStockForSales(itemService, stockInvSectionService,
                        invStockService, stockInvStockChannelService, stockChangeQueueDao, invStockChangeService,
                        stock, stockSection, true);
                }
            }
//            transactionManagerStock.commit(status);
        } catch (Exception e) {
//            transactionManagerStock.rollback(status);
            log.error("updateJiDiStock:更新基地库存信息失败， sku=" + sku + ",secCode=" + secCode + ",channel="
                      + channelCode + ", qty=" + newQty,
                e);
            throw new BusinessException("更新基地库存信息失败：" + e.getMessage());
        }
    }
    
    /**
     * CBS库存业务--有冻结记录的出库
     * @param refNo 单号
     * @param billType 交易类型
     * @param stockHoldersForOut 需要出库的对象列表
     * @param optUser 操作人
     * @return 执行结果
     */
    public boolean outFrozenStockQty(String refNo, InventoryBusinessTypes billType,
                                     List<StockHolder> stockHoldersForOut,
                                     String optUser) throws Exception {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

//        TransactionStatus status = transactionManagerStock.getTransaction(def);
        TransactionStatus statusShop = dataSourceTransactionManager.getTransaction(def);

        try {
            //中间变量
            Map<String, InvStock> skuStockMap = new HashMap<String, InvStock>();
            //物料对应的套机List
            List<InvStock> stockList;
            InvSection section;
            for (StockHolder holder : stockHoldersForOut) {
                //查询库位信息
                section = this.stockInvSectionService.getBySecCode(holder.getSecCode());
                //出库操作
                InvBaseStock baseStock = updateCbsStockByInventoryBusiness(holder.getSku(), section,
                    holder.getChangeQty(), holder.getChangeQty(), billType, billType.getMark(), refNo, false);
                //计算此子件对应的套机
                stockList = regroupStockQtyBySku(holder.getSku(), baseStock.getSecCode(),
                    baseStock.getStockQty() - baseStock.getFrozenQty());
                for (InvStock stock : stockList) {
                    String pKey = stock.getSku() + stock.getSecCode();
                    if (!skuStockMap.containsKey(pKey)) {
                        skuStockMap.put(pKey, stock);
                    } else if (skuStockMap.get(pKey).getStockQty() < stock.getStockQty()) {
                        skuStockMap.put(pKey, stock);
                    }
                }
            }
            //更新InvStock以及相关到货通知
            InvSection stockSection;
            for (Map.Entry<String, InvStock> stockEntry : skuStockMap.entrySet()) {
                stockSection = this.stockInvSectionService.getBySecCode(stockEntry.getValue().getSecCode());
                StockBizHelper.linkageUpdateStockForSales(itemService, stockInvSectionService, invStockService,
                    stockInvStockChannelService, stockChangeQueueDao, invStockChangeService,
                    stockEntry.getValue(), stockSection, true);
            }
            dataSourceTransactionManager.commit(statusShop);
//            transactionManagerStock.commit(status);

            if (!StringUtil.isEmpty(optUser) && log.isDebugEnabled()) {
                log.debug(optUser);
            }

            return true;
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(statusShop);
//            transactionManagerStock.rollback(status);
            throw e;
        }
    }
    /**
     * 根据出入库记录计算库存
     * @param sku 物料
     * @param section 库位信息
     * @param qty 数量
     * @param releaseQty 数量
     * @param bizType 交易类型
     * @param mark 借贷标志
     * @param refNo 单据号
     */
    private InvBaseStock updateCbsStockByInventoryBusiness(String sku, InvSection section,
                                                           Integer qty, Integer releaseQty,
                                                           InventoryBusinessTypes bizType,
                                                           String mark, String refNo,
                                                           boolean isForzeStock) {
        /*
         * 1.日日顺库存
         * 2.基地库存
         * 都不记录入批次表
         */
        if (!InvSection.CHANNEL_CODE_RRS.equalsIgnoreCase(section.getChannelCode())
            && !InvSection.CHANNEL_CODE_GD.equalsIgnoreCase(section.getChannelCode()))
            //库存批次设置
            updateStockBatch(sku, section.getSecCode(), refNo, bizType, mark, qty);
        //更新基础库存
        Integer baseReleaseQty = 0;
        if (releaseQty > 0) {//确定基础库存冻结数量
            InvStockLock stockLock = stockInvStockLockService.getLast(refNo, sku, section.getSecCode());
            if (stockLock == null) {
                baseReleaseQty = 0;
                updateStockLockAfterReleaseQty(sku, section.getSecCode(), refNo, releaseQty);
            } else {
                Integer availableReleaseQty = stockLock.getLockQty() - stockLock.getRealeaseQty();
                baseReleaseQty = availableReleaseQty <= releaseQty ? availableReleaseQty
                    : releaseQty;
                if (baseReleaseQty <= 0) {
                    //log.info("此交易已经释放库存，不再释放");
                    baseReleaseQty = 0;
                } else
                    updateStockLockAfterReleaseQty(sku, section.getSecCode(), refNo,
                        baseReleaseQty);
            }
        }

        InvBaseStock baseStock = updateStockBase(sku, section.getLesSecCode(), section.getSecCode(),
            qty, baseReleaseQty, bizType, mark, refNo);

        //如果是日日单需要占用库存
        if (isForzeStock) {
            List<InvStockLock> locks = stockInvStockLockService.getNotReleasedByRefNo(refNo);
            boolean isHasFrozen = false;
            for (InvStockLock lock : locks) {
                if (lock.getSku().equalsIgnoreCase(sku))
                    isHasFrozen = true;
            }
            if (!isHasFrozen) {
                baseStock = basicFrozeStockQty(sku, section.getSecCode(), refNo, qty,
                    InventoryBusinessTypes.FROZEN_BY_ZBCC, refNo, refNo);
            }
        }

        return baseStock;
        /*    //更新invStock并同步到商城
            syncStockToShop(sku, section, baseStock.getStockQty() - baseStock.getFrozenQty(),
                releaseQty, baseReleaseQty, mainSku, refNo);*/
    }
    
    /*********************************销售子键Start***********************************/
    /**
     * CBS库存业务--添加子件销售
     * @param sku 待销售的子件
     * @return 是否更新成功
     */
    public boolean saleSubMachine(String sku) {
        if (com.haier.common.util.StringUtil.isEmpty(sku, true)) {
            log.info("saleSubMachine:更新支持卖sku=" + sku + "子件销售，无效的SKU");
            return false;
        }
        Integer rows = invStockService.addSaleSubMachines(sku);
        return rows > 0;
    }
    
    /**
     * CBS库存业务--取消子件销售
     * @param sku 待取消的子物料编码
     * @return 是否取消成功
     */
    public boolean deleteSaleSubMachine(String sku) {
        if (com.haier.common.util.StringUtil.isEmpty(sku, true)) {
            log.info("deleteSaleSubMachines:取消支持卖sku=" + sku + "子件销售，无效的SKU");
            return false;
        }
        Integer rows = invStockService.deleteSaleSubMachines(sku);
        return rows > 0;
    }
    /**
     * 根据物料编码、库位获取CBS库存
     * @param sku 物料
     * @param secCode 库位
     * @return 销售库存
     */
    public InvStock getInvStock(String sku, String secCode) {
        return this.invStockService.getBySecCodeAndSku(secCode, sku);
    }

    /**
     * 获取库存变化队列
     * @param topX 前X条
     * @return 变化队列
     */
    public List<StockChangeQueue> getStockChangeQueue(Integer topX) {
        return stockChangeQueueDao.getListToProcess(topX);
    }

    /**
     * 处理后，删除变化队列
     * @param id 队列id
     * @return 影响行数
     */
    public Integer deleteStockChangeQueue(Integer id) {
        return stockChangeQueueDao.delete(id);
    }
    
    public Boolean machineSetSync(InvMachineSet machineSet) {
        if (machineSet == null)
            return false;
        InvMachineSet machineSetNow = stockInvMachineSetService.getByMainSkuAndBomNum(machineSet.getMainSku(), machineSet.getPosnr());
        Date now = DateUtil.currentDateTime();
        if (machineSetNow == null) {
            machineSet.setCreateTime(now);
            machineSet.setUpdateTime(now);
            stockInvMachineSetService.insert(machineSet);
        } else {
            machineSet.setId(machineSetNow.getId());
            machineSet.setCreateTime(machineSetNow.getCreateTime());
            machineSet.setUpdateTime(now);
            stockInvMachineSetService.update(machineSet);
        }
        return true;
    }
    
    /**
     * 取得所有有效的锁定记录
     * 有效：库位、sku非空，商城、天猫、企业字段有大于0的数据
     * @return 库存锁定记录
     */
    public List<StorageProducts> getAllEffectiveLocks() {
        return storageProductsService.getAllEffectiveLocks();
    }

    /**
     * 取得所有库位中（分渠道）物料库存信息
     * 
     * @return list of InvStock2Channel
     */
    public List<InvStock2Channel> getInvStockChannelLst() {
        return invStock2ChannelService.getInvStockChannelLst();
    }
    
    /**
     * 获取库存
     * @param sCode 库位
     * @param sku 物料编码
     * @return StorageProducts
     */
    public StorageProducts getStorageProducts(String sCode, String sku) {
        return storageProductsService.getBySCodeAndSku(sCode, sku);
    }

    /**
     * 查询未处理库存记录
     * @param lastBatchId 最新BatchId
     * @return 库存批次记录
     */
    public List<InvStockBatch> queryInvStockBatchList(Integer lastBatchId, int startIndex,
                                                      int pageSize) {
        return this.stockInvStockBatchService.queryInvStockBatch(lastBatchId, startIndex, pageSize);
    }
    
    /**
     * 查询并记录差异库存数
     * @return msg
     */
    public String compareStockQtyDifLog() {
        StringBuilder msg = new StringBuilder();
        List<InvStockQtyDifLog> difList = this.invStockQtyDifLogService.queryDifStockQty();
        msg.append("本次差异数据有").append(difList.size()).append("条");
        int errorCnt = 0;
        for (InvStockQtyDifLog difLog : difList) {
            try {
                difLog.setCreateUser("[job]");
                this.invStockQtyDifLogService.insert(difLog);
            } catch (Exception e) {
                errorCnt++;
                log.error("记录差异库存出现错误", e);
                e.printStackTrace();
            }
        }
        msg.append(",失败插入").append(errorCnt).append("条");
        return msg.toString();
    }
    

    public List<String> getSubSkuListByMainSku(String sku) {
        List<InvMachineSet> machineSets = stockInvMachineSetService.getByMainSku(sku);
        List<String> skuList = new ArrayList<String>();
        if (null != machineSets && !machineSets.isEmpty()) {
            for (InvMachineSet invMachineSet : machineSets) {
                skuList.add(invMachineSet.getSubSku());
            }
        }
        return skuList;
    }
    
    public List<InvDbmBase> selectInvDbmBase(Map<String, Object> params) {
        List<InvDbmBase> result = new ArrayList<InvDbmBase>();
        try {
            result = invDbmBaseService.selectInvDbmBaseItem(params);
        } catch (Exception ex) {
            log.error("[StockModel][selectInvDbmBase]:查询失败:", ex);
            return result;
        }
        return result;
    }
    
    public List<String> findAllBaseStorage(Map params) {
        List<String> result = new ArrayList<String>();
        try {
            result = invDbmBaseService.findAllBaseStorage(params);
        } catch (Exception ex) {
            log.error("[StockModel][findAllBaseStorage]:查询失败:", ex);
            return result;
        }
        return result;
    }
    
    public List<InvDbmBaseSendAddress> selecInvDbmBaseSendAddress(Map<String, Object> params) {
        List<InvDbmBaseSendAddress> result = new ArrayList<InvDbmBaseSendAddress>();
        try {
            result = invDbmBaseSendAddressService.selectInvDbmBaseSendAddressItem(params);
        } catch (Exception ex) {
            log.error("[StockModel][selecInvDbmBaseSendAddress]:查询失败:", ex);
            return result;
        }
        return result;
    }
    public Date syncSgStockFromEisToShop(Date lastSyncDate) {

        StringBuilder logMsg = new StringBuilder(LOG_MARK).append("预处理");
        long tsBegin = System.currentTimeMillis();
        int total = 0;

        Date syncDate = lastSyncDate;
        //获取变化的库存记录
        List<InvSgStockEntity> stocks = invSgStockService.fingSgStockByLastTime(lastSyncDate, 5000);
        if (stocks.size() <= 0) {
            log.info(LOG_MARK + "无变化库存,处理完成");
            return syncDate;
        }
        do {
            logMsg = new StringBuilder(LOG_MARK);
            total += stocks.size();
            logMsg.append("库存变化,size:").append(stocks.size());
            for (InvSgStockEntity stock : stocks) {
                if (syncDate.before(stock.getModifyTime())) {//记录最大库存变化时间
                    syncDate = stock.getModifyTime();
                }
                String storeCode = sgRealtimeStockService
                    .findStoreCodeByStoreId(Integer.valueOf(stock.getStoreCode()));
                if (null == storeCode) {
                    log.error(stock.getStoreCode() + "：无匹配店铺编码！");
                    continue;
                }
                SgRealtimeStock record = new SgRealtimeStock();
                record.setSku(stock.getSku());
                record.setStoreId(Integer.valueOf(stock.getStoreCode()));
                record.setStoreCode(storeCode);
                record.setScode(stock.getScode());
                SgRealtimeStock selectByParams = sgRealtimeStockService.selectByParams(record);
                if (null == selectByParams) {
                    record.setStockQty(stock.getStockQty());
                    record.setStoreTs(stock.getModifyTime());
                    record.setAddTime(new Date());
                    sgRealtimeStockService.insert(record);
                } else {
                    selectByParams.setStockQty(stock.getStockQty());
                    selectByParams.setStoreTs(stock.getModifyTime());
                    sgRealtimeStockService.updateByParams(selectByParams);
                }

            }
            //continue
            if (syncDate.after(lastSyncDate)) {
                stocks = invSgStockService.fingSgStockByLastTime(syncDate, 5000);
            }
        } while (stocks.size() > 0);
        logMsg = new StringBuilder(LOG_MARK);
        logMsg.append("syncToShop 完成,共").append(total).append("条记录,用时 ")
            .append(System.currentTimeMillis() - tsBegin).append(" ms.");
        log.info(logMsg.toString());
        return syncDate;
    }
    
    /**
     * 定时同步LES库存
     */
    public Integer autoSynchLesStock(Integer maxId, Integer maxNum) {
        List<InvBaseStockDiff> lists = invBaseStockDiffService.queryInvBaseStockDiff(maxId, maxNum);
        if (lists != null && !lists.isEmpty()) {
            for (InvBaseStockDiff stockDiff : lists) {
                try {
                    QueryLesStockToCbs query = new QueryLesStockToCbs();
                    if (StringUtils.isEmpty(stockDiff.getSku())) {
                        return null;
                    }
                    if (StringUtils.isEmpty(stockDiff.getLesSecCode())) {
                        return null;
                    }
                    if (maxId != null && stockDiff.getId() != null
                        && maxId.intValue() < stockDiff.getId().intValue()) {
                        maxId = stockDiff.getId();
                    }
                    query.setSku(stockDiff.getSku());
                    query.setSecCode(stockDiff.getLesSecCode());
                    query.setFlag("4");//cbs-les库存核对

                    InvBaseStock baseStock = stockInvBaseStockService.queryBySkuAndLesSecCode(query.getSku(),
                        query.getSecCode());
                    if (baseStock != null) {
                        stockDiff.setStockQty(baseStock.getStockQty());
                        stockDiff.setFrozenQty(baseStock.getFrozenQty());
                    }
                    ServiceResult<QueryLesStockOutType> res = stockCenterLESService.queryLesStock(query);
                    //DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                    //def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                    //TransactionStatus status = transactionManagerStock.getTransaction(def);
                    if (res.getSuccess() && res.getResult() != null) {
                        List<CBSKCType> types = res.getResult().getCbskc();
                        if (types != null && !types.isEmpty()) {
                            for (CBSKCType type : types) {
                                if ("10".equals(type.getATWART())) {
                                    //更新LES-CBS库存对比差异表
                                    stockDiff = updateInvBaseStockDiff(type, stockDiff);
                                    //往LES-CBS库存对比差异日志表中插入一条记录
                                    insertInvBaseStockDifLog(stockDiff);
                                }
                            }
                            //transactionManagerStock.commit(status);
                        }
                    }
                } catch (Exception e) {
                    // transactionManagerStock.rollback(status);
                    log.error("同步les实时库存信息(id)：" + stockDiff.getId(), e);
                    return maxId;
                }
            }
            log.error("同步les实时库存信息-返回最大id(maxId)：" + maxId);
            return maxId;
        }
        log.error("同步les实时库存信息已经同步结束，同步到最后一条数据，最大id(maxId)：" + maxId);
        return null;
    }
    
    /**
     * 定时删除invBaseStockDiffLog表中5天之前数据
     */
    public Boolean autoDeleteInvBaseStockDiffLog() {
        try {
            String maxDate = invBaseStockDiffLogService.getMaxTime();
            if (maxDate == null) {
                return false;
            }
            if (maxDate != null) {
                invBaseStockDiffLogService.batchDelete(maxDate, max_day);
            }
            return true;
        } catch (Exception e) {
            log.error("定时删除invBaseStockDiffLog表中数据出现异常" + e);
            return false;
        }
    }
    
    public List<SgFlagShipStoreAuthorization> syncToRegionEc(Integer storeId,
                                                             Integer brandId, String department) {
		List<SgFlagShipStoreAuthorization> lists = new ArrayList<SgFlagShipStoreAuthorization>();
		try {
			lists = sgFlagShipStoreAuthorizationService.queryByCondition(storeId,
					brandId, department);
		} catch (Exception e) {
			log.error("查询SgFlagShipStoreAuthorization出现异常" + e);
		}
		return lists;
	}
    
    /**
     * 根据条件查询产品列表,物流编码大小写不敏感
     * @param base {"Department": "产品组,"MaterialCode": "物料","DeleteFlag": "删除标识"}
     * @return search result
     */
//    public List<ItemBase> queryItemBaseByParamWithLike(ItemBase base) {
//        if (base == null) {
//            return null;
//        }
//        if (!StringUtils.isBlank(base.getDepartment())) {
//            base.setDepartments(new String[] { base.getDepartment() });
//        }
//        if (!StringUtils.isBlank(base.getMaterialCode())) {
//            base.setMaterialCode(base.getMaterialCode().toLowerCase());
//        }
//        if (null == base.getDeleteFlag()) {
//            base.setDeleteFlag(0);
//        }
//        return itemBaseDao.queryItemBaseByParamWithLike(base);
//    }

    /**
     * 根据条件查询产品记录数,物流编码大小写不敏感
     * @param base {"Department": "产品组,"MaterialCode": "物料","DeleteFlag": "删除标识"}
     * @return 符合条件的物料信息数
     */
//    public Integer countItemBaseByParamWithLike(ItemBase base) {
//        if (base == null) {
//            return null;
//        }
//        if (!StringUtils.isBlank(base.getDepartment())) {
//            base.setDepartments(new String[] { base.getDepartment() });
//        }
//        if (!StringUtils.isBlank(base.getMaterialCode())) {
//            base.setMaterialCode(base.getMaterialCode().toLowerCase());
//        }
//        if (null == base.getDeleteFlag()) {
//            base.setDeleteFlag(0);
//        }
//        return itemBaseDao.countItemBaseByParamWithLike(base);
//    }

    /**
     * 查询 -- 根据产品组获取ItemBase
     * @param depList list of 产品组
     * @return query result 
     */
//    public List<ItemBase> getItemBaseListByDepList(List<String> depList) {
//        if (depList == null) {
//            return null;
//        }
//        return itemBaseDao.getItemListByDepList(depList);
//    }

    /**
     * 查询所有CBS品类
     * @return 品类
     */
//    public List<String> getAllCbsCategory() {
//        return itemAttributeDao.getAllCbsCategory();
//    }

    private InvBaseStockDiff updateInvBaseStockDiff(CBSKCType type, InvBaseStockDiff stockDiff) {
        if (type.getCLABS() != null && stockDiff.getStockQty() != null
            && type.getCLABS().intValue() == stockDiff.getStockQty().intValue()) {
            stockDiff.setDiffType(0);
        } else {
            stockDiff.setDiffType(1);
        }
        stockDiff.setLesClabs(type.getCLABS());//实物库存
        stockDiff.setLesCumlm(type.getCUMLM());//在运库存 (从一库存地到另一库存地)
        stockDiff.setLesMenge1(type.getMENGE1());//开票未提
        stockDiff.setLesMenge2(type.getMENGE2());//可用库存数
        stockDiff.setLesZmenge1(type.getZMENGE1());//数量
        invBaseStockDiffService.updateInvBaseStockDiff(stockDiff);
        return stockDiff;
    }
    private void insertInvBaseStockDifLog(InvBaseStockDiff stockDiff) {
        InvBaseStockDiffLog diffLog = new InvBaseStockDiffLog();
        diffLog.setSku(stockDiff.getSku());
        diffLog.setSecName(stockDiff.getSecName());
        diffLog.setLesSecCode(stockDiff.getLesSecCode());
        diffLog.setStockQty(stockDiff.getStockQty());
        diffLog.setFrozenQty(stockDiff.getFrozenQty());
        diffLog.setItemProperty(stockDiff.getItemProperty());
        diffLog.setLesClabs(stockDiff.getLesClabs());
        diffLog.setLesCumlm(stockDiff.getLesCumlm());
        diffLog.setLesMenge1(stockDiff.getLesMenge1());
        diffLog.setLesMenge2(stockDiff.getLesMenge2());
        diffLog.setLesZmenge1(stockDiff.getLesZmenge1());
        invBaseStockDiffLogService.insert(diffLog);
    }
    
    /**
     * 通过仓库和渠道编码查询库位
     * @param whCode
     * @param channelCode
     * @return
     */
    public InvSection getSectionByCode(String whCode, String channelCode) {
        ServiceResult<List<InvSection>> result = stockCommonService
            .getSectionByWhCodeAndChannelCode(whCode, channelCode);
        if (result.getSuccess() && result.getResult() != null && result.getResult().size() >= 0) {
            return result.getResult().get(0);
        }
        return null;

    }
    public InvSection getSectionByCode(String sec_code) {
        ServiceResult<InvSection> rs = stockCommonService.getSectionByCode(sec_code);
        if (!rs.getSuccess())
            throw new BusinessException("通过库存服务获库位信息失败");
        return rs.getResult();
    }
    public ServiceResult<Integer> doInventoryFromUpload(List<InvStockInOut> invStockInOuts) {
        return stockAgeService.stockInOutRecord(invStockInOuts);
    }
    public List<InvStockChannel> getChannels() {
        ServiceResult<List<InvStockChannel>> rs = stockCommonService.getChannels();
        if (!rs.getSuccess())
            throw new BusinessException("通过库存服务获取渠道信息失败");
        return rs.getResult();
    }
    public ProductsNew getProdutsBySku(String sku) {
        ServiceResult<ProductsNew> rs = itemService.getProductBySku(sku);
        if (!rs.getSuccess())
            throw new BusinessException("通过产品服务获取产品信息失败");
        return rs.getResult();
    }
}
