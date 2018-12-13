package com.haier.eop.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Table: <strong>OrderCoupons</strong>
 * <p>
 * <table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 * <tr style="background-color:#ddd;Text-align:Left;">
 * <th nowrap>属性名</th>
 * <th nowrap>属性类型</th>
 * <th nowrap>字段名</th>
 * <th nowrap>字段类型</th>
 * <th nowrap>说明</th>
 * </tr>
 * <tr>
 * <td>id</td>
 * <td>{@link Integer}</td>
 * <td>id</td>
 * <td>int</td>
 * <td>&nbsp;</td>
 * </tr>
 * <tr>
 * <td>orderId</td>
 * <td>{@link Integer}</td>
 * <td>orderId</td>
 * <td>int</td>
 * <td>订单id</td>
 * </tr>
 * <tr>
 * <td>orderProductId</td>
 * <td>{@link Integer}</td>
 * <td>orderProductId</td>
 * <td>int</td>
 * <td>网单id</td>
 * </tr>
 * <tr>
 * <td>cOrderSn</td>
 * <td>{@link String}</td>
 * <td>cOrderSn</td>
 * <td>varchar2</td>
 * <td>网单号</td>
 * </tr>
 * <tr>
 * <td>price</td>
 * <td>{@link BigDecimal}</td>
 * <td>price</td>
 * <td>decimal</td>
 * <td>商品价格</td>
 * </tr>
 * <tr>
 * <td>productAmount</td>
 * <td>{@link BigDecimal}</td>
 * <td>productAmount</td>
 * <td>decimal</td>
 * <td>商品付款金额</td>
 * </tr>
 * <tr>
 * <td>number</td>
 * <td>{@link Integer}</td>
 * <td>number</td>
 * <td>int</td>
 * <td>商品数量</td>
 * </tr>
 * <tr>
 * <td>jfbAmount</td>
 * <td>{@link BigDecimal}</td>
 * <td>jfbAmount</td>
 * <td>decimal</td>
 * <td>集分宝</td>
 * </tr>
 * <tr>
 * <td>hbAmount</td>
 * <td>{@link BigDecimal}</td>
 * <td>hbAmount</td>
 * <td>decimal</td>
 * <td>红包</td>
 * </tr>
 * <tr>
 * <td>dqAmount</td>
 * <td>{@link BigDecimal}</td>
 * <td>dqAmount</td>
 * <td>decimal</td>
 * <td>点券</td>
 * </tr>
 * <tr>
 * <td>couponAmount</td>
 * <td>{@link BigDecimal}</td>
 * <td>couponAmount</td>
 * <td>decimal</td>
 * <td>优惠券</td>
 * </tr>
 * <tr>
 * <td>points</td>
 * <td>{@link Integer}</td>
 * <td>points</td>
 * <td>int</td>
 * <td>积分</td>
 * </tr>
 * <tr>
 * <td>tyqAmount</td>
 * <td>{@link BigDecimal}</td>
 * <td>tyqAmount</td>
 * <td>decimal</td>
 * <td>天猫通用券</td>
 * </tr>
 * <tr>
 * <td>djqAmount</td>
 * <td>{@link BigDecimal}</td>
 * <td>djqAmount</td>
 * <td>decimal</td>
 * <td>代金券</td>
 * </tr>
 * <tr>
 * <td>djpzAmount</td>
 * <td>{@link BigDecimal}</td>
 * <td>djpzAmount</td>
 * <td>decimal</td>
 * <td>定金膨胀</td>
 * </tr>
 * <tr>
 * <td>dpxdljAmount</td>
 * <td>{@link BigDecimal}</td>
 * <td>dpxdljAmount</td>
 * <td>decimal</td>
 * <td>店铺的下单立减</td>
 * </tr>
 * <tr>
 * <td>dpCouponAmount</td>
 * <td>{@link BigDecimal}</td>
 * <td>dpCouponAmount</td>
 * <td>decimal</td>
 * <td>店铺的优惠券</td>
 * </tr>
 * <tr>
 * <td>dphbAmount</td>
 * <td>{@link BigDecimal}</td>
 * <td>dphbAmount</td>
 * <td>decimal</td>
 * <td>店铺的红包</td>
 * </tr>
 * <tr>
 * <td>createTime</td>
 * <td>{@link Date}</td>
 * <td>createTime</td>
 * <td>Date</td>
 * <td>创建时间</td>
 * </tr>
 * <tr>
 * <td>updateTime</td>
 * <td>{@link Date}</td>
 * <td>updateTime</td>
 * <td>Date</td>
 * <td>更新时间</td>
 * </tr>
 * </table>
 * 
 * @author lujun
 */
public class OrderCoupons implements Serializable {

	private Integer id;
	// 订单id
	private Integer orderId;
	// 网单id
	private Integer orderProductId;
	// 网单号
	private String cOrderSn;
	// 商品数量
	private Integer number;
	// 商品价格
	private BigDecimal price;
	// 商品付款金额
	private BigDecimal productAmount;
	// 集分宝
	private BigDecimal jfbAmount;
	// 红包(苏宁:6903(易宝))、国美自营红券
	private BigDecimal hbAmount;
	// 点券、国美自营蓝券
	private BigDecimal dqAmount;
	// 优惠券
	private BigDecimal couponAmount;
	// 积分
	private BigDecimal points;
	// 天猫通用券
	private BigDecimal tyqAmount;
	// 代金券(苏宁:优惠单金额)
	private BigDecimal djqAmount;
	// 定金膨胀
	private BigDecimal djpzAmount;
	// 店铺的下单立减
	private BigDecimal dpxdljAmount;
	// 店铺的优惠券
	private BigDecimal dpCouponAmount;
	// 店铺的红包
	private BigDecimal dphbAmount;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderProductId() {
		return orderProductId;
	}

	public void setOrderProductId(Integer orderProductId) {
		this.orderProductId = orderProductId;
	}

	public String getcOrderSn() {
		return cOrderSn;
	}

	public void setcOrderSn(String cOrderSn) {
		this.cOrderSn = cOrderSn;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}

	public BigDecimal getJfbAmount() {
		return jfbAmount;
	}

	public void setJfbAmount(BigDecimal jfbAmount) {
		this.jfbAmount = jfbAmount;
	}

	public BigDecimal getHbAmount() {
		return hbAmount;
	}

	public void setHbAmount(BigDecimal hbAmount) {
		this.hbAmount = hbAmount;
	}

	public BigDecimal getDqAmount() {
		return dqAmount;
	}

	public void setDqAmount(BigDecimal dqAmount) {
		this.dqAmount = dqAmount;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public BigDecimal getPoints() {
		return points;
	}

	public void setPoints(BigDecimal points) {
		this.points = points;
	}

	public BigDecimal getTyqAmount() {
		return tyqAmount;
	}

	public void setTyqAmount(BigDecimal tyqAmount) {
		this.tyqAmount = tyqAmount;
	}

	public BigDecimal getDjqAmount() {
		return djqAmount;
	}

	public void setDjqAmount(BigDecimal djqAmount) {
		this.djqAmount = djqAmount;
	}

	public BigDecimal getDjpzAmount() {
		return djpzAmount;
	}

	public void setDjpzAmount(BigDecimal djpzAmount) {
		this.djpzAmount = djpzAmount;
	}

	public BigDecimal getDpxdljAmount() {
		return dpxdljAmount;
	}

	public void setDpxdljAmount(BigDecimal dpxdljAmount) {
		this.dpxdljAmount = dpxdljAmount;
	}

	public BigDecimal getDpCouponAmount() {
		return dpCouponAmount;
	}

	public void setDpCouponAmount(BigDecimal dpCouponAmount) {
		this.dpCouponAmount = dpCouponAmount;
	}

	public BigDecimal getDphbAmount() {
		return dphbAmount;
	}

	public void setDphbAmount(BigDecimal dphbAmount) {
		this.dphbAmount = dphbAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}