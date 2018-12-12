package com.haier.shop.dao.shopread;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haier.shop.model.O2OOrderConfirmQueues;
import com.haier.shop.model.O2oOrderCloseQueues;
import com.haier.shop.model.O2oOrderCloseQueuesExt;
@Mapper
public interface AccountCenterReadDao {

    /**
     * 更具网单号ID OrderProductId 获取 o2o确认订单成功队列表
     * @param orderProductId
     * @return
     */
    O2OOrderConfirmQueues getForwardConfirmToAccountCenterByOrderProductId(Integer orderProductId);

    /**
     * 获取 o2o确认订单成功队列表(count=0)
     * @param topX
     * @return
     */
    List<O2OOrderConfirmQueues> getForwardConfirmToAccountCenterList(Integer topX);

    /**
     * 获取 o2o确认订单成功队列表(count>0 and count<30)
     * @param topX
     * @return
     */
    List<O2OOrderConfirmQueues> getForwardConfirmToAccountCenterListThousand(Integer topX);

    /**
     * 获取完成关闭网单(count=0) 
     * @param topX
     */
    List<O2oOrderCloseQueues> getFinishColseQueues(@Param("topX") Integer topX);

    /**
     * 获取完成关闭网单(count>0 and count<30)
     * @param topX
     */
    List<O2oOrderCloseQueues> getDealFinishColseQueues(@Param("topX") Integer topX);

    /**
     * 获得取消网单
     * @param topX,flag
     * flag=0 count大于0且小于30次
     * flag=1 count=0
     */
    List<O2oOrderCloseQueues> getCancelStatusOrderQueues(@Param("topX") Integer topX,
                                                         @Param("flag") Integer flag);
    
    List<Map<String, Object>> getBrands();

    
    List<Map<String, Object>> getProductCates();

    
    List<Map<String, Object>> getReverseAcceptFinishInfo(@Param("lastTime") Long lastTime);

    /**
     * 根据订单和网单号查询队列表中匹配的数据总数
     * @param type
     * @param orderProductId
     * @return
     */
    int getCountOcqByTypeAndOrderProductId(@Param("type") Integer type,
                                           @Param("orderProductId") Integer orderProductId);

    int getCountOcqByfinishColseQueues();

    int getCountOcqCancelStatusOrderQueues();

    /**
     * 获取受理完成队列和取消关闭关联后的数据
     * @param unSendQueryNum
     * @param sendCount
     * @return
     */
    List<O2oOrderCloseQueuesExt> getUnSendOrderRepairsAndCancel(@Param("unSendQueryNum") Integer unSendQueryNum,
                                                                @Param("sendCount") Integer sendCount);

}
