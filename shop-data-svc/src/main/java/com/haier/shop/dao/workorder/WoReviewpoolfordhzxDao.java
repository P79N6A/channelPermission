package com.haier.shop.dao.workorder;


import com.haier.common.PagerInfo;
import com.haier.shop.model.Reviewpoolfordhzx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12 0012.
 */
@Mapper
public interface WoReviewpoolfordhzxDao {
    List<Reviewpoolfordhzx> page(@Param("reviewpoolfordhzx") Reviewpoolfordhzx reviewpoolfordhzx, @Param("pager") PagerInfo pager);

    Reviewpoolfordhzx getQueryReviewpoolfordhzx(@Param("reviewpoolfordhzx") Reviewpoolfordhzx reviewpoolfordhzx);


    Reviewpoolfordhzx findReviewByIds(@Param("reviewpoolfordhzx") Reviewpoolfordhzx reviewpoolfordhzx);

    int pageCount(@Param("reviewpoolfordhzx") Reviewpoolfordhzx reviewpoolfordhzx);

    int countReviewpoolfordhzx(@Param("reviewpoolfordhzx") Reviewpoolfordhzx reviewpoolfordhzx);

    //write

    void  insertReviewpoolDhzx(@Param("reviewpoolfordhzx") Reviewpoolfordhzx reviewpoolfordhzx);

    void  updateReviewpoolDhzx(@Param("reviewpoolfordhzx") Reviewpoolfordhzx reviewpoolfordhzx);

    void  updateReviewpoolDhzxForProblemMessage(@Param("reviewpoolfordhzx") Reviewpoolfordhzx reviewpoolfordhzx);



}
