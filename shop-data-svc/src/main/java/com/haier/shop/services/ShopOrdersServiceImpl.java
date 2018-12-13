package com.haier.shop.services;

import java.util.List;
import java.util.Map;

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

    public OrdersVo queryRepairVOMInfo(String id){
        return ordersReadDao.queryRepairVOMInfo(id);
    }

    public OrdersVo queryb2cVOM(String id){
        return ordersReadDao.queryb2cVOM(id);
    }

    /**
     *
     * @param sCode
     * @return
     */
    public Map queryFiveYard(String sCode){
        return ordersReadDao.queryFiveYard(sCode);
    }

    /**
     *
     * @param id
     * @return
     */
    public Map queryMinHpRecordId(String id){
        return ordersReadDao.queryMinHpRecordId(id);

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

    /**
     * 根据订单号查询地区国标码
     * @param orderSn
     * @return
     */
    @Override
    public Map getRegionByOrderSn(String orderSn){
        return ordersReadDao.getRegionByOrderSn(orderSn);
    }
	@Override
	public Orders selectOrderView(String productId) {
		// TODO Auto-generated method stub
		return ordersReadDao.selectOrderView(productId);
	}

	@Override
	public int insertOrders(Orders orders) {
		// TODO Auto-generated method stub
		ordersWriteDao.insertOrdersCopy(orders);
		return orders.getId();
	}

    @Override
    public int updataOrdersStatus(String id) {
        return ordersWriteDao.updataOrdersStatus(id);
    }

    @Override
    public  int getRowCnts(){
        return ordersReadDao.getRowCnts();
    }

    @Override
    public Orders getBySourceOrderSn(String sourceOrderSn) {
        return ordersReadDao.getBySourceOrderSn(sourceOrderSn);
    }

    @Override
    public Integer updateMemberinvoicesId(Integer orderId,Integer memberinvoicesId) {
        return ordersWriteDao.updateMemberinvoicesId(orderId,memberinvoicesId);
    }
    @Override
    public Orders getIdAndOhterByOrderSn(String orderSn) {
        return ordersReadDao.getIdAndOhterByOrderSn(orderSn);
    }

    @Override
    public Orders getOrderByRelationOrderSn(String connectOrderNum) {
        return ordersReadDao.getByRelationOrderSn(connectOrderNum);
    }
}
