package com.haier.shop.service;


import com.haier.shop.model.Orders;
import com.haier.shop.model.OrdersVo;
import com.haier.shop.model.QueryOrderParameter;

import java.util.List;

public interface ShopOrdersService {

    /**
     * 订单查询
     * @param queryOrder
     * @return
     */
    public List<QueryOrderParameter> getFindQueryOrderList(QueryOrderParameter queryOrder);
    
    /**
     * 查询订单总数
     * @param queryOrder
     * @return
     */
    public Integer getFindQueryOrderListCount(QueryOrderParameter queryOrder);
    
    /**
     * 查询推送给VOM的数据
     * @param id
     * @return
     */
    public OrdersVo queryVOMTransMission(String id);

    /**
     * 根据id获取订单对象
     * @param id
     * @return
     */
    Orders get(Integer id);

    /**
     * 根据网单号查询网单信息
     * @param orderSn
     * @return
     */
    Orders getByOrderSn(String orderSn);

    /**
     * 更新订单标建确认状态
     * @param orders
     * @return
     */
    int updateSmConfirmStatus(Orders orders);
}
