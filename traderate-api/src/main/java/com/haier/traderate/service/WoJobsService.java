package com.haier.traderate.service;

import com.haier.shop.model.QueueMails;

/**
 * Created by xupeng on 18/4/26.
 */
public interface WoJobsService {

    /**
     * 工单生成后两小时未处理，工单上诉件数+1
     *
     */
    public void automaticAppeal() throws Exception;

    /**
     *工单有了中间结果之后，48小时未处理，中间结果上诉件数+1
     *
     */
    public void middleAppeal() throws Exception;

    /**
     * 图片删除
     *
     * @throws Exception
     */
    public void imageDel() throws Exception;

    /**
     *退换货订单完成后，对应的退款类工单自动关闭
     *
     */
    public void poolCloseForRefund() throws Exception;

    /**
     * 物流类工单，网单完成关闭后，需要将对应工单关闭。
     *
     * @throws Exception
     */
    public void poolCloseForLogistics() throws Exception;


    /**
     * 短信推送
     * @throws Exception
     */
    public void sendSms() throws Exception;

    /**
     * 邮件推送
     * @throws Exception
     */
    public void sendMail() throws Exception;

    /**
     * 工单完成率定时邮件推送
     * @throws Exception
     */
    public void sendMailDutyStatistic() throws Exception;

    /**
     * 发送工单到sqm
     * @throws Exception
     */
    public void sendOrderToSQM() throws Exception;

    /**
     * 推送判定结果到SQM
     * @throws Exception
     */
    public void judgeResultToSQM() throws Exception;

    /**
     * 推送订单到HP
     * @throws Exception
     */
    public void addWodataToHP() throws Exception;

    /**
     * 给HP推送评价结果
     * @throws Exception
     */
    public void sendPjToHp() throws Exception;

    public void sendSMS(String phoneNo, String content);

    public void sendMailToGateway(QueueMails queueMails) throws Exception;

}
