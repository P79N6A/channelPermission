package com.haier.purchase.data.model;

import java.io.Serializable;

/**
 *
 * @author 刘骥飞
 * @date 2014-8-11
 * @email jifei.liu@dhc.com.cn
 */
public class GateOfDataPrivilegeItem implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2406427207638033561L;
    private String            role_id;
    private String            role_name;
    private String            gate_limit;
    private String            gate_limit_name;
    private String            ed_channel_id;
    private String            ed_channel_name;
    private String            product_group_id;
    private String            product_group_name;
    private String            cbs_catgory;
    private String            user_id;
    private String            user_name;

    /**
     * @return Returns the role_id
     */
    public String getRole_id() {
        return role_id;
    }

    /**
     * @param role_id
     * The role_id to set.
     */
    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    /**
     * @return Returns the role_name
     */
    public String getRole_name() {
        return role_name;
    }

    /**
     * @param role_name
     * The role_name to set.
     */
    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    /**
     * @return Returns the gate_limit
     */
    public String getGate_limit() {
        return gate_limit;
    }

    /**
     * @param gate_limit
     * The gate_limit to set.
     */
    public void setGate_limit(String gate_limit) {
        this.gate_limit = gate_limit;
    }

    /**
     * @return Returns the gate_limit_name
     */
    public String getGate_limit_name() {
        return gate_limit_name;
    }

    /**
     * @param gate_limit_name
     * The gate_limit_name to set.
     */
    public void setGate_limit_name(String gate_limit_name) {
        this.gate_limit_name = gate_limit_name;
    }

    /**
     * @return Returns the ed_channel_id
     */
    public String getEd_channel_id() {
        return ed_channel_id;
    }

    /**
     * @param ed_channel_id
     * The ed_channel_id to set.
     */
    public void setEd_channel_id(String ed_channel_id) {
        this.ed_channel_id = ed_channel_id;
    }

    /**
     * @return Returns the ed_channel_name
     */
    public String getEd_channel_name() {
        return ed_channel_name;
    }

    /**
     * @param ed_channel_name
     * The ed_channel_name to set.
     */
    public void setEd_channel_name(String ed_channel_name) {
        this.ed_channel_name = ed_channel_name;
    }

    /**
     * @return Returns the product_group_id
     */
    public String getProduct_group_id() {
        return product_group_id;
    }

    /**
     * @param product_group_id
     * The product_group_id to set.
     */
    public void setProduct_group_id(String product_group_id) {
        this.product_group_id = product_group_id;
    }

    /**
     * @return Returns the product_group_name
     */
    public String getProduct_group_name() {
        return product_group_name;
    }

    /**
     * @param product_group_name
     * The product_group_name to set.
     */
    public void setProduct_group_name(String product_group_name) {
        this.product_group_name = product_group_name;
    }

    /**
     * @return Returns the cbs_catgory
     */
    public String getCbs_catgory() {
        return cbs_catgory;
    }

    /**
     * @param cbs_catgory
     * The cbs_catgory to set.
     */
    public void setCbs_catgory(String cbs_catgory) {
        this.cbs_catgory = cbs_catgory;
    }

    /**
     * @return Returns the user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * @param user_id
     * The user_id to set.
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * @return Returns the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name
     * The user_name to set.
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}