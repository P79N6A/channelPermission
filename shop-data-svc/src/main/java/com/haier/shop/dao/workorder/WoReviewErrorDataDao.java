package com.haier.shop.dao.workorder;

import com.haier.shop.model.ReviewErrorData;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.dao.shopwrite
 * @Date: Created in 2018/4/28 9:39
 * @Modified By:
 */
public interface WoReviewErrorDataDao {

  int deleteByPrimaryKey(Long id);

  int insert(ReviewErrorData record);

  int insertSelective(ReviewErrorData record);

  ReviewErrorData selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(ReviewErrorData record);

  int updateByPrimaryKey(ReviewErrorData record);

}
