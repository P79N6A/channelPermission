package com.haier.distribute.service;/**
 * Created by Administrator on 2017/11/7 0007.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.PopOrderProducts;
import com.haier.distribute.data.model.PopOrders;
import com.haier.distribute.data.model.TChannelsInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/7 0007
 * \* Time: 9:19
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public interface DistributeCenterPopOrderService {

    JSONObject productList(PagerInfo pager, PopOrders condition);

    JSONArray getSource();

    JSONArray getSysDictionaryByType(String type);

    PopOrders getOneByCondition(PopOrders condition);

    JSONObject commodityList(Long orderId);

    JSONObject historyList(String orderSn);

    String orderOpertion(String orderStatus, String Id, String oId, BigDecimal price, String expressNo, String thisStatus);

    int cancelOpertion(String cancelStatus, String orderId, BigDecimal price);

    int editRemark(String orderSn, String codConfirmRemark);

    String editOid(String orderSn, String oid);

    String editExpressNo(String orderSn, String expressNo);

    int editOrigin(String orderSn, String consignee, String mobile, String originRegionName, String originAddress);

    JSONObject orderProductList(PagerInfo pager, PopOrderProducts orderProducts);
    PopOrderProducts getOneByOrderProducts(PopOrderProducts op);

    JSONObject channelList(PagerInfo pager, TChannelsInfo tChanneclsInfo);

    String addChannel(TChannelsInfo tChanneclsInfo);

    int removeChannel(Long id);
    
    List<PopOrders> exportOrderList(PopOrders po);
    
    List<PopOrderProducts> exportOrderProductsList(PopOrderProducts pop);

    int finishToCancel(String orderSn);
}
