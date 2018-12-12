package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderRepairTcRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderRepairTcRecordsReadDao {

	OrderRepairTcRecords getOrderRepairByVomTcSnAndSku(@Param("orderRepairSn") String orderRepairSn, @Param("sku") String sku);

	/**
     * 根据记录单号获取退仓入库记录(3W正品退仓)
     * @param recordSn
     * @return
     */
    OrderRepairTcRecords getByRecordSn(@Param("recordSn") String recordSn);
    
    /**
     * 根据退仓单id查询退仓记录信息
     * @param orderRepairTcId
     * @return
     */
    List<OrderRepairTcRecords> queryByOrderRepairTcId(@Param("orderRepairTcId") Integer orderRepairTcId);

}
