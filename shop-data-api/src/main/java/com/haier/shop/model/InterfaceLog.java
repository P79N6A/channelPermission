package com.haier.shop.model;

import java.util.Date;

/**
 * 
 * @Title: InterfaceLog.java
 * @Package com.haier.shop.model
 * @Description: 操作接口日志表
 * @author layne
 * @date 2018年7月5日 下午2:27:05
 * @version V1.0
 */
public class InterfaceLog implements java.io.Serializable {
	private Integer id;
	private String keyword;
	private String subordinateSystem;
	private String useTheScene;
	private String interfaceCallAddress;
	private Date createTime;
	private String createBy;
	private String sendMessage;
	private String returnMessage;
	private String exptionInformation;
	private String spareFieldOne;
	private String spareFieldTwo;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the subordinateSystem
	 */
	public String getSubordinateSystem() {
		return subordinateSystem;
	}

	/**
	 * @param subordinateSystem
	 *            the subordinateSystem to set
	 */
	public void setSubordinateSystem(String subordinateSystem) {
		this.subordinateSystem = subordinateSystem;
	}

	/**
	 * @return the useTheScene
	 */
	public String getUseTheScene() {
		return useTheScene;
	}

	/**
	 * @param useTheScene
	 *            the useTheScene to set
	 */
	public void setUseTheScene(String useTheScene) {
		this.useTheScene = useTheScene;
	}

	/**
	 * @return the interfaceCallAddress
	 */
	public String getInterfaceCallAddress() {
		return interfaceCallAddress;
	}

	/**
	 * @param interfaceCallAddress
	 *            the interfaceCallAddress to set
	 */
	public void setInterfaceCallAddress(String interfaceCallAddress) {
		this.interfaceCallAddress = interfaceCallAddress;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy
	 *            the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the sendMessage
	 */
	public String getSendMessage() {
		return sendMessage;
	}

	/**
	 * @param sendMessage
	 *            the sendMessage to set
	 */
	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}

	/**
	 * @return the returnMessage
	 */
	public String getReturnMessage() {
		return returnMessage;
	}

	/**
	 * @param returnMessage
	 *            the returnMessage to set
	 */
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	/**
	 * @return the exptionInformation
	 */
	public String getExptionInformation() {
		return exptionInformation;
	}

	/**
	 * @param exptionInformation
	 *            the exptionInformation to set
	 */
	public void setExptionInformation(String exptionInformation) {
		this.exptionInformation = exptionInformation;
	}

	/**
	 * @return the spareFieldOne
	 */
	public String getSpareFieldOne() {
		return spareFieldOne;
	}

	/**
	 * @param spareFieldOne
	 *            the spareFieldOne to set
	 */
	public void setSpareFieldOne(String spareFieldOne) {
		this.spareFieldOne = spareFieldOne;
	}

	/**
	 * @return the spareFieldTwo
	 */
	public String getSpareFieldTwo() {
		return spareFieldTwo;
	}

	/**
	 * @param spareFieldTwo
	 *            the spareFieldTwo to set
	 */
	public void setSpareFieldTwo(String spareFieldTwo) {
		this.spareFieldTwo = spareFieldTwo;
	}

}
