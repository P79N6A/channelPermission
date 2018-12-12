package com.haier.shop.service;


import com.haier.shop.model.O2OOrderConfirmQueues;
import com.haier.shop.model.O2oOrderCloseQueues;
import com.haier.shop.model.O2oOrderCloseQueuesExt;

import java.util.List;
import java.util.Map;


public interface AccountCenterService {

    /**
     * 插入 o2o确认订单成功队列表
     */
    int insertForwardConfirmToAccountCenterList(O2OOrderConfirmQueues o2oOrderConfirmQueues);

    /**
     * 更具网单号ID OrderProductId 获取 o2o确认订单成功队列表
     * @param pushNumber
     * @return
     */
    
    O2OOrderConfirmQueues getForwardConfirmToAccountCenterByOrderProductId(Integer orderProductId);

    /**
     * 获取 o2o确认订单成功队列表(count=0)
     * @param pushNumber
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
     * 更新 o2o确认订单成功队列表
     */
    int updateForwardConfirmToAccountCenterList(O2OOrderConfirmQueues o2oOrderConfirmQueues);

    /**
     * 获取完成关闭网单(count=0) 
     * @param topX
     */
    
    List<O2oOrderCloseQueues> getFinishColseQueues(Integer topX);

    /**
     * 获取完成关闭网单(count>0 and count<30)
     * @param topX
     */
    
    List<O2oOrderCloseQueues> getDealFinishColseQueues(Integer topX);

    /**
     * 获得取消网单
     * @param topX,flag
     * flag=0 count大于0且小于30次
     * flag=1 count=0
     */
    
    List<O2oOrderCloseQueues> getCancelStatusOrderQueues(Integer topX,
                                                         Integer flag);

    /**
     * 更新推送成功完成关闭网单
     * @param o2oQueue
     */
    int updateO2oCloseQueuesSuccessData(O2oOrderCloseQueues o2oQueue);

    /**
     * 更新未完成推送以及推送失败的完成关闭网单
     * @param o2oQueue
     */
    
    int updateO2oCloseQueuesFailData(O2oOrderCloseQueues o2oQueue);

    /**
     * 完成关闭订单
     * @param o2oQueue
     * @return
     */
    int insert(O2oOrderCloseQueues o2oQueue);

    /**
     * 完成关闭取消关闭网单写入队列（不重复插入）
     * @param o2oQueue
     * @return
     */
    int insertNotExists(O2oOrderCloseQueues o2oQueue);

    
    List<Map<String, Object>> getBrands();

    
    List<Map<String, Object>> getProductCates();

    
    List<Map<String, Object>> getReverseAcceptFinishInfo(Long lastTime);

    /**
     * 根据订单和网单号查询队列表中匹配的数据总数
     * @param type
     * @param orderProductId
     * @return
     */
    
    int getCountOcqByTypeAndOrderProductId(Integer type,
                                           Integer orderProductId);

    
    int getCountOcqByfinishColseQueues();

    
    int getCountOcqCancelStatusOrderQueues();

    /**
     * 获取受理完成队列和取消关闭关联后的数据
     * @param unSendQueryNum
     * @param sendCount
     * @return
     */
    
    List<O2oOrderCloseQueuesExt> getUnSendOrderRepairsAndCancel(Integer unSendQueryNum,
                                                                Integer sendCount);

    /**
     * 更新订单完成关闭队列
     * @param o2oOrderCloseQueuesExt
     * @return
     */
    int updateOrderCloseQueue(O2oOrderCloseQueuesExt o2oOrderCloseQueuesExt);
}
