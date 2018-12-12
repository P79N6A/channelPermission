package com.haier.eis.services;

import com.haier.eis.dao.eis.VomInOutStoreItemDao;
import com.haier.eis.model.VomInOutStoreItem;
import com.haier.eis.service.EisVomInOutStoreItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EisVomInOutStoreItemServiceImpl implements EisVomInOutStoreItemService {
    @Autowired
    private VomInOutStoreItemDao vomInOutStoreItemDao;
    @Override
    public Integer insert(VomInOutStoreItem vomInOutStoreItem){
        return vomInOutStoreItemDao.insert(vomInOutStoreItem);
    }
    @Override
    public List<VomInOutStoreItem> getNotProcessByOrderId(Integer orderId){
        return vomInOutStoreItemDao.getNotProcessByOrderId(orderId);
    }
    @Override
    public Integer updateProcessStatus(Integer id, Integer processStatusUpdateTo, Integer processStatusUpdateFrom){
        return vomInOutStoreItemDao.updateProcessStatus(id, processStatusUpdateTo, processStatusUpdateFrom);
    }

}
