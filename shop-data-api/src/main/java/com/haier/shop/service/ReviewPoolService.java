package com.haier.shop.service;

import com.haier.common.PagerInfo;
import com.haier.shop.model.ReviewPool;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by xupeng on 18/4/26.
 */
public interface ReviewPoolService {

    /**
     * 添加工单
     * @param reviewPool
     * @throws Exception
     */
    public void addReview(ReviewPool reviewPool);


    /**
     * 修改工单信息
     * @param pool
     */
    void updWorkOrder(@Param("pool") ReviewPool pool);


    /**
     * 删除所有
     * @throws
     */
    public void delAll();

    /**
     * 获得满足条件的总条数
     * @param pool
     * @return
     * @throws Exception
     */
    public int findPageCount(@Param("pool") ReviewPool pool, @Param("startTime") String startTime,
                             @Param("endTime") String endTime);

    /**
     * 获得满足条件的总条数
     * @param pool
     * @return
     * @throws Exception
     */
    public int searchPageCount(@Param("pool") ReviewPool pool, @Param("searchStartTime") String startTime,
                               @Param("searchEndTime") String endTime);
    /**
     * 查询单是否符合传送SQM条件
     * @param reviewPool
     * @throws Exception
     */
    public int getTplCount(@Param("pool") ReviewPool reviewPool);

    /**
     * 根据id 获得详情
     * @param pool
     * @return
     * @throws Exception
     */
    public ReviewPool findReviewById(@Param("pool") ReviewPool pool);




    public List<ReviewPool> getReviewPoolForMobile(@Param("pool") ReviewPool pool);

    /**
     * 修改评论(审核状态)
     * @param pool
     */
    public void updReview(@Param("pool") ReviewPool pool);

    /**
     * 查询netOrderId = #{netOrderId}的总条数
     * @param netOrderId
     * @return
     */
    public int qryFeedBackCount(@Param("netOrderId") String netOrderId);

    /**
     * 查询orderCome = 'SG' and workStatus = 0 and question1Level1 = #{question1Level1}
     * @param question1Level1
     * @return
     */
    public List<ReviewPool> queryNotifyReviewPools(@Param("question1Level1") String question1Level1);

    /**
     * 根据条件查询insertTime的最大时间
     * @param netOrderId
     * @param question1Level2
     * @return
     */
    public String findMaxTime(@Param("netOrderId") String netOrderId,
                              @Param("question1Level2") String question1Level2);

    /**
     * 获取分页查询list数据
     * @return
     */
    public List<ReviewPool> findPageList(@Param("pool") ReviewPool pool, @Param("pager") PagerInfo pager,
                                         @Param("startTime") String startTime,
                                         @Param("endTime") String endTime);

    /**
     * 查询工单状态时未处理的所有工单信息
     * @param
     * @return
     * @throws Exception
     */
    public List<ReviewPool> getReviewPoolByStatus(@Param("workStatus") String workStatus);


    public List<ReviewPool> getReviewPoolByStatusAppeal(@Param("pool") ReviewPool pool);

    /**
     * 查询需要传给SQM的工单
     * @param sqmStatus
     * @return
     * @throws Exception
     */
    public List<ReviewPool> getSqmOrderByStatus(@Param("sqmStatus") String sqmStatus);

    public List<ReviewPool> getSqmOrderByJudgeStatus();
    /**
     * 根据网单号与责任位查询最后一个插入的工单信息
     * @param netOrderId
     * @param question1Level2
     * @return
     */
    public ReviewPool findFinallyAdd(@Param("netOrderId") String netOrderId,
                                     @Param("question1Level2") String question1Level2,
                                     @Param("question1Level3") String question1Level3);


    //查找同样责任味,同样网单号,新产生工单的上条工单信息
    public ReviewPool findFinallyAddOne(@Param("netOrderId") String netOrderId,
                                        @Param("question1Level2") String question1Level2,
                                        @Param("question1Level3") String question1Level3);

    /**
     * 根据一级责任位，取得未关闭的工单
     * @param questionLevel
     * @param remark5
     * @return
     * @throws Exception
     */
    public List<ReviewPool> getPoolByQuestion1Level1(@Param("questionLevel") String questionLevel,
                                                     @Param("question1Level2") List<String> question1Level2,
                                                     @Param("remark5") String remark5);
    /*物流送装类自动关单*/

    public List<ReviewPool> getPoolByQuestion1Level1s();

    /**
     * 根据网单号获取所有工单信息
     * @param newOrderId    网单ID
     * @return
     */
    public List<ReviewPool> getPoolByNetOrderId(@Param("netOrderId") String newOrderId);

    /**
     * 责任位统计
     * @param item
     * @return
     */
    public List<Map<String, Object>> dutyStatistic(@Param("map") Map<String, Object> item,
                                                   @Param("pager") PagerInfo pager);

    /**
     * 人员统计
     * @param item
     * @return
     */
    public List<Map<String, Object>> personnelStatistic(@Param("map") Map<String, Object> item,
                                                        @Param("pager") PagerInfo pager);

    /**
     * 人员统计总数量
     * @param item
     */
    public int findPersonnelStatisticCount(@Param("map") Map<String, Object> item);

    /**
     * 责任位统计总数量
     * @param item
     */
    public int findDutyStatisticCount(@Param("map") Map<String, Object> item);

    /**\
     * 获取咨询类工单中当天的咨询数量
     * @param netOrderId
     * @return
     */
    public int qryWdzxCount(@Param("netOrderId") String netOrderId);

    /**
     * 通过网单号与责任位获取对应信息(不包含最终结果状态的)
     * @param netOrderId
     * @param question1Level2
     * @return
     */
    public ReviewPool findFinallyAddStateNoFinally(@Param("netOrderId") String netOrderId,
                                                   @Param("question1Level2") String question1Level2,
                                                   @Param("question1Level3") String question1Level3);

    public int selectdelete(String id);

    /**
     * 工单完成率定时邮件推送
     * @param monthStartTime
     * @return
     */
    public List<Map<String, Object>> sendMailDutyStatistic(@Param("monthStartTime") String monthStartTime);

    /**
     * 获取全部数据 数据迁移用
     * @return
     */
    public List<ReviewPool> getPage(@Param("pager") PagerInfo pager, @Param("map") Map<String, Object> item);

    /**
     * 数据迁移用: 获取执行过的数据量或没执行过的数据量
     * @param map
     * @return
     */
    public Integer findPerformNum(@Param("map") Map<String, Object> map);

    public List<ReviewPool> getHPOrderByStatusAndTO(@Param("sqmStatus") String sqmStatus);

    public List<ReviewPool> getHPOrderBySqmStatusAndTO();

    public String findCodeByRegions(@Param("regionId") String regionId);

    public void updateProductCode(@Param("reviewId") String reviewId,
                           @Param("productCode") String productCode);

    /**
     * 根据工单号添加对应的HP订单号
     */
    public void addHpOrderId(String reviewId, String hpOrderId);
}
