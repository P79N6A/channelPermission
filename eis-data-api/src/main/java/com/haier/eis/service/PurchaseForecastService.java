package com.haier.eis.service;

import com.haier.common.PagerInfo;
import com.haier.eis.model.OrderForecast;
import com.haier.eis.model.OrderForecastSeason;
import java.util.List;
import java.util.Map;

/**
 * @author: liwei
 * @description:
 * @date:created in 2018/4/25 16:51
 * @modificed by:
 */
public interface PurchaseForecastService {

  public List<OrderForecast> getOrderForecastList(PagerInfo pagerInfo, Map<String, Object> params);

  public List<OrderForecast> exportOrderForecastList(PagerInfo pagerInfo,
      Map<String, Object> params);

  public void InsertOrderForecastSeason(List<OrderForecastSeason> l);

  public void deleteOrderForecastSeason();

}
