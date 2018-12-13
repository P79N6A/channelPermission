package com.haier.shop.model;

import java.io.Serializable;

/**
 * Created by xupeng on 18/4/27.
 */
public class ReviewPoolJobSet implements Serializable {

    private static final long serialVersionUID = -813620575235526329L;
    private String id;
    private String jobName;
    private String jobType;
    private String value;
    private String time;
    private String isEnable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

}
