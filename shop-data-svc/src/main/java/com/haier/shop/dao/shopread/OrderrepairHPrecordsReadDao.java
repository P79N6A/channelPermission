package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderrepairHPrecords;
import com.haier.shop.model.OrderrepairHPrecordsVO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

public interface OrderrepairHPrecordsReadDao {
    OrderrepairHPrecords selectByHpreCordsId(String id);
    
    List<OrderrepairHPrecordsVO> queryRejectsPropelling();//查询鉴定为不良品的信息
    List<OrderrepairHPrecords> queryHPRecords();
    List<OrderrepairHPrecordsVO> queryPushSAP(); //	查询需要把库存信息推送给SAP的数据
    List<OrderrepairHPrecordsVO>  queryTbOrederSn();
    
    List<OrderrepairHPrecords>  queryGenerateOutOfStorage(); //查询未生成出入库记录的信息 已定时任务的方式生成出入库信息
    
    List<OrderrepairHPrecordsVO> queryThreeAppraisal(); //查询需要发起三次鉴定的信息
}
