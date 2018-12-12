package com.haier.shop.model;

import java.io.Serializable;

/**
 * @author 燕如朋
 * @date 2014-11-5
 * @email yanrp110428@dhc.com.cn
 */
public class InvoiceSAPLogs implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long   serialVersionUID = -4522752341578620616L;
    /**
     * 推送类型-普通开票
     * */
    public static final Integer PUSHTYPE_NORMAL  = 1;                    //普通开票
    /**
     * 推送类型-作废/冲红
     */
    public static final Integer PUSHTYPE_INVALID = 2;                    //作废/冲红
    /**
     * 推送类型-二次开票
     */
    public static final Integer PUSHTYPE_VOUCHER = 3;                    //二次开票

    private Integer id;                                      //ID
    private Integer addTime;                                 //添加时间
    private Integer invoiceId;                               //发票ID
    private Integer cOrderType;                              //网单类型
    private Integer orderProductId;                          //网单ID
    private Integer diffId;                                  //差异网单ID/专项开票ID
    private Integer pushType;                                //推送类型，1：普通开票；2：红冲发票；3：二次开票
    private String pushData;                                //推送数据
    private String returnData;                              //响应数据
    private Integer success;                                 //是否成功
    private Integer count;                                   //推送次数
    private Integer lastTime;                                //最后一次推送时间
    private String lastMessage;                             //最后一次推送信息

    /**
     * @return Returns the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     * The id to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return Returns the addTime
     */
    public Integer getAddTime() {
        return addTime;
    }

    /**
     * @param addTime
     * The addTime to set.
     */
    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    /**
     * @return Returns the invoiceId
     */
    public Integer getInvoiceId() {
        return invoiceId;
    }

    /**
     * @param invoiceId
     * The invoiceId to set.
     */
    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * @return Returns the cOrderType
     */
    public Integer getcOrderType() {
        return cOrderType;
    }

    /**
     * @param cOrderType
     * The cOrderType to set.
     */
    public void setcOrderType(Integer cOrderType) {
        this.cOrderType = cOrderType;
    }

    /**
     * @return Returns the orderProductId
     */
    public Integer getOrderProductId() {
        return orderProductId;
    }

    /**
     * @param orderProductId
     * The orderProductId to set.
     */
    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    /**
     * @return Returns the diffId
     */
    public Integer getDiffId() {
        return diffId;
    }

    /**
     * @param diffId
     * The diffId to set.
     */
    public void setDiffId(Integer diffId) {
        this.diffId = diffId;
    }

    /**
     * @return Returns the pushType
     */
    public Integer getPushType() {
        return pushType;
    }

    /**
     * @param pushType
     * The pushType to set.
     */
    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    /**
     * @return Returns the pushData
     */
    public String getPushData() {
        return pushData;
    }

    /**
     * @param pushData
     * The pushData to set.
     */
    public void setPushData(String pushData) {
        this.pushData = pushData;
    }

    /**
     * @return Returns the returnData
     */
    public String getReturnData() {
        return returnData;
    }

    /**
     * @param returnData
     * The returnData to set.
     */
    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    /**
     * @return Returns the success
     */
    public Integer getSuccess() {
        return success;
    }

    /**
     * @param success
     * The success to set.
     */
    public void setSuccess(Integer success) {
        this.success = success;
    }

    /**
     * @return Returns the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count
     * The count to set.
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return Returns the lastTime
     */
    public Integer getLastTime() {
        return lastTime;
    }

    /**
     * @param lastTime
     * The lastTime to set.
     */
    public void setLastTime(Integer lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * @return Returns the lastMessage
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * @param lastMessage
     * The lastMessage to set.
     */
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    /**
     * @return Returns the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}