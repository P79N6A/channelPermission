package com.haier.purchase.data.model;

import java.io.Serializable;

/**
 *
 * @author 刘骥飞
 * @date 2014-7-28
 * @email jifei.liu@dhc.com.cn
 */
public class GateOfStockExceedItem implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7315758884676253862L;

    private String            id;
    private String            judge_ed_channel_id;
    private String            judge_ed_channel_name;
    private String            exceed_age;
    private String            limit_ed_channel_id;
    private String            limit_ed_channel_name;
    private String            brand_type;
    private String            category_type;
    private String            model_type;
    private String            storage_type;
    private String            is_enabled;
    private String            is_enabled_name;
    private String            summary;
    private String            modify_user;
    private String            modify_time;

    /**
     * @return Returns the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     * The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the judge_ed_channel_id
     */
    public String getJudge_ed_channel_id() {
        return judge_ed_channel_id;
    }

    /**
     * @param judge_ed_channel_id
     * The judge_ed_channel_id to set.
     */
    public void setJudge_ed_channel_id(String judge_ed_channel_id) {
        this.judge_ed_channel_id = judge_ed_channel_id;
    }

    /**
     * @return Returns the judge_ed_channel_name
     */
    public String getJudge_ed_channel_name() {
        return judge_ed_channel_name;
    }

    /**
     * @param judge_ed_channel_name
     * The judge_ed_channel_name to set.
     */
    public void setJudge_ed_channel_name(String judge_ed_channel_name) {
        this.judge_ed_channel_name = judge_ed_channel_name;
    }

    /**
     * @return Returns the exceed_age
     */
    public String getExceed_age() {
        return exceed_age;
    }

    /**
     * @param exceed_age
     * The exceed_age to set.
     */
    public void setExceed_age(String exceed_age) {
        this.exceed_age = exceed_age;
    }

    /**
     * @return Returns the limit_ed_channel_id
     */
    public String getLimit_ed_channel_id() {
        return limit_ed_channel_id;
    }

    /**
     * @param limit_ed_channel_id
     * The limit_ed_channel_id to set.
     */
    public void setLimit_ed_channel_id(String limit_ed_channel_id) {
        this.limit_ed_channel_id = limit_ed_channel_id;
    }

    /**
     * @return Returns the limit_ed_channel_name
     */
    public String getLimit_ed_channel_name() {
        return limit_ed_channel_name;
    }

    /**
     * @param limit_ed_channel_name
     * The limit_ed_channel_name to set.
     */
    public void setLimit_ed_channel_name(String limit_ed_channel_name) {
        this.limit_ed_channel_name = limit_ed_channel_name;
    }

    /**
     * @return Returns the brand_type
     */
    public String getBrand_type() {
        return brand_type;
    }

    /**
     * @param brand_type
     * The brand_type to set.
     */
    public void setBrand_type(String brand_type) {
        this.brand_type = brand_type;
    }

    /**
     * @return Returns the category_type
     */
    public String getCategory_type() {
        return category_type;
    }

    /**
     * @param category_type
     * The category_type to set.
     */
    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }

    /**
     * @return Returns the model_type
     */
    public String getModel_type() {
        return model_type;
    }

    /**
     * @param model_type
     * The model_type to set.
     */
    public void setModel_type(String model_type) {
        this.model_type = model_type;
    }

    /**
     * @return Returns the storage_type
     */
    public String getStorage_type() {
        return storage_type;
    }

    /**
     * @param storage_type
     * The storage_type to set.
     */
    public void setStorage_type(String storage_type) {
        this.storage_type = storage_type;
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
     * @return Returns the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary
     * The summary to set.
     */
    public void setSummary(String summary) {
        this.summary = summary;
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