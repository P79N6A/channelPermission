package com.haier.logistics.services;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.ServiceResult;
import com.haier.logistics.Util.DateFormatUtil;
import com.haier.logistics.Util.SmsTemplateConst;
import com.haier.logistics.service.ItemService;
import com.haier.logistics.service.OrderRebackService;
import com.haier.logistics.service.OrderService;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.Orders;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.SmsLogs;
import com.haier.shop.service.HPDispatchWriteService;
import com.haier.shop.service.InvoicesService;
import com.haier.shop.service.OrderQueueExtendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* 作者:张波
* 2017/12/25
*/
@Service
public class OrderRebackServiceImpl implements OrderRebackService {
    @Autowired
    private InvoicesService invoiceDao;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderQueueExtendService orderQueueExtendService;
    @Autowired
    private HPDispatchWriteService hPDispatchWriteDao;
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(OrderRebackServiceImpl.class);
    /**
     * 根据id获得OrderProducts信息
     * @param
     * @return OrderProducts信息
     */
    public OrderProductsNew getOrderProductsByCOrderSn(String cOrderSn) {
        OrderProductsNew orderProduct = null;
        try {
            orderProduct = invoiceDao.getOrderProductsByCOrderSn(cOrderSn);
        } catch (Exception e) {
            log.error("[OrderRebackodel_getOrderProductsByCOrderSn]根据cOrderSn获得OrderProducts信息:"
                    + e.getMessage());
        }
        return orderProduct;
    }
    /**
     * 保存saveHpReservationDateRelation日志信息
     * @param orderProducts
     */
    public boolean saveHpReservationDateRelation(OrderProductsNew orderProducts, String remark,
                                                 String changeLog) {
        // 更新预约送货时间
        ServiceResult<Boolean> result = orderService.saveHpReservationDateRelation(orderProducts,
                remark, changeLog);
        if (!result.getSuccess()) {
            log.error(
                    "[OrderRebackodel][saveHpReservationDateRelation]Error:" + result.getMessage());
            return false;
        }
        return true;
    }
    /**
     * 发送短信
     * @param orderProducts
     */
    public int sendSms(OrderProductsNew orderProducts) {
        int flag = 0;
        try {
            Integer mysqlTime = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
            SmsLogs smsLogs = new SmsLogs();
            OrdersNew orders = orderQueueExtendService.getOrderById(orderProducts.getOrderId());
            if (null != orders) {
                //如果手机号为空,是会员的话取会员表里手机号
                if (StringUtils.isBlank(orders.getMobile()) && orders.getMemberId() > 0) {
                    orders.setMobile(orderQueueExtendService.getMemberMobile(orders.getMemberId()));
                }
                if (!StringUtils.isBlank(orders.getMobile())) {
                    String mobile = orders.getMobile().replace(" ", "").replace("-", "");
                    if (StringUtils.isBlank(mobile) || mobile.length() > 11) {
                        return 0;
                    }
                    mobile = mobile.trim();
                    String smsMsg = this.constructSmsMsg(orders, orderProducts);
                    smsLogs.setMobile(mobile);
                    smsLogs.setName(mobile);
                    smsLogs.setMessage(smsMsg);
                    smsLogs.setAddTime(mysqlTime);
                    smsLogs.setIsSuccess(0);//0-成功 1-失败
                    smsLogs.setPriority(254);
                    smsLogs.setLastTime(0);
                    smsLogs.setTryNum(0);
                    smsLogs.setSiteId(1);
                    log.error("SmsLog组装信息,Priority【" + smsLogs.getPriority() + "】: 预约时间【"
                            + orderProducts.getHpReservationDate() + "】");
                    flag = hPDispatchWriteDao.insertSmsLogs(smsLogs);
                }
            } else {
                log.error("确认订单发短信失败,不存在对应订单ID【" + orderProducts.getOrderId() + "】:");
            }
        } catch (Exception e) {
            log.error("确认订单发短信失败,网单号【" + orderProducts.getCOrderSn() + "】:", e);
        }
        return flag;
    }
    /**
     * 组装短信内容
     * @param orders
     * @param orderProducts
     * @return
     */
    public String constructSmsMsg(OrdersNew orders, OrderProductsNew orderProducts) {
        //        StringBuilder sb = new StringBuilder();
        //        if (orders.getSource() != null && orders.getSource().equalsIgnoreCase("MSTORE")) {
        //            sb.append("【顺逛微店】");
        //        } else {
        //            sb.append("【海尔商城】");
        //        }
        //        sb.append(orders.getConsignee() + "您好，您购买的\"");
        Integer typeId = orderProducts.getProductType();
        String typeName = "";
        if (typeId.intValue() > 0) {
            ServiceResult<ProductTypesNew> result = itemService.getProductType(typeId);
            if (result.getSuccess() && null != result.getResult()) {
                typeName = result.getResult().getTypeName();
            }
        }
        if (StringUtils.isBlank(typeName)) {
            typeName = orderProducts.getSku();
        }
        //        sb.append(typeName + "\"预约送货时间修改为"
        //                  + DateFormatUtil.formatTime(orderProducts.getHpReservationDate()));
        //        sb.append("。如有需要请联系在线客服！");

        if (orders.getSource() != null && orders.getSource().equalsIgnoreCase("MSTORE")) {
            return SmsTemplateConst.HopReturn_ReservationDate_MSTORE
                    .replaceAll("#consignee#", orders.getConsignee()).replaceAll("#typeName#", typeName)
                    .replaceAll("#hpReservationDate#",
                            DateFormatUtil.formatTime(orderProducts.getHpReservationDate()));
        } else {
            return SmsTemplateConst.HopReturn_ReservationDate_HESC
                    .replaceAll("#consignee#", orders.getConsignee()).replaceAll("#typeName#", typeName)
                    .replaceAll("#hpReservationDate#",
                            DateFormatUtil.formatTime(orderProducts.getHpReservationDate()));
        }
        //        return sb.toString();
    }
}
