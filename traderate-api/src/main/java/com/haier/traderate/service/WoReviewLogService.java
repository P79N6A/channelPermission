package com.haier.traderate.service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.dto.LogBean;
import java.util.List;

/**
 * Created by jtbshan on 2018/5/2.
 */
public interface WoReviewLogService {
    /**
     * 分页查询
     * @param record
     * @param pager
     * @return
     */
     ServiceResult<List<LogBean>> page(LogBean record, PagerInfo pager, String startTime,String endTime) ;

    /**
     * 插入日志操作
     * @param record
     * @return
     */
     ServiceResult<Boolean> insertLog(LogBean record);
}
