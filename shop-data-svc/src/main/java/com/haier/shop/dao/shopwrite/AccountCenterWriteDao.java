package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.O2OOrderConfirmQueues;
import com.haier.shop.model.O2oOrderCloseQueues;
import com.haier.shop.model.O2oOrderCloseQueuesExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccountCenterWriteDao {

    /**
     * 插入 o2o确认订单成功队列表
     */
    int insertForwardConfirmToAccountCenterList(O2OOrderConfirmQueues o2oOrderConfirmQueues);

    /**
     * 更新 o2o确认订单成功队列表
     */
    int updateForwardConfirmToAccountCenterList(O2OOrderConfirmQueues o2oOrderConfirmQueues);

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

    /**
     * 更新订单完成关闭队列
     * @param o2oOrderCloseQueuesExt
     * @return
     */
    int updateOrderCloseQueue(O2oOrderCloseQueuesExt o2oOrderCloseQueuesExt);
}
