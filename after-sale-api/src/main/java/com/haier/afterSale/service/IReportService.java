package com.haier.afterSale.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;

/**
 * 报表查询操作公共接口:包括及时率,kpi抓取和查询报表等
 * TODO:及时率报表查询后期可以考虑再移植查询逻辑,controller只关注参数和展示数据格式化:日期格式化和各种名称显示
 * 
 * @Filename: IReportService.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public interface IReportService {

    /**
     * 查询KPI报表数据
     * @param params
     * @return
     */
    public Map<String, List<Map<String, Object>>> queryKpiReportDatas(Map<String, String> params);

    /**
     * 每日凌晨抓取KPI报表明细数据
     * @return
     */
    public ServiceResult<Boolean> catchKpiReportDetail();

}
