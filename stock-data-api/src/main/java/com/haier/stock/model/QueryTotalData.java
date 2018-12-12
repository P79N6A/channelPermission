package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

public class QueryTotalData implements Serializable{
    public Integer id;
    public String channelCode;
    public Integer lockHours;

    public Integer getId() {
        return id;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public Integer getLockHours() {
        return lockHours;
    }

    public Integer getStatus() {
        return status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getRef() {
        return ref;
    }

    public Integer getAllowUpdate() {
        return allowUpdate;
    }

    public Integer status;
    public String updateTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public void setLockHours(Integer lockHours) {
        this.lockHours = lockHours;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setAllowUpdate(Integer allowUpdate) {
        this.allowUpdate = allowUpdate;
    }

    public String ref;
    public Integer allowUpdate;
}
