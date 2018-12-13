package com.haier.shop.dao.workorder;

import com.haier.shop.model.ReviewContext;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by xupeng on 18/4/26.
 */
@Mapper
public interface ReviewContextDao {


  /**
   * 批量添加评论信息
   */
  int addReviewContextList(List<ReviewContext> oldContexts);

  /**
   * 添加评论信息
   */
  void addReviewContext(ReviewContext reviewContext);

  /**
   * 修改评论信息
   */
  void updateReviewContext(@Param("reviewContext") ReviewContext reviewContext);

  /**
   * 根据主键id删除信息
   */
  void delReviewContextById(@Param("reviewContext") ReviewContext reviewContext);

  /**
   * 根绝主键id获取对象详情
   */
  ReviewContext findReviewContextById(@Param("reviewContext") ReviewContext reviewContext);

  /**
   * 根绝评论表id查询评论信息表信息
   */
  List<ReviewContext> getReviewContextByReviewId(@Param("reviewId") String reviewId);


  List<ReviewContext> querForReviewId(@Param("id") String id);


  /**
   * 根绝评论表id查询评论信息表信息条数
   */
  int getCountByReviewId(@Param("reviewId") String reviewId);

  /**
   * 根据工单idList查询反馈信息总条数
   */
  int getCountByReviewIdList(List<String> reviewIdList);

  /**
   * 通过工单ID获取最新非系统评论内容
   */
  ReviewContext findNewTimeCountByReviewId(@Param("reviewId") String reviewId);

  /**
   * 查出未发送HP的反馈
   */
  List<ReviewContext> findContextByState();

  /**
   *
   * @param reviewId
   * @return
   */
  ReviewContext findContextByReviewIdAndAddtime(@Param("reviewid") String reviewId);


}
