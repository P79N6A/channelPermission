package com.haier.logistics.services;

import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.logistics.Model.LesToOrderModel;
import com.haier.logistics.Model.OrderModel;
import com.haier.logistics.Model.OrderRepairModel;
import com.haier.logistics.service.OrderService;
import com.haier.shop.model.HpSignTimeInterface;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderShippedQueue;
import com.haier.shop.model.Orders;
import com.haier.shop.model.OrdersNew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderModel orderModel;
    @Autowired
    private OrderRepairModel orderRepairModel;
    @Autowired
    private LesToOrderModel lesToOrderModel;
    @Override
    /**
     * 根据编号列表，获取网单列表
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
    @Override
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
    @Override
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
    @Override
    public ServiceResult<List<OrderShippedQueue>> getOrderShippedQueueForProcess(Integer topX) {
        ServiceResult<List<OrderShippedQueue>> result = new ServiceResult<List<OrderShippedQueue>>();
        try {
            result.setResult(orderModel.getOrderShippedQueueForProcess(topX));
        } catch (Exception e) {
            log.error("获取待处理的出库队列时(" + topX + ")，出现未知异常:", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    @Override
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
    @Override
    public ServiceResult<Integer> deleteOrderShippedQueue(Integer id){
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
    @Override
    public ServiceResult<List<OrderProductsNew>> getOrderProductsByIds(List<Integer> ids){
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    public ServiceResult<String> acceptTimeFromHp(HpSignTimeInterface hpSignTimeInterface){
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

    /**
     * 更新网单预约时间
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
    @Override
    public ServiceResult<OrdersNew> getOrder(Integer orderId) {
        ServiceResult<OrdersNew> result = new ServiceResult<OrdersNew>();
        try {
            result.setResult(orderModel.getOrder(orderId));
        } catch (Exception e) {
            log.error("获取订单信息(orderId:" + orderId + ")时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    @Override
    public ServiceResult<Boolean> updateLesStatusToOrder(Integer orderProductId, boolean statusFlag){
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            int n = lesToOrderModel.updateLesStatusToOrder(orderProductId, statusFlag);
            if (n == 0) {
                result.setResult(true);
                return result;
            } else if (n == -100) {
                result.setResult(false);
                result.setSuccess(false);
                result.setMessage("参数数据错误，网单id为空或为0");
                return result;
            } else if (n == -101) {
                result.setResult(false);
                result.setSuccess(false);
                result.setMessage("VOM开提单更新返回状态信息时出错");
                return result;
            } else if (n == -102) {
                result.setResult(false);
                result.setSuccess(false);
                result.setMessage("参数错误，数据为空或网单id为空");
                return result;
            } else if (n == -103) {
                result.setResult(false);
                result.setSuccess(false);
                result.setMessage("网单id" + orderProductId + "不存在");
                return result;
            } else if (n == -104) {
                result.setResult(false);
                result.setSuccess(false);
                result.setMessage("订单不存在");
                return result;
            } else if (n == -105) {//订单取消，不在更新网单状态信息
                result.setResult(true);
                return result;
            } else if (n == -106) {//网单取消，不在更新网单状态信息
                result.setResult(true);
                return result;
            } else {
                result.setResult(false);
                result.setSuccess(false);
                result.setMessage("更新开提单状态数据异常");
                return result;
            }

        } catch (Exception e) {
            log.error("VOM开提单回传状态信息时发生未知异常:" + e.getMessage());
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage("VOM开提单回传状态信息时发生未知异常:" + e.getMessage());
            return result;
        }
    }
    @Override
    public ServiceResult<Boolean> completeCloseOrderProduct(String corderSn, Date signTime) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(orderModel.completeCloseOrderProduct(corderSn, signTime));
        } catch (Exception ex) {
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage(ex.getMessage());
            log.error("完成关闭网单失败：", ex);
        }
        return result;
    }
    @Override
    public ServiceResult<Boolean> updateOrderWorkflowNetPointArriveTime(Integer orderProductId,
                                                                        Date arriveTime) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            result.setResult(orderModel.updateOrderWorkflowNetPointArriveTime(orderProductId,
                    arriveTime));
        } catch (Exception ex) {
            log.error("更新orderWorkFlow netPointArriveTime 失败：", ex);
            result.setResult(false);
            result.setSuccess(false);
            result.setMessage(ex.getMessage());
        }
        return result;
    }
}
