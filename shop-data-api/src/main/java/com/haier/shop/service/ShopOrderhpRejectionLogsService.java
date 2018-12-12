package com.haier.shop.service;


import java.util.List;

import com.haier.shop.model.OrderhpRejectionLogs;

public interface ShopOrderhpRejectionLogsService {

    int insert(OrderhpRejectionLogs record);

    int insertSelective(OrderhpRejectionLogs record);

    OrderhpRejectionLogs selectByPrimaryKey(Integer id);

    String quereHPRejection(String id);//根据退货单号查询主键
    int updateHpRejectionLogs(OrderhpRejectionLogs record);
    
    List<OrderhpRejectionLogs>  quereEmptyOutSAP();///查询HP返回到CBS为不良品的数据 进行虚入虚出的操作
    
    int updataEmptyOut(String id,String emptyOut);
}