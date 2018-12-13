package com.haier.shop.service;

import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.OrdWfKpiData;
import com.haier.shop.model.OrderWorkflowRegion;

import java.util.*;

/**
 * Created by Administrator on 2018/8/28.
 */
public interface ReportModelDataService {
    public void deleteKpiDatasByDate(Date date);
    public Long getCount();
    public List<Long> getOntimeRateReverseOrsIds(Map<String, Object> paramMap);
    public List<Map<String, Object>> getOntimeRateReverse(Map<String, Object> paramMap);

    public void insertKpiDatas(List<OrdWfKpiData> kpiDatas, String type);
    public List<Map<String, Object>> getNotFinishData(Date date);
    public List<Map<String, Object>> getOntimeRateByOrderProductIds(Map<String, Object> paramMap);
    public List<Map<String, Object>> getOntimeRateReverseByOrsIds(Map<String, Object> paramMap);



}
