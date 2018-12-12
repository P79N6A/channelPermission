package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderRepairLESQueues;
import com.haier.shop.model.OrderRepairLESRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderRepairLESQueuesWriteDao {

    Integer updateVomResult(OrderRepairLESQueues queues);
    /**
     * 写LESRecords
     * @param orderRepairLESQueues
     * @return
     */
    Integer insert(OrderRepairLESQueues orderRepairLESQueues);
    
    /**
     * 退货单LES入库后，更新LES退货单
     * @param OrderRepairLesreCords
     * @return
     */
    Integer updateAfterLesInStorage(OrderRepairLESRecords OrderRepairLesreCords);
    /**
     * VOM接单成功后更新
     * @param OrderRepairLesreCords
     * @return
     */
    Integer updateAfterVomAccepted(OrderRepairLESRecords OrderRepairLesreCords);

    /**
     * 写LESRecords
     * @param lesRecords
     * @return
     */
    Integer insert(OrderRepairLESRecords lesRecords);
    
    /**
     * 退货单LES入库后，更新LES退货单
     * @param OrderRepairLesreCords
     * @return
     */
    Integer updateLesRecordAfterJLIN(OrderRepairLESRecords OrderRepairLesreCords);

    /**
     * 更新OP取消标记
     * @param OrderRepairLesreCords
     * @return
     */
    Integer updateOpCancelFlag(OrderRepairLESRecords OrderRepairLesreCords);
}
