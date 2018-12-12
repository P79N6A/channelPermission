package com.haier.distribute.data.service;


import java.util.List;

import com.haier.distribute.data.model.PopOrderProducts;

public interface OrderProductsService {
    int orderOpertion(String orderStatus, String Id);

//	int orderOpertionToSure(@Param("orderStatus") String orderStatus,@Param("oId") String oId, @Param("Id")String Id);

    List<PopOrderProducts> checkStatus(String id);

    List<PopOrderProducts> checkOid(String oid);

    PopOrderProducts getOneByOrderSn(String cOrderSn);

    List<PopOrderProducts> commodityList(Long orderId);

    List<PopOrderProducts> exportOrderProductsList(PopOrderProducts entity);

    List<PopOrderProducts> commission_product_invoice(int start, int rows, String categoryId, String startTime, String endTime, String policyYear, int channelId, int channelType, int brandId);

    List<PopOrderProducts> commission_product_invoice2(int start, int rows, String categoryId, String startTime, String endTime, String policyYear, int channelId, int channelType, int brandId);

    List<PopOrderProducts> commission_product_invoice3(String categoryId, String policyYear, int channelId, int channelType, int brandId);

    List<PopOrderProducts> commission_product_invoice1(String categoryId, String policyYear, int channelId, int channelType, int brandId);

    int getPagerCountS(String categoryId, String startTime, String endTime, String policyYear, int channelId, int channelType, int brandId);

    Integer updateHPAllotNetPoint(PopOrderProducts orderProduct);

    List<PopOrderProducts> getPageByCondition(PopOrderProducts entity, int start, int rows);

    long getPagerCount(PopOrderProducts entity);

    List<PopOrderProducts> listByCondition(PopOrderProducts entity);
}
