package com.haier.shop.dao.settleCenter;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Mapper
public interface ReportReadDao {
    /**
     * 得到kpi报表数据记录数
     * @return
     */
    Long getCount();

    /**
     * 根据日期查询未完成已超期的网单id和节点类型
     * @param date
     * @return
     */
    List<Map<String, Object>> getNotFinishData(Date date);
}