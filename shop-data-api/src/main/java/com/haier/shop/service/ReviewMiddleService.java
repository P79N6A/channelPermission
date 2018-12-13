package com.haier.shop.service;

import com.haier.shop.model.ReviewFinalResult;
import com.haier.shop.model.ReviewMiddle;
import com.haier.shop.model.Reviewpoolfordhzx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by xupeng on 18/4/26.
 */
public interface ReviewMiddleService {

    public void addReviewMiddle(ReviewMiddle reviewMiddle);

    /**
     * 修改评论中间表信息
     * @param reviewMiddle
     * @throws Exception
     */
    public void updateReviewMiddle(@Param("reviewMiddle") ReviewMiddle reviewMiddle);

    /**
     * 根据主键id删除信息
     * @throws Exception
     */
    public void delReviewMiddleById(@Param("reviewMiddle") ReviewMiddle reviewMiddle);

    /**
     * 根绝主键id获取对象详情
     * @param reviewMiddle
     * @return
     * @throws Exception
     */
    public ReviewMiddle findReviewMiddleById(@Param("reviewMiddle") ReviewMiddle reviewMiddle);



    /**
     * 根绝评论表id查询评论中间表信息
     * @param reviewId
     * @return
     * @throws Exception
     */
    public List<ReviewMiddle> getReviewMiddleByReviewId(@Param("reviewId") String reviewId);


    public List<ReviewMiddle> getReviewMiddleByReviewIds(@Param("reviewId") String id);
    /**
     * 根绝评论表id查询评论中间表信息条数
     * @param reviewId
     * @return
     * @throws Exception
     */
    public int getCountByReviewId(@Param("reviewId") String reviewId);


    /**
     * 根绝中间表id返回最小时间
     * @param reviewId
     * @return
     * @throws Exception
     */
    public String getMinTimeByReviewId(@Param("reviewId") String reviewId);

    /**
     * 根绝中间表id返回时间
     * @param reviewId
     * @return
     * @throws Exception
     */
    public String getMaxTimeByReviewId(@Param("reviewId") String reviewId);

    /**
     * 查询多条中间结果
     * @param
     * @return
     */
    public List<ReviewMiddle> findByreviewIdList(List<String> reviewIdList);

    /**
     * 查询最终结果历史信息
     * @param reviewId
     * @return
     * @throws Exception
     */
    public List<ReviewFinalResult> findFinalResult(@Param("reviewId") String reviewId);

    public List<ReviewFinalResult> findFinalResults(String reviewId);

    public void insertReviewpoolfordhzxMidd(Reviewpoolfordhzx reviewpoolfordhzx);

    public void insertReviewpoolfordhzxResult(Reviewpoolfordhzx reviewpoolfordhzx) ;
}
