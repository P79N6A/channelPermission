package com.haier.traderate.service;

import com.haier.common.ServiceResult;
import com.haier.shop.model.ReviewImage;
import java.util.List;

/**
 * 图片操作接口
 *
 * @Filename: ReviewImageService.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 */
public interface ReviewImageService {

  /**
   * 通过ID读取图片
   */
  public ServiceResult<ReviewImage> findReviewImageById(String id);

  /**
   * 查询多张图片
   */
  public ServiceResult<List<ReviewImage>> findReviewImgsById(String reviewId);

  /**
   * 通过工单ID查看图片是否存在
   */
  public ServiceResult<Boolean> findImageFlg(String reviewId);

  /**
   * 根据Id删除图片
   */
  public ServiceResult<Boolean> delById(String id);
}
