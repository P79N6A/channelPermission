package com.haier.shop.service;


import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderProductsNew;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface OrderProductsNewService {
    /**
     * 根据主键，获取网单对象
     * @param id
     * @return
     */
    OrderProductsNew get(Integer id);
    /**
     * 根据网单号列表，获取网单列表
     * @param snList
     * @return
     */
    List<OrderProductsNew> getByCOrderSnList(  List<String> snList);
    /**
     * 网单出库后，更新网单相关信息【3W】
     * @param orderProducts
     * @return
     */
    Integer updateAfterDelivery3W(OrderProductsNew orderProducts);
    /**
     * 网单出库后，更新网单相关信息
     * @param orderProducts
     * @return
     */
    Integer updateAfterDelivery(OrderProductsNew orderProducts);
    Integer updateAfterTransferFirstOut(OrderProductsNew orderProducts);
    Integer updateAfterTransferIn(OrderProductsNew orderProducts);


    /**
     * 开提单传les，还未返回状态信息时，即更新开提单字段=网单号
     * @param orderProduct 网单信息
     * @return
     */
    int updateSyncLes(OrderProductsNew orderProduct);



    List<OrderProductsNew> getUnLockStockOpList();

    List<OrderProductsNew> getLockStockExceptionOpList(  Integer id);


    /**
     * 根据订单id列表，获取网单列表
     * @param orderIds 订单id列表
     * @return
     */
    List<OrderProductsNew> getByOrderIds( List<Integer> orderIds);

    List<OrderProductsNew> getByOrderId(  Integer orderId);

    /**
     * 完成关闭网单
     * @param id
     * @return
     */
    Integer completeClose(  Integer id,   Long closeTime);

    /**
     * 冻结库存更新网单
     * @param orderProduct
     * @return
     */
    int updateForFrozenStock(OrderProductsNew orderProduct);

    /**
     * 确认订单根据订单id列表，获取网单列表
     * @param orderIds 订单id列表
     * @return
     */
    List<OrderProductsNew> getByOrderIdsForConfirm(  List<Integer> orderIds);

    /**
     * 更新网单状态
     * @param id
     * @param status
     * @return
     */
    Integer updateStatus( Integer id,  Integer status);

    /**
     * 根据网单编号，获取网单信息
     * @param cOrderSn
     * @return
     */
    OrderProductsNew getByCOrderSn(  String cOrderSn);

    /**
     * 更新网单物流模式
     */
    Integer updateShippingModeById(OrderProductsNew orderProduct);

    /**
     * 开票后更新网单开票状态
     * @param orderProduct 网单信息
     * @return
     */
    int updateAfterCreateInvoice(OrderProductsNew orderProduct);


    Integer updateOpHpReservationDate(OrderProductsNew orderProduct);

    /**
     * 分配开票类型更新
     * @param orderProduct
     * @return
     */
    int updateForMakeReceiptType(OrderProductsNew orderProduct);

    /**
     * 根据街道ID，获取是否超时免单街道
     * @param streetId
     * @return
     */
    Integer getStorageStreetsTimeoutFreeByStreetId(  Integer streetId);

    /**
     * 根据区县ID，获取是否超时免单区县
     * @param regionId
     * @return
     */
    Integer getStorageCitiesTimeoutFreeByRegionId( Integer regionId);

    /**
     * 更新超时免单
     * @param id
     * @return
     */
    Integer updateIsTimeoutFree(  Integer id,
                                  Integer isTimeoutFree);
    List<OrderProductsNew> getByTbNo( String tbNo);
    int updateOpModify( Integer opId);


    /**
     * 强制取消网单
     * @param id
     * @param closeTime
     * @return
     */
    Integer forceCancelClose(  Integer id,   Long closeTime);

    int update(OrderProductsNew orderProduct);


    /**
     * 根据网单id列表，获取网单列表
     * @param ids id列表
     * @return
     */
    List<OrderProductsNew> getByIds( List<Integer> ids);



    /**
     * 更新开票类型
     * @param orderProduct
     * @return
     */
    int updateMakeReceiptType(OrderProductsNew orderProduct);

    int updateForsyncInvoice(OrderProductsNew orderProduct);

    Integer updateHPAllotNetPoint(OrderProductsNew orderProduct);
    /**
     * 开提单后更新返回状态信息
     * @param orderProduct 网单信息
     * @return
     */
    int updateAfterSyncLes(OrderProductsNew orderProduct);
	int insert(OrderProducts OrderProducts);
	int interceptCancelClose(Integer id, Long closeTime);//取消原网单并更改拦截状态

    void updateCOrderSn(OrderProducts orderProducts);
    
    /**
     * 根据OrderID查询
     * @param id
     * @return
     */
    OrderProductsNew getOrderId(Integer id);

    /**
     * 查询占用库存失败的数据
     * @param params
     * @return
     */
    List<OrderProductsNew> queryOccupyStockFail(Map<String, Object> params);

    /**
     * 查询占用库存失败的数据条数
     * @param params
     * @return
     */
    Integer queryOccupyStockFailCount(Map<String, Object> params);

    /**
     * 根据订单修改网单付款状态
     *
     * @param orderId
     * @param cPaymentStatus
     * @return
     */
     Integer updatePaymentStatusByOrderId(Integer orderId, Integer cPaymentStatus);

    int updateNum(Long newNum, BigDecimal productAmount,String orderProductId);

    int updatesCodeBycOrderSn(String sCode ,String cOrderSn);

    /**
     * 根据日日单状态获取网单列表
     * @param pdOrderStatus 日日单状态
     * @return
     */
    List<OrderProductsNew> getByPdOrderStatusPaging(Integer pdOrderStatus, Integer minId, Integer size);

    /**
     * 更新日日单信息,包括日日单状态和集团OMS单号
     * @param orderProducts
     * @return
     */
    Integer updateRRSById(OrderProductsNew orderProducts);

    /**
     * 根据订单ID 获取订单下未占用库存成功的网单数量
     * @param orderId
     * @return
     */
    Integer getSuccessNum(Integer orderId);
}
