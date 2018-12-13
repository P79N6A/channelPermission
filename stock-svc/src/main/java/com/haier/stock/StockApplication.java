package com.haier.stock;

import com.haier.stock.model.StockAgeModel;
import com.haier.stock.util.DateFormatUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@ImportResource({ "classpath:spring/framework-dubbo-provider.xml","classpath:/spring/framework-dubbo-consumer.xml" })
public class StockApplication {


	public static ConfigurableApplicationContext applicationContext;
	public static void main(String[] args) {
//		SpringApplication.run(StockApplication.class, args);
		StockApplication.applicationContext = SpringApplication.run(StockApplication.class, args);
		System.out.print("-----------------------启动完毕-------------------------");

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
