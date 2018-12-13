package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dao.settleCenter.OdsGateGrossprofitDao;
import com.haier.shop.dto.OdsGateGrossprofitDto;
import com.haier.shop.model.OdsGateGrossprofit;
import com.haier.shop.service.OdsGateGrossprofitDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/26.
 */
@Service
public class OdsGateGrossprofitDataServiceImpl implements OdsGateGrossprofitDataService {

    @Autowired
    private OdsGateGrossprofitDao odsGateGrossprofitDao;

    @Override
    public JSONObject paging(OdsGateGrossprofitDto param, PagerInfo page) {
        JSONObject result = new JSONObject();
        List<OdsGateGrossprofitDto> odsGateGrossprofit = odsGateGrossprofitDao.paging(param,page);
        if(odsGateGrossprofit == null){
            odsGateGrossprofit = new ArrayList<OdsGateGrossprofitDto>();
        }
        result.put("rows", odsGateGrossprofit);
        result.put("total", odsGateGrossprofitDao.count(param));
        return result;
    }

    @Override
    public int insert(OdsGateGrossprofit param) {
        return odsGateGrossprofitDao.insert(param);
    }

    @Override
    public int update(OdsGateGrossprofit param) {
        return odsGateGrossprofitDao.update(param);
    }

    @Override
    public int delBatch(List<String> ids) {
        return odsGateGrossprofitDao.delBatch(ids);
    }

    @Override
    public List<OdsGateGrossprofit> queryRepetition(OdsGateGrossprofit odsGateGrossprofit) {
        return odsGateGrossprofitDao.queryRepetition(odsGateGrossprofit);
    }

    @Override
    public void bulkImport(List<OdsGateGrossprofit> list) {
         odsGateGrossprofitDao.bulkImport(list);
    }
}
