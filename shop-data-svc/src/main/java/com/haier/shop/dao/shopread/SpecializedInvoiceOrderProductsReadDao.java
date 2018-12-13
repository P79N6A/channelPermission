package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderProductsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/6/6 15:30
 */
@Mapper
public interface SpecializedInvoiceOrderProductsReadDao {

    /**
     * //分页查询
     * @param params
     * @return
     */
     List<OrderProductsVo> queryOderProductList(OrderProductsVo params);

    /**
     * 查询总数
     * @param params
     * @return
     */
     int  findOrderProductCNT(OrderProductsVo params);

    /**
     *导出网单列表的查询
     * @param
     * @return
     */
    List<Map<String,Object>> getListByParams(OrderProductsVo params);


    /**
     * 查询网单详情
     * @param cOrderSn
     * @return
     */
    OrderProductsVo getOrderOroductsDetail(String cOrderSn);
}
