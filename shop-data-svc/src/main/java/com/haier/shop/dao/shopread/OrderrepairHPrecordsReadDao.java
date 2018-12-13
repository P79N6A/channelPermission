package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderrepairHPrecords;
import com.haier.shop.model.OrderrepairHPrecordsVO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
public interface OrderrepairHPrecordsReadDao {
	List<OrderrepairHPrecordsVO> selectByHpreCordsId(String id);
    
    List<OrderrepairHPrecordsVO> queryRejectsPropelling();//查询鉴定为不良品的信息
    List<OrderrepairHPrecords> queryHPRecords();
    List<OrderrepairHPrecordsVO> queryPushSAP(); //	查询需要把库存信息推送给SAP的数据
    List<OrderrepairHPrecordsVO>  queryTbOrederSn();
    
    OrderrepairHPrecordsVO  querynotOutBoxOrederSn(String id);
    OrderrepairHPrecordsVO queryTenLibrary(String id);

    OrderrepairHPrecordsVO queryB2CTenLibrary(String id);

    OrderrepairHPrecordsVO queryRepairOrderInfo(String id);

    List<OrderrepairHPrecords>  queryGenerateOutOfStorage(); //查询未生成出入库记录的信息 已定时任务的方式生成出入库信息
    
    List<OrderrepairHPrecordsVO> queryThreeAppraisal(); //查询需要发起三次鉴定的信息
    
    
    List<OrderrepairHPrecordsVO> queryNotOutBoxQuality();//查询未开箱正品信息进行处理
    List<OrderrepairHPrecordsVO> SigninInvalidatedInvoiceView();//查询未开箱正品信息 需要作废发票的信息
    
    
    List<OrderrepairHPrecordsVO> quertNotOutBoxStockPishSAP();//查询未开箱正品信息匹配物流返回的流水信息进行匹配
    
    List<OrderrepairHPrecordsVO> queryThreeOutOfStorage();//查询发起三次鉴定的信息生成出入库信息
    
    int queryjudgeRejects(String orderRepairId);//根据退货单查询 是否是不良品

    List<OrderrepairHPrecordsVO> findByOid(String oid);
    
    OrderrepairHPrecordsVO queryChangeTheboxUnbox(String id); //查询是否为无箱可换
    
    int queryOrderHAdd1(String orderRepairId);//查询hp返回的一次鉴定数据

    OrderrepairHPrecordsVO findInvoice(Integer orderProductId);
}
