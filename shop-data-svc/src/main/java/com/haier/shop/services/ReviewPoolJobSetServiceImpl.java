package com.haier.shop.services;

import com.haier.shop.dao.workorder.ReviewPoolJobSetDao;
import com.haier.shop.model.ReviewPoolJobSet;
import com.haier.shop.service.ReviewPoolJobSetService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xupeng on 18/4/27.
 */
@Service
public class ReviewPoolJobSetServiceImpl implements ReviewPoolJobSetService {

    @Autowired
    ReviewPoolJobSetDao reviewPoolJobSetDao;

    public ReviewPoolJobSet queryReviewPoolJobSet(@Param("value") String value){
        return reviewPoolJobSetDao.queryReviewPoolJobSet(value);
    }

    public ReviewPoolJobSet queryJobSet(@Param("jobName") String jobName){
        return reviewPoolJobSetDao.queryJobSet(jobName);
    }

}
