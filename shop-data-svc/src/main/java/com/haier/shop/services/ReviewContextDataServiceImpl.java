package com.haier.shop.services;

import com.alibaba.dubbo.common.json.JSON;
import com.haier.common.BusinessException;
import com.haier.common.util.JsonUtil;
import com.haier.shop.dao.workorder.ReviewContextDao;
import com.haier.shop.dao.workorder.ReviewPoolDao;
import com.haier.shop.dao.workorder.WoReviewLogDao;
import com.haier.shop.dto.LogBean;
import com.haier.shop.model.ReviewContext;
import com.haier.shop.model.ReviewPool;
import com.haier.shop.service.ReviewContextDataService;
import com.haier.shop.service.ReviewWorkOrderDataService;
import com.haier.shop.util.ReviewConstants;
import com.haier.shop.util.TimeUtil;
import com.haier.shop.util.WebUtils;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by xupeng on 18/4/26.
 */
@Service
public class ReviewContextDataServiceImpl implements ReviewContextDataService {
  private static Logger log             = LogManager.getLogger(ReviewContextDataServiceImpl.class);
  //        private static Logger log             = LogManager.getLogger(ReviewContextModel.class);
  private static int TODAY = 1;
  private static int YESTERDAY = 2;
  private static int BEFORE = 3;

  private static long NOT_REPEAT_TIME = 7200000;
  /**
   * 读取的Dao
   */
  @Resource
  private ReviewContextDao reviewContextDao;
  @Resource
  private ReviewPoolDao reviewPoolDao;
  @Resource
  private WoReviewLogDao woReviewLogDao;

  @Resource
  private ReviewSmsQueueModel reviewSmsQueueModel;

  @Resource
  private ReviewWorkOrderDataService reviewWorkOrderDataService;

  @Value("${workorder.urlToHP}")
  private String urlToHP;
  @Value("${workorder.urlToSQM}")
  private String urlToSQM;
/*


        @Resource
        private ReviewLogModel        reviewLogModel;
        @Resource
        private ReviewTimedJobsModel reviewTimedJobsModel;*/

  /**
   * 根绝评论表id查询评论信息表信息
   *
   * @param reviewId 评论表id
   */
  public List<ReviewContext> getReviewContextByReviewId(String reviewId) {
    return reviewContextDao.getReviewContextByReviewId(reviewId);
  }

  /**
   * 添加评论信息 同时修改工单表最新评论
   * @param reviewContext
   * @throws Exception
   */

  /**
   * 给HP推送反馈结果
   */
  public void updateRequireToHp(ReviewContext reviewContext) {
    String time = "";
    try {
      time = System.currentTimeMillis() + "";
    } catch (Exception e) {
      e.printStackTrace();
    }
    //组织HP要求的参数格式
    Map paraMap = new HashMap();
    paraMap.put("wo_id", reviewContext.getReviewid());
    paraMap.put("require_desc", reviewContext.getContext());
    paraMap.put("method", "rrs.hcsp.shungunag.updaterequire");
    paraMap.put("timestamp", time);
    paraMap.put("access_token", "ZDZlZDVlNDItYmE3MC00YjI1LTgwZGItMzNjZGYxZmIwZDU0");
    try {
      String result = WebUtils.doPost(urlToHP, paraMap);
      Map<String, Object> paramMap = new HashMap<String, Object>();
      try {
        paramMap = JSON.parse(result, Map.class);
      } catch (com.alibaba.dubbo.common.json.ParseException e) {
        e.printStackTrace();
      }
      String status = paramMap.get("status").toString();
      if (!"ok".equals(status)) {
        log.error("反馈提交失败，失败原因：" + paramMap.get("msg").toString() + ",reviewId为" + reviewContext
            .getReviewid());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  /**
   * 修改评论信息
   */
  public void updateReviewContext(ReviewContext reviewContext) {
    reviewContextDao.updateReviewContext(reviewContext);
  }

  /**
   * 根据主键id删除信息
   */
  public void delReviewContextById(ReviewContext reviewContext) {
    reviewContextDao.delReviewContextById(reviewContext);
  }

  /**
   * 获得满足条件的总条数
   */
  public ReviewContext findReviewContextById(ReviewContext reviewContext) {
    return reviewContextDao.findReviewContextById(reviewContext);
  }

  /**
   * 根绝评论表id查询评论信息表信息条数
   */
  public int getCountByReviewId(String reviewId) {
    return reviewContextDao.getCountByReviewId(reviewId);
  }

  /**
   * 是否需要发送短信
   */
  public void send(ReviewContext reviewContext) {
    //是否发送短信逻辑
    String reviewId = reviewContext.getReviewid();
    String addTime = reviewContext.getAddtime();
    String addUser = reviewContext.getAdduser();
    List<ReviewContext> contexts = getReviewContextByReviewId(reviewId);
    //根据工单ID查询对应工单
    ReviewPool pool = new ReviewPool();
    pool.setId(reviewId);
    ReviewPool oldWorkOrder = reviewPoolDao.findReviewById(pool);
    //获取用户咨询次数+1
    int count = reviewWorkOrderDataService.findFeedBackNum(oldWorkOrder) + 1;
    if (contexts != null && contexts.size() > 0) {
      String finallyAddTime = contexts.get(0).getAddtime();
      //最后添加时间LONG
      long maxTime = TimeUtil.timeStrTOlong(finallyAddTime);
      //当前时间LONG
      long xzTime = TimeUtil.timeStrTOlong(addTime);
      //获取最后添加时间的状态 1:今天 2:昨天 3:以前 0:错误
      int day = TimeUtil.formatDateTime(finallyAddTime);
      //如果是今天
      if (day == TODAY) {
        //时间差
        long d = xzTime - maxTime;

        if (d > NOT_REPEAT_TIME) {
          //发送短信
          reviewSmsQueueModel.sendTextMessages(ReviewConstants.SMSTYPE.TYPE_2,
              oldWorkOrder.getId(), oldWorkOrder.getNetOrderId(), oldWorkOrder.getProductName(),
              oldWorkOrder.getRemark4(), oldWorkOrder.getBuyer(),
              oldWorkOrder.getBuyerMobile(), oldWorkOrder.getQuestion1Level2(),
              oldWorkOrder.getAppealCount1(), count, reviewContext.getContext(),
              ReviewConstants.SEND_PEOPLE.DUTY_PEOPLE);
        }
        //如果最后添加时间是昨天
      } else if (day == YESTERDAY) {
        //获取昨天17点的long值
        long yesterdayNight = TimeUtil.getAppointTime(finallyAddTime, 17);
        //获取今天9点的long值
        long todayMorning = TimeUtil.getAppointTime(addTime, 9);
        //如果昨天最后添加时间long值晚于昨天17点的long值 ,将最后添加的时间定为昨天17点的long值
        long timeDifference = (yesterdayNight - maxTime) <= 0 ? (yesterdayNight - maxTime)
            : 0;
        //(昨天17点的long值  - 昨天最后添加时间的Long值) + (当前时间的long值  - 今天9点的long值)
        long d = timeDifference + (xzTime - todayMorning);
        if (d > NOT_REPEAT_TIME) {
          //发送短信
          reviewSmsQueueModel.sendTextMessages(ReviewConstants.SMSTYPE.TYPE_2,
              oldWorkOrder.getId(), oldWorkOrder.getNetOrderId(), oldWorkOrder.getProductName(),
              oldWorkOrder.getRemark4(), oldWorkOrder.getBuyer(),
              oldWorkOrder.getBuyerMobile(), oldWorkOrder.getQuestion1Level2(),
              oldWorkOrder.getAppealCount1(), count, reviewContext.getContext(),
              ReviewConstants.SEND_PEOPLE.DUTY_PEOPLE);
        }
        //如果最后添加时间是以前
      } else if (day == BEFORE) {
        //发送短信
        reviewSmsQueueModel.sendTextMessages(ReviewConstants.SMSTYPE.TYPE_2,
            oldWorkOrder.getId(), oldWorkOrder.getNetOrderId(), oldWorkOrder.getProductName(),
            oldWorkOrder.getRemark4(), oldWorkOrder.getBuyer(),
            oldWorkOrder.getBuyerMobile(), oldWorkOrder.getQuestion1Level2(),
            oldWorkOrder.getAppealCount1(), count, reviewContext.getContext(),
            ReviewConstants.SEND_PEOPLE.DUTY_PEOPLE);
        //否则
      } else {
        throw new BusinessException("时间转换失败！");
      }
    }
  }

  /**
   * 通过网单号与责任位获取反馈信息
   */
  public Map<String, Object> findReviewContextByNetOrderIdAndQuestion1Level2(String netOrderId,
      String question1Level2, String question1Level3) {
    ReviewPool reviewPool = reviewPoolDao.findFinallyAddStateNoFinally(netOrderId,
        question1Level2, question1Level3);
    Map<String, Object> map = new HashMap<>();
    if (reviewPool != null) {
      List<ReviewContext> reviewContext = reviewContextDao
          .getReviewContextByReviewId(reviewPool.getId());
      map.put("list", reviewContext);
      map.put("askFlg", reviewPool.getComplaintFlg());
      return map;
    }
    return null;
  }

  public void addReviewContext(ReviewContext reviewContext) {
    String context = reviewContext.getContext();
    if (context != null && !"".equals(context.trim())) {
      ReviewPool reviewPool = new ReviewPool();
      reviewPool.setId(reviewContext.getReviewid());
      reviewPool.setContext(reviewContext.getContext());
      reviewPool.setRemark3(reviewContext.getAdduser());
      //大于2小时发送短信
      send(reviewContext);
      //咨询次数+1
      ReviewPool old = reviewPoolDao.findReviewById(reviewPool);
      int count = reviewWorkOrderDataService.findFeedBackNum(old);
      List<ReviewContext> contextList = reviewContextDao
          .getReviewContextByReviewId(reviewPool.getId());
      reviewPool.setFeedBackCount(contextList.size() + 1);
      //更新
      reviewPoolDao.updWorkOrder(reviewPool);
      //TODO 插入反馈内容log
      LogBean addContextlog = new LogBean(reviewContext.getAdduser(),
          ReviewConstants.MKNAME.MKNAME_3, ReviewConstants.LOG.LOG_12,
          "工单号:" + reviewContext.getReviewid() + " " + "反馈信息ID:" + reviewContext.getId()
              + " " + reviewContext.getContext(), reviewContext.getId());
      woReviewLogDao.insertSelective(addContextlog);
      reviewContextDao.addReviewContext(reviewContext);
      ReviewPool pool = new ReviewPool();
      pool.setId(reviewContext.getReviewid());
      pool = reviewPoolDao.findReviewById(pool);
      if (pool.getWorkOrderTo().equals("HP")) {
        this.updateRequireToHp(reviewContext);
      }
      if (pool.getWorkOrderTo().equals("SQM")) {
        this.sendSqmContext(reviewPool);
      }
    }
  }


  public boolean  sendSqmContext(ReviewPool reviewPool) {
    Map contentMap = new HashMap();

    contentMap.put("clientId", reviewPool.getId());
    contentMap.put("contents", reviewPool.getContext());


//                paraMap.put("content", contentMap);
    URI uri = null;
    try {
      uri = new URIBuilder(urlToSQM)
          .setParameter("informationSource", "HAIERSC")
          .setParameter("content", JsonUtil.toJson(contentMap))
          .build();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    HttpClient httpClient = new DefaultHttpClient();
    HttpPost httpPost = new HttpPost(uri);

    httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");

    HttpResponse response = null;
    try {
      response = httpClient.execute(httpPost);
    } catch (IOException e) {
      e.printStackTrace();
    }
    HttpEntity returnEntity = response.getEntity();
    try {

      // TODO
      String result = EntityUtils.toString(returnEntity);
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap = JSON.parse(result, Map.class);
      if (paramMap != null) {
        String successFlag = paramMap.get("success").toString();
        if ("true" .equals(successFlag)) {
          return true;
        }
      }
    } catch (Exception e) {
      return false;

    }

    return true;
  }
}
