package com.haier.afterSale.services;

import com.haier.afterSale.service.OmsReceivedQueueService;
import com.haier.eis.service.OmsReceivedQueueDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class OmsReceivedQueueServiceImpl implements OmsReceivedQueueService {
    @Autowired
    private OmsReceivedQueueDataService omsReceivedQueueDataService;
    public int insert(Map<String,Object> map){
        return omsReceivedQueueDataService.insert(map);
    }
}
