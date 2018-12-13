package com.haier.svc.api.controller.workorder;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.shop.dto.ThirdPartyLiabilityCondition;
import com.haier.shop.model.COrderBean;
import com.haier.shop.model.DutyStatistic;
import com.haier.shop.model.OrderBean;
import com.haier.shop.model.PersonnelStatistic;
import com.haier.shop.model.ReviewPool;
import com.haier.shop.model.Reviewpoolfordhzx;
import com.haier.shop.model.WorkOrderBean;
import com.haier.shop.util.ReviewConstants;
import com.haier.svc.api.controller.util.ReviewPropertyHolders;
import com.haier.svc.api.controller.util.Sign;
import com.haier.svc.api.controller.util.WebReviewConstants;
import com.haier.traderate.service.ReviewWorkOrderService;
import com.haier.traderate.service.WoDictionaryService;
import com.haier.traderate.service.WoReviewpoolfordhzxService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 工单表Controller
 *
 * @Filename: WebReviewWorkOrderController.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 */
@Controller
@RequestMapping(value = "/workorder", produces = "text/html;charset=UTF-8")
public class WebReviewWorkOrderController {



  private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
      .getLogger(WebReviewWorkOrderController.class);
  @Resource
  private ReviewWorkOrderService reviewWorkOrderService;

  @Resource
  private WoDictionaryService woDictionaryService;
  @Resource
  private WoReviewpoolfordhzxService woReviewpoolfordhzxService;

  private static Logger psilog = LoggerFactory.getLogger("psilogger");

  @GetMapping("/index")
  public String orderList(){
    return "workorder/index";
  }

  /**
   * 责任统计
   */
  @RequestMapping(value = {"/dutystatistic.ajax"}, method = {RequestMethod.POST, RequestMethod.GET})
  @ResponseBody
  public String dutyStatistic(
      @RequestParam(value = "startTime_search2", required = false) String startTime,
      @RequestParam(value = "endTime_search2", required = false) String endTime,
      @RequestParam(value = "level1_search2", required = false) String question1Level1,
      @RequestParam(value = "page", required = false) Integer no,
      @RequestParam(value = "rows", required = false) Integer size, HttpServletRequest request) {
    ServiceResult<String> resultService = new ServiceResult<String>();
    try {
      Map<String, Object> map = new HashMap<String, Object>();
      if (startTime != null && !"".equals(startTime)) {
        map.put("startTime", startTime);
      }
      if (endTime != null && !"".equals(endTime)) {
        map.put("endTime", endTime);
      }
      if (question1Level1 != null && !"".equals(question1Level1)) {
        map.put("question1Level1", question1Level1);
      }
      PagerInfo pager = new PagerInfo(size, no);
      map.put("page", pager);
      ServiceResult<List<DutyStatistic>> result = reviewWorkOrderService.dutyStatistic(map);
      // 创建一个Map,为了适应前台jquery easyUi语法
      Map<String, Object> retMap = new HashMap<String, Object>();
      retMap.put("total", result.getPager().getRowsCount());
      retMap.put("rows", result.getResult());
      if (result.getSuccess()) {
        return JsonUtil.toJson(retMap);
      } else {
        return result.getMessage();
      }
    } catch (Exception e) {
      return JsonUtil.toJson(resultService);
    }
  }

  /**
   * 人员统计
   */
  @RequestMapping(value = {"/personnelstatistic.ajax"}, method = {RequestMethod.POST,
      RequestMethod.GET})
  @ResponseBody
  public String personnelStatistic(
      @RequestParam(value = "startTime_search3", required = false) String startTime,
      @RequestParam(value = "endTime_search3", required = false) String endTime,
      @RequestParam(value = "person_search3", required = false) String wangId,
      @RequestParam(value = "page", required = false) Integer no,
      @RequestParam(value = "rows", required = false) Integer size, HttpServletRequest ser) {
    ServiceResult<String> resultService = new ServiceResult<String>();
    try {
      Map<String, Object> map = new HashMap<String, Object>();
      if (startTime != null && !"".equals(startTime)) {
        map.put("startTime", startTime);
      }
      if (endTime != null && !"".equals(endTime)) {
        map.put("endTime", endTime);
      }
      if (wangId != null && !"".equals(wangId)) {
        map.put("wangId", wangId);
      }
      PagerInfo pager = new PagerInfo(size, no);
      map.put("page", pager);
      ServiceResult<List<PersonnelStatistic>> result = reviewWorkOrderService
          .personnelStatistic(map);

      // 创建一个Map,为了适应前台jquery easyUi语法
      Map<String, Object> retMap = new HashMap<>();
      retMap.put("total", result.getPager().getRowsCount());
      retMap.put("rows", result.getResult());
      if (result.getSuccess()) {
        return JsonUtil.toJson(retMap);

      } else {
        return result.getMessage();
      }
    } catch (Exception e) {
      resultService.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
      log.error(WebReviewConstants.SYSTEM_ERROR, e);
      return JsonUtil.toJson(resultService);
    }
  }

  @RequestMapping(value = "/getReviewPoolForDhzxList", method = RequestMethod.POST)
  @ResponseBody
  public String getReviewPoolForDhzxLists(
      @RequestParam(value = "rows", required = false) Integer size,
      @RequestParam(value = "page", required = false) Integer no,
      @RequestParam(value = "username_search", required = false) String userName,
      @RequestParam(value = "phone1_search", required = false) String phone1,
      @RequestParam(value = "phone2_search", required = false) String phone2,
      @RequestParam(value = "phone3_search", required = false) String phone3,
      @RequestParam(value = "messageNum_search", required = false) String messageNum,
      @RequestParam(value = "workStatus_search", required = false) String workStatus,
      @RequestParam(value = "type_search", required = false) String type,
      @RequestParam(value = "store_search", required = false) String store,
      @RequestParam(value = "startTime_search", required = false) String startTime,
      @RequestParam(value = "endTime_search", required = false) String endTime,
      @RequestParam(value = "storeType_search", required = false) String storeType

  ) {
    Reviewpoolfordhzx r = new Reviewpoolfordhzx();
    PagerInfo pager = new PagerInfo(size, no);
    if (null != userName && !"".equals(userName)) {
      r.setUserName(userName);
    }
    if (null != phone1 && !"".equals(phone1)) {
      r.setPhone1(phone1);
    }
    if (null != phone2 && !"".equals(phone2)) {
      r.setPhone2(phone2);
    }
    if (null != phone3 && !"".equals(phone3)) {
      r.setPhone3(phone3);
    }
    if (null != messageNum && !"".equals(messageNum)) {
      r.setMessageNum(messageNum);
    }
    if (workStatus.equals("未处理")) {
      r.setWorkStatus("0");
    }
    if (workStatus.equals("中间结果")) {
      r.setWorkStatus("2");
    }
    if (workStatus.equals("最终结果")) {
      r.setWorkStatus("3");
    }
    if (type.equals("物流类工单")) {
      r.setType("0");
    }
    if (type.equals("店铺类工单")) {
      r.setType("1");
    }
    if (type.equals("售后类工单")) {
      r.setType("2");
    }
    if (store != null && !"".equals(store)) {
      r.setStore(store);

    }
    if (startTime != null && !"".equals(startTime)) {
      r.setStartTime(startTime);
    }
    if (endTime != null && !"".equals(endTime)) {
      r.setEndTime(endTime);
    }
    if (storeType != null && !"".equals(storeType)) {
      r.setStoreType(storeType);
    }
    ServiceResult<List<Reviewpoolfordhzx>> result = woReviewpoolfordhzxService
        .pageReviewpoolfordhzx(r, pager);
    // 创建一个Map,为了适应前台jquery easyUi语法
    Map<String, Object> retMap = new HashMap<String, Object>();
    retMap.put("total", result.getPager().getRowsCount());
    retMap.put("rows", result.getResult());

    if (result.getSuccess()) {
      return JsonUtil.toJson(retMap);
    } else {
      return result.getMessage();
    }

  }

  /**
   * 分页查询工单信息
   */
  @RequestMapping(value = {"/workorderpage.ajax"}, method = {RequestMethod.POST, RequestMethod.GET})
  @ResponseBody
  public String reViewPage(
      @RequestParam(value = "orderId_search", required = false) String orderId_search,
      @RequestParam(value = "orderCome_search", required = false) String orderCome_search,
      @RequestParam(value = "netOrderId_search", required = false) String netOrderId,
      @RequestParam(value = "workStatus", required = false) String workStatus,
      @RequestParam(value = "startTime_search", required = false) String startTime,
      @RequestParam(value = "endTime_search", required = false) String endTime,
      @RequestParam(value = "level1_search", required = false) String level1Search,
      @RequestParam(value = "level2_search", required = false) String level2Search,
      @RequestParam(value = "level3_search", required = false) String level3Search,
      @RequestParam(value = "category1_search", required = false) String categoryLevelOne,
      @RequestParam(value = "category2_search", required = false) String categoryLevelTwo,
      @RequestParam(value = "category3_search", required = false) String categoryLevelThree,
      @RequestParam(value = "company_search", required = false) String company,
      @RequestParam(value = "phone_search", required = false) String phone,
      @RequestParam(value = "person_search", required = false) String wangId,
      @RequestParam(value = "addsearch_search", required = false) String remark3,
      @RequestParam(value = "remark5_search", required = false) String remark5,
      @RequestParam(value = "rows", required = false) Integer size,
      @RequestParam(value = "clientName", required = false) String clientName,
      @RequestParam(value = "clientTel", required = false) String clientTel,
      @RequestParam(value = "page", required = false) Integer no,
      @RequestParam(value = "closeType_search", required = false) String closeType,
      @RequestParam(value = "complaintFlg_search", required = false) Integer complaintFlg,
      @RequestParam(value = "id_search", required = false) String id,
      @RequestParam(value = "workOrderTo", required = false) String workOrderTo,
      @RequestParam(value = "cType", required = false) String cType,
      @RequestParam(value = "storeId_search", required = false) String storeId,
      @RequestParam(value = "channelName_search", required = false) String channelName,
      HttpServletRequest request, HttpServletResponse response) {
    ServiceResult<String> resultService = new ServiceResult<String>();
    try {
      ReviewPool reViewPool = new ReviewPool();
      if (id != null && !"".equals(id)) {
        reViewPool.setId(id);
      }
      if (cType != null && !"".equals(cType)) {
        reViewPool.setCenterType(cType);
      }
      if (closeType != null && !"".equals(closeType)) {
        reViewPool.setCloseType(closeType);
      }
      if (orderId_search != null && !"".equals(orderId_search)) {
        reViewPool.setOrderId(orderId_search);
      }
      if (orderCome_search != null && !"".equals(orderCome_search)) {
        reViewPool.setOrderCome(orderCome_search);
      }
      if (netOrderId != null && !"".equals(netOrderId)) {
        reViewPool.setNetOrderId(netOrderId);
      }
      if (workStatus != null && !"".equals(workStatus)) {
        reViewPool.setWorkStatus(workStatus);
      }
      if (level1Search != null && !"".equals(level1Search)) {
        reViewPool.setQuestion1Level1(level1Search);
      }
      if (level2Search != null && !"".equals(level2Search)) {
        reViewPool.setQuestion1Level2(level2Search);
      }
      if (level3Search != null && !"".equals(level3Search)) {
        reViewPool.setQuestion1Level3(level3Search);
      }
      if (company != null && !"".equals(company)) {
        reViewPool.setCompany(company);
      }
      if (phone != null && !"".equals(phone)) {
        reViewPool.setPhone(phone);
      }
      if (wangId != null && !"".equals(wangId)) {
        reViewPool.setWangId(wangId);
      }
      if (remark3 != null && !"".equals(remark3)) {
        reViewPool.setRemark3(remark3);
      }
      if (remark5 != null && !"".equals(remark5)) {
        reViewPool.setRemark5(remark5);
      }
      if (workOrderTo != null && !"".equals(workOrderTo)) {
        reViewPool.setWorkOrderTo(workOrderTo);
      }
      if ("".equals(startTime)) {
        startTime = null;
      }
      if ("".equals(endTime)) {
        endTime = null;
      }
      if (clientName != null && !"".equals(clientName)) {
        reViewPool.setBuyer(clientName);
      }
      if (clientTel != null && !"".equals(clientTel)) {
        reViewPool.setBuyerMobile(clientTel);
      }
      if (complaintFlg != null) {
        reViewPool.setComplaintFlg(complaintFlg);// 是否投诉
      }
      if (categoryLevelOne != null && !"".equals(categoryLevelOne)) {
        reViewPool.setCategoryLevelOne(categoryLevelOne);
      }
      if (categoryLevelTwo != null && !"".equals(categoryLevelTwo) && !"请选择"
          .equals(categoryLevelTwo)) {
        reViewPool.setCategoryLevelTwo(categoryLevelTwo);
      }
      if (categoryLevelThree != null && !"".equals(categoryLevelThree) && !"请选择"
          .equals(categoryLevelThree)) {
        reViewPool.setCategoryLevelThree(categoryLevelThree);
      }
      if (storeId != null && !"".equals(storeId)) {
        reViewPool.setStoreId(storeId);
      }
      if (channelName != null && !"".equals(channelName)) {
        if (channelName.equals("无渠道")) {
          reViewPool.setChannelName("");
        } else {
          reViewPool.setChannelName(channelName);
        }
      }
      PagerInfo pager = new PagerInfo(size, no);
      /*
       * ServiceResult<List<Reviewpoolfordhzx>> result =
       * reviewWorkOrderService.pageReviewpoolfordhzx(null, pager);
       */
      ServiceResult<JSONObject> result = reviewWorkOrderService
          .page(reViewPool, pager, startTime,
              endTime);
      // 创建一个Map,为了适应前台jquery easyUi语法
      if (result.getSuccess()) {
        return JsonUtil.toJson(result.getResult());
      } else {
        return result.getMessage();
      }
    } catch (Exception e) {
      resultService.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
      log.error(WebReviewConstants.SYSTEM_ERROR, e);
      return JsonUtil.toJson(resultService);
    }
  }

  @RequestMapping(value = {"/getWorkOrderPage"}, method = {RequestMethod.POST, RequestMethod.GET})
  @ResponseBody
  public String getWorkOrderPage(
      @RequestParam(value = "orderId_search", required = false) String orderId_search,
      @RequestParam(value = "orderCome_search", required = false) String orderCome_search,
      @RequestParam(value = "netOrderId_search", required = false) String netOrderId,
      @RequestParam(value = "workStatus", required = false) String workStatus,
      @RequestParam(value = "startTime_search", required = false) String startTime,
      @RequestParam(value = "endTime_search", required = false) String endTime,
      @RequestParam(value = "level1_search", required = false) String level1Search,
      @RequestParam(value = "level2_search", required = false) String level2Search,
      @RequestParam(value = "level3_search", required = false) String level3Search,
      @RequestParam(value = "workOrderTo", required = false) String workOrderTo,
      @RequestParam(value = "company_search", required = false) String company,
      @RequestParam(value = "phone_search", required = false) String phone,
      @RequestParam(value = "person_search", required = false) String wangId,
      @RequestParam(value = "addsearch_search", required = false) String remark3,
      @RequestParam(value = "remark5_search", required = false) String remark5,
      @RequestParam(value = "rows", required = false) Integer size,
      @RequestParam(value = "clientName", required = false) String clientName,
      @RequestParam(value = "clientTel", required = false) String clientTel,
      @RequestParam(value = "page", required = false) Integer no,
      @RequestParam(value = "closeType_search", required = false) String closeType,
      @RequestParam(value = "complaintFlg_search", required = false) Integer complaintFlg,
      @RequestParam(value = "id_search", required = false) String id,
      @RequestParam(value = "params", required = false) String params, HttpServletRequest request,
      HttpServletResponse response) {
    ServiceResult<String> resultService = new ServiceResult<String>();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    if (params != null && !"".equals(params)) {
      try {
        paramMap = JSON.parse(params, Map.class);
      } catch (ParseException e) {
        resultService.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "顺逛传入参数格式错误！");
        return JsonUtil.toJson(resultService);
      }
    }
    Map<String, Object> signMap = new HashMap<String, Object>();
    signMap.put("appKey", ReviewPropertyHolders.getContextProperty("words.appKey"));
    signMap.put("storeId", paramMap.get("storeId"));
    signMap.put("userName", paramMap.get("userName"));
    String sign = Sign
        .buildParams(signMap, ReviewPropertyHolders.getContextProperty("words.secretKey"));
    String pageSign = "";
    if (paramMap.get("sign") != null) {
      pageSign = paramMap.get("sign").toString();
    }
    if (pageSign.equals("") || !pageSign.equals(sign)) {
      resultService.setError(WebReviewConstants.SYSTEM_ERROR_CODE, "签名错误");
      return JsonUtil.toJson(resultService);
    }
    try {
      ReviewPool reViewPool = new ReviewPool();
      if (id != null && !"".equals(id)) {
        reViewPool.setId(id);
      }
      if (closeType != null && !"".equals(closeType)) {
        reViewPool.setCloseType(closeType);
      }
      if (orderId_search != null && !"".equals(orderId_search)) {
        reViewPool.setOrderId(orderId_search);
      }
      if (orderCome_search != null && !"".equals(orderCome_search)) {
        reViewPool.setOrderCome(orderCome_search);
      }
      if (netOrderId != null && !"".equals(netOrderId)) {
        reViewPool.setNetOrderId(netOrderId);
      }
      if (workStatus != null && !"".equals(workStatus)) {
        reViewPool.setWorkStatus(workStatus);
      }
      if (level1Search != null && !"".equals(level1Search)) {
        reViewPool.setQuestion1Level1(level1Search);
      }
      if (level2Search != null && !"".equals(level2Search)) {
        reViewPool.setQuestion1Level2(level2Search);
      }
      if (level3Search != null && !"".equals(level3Search)) {
        reViewPool.setQuestion1Level3(level3Search);
      }
      if (workOrderTo != null && !"".equals(workOrderTo)) {
        reViewPool.setWorkOrderTo(workOrderTo);
      }
      if (company != null && !"".equals(company)) {
        reViewPool.setCompany(company);
      }
      if (phone != null && !"".equals(phone)) {
        reViewPool.setPhone(phone);
      }
      if (wangId != null && !"".equals(wangId)) {
        reViewPool.setWangId(wangId);
      }
      if (remark3 != null && !"".equals(remark3)) {
        reViewPool.setRemark3(remark3);
      }
      if (remark5 != null && !"".equals(remark5)) {
        reViewPool.setRemark5(remark5);
      }
      if ("".equals(startTime)) {
        startTime = null;
      }
      if ("".equals(endTime)) {
        endTime = null;
      }
      if (clientName != null && !"".equals(clientName)) {
        reViewPool.setBuyer(clientName);
      }
      if (clientTel != null && !"".equals(clientTel)) {
        reViewPool.setBuyerMobile(clientTel);
      }
      if (complaintFlg != null) {
        reViewPool.setComplaintFlg(complaintFlg);// 是否投诉
      }
      if (paramMap.get("storeId") != null && !"".equals(paramMap.get("storeId"))) {
        reViewPool.setStoreId(paramMap.get("storeId").toString());
      }

      PagerInfo pager = new PagerInfo(size, no);
      ServiceResult<JSONObject> result = reviewWorkOrderService
          .page(reViewPool, pager, startTime,
              endTime);
      // 创建一个Map,为了适应前台jquery easyUi语法

      if (result.getSuccess()) {
        reviewWorkOrderService.updateReviewFroStoreId(paramMap.get("storeId").toString());
        return JsonUtil.toJson(result.getResult());
      } else {
        return result.getMessage();
      }
    } catch (Exception e) {

      resultService.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
      log.error(WebReviewConstants.SYSTEM_ERROR, e);

      return JsonUtil.toJson(resultService);
    }
  }

  /**
   * 修改
   */
  @ResponseBody
  @RequestMapping(value = {"/updateworkorder.ajax"}, method = {RequestMethod.POST})
  public String updateReView(ReviewPool reViewPool, HttpServletRequest request,
      @RequestParam(value = "imgArr[]", required = false) String[] imgArr,
      @RequestParam(value = "imgNameArr[]", required = false) String[] imgNameArr,
      HttpServletResponse response) {
    ServiceResult<String> resultService = new ServiceResult<String>();

    String[] paraImgArr = null;
    String[] paraImgNameArr = null;
    Map<String, String[]> map = request.getParameterMap();
    for (Entry<String, String[]> entry : map.entrySet()) {
      if (entry.getKey().equals("imgArr[]")) {
        paraImgArr = entry.getValue();
      }
      if (entry.getKey().equals("imgNameArr[]")) {
        paraImgNameArr = entry.getValue();
      }
    }
    try {
      log.debug("WebReviewWorkOrderController.updateReView 修改工单入参：" + "reViewPool" + reViewPool);
      ServiceResult<String> serviceResult = new ServiceResult<String>();

      if (reViewPool == null) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "参数不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      if (reViewPool.getNetOrderId() == null || "".equals(reViewPool.getNetOrderId())) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "网单编号不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      if (reViewPool.getWangId() == null || "".equals(reViewPool.getWangId().trim())) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "人员不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      if (reViewPool.getRemark4() == null || "".equals(reViewPool.getRemark4().trim())) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "人员ID不能为空！");
        return JsonUtil.toJson(serviceResult);
      }

      ReviewPool r = new ReviewPool();
      r.setId(reViewPool.getId());

      ServiceResult<ReviewPool> review = reviewWorkOrderService.findReviewById(r);
      if (!review.getResult().getQuestion1Level1().equals(reViewPool.getQuestion1Level1())
          || !review.getResult().getQuestion1Level2().equals(reViewPool.getQuestion1Level2())
          || !review.getResult().getQuestion1Level3().equals(reViewPool.getQuestion1Level3())

          ) {
        if (review.getResult().getWorkOrderTo().equals("SQM")) {
          serviceResult.setSuccess(false);
          serviceResult.setMessage("该工单输入SQM工单,禁止修改责任位");
          return JsonUtil.toJson(serviceResult);
        }

        if (review.getResult().getWorkOrderTo().equals("HP")) {
          serviceResult.setSuccess(false);
          serviceResult.setMessage("该工单输入HP工单,禁止修改责任位");
          return JsonUtil.toJson(serviceResult);
        } else {

          ThirdPartyLiabilityCondition third = new ThirdPartyLiabilityCondition();
          third.setQuestion1Level1(reViewPool.getQuestion1Level1());
          third.setQuestion1Level2(reViewPool.getQuestion1Level2());
          third.setQuestion1Level3(reViewPool.getQuestion1Level3());
          third = woDictionaryService.findInfoFromThird(third);
          if (third != null) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage("该工单是普通工单,禁止修改为SQM,或者HP工单");
            return JsonUtil.toJson(serviceResult);
          }

        }
      }

      // 从cookie中获取用户登录名
      String userName = this.getUserName(request);
      if (StringUtils.isEmpty(userName)) {
        serviceResult
            .setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
        return JsonUtil.toJson(serviceResult);
      }
      // 从cookie中获取用户登录名结束
      // String userName = "修改人";

      reViewPool.setSqmStatus(null);

      if (null != reViewPool.getDesidePass() && !"".equals(reViewPool.getDesidePass())) {

        if (review.getResult().getWorkOrderTo().equals("SQM")) {
          reViewPool.setDesideText("已操作判定结果,等待推送SQM");
        }
        if (review.getResult().getWorkOrderTo().equals("HP")) {
          reViewPool.setDesideText("已操作判定结果,等待推送HP");

        }
      }

      ServiceResult<Boolean> data = reviewWorkOrderService
          .updeWorkOrder(reViewPool, paraImgArr, paraImgNameArr,
              userName);
      if (data != null && data.getSuccess()) {

        // 工单关单后，如果需要给SQM且判定不通过，需要重新创建一单

        ServiceResult<WorkOrderBean> result = reviewWorkOrderService
            .getInterface(reViewPool.getNetOrderId());

        serviceResult.setResult("保存成功！");
        log.debug("WebReviewWorkOrderController.updateReView 修改工单完成");
        return JsonUtil.toJson(serviceResult);
      }

      serviceResult.setError(data.getCode(), data.getMessage());
      return JsonUtil.toJson(serviceResult);
    } catch (Exception e) {
      resultService.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
      log.error(WebReviewConstants.SYSTEM_ERROR, e);
      return JsonUtil.toJson(resultService);
    }
  }

  // 2017.2.23 增加了从顺逛接收的params参数
  @ResponseBody
  @RequestMapping(value = {"/updateReview"}, method = {RequestMethod.POST})
  public String updateReview(
      @RequestParam(value = "reViewPool", required = false) String reViewPool,
      HttpServletRequest request, @RequestParam(value = "imgArr[]", required = false) String imgArr,
      HttpServletResponse response, String params) {

    ReviewPool pool = null;
    ServiceResult<String> serviceResult = new ServiceResult<String>();
    Map<String, Object> paramMap = new HashMap<String, Object>();
    try {
      pool = JSON.parse(reViewPool, ReviewPool.class);
    } catch (ParseException e1) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "顺逛传入参数格式错误！");
      return JsonUtil.toJson(serviceResult);
    }

    if (params != null && !"".equals(params)) {
      try {
        paramMap = JSON.parse(params, Map.class);
      } catch (ParseException e) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "顺逛传入参数格式错误！");
        return JsonUtil.toJson(serviceResult);
      }
    }
    // 接收到顺逛中的参数后，重新加密sign，与入参sign比较，不一致则为签名错误。
    Map<String, Object> signMap = new HashMap<String, Object>();
    signMap.put("appKey", ReviewPropertyHolders.getContextProperty("words.appKey"));
    signMap.put("storeId", paramMap.get("storeId"));
    signMap.put("userName", paramMap.get("userName"));
    String sign = Sign
        .buildParams(signMap, ReviewPropertyHolders.getContextProperty("words.secretKey"));
    String pageSign = "";
    if (paramMap.get("sign") != null) {
      pageSign = paramMap.get("sign").toString();
    }
    if (pageSign.equals("") || !pageSign.equals(sign)) {
      serviceResult.setError(WebReviewConstants.SYSTEM_ERROR_CODE, "签名错误");
      return JsonUtil.toJson(serviceResult);
    }

    try {
      log.debug("WebReviewWorkOrderController.updateWorkOrder 修改工单入参：" + "reviewPool" + pool);

      if (pool == null) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "参数不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      if (pool.getNetOrderId() == null || "".equals(pool.getNetOrderId())) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "网单编号不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      if (pool.getWangId() == null || "".equals(pool.getWangId().trim())) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "人员不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      if (pool.getRemark4() == null || "".equals(pool.getRemark4().trim())) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "人员ID不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      if (paramMap.get("userName") == null || "".equals(paramMap.get("userName"))) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "更新人不能为空！");
        return JsonUtil.toJson(serviceResult);
      }

      String userName = paramMap.get("userName").toString();
      pool.setSqmState(null);
      // ServiceResult<Boolean> data =
      // reviewWorkOrderService.updeWorkOrder(pool,
      // imgArr, userName);
      // if (data != null && data.getSuccess()) {
      // serviceResult.setResult("保存成功！");
      // log.debug("WebReviewWorkOrderController.updateReView 修改工单完成");
      // return JsonUtil.toJson(serviceResult);
      // }
      // serviceResult.setError(data.getCode(), data.getMessage());
      return JsonUtil.toJson(serviceResult);
    } catch (Exception e) {
      serviceResult.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
      log.error(WebReviewConstants.SYSTEM_ERROR, e);
      return JsonUtil.toJson(serviceResult);
    }
  }

  // 接收SQM的反馈信息
  @ResponseBody
  @RequestMapping(value = "/receiveReactFromSQM", method = RequestMethod.POST, consumes = "application/json")
  // @RequestMapping(value = { "/receiveReactFromSQM" }, method = {
  // RequestMethod.POST })
  public String receiveReactFromSQM(HttpServletRequest request, HttpServletResponse response,
      @RequestBody String params) {
    ServiceResult<String> serviceResult = new ServiceResult<String>();
    // SQM返回的工单号和反馈内容，需要更新到reviwepool中的中间结果 backContext2
    String orderId = "";
    String content = "";
    String acceptCenter = "";
    String acceptUser = "";
    Map<String, String> paramMap = new HashMap<String, String>();
    try {
      paramMap = JSON.parse(params, Map.class);
    } catch (ParseException e1) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "SQM传入参数格式错误！");
      return JsonUtil.toJson(serviceResult);
    }
    orderId = paramMap.get("clientId");
    content = paramMap.get("content");
    acceptCenter = paramMap.get("acceptCenter");
    acceptUser = paramMap.get("acceptUser");
    log.debug("clientId" + orderId + ",,,,,,content" + content);
    log.info("sqm反馈-------------------------------------------------------");
    log.info("content=" + content);

    try {
      if ("".equals(orderId) || "".equals(content)) {
        serviceResult.setMessage("");
        serviceResult
            .setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "工单号或反馈内容为空，请确认！");
        return JsonUtil.toJson(serviceResult);
      } else {
        ReviewPool update = new ReviewPool();
        update.setBackContext2(content);
        update.setId(orderId);
        update.setDesideText("SQM已返回投诉结果，等待发送判定结果");
        update.setSqmStatus("3");// 已接收到返回结果
        update.setRemark1("SQM受理人:" + acceptUser + "  所属工贸:" + acceptCenter);

        ServiceResult<ReviewPool> review = reviewWorkOrderService.findReviewById(update);
        if (review.getResult().getWorkStatus().equals("3")) {
          serviceResult.setResult("工单已经关单,不能反馈中间结果！");
          serviceResult.setSuccess(true);
          return JsonUtil.toJson(serviceResult);
        } else {
          ServiceResult<Boolean> b = reviewWorkOrderService.updateWorkOrder(update);

          if (b.getSuccess() == true) {
            serviceResult.setResult("反馈成功！");
            serviceResult.setSuccess(true);
          } else {
            serviceResult.setResult("反馈失败！");
            serviceResult.setSuccess(false);
          }

          log.debug("SQM反馈工单完成" + orderId);
          return JsonUtil.toJson(serviceResult);
        }
      }
    } catch (Exception e) {
      serviceResult.setMessage("");
      serviceResult
          .setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "工单号或反馈内容为空，请确认！");
      return JsonUtil.toJson(serviceResult);
    }

  }

  /**
   * 添加工单
   */
  @ResponseBody
  @RequestMapping(value = {"/addworkorder.ajax"}, method = {RequestMethod.POST})
  public String getReView(ReviewPool reViewPool, HttpServletRequest request,
      @RequestParam(value = "imgArr[]", required = false) String[] imgArr,
      @RequestParam(value = "imgNameArr[]", required = false) String[] imgNameArr,
      HttpServletResponse response) {
    ServiceResult<String> resultService = new ServiceResult<String>();

    String[] paraImgArr = null;
    String[] paraImgNameArr = null;
    Map<String, String[]> map = request.getParameterMap();
    for (Entry<String, String[]> entry : map.entrySet()) {
      if (entry.getKey().equals("imgArr[]")) {
        paraImgArr = entry.getValue();
      }
      if (entry.getKey().equals("imgNameArr[]")) {
        paraImgNameArr = entry.getValue();
      }
    }

    try {
      log.debug("WebReviewWorkOrderController.getReView 添加工单入参：" + "reViewPool" + reViewPool);
      ServiceResult<String> serviceResult = new ServiceResult<String>();
      if (reViewPool == null) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "参数不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      if (StringUtils.isEmpty(reViewPool.getWangId())) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "人员不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      if (reViewPool.getRemark4() == null || "".equals(reViewPool.getRemark4().trim())) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "人员ID不能为空！");
        return JsonUtil.toJson(serviceResult);
      }

      // 从cookie中获取用户登录名
      String userName = this.getUserName(request);
      if (StringUtils.isEmpty(userName)) {
        serviceResult
            .setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
        return JsonUtil.toJson(serviceResult);
      }
      // 从cookie中获取用户登录名结束
      // 新添加的工单准备传到SQM
      // ServiceResult<WorkOrderBean> result =
      // reviewWorkOrderService.getInterface(reViewPool.getNetOrderId());
      // 物流信息以WA结尾的工单要标记为发给SQM
      reViewPool.setSqmCount("0");

      ThirdPartyLiabilityCondition third = new ThirdPartyLiabilityCondition();
      third.setQuestion1Level1(reViewPool.getQuestion1Level1());
      third.setQuestion1Level2(reViewPool.getQuestion1Level2());
      third.setQuestion1Level3(reViewPool.getQuestion1Level3());
      third = woDictionaryService.findInfoFromThird(third);
      if (third == null) {
        System.out.println("普通的单子");
        reViewPool.setSqmStatus("4");
        reViewPool.setWorkOrderTo("");
      } else {
        if (reViewPool.getCenterType().endsWith("WA") && null != reViewPool.getRemark5()
            && (reViewPool.getRemark5().equals("微店") || reViewPool.getRemark5().equals("海尔创客_PC端")
            || reViewPool.getRemark5().equals("商城订单-分销渠道") || reViewPool.getRemark5().equals("商城订单")
            || reViewPool.getRemark5().equals("移动商城")
            || reViewPool.getRemark5().equals("商城订单-大学生项目订单")
            || reViewPool.getRemark5().equals("海尔创客_移动端") || reViewPool.getRemark5().equals("移动社交"))
            && third.getCanal().equals("TY") && third.getThirdPartyType().equals("SQM")) {

          reViewPool.setSqmStatus("0");
          reViewPool.setDesideText("向SQM推送中");
          reViewPool.setWorkOrderTo("SQM");
          reViewPool.setSqmType("ZX");
          reViewPool.setSqmState("0");
          if (null != reViewPool.getComplaintPhone() && !""
              .equals(reViewPool.getComplaintPhone())) {
            reViewPool.setSqmType("TS");
            reViewPool.setSqmState("1");
          }

        } else if (reViewPool.getCenterType().endsWith("WA") && third.getCanal().equals("RS")
            && third.getThirdPartyType().equals("HP")) {
          System.out.println("顺逛HP的单子");
          reViewPool.setSqmStatus("0");
          reViewPool.setDesideText("向HP推送中");
          reViewPool.setWorkOrderTo("HP");
          reViewPool.setSqmType(ReviewConstants.SQM_TYPE.CONSULT);
        } else if (third.getThirdPartyType().equals("HP") && third.getCanal().equals("TB")) {
          System.out.println("天猫HP的单子");
          reViewPool.setSqmStatus("0");
          reViewPool.setDesideText("向HP推送中");
          reViewPool.setWorkOrderTo("HP");
          reViewPool.setSqmType(ReviewConstants.SQM_TYPE.CONSULT);
        } else {
          System.out.println("普通的单子");
          reViewPool.setSqmStatus("4");
          reViewPool.setWorkOrderTo("");
        }
      }

      ServiceResult<Boolean> data = reviewWorkOrderService
          .addWorkOrder(reViewPool, paraImgArr, paraImgNameArr,
              userName);
      if (data != null && data.getSuccess()) {
        log.debug("WebReviewWorkOrderController.getReView 添加工单完成");
        serviceResult.setResult("添加成功！");
        return JsonUtil.toJson(serviceResult);
      }
      serviceResult.setError(data.getCode(), data.getMessage());
      return JsonUtil.toJson(serviceResult);
    } catch (Exception e) {
      resultService.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
      log.error("添加工单异常", e);
      return JsonUtil.toJson(resultService);
    }
  }

  @ResponseBody
  @RequestMapping(value = {"/getnetorder.ajax"}, method = {RequestMethod.POST})
  public String test(@RequestParam(value = "netOrderId", required = false) String netOrderId,
      HttpServletRequest request, HttpServletResponse response) {
    ServiceResult<String> resultService = new ServiceResult<String>();
    try {
      ServiceResult<WorkOrderBean> result = reviewWorkOrderService.getInterface(netOrderId);
      return JsonUtil.toJson(result);
    } catch (Exception e) {
      resultService.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
      log.error(WebReviewConstants.SYSTEM_ERROR, e);
      return JsonUtil.toJson(resultService);
    }

  }

  /**
   * 通过ID获取工单信息
   */
  @ResponseBody
  @RequestMapping(value = {"/findreviewbyid.ajax"}, method = {RequestMethod.POST})
  public String findReviewById(@RequestParam(value = "reviewId", required = false) String id,
      HttpServletRequest request, HttpServletResponse response) {
    ServiceResult<String> serviceResult = new ServiceResult<String>();
    try {
      if (id == null || "".equals(id)) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "工单号不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      ReviewPool pool = new ReviewPool();
      pool.setId(id);
      ServiceResult<ReviewPool> result = reviewWorkOrderService.findReviewById(pool);
      return JsonUtil.toJson(result);
    } catch (Exception e) {
      serviceResult.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
      log.error(WebReviewConstants.SYSTEM_ERROR, e);
      return JsonUtil.toJson(serviceResult);
    }
  }

  /**
   * 订单查询接口
   */
  @ResponseBody
  @RequestMapping(value = {"/findorderbyid.ajax"}, method = {RequestMethod.POST, RequestMethod.GET})
  public String gdOrderById(@RequestParam(value = "orderId", required = false) String orderId,
      HttpServletRequest request, HttpServletResponse response) {
    ServiceResult<String> serviceResult = new ServiceResult<String>();
    try {
      if (orderId == null || "".equals(orderId.trim())) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "订单号不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      ServiceResult<OrderBean> result = reviewWorkOrderService.gdOrderByOrder(orderId);
      if (result.getSuccess()) {
        return JsonUtil.toJson(result.getResult());
      } else {
        return result.getMessage();
      }
    } catch (Exception e) {
      serviceResult.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
      log.error(WebReviewConstants.SYSTEM_ERROR, e);
      return JsonUtil.toJson(serviceResult);
    }
  }

  /**
   * 网单查询接口
   *
   * @param corderId 网单号
   */
  @ResponseBody
  @RequestMapping(value = {"/findcorderbyid.ajax"}, method = {RequestMethod.POST,
      RequestMethod.GET})
  public String gdQueryOrderProductsByCOrderSn(
      @RequestParam(value = "corderId", required = false) String corderId,
      HttpServletRequest request, HttpServletResponse response) {
    ServiceResult<String> serviceResult = new ServiceResult<String>();
    try {
      if (corderId == null || "".equals(corderId.trim())) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "网单号不能为空！");
        return JsonUtil.toJson(serviceResult);
      }
      if (!"WD".equals(corderId.substring(0, 2).toUpperCase())) {
        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "请输入正确的网单号！");
        return JsonUtil.toJson(serviceResult);
      }
      if ("WDZX".equals(corderId.substring(0, 4).toUpperCase())) {
        serviceResult
            .setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "咨询类网单为随机生成,没有对应网单信息！");
        return JsonUtil.toJson(serviceResult);
      }
      ServiceResult<COrderBean> result = reviewWorkOrderService
          .gdQueryOrderProductsByCOrderSn(corderId);
      if (result.getSuccess()) {
        return JSONObject.toJSONString(result.getResult());
      } else {
        return result.getMessage();
      }
    } catch (Exception e) {
      serviceResult.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
      log.error(WebReviewConstants.SYSTEM_ERROR, e);
      return JsonUtil.toJson(serviceResult);
    }
  }

  /**
   * 数据迁移
   */
  @ResponseBody
  @RequestMapping(value = {"/dataTransfer.ajax"}, method = {RequestMethod.POST, RequestMethod.GET})
  public String dataTransfer(@RequestParam(value = "page", required = false) int page,
      @RequestParam(value = "rows", required = false) int rows,
      @RequestParam(value = "type", required = false) String type,
      @RequestParam(value = "insertTime", required = false) String insertTime,
      @RequestParam(value = "lastInsertTime", required = false) String lastInsertTime,
      @RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "sleepTime", required = false) Integer sleepTime) {
    Map<String, Object> mapDate = new HashMap<String, Object>();
    if (type != null && !"".equals(type)) {
      mapDate.put("type", type);
    }
    if (insertTime != null && !"".equals(insertTime)) {
      mapDate.put("insertTime", insertTime);
    }
    if (lastInsertTime != null && !"".equals(lastInsertTime)) {
      mapDate.put("lastInsertTime", lastInsertTime);
    }
    if (id != null && !"".equals(id)) {
      List<String> idList = Arrays.asList(id.split(","));
      mapDate.put("id", idList);
    }
    if (sleepTime != null) {
      mapDate.put("sleepTime", sleepTime);
    }

    ServiceResult<String> result = reviewWorkOrderService.dataTransfer(page, rows, mapDate);
    if (result.getSuccess()) {
      return JsonUtil.toJson(result.getResult());
    } else {
      return result.getMessage();
    }
  }

  /**
   * 数据迁移获取数量
   */
  @ResponseBody
  @RequestMapping(value = {"/findperformnum.ajax"}, method = {RequestMethod.POST,
      RequestMethod.GET})
  public String findPerformNum(@RequestParam(value = "isOpen", required = false) Integer isOpen,
      @RequestParam(value = "type", required = false) String type,
      @RequestParam(value = "insertTime", required = false) String insertTime,
      @RequestParam(value = "lastInsertTime", required = false) String lastInsertTime,
      HttpServletRequest request) {
    Map<String, Object> mapDate = new HashMap<String, Object>();
    if (isOpen != null) {
      mapDate.put("isOpen", isOpen);
    }
    if (type != null && !"".equals(type)) {
      mapDate.put("type", type);
    }
    if (insertTime != null && !"".equals(insertTime)) {
      mapDate.put("insertTime", insertTime);
    }
    if (lastInsertTime != null && !"".equals(lastInsertTime)) {
      mapDate.put("lastInsertTime", lastInsertTime);
    }
    ServiceResult<Integer> result = reviewWorkOrderService.findPerformNum(mapDate);
    if (result.getSuccess()) {
      return JsonUtil.toJson(result.getResult());
    } else {
      return result.getMessage();
    }
  }

  // 接收HP的反馈信息
  @ResponseBody
  @RequestMapping(value = "/getReviewMiddleFromHP", method = RequestMethod.POST, consumes = "application/json")
  public String getReviewMiddleFromHP(HttpServletRequest request, HttpServletResponse response,
      @RequestBody String params) {
    ServiceResult<String> serviceResult = new ServiceResult<String>();
    // HP返回的工单号和反馈内容，需要更新到reviwepool中的中间结果 backContext2
    String orderId = "";
    String content = "";
    String closeTime = "";
    Map<String, String> paramMap = new HashMap<String, String>();
    try {
      paramMap = JSON.parse(params, Map.class);
    } catch (ParseException e1) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "HP传入参数格式错误！");
      return JsonUtil.toJson(serviceResult);
    }
    orderId = paramMap.get("clientId");
    content = paramMap.get("content");

    closeTime = paramMap.get("closeTime");
    log.debug("clientId=" + orderId + ",,,,,,content=" + content + ",,,,,,closeTime=" + closeTime);
    log.info("HP反馈-------------------------------------------------------");
    log.info("content=" + content);

    try {
      if ("".equals(orderId) || "".equals(content)) {
        serviceResult.setMessage("");
        serviceResult
            .setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "工单号或反馈内容为空，请确认！");
        return JsonUtil.toJson(serviceResult);
      } else {

        ReviewPool update = new ReviewPool();
        update.setBackContext2(content);
        update.setId(orderId);
        update.setDesideText("HP已返回中间结果，等待发送判定结果");
        update.setSqmStatus("3");// 已接收到返回结果
        update.setRemark1("HP");
        update.setWorkOrderTime(closeTime);

        ServiceResult<ReviewPool> review = reviewWorkOrderService.findReviewById(update);
        if (review.getResult().getWorkStatus().equals("3")) {
          serviceResult.setResult("该工单已关单,不能反馈中间结果！");
          serviceResult.setSuccess(false);
          return JsonUtil.toJson(serviceResult);
        } else {
          ServiceResult<Boolean> b = reviewWorkOrderService.updateWorkOrder(update);
          if (b.getSuccess() == true) {
            serviceResult.setResult("反馈成功！");
            serviceResult.setSuccess(true);
          } else {
            serviceResult.setResult("反馈失败！");
            serviceResult.setSuccess(false);
          }

          log.debug("HP反馈工单完成" + orderId);
          return JsonUtil.toJson(serviceResult);
        }
      }
    } catch (Exception e) {
      serviceResult.setMessage("");
      serviceResult
          .setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "工单号或反馈内容为空，请确认！");
      return JsonUtil.toJson(serviceResult);
    }

  }
  // //接收信息创建新工单
  // @ResponseBody
  // @RequestMapping(value = "/createNewReviewPool", method =
  // RequestMethod.POST, consumes = "application/json")
  // public String createNewReviewPool(HttpServletRequest request,
  // HttpServletResponse response, @RequestBody String params) {
  // ServiceResult<String> serviceResult = new ServiceResult<String>();
  // ReviewPool reViewPool =new ReviewPool();
  // String netOrderId = "";
  // String question1Level1 = "";
  // String question1Level2 = "";
  // String question1Level3 = "";
  // String context="";
  // String adduser="";
  // Map<String, String> paramMap = new HashMap<String, String>();
  // try {
  // paramMap = JSON.parse(params, Map.class);
  // } catch (ParseException e1) {
  // serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR,
  // "传入参数格式错误！");
  // return JsonUtil.toJson(serviceResult);
  // }
  // netOrderId = paramMap.get("netOrderId");
  // question1Level1 = paramMap.get("question1Level1");
  // question1Level2 = paramMap.get("question1Level2");
  // question1Level3 = paramMap.get("question1Level3");
  // context = paramMap.get("context");
  // adduser = paramMap.get("adduser");
  // if(null==adduser||"".equals(adduser))
  // {
  // serviceResult.setError("参数异常","创建人不能为空！");
  // return JsonUtil.toJson(serviceResult);
  // }
  // log.debug("netOrderId="+netOrderId+",,,,,,question1Level1="+question1Level1+",,,,,,question1Level2="+question1Level2+
  // ",,,,,,,,,,question1Level3="+question1Level3+",,,,,,,,,context="+context+"........"+adduser
  // );
  // if(null==netOrderId||"".equals(netOrderId))
  // {
  // serviceResult.setError("参数异常","网单号不能为空！");
  // return JsonUtil.toJson(serviceResult);
  // }
  // if(null==question1Level1||"".equals(question1Level1)||
  // null==question1Level2||"".equals(question1Level2)
  // ){
  // serviceResult.setError("参数异常","责任位1或责任位2不能为空！");
  // return JsonUtil.toJson(serviceResult);
  // }
  // if(null==context||"".equals(context))
  // {
  // serviceResult.setError("参数异常","反馈内容不能为空！");
  // return JsonUtil.toJson(serviceResult);
  // }
  //
  //
  // //检查责任位是否对应
  // ReviewDataDictionaryEntity
  // rd1=woDictionaryService.selectByValueMeaning("duty_1",question1Level1,null).getResult();
  // ReviewDataDictionaryEntity
  // rd2=woDictionaryService.selectByValueMeaning("duty_2",question1Level2,rd1.getValue()).getResult();
  // if(null==rd2)
  // {
  // serviceResult.setError("参数异常","责任位2与责任位1不匹配！");
  // return JsonUtil.toJson(serviceResult);
  // }
  // ReviewDataDictionaryEntity
  // rd3=woDictionaryService.selectByValueMeaning("duty_3",question1Level3,rd2.getValue()).getResult();
  // ServiceResult<List<ReviewDataDictionaryEntity>> dicResult =
  // woDictionaryService
  // .selectByParentValue("duty_3",rd2.getValue());
  // if(null==rd3)
  // {
  // if("".equals(question1Level3))
  // {
  // if(null!=dicResult.getResult()&&dicResult.getResult().size()>0)
  // {
  // serviceResult.setError("参数异常","该责任位2下有相应的责任位3，请填写责任位3");
  // return JsonUtil.toJson(serviceResult);
  // }
  // }
  // else
  // {
  // if(null!=dicResult.getResult()&&dicResult.getResult().size()>0)
  // {
  // serviceResult.setError("参数异常","责任位3与责任位2不匹配！");
  // return JsonUtil.toJson(serviceResult);
  // }
  // }
  // }
  // try {
  // ServiceResult<WorkOrderBean> result =
  // reviewWorkOrderService.getInterface(netOrderId);
  // WorkOrderBean workOrderBean=result.getResult();
  // if(workOrderBean==null)
  // {
  // serviceResult.setError("参数异常","当前网单号未匹配到数据！");
  // return JsonUtil.toJson(serviceResult);
  // }
  // reViewPool.setStoreType(workOrderBean.getStoreType());
  // reViewPool.setQuestion1Level1(question1Level1);
  // reViewPool.setQuestion1Level2(question1Level2);
  // reViewPool.setQuestion1Level3(question1Level3);
  // reViewPool.setAddress(workOrderBean.getAddress());
  // reViewPool.setBuyer(workOrderBean.getBuyer());
  // reViewPool.setBuyerMobile(workOrderBean.getBuyerMobile());
  // reViewPool.setCenterType(workOrderBean.getCenterType());
  // reViewPool.setCompany(workOrderBean.getCompany());
  // reViewPool.setCorderPrimary(workOrderBean.getCorderPrimary());
  // reViewPool.setNetOrderId(workOrderBean.getCorderSn());
  // reViewPool.setOrderId(workOrderBean.getOrderSn());
  // reViewPool.setRemark3(adduser);
  // reViewPool.setProductType(workOrderBean.getProductName());
  // ServiceResult<List<ReviewDataDictionaryEntity>> orderSoure =
  // woDictionaryService
  // .selectBySetIdIgnoreFlg(null);
  // for (ReviewDataDictionaryEntity rdd:orderSoure.getResult())
  // {
  // if(rdd.getValue().equals(workOrderBean.getSource())&&rdd.getValueSetMeaning().equals("订单来源"))
  // {
  // reViewPool.setRemark5(rdd.getValueMeaning());
  // }
  // }
  // reViewPool.setOrderCome(workOrderBean.getShippingTime());
  // reViewPool.setProductAmount(workOrderBean.getProductAmount()+"");
  // reViewPool.setNetPointId(workOrderBean.getNetPointId());
  // reViewPool.setPayTime(workOrderBean.getPayTime());
  // reViewPool.setCreateTime(workOrderBean.getCreateTime());
  // reViewPool.setProductName(workOrderBean.getProductName());
  // reViewPool.setNumber(workOrderBean.getNumber());
  // reViewPool.setPhone(workOrderBean.getPhone());
  // reViewPool.setRegionName(workOrderBean.getRegionName());
  // reViewPool.setStoreId(workOrderBean.getStoreId());
  // reViewPool.setSku(workOrderBean.getSku());
  // reViewPool.setOrderPrimary(workOrderBean.getOrderPrimary());
  // String addTime = DateUtil.format(new Date(),
  // ReviewConstants.TIME.FORMAT_DATE);
  // reViewPool.setWorkCreateTime(addTime);
  // reViewPool.setContext(context);
  //
  //
  //
  //
  // ReviewContactsDto reviewContacts = new ReviewContactsDto();
  // reviewContacts.setQuestion1level1(reViewPool.getQuestion1Level1());
  // reviewContacts.setQuestion1level2(reViewPool.getQuestion1Level2());
  // if (null!=reViewPool.getRemark5() && !"".equals(reViewPool.getRemark5()))
  // {
  // reviewContacts.setOrdercome(reViewPool.getRemark5());
  // }
  // if (null!=reViewPool.getCompany()&& !"".equals(reViewPool.getCompany()))
  // {
  // reviewContacts.setCompany(reViewPool.getCompany());
  // }
  // ServiceResult<List<ReviewContactsDto>> contactsResult =
  // webReviewContactsModle
  // .findLG(reviewContacts);
  // if(null!=contactsResult.getResult()&&contactsResult.getResult().size()>0){
  // ReviewContactsDto rcd = contactsResult.getResult().get(0);
  // reViewPool.setRemark4(rcd.getUserid());
  // reViewPool.setWangId(rcd.getUsername());
  // }
  // else
  // {
  // serviceResult.setError("参数异常","该网单或责任位并未匹配到相关人员！");
  // return JsonUtil.toJson(serviceResult);
  // }
  // log.debug("WebReviewWorkOrderController.getReView 添加工单入参：" + "reViewPool"
  // + reViewPool);
  // if (reViewPool == null) {
  // serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR,
  // "参数不能为空！");
  // return JsonUtil.toJson(serviceResult);
  // }
  // //新添加的工单准备传到SQM
  // //物流信息以WA结尾的工单要标记为发给SQM
  // reViewPool.setSqmCount("0");
  // ThirdPartyLiabilityCondition third = new ThirdPartyLiabilityCondition();
  // third.setQuestion1Level1(reViewPool.getQuestion1Level1());
  // third.setQuestion1Level2(reViewPool.getQuestion1Level2());
  // third.setQuestion1Level3(reViewPool.getQuestion1Level3());
  // third = woDictionaryService.findInfoFromThird(third).getResult();
  // if(third==null)
  // {
  // System.out.println("普通的单子");
  // reViewPool.setSqmStatus("4");
  // reViewPool.setWorkOrderTo("");
  // }
  // else
  // {
  // if
  // (reViewPool.getCenterType().endsWith("WA")&&null!=reViewPool.getRemark5()&&
  // ( reViewPool.getRemark5().equals("微店")||
  // reViewPool.getRemark5().equals("海尔创客_PC端")||
  // reViewPool.getRemark5().equals("商城订单-分销渠道")||
  // reViewPool.getRemark5().equals("商城订单")||
  // reViewPool.getRemark5().equals("移动商城")||
  // reViewPool.getRemark5().equals("商城订单-大学生项目订单")||
  // reViewPool.getRemark5().equals("海尔创客_移动端")||
  // reViewPool.getRemark5().equals("移动社交")
  // )&&third.getCanal().equals("TY")&&third.getThirdPartyType().equals("SQM")
  // )
  // {
  // System.out.println("SQM的单子");
  // reViewPool.setSqmStatus("0");
  // reViewPool.setDesideText("向SQM推送中");
  // reViewPool.setWorkOrderTo("SQM");
  // }
  // else if(reViewPool.getCenterType().endsWith("WA")
  // &&third.getCanal().equals("RS")
  // &&third.getThirdPartyType().equals("HP")
  // )
  // {
  // System.out.println("顺逛HP的单子");
  // reViewPool.setSqmStatus("0");
  // reViewPool.setDesideText("向HP推送中");
  // reViewPool.setWorkOrderTo("HP");
  // }
  // else if(third.getThirdPartyType().equals("HP")
  // &&third.getCanal().equals("TB")
  // )
  // {
  // System.out.println("天猫HP的单子");
  // reViewPool.setSqmStatus("0");
  // reViewPool.setDesideText("向HP推送中");
  // reViewPool.setWorkOrderTo("HP");
  // }
  // else
  // {
  // System.out.println("普通的单子");
  // reViewPool.setSqmStatus("4");
  // reViewPool.setWorkOrderTo("");
  // }
  // }
  // reViewPool.setSqmType(ReviewConstants.SQM_TYPE.CONSULT);
  // ServiceResult<Boolean> data =
  // reviewWorkOrderService.addWorkOrder(reViewPool,null,null, adduser);
  // if (data != null && data.getSuccess()) {
  // log.debug("WebReviewWorkOrderController.getReView 添加工单完成");
  // if(null!=data.getMessage()&&!"".equals(data.getMessage()))
  // {
  // serviceResult.setResult("添加成功！"+data.getMessage());
  // }
  // else
  // {
  // serviceResult.setResult("添加成功！");
  // }
  // return JsonUtil.toJson(serviceResult);
  // }
  //
  // serviceResult.setError(data.getCode(), data.getMessage());
  // return JsonUtil.toJson(serviceResult);
  // } catch (Exception e) {
  // serviceResult.setError(WebReviewConstants.SYSTEM_ERROR_CODE,
  // WebReviewConstants.SYSTEM_ERROR);
  // log.error("添加工单异常", e);
  // return JsonUtil.toJson(serviceResult);
  // }
  //
  // }

  // 界面手动修改工单状态
  @ResponseBody
  @RequestMapping(value = {"/updateSQMStatus.ajax"}, method = {RequestMethod.POST})
  public String updateSQMStatus(HttpServletRequest request, HttpServletResponse response,
      @RequestParam(value = "ids", required = false) String ids) {
    ServiceResult<String> resultService = reviewWorkOrderService.updateSQMStatus(ids);
    System.out.println(resultService.getResult());
    return JsonUtil.toJson(resultService);
  }

  // 接收4 0 0 传过来的信息 创建一个 工单
/*
  @RequestMapping(value = {"/getReviewPoolForDhzxInsert"}, method = {
      RequestMethod.POST}, consumes = "application/json")
  @ResponseBody
  public String getReviewPoolForDhzxInsert(HttpServletRequest request, HttpServletResponse response,
      @RequestBody String params) {

    ServiceResult<String> serviceResult = new ServiceResult<String>();
    Map<String, String> paramMap = new HashMap<String, String>();
    try {
      // 接受参数例子
      // String a="{ \"messageNum\": \"Brett\",
      // \"userName\":\"McLaughlin\", \"phone1\": \"aaaa\" ,\"phone3\":
      // \"aaaa\" , \"phone2\": \"aaaa\", \"address\": \"aaaa\",
      // \"store\": \"店铺\", \"callTime\": \"2017-07-17\",
      // \"recoveryTime\": \"2017-07-18\"}";

      String addTime = DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE);

      paramMap = JSON.parse(params, Map.class);
      Reviewpoolfordhzx reviewpoolfordhzx = new Reviewpoolfordhzx();
      if (paramMap.get("messageNum") != null) {
        reviewpoolfordhzx.setMessageNum(paramMap.get("messageNum"));
      } else {
        reviewpoolfordhzx.setMessageNum("");
      }
      if (paramMap.get("userName") != null) {
        reviewpoolfordhzx.setUserName(paramMap.get("userName"));
      } else {
        reviewpoolfordhzx.setUserName("");
      }
      if (paramMap.get("phone1") != null) {
        reviewpoolfordhzx.setPhone1(paramMap.get("phone1"));
      } else {
        reviewpoolfordhzx.setPhone1("");
      }
      if (paramMap.get("phone2") != null) {
        reviewpoolfordhzx.setPhone2(paramMap.get("phone2"));
      } else {
        reviewpoolfordhzx.setPhone2("");
      }
      if (paramMap.get("phone3") != null) {
        reviewpoolfordhzx.setPhone3(paramMap.get("phone3"));
      } else {
        reviewpoolfordhzx.setPhone3("");
      }
      if (paramMap.get("address") != null) {
        reviewpoolfordhzx.setAddress(paramMap.get("address"));
      } else {
        reviewpoolfordhzx.setAddress("");
      }
      if (paramMap.get("store") != null) {
        reviewpoolfordhzx.setStore(paramMap.get("store"));
      } else {
        reviewpoolfordhzx.setStore("");
      }
      if (paramMap.get("callTime") != null) {
        reviewpoolfordhzx.setCallTime(paramMap.get("callTime"));
      } else {
        reviewpoolfordhzx.setCallTime("");
      }
      if (paramMap.get("recoveryTime") != null) {
        reviewpoolfordhzx.setRecoveryTime(paramMap.get("recoveryTime"));
      } else {
        reviewpoolfordhzx.setRecoveryTime("");
      }
      reviewpoolfordhzx.setType(paramMap.get("type"));
      if (paramMap.get("trip") != null) {
        reviewpoolfordhzx.setTrip(paramMap.get("trip"));

      }
      if (paramMap.get("problemMessage") != null) {
        reviewpoolfordhzx.setProblemMessage(
            reviewpoolfordhzx.getMessageNum() + ":" + paramMap.get("problemMessage") + "</br>");
      }
      if (paramMap.get("storeType") != null) {
        reviewpoolfordhzx.setStoreType(paramMap.get("storeType"));
      }
      if (paramMap.get("productType") != null) {
        reviewpoolfordhzx.setProductType(paramMap.get("productType"));
      }
      if (paramMap.get("model") != null) {
        reviewpoolfordhzx.setModel(paramMap.get("model"));
      }

      reviewpoolfordhzx.setCreateTime(addTime);
      ServiceResult<Boolean> returns;
      if (paramMap.get("doOrder") == null || "".equals(paramMap.get("doOrder"))) {
        SimpleDateFormat sdDateTime = new SimpleDateFormat("yyMMddHHmmssSSS");
        String date = "400HYD";
        date += sdDateTime.format(new Date());
        reviewpoolfordhzx.setReviewPoolId(date);
        returns = reviewWorkOrderService.insertReviewpoolDhzx(reviewpoolfordhzx);
      } else {
        reviewpoolfordhzx.setReviewPoolId(paramMap.get("doOrder"));
        returns = reviewWorkOrderService.updateReviewpoolDhzxForProblemMessage(reviewpoolfordhzx);

      }

      if (returns.getSuccess()) {

        serviceResult.setSuccess(true);
        serviceResult.setMessage("操作成功");
        return JsonUtil.toJson(serviceResult);
      } else {
        serviceResult.setSuccess(false);
        serviceResult.setMessage("操作失败");

        serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "参数格式错误！");
        return JsonUtil.toJson(serviceResult);
      }
    } catch (ParseException e1) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "参数格式错误！");
      return JsonUtil.toJson(serviceResult);
    }

  }*/
  // 接受400 中间结果反馈
/*
  @RequestMapping(value = {"/getReviewPoolForDhzxUpdate"}, method = {
      RequestMethod.POST}, consumes = "application/json")
  @ResponseBody
  public String getReviewPoolForDhzxUpdate(@RequestBody String params) {
    ServiceResult<String> serviceResult = new ServiceResult<>();
    Reviewpoolfordhzx reviewpoolfordhzx = new Reviewpoolfordhzx();
    Map<String, String> paramMap = new HashMap<>();
    try {

      paramMap = JSON.parse(params, Map.class);

      reviewpoolfordhzx.setMessageNum(paramMap.get("messageNum"));
      reviewpoolfordhzx.setUserName(paramMap.get("userName"));
      if (paramMap.get("type").equals("0")) {
        reviewpoolfordhzx.setProblem(paramMap.get("context"));
        reviewpoolfordhzx.setWorkStatus("2");
      } else {
        reviewpoolfordhzx.setBackContext3(paramMap.get("context"));
        reviewpoolfordhzx.setWorkStatus("3");
        String addTime = DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE);
        reviewpoolfordhzx.setCloseTime(addTime);
        reviewpoolfordhzx.setRemark2(paramMap.get("userName"));
      }
      ServiceResult<Reviewpoolfordhzx> query = woReviewpoolfordhzxService
          .getQueryReviewpoolfordhzx(reviewpoolfordhzx);
      Reviewpoolfordhzx a = query.getResult();

      if (a.getWorkStatus().equals("3")) {
        serviceResult.setSuccess(false);
        serviceResult.setMessage("该工单已经关单,不能回馈结果");
        return JsonUtil.toJson(serviceResult);
      } else {
        reviewpoolfordhzx.setId(a.getId());
        reviewpoolfordhzx.setRegion(paramMap.get("region"));
        woReviewpoolfordhzxService.updateReviewPoolForDhzxs(reviewpoolfordhzx);
        return JsonUtil.toJson(serviceResult);
      }

    } catch (Exception e) {
      log.info(
          "执行了400工单回馈" + reviewpoolfordhzx.getMessageNum() + "--" + reviewpoolfordhzx.getId() + "--"
              + reviewpoolfordhzx.getType());
      serviceResult.setError("获取工单号过多,存在重复工单" + paramMap.get("messageNum"), e.getMessage());
      return JsonUtil.toJson(serviceResult);
    }

  }*/

  @RequestMapping(value = {"/getnetOrderFroOrderId.ajax"}, method = {RequestMethod.POST})
  @ResponseBody
  public String getnetOrderFroOrderId(
      @RequestParam(value = "orderIds", required = false) String orderIds) {

    ServiceResult<List<ReviewPool>> serviceResult = reviewWorkOrderService
        .getnetOrderFroOrderId(orderIds);

    return JsonUtil.toJson(serviceResult);

  }

/*  @RequestMapping(value = {"/tripDhzx"}, method = {
      RequestMethod.POST}, consumes = "application/json")
  @ResponseBody
  public String tripDhzx(HttpServletRequest request, HttpServletResponse response,
      @RequestBody String params) {

    ServiceResult<String> serviceResult = new ServiceResult<String>();
    Map<String, String> paramMap;
    try {
      paramMap = JSON.parse(params, Map.class);
      Reviewpoolfordhzx reviewpoolfordhzx = new Reviewpoolfordhzx();
      reviewpoolfordhzx.setMessageNum(paramMap.get("messageNum"));
      reviewpoolfordhzx.setTrip(paramMap.get("trip"));
      woReviewpoolfordhzxService.updateReviewpoolfordhzxTrip(reviewpoolfordhzx);

    } catch (ParseException e1) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "参数格式错误！");
      return JsonUtil.toJson(serviceResult);
    }
    return null;
  }*/

  @RequestMapping(value = {"/getReviewPoolForMobiles"}, method = {
      RequestMethod.POST}, consumes = "application/json")
  @ResponseBody
  public String getReviewPoolForMobiles(
      @RequestParam(value = "params", required = false) String params) {
    ServiceResult<String> serviceResult = new ServiceResult<String>();
    ReviewPool r = new ReviewPool();
    r.setBuyerMobile(params);
    if (params == null || "".equals(params)) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage("传入手机号为空,手机号为" + params);
      return JsonUtil.toJson(serviceResult);
    } else {
      ServiceResult<List<ReviewPool>> service = reviewWorkOrderService.getReviewPoolForMobile(r);
      return JsonUtil.toJson(service);
    }
  }

  @RequestMapping(value = {"/verification.ajax"}, method = {RequestMethod.POST})
  @ResponseBody
  public String verification(
      @RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "question1Level1", required = false) String question1Level1,
      @RequestParam(value = "question1Level2", required = false) String question1Level2,
      @RequestParam(value = "question1Level3", required = false) String question1Level3) {
    ReviewPool r = new ReviewPool();
    r.setId(id);
    ServiceResult<String> serviceResult = new ServiceResult<String>();
    ServiceResult<ReviewPool> review = reviewWorkOrderService.findReviewById(r);
    if (review.getResult().getWorkOrderTo().equals("SQM")) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage("该工单输入SQM工单,禁止修改责任位");
      return JsonUtil.toJson(serviceResult);
    } else {

      ThirdPartyLiabilityCondition third = new ThirdPartyLiabilityCondition();
      third.setQuestion1Level1(question1Level1);
      third.setQuestion1Level2(question1Level2);
      third.setQuestion1Level3(question1Level3);
      third = woDictionaryService.findInfoFromThird(third);
      if (third != null) {
        serviceResult.setSuccess(false);
        serviceResult.setMessage("该工单是普通工单,禁止修改为SQM工单");
      } else {
        serviceResult.setSuccess(true);
      }
      return JsonUtil.toJson(serviceResult);
    }

  }
/*

  @RequestMapping(value = {"/updateReviewPoolForDhzxs.ajax"}, method = {RequestMethod.POST})
  @ResponseBody
  public String updateReviewPoolForDhzxs(HttpServletRequest request,
      @RequestParam(value = "id", required = false) String id,
      @RequestParam(value = "problem", required = false) String problem,
      @RequestParam(value = "backContext3", required = false) String backContext3,
      @RequestParam(value = "storeType", required = false) String storeType,
      @RequestParam(value = "store", required = false) String store

  )

  {
    ServiceResult<String> serviceResult = new ServiceResult<>();
    String userName = this.getUserName(request);
    Reviewpoolfordhzx reviewpoolfordhzx = new Reviewpoolfordhzx();

    reviewpoolfordhzx.setId(Integer.parseInt(id));
    if (null != problem && !"".equals(problem)) {
      reviewpoolfordhzx.setProblem(problem);
      reviewpoolfordhzx.setWorkStatus("2");
      reviewpoolfordhzx.setRemark2(userName);
    }
    if (null != backContext3 && !"".equals(backContext3)) {
      String addTime = DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE);
      reviewpoolfordhzx.setBackContext3(backContext3);
      reviewpoolfordhzx.setWorkStatus("3");
      reviewpoolfordhzx.setCloseTime(addTime);
      reviewpoolfordhzx.setRemark2(userName);

    }
    reviewpoolfordhzx.setUserName(userName);
    reviewpoolfordhzx.setStoreType(storeType);
    reviewpoolfordhzx.setStore(store);

    ServiceResult<Boolean> resultService = woReviewpoolfordhzxService
        .updateReviewPoolForDhzxs(reviewpoolfordhzx);

    if (resultService.getSuccess()) {
      serviceResult.setResult("保存成功！");
    } else {
      serviceResult.setSuccess(false);

      serviceResult.setResult("保存失败");
    }
    return JsonUtil.toJson(serviceResult);
  }
*/


  private String getUserName(HttpServletRequest request) {
    return (String) request.getSession().getAttribute("userName");
  }

}
