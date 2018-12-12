package com.haier.logistics.services;

import com.haier.common.ServiceResult;
import com.haier.eis.model.OrderInterfaceDataLog;

import com.haier.logistics.Model.OrderInterfaceDataLogModel;
import com.haier.logistics.service.OrderInterfaceDataLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* 作者:张波
* 2017/12/26
*/
@Service
public class OrderInterfaceDataLogServiceImpl implements OrderInterfaceDataLogService {
    @Autowired
    private OrderInterfaceDataLogModel orderInterfaceDataLogModel;
    private static Logger logger = LoggerFactory
            .getLogger(OrderInterfaceDataLogServiceImpl.class);
    @Override
    public ServiceResult<Boolean> record(OrderInterfaceDataLog log){
        ServiceResult<Boolean> ret = new ServiceResult<Boolean>();
        try {
            orderInterfaceDataLogModel.record(log);
            ret.setSuccess(true);
            ret.setResult(true);
        } catch (Exception e) {
            logger.error("记录访问日志时发生未知异常：", e);
            ret.setSuccess(false);
            ret.setResult(false);
            ret.setMessage("记录访问日志时发生未知异常");
        }
        return ret;
    }
}
