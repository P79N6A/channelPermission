package com.haier.shop.services;

import java.util.List;

import com.haier.shop.dao.workorder.WoReviewLogDao;
import com.haier.shop.dto.LogBean;
import com.haier.shop.service.WoReviewLogDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;

@Service
public class WoReviewLogDataServiceImpl implements WoReviewLogDataService {
    @Autowired
    private WoReviewLogDao woReviewLogDao;


    /**
     * 查询数据
     * @param record
     * @param pager
     * @param endTime 
     * @param startTime 
     * @return
     */
    public List<LogBean> findPageList(LogBean record, PagerInfo pager, String startTime,
                                      String endTime) {
        return woReviewLogDao.findPageList(record, pager, startTime, endTime);
    }

    /**
     * 查询总条数
     * @param record
     * @return
     */
    public int findPageCount(LogBean record, String startTime, String endTime) {
        return woReviewLogDao.findPageCount(record, startTime, endTime);
    }

    /**
     * 插入日志表数据
     * @param record
     * @return
     */
    public int insertSelective(LogBean record) {
        return woReviewLogDao.insertSelective(record);
    }
}