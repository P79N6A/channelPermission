package com.haier.shop.dao.workorder;

import com.haier.shop.model.ReviewImage;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReviewImageDao {

  int deleteByPrimaryKey(String workorderid);

  int insert(ReviewImage record);

  int insertSelective(ReviewImage record);

  ReviewImage selectByPrimaryKey(String workorderid);

  int updateByPrimaryKeySelective(ReviewImage record);

  int updateByPrimaryKeyWithBLOBs(ReviewImage record);

  int updateByPrimaryKey(ReviewImage record);

  int deleteByPoolColse(@Param("time") String time);

  int delById(String id);

  List<ReviewImage> findReviewImgsById(String reviewId);

  ReviewImage findReviewImageById(String id);

  int selectCount(@Param("reviewId")String reviewId,@Param("info")String info);
}