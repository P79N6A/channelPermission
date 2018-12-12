package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderQueueExtend;
import com.haier.shop.model.OrdersNew;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderQueueExtendWriteDao {

    /**
     * 更新扩展订单队列
     * @param orderQueueExtend
     * @return
     */
    public Integer update(OrderQueueExtend orderQueueExtend);

    /**
     * 更新财务状态
     * @param status
     * @return
     */
    public Integer updateSapStatus(@Param("cOrderSn") String cOrderSn, @Param("status") int status,
                                   @Param("sapMessage") String sapMsg);

    /**
     * 更新发票状态
     * @param cOrderSn
     * @param status
     * @param invoiceMsg
     * @return
     */
    public Integer updateInvoiceStatus(@Param("cOrderSn") String cOrderSn,
                                       @Param("status") int status,
                                       @Param("invoiceMessage") String invoiceMsg);

    /**
     * 新增扩展订单队列
     * @param orderQueueExtend
     * @return
     */
    public Integer insert(OrderQueueExtend orderQueueExtend);


    /**
     * 查询发票列表
     * @return
     */
    public List<OrderQueueExtend> queryInvoiceList(@Param("invoiceStatus") int invoiceStatus);

    /**
     * 取消网单
     * @param orderSn
     * @return
     */
    public Integer cancelOrderExt(@Param("orderSn") String orderSn,
                                  @Param("cancelStatus") Integer cancelStatus);

}
