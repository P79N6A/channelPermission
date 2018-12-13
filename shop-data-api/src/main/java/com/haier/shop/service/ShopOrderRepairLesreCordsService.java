package com.haier.shop.service;




import com.haier.shop.model.OrderRepairLESRecords;

import java.util.List;
import java.util.Map;


/**
 * 出入库 吴坤洋 2017-11-03
 * @author wukunyang
 *
 */
public interface ShopOrderRepairLesreCordsService {
	
	List<OrderRepairLESRecords> queryLesreCodrdsId(String id); //查询出入库信息
	int insert(OrderRepairLESRecords cords);//插入出入库信息
	
	List<OrderRepairLESRecords> queryOutofStorage();

	public  List<OrderRepairLESRecords> queryOutofStorageByRepairid(String orderRepairsId);
	
	int updataRecords(OrderRepairLESRecords orderRepairLESRecords);

    /**
     *
     * @param id
     * @param success
     * @return
     */
	public int updateRepairLesRecordcnSuccess(String id, Integer success);
    
    List<OrderRepairLESRecords> queryRecordSn(String operate,String storageType,String orderRepairId);//根据退货单id查询出入库单号

	public List<OrderRepairLESRecords> queryNotLesOrder();

    List<OrderRepairLESRecords> queryStorageType();//查询入10和入41的出入库信息  判断vom是否已经返回入库流水

	/**
	 * 查询入10库的小家电信息
	 * @return
	 */
	public List<OrderRepairLESRecords> b2cqueryStorageType();
    
    List<OrderRepairLESRecords> queryTransferBatch(String orderRepairsI); //查询根据单号查询未推送VOM的出入库数据

	public List<OrderRepairLESRecords> queryRepairLesOrder(String orderRepairsId);

	/**
	 *
	 * @param code
	 * @return
	 */
	public List<Map<String, Object>> queryStorageRegion(String code);
}
