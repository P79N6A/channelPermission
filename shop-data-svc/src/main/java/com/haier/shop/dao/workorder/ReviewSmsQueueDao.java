package com.haier.shop.dao.workorder;

import com.haier.shop.model.ReviewSmsQueue;

import java.util.List;

/**
 * Created by xupeng on 18/4/27.
 *
 */
public interface ReviewSmsQueueDao {

    ReviewSmsQueue selectByPrimaryKey(Long id);

    /**
     * 取得未发送短信的数据
     * @param
     * @return
     */
    List<ReviewSmsQueue> selectSendSms();

    int deleteByPrimaryKey(Long id);

    int insert(ReviewSmsQueue record);

    int insertSelective(ReviewSmsQueue record);

    int updateByPrimaryKeySelective(ReviewSmsQueue record);

    int updateByPrimaryKeyWithBLOBs(ReviewSmsQueue record);

    int updateByPrimaryKey(ReviewSmsQueue record);

    /**
     * 批量插入短信
     * @param record
     * @return
     */
    void addReviewSmsList(List<ReviewSmsQueue> record);

}
