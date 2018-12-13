package com.haier.shop.service;


import com.haier.shop.model.Orders;
import com.haier.shop.model.OrdersVo;
import com.haier.shop.model.QueryOrderParameter;

import java.util.List;
import java.util.Map;

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

    public OrdersVo queryRepairVOMInfo(String id);

    /**
     *
     * @param id
     * @return
     */
    public OrdersVo queryb2cVOM(String id);

    /**
     *
     * @param sCode
     * @return
     */
    public Map queryFiveYard(String sCode);

    /**
     *
     * @param id
     * @return
     */
    public Map queryMinHpRecordId(String id);
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

    /**
     * 根据订单号查询地区国标码
     * @param orderSn
     * @return
     */
    public Map getRegionByOrderSn(String orderSn);
    /**
     * 根据网单id查询订单信息
     * @param productId
     * @return
     */
    Orders  selectOrderView(String productId);
    
    int insertOrders(Orders orders);//插入信息到订单

    int updataOrdersStatus(String id);

    int getRowCnts();

    Orders getBySourceOrderSn(String sourceOrderSn);

    Integer updateMemberinvoicesId(Integer orderId,Integer memberinvoicesId);

    Orders getIdAndOhterByOrderSn(String orderSn);

    Orders getOrderByRelationOrderSn(String connectOrderNum);
}
