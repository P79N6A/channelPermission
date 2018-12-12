package com.haier.shop.service;


import java.util.List;

import com.haier.shop.model.OrderRepairHPRecordsNew;


public interface OrderRepairHPRecordsnNewService {
    /**
     * 获取HP退货单
     * @param orderRepairId 退货单id
     * @param checkType 质检类型
     * @return
     */
    OrderRepairHPRecordsNew getByRepairIdAndCheckType( Integer orderRepairId,
                                                       Integer checkType);

    /**
     * 根据OrderRepairId获取HPRecords
     * @param repairId
     * @return
     */
    List<OrderRepairHPRecordsNew> getByRepairId( int orderRepairId);
}
