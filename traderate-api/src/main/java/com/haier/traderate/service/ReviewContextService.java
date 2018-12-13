package com.haier.traderate.service;

import com.haier.common.ServiceResult;
import com.haier.shop.model.ReviewContext;
import java.util.List;

/**
 * 用户评论操作接口类
 *                       
 * @Filename: ReviewContextService.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
public interface ReviewContextService {
    /**
     * 通过reviewId获取评论信息
     * @param reviewId
     * @return
     */
    ServiceResult<List<ReviewContext>> getReviewContextByReviewId(String reviewId);

    /**
     * 添加评论信息
     * @param reviewContext
     * @throws Exception
     */
    ServiceResult<Boolean> addReviewContext(ReviewContext reviewContext);

    /**
     * 修改评论信息
     * @param reviewContext
     * @throws Exception
     */
    ServiceResult<Boolean> updateReviewContext(ReviewContext reviewContext);

    /**
     * 根据主键id删除信息
     * @throws Exception
     */
    ServiceResult<Boolean> delReviewContextById(ReviewContext reviewContext);

    /**
     * 根绝主键id获取对象详情
     * @param reviewContext
     * @return
     * @throws Exception
     */
    ServiceResult<ReviewContext> findReviewContextById(ReviewContext reviewContext);

    /**
     * 根绝评论表id查询评论信息表信息条数
     * @param reviewId
     * @return
     * @throws Exception
     */
    ServiceResult<Integer> getCountByReviewId(String reviewId);

    /**
     * 通过网单号与责任位获取反馈信息
     * @param netOrderId
     * @param question1Level2
     * @return
     */
    ServiceResult<List<ReviewContext>> findReviewContextByNetOrderIdAndQuestion1Level2(
        String netOrderId,
        String question1Level2,
        String question1Level3
    );
}
