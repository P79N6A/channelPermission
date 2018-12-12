package com.haier.svc.api.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuItem implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -6966264939936840122L;

    private Integer           mid;

    /**
     * 获取 菜单ID。
     */
    public Integer getMid() {
        return this.mid;
    }

    /**
     * 设置 菜单ID。
     *
     * @param value 属性值
     */
    public void setMid(Integer value) {
        this.mid = value;
    }

    private Integer pid;

    /**
     * 获取 父级菜单ID。
     *
     * <p>
     * 目前系统只支持2级菜单。
     */
    public Integer getPid() {
        return this.pid;
    }

    /**
     * 设置 父级菜单ID。
     *
     * <p>
     * 目前系统只支持2级菜单。
     *
     * @param value 属性值
     */
    public void setPid(Integer value) {
        this.pid = value;
    }

    private String name = "";

    /**
     * 获取 菜单名称。
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置 菜单名称。
     *
     * @param value 属性值
     */
    public void setName(String value) {
        this.name = value;
    }

    private Integer status = 0;

    /**
     * 获取 状态。
     *
     * <p>
     * -1逻辑删除，0禁用，1启用
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态。
     *
     * <p>
     * -1逻辑删除，0禁用，1启用
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private Integer orderBy = 0;

    /**
     * 获取 排列顺序，值大的排在前面。
     */
    public Integer getOrderBy() {
        return this.orderBy;
    }

    /**
     * 设置 排列顺序，值大的排在前面。
     *
     * @param value 属性值
     */
    public void setOrderBy(Integer value) {
        this.orderBy = value;
    }

    private String mtype = "url";

    /**
     * 获取 功能菜单类型。
     *
     * <p>
     * 空字符串：非功能菜单。<br />
     * mdl：菜单模块。<br />
     * app：ExtJS的功能菜单。<br />
     * url：直接url方式指定的功能菜单。
     */
    public String getMtype() {
        return this.mtype;
    }

    /**
     * 设置 功能菜单类型。
     *
     * <p>
     * 空字符串：非功能菜单。<br />
     * mdl：菜单模块。<br />
     * app：ExtJS的功能菜单。<br />
     * url：直接url方式指定的功能菜单。
     *
     * @param value 属性值
     */
    public void setMtype(String value) {
        this.mtype = value;
    }

    private String mdata;

    /**
     * 获取 功能菜单的目标地址。
     *
     * <p>
     * 功能菜单类型为app时，该字段存放ExtJS App名称；为url时该字段存放目标url地址。
     */
    public String getMdata() {
        return this.mdata;
    }

    /**
     * 设置 功能菜单的目标地址。
     *
     * <p>
     * 功能菜单类型为app时，该字段存放ExtJS App名称；为url时该字段存放目标url地址。
     *
     * @param value 属性值
     */
    public void setMdata(String value) {
        this.mdata = value;
    }

    private boolean leaf = false;

    public boolean getLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean value) {
        this.leaf = value;
    }

    private boolean expanded = true;

    public boolean getExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean value) {
        this.expanded = value;
    }

    private String iconCls;

    public String getIconCls() {
        return this.iconCls;
    }

    public void setIconCls(String value) {
        this.iconCls = value;
    }

    private List<MenuItem> children;

    public List<MenuItem> getChildren() {
        return this.children;
    }

    public void setChildren(List<MenuItem> value) {
        this.children = value;
    }

    public void addChildren(MenuItem menu) {
        if (this.children == null)
            this.children = new ArrayList<MenuItem>();
        this.children.add(menu);
    }

    private String updateUser = "";

    /**
     * 获取 最后更新人。
     */
    public String getUpdateUser() {
        return this.updateUser;
    }

    /**
     * 设置 最后更新人。
     *
     * @param value 属性值
     */
    public void setUpdateUser(String value) {
        this.updateUser = value;
    }

    private Date updateTime;

    /**
     * 获取 最后更新时间。
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置 最后更新时间。
     *
     * @param value 属性值
     */
    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }
}