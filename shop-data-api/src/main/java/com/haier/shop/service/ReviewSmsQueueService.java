package com.haier.shop.service;

import com.haier.shop.model.ReviewSmsQueue;

import java.util.List;

/**
 * Created by xupeng on 18/4/27.
 * 工单短信发送队列
 */
public interface ReviewSmsQueueService {

    public ReviewSmsQueue selectByPrimaryKey(Long id);

    /**
     * 取得未发送短信的数据
     * @param
     * @return
     */
    public List<ReviewSmsQueue> selectSendSms();

    public int deleteByPrimaryKey(Long id);

    public int insert(ReviewSmsQueue record);

    public int insertSelective(ReviewSmsQueue record);

    public int updateByPrimaryKeySelective(ReviewSmsQueue record);

    public int updateByPrimaryKeyWithBLOBs(ReviewSmsQueue record);

    public int updateByPrimaryKey(ReviewSmsQueue record);

    /**
     * 批量插入短信
     * @param record
     * @return
     */
    public void addReviewSmsList(List<ReviewSmsQueue> record);

}
