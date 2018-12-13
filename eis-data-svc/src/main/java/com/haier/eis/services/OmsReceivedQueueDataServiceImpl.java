package com.haier.eis.services;

import com.haier.eis.dao.eis.OmsReceivedQueueDataDao;
import com.haier.eis.service.OmsReceivedQueueDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class OmsReceivedQueueDataServiceImpl implements OmsReceivedQueueDataService {
    @Autowired
    private OmsReceivedQueueDataDao omsReceivedQueueDataDao;
    public int insert(Map<String,Object> map){
        return omsReceivedQueueDataDao.insert(map);
    }
    public List<Map<String,Object>> select(){
        return omsReceivedQueueDataDao.select();
    }
    public void update(List<Map<String,Object>> map){
        omsReceivedQueueDataDao.update(map);
    }
}
