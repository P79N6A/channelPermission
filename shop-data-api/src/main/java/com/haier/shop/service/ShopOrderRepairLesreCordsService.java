package com.haier.shop.service;




import com.haier.shop.model.OrderRepairLESRecords;

import java.util.List;


/**
 * 出入库 吴坤洋 2017-11-03
 * @author wukunyang
 *
 */
public interface ShopOrderRepairLesreCordsService {
	
	List<OrderRepairLESRecords> queryLesreCodrdsId(String id); //查询出入库信息
	int insert(OrderRepairLESRecords cords);//插入出入库信息
	
	List<OrderRepairLESRecords> queryOutofStorage();
	
	int updataRecords(OrderRepairLESRecords orderRepairLESRecords);
    
    List<OrderRepairLESRecords> queryRecordSn(String orderRepairId);//根据退货单id查询出入库单号
}
