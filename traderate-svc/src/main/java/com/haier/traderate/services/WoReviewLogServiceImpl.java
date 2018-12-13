package com.haier.traderate.services;

import java.util.List;

import javax.annotation.Resource;

import com.haier.shop.dto.LogBean;
import com.haier.shop.service.WoReviewLogDataService;
import com.haier.shop.util.ReviewConstants;
import com.haier.traderate.service.WoReviewLogService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import org.springframework.stereotype.Service;

/**
 * 日志
 *                       
 * @Filename: ReviewLogServiceImpl.java
 * @Version: 1.0
 * @Author: tianshuai.zhang 张天帅
 * @Email: tianshuai.zhang@dhc.com.cn
 *
 */
@Service
public class WoReviewLogServiceImpl implements WoReviewLogService {
    private static Logger  log = LogManager.getLogger(WoReviewLogServiceImpl.class);
    @Resource
    private WoReviewLogDataService woReviewLogDataService;

    /**
     * 分页查询
     * @param record
     * @param pager
     * @return
     */
    @Override
    public ServiceResult<List<LogBean>> page(LogBean record, PagerInfo pager, String startTime,
                                             String endTime) {
        ServiceResult<List<LogBean>> serviceResult = new ServiceResult<List<LogBean>>();
        try {
            List<LogBean> data = woReviewLogDataService.findPageList(record, pager, startTime, endTime);
            int num = woReviewLogDataService.findPageCount(record, startTime, endTime);
            PagerInfo pagerInfo = new PagerInfo();
            pagerInfo.setRowsCount(num);
            serviceResult.setResult(data);
            serviceResult.setPager(pagerInfo);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ReviewLogServiceImpl][page]取分页查询异常:", e);
        }
        return serviceResult;
    }

    /**
     * 插入日志操作
     * @param record
     * @return
     */
    @Override
    public ServiceResult<Boolean> insertLog(LogBean record) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        serviceResult.setResult(false);
        try {
            woReviewLogDataService.insertSelective(record);
            serviceResult.setResult(true);
        } catch (Exception e) {
            log.error("[ReviewLogServiceImpl][insertLog]插入日志信息异常:", e);
        }
        return serviceResult;
    }
}
