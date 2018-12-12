package com.haier.shop.model;

import java.util.Date;

/**
 * 声明一个用来接收VOM返回过来的数据
 * @author wukunyang 2017-12-7
 *
 */
public class VOMReturnInforMation {
	private String outCode;//消息code，消息唯一码，不能重复。
	private Date notIfyTime;//消息通知时间YYYY-MM-DD hh:mm:ss
	private String buType;//通知类型，接口方法名rrs_outinstore
	private String source;//来源，根据系统区分固定值 
	private String type;//报文格式：Json或xml
	private String sign;//签名base64(MD5(content+keyValue))	ketValue：Haier,123 content：如下
	private String content;//消息内容根据具体业务定义，如下
	
	public String getOutCode() {
		return outCode;
	}
	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}
	public Date getNotIfyTime() {
		return notIfyTime;
	}
	public void setNotIfyTime(Date notIfyTime) {
		this.notIfyTime = notIfyTime;
	}
	public String getBuType() {
		return buType;
	}
	public void setBuType(String buType) {
		this.buType = buType;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
