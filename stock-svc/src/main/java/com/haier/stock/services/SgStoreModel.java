package com.haier.stock.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.ProductBase;
import com.haier.shop.model.SgConstants;
import com.haier.shop.model.Stock;
import com.haier.shop.model.StoragesRela;
import com.haier.shop.service.BasChangeStockService;
import com.haier.shop.service.BigStoragesRelaService;
import com.haier.shop.service.InvSgScodeStreetsRefService;
import com.haier.shop.service.StorageCitiesService;
import com.haier.shop.service.StorageStreetsService;
import com.haier.shop.service.StoragesRelaService;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvSgStock;
import com.haier.stock.model.InvSgStockEntity;
import com.haier.stock.model.InvSgStockLock;
import com.haier.stock.model.InvSgStockLogEntity;
import com.haier.stock.model.InvStock;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvStore;
import com.haier.stock.service.InvSgStockLockService;
import com.haier.stock.service.InvSgStockLogService;
import com.haier.stock.service.InvSgStockService;
import com.haier.stock.service.InvStockOrderLockService;
import com.haier.stock.service.InvStockService;
import com.haier.stock.service.InvStoreService;
import com.haier.stock.service.InvWaStreetRefService;
import com.haier.stock.service.StockCenterItemService;
import com.haier.stock.service.StockInvSectionService;
import com.haier.stock.service.StockInvStockChannelService;
import com.haier.stock.services.Helper.StockBizHelper;

/**
 * HopStockModel Created by 钊 on 14-3-28.
 */
@Service
public class SgStoreModel {
    private final static Logger log      = LoggerFactory.getLogger(SgStoreModel.class);
    private final static String LOG_MARK = "[Stock][HopStockModel] ";
    @Autowired
    private StockInvStockChannelService           invStockChannelDao;
    @Autowired
    private InvStockService invStockService;
    @Autowired
    private InvStoreService invStoreService;
    @Autowired
    private BasChangeStockService basChangeStockService;
    @Autowired
    private StockInvSectionService stockInvSectionService;
    @Autowired
    private StockCenterItemService                  itemService;
    @Autowired
    private StorageCitiesService storageCitiesService;
    @Autowired
    private BigStoragesRelaService bigStoragesRelaService;
    @Autowired
    private StoragesRelaService storagesRelaService;
    private DataSourceTransactionManager transactionManagerShop;
    @Autowired
    private InvWaStreetRefService invWaStreetRefService;
    @Autowired
    private InvSgScodeStreetsRefService invSgScodeStreetsRefService;
    @Autowired
    private InvSgStockService invSgStockService;
    @Autowired
    private EStoreStockModel        eStoreStockModel;
    @Autowired
    private InvSgStockLogService invSgStockLogService;
    @Autowired
    private InvSgStockLockService invSgStockLockService;
    @Autowired
    private InvStockOrderLockService invStockOrderLockService;
    @Autowired
    private StorageStreetsService storageStreetsService;

    public InvSgStockLockService getInvSgStockLockDao() {
        return invSgStockLockService;
    }

    public void setInvSgStockLockDao(InvSgStockLockService invSgStockLockService) {
        this.invSgStockLockService = invSgStockLockService;
    }

    public InvSgStockLogService getInvSgStockLogDao() {
        return invSgStockLogService;
    }

    public void setInvSgStockLogDao(InvSgStockLogService invSgStockLogService) {
        this.invSgStockLogService = invSgStockLogService;
    }

    public EStoreStockModel geteStoreStockModel() {
        return eStoreStockModel;
    }

    public void seteStoreStockModel(EStoreStockModel eStoreStockModel) {
        this.eStoreStockModel = eStoreStockModel;
    }

    public StockInvStockChannelService getInvStockChannelDao() {
        return invStockChannelDao;
    }

    public void setInvStockChannelDao(StockInvStockChannelService invStockChannelDao) {
        this.invStockChannelDao = invStockChannelDao;
    }

    public InvStockService getInvStockDao() {
        return invStockService;
    }

    public void setInvStockDao(InvStockService invStockService) {
        this.invStockService = invStockService;
    }

    public InvStoreService getInvStoreDao() {
        return invStoreService;
    }

    public void setInvStoreDao(InvStoreService invStoreService) {
        this.invStoreService = invStoreService;
    }

    public BasChangeStockService getBasChangeStockDao() {
        return basChangeStockService;
    }

    public void setBasChangeStockDao(BasChangeStockService basChangeStockService) {
        this.basChangeStockService = basChangeStockService;
    }

    public StockInvSectionService getInvSectionDao() {
        return stockInvSectionService;
    }

    public void setInvSectionDao(StockInvSectionService stockInvSectionService) {
        this.stockInvSectionService = stockInvSectionService;
    }

    public StockCenterItemService getItemService() {
        return itemService;
    }

    public void setItemService(StockCenterItemService itemService) {
        this.itemService = itemService;
    }

    public StorageCitiesService getStorageCitiesDao() {
        return storageCitiesService;
    }

    public void setStorageCitiesDao(StorageCitiesService storageCitiesService) {
        this.storageCitiesService = storageCitiesService;
    }

    public BigStoragesRelaService getBigStoragesRelaDao() {
        return bigStoragesRelaService;
    }

    public void setBigStoragesRelaDao(BigStoragesRelaService bigStoragesRelaService) {
        this.bigStoragesRelaService = bigStoragesRelaService;
    }

    public StoragesRelaService getStoragesRelaDao() {
        return storagesRelaService;
    }

    public void setStoragesRelaDao(StoragesRelaService storagesRelaService) {
        this.storagesRelaService = storagesRelaService;
    }

    public DataSourceTransactionManager getTransactionManagerShop() {
        return transactionManagerShop;
    }

    public void setTransactionManagerShop(DataSourceTransactionManager transactionManagerShop) {
        this.transactionManagerShop = transactionManagerShop;
    }

    public InvWaStreetRefService getInvWaStreetRefDao() {
        return invWaStreetRefService;
    }

    public void setInvWaStreetRefDao(InvWaStreetRefService invWaStreetRefService) {
        this.invWaStreetRefService = invWaStreetRefService;
    }

    public InvSgScodeStreetsRefService getInvSgScodeStreetsRefDao() {
        return invSgScodeStreetsRefService;
    }

    public void setInvSgScodeStreetsRefDao(InvSgScodeStreetsRefService invSgScodeStreetsRefService) {
        this.invSgScodeStreetsRefService = invSgScodeStreetsRefService;
    }

    public InvSgStockService getInvSgStockDao() {
        return invSgStockService;
    }

    public void setInvSgStockDao(InvSgStockService invSgStockService) {
        this.invSgStockService = invSgStockService;
    }

    public InvStockOrderLockService getInvStockOrderLockDao() {
        return invStockOrderLockService;
    }

    public void setInvStockOrderLockDao(InvStockOrderLockService invStockOrderLockService) {
        this.invStockOrderLockService = invStockOrderLockService;
    }

    public StorageStreetsService getStorageStreetsDao() {
        return storageStreetsService;
    }

    public void setStorageStreetsDao(StorageStreetsService storageStreetsService) {
        this.storageStreetsService = storageStreetsService;
    }

    /**
     * 根据物料和区域获取库存 {@link com.haier.core.data.datasource.Slave}
     * 
     * @param sku 物料
     * @param streetId 街道ID
     * @param channelCode 渠道
     * @param requireQty 需求数量
     * @param isNeedMultipleSecCode 是否多层级
     * @param isReliable 是否可靠库存
     * @return 库存信息
     */
    public Stock getStockBySkuAndRegion(String sku, int streetId, String channelCode,
                                        int requireQty, boolean isNeedMultipleSecCode,
                                        boolean isReliable) {
        return getStockBySkuAndRegion(sku, streetId, channelCode, requireQty, isNeedMultipleSecCode,
            isReliable, true);
    }

    /**
     * 根据物料和区域获取库存 {@link com.haier.core.data.datasource.Slave}
     * 
     * @param sku 物料
     * @param streetId 街道ID
     * @param channelCode 渠道
     * @param requireQty 需求数量
     * @param isNeedMultipleSecCode 是否多层级
     * @param isReliable 是否可靠库存
     * @param lockFlag 下单锁标识
     * @return 库存信息
     */
    public Stock getStockBySkuAndRegion(String sku, int streetId, String channelCode,
                                        int requireQty, boolean isNeedMultipleSecCode,
                                        boolean isReliable, boolean lockFlag) {
        return getStockBySkuAndRegion(sku, streetId, channelCode, requireQty, isNeedMultipleSecCode,
            isReliable, lockFlag, "", false);
    }

    /**
     * 根据物料和区域获取库存 {@link com.haier.core.data.datasource.Slave}
     * 
     * @param sku 物料
     * @param streetId 街道ID
     * @param channelCode 渠道
     * @param requireQty 需求数量
     * @param isNeedMultipleSecCode 是否多层级
     * @param isReliable 是否可靠库存
     * @param lockFlag 下单锁标识
     * @param cOrderSn 网单号，付款时判断是否本身有下单锁
     * @param selfLockFlag 扣减本身下单锁标识 true:不扣减本身锁定数量,操作为加 false:扣减本身锁定数量,不操作
     * @return 库存信息
     */
    public Stock getStockBySkuAndRegion(String sku, int streetId, String channelCode,
                                        int requireQty, boolean isNeedMultipleSecCode,
                                        boolean isReliable, boolean lockFlag, String cOrderSn,
                                        boolean selfLockFlag) {

        long timestamp = System.currentTimeMillis();

        if (requireQty <= 0) {
            requireQty = 1;
        }
        if (selfLockFlag && StringUtil.isEmpty(cOrderSn)) {
            throw new BusinessException("支付时网单号不能为空,sku=" + sku + ",streetId=" + streetId);
        }
        StringBuilder message = new StringBuilder();
        message.append("根据区县获取库存:");
        message.append(isReliable ? "master|" : "salve|");
        InvStockChannel stockChannel = invStockChannelDao.getByCode(channelCode);
        if (stockChannel == null)
            throw new BusinessException("根据区县获取库存：不支持的渠道编码, channel=" + channelCode + ", streetId="
                                        + streetId + ", sku=" + sku);

        Long startTime = System.currentTimeMillis();
        //改造方法：需要通过街道获取WA库位信息。
        //		List<String> regionScodeList = storageCitiesService.getCodeListByRegion(streetId);
        //新建storageStreets表，改到新表查询街道与库位对应关系
        //        List<String> regionScodeList = invWaStreetRefService.findInvWaStreetRefByStreetId(streetId);
        List<String> regionScodeList = storageStreetsService.getSCodeByStreet(streetId);

        message.append("streetId(").append(streetId).append("):")
            .append(System.currentTimeMillis() - startTime).append("ms");
        if (regionScodeList == null || regionScodeList.isEmpty()
            || StringUtil.isEmpty(regionScodeList.get(0))) {
            throw new BusinessException("根据街道获取库存:库位不存在!streetId=" + streetId);
        }

        startTime = System.currentTimeMillis();
        ServiceResult<ProductBase> productResult = itemService.getPorductBaseBySku(sku);
        message.append("|sku(").append(sku).append("):")
            .append(System.currentTimeMillis() - startTime).append("ms");
        ProductBase product;
        if (productResult.getSuccess()) {
            product = productResult.getResult();
        } else {
            throw new BusinessException(productResult.getMessage());
        }
        if (product == null) {
            return StockBizHelper.getZeroStock(sku, regionScodeList.get(0).substring(0, 2));
        }

        String shippingMode = product.getShippingMode().equalsIgnoreCase("B2C") ? "B2C" : "B2B2C";// 物流模式
        Boolean checkMultiCode = product.getMultiStorage().equals(1);// 是否支持多层级，用于大家电
        Boolean isB2C = "B2C".equalsIgnoreCase(shippingMode);
        String oneToAllCode = product.getScode();
        String stockChannelCode = stockChannel.getStockChannelCodes();
        Stock stock;

        message.append("|stock(").append(channelCode);

        startTime = System.currentTimeMillis();
        if (isB2C) {
            message.append("|b2c");
            stock = checkStockSmallStoragesRela(isReliable, sku, oneToAllCode, regionScodeList,
                requireQty, channelCode, stockChannelCode, lockFlag, cOrderSn, selfLockFlag);
        } else {
            /**
             * 设置为不使用多层级为强制使用区县库位 如果使用多层级，依赖物料是否使用多层级的设置
             */
            message.append("|b2b2c").append(",Multi-").append(isNeedMultipleSecCode);
            if (!isNeedMultipleSecCode) {
                checkMultiCode = false;
            }
            stock = checkStockBigStoragesRela(checkMultiCode, isReliable, sku, oneToAllCode,
                requireQty, channelCode, stockChannelCode, regionScodeList, lockFlag, cOrderSn,
                selfLockFlag);
        }

        if (!StringUtil.isEmpty(oneToAllCode)) {
            message.append(",fix").append(oneToAllCode);
        }

        message.append(",").append(stock.getSecCode()).append(",").append(stock.getAvaibleQty())
            .append("):").append(System.currentTimeMillis() - startTime).append("ms");

        message.append("||total:").append(System.currentTimeMillis() - timestamp).append("ms");

        log.info(LOG_MARK + message.toString());

        //2017-04-07 多层级下单锁逻辑修改------start
        //20161212修改
        //        if (lockFlag) {
        //            Integer qty = 0;
        //            if (selfLockFlag && !StringUtil.isEmpty(cOrderSn)) {
        //                qty = eStoreStockModel.orderLockByRefNo(sku, stock.getStoreCode(),
        //                    stock.getSecCode(), SgConstants.STOCK_LOCK_TYPE.WA, cOrderSn);
        //            } else {
        //                qty = eStoreStockModel.orderLockByRefNo(sku, stock.getStoreCode(),
        //                    stock.getSecCode(), SgConstants.STOCK_LOCK_TYPE.WA, null);
        //            }
        //            Integer avaibleQty = stock.getAvaibleQty() - qty;
        //            //            avaibleQty = avaibleQty.intValue() < 0 ? 0 : avaibleQty;
        //            stock.setAvaibleQty(avaibleQty);
        //        }
        //        if (selfLockFlag && !StringUtil.isEmpty(cOrderSn)) {
        //            InvStockOrderLockEntity selfLock = invStockOrderLockDao
        //                .findInvStockOrderLockByRefNo(cOrderSn);
        //            if (selfLock != null && selfLock.getLockQty() != null
        //                && selfLock.getLockTime().compareTo(new Date()) > 0) {
        //                Integer avaibleQty = stock.getAvaibleQty() + selfLock.getLockQty();
        //                stock.setAvaibleQty(avaibleQty);
        //            }
        //        }
        //2017-04-07 多层级下单锁逻辑修改------end
        Integer avaibleQty = stock.getAvaibleQty();
        avaibleQty = avaibleQty.intValue() < 0 ? 0 : avaibleQty;
        stock.setAvaibleQty(avaibleQty);

        return stock;
    }

    /**
     * 
     * @param isReliable 是否可靠库存
     * @param sku	物料编码
     * @param oneToAllCode	 指定库位编码
     * @param regionCodes regionCodes
     * @param requireQty 获取数量
     * @param channelCode 渠道编码（顺逛为RS）
     * @param stockChannelCode  可用库存渠道
     * @return
     */
    private Stock checkStockSmallStoragesRela(boolean isReliable, String sku, String oneToAllCode,
                                              List<String> regionCodes, int requireQty,
                                              String channelCode, String stockChannelCode,
                                              boolean lockFlag, String cOrderSn,
                                              boolean selfLockFlag) {

        /**
         * 优先1仓发全国
         */
        String defaultWh;
        if (!StringUtil.isEmpty(oneToAllCode)) {
            // 默认仓库一仓发全国
            defaultWh = oneToAllCode.substring(0, 2);
            // 添加基地仓
            Set<String> whSet = StockBizHelper.addWhCode(stockChannelCode, defaultWh,
                InvSection.W10, true);
            String whs = StockBizHelper.concat(whSet.toArray(new String[whSet.size()]));
            // 查询 ---渠道，共享，基地
            List<InvStock> invStockList = this.findValidStock(isReliable, sku, whs,
                stockChannelCode, channelCode, lockFlag, cOrderSn, selfLockFlag);
            // 默认设置返回仓库
            Stock currStock = new Stock();
            currStock.setSku(sku);
            currStock.setSecCode(defaultWh + InvSection.CHANNEL_CODE_WA);
            currStock.setAvaibleQty(0);

            // 查找渠道和共享库存---封装查找对象
            for (InvStock invStock : invStockList) {
                boolean isGd = this.hasGdCode(invStock.getSecCode());
                if (currStock.getAvaibleQty() < invStock.getStockQty() && !isGd) {
                    currStock.setAvaibleQty(invStock.getStockQty());
                    currStock.setUpdateTime(invStock.getUpdateTime());
                    if (currStock.getAvaibleQty() >= requireQty) {
                        return currStock;
                    }
                }
            }

            /**
             * 查找基地库存 ---找到一个基地库存的就break;
             */
            if (currStock.getAvaibleQty() < requireQty) {
                for (InvStock invStock : invStockList) {
                    boolean isGd = this.hasGdCode(invStock.getSecCode());
                    if (currStock.getAvaibleQty() < invStock.getStockQty() && isGd) {
                        currStock.setAvaibleQty(invStock.getStockQty());
                        currStock.setUpdateTime(invStock.getUpdateTime());
                        if (requireQty <= invStock.getStockQty()) {
                            currStock.setSecCode(invStock.getSecCode());
                            break;
                        }
                    }
                }
            }

            if (currStock.getAvaibleQty() >= requireQty) {
                return currStock;
            }

            // 查找海朋库存--找到海朋库存break;
            invStockList = this.findValidStock(isReliable, sku, InvSection.CHANNEL_CODE_HAIP,
                InvSection.CHANNEL_CODE_HAIP, stockChannelCode, lockFlag, cOrderSn, selfLockFlag);

            if (invStockList != null && invStockList.size() > 0) {
                for (InvStock invStock : invStockList) {
                    // 优先找到第一个被赋值的
                    if (invStock.getStockQty() > currStock.getAvaibleQty()) {
                        currStock.setAvaibleQty(invStock.getStockQty());
                        currStock.setSecCode(invStock.getSecCode());
                        currStock.setUpdateTime(invStock.getUpdateTime());
                    }
                    if (requireQty <= currStock.getAvaibleQty()) {
                        break;
                    }
                }
            }
            //2017-05-02 净水库存--------START
            //            return currStock;//因为增加净水，所以不能直接返回，注释掉，增加数量判断
            if (currStock.getAvaibleQty() >= requireQty) {
                return currStock;
            }

            int tempNum = 0;
            for (String newChannel : InvSection.NEW_CHANNEL_CODE) {
                tempNum++;
                //查找净水库存
                invStockList = this.findValidStock(isReliable, sku, newChannel, newChannel,
                    stockChannelCode, lockFlag, cOrderSn, selfLockFlag);

                if (invStockList != null && invStockList.size() > 0) {
                    for (InvStock invStock : invStockList) {
                        // 优先找到第一个被赋值的
                        if (invStock.getStockQty() > currStock.getAvaibleQty()) {
                            currStock.setAvaibleQty(invStock.getStockQty());
                            currStock.setSecCode(invStock.getSecCode());
                            currStock.setUpdateTime(invStock.getUpdateTime());
                        }
                        if (requireQty <= currStock.getAvaibleQty()) {
                            break;
                        }
                    }
                }
                if (tempNum == InvSection.NEW_CHANNEL_CODE.size()) {
                    return currStock;
                }
            }
            //2017-05-02 净水库存--------END
        }

        List<StoragesRela> smalllalist = storagesRelaService.getListByCodes(regionCodes);
        Set<String> smallWhs = new HashSet<String>();
        for (String regCode : regionCodes) {
            String wh = regCode.substring(0, 2);
            if (!smallWhs.contains(wh)) {
                smallWhs.add(wh);
            }
        }
        for (StoragesRela small : smalllalist) {
            String whCodes = small != null && !StringUtil.isEmpty(small.getMulStoreCode())
                ? small.getMulStoreCode() : null;
            if (!StringUtil.isEmpty(whCodes)) {
                String[] scodes = whCodes.split(",");
                for (String code : scodes) {
                    String wh = StringUtil.isEmpty(code) ? null : code.substring(0, 2);
                    if (wh != null && !smallWhs.contains(wh)) {
                        smallWhs.add(wh);
                    }
                }
            }
        }
        // 根据库位列表，获取对应的库存列表
        smallWhs = StockBizHelper.addWhCode(stockChannelCode, smallWhs, InvSection.W10, true);
        String whCodes = StockBizHelper.concat(smallWhs.toArray(new String[smallWhs.size()]));
        List<InvStock> stockList = this.findValidStock(isReliable, sku, whCodes, stockChannelCode,
            channelCode, lockFlag, cOrderSn, selfLockFlag);
        // 查询海朋库存
        String[] stockChannelCodes = stockChannelCode.split(",");
        List<String> stockChannelCodeList = Arrays.asList(stockChannelCodes);
        if (stockChannelCodeList.contains(InvSection.CHANNEL_CODE_HAIP)) {
            List<InvStock> tempStockList = this.findValidStock(isReliable, sku,
                InvSection.CHANNEL_CODE_HAIP, InvSection.CHANNEL_CODE_HAIP, channelCode, lockFlag,
                cOrderSn, selfLockFlag);
            stockList.addAll(tempStockList);
        }
        //2017-05-02 净水库存--------START
        //查找净水库存
        for (String newChannel : InvSection.NEW_CHANNEL_CODE) {
            if (stockChannelCodeList.contains(newChannel)) {
                List<InvStock> tempStockList = this.findValidStock(isReliable, sku, newChannel,
                    newChannel, channelCode, lockFlag, cOrderSn, selfLockFlag);
                stockList.addAll(tempStockList);
            }
        }
        //2017-05-02 净水库存--------END
        if (stockList == null || stockList.size() == 0) {
            String scode = regionCodes.get(0);
            InvSection section = stockInvSectionService.getBySecCode(scode);
            return StockBizHelper.getZeroStock(sku, section.getWhCode());
        }

        Stock tempStock = null;
        int i = 0;
        for (String code : regionCodes) {
            // 计算code对应的小家电多层级关系
            StoragesRela smallRela = null;
            for (StoragesRela rela : smalllalist) {
                if (rela.getCode().equalsIgnoreCase(code)) {
                    smallRela = rela;
                    break;
                }
            }
            Stock theSp = calculateStockForB2C2(sku, requireQty, smallRela, stockList);
            if (theSp == null) {
                continue;
            }
            // 计算可用库存是否足够,若足够，则返回
            int availableQty = theSp.getAvaibleQty();
            if (availableQty >= requireQty) {
                // return theSp;
                tempStock = theSp;
                break;
            } else {
                if (i == 0) {
                    tempStock = theSp;
                }
            }
            i++;
        }

        return tempStock;

    }

    public Stock checkStockBigStoragesRela(boolean checkMultiCode, boolean isReliable, String sku,
                                           String onetoAllCode, int requireQty, String channelCode,
                                           String stockChannelCode, List<String> regionCodes,
                                           boolean lockFlag, String cOrderSn,
                                           boolean selfLockFlag) {

        /**
         * 优先1仓发全国
         */
        String defaultWh;
        if (!StringUtil.isEmpty(onetoAllCode)) {
            defaultWh = onetoAllCode.substring(0, 2);
            List<InvStock> invStockList = this.findValidStock(isReliable, sku, defaultWh,
                stockChannelCode, channelCode, lockFlag, cOrderSn, selfLockFlag);
            Stock currStock = new Stock();
            currStock.setSku(sku);
            currStock.setSecCode(defaultWh + InvSection.CHANNEL_CODE_WA);
            currStock.setAvaibleQty(0);
            for (InvStock invStock : invStockList) {
                if (currStock.getAvaibleQty() < invStock.getStockQty()) {
                    currStock.setAvaibleQty(invStock.getStockQty());
                    currStock.setUpdateTime(invStock.getUpdateTime());
                }
                if (currStock.getAvaibleQty() >= requireQty) {
                    return currStock;
                }
            }
            /**
             * 返回一个库存最大的
             */
            return currStock;
        }
        /**
         * 不支持多层级
         */
        String addedWh;
        if (!checkMultiCode || (!InvStockChannel.CHANNEL_SHANGCHENG.equals(channelCode)
                                && !InvStockChannel.CHANNEL_RRS.equals(channelCode)
                                && !InvStockChannel.CHANNEL_DAKEHU.equals(channelCode))) {
            Set<String> whList = new HashSet<String>();
            for (String code : regionCodes) {
                whList.add(code.substring(0, 2));
            }
            whList = StockBizHelper.addWhCode(stockChannelCode, whList, InvSection.W10, false);
            addedWh = StockBizHelper.concat(whList.toArray(new String[whList.size()]));
            List<InvStock> invStockList = this.findValidStock(isReliable, sku, addedWh,
                stockChannelCode, channelCode, lockFlag, cOrderSn, selfLockFlag);
            Stock currStock = new Stock();
            currStock.setSku(sku);
            currStock.setSecCode(addedWh + InvSection.CHANNEL_CODE_WA);
            currStock.setAvaibleQty(0);
            for (InvStock invStock : invStockList) {
                if (currStock.getAvaibleQty() < invStock.getStockQty()) {
                    currStock.setAvaibleQty(invStock.getStockQty());
                    currStock.setUpdateTime(invStock.getUpdateTime());
                }
                if (currStock.getAvaibleQty() >= requireQty) {
                    return currStock;
                }
            }
            return currStock;
        }
        List<BigStoragesRela> bigRelalist = bigStoragesRelaService.getListByCodes(regionCodes);
        List<String> bigWhs = new ArrayList<String>();
        for (String regCode : regionCodes) {
            String wh = regCode.substring(0, 2);
            if (!bigWhs.contains(wh)) {
                bigWhs.add(wh);
            }
        }
        for (BigStoragesRela big : bigRelalist) {
            String wh = big != null && !StringUtil.isEmpty(big.getMasterCode())
                ? big.getMasterCode().substring(0, 2) : null;
            if (!StringUtil.isEmpty(wh) && !bigWhs.contains(wh)) {
                bigWhs.add(wh);
            }
            wh = big != null && !StringUtil.isEmpty(big.getCenterCode())
                ? big.getCenterCode().substring(0, 2) : null;

            if (!StringUtil.isEmpty(wh) && !bigWhs.contains(wh)) {
                bigWhs.add(wh);
            }

        }

        String whCodes = StockBizHelper.concat(bigWhs.toArray(new String[bigWhs.size()]));
        List<InvStock> stockList = this.findValidStock(isReliable, sku, whCodes, stockChannelCode,
            channelCode, lockFlag, cOrderSn, selfLockFlag);

        if (stockList == null || stockList.size() == 0) {
            String scode = regionCodes.get(0);
            InvSection section = stockInvSectionService.getBySecCode(scode);
            return StockBizHelper.getZeroStock(sku, section.getWhCode());
        }
        // 计算多层级库位对应的库存,
        Stock tempStock = null;
        int i = 0;
        for (String code : regionCodes) {
            // 计算code对应的大家电多层级关系
            BigStoragesRela bigRela = null;
            for (BigStoragesRela rela : bigRelalist) {
                if (rela.getCode().equalsIgnoreCase(code)) {
                    bigRela = rela;
                    break;
                }
            }
            Stock theSp = calculateStockForB2B2C2(code, sku, true, requireQty, bigRela, stockList);
            if (theSp == null) {
                continue;
            }
            // 计算可用库存是否足够,若足够，则返回
            int availableQty = theSp.getAvaibleQty();
            if (availableQty >= requireQty) {
                return theSp;
            } else {
                if (i == 0) {
                    tempStock = theSp;
                }
            }
            i++;
        }
        return tempStock;

    }

    private List<InvStock> findValidStock(boolean isReliable, String sku, String wareHouses,
                                          String stockChannelCode, String channelCode,
                                          boolean lockFlag, String cOrderSn, boolean selfLockFlag) {
        //2017-04-07 多层级下单锁逻辑修改------start
        //        return isReliable
        //            ? invStockDao.getReliableStockQtyByWhCodesAndSkus(
        //                StockBizHelper.stringWithSingleQuote(sku),
        //                StockBizHelper.stringWithSingleQuote(wareHouses),
        //                StockBizHelper.stringWithSingleQuote(stockChannelCode),
        //                StockBizHelper.stringWithSingleQuote(channelCode))
        //            : invStockDao.getStockQtyByWhCodesAndSkus(StockBizHelper.stringWithSingleQuote(sku),
        //                StockBizHelper.stringWithSingleQuote(wareHouses),
        //                StockBizHelper.stringWithSingleQuote(stockChannelCode),
        //                StockBizHelper.stringWithSingleQuote(channelCode));
        List<InvStock> invStockList = isReliable
            ? invStockService.getReliableStockQtyByWhCodesAndSkus(
                StockBizHelper.stringWithSingleQuote(sku),
                StockBizHelper.stringWithSingleQuote(wareHouses),
                StockBizHelper.stringWithSingleQuote(stockChannelCode),
                StockBizHelper.stringWithSingleQuote(channelCode))
            : invStockService.getStockQtyByWhCodesAndSkus(StockBizHelper.stringWithSingleQuote(sku),
                StockBizHelper.stringWithSingleQuote(wareHouses),
                StockBizHelper.stringWithSingleQuote(stockChannelCode),
                StockBizHelper.stringWithSingleQuote(channelCode));

        if (invStockList != null && invStockList.size() > 0) {
            for (InvStock invStock : invStockList) {
                if (lockFlag) {
                    String secCode = invStock.getSecCode();
                    if (!InvSection.gdCodes.contains(secCode)
                        && !InvSection.HAIPENGCodes.contains(secCode)
                        //2017-05-02 净水库存--------START
                        && !InvSection.JINGSHUICodes.contains(secCode)) {
                        //2017-05-02 净水库存--------END
                        secCode = secCode.substring(0, 2) + InvSection.CHANNEL_CODE_WA;
                    }
                    Integer lockQty = computeLockQty(selfLockFlag, cOrderSn, sku, secCode);
                    Integer avaibleQty = invStock.getStockQty() - lockQty;
                    avaibleQty = avaibleQty.intValue() < 0 ? 0 : avaibleQty;
                    invStock.setStockQty(avaibleQty);
                }
            }
        }

        return invStockList;
    }

    private boolean hasGdCode(String secCode) {

        for (String code : InvSection.gdCodes) {
            if (code.equalsIgnoreCase(secCode)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasHiPengCode(String secCode) {
        for (String code : InvSection.HAIPENGCodes) {
            if (code.equalsIgnoreCase(secCode)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasJingShuiCode(String secCode) {
        for (String code : InvSection.JINGSHUICodes) {
            if (code.equalsIgnoreCase(secCode)) {
                return true;
            }
        }
        return false;
    }

    private Stock calculateStockForB2C2(String sku, int requireQty, StoragesRela smallRela,
                                        List<InvStock> stockList) {
        // 如果找不到对应的多层级库位关系，则返回
        if (smallRela == null) {
            return null;
        }
        // 计算多层级对应的库存
        String[] scodes = smallRela.getMulStoreCode().split(",");// 多层级库位列表
        if (scodes.length == 0) {
            return null;
        }
        // 在没有找到requireqty的情况下，总是保存找到有最大库存数的对象
        InvStock maxStock = null;
        for (String theCode : scodes) {// 按照顺序，逐个寻找满足要求的库位库存
            List<InvStock> stocks = getInvStockInList(sku, theCode.substring(0, 2), stockList);
            if (stocks.size() > 0) {
                for (InvStock stock : stocks) {
                    Integer stockQty = stock.getStockQty();
                    if (stockQty >= requireQty) {
                        Stock retStock = new Stock();
                        retStock.setSku(sku);
                        retStock.setSecCode(stock.getWhCode() + InvSection.CHANNEL_CODE_WA);
                        retStock.setAvaibleQty(stock.getStockQty());
                        return retStock;
                    }
                    // 1.首次赋值，如果为NULL，则赋值
                    if (maxStock == null) {
                        maxStock = stock;
                    }
                    // 2.找到比首次结果大的结果，重新赋值
                    if (stock.getStockQty() > maxStock.getStockQty()) {
                        maxStock = stock;
                    }

                }
            }
        }
        boolean notFound = maxStock == null || maxStock.getStockQty() < requireQty;
        Stock currStock = new Stock();
        if (!notFound) {
            currStock.setSku(maxStock.getSku());
            currStock.setSecCode(maxStock.getWhCode() + InvSection.CHANNEL_CODE_WA);
            currStock.setAvaibleQty(maxStock.getStockQty());
        }
        // 找基地库存
        Stock stockFound = this.findGdStock(stockList, requireQty, currStock);
        // 找海朋库存
        stockFound = this.findHiPengStock(stockList, requireQty, stockFound);
        //2017-05-02 净水库存--------START
        // 找净水库存
        for (int i = 0; i < InvSection.NEW_CHANNEL_CODE.size(); i++) {
            stockFound = this.findJingShuiStock(stockList, requireQty, stockFound);
            if (stockFound != null && stockFound.getAvaibleQty() >= requireQty) {
                break;
            }
        }
        //2017-05-02 净水库存--------END
        // 还没找到库存默认分配第一个库位
        if (stockFound == null) {
            stockFound = new Stock();
            stockFound.setSku(sku);
            stockFound.setSecCode(scodes[0]);
            stockFound.setAvaibleQty(0);
        }
        return stockFound;
    }

    private Stock findHiPengStock(List<InvStock> stockList, int requireQty, Stock currentStock) {
        boolean notFound = currentStock == null || currentStock.getAvaibleQty() < requireQty;
        if (!notFound) {
            return currentStock;
        }
        // 小家电可用基地库存,找基地库存
        for (InvStock stock : stockList) {
            String secCode = stock.getSecCode();

            if (hasHiPengCode(secCode)) {
                Integer stockQty = stock.getStockQty();
                Stock tempStock;
                if (stockQty >= requireQty) {
                    tempStock = new Stock();
                    tempStock.setSku(stock.getSku());
                    tempStock.setSecCode(stock.getSecCode());
                    tempStock.setAvaibleQty(stock.getStockQty());
                    return tempStock;
                }

            }
        }
        return null;
    }

    private Stock findGdStock(List<InvStock> stockList, int requireQty, Stock currentStock) {
        boolean notFound = currentStock == null || currentStock.getAvaibleQty() < requireQty;
        if (!notFound) {
            return currentStock;
        }
        // 小家电可用基地库存,找基地库存
        for (InvStock stock : stockList) {
            String secCode = stock.getSecCode();

            if (hasGdCode(secCode)) {
                Integer stockQty = stock.getStockQty();
                Stock tempStock;
                if (stockQty >= requireQty) {
                    tempStock = new Stock();
                    tempStock.setSku(stock.getSku());
                    tempStock.setSecCode(stock.getSecCode());
                    tempStock.setAvaibleQty(stock.getStockQty());
                    return tempStock;
                }

            }
        }
        return null;
    }

    private Stock findJingShuiStock(List<InvStock> stockList, int requireQty, Stock currentStock) {
        boolean notFound = currentStock == null || currentStock.getAvaibleQty() < requireQty;
        if (!notFound) {
            return currentStock;
        }
        //找净水库存
        for (InvStock stock : stockList) {
            String secCode = stock.getSecCode();

            if (hasJingShuiCode(secCode)) {
                Integer stockQty = stock.getStockQty();
                Stock tempStock;
                if (stockQty >= requireQty) {
                    tempStock = new Stock();
                    tempStock.setSku(stock.getSku());
                    tempStock.setSecCode(stock.getSecCode());
                    tempStock.setAvaibleQty(stock.getStockQty());
                    return tempStock;
                }

            }
        }
        return null;
    }

    private List<InvStock> getInvStockInList(String sku, String whCode, List<InvStock> stockList) {
        List<InvStock> stocks = new ArrayList<InvStock>();
        if (StringUtil.isEmpty(sku) || StringUtil.isEmpty(whCode) || stockList == null
            || stockList.size() == 0) {
            return stocks;
        }
        for (InvStock invStock : stockList) {
            if (invStock.getSku().equalsIgnoreCase(sku)
                && whCode.equalsIgnoreCase(invStock.getWhCode())) {
                stocks.add(invStock);
            }
        }
        return stocks;
    }

    private Stock calculateStockForB2B2C2(String scode, String sku, Boolean isMultipleSecCode,
                                          int requireQty, BigStoragesRela bigRela,
                                          List<InvStock> stockList) {

        String whCode = scode.substring(0, 2);
        List<InvStock> stocks = getInvStockInList(sku, whCode, stockList);

        Integer stockQty = 0;
        for (InvStock stock : stocks) {
            if (stock.getStockQty() > stockQty)
                stockQty = stock.getStockQty();
        }
        InvStock stock = stocks.size() > 0 ? stocks.get(0) : null;
        if (stock == null) {
            stock = new InvStock();
            stock.setSku(sku);
            stock.setWhCode(whCode);
        }

        stock.setStockQty(stockQty);
        // 如果物流模式是B2B2C，且没有多层级，则直接返回
        if (!isMultipleSecCode) {
            Stock retStock = new Stock();
            retStock.setSku(sku);
            retStock.setSecCode(stock.getWhCode() + InvSection.CHANNEL_CODE_WA);
            retStock.setAvaibleQty(stock.getStockQty());
            return retStock;
        }
        if (stockQty >= requireQty) {
            Stock retStock = new Stock();
            retStock.setSecCode(stock.getWhCode() + InvSection.CHANNEL_CODE_WA);
            retStock.setAvaibleQty(stock.getStockQty());
            retStock.setSku(sku);
            return retStock;
        }
        // 参数不对，则返回
        if (bigRela == null) {
            Stock retStock = new Stock();
            retStock.setSku(sku);
            retStock.setSecCode(stock.getWhCode() + InvSection.CHANNEL_CODE_WA);
            retStock.setAvaibleQty(stock.getStockQty());
            return retStock;
        }
        // 计算多层级对应的库存
        List<InvStock> masterStocks = getInvStockInList(sku,
            bigRela.getMasterCode().substring(0, 2), stockList);
        for (InvStock masterStock : masterStocks) {
            if (InvSection.CHANNEL_CODE_RRS.equalsIgnoreCase(masterStock.getChannelCode()))
                continue;
            if (masterStock.getStockQty() >= requireQty) {
                Stock retStock = new Stock();
                retStock.setSku(sku);
                retStock.setTsSecCode(whCode + InvSection.CHANNEL_CODE_WA);
                retStock.setAvaibleQty(masterStock.getStockQty());
                retStock.setSecCode(bigRela.getMasterCode());
                retStock.setTsShippingTime(bigRela.getMasterShippingTime());
                return retStock;
            }
        }

        List<InvStock> centerStocks = getInvStockInList(sku,
            bigRela.getCenterCode().substring(0, 2), stockList);
        for (InvStock centerStock : centerStocks) {
            if (InvSection.CHANNEL_CODE_RRS.equalsIgnoreCase(centerStock.getChannelCode()))
                continue;
            if (centerStock.getStockQty() >= requireQty) {
                Stock retStock = new Stock();
                retStock.setSku(sku);
                retStock.setTsSecCode(whCode + InvSection.CHANNEL_CODE_WA);
                retStock.setAvaibleQty(centerStock.getStockQty());
                retStock.setSecCode(bigRela.getCenterCode());
                retStock.setTsShippingTime(bigRela.getCenterShippingTime());
                return retStock;
            }
        }
        // 在区县对应库位、主库位、中心库位都不存在满足的库存（多层级情况下），则返回最大库存数
        Stock resultStock = new Stock();
        resultStock.setSku(sku);
        // 1.计算主库位库存数
        InvStock masterStock = masterStocks.size() > 0 ? masterStocks.get(0) : null;
        InvStock centerStock = centerStocks.size() > 0 ? centerStocks.get(0) : null;
        // 2.计算中心库位库存数
        int masterCnt = masterStock != null ? masterStock.getStockQty() : 0;
        int centerCnt = centerStock != null ? centerStock.getStockQty() : 0;
        // 3.找出有最大库存的
        int finalCnt = -1;
        // 4.区县库存最多或相等，取区县库存
        if (stock.getStockQty() >= masterCnt && stock.getStockQty() >= centerCnt) {
            finalCnt = stock.getStockQty();
            resultStock.setSecCode(stock.getWhCode() + InvSection.CHANNEL_CODE_WA);
        }
        // 即使区县库位库存为0，masterCnt和centerCnt都为0， 则finalCnt=0，不执行转运判断逻辑
        // 如果finalCnt仍然为0则说明转运库位中有库存
        if (finalCnt == -1) {
            // 5.其次取主库位信息
            if (masterCnt >= centerCnt) {
                finalCnt = masterCnt;
                resultStock.setSecCode(bigRela.getMasterCode());
                // resultStock.setTsSecCode(bigRela.getMasterCode());
                resultStock.setTsShippingTime(bigRela.getMasterShippingTime());
            } else {
                // 6.取中心库位信息
                finalCnt = centerCnt;
                resultStock.setSecCode(bigRela.getCenterCode());
                // resultStock.setTsSecCode(bigRela.getCenterCode());
                resultStock.setTsShippingTime(bigRela.getCenterShippingTime());
            }
            resultStock.setTsSecCode(stock.getWhCode() + InvSection.CHANNEL_CODE_WA);
        }
        resultStock.setAvaibleQty(finalCnt);
        return resultStock;
    }

    public InvSgStock getO2OStockBySkuAndStreet(String sku, int streetId, String storeId,
                                                int requireQty, boolean lockFlag) {
        return getO2OStockBySkuAndStreet(sku, streetId, storeId, requireQty, lockFlag, "", false);
    }

    public InvSgStock getO2OStockBySkuAndStreet(String sku, int streetId, String storeId,
                                                int requireQty, boolean lockFlag, String cOrderSn,
                                                boolean selfLockFlag) {
        if (selfLockFlag && StringUtil.isEmpty(cOrderSn)) {
            throw new BusinessException("支付时网单号不能为空,sku=" + sku + ",streetId=" + streetId);
        }
        //1：根据街道ID与店铺ID获取库位
        List<String> sCodes = invSgScodeStreetsRefService.findScodeByStreetIdAndStoreId(storeId,
            streetId);
        if (sCodes == null || sCodes.isEmpty()) {
            throw new BusinessException(
                "根据街道ID与店铺ID未获取到库位信息：streetId=" + streetId + "店铺ID=" + storeId);
        }

        //2：根据库位与sku获取库存总数。
        InvSgStockEntity invSgStock = invSgStockService
            .findInvSgStockByScodesAndSkuAndRequireQty(sCodes, sku, requireQty);
        InvSgStock stock = new InvSgStock();
        stock.setStockQty(0);
        if (invSgStock == null) {
            return stock;
        }
        //20161212修改
        if (lockFlag) {
            Integer qty = 0;
            if (selfLockFlag && !StringUtil.isEmpty(cOrderSn)) {
                //3:根据sku，库位码，获取锁定数量
                qty = eStoreStockModel.orderLockByRefNo(sku, storeId, sCodes.get(0),
                    SgConstants.STOCK_LOCK_TYPE.SG, cOrderSn);
            } else {
                qty = eStoreStockModel.orderLockByRefNo(sku, storeId, sCodes.get(0),
                    SgConstants.STOCK_LOCK_TYPE.SG, null);
            }
            //4：计算总库存并放到库存对象后返回。
            Integer sumQty = invSgStock.getStockQty() - qty;
            invSgStock.setStockQty(sumQty);
        }
        //        if (selfLockFlag && !StringUtil.isEmpty(cOrderSn)) {
        //            InvStockOrderLockEntity selfLock = invStockOrderLockDao
        //                .findInvStockOrderLockByRefNo(cOrderSn);
        //            if (selfLock != null && selfLock.getLockQty() != null
        //                && selfLock.getLockTime().compareTo(new Date()) > 0) {
        //                Integer avaibleQty = invSgStock.getStockQty() + selfLock.getLockQty();
        //                invSgStock.setStockQty(avaibleQty);
        //            }
        //        }
        Integer sumQty = invSgStock.getStockQty();
        sumQty = sumQty.intValue() > 0 ? sumQty : 0;
        invSgStock.setStockQty(sumQty);

        BeanUtils.copyProperties(invSgStock, stock);

        return stock;
    }

    public InvSgStock getO2OStockBySkuAndStreet(String sku, int streetId, String storeId,
                                                int requireQty) {
        return getO2OStockBySkuAndStreet(sku, streetId, storeId, requireQty, true);
    }

    /**
     * 顺逛自有库存入库接口
     * @param sku 物料编码
     * @param itemProperty 批次： SgConstants.ITEM_PROPERTY.W10 10:正品  默认先都传正品，以后扩展用。
     * @param storeId 店铺码
     * @param scode 库位码
     * @param newStockQty 新库存总量
     * @param type 1:入库 2:出库
     * @return 是否出/入库成功
     */
    public boolean inStock(String sku, String itemProperty, String storeId, String scode,
                           Integer newStockQty, String userName) {

        if (StringUtil.isEmpty(sku)) {
            throw new BusinessException("sku不能为空");
        }
        if (StringUtil.isEmpty(itemProperty)) {
            throw new BusinessException("批次不能为空");
        }
        if (StringUtil.isEmpty(storeId)) {
            throw new BusinessException("店铺码不能为空");
        }
        if (StringUtil.isEmpty(scode)) {
            throw new BusinessException("库位码不能为空");
        }
        if (newStockQty < 0) {
            throw new BusinessException("库存数量不能为负数");
        }
        //校验SKU是否存在。
        ServiceResult<ProductBase> productResult = itemService.getPorductBaseBySku(sku);
        if (!productResult.getSuccess()) {
            throw new BusinessException(productResult.getMessage());
        }
        ProductBase product = productResult.getResult();
        if (product == null) {
            throw new BusinessException("未获取到对应sku信息");
        }
        Integer o2oType = product.getProductO2OType();
        if (!SgConstants.PRODUCT_O2O_TYPE.O2O_GUIDE.equals(o2oType)
            && !SgConstants.PRODUCT_O2O_TYPE.O2O_SHARE.equals(o2oType)) {
            throw new BusinessException("非O2O商品不可录入顺逛自有库存");
        }
        List<String> sCodes = new ArrayList<String>();
        sCodes.add(scode);
        //获取库存信息
        InvSgStockEntity sgStock = invSgStockService.findInvSgStockByScodesAndSkuAndRequireQty(sCodes,
            sku, null);
        Integer oldStockQty = 0;
        Integer o = 0;
        if (sgStock == null) { //如果没有库存，则直接添加
            //通过SKU获取商品信息。
            InvSgStockEntity invSgStock = new InvSgStockEntity();
            invSgStock.setSku(sku);
            invSgStock.setStoreCode(storeId);
            invSgStock.setStockQty(newStockQty);//如果没有原库存，则变动库存为初始库存。
            invSgStock.setScode(scode);
            invSgStock.setItemProperty(itemProperty);//先写死，入库都是正品
            o = invSgStockService.insertInvSgStock(invSgStock);
            sgStock = invSgStock;
            //记录库存变动日志。
        } else {//如果有库存
            oldStockQty = sgStock.getStockQty(); // 原库存数量
            //如果旧库存大于新库存 则抛出异常
            if (oldStockQty > newStockQty) {
                throw new BusinessException("库存数量不能小于原可用库存数量");
            } else if (oldStockQty.equals(newStockQty)) { //如果新库存等于旧库存 直接返回true 不做日志记录。
                return true;
            }
            sgStock.setStockQty(newStockQty); //只更新库存信息。
            o = invSgStockService.updateInvSgStock(sgStock);

        }
        if (o > 0) {
            //记录库存变动日志。
            InvSgStockLogEntity invSgStockLog = new InvSgStockLogEntity();
            invSgStockLog.setMARK(SgConstants.STOCK_CHANGE_TYPE_LOG.IN);//入库
            invSgStockLog.setBillType(SgConstants.STOCK_BILL_TYPE.STORE_IN);
            invSgStockLog.setScode(scode);//库位码
            invSgStockLog.setStoreCode(storeId);//店铺码
            invSgStockLog.setNewStockQty(newStockQty);//新库存
            invSgStockLog.setOldStockQty(oldStockQty);//旧库存
            invSgStockLog.setChangeQty(newStockQty - oldStockQty);//变动库存
            invSgStockLog.setModifyUser(userName);//操作人
            addInvSgStockLog(SgConstants.STOCK_CHANGE_TYPE_LOG.IN,
                SgConstants.STOCK_BILL_TYPE.STORE_IN, storeId, scode, oldStockQty, newStockQty,
                newStockQty - oldStockQty, sgStock.getFrozenQty(), sgStock.getFrozenQty(), userName,
                null, sku);
        }
        return true;
    }

    /**
     * 
     * @param MARK H出 S入
     * @param billType 交易类型
     * @param storeId 店铺码
     * @param scode 库位码
     * @param oldStockQty 旧库存
     * @param newStockQty 新库存
     * @param changeQty 变动库存
     * @param oldFrozenQty 旧占用数量
     * @param newFrozenQty 新占用数量
     * @param userName 更新人
     * @param refNo
     */
    public void addInvSgStockLog(String MARK, String billType, String storeId, String scode,
                                 Integer oldStockQty, Integer newStockQty, Integer changeQty,
                                 Integer oldFrozenQty, Integer newFrozenQty, String userName,
                                 String refNo, String sku) {
        InvSgStockLogEntity invSgStockLog = new InvSgStockLogEntity();
        invSgStockLog.setMARK(MARK);//H出 S入
        invSgStockLog.setBillType(billType);//交易类型
        invSgStockLog.setScode(scode);//库位码
        invSgStockLog.setStoreCode(storeId);//店铺码
        invSgStockLog.setNewStockQty(newStockQty == null ? 0 : newStockQty);//新库存
        invSgStockLog.setOldStockQty(oldStockQty == null ? 0 : oldStockQty);//旧库存
        invSgStockLog.setOldFrozenQty(oldFrozenQty == null ? 0 : oldFrozenQty);//新占用数量
        invSgStockLog.setNewFrozenQty(newFrozenQty == null ? 0 : newFrozenQty);//旧占用数量
        invSgStockLog.setChangeQty(changeQty);//变动库存
        invSgStockLog.setModifyUser(userName);//操作人
        invSgStockLog.setRefNo(refNo);//关联单号
        invSgStockLog.setSku(sku);
        invSgStockLogService.insertInvSgStockLog(invSgStockLog);
    }

    /**
     * 根据sku，scode，storeCode查询数据
     * @param sku
     * @param scode
     * @param storeCode
     * @return
     */
    public InvSgStockEntity findInvSgStockBySkuRefNoStoreCode(String sku, String scode,
                                                              String storeCode) {
        InvSgStockEntity invSgStockEntity = invSgStockService.findInvSgStockBySkuRefNoStoreCode(sku,
            scode, storeCode);
        return invSgStockEntity;
    }

    /**
     * 更新数据
     * @param invSgStockLock
     * @return
     */
    public Integer updateInvSgStockQty(InvSgStockLock invSgStockLock) {
        Integer result = invSgStockService.updateInvSgStockQty(invSgStockLock);
        return result;
    }

    /**
     * 可用数量加数量，占有数量见数量
     * @param invSgStockLock
     * @return
     */
    public Integer updateReleaseForReturn(InvSgStockLock invSgStockLock) {
        Integer result = invSgStockService.updateReleaseForReturn(invSgStockLock);
        return result;
    }

    /**
     * 更新占有库存数量
     * 
     * @param sku 物料编码
     * @param storeCode 店表编码
     * @param releaseQty 占用数量
     * @param refNo 单据号(网单号)
     * @param scode 库位号
     * @return
     */
    public Integer updatefrozenQty(String sku, String storeCode, Integer releaseQty, String refNo,
                                   String scode) {
        Integer result = invSgStockService.updatefrozenQty(sku, storeCode, releaseQty, refNo, scode);
        return result;
    }

    /**
     * 通过顺逛自有店铺ID获取该店铺下所有sku与库存状态关系
     * @param storeId
     * @return
     */
    public Map<String, Boolean> selfO2OStockByStoreId(Integer storeId) {
        List<InvSgStockEntity> sgStockAll = invSgStockService.findInvSgStockByStoreId(storeId);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        if (sgStockAll != null && !sgStockAll.isEmpty()) {
            for (InvSgStockEntity entity : sgStockAll) {
                map.put(entity.getSku(), entity.getStockQty() > 0);
            }
        }
        return map;
    }

    /**
     * 通过88码获取EC店铺下所有sku与库存状态关系
     * @param storeCode
     * @return
     */
    public Map<String, Boolean> o2OStockByStoreId(String storeCode) {
        List<InvStore> sgStockAll = invSgStockService.findInvStockByStoreCode(storeCode,
            InvSection.W10);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        if (sgStockAll != null && !sgStockAll.isEmpty()) {
            for (InvStore entity : sgStockAll) {
                //				(inv.stock_qty-(IFNULL(isol.lock_qty,0)) >0)
                if (entity.getStockQty() - entity.getLockQty() > 0)
                    map.put(entity.getSku(), true);
            }
        }
        return map;
    }

    private Integer computeLockQty(boolean selfLockFlag, String cOrderSn, String sku,
                                   String secCode) {
        Integer qty = 0;
        if (selfLockFlag && !StringUtil.isEmpty(cOrderSn)) {
            qty = eStoreStockModel.orderLockByRefNo(sku, null, secCode,
                SgConstants.STOCK_LOCK_TYPE.WA, cOrderSn);
        } else {
            qty = eStoreStockModel.orderLockByRefNo(sku, null, secCode,
                SgConstants.STOCK_LOCK_TYPE.WA, null);
        }
        return qty;
    }
}
