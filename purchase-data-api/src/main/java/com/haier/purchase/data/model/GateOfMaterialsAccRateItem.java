package com.haier.purchase.data.model;

import java.io.Serializable;

/**
 *
 * @author 刘骥飞
 * @date 2014-8-11
 * @email jifei.liu@dhc.com.cn
 */
public class GateOfMaterialsAccRateItem implements Serializable {

    private static final long serialVersionUID = 231568752399184416L;
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private String            ed_channel_id;
    private String            ed_channel_name;
    private String            category_id;
    private String            acc_rate;
    private String            remark;
    private String            is_enabled;
    private String            is_enabled_name;
    private String            modify_user;
    private String            modify_time;

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
     * @return Returns the category_id
     */
    public String getCategory_id() {
        return category_id;
    }

    /**
     * @param category_id
     * The category_id to set.
     */
    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    /**
     * @return Returns the acc_rate
     */
    public String getAcc_rate() {
        return acc_rate;
    }

    /**
     * @param acc_rate
     * The acc_rate to set.
     */
    public void setAcc_rate(String acc_rate) {
        this.acc_rate = acc_rate;
    }

    /**
     * @return Returns the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     * The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return Returns the is_enabled
     */
    public String getIs_enabled() {
        return is_enabled;
    }

    /**
     * @param is_enabled
     * The is_enabled to set.
     */
    public void setIs_enabled(String is_enabled) {
        this.is_enabled = is_enabled;
    }

    /**
     * @return Returns the is_enabled_name
     */
    public String getIs_enabled_name() {
        return is_enabled_name;
    }

    /**
     * @param is_enabled_name
     * The is_enabled_name to set.
     */
    public void setIs_enabled_name(String is_enabled_name) {
        this.is_enabled_name = is_enabled_name;
    }

    /**
     * @return Returns the modify_user
     */
    public String getModify_user() {
        return modify_user;
    }

    /**
     * @param modify_user
     * The modify_user to set.
     */
    public void setModify_user(String modify_user) {
        this.modify_user = modify_user;
    }

    /**
     * @return Returns the modify_time
     */
    public String getModify_time() {
        return modify_time;
    }

    /**
     * @param modify_time
     * The modify_time to set.
     */
    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

}