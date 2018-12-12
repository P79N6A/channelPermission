package com.haier.stock.model;

import java.io.Serializable;

public class SgFlagShipStoreAuthorization implements Serializable{

	private static final long serialVersionUID = 2069425555393675735L;

	/**主键 */
	private Integer id;
	
	/**店铺ID */
	private Integer storeId;
	
	/**用户ID */
	private Integer ownerId;
	
	/**省 */
	private Integer provinceId;
	
	/**市 */
	private Integer cityId;
	
	/**区 */
	private Integer regionId;
	
	/**街道 */
	private Integer street;
	
	/**品牌id */
	private Integer brandId;
	
	/**产品组 */
	private String department;
	
	/**创建人 **/
	private String addUser;
	
	/**创建时间 **/
	private Integer addTime;
	
	/**更新人 **/
	private String modifyUser;
	
	/**更新时间 **/
	private Integer modifyTime;
	
	/**审核人 **/
	private String auditUser;
	
	/**审核时间 **/
	private Integer auditTime;
	
	/**审核意见 **/
	private String auditOpinion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getStreet() {
		return street;
	}

	public void setStreet(Integer street) {
		this.street = street;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public Integer getAddTime() {
		return addTime;
	}

	public void setAddTime(Integer addTime) {
		this.addTime = addTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Integer getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Integer modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public Integer getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Integer auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	
}
