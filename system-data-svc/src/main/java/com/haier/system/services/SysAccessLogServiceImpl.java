package com.haier.system.services;

import com.haier.system.dao.SysAccessLogDao;
import com.haier.system.model.SysAccessLog;
import com.haier.system.service.SysAccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by 李超 on 2018/1/12.
 */
@Service
public class SysAccessLogServiceImpl implements SysAccessLogService {

    @Autowired
    SysAccessLogDao sysAccessLogDao;

    @Override
    public int create(SysAccessLog accessLog) {
        return sysAccessLogDao.create(accessLog);
    }

    @Override
    public List<SysAccessLog> find(Date startTime, Date endTime, String clientIp, String visitUrl, String userName, String sessionId, Integer start, Integer size) {
        return sysAccessLogDao.find(startTime, endTime, clientIp, visitUrl, userName, sessionId, start, size);
    }

    @Override
    public Integer findCount(Date startTime, Date endTime, String clientIp, String visitUrl, String userName, String sessionId) {
        return sysAccessLogDao.findCount(startTime, endTime, clientIp, visitUrl, userName, sessionId);
    }
}
