package com.haier.shop.services;

import com.haier.shop.dao.workorder.ReviewMailQueueDao;
import com.haier.shop.model.ReviewMailQueue;
import com.haier.shop.service.ReviewMailQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xupeng on 18/4/27.
 *
 */
@Service
public class ReviewMailQueueServiceImpl implements ReviewMailQueueService {

    @Autowired
    ReviewMailQueueDao reviewMailQueueDao;

    @Override
    public ReviewMailQueue selectByPrimaryKey(Long id){
        return reviewMailQueueDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ReviewMailQueue> selectSendMail(){
        return reviewMailQueueDao.selectSendMail();
    }

    @Override
    public int deleteByPrimaryKey(Long id){
        return reviewMailQueueDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ReviewMailQueue record){
        return reviewMailQueueDao.insert(record);
    }

    @Override
    public int insertSelective(ReviewMailQueue record){
        return reviewMailQueueDao.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ReviewMailQueue record){
        return reviewMailQueueDao.updateByPrimaryKeySelective(record);
    }


    @Override
    public int updateByPrimaryKeyWithBLOBs(ReviewMailQueue record){
        return reviewMailQueueDao.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int updateByPrimaryKey(ReviewMailQueue record){
        return reviewMailQueueDao.updateByPrimaryKey(record);
    }

    /**
     * 批量插入邮件
     * @param record
     * @return
     */
    @Override
    public void addReviewEmailList(List<ReviewMailQueue> record){
        reviewMailQueueDao.addReviewEmailList(record);
    }

}
