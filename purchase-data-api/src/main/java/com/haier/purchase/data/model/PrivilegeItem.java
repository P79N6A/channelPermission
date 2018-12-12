/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.model;

import java.io.Serializable;

/**
 *                       
 * @Filename: PrivilegeItem.java  data_privilege_t实体类
 * @Version: 1.0
 * @Author: zhangxd 张小丹
 * @Email: xiaodan.zhang@dhc.com.cn
 *
 */
public class PrivilegeItem implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -5209760376900941282L;
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private Integer           role_id;                                 //角色编号              
    private String            role_name;                               //角色名称               
    private String            ed_channel_id;                           //适用渠道    
    private String            product_group_id;                        //适用产品组         
    private String            cbs_catgory;                             //适用品类  
    private String            gate_limit;                              //闸口秒杀

    /**
     * @return Returns the role_id
     */
    public Integer getRole_id() {
        return role_id;
    }

    /**
     * @param role_id
     * The role_id to set.
     */
    public void setRole_id(Integer role_id) {
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
}
