package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderRepairLESRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    List<OrderRepairLESRecords> queryOutofStorageByRepairid(String orderRepairsId);

    List<OrderRepairLESRecords> queryTransferBatch(String orderRepairsId); //查询根据单号查询未推送VOM的出入库数据

    List<OrderRepairLESRecords> queryRepairLesOrder(String orderRepairsId);
    
    List<OrderRepairLESRecords> queryRecordSn(@Param("operate")String operate,@Param("storageType")String storageType,@Param("orderRepairId")String orderRepairId);//根据退货单id查询出入库单号

    List<OrderRepairLESRecords> queryNotLesOrder();

    List<OrderRepairLESRecords> queryStorageType();//查询入10和入41的出入库信息  判断vom是否已经返回入库流水

    List<OrderRepairLESRecords> b2cqueryStorageType();//查询入10小家电入库信息

    /**
     * 查询仓库区域信息
     * @param code
     * @return
     */
    List<Map<String, Object>> queryStorageRegion(@Param("code") String code);
}
