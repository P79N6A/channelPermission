package com.haier.stock.services;

import com.haier.stock.StockApplication;
import com.haier.stock.util.DateFormatUtil;
import com.haier.stock.util.SpringContextUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.stock.model.StockAgeModel;
import com.haier.stock.service.StockAgeInStorageService;
@Service
public class StockAgeInStorageServiceImpl implements StockAgeInStorageService {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(StockAgeInStorageServiceImpl.class);
	@Autowired
	private StockAgeModel stockAgeModel;
	@Override
	//@Scheduled(cron = "*/5 * * * * ?")
    public ServiceResult<Boolean> calculateStockAgeDaily() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            stockAgeModel.calculateStockAgeDaily();
            result.setResult(true);
        } catch (Exception e) {
            log.error("按天计算库龄出现错误:", e);
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

  @Override
  //@Scheduled(cron = "*/5 * * * * ?")
  public ServiceResult<Boolean> calculateStockAgeTimeHistory(Date date) {
    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    try {
      stockAgeModel.calculateStockAgeTimelyHistory(date);
      result.setResult(true);
    } catch (Exception e) {
      log.error("按天计算库龄出现错误:", e);
      result.setSuccess(false);
      result.setResult(false);
      result.setMessage(e.getMessage());
    }
    return result;
  }
	
	/**
	 * 计算库龄(实时)
	 */
	@Override
   // @Scheduled(cron = "*/5 * * * * ?")
    public ServiceResult<Boolean> calculateStockAgeTimely() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            stockAgeModel.calculateStockAgeTimely();
            result.setResult(true);
        } catch (Exception e) {
            log.error("计算库龄(实时)出现错误:", e);
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

  @Override
  //@Scheduled(cron = "*/5 * * * * ?")
  public ServiceResult<Boolean> calculateStockAgeDayHistory(Date date) {
    ServiceResult<Boolean> result = new ServiceResult<Boolean>();
    try {
      stockAgeModel.calculateStockAgeDayHistory(date);
      result.setResult(true);
    } catch (Exception e) {
      log.error("按天计算库龄出现错误:", e);
      result.setSuccess(false);
      result.setResult(false);
      result.setMessage(e.getMessage());
    }
    return result;
  }

  public static void main(String[] args){
    Date start = DateFormatUtil.parseByType("yyyy-MM-dd","2018-06-22");
    Date end = DateFormatUtil.parseByType("yyyy-MM-dd","2018-08-14");
    List<Date> dates = getEveryday(start ,end);
    StockAgeModel model = (StockAgeModel) StockApplication.applicationContext.getBean("stockAgeModel");
    for (Date today:dates){

      model.calculateStockAgeDayHistory(today);
      System.out.println(today+"按天统计的处理结果结束");
      model.calculateStockAgeTimelyHistory(today);
      System.out.println(today+"实时统计的处理结果结束");
    }
  }

  public static    List<Date> getEveryday(Date beginDate , Date endDate){
    List<Date> results = new ArrayList<>();
    Calendar startTime =  Calendar.getInstance();
    startTime.setTime(beginDate);
    do {
      results.add(startTime.getTime());
      startTime.add(Calendar.DATE,1);
    }while (startTime.getTime().before(endDate));
    return results;
  }


}
