package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import com.haier.shop.dao.shopread.OrderWorkflowsReadDao;
import com.haier.shop.dao.shopwrite.OrderWorkflowsWriteDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.model.OrderWorkflows;
import com.haier.shop.service.ShopOrderWorkflowsService;

@Service
public class ShopOrderWorkflowsServiceImpl implements ShopOrderWorkflowsService {
    @Autowired
     private OrderWorkflowsWriteDao orderWorkflowsWriteDao;

    @Autowired
    private OrderWorkflowsReadDao orderWorkflowsReadDao;
    /**
     * 根据主键，获取订单全流程监控信息
     * @param id
     * @return
     */
    public OrderWorkflows get(Integer id){
        return orderWorkflowsReadDao.get(id);
    }

    /**
     * 根据网单id，获取订单全流程监控信息
     * @param orderProductId 网单id
     * @return
     */
    public OrderWorkflows getByOrderProductId(Integer orderProductId){
        return orderWorkflowsReadDao.getByOrderProductId(orderProductId);
    }

    /**
     * 查询大于id的全流程数据集合
     * @param id
     * @return
     */
    public   List<OrderWorkflows> queryById(Integer id){
        return orderWorkflowsReadDao.queryById(id);
    }
    /**
     * LES出库后，更新LES出库时间
     * @param orderWorkflow
     * @return
     */
    public   Integer updateAfterLesShipped(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateAfterLesShipped(orderWorkflow);
    }

    /**
     * 菜鸟出库后，更新LES出库时间和开提单时间－3W
     * @param orderWorkflow
     * @return
     */
    public   Integer updateAfterCnShipped(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateAfterCnShipped(orderWorkflow);
    }

    /**
     * 付款后更新付款时间，以订金付款时间为准
     * @param orderWorkflow
     * @return
     */
    public  Integer updateAfterPayment(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateAfterPayment(orderWorkflow);
    }
    /**
     * 更新订单应确认时间、订单预警时间
     * @param orderWorkflow
     * @return
     */

    public Integer updateTime(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateTime(orderWorkflow);
    }

    /**
     * 更新到达网点时间
     * @param id
     * @param netPointArriveTime
     * @return
     */

    public  Integer updateNetPointArriveTime(Integer id, Long netPointArriveTime){
        return orderWorkflowsWriteDao.updateNetPointArriveTime(id, netPointArriveTime);
    }

    /**
     * 更新网点出库时间
     * @param id
     * @param netPointShipTime
     * @return
     */

    public Integer updateNetPointShipTime(Integer id, Long netPointShipTime){
        return orderWorkflowsWriteDao.updateNetPointShipTime(id,netPointShipTime);
    }

    /**
     * 更新网点接单时间
     * @param id
     * @param netPointAcceptTime
     * @return
     */
    public   Integer updateNetPointAcceptTime(Integer id, Long netPointAcceptTime){
        return orderWorkflowsWriteDao.updateNetPointAcceptTime(id,netPointAcceptTime);
    }
    /**
     * 更新用户签收时间
     * @param id
     * @param userAcceptTime
     * @return
     */

    public  Integer updateUserAcceptTime(Integer id, Long userAcceptTime){
        return orderWorkflowsWriteDao.updateUserAcceptTime(id, userAcceptTime);
    }

    /**
     * 更新完成关闭时间
     * @param id
     * @param finishTime
     * @return
     */

    public   Integer updateFinishTime(Integer id,Long finishTime){
        return orderWorkflowsWriteDao.updateFinishTime(id, finishTime);
    }

    /**
     * 获取待订单确认的网单列表信息
     * @return 网单列表集合
     */
    public  List<Map<String, Object>> getConfirmList(){
        return orderWorkflowsReadDao.getConfirmList();
    }

    /**
     * 获取待HP派工的网单列表信息
     * @return 网单列表集合
     */
    public  List<Map<String, Object>> getHpList(){
        return orderWorkflowsReadDao.getHpList();
    }

    /**
     * 获取待送达网点的网单列表信息
     * @return 网单列表集合
     */
    public  List<Map<String, Object>> getNetpointList(){
        return orderWorkflowsReadDao.getNetpointList();
    }

    /**
     * 获取待送达用户的网单列表信息
     * @return 网单列表集合
     */
    public  List<Map<String, Object>> getUserList(){
        return orderWorkflowsReadDao.getUserList();
    }

    /**
     * 获取用户已申请退款的网单列表信息
     * @return 网单列表集合
     */
    public   List<Map<String, Object>> getRepairList(){
        return orderWorkflowsReadDao.getRepairList();
    }

    /**
     * 获取hp作废工单次数
     * @return 工单次数
     */
    public Integer getHpCancelByOrderProductId(Long orderProductId){
        return orderWorkflowsReadDao.getHpCancelByOrderProductId(orderProductId);
    }

    /**
     * 获取及时率报表数据
     * @return 网单列表集合
     */
    public  List<Map<String, Object>> getOntimeRate(Map<String, Object> paramMap){
        return orderWorkflowsReadDao.getOntimeRate(paramMap);
    }

    /**
     * 获取用户已申请退款的网单列表信息
     * @param startDate 起始日期
     * @param endDate 截止日期
     * @return 网单列表集合
     */
    public   List<Map<String, Object>> getRepairListByDate(Integer startDate,
                                                  Integer endDate){
        return orderWorkflowsReadDao.getRepairListByDate(startDate,endDate);
    }

    /**
     * 获取区县
     * @param parentId 上级id
     * @return 区县列表
     */
    public  List<Map<String, String>> getRegions(Integer parentId){
        return orderWorkflowsReadDao.getRegions(parentId);
    }

    /**
     * 获取库位
     * @return 库位列表
     */
    public  List<Map<String, String>> getStorages(){
        return orderWorkflowsReadDao.getStorages();
    }

    /**
     * 获取及时率网单明细总数
     * @param paramMap cOrderSn网单号数组,pager分页信息
     * @return
     */
    public  Integer getOntimeRateDetailCount(Map<String, Object> paramMap){
        return orderWorkflowsReadDao.getOntimeRateDetailCount(paramMap);
    }

    /**
     * 获取及时率网单明细列表
     * @param paramMap cOrderSn网单号数组,pager分页信息
     * @return
     */
    public  List<Map<String, Object>> getOntimeRateDetail(Map<String, Object> paramMap){
        return orderWorkflowsReadDao.getOntimeRateDetail(paramMap);
    }
    /**
     * 根据区id查询配送时效
     * @param id 区id
     * @return
     */
    public  Integer getShippingTimeByRegionId(Integer id){
        return orderWorkflowsReadDao.getShippingTimeByRegionId(id);
    }

    /**
     * 获取联系人手机信息
     * @return
     */
    public  List<Map<String, Object>> getTelephoneInfo(String smsPoint, String smsDeptName){
        return orderWorkflowsReadDao.getTelephoneInfo(smsPoint, smsDeptName);
    }

    /**
     * 获取工贸信息
     * @return
     */
    public  List<Map<String, Object>> getTradeInfo(){
        return orderWorkflowsReadDao.getTradeInfo();
    }

    /**
     * 获取中心信息
     * @return
     */
    public  List<Map<String, Object>> getCenterInfo(){
        return orderWorkflowsReadDao.getCenterInfo();
    }

    /**
     * 获取配送时效信息
     * @return
     */
    public  List<Map<String, Object>> getShippingTimeInfo(){
        return orderWorkflowsReadDao.getShippingTimeInfo();
    }


    public int insert(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.insert(orderWorkflow);
    }

    /**
     * 网单开提单后更新全流程的部分字段信息
     * @return
     */

    public   Integer updateAfterSyncOrderToLes(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateAfterSyncOrderToLes(orderWorkflow);
    }

    /**
     * 确认订单更新
     * @param orderWorkflow
     * @return
     */

    public int updateForConformOrder(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateForConformOrder(orderWorkflow);
    }

    /**
     * 公共计算更新全流程的时间
     * @return
     */

    public int updateForPubCountTime(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateForPubCountTime(orderWorkflow);
    }

    /**
     * 获取全流程信息
     * @return
     */
    public  List<OrderWorkflows> getListByOrderSn(String orderSn){
        return orderWorkflowsReadDao.getListByOrderSn(orderSn);
    }

    /**
     *  取消订单更新
     * @param orderWorkflow
     * @return
     */

    public  int updateForCancelOrder(OrderWorkflows orderWorkflow){
        return orderWorkflowsWriteDao.updateForCancelOrder(orderWorkflow);
    }

    /**
     * 更新超时免单
     * @param
     * @return
     */

    public   Integer updateIsTimeoutFree(Integer orderProductId, Integer isTimeoutFree){
        return orderWorkflowsWriteDao.updateIsTimeoutFree(orderProductId, isTimeoutFree); }
}
