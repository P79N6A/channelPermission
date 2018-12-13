package com.haier.traderate.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.shop.dto.ReviewRegionCompanyDto;
import com.haier.shop.model.COrderBean;
import com.haier.shop.model.COrderBean.Result;
import com.haier.shop.model.DutyStatistic;
import com.haier.shop.model.OrderBean;
import com.haier.shop.model.PersonnelStatistic;
import com.haier.shop.model.ReviewDataDictionaryEntity;
import com.haier.shop.model.ReviewPool;
import com.haier.shop.model.WorkOrderBean;
import com.haier.shop.service.ReviewWorkOrderDataService;
import com.haier.shop.service.WoDictionaryDataService;
import com.haier.shop.util.ReviewConstants;
import com.haier.traderate.service.ReviewWorkOrderService;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工单信息操作接口实现
 *
 * @Filename: ReviewWorkOrderServiceImpl.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 */
@Service
public class ReviewWorkOrderServiceImpl implements ReviewWorkOrderService {

  private static Logger log = LogManager.getLogger(ReviewWorkOrderServiceImpl.class);

  @Resource
  private ReviewWorkOrderDataService reviewWorkOrderDataService;

  @Resource
  private WoDictionaryDataService woDictionaryDataService;
  /*
   * @Resource private NoticeService noticeService;
   */

  @Override
  public ServiceResult<JSONObject> page(ReviewPool pool, PagerInfo pager, String startTime,
      String endTime) {
    log.debug("ReviewWorkOrderServiceImpl.page 工单分页查询入参：" + "pool" + pool + "startTime" + startTime
        + "endTime"
        + endTime);
    ServiceResult<JSONObject> serviceResult = new ServiceResult<>();
    try {
      serviceResult
          .setResult(reviewWorkOrderDataService.findPageList(pool, pager, startTime, endTime));
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][page]取分页查询异常:", e);
    }
    log.debug("ReviewWorkOrderServiceImpl.page 工单分页查询完成");
    return serviceResult;
  }

  @Override
  public void updateReviewFroStoreId(String storeId) {
    reviewWorkOrderDataService.updateReviewFroStoreId(storeId);
  }


  /**
   * 多图片添加工单
   */
  @Override
  public ServiceResult<Boolean> addWorkOrder(ReviewPool reviewPool, String[] imgArr,
      String[] imgNameArr,
      String userName) {
    log.debug("ReviewWorkOrderServiceImpl.addWorkOrder 添加工单入参" + "reviewPool" + reviewPool);
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    try {
      String rId = reviewWorkOrderDataService
          .addWorkOrder(reviewPool, imgArr, imgNameArr, userName);
      serviceResult.setResult(true);
      serviceResult.setMessage("工单号为:" + rId);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][addReview]添加工单异常:", e);
    }
    log.debug("ReviewWorkOrderServiceImpl.addWorkOrder 添加工单完成");
    return serviceResult;
  }

  @Override
  public int getTplCount(ReviewPool reviewPool) {
    log.debug("ReviewWorkOrderServiceImpl.addWorkOrder 添加工单入参" + "reviewPool" + reviewPool);
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    int num = 0;
    try {

      num = reviewWorkOrderDataService.getTplCount(reviewPool);

    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][addReview]添加工单异常:", e);
    }
    log.debug("ReviewWorkOrderServiceImpl.addWorkOrder 添加工单完成");
    return num;
  }

  /**
   * 删除所有
   */
  @Override
  public ServiceResult<Boolean> delAll() {
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    try {
      reviewWorkOrderDataService.delAll();
      serviceResult.setResult(true);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][delAll]删除所有工单异常:", e);
    }
    return serviceResult;
  }

  /**
   * 获得满足条件的总条数
   */
  @Override
  public ServiceResult<Integer> searchPageCount(ReviewPool pool, String startTime, String endTime) {
    ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
    try {
      int data = reviewWorkOrderDataService.searchPageCount(pool, startTime, endTime);
      serviceResult.setResult(data);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][searchPageCount]获得满足条件的总工单条数信息异常:", e);
    }
    return serviceResult;
  }

  /**
   * 根据id 获得详情
   */
  @Override
  public ServiceResult<ReviewPool> findReviewById(ReviewPool pool) {
    ServiceResult<ReviewPool> serviceResult = new ServiceResult<ReviewPool>();
    try {
      ReviewPool data = reviewWorkOrderDataService.findReviewById(pool);
      serviceResult.setResult(data);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][findReviewById]根据网单号取工单信息异常:", e);
    }
    return serviceResult;
  }

  @Override
  public ServiceResult<List<ReviewPool>> getReviewPoolForMobile(ReviewPool pool) {
    ServiceResult<List<ReviewPool>> serviceResult = new ServiceResult<List<ReviewPool>>();
    try {
      List<ReviewPool> data = reviewWorkOrderDataService.getReviewPoolForMobile(pool);
      serviceResult.setResult(data);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][findReviewById]根据网单号取工单信息异常:", e);
    }
    return serviceResult;
  }

  /**
   * 查询netOrderId = #{netOrderId}的总条数
   */
  @Override
  public ServiceResult<Integer> qryFeedBackCount(String netOrderId) {
    ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
    try {
      int data = reviewWorkOrderDataService.qryFeedBackCount(netOrderId);
      serviceResult.setResult(data);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][qryFeedBackCount]获取相同网单号总条数异常:", e);
    }
    return serviceResult;
  }

  /**
   * 查询orderCome = 'SG' and workStatus = 0 and question1Level1 = #{question1Level1}
   */
  @Override
  public ServiceResult<List<ReviewPool>> queryNotifyReviewPools(String question1Level1) {
    ServiceResult<List<ReviewPool>> serviceResult = new ServiceResult<List<ReviewPool>>();
    try {
      List<ReviewPool> data = reviewWorkOrderDataService.queryNotifyReviewPools(question1Level1);
      serviceResult.setResult(data);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][queryNotifyReviewPools]查询指定责任位手工录入并且工单状态为未处理的数据时异常:",
          e);
    }
    return serviceResult;
  }

  /**
   * 根据条件查询insertTime的最大时间
   */
  @Override
  public ServiceResult<String> findMaxTime(String netOrderId, String question1Level2) {
    ServiceResult<String> serviceResult = new ServiceResult<String>();
    try {
      String data = reviewWorkOrderDataService.findMaxTime(netOrderId, question1Level2);
      serviceResult.setResult(data);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][findMaxTime]获取指定网单号指定责任位的最后添加时间异常:", e);
    }
    return serviceResult;
  }

  /**
   * 责任位统计
   */
  @Override
  public ServiceResult<List<DutyStatistic>> dutyStatistic(Map<String, Object> item) {
    ServiceResult<List<DutyStatistic>> serviceResult = new ServiceResult<List<DutyStatistic>>();
    try {
      List<DutyStatistic> data = reviewWorkOrderDataService.dutyStatistic(item);
      int total = reviewWorkOrderDataService.findDutyStatisticCount(item);
      PagerInfo pagerInfo = new PagerInfo();
      pagerInfo.setRowsCount(total);
      serviceResult.setPager(pagerInfo);
      serviceResult.setResult(data);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][dutyStatistic]责任位统计异常:", e);
    }
    return serviceResult;
  }

  /**
   * 人员统计
   */
  @Override
  public ServiceResult<List<PersonnelStatistic>> personnelStatistic(Map<String, Object> item) {
    ServiceResult<List<PersonnelStatistic>> serviceResult = new ServiceResult<List<PersonnelStatistic>>();
    try {
      List<PersonnelStatistic> data = reviewWorkOrderDataService.personnelStatistic(item);
      int total = reviewWorkOrderDataService.findPersonnelStatisticCount(item);
      PagerInfo pagerInfo = new PagerInfo();
      pagerInfo.setRowsCount(total);
      serviceResult.setPager(pagerInfo);
      serviceResult.setResult(data);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][personnelStatistic]人员统计异常:", e);
    }
    return serviceResult;
  }

  /**
   * 页面回车调用接口
   *
   * @param netOrderId 网单号
   */
  @Override
  public ServiceResult<WorkOrderBean> getInterface(String netOrderId) {

    ServiceResult<WorkOrderBean> serviceResult = new ServiceResult<WorkOrderBean>();
    try {
      if (netOrderId == null || "".equals(netOrderId.trim())) {
        throw new BusinessException("网单号不能为空!");
      }
      // 查询下单人账号
/*      List<ReviewPool> list = reviewWorkOrderDataService
          .getnetOrderFroOrderId(order.getResult().getOrderSn());
      if (list.size() != 0) {
        workOrderBean.setNextUserName(list.get(0).getNextUserName());
      }*/
      WorkOrderBean workOrderBean = reviewWorkOrderDataService.gdOrderByOrder(netOrderId);
      if(null == workOrderBean){
        serviceResult.setSuccess(false);
        serviceResult.setMessage("没有查询到数据");
        return serviceResult;
      }
      if("0".equals(workOrderBean.getNetPointId())){
        workOrderBean.setNetPointName("无");
      }
      workOrderBean.setNextUserName(workOrderBean.getBuyer());
      // 根据网单号查询出商铺ID，返回给页面
        ReviewDataDictionaryEntity company = woDictionaryDataService.getCompany(workOrderBean.getRegion());
        if (company != null){
            workOrderBean.setCompany(company.getValueMeaning());
        }
      ReviewDataDictionaryEntity channelCode = woDictionaryDataService
          .getChannelCode(workOrderBean.getSource());
      if (channelCode == null) {
        workOrderBean.setChannelCode("");
        workOrderBean.setChannelName("");
      } else {
        workOrderBean.setChannelCode(channelCode.getValue());
        workOrderBean.setChannelName(channelCode.getValueMeaning());
      }

      serviceResult.setResult(workOrderBean);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][personnelStatistic]人员统计异常:", e);
    }
    return serviceResult;
  }

  /**
   * 封装界面回车后的bean
   */
  private WorkOrderBean editBean(COrderBean corder, OrderBean order) {
    WorkOrderBean workOrderBean = new WorkOrderBean();
    workOrderBean.setCreateTime(order.getResult().getSyncTime()); // 下单时间
    // //没有
    workOrderBean.setPayTime(order.getResult().getPayTime());// 付款时间
    workOrderBean.setOrderSn(order.getResult().getOrderSn());// 订单号
    workOrderBean.setCenterType(corder.getResult().getsCode());// 物流中心类别
    workOrderBean.setsCode(corder.getResult().getsCode());// 存储sCode供判断是否发给SQM
    String regionName = order.getResult().getRegionName();
    if (regionName != null) {
      workOrderBean.setRegionName(regionName.replace("null", ""));// 所属区域
      // 因为发现有null的数据所以替换掉
    } else {
      workOrderBean.setRegionName("");
    }
    workOrderBean.setSource(order.getResult().getSource());// 订单来源
    // workOrderBean.setOrderPrimary(order.getResult().getId());//订单ID
    // workOrderBean.setCorderPrimary(corder.getResult().getId());//网单ID
    workOrderBean.setCorderSn(corder.getResult().getcOrderSn());// 网单号
    workOrderBean.setProductName(corder.getResult().getProductName());// 宝贝名称
    workOrderBean.setNumber(corder.getResult().getNumber());// 数量
    workOrderBean.setSku(corder.getResult().getSku());// SKU编码
    workOrderBean.setStoreType(corder.getResult().getStockType());// 库存类型
    workOrderBean.setTbOrderSn(corder.getResult().getTbOrderSn());
    workOrderBean.setProductAmount(corder.getResult().getProductAmount());// 发票金额(商品金额)
    workOrderBean.setBuyer(order.getResult().getConsignee());// 收货联系人consignee
    String mobile = order.getResult().getMobile();// 获取收货人手机
    if (mobile == null || "".equals(mobile)) {// 如果手机为空
      mobile = order.getResult().getPhone();// 获取收货人电话。
    }
    workOrderBean.setBuyerMobile(mobile);// 收货电话mobile
    // TODO
    // workOrderBean.setBuyerMobile("15668655555");
    String netPointId = corder.getResult().getNetPointId();
    WorkOrderBean orderBean = reviewWorkOrderDataService.gdOrderByOrder(netPointId);
    workOrderBean.setNetPointId(orderBean.getNetPointName());// 配送网点
    String shippingModeTmp = corder.getResult().getShippingMode();
    String shippingMode = shippingModeTmp != null ? shippingModeTmp.toUpperCase().trim() : "";
    if (ReviewConstants.SHIPPINGMODE.B2C.equals(shippingMode)
        || ReviewConstants.SHIPPINGMODE.B2B.equals(shippingMode)) {// 判断是否B2C模式
      // TODO 如果没有物流中心需要去数据库通过省市县查询对应工贸
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("provinceId", order.getResult().getProvince());
      map.put("cityId", order.getResult().getCity());
      map.put("regionId", order.getResult().getRegion());
      List<ReviewRegionCompanyDto> data = (List<ReviewRegionCompanyDto>) woDictionaryDataService
          .findPageRegionCompanyList(map, null).get("rows");
      if (data != null && data.size() > 0) {
        workOrderBean.setCompany(data.get(0).getCompanyName());
      } else {
        workOrderBean.setCompany("");
      }

    } else {
      // 通过sCode查询对应物流中心
      if (corder.getResult() != null) {
        String sCode = corder.getResult().getsCode();
        String tsCode = corder.getResult().getTsCode();
        if (isNumeric(sCode)) { // 如果物流中心是数字
          workOrderBean.setPhone(sCode);// 物流中心
          Map<String, Object> map = new HashMap<String, Object>();
          map.put("provinceId", order.getResult().getProvince());
          map.put("cityId", order.getResult().getCity());
          map.put("regionId", order.getResult().getRegion());
          List<ReviewRegionCompanyDto> data = (List<ReviewRegionCompanyDto>) woDictionaryDataService
              .findPageRegionCompanyList(map, null).get("rows");
          if (data != null && data.size() > 0) {
            workOrderBean.setCompany(data.get(0).getCompanyName());
          } else {
            workOrderBean.setCompany("");
          }
          List<ReviewDataDictionaryEntity> logisticsCenter = woDictionaryDataService
              .selectByValue("logistics_point", sCode);
          if (logisticsCenter != null && logisticsCenter.size() > 0) {
            workOrderBean.setNetPointId(logisticsCenter.get(0).getValueMeaning());// 配送网点
          }
          if (tsCode != null && !"".equals(tsCode)) {
            workOrderBean.setPhone(tsCode);
          }
        } else {
          if (tsCode != null && !"".equals(tsCode)) {
            sCode = tsCode;
          }
          List<ReviewDataDictionaryEntity> logisticsCenter = woDictionaryDataService
              .selectByValue("logistics_center", sCode);
          if (logisticsCenter != null && logisticsCenter.size() > 0) {
            workOrderBean.setPhone(logisticsCenter.get(0).getValueMeaning());// 物流中心
            // 通过物料中心查询对应工贸
            List<ReviewDataDictionaryEntity> company = woDictionaryDataService
                .selectByValue("company",
                    logisticsCenter.get(0).getParentValue() + "");
            if (company != null && company.size() > 0) {
              workOrderBean.setCompany(company.get(0).getValueMeaning());// 所属工贸
            } else {
              workOrderBean.setCompany("");
              // throw new BusinessException("未获取到对应工贸,工贸ID:" +
              // logisticsCenter.get(0).getOrderFlag());
            }
          } else {
            // throw new BusinessException("未获取到对应物流中心,物流中心ID:" +
            // sCode);
            workOrderBean.setPhone("");// 物流中心
            // workOrderBean.setCompany("");//没有物流中心也就没有工贸
            // TODO 如果没有物流中心需要去数据库通过省市县查询对应工贸
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("provinceId", order.getResult().getProvince());
            map.put("cityId", order.getResult().getCity());
            map.put("regionId", order.getResult().getRegion());
            List<ReviewRegionCompanyDto> data = (List<ReviewRegionCompanyDto>) woDictionaryDataService
                .findPageRegionCompanyList(map, null).get("rows");
            if (data != null && data.size() > 0) {
              workOrderBean.setCompany(data.get(0).getCompanyName());
            } else {
              workOrderBean.setCompany("");
            }
          }
        }
      }
    }
    workOrderBean.setAddress(order.getResult().getAddress());// 详细地址

    return workOrderBean;
  }

  /**
   * 订单查询接口
   */
  @Override
  public ServiceResult<OrderBean> gdOrderById(String orderId) {
    ServiceResult<OrderBean> serviceResult = new ServiceResult<>();
    try {
      WorkOrderBean order = reviewWorkOrderDataService.gdOrderByOrder(orderId);
      if (order == null) {
        throw new BusinessException("未获取到对应订单信息");
      }
      // 转订单状态
      List<ReviewDataDictionaryEntity> status = woDictionaryDataService
          .selectByValue("order_status",
              order.getStatus());
      String orderStatus = order.getStatus();
      if (status != null && status.size() > 0) {
        orderStatus = status.get(0).getValueMeaning();
      }
/*      Result result = order.getResult();
      result.setOrderStatus(orderStatus);
      order.setResult(result);*/
      serviceResult.setResult(new OrderBean());
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][gdOrderById]订单查询接口异常:", e);
    }
    return serviceResult;
  }

  /**
   * 网单查询接口
   */
  @Override
  public ServiceResult<COrderBean> gdQueryOrderProductsByCOrderSn(String corderId) {
    ServiceResult<COrderBean> serviceResult = new ServiceResult<>();
    try {
      WorkOrderBean wob = reviewWorkOrderDataService.gdOrderByOrder(corderId);
      // 转网单状态
      List<ReviewDataDictionaryEntity> status = woDictionaryDataService
          .selectByValue("c_order_status",
              wob.getNetOrderStatus());
      COrderBean corder = new COrderBean();
      Result result1 = new Result();
      corder.setResult(result1);
      if (status != null && status.size() > 0) {
        result1.setStatus(status.get(0).getValueMeaning());
      }
      result1.setcOrderSn(wob.getCorderSn());
      result1.setNumber(wob.getNumber());
      result1.setsCode(wob.getsCode());
      result1.setPrice(wob.getPrice());
      result1.setLessShipTime(wob.getLessShipTime());
      corder.setSuccess(true);
      serviceResult.setResult(corder);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][gdQueryOrderProductsByCOrderSn]网单查询接口异常:", e);
    }
    return serviceResult;
  }

  /**
   * 数据分页迁移
   *
   * @param page 第几页
   * @param rows 一页多少个
   */
  @Override
  public ServiceResult<String> dataTransfer(int page, int rows, Map<String, Object> mapDate) {
    ServiceResult<String> serviceResult = new ServiceResult<String>();
    try {
      // PagerInfo pager = new PagerInfo(page, rows);
      mapDate.put("pageSize", page);
      mapDate.put("pageIndex", rows);
      String data = reviewWorkOrderDataService.dataTransfer(page, rows, mapDate);
      serviceResult.setResult(data);
    } catch (Exception e) {
      log.error("[ReviewWorkOrderServiceImpl][dataTransfer]数据迁移异常:", e);
    }
    return serviceResult;
  }

  /**
   * 通过订单号查询订单接口
   */
  @Override
  public ServiceResult<OrderBean> gdOrderByOrder(String orderId) {
    ServiceResult<OrderBean> serviceResult = new ServiceResult<>();
    try {
      WorkOrderBean wob = reviewWorkOrderDataService.gdOrderByOrder(orderId);
      if (wob == null) {
        throw new BusinessException("未获取到对应订单信息");
      }
      // 转订单状态
      List<ReviewDataDictionaryEntity> status = woDictionaryDataService
          .selectByValue("order_status",
              wob.getStatus());
      OrderBean order = new OrderBean();
      order.setResult(new com.haier.shop.model.OrderBean.Result());
      if (status != null && status.size() > 0) {
        order.getResult().setOrderStatus(status.get(0).getValueMeaning());
      }
      BeanUtils.copyProperties(wob,order.getResult());
      order.setSuccess(true);
      serviceResult.setResult(order);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][gdOrderByOrder]订单查询接口异常:", e);
    }
    return serviceResult;
  }

  @Override
  public ServiceResult<Integer> findPerformNum(Map<String, Object> mapDate) {
    ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
    try {
      Integer order = reviewWorkOrderDataService.findPerformNum(mapDate);
      serviceResult.setResult(order);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][findPerformNum]数据迁移查询数量异常:", e);
    }
    return serviceResult;
  }

  @Override
  public ServiceResult<Boolean> updateWorkOrder(ReviewPool dto) {
    log.debug("ReviewWorkOrderServiceImpl.updateWorkOrder 修改工单入参：" + "reViewPool" + dto);
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    try {
      reviewWorkOrderDataService.updateWorkOrder(dto);

      serviceResult.setSuccess(true);
      serviceResult.setMessage("更新成功");
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
      throw new RuntimeException(be.getMessage());
    } catch (Exception e) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage("更新商城工单失败，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][updateReView]修改工单异常:", e);
      throw new RuntimeException(e.getMessage());
    }

    log.debug("ReviewWorkOrderServiceImpl.updeWorkOrder 修改工单完成");
    return serviceResult;
  }

  @Override
  public ServiceResult<Boolean> updateWorkOrderOne(ReviewPool dto) {

    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    try {
      String addTime = DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE);
      ReviewPool byReviewPool = reviewWorkOrderDataService.findReviewById(dto);

      if (null != byReviewPool.getPosition2() && !"".equals(byReviewPool.getPosition2())
          && null != byReviewPool.getWorkOrderTime() && !""
          .equals(byReviewPool.getWorkOrderTime())) {
        dto.setPosition2(byReviewPool.getWorkOrderTime());
        dto.setWorkOrderTime(addTime);
      } else {
        dto.setPosition2(addTime);
        dto.setWorkOrderTime(addTime);
      }

      if (byReviewPool.getWorkStatus().equals("0")) {
        dto.setWorkStatus("2");
      } else {
        dto.setWorkStatus(byReviewPool.getWorkStatus());
      }

      if (byReviewPool.getWorkStatus().equals("3")) {
        dto.setSqmStatus(null);
      }

      dto.setMiddleType("1");

      reviewWorkOrderDataService.updWorkOrderOne(dto);
      serviceResult.setSuccess(true);
      serviceResult.setMessage("更新成功");
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage("更新商城工单失败，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][updateWorkOrderOne]修改工单异常:", e);
    }
    log.debug("ReviewWorkOrderServiceImpl.updeWorkOrder 修改工单完成");
    return serviceResult;

  }

  @Override
  public ServiceResult<String> updateSQMStatus(String ids) {
    ServiceResult<String> serviceResult = new ServiceResult<String>();
    ReviewPool pool = new ReviewPool();
    String msg = "工单号:";
    try {
      String[] str = ids.split(",");
      for (int i = 0; i < str.length; i++) {
        pool.setId(str[i]);
        pool = reviewWorkOrderDataService.findReviewById(pool);
        if (null == pool) {
          msg = msg + str[i] + ";";
        }
      }
      if (msg.equals("工单号:")) {
        reviewWorkOrderDataService.updateSQMStatus(ids);
        serviceResult.setResult("更新成功");
      } else {
        serviceResult.setResult(msg + "不存在");
      }
    } catch (Exception e) {
      e.printStackTrace();
      serviceResult.setSuccess(false);
      serviceResult.setMessage("更新商城工单失败，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][updateSQMStatus]修改工单异常:", e);
    }
    return serviceResult;
  }

  @Override
  public ServiceResult<List<ReviewPool>> getnetOrderFroOrderId(String orderId) {

    ServiceResult<List<ReviewPool>> serviceResult = new ServiceResult<>();
    try {
      List<ReviewPool> lists = reviewWorkOrderDataService.getnetOrderFroOrderId(orderId);
      serviceResult.setResult(lists);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return serviceResult;
  }

  private static boolean isNumeric(String str) {
    for (int i = 0; i < str.length(); i++) {
      System.out.println(str.charAt(i));
      if (!Character.isDigit(str.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  @Override
  public ServiceResult<Boolean> updeWorkOrder(ReviewPool reViewPool, String[] imgArr,
      String[] imgNameArr,
      String userName) {
    log.debug("ReviewWorkOrderServiceImpl.updeWorkOrder 修改工单入参：" + "reViewPool" + reViewPool);
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    try {
      reviewWorkOrderDataService.updeWorkOrder(reViewPool, imgArr, imgNameArr, userName);
      serviceResult.setResult(true);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewWorkOrderServiceImpl][updReView]修改工单异常:", e);
    }

    log.debug("ReviewWorkOrderServiceImpl.updeWorkOrder 修改工单完成");
    return serviceResult;
  }


}
