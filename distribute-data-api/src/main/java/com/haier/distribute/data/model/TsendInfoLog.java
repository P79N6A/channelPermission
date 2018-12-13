package com.haier.distribute.data.model;

import java.io.Serializable;

public class TsendInfoLog implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7186302294124552674L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_send_info_log.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_send_info_log.channelCode
     *
     * @mbggenerated
     */
    private String channelcode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_send_info_log.infoType
     *
     * @mbggenerated
     */
    private Byte infotype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_send_info_log.sendTime
     *
     * @mbggenerated
     */
    private String sendtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_send_info_log.sender
     *
     * @mbggenerated
     */
    private String sender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_send_info_log.sendStatus
     *
     * @mbggenerated
     */
    private Byte sendstatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_send_info_log.sendType
     *
     * @mbggenerated
     */
    private Byte sendtype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_send_info_log.remark
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_send_info_log.id
     *
     * @return the value of t_send_info_log.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_send_info_log.id
     *
     * @param id the value for t_send_info_log.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_send_info_log.channelCode
     *
     * @return the value of t_send_info_log.channelCode
     *
     * @mbggenerated
     */
    public String getChannelcode() {
        return channelcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_send_info_log.channelCode
     *
     * @param channelcode the value for t_send_info_log.channelCode
     *
     * @mbggenerated
     */
    public void setChannelcode(String channelcode) {
        this.channelcode = channelcode == null ? null : channelcode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_send_info_log.infoType
     *
     * @return the value of t_send_info_log.infoType
     *
     * @mbggenerated
     */
    public Byte getInfotype() {
        return infotype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_send_info_log.infoType
     *
     * @param infotype the value for t_send_info_log.infoType
     *
     * @mbggenerated
     */
    public void setInfotype(Byte infotype) {
        this.infotype = infotype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_send_info_log.sendTime
     *
     * @return the value of t_send_info_log.sendTime
     *
     * @mbggenerated
     */
    public String getSendtime() {
        return sendtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_send_info_log.sendTime
     *
     * @param sendtime the value for t_send_info_log.sendTime
     *
     * @mbggenerated
     */
    public void setSendtime(String sendtime) {
        this.sendtime = sendtime == null ? null : sendtime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_send_info_log.sender
     *
     * @return the value of t_send_info_log.sender
     *
     * @mbggenerated
     */
    public String getSender() {
        return sender;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_send_info_log.sender
     *
     * @param sender the value for t_send_info_log.sender
     *
     * @mbggenerated
     */
    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_send_info_log.sendStatus
     *
     * @return the value of t_send_info_log.sendStatus
     *
     * @mbggenerated
     */
    public Byte getSendstatus() {
        return sendstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_send_info_log.sendStatus
     *
     * @param sendstatus the value for t_send_info_log.sendStatus
     *
     * @mbggenerated
     */
    public void setSendstatus(Byte sendstatus) {
        this.sendstatus = sendstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_send_info_log.sendType
     *
     * @return the value of t_send_info_log.sendType
     *
     * @mbggenerated
     */
    public Byte getSendtype() {
        return sendtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_send_info_log.sendType
     *
     * @param sendtype the value for t_send_info_log.sendType
     *
     * @mbggenerated
     */
    public void setSendtype(Byte sendtype) {
        this.sendtype = sendtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_send_info_log.remark
     *
     * @return the value of t_send_info_log.remark
     *
     * @mbggenerated
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_send_info_log.remark
     *
     * @param remark the value for t_send_info_log.remark
     *
     * @mbggenerated
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}