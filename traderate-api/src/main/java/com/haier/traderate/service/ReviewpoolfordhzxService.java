package com.haier.traderate.service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.Reviewpoolfordhzx;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11 0011.
 */
public interface ReviewpoolfordhzxService {
    //创建工单
    ServiceResult<Boolean> insertReviewpoolDhzx(Reviewpoolfordhzx reviewpoolfordhzx);
    //修改问题描述的追加
    ServiceResult<Boolean> updateReviewpoolDhzxForProblemMessage(
        Reviewpoolfordhzx reviewpoolfordhzx);
    //查询工单
    ServiceResult<List<Reviewpoolfordhzx>> pageReviewpoolfordhzx(
        Reviewpoolfordhzx reviewpoolfordhzx, PagerInfo pager);
    //获取单个工单根据 400id
    ServiceResult<Reviewpoolfordhzx> getQueryReviewpoolfordhzx(Reviewpoolfordhzx reviewpoolfordhzx);
    //修改400工单
    ServiceResult<Boolean> updateReviewpoolfordhzx(Reviewpoolfordhzx reviewpoolfordhzx);
    //是否跳闸
    ServiceResult<Boolean> updateReviewpoolfordhzxTrip(Reviewpoolfordhzx reviewpoolfordhzx);
}
