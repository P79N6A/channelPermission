package com.haier.stock.model;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.Stock;
import com.haier.shop.model.StorageCities;
import com.haier.stock.service.StockCenterEStoreService;
import com.haier.stock.service.StockCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 * 库存管理     业务处理层                 
 * @Filename: StoreModel.java
 * @Version: 1.0
 * @Author: guanmi 官蜜
 * @Email: genemax1107@aliyun.com
 *
 */
@Service
public class StoreModel {
//    private InvStoreDao        invStoreDao;
    @Autowired
    private StockCommonService stockCommonService;
    @Autowired
    private StockCenterEStoreService eStoreService;

    /**
     * 按区域查询
     * @param sku 物料编码
     * @param regionId 区县ID
     * @param requireQty 数量
     * @return
     */
    public Stock getStockBySkuAndRegion(String sku, int regionId, int requireQty, String channelCode) {

        ServiceResult<Stock> ret = eStoreService.getStockBySkuAndRegionTimely(sku, regionId,
            channelCode, requireQty, true);
        if (!ret.getSuccess()) {
            throw new BusinessException("利用StoreModel根据物料编码[" + sku + "],区县编码[" + regionId
                                        + "],数量[" + requireQty + "],渠道编码[" + channelCode
                                        + "]取得区域库存信息时发生异常"
                                        + (ret == null ? "" : ":" + ret.getMessage()));
        }
        return ret.getResult();
    }

    /**
     * 查询渠道列表详细信息
     * @return
     */
    public List<InvStockChannel> getChannels() {
        ServiceResult<List<InvStockChannel>> ret = stockCommonService.getChannels();
        if (!ret.getSuccess()) {
            throw new BusinessException("利用StoreModel获取渠道列表时发生异常");
        }
        return ret.getResult();
    }

    /**
     * 查询所有省份信息
     * @return
     */
    public Map<Integer, String> queryAllProvince() {
        ServiceResult<Map<Integer, String>> ret = stockCommonService.getAllProvince();
        if (!ret.getSuccess()) {
            throw new BusinessException("利用StoreModel获取省份信息时发生异常");
        }
        return ret.getResult();
    }

    /**
     * 查询目标省内城市信息 
     * @param provinceCode  省份编码
     * @return
     */
    public Map<Integer, String> queryAllCityByProvId(Integer provinceCode) {
        ServiceResult<Map<Integer, String>> ret = stockCommonService
            .getAllCityByProvId(provinceCode);
        if (!ret.getSuccess()) {
            throw new BusinessException("利用StoreModel根据省份编码[" + provinceCode + "]获取城市信息时发生异常");
        }
        return ret.getResult();
    }

    /**
     * 查询目标市内区县信息
     * @param regionCode 城市编码
     * @return
     */
    public Map<Integer, String> getAllRegionByCityId(Integer regionCode) {
        ServiceResult<Map<Integer, String>> ret = stockCommonService
            .getAllRegionByCityId(regionCode);
        if (!ret.getSuccess()) {
            throw new BusinessException("利用StoreModel根据城市编码[" + regionCode + "]获取区县信息时发生异常");
        }
        return ret.getResult();
    }

    /**
     * 查询地区详细信息
     * @param provinceId 省份ID
     * @param cityId 城市ID
     * @param regionId 区县ID
     * @return
     */
    public StorageCities getStorageCityByIds(Integer provinceId, Integer cityId, Integer regionId) {
        ServiceResult<StorageCities> ret = stockCommonService.getStorageCityByIds(provinceId,
            cityId, regionId);
        if (!ret.getSuccess()) {
            throw new BusinessException("利用StoreModel根据省级编码[" + provinceId + "],城市编码[" + cityId
                                        + "],区县编码[" + regionId + "]获取区县信息时发生异常");
        }
        return ret.getResult();
    }

    /**
     * 条件查询
     * @param paramMap
     * @return
     */
//    public List<Map<String, Object>> queryInvStoreList(Map<String, Object> paramMap) {
//        return invStoreDao.queryInvStoreList(paramMap);
//    }

    /**
     * 条件查询总数
     * @param paramMap
     * @return
     */
//    public int getStoreRowListCount(Map<String, Object> paramMap) {
//        return invStoreDao.getStoreRowListCount(paramMap);
//    }

    /**
     * 处理参数内店铺码数据
     * @param paramMap
     * @return
     */
    public Map<String, Object> dealParamsMap(Map<String, Object> paramMap) {
        StringBuffer storeCodes = new StringBuffer();
        if (paramMap.get("storeCode") != null
            && !StringUtil.isEmpty(paramMap.get("storeCode").toString(), true)) {
            paramMap.put("storeCode", new String[] { paramMap.get("storeCode").toString() });
            return paramMap;
        }
        if ((Integer) paramMap.get("regionCode") > 0) {
            ServiceResult<List<String>> result = eStoreService
                .getStoreCodeByRegionId((Integer) paramMap.get("regionCode"));
            if (!result.getSuccess()) {
                throw new BusinessException("利用StoreModel根据区县编码[" + paramMap.get("regionCode")
                                            + "]获取店铺码信息时发生异常");
            }
            if (result.getResult() != null && !result.getResult().isEmpty()) {
                for (String storeCode : result.getResult()) {
                    storeCodes.append(storeCode).append(",");
                }
            }
        }
        if (storeCodes.length() > 0) {
            paramMap.put("storeCode", storeCodes.toString().split(","));
        }

        return paramMap;
    }

    public void setStockCommonService(StockCommonService stockCommonService) {
        this.stockCommonService = stockCommonService;
    }

//    public void setInvStoreDao(InvStoreDao invStoreDao) {
//        this.invStoreDao = invStoreDao;
//    }

//    public void seteStoreService(EStoreService eStoreService) {
//        this.eStoreService = eStoreService;
//    }

}
