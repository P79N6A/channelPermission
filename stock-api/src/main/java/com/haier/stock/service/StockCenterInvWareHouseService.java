package com.haier.stock.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.model.InvWarehouseInfo;
import com.haier.stock.model.PopInvWarehouse;

import java.util.List;
import java.util.Map;

/**
 * Created by 胡万来 on 2018/1/19 0019.
 */
public interface StockCenterInvWareHouseService {
    JSONObject invSectionList(PagerInfo pager, InvSection condition);


    String addInvSection(InvSection condition);

    String addInvWarehouse(PopInvWarehouse condition);

    JSONObject invWarehouseList(PagerInfo pager, PopInvWarehouse condition);

    int removeInvWarehouse(String id);

    Map<String, String> getChannelMap(Map<String, String> invstockchannelmap);

    ServiceResult<Map<String,Integer>> insertInvSections(List<InvSection> invSections);

    ServiceResult<Map<String,Integer>> insertInvWarehouses(List<PopInvWarehouse> invWarehouses);

    long checkInvSectionByCode(String code);

    long checkInvWarehouseByCode(String code);

    long checkInvWarehouseByName(String name);

    List<InvSection> exportSection(InvSection condition);

    List<PopInvWarehouse> exportInvWarehouse(PopInvWarehouse condition);

    int removeInvSection(String id);

    List<InvStockChannel> getChannelIdList();

    List<InvWarehouse> findCenter();

}
