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
	
	OrderrepairHPrecords selectByHpreCordsId(String id);
	
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
}
