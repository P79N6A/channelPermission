package com.haier.shop.services;

import com.haier.common.util.DateUtil;
import com.haier.shop.dao.workorder.ReviewMiddleDao;
import com.haier.shop.dao.workorder.WoReviewpoolfordhzxDao;
import com.haier.shop.model.ReviewFinalResult;
import com.haier.shop.model.ReviewMiddle;
import com.haier.shop.model.Reviewpoolfordhzx;
import com.haier.shop.service.ReviewMiddleService;
import com.haier.shop.util.ReviewConstants;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by xupeng on 18/4/26.
 */
@Service
public class ReviewMiddleServiceImpl implements ReviewMiddleService {

    @Autowired
    ReviewMiddleDao reviewMiddleDao;
    @Autowired
    WoReviewpoolfordhzxDao woReviewpoolfordhzxDao;

    /**
     * 添加评论中间表信息
     * @param reviewMiddle
     * @throws Exception
     */
    @Override
    public void addReviewMiddle(ReviewMiddle reviewMiddle){
        reviewMiddleDao.addReviewMiddle(reviewMiddle);
    }

    /**
     * 修改评论中间表信息
     * @param reviewMiddle
     * @throws Exception
     */
    @Override
    public void updateReviewMiddle(@Param("reviewMiddle") ReviewMiddle reviewMiddle){

        reviewMiddleDao.updateReviewMiddle(reviewMiddle);
    }

    /**
     * 根据主键id删除信息
     * @throws Exception
     */
    @Override
    public void delReviewMiddleById(@Param("reviewMiddle") ReviewMiddle reviewMiddle){
        reviewMiddleDao.delReviewMiddleById(reviewMiddle);
    }

    /**
     * 根绝主键id获取对象详情
     * @param reviewMiddle
     * @return
     * @throws Exception
     */
    @Override
    public ReviewMiddle findReviewMiddleById(@Param("reviewMiddle") ReviewMiddle reviewMiddle){
        return reviewMiddleDao.findReviewMiddleById(reviewMiddle);
    }



    /**
     * 根绝评论表id查询评论中间表信息
     * @param reviewId
     * @return
     * @throws Exception
     */
    @Override
    public List<ReviewMiddle> getReviewMiddleByReviewId(@Param("reviewId") String reviewId){
        return reviewMiddleDao.getReviewMiddleByReviewId(reviewId);
    }


    @Override
    public List<ReviewMiddle> getReviewMiddleByReviewIds(@Param("reviewId") String id){
        return reviewMiddleDao.getReviewMiddleByReviewIds(id);
    }
    /**
     * 根绝评论表id查询评论中间表信息条数
     * @param reviewId
     * @return
     * @throws Exception
     */
    @Override
    public int getCountByReviewId(@Param("reviewId") String reviewId){
        return reviewMiddleDao.getCountByReviewId(reviewId);
    }


    /**
     * 根绝中间表id返回最小时间
     * @param reviewId
     * @return
     * @throws Exception
     */
    @Override
    public String getMinTimeByReviewId(@Param("reviewId") String reviewId){
        return reviewMiddleDao.getMinTimeByReviewId(reviewId);
    }

    /**
     * 根绝中间表id返回时间
     * @param reviewId
     * @return
     * @throws Exception
     */
    @Override
    public String getMaxTimeByReviewId(@Param("reviewId") String reviewId){
        return reviewMiddleDao.getMaxTimeByReviewId(reviewId);
    }

    /**
     * 查询多条中间结果
     * @param
     * @return
     */
    @Override
    public List<ReviewMiddle> findByreviewIdList(List<String> reviewIdList){
        return reviewMiddleDao.findByreviewIdList(reviewIdList);
    }

    /**
     * 查询最终结果历史信息
     * @param reviewId
     * @return
     * @throws Exception
     */
    @Override
    public List<ReviewFinalResult> findFinalResult(@Param("reviewId") String reviewId){
        return reviewMiddleDao.findFinalResult(reviewId);
    }

    public List<ReviewFinalResult> findFinalResults(String reviewId) {
        List<ReviewFinalResult> reviewFinalResults=reviewMiddleDao.findFinalResult(reviewId);
        Reviewpoolfordhzx reviewPool = new Reviewpoolfordhzx();
        ReviewFinalResult reviewFinalResult = new ReviewFinalResult();
        reviewPool.setId(Integer.parseInt(reviewId));
        if (reviewFinalResults.size()==0)
        {
            reviewPool=woReviewpoolfordhzxDao.findReviewByIds(reviewPool);
            if(reviewPool.getBackContext3()!=null&&!"".equals(reviewPool.getBackContext3())) {
                reviewFinalResult.setMiddleContext(reviewPool.getBackContext3());
                reviewFinalResult.setReviewid(reviewFinalResult.getReviewid());
                reviewFinalResult.setAddtime(reviewPool.getCloseTime());
                reviewFinalResult.setAdduser(reviewPool.getRemark2());
                reviewFinalResults.add(reviewFinalResult);
            }
        }
        return reviewFinalResults;
    }

    public void insertReviewpoolfordhzxMidd(Reviewpoolfordhzx reviewpoolfordhzx) {
        String addTime = DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE);
        ReviewMiddle reviewMiddle = new ReviewMiddle();
        reviewMiddle.setId(UUID.randomUUID().toString());
        reviewMiddle.setReviewid(String.valueOf(reviewpoolfordhzx.getId()));
        reviewMiddle.setAddtime(addTime);
        reviewMiddle.setMiddleContext(reviewpoolfordhzx.getProblem());
        reviewMiddle.setAdduser(reviewpoolfordhzx.getUserName());
        reviewMiddle.setResultType("0");
        reviewMiddleDao.addReviewMiddle(reviewMiddle);
    }

    public void insertReviewpoolfordhzxResult(Reviewpoolfordhzx reviewpoolfordhzx) {
        String addTime = DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE);
        ReviewFinalResult reviewFinalResult = new ReviewFinalResult();
        reviewFinalResult.setAddtime(addTime);
        reviewFinalResult.setId(UUID.randomUUID().toString());
        reviewFinalResult.setMiddleContext(reviewpoolfordhzx.getBackContext3());
        reviewFinalResult.setAdduser(reviewpoolfordhzx.getUserName());
        reviewFinalResult.setReviewid(String.valueOf(reviewpoolfordhzx.getId()));
        reviewMiddleDao.addReviewFinalResult(reviewFinalResult);

    }
}
