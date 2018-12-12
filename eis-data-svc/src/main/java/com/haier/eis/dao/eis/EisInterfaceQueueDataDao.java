package com.haier.eis.dao.eis;

import java.util.List;
import java.util.Map;

import com.haier.eis.model.EisInterfaceQueueData;



public interface EisInterfaceQueueDataDao {

    public Integer insert(EisInterfaceQueueData queueData);

    public Integer update(EisInterfaceQueueData queueData);

    public List<EisInterfaceQueueData> queryEisInterfaceQueueData(Map<String, Object> params);

}
