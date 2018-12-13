package com.haier.shop.service;


import java.util.List;

import com.haier.shop.model.OrderhpRejectionLogs;
import com.haier.shop.model.OrderhpRejectionLogsVO;

public interface ShopOrderhpRejectionLogsService {

    int insert(OrderhpRejectionLogs record);

    int insertSelective(OrderhpRejectionLogs record);

    OrderhpRejectionLogs selectByPrimaryKey(Integer id);

    String quereHPRejection(String orderReprisSn,String hpLesId);//根据退货单号查询主键
    int updateHpRejectionLogs(OrderhpRejectionLogs record);
    
    List<OrderhpRejectionLogsVO>  quereEmptyOutSAP();///查询HP返回到CBS为不良品的数据 进行虚入虚出的操作
    List<OrderhpRejectionLogsVO>  queryRealOutofData();//查询需要实入数据
    
    int updataEmptyOut(String id,String emptyOut);
    
    /**
     * 查询虚入信息 
     * @return
     */
    List<OrderhpRejectionLogsVO> queryTheVirtualInto();//查询虚入
    
    String quereHpLesId(String channelOrderSn);//根据退货单号查询38单号
    
    List<OrderhpRejectionLogs> queryVirtualEntryState(String channelOrderSn);//根据退货单号查询虚入虚出状态
    
    
}