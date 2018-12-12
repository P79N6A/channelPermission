package com.haier.stock.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.haier.common.ServiceResult;
import com.haier.shop.model.BasChangeStock;
import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.ProductBase;
import com.haier.shop.model.SgConstants;
import com.haier.shop.model.Stock;
import com.haier.shop.model.StorageCities;
import com.haier.shop.model.StoragesRela;
import com.haier.shop.service.BasChangeStockService;
import com.haier.shop.service.BigStoragesRelaService;
import com.haier.shop.service.StorageCitiesService;
import com.haier.shop.service.StorageStreetsService;
import com.haier.shop.service.StoragesRelaService;
import com.haier.stock.model.BaseStock;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStock;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvStockOrderLockEntity;
import com.haier.stock.service.InvStockChannelService;
import com.haier.stock.service.InvStockOrderLockService;
import com.haier.stock.service.InvStockService;
import com.haier.stock.service.InvStoreService;
import com.haier.stock.service.StockCenterItemService;
import com.haier.stock.service.StockInvSectionService;
import com.haier.stock.services.Helper.StockBizHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.haier.common.BusinessException;
import com.haier.common.util.StringUtil;

/**
 * HopStockModel Created by 钊 on 14-3-28.
 */
@Service
public class HopStockModel {
    private final static Logger log      = LoggerFactory.getLogger(HopStockModel.class);
    private final static String LOG_MARK = "[Stock][HopStockModel] ";
    @Autowired
    private InvStockChannelService invStockChannelDao;
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
    private StorageStreetsService storageStreetsService;
    @Autowired
    private EStoreStockModel     eStoreStockModel;
    @Autowired
    private InvStockOrderLockService invStockOrderLockService;

    private static List<String> channelInHAIPShareList = new ArrayList<String>();

    static {
        channelInHAIPShareList.add(InvStockChannel.CHANNEL_TAOBAO);
        channelInHAIPShareList.add(InvStockChannel.CHANNEL_DAKEHU);
    }

    private static Map<String, String> SHARECODES = new HashMap<String, String>();

    static {
        SHARECODES.put("CT01", "SHWA");
        SHARECODES.put("SHTB", "SHWA");
        SHARECODES.put("SHDK", "SHWA");
        SHARECODES.put("SHWA", "SHWA");
        SHARECODES.put("SH", "SHWA");
    }
    /**
     * 获取指定时间后变化的库存可用数量列表 
     * 
     * @param dateTime 开始时间
     * @param channelCode 渠道
     * @return 库存列表
     */
    public List<Stock> getStockIncrement(Date dateTime, String channelCode) {

        long timestamp = System.currentTimeMillis();

        List<Stock> stocks = new ArrayList<Stock>();
        InvStockChannel invStockChannel = invStockChannelDao.getByCode(channelCode);
        if (invStockChannel == null) {
            throw new BusinessException("不可识别的销售渠道：" + channelCode);
        }
        String stockChannel = invStockChannel.getStockChannelCodes();
        // 查询海鹏变化库存
        String[] stockChannels = stockChannel.split(",");
        List<String> waChannelList = new ArrayList<String>();
        for (String channel : stockChannels) {
            //2017-05-02 净水库存--------START
            //            if (!InvSection.CHANNEL_CODE_HAIP.equals(channel)) {
            if (!InvSection.CHANNEL_CODE_HAIP.equals(channel)
                && !InvSection.NEW_CHANNEL_CODE.contains(channel)) {
                //2017-05-02 净水库存--------END
                waChannelList.add(channel);
            }
        }
        List<String> stockChannelList = Arrays.asList(stockChannels);
        boolean useHiPeng = stockChannelList.contains(InvSection.CHANNEL_CODE_HAIP);
        Map<String, InvStock> haipMap = new HashMap<String, InvStock>();
        if (useHiPeng) {
            List<InvStock> haipstocks = invStockService.getHAIPStockQty(dateTime,
                StockBizHelper.stringWithSingleQuote(InvSection.CHANNEL_CODE_HAIP));
            if (haipstocks != null && haipstocks.size() > 0) {
                for (InvStock stock : haipstocks) {
                    haipMap.put("SHWA" + stock.getSku(), stock);
                }
            }
        }
        List<InvStock> stockList = invStockService.getChangedListBySkuWhcode(dateTime,
            StockBizHelper.stringWithSingleQuote(waChannelList),
            StockBizHelper.stringWithSingleQuote(channelCode));
        Stock stock;
        Map<String, Stock> skuSecCodeMap = new HashMap<String, Stock>();

        // WA有变化
        for (InvStock invStock : stockList) {
            //2017-05-02 净水库存--------START
            //            if (InvSection.HAIPENGCodes.contains(invStock.getSecCode())) {
            if (InvSection.HAIPENGCodes.contains(invStock.getSecCode())
                || InvSection.JINGSHUICodes.contains(invStock.getSecCode())) {
                //2017-05-02 净水库存--------END
                continue;
            }
            stock = new Stock();
            stock.setSku(invStock.getSku());
            stock.setSecCode(invStock.getSecCode());
            // 添加海朋库存（只添加到SHWA库中）
            String key = invStock.getSecCode() + invStock.getSku();
            // InvStock haip = haipMap.get(key);
            if (useHiPeng && invStock.getSecCode().startsWith("SH")) {
                List<InvStock> haipList = invStockService.getReliableStockQtyByWhCodesAndSkus(
                    StockBizHelper.stringWithSingleQuote(invStock.getSku()),
                    StockBizHelper.stringWithSingleQuote(InvSection.CHANNEL_CODE_HAIP),
                    StockBizHelper.stringWithSingleQuote(InvSection.CHANNEL_CODE_HAIP),
                    channelCode);
                int totalQty = invStock.getStockQty();
                for (InvStock tempEntity : haipList) {
                    totalQty += tempEntity.getStockQty();
                }
                stock.setAvaibleQty(totalQty);
            } else {
                stock.setAvaibleQty(invStock.getStockQty());
            }
            stock.setUpdateTime(invStock.getUpdateTime());
            stocks.add(stock);
            skuSecCodeMap.put(invStock.getSecCode() + invStock.getSku(), stock);
        }
        List<Stock> afterStockList = new ArrayList<Stock>();
        // HAIP有变化
        if (useHiPeng) {
            for (Map.Entry<String, InvStock> entryMap : haipMap.entrySet()) {
                String key = entryMap.getKey();
                Stock entity = skuSecCodeMap.get(key);
                InvStock invStock = entryMap.getValue();
                if (entity == null) {
                    List<InvStock> waList = invStockService.getReliableStockQtyByWhCodesAndSkus(
                        StockBizHelper.stringWithSingleQuote(invStock.getSku()),
                        StockBizHelper.stringWithSingleQuote("SH"),
                        StockBizHelper.stringWithSingleQuote(waChannelList), channelCode);
                    int totalQty = invStock.getStockQty();
                    for (InvStock tempEntity : waList) {
                        totalQty += tempEntity.getStockQty();
                    }
                    Stock tempEntity = new Stock();
                    tempEntity.setAvaibleQty(totalQty);
                    tempEntity.setSecCode(SHARECODES.get(invStock.getSecCode()));
                    tempEntity.setSku(invStock.getSku());
                    tempEntity.setUpdateTime(invStock.getUpdateTime());
                    skuSecCodeMap.put(key, tempEntity);
                    afterStockList.add(tempEntity);
                }

            }
        }
        // stocks中没有HAIP库的
        afterStockList.addAll(stocks);

        //统一剔除一遍下单锁，SHWA库位需要剔除SHWA和CT01两个库位下单锁
        subtractLockQty(afterStockList);

        log.info(LOG_MARK + "getStockIncrement:时间：" + dateTime + ", 渠道：" + channelCode + ",总共:"
                 + stocks.size() + "条,耗时:" + (System.currentTimeMillis() - timestamp) + " ms");
        return afterStockList;
    }

    /**
     * 获取此渠道的指定SKU的库存列表 
     * @param sku 物料
     * @param channelCode 渠道
     * @return 库存列表
     */
    public List<Stock> getStockListBySku(String sku, String channelCode) {
        return getStockListBySku(sku, channelCode, true);
    }

    /**
     * 获取此渠道的指定SKU的库存列表 
     * @param sku
     * @param channelCode
     * @param notSearchAllflag true:数量＝0不返回，false:返回全部
     * @return
     */
    public List<Stock> getStockListBySku(String sku, String channelCode, boolean notSearchAllflag) {
        InvStockChannel invStockChannel = invStockChannelDao.getByCode(channelCode);
        if (invStockChannel == null) {
            throw new BusinessException("不可识别的销售渠道：" + channelCode);
        }
        String stockChannel = invStockChannel.getStockChannelCodes();
        String[] stockChannels = stockChannel.split(",");
        List<String> stockChannelList = Arrays.asList(stockChannels);
        boolean useGd = stockChannelList.contains(InvSection.CHANNEL_CODE_GD);
        boolean useHAIP = stockChannelList.contains(InvSection.CHANNEL_CODE_HAIP);
        //2017-05-02 净水库存--------START
        boolean useNewChannel = false;
        if (stockChannelList != null) {
            for (String channel : InvSection.NEW_CHANNEL_CODE) {
                if (stockChannelList.contains(channel)) {
                    useNewChannel = true;
                    break;
                }
            }
        }
        //2017-05-02 净水库存--------END
        // stockChannel : TB,WA,HAIP
        List<InvStock> invStockList = invStockService.getReliableStockQtyByWhCodesAndSkus(
            StockBizHelper.stringWithSingleQuote(sku), "",
            StockBizHelper.stringWithSingleQuote(stockChannel),
            StockBizHelper.stringWithSingleQuote(channelCode));
        //2016-10-28 第三方接口组要求返回数量＝0的数据
        //2017-05-02 净水库存--------START
        //        List<Stock> stockList = StockBizHelper.convertInvStockToStock(invStockList, useGd, useHAIP,
        //            notSearchAllflag);
        List<Stock> stockList = StockBizHelper.convertInvStockToStock(invStockList, useGd, useHAIP,
            useNewChannel, notSearchAllflag);
        //2017-05-02 净水库存--------END
        // 如有海朋库存分享到上海共享（TB,DKH）
        List<Stock> afterStockList = this.afterQueryHanler(channelCode, useHAIP, stockList);
        //统一剔除一遍下单锁，SHWA库位需要剔除SHWA和CT01两个库位下单锁
        subtractLockQty(afterStockList);
        return afterStockList;
    }

    /**
     * 获取此渠道的指定sku和库位列表的库存列表 
     * @param sku 物料
     * @param secCodeList 库位列表
     * @param channelCode 渠道
     * @return 库存列表
     */
    public List<Stock> getStockBySkuAndSecCodes(String sku, List<String> secCodeList,
                                                String channelCode) {
        return getStockBySkuAndSecCodes(sku, secCodeList, channelCode, true);
    }

    /**
     * 获取此渠道的指定sku和库位列表的库存列表 
     * @param sku 物料
     * @param secCodeList 库位列表
     * @param channelCode 渠道
     * @param notSearchAllflag true:数量＝0不返回，false:返回全部
     * @return 库存列表
     */
    public List<Stock> getStockBySkuAndSecCodes(String sku, List<String> secCodeList,
                                                String channelCode, boolean notSearchAllflag) {
        InvStockChannel invStockChannel = invStockChannelDao.getByCode(channelCode);
        if (invStockChannel == null) {
            throw new BusinessException("不可识别的销售渠道：" + channelCode);
        }
        String stockChannel = invStockChannel.getStockChannelCodes();
        String[] stockChannels = stockChannel.split(",");
        List<String> stockChannelList = Arrays.asList(stockChannels);
        boolean useGd = stockChannelList.contains(InvSection.CHANNEL_CODE_GD);
        boolean useHAIP = stockChannelList.contains(InvSection.CHANNEL_CODE_HAIP);
        //2017-05-02 净水库存--------START
        boolean useNewChannel = false;
        if (stockChannelList != null) {
            for (String channel : InvSection.NEW_CHANNEL_CODE) {
                if (stockChannelList.contains(channel)) {
                    useNewChannel = true;
                    break;
                }
            }
        }
        //2017-05-02 净水库存--------END
        List<String> whList = StockBizHelper.convertSecCodesToWh(stockInvSectionService, secCodeList, useGd,
            useHAIP, channelCode);
        // /如果包含上海库位（SH）则添加海朋库位作为查询条件
        beforeQueryAddWhs(channelCode, useHAIP, whList);
        String whCodes = StockBizHelper.concat(whList.toArray(new String[whList.size()]));
        List<InvStock> invStockList = invStockService.getReliableStockQtyByWhCodesAndSkus(
            StockBizHelper.stringWithSingleQuote(sku),
            StockBizHelper.stringWithSingleQuote(whCodes),
            StockBizHelper.stringWithSingleQuote(stockChannel),
            StockBizHelper.stringWithSingleQuote(channelCode));
        //2017-05-02 净水库存--------START
        //        List<Stock> stockList = StockBizHelper.convertInvStockToStock(invStockList, useGd, useHAIP,
        //            notSearchAllflag);
        List<Stock> stockList = StockBizHelper.convertInvStockToStock(invStockList, useGd, useHAIP,
            useNewChannel, notSearchAllflag);
        //2017-05-02 净水库存--------END
        // 如有海朋库存分享到上海共享（TB,DKH）
        List<Stock> afterStockList = this.afterQueryHanler(channelCode, useHAIP, stockList);
        //统一剔除一遍下单锁，SHWA库位需要剔除SHWA和CT01两个库位下单锁
        subtractLockQty(afterStockList);
        return afterStockList;
    }

    /**
     * 获取此渠道的指定库位和sku列表的库存列表 
     * 
     * @param secCode 库位
     * @param skuList 物料列表
     * @param channelCode 渠道
     * @return 库存列表
     */
    public List<Stock> getStockBySecCodeAndSkus(String secCode, List<String> skuList,
                                                String channelCode) {
        InvStockChannel invStockChannel = invStockChannelDao.getByCode(channelCode);
        if (invStockChannel == null) {
            throw new BusinessException("不可识别的销售渠道：" + channelCode);
        }
        String stockChannel = invStockChannel.getStockChannelCodes();
        String skus = StockBizHelper.concat(skuList.toArray(new String[skuList.size()]));
        InvSection section = stockInvSectionService.getBySecCode(secCode);
        String[] stockChannels = stockChannel.split(",");
        List<String> stockChannelList = Arrays.asList(stockChannels);
        boolean useGd = stockChannelList.contains(InvSection.CHANNEL_CODE_GD);
        boolean useHAIP = stockChannelList.contains(InvSection.CHANNEL_CODE_HAIP);
        List<String> defaultWhs = new ArrayList<String>();
        defaultWhs.add(section.getWhCode());
        if (useGd) {
            defaultWhs.add(InvSection.CHANNEL_CODE_GD);
        }
        // /如果包含上海库位（SH）则添加海朋库位作为查询条件
        beforeQueryAddWhs(channelCode, useHAIP, defaultWhs);
        List<InvStock> invStockList = invStockService.getReliableStockQtyByWhCodesAndSkus(
            StockBizHelper.stringWithSingleQuote(skus),
            StockBizHelper.stringWithSingleQuote(defaultWhs),
            StockBizHelper.stringWithSingleQuote(stockChannel),
            StockBizHelper.stringWithSingleQuote(channelCode));
        List<Stock> stockList = StockBizHelper.convertInvStockToStock(invStockList, useGd, useHAIP);
        // 如有海朋库存分享到上海共享（TB,DKH）
        return this.afterQueryHanler(channelCode, useHAIP, stockList);
    }

    /**
     * 共享海朋库存到指定共享库位里
     * 
     * @param useHAIP 是否使用海朋库存
     * @param stockList 库存列表
     * @return 筛选后的库存列表
     */
    private List<Stock> addHAIPToWa(boolean useHAIP, List<Stock> stockList) {
        Map<String, Stock> waMap = new HashMap<String, Stock>();
        List<Stock> validList = new ArrayList<Stock>();
        for (Stock entity : stockList) {
            String code = entity.getSecCode();
            String shareCode = SHARECODES.get(code);
            if (!StringUtil.isEmpty(shareCode)) {
                String key = shareCode + entity.getSku();
                Stock stock = waMap.get(key);
                int qty = stock == null ? entity.getAvaibleQty()
                    : (stock.getAvaibleQty() + entity.getAvaibleQty());
                if (stock == null) {
                    stock = new Stock();
                    waMap.put(key, stock);
                }
                waMap.get(key).setAvaibleQty(qty);
                waMap.get(key).setSku(entity.getSku());
                waMap.get(key).setSecCode(shareCode);
            } else {
                validList.add(entity);
            }
        }
        for (Map.Entry<String, Stock> keyEntity : waMap.entrySet()) {
            validList.add(keyEntity.getValue());
        }
        return validList;

    }

    private void beforeQueryAddWhs(String channelCode, boolean useHAIP, List<String> defaultWhs) {
        // 需要按照库位共享库存的渠道
        if (channelInHAIPShareList.contains(channelCode) && useHAIP) {
            // BUG 又查询又添加， 出现多个共享后修改
            for (String wh : defaultWhs) {
                // 查询共享到的库位
                String shareCode = SHARECODES.get(wh);
                if (!StringUtil.isEmpty(shareCode)) {
                    defaultWhs.add(InvSection.CHANNEL_CODE_HAIP);
                    return;
                }
            }
        } else {
            if (useHAIP) {
                defaultWhs.add(InvSection.CHANNEL_CODE_HAIP);
            }
        }
    }

    private List<Stock> afterQueryHanler(String channelCode, boolean useHAIP,
                                         List<Stock> convertStockList) {
        List<Stock> tempList = null;
        if (channelInHAIPShareList.contains(channelCode) && useHAIP) {
            tempList = this.addHAIPToWa(true, convertStockList);
        }
        return tempList == null ? convertStockList : tempList;
    }

    /**
     * 获取渠道的库存列表，指定『sku,secCode』列表 
     * 
     * @param skuList 物料列表
     * @param secCodeList 库位列表
     * @param channelCode 渠道
     * @return 库存列表
     */
    public List<Stock> getStockList(List<String> skuList, List<String> secCodeList,
                                    String channelCode) {
        InvStockChannel invStockChannel = invStockChannelDao.getByCode(channelCode);
        if (invStockChannel == null) {
            throw new BusinessException("不可识别的销售渠道：" + channelCode);
        }
        String stockChannel = invStockChannel.getStockChannelCodes();
        String[] stockChannels = stockChannel.split(",");
        List<String> stockChannelList = Arrays.asList(stockChannels);
        boolean useGd = stockChannelList.contains(InvSection.CHANNEL_CODE_GD);
        Map<String, Integer> gdStockQtyMap = this.findBackUpStock(useGd, skuList,
            InvSection.CHANNEL_CODE_GD, stockChannel, channelCode);
        boolean useHAIP = stockChannelList.contains(InvSection.CHANNEL_CODE_HAIP);
        Map<String, Integer> hiPengStockQtyMap = this.findBackUpStock(useHAIP, skuList,
            InvSection.CHANNEL_CODE_HAIP, stockChannel, channelCode);
        int i = 0;
        List<Stock> stockList = new ArrayList<Stock>();
        InvSection section;
        List<InvStock> invStockList;
        Stock stock;
        for (String tempSku : skuList) {
            section = stockInvSectionService.getBySecCode(secCodeList.get(i));
            List<String> defaultWhs = new ArrayList<String>();
            defaultWhs.add(section.getWhCode());
            // 如果当前库位是上海库，则添加海朋库位
            beforeQueryAddWhs(channelCode, useHAIP, defaultWhs);
            invStockList = invStockService.getReliableStockQtyByWhCodesAndSkus(
                StockBizHelper.stringWithSingleQuote(tempSku),
                StockBizHelper.stringWithSingleQuote(defaultWhs),
                StockBizHelper.stringWithSingleQuote(stockChannel),
                StockBizHelper.stringWithSingleQuote(channelCode));
            List<Stock> convertStockList = StockBizHelper.convertInvStockToStock(invStockList,
                useGd, useHAIP);
            // 如有海朋库存分享到上海共享（TB,DKH）
            convertStockList = this.afterQueryHanler(channelCode, useHAIP, convertStockList);
            if (convertStockList == null || convertStockList.isEmpty()) {
                // 没有查询到可用库存时，统一返回当前库位、SKU的0库存
                stock = new Stock();
                // 取基地库存
                Integer qty = gdStockQtyMap.get(tempSku) == null ? 0 : gdStockQtyMap.get(tempSku);
                String secCode = section.getWhCode() + InvSection.CHANNEL_CODE_WA;
                // if (qty > 0) {
                // 取海朋库存
                Integer hpQty = hiPengStockQtyMap.get(tempSku) == null ? 0
                    : hiPengStockQtyMap.get(tempSku);

                if (channelInHAIPShareList.contains(channelCode) && useHAIP) {
                    String shareCode = SHARECODES.get(section.getSecCode());
                    if (!StringUtil.isEmpty(shareCode)) {
                        secCode = shareCode;
                    }
                }
                qty = qty > 0 ? qty : hpQty;
                // }

                stock.setAvaibleQty(qty);
                stock.setSecCode(secCode);
                stock.setSku(tempSku);
                stockList.add(stock);
            } else {
                stockList.addAll(convertStockList);
            }
            i++;
        }
        return stockList;
    }

    private Map<String, Integer> findBackUpStock(boolean useStock, List<String> tempSku,
                                                 String defaultWhs, String stockChannel,
                                                 String channelCode) {
        Map<String, Integer> gdSkuQtyMap = new HashMap<String, Integer>();
        if (useStock) {
            List<InvStock> gdStockList = invStockService.getReliableStockQtyByWhCodesAndSkus(
                StockBizHelper.stringWithSingleQuote(tempSku),
                StockBizHelper.stringWithSingleQuote(defaultWhs),
                StockBizHelper.stringWithSingleQuote(stockChannel),
                StockBizHelper.stringWithSingleQuote(channelCode));
            if (gdStockList == null || gdStockList.size() <= 0) {
                return gdSkuQtyMap;
            }
            for (InvStock invStock : gdStockList) {
                if (invStock.getStockQty() > 0 && !gdSkuQtyMap.containsKey(invStock.getSku())) {
                    gdSkuQtyMap.put(invStock.getSku(), invStock.getStockQty());
                }
            }
        }
        return gdSkuQtyMap;
    }

    /**
     * 获取此渠道的所有城市中有库存的sku 
     * Map<cityId, List<sku>>
     * 
     * @param channel 渠道
     * @return 各城市下有库存的物料
     */
    public Map<Integer, List<String>> getCitySkusByChannel(String channel) {
        // 确定可用库存渠道
        InvStockChannel stockChannel = invStockChannelDao.getByCode(channel);
        if (stockChannel == null)
            return null;
        Long startTime = System.currentTimeMillis();
        // 查询上家商品列表
        List<ProductBase> onSaleList;
        ServiceResult<List<ProductBase>> onSaleListResult;

        onSaleListResult = itemService.getAllOnSaleProducts();
        if (onSaleListResult.getSuccess()) {
            onSaleList = onSaleListResult.getResult();
        } else {
            throw new BusinessException(onSaleListResult.getMessage());
        }

        if (onSaleList == null || onSaleList.isEmpty()) {
            throw new BusinessException("找不到上架商品");
        }
        startTime = System.currentTimeMillis();
        // 转换上架商品sku -> ProductBase
        Map<String, ProductBase> productMap = new HashMap<String, ProductBase>();
        for (ProductBase product : onSaleList) {
            productMap.put(product.getSku(), product);
        }
        startTime = System.currentTimeMillis();
        // 可用渠道查询库存
        List<InvStock> stockList = invStockService.getStockQtyByWhCodesAndSkus(null, null,
            StockBizHelper.stringWithSingleQuote(stockChannel.getStockChannelCodes()),
            StockBizHelper.stringWithSingleQuote(channel));

        startTime = System.currentTimeMillis();
        // 去掉日日顺库存、下架商品
        Map<String, List<ProductBase>> secCodeProductsMap = new HashMap<String, List<ProductBase>>();
        List<ProductBase> productList;
        for (InvStock invStock : stockList) {
            String wh = invStock.getSecCode().substring(0, 2);
            String secCode = wh + InvSection.CHANNEL_CODE_WA;
            if (invStock.getStockQty() > 0 && productMap.containsKey(invStock.getSku())
                && !StockBizHelper.isChannelRRS(channel)) {
                if (secCodeProductsMap.containsKey(secCode)) {
                    secCodeProductsMap.get(secCode).add(productMap.get(invStock.getSku()));
                } else {
                    productList = new ArrayList<ProductBase>();
                    productList.add(productMap.get(invStock.getSku()));
                    secCodeProductsMap.put(secCode, productList);
                }
            }
        }

        // 查询所有城市下的库位列表
        List<StorageCities> citiesList = storageCitiesService.getStorageCities(null);
        // 查询小家电大家电列表
        List<BigStoragesRela> bigRelaList = bigStoragesRelaService.getList();
        List<StoragesRela> relaList = storagesRelaService.getList();
        // 城市下的可用库位
        Map<Integer, List<String>> cityBigScodesMap = new HashMap<Integer, List<String>>();
        Map<Integer, List<String>> citySmalScodesMap = new HashMap<Integer, List<String>>();
        startTime = System.currentTimeMillis();
        // 查询各个城市可用库位
        List<String> sCodeList;
        for (StorageCities city : citiesList) {
            sCodeList = new ArrayList<String>();
            // 大家电多层级
            List<String> bigList = this.getCodeListByBigStoragesRela(city.getSCodeA(), bigRelaList);
            if (bigList != null && bigList.size() > 0) {
                for (String bigCode : bigList) {
                    sCodeList.add(bigCode);
                }
                cityBigScodesMap.put(city.getCityId(), sCodeList);
            }
            // 小家电多层级
            List<String> smallList = this.getCodeListByStoragesRela(city.getSCodeA(), relaList);
            if (smallList != null && smallList.size() > 0) {
                for (String smallCode : smallList) {
                    sCodeList.add(smallCode);
                }
                citySmalScodesMap.put(city.getCityId(), sCodeList);
            }

        }
        // 实例化城市和skuList的Map
        Map<Integer, List<String>> citySkusMap = new HashMap<Integer, List<String>>();
        // 查询有指定库位的产品， 键、值：sku、secCode
        Map<String, String> skuSecCodesMap = StockBizHelper.getAllProductssCodes(itemService);
        if (skuSecCodesMap == null || skuSecCodesMap.isEmpty()) {
            skuSecCodesMap = new HashMap<String, String>();
        }
        Set<String> whList = new HashSet<String>();
        Set<String> skuWithScodList = new HashSet<String>();
        for (Map.Entry<String, String> entry : skuSecCodesMap.entrySet()) {
            whList.add(entry.getValue().substring(0, 2));
            skuWithScodList.add(entry.getKey());
        }
        // 查询库存情况
        List<InvStock> invStockWithSkuList = invStockService.getStockQtyByWhCodesAndSkus(
            StockBizHelper.stringWithSingleQuote(
                StockBizHelper.concat(skuWithScodList.toArray(new String[skuWithScodList.size()]))),
            StockBizHelper.stringWithSingleQuote(
                StockBizHelper.concat(whList.toArray(new String[whList.size()]))),
            StockBizHelper.stringWithSingleQuote(stockChannel.getStockChannelCodes()),
            StockBizHelper.stringWithSingleQuote(channel));
        if (invStockWithSkuList != null) {
            Set<String> skuList;
            // 添加有指定库位的sku
            for (InvStock invStock : invStockWithSkuList) {
                String wh = invStock.getSecCode().substring(0, 2);
                String secCode = wh + InvSection.CHANNEL_CODE_WA;
                if (invStock.getStockQty() > 0
                    && secCode.equals(skuSecCodesMap.get(invStock.getSku()))) {
                    for (Map.Entry<Integer, List<String>> entry : citySkusMap.entrySet()) {
                        List<String> skusList = entry.getValue();
                        if (skusList == null) {
                            skuList = new HashSet<String>();
                            skuList.add(invStock.getSku());
                            citySkusMap.put(entry.getKey(), new ArrayList<String>(skuList));
                        } else {
                            skusList.add(invStock.getSku());
                        }

                    }
                }
            }
        }

        startTime = System.currentTimeMillis();
        // 根据可用库位，查询城市下的可用物料

        for (Map.Entry<Integer, List<String>> entry : cityBigScodesMap.entrySet()) {
            List<String> secCodeList = entry.getValue();
            for (String secCode : secCodeList) {
                List<ProductBase> productBaseList = secCodeProductsMap.get(secCode);
                if (productBaseList == null) {
                    continue;
                }
                for (ProductBase product : productBaseList) {
                    boolean isB2C = "B2C".equalsIgnoreCase(product.getShippingMode());

                    if (isB2C) {
                        continue;
                    }

                    if (!skuSecCodesMap.containsKey(product.getSku())) {

                        List<String> skus = citySkusMap.get(entry.getKey());
                        if (skus == null) {
                            skus = new ArrayList<String>();
                        }
                        if (!skus.contains(product.getSku())) {
                            skus.add(product.getSku());
                            citySkusMap.put(entry.getKey(), skus);
                        }

                    }
                }
            }
        }

        for (Map.Entry<Integer, List<String>> entry : citySmalScodesMap.entrySet()) {
            List<String> secCodeList = entry.getValue();
            for (String secCode : secCodeList) {
                List<ProductBase> productBaseList = secCodeProductsMap.get(secCode);
                if (productBaseList == null) {
                    continue;
                }
                for (ProductBase product : productBaseList) {
                    boolean isB2C = "B2C".equalsIgnoreCase(product.getShippingMode());
                    if (!isB2C) {
                        continue;
                    }
                    if (!skuSecCodesMap.containsKey(product.getSku())) {

                        List<String> skus = citySkusMap.get(entry.getKey());
                        if (skus == null) {
                            skus = new ArrayList<String>();
                        }
                        if (!skus.contains(product.getSku())) {
                            skus.add(product.getSku());
                            citySkusMap.put(entry.getKey(), skus);
                        }

                    }
                }
            }
        }
        log.info(LOG_MARK + "根据可用库位，查询城市下的可用物料:" + (System.currentTimeMillis() - startTime));

        return citySkusMap;

    }

    private List<String> getCodeListByBigStoragesRela(String scode, List<BigStoragesRela> list) {
        if (list == null || StringUtil.isEmpty(scode) || list.size() == 0) {
            return null;
        }
        BigStoragesRela theRela = null;
        for (BigStoragesRela rela : list) {
            if (rela.getCode().equalsIgnoreCase(scode)) {
                theRela = rela;
                break;
            }
        }
        if (theRela == null) {
            return null;
        }
        List<String> codeList = new ArrayList<String>();
        codeList.add(theRela.getMasterCode());
        codeList.add(theRela.getCenterCode());
        return codeList;
    }

    private List<String> getCodeListByStoragesRela(String scode, List<StoragesRela> list) {
        if (list == null || StringUtil.isEmpty(scode) || list.size() == 0) {
            return null;
        }
        StoragesRela theRela = null;
        for (StoragesRela rela : list) {
            if (rela.getCode().equalsIgnoreCase(scode)) {
                theRela = rela;
                break;
            }
        }
        if (theRela == null) {
            return null;
        }
        List<String> codeList = new ArrayList<String>();
        String[] scodes = theRela.getMulStoreCode().split(",");// 多层级库位列表
        if ((scodes.length == 0)) {
            return null;
        }
        for (String theCode : scodes) {// 按照顺序，逐个寻找满足要求的库位库存
            if (!StringUtil.isEmpty(theCode)) {
                codeList.add(theCode);
            }
        }
        return codeList;
    }

    /**
     * 根据物料和区域获取库存 
     * 
     * @param sku 物料
     * @param regionId 区域ID
     * @param channelCode 渠道
     * @param requireQty 需求数量
     * @param isNeedMultipleSecCode 是否多层级
     * @param isReliable 是否可靠库存
     * @return 库存信息
     */
    public Stock getStockBySkuAndRegion(String sku, int regionId, String channelCode,
                                        int requireQty, boolean isNeedMultipleSecCode,
                                        boolean isReliable) {
        return getStockBySkuAndRegion(sku, regionId, channelCode, requireQty, isNeedMultipleSecCode,
            isReliable, true);
    }

    /**
     * 根据物料和区域获取库存 
     * 
     * @param sku 物料
     * @param regionId 区域ID
     * @param channelCode 渠道
     * @param requireQty 需求数量
     * @param isNeedMultipleSecCode 是否多层级
     * @param isReliable 是否可靠库存
     * @param lockFlag 下单锁标识
     * @return 库存信息
     */
    public Stock getStockBySkuAndRegion(String sku, int regionId, String channelCode,
                                        int requireQty, boolean isNeedMultipleSecCode,
                                        boolean isReliable, boolean lockFlag) {
        return getStockBySkuAndRegion(sku, regionId, channelCode, requireQty, isNeedMultipleSecCode,
            isReliable, lockFlag, "", false);
    }

    /**
     * 根据物料和区域获取库存 
     * 
     * @param sku 物料
     * @param regionId 区域ID
     * @param channelCode 渠道
     * @param requireQty 需求数量
     * @param isNeedMultipleSecCode 是否多层级
     * @param isReliable 是否可靠库存
     * @param lockFlag 下单锁标识 true:扣减下单锁 false:不扣减下单锁
     * @param cOrderSn 网单号，付款时判断是否本身有下单锁
     * @param selfLockFlag 扣减本身下单锁标识 true:不扣减本身锁定数量,操作为加 false:扣减本身锁定数量,不操作 
     * @return 库存信息
     */
    public Stock getStockBySkuAndRegion(String sku, int regionId, String channelCode,
                                        int requireQty, boolean isNeedMultipleSecCode,
                                        boolean isReliable, boolean lockFlag, String cOrderSn,
                                        boolean selfLockFlag) {
        return getStockBySkuAndRegion(sku, regionId, channelCode, requireQty, isNeedMultipleSecCode,
            isReliable, lockFlag, cOrderSn, selfLockFlag, 3);
    }

    /**
     * 根据物料和区域获取库存 
     * 
     * @param sku 物料
     * @param regionId 区域ID
     * @param channelCode 渠道
     * @param requireQty 需求数量
     * @param isNeedMultipleSecCode 是否多层级
     * @param isReliable 是否可靠库存
     * @param lockFlag 下单锁标识 true:扣减下单锁 false:不扣减下单锁
     * @param cOrderSn 网单号，付款时判断是否本身有下单锁
     * @param selfLockFlag 扣减本身下单锁标识 true:不扣减本身锁定数量,操作为加 false:扣减本身锁定数量,不操作 
     * @param addressLevel 地区级别   区县=3，街道 =4
     * @return 库存信息
     */
    public Stock getStockBySkuAndRegion(String sku, int regionId, String channelCode,
                                        int requireQty, boolean isNeedMultipleSecCode,
                                        boolean isReliable, boolean lockFlag, String cOrderSn,
                                        boolean selfLockFlag, int addressLevel) {

        long timestamp = System.currentTimeMillis();

        if (requireQty <= 0) {
            requireQty = 1;
        }
        if (addressLevel == 0) {
            throw new BusinessException("区域和街道级别标示参数为空, sku=" + sku);
        }
        if (selfLockFlag && StringUtil.isEmpty(cOrderSn)) {
            throw new BusinessException("支付时网单号不能为空,sku=" + sku + ",regionId=" + regionId);
        }
        StringBuilder message = new StringBuilder();
        message.append("根据区县或街道获取库存:");
        message.append(isReliable ? "master|" : "salve|");
        InvStockChannel stockChannel = invStockChannelDao.getByCode(channelCode);
        if (stockChannel == null)
            throw new BusinessException("根据区县或街道获取库存：不支持的渠道编码, channel=" + channelCode
                                        + ", regionId=" + regionId + ", sku=" + sku);

        Long startTime = System.currentTimeMillis();
        List<String> regionScodeList;
        String quyu = "街道";
        String quyuId = "streetId";
        if (addressLevel == 4) {//4级街道查询
            //根据街道id，获取对应的库位列表（清除重复）已完成
            regionScodeList = storageStreetsService.getSCodeByStreet(regionId);
            message.append("street(").append(regionId).append("):")
                .append(System.currentTimeMillis() - startTime).append("ms");
        } else {//默认3级区县查询
            regionScodeList = storageCitiesService.getCodeListByRegion(regionId);
            message.append("region(").append(regionId).append("):")
                .append(System.currentTimeMillis() - startTime).append("ms");
            quyu = "区县";
            quyuId = "regionId";
        }
        if (regionScodeList == null || regionScodeList.isEmpty()
            || StringUtil.isEmpty(regionScodeList.get(0))) {
            throw new BusinessException("根据区县或街道获取库存:" + quyu + "不存在!" + quyuId + "=" + regionId);
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
                    if (currStock.getAvaibleQty() >= requireQty) {
                        return currStock;
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
        //        ? invStockDao.getReliableStockQtyByWhCodesAndSkus(
        //            StockBizHelper.stringWithSingleQuote(sku),
        //            StockBizHelper.stringWithSingleQuote(wareHouses),
        //            StockBizHelper.stringWithSingleQuote(stockChannelCode),
        //            StockBizHelper.stringWithSingleQuote(channelCode))
        //        : invStockDao.getStockQtyByWhCodesAndSkus(StockBizHelper.stringWithSingleQuote(sku),
        //            StockBizHelper.stringWithSingleQuote(wareHouses),
        //            StockBizHelper.stringWithSingleQuote(stockChannelCode),
        //            StockBizHelper.stringWithSingleQuote(channelCode));
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

    public Stock getStockBySkuAndRegion(String sku, int regionId, String channelCode,
                                        int requireQty, boolean isReliable) {
        return getStockBySkuAndRegion(sku, regionId, channelCode, requireQty, true, isReliable,
            true);
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

    private boolean hasGdCode(String secCode) {

        for (String code : InvSection.gdCodes) {
            if (code.equalsIgnoreCase(secCode)) {
                return true;
            }
        }
        return false;
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

    public Date stockStatusInCity(String channel, Date startQueryTime) {
        if (!InvStockChannel.CHANNEL_SHANGCHENG.equals(channel)) {
            return null;
        }
        // 查询 商城渠道可使用的渠道库存
        InvStockChannel stockChannel = invStockChannelDao.getByCode(channel);
        if (stockChannel == null) {
            return null;
        }
        Long startTime = System.currentTimeMillis();
        String logInfo = "";
        long totalExecuteTime = 0l;
        long executeTime = 0l;

        String stockChannels = stockChannel.getStockChannelCodes();
        String[] stockChannelArray = stockChannels.split(",");

        List<String> waChannelList = new ArrayList<String>();
        for (String channelEntity : stockChannelArray) {
            if (InvSection.CHANNEL_CODE_WA.equals(channelEntity)
                || InvStockChannel.CHANNEL_SHANGCHENG.equals(channelEntity)) {
                waChannelList.add(channelEntity);
            }
        }
        // 查询WA变化库存
        startTime = System.currentTimeMillis();
        List<InvStock> waChangedList = invStockService.getChangedListBySkuWhcode(startQueryTime,
            StockBizHelper.stringWithSingleQuote(waChannelList),
            StockBizHelper.stringWithSingleQuote(channel));
        List<Stock> waChangedStockList = convertToStock(waChangedList);
        executeTime = System.currentTimeMillis() - startTime;
        totalExecuteTime += executeTime;
        logInfo += "查询WA库变化库存耗时---" + executeTime + "ms,";
        // 海朋库变化库存
        startTime = System.currentTimeMillis();
        List<InvStock> haipChangedList = invStockService.getChangedStockQty(startQueryTime,
            StockBizHelper.stringWithSingleQuote(InvSection.CHANNEL_CODE_HAIP),
            StockBizHelper.stringWithSingleQuote(InvSection.CHANNEL_CODE_HAIP));
        List<Stock> haipChangedStockList = convertToStock(haipChangedList);
        executeTime = System.currentTimeMillis() - startTime;
        totalExecuteTime += executeTime;
        logInfo += "查询海朋库变化库存耗时---" + executeTime + "ms,";

        // 基地库变化库存
        startTime = System.currentTimeMillis();
        List<InvStock> gdkChangedList = invStockService.getChangedStockQty(startQueryTime,
            StockBizHelper.stringWithSingleQuote(InvSection.CHANNEL_CODE_GD),
            StockBizHelper.stringWithSingleQuote(InvSection.CHANNEL_CODE_GD));
        List<Stock> gdkChangedStockList = convertToStock(gdkChangedList);
        executeTime = System.currentTimeMillis() - startTime;
        totalExecuteTime += executeTime;
        logInfo += "查询基地库变化库存耗时---" + executeTime + "ms,";
        Date now = new Date();
        // 查询Store变化库存
        /*
         * startTime = System.currentTimeMillis(); List<InvStore>
         * storeChangedList = invStoreDao.getChangedStockQty(startQueryTime);
         * List<Stock> storeChangedStockList =
         * convertStoreToStock(storeChangedList); executeTime =
         * System.currentTimeMillis() - startTime; totalExecuteTime +=
         * executeTime; logInfo += "查询EStore变化库存耗时---" + executeTime + "ms,";
         */

        startTime = System.currentTimeMillis();
        this.stockChangedUpdate(waChangedStockList, haipChangedStockList, gdkChangedStockList, null,
            channel, waChannelList);
        executeTime = System.currentTimeMillis() - startTime;
        totalExecuteTime += executeTime;
        logInfo += "计算城市物料有无货耗时---" + executeTime + "ms。";
        String[] logInfoArray = logInfo.split(",");
        for (String array : logInfoArray) {
            log.info(array);
        }
        log.info("总耗时----" + totalExecuteTime + "ms");
        return now;
    }

    private List<Stock> convertToStock(List<InvStock> invStockList) {
        List<Stock> stockList = new ArrayList<Stock>();
        for (InvStock entity : invStockList) {
            Stock stock = new Stock();
            stock.setSku(entity.getSku());
            stock.setSecCode(entity.getSecCode());
            stock.setStockType(BaseStock.STOCKTYPE_WA);
            stock.setAvaibleQty(entity.getStockQty());
            stockList.add(stock);
        }
        return stockList;
    }

    private void stockChangedUpdate(List<Stock> waChangedList, List<Stock> haipChangedList,
                                    List<Stock> gdkChangedList, List<Stock> storeChangedList,
                                    String channel, List<String> stockChannels) {
        Map<String, Integer> daMap = new HashMap<String, Integer>();
        List<Stock> daStockList = new ArrayList<Stock>();
        List<Stock> xiaoStockList = new ArrayList<Stock>();
        List<Stock> yiStockList = new ArrayList<Stock>();
        for (Stock entity : waChangedList) {
            ServiceResult<ProductBase> prdService = itemService
                .getPorductBaseBySku(entity.getSku());
            if (!prdService.getSuccess()) {
                throw new BusinessException(prdService.getMessage());
            }
            ProductBase p = prdService.getResult();

            if (p == null) {
                //log.info("无法区分大小家电，过滤掉此sku-->" + entity.getSku());
                continue;
            }

            // 一仓发全国的商品
            if (!StringUtil.isEmpty(p.getScode())
                && p.getScode().equalsIgnoreCase(entity.getSecCode())) {
                yiStockList.add(entity);
                continue;
            }
            // 小家电
            if ("B2C".equalsIgnoreCase(p.getShippingMode())) {
                xiaoStockList.add(entity);
            }
            // 大家电
            else {
                daStockList.add(entity);
                daMap.put(p.getSku(), p.getMultiStorage());
            }
        }
        boolean initFlagOn = false;
        boolean zeroCheck = !initFlagOn;
        List<StorageCities> allCityIds = storageCitiesService.getAllCityIds();
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        for (StorageCities cityId : allCityIds) {
            TransactionStatus statusShop = transactionManagerShop.getTransaction(def);
            try {
                List<BasChangeStock> insertList = new ArrayList<BasChangeStock>();
                Map<String, Set<String>> skuList = getStockStatusByCity(channel, cityId.getCityId(),
                    true, yiStockList, xiaoStockList, daStockList, haipChangedList, gdkChangedList,
                    storeChangedList, daMap, stockChannels, zeroCheck);
                for (Map.Entry<String, Set<String>> entityMap : skuList.entrySet()) {
                    String key = entityMap.getKey();
                    Set<String> value = entityMap.getValue();
                    if (value == null || value.size() <= 0) {
                        continue;
                    }
                    /*
                     * log.info("城市cityIds==" + cityId.getCityId() + ", key==" +
                     * key + ", skus==" + Arrays.toString(value.toArray()));
                     */
                    for (String sku : value) {
                        BasChangeStock entity = new BasChangeStock();
                        entity.setCityId(cityId.getCityId());
                        entity.setSku(sku);
                        entity.setHasStock(key.equals("youSku") ? 1 : 0);
                        if (initFlagOn) {
                            insertList.add(entity);
                        } else {
                            Integer rows = basChangeStockService.update(entity);
                            if (rows <= 0) {
                                insertList.add(entity);
                            }
                        }
                    }
                }
                if (insertList.size() > 0) {
                	basChangeStockService.insert(insertList);
                }
                // log.info("物料描述[" + cityId.getCityId() + "]" +
                // JsonUtil.toJson(skuList));
                //log.info("城市cityId="+cityId+", 库存更新完成!!!");
                transactionManagerShop.commit(statusShop);
            } catch (Exception e) {
                e.printStackTrace();
                transactionManagerShop.rollback(statusShop);
                log.error("更新城市cityId[" + cityId + "]物料失败" + e.getMessage());
            }
        }
    }

    public Map<String, Set<String>> getStockStatusByCity(String channel, int cityId,
                                                         boolean hasStock, List<Stock> yiStockList,
                                                         List<Stock> xiaoStockList,
                                                         List<Stock> daStockList,
                                                         List<Stock> haipChangedList,
                                                         List<Stock> gdkChangedList,
                                                         List<Stock> storeChangedList,
                                                         Map<String, Integer> daMap,
                                                         List<String> stockChannels,
                                                         boolean zeroCheck) {
        Long startTime = System.currentTimeMillis();
        Map<String, Set<String>> skuList = new HashMap<String, Set<String>>();
        // 有库存的Sku
        Set<String> youSkuList = new HashSet<String>();
        Set<String> wuSkuList = new HashSet<String>();

        // 计算一仓发全国的有库存的商品
        if (yiStockList != null && yiStockList.size() > 0) {
            for (Stock stock : yiStockList) {
                if (stock.getAvaibleQty() <= 0) {
                    if (!wuSkuList.contains(stock.getSku())
                        && !youSkuList.contains(stock.getSku())) {
                        wuSkuList.add(stock.getSku());
                    }
                    if (!youSkuList.contains(stock.getSku())) {
                        boolean tempHasStock = checkZeroHasStock(stock.getSku(), stockChannels,
                            false, zeroCheck);
                        if (tempHasStock) {
                            wuSkuList.remove(stock.getSku());
                            youSkuList.add(stock.getSku());
                        }
                    }

                    continue;
                } else {
                    if (wuSkuList.contains(stock.getSku())) {
                        wuSkuList.remove(stock.getSku());
                    }
                }
                /**
                 * 判断条件 1、库存大于0 2、sku一仓发全国，并且为该sku对应的scode
                 */
                String whCode = stock.getSecCode().substring(0, 2) + "WA";
                if (stock.getAvaibleQty() > 0 /*
                                              * &&
                                              * yiMap.containsKey(stock.getSku())
                                              * && yiMap.get(stock.getSku()).
                                              * equalsIgnoreCase(whCode)
                                              */) {
                    if (!youSkuList.contains(stock.getSku())) {
                        youSkuList.add(stock.getSku());
                    }

                }

            }
        }
        startTime = System.currentTimeMillis();

        // 计算小家电有库存的商品
        List<String> xiaoWhList = new ArrayList<String>();// 小家电涉及到的仓库
        // 小家电对应的仓库 - 七仓发全国
        xiaoWhList.add("SYWA");
        xiaoWhList.add("BJWA");
        xiaoWhList.add("JOWA");
        xiaoWhList.add("SHWA");
        xiaoWhList.add("FSWA");
        xiaoWhList.add("WHWA");
        xiaoWhList.add("CDWA");

        if (xiaoStockList != null && xiaoStockList.size() > 0) {
            startTime = System.currentTimeMillis();
            for (Stock stock : xiaoStockList) {

                /**
                 * 判断条件 1、库存大于0
                 */
                if (!xiaoWhList.contains(stock.getSecCode())) {
                    continue;
                }
                if (stock.getAvaibleQty() <= 0) {
                    if (!wuSkuList.contains(stock.getSku())
                        && !youSkuList.contains(stock.getSku())) {
                        wuSkuList.add(stock.getSku());
                    }
                    if (!youSkuList.contains(stock.getSku())) {
                        boolean tempHasStock = checkZeroHasStock(stock.getSku(), stockChannels,
                            true, zeroCheck);
                        if (tempHasStock) {
                            wuSkuList.remove(stock.getSku());
                            youSkuList.add(stock.getSku());
                        }
                    }
                    continue;
                } else {
                    if (wuSkuList.contains(stock.getSku())) {
                        wuSkuList.remove(stock.getSku());
                    }
                }
                if (!youSkuList.contains(stock.getSku())
                    && xiaoWhList.contains(stock.getSecCode())) {
                    youSkuList.add(stock.getSku());
                }

            }
        }
        startTime = System.currentTimeMillis();

        // 根据城市，获取对应的库位列表
        List<String> citySCodeList = daStockList.size() > 0
            ? storageCitiesService.getCodeListByCity(cityId) : new ArrayList<String>();
        startTime = System.currentTimeMillis();

        // 获取大家电多层级关系
        List<BigStoragesRela> daRelaList = daStockList.size() > 0 ? bigStoragesRelaService.getList()
            : null;

        // 组织大家电用到的所有库位
        List<String> daWhList = new ArrayList<String>();
        List<String> regionWhList = new ArrayList<String>();

        for (String s : citySCodeList) {
            String wh = s.length() > 2 ? s.substring(0, 2) : s;
            if (!daWhList.contains(wh)) {
                daWhList.add(wh);
                regionWhList.add(wh);
            }
        }
        List<String> daMutilSecCodeList = new ArrayList<String>();// 该城市下对应的所有多层级库位集合
        if (daRelaList != null && daRelaList.size() > 0) {
            for (BigStoragesRela rela : daRelaList) {
                if (!citySCodeList.contains(rela.getCode())) {
                    continue;// 过滤掉不是该城市下的多层级关系
                }
                // 主库位
                String wh = rela.getMasterCode().length() > 2 ? rela.getMasterCode().substring(0, 2)
                    : rela.getMasterCode();
                if (!daWhList.contains(wh)) {
                    daWhList.add(wh);
                    daMutilSecCodeList.add(rela.getMasterCode());
                }
                // 中心库位
                wh = rela.getCenterCode().length() > 2 ? rela.getCenterCode().substring(0, 2)
                    : rela.getCenterCode();
                if (!daWhList.contains(wh)) {
                    daWhList.add(wh);
                    daMutilSecCodeList.add(rela.getCenterCode());
                }
            }
        }
        startTime = System.currentTimeMillis();

        // 计算大家电有库存的商品

        if (daStockList != null && daStockList.size() > 0) {
            startTime = System.currentTimeMillis();
            for (Stock stock : daStockList) {
                /**
                 * 判断条件 1、库存大于0 2、使用多层级的时候，才能用到多层级相关的库存
                 */
                Boolean isMutiStorage = daMap.get(stock.getSku().trim()).equals(1) ? true : false;// 商品是否支持多层级
                String whCode = stock.getSecCode().substring(0, 2) + "WA";
                // 如果不支持多层级，且没有库位直接覆盖，则不能算有库存
                if (!isMutiStorage && !citySCodeList.contains(whCode)) {
                    continue;
                }
                // 如果支持多层级，但是多层级库位没有货，也不能算有库存
                if (isMutiStorage && !daMutilSecCodeList.contains(whCode)
                    && !citySCodeList.contains(whCode)) {
                    continue;
                }
                if (stock.getAvaibleQty() <= 0) {
                    if (!wuSkuList.contains(stock.getSku())
                        && !youSkuList.contains(stock.getSku())) {
                        wuSkuList.add(stock.getSku());
                    }

                    List<InvStock> tempdaList = invStockService.getValidStockQtyByWhCodesAndSkus(
                        StockBizHelper.stringWithSingleQuote(stock.getSku()),
                        // 如果物料不支持多层级，直接采用区县库位，如果采用多层级， daWhList中包含了区县库位和多层级库位
                        StockBizHelper
                            .stringWithSingleQuote(isMutiStorage ? daWhList : regionWhList),
                        StockBizHelper.stringWithSingleQuote(stockChannels));
                    Boolean tempHasStock = false;
                    for (InvStock tempStock : tempdaList) {
                        if (tempStock.getStockQty() > 0) {
                            tempHasStock = true;
                            break;
                        }
                    }
                    if (tempHasStock) {
                        wuSkuList.remove(stock.getSku());
                        youSkuList.add(stock.getSku());
                    }
                    continue;
                } else {
                    if (wuSkuList.contains(stock.getSku())) {
                        wuSkuList.remove(stock.getSku());
                    }
                }
                if (!youSkuList.contains(stock.getSku())) {
                    youSkuList.add(stock.getSku());
                }

            }
            startTime = System.currentTimeMillis();
        }

        if (gdkChangedList != null && gdkChangedList.size() > 0) {
            for (Stock stock : gdkChangedList) {
                /**
                 * 判断条件 1、库存大于0
                 */
                if (stock.getAvaibleQty() <= 0) {
                    if (!wuSkuList.contains(stock.getSku())
                        && !youSkuList.contains(stock.getSku())) {
                        wuSkuList.add(stock.getSku());
                    }
                    if (!youSkuList.contains(stock.getSku())) {
                        boolean tempHasStock = checkZeroHasStock(stock.getSku(), stockChannels,
                            true, zeroCheck);

                        if (tempHasStock) {
                            wuSkuList.remove(stock.getSku());
                            youSkuList.add(stock.getSku());
                        }
                    }
                    continue;
                } else {
                    if (wuSkuList.contains(stock.getSku())) {
                        wuSkuList.remove(stock.getSku());
                    }
                }
                if (!youSkuList.contains(stock.getSku())) {
                    youSkuList.add(stock.getSku());
                }

            }
        }

        if (haipChangedList != null && haipChangedList.size() > 0) {
            for (Stock stock : haipChangedList) {

                /*	ServiceResult<ProductBase> prdService = itemService
                			.getPorductBaseBySku(stock.getSku());
                	ProductBase p = null;
                	if (prdService.getSuccess()) {
                		p = prdService.getResult();
                		if (p == null) {
                			//log.info("无法区分大小家电，过滤掉此sku-->" + entity.getSku());
                			continue;
                		}
                	}*/
                /**
                 * 判断条件 1、库存大于0
                 */
                if (stock.getAvaibleQty() <= 0) {
                    if (!wuSkuList.contains(stock.getSku())
                        && !youSkuList.contains(stock.getSku())) {
                        wuSkuList.add(stock.getSku());
                    }
                    if (!youSkuList.contains(stock.getSku())) {
                        boolean tempHasStock = checkZeroHasStock(stock.getSku(), stockChannels,
                            true, zeroCheck);

                        if (tempHasStock) {
                            wuSkuList.remove(stock.getSku());
                            youSkuList.add(stock.getSku());
                        }
                    }

                    continue;
                } else {
                    if (wuSkuList.contains(stock.getSku())) {
                        wuSkuList.remove(stock.getSku());
                    }
                }
                if (!youSkuList.contains(stock.getSku())) {
                    youSkuList.add(stock.getSku());
                }

            }
        }
        // 查询这个城市下的8码
        if (storeChangedList != null && storeChangedList.size() > 0) {
            for (Stock stock : storeChangedList) {
                if (stock.getAvaibleQty() <= 0) {
                    if (!wuSkuList.contains(stock.getSku())
                        && !youSkuList.contains(stock.getSku())) {
                        wuSkuList.add(stock.getSku());
                    }
                    continue;
                } else {
                    if (wuSkuList.contains(stock.getSku())) {
                        wuSkuList.remove(stock.getSku());
                    }
                }
                if (!youSkuList.contains(stock.getSku())) {
                    youSkuList.add(stock.getSku());
                }
            }
        }
        /*
         * String[] infoArray = logInfo.split(";"); for (String s : infoArray) {
         * log.info(s); } log.info("总计耗时：" + totalExecuteTime + "ms。");
         * log.info("----------------getStockStatusByCity(cityId:" + cityId +
         * ", channel:" + channel + ")耗时记录-----结束---------------------------");
         */
        skuList.put("youSku", youSkuList);
        skuList.put("wuSku", wuSkuList);
        return skuList;
    }

    private boolean checkZeroHasStock(String sku, List<String> stockChannels, boolean enableWa,
                                      boolean zeroCheck) {
        if (!zeroCheck) {
            return false;
        }
        List<InvStock> tempStockList = null;
        boolean tempHasStock = false;
        if (!tempHasStock && enableWa) {
            List<String> tempWhList = new ArrayList<String>();
            tempWhList.add("SY");
            tempWhList.add("BJ");
            tempWhList.add("JO");
            tempWhList.add("SH");
            tempWhList.add("FS");
            tempWhList.add("WH");
            tempWhList.add("CD");
            List<InvStock> tempXiaoList = invStockService.getValidStockQtyByWhCodesAndSkus(
                StockBizHelper.stringWithSingleQuote(sku),
                StockBizHelper.stringWithSingleQuote(tempWhList),
                StockBizHelper.stringWithSingleQuote(stockChannels));
            for (InvStock tempStock : tempXiaoList) {
                if (tempStock.getStockQty() > 0) {
                    tempHasStock = true;
                    break;
                }
            }
        }
        if (!tempHasStock) {
            ServiceResult<ProductBase> prdService = itemService.getPorductBaseBySku(sku);
            if (prdService.getSuccess()) {
                ProductBase p = prdService.getResult();
                if (p != null && !StringUtil.isEmpty(p.getScode())) {
                    String sCode = p.getScode();
                    tempStockList = invStockService.getValidStockQtyByWhCodesAndSkus(
                        StockBizHelper.stringWithSingleQuote(sku),
                        StockBizHelper.stringWithSingleQuote(sCode.substring(0, 2)),
                        StockBizHelper.stringWithSingleQuote(stockChannels));
                    for (InvStock tstock : tempStockList) {
                        if (tstock.getStockQty() > 0) {
                            tempHasStock = true;
                            break;
                        }
                    }
                }
            }
        }
        if (!tempHasStock) {
            List<InvStock> haipStockList = invStockService.getValidStockQtyByWhCodesAndSkus(
                StockBizHelper.stringWithSingleQuote(sku),
                StockBizHelper.stringWithSingleQuote(InvSection.CHANNEL_CODE_HAIP),
                StockBizHelper.stringWithSingleQuote(InvSection.CHANNEL_CODE_HAIP));
            for (InvStock tempStock : haipStockList) {
                if (tempStock.getStockQty() > 0) {
                    tempHasStock = true;
                    break;
                }
            }
        }
        if (!tempHasStock) {
            List<InvStock> gdkStockList = invStockService.getValidStockQtyByWhCodesAndSkus(
                StockBizHelper.stringWithSingleQuote(sku),
                StockBizHelper.stringWithSingleQuote(InvSection.CHANNEL_CODE_GD),
                StockBizHelper.stringWithSingleQuote(InvSection.CHANNEL_CODE_GD));
            for (InvStock gdkStock : gdkStockList) {
                if (gdkStock.getStockQty() > 0) {
                    tempHasStock = true;
                    break;
                }
            }
        }
        return tempHasStock;
    }

    /**
     * 获取城下的有库存物料列表
     * 
     * @param cityId
     * @param channel
     * @return
     */
    public List<String> getStockStatusByCity(int cityId, String channel) {
        if (!InvStockChannel.CHANNEL_SHANGCHENG.equals(channel)) {
            throw new BusinessException("不支持的渠道编码 ：" + channel);
        }
        // 商城渠道可使用的渠道库存
        InvStockChannel stockChannel = invStockChannelDao.getByCode(channel);
        if (stockChannel == null) {
            throw new BusinessException("该渠道没有配置可用库存渠道 ：" + channel);
        }
        Long startTime = System.currentTimeMillis();
        String logInfo = "";
        long totalExecuteTime = 0;

        // 获取上架的商品列表
        List<ProductBase> pList = itemService.getAllOnSaleProducts().getResult();
        logInfo += "获取上架的商品列表, 记录数(" + (pList == null ? 0 : pList.size()) + "), 耗时("
                   + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        if (pList == null || pList.size() == 0) {
            return null;
        }

        // 分拣出各种库存模式的商品
        List<String> yiSkuList = new ArrayList<String>();// 一仓发全国的商品
        List<String> yiWhList = new ArrayList<String>();// 一仓发全国涉及到的仓库
        Map<String, String> yiMap = new HashMap<String, String>();// 一仓发全国的sku和scode映射关系
        List<String> xiaoSkuList = new ArrayList<String>();// 小家电
        List<String> daSkuList = new ArrayList<String>();// 大家电
        Map<String, Integer> daMap = new HashMap<String, Integer>();// 大家电sku和是否使用多层级的映射关系
        for (ProductBase p : pList) {
            // 一仓发全国的商品
            if (!StringUtil.isEmpty(p.getScode())) {
                yiSkuList.add(p.getSku());
                String whCode = p.getScode();
                if (whCode.length() > 2) {// 截取库位的前两个字母作为仓库编码
                    whCode = whCode.substring(0, 2);
                }
                yiWhList.add(whCode);
                if (!yiMap.containsKey(p.getSku())) {
                    yiMap.put(p.getSku(), p.getScode());
                }
                continue;
            }
            // 小家电
            if ("B2C".equalsIgnoreCase(p.getShippingMode())) {
                xiaoSkuList.add(p.getSku());
            }
            // 大家电
            else {
                daSkuList.add(p.getSku());
                daMap.put(p.getSku(), p.getMultiStorage());
            }
        }
        logInfo += "分拣出各种库存模式的商品, 一仓发全国(" + yiSkuList.size() + "), 大家电(" + daSkuList.size()
                   + "), 小家电(" + xiaoSkuList.size() + "), 耗时("
                   + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        // 有库存的Sku
        List<String> youSkuList = new ArrayList<String>();

        // 计算一仓发全国的有库存的商品
        List<InvStock> yiStockList = null;
        if (yiSkuList != null && yiSkuList.size() > 0) {
            String skus = StockBizHelper.concat(yiSkuList.toArray(new String[yiSkuList.size()]));
            String whCodes = StockBizHelper.concat(yiWhList.toArray(new String[yiWhList.size()]));
            yiStockList = invStockService.getValidStockQtyByWhCodesAndSkus(
                StockBizHelper.stringWithSingleQuote(skus),
                StockBizHelper.stringWithSingleQuote(whCodes),
                StockBizHelper.stringWithSingleQuote(stockChannel.getStockChannelCodes()));
            logInfo += "获取一仓发全国商品的库存, 获取记录数(" + (yiStockList == null ? 0 : yiStockList.size())
                       + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
            totalExecuteTime += System.currentTimeMillis() - startTime;
            startTime = System.currentTimeMillis();
            if (yiStockList != null && yiStockList.size() > 0) {
                for (InvStock stock : yiStockList) {
                    /**
                     * 判断条件 1、库存大于0 2、sku一仓发全国，并且为该sku对应的scode
                     */
                    String whCode = stock.getSecCode().substring(0, 2) + "WA";
                    if (stock.getStockQty() > 0 && yiMap.containsKey(stock.getSku())
                        && yiMap.get(stock.getSku()).equalsIgnoreCase(whCode)) {
                        if (!youSkuList.contains(stock.getSku())) {
                            youSkuList.add(stock.getSku());
                        }
                    }
                }
            }
        }
        logInfo += "计算一仓发全国有库存的商品, 处理库存记录数(" + (yiStockList == null ? 0 : yiStockList.size())
                   + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        // 计算小家电有库存的商品
        List<String> xiaoWhList = new ArrayList<String>();// 小家电涉及到的仓库
        // 小家电对应的仓库 - 七仓发全国
        xiaoWhList.add("SY");
        xiaoWhList.add("BJ");
        xiaoWhList.add("JO");
        xiaoWhList.add("SH");
        xiaoWhList.add("FS");
        xiaoWhList.add("WH");
        xiaoWhList.add("CD");
        List<InvStock> xiaoStockList = null;
        if (xiaoSkuList != null && xiaoSkuList.size() > 0) {
            String skus = StockBizHelper
                .concat(xiaoSkuList.toArray(new String[xiaoSkuList.size()]));
            String whCodes = StockBizHelper
                .concat(xiaoWhList.toArray(new String[xiaoWhList.size()]));
            xiaoStockList = invStockService.getValidStockQtyByWhCodesAndSkus(
                StockBizHelper.stringWithSingleQuote(skus),
                StockBizHelper.stringWithSingleQuote(whCodes),
                StockBizHelper.stringWithSingleQuote(stockChannel.getStockChannelCodes()));
            logInfo += "获取小家电的库存, 获取记录数(" + (xiaoStockList == null ? 0 : xiaoStockList.size())
                       + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
            totalExecuteTime += System.currentTimeMillis() - startTime;
            startTime = System.currentTimeMillis();
            for (InvStock stock : xiaoStockList) {
                /**
                 * 判断条件 1、库存大于0
                 */
                if (stock.getStockQty() <= 0) {
                    continue;
                }
                if (!youSkuList.contains(stock.getSku())) {
                    youSkuList.add(stock.getSku());
                }
            }
        }
        logInfo += "计算小家电有库存的商品, 处理库存记录数(" + (xiaoStockList == null ? 0 : xiaoStockList.size())
                   + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        // 根据城市，获取对应的库位列表
        List<String> citySCodeList = storageCitiesService.getCodeListByCity(cityId);
        logInfo += "根据城市，获取对应的库位列表, 记录数(" + (citySCodeList == null ? 0 : citySCodeList.size())
                   + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        if (citySCodeList == null || citySCodeList.size() == 0) {
            // 记录执行情况，便于分析和优化
            log.info("----------------getStockStatusByCity(cityId:" + cityId + ", channel:"
                     + channel + ")耗时记录-----开始---------------------------");
            String[] infoArray = logInfo.split(";");
            for (String s : infoArray) {
                log.info(s);
            }
            log.info("总计耗时：" + totalExecuteTime + "ms。");
            log.info("----------------getStockStatusByCity(cityId:" + cityId + ", channel:"
                     + channel + ")耗时记录-----结束---------------------------");
            return youSkuList;
        }

        // 获取大家电多层级关系
        List<BigStoragesRela> daRelaList = bigStoragesRelaService.getList();

        // 组织大家电用到的所有库位
        List<String> daWhList = new ArrayList<String>();
        for (String s : citySCodeList) {
            String wh = s.length() > 2 ? s.substring(0, 2) : s;
            if (!daWhList.contains(wh)) {
                daWhList.add(wh);
            }
        }
        List<String> daMutilSecCodeList = new ArrayList<String>();// 该城市下对应的所有多层级库位集合
        if (daRelaList != null && daRelaList.size() > 0) {
            for (BigStoragesRela rela : daRelaList) {
                if (!citySCodeList.contains(rela.getCode())) {
                    continue;// 过滤掉不是该城市下的多层级关系
                }
                // 主库位
                String wh = rela.getMasterCode().length() > 2 ? rela.getMasterCode().substring(0, 2)
                    : rela.getMasterCode();
                if (!daWhList.contains(wh)) {
                    daWhList.add(wh);
                    daMutilSecCodeList.add(rela.getMasterCode());
                }
                // 中心库位
                wh = rela.getCenterCode().length() > 2 ? rela.getCenterCode().substring(0, 2)
                    : rela.getCenterCode();
                if (!daWhList.contains(wh)) {
                    daWhList.add(wh);
                    daMutilSecCodeList.add(rela.getCenterCode());
                }
            }
        }
        logInfo += "组织大家电用到的所有库位, 处理记录数(" + (daWhList == null ? 0 : daWhList.size()) + "), 耗时("
                   + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        // 计算大家电有库存的商品
        List<InvStock> daStockList = null;
        if (daSkuList != null && daSkuList.size() > 0) {
            String skus = StockBizHelper.concat(daSkuList.toArray(new String[daSkuList.size()]));
            String whCodes = StockBizHelper.concat(daWhList.toArray(new String[daWhList.size()]));
            daStockList = invStockService.getValidStockQtyByWhCodesAndSkus(
                StockBizHelper.stringWithSingleQuote(skus),
                StockBizHelper.stringWithSingleQuote(whCodes),
                StockBizHelper.stringWithSingleQuote(stockChannel.getStockChannelCodes()));
            logInfo += "获取大家电的库存, 获取记录数(" + (daStockList == null ? 0 : daStockList.size())
                       + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
            totalExecuteTime += System.currentTimeMillis() - startTime;
            startTime = System.currentTimeMillis();
            for (InvStock stock : daStockList) {
                /**
                 * 判断条件 1、库存大于0 2、使用多层级的时候，才能用到多层级相关的库存
                 */
                if (stock.getStockQty() <= 0) {
                    continue;
                }
                Boolean isMutiStorage = daMap.get(stock.getSku()).equals(1) ? true : false;// 商品是否支持多层级
                String whCode = stock.getSecCode().substring(0, 2) + "WA";
                // 如果不支持多层级，且没有库位直接覆盖，则不能算有库存
                if (!isMutiStorage && !citySCodeList.contains(whCode)) {
                    continue;
                }
                // 如果支持多层级，但是多层级库位没有货，也不能算有库存
                if (isMutiStorage && !daMutilSecCodeList.contains(whCode)
                    && !citySCodeList.contains(whCode)) {
                    continue;
                }
                if (!youSkuList.contains(stock.getSku())) {
                    youSkuList.add(stock.getSku());
                }
            }
            logInfo += "计算大家电有库存的商品, 处理记录数(" + (daStockList == null ? 0 : daStockList.size())
                       + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
            totalExecuteTime += System.currentTimeMillis() - startTime;
            startTime = System.currentTimeMillis();
        }

        String stockChannelCode = stockChannel.getStockChannelCodes();
        String[] stockChannelCodes = stockChannelCode.split(",");
        List<String> stockChannelList = Arrays.asList(stockChannelCodes);
        boolean useGd = stockChannelList.contains(InvSection.CHANNEL_CODE_GD);
        boolean useHIPENG = stockChannelList.contains(InvSection.CHANNEL_CODE_HAIP);
        if (useGd) {
            // 获取基地库存
            getSkusByStockChannel(InvSection.CHANNEL_CODE_GD, InvSection.CHANNEL_CODE_GD,
                youSkuList, "基地");
        }
        if (useHIPENG) {
            getSkusByStockChannel(InvSection.CHANNEL_CODE_HAIP, InvSection.CHANNEL_CODE_HAIP,
                youSkuList, "海朋");
        }

        // 记录执行情况，便于分析和优化
        log.info("getStockStatusByCity(cityId:" + cityId + ", channel:" + channel + ")，总计耗时："
                 + totalExecuteTime + "ms。");
        // String[] infoArray = logInfo.split(";");
        // for (String s : infoArray) {
        // log.info(s);
        // }
        // log.info("总计耗时：" + totalExecuteTime + "ms。");
        // log.info("----------------getStockStatusByCity(cityId:" + cityId +
        // ", channel:" + channel
        // + ")耗时记录-----结束---------------------------");

        return youSkuList;
    }

    private void getSkusByStockChannel(String channel, String stockChannel, List<String> youSkuList,
                                       String str) {
        String logInfo = "";
        Long totalExecuteTime = 0l;
        Long startTime = System.currentTimeMillis();
        List<InvStock> haipStockList = invStockService.getValidStockQtyByWhCodesAndSkus(null,
            StockBizHelper.stringWithSingleQuote(channel),
            StockBizHelper.stringWithSingleQuote(stockChannel));
        logInfo += "获取" + str + "库存, 获取记录数(" + (haipStockList == null ? 0 : haipStockList.size())
                   + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        // 计算基地库存商品
        startTime = System.currentTimeMillis();
        for (InvStock stock : haipStockList) {
            /**
             * 判断条件 1、库存大于0
             */
            if (stock.getStockQty() <= 0) {
                continue;
            }
            if (!youSkuList.contains(stock.getSku())) {
                youSkuList.add(stock.getSku());
            }
        }
        logInfo += "计算基地库存的商品, 处理记录数(" + (haipStockList == null ? 0 : haipStockList.size())
                   + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        log.info("getSkusByStockChannel：channel[" + channel + "],stockChannel[" + stockChannel
                 + "], 耗费" + totalExecuteTime);
    }

    /**
     * 获取城下的有库存物料列表 -按批次 不支持多层级
     * 
     * @param cityId
     * @param channel
     * @return
     */
    public List<String> getStockStatusByCity(int cityId, String channel, Integer itemProperty) {
        if (!InvStockChannel.CHANNEL_SHANGCHENG.equals(channel)) {
            throw new BusinessException("不支持的渠道编码 ：" + channel);
        }
        if (itemProperty == null) {
            itemProperty = 10;// 默认10批次
        }
        // 是否使用WA替换41（itemProperty）

        // 商城渠道可使用的渠道库存
        InvStockChannel stockChannel = invStockChannelDao.getByCode(channel);
        if (stockChannel == null) {
            throw new BusinessException("该渠道没有配置可用库存渠道 ：" + channel);
        }
        Long startTime = System.currentTimeMillis();
        String logInfo = "";
        long totalExecuteTime = 0;

        // 获取上架的商品列表
        List<ProductBase> pList = itemService.getAllOnSaleProducts().getResult();
        logInfo += "获取上架的商品列表, 记录数(" + (pList == null ? 0 : pList.size()) + "), 耗时("
                   + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        if (pList == null || pList.size() == 0) {
            return null;
        }

        // 分拣出各种库存模式的商品
        List<String> yiSkuList = new ArrayList<String>();// 一仓发全国的商品
        List<String> yiWhList = new ArrayList<String>();// 一仓发全国涉及到的仓库
        Map<String, String> yiMap = new HashMap<String, String>();// 一仓发全国的sku和scode映射关系
        List<String> xiaoSkuList = new ArrayList<String>();// 小家电
        List<String> daSkuList = new ArrayList<String>();// 大家电
        Map<String, Integer> daMap = new HashMap<String, Integer>();// 大家电sku和是否使用多层级的映射关系
        for (ProductBase p : pList) {
            // 一仓发全国的商品
            if (!StringUtil.isEmpty(p.getScode())) {
                yiSkuList.add(p.getSku());
                String whCode = p.getScode();
                if (whCode.length() > 2) {// 截取库位的前两个字母作为仓库编码
                    whCode = whCode.substring(0, 2);
                }
                yiWhList.add(whCode);
                if (!yiMap.containsKey(p.getSku())) {
                    yiMap.put(p.getSku(), p.getScode());
                }
                continue;
            }
            // 小家电
            if ("B2C".equalsIgnoreCase(p.getShippingMode())) {
                xiaoSkuList.add(p.getSku());
            }
            // 大家电
            else {
                daSkuList.add(p.getSku());
                daMap.put(p.getSku(), 0);// -- 设置都不支持多层级，后面的多层级不在生效 0不支持 1支持
                                         // old=daMap.put(p.getSku(),
                                         // p.getMultiStorage())
                                         // wyj:2014-09-02
            }
        }
        logInfo += "分拣出各种库存模式的商品, 一仓发全国(" + yiSkuList.size() + "), 大家电(" + daSkuList.size()
                   + "), 小家电(" + xiaoSkuList.size() + "), 耗时("
                   + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        // 有库存的Sku
        List<String> youSkuList = new ArrayList<String>();

        // 计算一仓发全国的有库存的商品
        List<InvStock> yiStockList = null;
        if (yiSkuList != null && yiSkuList.size() > 0) {
            String skus = StockBizHelper.concat(yiSkuList.toArray(new String[yiSkuList.size()]));
            String whCodes = StockBizHelper.concat(yiWhList.toArray(new String[yiWhList.size()]));
            yiStockList = invStockService.getValidStockQtyByItemProperty(
                StockBizHelper.stringWithSingleQuote(skus),
                StockBizHelper.stringWithSingleQuote(whCodes),
                StockBizHelper.stringWithSingleQuote(stockChannel.getStockChannelCodes()),
                itemProperty);
            logInfo += "获取一仓发全国商品的库存, 获取记录数(" + (yiStockList == null ? 0 : yiStockList.size())
                       + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
            totalExecuteTime += System.currentTimeMillis() - startTime;
            startTime = System.currentTimeMillis();
            if (yiStockList != null && yiStockList.size() > 0) {
                for (InvStock stock : yiStockList) {
                    /**
                     * 判断条件 1、库存大于0 2、sku一仓发全国，并且为该sku对应的scode
                     */
                    String whCode = stock.getSecCode().substring(0, 2) + "WA";
                    if (stock.getStockQty() > 0 && yiMap.containsKey(stock.getSku())
                        && yiMap.get(stock.getSku()).equalsIgnoreCase(whCode)) {
                        if (!youSkuList.contains(stock.getSku())) {
                            youSkuList.add(stock.getSku());
                        }
                    }
                }
            }
        }
        logInfo += "计算一仓发全国有库存的商品, 处理库存记录数(" + (yiStockList == null ? 0 : yiStockList.size())
                   + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        // 计算小家电有库存的商品
        List<String> xiaoWhList = new ArrayList<String>();// 小家电涉及到的仓库
        // 小家电对应的仓库 - 七仓发全国
        xiaoWhList.add("SY");
        xiaoWhList.add("BJ");
        xiaoWhList.add("JO");
        xiaoWhList.add("SH");
        xiaoWhList.add("FS");
        xiaoWhList.add("WH");
        xiaoWhList.add("CD");
        List<InvStock> xiaoStockList = null;
        if (xiaoSkuList != null && xiaoSkuList.size() > 0) {
            String skus = StockBizHelper
                .concat(xiaoSkuList.toArray(new String[xiaoSkuList.size()]));
            String whCodes = StockBizHelper
                .concat(xiaoWhList.toArray(new String[xiaoWhList.size()]));
            xiaoStockList = invStockService.getValidStockQtyByItemProperty(
                StockBizHelper.stringWithSingleQuote(skus),
                StockBizHelper.stringWithSingleQuote(whCodes),
                StockBizHelper.stringWithSingleQuote(stockChannel.getStockChannelCodes()),
                itemProperty);
            logInfo += "获取小家电的库存, 获取记录数(" + (xiaoStockList == null ? 0 : xiaoStockList.size())
                       + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
            totalExecuteTime += System.currentTimeMillis() - startTime;
            startTime = System.currentTimeMillis();
            for (InvStock stock : xiaoStockList) {
                /**
                 * 判断条件 1、库存大于0
                 */
                if (stock.getStockQty() <= 0) {
                    continue;
                }
                if (!youSkuList.contains(stock.getSku())) {
                    youSkuList.add(stock.getSku());
                }
            }
        }
        logInfo += "计算小家电有库存的商品, 处理库存记录数(" + (xiaoStockList == null ? 0 : xiaoStockList.size())
                   + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        // 根据城市，获取对应的库位列表
        List<String> citySCodeList = storageCitiesService.getCodeListByCity(cityId);
        logInfo += "根据城市，获取对应的库位列表, 记录数(" + (citySCodeList == null ? 0 : citySCodeList.size())
                   + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        if (citySCodeList == null || citySCodeList.size() == 0) {
            // 记录执行情况，便于分析和优化
            log.info("----------------getStockStatusByCity(cityId:" + cityId + ", channel:"
                     + channel + ")耗时记录-----开始---------------------------");
            String[] infoArray = logInfo.split(";");
            for (String s : infoArray) {
                log.info(s);
            }
            log.info("总计耗时：" + totalExecuteTime + "ms。");
            log.info("----------------getStockStatusByCity(cityId:" + cityId + ", channel:"
                     + channel + ")耗时记录-----结束---------------------------");
            return youSkuList;
        }

        // 获取大家电多层级关系
        List<BigStoragesRela> daRelaList = bigStoragesRelaService.getList();

        // 组织大家电用到的所有库位
        List<String> daWhList = new ArrayList<String>();
        for (String s : citySCodeList) {
            String wh = s.length() > 2 ? s.substring(0, 2) : s;
            if (!daWhList.contains(wh)) {
                daWhList.add(wh);
            }
        }
        List<String> daMutilSecCodeList = new ArrayList<String>();// 该城市下对应的所有多层级库位集合
        if (daRelaList != null && daRelaList.size() > 0) {
            for (BigStoragesRela rela : daRelaList) {
                if (!citySCodeList.contains(rela.getCode())) {
                    continue;// 过滤掉不是该城市下的多层级关系
                }
                // 主库位
                String wh = rela.getMasterCode().length() > 2 ? rela.getMasterCode().substring(0, 2)
                    : rela.getMasterCode();
                if (!daWhList.contains(wh)) {
                    daWhList.add(wh);
                    daMutilSecCodeList.add(rela.getMasterCode());
                }
                // 中心库位
                wh = rela.getCenterCode().length() > 2 ? rela.getCenterCode().substring(0, 2)
                    : rela.getCenterCode();
                if (!daWhList.contains(wh)) {
                    daWhList.add(wh);
                    daMutilSecCodeList.add(rela.getCenterCode());
                }
            }
        }
        logInfo += "组织大家电用到的所有库位, 处理记录数(" + (daWhList == null ? 0 : daWhList.size()) + "), 耗时("
                   + (System.currentTimeMillis() - startTime) + "ms);";
        totalExecuteTime += System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        // 计算大家电有库存的商品
        List<InvStock> daStockList = null;
        if (daSkuList != null && daSkuList.size() > 0) {
            String skus = StockBizHelper.concat(daSkuList.toArray(new String[daSkuList.size()]));
            String whCodes = StockBizHelper.concat(daWhList.toArray(new String[daWhList.size()]));
            daStockList = invStockService.getValidStockQtyByItemProperty(
                StockBizHelper.stringWithSingleQuote(skus),
                StockBizHelper.stringWithSingleQuote(whCodes),
                StockBizHelper.stringWithSingleQuote(stockChannel.getStockChannelCodes()),
                itemProperty);
            logInfo += "获取大家电的库存, 获取记录数(" + (daStockList == null ? 0 : daStockList.size())
                       + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
            totalExecuteTime += System.currentTimeMillis() - startTime;
            startTime = System.currentTimeMillis();
            for (InvStock stock : daStockList) {
                /**
                 * 判断条件 1、库存大于0 2、使用多层级的时候，才能用到多层级相关的库存
                 */
                if (stock.getStockQty() <= 0) {
                    continue;
                }
                Boolean isMutiStorage = daMap.get(stock.getSku()).equals(1) ? true : false;// 商品是否支持多层级
                String whCode = stock.getSecCode().substring(0, 2) + "WA";
                // 如果不支持多层级，且没有库位直接覆盖，则不能算有库存
                if (!isMutiStorage && !citySCodeList.contains(whCode)) {
                    continue;
                }
                // 如果支持多层级，但是多层级库位没有货，也不能算有库存
                if (isMutiStorage && !daMutilSecCodeList.contains(whCode)
                    && !citySCodeList.contains(whCode)) {
                    continue;
                }
                if (!youSkuList.contains(stock.getSku())) {
                    youSkuList.add(stock.getSku());
                }
            }
            logInfo += "计算大家电有库存的商品, 处理记录数(" + (daStockList == null ? 0 : daStockList.size())
                       + "), 耗时(" + (System.currentTimeMillis() - startTime) + "ms);";
            totalExecuteTime += System.currentTimeMillis() - startTime;
            startTime = System.currentTimeMillis();
        }

        // 记录执行情况，便于分析和优化
        log.info("----------------getStockStatusByCity(cityId:" + cityId + ", channel:" + channel
                 + ")耗时记录-----开始---------------------------");
        String[] infoArray = logInfo.split(";");
        for (String s : infoArray) {
            log.info(s);
        }
        log.info("总计耗时：" + totalExecuteTime + "ms。");
        log.info("----------------getStockStatusByCity(cityId:" + cityId + ", channel:" + channel
                 + ")耗时记录-----结束---------------------------");

        return youSkuList;
    }

    /**
     * 根据物料和区域获取库存 -按批次 --不支持多层级 
     * 
     * @param sku
     * @param regionId
     * @param channelCode
     * @param requireQty
     * @param isReliable
     * @param itemProperty
     * @return
     */
    public Stock getStockBySkuAndRegion(String sku, int regionId, String channelCode,
                                        int requireQty, boolean isReliable, Integer itemProperty) {
        // ---------------------------------
        boolean isNeedMultipleSecCode = false;// 不支持多层级
        if (!InvStockChannel.CHANNEL_SHANGCHENG.equals(channelCode)) {
            throw new BusinessException("不支持的渠道编码 ：" + channelCode);
        }
        // ---------------------------------
        if (requireQty <= 0)
            requireQty = 1;
        if (itemProperty == null) {
            itemProperty = 10;// 默认10批次
        }
        Long startTime = System.currentTimeMillis();
        // 根据区县，获取对应的库位列表
        List<String> regionScodeList = storageCitiesService.getCodeListByRegion(regionId);
        if (regionScodeList == null || regionScodeList.isEmpty()) {
            throw new BusinessException("查询区县下的库位列表发生异常");
        }
        log.info("获取scodeList（" + regionScodeList.size() + "）："
                 + (System.currentTimeMillis() - startTime));

        startTime = System.currentTimeMillis();

        // 获取产品信息
        ServiceResult<ProductBase> productResult = new ServiceResult<ProductBase>();
        try {
            productResult = itemService.getPorductBaseBySku(sku);
        } catch (Exception e) {
            productResult.setMessage(e.getMessage());
            e.printStackTrace();
        }
        ProductBase product;
        if (productResult.getSuccess()) {
            product = productResult.getResult();
        } else {
            throw new BusinessException(productResult.getMessage());
        }
        if (product == null) {
            return StockBizHelper.getZeroStock(sku, regionScodeList.get(0).substring(0, 2));
        }
        log.info("获取产品信息: " + (System.currentTimeMillis() - startTime));
        InvStockChannel stockChannel = invStockChannelDao.getByCode(channelCode);
        if (stockChannel == null)
            throw new BusinessException("不支持的渠道编码");
        // check大家电
        String wareHouses = "";
        // 大家电且不是商城渠道的或者不需要多层级或者指定了默认发货库位，则不能使用多层级
        if (!StringUtil.isEmpty(product.getScode())) {
            wareHouses = product.getScode().substring(0, 2);
        } else if ((!"B2C".equalsIgnoreCase(product.getShippingMode())
                    && !InvStockChannel.CHANNEL_SHANGCHENG.equals(channelCode))
                   || !isNeedMultipleSecCode) {
            Set<String> whList = new HashSet<String>();
            for (String code : regionScodeList) {
                whList.add(code.substring(0, 2));
            }
            wareHouses = StockBizHelper.concat(whList.toArray(new String[whList.size()]));
        }
        log.info("getStockBySkuAndRegion:" + (StringUtil.isEmpty(wareHouses) ? "使用多层级" : "不使用多层级"));

        List<InvStock> stockList;

        // 不使用多层级
        if (!StringUtil.isEmpty(wareHouses)) {
            if (isReliable) {
                stockList = invStockService.getReliableStockQtyByItemProperty(
                    StockBizHelper.stringWithSingleQuote(sku),
                    StockBizHelper.stringWithSingleQuote(wareHouses),
                    StockBizHelper.stringWithSingleQuote(stockChannel.getStockChannelCodes()),
                    itemProperty);
            } else {
                stockList = invStockService.getStockQtyByItemProperty(
                    StockBizHelper.stringWithSingleQuote(sku),
                    StockBizHelper.stringWithSingleQuote(wareHouses),
                    StockBizHelper.stringWithSingleQuote(stockChannel.getStockChannelCodes()),
                    itemProperty);
            }
            Stock stock;
            int stockQty = 0;
            Date updateDate = null;
            for (InvStock invStock : stockList) {
                if (stockQty < invStock.getStockQty()) {
                    stockQty = invStock.getStockQty();
                    updateDate = invStock.getUpdateTime();
                }
            }
            stock = new Stock();
            stock.setAvaibleQty(stockQty);
            stock.setSecCode(wareHouses + InvSection.CHANNEL_CODE_WA);// 应该和什么拼接
                                                                      // 41
                                                                      // 还是WA
            stock.setSku(sku);
            stock.setUpdateTime(updateDate);
            return stock;
        }

        return null;
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

    /**
     * 同步第三方库存，减掉下单锁数量
     * @param afterStockList
     */
    private void subtractLockQty(List<Stock> afterStockList) {
        if (afterStockList != null && afterStockList.size() > 0) {
            int num = 500;//每次查询数量
            List<InvStockOrderLockEntity> lockList = new ArrayList<InvStockOrderLockEntity>();
            int count = afterStockList.size() % num == 0 ? afterStockList.size() / num
                : afterStockList.size() / num + 1;
            for (int i = 0; i < count; i++) {
                Set<String> arraySku = null;
                Set<String> arrayScode = null;
                if (i == count - 1) {
                    arraySku = new HashSet<String>(afterStockList.size() - i * num);
                    arrayScode = new HashSet<String>(afterStockList.size() - i * num + 1);
                    for (int j = i * num; j < afterStockList.size(); j++) {
                        arraySku.add(afterStockList.get(j).getSku());
                        arrayScode.add(afterStockList.get(j).getSecCode());
                    }
                } else {
                    arraySku = new HashSet<String>(num);
                    arrayScode = new HashSet<String>(num + 1);
                    for (int j = i * num; j < (i + 1) * num; j++) {
                        arraySku.add(afterStockList.get(j).getSku());
                        arrayScode.add(afterStockList.get(j).getSecCode());
                    }
                }
                if (arrayScode.contains("SHWA")) {
                    arrayScode.add("CT01");
                }
                List<InvStockOrderLockEntity> tempLockList = invStockOrderLockService
                    .findLockQtyByScodeAndSku(
                        (String[]) arrayScode.toArray(new String[arrayScode.size()]),
                        (String[]) arraySku.toArray(new String[arraySku.size()]));
                if (tempLockList != null && tempLockList.size() > 0) {
                    lockList.addAll(tempLockList);
                }
            }

            if (lockList != null && lockList.size() > 0) {
                Map<String, Integer> lockQtyMap = new HashMap<String, Integer>();
                for (InvStockOrderLockEntity invStockOrderLockEntity : lockList) {
                    String sCode = invStockOrderLockEntity.getScode();
                    if (!StringUtil.isEmpty(SHARECODES.get(sCode))) {
                        sCode = SHARECODES.get(sCode);
                    }
                    String key = sCode + invStockOrderLockEntity.getSku();
                    if (!lockQtyMap.containsKey(key)) {
                        lockQtyMap.put(key, invStockOrderLockEntity.getLockQty());
                    } else {
                        lockQtyMap.put(key,
                            lockQtyMap.get(key) + invStockOrderLockEntity.getLockQty());
                    }
                }
                for (Stock afterStock : afterStockList) {
                    String key = afterStock.getSecCode() + afterStock.getSku();
                    if (lockQtyMap.containsKey(key)) {
                        Integer avaibleQty = afterStock.getAvaibleQty();
                        avaibleQty = avaibleQty - lockQtyMap.get(key);
                        avaibleQty = avaibleQty.intValue() < 0 ? 0 : avaibleQty;
                        afterStock.setAvaibleQty(avaibleQty);
                    }
                }
            }
        }
    }
}
