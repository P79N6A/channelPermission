package com.haier.shop.dao.shopwrite;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.shop.model.OrderProductsNew;

// TODO 与 OrderProducts 模型冲突
public interface OrderProductsNewDao {
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
    List<OrderProductsNew> getByCOrderSnList(@Param("snList") List<String> snList);
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
    
    List<OrderProductsNew> getLockStockExceptionOpList(@Param("id") Integer id);
    
    
    /**
     * 根据订单id列表，获取网单列表
     * @param orderIds 订单id列表
     * @return
     */
    List<OrderProductsNew> getByOrderIds(@Param("orderIds") List<Integer> orderIds);
    
    List<OrderProductsNew> getByOrderId(@Param("orderId") Integer orderId);
    
    /**
     * 完成关闭网单
     * @param id
     * @return
     */
    Integer completeClose(@Param("id") Integer id, @Param("closeTime") Long closeTime);
    
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
    List<OrderProductsNew> getByOrderIdsForConfirm(@Param("orderIds") List<Integer> orderIds);
    
    /**
     * 更新网单状态
     * @param id
     * @param status
     * @return
     */
    Integer updateStatus(@Param("id") Integer id, @Param("status") Integer status);
    
    /**
     * 根据网单编号，获取网单信息
     * @param cOrderSn
     * @return
     */
    OrderProductsNew getByCOrderSn(@Param("cOrderSn") String cOrderSn);

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
    Integer getStorageStreetsTimeoutFreeByStreetId(@Param("streetId") Integer streetId);

    /**
     * 根据区县ID，获取是否超时免单区县
     * @param regionId
     * @return
     */
    Integer getStorageCitiesTimeoutFreeByRegionId(@Param("regionId") Integer regionId);

    /**
     * 更新超时免单
     * @param id
     * @return
     */
    Integer updateIsTimeoutFree(@Param("id") Integer id,
                                @Param("isTimeoutFree") Integer isTimeoutFree);
    List<OrderProductsNew> getByTbNo(@Param("tbNo") String tbNo);
    int updateOpModify(@Param("opId") Integer opId);

    
    /**
     * 强制取消网单
     * @param id
     * @param closeTime
     * @return
     */
    Integer forceCancelClose(@Param("id") Integer id, @Param("closeTime") Long closeTime);
    
    int update(OrderProductsNew orderProduct);
    

    /**
     * 根据网单id列表，获取网单列表
     * @param ids id列表
     * @return
     */
    List<OrderProductsNew> getByIds(@Param("ids") List<Integer> ids);
    
    

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

}
