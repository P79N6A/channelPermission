package com.haier.traderate.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.COrderBean;
import com.haier.shop.model.DutyStatistic;
import com.haier.shop.model.OrderBean;
import com.haier.shop.model.PersonnelStatistic;
import com.haier.shop.model.ReviewPool;
import com.haier.shop.model.WorkOrderBean;
import java.util.List;
import java.util.Map;

/**
 * 工单信息操作接口
 *
 * @Filename: ReviewWorkOrderService.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 */
// ....
public interface ReviewWorkOrderService {

  /**
   * 分页查询
   */
  ServiceResult<JSONObject> page(ReviewPool pool, PagerInfo pager, String startTime,
      String endTime);

  /**
   * 根据storeId 修改工单为 商家中心的
   **/
  void updateReviewFroStoreId(String storeId);

  /**
   * 修改评论(审核状态)
   */
  ServiceResult<Boolean> updeWorkOrder(ReviewPool reViewPool, String[] imgArr,
      String[] imgNameArr,
      String userName);

  /**
   * 添加评论
   *
   * @param reviewPool
   * @param imageFile
   * @param userName
   * @throws Exception
   */
  // ServiceResult<Boolean> addWorkOrder(ReviewPool reviewPool, String[]
  // imgArr, String userName);

  /**
   * 多图片
   */
  ServiceResult<Boolean> addWorkOrder(ReviewPool reviewPool, String[] imgArr, String[] imgNameArr,
      String userName);

  /*
   * 判断该网单符不符合传送SQM条件
   */
  int getTplCount(ReviewPool reviewPool);

  /**
   * 删除所有
   */
  ServiceResult<Boolean> delAll();

  /**
   * 获得满足条件的总条数
   */
  ServiceResult<Integer> searchPageCount(ReviewPool pool, String startTime, String endTime);

  /**
   * 根据id 获得详情
   */
  ServiceResult<ReviewPool> findReviewById(ReviewPool pool);

  /**
   * 给HIC提供的接口 根据手机号 来获取 单独工单信息
   */
  ServiceResult<List<ReviewPool>> getReviewPoolForMobile(ReviewPool pool);

  /**
   * 查询netOrderId = #{netOrderId}的总条数
   */
  ServiceResult<Integer> qryFeedBackCount(String netOrderId);

  /**
   * 查询orderCome = 'SG' and workStatus = 0 and question1Level1 = #{question1Level1}
   */
  ServiceResult<List<ReviewPool>> queryNotifyReviewPools(String question1Level1);

  /**
   * 根据条件查询insertTime的最大时间
   */
  ServiceResult<String> findMaxTime(String netOrderId, String question1Level2);

  /**
   * 责任位统计
   */
  ServiceResult<List<DutyStatistic>> dutyStatistic(Map<String, Object> map);

  /**
   * 人员统计
   */
  ServiceResult<List<PersonnelStatistic>> personnelStatistic(Map<String, Object> item);

  /**
   * 页面回车调用接口
   *
   * @param netOrderId 网单号
   */
  ServiceResult<WorkOrderBean> getInterface(String netOrderId);

  /**
   * 订单查询接口
   */
  ServiceResult<OrderBean> gdOrderById(String orderId);

  /**
   * 网单查询接口
   *
   * @param corderId 网单号
   */
  ServiceResult<COrderBean> gdQueryOrderProductsByCOrderSn(String corderId);

  /**
   * 数据分页迁移
   *
   * @param page 第几页
   * @param rows 一页多少个
   */
  ServiceResult<String> dataTransfer(int page, int rows, Map<String, Object> mapDate);

  /**
   * 通过订单号查询订单接口
   */
  ServiceResult<OrderBean> gdOrderByOrder(String order);

  /**
   * 数据迁移方法
   */
  ServiceResult<Integer> findPerformNum(Map<String, Object> mapDate);

  ServiceResult<Boolean> updateWorkOrder(ReviewPool reViewPool);

  ServiceResult<Boolean> updateWorkOrderOne(ReviewPool dto);

  ServiceResult<String> updateSQMStatus(String ids);

  // 根据订单号查询网单号
  ServiceResult<List<ReviewPool>> getnetOrderFroOrderId(String orderId);

}
