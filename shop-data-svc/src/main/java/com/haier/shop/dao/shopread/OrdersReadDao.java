package com.haier.shop.dao.shopread;

import com.haier.shop.model.Orders;
import com.haier.shop.model.OrdersVo;
import com.haier.shop.model.QueryOrderParameter;
import com.haier.shop.model.WorkOrderBean;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface OrdersReadDao {

  Orders get(@Param("id") Integer id);

  Orders getByOrderSn(String orderSn);

  WorkOrderBean getOrderByOrderSn(String orderSn);

  Map getRegionByOrderSn(String orderSn);

  /**
   * 订单查询
   */
  List<QueryOrderParameter> getFindQueryOrderList(QueryOrderParameter queryOrder);

  /**
   * 查询订单总数
   */
  Integer getFindQueryOrderListCount(QueryOrderParameter queryOrder);

  /**
   * 查询推送给VOM的数据
   */
  OrdersVo queryVOMTransMission(String id);


  OrdersVo queryRepairVOMInfo(String id);

  /**
   *
   * @param id
   * @return
   */
  OrdersVo queryb2cVOM(String id);

    /**
     *
     * @param sCode
     * @return
     */
  Map queryFiveYard(String sCode);

    /**
     *
     * @param id
     * @return
     */
  Map queryMinHpRecordId(String id);

  /**
   * 根据网单id查询订单信息
   * @param productId
   * @return
   */
  Orders  selectOrderView(String productId);



  int getRowCnts();

  Orders getBySourceOrderSn(@Param("sourceOrderSn") String sourceOrderSn);

  Orders getIdAndOhterByOrderSn(String orderSn);

  Orders getByRelationOrderSn(@Param("connectOrderNum") String connectOrderNum);
}
