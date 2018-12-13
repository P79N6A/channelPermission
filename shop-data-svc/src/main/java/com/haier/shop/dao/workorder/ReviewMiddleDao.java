package com.haier.shop.dao.workorder;

import com.haier.shop.model.ReviewFinalResult;
import com.haier.shop.model.ReviewMiddle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xupeng on 18/4/26.
 */
@Mapper
public interface ReviewMiddleDao {

    /**
     * 添加评论中间表信息
     * @param reviewMiddle
     * @throws Exception
     */
    void addReviewMiddle(ReviewMiddle reviewMiddle);

    /**
     * 修改评论中间表信息
     * @param reviewMiddle
     * @throws Exception
     */
    void updateReviewMiddle(@Param("reviewMiddle") ReviewMiddle reviewMiddle);

    /**
     * 根据主键id删除信息
     * @throws Exception
     */
    void delReviewMiddleById(@Param("reviewMiddle") ReviewMiddle reviewMiddle);

    /**
     * 根绝主键id获取对象详情
     * @param reviewMiddle
     * @return
     * @throws Exception
     */
    ReviewMiddle findReviewMiddleById(@Param("reviewMiddle") ReviewMiddle reviewMiddle);



    /**
     * 根绝评论表id查询评论中间表信息
     * @param reviewId
     * @return
     * @throws Exception
     */
    List<ReviewMiddle> getReviewMiddleByReviewId(@Param("reviewId") String reviewId);


    List<ReviewMiddle> getReviewMiddleByReviewIds(@Param("reviewId") String id);
    /**
     * 根绝评论表id查询评论中间表信息条数
     * @param reviewId
     * @return
     * @throws Exception
     */
    int getCountByReviewId(@Param("reviewId") String reviewId);


    /**
     * 根绝中间表id返回最小时间
     * @param reviewId
     * @return
     * @throws Exception
     */
    String getMinTimeByReviewId(@Param("reviewId") String reviewId);

    /**
     * 根绝中间表id返回时间
     * @param reviewId
     * @return
     * @throws Exception
     */
    String getMaxTimeByReviewId(@Param("reviewId") String reviewId);

    /**
     * 查询多条中间结果
     * @param
     * @return
     */
    List<ReviewMiddle> findByreviewIdList(List<String> reviewIdList);

    /**
     * 查询最终结果历史信息
     * @param reviewId
     * @return
     * @throws Exception
     */
    List<ReviewFinalResult> findFinalResult(@Param("reviewId") String reviewId);

    /**
     * 添加最终表信息
     * @param reviewFinalResult
     * @throws Exception
     */
    void addReviewFinalResult(ReviewFinalResult reviewFinalResult);


  /**
   * 批量添加中间表信息
   */
  void addReviewMiddleList(List<ReviewMiddle> reviewMiddleList);

}
