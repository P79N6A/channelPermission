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

    int updataPushSap(String id);
    int   RepairsTermination(@Param("id") String id,@Param("handleRemark")String handleRemark); //关闭退货单更改退货单状态
}
