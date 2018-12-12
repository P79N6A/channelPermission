package com.haier.stock.services.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.*;
import com.haier.shop.model.Stock;
import com.haier.shop.service.StockChangeQueueService;
import com.haier.stock.model.*;
import com.haier.stock.model.BaseStock;
import com.haier.stock.service.*;
import com.haier.stock.util.Ustring;

/**
 * 
 *  该类负责Stock Model中可能会公用的逻辑单元             
 * @Filename: StockBizHelper.java
 * @Version: 1.0
 *
 */
public class StockBizHelper {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
        .getLogger(StockBizHelper.class);

    public static final String SPLITER_COMMA = ",";

    /**
     * 渠道配置了销售库存渠道的库存，则销售
     * @param stockChannelCode
     * @param mainWhCode
     * @param itemProperty
     * @param isB2C
     * @return
     */
    public static Set<String> addWhCode(String stockChannelCode, String mainWhCode,
                                        String itemProperty, boolean isB2C) {
        Set<String> whCodes = new HashSet<String>();
        String[] codes = mainWhCode.split(",");
        for (String code : codes) {
            whCodes.add(code);
        }
        String[] stockChannels = stockChannelCode.split(",");
        List<String> sCha = Arrays.asList(stockChannels);
        boolean useGd = sCha.contains(InvSection.CHANNEL_CODE_GD);
        if (!useGd || !InvSection.W10.equals(itemProperty) || !isB2C) {
            return whCodes;
        }
        //基地仓库编码就是GD
        if (!whCodes.contains(InvSection.CHANNEL_CODE_GD)) {
            whCodes.add(InvSection.CHANNEL_CODE_GD);
        }
        return whCodes;
    }

    public static Set<String> addWhCode(String stockChannelCode, Set<String> mainWhCode,
                                        String itemProperty, boolean isB2C) {
        String[] stockChannelCodes = stockChannelCode.split(",");
        List<String> stockChannelCodeList = Arrays.asList(stockChannelCodes);
        boolean useGd = stockChannelCodeList.contains(InvSection.CHANNEL_CODE_GD);
        if (!useGd || !isB2C || !InvSection.W10.equals(itemProperty)) {
            return mainWhCode;
        }
        if (mainWhCode == null) {
            mainWhCode = new HashSet<String>();
        }
        mainWhCode.add(InvSection.CHANNEL_CODE_GD);
        return mainWhCode;
    }

    /**
     * 冻结库存时 根据销售渠道配置是否用基地库存为准
     * @param channel
     * @param mainWhCode 逗号分隔的主库位编码
     * @param invWareHouseExtDao
     * @return
     */
    public static String findWhCodes(String channel, String mainWhCode) {

        //基地仓库编码就是GD
        if (InvSection.CHANNEL_CODE_GD.equals(channel)) {
            return InvSection.CHANNEL_CODE_GD;
        }
        return mainWhCode;
    }

    /**
     * 转换SKU和secCode的对应关系
     * @param skus
     * @param secCodes
     * @return
     */
    public static List<Stock> convertToStock(List<String> skus, List<String> secCodes) {
        List<Stock> stockList = new ArrayList<Stock>();
        if (skus == null || secCodes == null || skus.size() != secCodes.size()) {
            return stockList;
        }
        Stock stock;
        int i = 0;
        for (String sku : skus) {
            stock = new Stock();
            stock.setSku(sku);
            stock.setSecCode(secCodes.get(i));
            i++;
        }
        return stockList;

    }

    public static List<Stock> convertInvStockToStock(List<InvStock> invStockList, boolean useGd,
                                                     boolean useHiPeng) {
        return convertInvStockToStock(invStockList, useGd, useHiPeng, false, true);
    }

    public static List<Stock> convertInvStockToStock(List<InvStock> invStockList, boolean useGd,
                                                     boolean useHiPeng, boolean notSearchAllflag) {
        return convertInvStockToStock(invStockList, useGd, useHiPeng, false, notSearchAllflag);
    }

    public static List<Stock> convertInvStockToStock(List<InvStock> invStockList, boolean useGd,
                                                     boolean useHiPeng, boolean useNewChannel,
                                                     boolean notSearchAllflag) {
        if (invStockList == null) {
            return null;
        }
        List<Stock> stockQtyList = new ArrayList<Stock>();
        Map<String, Integer> stockQtyMap = new HashMap<String, Integer>();
        Stock stock;
        String pKey;
        for (InvStock invStock : invStockList) {
            pKey = invStock.getWhCode() + invStock.getSku();
            boolean isGd = InvSection.CHANNEL_CODE_GD.equals(invStock.getChannelCode());
            boolean isHiPeng = InvSection.CHANNEL_CODE_HAIP.equals(invStock.getChannelCode());
            //2017-05-02 净水库存--------START
            boolean isNewChannel = InvSection.NEW_CHANNEL_CODE.contains(invStock.getChannelCode());
            //            if (isGd || isHiPeng) {
            if (isGd || isHiPeng || isNewChannel) {
                //2017-05-02 净水库存--------END
                pKey = invStock.getSecCode() + invStock.getSku();
            }
            //商城走一下逻辑
            //2017-05-02 净水库存--------START
            //            if (useGd || useHiPeng) {
            if (useGd || useHiPeng || useNewChannel) {
                //2017-05-02 净水库存--------END
                if (!stockQtyMap.containsKey(pKey)
                    && ((notSearchAllflag && Integer.parseInt(Ustring.getString0(invStock.getStockQty())) > 0) || !notSearchAllflag)) {
                    pKey = InvSection.CHANNEL_CODE_GD.equals(invStock.getWhCode())
                           || InvSection.CHANNEL_CODE_HAIP.equals(invStock.getWhCode())
                           //2017-05-02 净水库存--------START
                           || InvSection.NEW_CHANNEL_CODE.contains(invStock.getWhCode())
                               //2017-05-02 净水库存--------END
                               ? invStock.getSecCode() + invStock.getSku()
                               : invStock.getWhCode() + invStock.getSku();
                    stock = new Stock();
                    stock.setAvaibleQty(invStock.getStockQty());
                    stock.setSecCode(InvSection.CHANNEL_CODE_GD.equals(invStock.getWhCode())
                                     || InvSection.CHANNEL_CODE_HAIP.equals(invStock.getWhCode())
                                     //2017-05-02 净水库存--------START
                                     || InvSection.NEW_CHANNEL_CODE.contains(invStock.getWhCode())
                                         //2017-05-02 净水库存--------END
                                         ? invStock.getSecCode()
                                         : invStock.getWhCode() + InvSection.CHANNEL_CODE_WA);
                    stock.setUpdateTime(invStock.getUpdateTime());
                    stock.setSku(invStock.getSku());
                    stockQtyList.add(stock);
                    stockQtyMap.put(pKey, invStock.getStockQty());
                } else {
                    continue;
                }
            }
            //主要区分WA和日日顺
            else if (!stockQtyMap.containsKey(pKey)) {
                stock = new Stock();
                stock.setAvaibleQty(invStock.getStockQty());
                stock.setSecCode(invStock.getWhCode() + InvSection.CHANNEL_CODE_WA);
                stock.setUpdateTime(invStock.getUpdateTime());
                stock.setSku(invStock.getSku());
                stockQtyList.add(stock);
                stockQtyMap.put(invStock.getWhCode() + invStock.getSku(), invStock.getStockQty());
            } else {
                continue;
            }
        }
        return stockQtyList;
    }

    /**
     * @param invSectionDao
     * @param secCodes
     * @param useGd
     * @param useHaip
     * @param channel
     * @return
     */
    public static List<String> convertSecCodesToWh(StockInvSectionService stockInvSectionService,
                                                   List<String> secCodes, boolean useGd,
                                                   boolean useHaip, String channel) {
        List<String> whList = new ArrayList<String>();
        InvSection section;
        for (String secCode : secCodes) {
            section = stockInvSectionService.getBySecCode(secCode);
            whList.add(section.getWhCode());
        }
        if (useGd) {
            whList.add(InvSection.CHANNEL_CODE_GD);
        }

        return whList;
    }

    public static String stringWithSingleQuote(String str) {
        return stringWithSingleQuote(str, SPLITER_COMMA);
    }

    public static String stringWithSingleQuote(List<String> list) {
        return stringWithSingleQuote(concat(list.toArray(new String[list.size()])));
    }

    public static String stringWithSingleQuote(String str, String spliter) {
        if (StringUtil.isEmpty(str)) {
            return "";
        }

        String[] arr = str.split(spliter);
        StringBuffer buffer = new StringBuffer();
        for (String item : arr) {
            if (StringUtil.isEmpty(buffer.toString())) {
                buffer.append("'" + item + "'");
            } else {
                buffer.append(spliter);
                buffer.append("'" + item + "'");
            }
        }
        return buffer.toString();
    }

    public static String concat(String[] arr) {
        return concat(arr, SPLITER_COMMA);
    }

    public static String concat(String[] arr, String spliter) {
        if (arr == null) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        for (String item : arr) {
            if (StringUtil.isEmpty(buffer.toString())) {
                buffer.append(item);
            } else {
                buffer.append(spliter);
                buffer.append(item);
            }
        }
        return buffer.toString();
    }

    public static boolean isChannelRRS(String channel) {
        return InvSection.CHANNEL_CODE_RRS.equals(channel);
    }

    public static List<String> getStockChannelCodeByChannelCode(StockInvStockChannelService stockInvStockChannelService,
                                                                String channelCode) {
        InvStockChannel invStockChannel = stockInvStockChannelService.getByCode(channelCode);
        if (invStockChannel == null) {
            throw new BusinessException("不可识别的销售渠道：" + channelCode);
        }
        String stockChannel = invStockChannel.getStockChannelCodes();
        List<String> channels = StringUtil.isEmpty(stockChannel) ? null
            : Arrays.asList(stockChannel.split(StockBizHelper.SPLITER_COMMA));
        return channels;
    }

    public static Map<String, String> getAllProductssCodes(StockCenterItemService itemService) {
        ServiceResult<List<ProductBase>> productsService = itemService.getAllProductsBysCode(null);
        List<ProductBase> products = productsService.getResult();
        Map<String, String> skuSecCodeMap = new HashMap<String, String>();
        for (ProductBase product : products) {
            if (StringUtil.isEmpty(product.getScode())) {
                continue;
            }
            if (!skuSecCodeMap.containsKey(product.getSku())) {
                skuSecCodeMap.put(product.getSku(), product.getScode());
            }
        }
        return skuSecCodeMap;
    }

    public static Stock getZeroStock(String sku, String wh) {
        if (StringUtil.isEmpty(sku) || StringUtil.isEmpty(wh)) {
            return null;
        }
        Stock stock = new Stock();
        stock.setAvaibleQty(0);
        stock.setSecCode(wh + InvSection.CHANNEL_CODE_WA);
        stock.setSku(sku);
        return stock;
    }

    public static InvSection newSection(String secCode, String channelCode, String itemProperty) {
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
        return section;
    }

    public static InvBaseStockLog newInvBaseStockLog(InvBaseStock baseStock, Integer newStockQty,
                                                     Integer newFrozenQty, String refNo,
                                                     String mark, InventoryBusinessTypes bizType,
                                                     Date dateTime) {
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
        return baseStockLog;
    }

    /*************************************************记录库存变化通知*******************************/
    public static List<BaseStock> regroupStockQtyBySku(BaseStockService baseStockService,
                                                       StockInvMachineSetService stockInvMachineSetService,
                                                       BaseStock baseStock
    /*String sku, String code, Integer stockQty*/) {
        List<BaseStock> stocks = new ArrayList<BaseStock>();
        List<InvMachineSet> machineSets = stockInvMachineSetService.getBySubSku(baseStock.getSku());
        if (machineSets.size() <= 0) {
            BaseStock bomStock = new BaseStock();
            bomStock.setCode(baseStock.getCode());
            bomStock.setSku(baseStock.getSku());
            bomStock.setStockQty(baseStock.getStockQty());
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
                    && machineSet.getSubSku().equals(baseStock.getSku())) {
                    BaseStock subStock = new BaseStock();
                    subStock.setCode(baseStock.getCode());
                    subStock.setSku(machineSet.getSubSku());
                    subStock.setStockQty(baseStock.getStockQty());
                    stocks.add(subStock);
                }
            }
            for (Entry<String, List<InvMachineSet>> entry : keyMap.entrySet()) {
                BaseStock bomStock = new BaseStock();
                bomStock.setCode(baseStock.getCode());
                bomStock.setSku(entry.getKey());
                bomStock.setItemProperty(baseStock.getItemProperty());
                bomStock.setStockType(baseStock.getStockType());
                calculateBomStockByBase(baseStockService, bomStock, entry.getValue());
                stocks.add(bomStock);
            }
        }
        return stocks;
    }

    /**
     * 1.更新InvStock
     * 2.添加到货通知
     * @param stock
     * @param section
     * @param synStockChanges：是否同步到货通知
     */
    public static void linkageUpdateStockForSales(StockCenterItemService itemService,
                                                  StockInvSectionService stockInvSectionService,
                                                  InvStockService invStockService,
                                                  StockInvStockChannelService stockInvStockChannelService,
                                                  StockChangeQueueService stockChangeQueueService,
                                                  InvStockChangeService invStockChangeService,
                                                  InvStock stock, InvSection section,
                                                  boolean synStockChanges) {

        if (synStockChanges) {
            String defaultChannel = InvStockChannel.CHANNEL_SHANGCHENG;
            List<String> channels = StockBizHelper
                .getStockChannelCodeByChannelCode(stockInvStockChannelService, defaultChannel);
            //只有商城触发到货通知，通知的库位范围包括WA,SC,RRS
            saveShopStockChanggingQueue(invStockService, stockChangeQueueService, stock, section, channels,
                defaultChannel);
            //如果渠道不包含在商城使用运行的渠道之内，则不记录库存变化（主要记录库存从无到有）
            if (channels != null && channels.contains(section.getChannelCode())) {
                saveInvStockChangging(invStockService, invStockChangeService, stock, section, channels,
                    defaultChannel);
            }
        }
        updateStockForSale(itemService, stockInvSectionService, invStockService, stock.getSku(),
            stock.getSecCode(), stock.getStockQty());
    }

    public static void updateStockForSale(StockCenterItemService itemService, StockInvSectionService stockInvSectionService,
                                          InvStockService invStockService, String sku, String secCode,
                                          Integer stockQty) {
        if (stockQty < 0) {
            stockQty = 0;
        }
        if (stockQty < 0) {
            stockQty = 0;
        }
        InvStock stock = invStockService.getBySecCodeAndSku(secCode, sku);
        if (stock == null) {
            stock = new InvStock();
            stock.setSecCode(secCode);
            stock.setSku(sku);
            stock.setStockQty(stockQty);
            StockBizHelper.assignmentValue2InvStock(itemService, stockInvSectionService, stock);
            Integer effect = invStockService.insert(stock);
            log.info("插入invstock成功，secCode:" + secCode + ", sku:" + sku + ", stockQty:" + stockQty
                     + ", effect:" + effect);
        } else {
            stock.setStockQty(stockQty);
            stock.setFrozenQty(0);
            Integer effect = invStockService.updateQty(stock);
            log.info("更新invstock成功，secCode:" + secCode + ", sku:" + sku + ", stockQty:" + stockQty
                     + ", effect:" + effect);
        }
    }

    public static void saveShopStockChanggingQueue(InvStockService invStockService,
                                                   StockChangeQueueService stockChangeQueueService,
                                                   InvStock invStock, InvSection section,
                                                   List<String> channels, String channelCode) {
        boolean isGoodsArr = false;
        boolean useGd = channels == null ? false : channels.contains(InvSection.CHANNEL_CODE_GD);
        boolean useHiPeng = channels == null ? false
            : channels.contains(InvSection.CHANNEL_CODE_HAIP);
        //2017-05-02 净水库存--------START
        boolean useNewChannel = false;
        if (channels != null) {
            for (String channel : InvSection.NEW_CHANNEL_CODE) {
                if (channels.contains(channel)) {
                    useNewChannel = true;
                    break;
                }
            }
        }
        //2017-05-02 净水库存--------END
        List<String> defaultWhs = new ArrayList<String>();
        defaultWhs.add(section.getWhCode());
        /*if (useGd) {
            //defaultWhs.add(InvSection.CHANNEL_CODE_GD);
            return;
        }
        if (useHiPeng) {
            //defaultWhs.add(InvSection.CHANNEL_CODE_HAIPENG);
            return;
        
        }*/
        if (channels != null && channels.contains(section.getChannelCode())) {
            List<InvStock> invStockList = invStockService.getStockQtyByWhCodesAndSkus(
                StockBizHelper.stringWithSingleQuote(invStock.getSku()),
                StockBizHelper.stringWithSingleQuote(defaultWhs),
                StockBizHelper.stringWithSingleQuote(channels),
                StockBizHelper.stringWithSingleQuote(channelCode));
            if (invStockList != null && invStockList.size() > 0) {
                //2017-05-02 净水库存--------START
                //                List<Stock> tempStockList = StockBizHelper.convertInvStockToStock(invStockList,
                //                    useGd, useHiPeng);
                List<Stock> tempStockList = StockBizHelper.convertInvStockToStock(invStockList,
                    useGd, useHiPeng, useNewChannel, true);
                //2017-05-02 净水库存--------END
                if (tempStockList != null && tempStockList.size() > 0) {
                    Stock tempStock = tempStockList.get(0);
                    if (tempStock.getAvaibleQty() <= 0) {
                        isGoodsArr = true;
                    }
                } else {
                    isGoodsArr = true;
                }
            } else {
                isGoodsArr = true;
            }
        }
        StockChangeQueue changeQueue = new StockChangeQueue();
        changeQueue.setScode(invStock.getSecCode());
        changeQueue.setSku(invStock.getSku());
        if (isGoodsArr) {
            changeQueue.setIsArrivalNotice(1);
        }
        stockChangeQueueService.insert(changeQueue);
    }

    public static void saveInvStockChangging(InvStockService invStockService,
                                             InvStockChangeService invStockChangeService, InvStock invStock,
                                             InvSection section, List<String> channels,
                                             String channelCode) {
        List<InvStock> invStockList;
        InvStockChange stockChange;
        boolean useGd = channels == null ? false : channels.contains(InvSection.CHANNEL_CODE_GD);
        boolean useHiPeng = channels == null ? false
            : channels.contains(InvSection.CHANNEL_CODE_HAIP);
        //2017-05-02 净水库存--------START
        boolean useNewChannel = false;
        if (channels != null) {
            for (String channel : InvSection.NEW_CHANNEL_CODE) {
                if (channels.contains(channel)) {
                    useNewChannel = true;
                    break;
                }
            }
        }
        //2017-05-02 净水库存--------END
        List<String> defaultWhs = new ArrayList<String>();
        defaultWhs.add(section.getWhCode());
        /*if (useGd) {
            //defaultWhs.add(InvSection.CHANNEL_CODE_GD);
            return;
        }
        if (useHiPeng) {
            //defaultWhs.add(InvSection.CHANNEL_CODE_HAIPENG);
            return;
        }*/
        invStockList = invStockService.getStockQtyByWhCodesAndSkus(
            StockBizHelper.stringWithSingleQuote(invStock.getSku()),
            StockBizHelper.stringWithSingleQuote(defaultWhs),
            StockBizHelper.stringWithSingleQuote(channels),
            StockBizHelper.stringWithSingleQuote(channelCode));
        Stock tempStock;
        boolean isGoodsArr = false;
        if (invStockList != null && invStockList.size() > 0) {
            //2017-05-02 净水库存--------START
            //            List<Stock> tempStockList = StockBizHelper.convertInvStockToStock(invStockList, useGd,
            //                useHiPeng);
            List<Stock> tempStockList = StockBizHelper.convertInvStockToStock(invStockList, useGd,
                useHiPeng, useNewChannel, true);
            //2017-05-02 净水库存--------END
            if (tempStockList != null && tempStockList.size() > 0) {
                tempStock = tempStockList.get(0);
                if (tempStock.getAvaibleQty() <= 0) {
                    isGoodsArr = true;
                }
            } else {
                isGoodsArr = true;
            }
        } else {
            isGoodsArr = true;
        }
        if (isGoodsArr) {
            stockChange = new InvStockChange();
            stockChange.setSecCode(section.getWhCode() + InvSection.CHANNEL_CODE_WA);
            stockChange.setSku(invStock.getSku());
            stockChange.setUpdateTime(new Date());
            invStockChangeService.insert(stockChange);
        }
    }

    /*************************************记录库存变化通知End*******************************/
    public static void calculateBomStockByBase(BaseStockService baseStockService, BaseStock baseStock,
                                               List<InvMachineSet> bomInfos) {
        int minStock = -1;
        for (InvMachineSet bominfo : bomInfos) {
            String subSku = bominfo.getSubSku();
            BaseStock subStock;
            if (BaseStock.STOCKTYPE_WA.equals(baseStock.getStockType())) {
                subStock = baseStockService.get(subSku, baseStock.getCode());
            } else {
                subStock = baseStockService.getByItemProperty(subSku, baseStock.getCode(),
                    baseStock.getItemProperty());
            }
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
        baseStock.setStockQty(minStock);
    }

    /**
     * 通过sku查询item_base
     * @param sku
     * @return
     */
    public static ItemBase getItemBaseBySku(StockCenterItemService itemService, String sku) {
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
    public static ItemAttribute getItemAttributeByValueSetIdAndValue(StockCenterItemService itemService,
                                                                     String valueSetId,
                                                                     String value) {
        ServiceResult<ItemAttribute> result = itemService
            .getItemAttributeByValueSetIdAndValue(valueSetId, value);
        if (result == null || !result.getSuccess())
            return null;
        return result.getResult();
    }

    /**
     * 给InvBaseStock添加的字段赋值
     * @param baseStock
     */
    public static void assignmentValue2InvBaseStock(StockCenterItemService itemService,
                                                    StockInvSectionService stockInvSectionService,
                                                    InvBaseStock baseStock) {
        ItemBase itemBase = getItemBaseBySku(itemService, baseStock.getSku());
        if (itemBase != null) {
            String productName = itemBase.getMaterialDescription();//产品型号
            baseStock.setProductName(productName);
            ItemAttribute itemAttribute = getItemAttributeByValueSetIdAndValue(itemService,
                "ProductGroup", itemBase.getDepartment());
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
     * 给InvStock添加的字段赋值
     * @param stock
     */
    public static void assignmentValue2InvStock(StockCenterItemService itemService,
                                                StockInvSectionService stockInvSectionService, InvStock stock) {
        ItemBase itemBase = getItemBaseBySku(itemService, stock.getSku());
        if (itemBase != null) {
            String productName = itemBase.getMaterialDescription();//产品型号
            stock.setProductName(productName);
            ItemAttribute itemAttribute = getItemAttributeByValueSetIdAndValue(itemService,
                "ProductGroup", itemBase.getDepartment());
            if (itemAttribute != null) {
                String productTypeName = itemAttribute.getCbsCategory();//品类
                stock.setProductTypeName(productTypeName);
                String productGroupName = itemAttribute.getValueMeaning();//产品组
                stock.setProductGroupName(productGroupName);
            }
        }
        InvSection invSection = stockInvSectionService.getBySecCode(stock.getSecCode());
        if (invSection != null) {
            String secName = invSection.getSecName();//库位名称
            stock.setSecName(secName);
            String itemProperty = invSection.getItemProperty();//批次
            stock.setItemProperty(itemProperty);
        }
    }

    /**
     * 给InvBaseStockLog添加的字段赋值
     * @param baseStockLog
     */
    public static void assignmentValue2InvBaseStockLog(StockCenterItemService itemService,
                                                       StockInvSectionService stockInvSectionService,
                                                       InvBaseStockLog baseStockLog) {
        ItemBase itemBase = getItemBaseBySku(itemService, baseStockLog.getSku());
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
}
