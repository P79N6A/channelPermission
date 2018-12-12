package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderQueueExtend;
import com.haier.shop.model.OrdersNew;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderQueueExtendReadDao {


    /**
     * 查询扩展订单列表
     * @return
     */
    public List<OrderQueueExtend> queryOrderQueueExtList(OrderQueueExtend orderQueueExtend);

    /**
     * 查询出库未同步财务
     * @return
     */
    public List<OrderQueueExtend> queryOrderOutList(@Param("financeStatus") int financeStatus);

    /**
     * 查询第三方出库未同步财务
     * @param financeStatus 同步财务状态
     * @param source 第三方来源
     * @param status 主流程状态（大于等于）
     * @return
     */
    public List<OrderQueueExtend> queryThirdPartyOrderOutList(@Param("financeStatus") int financeStatus,
                                                              @Param("source") String source, @Param("status") int status);

    /**
     * 查询发票列表
     * @return
     */
    public List<OrderQueueExtend> queryInvoiceList(@Param("invoiceStatus") int invoiceStatus);


    public OrderQueueExtend getLastOrderSnByOrderSource(@Param("source") String orderSource);

    public List<OrderQueueExtend> getByCOrderSnAndOrderSource(@Param("cordersn") String cordersn,
                                                              @Param("source") String orderSource);
    OrdersNew getOrderById(@Param("id") int id);
    String getMemberMobile(@Param("id") int id);
}
