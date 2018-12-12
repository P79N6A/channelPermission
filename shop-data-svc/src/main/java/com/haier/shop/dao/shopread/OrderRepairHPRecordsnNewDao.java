package com.haier.shop.dao.shopread;


import com.haier.shop.model.OrderRepairHPRecordsNew;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface OrderRepairHPRecordsnNewDao {
    /**
     * 获取HP退货单
     * @param orderRepairId 退货单id
     * @param checkType 质检类型
     * @return
     */
    OrderRepairHPRecordsNew getByRepairIdAndCheckType(@Param("orderRepairId") Integer orderRepairId,
                                                      @Param("checkType") Integer checkType);
    
    /**
     * 根据OrderRepairId获取HPRecords
     * @param orderRepairId
     * @return
     */
    List<OrderRepairHPRecordsNew> getByRepairId(@Param("orderRepairId") int orderRepairId);
}
