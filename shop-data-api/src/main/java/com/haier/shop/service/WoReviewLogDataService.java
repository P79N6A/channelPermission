package com.haier.shop.service;

import com.haier.common.PagerInfo;
import com.haier.shop.dto.LogBean;

import java.util.List;

/**
 * Created by jtbshan on 2018/5/2.
 */
public interface WoReviewLogDataService {
    /**
     * 查询数据
     * @param record
     * @param pager
     * @param endTime
     * @param startTime
     * @return
     */
    public List<LogBean> findPageList(LogBean record, PagerInfo pager, String startTime,String endTime) ;

    /**
     * 查询总条数
     * @param record
     * @return
     */
    public int findPageCount(LogBean record, String startTime, String endTime) ;

    /**
     * 插入日志表数据
     * @param record
     * @return
     */
    public int insertSelective(LogBean record);
}
