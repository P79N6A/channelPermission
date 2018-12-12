package com.haier.purchase.data.model.vehcile;

import java.io.Serializable;
import java.util.Date;

/**
 * 下单接口入参
 *
 * @author
 * @create 2017-09-05 9:11
 **/
public class OesMaterielDTO implements Serializable {

	private static final long serialVersionUID = 8450483848703582034L;
	private int id; // 编码
	private String productgroup;// 产品组
	private String materialcode;// 型号编码
	private String materialname;// 型号名称
	private String tradecode;//工贸编码
	private String tradename;//工贸名称
	private String parkcode;//园区编码
	private String parkname;//园区名称
	private int pri;//优先级【成本】
	private int maxqty;//最大量【整车直发】
	private int minqty;//最小量【整车直发】
	private String updateuser;//修改人
	private String updatetime;//修改时间
	private String seriescode;//系列名
	private String plantcode;//工厂编码
	private String plantname;//工厂名称
	private String orderflag;//预测订单标识1订单2预测3全部
	private int arriveweek;//启用日期
	private int user_pri;//用户优先级
	private int delete_flag;//删除标记

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductgroup() {
		return productgroup;
	}

	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
	}

	public String getMaterialcode() {
		return materialcode;
	}

	public void setMaterialcode(String materialcode) {
		this.materialcode = materialcode;
	}

	public String getMaterialname() {
		return materialname;
	}

	public void setMaterialname(String materialname) {
		this.materialname = materialname;
	}

	public String getTradecode() {
		return tradecode;
	}

	public void setTradecode(String tradecode) {
		this.tradecode = tradecode;
	}

	public String getTradename() {
		return tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

	public String getParkcode() {
		return parkcode;
	}

	public void setParkcode(String parkcode) {
		this.parkcode = parkcode;
	}

	public String getParkname() {
		return parkname;
	}

	public void setParkname(String parkname) {
		this.parkname = parkname;
	}

	public int getPri() {
		return pri;
	}

	public void setPri(int pri) {
		this.pri = pri;
	}

	public int getMaxqty() {
		return maxqty;
	}

	public void setMaxqty(int maxqty) {
		this.maxqty = maxqty;
	}

	public int getMinqty() {
		return minqty;
	}

	public void setMinqty(int minqty) {
		this.minqty = minqty;
	}

	public String getUpdateuser() {
		return updateuser;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getSeriescode() {
		return seriescode;
	}

	public void setSeriescode(String seriescode) {
		this.seriescode = seriescode;
	}

	public String getPlantcode() {
		return plantcode;
	}

	public void setPlantcode(String plantcode) {
		this.plantcode = plantcode;
	}

	public String getPlantname() {
		return plantname;
	}

	public void setPlantname(String plantname) {
		this.plantname = plantname;
	}

	public String getOrderflag() {
		return orderflag;
	}

	public void setOrderflag(String orderflag) {
		this.orderflag = orderflag;
	}

	public int getArriveweek() {
		return arriveweek;
	}

	public void setArriveweek(int arriveweek) {
		this.arriveweek = arriveweek;
	}

	public int getUser_pri() {
		return user_pri;
	}

	public void setUser_pri(int user_pri) {
		this.user_pri = user_pri;
	}

	public int getDelete_flag() {
		return delete_flag;
	}

	public void setDelete_flag(int delete_flag) {
		this.delete_flag = delete_flag;
	}

}
