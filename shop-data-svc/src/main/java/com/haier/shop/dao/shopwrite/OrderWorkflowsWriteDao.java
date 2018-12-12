package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderWorkflows;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderWorkflowsWriteDao {

    /**
     * LES出库后，更新LES出库时间
     * @param orderWorkflow
     * @return
     */
    Integer updateAfterLesShipped(OrderWorkflows orderWorkflow);

    /**
     * 菜鸟出库后，更新LES出库时间和开提单时间－3W
     * @param orderWorkflow
     * @return
     */
    Integer updateAfterCnShipped(OrderWorkflows orderWorkflow);

    /**
     * 付款后更新付款时间，以订金付款时间为准
     * @param orderWorkflow
     * @return
     */
    Integer updateAfterPayment(OrderWorkflows orderWorkflow);

    /**
     * 更新订单应确认时间、订单预警时间
     * @param orderWorkflow
     * @return
     */
    
    Integer updateTime(OrderWorkflows orderWorkflow);

    /**
     * 更新到达网点时间
     * @param id
     * @param netPointArriveTime
     * @return
     */
    
    Integer updateNetPointArriveTime(@Param("id") Integer id,
                                     @Param("netPointArriveTime") Long netPointArriveTime);

    /**
     * 更新网点出库时间
     * @param id
     * @param netPointShipTime
     * @return
     */
    
    Integer updateNetPointShipTime(@Param("id") Integer id,
                                   @Param("netPointShipTime") Long netPointShipTime);

    /**
     * 更新网点接单时间
     * @param id
     * @param netPointAcceptTime
     * @return
     */
    Integer updateNetPointAcceptTime(@Param("id") Integer id,
                                     @Param("netPointAcceptTime") Long netPointAcceptTime);

    /**
     * 更新用户签收时间
     * @param id
     * @param userAcceptTime
     * @return
     */
    
    Integer updateUserAcceptTime(@Param("id") Integer id,
                                 @Param("userAcceptTime") Long userAcceptTime);

    /**
     * 更新完成关闭时间
     * @param id
     * @param finishTime
     * @return
     */
    
    Integer updateFinishTime(@Param("id") Integer id, @Param("finishTime") Long finishTime);

    int insert(OrderWorkflows orderWorkflow);

    /**
     * 网单开提单后更新全流程的部分字段信息
     * @return
     */
    
    Integer updateAfterSyncOrderToLes(OrderWorkflows orderWorkflow);

    /**
     * 确认订单更新
     * @param orderWorkflow
     * @return
     */
    
    int updateForConformOrder(OrderWorkflows orderWorkflow);

    /**
     * 公共计算更新全流程的时间
     * @return
     */
    
    int updateForPubCountTime(OrderWorkflows orderWorkflow);

    /**
     *  取消订单更新
     * @param orderWorkflow
     * @return
     */
    
    int updateForCancelOrder(OrderWorkflows orderWorkflow);

    /**
     * 更新超时免单
     * @param cOrderSn
     * @return
     */
    
    Integer updateIsTimeoutFree(@Param("orderProductId") Integer orderProductId,
                                @Param("isTimeoutFree") Integer isTimeoutFree);
}
