package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dto.OdsGateGrossprofitDto;
import com.haier.shop.model.OdsGateGrossprofit;

import java.util.List;

/**
 * Created by Administrator on 2018/6/26.
 */
public interface OdsGateGrossprofitDataService {

    public JSONObject paging(OdsGateGrossprofitDto param, PagerInfo page) ;

    public int insert(OdsGateGrossprofit param);

    public int update(OdsGateGrossprofit param);

    public int delBatch(List<String> ids);

    public List<OdsGateGrossprofit> queryRepetition(OdsGateGrossprofit odsGateGrossprofit);

    public void bulkImport(List<OdsGateGrossprofit> list);
}
