package com.haier.shop.service;


import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.Reviewpoolfordhzx;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11 0011.
 */
public interface WoReviewpoolfordhzxDataService {
    public  void insertReviewpoolDhzx(Reviewpoolfordhzx reviewpoolfordhzx);
    public  void updateReviewpoolDhzxForProblemMessage(Reviewpoolfordhzx reviewpoolfordhzx);
    public  int  countReviewpoolfordhzx(Reviewpoolfordhzx reviewpoolfordhzx);

    public  void updateReviewpoolDhzx(Reviewpoolfordhzx reviewpoolfordhzx);
    public  Reviewpoolfordhzx findReviewByIds(Reviewpoolfordhzx reviewpoolfordhzx);
    public   List<Reviewpoolfordhzx>  page(Reviewpoolfordhzx reviewpoolfordhzx, PagerInfo pager) ;
    public   Reviewpoolfordhzx  getQueryReviewpoolfordhzx(Reviewpoolfordhzx reviewpoolfordhzx) ;
    public int pageCount(Reviewpoolfordhzx reviewpoolfordhzx);

}
