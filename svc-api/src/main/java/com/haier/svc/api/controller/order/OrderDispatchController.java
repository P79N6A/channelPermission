package com.haier.svc.api.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.order.service.ProductsManageService;
import com.haier.shop.model.ProductBase;
import com.haier.stock.model.SystemTo3wCbs;
import com.haier.stock.service.HandleVomDataService;
import com.haier.stock.service.StockCenterItemServiceNew;
import com.haier.stock.service.SystemTo3wCbsService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: gaohaiming
 * @description: 订单派工
 * @date:created in 2018/7/5 10:21
 * @modificed by:
 */
@Controller
@RequestMapping(value = "/dispatch")
public class OrderDispatchController {

  @Autowired
  private SystemTo3wCbsService systemTo3wCbsService;
  @Autowired
  private ProductsManageService productsManageService;
  @Autowired
  private HandleVomDataService handleVomDataService;

  @RequestMapping(value = "/failPage.html")
  public String getFailPage() {
    return "order/orderDispatchPage";
  }

  @ResponseBody
  @RequestMapping(value = "/failList.html", method = RequestMethod.POST)
  public String getFailData(HttpServletRequest request, HttpServletResponse response) {
    Integer page = Integer
        .parseInt(null == request.getParameter("page") ? "1" : request.getParameter("page"));
    Integer rows = Integer
        .parseInt(null == request.getParameter("rows") ? "20" : request.getParameter("rows"));
    String cOrderSn = request.getParameter("cOrderSn");
    String createTimeMin = request.getParameter("createTimeMin");
    String createTimeMax = request.getParameter("createTimeMax");

    Map<String, Object> param = new HashMap<String, Object>(6);

    param.put("index", (page - 1) * rows);
    param.put("rows", rows);
    param.put("cOrderSn", cOrderSn);
    param.put("createTimeMin", createTimeMin);
    param.put("createTimeMax", createTimeMax);
    param.put("interfaceFlag", 3);
    Map<String, Object> result = new HashMap<String, Object>(3);
    response.setContentType("application/json;charset=UTF-8");
    try {
      ServiceResult<List<JSONObject>> serviceResult = systemTo3wCbsService.queryFailList(param);
      ServiceResult<Integer> counts = systemTo3wCbsService.queryFailCounts(param);
      result.put("rows", serviceResult.getResult());
      result.put("total", counts.getResult());
    } catch (Exception ex) {
      result.put("rows", new ArrayList<>());
      result.put("total", 0);
      result.put("error", ex.getMessage());
    }
    return JsonUtil.toJson(result);
  }

  @ResponseBody
  @RequestMapping(value = "/reSign", method = RequestMethod.POST)
  public String reSign(HttpServletRequest request) {
    Integer systemId = Integer.parseInt(request.getParameter("systemId"));
    Map<String, Object> result = new HashMap<String, Object>(3);
    try {
      ServiceResult<SystemTo3wCbs> serviceResult = systemTo3wCbsService.getRecordById(systemId);
      if (serviceResult.getSuccess()) {
        handleVomDataService.manualHandleAcceptHpTime(Arrays.asList(serviceResult.getResult()));
        ServiceResult<SystemTo3wCbs> newResult = systemTo3wCbsService.getRecordById(systemId);
        if (newResult.getSuccess()) {
          result.put("success", true);
          result.put("message", newResult.getResult().getErrorMessage());
        } else {
          result.put("success", false);
          result.put("message", newResult.getMessage());
        }
      } else {
        result.put("success", false);
        result.put("message", serviceResult.getMessage());
      }
    } catch (Exception e) {
      result.put("success", false);
      result.put("message", e.getMessage());
    }
    return JsonUtil.toJson(result);
  }

}
