package com.haier.stock.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.*;
import com.haier.shop.service.ShopItemAttributeService;
import com.haier.stock.model.*;
import com.haier.stock.service.InvBaseStockMgtService;
import com.haier.stock.service.StockInvBaseStockService;
import com.haier.stock.service.StockInvMachineSetService;
import com.haier.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.haier.stock.model.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 胡万来 on 2018/1/23 0023.
 */
@Service
public class InvBaseStockMgtServiceImpl implements InvBaseStockMgtService {

    @Autowired
    StockInvBaseStockService stockInvBaseStockService;

    @Autowired
    ShopItemAttributeService shopItemAttributeService;

    @Autowired
    StockInvMachineSetService stockInvMachineSetService;
    @Autowired
    StockService stockService;
    @Autowired
    StoreModel storeModel;

    @Override
    public JSONObject getBaseStockList(PagerInfo pager, InvBaseStock condition) {

        List<InvBaseStock> list = stockInvBaseStockService.getPageByCondition(condition,
                pager.getStart(), pager.getPageSize());
        long total = stockInvBaseStockService.getPagerCount(condition);

        return jsonResult(list, total);
    }

    @Override
    public JSONArray getAllProductTypes() {
        List<ItemAttribute> itemAttributes = shopItemAttributeService.getProductTypes();

        JSONArray res = new JSONArray();

        for (ItemAttribute i : itemAttributes) {
            JSONObject json = new JSONObject();
            json.put("text", i.getCbsCategory());
            json.put("value", i.getCbsCategory());
            res.add(json);
        }
        return res;
    }

    @Override
    public JSONObject getMachineBaseStockList(PagerInfo pager, InvBaseStock invBaseStock) {
        List<InvBaseStock> list = new ArrayList<InvBaseStock>();
        long total = 0;
        String itemProperty = invBaseStock.getStockItemProperty();
        if (InvSection.W10.equals(itemProperty) || StringUtil.isEmpty(itemProperty)) {
            list = stockInvBaseStockService.getMachinePageByCondition(invBaseStock,
                    pager.getStart(), pager.getPageSize());
            total = stockInvBaseStockService.getMachinePagerCount(invBaseStock);
        }

        return jsonResult(list, total);
    }

    @Override
    public List<InvBaseStock> exportBaseStockList(InvBaseStock condition) {
        return stockInvBaseStockService.getPageByCondition(condition,
            0, Integer.MAX_VALUE);
    }

    @Override
    public List<InvBaseStock> exportBaseStockListByCondition(InvBaseStock condition,Integer start,Integer size) {
        return stockInvBaseStockService.getPageByCondition(condition,
            start, size);
    }

    @Override
    public List<InvBaseStock> exportMachineBaseStockList(InvBaseStock condition) {
        List<InvBaseStock> list = new ArrayList<InvBaseStock>();
        String itemProperty = condition.getStockItemProperty();
        if (InvSection.W10.equals(itemProperty) || StringUtil.isEmpty(itemProperty)) {
            list = stockInvBaseStockService.getMachinePageByCondition(condition,
                    0, Integer.MAX_VALUE);
        }
        return list;
    }
    @Override
    public List<InvBaseStock> exportMachineBaseStockListByContion(InvBaseStock condition,Integer start,Integer size) {
        List<InvBaseStock> list = new ArrayList<InvBaseStock>();
        String itemProperty = condition.getStockItemProperty();
        if (InvSection.W10.equals(itemProperty) || StringUtil.isEmpty(itemProperty)) {
            list = stockInvBaseStockService.getMachinePageByCondition(condition,
                    start, size);
        }
        return list;
    }

    @Override
    public JSONObject getInvBaseStockLogList(PagerInfo pager, InvBaseStockLog condition) {
        List<InvBaseStockLog> list = stockInvBaseStockService.getLogPageByCondition(condition,
                pager.getStart(), pager.getPageSize());
        long total = stockInvBaseStockService.getLogPagerCount(condition);

        return jsonResult(list, total);
    }

    @Override
    public List<InvBaseStockLog> exportBaseStockLogList(InvBaseStockLog condition) {
        return stockInvBaseStockService.getLogPageByCondition(condition,
                0, Integer.MAX_VALUE);
    }

    @Override
    public JSONObject getStoreList(PagerInfo pager, InvStore condition) {
        List<InvStore> list = stockInvBaseStockService.getStorePageByCondition(condition,
                pager.getStart(), pager.getPageSize());
        long total = stockInvBaseStockService.getStorePagerCount(condition);

        return jsonResult(list, total);
    }

    @Override
    public List<InvStore> exportStoreList(InvStore condition) {
        return stockInvBaseStockService.getStorePageByCondition(condition,
                0, Integer.MAX_VALUE);
    }

    @Override
    public JSONObject getMachineSetMgtList(PagerInfo pager, InvMachineSet condition) {
        List<InvMachineSet> list = stockInvMachineSetService.getPageByCondition(condition,
                pager.getStart(), pager.getPageSize());
        long total = stockInvMachineSetService.getPagerCount(condition);

        for (InvMachineSet invMachineSet : list) {
            if (0 == invMachineSet.getIsSaleSub()) {
                invMachineSet.setOperation("<a onclick=\"enableSubSku('" + 1 + "," + invMachineSet.getSubSku() + "')\" >启用子件销售</a>");
            } else if (1 == invMachineSet.getIsSaleSub())
                invMachineSet.setOperation("<a onclick=\"enableSubSku('" + 0 + "," + invMachineSet.getSubSku() + "')\" >停用子件销售</a>");
        }
        return jsonResult(list, total);
    }

    @Override
    public String updateSubSku( String sku,String status, String currentUser) {

        Integer s = status == null || "".equals(status) ? null : Integer.valueOf(status);
        if (s != InvMachineSet.STATUS_CANCEL_SALE_SUB_MACHINE
                && s != InvMachineSet.STATUS_SALE_SUB_MACHINE) {
            return "不支持的销售状态";
        }

        Integer rows = stockInvMachineSetService.updateSubSku(sku, s, currentUser);
        if (rows <= 0) {
            return s == InvMachineSet.STATUS_CANCEL_SALE_SUB_MACHINE ? "取消子件销售不成功"
                    : (s == InvMachineSet.STATUS_SALE_SUB_MACHINE ? "添加子件销售失败" : "不支持的状态");
        }
        ServiceResult<Boolean> result = null;
        if (s == InvMachineSet.STATUS_CANCEL_SALE_SUB_MACHINE) {
            result = stockService.deleteSaleSubMachine(sku);
        } else if (s == InvMachineSet.STATUS_SALE_SUB_MACHINE) {
            result = stockService.saleSubMachine(sku);
        }
        return result.getMessage();
    }

    @Override
    public JSONObject getStockAreaList(PagerInfo pager, StockArea stockArea) {
        List<StockArea> list = new ArrayList<>();
        StockArea sa = new StockArea();
        Stock stock = storeModel.getStockBySkuAndRegion(stockArea.getSku(), stockArea.getRegionCode(), 0, stockArea.getChannelCode());
        if (stock != null) {
            StorageCities storageCities = storeModel.getStorageCityByIds(stockArea.getProvinceCode(), stockArea.getCityCode(), stockArea.getRegionCode());
            if (null != storageCities) {
                sa.setCityCode(storageCities.getCityId());
                sa.setRegionCode(storageCities.getRegionId());
                sa.setCityName(storageCities.getCityName());
                sa.setRegionName(storageCities.getRegionName());
            }
        }else {
            return jsonResult(null, 0);
        }

        sa.setStockType(stock.getStockType() == null ? "" : stock.getStockType());
        sa.setSecCode(stock.getSecCode() == null ? "" : stock.getSecCode());
        sa.setSku(stock.getSku() == null ? "" : stock.getSku());
        sa.setAvaibleQty(stock.getAvaibleQty());
        sa.setUpdateTime(stock.getUpdateTime());
        list.add(sa);
        if (stock.getSku() == null) {
            return jsonResult(null, 0);
        } else {
            return jsonResult(list, 1);
        }
    }

    @Override
    public JSONArray queryAllProvince() {
        Map<Integer, String> map = storeModel.queryAllProvince();

        JSONArray res = new JSONArray();
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            JSONObject json = new JSONObject();
            json.put("text", entry.getValue());
            json.put("value", entry.getKey());
            res.add(json);
        }
        return res;
    }

    @Override
    public Map<Integer, String> queryAllCityByProvId(Integer provinceCode) {
        return storeModel.queryAllCityByProvId(provinceCode);
    }

    @Override
    public Map<Integer, String> getAllRegionByCityId(Integer city_code) {
        return storeModel.getAllRegionByCityId(city_code);
    }

    @Override
    public JSONArray getChannels() {

        List<InvStockChannel> list = storeModel.getChannels();
        JSONArray res = new JSONArray();
        for (InvStockChannel i : list) {
            JSONObject json = new JSONObject();
            json.put("value", i.getChannelCode());
            json.put("text", i.getName());
            res.add(json);
        }
        return res;
    }

    private <T> JSONObject jsonResult(List<T> list, long total) {
        JSONObject json = new JSONObject();
        json.put("total", total);
        if (list == null || list.isEmpty()) {
            json.put("rows", new ArrayList<T>());
        } else {
            json.put("rows", list);
        }
        return json;
    }
}
