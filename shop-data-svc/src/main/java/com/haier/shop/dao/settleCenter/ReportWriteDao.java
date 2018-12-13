package com.haier.shop.dao.settleCenter;

import java.util.Date;
import java.util.List;

import com.haier.shop.model.OrdWfKpiData;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportWriteDao {
    /**
     * 插入kpi报表数据
     * @param kpiDatas
     * @return
     */
    Integer insertKpiDatas(List<OrdWfKpiData> kpiDatas);

    /**
     * 按照日期删除kpi报表数据
     * @param date
     */
    void deleteKpiDatasByDate(Date date);
}