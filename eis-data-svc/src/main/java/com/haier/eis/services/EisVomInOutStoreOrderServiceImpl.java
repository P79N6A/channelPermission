package com.haier.eis.services;

import com.haier.common.ServiceResult;
import com.haier.eis.dao.eis.VomInOutStoreOrderDao;
import com.haier.eis.model.VomInOutStoreOrder;
import com.haier.eis.service.EisVomInOutStoreOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EisVomInOutStoreOrderServiceImpl implements EisVomInOutStoreOrderService {
    @Autowired
    private VomInOutStoreOrderDao vomInOutStoreOrderDao;
    @Override
    public ServiceResult<VomInOutStoreOrder> insert(VomInOutStoreOrder vomInOutStoreOrder){
        ServiceResult<VomInOutStoreOrder> result = new ServiceResult<VomInOutStoreOrder>();
        if (vomInOutStoreOrderDao.insert(vomInOutStoreOrder) > 0) {
          result.setResult(vomInOutStoreOrder);
          result.setSuccess(true);
        } else {
          result.setSuccess(false);
        }
        
          return result;
      }
    @Override
    public List<VomInOutStoreOrder> getByProcessStatus(Integer processStatus, Integer delay){
        return vomInOutStoreOrderDao.getByProcessStatus(processStatus,delay);
    }
    @Override
    public VomInOutStoreOrder getByRefNo(String refNo){
        return vomInOutStoreOrderDao.getByRefNo(refNo);
    }
    @Override
    public Integer updateProcessStatus(Integer id, Integer processStatusUpdateTo, Integer processStatusUpdateFrom){
        return vomInOutStoreOrderDao.updateProcessStatus(id, processStatusUpdateTo, processStatusUpdateFrom);
    }
    @Override
    public Integer setDelay(Integer id,String msg){
        return vomInOutStoreOrderDao.setDelay(id, msg);
    }

    /**
     * 获取3W正品退仓
     * @param processStatus
     * @param delay
     * @return
     */
    @Override
    public List<VomInOutStoreOrder> getByProcessStatus3W(Integer processStatus, Integer delay){
        return vomInOutStoreOrderDao.getByProcessStatus3W(processStatus, delay);
    }
    @Override
    public List<VomInOutStoreOrder> findInTime() {
    	// TODO Auto-generated method stub
    	return vomInOutStoreOrderDao.findInTime();
    }
	@Override
	public VomInOutStoreOrder queryVomInOut(String orderNo) {
		// TODO Auto-generated method stub
		return vomInOutStoreOrderDao.queryVomInOut(orderNo);
	}
	@Override
	public VomInOutStoreOrder queryGetStoreCode(String storageType, String busType, String orderNo) {
		// TODO Auto-generated method stub
		return vomInOutStoreOrderDao.queryGetStoreCode(storageType, busType, orderNo);
	}

    @Override
    public VomInOutStoreOrder getByStockInfoByOrderNo(String orderNo) {
        return vomInOutStoreOrderDao.getByStockInfoByOrderNo(orderNo);
    }

	@Override
	public VomInOutStoreOrder queryVomInTenlibrary(String orderNo) {
		// TODO Auto-generated method stub
		return vomInOutStoreOrderDao.queryVomInTenlibrary(orderNo);
	}
}
