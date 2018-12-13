package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:JinXueqian
 * @Date: 2018/7/26 15:04
 */
public class Wlbstocksyncstorages  implements Serializable {

    private static final long serialVersionUID = 6002780393210049941L;

    /**
     *  库位ID
     */
    private Integer id;

    /**
     * 库位编码
     */
    private Integer storageId;

    /**
     * 淘宝仓库编码
     */
    private String sCode;

    /**
     * 淘宝仓库编码
     *
     */
    private String taobaoStoreCode;

    /**
     * 淘宝店,taobao:淘宝官方旗舰店;taobaorsq淘宝热水器专卖店
     */
    private String source;

    /**
     * 添加时间
     */
    private Date addTime;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public String getTaobaoStoreCode() {
        return taobaoStoreCode;
    }

    public void setTaobaoStoreCode(String taobaoStoreCode) {
        this.taobaoStoreCode = taobaoStoreCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
