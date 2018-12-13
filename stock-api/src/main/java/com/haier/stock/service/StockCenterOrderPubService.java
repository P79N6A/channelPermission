package com.haier.stock.service;

import com.haier.common.ServiceResult;
import com.haier.shop.model.OrderProductsNew;
import java.util.List;

/**
 * Created by 胡万来 on 2017/12/25 0025.
 */
public interface StockCenterOrderPubService {
    /**
     * 确认订单,供定时任务调用
     * @return
     */
    ServiceResult<Boolean> autoConfirmOrder();
    
    /**
     * 系统后台正常自动锁定库存
     * @return
     */
    ServiceResult<Boolean> autoFrozenStockByOrder();

    /**
     * 重新占用库存
     * @param unFrozenOpList
     */
    void occupyStockAgain(List<OrderProductsNew> unFrozenOpList);

    /**
     * 修改网单库位后重新占用库存
     */
    void occupyStockAgainBysCode(List<OrderProductsNew> unFrozenOpList);


}
