package com.haier.svc.api.controller.workorder;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.dto.LogBean;
import com.haier.shop.dto.ReviewContactsDto;
import com.haier.shop.model.DutyStatistic;
import com.haier.shop.model.PersonnelStatistic;
import com.haier.shop.model.ReviewPool;
import com.haier.shop.model.Reviewpoolfordhzx;
import com.haier.shop.model.WOUser;
import com.haier.svc.api.controller.excel.WebReviewExportDutyStatistic;
import com.haier.svc.api.controller.excel.WebReviewExportPeople;
import com.haier.svc.api.controller.excel.WebReviewExportPersonnelStatistic;
import com.haier.svc.api.controller.excel.WebReviewExportTemplate;
import com.haier.svc.api.controller.excel.WebReviewExportaddressList;
import com.haier.svc.api.controller.excel.WebReviewExportdhzxle;
import com.haier.svc.api.controller.util.Constant;
import com.haier.traderate.service.ReviewWorkOrderService;
import com.haier.traderate.service.WoReviewContactsService;
import com.haier.traderate.service.WoReviewLogService;
import com.haier.traderate.service.WoReviewpoolfordhzxService;
import com.haier.traderate.service.WorkOrderWOUserService;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/export")
public class WebReviewExportExcelController implements Serializable {

  @Resource
  private WoReviewpoolfordhzxService woReviewpoolfordhzxService;
  @Resource
  private WoReviewLogService woReviewLogService;
  @Resource
  private ReviewWorkOrderService reviewWorkOrderService;
  @Resource
  private WoReviewContactsService woReviewContactsService;
  @Resource
  private WorkOrderWOUserService workOrderWOUserService;

  private static Logger log = LogManager
      .getLogger(WebReviewExportExcelController.class);
  private String fileName;
  private static final int LENGTH = 20000;
  private static final int MAX = 20000;


  private static final long serialVersionUID = -8032563597222740691L;
  private static org.slf4j.Logger psilog = LoggerFactory.getLogger("psilogger");


  /**
   * 设置响应头
   */
  public void setResponseHeader(HttpServletResponse response) {
    try {
      response.setContentType("application/octet-stream;charset=UTF-8");
      response.setHeader("Content-Disposition",
          "attachment;filename=" + java.net.URLEncoder.encode(this.fileName, "UTF-8")
              + ".xls");
      response.addHeader("Pargam", "no-cache");
      response.addHeader("Cache-Control", "no-cache");
    } catch (Exception ex) {
      log.debug("Exception:", ex);
    }
  }


  @ResponseBody
  @RequestMapping(value = "/exportdhzx", method = RequestMethod.GET)
  public void exportDhzxStatistic(
      @RequestParam(value = "username_search", required = false) String userName,
      @RequestParam(value = "phone1_search", required = false) String phone1,
      @RequestParam(value = "phone2_search", required = false) String phone2,
      @RequestParam(value = "phone3_search", required = false) String phone3,
      @RequestParam(value = "messageNum_search", required = false) String messageNum,
      @RequestParam(value = "workStatus_search", required = false) String workStatus,
      @RequestParam(value = "type_search", required = false) String type,
      @RequestParam(value = "store_search", required = false) String store,
      @RequestParam(value = "storeType_search", required = false) String storeType,
      @RequestParam(value = "startTime_search", required = false) String startTime,
      @RequestParam(value = "endTime_search", required = false) String endTime,
      @RequestParam(value = "page", required = false) Integer no,
      @RequestParam(value = "rows", required = false) Integer size,
      HttpServletRequest request, HttpServletResponse response) {

    String excelCondition = "";
    PagerInfo pager = new PagerInfo(MAX, 1);
    Reviewpoolfordhzx r = new Reviewpoolfordhzx();
    if (userName != null && !"".equals(userName)) {
      r.setUserName(userName);
      excelCondition += "人员:" + userName;
    }
    if (phone1 != null && !"".equals(phone1)) {
      r.setPhone1(phone1);
      excelCondition += "电话1:" + phone1;
    }
    if (phone2 != null && !"".equals(phone2)) {
      r.setPhone2(phone1);
      excelCondition += "电话2:" + phone2;
    }

    if (phone3 != null && !"".equals(phone3)) {
      r.setPhone3(phone3);
      excelCondition += "电话3:" + phone3;
    }
    if (messageNum != null && !"".equals(messageNum)) {
      r.setMessageNum(messageNum);
      excelCondition += "信息编号:" + messageNum;
    }
    if (workStatus != null && !"".equals(workStatus)) {
      if (workStatus.equals("未处理")) {
        r.setWorkStatus("0");
      }
      if (workStatus.equals("中间结果")) {
        r.setWorkStatus("2");
      }
      if (workStatus.equals("最终结果")) {
        r.setWorkStatus("3");
      }
      excelCondition += "最终结果:" + workStatus;
    }

    if (type != null && !"".equals(type)) {
      if (type.equals("物流类工单")) {
        r.setType("0");
      }
      if (type.equals("店铺类工单")) {
        r.setType("1");
      }
      if (type.equals("售后类工单")) {
        r.setType("2");
      }
      excelCondition += "工单类型:" + type;
    }
    if (store != null && !"".equals(store)) {
      r.setStore(store);
      excelCondition += "渠道类型:" + store;
    }
    if (storeType != null && !"".equals(storeType)) {
      r.setStoreType(storeType);
      excelCondition += "店铺类型:" + storeType;
    }
    if (startTime != null && !"".equals(startTime)) {
      r.setStartTime(startTime);
      excelCondition += "开始时间:" + startTime;
    }
    if (endTime != null && !"".equals(endTime)) {
      r.setEndTime(endTime);
      excelCondition += "结束时间:" + endTime;
    }

    // 文件名获取
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    String f = "400工单信息列表-" + format.format(date);
    this.fileName = f;
    setResponseHeader(response);
    OutputStream out = null;
    List<Reviewpoolfordhzx> list = new ArrayList<Reviewpoolfordhzx>();
    try {
      out = response.getOutputStream();
      ServiceResult<List<Reviewpoolfordhzx>> resp = woReviewpoolfordhzxService
          .pageReviewpoolfordhzx(r, pager);
      list = resp.getResult();
      WebReviewExportdhzxle exportUsableStockTemplate = new WebReviewExportdhzxle();
      if (list.size() <= 0) {
        Reviewpoolfordhzx pool = new Reviewpoolfordhzx();
        list.add(pool);
      }
      exportUsableStockTemplate.toExcel(list, request, LENGTH, f, out);
      //获取用户登录名
      HttpSession session = request.getSession();
      String userName1 = (String) session.getAttribute("userName");

      //从cookie中获取用户登录名结束
      //TODO 工单导出log
      if ("".equals(excelCondition)) {
        excelCondition = "无";
      }
      LogBean log = new LogBean(userName1, Constant.MKNAME.MKNAME_3, Constant.LOG.LOG_25,
          "导出条件:" + excelCondition + " 共导出:" + list.size() + "条", "");
      woReviewLogService.insertLog(log);
    } catch (IOException e1) {
      log.debug("Exception:", e1);
    }

  }

  /**
   * 工单信息导出
   */
  @ResponseBody
  @RequestMapping(value = "/workOrder", method = RequestMethod.GET)
  public void exportReView(
      @RequestParam(value = "orderId_search", required = false) String orderId_search,
      //                             @RequestParam(value = "orderCome_search", required = false) String orderCome_search,
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
      @RequestParam(value = "centerType", required = false) String centerType,
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
      @RequestParam(value = "channelName_search", required = false) String channelName,
      HttpServletRequest request, HttpServletResponse response) {
    ReviewPool reViewPool = new ReviewPool();
    String excelCondition = "";

    if (id != null && !"".equals(id)) {
      reViewPool.setId(id);
      excelCondition += " 工单号:" + id;
    }
    if (closeType != null && !"".equals(closeType)) {
      reViewPool.setCloseType(closeType);
      String closeTypeTmp = "";
      if ("1".equals(closeType)) {
        closeTypeTmp = "正常关闭";
      } else if ("2".equals(closeType)) {
        closeTypeTmp = "强制关闭";
      } else if ("3".equals(closeType)) {
        closeTypeTmp = "虚假封单";
      } else if ("4".equals(closeType)) {
        closeTypeTmp = "客服原因";
      }
      excelCondition += " 关闭原因:" + closeTypeTmp;
    }
    if (orderId_search != null && !"".equals(orderId_search)) {
      reViewPool.setOrderId(orderId_search);
      excelCondition += " 订单号:" + orderId_search;
    }
    //        if (orderCome_search != null && !"".equals(orderCome_search)) {
    //            reViewPool.setOrderCome(orderCome_search);
    //            excelCondition += " 订单来源:" + orderCome_search;
    //        }
    if (netOrderId != null && !"".equals(netOrderId)) {
      reViewPool.setNetOrderId(netOrderId);
      excelCondition += " 网单号:" + netOrderId;
    }
    if (workStatus != null && !"".equals(workStatus)) {
      reViewPool.setWorkStatus(workStatus);
      String tmp = "";
      if ("0".equals(workStatus)) {
        tmp = "未处理";
      } else if ("2".equals(workStatus)) {
        tmp = "中间结果";
      } else if ("3".equals(workStatus)) {
        tmp = "最终结果";
      }
      excelCondition += " 工单状态:" + tmp;
    }
    if (level1Search != null && !"".equals(level1Search)) {
      reViewPool.setQuestion1Level1(level1Search);
      excelCondition += " 责任位1:" + level1Search;
    }
    if (level2Search != null && !"".equals(level2Search)) {
      reViewPool.setQuestion1Level2(level2Search);
      excelCondition += " 责任位2:" + level2Search;
    }
    if (level3Search != null && !"".equals(level3Search)) {
      reViewPool.setQuestion1Level3(level3Search);
      excelCondition += " 责任位3:" + level3Search;
    }
    if (workOrderTo != null && !"".equals(workOrderTo)) {
      reViewPool.setWorkOrderTo(workOrderTo);
      excelCondition += " 工单去向:" + workOrderTo;
    }
    if (centerType != null && !"".equals(centerType)) {
      reViewPool.setCenterType(centerType);
      excelCondition += " 工单类别:" + centerType;
    }
    if (company != null && !"".equals(company)) {
      reViewPool.setCompany(company);
      excelCondition += " 工贸:" + company;
    }
    if (phone != null && !"".equals(phone)) {
      reViewPool.setPhone(phone);
      excelCondition += " 物流中心:" + phone;
    }
    if (wangId != null && !"".equals(wangId)) {
      reViewPool.setWangId(wangId);
      excelCondition += " 人员:" + wangId;
    }
    if (remark3 != null && !"".equals(remark3)) {
      reViewPool.setRemark3(remark3);
      excelCondition += " 操作人:" + remark3;
    }
    if (remark5 != null && !"".equals(remark5)) {
      reViewPool.setRemark5(remark5);
      excelCondition += " 订单来源:" + remark5;
    }
    if ("".equals(startTime)) {
      startTime = null;
    } else {
      excelCondition += " 开始时间:" + startTime;
    }
    if ("".equals(endTime)) {
      endTime = null;
    } else {
      excelCondition += " 结束时间:" + endTime;
    }
    if (clientName != null && !"".equals(clientName)) {
      reViewPool.setBuyer(clientName);
      excelCondition += " 收货联系人:" + clientName;
    }
    if (clientTel != null && !"".equals(clientTel)) {
      reViewPool.setBuyerMobile(clientTel);
      excelCondition += " 收货电话:" + clientTel;
    }
    if (complaintFlg != null) {
      reViewPool.setComplaintFlg(complaintFlg);//是否投诉
      String tmp = "";
      if (0 == complaintFlg) {
        tmp = "否";
      } else if (1 == complaintFlg) {
        tmp = "是";
      }
      excelCondition += " 是否投诉:" + tmp;
    }
    if (channelName != null && !"".equals(channelName)) {
      reViewPool.setChannelName(channelName);
      excelCondition += " 订单渠道:" + clientTel;
    }
    PagerInfo pager = new PagerInfo(MAX, 1);
    // 文件名获取
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    String f = "工单列表-" + format.format(date);
    this.fileName = f;
    setResponseHeader(response);
    OutputStream out;
    try {
      out = response.getOutputStream();
      ServiceResult<JSONObject> res = reviewWorkOrderService.page(reViewPool, pager,
          startTime, endTime);
      List<ReviewPool> list = (List<ReviewPool>) res.getResult().get("rows");
      WebReviewExportTemplate exportUsableStockTemplate = new WebReviewExportTemplate();
      if (list.size() <= 0) {
        ReviewPool pool = new ReviewPool();
        list.add(pool);
      }
      exportUsableStockTemplate.toExcel(list, request, LENGTH, f, out);
      //从cookie中获取用户登录名
      String userName = this.getUserName(request);
      //从cookie中获取用户登录名结束
      //TODO 工单导出log
      if ("".equals(excelCondition)) {
        excelCondition = "无";
      }
      LogBean log = new LogBean(userName, Constant.MKNAME.MKNAME_3, Constant.LOG.LOG_16,
          "导出条件:" + excelCondition + " 共导出:" + list.size() + "条", "");
      woReviewLogService.insertLog(log);
    } catch (IOException e1) {
      log.debug("Exception:", e1);
    }

  }

  /**
   * 责任位统计导出
   */
  @ResponseBody
  @RequestMapping(value = "/export2.html", method = RequestMethod.GET)
  public void exportDutyStatistic(
      @RequestParam(value = "startTime_search2", required = false) String startTime,
      @RequestParam(value = "endTime_search2", required = false) String endTime,
      @RequestParam(value = "level1_search2", required = false) String question1Level1,
      @RequestParam(value = "page", required = false) Integer no,
      @RequestParam(value = "rows", required = false) Integer size,
      HttpServletRequest request, HttpServletResponse response) {

    String excelCondition = "";
    PagerInfo pager = new PagerInfo(MAX, 1);
    // 文件名获取
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    String f = "责任位统计-" + format.format(date);
    this.fileName = f;
    setResponseHeader(response);
    OutputStream out;
    List<DutyStatistic> list;
    try {
      out = response.getOutputStream();
      Map<String, Object> map = new HashMap<String, Object>();
      if (startTime != null && !"".equals(startTime)) {
        map.put("startTime", startTime);
        excelCondition += " 开始时间:" + startTime;
      }
      if (endTime != null && !"".equals(endTime)) {
        map.put("endTime", endTime);
        excelCondition += " 结束时间:" + endTime;
      }
      if (question1Level1 != null && !"".equals(question1Level1)) {
        map.put("question1Level1", question1Level1);
        excelCondition += " 责任位1:" + question1Level1;
      }
      map.put("page", pager);
      ServiceResult<List<DutyStatistic>> result = reviewWorkOrderService.dutyStatistic(map);

      list = result.getResult();
      WebReviewExportDutyStatistic exportDutyStatistic = new WebReviewExportDutyStatistic();
      if (list.size() <= 0) {
        DutyStatistic pool = new DutyStatistic("", "");
        list.add(pool);
      }
      exportDutyStatistic.toExcel(list, request, LENGTH, f, out);
      //从cookie中获取用户登录名
      String userName = this.getUserName(request);
      //从cookie中获取用户登录名结束
      //TODO 责任位统计导出log
      if ("".equals(excelCondition)) {
        excelCondition = "无";
      }
      LogBean log = new LogBean(userName, Constant.MKNAME.MKNAME_3, Constant.LOG.LOG_17,
          "导出条件:" + excelCondition + " 共导出:" + list.size() + "条", "");
      woReviewLogService.insertLog(log);
    } catch (IOException e1) {
      log.debug("Exception:", e1);
    }

  }

  /**
   * 人员统计导出
   */
  @ResponseBody
  @RequestMapping(value = "/export3.html", method = RequestMethod.GET)
  public void exportPersonnelStatistic(
      @RequestParam(value = "startTime_search3", required = false) String startTime,
      @RequestParam(value = "endTime_search3", required = false) String endTime,
      @RequestParam(value = "person_search3", required = false) String wangId,
      @RequestParam(value = "page", required = false) Integer no,
      @RequestParam(value = "rows", required = false) Integer size,
      HttpServletRequest request, HttpServletResponse response) {

    String excelCondition = "";
    PagerInfo pager = new PagerInfo(MAX, 1);
    // 文件名获取
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    String f = "人员统计-" + format.format(date);
    this.fileName = f;
    setResponseHeader(response);
    OutputStream out;
    List<PersonnelStatistic> list;
    try {
      out = response.getOutputStream();
      Map<String, Object> map = new HashMap<String, Object>();
      if (startTime != null && !"".equals(startTime)) {
        map.put("startTime", startTime);
        excelCondition += " 开始时间:" + startTime;
      }
      if (endTime != null && !"".equals(endTime)) {
        map.put("endTime", endTime);
        excelCondition += " 结束时间:" + endTime;
      }
      if (wangId != null && !"".equals(wangId)) {
        map.put("wangId", wangId);
        excelCondition += " 人员:" + wangId;
      }
      map.put("page", pager);
      ServiceResult<List<PersonnelStatistic>> result = reviewWorkOrderService
          .personnelStatistic(map);
      list = result.getResult();
      WebReviewExportPersonnelStatistic exportPersonnelStatistic = new WebReviewExportPersonnelStatistic();
      if (list.size() <= 0) {
        PersonnelStatistic pool = new PersonnelStatistic("", "", "");
        list.add(pool);
      }
      exportPersonnelStatistic.toExcel(list, request, LENGTH, f, out);
      //从cookie中获取用户登录名
      String userName = this.getUserName(request);
      //从cookie中获取用户登录名结束
      //TODO 责任位统计导出log
      if ("".equals(excelCondition)) {
        excelCondition = "无";
      }
      LogBean log = new LogBean(userName, Constant.MKNAME.MKNAME_3, Constant.LOG.LOG_18,
          "导出条件:" + excelCondition + " 共导出:" + list.size() + "条", "");
      woReviewLogService.insertLog(log);
    } catch (IOException e1) {
      log.debug("Exception:", e1);
    }

  }

  /**
   * 人员信息导出
   */
  @ResponseBody
  @RequestMapping(value = "/export4.html", method = RequestMethod.GET)
  public void exportPeopleStatistic(
      @RequestParam(value = "username_search", required = false) String username,
      @RequestParam(value = "phone_search", required = false) String phone,
      @RequestParam(value = "email_search", required = false) String email,
      @RequestParam(value = "type_search", required = false) String type,
      @RequestParam(value = "issend_search", required = false) String issend,
      HttpServletRequest request, HttpServletResponse response) {

    String excelCondition = "";
    PagerInfo pager = new PagerInfo(MAX, 1);
    WOUser record = new WOUser();
    if (username != null && !"".equals(username)) {
      record.setUserName(username);
      excelCondition += "人员:" + username;
    }
    if (phone != null && !"".equals(phone)) {
      record.setMobile(phone);
      excelCondition += "电话:" + phone;
    }
    if (email != null && !"".equals(email)) {
      record.setEmail(email);
      excelCondition += "邮件:" + email;
    }
    if (type != null && !"".equals(type)) {
      record.setUserType(Integer.valueOf(type));
      excelCondition += "员工类型:" + type;
    }
    if (issend != null && !"".equals(issend)) {
      record.setSendEmail(Integer.valueOf(issend));
      excelCondition += "发送统计邮件:" + issend;
    }
    // 文件名获取
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    String f = "人员信息列表-" + format.format(date);
    this.fileName = f;
    setResponseHeader(response);
    OutputStream out;
    List<WOUser> list;
    try {
      out = response.getOutputStream();
      JSONObject resp = workOrderWOUserService.getPageByParam(record, pager);
      list = (List<WOUser>) resp.get("rows");
      WebReviewExportPeople exportUsableStockTemplate = new WebReviewExportPeople();
      if (list.size() <= 0) {
        WOUser pool = new WOUser();
        list.add(pool);
      }
      exportUsableStockTemplate.toExcel(list, request, LENGTH, f, out);
      //从cookie中获取用户登录名
      String userName = this.getUserName(request);
      //从cookie中获取用户登录名结束
      //TODO 工单导出log
      if ("".equals(excelCondition)) {
        excelCondition = "无";
      }
      LogBean log = new LogBean(userName, Constant.MKNAME.MKNAME_1, Constant.LOG.LOG_19,
          "导出条件:" + excelCondition + " 共导出:" + list.size() + "条", "");
      woReviewLogService.insertLog(log);
    } catch (IOException e1) {
      log.debug("Exception:", e1);
    }

  }

  /**
   * 人员责任位配置导出
   */
  @ResponseBody
  @RequestMapping(value = "/export5.html", method = RequestMethod.GET)
  public void exportaddressListStatistic(
      @RequestParam(value = "question1level1_search", required = false) String question1level1,
      @RequestParam(value = "question1level2_search", required = false) String question1level2,
      @RequestParam(value = "ordercome_search", required = false) String ordercome,
      @RequestParam(value = "username_search", required = false) String username,
      @RequestParam(value = "manageruserId1_search", required = false) String manageruserId1,
      @RequestParam(value = "manageruserId2_search", required = false) String manageruserId2,
      @RequestParam(value = "company_search", required = false) String company,
      @RequestParam(value = "page", required = false) Integer no,
      @RequestParam(value = "rows", required = false) Integer size,
      HttpServletRequest request, HttpServletResponse response) {
    String excelCondition = "";
    PagerInfo pager = new PagerInfo(MAX, 1);
    ReviewContactsDto record = new ReviewContactsDto();
    if (question1level1 != null && !"".equals(question1level1)) {
      record.setQuestion1level1(question1level1);
      excelCondition += "责任位1:" + question1level1;
    }
    if (question1level2 != null && !"".equals(question1level2)) {
      record.setQuestion1level2(question1level2);
      excelCondition += "责任位2:" + question1level2;
    }
    if (ordercome != null && !"".equals(ordercome)) {
      record.setOrdercome(ordercome);
      excelCondition += "订单来源:" + ordercome;
    }
    if (username != null && !"".equals(username)) {
      record.setUsername(username);
      excelCondition += "人员:" + username;
    }
    if (manageruserId1 != null && !"".equals(manageruserId1)) {
      record.setManageruserid1(manageruserId1);
      excelCondition += "一级领导:" + manageruserId1;
    }
    if (manageruserId2 != null && !"".equals(manageruserId2)) {
      record.setManageruserid1(manageruserId1);
      excelCondition += "二级领导:" + manageruserId2;
    }
    if (company != null && !"".equals(company)) {
      record.setCompany(company);
      excelCondition += "工贸:" + company;
    }
    // 文件名获取
    Date date = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    String f = "人员责任位配置列表-" + format.format(date);
    this.fileName = f;
    setResponseHeader(response);
    OutputStream out = null;
    List<ReviewContactsDto> list = new ArrayList<ReviewContactsDto>();
    try {
      out = response.getOutputStream();
      JSONObject resp = woReviewContactsService
          .page(record, pager);
      list = (List<ReviewContactsDto>) resp.get("rows");
      WebReviewExportaddressList exportUsableStockTemplate = new WebReviewExportaddressList();
      if (list.size() <= 0) {
        ReviewContactsDto pool = new ReviewContactsDto();
        list.add(pool);
      }
      exportUsableStockTemplate.toExcel(list, request, LENGTH, f, out);
      //从cookie中获取用户登录名
      String userName = this.getUserName(request);
      //从cookie中获取用户登录名结束
      //TODO 工单导出log
      if ("".equals(excelCondition)) {
        excelCondition = "无";
      }
      LogBean log = new LogBean(userName, Constant.MKNAME.MKNAME_2, Constant.LOG.LOG_20,
          "导出条件:" + excelCondition + " 共导出:" + list.size() + "条", "");
      woReviewLogService.insertLog(log);
    } catch (IOException e1) {
      log.debug("Exception:", e1);
    }

  }


  private String getUserName(HttpServletRequest request) {
    return (String) request.getSession().getAttribute("userName");
  }
}
