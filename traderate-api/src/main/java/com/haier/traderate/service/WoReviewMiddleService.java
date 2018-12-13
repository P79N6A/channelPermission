package com.haier.traderate.service;

import java.util.List;

import javax.annotation.Resource;


import com.haier.common.ServiceResult;
import com.haier.shop.model.ReviewFinalResult;
import com.haier.shop.model.ReviewMiddle;

/**
 * 中间结果操作
 *                       
 * @Filename: WebReviewMiddleModel.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */

public interface WoReviewMiddleService {


    /**
     * 添加评论中间表信息
     * @param reviewMiddle
     * @throws Exception
     */
    public void addReviewMiddle(ReviewMiddle reviewMiddle);

    /**
     * 修改评论中间表信息
     * @param reviewMiddle
     * @throws Exception
     */
    public void updateReviewMiddle(ReviewMiddle reviewMiddle);

    /**
     * 根据主键id删除信息
     * @throws Exception
     */
    public void delReviewMiddleById(ReviewMiddle reviewMiddle) ;

    /**
     * 根绝主键id获取对象详情
     * @param reviewMiddle
     * @return
     * @throws Exception
     */
    public ServiceResult<ReviewMiddle> findReviewMiddleById(ReviewMiddle reviewMiddle);

    /**
     * 根绝评论表id查询评论中间表信息
     * @param reviewId
     * @return
     * @throws Exception
     */
    public ServiceResult<List<ReviewMiddle>> getReviewMiddleByReviewId(String reviewId);

    /**
     * 根绝评论表id查询评论中间表信息条数
     * @param reviewId
     * @return
     * @throws Exception
     */
    public ServiceResult<Integer> getCountByReviewId(String reviewId);

    /**
     * 根绝中间表id返回最小时间
     * @param reviewId
     * @return
     * @throws Exception
     */
    public ServiceResult<String> getMinTimeByReviewId(String reviewId);



    /**
     * 查询最终结果历史信息
     * @param reviewId
     * @return
     * @throws Exception
     */
    public ServiceResult<List<ReviewFinalResult>> findFinalResult(String reviewId);
    public ServiceResult<List<ReviewFinalResult>> findFinalResults(String reviewId);

    ServiceResult<List<ReviewMiddle>> findReviewMiddleByNetOrderIdAndQuestion1Level2(String netOrderId,
        String question1Level2);
}
