package com.haier.eis.services;

import com.haier.common.PagerInfo;
import com.haier.eis.dao.eis.OrderForecastDao;
import com.haier.eis.dao.eis.OrderForecastSeasonWriteDao;
import com.haier.eis.model.OrderForecast;
import com.haier.eis.model.OrderForecastSeason;
import com.haier.eis.service.PurchaseForecastService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author: liwei
 * @description:
 * @date:created in 2018/4/25 16:53
 * @modificed by:
 */
@Service
public class PurchaseForecastServiceImpl implements PurchaseForecastService {
  private Logger logger = LogManager.getLogger(PurchaseForecastService.class);

  @Resource
  private OrderForecastSeasonWriteDao orderForecastSeasonWriteDao;
  @Resource
  private OrderForecastDao orderForecastDao;

  @Override
  public List<OrderForecast> getOrderForecastList(PagerInfo pagerInfo, Map<String, Object> params) {
    params.put("start", pagerInfo.getStart());
    params.put("size", pagerInfo.getPageSize());
    int count = orderForecastDao.getCount(params);
    pagerInfo.setRowsCount(count);
    return orderForecastDao.getAll(params);
  }

  @Override
  public List<OrderForecast> exportOrderForecastList(PagerInfo pagerInfo,
      Map<String, Object> params) {
    params.put("start", pagerInfo.getStart());
    params.put("size", pagerInfo.getPageSize());
    int count = orderForecastDao.getCount(params);
    pagerInfo.setRowsCount(count);
    return orderForecastDao.getAll(params);
  }

  @Override
  public void InsertOrderForecastSeason(List<OrderForecastSeason> l) {
    int ofsSize = l.size();
    int execCount = 1000; //每次执行条数
    if (ofsSize < execCount) {
      this.orderForecastSeasonWriteDao.InsertOrderForecastSeason(l);
    } else {

      int currentPage = ofsSize / execCount + (ofsSize % execCount == 0 ? 0 : 1);
      for (int i = 0; i < currentPage; i++) {
        if (i == 0) {
          List<OrderForecastSeason> pageL = l.subList(i * execCount, execCount - 1);
          this.orderForecastSeasonWriteDao.InsertOrderForecastSeason(pageL);
        }
        if (i >= 1) {
          int currentCount = i * execCount - 1 + execCount;
          List<OrderForecastSeason> pageL = l.subList(i * execCount - 1,
              currentCount > ofsSize ? ofsSize : currentCount);
          this.orderForecastSeasonWriteDao.InsertOrderForecastSeason(pageL);
        }
      }
    }

  }

  @Override
  public void deleteOrderForecastSeason() {
    this.orderForecastSeasonWriteDao.deleteOrderForecastSeason();
  }
}
