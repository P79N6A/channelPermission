package com.haier.stock.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.stock.model.*;

import java.util.List;
import java.util.Map;

/**
 * Created by 胡万来 on 2018/1/23 0023.
 */
public interface InvBaseStockMgtService {

    JSONObject getBaseStockList(PagerInfo pager, InvBaseStock condition);

    JSONArray getAllProductTypes();

    JSONObject getMachineBaseStockList(PagerInfo pager, InvBaseStock invBaseStock);

    List<InvBaseStock> exportBaseStockList(InvBaseStock condition);

    List<InvBaseStock> exportBaseStockListByCondition(InvBaseStock condition,Integer start,Integer size);

    List<InvBaseStock> exportMachineBaseStockList(InvBaseStock condition);

    List<InvBaseStock> exportMachineBaseStockListByContion(InvBaseStock condition,Integer start,Integer size);

    JSONObject getInvBaseStockLogList(PagerInfo pager, InvBaseStockLog invBaseStockLog);

    List<InvBaseStockLog> exportBaseStockLogList(InvBaseStockLog condition);

    JSONObject getStoreList(PagerInfo pager, InvStore invStore);

    List<InvStore> exportStoreList(InvStore condition);

    JSONObject getMachineSetMgtList(PagerInfo pager, InvMachineSet invMachineSet);

    String updateSubSku(String sku, String status, String currentUser);

    JSONObject getStockAreaList(PagerInfo pager, StockArea stockArea);

    JSONArray queryAllProvince();

    Map<Integer,String> queryAllCityByProvId(Integer provinceCode);

    Map<Integer,String> getAllRegionByCityId(Integer city_code);

    JSONArray getChannels();
}
