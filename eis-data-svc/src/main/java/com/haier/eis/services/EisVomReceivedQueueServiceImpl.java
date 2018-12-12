package com.haier.eis.services;

import com.haier.eis.dao.eis.VomReceivedQueueDao;
import com.haier.eis.model.VomReceivedQueue;
import com.haier.eis.service.EisVomReceivedQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EisVomReceivedQueueServiceImpl implements EisVomReceivedQueueService {
    @Autowired
    private VomReceivedQueueDao vomReceivedQueueDao;
    @Override
    public Integer insert(VomReceivedQueue receivedQueue){
        return vomReceivedQueueDao.insert(receivedQueue);
    }
    @Override
    public List<VomReceivedQueue> getByStatus(Integer status){
        return vomReceivedQueueDao.getByStatus(status);
    }
    @Override
    public Integer outCode( String outCode){
        return vomReceivedQueueDao.outCode(outCode);
    }
    @Override
    public Integer updateDone( Integer id){
        return vomReceivedQueueDao.updateDone(id);
    }
    @Override
    public Integer updateError(Integer id, String msg){
        return vomReceivedQueueDao.updateError(id,msg);
    }
    @Override
    public List<VomReceivedQueue> getByIdStatus(Integer id, Integer status){
        return vomReceivedQueueDao.getByIdStatus(id,status);
    }
    @Override
    public Integer updateStatusById(Integer id,  Integer status){
        return vomReceivedQueueDao.updateStatusById(id,status);
    }
}
