package com.haier.shop.services;

import com.haier.shop.dao.shopread.WwwHpRecordReadDao;
import com.haier.shop.service.WwwHpRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class WwwHpRecordServiceImpl implements WwwHpRecordService {
    @Autowired
    private WwwHpRecordReadDao wwwHpRecordReadDao;
    public List<Map<String,Object>> selectBysuccess(){
        return wwwHpRecordReadDao.selectBysuccess();
    }
}
