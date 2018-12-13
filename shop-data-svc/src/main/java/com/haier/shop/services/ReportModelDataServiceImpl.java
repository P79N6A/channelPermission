package com.haier.shop.services;

import com.haier.shop.dao.settleCenter.*;
import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.OrdWfKpiData;
import com.haier.shop.model.OrderRepairs;
import com.haier.shop.model.OrderWorkflowRegion;
import com.haier.shop.service.ReportModelDataService;
import com.haier.shop.util.ArraySplitUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2018/8/28.
 */
@Service
public class ReportModelDataServiceImpl implements ReportModelDataService{
    private static org.apache.log4j.Logger log                = org.apache.log4j.LogManager
            .getLogger(ReportModelDataServiceImpl.class);

    @Autowired
    OrderRepairsDao orderRepairsDao;
    @Autowired
    private ReportWriteDao reportWriteDao;
    @Autowired
    private ReportReadDao reportReadDao;
    @Autowired
    private OrderWorkflowsDao orderWorkflowsDao;


    /**
     * 按照日期删除kpi报表数据
     * @param
     */
    public void deleteKpiDatasByDate(Date date) {
        try {
            reportWriteDao.deleteKpiDatasByDate(date);
        } catch (Exception e) {
            log.error("按照日期删除kpi报表数据出错:", e);
        }
    }

    /**
     * 得到kpi报表数据记录数
     * @return
     */
    public Long getCount() {
        try {
            return reportReadDao.getCount();
        } catch (Exception e) {
            log.error("查询kpi报表数量出错:", e);
        }
        return null;
    }
    public List<Long> getOntimeRateReverseOrsIds(Map<String, Object> paramMap) {
        try {
            return orderWorkflowsDao.getOntimeRateReverseOrsIds(paramMap);
        } catch (Exception e) {
            log.error("查询kpi逆向昨日退换货单id集合出错:", e);
        }
        return null;
    }

    /**
     * 查询逆向报表数据
     * @param paramMap
     * @return
     */
    public List<Map<String, Object>> getOntimeRateReverse(Map<String, Object> paramMap) {
        try {
            return orderWorkflowsDao.getOntimeRateReverse(paramMap);
        } catch (Exception e) {
            log.error("查询逆向报表数据出错:", e);
        }
        return null;
    }

    /**
     * 批量插入kpi报表数据
     * @param kpiDatas
     * @param type
     */
    public void insertKpiDatas(List<OrdWfKpiData> kpiDatas, String type) {
        if (null == kpiDatas || kpiDatas.isEmpty()) {
            return;
        }
        //每次插入数据大小
        int size = 100;
        try {
            if (kpiDatas.size() <= size) {
                reportWriteDao.insertKpiDatas(kpiDatas);
            } else {
                //大于100条分组执行
                List<List<OrdWfKpiData>> splitList = new ArraySplitUtil<OrdWfKpiData>().splistList(
                        kpiDatas, size);
                for (List<OrdWfKpiData> kpiDataList : splitList) {
                    if (!kpiDataList.isEmpty()) {
                        reportWriteDao.insertKpiDatas(kpiDataList);
                    }
                }
            }
        } catch (Exception e) {
            log.error("插入kpi报表,类型:" + type + "数据出错", e);
        }
    }
    /**
     * 查询kpi报表昨日未完成已超期数据
     * @param date
     * @return
     */
    public List<Map<String, Object>> getNotFinishData(Date date) {
        try {
            return reportReadDao.getNotFinishData(date);
        } catch (Exception e) {
            log.error("查询kpi报表数据昨日未完成已超期数据出错:", e);
        }
        return null;
    }
    public List<Map<String, Object>> getOntimeRateByOrderProductIds(Map<String, Object> paramMap) {
        try {
            return orderWorkflowsDao.getOntimeRateByOrderProductIds(paramMap);
        } catch (Exception e) {
            log.error("查询kpi正向昨日差报表数据出错:", e);
        }
        return null;
    }
    public List<Map<String, Object>> getOntimeRateReverseByOrsIds(Map<String, Object> paramMap) {
        try {
            return orderWorkflowsDao.getOntimeRateReverseByOrsIds(paramMap);
        } catch (Exception e) {
            log.error("查询kpi逆向昨日差报表数据出错:", e);
        }
        return null;
    }

}
