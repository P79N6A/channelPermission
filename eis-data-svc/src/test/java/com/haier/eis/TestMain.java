package com.haier.eis;

import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.eis.services.LesStockTransQueueServiceImpl;
import java.util.Date;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: gaohaiming
 * @description:
 * @date:created in 2018/5/28 9:59
 * @modificed by:
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMain {


  public static void main(String[] args) {
    ClassPathXmlApplicationContext applicationContext =
        new ClassPathXmlApplicationContext("spring/framework-dubbo-consumer.xml");
    applicationContext.start();
    LesStockTransQueueService lesStockTransQueueService =
        (LesStockTransQueueService) applicationContext.getBean("lesStockTransQueueService");
    LesStockTransQueue test = new LesStockTransQueue();
    test.setLesBatchId(123);
    test.setCharg("20");
    test.setCharg("20");
    test.setAddTime(new Date());
    test.setVersionCode("version");
    test.setIsManualSetChannel(1);
    int result = lesStockTransQueueService.insert(test);
    System.out.println("+++++++++++++" + result + "+++++++++++");
  }

}
