package com.haier.shop.services;

import java.util.List;

import com.haier.shop.dao.shopwrite.OrdersWriteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrdersReadDao;
import com.haier.shop.model.Orders;
import com.haier.shop.model.OrdersVo;
import com.haier.shop.model.QueryOrderParameter;
import com.haier.shop.service.ShopOrdersService;
@Service
public class ShopOrdersServiceImpl implements ShopOrdersService {

    @Autowired
    OrdersReadDao ordersReadDao;
    @Autowired
    OrdersWriteDao ordersWriteDao;

    /**
     * 订单查询
     * @param queryOrder
     * @return
     */
    public List<QueryOrderParameter> getFindQueryOrderList(QueryOrderParameter queryOrder){
        return ordersReadDao.getFindQueryOrderList(queryOrder);
    }

    /**
     * 查询订单总数
     * @param queryOrder
     * @return
     */
    public Integer getFindQueryOrderListCount(QueryOrderParameter queryOrder){
        return ordersReadDao.getFindQueryOrderListCount(queryOrder);
    }

    /**
     * 查询推送给VOM的数据
     * @param id
     * @return
     */
    public OrdersVo queryVOMTransMission(String id){
        return ordersReadDao.queryVOMTransMission(id);
    }

    @Override
    public Orders get(Integer id) {
        return ordersReadDao.get(id);
    }

    @Override
    public Orders getByOrderSn(String orderSn) {
        return ordersReadDao.getByOrderSn(orderSn);
    }

    @Override
    public int updateSmConfirmStatus(Orders orders) {
        return ordersWriteDao.updateSmConfirmStatus(orders);
    }
}
