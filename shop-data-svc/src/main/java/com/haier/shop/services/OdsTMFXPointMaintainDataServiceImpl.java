package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dao.settleCenter.OdsTMFXPointMaintainDao;
import com.haier.shop.dao.settleCenter.SettlementLogDao;
import com.haier.shop.model.OdsTMFXPointMaintain;
import com.haier.shop.service.OdsTMFXPointMaintainDataService;
import com.haier.shop.service.WOUserDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.workorder.data.services
 * @Date: Created in 2018/4/23 10:38
 * @Modified By:
 */
@Service
public class OdsTMFXPointMaintainDataServiceImpl implements OdsTMFXPointMaintainDataService {

    @Resource
    private OdsTMFXPointMaintainDao odsTMFXPointMaintainDao;
    @Resource
    private SettlementLogDao settlementLogDao;

    @Override
    public JSONObject paging(OdsTMFXPointMaintain param, PagerInfo page) {
        JSONObject result = new JSONObject();
        List<OdsTMFXPointMaintain> odsTMFXPointMaintainList = odsTMFXPointMaintainDao.paging(param,page);
        if(odsTMFXPointMaintainList == null){
            odsTMFXPointMaintainList = new ArrayList<OdsTMFXPointMaintain>();
        }
        result.put("rows", odsTMFXPointMaintainList);
        result.put("total", odsTMFXPointMaintainDao.count(param));
        return result;
    }

    @Override
    public JSONObject export(OdsTMFXPointMaintain param) {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = odsTMFXPointMaintainDao.export(param);
        if(list == null){
            list = new ArrayList<Map<String,Object>>();
        }
        result.put("rows", list);
        return result;
    }

    @Override
    public int insert(OdsTMFXPointMaintain param) {
        return odsTMFXPointMaintainDao.insert(param);
    }

    @Override
    public int update(OdsTMFXPointMaintain param) {
        return odsTMFXPointMaintainDao.update(param);
    }

    @Override
    public int delBatch(List<String> ids) {
        return odsTMFXPointMaintainDao.delBatch(ids);
    }

    @Override
    public List<OdsTMFXPointMaintain> queryRepetition(OdsTMFXPointMaintain odsTMFXPointMaintain){
        return odsTMFXPointMaintainDao.queryRepetition(odsTMFXPointMaintain);
    };

    @Override
    public void bulkImport(List<OdsTMFXPointMaintain> list){
        odsTMFXPointMaintainDao.bulkImport(list);
    }

    @Override
    public String getLogInfo(String source) {
        return settlementLogDao.queryLogInfo(source);
    }
}
