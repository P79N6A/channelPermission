package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.OrderQueueExtend;
import com.haier.shop.model.OrdersNew;


public interface OrderQueueExtendService {

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
    public Integer updateSapStatus(  String cOrderSn,  int status,
                                    String sapMsg);

    /**
     * 更新发票状态
     * @param cOrderSn
     * @param status
     * @param invoiceMsg
     * @return
     */
    public Integer updateInvoiceStatus(  String cOrderSn,
                                        int status,
                                         String invoiceMsg);

    /**
     * 新增扩展订单队列
     * @param orderQueueExtend
     * @return
     */
    public Integer insert(OrderQueueExtend orderQueueExtend);

    /**
     * 查询扩展订单列表
     * @return
     */
    public List<OrderQueueExtend> queryOrderQueueExtList(OrderQueueExtend orderQueueExtend);

    /**
     * 查询出库未同步财务
     * @return
     */
    public List<OrderQueueExtend> queryOrderOutList(  int financeStatus);

    /**
     * 查询第三方出库未同步财务
     * @param financeStatus 同步财务状态
     * @param source 第三方来源
     * @param status 主流程状态（大于等于）
     * @return
     */
    public List<OrderQueueExtend> queryThirdPartyOrderOutList(  int financeStatus,
    		 String source, int status);

    /**
     * 查询发票列表
     * @return
     */
    public List<OrderQueueExtend> queryInvoiceList( int invoiceStatus);

    /**
     * 取消网单
     * @param orderSn
     * @return
     */
    public Integer cancelOrderExt( String orderSn,
                                    Integer cancelStatus);

    public OrderQueueExtend getLastOrderSnByOrderSource( String orderSource);

    public List<OrderQueueExtend> getByCOrderSnAndOrderSource(  String cordersn,
                                                                String orderSource);
    OrdersNew getOrderById( int id);
    String getMemberMobile( int id);
}
