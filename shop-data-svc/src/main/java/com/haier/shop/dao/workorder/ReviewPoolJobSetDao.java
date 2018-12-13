package com.haier.shop.dao.workorder;

import com.haier.shop.model.ReviewPoolJobSet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by xupeng on 18/4/27.
 */
@Mapper
public interface ReviewPoolJobSetDao {

    ReviewPoolJobSet queryReviewPoolJobSet(@Param("value") String value);

    ReviewPoolJobSet queryJobSet(@Param("jobName") String jobName);
}
