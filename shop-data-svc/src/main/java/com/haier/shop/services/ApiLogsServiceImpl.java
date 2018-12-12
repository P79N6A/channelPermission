package com.haier.shop.services;

import com.haier.shop.dao.shopwrite.ApiLogsWriteDao;
import com.haier.shop.model.ApiLogs;
import com.haier.shop.service.ApiLogsService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiLogsServiceImpl implements ApiLogsService{
    @Autowired
    private ApiLogsWriteDao apiLogsWriteDao;
    public int insert(ApiLogs apiLogs){
        System.out.println("插入前主键值为 :"+apiLogs.getId());
        int x=apiLogsWriteDao.insert(apiLogs);

        System.out.println("插入后主键值为 :"+apiLogs.getId());
        return apiLogs.getId();
    }
    public int updateReturnDataById(@Param("id") Integer id, @Param("returnData") String returnData){
        return apiLogsWriteDao.updateReturnDataById(id,returnData);
    }
}
