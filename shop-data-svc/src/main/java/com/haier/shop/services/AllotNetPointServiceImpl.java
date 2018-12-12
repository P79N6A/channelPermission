package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.AllotNetPointReadDao;
import com.haier.shop.dao.shopwrite.AllotNetPointWriteDao;
import com.haier.shop.model.AllotNetPoint;
import com.haier.shop.service.AllotNetPointService;
@Service
public class AllotNetPointServiceImpl implements AllotNetPointService {
    @Autowired
    private AllotNetPointReadDao allotNetPointReadDao;
    @Autowired
    private AllotNetPointWriteDao allotNetPointWriteDao;


    public List<AllotNetPoint> getByStatus(Integer status, Integer rowNum){
        return allotNetPointReadDao.getByStatus(status,rowNum);
    }

    public int insert(AllotNetPoint allotNetPoint){
        return allotNetPointWriteDao.insert(allotNetPoint);
    }

    public int updateById(Integer id, int status, String message){
        return allotNetPointWriteDao.updateById(id, status, message);
    }
    public List<Map<String, Object>> getNetPoints(){
        return allotNetPointReadDao.getNetPoints();
    }
    public int batchInsert(Map<String, Object> params){
        return allotNetPointWriteDao.batchInsert(params);
    }
}
