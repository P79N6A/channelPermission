package com.haier.shop.service;

import com.haier.shop.model.ReviewMailQueue;

import java.util.List;

/**
 * Created by xupeng on 18/4/27.
 */
public interface ReviewMailQueueService {

    public ReviewMailQueue selectByPrimaryKey(Long id);

    public List<ReviewMailQueue> selectSendMail();

    public int deleteByPrimaryKey(Long id);

    public int insert(ReviewMailQueue record);

    public int insertSelective(ReviewMailQueue record);

    public int updateByPrimaryKeySelective(ReviewMailQueue record);


    public int updateByPrimaryKeyWithBLOBs(ReviewMailQueue record);

    public int updateByPrimaryKey(ReviewMailQueue record);

    /**
     * 批量插入邮件
     * @param record
     * @return
     */
    public void addReviewEmailList(List<ReviewMailQueue> record);

}
