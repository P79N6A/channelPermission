package com.haier.stock.service;


import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.distribute.data.model.Regions;
import com.haier.distribute.data.model.TChannelsInfo;
import com.haier.distribute.data.model.TSaleProductStock;
import com.haier.distribute.data.model.TWarehouse;
import com.haier.distribute.data.model.TWarehouseRegion;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.PopInvWarehouse;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/16 0016
 * \* Time: 9:17
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public interface StockWareHouseService {
    JSONObject wareHouseList(PagerInfo pager, TWarehouse condition);

    String addWarehouse(TWarehouse tWarehouse);

    List<TChannelsInfo> getChannelIdList();

    int removeWarehouse(Integer id);
    
    int removeInvSection(String id);

    JSONObject twRegionList(PagerInfo pager, TWarehouseRegion condition);

    List<TWarehouse> getTWarehouseList();

    int addTwRegion(int channelId, List<String> regionId, int warehouseId,String remark);

    int updateTwRegion(int id, int channelId, int regionId,int warehouseId, String remark);

    int removeTwRegion(Integer id);

    List<TWarehouse> getWareHouseServiceStart();

    JSONObject SaleStockList(PagerInfo pager,TSaleProductStock condition);

    String addSaleStock(TSaleProductStock tSaleProductStock);

    int removeSaleStock(Integer id);

    int getRegionType(int regionId);

    List<Map<String, Object>> autoLoadPid(Integer channelId, Integer id);

    Regions geParentId(Integer cid);

    List<Regions> getRegionsAll();

    JSONObject invSectionList(PagerInfo pager,InvSection condition);
    
    String addInvSection(InvSection condition);

    String addInvWarehouse(PopInvWarehouse condition);

    JSONObject invWarehouseList(PagerInfo pager,PopInvWarehouse condition);

    int removeInvWarehouse(String id);

    Map<String, String> getChannelMap(Map<String, String> invstockchannelmap);

    ServiceResult<Map<String,Integer>> insertInvSections(List<InvSection> invSections);

    ServiceResult<Map<String,Integer>> insertInvWarehouses(List<PopInvWarehouse> invWarehouses);

    long checkInvSectionByCode(String code);

    long checkInvWarehouseByCode(String code);

    long checkInvWarehouseByName(String name);

    List<InvSection> exportSection(InvSection condition);

    List<PopInvWarehouse> exportInvWarehouse(PopInvWarehouse condition);
}