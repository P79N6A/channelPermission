package com.haier.shop.service;


import java.util.List;

import com.haier.shop.model.OrderrepairHPrecords;
import com.haier.shop.model.OrderrepairHPrecordsVO;

/**
 * HP回传数据 解析之后的表
 * @author wukunyang
 *吴坤洋 2017-11-03
 */
public interface ShopOrderrepairHPrecordsService {
	
	List<OrderrepairHPrecordsVO> selectByHpreCordsId(String id);
	
	int insert(OrderrepairHPrecords orderrepairHPrecords);//插入
	
	List<OrderrepairHPrecords> queryHPRecords();
	 List<OrderrepairHPrecordsVO> queryRejectsPropelling();
	 List<OrderrepairHPrecordsVO> queryPushSAP();
	 
	int  updataRepaiHp(OrderrepairHPrecords orderrepairHPrecords); //修改推送数据
	
	 List<OrderrepairHPrecordsVO>  queryTbOrederSn();//查询需要推送SAP的信息
	 
	 List<OrderrepairHPrecords>  queryGenerateOutOfStorage(); //查询未生成出入库记录的信息 已定时任务的方式生成出入库信息
	 
	 /**
	     * 更改生成出入库单状态
	     * @param OutOfStorageFlag
	     * @param id
	     * @return
	     */
	    int updataOutOfStorage(String OutOfStorageFlag ,String id);
	    
	    List<OrderrepairHPrecordsVO> queryThreeAppraisal(); //查询需要发起三次鉴定的信息
	    
	    /**
	     *更改虚入虚出状态
	     * @param id
	     * @param virtualEntryState
	     * @return
	     */
	    int UpdaVirtualEntryState(String id,String virtualEntryState);
	    List<OrderrepairHPrecordsVO> queryNotOutBoxQuality();//查询未开箱正品信息进行处理
	    List<OrderrepairHPrecordsVO> quertNotOutBoxStockPishSAP();
	    OrderrepairHPrecordsVO  querynotOutBoxOrederSn(String id);
	    List<OrderrepairHPrecordsVO> queryThreeOutOfStorage();//查询发起三次鉴定的信息生成出入库信息
	    int queryjudgeRejects(String orderRepairId);//根据退货单查询 是否是不良品
	    List<OrderrepairHPrecordsVO> SigninInvalidatedInvoiceView();//查询未开箱正品信息 需要作废发票的信息
	    
	    int updataPushRejects(String id);//更改不良品推送HP状态
	    OrderrepairHPrecordsVO queryTenLibrary(String id);

	public OrderrepairHPrecordsVO queryRepairOrderInfo(String id);

	List<OrderrepairHPrecordsVO> findByOid(String oid);
	
	   OrderrepairHPrecordsVO queryChangeTheboxUnbox(String id); 
	   
	    int queryOrderHAdd1(String orderRepairId);//查询hp返回的一次鉴定数据

	OrderrepairHPrecordsVO findInvoice(Integer orderProductId);
}
