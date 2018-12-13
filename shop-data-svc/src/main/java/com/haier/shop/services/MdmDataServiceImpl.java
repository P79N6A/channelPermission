package com.haier.shop.services;

import com.haier.shop.dao.shopread.MdmDataReadDao;
import com.haier.shop.dao.shopwrite.MdmDataWriteDao;
import com.haier.shop.model.SysMdmVO;
import com.haier.shop.service.MdmDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class MdmDataServiceImpl implements MdmDataService {
    @Autowired
    private MdmDataReadDao mdmDataReadDao;
    @Autowired
    private MdmDataWriteDao mdmDataWriteDao;

    @Override
    public SysMdmVO queryBySku(String sku) {
        return mdmDataReadDao.queryBySku(sku);
    }

    public int insert(List<Map<String,Object>> maps){
        return  mdmDataWriteDao.insert(maps);
    }
    public void delete(){
        mdmDataWriteDao.delete();
    }
}
