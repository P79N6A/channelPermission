package com.haier.shop.services;

import com.haier.common.BusinessException;
import com.haier.shop.dao.shopread.MembersReadDao;
import com.haier.shop.dao.workorder.ReviewPoolDao;
import com.haier.shop.dao.workorder.ReviewSmsQueueDao;
import com.haier.shop.dao.workorder.WOUserDao;
import com.haier.shop.dao.workorder.WoReviewContactsDao;
import com.haier.shop.dto.ReviewContactsDto;
import com.haier.shop.model.Members;
import com.haier.shop.model.ReviewPool;
import com.haier.shop.model.ReviewSmsQueue;
import com.haier.shop.model.WOUser;
import com.haier.shop.util.ReviewConstants;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 短信操作
 *                       
 * @Filename: ReviewSmsQueueModel.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
@Service
public class ReviewSmsQueueModel {
    private static String          DEFAULT = "";
    @Resource
    private ReviewSmsQueueDao reviewSmsQueueDao;
    @Resource
    private WOUserDao woUserDao;
    @Resource
    private WoReviewContactsDao  woReviewContactsDao;
    @Resource
    private ReviewPoolDao reviewPoolDao;

/*
    @Resource
    private  NoticeService noticeService;
*/


    @Resource
    private MembersReadDao membersReadDao;
    @Value("${workorder.smssend}")
    private String smssend;
    @Value("${workorder.mailsend}")
    private String mailsend;


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
        if (isLead==ReviewConstants.SEND_PEOPLE.MANAGERUSER_1_OR_2) {
            ReviewContactsDto record = new ReviewContactsDto();
            record.setUserid(wangId);
            List<ReviewContactsDto> data = woReviewContactsDao.findList(record);
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
        } else if(isLead==ReviewConstants.SEND_PEOPLE.MANAGERUSER_1){
        	 ReviewContactsDto record = new ReviewContactsDto();
             record.setUserid(wangId);
             List<ReviewContactsDto> data = woReviewContactsDao.findList(record);
             if (data != null && data.size() > 0) {
                 //获取领导电话信息
                 if ("1".equals(data.get(0).getIssend1())) {
                     phoneList.add(data.get(0).getPhone1());
                 }
             } else {
                 throw new BusinessException("该工单没有匹配到责任人领导！ reviewId:"+reviewId);
             }
        }else if(isLead==ReviewConstants.SEND_PEOPLE.DUTY_PEOPLE){
            //通过人员Id获取对应人员信息
            WOUser user = woUserDao.getById(wangId);
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
        ReviewPool reviewPool = reviewPoolDao.findReviewById(pool);
        //如果属于商家中心的工单的话
        if(null!=reviewPool.getStoreType()&&(
                reviewPool.getStoreType().equals("store")||reviewPool.getStoreType().equals("O2O")))
        {
            Members members=membersReadDao.getMembers(reviewPool.getStoreId());
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
                    reviewSmsQueueDao.addReviewSmsList(reviewSmsQueueList);
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
                                addContent = MessageFormat.format(smssend, type, netOrderId, productName, buyer,
                                        buyerMobile, question1Level2, appealCount1, feedBackCount, reason);
                            }else{
                                String company = reviewPool.getCompany();
                                company=company!=null?company:"";
                                String phone = reviewPool.getPhone();
                                phone=phone!=null?phone:"";
                                String netPointId = reviewPool.getNetPointId();
                                netPointId=netPointId!=null?netPointId:"";
                                //网单号，产品型号，联系人+电话，第二责任位，申诉次数，咨询次数，用户反馈内容
                                addContent = MessageFormat.format(mailsend, type, netOrderId, productName, buyer,
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
                                reviewSmsQueueDao.addReviewSmsList(reviewSmsQueueList);
                            }
       }
    }

    public void sendMessageToStore(String type,String reviewId, String netOrderId, String productName, String wangId,
                                 String buyer, String buyerMobile, String question1Level2,
                                 Integer appealCount1, Integer feedBackCount, String reason, String storePhone) {

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
        phoneList.add(storePhone);
        //通过工单号获取工单信息
        ReviewPool pool=new ReviewPool();
        pool.setId(reviewId);
        ReviewPool reviewPool = reviewPoolDao.findReviewById(pool);
        int question1Type=1;//定义1为 不需要发送工贸等信息的，2为需要发送工贸等信息的
        if(reviewPool!=null){
            if(ReviewConstants.QUESTION1LEVEL1.DUTY_1_1.equals(reviewPool.getQuestion1Level1())||ReviewConstants.QUESTION1LEVEL1.DUTY_1_9.equals(reviewPool.getQuestion1Level1())){
                //如果是物流类或者售后类
                question1Type=2;
            }
        }
        String addContent = "";
        if(question1Type==1){
            addContent = MessageFormat.format(smssend, type, netOrderId, productName, buyer,
                    buyerMobile, question1Level2, appealCount1, feedBackCount, reason);
        }else{
            String company = reviewPool.getCompany();
            company=company!=null?company:"";
            String phone = reviewPool.getPhone();
            phone=phone!=null?phone:"";
            String netPointId = reviewPool.getNetPointId();
            netPointId=netPointId!=null?netPointId:"";
            //网单号，产品型号，联系人+电话，第二责任位，申诉次数，咨询次数，用户反馈内容
            addContent = MessageFormat.format(mailsend, type, netOrderId, productName, buyer,
                    buyerMobile,company,phone,netPointId, question1Level2, appealCount1, feedBackCount, reason);
        }

        List<ReviewSmsQueue> reviewSmsQueueList = new ArrayList<ReviewSmsQueue>();
        for (String phone : phoneList) {
            ReviewSmsQueue reviewSmsQueue = new ReviewSmsQueue();
            //接收者
            reviewSmsQueue.setReceiver(phone);
            //发送内容
            reviewSmsQueue.setInformation(addContent);
            reviewSmsQueueList.add(reviewSmsQueue);
        }
        if (reviewSmsQueueList.size() > 0) {
            reviewSmsQueueDao.addReviewSmsList(reviewSmsQueueList);
        }
    }
}
