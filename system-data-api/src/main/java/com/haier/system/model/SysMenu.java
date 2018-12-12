package com.haier.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysMenu implements Serializable {
	private static final long serialVersionUID = 7025594546348330406L;
	public static final String MENU_MODULE = "mdl";
	public static final String MENU_ITEM_APP = "app";
	public static final String MENU_ITEM_URL = "url";
	private Integer menuId;
	private Integer parentId;
	private String menuName;
	private Integer status;
	private Integer orderBy;
	private String menuItemType;
	private String menuItemData;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private List<SysMenu> children;

	public SysMenu() {
		this.menuName = "";

		this.status = Integer.valueOf(0);

		this.orderBy = Integer.valueOf(0);

		this.menuItemType = "url";

		this.createUser = "";

		this.createTime = null;

		this.updateUser = "";
	}

	public Integer getMenuId() {
		return this.menuId;
	}

	public void setMenuId(Integer value) {
		this.menuId = value;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer value) {
		this.parentId = value;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String value) {
		this.menuName = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public Integer getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(Integer value) {
		this.orderBy = value;
	}

	public String getMenuItemType() {
		return this.menuItemType;
	}

	public void setMenuItemType(String value) {
		this.menuItemType = value;
	}

	public String getMenuItemData() {
		return this.menuItemData;
	}

	public void setMenuItemData(String value) {
		this.menuItemData = value;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String value) {
		this.createUser = value;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date value) {
		this.createTime = value;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String value) {
		this.updateUser = value;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}

	public List<SysMenu> getChildren() {
		return this.children;
	}

	public void setChildren(List<SysMenu> value) {
		this.children = value;
	}

	public void addChildren(SysMenu menu) {
		if (this.children == null)
			this.children = new ArrayList();
		this.children.add(menu);
	}
}