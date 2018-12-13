package com.haier.shop.services;

import com.haier.common.PagerInfo;
import com.haier.shop.dao.workorder.ReviewPoolDao;
import com.haier.shop.model.ReviewPool;
import com.haier.shop.service.ReviewPoolService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xupeng on 18/4/26.
 */
@Service
public class ReviewPoolServiceImpl implements ReviewPoolService {

    @Autowired
    ReviewPoolDao reviewPoolDao;
    /**
     * 添加工单
     * @param reviewPool
     * @throws Exception
     */
    @Override
    public void addReview(ReviewPool reviewPool){
        reviewPoolDao.addReview(reviewPool);

    }


    /**
     * 修改工单信息
     * @param pool
     */
    @Override
    public void updWorkOrder(@Param("pool") ReviewPool pool){
        reviewPoolDao.updWorkOrder(pool);
    }


    /**
     * 删除所有
     * @throws Exception
     */
    @Override
    public void delAll(){
        reviewPoolDao.delAll();
    }

    /**
     * 获得满足条件的总条数
     * @param pool
     * @return
     * @throws Exception
     */
    @Override
    public int findPageCount(@Param("pool") ReviewPool pool, @Param("startTime") String startTime,
                                                            @Param("endTime") String endTime){
        return reviewPoolDao.findPageCount(pool, startTime, endTime);

    }

    /**
     * 获得满足条件的总条数
     * @param pool
     * @return
     * @throws Exception
     */
    @Override
    public int searchPageCount(@Param("pool") ReviewPool pool, @Param("searchStartTime") String startTime,
                                                                        @Param("searchEndTime") String endTime){

        return reviewPoolDao.searchPageCount(pool, startTime, endTime);

    }
    /**
     * 查询单是否符合传送SQM条件
     * @param reviewPool
     * @throws Exception
     */
    @Override
    public int getTplCount(@Param("pool") ReviewPool reviewPool){
        return reviewPoolDao.getTplCount(reviewPool);
    }

    /**
     * 根据id 获得详情
     * @param pool
     * @return
     * @throws Exception
     */
    @Override
    public ReviewPool findReviewById(@Param("pool") ReviewPool pool){
        return reviewPoolDao.findReviewById(pool);
    }




    @Override
    public List<ReviewPool> getReviewPoolForMobile(@Param("pool") ReviewPool pool){
        return reviewPoolDao.getReviewPoolForMobile(pool);
    }

    /**
     * 修改评论(审核状态)
     * @param pool
     */
    @Override
    public void updReview(@Param("pool") ReviewPool pool){
        reviewPoolDao.updReview(pool);
    }

    /**
     * 查询netOrderId = #{netOrderId}的总条数
     * @param netOrderId
     * @return
     */
    @Override
    public int qryFeedBackCount(@Param("netOrderId") String netOrderId){
        return reviewPoolDao.qryFeedBackCount(netOrderId);
    }

    /**
     * 查询orderCome = 'SG' and workStatus = 0 and question1Level1 = #{question1Level1}
     * @param question1Level1
     * @return
     */
    @Override
    public List<ReviewPool> queryNotifyReviewPools(@Param("question1Level1") String question1Level1){
        return reviewPoolDao.queryNotifyReviewPools(question1Level1);
    }

    /**
     * 根据条件查询insertTime的最大时间
     * @param netOrderId
     * @param question1Level2
     * @return
     */
    @Override
    public String findMaxTime(@Param("netOrderId") String netOrderId,
                                                    @Param("question1Level2") String question1Level2){
        return reviewPoolDao.findMaxTime(netOrderId, question1Level2);
    }

    /**
     * 获取分页查询list数据
     * @return
     */
    @Override
    public List<ReviewPool> findPageList(@Param("pool") ReviewPool pool, @Param("pager") PagerInfo pager,
                                                                      @Param("startTime") String startTime,
                                                                                @Param("endTime") String endTime){
        return reviewPoolDao.findPageList(pool, pager, startTime, endTime);
    }

    /**
     * 查询工单状态时未处理的所有工单信息
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public List<ReviewPool> getReviewPoolByStatus(@Param("workStatus") String workStatus){
        return reviewPoolDao.getReviewPoolByStatus(workStatus);
    }


    @Override
    public List<ReviewPool> getReviewPoolByStatusAppeal(@Param("pool") ReviewPool pool){
        return reviewPoolDao.getReviewPoolByStatusAppeal(pool);
    }

    /**
     * 查询需要传给SQM的工单
     * @param sqmStatus
     * @return
     * @throws Exception
     */
    @Override
    public List<ReviewPool> getSqmOrderByStatus(@Param("sqmStatus") String sqmStatus){
        return reviewPoolDao.getSqmOrderByStatus(sqmStatus);
    }

    @Override
    public List<ReviewPool> getSqmOrderByJudgeStatus(){
        return reviewPoolDao.getSqmOrderByJudgeStatus();
    }
    /**
     * 根据网单号与责任位查询最后一个插入的工单信息
     * @param netOrderId
     * @param question1Level2
     * @return
     */
    @Override
    public ReviewPool findFinallyAdd(@Param("netOrderId") String netOrderId,
                                                          @Param("question1Level2") String question1Level2,
                                                          @Param("question1Level3") String question1Level3){
        return reviewPoolDao.findFinallyAdd(netOrderId, question1Level2, question1Level3);
    }


    //查找同样责任味,同样网单号,新产生工单的上条工单信息
    @Override
    public ReviewPool findFinallyAddOne(@Param("netOrderId") String netOrderId,
                                                             @Param("question1Level2") String question1Level2,
                                                             @Param("question1Level3") String question1Level3){
        return reviewPoolDao.findFinallyAddOne(netOrderId, question1Level2, question1Level3);
    }

    /**
     * 根据一级责任位，取得未关闭的工单
     * @param questionLevel
     * @param remark5
     * @return
     * @throws Exception
     */
    @Override
    public List<ReviewPool> getPoolByQuestion1Level1(@Param("questionLevel") String questionLevel,
                                                          @Param("question1Level2") List<String> question1Level2,
                                                                                    @Param("remark5") String remark5){
        return reviewPoolDao.getPoolByQuestion1Level1(questionLevel, question1Level2, remark5);
    }
    /*物流送装类自动关单*/

    @Override
    public List<ReviewPool> getPoolByQuestion1Level1s(){
        return reviewPoolDao.getPoolByQuestion1Level1s();
    }

    /**
     * 根据网单号获取所有工单信息
     * @param newOrderId    网单ID
     * @return
     */
    @Override
    public List<ReviewPool> getPoolByNetOrderId(@Param("netOrderId") String newOrderId){
        return reviewPoolDao.getPoolByNetOrderId(newOrderId);
    }

    /**
     * 责任位统计
     * @param item
     * @return
     */
    @Override
    public List<Map<String, Object>> dutyStatistic(@Param("map") Map<String, Object> item,
                                                            @Param("pager") PagerInfo pager){
        return reviewPoolDao.dutyStatistic(item, pager);
    }

    /**
     * 人员统计
     * @param item
     * @return
     */
    @Override
    public List<Map<String, Object>> personnelStatistic(@Param("map") Map<String, Object> item,
                                                                    @Param("pager") PagerInfo pager){
        return reviewPoolDao.personnelStatistic(item, pager);
    }

    /**
     * 人员统计总数量
     * @param item
     */
    @Override
    public int findPersonnelStatisticCount(@Param("map") Map<String, Object> item){
        return reviewPoolDao.findPersonnelStatisticCount(item);
    }

    /**
     * 责任位统计总数量
     * @param item
     */
    @Override
    public int findDutyStatisticCount(@Param("map") Map<String, Object> item){
        return reviewPoolDao.findDutyStatisticCount(item);
    }

    /**\
     * 获取咨询类工单中当天的咨询数量
     * @param netOrderId
     * @return
     */
    @Override
    public int qryWdzxCount(@Param("netOrderId") String netOrderId){
        return reviewPoolDao.qryWdzxCount(netOrderId);
    }

    /**
     * 通过网单号与责任位获取对应信息(不包含最终结果状态的)
     * @param netOrderId
     * @param question1Level2
     * @return
     */
    @Override
    public ReviewPool findFinallyAddStateNoFinally(@Param("netOrderId") String netOrderId,
                                                    @Param("question1Level2") String question1Level2,
                                                    @Param("question1Level3") String question1Level3){
        return reviewPoolDao.findFinallyAddStateNoFinally(netOrderId, question1Level2, question1Level3);
    }

    @Override
    public int selectdelete(String id){
        return reviewPoolDao.selectdelete(id);
    }

    /**
     * 工单完成率定时邮件推送
     * @param monthStartTime
     * @return
     */
    @Override
    public List<Map<String, Object>> sendMailDutyStatistic(@Param("monthStartTime") String monthStartTime){
        return reviewPoolDao.sendMailDutyStatistic(monthStartTime);
    }

    /**
     * 获取全部数据 数据迁移用
     * @return
     */
    @Override
    public List<ReviewPool> getPage(@Param("pager") PagerInfo pager, @Param("map") Map<String, Object> item){
        return reviewPoolDao.getPage(pager, item);
    }

    /**
     * 数据迁移用: 获取执行过的数据量或没执行过的数据量
     * @param map
     * @return
     */
    @Override
    public Integer findPerformNum(@Param("map") Map<String, Object> map){
        return reviewPoolDao.findPerformNum(map);
    }

    @Override
    public List<ReviewPool> getHPOrderByStatusAndTO(@Param("sqmStatus") String sqmStatus){
        return reviewPoolDao.getHPOrderByStatusAndTO(sqmStatus);
    }

    @Override
    public List<ReviewPool> getHPOrderBySqmStatusAndTO(){
        return reviewPoolDao.getHPOrderBySqmStatusAndTO();
    }

    @Override
    public String findCodeByRegions(@Param("regionId") String regionId){
        return reviewPoolDao.findCodeByRegions(regionId);
    }

    @Override
    public void updateProductCode(@Param("reviewId") String reviewId,
                           @Param("productCode") String productCode){
        reviewPoolDao.updateProductCode(reviewId, productCode);
    }

    /**
     * 根据工单号添加对应的HP订单号
     */
    @Override
    public void addHpOrderId(String reviewId, String hpOrderId){
        reviewPoolDao.addHpOrderId(reviewId, hpOrderId);

    }


}
