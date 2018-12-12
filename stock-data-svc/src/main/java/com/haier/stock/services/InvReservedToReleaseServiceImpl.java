package com.haier.stock.services;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.stock.dao.stock.InvReservedToReleaseDao;
import com.haier.stock.model.InvReservedConfig;
import com.haier.stock.model.InvReservedToRelease;
import com.haier.stock.model.QueryTotalData;
import com.haier.stock.service.InvReservedToReleaseService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvReservedToReleaseServiceImpl implements InvReservedToReleaseService {
    @Autowired
    InvReservedToReleaseDao invReservedToReleaseDao;

    @Override
    public Integer updateReservedConfigById(InvReservedConfig config) {
        // TODO Auto-generated method stub
        return invReservedToReleaseDao.updateReservedConfigById(config);
    }

    @Override
    public Integer insertReservedConfig(InvReservedConfig config) {
        // TODO Auto-generated method stub
        return invReservedToReleaseDao.insertReservedConfig(config);
    }

    @Override
    public List<InvReservedConfig> getReservedConfig(InvReservedConfig config) {
        // TODO Auto-generated method stub
        return invReservedToReleaseDao.getReservedConfig(config);
    }

    @Override
    public Integer insertReserved(Integer batchId, Date date) {
        // TODO Auto-generated method stub
        return invReservedToReleaseDao.insertReserved(batchId, date);
    }

    @Override
    public Integer updateReserved(InvReservedToRelease releases) {
        // TODO Auto-generated method stub
        return invReservedToReleaseDao.updateReserved(releases);
    }
    public List<Map<String,String>> queryTotalPage(Map<String,Object> map){
        return invReservedToReleaseDao.queryTotalPage(map);
    }
    public List<QueryTotalData> queryTotalData(Map<String,Object> map, int start, int rows){
        return invReservedToReleaseDao.queryTotalData(map,start,rows);
    }
}
