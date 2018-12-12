package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

public class RefundBillSyncRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    public RefundBillSyncRecord() {

    }

    public static enum BillSyncState {
        SyncFailure, SyncError, SyncSuccess, UnSync, Syncing
    }

    private Integer       id;
    // 逆向单号
    private Long          refundId;
	// 天猫订单号（外部订单号）
    private Long          tid;
    // 天猫子订单号    
    private Long          oid;
    // 物料号
    private String        sku;
    // 订单来源    
    private String        source;
    // 同步状态
    private BillSyncState syncState;
    // 同步执行次数
    private int           syncCount;
    // 创建时间
    private Date          created;
    // 最后一次操作时间
    private Date          lastTime;
    // 天猫最后操作时间
    private Date          jdpModified;
    // 聚石塔订单详情
    private String        jdpResponse;
    // 执行日志
    private String        sysLog;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BillSyncState getSyncState() {
        return syncState;
    }

    public void setSyncState(BillSyncState syncState) {
        this.syncState = syncState;
    }

    public int getSyncCount() {
        return syncCount;
    }

    public void setSyncCount(int syncCount) {
        this.syncCount = syncCount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Date getJdpModified() {
        return jdpModified;
    }

    public void setJdpModified(Date jdpModified) {
        this.jdpModified = jdpModified;
    }

    public String getSysLog() {
        return sysLog;
    }

    public void setSysLog(String sysLog) {
        this.sysLog = sysLog;
    }

    public String getJdpResponse() {
        return jdpResponse;
    }

    public void setJdpResponse(String jdpResponse) {
        this.jdpResponse = jdpResponse;
    }

}