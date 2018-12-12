package com.haier.shop.dao.shopread;

import com.haier.shop.model.Orders;
import com.haier.shop.model.OrdersVo;
import com.haier.shop.model.QueryOrderParameter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface OrdersReadDao {

    Orders get(@Param("id") Integer id);

    Orders getByOrderSn(String orderSn);

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
    public OrdersVo queryVOMTransMission(String id);//
}
