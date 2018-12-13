package com.haier.shop.services;

import com.haier.shop.dao.shopread.NetPointsReadDao;
import com.haier.shop.dao.shopwrite.NetPointsWriteDao;
import com.haier.shop.model.NetPoints;
import com.haier.shop.service.NetPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NetPointsServiceImpl implements NetPointsService {
    @Autowired
    private NetPointsReadDao netPointsReadDao;
    @Autowired
    private NetPointsWriteDao netPointsWriteDao;

    public int deleteByPrimaryKey(Integer id){
        return netPointsWriteDao.deleteByPrimaryKey(id);
    }

    public int insert(NetPoints record){
        return netPointsWriteDao.insert(record);
    }

    public int insertSelective(NetPoints record){
        return netPointsWriteDao.insertSelective(record);
    }

    public  NetPoints selectByPrimaryKey(Integer id){
        return netPointsReadDao.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(NetPoints record){
        return netPointsWriteDao.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(NetPoints record){
        return netPointsWriteDao.updateByPrimaryKey(record);
    }

    public  NetPoints getByNetPointN8(String netPointN8){
        return netPointsReadDao.getByNetPointN8(netPointN8);
    }

    public NetPoints get(Integer id){
        return netPointsReadDao.get(id);
    }

    public NetPoints getByNetPointByCode(String netPointCode){
        return netPointsReadDao.getByNetPointByCode(netPointCode);
    }

    @Override
    public Map<String, Object> getNetPointsList(Map<String, Object> params) {

        List<NetPoints> netPointListList =netPointsReadDao.queryNetPointList(params);
        //查询总条数
        int resultCount = netPointsReadDao.getRowCnts();

        Map<String, Object> retMap = new HashMap<>();

        retMap.put("total", resultCount);
        retMap.put("rows", netPointListList);
        return retMap;
    }
}
