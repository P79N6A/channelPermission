package com.haier.stock.services;

import com.haier.shop.model.OrderProductsNew;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.stock.datasource.ReadWriteRoutingDataSourceHolder;
import com.haier.stock.service.StockCenterOrderPubService;

/**
 * Created by 胡万来 on 2017/12/25 0025.
 */
@Service
@Configuration
@EnableScheduling
public class StockCenterOrderPubServiceImpl implements StockCenterOrderPubService {

    private static Logger log = LoggerFactory.getLogger(StockCenterOrderPubServiceImpl.class);
    @Autowired
	private FrozenStockModel frozenStockModel;
    @Autowired
    private ConfirmOrderModel confirmOrderModel;

    @Override
//    @Scheduled(cron = "0 0/3 * * * ?")
    public ServiceResult<Boolean> autoConfirmOrder() {

        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
            confirmOrderModel.autoConfirmOrder();
            result.setResult(true);
        } catch (Exception e) {
            log.error("自动确认订单,发生异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(false);
        } finally {
            ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
        }
        return result;
    }
    /**
     * 吴坤洋 占用库存 定时任务
     * @author wukunyang
     */
//	 @Scheduled(cron="0/5 * *  * * ?")
    @Override
   public ServiceResult<Boolean> autoFrozenStockByOrder() {
       ServiceResult<Boolean> result = new ServiceResult<Boolean>();
       try {
           ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
           frozenStockModel.autoFrozenStock();
           result.setResult(true);
       } catch (Exception e) {
           log.error("自动锁定库存,发生异常：", e);
           result.setMessage("自动锁定库存发生异常：" + e.getMessage()); 
           result.setSuccess(false);
           result.setResult(false);
       } finally {
           ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
       }
       return result;
   }

    @Override
    public void occupyStockAgain(List<OrderProductsNew> unFrozenOpList) {
        //重新占用库存
        frozenStockModel.execute(unFrozenOpList);
    }

    @Override
    public void occupyStockAgainBysCode(List<OrderProductsNew> unFrozenOpList) {
        frozenStockModel.frozenStockByOrderBysCode(unFrozenOpList);
    }
}
