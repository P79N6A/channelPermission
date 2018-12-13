package com.haier.svc.api.controller.stock.mode;

import com.haier.common.PagerInfo;
import com.haier.eis.model.OrderForecast;
import com.haier.eis.model.OrderForecastSeason;
import com.haier.eis.service.PurchaseForecastService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class PurchaseForecastModel {

  @Resource
  private PurchaseForecastService purchaseForecastService;

  public List<OrderForecast> getOrderForecastList(PagerInfo pagerInfo, Map<String, Object> params) {
    return purchaseForecastService.getOrderForecastList(pagerInfo, params);
  }

  public List<OrderForecast> exportOrderForecastList(PagerInfo pagerInfo,
      Map<String, Object> params) {
    return purchaseForecastService.exportOrderForecastList(pagerInfo, params);
  }

  public void InsertOrderForecastSeason(List<OrderForecastSeason> l) {
    purchaseForecastService.InsertOrderForecastSeason(l);
  }

  public void deleteOrderForecastSeason() {
    purchaseForecastService.deleteOrderForecastSeason();
  }
}
