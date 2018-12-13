package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderRepairs;
import com.haier.shop.model.OrderRepairsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author lichunsheng
 * @create 2018-01-04
 **/
@Mapper
public interface OrderRepairsWriteDao {

    Integer updateReceiptInfo(OrderRepairs orderRepairs);

    int insertSelective(OrderRepairsVo record);//插入

    int updataStatus(@Param("id") String id, @Param("status") String status, @Param("handleRemark") String handleRemark); //退货审核

    int updatePushHp(OrderRepairsVo orderRepairsVo); //推送HP 添加信息

    int  updateStatus(@Param("receiptStatus") String receiptStatus, @Param("storageStatus") String storageStatus, @Param("id") String id);//修改 发票状态 和 货物状态改成带召回

    int updataPushSap(@Param("id")String id,@Param("pushSap")String pushSap);
    int RepairsTermination(@Param("id") String id,@Param("handleStatus")String handleStatus,@Param("handleRemark")String handleRemark); //关闭退货单更改退货单状态
    int RepairsRminatereverse(@Param("id") String id,@Param("handleStatus")String handleStatus,@Param("handleRemark")String handleRemark,@Param("terminationReason")String terminationReason); //取消逆向单

    /**
     * 更改退货单状态
     * @param id
     * @param handleStatus
     * @return
     */
    int updateHandleStatus(@Param("id") String id,@Param("handleStatus")String handleStatus);


    /**
     * 更改发票和货物状态
     * @param receiptStatus
     * @param storageStatus
     * @param id
     * @return
     */
    int updataOrderRepairsStatus(@Param("receiptStatus")String receiptStatus,@Param("storageStatus")String storageStatus,@Param("id")String id);

    int updateExchange(Integer repairId);
}
