package com.haier.stock.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.shop.model.EStoreResponseMsg;
import com.haier.shop.model.EStoreStock;
import com.haier.stock.service.StockCenterEStoreInfoService;
import com.haier.stock.util.HttpServiceUtil;
@Service
public class StockCenterEStoreInfoServiceImpl implements StockCenterEStoreInfoService {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                   .getLogger(StockCenterEStoreInfoServiceImpl.class);

    private String                         estoreStockUrl;

    public ServiceResult<EStoreStock> getByStoreCode(String storeCode, String sku,
                                                     String itemProperty) {

        ServiceResult<EStoreStock> baseResult = new ServiceResult<EStoreStock>();
        if (StringUtil.isEmpty(sku)) {
            baseResult.setSuccess(false);
            baseResult.setResult(null);
            return baseResult;
        }
        if (!"10".equals(itemProperty)) {
            baseResult.setSuccess(false);
            baseResult.setResult(null);
            baseResult.setMessage("不支持的批次编码itemProperty=" + itemProperty);
            return baseResult;
        }
        ServiceResult<List<EStoreStock>> resultList = this.getEStoreStock(storeCode, sku,
            itemProperty);
        if (resultList.getSuccess()) {
            List<EStoreStock> entityList = resultList.getResult();
            EStoreStock entity = entityList.isEmpty() ? null : entityList.get(0);
            baseResult.setResult(entity);
            baseResult.setSuccess(true);
        }
        return baseResult;
    }

    @Override
    public ServiceResult<List<EStoreStock>> getEStoreStock(String storeCode, String sku,
                                                           String itemProperty) {

        ServiceResult<List<EStoreStock>> result = new ServiceResult<List<EStoreStock>>();
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("signId", "ehaier");//调用方签名
            params.put("franchiserId", storeCode);//经销商编码（主账户8码）
            params.put("itemNo", sku);//商品编号
            String content = JsonUtil.toJson(params);
            ServiceResult<String> res = HttpServiceUtil.executeService(storeCode,
                EisInterfaceDataLog.INTERFACE_QUERY_ESTORE_STOCK, content, estoreStockUrl);
            if (res == null || !res.getSuccess()) {
                result.setResult(null);
                result.setSuccess(false);
                result.setMessage("通过HTTP查询EC库存失败");
                return result;
            }
            log.info("查询EC库存返回的结果为：\n" + res.getResult());
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            EStoreResponseMsg responseMsg = gson.fromJson(res.getResult(),
                new TypeToken<EStoreResponseMsg>() {
                }.getType());
            if (responseMsg == null) {
                result.setResult(null);
                result.setSuccess(false);
                result.setMessage("查询EC库存返回空");
                return result;
            }
            List<EStoreStock> eStoreStocks = responseMsg.getItems();
            result.setResult(eStoreStocks);
            result.setSuccess(true);
            result.setMessage("查询EC库存成功");
        } catch (Exception e) {
            result.setResult(null);
            result.setSuccess(false);
            result.setMessage("查询EC库存出现异常");
            log.error("查询EC库存出现异常：" + e);
        }
        return result;
    }

    public void setEstoreStockUrl(String estoreStockUrl) {
        this.estoreStockUrl = estoreStockUrl;
    }

}
