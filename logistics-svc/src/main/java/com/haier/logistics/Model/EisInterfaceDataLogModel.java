package com.haier.logistics.Model;

import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.service.EisInterfaceDataLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* 作者:张波
* 2017/12/25
*/
@Service
public class EisInterfaceDataLogModel {
    private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager
            .getLogger(EisInterfaceDataLogModel.class);
    @Autowired
    private EisInterfaceDataLogService interfaceDataLogDao;

    /**
     * 记录访问日志
     * @param log
     */
    public void record(EisInterfaceDataLog log) {
        interfaceDataLogDao.insert(log);
    }
}
