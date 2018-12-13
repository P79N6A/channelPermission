package com.haier.svc.api.controller.stock;

import com.haier.common.PagerInfo;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.OrderForecast;
import com.haier.svc.api.controller.stock.mode.PurchaseForecastModel;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 采购预测
 *
 * @author:
 * @description:
 * @date:created in 2018/4/25 9:07
 * @modificed by:
 */
@Controller
@RequestMapping(value = "purchaseForecast")
public class PurchaseForecastController {

  private final static org.apache.log4j.Logger logger = LogManager
      .getLogger(PurchaseForecastController.class);

  @Resource
  private PurchaseForecastModel purchaseForecastModel;

  @RequestMapping(value = {"/purchaseForecast"}, method = {RequestMethod.GET})
  String getPurchaseForecast(Map<String, Object> modelMap) {
    Calendar cl = Calendar.getInstance();
    cl.setFirstDayOfWeek(Calendar.WEDNESDAY);
    modelMap.put("searchYear", cl.get(Calendar.YEAR));
    modelMap.put("searchWeek", cl.get(Calendar.WEEK_OF_YEAR));
    return "/stock/purchaseForecast";
  }

  @RequestMapping(value = {"/purchaseForecast1"}, method = {RequestMethod.GET})
  String getPurchaseForecast1(Map<String, Object> modelMap) {
    Calendar cl = Calendar.getInstance();
    cl.setFirstDayOfWeek(Calendar.WEDNESDAY);
    modelMap.put("searchYear", cl.get(Calendar.YEAR));
    modelMap.put("searchWeek", cl.get(Calendar.WEEK_OF_YEAR));
    return "/stock/purchaseForecast1";
  }

  @RequestMapping(value = { "/purchaseForecastList1" }, method = { RequestMethod.GET })
  String getPurchaseForecastList(Map<String, Object> modelMap,
      @RequestParam(required = false) String sec_code,
      @RequestParam(required = false) String channel,
      @RequestParam(required = false) String sku,
      @RequestParam(required = false) String type,
      @RequestParam(required = false) Integer pageIndex,
      @RequestParam(required = false) Integer pageSize,
      @RequestParam(required = false) Integer searchWeek,
      @RequestParam(required = false) Integer searchYear,
      @RequestParam(required = false) String product_type_name) {

    if (pageIndex == null) {
      pageIndex = 1;
    }
    if (pageSize == null) {
      pageSize = 100;
    }

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("sec_code", sec_code.trim());
    params.put("sku", sku.trim());
    params.put("orderWeek", searchWeek);
    params.put("orderYear", searchYear);
    params.put("product_type_name", product_type_name);

    PagerInfo pagerInfo = new PagerInfo(pageSize, pageIndex);
        /* ServiceResult<List<OrderForecast>> result = purchaseForecastModel.getOrderForecastList(
             pagerInfo, params);*/
    List<OrderForecast> orderForecastList = purchaseForecastModel.getOrderForecastList(
        pagerInfo, params);

        /* if (!result.getSuccess()) {
             logger.error("获取采购预测信息出现未知异常：" + result.getMessage());
             throw new BusinessException(result.getMessage());
         }*/

    addPagerParam(modelMap, pagerInfo);

        /*   List<OrderForecast> forecastList = result.getResult();*/
    modelMap.put("purchaseForecastList", orderForecastList);
    if (StringUtil.isEmpty(channel))
      channel = "ALL";
    modelMap.put("channel", channel);
    if ("main".equals(type))
      return "/stock/purchaseForecastList";
    else if ("channel".equals(type))
      return "/stock/purchaseForecastListForChannels";
    else
      return "/stock/purchaseForecastListForSku";
  }

  @RequestMapping(value = {"purchaseForecastList"}, method = {RequestMethod.POST})
  public void purchaseForecastList(Map<String, Object> modelMap,
      @RequestParam(required = false) String sec_code,
      @RequestParam(required = false) String channel,
      @RequestParam(required = false) String sku,
      @RequestParam(required = false) String type,
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer rows,
      @RequestParam(required = false) Integer searchWeek,
      @RequestParam(required = false) Integer searchYear,
      @RequestParam(required = false) String product_type_name,
      HttpServletResponse response) {
    try {
      if (page == null) {
        page = 1;
      }
      if (rows == null) {
        rows = 100;
      }

      Map<String, Object> params = new HashMap<String, Object>();
      params.put("sec_code", sec_code.trim());
      params.put("sku", sku.trim());
      params.put("orderWeek", searchWeek);
      params.put("orderYear", searchYear);
      if("全部".equals(product_type_name)){
        params.put("product_type_name", "");
      } else {
        params.put("product_type_name", product_type_name);
      }


      PagerInfo pagerInfo = new PagerInfo(rows, page);

      List<OrderForecast> orderForecastList = purchaseForecastModel.getOrderForecastList(
          pagerInfo, params);

      addPagerParam(modelMap, pagerInfo);
      modelMap.put("purchaseForecastList", orderForecastList);
      if (StringUtil.isEmpty(channel)) {
        channel = "ALL";
      }
      modelMap.put("channel", channel);
      modelMap.put("total", orderForecastList.size());
      modelMap.put("rows", orderForecastList);
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(JsonUtil.toJson(modelMap));
      response.getWriter().flush();
      response.getWriter().close();
    } catch (Exception e) {
      logger.error("查询失败",e);
    }
  }

  private void addPagerParam(Map<String, Object> modelMap, PagerInfo pager) {
    Integer totalPage = pager.getRowsCount() % pager.getPageSize() == 0 ? pager.getRowsCount()
        / pager.getPageSize()
        : pager.getRowsCount() / pager.getPageSize() + 1;
    modelMap.put("totalCount", pager.getRowsCount());
    if (pager.getPageIndex() > 1) {
      modelMap.put("hasFirst", true);
    } else {
      modelMap.put("hasFirst", false);
    }
    if (pager.getPageIndex() - 1 > 0) {
      modelMap.put("hasPrevious", true);
    } else {
      modelMap.put("hasPrevious", false);
    }
    if (pager.getPageIndex() + 1 <= totalPage) {
      modelMap.put("hasNext", true);
    } else {
      modelMap.put("hasNext", false);
    }
    if (totalPage > 1 && pager.getPageIndex() != totalPage) {
      modelMap.put("hasLast", true);
    } else {
      modelMap.put("hasLast", false);
    }
    modelMap.put("curPage", totalPage > 0 ? pager.getPageIndex() : 0);
    modelMap.put("totalPage", totalPage);
    modelMap.put("startNum", pager.getStart() + 1);
    modelMap
        .put(
            "endNum",
            (pager.getStart() + pager.getPageSize()) > pager.getRowsCount() ? (pager.getStart()
                + pager
                .getRowsCount() % pager.getPageSize())
                : pager.getStart() + pager.getPageSize());
  }

}
