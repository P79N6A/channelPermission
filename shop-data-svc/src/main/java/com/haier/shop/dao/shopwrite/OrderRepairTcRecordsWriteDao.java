package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderRepairTcRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderRepairTcRecordsWriteDao {

	Integer updateHpReturn(OrderRepairTcRecords orderRepairTcRecords);

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

}
