package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderRepairLESRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
* 作者:张波
* 2017/12/19
* */
@Mapper
public interface OrderRepairLESRecordsReadDao {
    /**
     * 根据记录单号获取LES退货单
     * @param recordSn
     * @return
     */
    OrderRepairLESRecords getByRecordSn(@Param("recordSn") String recordSn);

    /**
     * 根据les提单号查询纪录
     * @param lesOrderSn
     * @return
     */
    OrderRepairLESRecords getByLesOrderSn(@Param("lesOrderSn") String lesOrderSn,
                                          @Param("cOrderSn") String cOrderSn);

    /**
     * 根据记录单号获取LES出入库记录
     * @return
     */
    public List<OrderRepairLESRecords> getWaitforCancelOP(int limitNum);

    List<OrderRepairLESRecords> queryLesreCodrdsId(String id);
    
    List<OrderRepairLESRecords> queryOutofStorage(); //查询出入库信息
    
    List<OrderRepairLESRecords> queryRecordSn(String orderRepairId);//根据退货单id查询出入库单号
}
