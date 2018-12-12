package com.haier.system.services;

import com.haier.system.dao.SysSessionDao;
import com.haier.system.model.SysSession;
import com.haier.system.service.SysSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 李超 on 2018/1/12.
 */
@Service
public class SysSessionServiceImpl implements SysSessionService {

    @Autowired
    SysSessionDao sysSessionDao;

    @Override
    public int create(SysSession session) {
        return sysSessionDao.create(session);
    }

    @Override
    public int update(SysSession session) {
        return sysSessionDao.update(session);
    }

    @Override
    public SysSession get(String sessionId) {
        return sysSessionDao.get(sessionId);
    }

    @Override
    public int delete(String sessionId) {
        return sysSessionDao.delete(sessionId);
    }
}
