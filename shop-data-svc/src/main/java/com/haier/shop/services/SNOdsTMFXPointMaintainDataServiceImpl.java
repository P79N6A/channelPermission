package com.haier.shop.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dao.settleCenter.SNOdsTMFXPointMaintainDao;
import com.haier.shop.dao.settleCenter.SettlementLogDao;
import com.haier.shop.model.SNOdsTMFXPointMaintain;
import com.haier.shop.service.SNOdsTMFXPointMaintainDataService;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.workorder.data.services
 * @Date: Created in 2018/4/23 10:38
 * @Modified By:
 */
@Service("SNOdsTMFXPointMaintainDataServiceImpl")
public class SNOdsTMFXPointMaintainDataServiceImpl implements SNOdsTMFXPointMaintainDataService {

    @Resource
    private SNOdsTMFXPointMaintainDao sNOdsTMFXPointMaintainDao;
    @Resource
    private SettlementLogDao settlementLogDao;

    @Override
    public JSONObject paging(SNOdsTMFXPointMaintain param, PagerInfo page) {
        JSONObject result = new JSONObject();
        List<SNOdsTMFXPointMaintain> odsTMFXPointMaintainList = sNOdsTMFXPointMaintainDao.paging(param,page);
        if(odsTMFXPointMaintainList == null){
            odsTMFXPointMaintainList = new ArrayList<SNOdsTMFXPointMaintain>();
        }
        result.put("rows", odsTMFXPointMaintainList);
        result.put("total", sNOdsTMFXPointMaintainDao.count(param));
        return result;
    }

    @Override
    public JSONObject export(SNOdsTMFXPointMaintain param) {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = sNOdsTMFXPointMaintainDao.export(param);
        if(list == null){
            list = new ArrayList<Map<String,Object>>();
        }
        result.put("rows", list);
        return result;
    }

    @Override
    public int insert(SNOdsTMFXPointMaintain param) {
        return sNOdsTMFXPointMaintainDao.insert(param);
    }

    @Override
    public int update(SNOdsTMFXPointMaintain param) {
        return sNOdsTMFXPointMaintainDao.update(param);
    }

    @Override
    public int delBatch(List<String> ids) {
        return sNOdsTMFXPointMaintainDao.delBatch(ids);
    }

    @Override
    public List<SNOdsTMFXPointMaintain> queryRepetition(SNOdsTMFXPointMaintain odsTMFXPointMaintain){
        return sNOdsTMFXPointMaintainDao.queryRepetition(odsTMFXPointMaintain);
    };

    @Override
    public void bulkImport(List<SNOdsTMFXPointMaintain> list){
        sNOdsTMFXPointMaintainDao.bulkImport(list);
    }

    @Override
    public String getLogInfo(String source) {
        return settlementLogDao.queryLogInfo(source);
    }
}
