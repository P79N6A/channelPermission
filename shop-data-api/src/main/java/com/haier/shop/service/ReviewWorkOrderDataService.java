package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.model.COrderBean;
import com.haier.shop.model.DutyStatistic;
import com.haier.shop.model.OrderBean;
import com.haier.shop.model.PersonnelStatistic;
import com.haier.shop.model.ReviewPool;
import com.haier.shop.model.Reviewpoolfordhzx;
import com.haier.shop.model.WorkOrderBean;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.service
 * @Date: Created in 2018/4/27 19:07
 * @Modified By:
 */
public interface ReviewWorkOrderDataService {

  JSONObject findPageList(ReviewPool pool, PagerInfo pager, String startTime,
      String endTime);

  void updateReviewFroStoreId(String storeId);

  List<ReviewPool> getReviewPoolForMobile(ReviewPool pool);

  void updeWorkOrder(ReviewPool reviewPool, String[] imgArr, String[] imgNameArr,
      String userName)
      throws ParseException;

  String addWorkOrder(ReviewPool reviewPool, String[] imgArr, String[] imgNameArr,
      String userName)
      throws ParseException;

  int getTplCount(ReviewPool reviewPool);

  void delAll();

  int searchPageCount(ReviewPool pool, String startTime, String endTime);

  ReviewPool findReviewById(ReviewPool pool);

  int qryFeedBackCount(String netOrderId);

  List<ReviewPool> queryNotifyReviewPools(String question1Level1);

  String findMaxTime(String netOrderId, String question1Level2);

  List<PersonnelStatistic> personnelStatistic(Map<String, Object> item);

  int findDutyStatisticCount(Map<String, Object> item);

  int findPersonnelStatisticCount(Map<String, Object> item);

  WorkOrderBean gdOrderByOrder(String order);

  OrderBean gdOrderById(String string);

  COrderBean gdQueryOrderProductsByCOrderSn(String cOrderSn);

  List<ReviewPool> getnetOrderFroOrderId(String orderId);

/*  RegionBean getShippingTime(String region);

  PointBean getNetPoint(String pointId);*/

  String dataTransfer(int pageFind, int rowsFind, Map<String, Object> mapDate);

  void logError(String errorContent);

  Integer findPerformNum(Map<String, Object> map);

  void updateWorkOrder(ReviewPool dto);

  void updWorkOrderOne(ReviewPool dto);

  long JiSuanDate(String minDate, String maxDate) throws ParseException;

  void insertReviewpoolfordhzxMidd(Reviewpoolfordhzx reviewpoolfordhzx);

  void insertReviewpoolfordhzxResult(Reviewpoolfordhzx reviewpoolfordhzx);

  int findFeedBackNum(ReviewPool reviewPool);

  List<DutyStatistic> dutyStatistic(Map<String, Object> item);

  void updateSQMStatus(String ids);

}
