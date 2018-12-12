package com.haier.eis.service;

import java.util.List;
import java.util.Map;

import com.haier.eis.model.EisInterfaceQueueData;




public interface EisInterfaceQueueDataService {

    public Integer insert(EisInterfaceQueueData queueData);

    public Integer update(EisInterfaceQueueData queueData);

    public List<EisInterfaceQueueData> queryEisInterfaceQueueData(Map<String, Object> params);

}
