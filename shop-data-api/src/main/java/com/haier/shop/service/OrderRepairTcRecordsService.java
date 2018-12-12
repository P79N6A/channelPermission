package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.OrderRepairTcRecords;


public interface OrderRepairTcRecordsService {

	Integer updateHpReturn(OrderRepairTcRecords orderRepairTcRecords);
	
	OrderRepairTcRecords getOrderRepairByVomTcSnAndSku( String orderRepairSn,  String sku);

	/**
     * 根据记录单号获取退仓入库记录(3W正品退仓)
     * @param recordSn
     * @return
     */
    OrderRepairTcRecords getByRecordSn(  String recordSn);
    
    /**
     * VOM接单成功后更新(3W正品退仓)
     * @param orderRepairLESRecords
     * @return
     */
    Integer updateAfterVomAccepted(OrderRepairTcRecords orderRepairTcRecords);
    
    /**
     * 退货单LES入库后，更新LES退货单(3W正品退仓)
     * @param orderRepairLESRecords
     * @return
     */
    Integer updateAfterLesInStorage(OrderRepairTcRecords orderRepairTcRecords);
    
    /**
     * 根据退仓单id查询退仓记录信息
     * @param orderRepairTcId
     * @return
     */
    List<OrderRepairTcRecords> queryByOrderRepairTcId( Integer orderRepairTcId);

}
