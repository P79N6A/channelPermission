package com.haier.order.model;

import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.order.services.OrderCenterItemServiceImpl;
import com.haier.shop.model.BasChangeStockRegion;
import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.ProductBase;
import com.haier.shop.model.StorageCities;
import com.haier.shop.service.BasChangeStockRegionService;
import com.haier.shop.service.BigStoragesRelaService;
import com.haier.shop.service.StorageCitiesService;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStock;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.service.InvStockService;
import com.haier.stock.service.StockInvStockChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 同步区县库存到顺逛model类
 */
@Service
public class StockSyncToRegionModel {

    private static final Logger LOGGER   = LoggerFactory.getLogger(StockSyncToRegionModel.class);
    private static final String LOG_MARK = "[Stock][SyncToRegionMode] ";
    @Autowired
    private StockInvStockChannelService invStockChannelDao;
    @Autowired
    private InvStockService invStockDao;
    @Autowired
    private BigStoragesRelaService bigStoragesRelaService;
    @Autowired
    private StorageCitiesService storageCitiesDao;
    @Autowired
    private BasChangeStockRegionService basChangeStockRegionDao;
    @Autowired
    private OrderCenterItemServiceImpl orderThirdCenterItemService;

    private static final int TOP_X = 5000;

    private static final String B2B2C = "B2B2C";

    /**
     * Product shipping mode B2C
     */
    private static final String B2C = "B2C";

    /**
     * 表示小家电库位
     */
    private static final String COUNTRY_WIDE_SEC_CODE = "ONE";

    /**
     * 小家电7仓发全国库位
     */
    private static final String[] B2C_WA_WH_CODES = { "SH", "JO", "WH", "FS", "BJ", "CD", "SY" };

    /**
     * B2C发货模式下,可以一仓发全国
     */
    private static final Map<Integer, Integer> COUNTRY_WIDE_REGION = new HashMap<Integer, Integer>();

    static {
        COUNTRY_WIDE_REGION.put(0, 0);
    }

    public Date syncToRegion(Date lastSyncDate) {

        StringBuilder logMsg = new StringBuilder(LOG_MARK).append("预处理");
        long tsBegin = System.currentTimeMillis();
        int total = 0;

        //每小时的28分全部跑一遍.原因:商品上下架|大家电多层级变更|小家电多层级变更会影响计算结果
        if (isCycle()) {
            lastSyncDate = DateUtil.parse("1970-01-01", "yyyy-MM-dd");
        }

        Date syncDate = lastSyncDate;

        //渠道
        InvStockChannel stockChannel = invStockChannelDao.getByCode(InvStockChannel.CHANNEL_RRS);
        if (stockChannel == null) {
            return lastSyncDate;
        }
        HashSet<String> stockChannels = new HashSet<String>(
            Arrays.asList(stockChannel.getStockChannelCodes().split(",")));

        long ts_step = System.currentTimeMillis();
        logMsg.append(">>> channel:").append(ts_step - tsBegin).append(" ms");
        //获取变化的库存记录
        List<InvStock> stocks = getChangedStockList(syncDate);

        if (stocks.size() <= 0) {
            LOGGER.info(LOG_MARK + "无变化库存,处理完成");
            return syncDate;
        }

        ts_step = System.currentTimeMillis();
        //获取商品列表
        List<ProductBase> onSaleProducts = getAllProducts();
        if (onSaleProducts.size() <= 0) {
            return syncDate;
        }
        logMsg.append("|products:").append(System.currentTimeMillis() - ts_step).append(" ms");
        ts_step = System.currentTimeMillis();
        //组建库位城市覆盖关系
        Map<String, WarehouseRegionNode> warehouseRegions = getWarehouseRegions();
        logMsg.append("|warehouseRegions").append(System.currentTimeMillis() - ts_step)
            .append(" ms");

        LOGGER.info(logMsg.toString());

        Map<String, SyncStructure> structures = new HashMap<String, SyncStructure>();
        do {
            logMsg = new StringBuilder(LOG_MARK);
            total += stocks.size();
            logMsg.append("装配库存和区县关系,size:").append(stocks.size());
            ts_step = System.currentTimeMillis();
            for (InvStock stock : stocks) {
                if (syncDate.before(stock.getUpdateTime())) {//记录最大库存变化时间
                    syncDate = stock.getUpdateTime();
                }
                String sku = stock.getSku();
                String secCode = stock.getSecCode();

                //商品是否上架
                ProductBase productBase = getProducts(sku, onSaleProducts);
                if (productBase == null) {
                    continue;
                }

                //变化库存是否影响此渠道库存数量
                if (!isInfluenceChannel(secCode, stockChannels, productBase)) {
                    continue;
                }

                String whCode = secCode.substring(0, 2);

                SyncStructure structure = structures.get(sku);
                if (structure == null) {
                    structure = new SyncStructure(productBase);
                    structures.put(sku, structure);
                }
                WarehouseRegionNode node;
                if (B2C.equalsIgnoreCase(structure.getShippingMode())) {
                    node = warehouseRegions.get(COUNTRY_WIDE_SEC_CODE);
                } else {
                    if (structure.isFixedWarehouse()) {
                        node = new WarehouseRegionNode(COUNTRY_WIDE_SEC_CODE, COUNTRY_WIDE_REGION);
                    } else {
                        node = warehouseRegions.get(whCode);
                    }
                }
                if (node == null) {
                    continue;
                }
                structure.addWarehouseRegion(new WarehouseRegion(node));
            }

            logMsg.append("|time:").append(System.currentTimeMillis() - ts_step).append(" ms");

            LOGGER.info(logMsg.toString());

            //continue
            if (syncDate.after(lastSyncDate)) {
                lastSyncDate = syncDate;
                stocks = getChangedStockList(syncDate);
            } else {
                stocks = new ArrayList<InvStock>();
            }
        } while (stocks.size() > 0);

        Set<String> extWhCodes = new HashSet<String>();
        if (stockChannels.contains(InvSection.CHANNEL_CODE_GD)) {
            extWhCodes.add(InvSection.CHANNEL_CODE_GD);
        }

        if (stockChannels.contains(InvSection.CHANNEL_CODE_HAIP)) {
            extWhCodes.add(InvSection.CHANNEL_CODE_HAIP);
        }

        //2017-05-02 净水库存--------START
        for (String channel : InvSection.NEW_CHANNEL_CODE) {
            if (stockChannels.contains(channel)) {
                extWhCodes.add(channel);
            }
        }
        //2017-05-02 净水库存--------END

        Set<String> smallWhCodes = new HashSet<String>(Arrays.asList(B2C_WA_WH_CODES));
        smallWhCodes.addAll(extWhCodes);

        SyncStructures syncStructures = new SyncStructures(
            new ArrayList<SyncStructure>(structures.values()), stockChannels, smallWhCodes,
            extWhCodes);

        if (total <= 500) {
            updateRegionStocks(new ArrayList<SyncStructure>(structures.values()), stockChannels,
                smallWhCodes, extWhCodes);
        } else {
            int n = total / 500;
            if (n > 15)
                n = 15;
            ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors
                .newFixedThreadPool(n);
            for (int i = 0; i < n; i++) {
                executorService.execute(new Worker(syncStructures));
            }
            while (executorService.getCompletedTaskCount() < n) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    LOGGER.error("", e);
                }
            }
        }

        logMsg = new StringBuilder(LOG_MARK);
        logMsg.append("syncToShop 完成,共").append(total).append("条记录,用时 ")
            .append(System.currentTimeMillis() - tsBegin).append(" ms.");
        LOGGER.info(logMsg.toString());
        return syncDate;

    }

    private int updateRegionStocks(List<SyncStructure> structures, Set<String> stockChannels,
                                   Set<String> smallWhCodes, Set<String> extWhCodes) {

        if (structures.size() <= 0)
            return 0;

        for (SyncStructure structure : structures) {
            setIsHasStock(structure, stockChannels, smallWhCodes, extWhCodes);
        }

        List<BasChangeStockRegion> toUpdateStocks = new ArrayList<BasChangeStockRegion>();

        long ts = System.currentTimeMillis();
        for (SyncStructure structure : structures) {
            toUpdateStocks.addAll(structureToStock(structure));
        }
        LOGGER.info(LOG_MARK + "生成库存(" + toUpdateStocks.size() + ")用时:"
                    + (System.currentTimeMillis() - ts) + " ms");
        updateBasChangeStock(toUpdateStocks);
        return toUpdateStocks.size();
    }

    private void updateBasChangeStock(List<BasChangeStockRegion> stocks) {
        List<BasChangeStockRegion> insertList = new ArrayList<BasChangeStockRegion>();
        for (BasChangeStockRegion stock : stocks) {
            int rows = basChangeStockRegionDao.update2(stock);
            if (rows <= 0) {
                insertList.add(stock);
            }
        }

        while (insertList.size() > TOP_X) {
            List<BasChangeStockRegion> subList = insertList.subList(0, TOP_X);
            basChangeStockRegionDao.insert2(subList);
            subList.clear();
        }
        if (insertList.size() > 0)
            basChangeStockRegionDao.insert2(insertList);

    }

    private void setIsHasStock(SyncStructure structure, Set<String> stockChannels,
                               Set<String> smallWhCodes, Set<String> extWhCodes) {

        Set<String> codes;
        String sku = structure.getSku();
        boolean isB2C = B2C.equalsIgnoreCase(structure.getShippingMode());
        if (structure.isFixedWarehouse() || isB2C) {
            InvStock stock;
            if (!isB2C) {//大家电一仓发全国(不应该存在这种业务)
                stock = getStock(sku, Collections.singleton(structure.getFixedSecCode()),
                    stockChannels);
            } else if (structure.isFixedWarehouse()) {//小家电一仓发全国
                codes = new HashSet<String>(extWhCodes);
                codes.add(structure.getFixedSecCode());
                stock = getStock(sku, codes, stockChannels);
            } else {//小家电
                stock = getStock(sku, smallWhCodes, stockChannels);
            }
            if (stock != null) {
                boolean hasStock = stock.getStockQty() > 0;
                for (WarehouseRegion warehouseRegion : structure.getWarehouseRegions()) {
                    warehouseRegion.setHasStock(hasStock);
                    warehouseRegion.setInventoryUpdateTime(stock.getUpdateTime());
                }
            }
        } else {
            for (WarehouseRegion warehouseRegion : structure.getWarehouseRegions()) {
                codes = new HashSet<String>();
                codes.add(warehouseRegion.getSecCode());
                if (warehouseRegion.getNode().hasParent() && structure.isMultiStorage()) {
                    codes.add(warehouseRegion.getNode().getParent().getSecCode());
                }
                InvStock stock = getStock(sku, codes, stockChannels);
                if (stock != null) {
                    warehouseRegion.setHasStock(stock.getStockQty() > 0);
                    warehouseRegion.setInventoryUpdateTime(stock.getUpdateTime());
                }
            }
        }

        //        LOGGER.info(LOG_MARK + "城市是否有货(" + sku + "," + structure.getWarehouseCitiesSize() + ") 用时:"
        //                    + (System.currentTimeMillis() - ts) + " ms");

    }

    private Collection<BasChangeStockRegion> structureToStock(SyncStructure structure) {
        String sku = structure.getSku();
        Map<Integer, BasChangeStockRegion> toUpdateMap = new HashMap<Integer, BasChangeStockRegion>();
        for (WarehouseRegion warehouseRegion : structure.getWarehouseRegions()) {
            if (!warehouseRegion.isUsable()) {
                continue;
            }
            Map<Integer, Integer> regionsMap = warehouseRegion.getNode().getRegions();
            for (Entry<Integer, Integer> region : regionsMap.entrySet()) {
                boolean isHasStock = warehouseRegion.isHasStock();
                BasChangeStockRegion stock = toUpdateMap.get(region.getKey());
                if (stock == null) {
                    stock = new BasChangeStockRegion();
                    stock.setSku(sku);
                    stock.setRegionId(region.getKey());
                    stock.setCityId(region.getValue());
                    stock.setHasStock(isHasStock ? 1 : 0);
                    stock.setUpdateTime(warehouseRegion.getInventoryUpdateTime() == null
                        ? new Date() : warehouseRegion.getInventoryUpdateTime());
                    toUpdateMap.put(region.getKey(), stock);
                } else if (isHasStock) {
                    stock.setHasStock(1);
                }
            }
        }
        return toUpdateMap.values();
    }

    private InvStock getStock(String sku, Set<String> whCodes, Set<String> stockChannels) {
        return invStockDao.getMaxStock(sku, whCodes, stockChannels);
    }

    private ProductBase getProducts(String sku, List<ProductBase> onSaleProducts) {
        for (ProductBase onSaleProduct : onSaleProducts) {
            if (onSaleProduct.getSku().equals(sku)) {
                return onSaleProduct;
            }
        }
        return null;
    }

    private boolean isInfluenceChannel(String secCode, Set<String> stockChannels,
                                       ProductBase productBase) {
        if (StringUtil.isEmpty(secCode) || secCode.length() != 4) {
            return false;
        }

        if (isGDInventory(secCode) && stockChannels.contains(InvSection.CHANNEL_CODE_GD)) {
            return true;
        }

        if (isHaiPengInventory(secCode) && stockChannels.contains(InvSection.CHANNEL_CODE_HAIP)) {
            return true;
        }

        //2017-05-02 净水库存--------START
        if (isJINS(secCode)) {
            if (stockChannels != null) {
                for (String channel : InvSection.NEW_CHANNEL_CODE) {
                    if (stockChannels.contains(channel)) {
                        return true;
                    }
                }
            }
        }
        //2017-05-02 净水库存--------END

        String fixedCode = productBase.getScode();
        String subStr = secCode.substring(2, 4);
        if (!StringUtil.isEmpty(fixedCode)) {
            return fixedCode.substring(0, 2).equalsIgnoreCase(secCode.substring(0, 2));
        }

        if ("DK".equalsIgnoreCase(subStr)) {
            subStr = "DKH";
        }

        return stockChannels.contains(subStr);

    }

    private boolean isGDInventory(String secCode) {
        return InvSection.gdCodes.contains(secCode);
    }

    private boolean isHaiPengInventory(String secCode) {
        return InvSection.HAIPENGCodes.contains(secCode);
    }

    private boolean isJINS(String secCode) {
        return InvSection.JINGSHUICodes.contains(secCode);
    }

    private boolean isCycle() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        //由于服务器有时间差，特殊处理，大约5:15时跑的需要全部处理
        return hour == 5 && (minute >= 10 && minute < 25);
    }

    private List<InvStock> getChangedStockList(Date time) {
        long ts = System.currentTimeMillis();
        List<InvStock> stockList = invStockDao.getChanngedStockList(time, TOP_X);
        LOGGER.info("获取变化库存,数量:" + stockList.size() + " 用时:" + (System.currentTimeMillis() - ts));
        return stockList;
    }

    private List<ProductBase> getAllProducts() {
        ServiceResult<List<ProductBase>> result = orderThirdCenterItemService.getAllProducts();
        if (!result.getSuccess()) {
            LOGGER.error(LOG_MARK + " 获取所有商品列表发生未知错误:" + result.getMessage());
            return new ArrayList<ProductBase>();
        }
        return result.getResult();
    }

    private Map<String, WarehouseRegionNode> getWarehouseRegions() {
        Map<String, WarehouseRegionNode> warehouseRegions = new HashMap<String, WarehouseRegionNode>();
        //大家电多层级关系
        List<BigStoragesRela> bigStoragesRelas = getBigStoragesRelas();
        //库位城市覆盖关系
        List<StorageCities> storageRegions = getStorageRegions();

        //小家电
        warehouseRegions.put(COUNTRY_WIDE_SEC_CODE,
            new WarehouseRegionNode(COUNTRY_WIDE_SEC_CODE, COUNTRY_WIDE_REGION));

        //大家电
        //确定库位覆盖城市关系
        Map<String, Map<Integer, Integer>> codeRegionMap = new HashMap<String, Map<Integer, Integer>>();
        for (StorageCities storageRegion : storageRegions) {
            String sCode = storageRegion.getSCodeA();
            if (sCode == null || sCode.length() < 2) {
                continue;
            }
            String whCode = sCode.substring(0, 2);
            Map<Integer, Integer> regions = codeRegionMap.get(whCode);
            if (regions == null) {
                regions = new HashMap<Integer, Integer>();
                codeRegionMap.put(whCode, regions);
            }
            regions.put(storageRegion.getRegionId(), storageRegion.getCityId());
        }

        //添加到warehouseCities中
        for (Entry<String, Map<Integer, Integer>> entry : codeRegionMap.entrySet()) {
            String whCode = entry.getKey();
            warehouseRegions.put(whCode, new WarehouseRegionNode(whCode, entry.getValue()));
        }

        //确定多层级关系
        for (BigStoragesRela bigStoragesRela : bigStoragesRelas) {
            String code = bigStoragesRela.getCode().substring(0, 2);
            String masterCode = bigStoragesRela.getMasterCode().substring(0, 2);

            WarehouseRegionNode warehouseRegionNode = warehouseRegions.get(code);
            if (warehouseRegionNode == null) {
                continue;
            }
            WarehouseRegionNode parent = warehouseRegions.get(masterCode);
            if (parent == null) {
                continue;
            }
            parent.addChild(warehouseRegionNode);
            warehouseRegionNode.setParent(parent);

        }

        return warehouseRegions;

    }

    private List<BigStoragesRela> getBigStoragesRelas() {
        return bigStoragesRelaService.getList();
    }

    private List<StorageCities> getStorageRegions() {
        return storageCitiesDao.getAllRegions();
    }

    private static class SyncStructures {
        private List<SyncStructure> structures;

        private Set<String> stockChannels;
        private Set<String> smallWhCodes;
        private Set<String> extWhCodes;

        public SyncStructures(List<SyncStructure> structures, Set<String> stockChannels,
                              Set<String> smallWhCodes, Set<String> extWhCodes) {
            this.structures = structures;
            this.stockChannels = stockChannels;
            this.smallWhCodes = smallWhCodes;
            this.extWhCodes = extWhCodes;
        }

        public synchronized SyncStructure getStructure() {

            if (structures.size() <= 0) {
                return null;
            }

            return structures.remove(0);

        }

        public Set<String> getStockChannels() {
            return stockChannels;
        }

        public Set<String> getSmallWhCodes() {
            return smallWhCodes;
        }

        public Set<String> getExtWhCodes() {
            return extWhCodes;
        }
    }

    private static class SyncStructure {
        private String                sku;
        private String                shippingMode;
        private boolean               fixedWarehouse;
        private String                fixedSecCode;
        private boolean               multiStorage;
        private Set<String>           codes            = new HashSet<String>();
        private List<WarehouseRegion> warehouseRegions = new ArrayList<WarehouseRegion>();

        private List<WarehouseRegion> warehouseRegionsReal = new ArrayList<WarehouseRegion>();

        public SyncStructure(ProductBase productBase) {
            this.sku = productBase.getSku();
            this.shippingMode = productBase.getShippingMode();
            if (StringUtil.isEmpty(shippingMode)) {
                shippingMode = B2B2C;
            }
            multiStorage = productBase.getMultiStorage().equals(1);
            fixedSecCode = productBase.getScode();
            this.fixedWarehouse = !StringUtil.isEmpty(fixedSecCode);
        }

        public void addWarehouseRegion(WarehouseRegion warehouseRegion) {
            if (codes.contains(warehouseRegion.getSecCode())) {
                return;
            }
            codes.add(warehouseRegion.getSecCode());
            warehouseRegions.add(warehouseRegion);
        }

        public String getSku() {
            return sku;
        }

        public List<WarehouseRegion> getWarehouseRegions() {
            if (this.warehouseRegionsReal.size() == 0) {
                Map<String, WarehouseRegion> warehouseRegionMap = new HashMap<String, WarehouseRegion>();
                for (WarehouseRegion warehouseRegion : warehouseRegions) {
                    String key = warehouseRegion.getSecCode();
                    if (!warehouseRegionMap.containsKey(key)) {
                        warehouseRegionMap.put(key, warehouseRegion);
                    }
                    if (warehouseRegion.getNode().hasChildren()) {
                        for (WarehouseRegionNode node : warehouseRegion.getNode().getChildren()) {
                            String childKey = node.getSecCode();
                            if (!warehouseRegionMap.containsKey(childKey)) {
                                warehouseRegionMap.put(childKey, new WarehouseRegion(node));
                            }
                        }
                    }
                }
                this.warehouseRegionsReal = new ArrayList<WarehouseRegion>(
                    warehouseRegionMap.values());
            }

            return this.warehouseRegionsReal;
        }

        public String getShippingMode() {
            return shippingMode;
        }

        public boolean isFixedWarehouse() {
            return fixedWarehouse;
        }

        public String getFixedSecCode() {
            return fixedSecCode;
        }

        public boolean isMultiStorage() {
            return multiStorage;
        }
    }

    private class Worker implements Runnable {
        private SyncStructures syncStructures;

        public Worker(SyncStructures mm) {
            this.syncStructures = mm;
        }

        @Override
        public void run() {
            try {
                SyncStructure structure = syncStructures.getStructure();
                while (structure != null) {
                    long ts = System.currentTimeMillis();
                    setIsHasStock(structure, syncStructures.getStockChannels(),
                        syncStructures.getSmallWhCodes(), syncStructures.getExtWhCodes());
                    ArrayList<BasChangeStockRegion> stocks = new ArrayList<BasChangeStockRegion>(
                        structureToStock(structure));
                    updateBasChangeStockRegion(stocks);
                    LOGGER.info(LOG_MARK + "Worker-" + Thread.currentThread().getId() + " execute "
                                + structure.getSku() + " size:" + stocks.size() + ",用时:"
                                + (System.currentTimeMillis() - ts) + " ms");
                    structure = syncStructures.getStructure();
                }
            } catch (Exception e) {
                LOGGER.error(LOG_MARK + "Worker error:", e);
            }
        }
    }

    private void updateBasChangeStockRegion(List<BasChangeStockRegion> stocks) {
        List<BasChangeStockRegion> insertList = new ArrayList<BasChangeStockRegion>();
        for (BasChangeStockRegion stock : stocks) {
            Integer rows = basChangeStockRegionDao.update2(stock);
            if (rows <= 0) {
                insertList.add(stock);
            }
        }

        while (insertList.size() > TOP_X) {
            List<BasChangeStockRegion> subList = insertList.subList(0, TOP_X);
            basChangeStockRegionDao.insert2(subList);
            subList.clear();
        }
        if (insertList.size() > 0)
            basChangeStockRegionDao.insert2(insertList);

    }

    private static class WarehouseRegion {

        private WarehouseRegionNode node;

        private boolean usable = false;

        //是否有库存
        private boolean hasStock = false;
        //库存更新时间
        private Date    inventoryUpdateTime;

        public String getSecCode() {
            return node.getSecCode();
        }

        public WarehouseRegion(WarehouseRegionNode node) {
            this.node = node;
        }

        public boolean isHasStock() {
            return hasStock;
        }

        public void setHasStock(boolean hasStock) {
            this.hasStock = hasStock;
        }

        public Date getInventoryUpdateTime() {
            return inventoryUpdateTime;
        }

        public void setInventoryUpdateTime(Date inventoryUpdateTime) {
            usable = true;
            this.inventoryUpdateTime = inventoryUpdateTime;
        }

        public WarehouseRegionNode getNode() {
            return node;
        }

        public boolean isUsable() {
            return usable;
        }

        @Override
        public String toString() {
            return JsonUtil.toJson(this);
        }
    }

    private static class WarehouseRegionNode {
        private String                    secCode;
        private Map<Integer, Integer>     regions;
        private WarehouseRegionNode       parent;
        private List<WarehouseRegionNode> children;

        public WarehouseRegionNode(String secCode, Map<Integer, Integer> regions) {
            this.secCode = secCode;
            this.regions = regions;
        }

        public void setParent(WarehouseRegionNode node) {
            this.parent = node;
        }

        public void addChild(WarehouseRegionNode childNode) {
            if (children == null) {
                children = new ArrayList<WarehouseRegionNode>();
            }
            children.add(childNode);
        }

        public boolean hasParent() {
            return parent != null;
        }

        public boolean hasChildren() {
            return children != null && children.size() > 0;
        }

        public String getSecCode() {
            return secCode;
        }

        public Map<Integer, Integer> getRegions() {
            return regions;
        }

        public WarehouseRegionNode getParent() {
            return parent;
        }

        public List<WarehouseRegionNode> getChildren() {
            return children;
        }
    }

    public StockInvStockChannelService getInvStockChannelDao() {
        return invStockChannelDao;
    }

    public void setInvStockChannelDao(StockInvStockChannelService invStockChannelDao) {
        this.invStockChannelDao = invStockChannelDao;
    }

    public InvStockService getInvStockDao() {
        return invStockDao;
    }

    public void setInvStockDao(InvStockService invStockDao) {
        this.invStockDao = invStockDao;
    }

    public BigStoragesRelaService getBigStoragesRelaDao() {
        return bigStoragesRelaService;
    }

    public void setBigStoragesRelaDao(BigStoragesRelaService bigStoragesRelaService) {
        this.bigStoragesRelaService = bigStoragesRelaService;
    }

    public StorageCitiesService getStorageCitiesDao() {
        return storageCitiesDao;
    }

    public void setStorageCitiesDao(StorageCitiesService storageCitiesDao) {
        this.storageCitiesDao = storageCitiesDao;
    }

    public BasChangeStockRegionService getBasChangeStockRegionDao() {
        return basChangeStockRegionDao;
    }

    public void setBasChangeStockRegionDao(BasChangeStockRegionService basChangeStockRegionDao) {
        this.basChangeStockRegionDao = basChangeStockRegionDao;
    }

//    public OrderThirdCenterItemService getOrderThirdCenterItemService() {
//        return orderThirdCenterItemService;
//    }
//
//    public void setOrderThirdCenterItemService(OrderThirdCenterItemService orderThirdCenterItemService) {
//        this.orderThirdCenterItemService = orderThirdCenterItemService;
//    }

}
