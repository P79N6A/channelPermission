package com.haier.shop.dao.workorder;

import com.haier.common.PagerInfo;
import com.haier.shop.model.ReviewPool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by xupeng on 18/4/26.
 */
@Mapper
public interface ReviewPoolDao {

    /**
     * 添加工单
     */
    void addReview(ReviewPool reviewPool);


    /**
     * 查询单是否符合传送SQM条件
     */
    int getTplCount(@Param("pool") ReviewPool reviewPool);


    List<ReviewPool> getReviewPoolForMobile(@Param("pool") ReviewPool pool);

    /**
     * 修改评论(审核状态)
     */
    void updReview(@Param("pool") ReviewPool pool);


    /**
     * 获取分页查询list数据
     */
    List<ReviewPool> findPageList(@Param("pool") ReviewPool pool, @Param("pager") PagerInfo pager,
        @Param("startTime") String startTime,
        @Param("endTime") String endTime);

    /**
     * 查询工单状态时未处理的所有工单信息
     */
    List<ReviewPool> getReviewPoolByStatus(@Param("workStatus") String workStatus);


    List<ReviewPool> getReviewPoolByStatusAppeal(@Param("pool") ReviewPool pool);

    /**
     * 查询需要传给SQM的工单
     */
    List<ReviewPool> getSqmOrderByStatus(@Param("sqmStatus") String sqmStatus);

    List<ReviewPool> getSqmOrderByJudgeStatus();

    /**
     * 根据网单号与责任位查询最后一个插入的工单信息
     */
    ReviewPool findFinallyAdd(@Param("netOrderId") String netOrderId,
        @Param("question1Level2") String question1Level2,
        @Param("question1Level3") String question1Level3);


    //查找同样责任味,同样网单号,新产生工单的上条工单信息
    ReviewPool findFinallyAddOne(@Param("netOrderId") String netOrderId,
        @Param("question1Level2") String question1Level2,
        @Param("question1Level3") String question1Level3
    );

    /**
     * 根据一级责任位，取得未关闭的工单
     */
    List<ReviewPool> getPoolByQuestion1Level1(@Param("questionLevel") String questionLevel,
        @Param("question1Level2") List<String> question1Level2,
        @Param("remark5") String remark5);
    /*物流送装类自动关单*/

    List<ReviewPool> getPoolByQuestion1Level1s();

    /**
     * 根据网单号获取所有工单信息
     *
     * @param newOrderId 网单ID
     */
    List<ReviewPool> getPoolByNetOrderId(@Param("netOrderId") String newOrderId);

    List<ReviewPool> getPoolByOrderId(@Param("orderId") String orderId);

    /**
     * 责任位统计
     */
    List<Map<String, Object>> dutyStatistic(@Param("map") Map<String, Object> item,
        @Param("pager") PagerInfo pager);

    /**
     * 人员统计
     */
    List<Map<String, Object>> personnelStatistic(@Param("map") Map<String, Object> item,
        @Param("pager") PagerInfo pager);

    /**
     * 人员统计总数量
     */
    int findPersonnelStatisticCount(@Param("map") Map<String, Object> item);

    /**
     * 责任位统计总数量
     */
    int findDutyStatisticCount(@Param("map") Map<String, Object> item);

    /**
     * \ 获取咨询类工单中当天的咨询数量
     */
    int qryWdzxCount(@Param("netOrderId") String netOrderId);

    /**
     * 通过网单号与责任位获取对应信息(不包含最终结果状态的)
     */
    ReviewPool findFinallyAddStateNoFinally(@Param("netOrderId") String netOrderId,
        @Param("question1Level2") String question1Level2,
        @Param("question1Level3") String question1Level3);

    int selectdelete(String id);

    /**
     * 工单完成率定时邮件推送
     */
    List<Map<String, Object>> sendMailDutyStatistic(@Param("monthStartTime") String monthStartTime);

    /**
     * 获取全部数据 数据迁移用
     */
    List<ReviewPool> getPage(@Param("pager") PagerInfo pager, @Param("map") Map<String, Object> item);

    /**
     * 数据迁移用: 获取执行过的数据量或没执行过的数据量
     */
    Integer findPerformNum(@Param("map") Map<String, Object> map);

    List<ReviewPool> getHPOrderByStatusAndTO(@Param("sqmStatus") String sqmStatus);

    List<ReviewPool> getHPOrderBySqmStatusAndTO();

    String findCodeByRegions(@Param("regionId") String regionId);

    /**
     * 添加工单
     */
    void addWorkOrder(@Param("pool") ReviewPool reviewPool);


    void updateReviewFroStoreId(String storeId);


    /**
     * 删除所有
     */
    void delAll();

    /**
     * 获得满足条件的总条数
     */
    int findPageCount(@Param("pool") ReviewPool pool, @Param("startTime") String startTime,
        @Param("endTime") String endTime);

    /**
     * 获得满足条件的总条数
     */
    int searchPageCount(@Param("pool") ReviewPool pool, @Param("searchStartTime") String startTime,
        @Param("searchEndTime") String endTime);

    /**
     * 根据id 获得详情
     */
    ReviewPool findReviewById(@Param("pool") ReviewPool pool);

    /**
     * 修改工单信息
     */
    void updWorkOrder(@Param("pool") ReviewPool pool);

    void updWorkOrderOne(@Param("pool") ReviewPool pool);

    int qryFeedBackCount(@Param("netOrderId") String netOrderId);

    List<ReviewPool> queryNotifyReviewPools(@Param("question1Level1") String question1Level1);

    String findMaxTime(@Param("netOrderId") String netOrderId,
        @Param("question1Level2") String question1Level2);

    /**
     * 更新全部工单 数据迁移用。
     */
    int updAll(List<ReviewPool> dataAll);

    /**
     * 数据迁移_计算所有反馈次数
     */
    int dataMigration();

    /**
     * 数据迁移_更新中间处理时间为第一次处理时间
     */
    int minAddtime();

    /**
     * 数据迁移_更新中间处理时间为最新处理时间
     */
    int maxAddtime();

    /**
     * 根据工单号添加对应的HP订单号
     */
    void addHpOrderId(@Param("reviewId") String reviewId, @Param("hpOrderId") String hpOrderId);

    /**
     *
     * @param reviewId
     * @param productCode
     */
    void updateProductCode(@Param("reviewId") String reviewId,
        @Param("productCode") String productCode);

    void updateSQMStatus(@Param("ids") String ids);


}
