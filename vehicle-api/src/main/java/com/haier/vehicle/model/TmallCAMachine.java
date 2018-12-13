package com.haier.vehicle.model;

import java.io.Serializable;

public class TmallCAMachine implements Serializable{

	private String id;

	/*
	 * 序号
	 */
	private String ROWNO;

	/*
	 * 物料编码
	 */
	private String CGB_GBID;

	/*
	 * 工厂
	 */
	private String CGB_FACTORY;

	/*
	 * BOM 组件
	 */
	private String CGB_SUBGBID;;

	/*
	 * 组件数量
	 */
	private String CGB_SUBSL;

	/*
	 * 物料描述
	 */
	private String CGB_SUBNAME1;

	/*
	 * 物料描述
	 */
	private String CGB_SUBNAME2;
	
	/*
	 * 更新时间
	 */
	private String CGB_GXTIME;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getROWNO() {
		return ROWNO;
	}

	public void setROWNO(String rOWNO) {
		ROWNO = rOWNO;
	}

	public String getCGB_GBID() {
		return CGB_GBID;
	}

	public void setCGB_GBID(String cGB_GBID) {
		CGB_GBID = cGB_GBID;
	}

	public String getCGB_FACTORY() {
		return CGB_FACTORY;
	}

	public void setCGB_FACTORY(String cGB_FACTORY) {
		CGB_FACTORY = cGB_FACTORY;
	}

	public String getCGB_SUBGBID() {
		return CGB_SUBGBID;
	}

	public void setCGB_SUBGBID(String cGB_SUBGBID) {
		CGB_SUBGBID = cGB_SUBGBID;
	}

	public String getCGB_SUBSL() {
		return CGB_SUBSL;
	}

	public void setCGB_SUBSL(String cGB_SUBSL) {
		CGB_SUBSL = cGB_SUBSL;
	}

	public String getCGB_SUBNAME1() {
		return CGB_SUBNAME1;
	}

	public void setCGB_SUBNAME1(String cGB_SUBNAME1) {
		CGB_SUBNAME1 = cGB_SUBNAME1;
	}

	public String getCGB_SUBNAME2() {
		return CGB_SUBNAME2;
	}

	public void setCGB_SUBNAME2(String cGB_SUBNAME2) {
		CGB_SUBNAME2 = cGB_SUBNAME2;
	}

	public String getCGB_GXTIME() {
		return CGB_GXTIME;
	}

	public void setCGB_GXTIME(String cGB_GXTIME) {
		CGB_GXTIME = cGB_GXTIME;
	}

	
}
