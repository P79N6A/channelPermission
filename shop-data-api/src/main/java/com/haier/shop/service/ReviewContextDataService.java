package com.haier.shop.service;

import com.haier.shop.model.ReviewContext;
import java.util.List;
import java.util.Map;

/**
 * Created by xupeng on 18/4/26.
 *
 *
 */
public interface ReviewContextDataService {
    Map<String, Object> findReviewContextByNetOrderIdAndQuestion1Level2(String netOrderId,
        String question1Level2,String question1Level3);

    void addReviewContext(ReviewContext reviewContext);

    List<ReviewContext> getReviewContextByReviewId(String reviewId);

    void updateReviewContext(ReviewContext reviewContext);

    void delReviewContextById(ReviewContext reviewContext);

    int getCountByReviewId(String reviewId);

    ReviewContext findReviewContextById(ReviewContext reviewContext);
}
