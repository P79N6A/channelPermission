package com.haier.eis.services;

import com.haier.eis.dao.eis.OrdShippingStatusQueueDao;
import com.haier.eis.model.OrdShippingStatusQueue;
import com.haier.eis.service.OrdShippingStatusQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdShippingStatusQueueServiceImpl implements OrdShippingStatusQueueService {
    @Autowired
    private OrdShippingStatusQueueDao ordShippingStatusQueueDao;
    /**
     * 根据网单号获取队列信息
     * @param orderProductId
     * @return
     */
    public OrdShippingStatusQueue getByOrderProductId(Integer orderProductId){
        return ordShippingStatusQueueDao.getByOrderProductId(orderProductId);
    }

    /**
     * 新增队列（已处理并发）
     * @param queue
     * @return
     */
    public Integer insert(OrdShippingStatusQueue queue){
        return ordShippingStatusQueueDao.insert(queue);
    }
}
