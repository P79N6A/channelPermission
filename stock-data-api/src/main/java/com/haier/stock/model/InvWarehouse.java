package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓库设置。
 * 
 * <p>
 * Table: <strong>inv_warehouse</strong>
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
 * <td>whCode</td>
 * <td>{@link String}</td>
 * <td>wh_code</td>
 * <td>char</td>
 * <td>仓库（TC）代码。</td>
 * </tr>
 * <tr>
 * <td>whName</td>
 * <td>{@link String}</td>
 * <td>wh_name</td>
 * <td>varchar</td>
 * <td>仓库名称。</td>
 * </tr>
 * <tr>
 * <td>status</td>
 * <td>{@link Integer}</td>
 * <td>status</td>
 * <td>int</td>
 * <td>状态。</td>
 * </tr>
 * <tr>
 * <td>centerCode</td>
 * <td>{@link String}</td>
 * <td>center_code</td>
 * <td>varchar</td>
 * <td>网单中心代码</td>
 * </tr>
 * <tr>
 * <td>supportCod</td>
 * <td>{@link Integer}</td>
 * <td>support_cod</td>
 * <td>tinyint</td>
 * <td>是否支持货到付款。</td>
 * </tr>
 * <tr>
 * <td>createUser</td>
 * <td>{@link String}</td>
 * <td>create_user</td>
 * <td>varchar</td>
 * <td>创建者。</td>
 * </tr>
 * <tr>
 * <td>createTime</td>
 * <td>{@link Date}</td>
 * <td>create_time</td>
 * <td>datetime</td>
 * <td>创建时间。</td>
 * </tr>
 * <tr>
 * <td>updateUser</td>
 * <td>{@link String}</td>
 * <td>update_user</td>
 * <td>varchar</td>
 * <td>最后更新人。</td>
 * </tr>
 * <tr>
 * <td>updateTime</td>
 * <td>{@link Date}</td>
 * <td>update_time</td>
 * <td>timestamp/date</td>
 * <td>最后更新时间。</td>
 * </tr>
 * <tr>
 * <td>lesWhCode</td>
 * <td>{@link String}</td>
 * <td>les_wh_code</td>
 * <td>varchar</td>
 * <td>LES库位编码</td>
 * </tr>
 * <tr>
 * <td>lesAccepter</td>
 * <td>{@link Object}</td>
 * <td>les_accepter</td>
 * <td>serial/integer</td>
 * <td>LES送达方-</td>
 * </tr>
 * </table>
 * 
 * @author 刘志斌
 * @date 2013-6-8
 * @email yudi@sina.com
 */
public class InvWarehouse implements Serializable {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	public static int WAREHOUSE_ON = 1;
	public static int WAREHOUSE_OFF = 0;
	private String whCode;

	/**
	 * 获取 仓库（TC）代码。
	 */
	public String getWhCode() {
		return this.whCode;
	}

	/**
	 * 设置 仓库（TC）代码。
	 * 
	 * @param value
	 *            属性值
	 */
	public void setWhCode(String value) {
		this.whCode = value;
	}

	private String whName;

	/**
	 * 获取 仓库名称。
	 */
	public String getWhName() {
		return this.whName;
	}

	/**
	 * 设置 仓库名称。
	 * 
	 * @param value
	 *            属性值
	 */
	public void setWhName(String value) {
		this.whName = value;
	}

	private Integer status = 0;

	/**
	 * 获取 状态。
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 设置 状态。
	 * 
	 * @param value
	 *            属性值
	 */
	public void setStatus(Integer value) {
		this.status = value;
	}

	private String centerCode;

	/**
	 * 获取 网单中心代码。
	 */
	public String getCenterCode() {
		return this.centerCode;
	}

	/**
	 * 设置 网单中心代码。
	 * 
	 * @param value
	 *            属性值
	 */
	public void setCenterCode(String value) {
		this.centerCode = value;
	}

	private Integer supportCod = 0;

	/**
	 * 获取 是否支持货到付款。
	 */
	public Integer getSupportCod() {
		return this.supportCod;
	}

	/**
	 * 设置 是否支持货到付款。
	 * 
	 * @param value
	 *            属性值
	 */
	public void setSupportCod(Integer value) {
		this.supportCod = value;
	}

	private String createUser;

	/**
	 * 获取 创建者。
	 */
	public String getCreateUser() {
		return this.createUser;
	}

	/**
	 * 设置 创建者。
	 * 
	 * @param value
	 *            属性值
	 */
	public void setCreateUser(String value) {
		this.createUser = value;
	}

	private String createTime = null;

	/**
	 * 获取 创建时间。
	 */
	public String getCreateTime() {
		return this.createTime;
	}

	/**
	 * 设置 创建时间。
	 * 
	 * @param value
	 *            属性值
	 */
	public void setCreateTime(String value) {
		this.createTime = value;
	}

	private String updateUser;

	/**
	 * 获取 最后更新人。
	 */
	public String getUpdateUser() {
		return this.updateUser;
	}

	/**
	 * 设置 最后更新人。
	 * 
	 * @param value
	 *            属性值
	 */
	public void setUpdateUser(String value) {
		this.updateUser = value;
	}

	private String updateTime = null;

	/**
	 * 获取 最后更新时间。
	 */
	public String getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 设置 最后更新时间。
	 * 
	 * @param value
	 *            属性值
	 */
	public void setUpdateTime(String value) {
		this.updateTime = value;
	}

	private String lesAccepter;

	/**
	 * 获取 LES送达方。
	 */
	public String getLesAccepter() {
		return this.lesAccepter;
	}

	/**
	 * 设置 LES送达方。
	 * 
	 * @param value
	 *            属性值
	 */
	public void setLesAccepter(String value) {
		this.lesAccepter = value;
	}

	private String estorge_id; // 电商库位码
	private String estorge_name; // 电商库位名称
	private String transmit_id; // 送达方代码
	private String transmit_desc; // 送达方名称
	private String les_OE_id; // 0E码（LES）
	private String custom_id; // 管理客户编码
	private String custom_desc; // 管理客户名称
	private String industry_trade_id; // 工贸代码
	private String industry_trade_desc; // 工贸描述
	private String graininess_id; // 颗粒度编码
	private String net_management_id; // 网单经营体编码
	private String esale_id; // 电商售达方编码
	private String esale_name; // 电商售达方名称
	private String sale_org_id; // 销售组织编码
	private String branch; // 分公司
	private String payment_id; // 电商付款方编码
	private String payment_name; // 电商付款方名称
	private String area_id; // 地区编码（CRM专用）
	private String rrs_distribution_id; // 日日顺配送编码
	private String rrs_distribution_name; // 日日顺配送名称
	private String rrs_sale_id; // 日日顺售达方
	private String rrs_sale_name; // 日日顺售达方名称
	private String oms_rrs_payment_id; // OMS重庆新日日顺付款方
	private String oms_rrs_payment_name; // OMS重庆新日日顺付款方名称
    private String city_code;            //城市编码
    private String city_desc;            //城市名称

	/**
	 * @return Returns the wAREHOUSE_ON
	 */
	public static int getWAREHOUSE_ON() {
		return WAREHOUSE_ON;
	}

	/**
	 * @param wAREHOUSE_ON
	 *            The wAREHOUSE_ON to set.
	 */
	public static void setWAREHOUSE_ON(int wAREHOUSE_ON) {
		WAREHOUSE_ON = wAREHOUSE_ON;
	}

	/**
	 * @return Returns the wAREHOUSE_OFF
	 */
	public static int getWAREHOUSE_OFF() {
		return WAREHOUSE_OFF;
	}

	/**
	 * @param wAREHOUSE_OFF
	 *            The wAREHOUSE_OFF to set.
	 */
	public static void setWAREHOUSE_OFF(int wAREHOUSE_OFF) {
		WAREHOUSE_OFF = wAREHOUSE_OFF;
	}

	/**
	 * @return Returns the estorge_id
	 */
	public String getEstorge_id() {
		return estorge_id;
	}

	/**
	 * @param estorge_id
	 *            The estorge_id to set.
	 */
	public void setEstorge_id(String estorge_id) {
		this.estorge_id = estorge_id;
	}

	/**
	 * @return Returns the estorge_name
	 */
	public String getEstorge_name() {
		return estorge_name;
	}

	/**
	 * @param estorge_name
	 *            The estorge_name to set.
	 */
	public void setEstorge_name(String estorge_name) {
		this.estorge_name = estorge_name;
	}

	/**
	 * @return Returns the transmit_id
	 */
	public String getTransmit_id() {
		return transmit_id;
	}

	/**
	 * @param transmit_id
	 *            The transmit_id to set.
	 */
	public void setTransmit_id(String transmit_id) {
		this.transmit_id = transmit_id;
	}

	/**
	 * @return Returns the transmit_desc
	 */
	public String getTransmit_desc() {
		return transmit_desc;
	}

	/**
	 * @param transmit_desc
	 *            The transmit_desc to set.
	 */
	public void setTransmit_desc(String transmit_desc) {
		this.transmit_desc = transmit_desc;
	}

	/**
	 * @return Returns the les_OE_id
	 */
	public String getLes_OE_id() {
		return les_OE_id;
	}

	/**
	 * @param les_OE_id
	 *            The les_OE_id to set.
	 */
	public void setLes_OE_id(String les_OE_id) {
		this.les_OE_id = les_OE_id;
	}

	/**
	 * @return Returns the custom_id
	 */
	public String getCustom_id() {
		return custom_id;
	}

	/**
	 * @param custom_id
	 *            The custom_id to set.
	 */
	public void setCustom_id(String custom_id) {
		this.custom_id = custom_id;
	}

	/**
	 * @return Returns the custom_desc
	 */
	public String getCustom_desc() {
		return custom_desc;
	}

	/**
	 * @param custom_desc
	 *            The custom_desc to set.
	 */
	public void setCustom_desc(String custom_desc) {
		this.custom_desc = custom_desc;
	}

	/**
	 * @return Returns the industry_trade_id
	 */
	public String getIndustry_trade_id() {
		return industry_trade_id;
	}

	/**
	 * @param industry_trade_id
	 *            The industry_trade_id to set.
	 */
	public void setIndustry_trade_id(String industry_trade_id) {
		this.industry_trade_id = industry_trade_id;
	}

	/**
	 * @return Returns the industry_trade_desc
	 */
	public String getIndustry_trade_desc() {
		return industry_trade_desc;
	}

	/**
	 * @param industry_trade_desc
	 *            The industry_trade_desc to set.
	 */
	public void setIndustry_trade_desc(String industry_trade_desc) {
		this.industry_trade_desc = industry_trade_desc;
	}

	/**
	 * @return Returns the graininess_id
	 */
	public String getGraininess_id() {
		return graininess_id;
	}

	/**
	 * @param graininess_id
	 *            The graininess_id to set.
	 */
	public void setGraininess_id(String graininess_id) {
		this.graininess_id = graininess_id;
	}

	/**
	 * @return Returns the net_management_id
	 */
	public String getNet_management_id() {
		return net_management_id;
	}

	/**
	 * @param net_management_id
	 *            The net_management_id to set.
	 */
	public void setNet_management_id(String net_management_id) {
		this.net_management_id = net_management_id;
	}

	/**
	 * @return Returns the esale_id
	 */
	public String getEsale_id() {
		return esale_id;
	}

	/**
	 * @param esale_id
	 *            The esale_id to set.
	 */
	public void setEsale_id(String esale_id) {
		this.esale_id = esale_id;
	}

	/**
	 * @return Returns the esale_name
	 */
	public String getEsale_name() {
		return esale_name;
	}

	/**
	 * @param esale_name
	 *            The esale_name to set.
	 */
	public void setEsale_name(String esale_name) {
		this.esale_name = esale_name;
	}

	/**
	 * @return Returns the sale_org_id
	 */
	public String getSale_org_id() {
		return sale_org_id;
	}

	/**
	 * @param sale_org_id
	 *            The sale_org_id to set.
	 */
	public void setSale_org_id(String sale_org_id) {
		this.sale_org_id = sale_org_id;
	}

	/**
	 * @return Returns the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch
	 *            The branch to set.
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * @return Returns the payment_id
	 */
	public String getPayment_id() {
		return payment_id;
	}

	/**
	 * @param payment_id
	 *            The payment_id to set.
	 */
	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	/**
	 * @return Returns the payment_name
	 */
	public String getPayment_name() {
		return payment_name;
	}

	/**
	 * @param payment_name
	 *            The payment_name to set.
	 */
	public void setPayment_name(String payment_name) {
		this.payment_name = payment_name;
	}

	/**
	 * @return Returns the area_id
	 */
	public String getArea_id() {
		return area_id;
	}

	/**
	 * @param area_id
	 *            The area_id to set.
	 */
	public void setArea_id(String area_id) {
		this.area_id = area_id;
	}

	/**
	 * @return Returns the rrs_distribution_id
	 */
	public String getRrs_distribution_id() {
		return rrs_distribution_id;
	}

	/**
	 * @param rrs_distribution_id
	 *            The rrs_distribution_id to set.
	 */
	public void setRrs_distribution_id(String rrs_distribution_id) {
		this.rrs_distribution_id = rrs_distribution_id;
	}

	/**
	 * @return Returns the rrs_distribution_name
	 */
	public String getRrs_distribution_name() {
		return rrs_distribution_name;
	}

	/**
	 * @param rrs_distribution_name
	 *            The rrs_distribution_name to set.
	 */
	public void setRrs_distribution_name(String rrs_distribution_name) {
		this.rrs_distribution_name = rrs_distribution_name;
	}

	/**
	 * @return Returns the rrs_sale_id
	 */
	public String getRrs_sale_id() {
		return rrs_sale_id;
	}

	/**
	 * @param rrs_sale_id
	 *            The rrs_sale_id to set.
	 */
	public void setRrs_sale_id(String rrs_sale_id) {
		this.rrs_sale_id = rrs_sale_id;
	}

	/**
	 * @return Returns the rrs_sale_name
	 */
	public String getRrs_sale_name() {
		return rrs_sale_name;
	}

	/**
	 * @param rrs_sale_name
	 *            The rrs_sale_name to set.
	 */
	public void setRrs_sale_name(String rrs_sale_name) {
		this.rrs_sale_name = rrs_sale_name;
	}

	/**
	 * @return Returns the oms_rrs_payment_id
	 */
	public String getOms_rrs_payment_id() {
		return oms_rrs_payment_id;
	}

	/**
	 * @param oms_rrs_payment_id
	 *            The oms_rrs_payment_id to set.
	 */
	public void setOms_rrs_payment_id(String oms_rrs_payment_id) {
		this.oms_rrs_payment_id = oms_rrs_payment_id;
	}

	/**
	 * @return Returns the oms_rrs_payment_name
	 */
	public String getOms_rrs_payment_name() {
		return oms_rrs_payment_name;
	}

	/**
	 * @param oms_rrs_payment_name
	 *            The oms_rrs_payment_name to set.
	 */
	public void setOms_rrs_payment_name(String oms_rrs_payment_name) {
		this.oms_rrs_payment_name = oms_rrs_payment_name;
	}

	/**
	 * @return Returns the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String sapCustomerCode;

	/**
	 * 获取 sap客户编码。
	 */
	public String getSapCustomerCode() {
		return this.sapCustomerCode;
	}

	/**
	 * 设置 sap客户编码。
	 * 
	 * @param value
	 *            属性值
	 */
	public void setSapCustomerCode(String value) {
		this.sapCustomerCode = value;
	}

	private String sapCustomerName;

	/**
	 * 获取 sap客户名称。
	 */
	public String getSapCustomerName() {
		return this.sapCustomerName;
	}

	/**
	 * 设置 sap客户名称。
	 * 
	 * @param value
	 *            属性值
	 */
	public void setSapCustomerName(String value) {
		this.sapCustomerName = value;
	}

	/**
	 * 网单经营体编码
	 */
	private String netManagementId;

	/**
	 * 获取网单经营体编码
	 * 
	 * @return
	 */
	public String getNetManagementId() {
		return netManagementId;
	}

	/**
	 * 设置网单经营体编码
	 * 
	 * @param netManagementId
	 */
	public void setNetManagementId(String netManagementId) {
		this.netManagementId = netManagementId;
	}

	/**
	 * 电商售达方编码
	 */
	private String esaleId;

	/**
	 * 获取电商售达方编码
	 * 
	 * @return
	 */
	public String getEsaleId() {
		return esaleId;
	}

	/**
	 * 设置电商售达方编码
	 * 
	 * @param esaleId
	 */
	public void setEsaleId(String esaleId) {
		this.esaleId = esaleId;
	}

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getCity_desc() {
        return city_desc;
    }

    public void setCity_desc(String city_desc) {
        this.city_desc = city_desc;
    }

    private Integer is_enabled; // '启用和禁用状态，启用是0，禁用是1',

    public Integer getIs_enabled() {
		return is_enabled;
	}

    public void setIs_enabled(Integer is_enabled) {
		this.is_enabled = is_enabled;
	}

	// 不是数据表内的数据，启用和禁用
	private String is_enabled_name;

	public String getIs_enabled_name() {
		return is_enabled_name;
	}

	public void setIs_enabled_name(String is_enabled_name) {
		this.is_enabled_name = is_enabled_name;
	}

    @Override
    public String toString() {
        return "[whCode=" + whCode + ", whName=" + whName + ", status=" + status + ", centerCode="
               + centerCode + ", supportCod=" + supportCod + ", updateUser=" + updateUser
               + ", updateTime=" + updateTime + ", lesAccepter=" + lesAccepter + ", estorge_id="
               + estorge_id + ", estorge_name=" + estorge_name + ", transmit_id=" + transmit_id
               + ", transmit_desc=" + transmit_desc + ", les_OE_id=" + les_OE_id + ", custom_id="
               + custom_id + ", custom_desc=" + custom_desc + ", industry_trade_id="
               + industry_trade_id + ", industry_trade_desc=" + industry_trade_desc
               + ", graininess_id=" + graininess_id + ", net_management_id=" + net_management_id
               + ", esale_id=" + esale_id + ", esale_name=" + esale_name + ", sale_org_id="
               + sale_org_id + ", branch=" + branch + ", payment_id=" + payment_id
               + ", payment_name=" + payment_name + ", area_id=" + area_id
               + ", rrs_distribution_id=" + rrs_distribution_id + ", rrs_distribution_name="
               + rrs_distribution_name + ", rrs_sale_id=" + rrs_sale_id + ", rrs_sale_name="
               + rrs_sale_name + ", oms_rrs_payment_id=" + oms_rrs_payment_id
               + ", oms_rrs_payment_name=" + oms_rrs_payment_name + ", city_code=" + city_code
               + ", city_desc=" + city_desc + ", sapCustomerCode=" + sapCustomerCode
               + ", sapCustomerName=" + sapCustomerName + ", netManagementId=" + netManagementId
               + ", esaleId=" + esaleId + "]";
    }

}