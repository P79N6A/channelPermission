package com.haier.shop.services;

import com.haier.shop.dao.workorder.ReviewSmsQueueDao;
import com.haier.shop.model.ReviewSmsQueue;
import com.haier.shop.service.ReviewSmsQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xupeng on 18/4/27.
 */
@Service
public class ReviewSmsQueueServiceImpl implements ReviewSmsQueueService {

    @Autowired
    ReviewSmsQueueDao reviewSmsQueueDao;

    public ReviewSmsQueue selectByPrimaryKey(Long id){
        return reviewSmsQueueDao.selectByPrimaryKey(id);
    }

    /**
     * 取得未发送短信的数据
     * @param
     * @return
     */
    public List<ReviewSmsQueue> selectSendSms(){
        return reviewSmsQueueDao.selectSendSms();
    }

    public int deleteByPrimaryKey(Long id){
        return reviewSmsQueueDao.deleteByPrimaryKey(id);
    }

    public int insert(ReviewSmsQueue record){
        return reviewSmsQueueDao.insert(record);
    }

    public int insertSelective(ReviewSmsQueue record){
        return reviewSmsQueueDao.insertSelective(record);
    }

    public int updateByPrimaryKeySelective(ReviewSmsQueue record){
        return reviewSmsQueueDao.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(ReviewSmsQueue record){
        return reviewSmsQueueDao.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(ReviewSmsQueue record){
        return reviewSmsQueueDao.updateByPrimaryKey(record);
    }

    /**
     * 批量插入短信
     * @param record
     * @return
     */
    public void addReviewSmsList(List<ReviewSmsQueue> record){
        reviewSmsQueueDao.addReviewSmsList(record);
    }

}
