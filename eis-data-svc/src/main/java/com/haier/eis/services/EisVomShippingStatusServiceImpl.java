package com.haier.eis.services;

import com.haier.eis.dao.eis.VomShippingStatusDao;
import com.haier.eis.model.VomShippingStatus;
import com.haier.eis.service.EisVomShippingStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EisVomShippingStatusServiceImpl implements EisVomShippingStatusService {
    @Autowired
    private VomShippingStatusDao vomShippingStatusDao;
    @Override
    public List<VomShippingStatus> getByProcessStatus(Integer processStatus){
        return vomShippingStatusDao.getByProcessStatus(processStatus);
    }
    @Override
    public Integer updateProcessStatus( Integer id, Integer processStatusUpdateTo, Integer processStatusUpdateFrom, String msg){
        return vomShippingStatusDao.updateProcessStatus(id, processStatusUpdateTo, processStatusUpdateFrom, msg);
    }

    /**
     * 获取3W正品
     * @param processStatus
     * @return
     */
    @Override
    public List<VomShippingStatus> getByProcessStatus3W(Integer processStatus){
        return vomShippingStatusDao.getByProcessStatus3W(processStatus);
    }
    @Override
    public Integer insert(VomShippingStatus shippingStatus){
        return vomShippingStatusDao.insert(shippingStatus);
    }
}
