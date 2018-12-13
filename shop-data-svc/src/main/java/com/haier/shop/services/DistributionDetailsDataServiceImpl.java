package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dao.settleCenter.DistributionDetailsDao;
import com.haier.shop.model.DistributionDetails;
import com.haier.shop.service.DistributionDetailsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
* Code generated by CodeGen
* Desc: 天猫分销明细表服务实现类
* Date: 2018-05-22
*/
@Service
public class DistributionDetailsDataServiceImpl implements DistributionDetailsDataService {

    @Autowired
    private DistributionDetailsDao distributionDetailsDao;


    @Override
    public List<DistributionDetails> queryDistributionDetails(DistributionDetails model) {
        return distributionDetailsDao.list(model);
    }

    @Override
    public JSONObject paging(DistributionDetails param, PagerInfo page) {
        JSONObject result = new JSONObject();
        List<DistributionDetails> distributionDetailsList = distributionDetailsDao.paging(param,page);
        if(distributionDetailsList == null){
            distributionDetailsList = new ArrayList<DistributionDetails>();
        }
        result.put("rows", distributionDetailsList);
        result.put("total", distributionDetailsDao.count(param));
        return result;
    }
    @Override
    public JSONObject export(DistributionDetails param) {
        JSONObject result = new JSONObject();
        List<Map<String,Object>> list = distributionDetailsDao.export(param);
        if(list == null){
            list = new ArrayList<Map<String,Object>>();
        }
        result.put("rows", list);
        return result;
    }
    @Override
    public List<DistributionDetails> queryDistributionDetailsByNetSn(String netSn) {
        List<DistributionDetails> distributionDetailsList = distributionDetailsDao.loadByNetSn(netSn);
        return distributionDetailsList;
    }

    @Override
    public void create(DistributionDetails model){
        distributionDetailsDao.create(model);
    }

    @Override
    public void creates(List<DistributionDetails> modelList){
        distributionDetailsDao.creates(modelList);
    }


    public void updateDistributionDetails(DistributionDetails model) {
        distributionDetailsDao.update(model);
    }



}
