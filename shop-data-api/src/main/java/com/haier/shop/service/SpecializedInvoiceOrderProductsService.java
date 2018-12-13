package com.haier.shop.service;

import com.haier.common.ServiceResult;
import com.haier.shop.model.OrderProductsVo;

import java.util.List;
import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/6/6 15:21
 */
public interface SpecializedInvoiceOrderProductsService {

    /**
     * 网单列表(分页查询)
     *
     * @param params
     * @return
     */
    ServiceResult<List<OrderProductsVo>> getSpecializedInvoiceOrderProductsList(OrderProductsVo params);

    /**
     *导出网单列表查询
     * @param
     * @return
     */
    List<Map<String,Object>> getListByParams(OrderProductsVo params);

    /**
     * 查询 明细 （网单）
     * @param cOrderSn
     * @return
     */
    OrderProductsVo getOrderOroductsDetail(String cOrderSn);

    /**
     * 修改网单单价和金额
     * @param params
     * @return
     */
    Integer updateOrderProductsPriceAndProductAmount(Map<String,Object> params);
}
