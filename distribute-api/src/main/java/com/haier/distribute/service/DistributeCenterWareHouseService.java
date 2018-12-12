package com.haier.distribute.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.distribute.data.model.*;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.PopInvWarehouse;

import java.util.List;
import java.util.Map;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/16 0016
 * \* Time: 9:17
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public interface DistributeCenterWareHouseService {
    JSONObject wareHouseList(PagerInfo pager, TWarehouse condition);

    String addWarehouse(TWarehouse tWarehouse,String username);

    List<TChannelsInfo> getChannelIdList();

    int removeWarehouse(Integer id);


    JSONObject twRegionList(PagerInfo pager, TWarehouseRegion condition);

    List<TWarehouse> getTWarehouseList();

    int addTwRegion(int channelId, List<String> regionId, int warehouseId, String remark);

    int updateTwRegion(int id, int channelId, int regionId, int warehouseId, String remark);

    int removeTwRegion(Integer id);

    List<TWarehouse> getWareHouseServiceStart();

    JSONObject SaleStockList(PagerInfo pager, TSaleProductStock condition);

    String addSaleStock(TSaleProductStock tSaleProductStock);

    int removeSaleStock(Integer id);

    int getRegionType(int regionId);

    List<Map<String, Object>> autoLoadPid(Integer channelId, Integer id);

    Regions geParentId(Integer cid);

    List<Regions> getRegionsAll();

}