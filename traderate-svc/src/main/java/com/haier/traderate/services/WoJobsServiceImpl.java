package com.haier.traderate.services;

import com.alibaba.dubbo.common.json.JSON;
import com.haier.common.BusinessException;
import com.haier.common.util.DateUtil;
import com.haier.shop.dto.ReviewContactsDto;
import com.haier.shop.dto.ThirdPartyLiabilityCondition;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import com.haier.shop.util.ReviewConstants;
import com.haier.traderate.service.WoJobsService;
import com.haier.traderate.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xupeng on 18/4/26.
 */
@Service
public class WoJobsServiceImpl implements WoJobsService {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                        .getLogger(WoJobsServiceImpl.class);

    @Autowired
    ReviewPoolService reviewPoolService;

    @Autowired
    ReviewPoolJobSetService reviewPoolJobSetService;

    @Autowired
    ShopMembersService shopMembersService;

    @Autowired
    WOUserDataService woUserDataService;

    @Autowired
    ReviewSmsQueueService reviewSmsQueueService;

    @Autowired
    ReviewMailQueueService reviewMailQueueService;

    @Autowired
    WoReviewContactsDataService woReviewContactsDataService;

    @Autowired
    WoDictionaryDataService woDictionaryDataService;

    @Autowired
    ShopOrdersService shopOrdersService;

    @Autowired
    ReviewImageDataService reviewImageDataService;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${workorder.smssend}")
    private String smsSendContext;

    @Value("${workorder.mailsend}")
    private String mailSendContext;

    @Value("${workorder.urlToHP}")
    private String urlToHP;

    private static String DEFAULT = "";
    /**
     * 工单生成后两小时未处理，工单上诉件数+1
     *
     */
    @Override
    public void automaticAppeal() throws Exception {

        // 根据工单状态，取得未处理的工单

        List<ReviewPool> data = reviewPoolService.getReviewPoolByStatus(ReviewConstants.WORKSTATUS.UNTREATED);
        boolean appealFlg = false;
        ReviewPool updPool = new ReviewPool();
        for (ReviewPool reviewPool : data) {
            try {
                appealFlg = false;
                // 待发短信队列
                List<ReviewSmsQueue> reviewSmsQueueList = new ArrayList<ReviewSmsQueue>();
                // 待发邮件队列
                List<ReviewMailQueue> reviewMailQueueList = new ArrayList<ReviewMailQueue>();

                ReviewPoolJobSet reviewPoolJobSet = reviewPoolJobSetService.queryJobSet(reviewPool.getQuestion1Level1());
                if (reviewPoolJobSet != null) {

                    // 当前时间
                    Calendar now = Calendar.getInstance();
                    // 工单创建时间（最终上诉时间）
                    Calendar creat = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if (reviewPoolJobSet.getTime() == null) {
                        now.add(Calendar.MINUTE, 0);
                    } else {
                        now.add(Calendar.MINUTE, -Integer.parseInt(reviewPoolJobSet.getTime()));
                    }
                    // 当前时间减去两小时之后的时间
                    String compareTimeNow = df.format(now.getTime());
                    // 工单创建时间（最终上诉时间）
                    String compareTimeCreat = "";

                    // 根据上诉记录，判断两小时未处理的参照时间
                    if (reviewPool.getAppealCount1() >= 1 && reviewPool.getLastAppealTime() != null) {
                        creat.setTime(df.parse(reviewPool.getLastAppealTime()));
                        compareTimeCreat = reviewPool.getLastAppealTime();
                    } else {
                        // 没有上诉过，由工单专员处理
                        creat.setTime(df.parse(reviewPool.getInsertTime()));
                        compareTimeCreat = reviewPool.getInsertTime();
                    }

                    if (creat.get(Calendar.HOUR_OF_DAY) >= 15) {
                        // 15:00-17:00之间工单不足2小时，T+1日9:00开始计时满2小时开始上诉。

                        // 15点之后的工单，跨天之后可能上诉，当天不会上诉
                        if (now.get(Calendar.DAY_OF_YEAR) != creat.get(Calendar.DAY_OF_YEAR)) {

                            // 工单创建时间（最终上诉时间）距离现在大于一天的情况下
                            now.add(Calendar.HOUR_OF_DAY, 2);
                            if ((now.getTimeInMillis() - creat.getTimeInMillis()) / 1000 / 60 / 60 / 24 > 1) {
                                appealFlg = true;
                            } else {
                                if (creat.get(Calendar.HOUR_OF_DAY) < 17) {
                                    // 创建时间（最终上诉时间）距离17：00：00的时间差
                                    int hour = 17 - creat.get(Calendar.HOUR_OF_DAY) - 1;
                                    int minute = 60 - creat.get(Calendar.MINUTE) - 1;
                                    int second = 60 - creat.get(Calendar.SECOND);
                                    // 当前时间 + 前一天的工作时间
                                    now.add(Calendar.HOUR_OF_DAY, hour);
                                    now.add(Calendar.MINUTE, minute);
                                    now.add(Calendar.SECOND, second);
                                }
                                // 两天工作时间满两小时上诉
                                if (now.get(Calendar.HOUR_OF_DAY) >= 11) {
                                    appealFlg = true;
                                }
                            }
                        }
                    } else {
                        // 9:00-15:00之间工单在工作时间内2小时未录入中间结果或者最终结果，系统记录上诉次数1次
                        if (compareTimeCreat.compareTo(compareTimeNow) <= 0) {
                            // 如果不同天，前一天肯定满两小时工作时间
                            if (creat.get(Calendar.DAY_OF_YEAR) != now.get(Calendar.DAY_OF_YEAR)) {

                                appealFlg = true;
                            } else {
                                // 如果同一天，当天要满两小时工作时间
                                if (now.get(Calendar.HOUR_OF_DAY) >= 9) {

                                    appealFlg = true;
                                }
                            }
                        }
                    }

                    // 工单上诉
                    if (appealFlg) {

                        String info = smsSendContext;

                        String addContent = MessageFormat.format(info, ReviewConstants.SMSTYPE.TYPE_3,
                                reviewPool.getNetOrderId(), reviewPool.getProductName(), reviewPool.getBuyer(),
                                reviewPool.getBuyerMobile(), reviewPool.getQuestion1Level2(),
                                reviewPool.getAppealCount1(), reviewPool.getFeedBackCount(), reviewPool.getContext());

                        // 判断工单上诉时间的列 是不是空的
                        if (reviewPool.getAutomaticAppealTime() != null
                                && !"".equals(reviewPool.getAutomaticAppealTime())) {

                            Date dt1 = df.parse(compareTimeNow);
                            Date dt2 = df.parse(reviewPool.getAutomaticAppealTime());
                            // 如果不是空的 判断当前时间 减去设置的2小时 是不是 大于 发送时间 大于的话 就发送短信
                            // 否则就不发 代表还没到间隔发送时间

                            if (dt1.getTime() > dt2.getTime()) {

                                // 如果工单未处理上诉两次以上， 短信通知责任人上线人员。
//                                ReviewJurisdiction rs1 = null;
//                                ReviewJurisdiction rs2 = null;
//                                ReviewJurisdiction rs3 = null;
//                                ReviewJurisdiction rs4 = null;
//                                ReviewJurisdiction rs5 = null;

                                // 如果是电话中心的话

                                if (null != reviewPool.getStoreType() && (reviewPool.getStoreType().equals("store")
                                        || reviewPool.getStoreType().equals("O2O"))) {
                                    String memberMobile = shopMembersService.getMemberMobile(Integer.valueOf(reviewPool.getStoreId()));
                                    if (memberMobile != null) {

                                        ReviewSmsQueue reviewSmsQueue = new ReviewSmsQueue();
                                        // 接收者
                                        // reviewSmsQueue.setReceiver(phone);
                                        reviewSmsQueue.setReceiver(memberMobile);
                                        // 发送内容
                                        reviewSmsQueue.setInformation("您有新的投诉工单.工单信息,工单号:" + reviewPool.getNetOrderId()
                                                + ",收货人员:" + reviewPool.getBuyer() + ",责任位1:"
                                                + reviewPool.getQuestion1Level1() + ",责任位2:"
                                                + reviewPool.getQuestion1Level2() + ",请尽快处理操作并闭环");
                                        reviewSmsQueueList.add(reviewSmsQueue);

                                    }
                                } else {

                                    if (reviewPool.getAppealCount1() > 0) {

                                        List<String> mailList = new ArrayList<String>();

                                        // 这里开始写 工单未处理 逻辑
//                                        ReviewJurisdiction r = new ReviewJurisdiction();
//                                        r.setZrw1(reviewPoolJobSet.getValue());
//                                        r.setLevel("1");
//                                        rs1 = reviewJurisdictionReadDao.queryReviewJurisdiction(r);
//                                        if (rs1 != null) {
//                                            ReviewSmsQueue reviewSmsQueue = new ReviewSmsQueue();
//                                            // 接收者
//                                            reviewSmsQueue.setReceiver(rs1.getPhone());
//                                            // 发送内容
//                                            reviewSmsQueue.setInformation(addContent);
//                                            reviewSmsQueueList.add(reviewSmsQueue);
//                                            // 领导邮件
//                                            if(null != rs1.getMail() && !"".equals(rs1.getMail())) {
//                                                mailList.add(rs1.getMail());
//                                            } else {
//                                                //mailList.add("");
//                                                log.error("领导邮箱为空");
//                                            }
//
//                                        }
                                        // 此处新增逻辑缺陷：上诉时未给工单专员发送短信
                                        WOUser reviewUser = new WOUser();
                                        reviewUser.setUserName(reviewPool.getWangId());

                                        List<WOUser> userList = woUserDataService.getListByParam(reviewUser);

                                        if (userList != null && userList.size() > 0) {
                                            WOUser sendUser = userList.get(0);
                                            ReviewSmsQueue smsQueue = new ReviewSmsQueue();
                                            smsQueue.setReceiver(sendUser.getMobile());
                                            smsQueue.setInformation(addContent);
                                            reviewSmsQueueList.add(smsQueue);
                                            //工单专员邮件
                                            if(null != sendUser.getEmail() && !"".equals(sendUser.getEmail()) ) {

                                                mailList.add(sendUser.getEmail());

                                            } else {
                                                //mailList.add("");
                                                log.error("工单专员邮件为空");
                                            }
                                        }

                                        /////////////////////////////////////////////////
                                        for (String mail : mailList) {
                                            // 发邮件
                                            ReviewMailQueue mailQueue = new ReviewMailQueue();
                                            // 发送者
                                            mailQueue.setSender("系统");
                                            // 接收者邮箱
                                            mailQueue.setReceiver(mail);
                                            // 发送内容
                                            mailQueue.setInformation(addContent);
                                            reviewMailQueueList.add(mailQueue);
                                        }

                                    }
//                                    if (reviewPool.getAppealCount1() > 1) {
//                                        ReviewJurisdiction r = new ReviewJurisdiction();
//                                        r.setZrw1(reviewPoolJobSet.getValue());
//                                        r.setLevel("2");
//                                        rs2 = reviewJurisdictionReadDao.queryReviewJurisdiction(r);
//                                        if (rs2 != null) {
//                                            ReviewSmsQueue reviewSmsQueue = new ReviewSmsQueue();
//                                            // 接收者
//                                            reviewSmsQueue.setReceiver(rs2.getPhone());
//                                            // 发送内容
//                                            reviewSmsQueue.setInformation(addContent);
//                                            reviewSmsQueueList.add(reviewSmsQueue);
//
//                                            /////////// 发邮件
//                                            ReviewMailQueue mailQueue = new ReviewMailQueue();
//                                            // 发送者
//                                            mailQueue.setSender("系统");
//                                            //领导邮件
//                                            if(null != rs2.getMail() && !"".equals(rs2.getMail())) {
//                                                mailQueue.setReceiver(rs2.getMail());
//                                            } else {
//                                                //mailQueue.setReceiver("");
//                                                log.error("领导邮箱为空");
//                                            }
//                                            // 发送内容
//                                            mailQueue.setInformation(addContent);
//                                            reviewMailQueueList.add(mailQueue);
//
//                                        }
//
//                                    }
//                                    if (reviewPool.getAppealCount1() > 2) {
//                                        ReviewJurisdiction r = new ReviewJurisdiction();
//                                        r.setZrw1(reviewPoolJobSet.getValue());
//                                        r.setLevel("3");
//                                        rs3 = reviewJurisdictionReadDao.queryReviewJurisdiction(r);
//                                        if (rs3 != null) {
//                                            ReviewSmsQueue reviewSmsQueue = new ReviewSmsQueue();
//                                            // 接收者
//                                            // reviewSmsQueue.setReceiver(phone);
//                                            reviewSmsQueue.setReceiver(rs3.getPhone());
//                                            // 发送内容
//                                            reviewSmsQueue.setInformation(addContent);
//                                            reviewSmsQueueList.add(reviewSmsQueue);
//
//                                            /////////// 发邮件
//                                            ReviewMailQueue mailQueue = new ReviewMailQueue();
//                                            // 发送者
//                                            mailQueue.setSender("系统");
//                                            // 接收者邮箱
//                                            if(null != rs3.getMail() && !"".equals(rs3.getMail())) {
//                                                mailQueue.setReceiver(rs3.getMail());
//                                            } else {
//                                                //mailQueue.setReceiver("");
//                                                log.error("领导邮箱为空");
//                                            }
//                                            // 发送内容
//                                            mailQueue.setInformation(addContent);
//                                            reviewMailQueueList.add(mailQueue);
//
//                                        }
//
//                                    }
//                                    if (reviewPool.getAppealCount1() > 3) {
//                                        ReviewJurisdiction r = new ReviewJurisdiction();
//                                        r.setZrw1(reviewPoolJobSet.getValue());
//                                        r.setLevel("4");
//                                        rs4 = reviewJurisdictionReadDao.queryReviewJurisdiction(r);
//                                        if (rs4 != null) {
//                                            ReviewSmsQueue reviewSmsQueue = new ReviewSmsQueue();
//                                            // 接收者
//                                            // reviewSmsQueue.setReceiver(phone);
//                                            reviewSmsQueue.setReceiver(rs4.getPhone());
//                                            // 发送内容
//                                            reviewSmsQueue.setInformation(addContent);
//                                            reviewSmsQueueList.add(reviewSmsQueue);
//
//                                            /////////// 发邮件
//                                            ReviewMailQueue mailQueue = new ReviewMailQueue();
//                                            // 发送者
//                                            mailQueue.setSender("系统");
//                                            // 接收者邮箱
//                                            if(null != rs4.getMail() && !"".equals(rs4.getMail())) {
//                                                mailQueue.setReceiver(rs4.getMail());
//                                            } else {
//                                                //mailQueue.setReceiver("");
//                                                log.error("领导邮箱为空");
//                                            }
//                                            // 发送内容
//                                            mailQueue.setInformation(addContent);
//                                            reviewMailQueueList.add(mailQueue);
//
//                                        }
//
//                                    }
//                                    if (reviewPool.getAppealCount1() > 4) {
//                                        ReviewJurisdiction r = new ReviewJurisdiction();
//                                        r.setZrw1(reviewPoolJobSet.getValue());
//                                        r.setLevel("5");
//
//                                        rs5 = reviewJurisdictionReadDao.queryReviewJurisdiction(r);
//                                        if (rs5 != null) {
//                                            ReviewSmsQueue reviewSmsQueue = new ReviewSmsQueue();
//                                            // 接收者
//                                            // reviewSmsQueue.setReceiver(phone);
//                                            reviewSmsQueue.setReceiver(rs5.getPhone());
//                                            // 发送内容
//                                            reviewSmsQueue.setInformation(addContent);
//                                            reviewSmsQueueList.add(reviewSmsQueue);
//
//                                            /////////// 发邮件
//                                            ReviewMailQueue mailQueue = new ReviewMailQueue();
//                                            // 发送者
//                                            mailQueue.setSender("系统");
//                                            // 接收者邮箱
//                                            if(null != rs5.getMail() && !"".equals(rs5.getMail())) {
//                                                mailQueue.setReceiver(rs5.getMail());
//                                            } else {
//                                                //mailQueue.setReceiver("");
//                                                log.error("领导邮箱为空");
//                                            }
//                                            // 发送内容
//                                            mailQueue.setInformation(addContent);
//                                            reviewMailQueueList.add(mailQueue);
//
//                                        }
//                                    }
                                }
                            }

                        } else {// 第一次处理工单，发送短信给工单专员
                            if (reviewPool.getAppealCount1() == 0) {

                                ReviewSmsQueue reviewSmsQueue = new ReviewSmsQueue();
                                // 接收者
                                // 此处不应该取用户手机号
                                // 改为工单专员手机号
                                WOUser reviewUser = new WOUser();
                                reviewUser.setUserName(reviewPool.getWangId());
                                List<WOUser> sendUserList = woUserDataService.getListByParam(reviewUser);
                                if (sendUserList != null && sendUserList.size() > 0) {
                                    WOUser woUser = sendUserList.get(0);

                                    reviewSmsQueue.setReceiver(woUser.getMobile());// 设置手机号
                                    // 发送内容
                                    reviewSmsQueue.setInformation(addContent);
                                    reviewSmsQueueList.add(reviewSmsQueue);

                                    /////////// 发邮件
                                    ReviewMailQueue mailQueue = new ReviewMailQueue();
                                    // 发送者
                                    mailQueue.setSender("系统");
                                    // 接收者邮箱
                                    if(null != woUser.getEmail() && !"".equals(woUser.getEmail())) {
                                        mailQueue.setReceiver(woUser.getEmail());
                                    } else {
                                        //mailQueue.setReceiver("");
                                        log.error("工单专员邮箱为空");
                                    }
                                    // 发送内容
                                    mailQueue.setInformation(addContent);
                                    reviewMailQueueList.add(mailQueue);

                                }
                            }
                        }
                        if (reviewSmsQueueList.size() > 0) {

                            reviewSmsQueueService.addReviewSmsList(reviewSmsQueueList);

                            updPool = new ReviewPool();
                            updPool.setId(reviewPool.getId());

                            updPool.setAppealCount1(reviewPool.getAppealCount1() + 1);

                            now.setTime(df.parse(compareTimeNow));

                            now.add(Calendar.HOUR_OF_DAY, 2);

                            updPool.setLastAppealTime(df.format(now.getTime()));

                            updPool.setAutomaticAppealTime(df.format(now.getTime()));

                            reviewPoolService.updWorkOrder(updPool);

                        }
                        if(reviewMailQueueList.size() > 0) {
                            reviewMailQueueService.addReviewEmailList(reviewMailQueueList);
                        } else {
                            log.error("上诉：邮件队列为空---" + reviewPool.getId());
                        }
                    }
                }
            } catch (Exception e) {
                log.error("上诉job异常", e);

            }
        }
    }

    /**
     *工单有了中间结果之后，48小时未处理，中间结果上诉件数+1
     *
     */
    @Override
    public void middleAppeal() throws Exception {

        // 根据工单状态，取得中间结果工单
        log.info("中间结果上诉sql开始");
        ReviewPool t = new ReviewPool();
        t.setWorkStatus(ReviewConstants.WORKSTATUS.MIDDLERESULT);
        t.setMiddleType("1");
        List<ReviewPool> data = reviewPoolService.getReviewPoolByStatusAppeal(t);

        ReviewPool updPool = new ReviewPool();
        for (ReviewPool reviewPool : data) {
            try {

                Calendar now = Calendar.getInstance();
                now.add(Calendar.DATE, -2); // 当前时间48小时以前的时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String nowTime = df.format(now.getTime());
                // 最新中间结果上一条时间
                String middleTime = "";
                // 最新中间结果时间
                String lastTime = "";

                String lastTime1 = "";

                // 这是中间结果 倒数第二条中间结果的时间
                if (reviewPool.getPosition2() != null) {
                    middleTime = reviewPool.getPosition2();
                }
                // 最新一条中间结果时间不为null的话
                int num = 0;
                if (reviewPool.getWorkOrderTime() != null && !"".equals(reviewPool.getWorkOrderTime())) {
                    lastTime = reviewPool.getWorkOrderTime();
                    Date date = df.parse(lastTime);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.DATE, -2); // 当前时间48小时以前的时间
                    lastTime1 = df.format(calendar.getTime());// 最新时间减去两天之前的时间

                    num = 1;
                }
                // 如果倒数第二条最终结果时间 与 最新结果时间相等的话 就代表 最终结果就一条

                if (middleTime.equals(lastTime)) {

                    Date dt1 = df.parse(nowTime);
                    Date dt2 = df.parse(middleTime);
                    if (dt1.getTime() > dt2.getTime()) {
                        num = 2;// 超时
                    }
                } else {

                    Date dt1 = df.parse(lastTime1);
                    Date dt2 = df.parse(middleTime);
                    if (dt1.getTime() > dt2.getTime()) {
                        num = 2;// 超时
                    }

                }
                // } else if(dt1.getTime() < dt2.getTime()) {
                // num=2;//未超时
                // }else{
                // num=3;//相等
                // }

                if (num == 2) {
                    updPool = new ReviewPool();

                    if (reviewPool.getAppealCount2() == 0) {
                        // 工单中间结果48小时以上，短信通知责任人的上线人员。
                        sendTextMessages(ReviewConstants.SMSTYPE.TYPE_4, reviewPool.getId(),
                                reviewPool.getNetOrderId(), reviewPool.getProductName(), reviewPool.getRemark4(),
                                reviewPool.getBuyer(), reviewPool.getBuyerMobile(), reviewPool.getQuestion1Level2(),
                                reviewPool.getAppealCount1(), reviewPool.getFeedBackCount(), reviewPool.getContext(),
                                ReviewConstants.SEND_PEOPLE.DUTY_PEOPLE);
                    }

                    if (reviewPool.getAppealCount2() > 0) {
                        // 工单中间结果48小时以上，短信通知责任人的上线人员。
                        sendTextMessages(ReviewConstants.SMSTYPE.TYPE_4, reviewPool.getId(),
                                reviewPool.getNetOrderId(), reviewPool.getProductName(), reviewPool.getRemark4(),
                                reviewPool.getBuyer(), reviewPool.getBuyerMobile(), reviewPool.getQuestion1Level2(),
                                reviewPool.getAppealCount1(), reviewPool.getFeedBackCount(), reviewPool.getContext(),
                                ReviewConstants.SEND_PEOPLE.MANAGERUSER_1);
                    }
                    if (reviewPool.getAppealCount2() > 1) {
                        // 工单中间结果48小时以上，短信通知责任人的上线人员。
                        sendTextMessages(ReviewConstants.SMSTYPE.TYPE_4, reviewPool.getId(),
                                reviewPool.getNetOrderId(), reviewPool.getProductName(), reviewPool.getRemark4(),
                                reviewPool.getBuyer(), reviewPool.getBuyerMobile(), reviewPool.getQuestion1Level2(),
                                reviewPool.getAppealCount1(), reviewPool.getFeedBackCount(), reviewPool.getContext(),
                                ReviewConstants.SEND_PEOPLE.MANAGERUSER_1_OR_2);
                    }

                    updPool.setId(reviewPool.getId());
                    updPool.setAppealCount2(reviewPool.getAppealCount2() + 1);
                    now.add(Calendar.DATE, 2);
                    updPool.setLastAppealTime(df.format(now.getTime()));
                    updPool.setMiddleType("0");
                    reviewPoolService.updWorkOrder(updPool);
                }

            } catch (BusinessException be) {
                log.error("中间上诉job异常：", be);

            } catch (Exception e) {
                log.error("中间上诉job异常：" + e);
            }
        }

    }


    /**
     * @param type 发送类型
     * @param netOrderId 网单号
     * @param productName 宝贝名称(产品型号)
     * @param wangId 短信接收人Id
     * @param buyer    收货人
     * @param buyerMobile 收货人手机
     * @param question1Level2 第二责任位
     * @param appealCount1 申诉次数
     * @param feedBackCount 咨询次数
     * @param reason 用户反馈内容
     * @param isLead 指定发送人   0:责任人  1:一级领导 2:一级领导与二级领导
     */
    public void sendTextMessages(String type,String reviewId, String netOrderId, String productName, String wangId,
                                 String buyer, String buyerMobile, String question1Level2,
                                 Integer appealCount1, Integer feedBackCount, String reason,
                                 Integer isLead) {

        if (netOrderId == null || "".equals(netOrderId.trim())) {
            throw new BusinessException("网单号不能为空！");
        }
        if (productName == null || "".equals(productName.trim())) {
            productName = DEFAULT;
        }
        if (wangId == null || "".equals(wangId)) {
            throw new BusinessException("人员Id不能为空！");
        }
        if (buyer == null || "".equals(buyer)) {
            //            throw new BusinessException("收货人不能为空！");
            buyer = DEFAULT;
        }
        if (buyerMobile == null || "".equals(buyerMobile)) {
            //            throw new BusinessException("收货人电话不能为空！");
            buyerMobile = DEFAULT;
        }
        if (question1Level2 == null || "".equals(question1Level2.trim())) {
            //            throw new BusinessException("第二责任位不能为空！");
            question1Level2 = DEFAULT;
        }

        //        if (appealCount1 == null || "".equals(appealCount1)) {
        //            throw new BusinessException("申诉次数不能为空！");
        //        }
        //        if (feedBackCount == null || "".equals(feedBackCount)) {
        //            throw new BusinessException("咨询次数不能为空！");
        //        }
        if (reason == null || "".equals(reason.trim())) {
            reason = DEFAULT;
        }
        List<String> phoneList = new ArrayList<String>();
        if (isLead.intValue() == ReviewConstants.SEND_PEOPLE.MANAGERUSER_1_OR_2.intValue()) {
            ReviewContactsDto record = new ReviewContactsDto();
            record.setUserid(wangId);
            List<ReviewContactsDto> data = woReviewContactsDataService.findListBySearch(record);
            if (data != null && data.size() > 0) {
                //获取领导电话信息
             /*   if ("1".equals(data.get(0).getIssend1())) {
                    phoneList.add(data.get(0).getPhone1());
                }*/
                if ("1".equals(data.get(0).getIssend2())) {
                    phoneList.add(data.get(0).getPhone2());
                }
            } else {
                throw new BusinessException("该工单没有匹配到责任人领导！");
            }
        } else if(isLead.intValue() == ReviewConstants.SEND_PEOPLE.MANAGERUSER_1.intValue()){
            ReviewContactsDto record = new ReviewContactsDto();
            record.setUserid(wangId);
            List<ReviewContactsDto> data = woReviewContactsDataService.findListBySearch(record);
            if (data != null && data.size() > 0) {
                //获取领导电话信息
                if ("1".equals(data.get(0).getIssend1())) {
                    phoneList.add(data.get(0).getPhone1());
                }
            } else {
                throw new BusinessException("该工单没有匹配到责任人领导！ reviewId:"+reviewId);
            }
        }else if(isLead.intValue() == ReviewConstants.SEND_PEOPLE.DUTY_PEOPLE.intValue()){
            //通过人员Id获取对应人员信息
            WOUser reviewUser = new WOUser();
            reviewUser.setUserId(wangId);

            List<WOUser> userList = woUserDataService.getListByParam(reviewUser);
            WOUser user = userList != null && userList.size() > 0 ? userList.get(0) : null;

            if (user != null) {//*******************************************************************
                String phone = user.getMobile();//获取人员电话
                phoneList.add(phone);
            } else {
                throw new BusinessException("该工单没有匹配到责任人！reviewId:"+reviewId);
            }

        }
        //通过工单号获取工单信息
        ReviewPool pool=new ReviewPool();
        pool.setId(reviewId);
        ReviewPool reviewPool = reviewPoolService.findReviewById(pool);
        //如果属于商家中心的工单的话
        if(null!=reviewPool.getStoreType()&&(
                reviewPool.getStoreType().equals("store")||reviewPool.getStoreType().equals("O2O")))
        {
            Members members = shopMembersService.get(Integer.valueOf(reviewPool.getStoreId()));
            if(members.getMobile()!=null) {
                List<ReviewSmsQueue> reviewSmsQueueList = new ArrayList<ReviewSmsQueue>();
                ReviewSmsQueue reviewSmsQueue = new ReviewSmsQueue();
                //接收者
                //reviewSmsQueue.setReceiver(phone);
                reviewSmsQueue.setReceiver(members.getMobile());
                //发送内容
                reviewSmsQueue.setInformation("您有新的投诉工单.工单信息,工单号:" + reviewPool.getNetOrderId() + ",收货人员:" + reviewPool.getBuyer() + ",责任位1:" + reviewPool.getQuestion1Level1() + ",责任位2:" + reviewPool.getQuestion1Level2() + ",请尽快处理操作并闭环");
                reviewSmsQueueList.add(reviewSmsQueue);
                if (reviewSmsQueueList.size() > 0) {
                    reviewSmsQueueService.addReviewSmsList(reviewSmsQueueList);
                }
            }else
            {

                System.out.println("商家中心手机为空,工单号为"+reviewPool.getId());
                throw new BusinessException("商家中心手机为空,工单号为"+reviewPool.getId());
            }

        }else{

            int question1Type=1;//定义1为 不需要发送工贸等信息的，2为需要发送工贸等信息的
            if(reviewPool!=null){
                if(ReviewConstants.QUESTION1LEVEL1.DUTY_1_1.equals(reviewPool.getQuestion1Level1())||ReviewConstants.QUESTION1LEVEL1.DUTY_1_9.equals(reviewPool.getQuestion1Level1())
                        ||"顺逛商城-O2O类".equals(reviewPool.getQuestion1Level1())
                        ||ReviewConstants.QUESTION1LEVEL1.DUTY_2_0.equals(reviewPool.getQuestion1Level1())){
                    //如果是物流类或者售后类
                    //新增o2o和社会化
                    question1Type=2;
                }
            }
            String addContent = "";
            if(question1Type==1){
                String info = smsSendContext;
                addContent = MessageFormat.format(info, type, netOrderId, productName, buyer,
                        buyerMobile, question1Level2, appealCount1, feedBackCount, reason);
            }else{
                String info = mailSendContext;
                String company = reviewPool.getCompany();
                company=company!=null?company:"";
                String phone = reviewPool.getPhone();
                phone=phone!=null?phone:"";
                String netPointId = reviewPool.getNetPointId();
                netPointId=netPointId!=null?netPointId:"";
                //网单号，产品型号，联系人+电话，第二责任位，申诉次数，咨询次数，用户反馈内容
                addContent = MessageFormat.format(info, type, netOrderId, productName, buyer,
                        buyerMobile,company,phone,netPointId,  question1Level2, appealCount1, feedBackCount, reason);
            }

            List<ReviewSmsQueue> reviewSmsQueueList = new ArrayList<ReviewSmsQueue>();
            for (String phone : phoneList) {
                ReviewSmsQueue reviewSmsQueue = new ReviewSmsQueue();
                //接收者
                //reviewSmsQueue.setReceiver(phone);
                reviewSmsQueue.setReceiver(phone);
                //发送内容
                reviewSmsQueue.setInformation(addContent);
                reviewSmsQueueList.add(reviewSmsQueue);
            }
            if (reviewSmsQueueList.size() > 0) {
                reviewSmsQueueService.addReviewSmsList(reviewSmsQueueList);
            }
        }
    }

    /**
     * 图片删除
     * 已经完成工单一周之后，将其中的图片信息删除。
     *
     * @throws Exception
     */
    @Override
    public void imageDel() throws Exception {

        // 根据工单状态，取得未处理的工单
        Calendar now = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        now.add(Calendar.DATE, -7);
        String compareTimeNow = df.format(now.getTime());

        // 已经完成工单一周之后，将其中的图片信息删除。
        reviewImageDataService.deleteByPoolColse(compareTimeNow);

    }

    /**
     *退换货订单完成后，对应的退款类工单自动关闭
     *
     */
    @Override
    public void poolCloseForRefund() throws Exception {
        // 取得退款类未关闭的工单
        List<ReviewPool> data = reviewPoolService.getPoolByQuestion1Level1(ReviewConstants.QUESTION1LEVEL1.DUTY_1_3,
                null, ReviewConstants.REMARK5.REMARK5_1);

//        ReviewPool updPool = new ReviewPool();
//        for (ReviewPool reviewPool : data) {
//
//            Calendar now = Calendar.getInstance();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//            // 根据订单id取得退换货订单状态
//            OrderBean order = reviewWorkOrderModel.gdOrderByOrder(reviewPool.getOrderId());
//            // OrderBean order = reviewWorkOrderModel.gdOrderById("7466103");
//            if (order != null && order.getSuccess() && order.getResult() != null) {
//                // 订单完成后，对应的工单关闭
//                if (ReviewConstants.ORDERSTATUS.OS_COMPLETE.equals(order.getResult().getOrderStatus())) {
//                    updPool = new ReviewPool();
//                    updPool.setId(reviewPool.getId());
//                    updPool.setWorkStatus(ReviewConstants.WORKSTATUS.FINALRESULT);
//                    updPool.setRemark2(ReviewConstants.SYS.SYSTEM);
//                    // 最终结果添加时间
//                    updPool.setPosition3(df.format(now.getTime()));
//                    // 最终结果（已退款、记录退款时间、操作人：退款操作人）
//                    String info = ReviewPropertyHolder.getContextProperty("words.refundFinalResult");
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    updPool.setBackContext3(MessageFormat.format(info,
//                            sdf.format(new Date(Long.parseLong(order.getResult().getFinishTime() + "000")))));
//
//                    // 订单关闭原因
//                    updPool.setCloseType(ReviewConstants.CLOSETYPE.CLOSETYPE_1);
//                    reviewPoolWriteDao.updWorkOrder(updPool);
//                }
//            }
//        }

    }

    /**
     * 物流类工单，网单完成关闭后，需要将对应工单关闭。
     *
     * @throws Exception
     */
    @Override
    public void poolCloseForLogistics() throws Exception {
        // 取得物流类未关闭的工单
//        List<ReviewPool> data = reviewPoolService.getPoolByQuestion1Level1s();
//
//        ReviewPool updPool = new ReviewPool();
//        for (ReviewPool reviewPool : data) {
//
//            Calendar now = Calendar.getInstance();
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            // 根据网单号取得物流类网单状态
//            COrderBean corder = reviewWorkOrderModel.gdQueryOrderProductsByCOrderSn(reviewPool.getNetOrderId());
//            if (corder != null && corder.getSuccess() && corder.getResult() != null) {
//                // 如果责任位2是退换货并且网单状态为取消关闭，或者责任位2不是退换货并且网单状态为完成关闭，则自动关闭对应工单
//                // 2017.4.7改动,退换货类改为全部不需要自动关单,移除过滤条件:
//                // (ReviewConstants.QUESTION1LEVEL2.DUTY_2_1.equals(reviewPool
//                // .getQuestion1Level2()) &&
//                // ReviewConstants.STATUS.CANCEL_CLOSE.equals(corder
//                // .getResult().getStatus()))
//                // ||
//                System.out.println(ReviewConstants.QUESTION1LEVEL2.DUTY_2_1 + "--" + reviewPool.getQuestion1Level2());
//                System.out.println(ReviewConstants.STATUS.COMPLETED_CLOSE + "--" + corder.getResult().getStatus());
//                if ((!ReviewConstants.QUESTION1LEVEL2.DUTY_2_1.equals(reviewPool.getQuestion1Level2())
//                        && ReviewConstants.STATUS.COMPLETED_CLOSE.equals(corder.getResult().getStatus()))) {
//                    updPool = new ReviewPool();
//                    updPool.setId(reviewPool.getId());
//                    updPool.setWorkStatus(ReviewConstants.WORKSTATUS.FINALRESULT);
//                    updPool.setRemark2(ReviewConstants.SYS.SYSTEM);
//                    // 最终结果添加时间
//                    updPool.setPosition3(df.format(now.getTime()));
//                    // 最终结果（已完成、时间记录关闭时间、操作人：系统）
//                    String info = ReviewPropertyHolder.getContextProperty("words.logisticsFinalResult");
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    updPool.setBackContext3(MessageFormat.format(info,
//                            sdf.format(new Date(Long.parseLong(corder.getResult().getCloseTime() + "000")))));
//
//                    // 订单关闭原因
//                    updPool.setCloseType(ReviewConstants.CLOSETYPE.CLOSETYPE_1);
//                    reviewPoolWriteDao.updWorkOrder(updPool);
//                }
//            }
//        }
    }

    @Override
    public void sendSms() throws Exception {
        // 取得未发送的短信推送数据

        List<ReviewSmsQueue> data = reviewSmsQueueService.selectSendSms();

        ReviewSmsQueue smsQueue = new ReviewSmsQueue();
        for (ReviewSmsQueue reviewSms : data) {
            Map<String, Object> params = new HashMap<String, Object>();

            String mobile = reviewSms.getReceiver();
            String message = reviewSms.getInformation();

            // 调接口发送短信，取得发送结果
            boolean sendResult = false;
            try {

                sendSMS(mobile, message);
                sendResult = true;
            } catch (Exception e) {
                log.error("短信发送接口调用异常", e);
            }

            smsQueue = new ReviewSmsQueue();
            smsQueue.setId(reviewSms.getId());
            if (sendResult) {
                // 是否已经发送
                smsQueue.setIssend(1);
                // 成功发送时间
                smsQueue.setSendtime(new Date());
            } else {
                // 尝试发送次数
                smsQueue.setTrytime(reviewSms.getTrytime() + 1);
            }
            reviewSmsQueueService.updateByPrimaryKeySelective(smsQueue);
        }


    }

    @Override
    public void sendMail() throws Exception {
        // 取得未发送的邮件推送数据
        List<ReviewMailQueue> data = reviewMailQueueService.selectSendMail();

        ReviewMailQueue mailQueue = new ReviewMailQueue();
        for (ReviewMailQueue reviewMail : data) {

            QueueMails queueMails = new QueueMails();
            queueMails.setSiteId(1);
            // 主题
            queueMails.setSubject("当前月工单完成率汇总");
            // 发送内容
            queueMails.setBody(reviewMail.getInformation());
            // 接收者邮箱
            queueMails.setTo(reviewMail.getReceiver());
            // 优先级
            queueMails.setPriority(1);
            // 添加时间
            queueMails.setAddTime(Integer.parseInt(String.valueOf(reviewMail.getAddtime().getTime()).substring(0, 10)));
            // 已发送次数
            queueMails.setSentTimes(0);
            // 上次发送时间
            queueMails.setLastSentTime(0);
            // 上次错误信息
            queueMails.setLastErrorMessage("");

            queueMails.setDeadline(0);

            mailQueue = new ReviewMailQueue();

            mailQueue.setId(reviewMail.getId());
            try {
                sendMailToGateway(queueMails);
                // 是否已经发送
                mailQueue.setIssend(1);
                // 成功发送时间
                mailQueue.setSendtime(new Date());
            }catch (Exception e){
                log.error("sendMailToGateway error", e);
                // 尝试发送次数
                mailQueue.setTrytime(reviewMail.getTrytime() + 1);

            }

            reviewMailQueueService.updateByPrimaryKeySelective(mailQueue);
        }

    }

    @Override
    public void sendMailDutyStatistic() throws Exception {

        // 当前时间
        Calendar now = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        now.add(Calendar.YEAR, -1);
        now.set(Calendar.DAY_OF_MONTH, 1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        // 当前月开始时间
        String monthStartTime = df.format(now.getTime());
        // 当前月工单信息
        List<Map<String, Object>> data = reviewPoolService.sendMailDutyStatistic(monthStartTime);

        // 同一用户下责任位行的内容
        StringBuffer sbQuestion1Level2 = new StringBuffer();
        // 用户的邮件内容
        StringBuffer sbMailText = new StringBuffer();

        // 邮件内容标题
        now = Calendar.getInstance();
        String month = (now.get(Calendar.MONTH) + 1) + "";
        String day = now.get(Calendar.DAY_OF_MONTH) + "";
        String strHead = "截至到" + month + "月" + day + "日海尔商城当月工单信息汇总";
        sbMailText.append("<table cellspacing=\"0\" border=\"1\"><tr><td colspan = \"6\" align=\"center\">")
                .append(strHead).append("</td></tr>");
        sbMailText.append("<tr><td align=\"center\">").append("责任位").append("</td>");
        sbMailText.append("<td align=\"center\">").append("订单来源").append("</td>");
        sbMailText.append("<td align=\"center\">").append("未处理").append("</td>");
        sbMailText.append("<td align=\"center\">").append("中间结果").append("</td>");
        sbMailText.append("<td align=\"center\">").append("最终结果").append("</td>");
        sbMailText.append("<td align=\"center\">").append("完成率").append("</td></tr>");

        // 上一条记录的用户ID
        String oldUserId = "";
        // 上一条记录的二级责任位
        String oldQuestion1Level2 = "";
        // 上一条记录的邮件地址
        String oldEmail = "";

        int sumUnsettled = 0;
        int sumMiddle = 0;
        int sumFinally = 0;

        // 用来代替数量的特殊字符
        String reCount = "ū";
        // 记录同一责任位下订单来源数量
        int countOrderCome = 0;
        for (Map<String, Object> map : data) {
            String userId = (String) map.get("id");
            String question1Level2 = (String) map.get("question1Level2");
            String orderCome = (String) map.get("orderCome");
            String email = (String) map.get("email");
            // 获取状态 0：未处理 2：中间结果 3：最终结果
            String workStatus = (String) map.get("workStatus");
            // 数量
            String num = map.get("num") + "";
            List<String> workStatusList = Arrays.asList(workStatus.split(","));
            List<String> numList = Arrays.asList(num.split(","));
            String unsettledNum = "0";
            String middleNum = "0";
            String finallyNum = "0";

            for (int i = 0; i < numList.size(); i++) {
                String workStatusF = workStatusList.get(i);
                String numF = numList.get(i);
                if ("0".equals(workStatusF)) {
                    unsettledNum = numF;
                } else if ("2".equals(workStatusF)) {
                    middleNum = numF;
                } else if ("3".equals(workStatusF)) {
                    finallyNum = numF;
                }
            }

            // 换用户之后，给前一个用户发送邮件
            if (!userId.equals(oldUserId)) {
                if (!"".equals(oldUserId) && oldUserId != null) {
                    if (countOrderCome != 0) {
                        // 用订单来源数量，替换掉责任位代替合并单元格数量的特殊字符
                        sbQuestion1Level2.replace(sbQuestion1Level2.indexOf(reCount),
                                sbQuestion1Level2.indexOf(reCount) + 1, countOrderCome + "");
                    }
                    // 添加合计行

                    sbQuestion1Level2.append("<tr><td align=\"center\">").append("合计").append("</td>");
                    sbQuestion1Level2 = addTd(sbQuestion1Level2, "", String.valueOf(sumUnsettled),
                            String.valueOf(sumMiddle), String.valueOf(sumFinally));

                    sbQuestion1Level2.append("</table>");

                    QueueMails queueMails = new QueueMails();
                    queueMails.setSiteId(0);
                    // 主题
                    queueMails.setSubject("当前月工单完成率汇总");
                    // 发送内容
                    queueMails.setBody(sbMailText.toString() + sbQuestion1Level2.toString());
                    // 接收者邮箱
                    queueMails.setTo(oldEmail);
                    // 优先级
                    queueMails.setPriority(1);
                    // 添加时间
                    queueMails.setAddTime(Integer.parseInt(String.valueOf(now.getTimeInMillis()).substring(0, 10)));
                    // 已发送次数
                    queueMails.setSentTimes(0);
                    // 上次发送时间
                    queueMails.setLastSentTime(0);
                    // 上次错误信息
                    queueMails.setLastErrorMessage("");

                    sendMailToGateway(queueMails);

                    // 合计数据初始化
                    sumUnsettled = 0;
                    sumMiddle = 0;
                    sumFinally = 0;
                    // 重新开始记录同一责任位下，订单来源数量
                    countOrderCome = 0;
                }
                sbQuestion1Level2 = new StringBuffer();
            }

            // 合计统计
            sumUnsettled = sumUnsettled + Integer.parseInt(unsettledNum);
            sumMiddle = sumMiddle + Integer.parseInt(middleNum);
            sumFinally = sumFinally + Integer.parseInt(finallyNum);

            // 责任位变换之后，新加一个责任位行
            if (!question1Level2.equals(oldQuestion1Level2)) {
                if (countOrderCome != 0) {
                    // 用订单来源数量，替换掉责任位代替合并单元格数量的特殊字符
                    sbQuestion1Level2.replace(sbQuestion1Level2.indexOf(reCount),
                            sbQuestion1Level2.indexOf(reCount) + 1, countOrderCome + "");
                }
                // 重新开始记录同一责任位下，订单来源数量
                countOrderCome = 0;
                sbQuestion1Level2.append("<tr><td rowspan = \"").append(reCount).append("\">").append(question1Level2)
                        .append("</td>");
                sbQuestion1Level2 = addTd(sbQuestion1Level2, orderCome, unsettledNum, middleNum, finallyNum);
                countOrderCome++;
            } else {
                // 同一责任位下，新加一个订单来源行
                sbQuestion1Level2.append("<tr>");
                sbQuestion1Level2 = addTd(sbQuestion1Level2, orderCome, unsettledNum, middleNum, finallyNum);
                countOrderCome++;
            }
            oldQuestion1Level2 = question1Level2;
            oldUserId = userId;
            oldEmail = email;
        }
        // 最后一个用户的邮件信息
        if (!"".equals(oldUserId) && oldUserId != null) {
            if (countOrderCome != 0) {
                // 用订单来源数量，替换掉责任位代替合并单元格数量的特殊字符
                sbQuestion1Level2.replace(sbQuestion1Level2.indexOf(reCount), sbQuestion1Level2.indexOf(reCount) + 1,
                        countOrderCome + "");
            }
            // 添加合计行
            sbQuestion1Level2.append("<tr><td align=\"center\">").append("合计").append("</td>");
            sbQuestion1Level2 = addTd(sbQuestion1Level2, "", String.valueOf(sumUnsettled), String.valueOf(sumMiddle),
                    String.valueOf(sumFinally));
            sbQuestion1Level2.append("</table>");

            QueueMails queueMails = new QueueMails();
            queueMails.setSiteId(0);
            // 主题
            queueMails.setSubject("当前月工单完成率汇总");
            // 发送内容
            queueMails.setBody(sbMailText.toString() + sbQuestion1Level2.toString());
            // 接收者邮箱
            queueMails.setTo(oldEmail);
            // 优先级
            queueMails.setPriority(1);
            // 添加时间
            queueMails.setAddTime(Integer.parseInt(String.valueOf(now.getTimeInMillis()).substring(0, 10)));
            // 已发送次数
            queueMails.setSentTimes(0);
            // 上次发送时间
            queueMails.setLastSentTime(0);
            // 上次错误信息
            queueMails.setLastErrorMessage("");
            sendMailToGateway(queueMails);
        }

    }

    private StringBuffer addTd(StringBuffer strbf, String orderCome, String unsettledNum, String middleNum,
                               String finallyNum) {
        strbf.append("<td>").append(orderCome).append("</td>");
        strbf.append("<td>").append(unsettledNum).append("</td>");
        strbf.append("<td>").append(middleNum).append("</td>");
        strbf.append("<td>").append(finallyNum).append("</td>");
        String percent = String.format("%.1f", (Double.parseDouble(finallyNum)
                / (Double.parseDouble(unsettledNum) + Double.parseDouble(middleNum) + Double.parseDouble(finallyNum))
                * 100)) + "%";
        strbf.append("<td>").append(percent).append("</td></tr>");
        return strbf;
    }

    /**
     *
     * 发送工单到sqm 天猫渠道工单不需发送到sqm
     *
     * @throws Exception
     */
    @Override
    public void sendOrderToSQM() throws Exception {

    }

    /**
     * 天猫渠道工单不需发送到sqm
     *
     * @throws Exception
     */
    @Override
    public void judgeResultToSQM() throws Exception {

    }

    /**
     * 推送订单到HP
     * @throws Exception
     */
    @Override
    public void addWodataToHP() throws Exception {

        List<ReviewPool> sendHpList = new ArrayList<ReviewPool>();
        sendHpList = reviewPoolService.getHPOrderByStatusAndTO("0");
        String ip = "";
        String time = "";
        long timeLong = 0;
        String timeStr = "";
        String require_service_date = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            time = System.currentTimeMillis() + "";
        } catch (Exception e) {
            log.error("addWodataToHP error", e);
        }
        if (sendHpList != null && sendHpList.size() > 0) {
            for (ReviewPool dto : sendHpList) {
                ReviewPool update = new ReviewPool();
                int count = Integer.parseInt(dto.getSqmCount()) + 1;
                if (count == 0) {
                    count = 1;
                }
                // 组织HP要求的参数格式
                Map paraMap = new HashMap();
                Map region;
                try {

                    region = shopOrdersService.getRegionByOrderSn(dto.getOrderId());

                } catch (Exception e) {
                    log.error("addWodataToHP error", e);
                    region = null;
                }
                paraMap.put("apply_id", dto.getId());

                dto = this.getProdtypeFromHP(dto);

                if (null != region && null != region.get("code")) {
                    paraMap.put("district", region.get("code"));

                    ThirdPartyLiabilityCondition thirdParams = new ThirdPartyLiabilityCondition();
                    thirdParams.setQuestion1Level1(dto.getQuestion1Level1());
                    thirdParams.setQuestion1Level2(dto.getQuestion1Level2());
                    thirdParams.setQuestion1Level3(dto.getQuestion1Level3());

                    ThirdPartyLiabilityCondition thirds = woDictionaryDataService.findInfoFromThird(thirdParams);

                    if (null != thirds) {
                        if (thirds.getThirdPartyType().equals("HP")) {
                            paraMap.put("service_type", thirds.getServiceType());
                            if (null != dto.getProductCode() && !"".equals(dto.getProductCode())) {
                                try {
                                    String addTime = DateUtil.format(new Date(), ReviewConstants.TIME.FORMAT_DATE);

                                    Date date = format.parse(addTime);
                                    timeLong = date.getTime();
                                    timeLong += 2 * 60 * 60 * 1000;
                                    Date da = new Date(timeLong);
                                    require_service_date = format.format(da);
                                    long curren = System.currentTimeMillis();
                                    da = new Date(curren);
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    timeStr = dateFormat.format(da);
                                } catch (ParseException e) {
                                    log.error("addWodataToHP error", e);
                                }
                                paraMap.put("product_id", dto.getProductCode());
                                paraMap.put("product_series", dto.getNetOrderId());
                                paraMap.put("require_service_date", require_service_date);
                                paraMap.put("customer_name", dto.getBuyer());
                                paraMap.put("mobile_phone", dto.getBuyerMobile());
                                paraMap.put("phone", dto.getBuyerMobile());
                                paraMap.put("address", dto.getRegionName() + "  " + dto.getAddress());
                                paraMap.put("require_service_desc", dto.getContext());
                                paraMap.put("service_time", timeStr);
                                paraMap.put("userip", ip);
                                paraMap.put("source_code", "276");
                                paraMap.put("client_type", "0");
                                paraMap.put("method", "rrs.order.addwodata");
                                paraMap.put("timestamp", time);
                                paraMap.put("access_token", "ZDZlZDVlNDItYmE3MC00YjI1LTgwZGItMzNjZGYxZmIwZDU0");
                                String url = urlToHP;
                                try {
                                    String result = WebUtils.doPost(url, paraMap);
                                    Map<String, Object> paramMap = new HashMap<String, Object>();
                                    try {
                                        paramMap = JSON.parse(result, Map.class);
                                    } catch (com.alibaba.dubbo.common.json.ParseException e) {
                                        log.error("addWodataToHP error", e);
                                    }
                                    String status = paramMap.get("status").toString();
                                    if ("ok".equals(status)) {
                                        update.setSqmCount(count + "");
                                        if (Integer.parseInt(update.getSqmCount()) == 0) {
                                            update.setSqmCount("1");
                                        }
                                        update.setId(dto.getId());
                                        update.setSqmStatus(ReviewConstants.SQM_STATUS.COMPLETE);
                                        update.setDesideText("HP已入单，等待HP返回中间结果");

                                        reviewPoolService.updWorkOrder(update);
                                        String HPOrderId = paramMap.get("data").toString();
                                        reviewPoolService.addHpOrderId(dto.getId(), HPOrderId);
                                    } else {
                                        update.setId(dto.getId());
                                        update.setDesideText(status + ":" + paramMap.get("msg").toString());
                                        reviewPoolService.updWorkOrder(update);
                                        // TODO 发到HP失败
                                        try {
                                            log.error("下发到HP失败，五分钟后重试。" + dto.getId() + "----" + status + ":"
                                                    + paramMap.get("message").toString());
                                        } catch (Exception e) {
                                            log.error("addWodataToHP error", e);
                                            continue;
                                        }
                                    }
                                } catch (IOException e) {
                                    log.error("传送订单失败,订单号为," + dto.getId(), e);
                                    continue;
                                }
                            } else {
                                update.setId(dto.getId());
                                update.setDesideText("HP返回的产品大类是空的");
                                update.setSqmStatus("4");
                                reviewPoolService.updWorkOrder(update);
                            }
                        }
                    } else {
                        update.setId(dto.getId());
                        update.setDesideText("服务类型没获取到数据,工单号为" + dto.getId());
                        update.setSqmStatus("4");
                        reviewPoolService.updWorkOrder(update);
                    }
                } else {
                    update.setId(dto.getId());
                    update.setDesideText("国标码未获取到");
                    update.setSqmStatus("4");
                    reviewPoolService.updWorkOrder(update);
                }
            }
        }

    }

    /**
     * 从HP查询产品大类
     */
    private ReviewPool getProdtypeFromHP(ReviewPool dto) {
        String time = "";
        try {
            time = System.currentTimeMillis() + "";
        } catch (Exception e) {
            log.error("getProdtypeFromHP error", e);
        }
        new ReviewPool();
        // 组织HP要求的参数格式
        Map paraMap = new HashMap();
        paraMap.put("code", dto.getSku());
        paraMap.put("method", "rrs.basic.prodtype.getprodtype");
        paraMap.put("timestamp", time);
        paraMap.put("access_token", "ZDZlZDVlNDItYmE3MC00YjI1LTgwZGItMzNjZGYxZmIwZDU0");
        String url = urlToHP;
        try {
            String result = WebUtils.doPost(url, paraMap);
            Map<String, Object> hsMap = new HashMap<String, Object>();
            try {
                hsMap = JSON.parse(result, Map.class);
            } catch (com.alibaba.dubbo.common.json.ParseException e) {
                e.printStackTrace();
            }
            String status = hsMap.get("status").toString();
            String data = hsMap.get("data").toString();
            if ("ok".equals(status)) {
                if (data.equals("[]")) {
                    return dto;
                } else {
                    try {
                        Map<String, Object> map = JSON.parse(result, Map.class);
                        String pcode = ((List<Map<String, Object>>) map.get("data")).get(0).get("product_code")
                                .toString();
                        reviewPoolService.updateProductCode(dto.getId(), pcode);
                        dto.setProductCode(pcode);
                        return dto;
                    } catch (Exception e) {
                        log.error("调用查询HP大类失败,单号为," + dto.getId(), e);
                        return dto;
                    }
                }
            } else {
                log.error("调用查询HP大类失败,单号为," + dto.getId() + "返回的结果为" + result);
                return dto;
            }
        } catch (Exception e) {
            log.error("调用查询HP大类失败,单号为," + dto.getId(), e);
            return dto;
        }
    }


    /**
     * 给HP推送评价结果
     * @throws Exception
     */
    @Override
    public void sendPjToHp() throws Exception {
        List<ReviewPool> sendHpList = new ArrayList<ReviewPool>();
        sendHpList = reviewPoolService.getHPOrderBySqmStatusAndTO();
        String time = "";
        try {
            time = System.currentTimeMillis() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != sendHpList && sendHpList.size() > 0) {
            ReviewPool update = new ReviewPool();
            for (ReviewPool dto : sendHpList) {
                // 组织HP要求的参数格式
                Map paraMap = new HashMap();
                if (null != dto.getHpOrderId() && !"".equals(dto.getHpOrderId())) {
                    paraMap.put("wo_id", dto.getHpOrderId());
                } else {
                    log.error("HP单号为空");
                    continue;
                }
                if (dto.getDesidePass().equals("1")) {
                    paraMap.put("satis_check", "Y");
                } else {
                    paraMap.put("satis_check", "N");
                }

                paraMap.put("unsatisfy_reason", dto.getBackContext3());
                paraMap.put("cus_remark", "意见与建议");
                paraMap.put("client_type", "0");
                paraMap.put("data_source", "shunguang");
                paraMap.put("method", "rrs.hcsp.pj.savepj");
                paraMap.put("timestamp", time);
                paraMap.put("access_token", "ZDZlZDVlNDItYmE3MC00YjI1LTgwZGItMzNjZGYxZmIwZDU0");
                String url = urlToHP;
                try {
                    String result = WebUtils.doPost(url, paraMap);
                    Map<String, Object> paramMap = new HashMap<String, Object>();
                    try {
                        paramMap = JSON.parse(result, Map.class);
                    } catch (com.alibaba.dubbo.common.json.ParseException e) {
                        e.printStackTrace();
                    }
                    String status = paramMap.get("status").toString();
                    if ("ok".equals(status)) {
                        update.setId(dto.getId());
                        update.setDesidePass(ReviewConstants.JUDGE_STATUS.SENT_SQM);
                        update.setSqmStatus("0");
                        update.setBackContext3("");
                        update.setSqmCount(dto.getSqmCount());
                        int num = 0;
                        if (dto.getDesidePass().equals("1")) {
                            update.setSqmStatus("5");
                            update.setDesideText("HP流转结束,工单确认通过,工单已处理完成");
                            update.setWorkStatus("3");
                            num = 1;
                        }
                        if (num != 1) {
                            update.setDesideText("HP已关单，稍后将再次发起工单推送,工单状态：不通过");
                            update.setWorkStatus("2");
                        }
                        reviewPoolService.updWorkOrder(update);
                    } else {
                        log.error("评价提交失败，失败原因：" + paramMap.get("msg").toString());
                    }
                } catch (IOException e) {
                    log.error("评价提交任务异常", e);
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void sendSMS(String phoneNo, String content) {
        try {
            URL url = new URL("http://10.128.3.249:8080/HttpApi_Simple/submitMessage");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "close");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(5000);
            conn.connect();
            String inputXML = "<?xml version=\"1.0\" encoding=\"GBK\"?>"
                    + "<CoreSMS>" +
                    "<OperID>haierb2b</OperID>"
                    + "<OperPass>haierb2b</OperPass>"
                    + "<Action>Submit</Action>" + "<Category>0</Category>"
                    + "<Body>" + "<SendTime></SendTime>"
                    + "<AppendID></AppendID>" + "<Message>" + "<DesMobile>"
                    + phoneNo + "</DesMobile>"
                    + "<Content><![CDATA[" + "【海尔】" + content + "]]></Content>" + "</Message>" +
                    // 可加多个Message标签
                    "</Body>" + "</CoreSMS>";
            byte[] b = inputXML.getBytes("gbk");
            OutputStream os = conn.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.write(b);
            dos.flush();
            os.close();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            int r = bis.read();
            while (r >= 0) {
                bos.write(r);
                r = bis.read();
            }
            String outputXML = new String(bos.toByteArray(), "gbk");
            System.out.println(""+phoneNo+":"+content);
            System.out.println(outputXML);
            is.close();
            conn.disconnect();
            dos.close();
            bis.close();
        } catch (IOException ex2) {
            log.error("sendSMS eror", ex2);
        }
    }

    @Override
    public void sendMailToGateway(QueueMails queueMails) throws Exception{
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hbdm@haier.net");
        message.setTo(queueMails.getTo());
        message.setSubject("当前月工单完成率汇总");
        message.setText(queueMails.getBody());
        mailSender.send(message);

    }


//    public void sendmail(){
//        String[] tos = {"rj.xup@haier.com","7093386@qq.com"};
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("hbdm@haier.net");
//        message.setTo(tos);
//        message.setSubject("工单上诉提醒");
//        message.setText("工单测试邮件内容");
//        mailSender.send(message);
//
//
//    }

}
