package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderRepairLESRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
* 作者:张波
* 2017/12/19
* */
@Mapper
public interface OrderRepairLESRecordsWriteDao {
    /**
     * 退货单LES入库后，更新LES退货单
     * @param orderRepairLESRecords
     * @return
     */
    Integer updateAfterLesInStorage(OrderRepairLESRecords orderRepairLESRecords);
    /**
     * VOM接单成功后更新
     * @param orderRepairLESRecords
     * @return
     */
    Integer updateAfterVomAccepted(OrderRepairLESRecords orderRepairLESRecords);

    /**
     * 写LESRecords
     * @param lesRecords
     * @return
     */
    Integer insert(OrderRepairLESRecords lesRecords);
    
    /**
     * 退货单LES入库后，更新LES退货单
     * @param orderRepairLESRecords
     * @return
     */
    Integer updateLesRecordAfterJLIN(OrderRepairLESRecords orderRepairLESRecords);


    /**
     * 更新OP取消标记
     * @param orderRepairLESRecords
     * @return
     */
    Integer updateOpCancelFlag(OrderRepairLESRecords orderRepairLESRecords);
    /**
     * 更新推送状态信息
     * @return
     */
    int updataRecords(OrderRepairLESRecords orderRepairLESRecords);
   
}
