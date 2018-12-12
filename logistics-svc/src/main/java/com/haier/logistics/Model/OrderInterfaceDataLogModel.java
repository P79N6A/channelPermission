package com.haier.logistics.Model;


import com.haier.eis.model.OrderInterfaceDataLog;
import com.haier.eis.service.OrderInterfaceDataLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* 作者:张波
* 2017/12/26
*/
@Service
public class OrderInterfaceDataLogModel {
    @Autowired
    private OrderInterfaceDataLogService orderInterfaceDataLogDao;
    /**
     * 记录访问日志
     * @param log
     */
    public void record(OrderInterfaceDataLog log) {
        orderInterfaceDataLogDao.insert(log);
    }
}
