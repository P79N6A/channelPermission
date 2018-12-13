package com.haier.shop.services;




import com.haier.common.PagerInfo;
import com.haier.shop.dao.workorder.WoReviewpoolfordhzxDao;
import com.haier.shop.model.Reviewpoolfordhzx;
import com.haier.shop.service.WoReviewpoolfordhzxDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11 0011.
 */
@Service
public class WoReviewpoolfordhzxDataServiceImpl implements WoReviewpoolfordhzxDataService {

    @Autowired
    private WoReviewpoolfordhzxDao woReviewpoolfordhzxDao;


    public  void insertReviewpoolDhzx(Reviewpoolfordhzx reviewpoolfordhzx){

        woReviewpoolfordhzxDao.insertReviewpoolDhzx(reviewpoolfordhzx);

    }public  void updateReviewpoolDhzxForProblemMessage(Reviewpoolfordhzx reviewpoolfordhzx){

        woReviewpoolfordhzxDao.updateReviewpoolDhzxForProblemMessage(reviewpoolfordhzx);

    }
    public  int  countReviewpoolfordhzx(Reviewpoolfordhzx reviewpoolfordhzx){

        return woReviewpoolfordhzxDao.countReviewpoolfordhzx(reviewpoolfordhzx);

    }

    public  void updateReviewpoolDhzx(Reviewpoolfordhzx reviewpoolfordhzx){

        woReviewpoolfordhzxDao.updateReviewpoolDhzx(reviewpoolfordhzx);

    }public  Reviewpoolfordhzx findReviewByIds(Reviewpoolfordhzx reviewpoolfordhzx){

       return  woReviewpoolfordhzxDao.findReviewByIds(reviewpoolfordhzx);

    }
    public   List<Reviewpoolfordhzx>  page(Reviewpoolfordhzx reviewpoolfordhzx, PagerInfo pager) {
                List<Reviewpoolfordhzx> date= woReviewpoolfordhzxDao.page(reviewpoolfordhzx,pager);
        return date;
    }  public   Reviewpoolfordhzx  getQueryReviewpoolfordhzx(Reviewpoolfordhzx reviewpoolfordhzx) {
                Reviewpoolfordhzx date= woReviewpoolfordhzxDao.getQueryReviewpoolfordhzx(reviewpoolfordhzx);
        return date;
    }
    public int pageCount(Reviewpoolfordhzx reviewpoolfordhzx) {
        int total = woReviewpoolfordhzxDao.pageCount(reviewpoolfordhzx);
        return total;
    }

}
