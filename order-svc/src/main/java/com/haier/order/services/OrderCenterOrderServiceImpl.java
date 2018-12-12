package com.haier.order.services;

import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.order.model.OrderModel;
import com.haier.order.model.OrderRepairModel;
import com.haier.shop.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderCenterOrderServiceImpl {
    private static Logger log = LoggerFactory.getLogger(OrderCenterOrderServiceImpl.class);
    @Autowired
    private OrderModel orderModel;
    @Autowired
    private OrderRepairModel orderRepairModel;

    //    @Autowired
//    private OrderProductsNewService orderProductsNewDao;
//    @Autowired
//    private OrdersNewService ordersNewService;
//    @Autowired
//    private ShopOrderWorkflowsService orderWorkflowsDao;

    /**
     * 根据编号列表，获取网单列表
     *
     * @param snList 网单编号列表
     * @return
     */
    public ServiceResult<List<OrderProductsNew>> getOrderLineBySnList(List<String> snList) {
        ServiceResult<List<OrderProductsNew>> result = new ServiceResult<List<OrderProductsNew>>();
        try {
            return orderModel.getOrderLineBySnList(snList);
        } catch (Exception e) {
            String sns = "";
            if (snList != null) {
                for (String s : snList) {
                    if (!StringUtil.isEmpty(sns)) {
                        sns += ";";
                    }
                    sns += s;
                }
            }
            log.error("根据网单编号列表(" + sns + ")获取网单列表时，出现未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<List<OrdersNew>> getOrderByIds(List<Integer> ids) {
        ServiceResult<List<OrdersNew>> result = new ServiceResult<List<OrdersNew>>();
        if (ids == null || ids.size() == 0 || ids.size() > 1000) {
            result.setSuccess(false);
            result.setMessage("ids不能为空，且数量不能大于1000");
            return result;
        }

        try {
            result.setResult(orderModel.getOrderByIds(ids));
        } catch (Exception e) {
            String idList = "";
            if (ids.size() > 0) {
                for (Integer id : ids) {
                    if (!StringUtil.isEmpty(idList)) {
                        idList += ",";
                    }
                    idList += id;
                }
            }
            log.error("根据ids(" + idList + ")获取订单时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<Boolean> repairOrderIn(String cOrderSn, String lesIoNo) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result = orderRepairModel.repairOrderIn(cOrderSn, lesIoNo);
        } catch (Exception e) {
            log.error("退货单待检入库(cOrderSn:" + cOrderSn + ",lesIoNo:" + lesIoNo + ")，出现未知异常:", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<Boolean> processAfterLesShipped(OrdersNew order, OrderProductsNew orderProduct,
                                                         Integer iType, String lesNo,
                                                         Date lesShipTime, String expressNo,
                                                         String expressCompony) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        if (order == null || orderProduct == null) {
            result.setMessage("订单或者网单为null");
            result.setResult(false);
            return result;
        }
        try {
            result = orderModel.processAfterLesShipped(order, orderProduct, iType, lesNo,
                    lesShipTime, expressNo, expressCompony, null);
        } catch (Exception e) {
            log.error("LES出入库后，商城相关处理(lesShipTime:" + lesShipTime + ",lesNo:" + lesNo
                    + ",orderProductId:" + orderProduct.getId() + ",iType:" + iType
                    + ",expressNo:" + expressNo + ",expressCompany:" + expressCompony
                    + ")，出现未知异常:", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<OrderProductsNew> getOrderProductByCOrderSn(String cOrderSn) {
        ServiceResult<OrderProductsNew> result = new ServiceResult<OrderProductsNew>();
        try {
            result.setResult(orderModel.getOrderProductByCOrderSn(cOrderSn));
        } catch (Exception e) {
            log.error("获取网单信息(cOrderSn:" + cOrderSn + ")时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<Boolean> repairAfterVomAccepted(String corderSn, String expNo,
                                                         Date acceptTime) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(orderRepairModel.updateAfterVomAccepted(corderSn, acceptTime, expNo));
        } catch (Exception e) {
            log.error("退货单-VOM接单后处理失败：", e);
            result.setResult(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    public ServiceResult<Boolean> repairAfterVomRejected(String corderSn, String reason,
                                                         Date rejectedTime) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(orderRepairModel
                    .updateAfterVomRejected(corderSn, rejectedTime, reason));
        } catch (Exception e) {
            log.error("退货单-VOM据单后处理失败：", e);
            result.setResult(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 更新网单预约时间
     *
     * @param orderProduct
     * @return
     */

    public ServiceResult<Boolean> saveHpReservationDateRelation(OrderProductsNew orderProduct,
                                                                String remark, String changeLog) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(orderModel.saveHpReservationDateRelation(orderProduct, remark,
                    changeLog));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setResult(null);
            result.setMessage("更新网单预约时间失败");
            result.setSuccess(false);
            log.error("[saveHpReservationDateRelation]更新网单预约时间异常", e);
        }
        return result;
    }

    public ServiceResult<OrderProductsAttributes> getByOrderProductId(Integer orderProductId) {
        ServiceResult<OrderProductsAttributes> result = new ServiceResult<OrderProductsAttributes>();
        try {
            result.setResult(orderModel.getByOrderProductId(orderProductId));
            result.setSuccess(true);
        } catch (Exception e) {
            result.setResult(null);
            result.setSuccess(false);
            result.setMessage("根据网单号获取网单扩展属性失败");
            log.error("根据网单号获取网单扩展属性失败", e);
        }
        return result;
    }

    public ServiceResult<OrdersNew> getByOrderSn(String orderSn) {
        ServiceResult<OrdersNew> result = new ServiceResult<OrdersNew>();
        try {
            result.setResult(orderModel.getByOrderSn(orderSn));
        } catch (Exception e) {
            log.error("根据orderSn(" + orderSn + ")获取订单时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<OrderRepairsNew> getValidByOrderProductId(Integer orderProductId) {
        ServiceResult<OrderRepairsNew> result = new ServiceResult<OrderRepairsNew>();
        try {
            result.setResult(orderModel.getValidByOrderProductId(orderProductId));
        } catch (Exception e) {
            log.error("获取指定网单的有效退货单时，出现未知异常:", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<List<OrderRepairsNew>> getOrderRepairsByOrderProductId(Integer orderProductId) {
        ServiceResult<List<OrderRepairsNew>> result = new ServiceResult<List<OrderRepairsNew>>();
        try {
            result.setResult(orderModel.getOrderRepairsByOrderProductId(orderProductId));
        } catch (Exception e) {
            log.error("获取指定网单的退货单时，出现未知异常:", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<OrdersNew> getOrderById(Integer id) {
        ServiceResult<OrdersNew> result = new ServiceResult<OrdersNew>();
        try {
            result.setResult(orderModel.getOrderById(id));
        } catch (Exception e) {
            log.error("根据id(" + id + ")获取订单时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<String> acceptTimeFromHp(HpSignTimeInterface hpSignTimeInterface) {
        ServiceResult<String> result = new ServiceResult<String>();
        String mes[] = new String[1];
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            boolean suc = true;
            switch (hpSignTimeInterface.getStatus()) {
                case 1:
                    suc = orderModel.updateOrderWorkflowNetPointAcceptTime(
                            hpSignTimeInterface.getOrderSn(),
                            format.parse(hpSignTimeInterface.getStatusTime()), mes);
                    result.setSuccess(suc);
                    result.setResult("网点接单." + (suc ? "成功" : "失败:" + mes[0]));
                    break;
                case 2:
                    suc = orderModel.updateOrderWorkflowNetPointShipTime(
                            hpSignTimeInterface.getOrderSn(),
                            format.parse(hpSignTimeInterface.getStatusTime()), mes);
                    result.setSuccess(suc);
                    result.setResult("网点出库." + (suc ? "成功" : "失败:" + mes[0]));
                    break;
                case 3:
                    suc = orderModel.updateOrderWorkflowUserAcceptTime(hpSignTimeInterface, mes);
                    result.setSuccess(suc);
                    result.setResult("用户签收." + (suc ? "成功" : "失败:" + mes[0]));
                    if (!suc) {
                        orderModel.insertErrorHpSignTimeInterface(hpSignTimeInterface);
                    }
                    break;
                default:
                    result.setResult("商城无用标识");
                    result.setSuccess(false);
                    log.error("[acceptTimeFromHp]不支持此状态的接受, type=="
                            + hpSignTimeInterface.getStatus() + ", orderSn=="
                            + hpSignTimeInterface.getOrderSn());
            }
        } catch (Exception e) {
            result.setResult("失败, orderSn==" + hpSignTimeInterface.getOrderSn());
            result.setMessage("根据网单编号接收hp回传时间失败 ，orderSn=" + hpSignTimeInterface.getOrderSn());
            result.setSuccess(false);
            log.error("[acceptTimeFromHp]处理HP回传网点接单，网点出库，用户签收数据异常", e);
        }
        return result;
    }

    public ServiceResult<List<OrderShippedQueue>> getOrderShippedQueueForProcess(Integer topX) {
        ServiceResult<List<OrderShippedQueue>> result = new ServiceResult<List<OrderShippedQueue>>();
        try {
            result.setResult(orderModel.getOrderShippedQueueForProcess(topX));
            System.out.println(orderModel.getOrderShippedQueueForProcess(topX).size());
        } catch (Exception e) {
            log.error("获取待处理的出库队列时(" + topX + ")，出现未知异常:", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<List<OrderProductsNew>> getOrderProductsByIds(List<Integer> ids) {
        ServiceResult<List<OrderProductsNew>> result = new ServiceResult<List<OrderProductsNew>>();
        if (ids == null || ids.size() == 0 || ids.size() > 1000) {
            result.setSuccess(false);
            result.setMessage("ids不能为空，且数量不能大于1000");
            return result;
        }

        try {
            result.setResult(orderModel.getOrderProductsByIds(ids));
        } catch (Exception e) {
            String idList = "";
            if (ids.size() > 0) {
                for (Integer id : ids) {
                    if (!StringUtil.isEmpty(idList)) {
                        idList += ",";
                    }
                    idList += id;
                }
            }
            log.error("根据ids(" + idList + ")获取网单时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    public ServiceResult<Integer> deleteOrderShippedQueue(Integer id) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(orderModel.deleteOrderShippedQueue(id));
        } catch (Exception e) {
            log.error("根据指定id(" + id + ")删除出库队列时，出现未知异常:", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
}
