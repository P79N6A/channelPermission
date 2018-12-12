package com.haier.logistics.service;

import com.haier.common.ServiceResult;
import com.haier.shop.model.HpSignTimeInterface;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderProductsAttributes;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderRepairsNew;
import com.haier.shop.model.OrderShippedQueue;
import com.haier.shop.model.Orders;
import com.haier.shop.model.OrdersNew;

import java.util.Date;
import java.util.List;

public interface OrderService {
    /**
     * 根据编号列表，获取网单列表
     * @param snList 网单编号列表
     * @return
     */
    ServiceResult<List<OrderProductsNew>> getOrderLineBySnList(List<String> snList);
    /**
     * 退货单 待检入库（包括存性变更）
     * @param cOrderSn 记录单号
     * @param lesIoNo 入库单号
     * @return
     */
    ServiceResult<Boolean> repairOrderIn(String cOrderSn, String lesIoNo);
    /**
     * LES出入库后，商城相关处理
     * @param order 订单
     * @param orderProduct 网单
     * @param iType 出入库类型
     * @param iType 出入库类型
     * @param lesNo 出入库凭证号
     * @param lesShipTime 出入库时间
     * @param expressNo 快递单号
     * @param expressCompony 快递公司
     * @return 是否处理成功
     */
    ServiceResult<Boolean> processAfterLesShipped(OrdersNew order, OrderProductsNew orderProduct,
                                                  Integer iType, String lesNo, Date lesShipTime,
                                                  String expressNo, String expressCompony);
    /**
     * 获取待处理的出库队列
     * @param topX 前x行
     * @return 出库队列
     */
    ServiceResult<List<OrderShippedQueue>> getOrderShippedQueueForProcess(Integer topX);
    /**
     * 根据id列表，获取订单列表
     * @param ids id列表
     * @return
     */
    ServiceResult<List<OrdersNew>> getOrderByIds(List<Integer> ids);
    /**
     * 处理结束后，删除指定的出库队列
     * @param id 出库队列id
     * @return 影响行数
     */
    ServiceResult<Integer> deleteOrderShippedQueue(Integer id);
    /**
     * 根据id列表，获取网单列表
     * @param ids
     * @return
     */
    ServiceResult<List<OrderProductsNew>> getOrderProductsByIds(List<Integer> ids);
    /**
     * 根据网单编号，获取网单信息
     * @param cOrderSn 网单编号
     * @return
     */
    ServiceResult<OrderProductsNew> getOrderProductByCOrderSn(String cOrderSn);
    /**
     * 退货单 Vom接单
     * @param corderSn
     * @param expNo
     * @param acceptTime
     * @return
     */
    ServiceResult<Boolean> repairAfterVomAccepted(String corderSn, String expNo, Date acceptTime);
    /**
     * 退货单 VOM据单
     * @param corderSn
     * @param reason
     * @param acceptTime
     * @return
     */
    ServiceResult<Boolean> repairAfterVomRejected(String corderSn, String reason, Date acceptTime);
    /**
     * 接受hp回传时间
     * @param //orderSn
     * @param //
     * @param //type
     * @return
     */
    ServiceResult<String> acceptTimeFromHp(HpSignTimeInterface hpSignTimeInterface);
    /**
     * 保存saveHpReservationDateRelation信息
     * @param orderProduct
     * @return
     */
    ServiceResult<Boolean> saveHpReservationDateRelation(OrderProductsNew orderProduct, String remark,
                                                         String changeLog);
    /**
     * 根据主键，获取订单对象
     * @param orderId 订单id
     * @return
     */
    ServiceResult<OrdersNew> getOrder(Integer orderId);
    /**
     * 更新开提单les回传状态信息
     * @param orderProductId 网单id
     * @param statusFlag  成功标志，false失败，true成功
     */
    ServiceResult<Boolean> updateLesStatusToOrder(Integer orderProductId, boolean statusFlag);
    /**
     * 完成关闭订单
     * @param corderSn
     * @param signTime
     * @return
     */
    ServiceResult<Boolean> completeCloseOrderProduct(String corderSn, Date signTime);
    /**
     * 更新订单全流程表的到达网点时间，如果此时间已经赋值，则不再更新
     * @param orderProductId
     * @param arriveTime
     * @return
     */
    ServiceResult<Boolean> updateOrderWorkflowNetPointArriveTime(Integer orderProductId,
                                                                 Date arriveTime);

}
