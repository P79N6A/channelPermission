package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.util.DateUtil;
import com.haier.shop.dao.shopread.OrdersReadDao;
import com.haier.shop.dao.workorder.ReviewContextDao;
import com.haier.shop.dao.workorder.ReviewImageDao;
import com.haier.shop.dao.workorder.ReviewMailQueueDao;
import com.haier.shop.dao.workorder.ReviewMiddleDao;
import com.haier.shop.dao.workorder.ReviewPoolDao;
import com.haier.shop.dao.workorder.WOUserDao;
import com.haier.shop.dao.workorder.WoDictionaryDao;
import com.haier.shop.dao.workorder.WoReviewContactsDao;
import com.haier.shop.dao.workorder.WoReviewErrorDataDao;
import com.haier.shop.dao.workorder.WoReviewLogDao;
import com.haier.shop.dto.LogBean;
import com.haier.shop.dto.ReviewContactsDto;
import com.haier.shop.dto.ThirdPartyLiabilityCondition;
import com.haier.shop.model.COrderBean;
import com.haier.shop.model.DutyStatistic;
import com.haier.shop.model.OrderBean;
import com.haier.shop.model.Orders;
import com.haier.shop.model.PersonnelStatistic;
import com.haier.shop.model.ReviewContext;
import com.haier.shop.model.ReviewDataDictionaryEntity;
import com.haier.shop.model.ReviewErrorData;
import com.haier.shop.model.ReviewFinalResult;
import com.haier.shop.model.ReviewImage;
import com.haier.shop.model.ReviewMailQueue;
import com.haier.shop.model.ReviewMiddle;
import com.haier.shop.model.ReviewPool;
import com.haier.shop.model.Reviewpoolfordhzx;
import com.haier.shop.model.WOUser;
import com.haier.shop.model.WorkOrderBean;
import com.haier.shop.service.ReviewWorkOrderDataService;
import com.haier.shop.util.ReviewConstants;
import com.haier.shop.util.TimeUtil;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 工单表操作
 *
 * @Filename: ReviewWorkOrderModel.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 */
@Service
public class ReviewWorkOrderDataServiceImpl implements ReviewWorkOrderDataService {

  private String idtmp = null;
  private String oldId = null;
  private int runCount = 0;
  @Value("${workorder.smssend}")
  private String smssend;
  @Value("${workorder.mailsend}")
  private String mailsend;
  private static String prefix="HWO";
  private static int TODAY = 1;
  private static int YESTERDAY = 2;
  private static int BEFORE = 3;
  private static long NOT_REPEAT_TIME = 7200000;
  /**
   * 新工单上诉次数
   */
  private static Logger log = LogManager.getLogger(ReviewWorkOrderDataServiceImpl.class);
  @Resource
  private ReviewPoolDao reviewPoolDao;
  @Resource
  private ReviewContextDao reviewContextDao;
  @Resource
  private ReviewMiddleDao reviewMiddleDao;
  @Resource
  private WoReviewLogDao woReviewLogDao;
  @Resource
  private OrdersReadDao ordersReadDao;
  @Resource
  private WOUserDao woUserDao;
  @Resource
  private ReviewMailQueueDao reviewMailQueueDao;
  @Resource
  private ReviewImageDao reviewImageDao;
  @Resource
  private ReviewSmsQueueModel reviewSmsQueueModel;
/*  @Resource
  private ReviewImageWriteDao reviewImageWriteDao;
  @Resource
  private ReviewImageReadDao reviewImageReadDao;

  @Resource
  private ReviewMailQueueModel reviewMailQueueModel;
  @Resource
  private ReviewContextModel reviewContextModel;*/

  @Resource
  private WoDictionaryDao woDictionaryDao;
  @Resource
  private WoReviewErrorDataDao woReviewErrorDataDao;
  @Resource
  private WoReviewContactsDao woReviewContactsDao;

  @Override
  public JSONObject findPageList(ReviewPool pool, PagerInfo pager, String startTime,
      String endTime) {
    JSONObject result = new JSONObject();
    result.put("rows", reviewPoolDao.findPageList(pool, pager, startTime, endTime));
    result.put("total", reviewPoolDao.findPageCount(pool, startTime, endTime));
    return result;
  }

  @Override
  public void updateReviewFroStoreId(String storeId) {
    reviewPoolDao.updateReviewFroStoreId(storeId);
  }

  @Override
  public List<ReviewPool> getReviewPoolForMobile(ReviewPool pool) {
    List<ReviewPool> list = reviewPoolDao.getReviewPoolForMobile(pool);
    StringBuilder sb = new StringBuilder();
    StringBuilder sb1 = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      ReviewPool r = list.get(i);
      List<ReviewContext> contextList = reviewContextDao.querForReviewId(r.getId());
      for (ReviewContext reviewcontext : contextList) {
        sb = sb.append(reviewcontext.getContext().replaceAll(" ", "")).append("   ")
            .append(reviewcontext.getAdduser()).append("   ").append(reviewcontext.getAddtime())
            .append("        ");
      }
      List<ReviewMiddle> middleList = reviewMiddleDao.getReviewMiddleByReviewIds(r.getId());
      for (ReviewMiddle reviewMiddle : middleList) {
        sb1 = sb1.append(reviewMiddle.getMiddleContext().replaceAll(" ", "")).append("   ")
            .append(reviewMiddle.getAdduser()).append("   ").append(reviewMiddle.getAddtime())
            .append("        ");

      }
      r.setContext(sb.toString());
      r.setBackContext2(sb1.toString());

    }

    return list;
  }

  /**
   * 修改工单（包括图片）
   */
  @Override
  public void updeWorkOrder(ReviewPool reviewPool, String[] imgArr, String[] imgNameArr,
      String userName)
      throws ParseException {
    ReviewPool oldReviewPool = reviewPoolDao.findReviewById(reviewPool);

    // 判断 是ZX中途转TS 还是本来就是投诉
    if (oldReviewPool.getWorkOrderTo().equals("SQM")
        && ("".equals(oldReviewPool.getComplaintPhone()) || null == oldReviewPool
        .getComplaintPhone())

        && (null != reviewPool.getComplaintPhone() && !"".equals(reviewPool.getComplaintPhone()))

        ) {

      reviewPool.setSqmType("TS");
      reviewPool.setSqmState("1");
      reviewPool.setSqmStatus("0");
      reviewPool.setDesideText("工单已转为投诉,向SQM推送中");
    } else {
      reviewPool.setSqmStatus(null);
      reviewPool.setSqmState(null);

    }

    String addTime = DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE);
    if (oldReviewPool == null) {
      throw new BusinessException("没有找到" + reviewPool.getId() + "工单，修改失败");
    } else {
      reviewPool.setInsertTime(oldReviewPool.getInsertTime());
    }
    // 旧责任位
    String oldDuty = oldReviewPool.getQuestion1Level2();
    // 新责任位
    String newDuty = reviewPool.getQuestion1Level2();
    // 判断责任位是否被更换
    if (oldDuty.equals(newDuty)) {
      // 中间结果添加
      if (!"".equals(reviewPool.getBackContext2().trim())) {
        List<ReviewMiddle> oldMiddles = reviewMiddleDao
            .getReviewMiddleByReviewId(reviewPool.getId());
        if (null == oldMiddles || 0 == oldMiddles.size()) {
          reviewPool.setPosition2(addTime);
        } else {
          reviewPool.setPosition2(oldMiddles.get(0).getAddtime());
        }
        // 中间结果添加时间
        reviewPool.setWorkOrderTime(DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE));
        // 工单更改为中间结果状态
        reviewPool.setWorkStatus(ReviewConstants.WORKSTATUS.MIDDLERESULT);
        // 中间结果添加人
        reviewPool.setRemark1(userName);
        // 如果旧工单状态是未处理状态
        if (oldReviewPool.getWorkStatus().equals(ReviewConstants.WORKSTATUS.UNTREATED)) {
          // 计算中间结果处理时效
          oneProcessCount(reviewPool, oldReviewPool);
        }

      }
      // 最终结果添加
      if (!"".equals(reviewPool.getBackContext3().trim())) {
        // 最终结果添加时间
        reviewPool.setPosition3(DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE));
        // 最终结果添加内容
        reviewPool.setWorkStatus(ReviewConstants.WORKSTATUS.FINALRESULT);
        // 最终结果添加人
        reviewPool.setRemark2(userName);
        // 设置订单关闭原因为正常关闭
        reviewPool.setCloseType(ReviewConstants.CLOSETYPE.CLOSETYPE_1);
        // 计算最终结果处理时效
        orderProcessCount(reviewPool, oldReviewPool);
        // TODO 插入最终结果log
        LogBean log = new LogBean(userName, ReviewConstants.MKNAME.MKNAME_3,
            ReviewConstants.LOG.LOG_14,
            "工单号:" + reviewPool.getId() + " " + reviewPool.getPosition3() + " " + reviewPool
                .getRemark2()
                + " " + reviewPool.getBackContext3(),
            reviewPool.getId());
        woReviewLogDao.insertSelective(log);
        ReviewFinalResult reviewFinalResult = new ReviewFinalResult();
        reviewFinalResult.setAddtime(addTime);
        reviewFinalResult.setId(UUID.randomUUID().toString());
        reviewFinalResult.setMiddleContext(reviewPool.getBackContext3());
        reviewFinalResult.setAdduser(userName);
        reviewFinalResult.setReviewid(reviewPool.getId());
        reviewMiddleDao.addReviewFinalResult(reviewFinalResult);
      }
      // 中间结果内容
      String backContext2 = reviewPool.getBackContext2();
      /////////////////////////////////////////////////////////////
      //中间结果字节数

      byte[] bytes4 = backContext2.getBytes();
      System.out.println("中间结果，字节数----：" + bytes4.length);

      // 添加中间结果
      if (backContext2 != null && !"".equals(backContext2.trim())) {
        ReviewMiddle reviewMiddle = new ReviewMiddle();
        reviewMiddle.setId(UUID.randomUUID().toString());
        reviewMiddle.setReviewid(reviewPool.getId());
        reviewMiddle.setAddtime(addTime);
        reviewMiddle.setMiddleContext(backContext2);
        reviewMiddle.setAdduser(userName);
        reviewMiddle.setResultType("0");
        reviewMiddleDao.addReviewMiddle(reviewMiddle);
        // TODO 插入中间结果log
        LogBean log = new LogBean(userName, ReviewConstants.MKNAME.MKNAME_3,
            ReviewConstants.LOG.LOG_13, "工单号:"
            + reviewMiddle.getReviewid() + " " + "中间结果ID:" + reviewMiddle.getId() + " "
            + backContext2,
            reviewMiddle.getId());
        woReviewLogDao.insertSelective(log);
      } else {
        // 为空串不替换
        reviewPool.setBackContext2(null);
      }
      // TODO 插入修改工单log
      LogBean updatelog = new LogBean(userName, ReviewConstants.MKNAME.MKNAME_3,
          ReviewConstants.LOG.LOG_11,
          "工单号:" + reviewPool.getId(), reviewPool.getId());
      woReviewLogDao.insertSelective(updatelog);
      reviewPool.setMiddleType("1");

      reviewPoolDao.updWorkOrder(reviewPool);
      if (null != imgArr && imgArr.length > 0) {
        addImage(reviewPool, imgArr, imgNameArr);
      }
    } else {// 如果责任位被更改
      reviewPool.setBackContext3(null);
      // 复制旧工单，重新生成新工单
      String netOrderId = oldReviewPool.getNetOrderId();
      String backContext3 = "";

      ReviewPool oldWorkOrder = reviewPoolDao.findFinallyAdd(netOrderId, newDuty, null);
      // 判断被更改的责任位下有没有重复工单
      if (oldWorkOrder != null) {
        // 判断是否是最终结果状态
        if (ReviewConstants.WORKSTATUS.FINALRESULT.equals(oldWorkOrder.getWorkStatus())) {
          // 如果有重复工单并且是最终结果状态,新工单号重新生成新工单ID
          this.idtmp = createHYDID();
          backContext3 = "责任人：" + userName + "将工单责任位更改由" + oldDuty + "更改为" + newDuty
              + "，责任位录入错误自动闭环，保存当前所有信息，关联新工单号为:" + this.idtmp;
        } else {
          // 如果有重复工单并且不是最终结果状态,新工单号指向当前重复的工单ID
          this.idtmp = oldWorkOrder.getId();
          backContext3 = "责任人：" + userName + "将工单责任位更改由" + oldDuty + "更改为" + newDuty
              + "，责任位录入错误自动闭环，保存当前所有信息，关联新工单号为:" + oldWorkOrder.getId();
        }

      } else {
        // 如果没有重复工单,新工单号重新生成新工单ID
        this.idtmp = createHYDID();
        backContext3 = "责任人：" + userName + "将工单责任位更改由" + oldDuty + "更改为" + newDuty
            + "，责任位录入错误自动闭环，保存当前所有信息，关联新工单号为:" + this.idtmp;
        // copyWorkOrder(reviewPool, oldReviewPool, addTime,
        // CLOSE_TYPE_CUSTOMER_SERVICE,
        // backContext3, id, imageFile);
      }
      this.oldId = oldReviewPool.getId();
      // 关闭旧工单
      closeOldWorkOrder(oldReviewPool, ReviewConstants.CLOSETYPE.CLOSETYPE_4, backContext3,
          userName);
      // 重新调用添加接口
      // TODO 插入更改责任位log
      LogBean log = new LogBean(userName, ReviewConstants.MKNAME.MKNAME_3,
          ReviewConstants.LOG.LOG_15,
          "工单号:" + this.oldId + " " + backContext3, this.oldId);
      woReviewLogDao.insertSelective(log);
      // 此处需要修改与此工单对应的图片
      addWorkOrder(reviewPool, imgArr, imgNameArr, userName);
    }

  }

  /**
   * 多图片
   */
  @Override
  public String addWorkOrder(ReviewPool reviewPool, String[] imgArr, String[] imgNameArr,
      String userName)
      throws ParseException {
    String rId = "";
    // 用来区分是修改逻辑中的添加还是正常逻辑中的添加
    if (reviewPool.getId() == null || "".equals(reviewPool.getId().trim())) {
      this.idtmp = null;
      this.oldId = null;
    }

    // 如果是咨询类网单 自动生成网单号
    if (reviewPool.getAskFlg() != null) {
      reviewPool.setNetOrderId(createWDZX());
    }
    // 获取当前时间
    String addTime = DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE);
    // 网单号
    String netOrderId = reviewPool.getNetOrderId();
    // 查询相同网单相同责任位最后一个插入的工单信息

    // 查询相同网单的总条数

    int sum = reviewPoolDao.qryFeedBackCount(netOrderId);
    // System.out.println(sum);

    // 判断是否存在相同网单号
    if (sum > 0) {
      ReviewPool oldWorkOrder = reviewPoolDao
          .findFinallyAdd(netOrderId, reviewPool.getQuestion1Level2(),
              reviewPool.getQuestion1Level3());
      // 判断相同网单相同责任位是否有重复数据
      if (oldWorkOrder != null) {
        reviewPoolDao.findFinallyAddOne(netOrderId, reviewPool.getQuestion1Level2(),
            reviewPool.getQuestion1Level3());

        // 获取工单状态 0未处理、1已确认、2已反馈(中间结果)、3已闭环
        String workStatus = oldWorkOrder.getWorkStatus();
        // 如果工单状态是最终结果
        if (ReviewConstants.WORKSTATUS.FINALRESULT.equals(workStatus)) {
          String id = "";
          // 判断是否是修改责任位之后造成的虚假封单
          if (this.idtmp != null && !"".equals(this.idtmp)) {
            id = this.idtmp;
          } else {
            id = createHYDID();
          }
          // 虚假封单逻辑
          // copyWorkOrder(reviewPool, oldWorkOrder, addTime,
          // CLOSE_TYPE_FALSE, null, id,
          // imageFile);
          // 关闭旧工单
          closeOldWorkOrder(oldWorkOrder, ReviewConstants.CLOSETYPE.CLOSETYPE_3,
              "\r\n(虚假封单)新工单号:" + id,
              userName);
          // 创建新工单

          foundNewWorkOrder(reviewPool, oldWorkOrder, addTime, id, imgArr, imgNameArr, userName);

          // 上诉次数
          Integer appealCount =
              oldWorkOrder.getAppealCount1() != null ? oldWorkOrder.getAppealCount1() : 0;
          // 咨询次数
          int count = findFeedBackNum(reviewPool);
          // 如果不是责任录入错误造成的虚假封单 咨询次数需+1
          if (!(this.idtmp != null && !"".equals(this.idtmp))) {
            count = count + 1;
          }

          this
              .mailTextMessages(ReviewConstants.SMSTYPE.TYPE_5, oldWorkOrder.getId(),
                  oldWorkOrder.getNetOrderId(), oldWorkOrder.getProductName(),
                  oldWorkOrder.getRemark4(),
                  oldWorkOrder.getBuyer(), oldWorkOrder.getBuyerMobile(),
                  oldWorkOrder.getQuestion1Level2(),
                  appealCount + 1, count, "该工单存在虚假封单,原工单号为:" + oldWorkOrder.getId(), true);
          // 如果是其他
        } else {
          addContext(reviewPool, oldWorkOrder, imgArr, imgNameArr, userName);
        }

      } else {
        int count = findFeedBackNum(reviewPool);
        if (reviewPool.getAskFlg() == null) {
          // 相同网单相同责任位没有重复数据,直接执行添加操作

          if (reviewPool.getContext() != null && !"".equals(reviewPool.getContext())) {
            count = count + 1;
          }
          setReviePoolProperty(reviewPool);
        }
        reviewPool.setWorkCreateTime(addTime);
        rId = normalAdd(reviewPool, addTime, count, imgArr, imgNameArr, userName);
      }
    } else {
      if (reviewPool.getAskFlg() == null) {
        setReviePoolProperty(reviewPool);
      }
      reviewPool.setWorkCreateTime(addTime);
      // 没有相同网单,直接执行添加操作
      rId = normalAdd(reviewPool, addTime, 1, imgArr, imgNameArr, userName);
    }
    return rId;
  }


  private void setReviePoolProperty(ReviewPool reviewPool) {
    ThirdPartyLiabilityCondition third = woDictionaryDao
        .findInfoFromThird(reviewPool.getQuestion1Level1(), reviewPool.getQuestion1Level2(),
            reviewPool.getQuestion1Level3());
    if (third == null) {
      System.out.println("普通的单子");
      reviewPool.setSqmStatus("4");
      reviewPool.setWorkOrderTo("");
    } else {
      if (reviewPool.getCenterType().endsWith("WA") && null != reviewPool.getRemark5()
          && (reviewPool.getRemark5().equals("微店") || reviewPool.getRemark5().equals("海尔创客_PC端")
          || reviewPool.getRemark5().equals("商城订单-分销渠道")
          || reviewPool.getRemark5().equals("商城订单") || reviewPool.getRemark5().equals("移动商城")
          || reviewPool.getRemark5().equals("商城订单-大学生项目订单")
          || reviewPool.getRemark5().equals("海尔创客_移动端")
          || reviewPool.getRemark5().equals("移动社交"))
          && third.getCanal().equals("TY") && third.getThirdPartyType().equals("SQM")) {
        System.out.println("SQM的单子");
        reviewPool.setSqmStatus("0");
        reviewPool.setDesideText("向SQM推送中");
        reviewPool.setWorkOrderTo("SQM");
        reviewPool.setSqmType("ZX");
        if (null != reviewPool.getComplaintPhone() && !""
            .equals(reviewPool.getComplaintPhone())) {
          reviewPool.setSqmType("TS");
          reviewPool.setSqmState("1");
        }
      } else if (reviewPool.getCenterType().endsWith("WA") && third.getCanal().equals("RS")
          && third.getThirdPartyType().equals("HP")) {
        System.out.println("顺逛HP的单子");
        reviewPool.setSqmStatus("0");
        reviewPool.setDesideText("向HP推送中");
        reviewPool.setWorkOrderTo("HP");
      } else if (third.getThirdPartyType().equals("HP") && third.getCanal().equals("TB")) {
        System.out.println("天猫HP的单子");
        reviewPool.setSqmStatus("0");
        reviewPool.setDesideText("向HP推送中");
        reviewPool.setWorkOrderTo("HP");
      } else {
        System.out.println("普通的单子");
        reviewPool.setSqmStatus("4");
        reviewPool.setWorkOrderTo("");
      }
    }
  }


  /*
   * 查看该工单符合不符合传送SQM状态
   */
  @Override
  public int getTplCount(ReviewPool reviewPool) {
    int sum = reviewPoolDao.getTplCount(reviewPool);
    return sum;
  }

  /**
   * 多图片
   */
  private String normalAdd(ReviewPool reviewPool, String addTime, int feedBackCount,
      String[] imgArr,
      String[] imgNameArr, String userName) {
    // 判断是否是客服录入错误导致的添加工单
    String id = null;
    if (this.idtmp != null && !"".equals(this.idtmp)) {
      reviewPool.setId(this.idtmp);
      // 查询旧评论信息
      List<ReviewContext> oldContexts = reviewContextDao.getReviewContextByReviewId(this.oldId);
      // 查询旧中间信息
      List<ReviewMiddle> oldMiddles = reviewMiddleDao
          .getReviewMiddleByReviewId(reviewPool.getId());
      // 查询旧图片
      List<ReviewImage> imgs = reviewImageDao.findReviewImgsById(this.oldId);

      // 如果有旧图片并且没有用户新传图片,将旧图片添加到新生成的工单中
      // if (!(imgArr != null && !"".equals(imageFile.trim()))) {
      // if (oldImage != null) {
      // oldImage.setWorkorderid(this.idtmp);
      // reviewImageWriteDao.insertSelective(oldImage);
      // }
      // }
      ////////////////// 如果有旧图片并且没有用户新传图片,将旧图片添加到新生成的工单中
      if (null != imgArr) {
        if (imgArr.length == 0 && (null != imgs && imgs.size() > 0)) {
          for (ReviewImage img : imgs) {
            img.setWorkorderid(this.idtmp);
            reviewImageDao.insertSelective(img);
          }
        }
      }

      if (oldContexts != null && oldContexts.size() > 0) {
        // 添加最新评论信息
        ReviewContext newCt = reviewContextDao.findNewTimeCountByReviewId(this.oldId);
        if (newCt != null) {
          reviewPool.setContext(newCt.getContext());
        }
        // 循环旧评论
        for (ReviewContext oldContext : oldContexts) {
          // 生成新的评论ID
          oldContext.setId(UUID.randomUUID().toString());
          // 设置成新的工单号
          oldContext.setReviewid(this.idtmp);
        }
      }
      if (oldMiddles != null && oldMiddles.size() > 0) {
        reviewPool.setBackContext2(oldMiddles.get(0).getMiddleContext());// 中间结果
        reviewPool.setPosition2(oldMiddles.get(0).getAddtime());// 中间结果添加时间
        reviewPool.setRemark1(oldMiddles.get(0).getAdduser());// 中间结果添加人
        for (ReviewMiddle oldMiddle : oldMiddles) {
          // 生成新的中间结果ID
          oldMiddle.setId(UUID.randomUUID().toString());
          // 设置成新的工单号
          oldMiddle.setReviewid(this.idtmp);
        }
      }
      ReviewContext kfContext = new ReviewContext();
      kfContext.setId(UUID.randomUUID().toString());// 生成新评论ID
      kfContext.setReviewid(this.idtmp);// 设置新评论ID
      kfContext.setAdduser(ReviewConstants.SYS.SYSTEM);
      // 设置添加时间为当前时间
      kfContext.setAddtime(addTime);
      // 设置新评论内容
      kfContext.setContext("该工单存在责任位变更,原工单号为:" + this.oldId + " 变更责任人：" + userName);
      oldContexts.add(kfContext);
      if (oldContexts != null && oldContexts.size() > 0) {
        // 添加评论信息

        reviewContextDao.addReviewContextList(oldContexts);
        for (ReviewContext oldContext : oldContexts) {
          // TODO 插入反馈内容log
          LogBean addContextlog = new LogBean(oldContext.getAdduser(),
              ReviewConstants.MKNAME.MKNAME_3,
              ReviewConstants.LOG.LOG_12,
              "工单号:" + oldContext.getReviewid() + " " + oldContext.getContext(),
              oldContext.getId());
          woReviewLogDao.insertSelective(addContextlog);
        }
      }
      if (oldMiddles != null && oldMiddles.size() > 0) {
        // 添加中间结果信息
        for (int i = 0; i < oldMiddles.size(); i++) {
          oldMiddles.get(i).setResultType("0");
        }
        reviewMiddleDao.addReviewMiddleList(oldMiddles);
        for (ReviewMiddle oldMiddle : oldMiddles) {
          // TODO 插入中间结果log
          LogBean addContextlog = new LogBean(oldMiddle.getAdduser(),
              ReviewConstants.MKNAME.MKNAME_3,
              ReviewConstants.LOG.LOG_13, "工单号:" + oldMiddle.getReviewid() + " " + "中间结果ID:"
              + oldMiddle.getId() + " " + oldMiddle.getMiddleContext(),
              oldMiddle.getId());
          woReviewLogDao.insertSelective(addContextlog);
        }
      }

    } else {
      // 生成HYD工单号
      id = createHYDID();
      reviewPool.setId(id);
    }
    // 设置添加时间
    reviewPool.setCreateTime(addTime);
    reviewPool.setInsertTime(addTime);
    reviewPool.setWorkCreateTime(addTime);
    // 新工单默认未处理
    reviewPool.setWorkStatus(ReviewConstants.WORKSTATUS.UNTREATED);
    // 用户咨询次数设置为总条数+1
    reviewPool.setFeedBackCount(1);
    ReviewContext reviewContext = new ReviewContext();
    reviewContext.setId(UUID.randomUUID().toString());// 生成评论ID
    reviewContext.setReviewid(id);
    // 设置添加时间为当前时间
    reviewContext.setAddtime(addTime);
    reviewContext.setContext(reviewPool.getContext());
    reviewContext.setAdduser(userName);
    // 添加创建人
    reviewPool.setRemark3(userName);

    ////////////////////////////////////////////////////////////////////// 添加工单信息
    reviewPoolDao.addWorkOrder(reviewPool);
    // 如果是物流类工单 售后类工单,社会化业务，退换机，OTO类，小电库类，服务类，送装类
    // 第一次创建发送短信
    if (ReviewConstants.QUESTION1LEVEL1.DUTY_1_1.equals(reviewPool.getQuestion1Level1())
        || "天猫渠道-物流类".equals(reviewPool.getQuestion1Level1())
        || "京东-物流类".equals(reviewPool.getQuestion1Level1()) || "顺逛商城-售后类"
        .equals(reviewPool.getQuestion1Level1())
        || "天猫渠道-3W售后类".equals(reviewPool.getQuestion1Level1())
        || "京东-售后类".equals(reviewPool.getQuestion1Level1()) || "顺逛商城-社会化业务"
        .equals(reviewPool.getQuestion1Level1())
        || "顺逛商城-O2O类".equals(reviewPool.getQuestion1Level1())

        ) {
      // 发送短信
      // 上诉次数
      Integer appealCount = reviewPool.getAppealCount1() != null ? reviewPool.getAppealCount1() : 0;
      reviewSmsQueueModel.sendTextMessages(ReviewConstants.SMSTYPE.TYPE_1, reviewPool.getId(),
          reviewPool.getNetOrderId(), reviewPool.getProductName(), reviewPool.getRemark4(),
          reviewPool.getBuyer(), reviewPool.getBuyerMobile(), reviewPool.getQuestion1Level2(),
          appealCount,
          reviewPool.getFeedBackCount(), reviewPool.getContext(),
          ReviewConstants.SEND_PEOPLE.DUTY_PEOPLE);
    }

    // TODO 插入添加工单log
    LogBean addlog = new LogBean(userName, ReviewConstants.MKNAME.MKNAME_3,
        ReviewConstants.LOG.LOG_10,
        "工单号:" + reviewPool.getId(), reviewPool.getId());
    woReviewLogDao.insertSelective(addlog);
    // TODO 插入反馈内容log
    LogBean addContextlog = new LogBean(userName, ReviewConstants.MKNAME.MKNAME_3,
        ReviewConstants.LOG.LOG_12,
        "工单号:" + reviewContext.getReviewid() + " " + reviewContext.getContext(),
        reviewContext.getId());
    woReviewLogDao.insertSelective(addContextlog);
    // 添加评论信息
    reviewContextDao.addReviewContext(reviewContext);
    // 如果有图片
    if (null != imgArr && imgArr.length > 0) {
      // 添加图片
      addImage(reviewPool, imgArr, imgNameArr);
    }
    System.out.println("id为" + reviewPool.getId());
    return reviewPool.getId();
  }

  /**
   * 删除所有
   */
  public void delAll() {
    reviewPoolDao.delAll();
  }

  /**
   * 获得满足条件的总条数
   */
  public int searchPageCount(ReviewPool pool, String startTime, String endTime) {
    return reviewPoolDao.searchPageCount(pool, startTime, endTime);
  }

  /**
   * 根据id 获得详情
   */
  public ReviewPool findReviewById(ReviewPool pool) {
    return reviewPoolDao.findReviewById(pool);
  }

  /**
   * 查询netOrderId = #{netOrderId}的总条数
   */
  public int qryFeedBackCount(String netOrderId) {
    return reviewPoolDao.qryFeedBackCount(netOrderId);
  }

  /**
   * 查询orderCome = 'SG' and workStatus = 0 and question1Level1 = #{question1Level1}
   */
  public List<ReviewPool> queryNotifyReviewPools(String question1Level1) {
    return reviewPoolDao.queryNotifyReviewPools(question1Level1);
  }

  /**
   * 根据条件查询insertTime的最大时间
   */
  public String findMaxTime(String netOrderId, String question1Level2) {
    return reviewPoolDao.findMaxTime(netOrderId, question1Level2);
  }

  private static String createHYDID() {
    SimpleDateFormat sdDateTime = new SimpleDateFormat("yyMMddHHmmssSSS");
    String date = prefix;
    try {
      date += sdDateTime.format(new Date());
    } catch (Exception e) {
      return date;
    }
    return date;
  }

  /**
   * 计算一次处理时效
   */
  private void oneProcessCount(ReviewPool reviewPool, ReviewPool oldreviewPool) {
    Date oneProcessDate = null;
    long oneProcessCount;
    String oneProcessTime = null;
    String insertTime = oldreviewPool.getInsertTime();// 生成工单时间
    String position2 = reviewPool.getPosition2();// 中间结果时间
    Date startDate = TimeUtil.timeStrTOdate(insertTime);
    oneProcessDate = TimeUtil.timeStrTOdate(position2);
    // 中间结果时间 - 创建时间 - 之间所有17点～9点时间
    oneProcessCount = oneProcessDate.getTime() - startDate.getTime()
        - (57600000 * TimeUtil.getIntervalDays(startDate, oneProcessDate));
    // 获取经历的时间
    oneProcessTime = TimeUtil.convertTimeByMillisecods(oneProcessCount);
    reviewPool.setOneProcessTime(oneProcessTime);
  }

  /**
   * 计算工单处理时效
   */
  private void orderProcessCount(ReviewPool reviewPool, ReviewPool oldreviewPool) {
    Date orderProcessDate = null;
    long orderProcessCount;
    String orderProcessTime = null;
    String insertTime = oldreviewPool.getInsertTime();// 生成工单时间
    String position3 = reviewPool.getPosition3();// 最终结果时间
    Date startDate = TimeUtil.timeStrTOdate(insertTime);
    orderProcessDate = TimeUtil.timeStrTOdate(position3);
    if (position3.substring(0, 10).equals(insertTime.substring(0, 10))) {// 创建时间和结果时间为同一天
      orderProcessCount = orderProcessDate.getTime() - startDate.getTime();// 间隔时间
    } else {// 不是同一天
      // 中间结果时间 - 创建时间 - 之间所有17点～9点时间
      orderProcessCount = orderProcessDate.getTime() - startDate.getTime()
          - (57600000 * TimeUtil.getIntervalDays(startDate, orderProcessDate));
    }
    // 获取经历的时间
    orderProcessTime = TimeUtil.convertTimeByMillisecods(orderProcessCount);
    reviewPool.setOrderProcessTime(orderProcessTime);
  }

  /**
   * 添加图片
   *
   * @param reviewPool
   * @param imageFile
   */
//	private void addImage(ReviewPool reviewPool, String[] imgArr) {
//		for (String img : imgArr) {
//			ReviewImage reviewImage = new ReviewImage();
//			reviewImage.setWorkorderid(reviewPool.getId());
//			reviewImage.setInformation(img);
//			reviewImage.setCreatetime(new Date());
//			reviewImage.setCreateuser(reviewPool.getRemark3());
//			// reviewImage.setName(name);
//			// 判断有没有该图片
//			int count = reviewImageReadDao.selectCount(img);
//			if (count <= 0) {
//				reviewImageWriteDao.insertSelective(reviewImage);
//			}
//		}
//	}

  /**
   * 多图片
   */
  private void addImage(ReviewPool reviewPool, String[] imgArr, String[] imgNameArr) {
    for (int i = 0; i < imgArr.length; i++) {
      ReviewImage reviewImage = new ReviewImage();
      reviewImage.setWorkorderid(reviewPool.getId());
      reviewImage.setInformation(imgArr[i]);
      reviewImage.setCreatetime(new Date());
      reviewImage.setCreateuser(reviewPool.getRemark3());
      reviewImage.setName(imgNameArr[i]);
      // 判断有没有该图片
      int count = reviewImageDao.selectCount(reviewPool.getId(),imgArr[i]);
      if (count <= 0) {
        reviewImageDao.insertSelective(reviewImage);
      }
    }
  }

  /**
   * 相同网单责任位添加反馈信息
   */
  private void addContext(ReviewPool reviewPool, ReviewPool oldWorkOrder, String[] imgArr,
      String[] imgNameArr, String userName) {
    String addTime = DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE);
    // 判断是否是客服录入错误导致的添加工单
    if (this.idtmp != null && !"".equals(this.idtmp)) {
      reviewPool.setId(this.idtmp);
      // 查询旧评论信息
      List<ReviewContext> oldContexts = reviewContextDao.getReviewContextByReviewId(this.oldId);
      // 查询新评论信息
      List<ReviewContext> newContexts = reviewContextDao.getReviewContextByReviewId(this.idtmp);
      // 查询旧中间信息
      List<ReviewMiddle> oldMiddles = reviewMiddleDao
          .getReviewMiddleByReviewId(reviewPool.getId());
      List<ReviewImage> oldImgs = reviewImageDao.findReviewImgsById(this.oldId);
      List<ReviewImage> newImgs = reviewImageDao.findReviewImgsById(this.idtmp);
      if (null != imgArr && imgArr.length < 0) {
        if (null != oldImgs && oldImgs.size() > 0) {
          if (null != newImgs && newImgs.size() > 0) {
            Long oldImgTime = TimeUtil.timeStrTOlong(
                DateUtil.format(oldImgs.get(0).getCreatetime(), ReviewConstants.TIME.FORMAT_DATE));
            Long newImgTime = TimeUtil.timeStrTOlong(
                DateUtil.format(newImgs.get(0).getCreatetime(), ReviewConstants.TIME.FORMAT_DATE));
            if (oldImgTime > newImgTime) {// 判断旧图片添加时间是否大于新图片添加时间
              oldImgs.get(0).setWorkorderid(this.idtmp);
              reviewImageDao.updateByPrimaryKeySelective(oldImgs.get(0));// 更新图片
            }
          } else {
            oldImgs.get(0).setWorkorderid(this.idtmp);
            reviewImageDao.insertSelective(oldImgs.get(0));
          }
        }
      }

      if (oldContexts != null && oldContexts.size() > 0) {
        ReviewContext oldCt = reviewContextDao
            .findNewTimeCountByReviewId(this.oldId);// 获取旧ID的最新评论
        if (oldCt != null) {// 如果不为空
          if (newContexts != null && newContexts.size() > 0) {// 判断是否有新评论
            ReviewContext newCt = reviewContextDao
                .findNewTimeCountByReviewId(this.idtmp);// 获取新Id的最新评论
            if (newCt != null) {// 判断是否有新评论
              Long newTime = TimeUtil.timeStrTOlong(newCt.getAddtime());
              Long oldTime = TimeUtil.timeStrTOlong(oldCt.getAddtime());
              if (oldTime > newTime) {
                // 添加最新评论信息
                reviewPool.setContext(oldCt.getContext());
              }
            } else {
              // 添加最新评论信息
              reviewPool.setContext(oldCt.getContext());
            }
          } else {
            // 添加最新评论信息
            reviewPool.setContext(oldCt.getContext());
          }
        }
        // 循环旧评论
        for (ReviewContext oldContext : oldContexts) {
          // 生成新的评论ID
          oldContext.setId(UUID.randomUUID().toString());
          // 设置成新的工单号
          oldContext.setReviewid(this.idtmp);
        }
      }
      if (oldMiddles != null && oldMiddles.size() > 0) {
        for (ReviewMiddle oldMiddle : oldMiddles) {
          // 生成新的中间结果ID
          oldMiddle.setId(UUID.randomUUID().toString());
          // 设置成新的工单号
          oldMiddle.setReviewid(this.idtmp);
        }
      }

      ReviewContext kfContext = new ReviewContext();
      kfContext.setId(UUID.randomUUID().toString());// 生成新评论ID
      kfContext.setReviewid(this.idtmp);// 设置新评论ID
      kfContext.setAdduser(ReviewConstants.SYS.SYSTEM);
      // 设置添加时间为当前时间
      kfContext.setAddtime(addTime);
      // 设置新评论内容
      kfContext.setContext("该工单存在责任位变更,原工单号为:" + this.oldId + " 变更责任人：" + userName);
      oldContexts.add(kfContext);
      if (oldContexts != null && oldContexts.size() > 0) {
        // 添加评论信息
        reviewContextDao.addReviewContextList(oldContexts);
        for (ReviewContext oldContext : oldContexts) {
          // TODO 插入反馈内容log
          LogBean addContextlog = new LogBean(oldContext.getAdduser(),
              ReviewConstants.MKNAME.MKNAME_3,
              ReviewConstants.LOG.LOG_12,
              "工单号:" + oldContext.getReviewid() + " " + oldContext.getContext(),
              oldContext.getId());
          woReviewLogDao.insertSelective(addContextlog);
        }
      }
      if (oldMiddles != null && oldMiddles.size() > 0) {
        // 添加中间结果信息
        for (int i = 0; i < oldMiddles.size(); i++) {
          oldMiddles.get(i).setResultType("0");
        }
        reviewMiddleDao.addReviewMiddleList(oldMiddles);
        for (ReviewMiddle oldMiddle : oldMiddles) {
          // TODO 插入中间结果log
          LogBean addContextlog = new LogBean(oldMiddle.getAdduser(),
              ReviewConstants.MKNAME.MKNAME_3,
              ReviewConstants.LOG.LOG_13, "工单号:" + oldMiddle.getReviewid() + " " + "中间结果ID:"
              + oldMiddle.getId() + " " + oldMiddle.getMiddleContext(),
              oldMiddle.getId());
          woReviewLogDao.insertSelective(addContextlog);
        }
      }
      ReviewPool rPool = new ReviewPool();
      if (reviewPool.getContext() != null && !"".equals(reviewPool.getContext().trim())) {
        rPool.setId(this.idtmp);
        rPool.setContext(reviewPool.getContext());
        // TODO 插入修改工单log
        // LogBean updatelog = new LogBean(userName,
        // ReviewConstants.MKNAME.MKNAME_3,
        // ReviewConstants.LOG.LOG_11, "工单号:" + rPool.getId() + "
        // 更新评论为最新评论:"
        // + rPool.getContext(), rPool.getId());
        // woReviewLogDao.insertSelective(updatelog);
      }
      // 判断新工单与旧工单的创建时间哪个更早
      Long newPoolTime = TimeUtil.timeStrTOlong(reviewPool.getInsertTime());
      Long oldPoolTime = TimeUtil.timeStrTOlong(oldWorkOrder.getInsertTime());
      if (newPoolTime < oldPoolTime) {
        rPool.setId(this.idtmp);
        // 更新创建时间为旧的工单的创建时间
        rPool.setInsertTime(reviewPool.getInsertTime());
        // TODO 插入修改工单log
        // LogBean updatelog = new LogBean(userName,
        // ReviewConstants.MKNAME.MKNAME_3,
        // ReviewConstants.LOG.LOG_11, "工单号:" + rPool.getId()
        // + " 责任更换合并工单,更新创建时间为旧的工单的创建时间，原创建时间:"
        // + oldWorkOrder.getInsertTime() + " 新创建时间:"
        // + reviewPool.getInsertTime(), rPool.getId());
        // woReviewLogDao.insertSelective(updatelog);
        reviewPoolDao.updWorkOrder(rPool);
      } else {
        // 将原工单信息替换成最新的工单内容
        reviewPool.setRemark3(oldWorkOrder.getRemark3());// 还是原创建人
        reviewPool.setInsertTime(oldWorkOrder.getInsertTime());// 还是原创建时间
        reviewPoolDao.updWorkOrder(reviewPool);
        // LogBean updatelog = new LogBean(userName,
        // ReviewConstants.MKNAME.MKNAME_3,
        // ReviewConstants.LOG.LOG_11, "工单号:" + reviewPool.getId()
        // + " 责任更换合并工单,更新原存在工单信息为最新工单内容", reviewPool.getId());
        // woReviewLogDao.insertSelective(updatelog);
      }

    } else {
      reviewPool.setId(oldWorkOrder.getId());
      reviewPool.setRemark3(userName);
      // 咨询次数+1
      ReviewPool old = reviewPoolDao.findReviewById(reviewPool);
      int count = findFeedBackNum(old);
      reviewPool.setFeedBackCount(count + 1);
      reviewPoolDao.updWorkOrder(reviewPool);

      // 将新添加的内容保存到原网单号上 调用添加反馈
      ReviewContext reviewContext = new ReviewContext();
      reviewContext.setId(UUID.randomUUID().toString());
      reviewContext.setReviewid(oldWorkOrder.getId());
      reviewContext.setAddtime(addTime);
      reviewContext.setAdduser(userName);
      reviewContext.setContext(reviewPool.getContext());
      // 大于2小时发送短信
      this.send(reviewContext);
      reviewContextDao.addReviewContext(reviewContext);
      // TODO 插入反馈内容log
      LogBean addContextlog = new LogBean(reviewContext.getAdduser(),
          ReviewConstants.MKNAME.MKNAME_3,
          ReviewConstants.LOG.LOG_12,
          "工单号:" + reviewContext.getReviewid() + " " + reviewContext.getContext(),
          reviewContext.getId());
      woReviewLogDao.insertSelective(addContextlog);

      // TODO 插入修改工单log
      // LogBean updatelog = new LogBean(userName,
      // ReviewConstants.MKNAME.MKNAME_3,
      // ReviewConstants.LOG.LOG_11, "工单号:" + reviewPool.getId() + "
      // 添加相同网单相同责任工单,更新最新工单内容",
      // reviewPool.getId());
      // woReviewLogDao.insertSelective(updatelog);
    }
    // 如果有图片
    if (null != imgArr && imgArr.length > 0) {
      // 添加图片
      addImage(reviewPool, imgArr, imgNameArr);
    }
  }

  /**
   * 关闭旧工单
   *
   * @param oldWorkOrder 要关闭的旧工单
   * @param closeType 工单关闭原因
   * @param message 最终结果追加内容
   * @param closeUser 关闭人员
   */
  private void closeOldWorkOrder(ReviewPool oldWorkOrder, String closeType, String message,
      String closeUser) {
    String addTime = DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE);
    // 原工单状态改为最终结果
    oldWorkOrder.setWorkStatus(ReviewConstants.WORKSTATUS.FINALRESULT);
    if (oldWorkOrder.getBackContext3() != null) {
      // 原工单最终结果内容写固定内容
      oldWorkOrder.setBackContext3(oldWorkOrder.getBackContext3() + message);
    } else {
      // 原工单最终结果内容写固定内容
      oldWorkOrder.setBackContext3(message);

    }

    // 判断关闭原因是否是客服录入错误,如果是则最终结果添加人为系统
    if (ReviewConstants.CLOSETYPE.CLOSETYPE_4.equals(closeType)) {
      oldWorkOrder.setRemark2(ReviewConstants.SYS.SYSTEM); // 最终结果添加人
      // 原工单最终结果操作时间写为当前时间
      oldWorkOrder.setPosition3(addTime);
      // 计算置为最终结果后的工单处理时效
      orderProcessCount(oldWorkOrder, oldWorkOrder);
    }
    // 订单关闭原因设置为指定原因
    oldWorkOrder.setCloseType(closeType);

    // TODO 插入修改工单log
    // String closeTypeStr = "";
    // if (closeType.equals(ReviewConstants.CLOSETYPE.CLOSETYPE_3)) {
    // closeTypeStr = "虚假封单";
    // } else if (closeType.equals(ReviewConstants.CLOSETYPE.CLOSETYPE_4)) {
    // closeTypeStr = "客服原因";
    // }
    // LogBean updatelog = new LogBean(closeUser,
    // ReviewConstants.MKNAME.MKNAME_3,
    // ReviewConstants.LOG.LOG_11, "工单号:" + oldWorkOrder.getId() + "
    // 关闭旧工单操作,工单关闭原因:"
    // + closeTypeStr + " 最终结果追加内容：" + message,
    // oldWorkOrder.getId());
    // woReviewLogDao.insertSelective(updatelog);
    // TODO 最终结果log
    LogBean lastlog = new LogBean(ReviewConstants.SYS.SYSTEM, ReviewConstants.MKNAME.MKNAME_3,
        ReviewConstants.LOG.LOG_14,
        "工单号:" + oldWorkOrder.getId() + " " + oldWorkOrder.getPosition3() + " "
            + oldWorkOrder.getRemark2() + " " + oldWorkOrder.getBackContext3(),
        oldWorkOrder.getId());
    woReviewLogDao.insertSelective(lastlog);
    // 更新旧工单
    reviewPoolDao.updWorkOrder(oldWorkOrder);
  }

  /**
   * 多图片
   */
  private void foundNewWorkOrder(ReviewPool reviewPool, ReviewPool oldWorkOrder, String addTime,
      String id,
      String[] imgArr, String[] imgNameArr, String userName) throws ParseException {

    setReviePoolProperty(reviewPool);

    // 新工单默认未处理
    reviewPool.setWorkStatus(ReviewConstants.WORKSTATUS.UNTREATED + "");
    // String id = createHYDID(); //生成HYD订单号
    reviewPool.setId(id);// 设置新工单ID
    // 设置新工单添加时间
    reviewPool.setInsertTime(addTime);
    reviewPool.setWorkCreateTime(addTime);
    int count = 1;

    // 设置新工单的上诉次数加1
    reviewPool.setAppealCount1(oldWorkOrder.getAppealCount1() + 1);
    // 添加创建人
    reviewPool.setRemark3(userName);
    // 清空中间结果
    reviewPool.setBackContext2(null);
    // 清空中间结果添加人
    reviewPool.setRemark1(null);
    // 清空中间结果添加时间
    reviewPool.setPosition2(null);
    // 清空最终结果添加人
    reviewPool.setRemark1(null);
    // 清空最终结果添加时间
    reviewPool.setPosition3(null);
    // 清空最终结果
    reviewPool.setBackContext3(null);

    if (null != imgArr && imgArr.length > 0) {
      addImage(reviewPool, imgArr, imgNameArr);
    }
    List<ReviewContext> oldContexts = new ArrayList<ReviewContext>();
    String context = reviewPool.getContext();
    if (context != null && !"".equals(context.trim())) {
      ReviewContext reviewContext = new ReviewContext();
      reviewContext.setId(UUID.randomUUID().toString());// 生成新评论ID
      reviewContext.setReviewid(id);// 设置新评论ID
      // 设置添加时间为当前时间
      reviewContext.setAddtime(addTime);
      reviewContext.setAdduser(userName);
      // 设置新评论内容
      reviewContext.setContext(reviewPool.getContext());
      oldContexts.add(reviewContext);
    }
    ReviewContext oldIdContext = new ReviewContext();
    oldIdContext.setId(UUID.randomUUID().toString());// 生成新评论ID
    oldIdContext.setReviewid(id);// 设置新评论ID
    // 设置添加时间为当前时间
    oldIdContext.setAdduser(ReviewConstants.SYS.SYSTEM);
    oldIdContext.setAddtime(addTime);
    // 设置新评论内容
    oldIdContext.setContext("该工单存在虚假封单 原工单号为:" + oldWorkOrder.getId());

    // 判断是否是客服录入错误导致的添加工单
    if (this.idtmp != null && !"".equals(this.idtmp)) {
      // 查询旧评论信息
      List<ReviewContext> oldContexts2 = reviewContextDao
          .getReviewContextByReviewId(this.oldId);
      // 查询旧中间信息
      List<ReviewMiddle> oldMiddles = reviewMiddleDao.getReviewMiddleByReviewId(this.oldId);
      // 查询旧图片
      List<ReviewImage> imgs = reviewImageDao.findReviewImgsById(this.oldId);
      // 如果有旧图片并且没有用户新传图片,将旧图片添加到新生成的工单中
      // if (!(imageFile != null && !"".equals(imageFile.trim()))) {
      // if (oldImage != null && imageFile == null) {
      // oldImage.setWorkorderid(this.idtmp);
      // reviewImageWriteDao.insertSelective(oldImage);
      // }
      // }
      if (imgArr.length == 0 && (null != imgs && imgs.size() > 0)) {
        for (ReviewImage img : imgs) {
          img.setWorkorderid(this.idtmp);
          reviewImageDao.insertSelective(img);
        }
      }

      if (oldContexts2 != null && oldContexts2.size() > 0) {
        // 添加最新评论信息
        ReviewContext newCt = reviewContextDao.findNewTimeCountByReviewId(this.oldId);
        if (newCt != null) {
          reviewPool.setContext(newCt.getContext());
        }
        // 循环旧评论
        for (ReviewContext oldContext : oldContexts2) {
          // 生成新的评论ID
          oldContext.setId(UUID.randomUUID().toString());
          // 设置成新的工单号
          oldContext.setReviewid(this.idtmp);
        }
      }
      if (oldMiddles != null && oldMiddles.size() > 0) {
        reviewPool.setBackContext2(oldMiddles.get(0).getMiddleContext());// 中间结果
        reviewPool.setPosition2(oldMiddles.get(0).getAddtime());// 中间结果添加时间
        reviewPool.setRemark1(oldMiddles.get(0).getAdduser());// 中间结果添加人
        for (ReviewMiddle oldMiddle : oldMiddles) {
          // 生成新的中间结果ID
          oldMiddle.setId(UUID.randomUUID().toString());
          // 设置成新的工单号
          oldMiddle.setReviewid(this.idtmp);
        }
      }

      ReviewContext kfContext = new ReviewContext();
      kfContext.setId(UUID.randomUUID().toString());// 生成新评论ID
      kfContext.setReviewid(id);// 设置新评论ID
      kfContext.setAdduser(ReviewConstants.SYS.SYSTEM);
      // 设置添加时间为当前时间
      kfContext.setAddtime(addTime);
      // 设置新评论内容
      kfContext.setContext("该工单存在责任位变更,原工单号为:" + this.oldId + " 变更责任人：" + userName);
      // 将客服录入错误的评论追加到评论中
      oldContexts.add(kfContext);
      oldContexts.addAll(oldContexts2);
      if (oldMiddles != null && oldMiddles.size() > 0) {
        // 添加中间结果信息
        for (int i = 0; i < oldMiddles.size(); i++) {
          oldMiddles.get(i).setResultType("0");
        }
        reviewMiddleDao.addReviewMiddleList(oldMiddles);
        for (ReviewMiddle oldMiddle : oldMiddles) {
          // TODO 插入中间结果log
          LogBean addContextlog = new LogBean(oldMiddle.getAdduser(),
              ReviewConstants.MKNAME.MKNAME_3,
              ReviewConstants.LOG.LOG_13, "工单号:" + oldMiddle.getReviewid() + " " + "中间结果ID:"
              + oldMiddle.getId() + " " + oldMiddle.getMiddleContext(),
              oldMiddle.getId());
          woReviewLogDao.insertSelective(addContextlog);
        }
      }
    } else {
      // 如果不是客服错误,咨询次数+1
      count = count + 1;
    }
    // 将新的评论追加到评论中
    oldContexts.add(oldIdContext);
    if (oldContexts != null && oldContexts.size() > 0) {
      // 添加评论信息
      reviewContextDao.addReviewContextList(oldContexts);
      for (ReviewContext oldContext : oldContexts) {
        // TODO 插入反馈内容log
        LogBean addContextlog = new LogBean(oldContext.getAdduser(),
            ReviewConstants.MKNAME.MKNAME_3,
            ReviewConstants.LOG.LOG_12,
            "工单号:" + oldContext.getReviewid() + " " + oldContext.getContext(),
            oldContext.getId());
        woReviewLogDao.insertSelective(addContextlog);
      }
    }
    reviewPool.setFeedBackCount(count);

    // 添加工单信息
    reviewPoolDao.addWorkOrder(reviewPool);
    // 如果是物流类工单,第一次创建发送短信
    // if
    // (ReviewConstants.QUESTION1LEVEL1.DUTY_1_1.equals(reviewPool.getQuestion1Level1()))
    // {
    // 发送短信
    // 上诉次数
    Integer appealCount = reviewPool.getAppealCount1() != null ? reviewPool.getAppealCount1() : 0;

    reviewSmsQueueModel.sendTextMessages(ReviewConstants.SMSTYPE.TYPE_1, reviewPool.getId(),
        reviewPool.getNetOrderId(), reviewPool.getProductName(), reviewPool.getRemark4(),
        reviewPool.getBuyer(),
        reviewPool.getBuyerMobile(), reviewPool.getQuestion1Level2(), appealCount,
        reviewPool.getFeedBackCount(), reviewPool.getContext(),
        ReviewConstants.SEND_PEOPLE.DUTY_PEOPLE);

    // 查询相同网单号相同责任人的上一条记录
    ReviewPool oldWorkOrderOne = reviewPoolDao.findFinallyAddOne(reviewPool.getNetOrderId(),
        reviewPool.getQuestion1Level2(), reviewPool.getQuestion1Level3());

    String done = oldWorkOrderOne.getPosition3();
    // 查出相同网单号相同责任人最新工单信息
    ReviewPool oldWorkOrderlest = reviewPoolDao.findFinallyAdd(reviewPool.getNetOrderId(),
        reviewPool.getQuestion1Level2(), reviewPool.getQuestion1Level3());
    // 最新相同网单号相同责任人添加时间
    String d = oldWorkOrderlest.getInsertTime();
    if (reviewPool.getQuestion1Level1().equals("顺逛商城-物流类") || reviewPool.getQuestion1Level1()
        .equals("天猫渠道-物流类")
        || reviewPool.getQuestion1Level1().equals("京东-物流类") || reviewPool.getQuestion1Level1()
        .equals("顺逛商城-售后类")
        || reviewPool.getQuestion1Level1().equals("天猫渠道-3W售后类")
        || reviewPool.getQuestion1Level1().equals("京东-售后类")) {
      long num = 0;
      // 判断是否相差5天 参数1为 上一网单关闭时间 参数2为 新网单添加时间
      num = this
          .JiSuanDate(done.substring(0, 10).replace("-", ""), d.substring(0, 10).replace("-", ""));
      // 如果小于等于5天的时候 就要发送短信 其他类型的虚假网单 业务逻辑不变
      if (num <= 5) {
        reviewSmsQueueModel.sendTextMessages(ReviewConstants.SMSTYPE.TYPE_5, oldWorkOrder.getId(),
            oldWorkOrder.getNetOrderId(), oldWorkOrder.getProductName(), oldWorkOrder.getRemark4(),
            oldWorkOrder.getBuyer(), oldWorkOrder.getBuyerMobile(),
            oldWorkOrder.getQuestion1Level2(),
            appealCount + 1, count, "该工单存在虚假封单,原工单号为:" + oldWorkOrder.getId(),
            ReviewConstants.SEND_PEOPLE.MANAGERUSER_1_OR_2);
      }

    } else {
      // 如果网单不是 物流类 和 售后类 就正常 发送短信
      reviewSmsQueueModel.sendTextMessages(ReviewConstants.SMSTYPE.TYPE_1, reviewPool.getId(),
          reviewPool.getNetOrderId(), reviewPool.getProductName(), reviewPool.getRemark4(),
          reviewPool.getBuyer(), reviewPool.getBuyerMobile(), reviewPool.getQuestion1Level2(),
          appealCount,
          reviewPool.getFeedBackCount(), reviewPool.getContext(),
          ReviewConstants.SEND_PEOPLE.DUTY_PEOPLE);
    }

    // }

    String storeId = reviewPool.getStoreId();
    if (storeId != null && !"".equals(storeId) && "0".equals(storeId)) {
      // TODO 调用根据storeId获取手机号接口。
      String phoneNo = "18724730609";
      boolean isMobileNo = checkIfMobileNo(phoneNo);
      if (isMobileNo) {
        appealCount = reviewPool.getAppealCount1() != null ? reviewPool.getAppealCount1() : 0;
        if (ReviewConstants.QUESTION1LEVEL1.DUTY_1_1.equals(reviewPool.getQuestion1Level1())) {
          reviewSmsQueueModel.sendMessageToStore(ReviewConstants.SMSTYPE.TYPE_1, reviewPool.getId(),
              reviewPool.getNetOrderId(), reviewPool.getProductName(), reviewPool.getRemark4(),
              reviewPool.getBuyer(), reviewPool.getBuyerMobile(), reviewPool.getQuestion1Level2(),
              appealCount, reviewPool.getFeedBackCount(), reviewPool.getContext(), phoneNo);
        }
        if (ReviewConstants.QUESTION1LEVEL1.DUTY_1_2.equals(reviewPool.getQuestion1Level1())) {
          reviewSmsQueueModel.sendMessageToStore(ReviewConstants.SMSTYPE.TYPE_7, reviewPool.getId(),
              reviewPool.getNetOrderId(), reviewPool.getProductName(), reviewPool.getRemark4(),
              reviewPool.getBuyer(), reviewPool.getBuyerMobile(), reviewPool.getQuestion1Level2(),
              appealCount, reviewPool.getFeedBackCount(), reviewPool.getContext(), phoneNo);
        }
        if (ReviewConstants.QUESTION1LEVEL1.DUTY_1_9.equals(reviewPool.getQuestion1Level1())) {
          reviewSmsQueueModel.sendMessageToStore(ReviewConstants.SMSTYPE.TYPE_8, reviewPool.getId(),
              reviewPool.getNetOrderId(), reviewPool.getProductName(), reviewPool.getRemark4(),
              reviewPool.getBuyer(), reviewPool.getBuyerMobile(), reviewPool.getQuestion1Level2(),
              appealCount, reviewPool.getFeedBackCount(), reviewPool.getContext(), phoneNo);
        }
      }
    }
    // TODO 插入添加工单log
    LogBean addlog = new LogBean(userName, ReviewConstants.MKNAME.MKNAME_3,
        ReviewConstants.LOG.LOG_10,
        "工单号:" + reviewPool.getId(), reviewPool.getId());
    woReviewLogDao.insertSelective(addlog);

  }

  private boolean checkIfMobileNo(String phoneNo) {
    String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
    Pattern p = Pattern.compile(regExp);
    Matcher m = p.matcher(phoneNo);
    return m.find();
  }

  /**
   * 获取反馈次数
   */
  @Override
  public int findFeedBackNum(ReviewPool reviewPool) {
    // 设置新工单咨询次数
    List<ReviewPool> poolList = reviewPoolDao.getPoolByNetOrderId(reviewPool.getNetOrderId());
    List<String> reviewIdList = new ArrayList<String>();
    if (poolList != null && poolList.size() > 0) {
      for (ReviewPool pool : poolList) {
        reviewIdList.add(pool.getId());
      }
    }
    int count = reviewContextDao.getCountByReviewIdList(reviewIdList);
    return count;
  }

  /**
   * 责任位统计
   */
  public List<DutyStatistic> dutyStatistic(Map<String, Object> item) {
    PagerInfo page = (PagerInfo) item.get("page");
    List<Map<String, Object>> data = reviewPoolDao.dutyStatistic(item, page);
    List<DutyStatistic> dutyStatisticList = new ArrayList<DutyStatistic>();
    for (Map<String, Object> map : data) {
      // 获取责任位
      String question1Level1 = "";
      String question1Level2 = "";
      // if (item.get("question1Level1") != null &&
      // !"".equals(item.get("question1Level1"))) {
      question1Level1 = (String) map.get("question1Level1");
      question1Level2 = (String) map.get("question1Level2");
      // } else {
      // question1Level1 = (String) map.get("question1Level1");
      // }
      // 获取状态 0：未处理 2：中间结果 3：最终结果
      String workStatus = (String) map.get("workStatus");
      // 数量
      String num = map.get("num") + "";
      List<String> workStatusList = Arrays.asList(workStatus.split(","));
      List<String> numList = Arrays.asList(num.split(","));
      DutyStatistic isD = new DutyStatistic(question1Level1, question1Level2);
      for (int i = 0; i < numList.size(); i++) {
        String workStatusF = workStatusList.get(i);
        String numF = numList.get(i);
        if (ReviewConstants.WORKSTATUS.UNTREATED.equals(workStatusF)) {
          isD.setUnsettledNum(numF);
        } else if (ReviewConstants.WORKSTATUS.MIDDLERESULT.equals(workStatusF)) {
          isD.setMiddleNum(numF);
        } else if (ReviewConstants.WORKSTATUS.FINALRESULT.equals(workStatusF)) {
          isD.setFinallyNum(numF);
        }
      }
      dutyStatisticList.add(isD);
      for (DutyStatistic dutyStatistic : dutyStatisticList) {
        int finallyNum = Integer
            .parseInt(dutyStatistic.getFinallyNum() != null ? dutyStatistic.getFinallyNum() : "0");
        int sum = Integer
            .parseInt(
                dutyStatistic.getUnsettledNum() != null ? dutyStatistic.getUnsettledNum() : "0")
            + Integer
            .parseInt(dutyStatistic.getMiddleNum() != null ? dutyStatistic.getMiddleNum() : "0")
            + finallyNum;
        String str = (double) finallyNum / (double) sum + "";
        dutyStatistic
            .setFinishPercent(String.format("%.1f", (Double.parseDouble(str) * 100)) + "%");
        if (dutyStatistic.getFinallyNum() == null) {
          dutyStatistic.setFinallyNum("0");
        }
        if (dutyStatistic.getMiddleNum() == null) {
          dutyStatistic.setMiddleNum("0");
        }
        if (dutyStatistic.getUnsettledNum() == null) {
          dutyStatistic.setUnsettledNum("0");
        }
      }
    }
    return dutyStatisticList;
  }

  /**
   * 人员统计
   */
  public List<PersonnelStatistic> personnelStatistic(Map<String, Object> item) {
    PagerInfo page = (PagerInfo) item.get("page");
    List<Map<String, Object>> data = reviewPoolDao.personnelStatistic(item, page);
    List<PersonnelStatistic> personnelStatistic = new ArrayList<PersonnelStatistic>();
    for (Map<String, Object> map : data) {
      // 获取人员
      String wangId = (String) map.get("wangId");
      // 获取工贸
      String company = (String) map.get("company");
      // 获取总上诉次数
      String appealCount2 = map.get("appealCount2") + "";
      // 获取状态 0：未处理 2：中间结果 3：最终结果
      String workStatus = (String) map.get("workStatus");
      // 数量
      String num = (String) map.get("num");
      List<String> workStatusList = Arrays.asList(workStatus.split(","));
      List<String> numList = Arrays.asList(num.split(","));
      PersonnelStatistic isP = new PersonnelStatistic(wangId, company, appealCount2);
      for (int i = 0; i < numList.size(); i++) {
        String workStatusF = workStatusList.get(i);
        String numF = numList.get(i);
        if (ReviewConstants.WORKSTATUS.UNTREATED.equals(workStatusF)) {
          isP.setUnsettledNum(numF);
        } else if (ReviewConstants.WORKSTATUS.MIDDLERESULT.equals(workStatusF)) {
          isP.setMiddleNum(numF);
        } else if (ReviewConstants.WORKSTATUS.FINALRESULT.equals(workStatusF)) {
          isP.setFinallyNum(numF);
        }
      }
      personnelStatistic.add(isP);
      for (PersonnelStatistic p : personnelStatistic) {
        int finallyNum = Integer.parseInt(p.getFinallyNum() != null ? p.getFinallyNum() : "0");
        int sum = Integer.parseInt(p.getUnsettledNum() != null ? p.getUnsettledNum() : "0")
            + Integer.parseInt(p.getMiddleNum() != null ? p.getMiddleNum() : "0") + finallyNum;
        String str = (double) finallyNum / (double) sum + "";
        p.setFinishPercent(String.format("%.1f", (Double.parseDouble(str) * 100)) + "%");
        if (p.getFinallyNum() == null) {
          p.setFinallyNum("0");
        }
        if (p.getMiddleNum() == null) {
          p.setMiddleNum("0");
        }
        if (p.getUnsettledNum() == null) {
          p.setUnsettledNum("0");
        }
      }
    }
    return personnelStatistic;
  }

  /**
   * 生成咨询类工单网单ID
   */
  private String createWDZX() {
    SimpleDateFormat sdDateTime = new SimpleDateFormat("yyyyMMdd");
    StringBuffer date = new StringBuffer();
    date.append("WDZX");
    try {
      date.append(sdDateTime.format(new Date()));
    } catch (Exception e) {
      return date.toString();
    }
    int sum = reviewPoolDao.qryWdzxCount(date.toString());
    sum++;
    String s = "";
    int len = (sum + "").length();
    // 不足四位
    if (len < 4) {
      int prefixNum = 4 - len;// 计算要补几个0
      // 前面补0
      for (int i = 0; i < prefixNum; i++) {
        s = "0" + s;
      }
    }
    date.append(s + sum);
    return date.toString();
  }

  /**
   * 根据网单号与责任位查询最后一个插入的工单信息(关闭状态的不显示)
   */
  ReviewPool findFinallyAdd(String netOrderId, String question1Level2) {
    return reviewPoolDao.findFinallyAddStateNoFinally(netOrderId, question1Level2, null);
  }

  public int findDutyStatisticCount(Map<String, Object> item) {
    return reviewPoolDao.findDutyStatisticCount(item);
  }

  public int findPersonnelStatisticCount(Map<String, Object> item) {
    return reviewPoolDao.findPersonnelStatisticCount(item);
  }

  /**
   * 通过订单号查询订单接口
   *
   * @param order 订单号
   */
  public WorkOrderBean gdOrderByOrder(String order) {
    return ordersReadDao.getOrderByOrderSn(order);
  }

  /**
   * 订单查询接口
   *
   * @param string 订单ID
   */
  public OrderBean gdOrderById(String string) {
    return null;
  }

  /**
   * 网单查询接口
   *
   * @param cOrderSn 网单号
   */
  public COrderBean gdQueryOrderProductsByCOrderSn(String cOrderSn) {
    return null;
  }

  public List<ReviewPool> getnetOrderFroOrderId(String orderId) {
    WorkOrderBean bean = ordersReadDao.getOrderByOrderSn(orderId);
    List<ReviewPool> list = new ArrayList<>();
    ReviewPool r = new ReviewPool();
    r.setNetOrderId(bean.getCorderSn());
    r.setSku(bean.getSku());
    r.setNextUserName(bean.getNextUserName());
    r.setProductName(bean.getProductName());
    list.add(r);
    return list;

  }


  /**
   * 数据迁移 更新:一次处理时效,最终处理时效,是否是咨询类,网单号主键ID,订单号主键Id,配送时效,人员ID,未处理上诉次数,咨询次数
   */
  public String dataTransfer(int pageFind, int rowsFind, Map<String, Object> mapDate) {
    runCount++;
    int sumCount = 0; // 计算总迁移数量
    int oneSumCount = 0; // 计算一次循环总迁移数量
    int oneErrorCount = 0; // 计算一次循环失败的迁移数量
    int oneJumpCount = 0; // 记录一次循环跳过的迁移数量
    int i = 0; // 记录循环次数
    ReviewContactsDto reviewContacts2 = new ReviewContactsDto();
    PagerInfo page = new PagerInfo(100, 1);
    StringBuffer sb = new StringBuffer();
    String type = (String) mapDate.get("type");
    if (type != null) {
      sb.append(" type:" + type);
    }
    String insertTimeFind = (String) mapDate.get("insertTime");
    if (insertTimeFind != null) {
      sb.append(" insertTime:" + insertTimeFind);
    }
    String lastInsertTime = (String) mapDate.get("lastInsertTime");
    if (lastInsertTime != null) {
      sb.append(" lastInsertTime:" + lastInsertTime);
    }
    List idList = (List) mapDate.get("id");
    if (idList != null) {
      sb.append(" idNum:" + idList.size());
    }
    List<ReviewContactsDto> data = woReviewContactsDao.findPageList(reviewContacts2, page);
    logError(
        "第" + runCount + "次数据迁移开始 配置信息: 每次迁移" + pageFind + "条" + " 从第" + rowsFind + "条开始" + "附加参数("
            + sb + ")");
    ReviewDataDictionaryEntity record = new ReviewDataDictionaryEntity();
    record.setValueSetId("order_source");
    List<ReviewDataDictionaryEntity> data_data = woDictionaryDao
        .selectBySetId(record);
    Long start = System.currentTimeMillis();
    // reviewPoolDao.dataMigration();//计算反馈次数
    // reviewPoolDao.minAddtime();//更新中间处理时间为第一次处理时间

    //
    // Map<String, Object> mapDate = new HashMap<String, Object>();
    // while (true) {
    i++;
    oneSumCount = 0;
    oneJumpCount = 0;
    oneErrorCount = 0; // 计算一次循环失败的迁移数量
    long info = System.currentTimeMillis();
    List<ReviewPool> dataAll = new ArrayList<ReviewPool>();
    dataAll = reviewPoolDao.getPage(null, mapDate);// 获取全部数据
    long end1 = System.currentTimeMillis();
    log.error("分页查询 " + pageFind + " 条：" + (info - end1));
    if (dataAll == null || dataAll.size() <= 0) {
      return "false";
    }
    // 查询分页中间结果信息
    List<String> reviewIdList = new ArrayList<String>();
    for (ReviewPool pReviewPool : dataAll) {
      reviewIdList.add(pReviewPool.getId());
    }
    info = System.currentTimeMillis();
    List<ReviewMiddle> midDate = reviewMiddleDao.findByreviewIdList(reviewIdList);
    end1 = System.currentTimeMillis();
    log.error("中间结果查询" + (info - end1));
    oneSumCount = dataAll.size();
    sumCount = sumCount + dataAll.size();
    // int index = pager.getPageIndex();
    // index++;
    // pager = new PagerInfo(pager.getPageSize(), index);
    Date calcDate = null;
    String appealCount = "";
    Map<String, List<ReviewMiddle>> map = new HashMap<String, List<ReviewMiddle>>();
    // Map转List
    for (ReviewMiddle rm : midDate) {
      String rId = rm.getReviewid();
      if (map.containsKey(rId)) {
        List<ReviewMiddle> rmList = map.get(rId);
        rmList.add(rm);
      } else {
        List<ReviewMiddle> rmList = new ArrayList<ReviewMiddle>();
        rmList.add(rm);
        map.put(rId, rmList);
      }
    }
    info = System.currentTimeMillis();
    for (ReviewPool reviewPool : dataAll) {
      String c = reviewPool.getCorderPrimary();
      if (c == null || "".equals(c) || "error".equals(c)) {
        // ============================计算中间结果上诉次数=========================
        String id = reviewPool.getId();
        String workStatus = reviewPool.getWorkStatus();
        if (!"0".equals(workStatus)) { // 如果不是未处理
          List<ReviewMiddle> midList = map.get(id);
          if (midList != null && midList.size() > 0) { // 如果有中间结果
            int count = 0;// 临时存放中间结果上诉次数
            if (midList.size() > 1) {// 如果有N个中间结果
              for (int q = 0; q < midList.size() - 1; q++) {
                String addTime = midList.get(q).getAddtime();
                String addTime2 = midList.get(q + 1).getAddtime();
                long addTimeLong = TimeUtil.timeStrTOlong(addTime);
                long addTimeLong2 = TimeUtil.timeStrTOlong(addTime2);
                long timeX = addTimeLong2 - addTimeLong;
                timeX = timeX / 1000;
                int h = (int) (timeX / 3600);
                h = h / 48;
                count = count + h;
              }
            }
            if ("2".equals(workStatus)) {// 如果是中间结果 计算到现在时间
              // 计算最后一个到现在的时间
              String addTime = midList.get(midList.size() - 1).getAddtime();// 获取最后一个添加时间
              long addTimeLong = TimeUtil.timeStrTOlong(addTime);
              long timeLong = System.currentTimeMillis();
              long timeX = timeLong - addTimeLong;
              timeX = timeX / 1000;
              int h = (int) (timeX / 3600);
              h = h / 48;
              count = count + h;
              reviewPool.setAppealCount2(count);// 计算中间结果添加时间 到
              // 现在经历了多少个48小时
            } else if ("3".equals(workStatus)) {// 如果是最终结果
              // 计算最后一个到最终结果添加时间
              String addTime = midList.get(midList.size() - 1).getAddtime();// 获取最后一个添加时间
              String closeTime = reviewPool.getPosition3();
              long addTimeLong = TimeUtil.timeStrTOlong(addTime);
              long timeLong = TimeUtil.timeStrTOlong(closeTime);
              long timeX = timeLong - addTimeLong;
              timeX = timeX / 1000;
              int h = (int) (timeX / 3600);
              h = h / 48;
              count = count + h;
              reviewPool.setAppealCount2(count);// 计算中间结果添加时间 到
              // 最终结果添加时间经历了多少个48小时
            }
          } else {// 如果没有中间结果
            reviewPool.setAppealCount2(0);// 中间结果上诉次数不用计算
          }
        } else {// 如果是未处理
          reviewPool.setAppealCount2(0);// 中间结果上诉次数不用计算
        }
        // ============================计算中间结果上诉次数结束=========================
        boolean errorFlg = false;
        // 初始化参数,防止更新时错误
        // reviewPool.setAppealCount1(0); //​未处理上诉次数
        // reviewPool.setFeedBackCount(0); //​咨询次数
        // reviewPool.setRemark4(""); //​人员id
        // if (runCount == 1) {//只有第一次执行下面数据
        // reviewPool.setOrderCome(""); //配送时效
        // }
        // reviewPool.setCloseType(""); //订单关闭原因
        // reviewPool.setOrderProcessTime(""); //最终处理时效
        reviewPool.setAskFlg(0); // 是否咨询
        // 是否是咨询类判断网单是否是WDZX类
        String netOrderIdIsNull = reviewPool.getNetOrderId();
        netOrderIdIsNull = netOrderIdIsNull != null ? netOrderIdIsNull : "";// 解决网单号为NULL时抛出空指针的bug
        if ("WDZX".equals(netOrderIdIsNull.toUpperCase())) {
          reviewPool.setAskFlg(1);
          // 咨询类工单配送时效设为空
          reviewPool.setOrderCome("");
        } else {
          if (!"3".equals(reviewPool.getWorkStatus())
              && ("".equals(reviewPool.getOrderCome()) || "SG".equals(reviewPool.getOrderCome()))) {
            /*
             * ================================接口调用=================
             * ===== ===============
             */
            // 获取订单号主键Id
            Orders orderBean = ordersReadDao.get(Integer.valueOf(reviewPool.getOrderId()));
            try {
              Integer miao = (Integer) mapDate.get("sleepTime");
              Thread.currentThread().sleep(miao);// 毫秒
            } catch (Exception e) {
            }
            // 判断订单接口是否调用成功
            if (orderBean != null) {
              // ====================订单来源维护==========================//
              if ("天猫模卡".equals(reviewPool.getRemark5())) { // 目前就这一个不匹配
                // 直接写死了.
                reviewPool.setRemark5("mooka模卡官方旗舰店");
              }
              if (reviewPool.getRemark5() == null || "".equals(reviewPool.getRemark5())) {
                String ddly = orderBean.getSource();
                ReviewDataDictionaryEntity ddlyList = selectByValue(ddly, data_data);
                if (ddlyList != null) {
                  reviewPool.setRemark5(ddlyList.getValueMeaning());
                }
              }
            } else {
              logError("订单接口调用失败。工单号：" + reviewPool.getId() + " 订单号：" + reviewPool.getOrderId());
              reviewPool.setOrderCome(""); // 配送时效直接置空
              errorFlg = true;
            }

            /*
             * ================================接口调用结束===============
             * ===== ==============
             */
          } else {// 不是关闭状态执行代码
            if ("SG".equals(reviewPool.getOrderCome())) {
              reviewPool.setOrderCome("");
            }
            if (reviewPool.getRemark5() == null || "".equals(reviewPool.getRemark5().trim())) {
              // 获取订单号主键Id
              WorkOrderBean orderBean = gdOrderByOrder(reviewPool.getOrderId());
              // 判断订单接口是否调用成功
              if (orderBean != null) {
                // ====================订单来源维护==========================//
                String ddly = orderBean.getSource();
                ReviewDataDictionaryEntity ddlyList = selectByValue(ddly, data_data);
                if (ddlyList != null) {
                  reviewPool.setRemark5(ddlyList.getValueMeaning());
                }
              }
            } else {
              if ("天猫模卡".equals(reviewPool.getRemark5())) { // 目前就这一个不匹配
                // 直接写死了.
                reviewPool.setRemark5("mooka模卡官方旗舰店");
              }
            }
          }
        }
        String remark4 = reviewPool.getRemark4();
        if (remark4 == null || "".equals(remark4)) {// 判断是否需要匹配人员

          /* =========更新人员与人员ID(支持同名,前提需要配置好人员责任管理)============= */
          ReviewContactsDto popleList = null;

          ReviewContactsDto reviewContacts = new ReviewContactsDto();
          reviewContacts.setQuestion1level1(reviewPool.getQuestion1Level1());// 责任位1
          reviewContacts.setQuestion1level2(reviewPool.getQuestion1Level2());// 责任位2
          reviewContacts.setOrdercome(reviewPool.getRemark5());// 订单来源
          reviewContacts.setCompany(reviewPool.getCompany());// 工贸
          popleList = findListLG(reviewContacts, data);
          // 查询人员责任管理表中该工单的对应的人员
          // List<ReviewContactsDto> popleList = reviewContactsModel
          // .findListLG(reviewContacts);
          if (popleList != null) {
            reviewPool.setWangId(popleList.getUsername());
            reviewPool.setRemark4(popleList.getUserid());
          } else {
            logError(
                "更新人员Id失败。工单号：" + reviewPool.getId() + " 人员姓名:" + reviewPool.getWangId() + " 责任位1:"
                    + reviewPool.getQuestion1Level1() + " 责任位2:" + reviewPool.getQuestion1Level2()
                    + " 订单来源:" + reviewPool.getRemark5() + " 工贸:" + reviewPool.getCompany());
            errorFlg = true;
          }
          /* =========更新人员与人员ID(支持同名,前提需要配置好人员责任管理)结束============= */
        }
        /* =========更新人员ID备用(不支持同名)============= */
        // ReviewUser record = new ReviewUser();
        // record.setUsername(reviewPool.getWangId());
        // PagerInfo pager = new PagerInfo(1, 1);
        // List<ReviewUser> user =
        // reviewUserReadDao.findPageList(record, pager);
        // if (user != null && user.size() > 0) {
        // reviewPool.setRemark4(user.get(0).getId());
        // } else {
        // log.error("=============更新人员Id失败。工单号：" + reviewPool.getId() +
        // " 人员姓名:"
        // + reviewPool.getWangId() + "=============");
        // }
        /* =========更新人员ID备用(不支持同名)结束============= */
        // if (runCount == 1) {//只有第一次执行下面数据 因为不涉及到调用接口 所以不会涉及到失败！

        Integer appealCount1 = reviewPool.getAppealCount1();
        Date currtyDate = new Date();
        String insertTime = reviewPool.getInsertTime();// 生成工单时间
        Date startDate = TimeUtil.timeStrTOdate(insertTime);
        // String position2 =
        // reviewMiddleDao.getMinTimeByReviewId(reviewPool.getId());//
        // 中间结果时间
        String position2 = reviewPool.getPosition2();// 中间结果时间
        String position3 = reviewPool.getPosition3();// 最终结果时间
        if (appealCount1 == null || appealCount1 <= 0) { // 判断是否有上诉次数
          /*
           * ================================原计算上诉次数==================
           * ====== =============
           */
          // 计算申诉次数
          if ((position2 == null || "".equals(position2)) && (position3 == null || ""
              .equals(position3))) {
            calcDate = currtyDate;
          } else {
            if ((position2 == null || "".equals(position2))
                && (position3 != null && !"".equals(position3))) {
              calcDate = TimeUtil.timeStrTOdate(position3);
            } else if ((position3 == null || "".equals(position3))
                && (position2 != null && !"".equals(position2))) {
              calcDate = TimeUtil.timeStrTOdate(position2);
            } else {
              calcDate = TimeUtil.timeStrTOdate(position2);
            }
          }
          long countTime = 0;
          if (DateUtil.format(calcDate, ReviewConstants.TIME.FORMAT_DATE).substring(0, 10)
              .equals(DateUtil.format(startDate, ReviewConstants.TIME.FORMAT_DATE)
                  .substring(0, 10))) {// 创建时间和结果时间为同一天
            if (calcDate.getHours() < 9) {
              calcDate.setHours(9);
            }
            if (calcDate.getHours() > 17) {
              calcDate.setHours(17);
            }
            countTime = calcDate.getTime() - startDate.getTime();
          } else {
            if (calcDate.getHours() > 17) {
              calcDate.setHours(17);
            }
            if (calcDate.getHours() < 9) {
              calcDate.setHours(9);
            }
            countTime = calcDate.getTime() - startDate.getTime()
                - (57600000 * TimeUtil.getIntervalDays(startDate, calcDate));
          }
          appealCount = String.valueOf((countTime / (60 * 60 * 1000)) / 2);
          reviewPool.setAppealCount1(Math.abs(Integer.parseInt(appealCount)));
          /*
           * ================================原计算上诉次数结束================
           * ====== =============
           */
        }
        /*
         * ================================计算用户咨询次数=====================
         * = ===============
         */
        // Integer feedBackCount = reviewPool.getFeedBackCount();
        // if (feedBackCount == null || feedBackCount <= 0) {
        // //判断是否需要计算用户咨询次数
        // int count = findFeedBackNum(reviewPool);
        // reviewPool.setFeedBackCount(count);
        // }
        String oneProcess = reviewPool.getOneProcessTime();
        if (oneProcess == null || "".equals(oneProcess)) {
          /*
           * ===========================计算一次处理时效======================
           * ===
           */
          if (position2 != null && !"".equals(position2) && reviewPool.getPosition2() != null
              && !"".equals(reviewPool.getPosition2())) {
            Date oneProcessDate = null;
            long oneProcessCount;
            String oneProcessTime = null;
            oneProcessDate = TimeUtil.timeStrTOdate(position2);
            // 中间结果时间 - 创建时间 - 之间所有17点～9点时间
            oneProcessCount = oneProcessDate.getTime() - startDate.getTime()
                - (57600000 * TimeUtil.getIntervalDays(startDate, oneProcessDate));
            // 获取经历的时间
            oneProcessTime = TimeUtil.convertTimeByMillisecods(oneProcessCount);
            reviewPool.setOneProcessTime(oneProcessTime);
          }
        }
        String orderProcess = reviewPool.getOrderProcessTime();
        if (orderProcess == null || "".equals(orderProcess)) {

          /*
           * ===========================计算最终处理时效======================
           * ===
           */
          if (reviewPool.getPosition3() != null && !"".equals(reviewPool.getPosition3())) {
            orderProcessCount(reviewPool, reviewPool);
            /*
             * ===========================判断关闭状态====================
             * =====
             */
            if (reviewPool.getCloseType() == null || "".equals(reviewPool.getCloseType())) {
              boolean a = reviewPool.getBackContext3().contains("责任位录入错误自动闭环，保存当前所有信息，关联新工单号为");
              if (a) {
                reviewPool.setCloseType(ReviewConstants.CLOSETYPE.CLOSETYPE_4);
              } else {
                reviewPool.setCloseType(ReviewConstants.CLOSETYPE.CLOSETYPE_1);
              }
            }

          }
        }
        // }
        if (errorFlg) { // 如果这一条有失败的,
          oneErrorCount++;// 记录一次失败条数
          reviewPool.setCorderPrimary("error"); // 临时用一下
        } else {
          reviewPool.setCorderPrimary("success"); // 临时用一下
        }
      } else {
        oneJumpCount++;// 记录一次跳过条数
      }
    }
    end1 = System.currentTimeMillis();
    log.error("for循环:" + (info - end1));
    // 批量更新
    if (dataAll != null && dataAll.size() > 0 && dataAll.size() > oneJumpCount) {// 判断是否全部跳过。
      // 都跳过了则不更新
      reviewPoolDao.updAll(dataAll);
    }
    Long end = System.currentTimeMillis();
    long l = end - start;
    long day = l / (24 * 60 * 60 * 1000);
    day = l / (24 * 60 * 60 * 1000);
    long hour = (l / (60 * 60 * 1000) - day * 24);
    long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
    long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
    String r = "";
    if (day != 0) {
      r = Math.abs(day) + "天";
    }
    if (hour != 0) {
      r += Math.abs(hour) + "小时";
    }
    if (min != 0) {
      r += Math.abs(min) + "分";
    }
    if (s != 0) {
      r += Math.abs(s) + "秒";
    }
    logError("第" + i + "次循环,迁移数量:" + oneSumCount + "条,失败:" + oneErrorCount + "条,跳过:" + oneJumpCount
        + "条,总用时:" + r);
    return "第" + i + "次循环,迁移数量:" + oneSumCount + "条,失败:" + oneErrorCount + "条,跳过:" + oneJumpCount
        + "条,总用时:" + r;
    // }
    // reviewPoolDao.maxAddtime();//更新中间处理时间为最新处理时间
    // Long end = System.currentTimeMillis();
    // long l = end - start;
    // long day = l / (24 * 60 * 60 * 1000);
    // day = l / (24 * 60 * 60 * 1000);
    // long hour = (l / (60 * 60 * 1000) - day * 24);
    // long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
    // long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
    // String r = "";
    // if (day != 0) {
    // r = Math.abs(day) + "天";
    // }
    // if (hour != 0) {
    // r += Math.abs(hour) + "小时";
    // }
    // if (min != 0) {
    // r += Math.abs(min) + "分";
    // }
    // if (s != 0) {
    // r += Math.abs(s) + "秒";
    // }
    // logError("第" + runCount + "次数据迁移结束。总迁移数量:" + sumCount + "条,总失败:" +
    // errorCount + "条,总跳过:"
    // + jumpCount + "条,总用时:" + r);
  }

  /**
   * 获取订单来源
   */
  private ReviewDataDictionaryEntity selectByValue(String ddly,
      List<ReviewDataDictionaryEntity> data_data) {
    for (ReviewDataDictionaryEntity r : data_data) {
      if (ddly.equals(r.getValue())) {
        return r;
      }
    }
    return null;
  }

  /**
   * 数据迁移_查询人员ID
   */
  private ReviewContactsDto findListLG(ReviewContactsDto reviewContacts,
      List<ReviewContactsDto> data) {
    for (ReviewContactsDto dto : data) {
      // 责任位1
      String dtoZr1 = dto.getQuestion1level1();
      String zr1 = reviewContacts.getQuestion1level1();
      // 订单来源
      String d = dto.getOrdercome() != null ? dto.getOrdercome() : "";
      String d2 = reviewContacts.getOrdercome() != null ? reviewContacts.getOrdercome() : "";
      String dtoDdly = "," + d + ",";
      String ddly = "," + d2 + ",";
      // 工贸
      String g = dto.getCompany() != null ? dto.getCompany() : "";
      String g2 = reviewContacts.getCompany() != null ? reviewContacts.getCompany() : "";
      String dtoGm = "," + g + ",";
      String gm = "," + g2 + ",";
      if (dtoZr1.equals(zr1) && (dtoDdly.indexOf(ddly) != -1)) { // 判断责任位1是否相等
        // 订单来源是否存在
        if (dtoGm.indexOf(gm) != -1) { // 判断是否存在工贸
          return dto;
        } else {
          if (dto.getCompany() == null || "".equals(dto.getCompany())) {
            return dto;
          }
        }
      }
    }
    return null;
  }

  /**
   * 数据迁移记录错误
   */
  public void logError(String errorContent) {
    ReviewErrorData reviewErrorData = new ReviewErrorData();
    reviewErrorData.setId(UUID.randomUUID().toString());
    reviewErrorData.setAdduser(ReviewConstants.SYS.SYSTEM);
    reviewErrorData.setAddtime(DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE));
    reviewErrorData.setErrorcontent(errorContent);
    woReviewErrorDataDao.insertSelective(reviewErrorData);
  }

  public Integer findPerformNum(Map<String, Object> map) {
    Integer i = reviewPoolDao.findPerformNum(map);
    return i;
  }

  public void updateWorkOrder(ReviewPool dto) {

    String addTime = DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE);
    ReviewPool byReviewPool = reviewPoolDao.findReviewById(dto);

    ////////////////////////////////////////

    if (dto.getRemark1().indexOf("SQM") != -1) {
      ReviewMiddle reviewMiddle = new ReviewMiddle();
      reviewMiddle.setAddtime(addTime);
      reviewMiddle.setId(UUID.randomUUID().toString());
      reviewMiddle.setResultType("0");
      reviewMiddle.setAdduser(dto.getRemark1());
      reviewMiddle.setMiddleContext(dto.getBackContext2());

      reviewMiddle.setReviewid(dto.getId());
      reviewMiddleDao.addReviewMiddle(reviewMiddle);

      if (null != byReviewPool.getPosition2() && !"".equals(byReviewPool.getPosition2())
          && null != byReviewPool.getWorkOrderTime() && !""
          .equals(byReviewPool.getWorkOrderTime())) {
        dto.setPosition2(byReviewPool.getWorkOrderTime());
        dto.setWorkOrderTime(addTime);
      } else {
        dto.setPosition2(addTime);
        dto.setWorkOrderTime(addTime);
      }

      if (byReviewPool.getWorkStatus().equals("0")) {
        dto.setWorkStatus("2");
      }

      if (byReviewPool.getWorkStatus().equals("3")) {
        dto.setSqmStatus(null);
      }

      dto.setMiddleType("1");
    }
    if (dto.getRemark1().equals("HP")) {
      ReviewMiddle reviewMiddle = new ReviewMiddle();
      reviewMiddle.setAddtime(dto.getWorkOrderTime());
      reviewMiddle.setId(UUID.randomUUID().toString());
      reviewMiddle.setResultType("0");
      reviewMiddle.setAdduser("HP");
      reviewMiddle.setMiddleContext(dto.getBackContext2());
      reviewMiddle.setReviewid(dto.getId());
      // log.info("执行了修改HP工单中间结果表开始:"+dto.getId());
      reviewMiddleDao.addReviewMiddle(reviewMiddle);

      // log.info("执行了修改HP工单中间结果表结束:"+dto.getId());

      if (null != byReviewPool.getPosition2() && !"".equals(byReviewPool.getPosition2())
          && null != byReviewPool.getWorkOrderTime() && !""
          .equals(byReviewPool.getWorkOrderTime())) {
        dto.setPosition2(byReviewPool.getWorkOrderTime());
      } else {
        dto.setPosition2(addTime);
        dto.setWorkOrderTime(addTime);
      }

      if (byReviewPool.getWorkStatus().equals("0")) {
        dto.setWorkStatus("2");
      }

      if (byReviewPool.getWorkStatus().equals("3")) {
        dto.setSqmStatus(null);
      }

      dto.setMiddleType("1");
    }
    // log.info("执行了修改HP工单主表update开始:"+dto.getId());
    reviewPoolDao.updWorkOrder(dto);
    // log.info("执行了修改HP工单主表update结束:"+dto.getId());

  }

  public void updWorkOrderOne(ReviewPool dto) {
    reviewPoolDao.updWorkOrderOne(dto);
    log.error("执行了修改HP工单主表update结束:" + dto.getId() + "-----" + dto.getSqmStatus() + "----" + dto
        .getDesideText());
  }

  // 计算日期相差天数
  public long JiSuanDate(String minDate, String maxDate) throws ParseException {
    SimpleDateFormat d = new SimpleDateFormat("yyyyMMdd");
    Date date1 = d.parse(minDate);// 日期1
    Date date2 = d.parse(maxDate);// 日期2
    /*
     * 将2个日期各自转成毫秒值
     */
    long s1 = date1.getTime();
    long s2 = date2.getTime();
    /*
     * 计算2个日期之间相差的秒数，再转成天数
     */
    long day = ((s2 - s1) / (24 * 60 * 60 * 1000));
    System.out.println(day);// 控制台输出天数
    return day;
  }

  public void updateSQMStatus(String ids) {
    String[] list = ids.split(",");
    for (int i = 0; i < list.length; i++) {
      reviewPoolDao.updateSQMStatus(list[i]);
    }
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


  void mailTextMessages(String type, String reviewId, String netOrderId, String productName,
      String wangId,
      String buyer, String buyerMobile, String question1Level2,
      Integer appealCount1, Integer feedBackCount, String reason,
      boolean isLead) {

    if (netOrderId == null || "".equals(netOrderId.trim())) {
      throw new BusinessException("网单号不能为空！");
    }
    if (productName == null || "".equals(productName.trim())) {
      productName = "";
    }
    if (wangId == null || "".equals(wangId)) {
      throw new BusinessException("人员Id不能为空！");
    }
    if (buyer == null || "".equals(buyer)) {
      buyer = "";
    }
    if (buyerMobile == null || "".equals(buyerMobile)) {
      buyerMobile = "";
    }
    if (question1Level2 == null || "".equals(question1Level2.trim())) {
      question1Level2 = "";
    }
    if (reason == null || "".equals(reason.trim())) {
      reason = "";
    }
    List<String> emailList = new ArrayList<String>();
    //判断是否是发送领导
    if (isLead) {
      ReviewContactsDto record = new ReviewContactsDto();
      record.setUserid(wangId);
      List<ReviewContactsDto> data = woReviewContactsDao.findList(record);
      if (data != null && data.size() > 0) {
        //获取领导email信息
        if ("1".equals(data.get(0).getIssend1())) {
          emailList.add(data.get(0).getEmail1());
        }
        if ("1".equals(data.get(0).getIssend2())) {
          emailList.add(data.get(0).getEmail2());
        }
      } else {
        throw new BusinessException("该工单没有匹配到责任人领导！");
      }
    } else {
      //通过人员Id获取对应人员信息
      WOUser user = woUserDao.getById(wangId);
      if (user != null) {
        String email = user.getEmail();//获取人员邮箱
        //获取人员email信息
        emailList.add(email);
      } else {
        throw new BusinessException("该工单没有匹配到责任人！");
      }
    }

    //通过工单号获取工单信息
    ReviewPool pool = new ReviewPool();
    pool.setId(reviewId);
    ReviewPool reviewPool = reviewPoolDao.findReviewById(pool);
    int question1Type = 1;//定义1为 不需要发送工贸等信息的，2为需要发送工贸等信息的
    if (reviewPool != null) {
      if (ReviewConstants.QUESTION1LEVEL1.DUTY_1_1.equals(reviewPool.getQuestion1Level1())
          || ReviewConstants.QUESTION1LEVEL1.DUTY_1_9.equals(reviewPool.getQuestion1Level1())) {
        //如果是物流类或者售后类
        question1Type = 2;
      }
    }
    String addContent = "";
    if (question1Type == 1) {
      //网单号，产品型号，联系人+电话，第二责任位，申诉次数，咨询次数，用户反馈内容
      addContent = MessageFormat.format(smssend, type, netOrderId, productName, buyer,
          buyerMobile, question1Level2, appealCount1, feedBackCount, reason);
    } else {
      String company = reviewPool.getCompany();
      company = company != null ? company : "";
      String phone = reviewPool.getPhone();
      phone = phone != null ? phone : "";
      String netPointId = reviewPool.getNetPointId();
      netPointId = netPointId != null ? netPointId : "";
      //网单号，产品型号，联系人+电话，工贸，物流中心，配送网点，第二责任位，申诉次数，咨询次数，用户反馈内容
      addContent = MessageFormat.format(mailsend, type, netOrderId, productName, buyer,
          buyerMobile, company, phone, netPointId, question1Level2, appealCount1, feedBackCount,
          reason);
    }
    List<ReviewMailQueue> reviewMailQueueList = new ArrayList<>();
    for (String email : emailList) {
      ReviewMailQueue reviewMailQueue = new ReviewMailQueue();
      //发送者
      reviewMailQueue.setSender("系统");
      //接收者邮箱
      reviewMailQueue.setReceiver(email);
      //发送内容
      reviewMailQueue.setInformation(addContent);
      reviewMailQueueList.add(reviewMailQueue);
    }
    if (reviewMailQueueList.size() > 0) {
      reviewMailQueueDao.addReviewEmailList(reviewMailQueueList);
    }
  }

  /**
   * 是否需要发送短信
   */
  private void send(ReviewContext reviewContext) {
    //是否发送短信逻辑
    String reviewId = reviewContext.getReviewid();
    String addTime = reviewContext.getAddtime();
    String addUser = reviewContext.getAdduser();
    List<ReviewContext> contexts = reviewContextDao.getReviewContextByReviewId(reviewId);
    //根据工单ID查询对应工单
    ReviewPool pool = new ReviewPool();
    pool.setId(reviewId);
    ReviewPool oldWorkOrder = reviewPoolDao.findReviewById(pool);
    //获取用户咨询次数+1
    int count = this.findFeedBackNum(oldWorkOrder) + 1;
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

}
