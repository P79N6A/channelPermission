package com.haier.shop.service;


import java.util.List;
import java.util.Map;

import com.haier.shop.model.OrderWorkflows;

public interface ShopOrderWorkflowsService {
    /**
     * 根据主键，获取订单全流程监控信息
     * @param id
     * @return
     */
    OrderWorkflows get(Integer id);

    /**
     * 根据网单id，获取订单全流程监控信息
     * @param orderProductId 网单id
     * @return
     */
    OrderWorkflows getByOrderProductId(Integer orderProductId);

    /**
     * 查询大于id的全流程数据集合
     * @param id
     * @return
     */
    List<OrderWorkflows> queryById(Integer id);

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
    
    Integer updateNetPointArriveTime(Integer id, Long netPointArriveTime);

    /**
     * 更新网点出库时间
     * @param id
     * @param netPointShipTime
     * @return
     */
    
    Integer updateNetPointShipTime(Integer id, Long netPointShipTime);

    /**
     * 更新网点接单时间
     * @param id
     * @param netPointAcceptTime
     * @return
     */
    Integer updateNetPointAcceptTime(Integer id, Long netPointAcceptTime);

    /**
     * 更新用户签收时间
     * @param id
     * @param userAcceptTime
     * @return
     */
    
    Integer updateUserAcceptTime(Integer id, Long userAcceptTime);

    /**
     * 更新完成关闭时间
     * @param id
     * @param finishTime
     * @return
     */
    
    Integer updateFinishTime(Integer id,Long finishTime);

    /**
    * 获取待订单确认的网单列表信息
    * @return 网单列表集合
    */
    List<Map<String, Object>> getConfirmList();

    /**
     * 获取待HP派工的网单列表信息
     * @return 网单列表集合
     */
    List<Map<String, Object>> getHpList();

    /**
     * 获取待送达网点的网单列表信息
     * @return 网单列表集合
     */
    List<Map<String, Object>> getNetpointList();

    /**
     * 获取待送达用户的网单列表信息
     * @return 网单列表集合
     */
    List<Map<String, Object>> getUserList();

    /**
     * 获取用户已申请退款的网单列表信息
     * @return 网单列表集合
     */
    List<Map<String, Object>> getRepairList();

    /**
     * 获取hp作废工单次数
     * @return 工单次数
     */
    Integer getHpCancelByOrderProductId(Long orderProductId);

    /**
     * 获取及时率报表数据
     * @return 网单列表集合
     */
    List<Map<String, Object>> getOntimeRate(Map<String, Object> paramMap);

    /**
     * 获取用户已申请退款的网单列表信息
     * @param startDate 起始日期
     * @param endDate 截止日期
     * @return 网单列表集合
     */
    List<Map<String, Object>> getRepairListByDate(Integer startDate,
                                                  Integer endDate);

    /**
     * 获取区县
     * @param parentId 上级id
     * @return 区县列表
     */
    List<Map<String, String>> getRegions(Integer parentId);

    /**
     * 获取库位
     * @return 库位列表
     */
    List<Map<String, String>> getStorages();

    /**
     * 获取及时率网单明细总数
     * @param paramMap cOrderSn网单号数组,pager分页信息
     * @return
     */
    Integer getOntimeRateDetailCount(Map<String, Object> paramMap);

    /**
     * 获取及时率网单明细列表
     * @param paramMap cOrderSn网单号数组,pager分页信息
     * @return
     */
    List<Map<String, Object>> getOntimeRateDetail(Map<String, Object> paramMap);

    /**
     * 根据区id查询配送时效
     * @param id 区id
     * @return
     */
    Integer getShippingTimeByRegionId(Integer id);

    /**
     * 获取联系人手机信息
     * @return
     */
    List<Map<String, Object>> getTelephoneInfo(String smsPoint, String smsDeptName);

    /**
     * 获取工贸信息
     * @return
     */
    List<Map<String, Object>> getTradeInfo();

    /**
     * 获取中心信息
     * @return
     */
    List<Map<String, Object>> getCenterInfo();

    /**
     * 获取配送时效信息
     * @return
     */
    List<Map<String, Object>> getShippingTimeInfo();

    
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
     * 获取全流程信息
     * @return
     */
    List<OrderWorkflows> getListByOrderSn(String orderSn);

    /**
     *  取消订单更新
     * @param orderWorkflow
     * @return
     */
    
    int updateForCancelOrder(OrderWorkflows orderWorkflow);

    /**
     * 更新超时免单
     * @param
     * @return
     */
    
    Integer updateIsTimeoutFree(Integer orderProductId, Integer isTimeoutFree);
}
