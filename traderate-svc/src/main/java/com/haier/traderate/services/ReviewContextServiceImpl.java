package com.haier.traderate.services;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.shop.model.ReviewContext;
import com.haier.shop.service.ReviewContextDataService;
import com.haier.shop.util.ReviewConstants;
import com.haier.traderate.service.ReviewContextService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 评论操作
 *
 * @Filename: WebReviewContextModel.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 */
@Service
public class ReviewContextServiceImpl implements ReviewContextService {

  @Resource
  private ReviewContextDataService reviewContextDataService;

  /**
   * 通过reviewId获取评论信息
   */
  @Override
  public ServiceResult<List<ReviewContext>> getReviewContextByReviewId(String reviewId) {
    ServiceResult<List<ReviewContext>> serviceResult = new ServiceResult<List<ReviewContext>>();
    try {
            List<ReviewContext> data = reviewContextDataService.getReviewContextByReviewId(reviewId);
            serviceResult.setResult(data);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
    }
    return serviceResult;
  }

  /**
   * 添加评论信息
   */
  @Override
  public ServiceResult<Boolean> addReviewContext(ReviewContext reviewContext) {
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    try {
            reviewContextDataService.addReviewContext(reviewContext);
      serviceResult.setResult(true);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
    }
    return serviceResult;
  }

  /**
   * 修改评论信息
   */
  @Override
  public ServiceResult<Boolean> updateReviewContext(ReviewContext reviewContext) {
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    try {
            reviewContextDataService.updateReviewContext(reviewContext);
      serviceResult.setResult(true);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
    }
    return serviceResult;
  }

  /**
   * 根据主键id删除信息
   */
  @Override
  public ServiceResult<Boolean> delReviewContextById(ReviewContext reviewContext) {
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    try {
            reviewContextDataService.delReviewContextById(reviewContext);
      serviceResult.setResult(true);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
    }
    return serviceResult;
  }

  /**
   * 根绝主键id获取对象详情
   */
  @Override
  public ServiceResult<ReviewContext> findReviewContextById(ReviewContext reviewContext) {
    ServiceResult<ReviewContext> serviceResult = new ServiceResult<ReviewContext>();
    try {
            ReviewContext data = reviewContextDataService.findReviewContextById(reviewContext);
            serviceResult.setResult(data);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
    }
    return serviceResult;
  }

  /**
   * 根绝评论表id查询评论信息表信息条数
   */
  @Override
  public ServiceResult<Integer> getCountByReviewId(String reviewId) {
    ServiceResult<Integer> serviceResult = new ServiceResult<Integer>();
    try {
            int data = reviewContextDataService.getCountByReviewId(reviewId);
            serviceResult.setResult(data);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
    }
    return serviceResult;
  }

  /**
   * 通过网单号与责任位获取反馈信息
   */
  @Override
  public ServiceResult<List<ReviewContext>> findReviewContextByNetOrderIdAndQuestion1Level2(
      String netOrderId,
      String question1Level2, String question1Level3) {
    ServiceResult<List<ReviewContext>> serviceResult = new ServiceResult<List<ReviewContext>>();
    try {
      Map<String, Object> map = reviewContextDataService
          .findReviewContextByNetOrderIdAndQuestion1Level2(netOrderId, question1Level2,
              question1Level3);
      if (map != null) {
        List<ReviewContext> list = (List<ReviewContext>) map.get("list");
        serviceResult.setMessage(map.get("askFlg") + "");
        serviceResult.setResult(list);
      } else {
        serviceResult.setSuccess(false);
      }
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
    }
    return serviceResult;
  }
}
