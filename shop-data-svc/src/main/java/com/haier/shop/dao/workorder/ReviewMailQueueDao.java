package com.haier.shop.dao.workorder;

import com.haier.shop.model.ReviewMailQueue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by xupeng on 18/4/27.
 */
@Mapper
public interface ReviewMailQueueDao {

    ReviewMailQueue selectByPrimaryKey(Long id);

    List<ReviewMailQueue> selectSendMail();

    int deleteByPrimaryKey(Long id);

    int insert(ReviewMailQueue record);

    int insertSelective(ReviewMailQueue record);

    int updateByPrimaryKeySelective(ReviewMailQueue record);

    int updateByPrimaryKeyWithBLOBs(ReviewMailQueue record);

    int updateByPrimaryKey(ReviewMailQueue record);

    /**
     * 批量插入邮件
     * @param record
     * @return
     */
    void addReviewEmailList(List<ReviewMailQueue> record);

}
