package com.haier.shop.service;

import com.haier.shop.model.ReviewImage;
import java.util.List;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.service
 * @Date: Created in 2018/5/7 14:20
 * @Modified By:
 */
public interface ReviewImageDataService {

  ReviewImage findReviewImageById(String id);

  int selectCount(String reviewId,String info);

  List<ReviewImage> findReviewImgsById(String reviewId);

  int delById(String id);

  /**
   * 删除指定日期之前的图片
   * @param time
   * @return
   */
  public int deleteByPoolColse(String time);
}
