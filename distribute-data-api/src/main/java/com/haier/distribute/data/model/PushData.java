package com.haier.distribute.data.model;

import java.io.Serializable;

public class PushData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2011529928104508545L;

	/**
     *Comment for <code>serialVersionUID</code>
     */
    private Integer id;

    private String channelCode;

    private String channelName;

    private String sendTime;

    private String sendInfo;

    private Byte sendStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendInfo() {
		return sendInfo;
	}

	public void setSendInfo(String sendInfo) {
		this.sendInfo = sendInfo;
	}

	public Byte getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Byte sendStatus) {
		this.sendStatus = sendStatus;
	}

	

    
}