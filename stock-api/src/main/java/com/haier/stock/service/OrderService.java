package com.haier.stock.service;

import java.util.Date;
import java.util.List;

import com.haier.common.ServiceResult;
import com.haier.shop.model.HpSignTimeInterface;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderProductsAttributes;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderRepairsNew;
import com.haier.shop.model.OrderShippedQueue;
import com.haier.shop.model.OrdersNew;
public interface OrderService {
    /**
     * 根据编号列表，获取网单列表
     * @param snList 网单编号列表
     * @return
     */
    ServiceResult<List<OrderProductsNew>> getOrderLineBySnList(List<String> snList);
    /**
     * 根据id列表，获取订单列表
     * @param ids id列表
     * @return
     */
    ServiceResult<List<OrdersNew>> getOrderByIds(List<Integer> ids);
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
     * 根据id获取订单对象
     * @param id
     * @return
     */
    ServiceResult<OrdersNew> getOrderById(Integer id);
    
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
     * 保存saveHpReservationDateRelation信息
     * @param orderProduct
     * @return
     */
    ServiceResult<Boolean> saveHpReservationDateRelation(OrderProductsNew orderProduct, String remark,
                                                         String changeLog);

    /**
     * 接受hp回传时间
     * @param //orderSn
     * @param //
     * @param //type
     * @return
     */
    ServiceResult<String> acceptTimeFromHp(HpSignTimeInterface hpSignTimeInterface);

    
    /**
     * 根据网单id获取网单扩展属性
     * @param orderProductId
     * @return
     */
    ServiceResult<OrderProductsAttributes> getByOrderProductId(Integer orderProductId);
    
    /**
     * 根据网单号获取订单对象
     * @param orderSn
     * @return
     */
    ServiceResult<OrdersNew> getByOrderSn(String orderSn);
    
    /**
     * 获取指定网单的有效退货单
     * @param orderProductId
     * @return
     */
    ServiceResult<OrderRepairsNew> getValidByOrderProductId(Integer orderProductId);
    /**
     * 获取指定网单的退货单
     * @param orderProductId
     * @return
     */
    ServiceResult<List<OrderRepairsNew>> getOrderRepairsByOrderProductId(Integer orderProductId);
    /**
     * 获取待处理的出库队列
     * @param topX 前x行
     * @return 出库队列
     */
    ServiceResult<List<OrderShippedQueue>> getOrderShippedQueueForProcess(Integer topX);

    /**
     * 根据id列表，获取网单列表
     * @param ids
     * @return
     */
    ServiceResult<List<OrderProductsNew>> getOrderProductsByIds(List<Integer> ids);
    /**
     * 处理结束后，删除指定的出库队列
     * @param id 出库队列id
     * @return 影响行数
     */
    ServiceResult<Integer> deleteOrderShippedQueue(Integer id);
    
    /**
     * 根据网单编号，获取网单信息
     * @param cOrderSn 网单编号
     * @return
     */
//    ServiceResult<OrderProducts> getOrderProductByCOrderSn(String cOrderSn);

    /**
     * 根据日日单状态获取网单列表
     * @param pdOrderStatus 日日单状态
     * @return
     */
    ServiceResult<List<OrderProductsNew>> getByPdOrderStatusPaging(Integer pdOrderStatus,
        Integer minId, Integer size);

    /**
     * 根据主键，获取订单对象
     * @param orderId 订单id
     * @return
     */
    ServiceResult<OrdersNew> getOrder(Integer orderId);

    /**
     * 根据网单id更新日日单信息
     * @param orderProducts
     * @return
     */
    ServiceResult<Boolean> updateRRSById(OrderProductsNew orderProducts);
}
