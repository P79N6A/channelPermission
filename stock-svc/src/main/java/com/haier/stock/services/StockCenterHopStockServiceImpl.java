package com.haier.stock.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.Stock;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvStockLock;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.service.StockCenterHopStockService;
import com.haier.stock.services.Helper.StockBizHelper;
@Service
public class StockCenterHopStockServiceImpl implements StockCenterHopStockService {
    private static Logger log = LoggerFactory.getLogger(StockCenterHopStockServiceImpl.class);
    @Autowired
    private StockModel       stockModel;
    @Autowired
    private HopStockModel    hopStockModel;
    @Autowired
    private StockCommonModel stockCommonModel;

    private static final String LOG_MARK = "[Stock][HopStockService] ";

    @Override
    public ServiceResult<List<Stock>> getStockIncrement(Date startTime, String channel) {
        ServiceResult<List<Stock>> result = new ServiceResult<List<Stock>>();
        if (startTime == null || StringUtil.isEmpty(channel)) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("startTime或渠道编码无效");
            return result;
        }
        try {
            result.setResult(hopStockModel.getStockIncrement(startTime, channel));
            result.setSuccess(true);
        } catch (Exception e) {
            log.error(LOG_MARK + "增量获取变化的库存列表错误：", e);
            result.setSuccess(false);
            result.setMessage("增量获取变化的库存列表错误" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<List<Stock>> getStockListBySku(String sku, String channel) {
        ServiceResult<List<Stock>> result = new ServiceResult<List<Stock>>();
        if (StringUtil.isEmpty(sku) || StringUtil.isEmpty(channel)) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("sku编码或渠道编码无效");
            return result;
        }
        try {
            result.setResult(hopStockModel.getStockListBySku(sku, channel));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("getStockListBySku:查询可用库存(" + "sku:" + sku + ",channel：" + channel
                              + "出现异常：" + e.getMessage());
            log.error(LOG_MARK + "查询可用库存(" + "sku:" + sku + ",channel：" + channel + ")", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<Stock>> getAllStockListBySku(String sku, String channel) {
        ServiceResult<List<Stock>> result = new ServiceResult<List<Stock>>();
        if (StringUtil.isEmpty(sku) || StringUtil.isEmpty(channel)) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("sku编码或渠道编码无效");
            return result;
        }
        try {
            result.setResult(hopStockModel.getStockListBySku(sku, channel, false));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("getStockListBySku:查询可用库存(" + "sku:" + sku + ",channel：" + channel
                              + "出现异常：" + e.getMessage());
            log.error(LOG_MARK + "查询可用库存(" + "sku:" + sku + ",channel：" + channel + ")", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Stock> getStockBySecCodeAndSku(String secCode, String sku,
                                                        String channel) {
        List<String> skuList = null;
        if (sku != null) {
            skuList = new ArrayList<String>();
            skuList.add(sku);
        }
        ServiceResult<List<Stock>> stockListResult = this.getStockBySecCodeAndSkus(secCode, skuList,
            channel);
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        if (stockListResult.getResult() == null || stockListResult.getResult().size() < 1) {
            result.setResult(null);
        } else {
            Stock stock = stockListResult.getResult().get(0);
            result.setResult(stock);
        }
        result.setSuccess(stockListResult.getSuccess());
        result.setMessage(stockListResult.getMessage());
        return result;
    }

    @Override
    public ServiceResult<List<Stock>> getStockBySkuAndSecCodes(String sku, List<String> secCodeList,
                                                               String channel) {
        ServiceResult<List<Stock>> result = new ServiceResult<List<Stock>>();
        if (StringUtil.isEmpty(sku) || secCodeList == null || secCodeList.size() <= 0
            || StringUtil.isEmpty(channel)) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("sku、secCode或渠道编码无效");
            return result;
        }
        try {
            result.setResult(hopStockModel.getStockBySkuAndSecCodes(sku, secCodeList, channel));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("查询库存失败：" + e.getMessage());
            log.error(LOG_MARK + "getStockBySkuAndSecCodes:查询可用库存(" + "sku:" + sku + ",secCode："
                      + JsonUtil.toJson(secCodeList) + "出现异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<Stock>> getAllStockBySkuAndSecCodes(String sku,
                                                                  List<String> secCodeList,
                                                                  String channel) {
        ServiceResult<List<Stock>> result = new ServiceResult<List<Stock>>();
        if (StringUtil.isEmpty(sku) || secCodeList == null || secCodeList.size() <= 0
            || StringUtil.isEmpty(channel)) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("sku、secCode或渠道编码无效");
            return result;
        }
        try {
            result.setResult(
                hopStockModel.getStockBySkuAndSecCodes(sku, secCodeList, channel, false));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("查询库存失败：" + e.getMessage());
            log.error(LOG_MARK + "getStockBySkuAndSecCodes:查询可用库存(" + "sku:" + sku + ",secCode："
                      + JsonUtil.toJson(secCodeList) + "出现异常：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<Stock>> getStockBySecCodeAndSkus(String secCode, List<String> skuList,
                                                               String channel) {
        ServiceResult<List<Stock>> result = new ServiceResult<List<Stock>>();
        if (StringUtil.isEmpty(secCode) || skuList == null || skuList.size() <= 0
            || StringUtil.isEmpty(channel)) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("sku、secCode或渠道编码无效");
            return result;
        }
        try {
            result.setResult(hopStockModel.getStockBySecCodeAndSkus(secCode, skuList, channel));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage(
                LOG_MARK + "getStockBySecCodeAndSkus:查询可用库存(" + "secCode:" + secCode + ",sku："
                              + StockBizHelper.concat(skuList.toArray(new String[skuList.size()]))
                              + "出现异常：" + e.getMessage());
            log.error(LOG_MARK + "查询可用库存(" + "secCode:" + secCode + ",sku："
                      + StockBizHelper.concat(skuList.toArray(new String[skuList.size()])),
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<Stock>> getStockList(List<String> skuList, List<String> secCodeList,
                                                   String channel) {
        ServiceResult<List<Stock>> result = new ServiceResult<List<Stock>>();
        if (skuList == null || skuList.size() <= 0 || secCodeList == null || skuList.size() <= 0
            || StringUtil.isEmpty(channel)) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("sku、secCode或渠道编码无效");
            return result;
        }
        if (skuList.size() != secCodeList.size()) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("sku列表和secCode列表长度不一致");
            return result;
        }
        try {
            result.setResult(hopStockModel.getStockList(skuList, secCodeList, channel));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取库存出现异常：" + e.getMessage());
            log.error(LOG_MARK + "查询可用库存(" + "sku:" + JsonUtil.toJson(skuList) + ",secCode："
                      + JsonUtil.toJson(secCodeList),
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<String>> getStockStatusByCity(int cityId, String channel) {
        ServiceResult<List<String>> result = new ServiceResult<List<String>>();
        try {
            result.setResult(hopStockModel.getStockStatusByCity(cityId, channel));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取失败：" + e.getMessage());
            log.error(LOG_MARK + "获取城市下有可用库存的物料失败(" + cityId + "," + channel + ")：", e);
        }
        return result;
    }

    public ServiceResult<Date> stockStatusInCity(Date startTime) {
        ServiceResult<Date> result = new ServiceResult<Date>();
        try {
            //  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date queryDate = hopStockModel.stockStatusInCity(InvStockChannel.CHANNEL_SHANGCHENG,
                startTime);
            result.setResult(queryDate);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取失败：" + e.getMessage());
            log.error(LOG_MARK + "获取城市下变化库存", e);
            result.setResult(null);
        }
        return result;
    }

    @Override
    public ServiceResult<Stock> getStockBySkuAndRegion(String sku, int regionId, String channel,
                                                       int requireQty) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        try {
            Stock stock = hopStockModel.getStockBySkuAndRegion(sku, regionId, channel, requireQty,
                false);
            result.setResult(stock);
            if (stock == null) {
                result.setMessage("没有获取到可用库存，RegionId=" + regionId);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("根据区县获取可用库存失败：" + e.getMessage());
            log.error(LOG_MARK + "根据区县获取可用库存失败（" + sku + "," + regionId + "," + channel + "）", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Stock> getReliableStockBySkuAndRegion(String sku, int regionId,
                                                               String channel, int requireQty) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        try {
            Stock stock = hopStockModel.getStockBySkuAndRegion(sku, regionId, channel, requireQty,
                true);
            result.setResult(stock);
            if (stock == null) {
                result.setMessage("没有获取到可用库存，RegionId=" + regionId);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("根据区县获取可用库存失败：" + e.getMessage());
            log.error(LOG_MARK + "根据区县获取可用库存失败（" + regionId + "）", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Stock> getReliableStockBySkuAndRegionForLevel(String sku, int regionId,
                                                                       String channel,
                                                                       int requireQty,
                                                                       int addressLevel) {

        ServiceResult<Stock> result = new ServiceResult<Stock>();
        try {
            if (addressLevel != 4) {
                addressLevel = 3;
            }
            Stock stock = hopStockModel.getStockBySkuAndRegion(sku, regionId, channel, requireQty,
                true, true, true, "", false, addressLevel);
            result.setResult(stock);
            if (stock == null) {
                result.setMessage("没有获取到可用库存，streetId=" + regionId);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("根据街道获取可用库存失败：" + e.getMessage());
            log.error(LOG_MARK + "根据街道获取可用库存失败（" + regionId + "）", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Stock> getStockBySkuAndRegion(String sku, int regionId, String channel,
                                                       int requireQty,
                                                       boolean isNeedMultipleSecCode) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        try {
            result.setResult(hopStockModel.getStockBySkuAndRegion(sku, regionId, channel,
                requireQty, isNeedMultipleSecCode, false));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("根据区县获取可用库存失败：" + e.getMessage());
            log.error(LOG_MARK + "根据区县获取可用库存失败（" + sku + "," + regionId + "," + channel + "）", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Stock> getStockBySkuAndRegionWithOutLock(String sku, int regionId,
                                                                  String channel, int requireQty,
                                                                  boolean isNeedMultipleSecCode,
                                                                  boolean lockFlag) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        try {
            result.setResult(hopStockModel.getStockBySkuAndRegion(sku, regionId, channel,
                requireQty, isNeedMultipleSecCode, false, lockFlag));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("根据区县获取可用库存失败：" + e.getMessage());
            log.error(LOG_MARK + "根据区县获取可用库存失败（" + sku + "," + regionId + "," + channel + "）", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Stock> getStockBySkuAndRegionWithOutLockForLevel(String sku, int regionId,
                                                                          String channel,
                                                                          int requireQty,
                                                                          boolean isNeedMultipleSecCode,
                                                                          boolean lockFlag,
                                                                          int addressLevel) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        try {

            result.setResult(hopStockModel.getStockBySkuAndRegion(sku, regionId, channel,
                requireQty, isNeedMultipleSecCode, false, lockFlag, "", false, addressLevel));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("根据区县获取可用库存失败：" + e.getMessage());
            log.error(LOG_MARK + "根据区县获取可用库存失败（" + sku + "," + regionId + "," + channel + "）", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Stock> getReliableStockBySkuAndRegion(String sku, int regionId,
                                                               String channel, int requireQty,
                                                               boolean isNeedMultipleSecCode) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        try {
            result.setResult(hopStockModel.getStockBySkuAndRegion(sku, regionId, channel,
                requireQty, isNeedMultipleSecCode, true));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("根据区县获取可用库存失败：" + e.getMessage());
            log.error(LOG_MARK + "根据区县获取可用库存失败（" + sku + "," + regionId + "," + channel + "）", e);
        }
        return result;
    }

    public ServiceResult<Map<Integer, List<String>>> getCitySkusByChannel(String channel) {
        ServiceResult<Map<Integer, List<String>>> result = new ServiceResult<Map<Integer, List<String>>>();
        try {
            result.setResult(hopStockModel.getCitySkusByChannel(channel));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("按渠道查询所有城市可用sku失败:" + e.getMessage());
            log.error(LOG_MARK + "按渠道查询所有城市可用sku失败:", e);
        }
        return result;
    }

    @Override
    public ServiceResult<String> frozeStockQty(String sku, String secCode, Integer frozenQty,
                                               String refNo, String channelCode,
                                               InventoryBusinessTypes billType) {
        return frozeStockQty(sku, secCode, frozenQty, refNo, channelCode, billType, false);
    }

    @Override
    public ServiceResult<String> frozeStockQty(String sku, String secCode, Integer frozenQty,
                                               String refNo, String channelCode,
                                               InventoryBusinessTypes billType, boolean useRRS) {
        ServiceResult<String> result = new ServiceResult<String>();
        try {
            InvSection section = stockCommonModel.getSectionByCode(secCode);
            String code = section.getChannelCode();
            if (InvSection.CHANNEL_CODE_GD.equals(code) || InvSection.CHANNEL_CODE_HAIP.equals(code)
            //2017-05-02 净水库存--------START
                || InvSection.NEW_CHANNEL_CODE.contains(code)
            //2017-05-02 净水库存--------END
            ) {
                ServiceResult<Boolean> frozeResult = stockModel.frozeStockQty(sku, secCode, refNo,
                    frozenQty, billType, "sys");
                result.setSuccess(frozeResult.getSuccess());
                result.setMessage(frozeResult.getMessage());
                result.setResult(
                    frozeResult.getSuccess() && frozeResult.getResult() ? secCode : null);
                return result;
            }

            String whCode = null;
            if (!StringUtil.isEmpty(secCode) && secCode.length() >= 2)
                whCode = section.getWhCode();
            return stockModel.frozeStockQtyByWhCode(sku, whCode, channelCode, refNo, frozenQty,
                billType, useRRS);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("冻结库存失败：" + e.getMessage());
            log.error(LOG_MARK + "frozeStockQty:冻结库存时发生未知异常(refNo:" + refNo + ",sku:" + sku
                      + ",secCode" + secCode + ",frozenQty:" + frozenQty + ")：",
                e);
        }
        return result;
    }

    @Override
    public ServiceResult<String> frozeStockQtyByProperty(String sku, String lesSecCode,
                                                         Integer property, Integer frozenQty,
                                                         String refNo,
                                                         InventoryBusinessTypes billType) {
        ServiceResult<String> result = new ServiceResult<String>();
        try {
            //计算虚拟库位
            String secCode = "";
            if (!StringUtil.isEmpty(lesSecCode) && lesSecCode.length() >= 2)
                secCode = lesSecCode.substring(0, 2);
            switch (property) {
                case 41:
                    secCode += "41";
                    break;
                case 40:
                    secCode += "40";
                    break;
                case 22:
                    secCode += "22";
                    break;
                case 21:
                    secCode += "21";
                    break;
                default:
                    result.setSuccess(false);
                    result.setResult(null);
                    result.setMessage("不支持对批次(" + property + ")进行冻结");
                    return result;
            }
            //冻结指定虚拟库位的库存
            ServiceResult<Boolean> r = stockModel.frozeStockQty(sku, secCode, refNo, frozenQty,
                billType, "sys");
            //冻结失败
            if (!r.getResult()) {
                result.setSuccess(false);
                result.setResult(null);
                result.setMessage("冻结库存失败：" + r.getMessage());
                return result;
            }
            //冻结成功
            result.setSuccess(true);
            result.setResult(lesSecCode);
            result.setMessage("");
            return result;
        } catch (Exception be) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("冻结库存失败：" + be.getMessage());
            log.error(LOG_MARK + "根据库存属性（批次）冻结库存:冻结库存时发生未知异常(refNo:" + refNo + ",sku:" + sku
                      + ",lesSecCode" + lesSecCode + ",property:" + property + ",frozenQty:"
                      + frozenQty + ")：",
                be);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> releaseFrozenStockQty(String sku, Integer releaseQty,
                                                        String refNo,
                                                        InventoryBusinessTypes billType) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            return stockModel.releaseFrozenStockQty(sku, refNo, releaseQty, billType);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("释放冻结库存失败：" + e.getMessage());
            log.error(LOG_MARK + "释放冻结库存失败(" + sku + "," + refNo + "," + releaseQty + ","
                      + billType.getCode() + ")：",
                e);
        }
        return result;
    }

    public void setStockModel(StockModel stockModel) {
        this.stockModel = stockModel;
    }

    public void setHopStockModel(HopStockModel hopStockModel) {
        this.hopStockModel = hopStockModel;
    }

    public void setStockCommonModel(StockCommonModel stockCommonModel) {
        this.stockCommonModel = stockCommonModel;
    }

    @Override
    public ServiceResult<Boolean> checkIfSkuHasStock(String sku) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setSuccess(true);
            result.setResult(stockModel.checkIfSkuHasStock(sku));
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage(e.getMessage());
            log.error(LOG_MARK + "检查sku(" + sku + ")是否有库存时发生未知异常：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<String>> getStockStatusByCity(int cityId, String channel,
                                                            Integer itemProperty) {
        ServiceResult<List<String>> result = new ServiceResult<List<String>>();
        try {
            result.setResult(hopStockModel.getStockStatusByCity(cityId, channel, itemProperty));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取失败：" + e.getMessage());
            log.error(LOG_MARK + "获取城市下有可用库存的物料失败(" + cityId + "," + channel + "," + itemProperty
                      + ")：" + e);
        }
        return result;
    }

    @Override
    public ServiceResult<Stock> getStockBySkuAndRegion(String sku, int regionId, String channelCode,
                                                       int requireQty, boolean isReliable,
                                                       Integer itemProperty) {
        ServiceResult<Stock> result = new ServiceResult<Stock>();
        try {
            result.setSuccess(true);
            result.setResult(hopStockModel.getStockBySkuAndRegion(sku, regionId, channelCode,
                requireQty, isReliable, itemProperty));
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("根据区县获取可用库存失败：" + e.getMessage());
            log.error(LOG_MARK + "根据区县获取可用库存失败（" + regionId + "）", e);
        }
        return result;
    }

    @Override
    public ServiceResult<List<InvStockLock>> getNoReleaseByLockTime(String lockTime, Integer topx) {
        ServiceResult<List<InvStockLock>> result = new ServiceResult<List<InvStockLock>>();
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
            result.setResult(stockModel.getNoReleaseByLockTime(lockTime, topx));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setResult(null);
            result.setMessage("getNoReleaseByLockTime:查询一段时间之后没有释放的库存[商城、海鹏、基地库]出现异常");
            log.error("查询一段时间之后没有释放的库存[商城、海鹏、基地库]出现异常:", e);
        }
        return result;

    }

}
