package com.haier.shop.service;

import com.haier.shop.model.OrderRepairLESRecords;

import java.util.List;
/*
* 作者:张波
* 2017/12/19
* */
public interface OrderRepairLESRecordsService {
    /**
     * 根据记录单号获取LES退货单
     * @param recordSn
     * @return
     */
    OrderRepairLESRecords getByRecordSn(String recordSn);
    /**
     * 退货单LES入库后，更新LES退货单
     * @param OrderRepairLESRecords
     * @return
     */
    Integer updateAfterLesInStorage(OrderRepairLESRecords OrderRepairLESRecords);
    /**
     * VOM接单成功后更新
     * @param OrderRepairLESRecords
     * @return
     */
    Integer updateAfterVomAccepted(OrderRepairLESRecords OrderRepairLESRecords);
    

    /**
     * 根据les提单号查询纪录
     * @param lesOrderSn
     * @return
     */
    OrderRepairLESRecords getByLesOrderSn( String lesOrderSn,
                                        String cOrderSn);
    /**
     * 写LESRecords
     * @param lesRecords
     * @return
     */
    Integer insert(OrderRepairLESRecords lesRecords);
    
    
    List<OrderRepairLESRecords> queryLesreCodrdsId(String id); //查询出入库信息
    /**
     * 退货单LES入库后，更新LES退货单
     * @param OrderRepairLESRecords
     * @return
     */
    Integer updateLesRecordAfterJLIN(OrderRepairLESRecords OrderRepairLESRecords);

    /**
     * 根据记录单号获取LES出入库记录
     * @return
     */
    public List<OrderRepairLESRecords> getWaitforCancelOP(int limitNum);
    
    /**
     * 更新OP取消标记
     * @param OrderRepairLESRecords
     * @return
     */
    Integer updateOpCancelFlag(OrderRepairLESRecords OrderRepairLESRecords);
}
