package com.haier.shop.service;


import com.haier.shop.model.OrderProducts;

import com.haier.shop.model.OrderProductsNew;
import java.util.List;
import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
public interface ShopOrderProductsService {

    /**
     * 根据ID获取网单信息
     * @param orderProductId
     * @return
     */
    OrderProducts get(Integer orderProductId);

    /**
     * 根据订单编号，获取订单id
     * @param cOrderSn
     * @return
     */
    Integer getOrderIdByCOrderSn(String cOrderSn);

    /**
     * 根据订单编号，获取网单信息
     * @param cOrderSn
     * @return
     */
    OrderProducts getByCOrderSn(String cOrderSn);

    /**
     * 更新网单发票信息
     * @param orderProduct
     * @return
     */
    Integer updateForsyncInvoice(OrderProducts orderProduct);

    /**
     * 更新开票类型
     * @param orderProduct
     * @return
     */
    Integer updateMakeReceiptType(OrderProducts orderProduct);

    /**
     * 根据OrderId获得OrderProducts信息
     * @param orderId
     * @return OrderProducts信息
     */
    List<OrderProducts> getOrderProductsByOrderId(Integer orderId);

    /**
     * 根据网单id列表，获取网单列表
     * @param ids
     * @return
     */
    List<OrderProducts> getByIds(List<Integer> ids);

    /**
     * 根据cOrderSn批量获取网单ID
     * @param cOrderSnsMap
     * @return
     */
    List<Integer> getOrderProductIdsInfo(Map<String, Object> cOrderSnsMap);

    /**
     * 根据id获取OrderId
     * @param id
     * @return
     */
    Integer getOrderIdById(Integer id);

    /**
     * 批量查询网单数量，界面展示
     * @param paramMap
     * @return
     */
    Integer getOpListByCOrderSnCount(Map<String, Object> paramMap);

    /**
     *  获取网单信息列表List[普通网单]
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getOpListByCOrderSnSearch(Map<String, Object> paramMap);

    /**
     *  获取[导出网单信息]列表List[普通网单]
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getOpListByCOrderSn(Map<String, Object> paramMap);

    /**
     * 根据tb单号查询网单信息
     * @param tbOrderSn
     * @return
     * @throws Exception
     */
    OrderProducts getOrderProductsByTbNo(String tbOrderSn) throws Exception;

    Integer updateCorderSnById(Integer oPid, String cOrderSn);

    OrderProducts findOPBycOrderSnAndSku(String cOrderSn, String sku);
}
