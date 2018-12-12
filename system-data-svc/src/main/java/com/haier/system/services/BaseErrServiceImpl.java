package com.haier.system.services;

import com.haier.system.dao.BaseErrDao;
import com.haier.system.model.BaseErr;
import com.haier.system.service.BaseErrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 李超 on 2018/1/12.
 */
@Service
public class BaseErrServiceImpl implements BaseErrService {

    @Autowired
    BaseErrDao baseErrDao;

    @Override
    public Integer adderr(BaseErr base) {
        return baseErrDao.adderr(base);
    }

    @Override
    public List<BaseErr> queryErr(Map<String, Object> map) {
        return baseErrDao.queryErr(map);
    }

    @Override
    public int getRowCnts(Map<String, Object> map) {
        return baseErrDao.getRowCnts(map);
    }
}
