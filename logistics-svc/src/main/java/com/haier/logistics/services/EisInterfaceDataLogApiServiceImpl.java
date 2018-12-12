package com.haier.logistics.services;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.logistics.Model.EisInterfaceDataLogModel;
import com.haier.logistics.Util.PropertiesUtil;
import com.haier.logistics.service.EisInterfaceDataLogApiService;
import com.haier.shop.model.OrderProductsNew;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EisInterfaceDataLogApiServiceImpl implements EisInterfaceDataLogApiService {
    private static Logger logger = LoggerFactory
            .getLogger(EisInterfaceDataLogApiServiceImpl.class);
    @Autowired
    private EisInterfaceDataLogApiService eisInterfaceDataLogService;
    @Autowired
    private PropertiesUtil   propertiesUtil;
    @Autowired
    private EisInterfaceDataLogModel eisInterfaceDataLogModel;
    public ServiceResult<Boolean> record(EisInterfaceDataLog log){
        ServiceResult<Boolean> ret = new ServiceResult<Boolean>();
        try {
            eisInterfaceDataLogModel.record(log);
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
    public OrderProductsNew getOrderProductsByCOrderSn(String cOrderSn){
        return eisInterfaceDataLogService.getOrderProductsByCOrderSn(cOrderSn);
    }
    public boolean saveHpReservationDateRelation(OrderProductsNew orderProducts, String remark,
                                                 String changeLog){
        return eisInterfaceDataLogService.saveHpReservationDateRelation(orderProducts,remark,changeLog);
    }
    public int sendSms(OrderProductsNew orderProducts){
        return eisInterfaceDataLogService.sendSms(orderProducts);
    }
    public String getProperties(String name){
    	return propertiesUtil.getProperties(name);
    }

}
