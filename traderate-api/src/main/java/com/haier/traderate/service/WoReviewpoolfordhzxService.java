package com.haier.traderate.service;


import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.Reviewpoolfordhzx;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11 0011.
 */
public interface WoReviewpoolfordhzxService {

  public ServiceResult<Boolean> insertReviewpoolDhzx(Reviewpoolfordhzx reviewpoolfordhzx);


  public ServiceResult<Boolean> updateReviewpoolDhzxForProblemMessage(
      Reviewpoolfordhzx reviewpoolfordhzx);


  public ServiceResult<List<Reviewpoolfordhzx>> pageReviewpoolfordhzx(
      Reviewpoolfordhzx reviewpoolfordhzx, PagerInfo pager);


  public ServiceResult<Reviewpoolfordhzx> getQueryReviewpoolfordhzx(
      Reviewpoolfordhzx reviewpoolfordhzx);

  public ServiceResult<Boolean> updateReviewpoolfordhzx(Reviewpoolfordhzx reviewpoolfordhzx);

  public ServiceResult<Boolean> updateReviewpoolfordhzxTrip(Reviewpoolfordhzx reviewpoolfordhzx);
}
