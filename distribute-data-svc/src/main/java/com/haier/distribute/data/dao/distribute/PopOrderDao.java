package com.haier.distribute.data.dao.distribute;
/**
 * Created by Administrator on 2017/11/7 0007.
 */

import com.haier.distribute.data.model.PopOrders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/7 0007
 * \* Time: 9:13
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public interface PopOrderDao extends BaseDao<PopOrders>{
    String getVehicleOrderNo(@Param("begin") String begin);

    int updateSelectiveByOrderNo(@Param("entity") PopOrders entity);

//    int updateStatus(@Param("orderStatus") short orderStatus,@Param("orderId") String orderId);

    int updateStatus(@Param("orderStatus") String orderStatus, @Param("Id")String Id);
    int orderOpertionToSure(@Param("sellPeople")String sellPeople,@Param("orderStatus") String orderStatus,@Param("oId") String oId, @Param("Id")String Id);

    int addConfirmTime(@Param("confirmTime") long confirmTime , @Param("Id")String Id);

    int confirmTime(@Param("confirmTime") long confirmTime , @Param("Id")String Id);

    int updateCancelStatus(@Param("cancelStatus") String cancelStatus , @Param("Id")String orderId);

    int editRemark(@Param("codConfirmRemark") String codConfirmRemark , @Param("orderSn")String orderSn);

    int editOid(@Param("oid") String oid , @Param("orderSn")String orderSn);

    int editOrigin(@Param("consignee")String consignee,@Param("mobile")String mobile,
                   @Param("originRegionName")String originRegionName,
                   @Param("originAddress")String originAddress, @Param("orderSn")String orderSn);

    List<PopOrders> checkOid(@Param("oid") String oid , @Param("orderSn") String orderSn);
    
    List<PopOrders> exportlist(@Param("entity") PopOrders entity);

    /**
     * 根据id获取订单对象
     * @param id
     * @return
     */
    PopOrders get(Integer id);

    int finishToCancel(String orderSn);
}
