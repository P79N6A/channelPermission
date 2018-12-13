package com.haier.svc.api.controller.workorder;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.google.gson.Gson;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.shop.model.ReviewPool;
import com.haier.shop.model.Reviewpoolfordhzx;

import com.haier.shop.util.ReviewConstants;
import com.haier.svc.api.controller.util.Httpclient;
import com.haier.svc.api.controller.util.WebUtil;
import com.haier.system.model.SysMenu;
import com.haier.traderate.service.ReviewWorkOrderService;
import com.haier.traderate.service.WoDictionaryService;
import com.haier.traderate.service.WoReviewpoolfordhzxService;
import net.sf.json.util.WebUtils;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.svc.api.controller.workorder
 * @Date: Created in 2018/4/20 14:30
 * @Modified By:
 */
@Controller
@RequestMapping("/workOrderDHZX")
public class WorkOrderDHZXController {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(WorkOrderDHZXController.class);
  @Resource
  private WoReviewpoolfordhzxService woReviewpoolfordhzxService;
  @Resource
  private ReviewWorkOrderService reviewWorkOrderService;

  @GetMapping("/workOrderDHZX")
  public String orderList(){
    return "workorder/dhzx";
  }

  @RequestMapping(value = "/getReviewPoolForDhzxList", method = RequestMethod.POST)
  @ResponseBody
  public String getReviewPoolForDhzxLists(@RequestParam(value = "rows", required = false) Integer size,
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

  )
  {
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
      ServiceResult<List<Reviewpoolfordhzx>> result = woReviewpoolfordhzxService.pageReviewpoolfordhzx(r, pager);
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




    @RequestMapping(value = { "/updateReviewPoolForDhzxs.ajax" }, method = { RequestMethod.POST })
    @ResponseBody
    public String updateReviewPoolForDhzxs(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(value = "id", required = false) String id,
                                           @RequestParam(value = "problem", required = false) String problem,
                                           @RequestParam(value = "backContext3", required = false) String backContext3,
                                           @RequestParam(value = "storeType", required = false) String storeType,
                                           @RequestParam(value = "store", required = false) String store

    )

    {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        HttpSession session = request.getSession();
        String userName = (String)session.getAttribute("userName");
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

        ServiceResult<Boolean> resultService = woReviewpoolfordhzxService.updateReviewpoolfordhzx(reviewpoolfordhzx);

        if (resultService.getSuccess()) {
            serviceResult.setResult("保存成功！");
        }
        return JsonUtil.toJson(serviceResult);
    }



    // 接收4 0 0 传过来的信息 创建一个 工单

    @RequestMapping(value = { "/getReviewPoolForDhzxInsert" }, method = {
            RequestMethod.POST }, consumes = "application/json")
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
            ServiceResult<Boolean> returns = new ServiceResult<Boolean>();
            if (paramMap.get("doOrder") == null || "".equals(paramMap.get("doOrder"))) {
                SimpleDateFormat sdDateTime = new SimpleDateFormat("yyMMddHHmmssSSS");
                    String date = "400HYD";
                date += sdDateTime.format(new Date());
                reviewpoolfordhzx.setReviewPoolId(date);
                returns = woReviewpoolfordhzxService.insertReviewpoolDhzx(reviewpoolfordhzx);
            } else {
                reviewpoolfordhzx.setReviewPoolId(paramMap.get("doOrder").toString());
                returns = woReviewpoolfordhzxService.updateReviewpoolDhzxForProblemMessage(reviewpoolfordhzx);

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

    }


    // 接受400 中间结果反馈

    @RequestMapping(value = { "/getReviewPoolForDhzxUpdate" }, method = {
            RequestMethod.POST }, consumes = "application/json")
    @ResponseBody
    public String getReviewPoolForDhzxUpdate(HttpServletRequest request, HttpServletResponse response,
                                             @RequestBody String params) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        Reviewpoolfordhzx reviewpoolfordhzx = new Reviewpoolfordhzx();
        Map<String, String> paramMap = new HashMap<String, String>();
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

                ServiceResult<Boolean> resultService = woReviewpoolfordhzxService
                        .updateReviewpoolfordhzx(reviewpoolfordhzx);

                return JsonUtil.toJson(serviceResult);
            }

        } catch (Exception e) {
            log.info("执行了400工单回馈" + reviewpoolfordhzx.getMessageNum() + "--" + reviewpoolfordhzx.getId() + "--"
                    + reviewpoolfordhzx.getType());
            serviceResult.setError("获取工单号过多,存在重复工单" + paramMap.get("messageNum"), e.getMessage());
            return JsonUtil.toJson(serviceResult);
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
                serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "工单号或反馈内容为空，请确认！");
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
            log.error("HP反馈工单异常", e);
            serviceResult.setMessage("");
            serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_PARAMETER_ERROR, "工单号或反馈内容为空，请确认！");
            return JsonUtil.toJson(serviceResult);
        }

    }


    @ResponseBody
    @RequestMapping(value = "/test")
    public String accordion(HttpServletRequest request,HttpServletResponse response){
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        System.out.println("pageload sessionId ==>"+session.getId());
        String result = "";
        try {
            result = Httpclient.send("http://127.0.0.1:8081/workOrderDHZX/getReviewMiddleFromHP","{\"clientId\":\"HWO180515145414354\",\"content\":\"2018-05-15 17:18 已测电，向用户本人讲解；\",\"closeTime\":\"2018-05-15 17:18:00\"}","application/json");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
