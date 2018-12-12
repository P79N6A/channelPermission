package com.haier.system.services;

import com.haier.system.dao.SysActionDao;
import com.haier.system.model.SysAction;
import com.haier.system.service.SysActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 李超 on 2018/1/12.
 */
@Service
public class SysActionServiceImpl implements SysActionService {

    @Autowired
    SysActionDao sysActionDao;

    @Override
    public List<SysAction> find(String actKey, String actName, String remark, Integer status, String menuName, Integer start, Integer size) {
        return sysActionDao.find(actKey, actName, remark, status, menuName, start, size);
    }

    @Override
    public Integer findCount(String actKey, String actName, String remark, Integer status, String menuName, Integer start, Integer size) {
        return sysActionDao.findCount(actKey, actName, remark, status, menuName, start, size);
    }

    @Override
    public List<SysAction> findByFn(String fnId) {
        return sysActionDao.findByFn(fnId);
    }

    @Override
    public SysAction findByUrl(String url) {
        return sysActionDao.findByUrl(url);
    }

    @Override
    public SysAction findByActKey(String actKey) {
        return sysActionDao.findByActKey(actKey);
    }

    @Override
    public SysAction get(int actId) {
        return sysActionDao.get(actId);
    }

    @Override
    public int update(SysAction action) {
        return sysActionDao.update(action);
    }

    @Override
    public void create(SysAction action) {
        sysActionDao.create(action);
    }
}
