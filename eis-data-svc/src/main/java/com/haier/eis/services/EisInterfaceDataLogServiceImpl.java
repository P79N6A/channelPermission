package com.haier.eis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.eis.dao.eis.EisInterfaceDataLogDao;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.service.EisInterfaceDataLogService;

import java.util.List;
import java.util.Map;

@Service
public class EisInterfaceDataLogServiceImpl implements EisInterfaceDataLogService {
    @Autowired
    EisInterfaceDataLogDao eisInterfaceDataLogDao;

    @Override
//    public Integer insert(EisInterfaceDataLog log) {
//        // TODO Auto-generated method stub
//        return eisInterfaceDataLogDao.insert(log);
//    }
    public ServiceResult<EisInterfaceDataLog> insert(EisInterfaceDataLog log){
        ServiceResult<EisInterfaceDataLog> result = new ServiceResult<EisInterfaceDataLog>();
        if (eisInterfaceDataLogDao.insert(log) > 0) {
          result.setResult(log);
          result.setSuccess(true);
        } else {
          result.setSuccess(false);
        }
        
          return result;
      }
    @Override
    public Integer insertAndReturnId(EisInterfaceDataLog log) {
        // TODO Auto-generated method stub
        return eisInterfaceDataLogDao.insertAndReturnId(log);
    }

    @Override
    public List<EisInterfaceDataLog> getEisInterfaceList(Map<String, Object> params) {
        return eisInterfaceDataLogDao.getEisInterfaceList(params);
    }

    @Override
    public int getEisInterfaceCNT(Map<String, Object> params) {
        return eisInterfaceDataLogDao.getEisInterfaceCNT(params);
    }

    @Override
    public List<EisInterfaceDataLog> getEisInterfaceDataList(Map<String, Object> params) {
        return eisInterfaceDataLogDao.getEisInterfaceDataList(params);
    }


}
