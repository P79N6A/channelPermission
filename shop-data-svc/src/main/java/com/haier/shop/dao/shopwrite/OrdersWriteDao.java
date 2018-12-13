package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrdersWriteDao {

    /**
     * 更新订单标建确认状态
     * @param orders
     * @return
     */
    int updateSmConfirmStatus(Orders orders);
    int findOrderNum(@Param("begintime")String begintime,@Param("endtime")String endtime);//查询订单的数量
    int updataOrdersStatus(String id);
    int insertOrders(Orders orders);//插入信息到订单
    int insertOrdersCopy(Orders orders);
    Integer updateMemberinvoicesId(@Param("orderId") Integer orderId,@Param("memberinvoicesId")Integer memberinvoicesId);
}
