package com.haier.shop.service;

import com.haier.shop.model.ReviewPoolJobSet;
import org.apache.ibatis.annotations.Param;

/**
 * Created by xupeng on 18/4/27.
 */
public interface ReviewPoolJobSetService {

    public ReviewPoolJobSet queryReviewPoolJobSet(@Param("value") String value);

    public ReviewPoolJobSet queryJobSet(@Param("jobName") String jobName);

}
