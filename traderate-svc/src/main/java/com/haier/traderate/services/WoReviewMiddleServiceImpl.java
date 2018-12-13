package com.haier.traderate.services;

import com.haier.shop.model.ReviewPool;
import com.haier.shop.service.ReviewPoolService;
import java.util.List;

import javax.annotation.Resource;


import com.haier.shop.model.ReviewFinalResult;
import com.haier.shop.model.ReviewMiddle;
import com.haier.shop.service.ReviewMiddleService;
import com.haier.shop.util.ReviewConstants;
import com.haier.traderate.service.WoReviewMiddleService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import org.springframework.stereotype.Service;

/**
 * 中间信息接口实现
 *
 * @Filename: ReviewMiddleServiceImpl.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
@Service
public class WoReviewMiddleServiceImpl implements WoReviewMiddleService {
    private static Logger     log = LogManager.getLogger(WoReviewMiddleServiceImpl.class);
    @Resource
    private ReviewMiddleService reviewMiddleService;
    @Resource
    private ReviewPoolService reviewPoolService;

    @Override
    public void addReviewMiddle(ReviewMiddle reviewMiddle) {
        try {
            reviewMiddleService.addReviewMiddle(reviewMiddle);
        } catch (Exception e) {
            log.error("[ReviewMiddleServiceImpl][addReviewMiddle]添加中间信息异常:", e);
        }
    }

    @Override
    public void updateReviewMiddle(ReviewMiddle reviewMiddle) {
        try {
            reviewMiddleService.updateReviewMiddle(reviewMiddle);
        } catch (Exception e) {
            log.error("[ReviewMiddleServiceImpl][updateReviewMiddle]修改中间信息异常:", e);
        }
    }

    @Override
    public void delReviewMiddleById(ReviewMiddle reviewMiddle) {
        try {
            reviewMiddleService.delReviewMiddleById(reviewMiddle);
        } catch (Exception e) {
            log.error("[ReviewMiddleServiceImpl][delReviewMiddleById]删除中间信息异常:", e);
        }
    }

    @Override
    public ServiceResult<ReviewMiddle> findReviewMiddleById(ReviewMiddle reviewMiddle) {
        ServiceResult<ReviewMiddle> serviceResult = new ServiceResult<ReviewMiddle>();
        try {
            ReviewMiddle data = reviewMiddleService.findReviewMiddleById(reviewMiddle);
            serviceResult.setResult(data);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ReviewMiddleServiceImpl][findReviewMiddleById]取中间信息异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<List<ReviewMiddle>> getReviewMiddleByReviewId(String reviewId) {
        ServiceResult<List<ReviewMiddle>> serviceResult = new ServiceResult<List<ReviewMiddle>>();
        try {
            List<ReviewMiddle> data = reviewMiddleService.getReviewMiddleByReviewId(reviewId);
            serviceResult.setResult(data);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ReviewMiddleServiceImpl][getReviewMiddleByReviewId]取中间信息异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Integer> getCountByReviewId(String reviewId) {
        ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
        try {
            int data = reviewMiddleService.getCountByReviewId(reviewId);
            serviceResult.setResult(data);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ReviewMiddleServiceImpl][getCountByReviewId]取中间信息异常:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<String> getMinTimeByReviewId(String reviewId) {
        ServiceResult<String> serviceResult = new ServiceResult<String>();
        try {
            String data = reviewMiddleService.getMinTimeByReviewId(reviewId);
            serviceResult.setResult(data);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ReviewMiddleServiceImpl][getMinTimeByReviewId]取中间信息异常:", e);
        }
        return serviceResult;
    }



    @Override
    public ServiceResult<List<ReviewFinalResult>> findFinalResult(String reviewId) {
        ServiceResult<List<ReviewFinalResult>> serviceResult = new ServiceResult<List<ReviewFinalResult>>();
        try {
            List<ReviewFinalResult> data = reviewMiddleService.findFinalResult(reviewId);
            serviceResult.setResult(data);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ReviewMiddleServiceImpl][findFinalResult]取最终信息异常:", e);
        }
        return  serviceResult;
    }

    @Override
    public ServiceResult<List<ReviewFinalResult>> findFinalResults(String reviewId) {
        ServiceResult<List<ReviewFinalResult>> serviceResult = new ServiceResult<List<ReviewFinalResult>>();
        try {
            List<ReviewFinalResult> data = reviewMiddleService.findFinalResults(reviewId);
            serviceResult.setResult(data);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ReviewMiddleServiceImpl][findFinalResult]取最终信息异常:", e);
        }
        return  serviceResult;
    }
    @Override
    public ServiceResult<List<ReviewMiddle>> findReviewMiddleByNetOrderIdAndQuestion1Level2(String netOrderId,
        String question1Level2) {
        ReviewPool reviewPool = reviewPoolService.findFinallyAddStateNoFinally(netOrderId,
            question1Level2,null);
        if (reviewPool != null) {
            ServiceResult<List<ReviewMiddle>> result = new ServiceResult<>();
            result.setResult(reviewMiddleService.getReviewMiddleByReviewId(reviewPool.getId()));
            return result;
        }
        return null;
    }
}
