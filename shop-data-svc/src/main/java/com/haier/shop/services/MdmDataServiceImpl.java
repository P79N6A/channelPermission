package com.haier.shop.services;

import com.haier.shop.dao.shopwrite.MdmDataWriteDao;
import com.haier.shop.service.MdmDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class MdmDataServiceImpl implements MdmDataService {
    @Autowired
    private MdmDataWriteDao mdmDataWriteDao;
    public int insert(List<Map<String,Object>> maps){
        System.out.println("11111");
        return  mdmDataWriteDao.insert(maps);
    }
    public void delete(){
        System.out.println("222222");
        mdmDataWriteDao.delete();
    }
}
