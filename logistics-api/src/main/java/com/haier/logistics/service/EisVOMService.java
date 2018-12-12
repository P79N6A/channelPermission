package com.haier.logistics.service;

import com.haier.common.ServiceResult;
import com.haier.eis.model.VomReceivedQueue;


public interface EisVOMService {
    /**
     * 处理VOM回传的订单状态：出入库处理、
     * @return
     */
    ServiceResult<Boolean> processVomShippingStatus();
    

    /**
     * 预处理接收的信息内容
     * @return
     */
    ServiceResult<Boolean> reprocessingContent();
    
    /**
     * 处理VOM出入库记录:生成库存交易
     * @return
     */
    ServiceResult<Boolean> processOutInStoreForGenerateStockTransaction();
    
    public void test();
    /**
     * 预处理VOM信息失败后再处理
     * @return
     */
    ServiceResult<Boolean> processVomReceivedQueueFailStatus();
}
