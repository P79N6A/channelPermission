package com.haier.traderate.services;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.shop.model.ReviewImage;
import com.haier.shop.service.ReviewImageDataService;
import com.haier.shop.util.ReviewConstants;
import com.haier.traderate.service.ReviewImageService;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.traderate.services
 * @Date: Created in 2018/5/7 14:13
 * @Modified By:
 */
@Service
public class ReviewImageServiceImpl implements ReviewImageService {
  private static Logger log = LogManager.getLogger(ReviewImageServiceImpl.class);

  @Resource
  private ReviewImageDataService reviewImageDataService;

  /**
   * 通过ID读取图片
   *
   * @return
   */
  @Override
  public ServiceResult<ReviewImage> findReviewImageById(String id) {
    ServiceResult<ReviewImage> serviceResult = new ServiceResult<ReviewImage>();
    try {
      ReviewImage image = reviewImageDataService.findReviewImageById(id);
      serviceResult.setResult(image);
    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewImageServiceImpl][findReviewImageById]通过ID读取图片异常:", e);
    }
    return serviceResult;
  }

  /**
   * 通过工单ID查看图片是否存在
   *
   * @param reviewId
   * @return
   */
  @Override
  public ServiceResult<Boolean> findImageFlg(String reviewId) {
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    try {
      List<ReviewImage> list = reviewImageDataService.findReviewImgsById(reviewId);
      if (list.size() > 0) {
        serviceResult.setResult(true);
      } else {
        serviceResult.setResult(false);
      }

    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewImageServiceImpl][findReviewImageById]通过工单ID读取图片异常:", e);
    }
    return serviceResult;
  }

  @Override
  public ServiceResult<List<ReviewImage>> findReviewImgsById(String reviewId) {
    ServiceResult<List<ReviewImage>> serviceResult = new ServiceResult<List<ReviewImage>>();
    try {
      List<ReviewImage> images = reviewImageDataService.findReviewImgsById(reviewId);
      if (null != images && images.size() > 0) {
        serviceResult.setSuccess(true);
        serviceResult.setResult(images);
      } else {
        //没有查到图片
        serviceResult.setMessage("无图片");
      }

    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewImageServiceImpl][findReviewImgsById]通过工单ID读取图片异常:", e);
    }
    return serviceResult;
  }

  @Override
  public ServiceResult<Boolean> delById(String id) {
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    try {
      int count = reviewImageDataService.delById(id);
      if (count > 0) {
        serviceResult.setResult(true);
      } else {
        serviceResult.setResult(false);
      }

    } catch (BusinessException be) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage(be.getMessage());
    } catch (Exception e) {
      serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
      log.error("[ReviewImageServiceImpl][delById]删除图片异常:", e);
    }
    return serviceResult;
  }
}

