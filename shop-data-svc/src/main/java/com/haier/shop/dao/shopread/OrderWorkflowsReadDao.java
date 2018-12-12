package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderWorkflows;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface OrderWorkflowsReadDao {
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
    List<Map<String, Object>> getRepairListByDate(@Param("startDate") Integer startDate,
                                                  @Param("endDate") Integer endDate);

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
    List<Map<String, Object>> getTelephoneInfo(@Param("smsPoint") String smsPoint,
                                               @Param("smsDeptName") String smsDeptName);

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

    /**
     * 获取全流程信息
     * @return
     */
    List<OrderWorkflows> getListByOrderSn(String orderSn);

}
