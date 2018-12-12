package com.haier.shop.model;
/**
 * 接收hp分配网点 日志记录
 * @Filename: ApiLogs.java
 * @Version: 1.0
 *
 */
import java.io.Serializable;

public class ApiLogs implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6547237293576732699L;

    public static int         FLAG_SUCCESS     = 1;
    public static int         FLAG_FAIL        = 0;

    public static String      TYPE_HPPULL      = "hpPull";

    private int           id;

    /**
     *获取 id
     */
    public int getId() {
        return id;
    }

    /**
     *设置 id
     *@param id
     */
    public void setId(int id) {
        this.id = id;
    }

    private Integer flag;

    /**
     *获取 标记1-成功,0-失败
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     *设置 标记1-成功,0-失败
     *@param flag 标记1-成功,0-失败
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    private String resultCode;

    /**
     *获取 结果代码
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     *设置 结果代码
     *@param resultCode 结果代码
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    private String pushData;

    /**
     *获取 推送数据
     */
    public String getPushData() {
        return pushData;
    }

    /**
     *设置 推送数据
     *@param pushData 推送数据
     */
    public void setPushData(String pushData) {
        this.pushData = pushData;
    }

    private String pullData;

    /**
     *获取 接收数据
     */
    public String getPullData() {
        return pullData;
    }

    /**
     *设置 接收数据
     *@param pullData 接收数据
     */
    public void setPullData(String pullData) {
        this.pullData = pullData;
    }

    private String returnData;

    /**
     *获取 返回的数据
     */
    public String getReturnData() {
        return returnData;
    }

    /**
     *设置 返回的数据
     *@param returnData 返回的数据
     */
    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    private String cOrderSn;

    /**
     *获取 子订单号码
     */
    public String getCOrderSn() {
        return cOrderSn;
    }

    /**
     *设置 子订单号码
     *@param cOrderSn 子订单号码
     */
    public void setCOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    private String type;

    /**
     *获取 STOCK 库存同步 USEDSTOCK 出库同步
     */
    public String getType() {
        return type;
    }

    /**
     *设置 STOCK 库存同步 USEDSTOCK 出库同步
     *@param type STOCK 库存同步 USEDSTOCK 出库同步
     */
    public void setType(String type) {
        this.type = type;
    }

    private Long addTime;

    /**
     *获取 时间
     */
    public Long getAddTime() {
        return addTime;
    }

    /**
     *设置 时间
     *@param addTime 时间
     */
    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    private String addDateTime;

    /**
     *获取 日志记录时间
     */
    public String getAddDateTime() {
        return addDateTime;
    }

    /**
     *设置 日志记录时间
     *@param addDateTime 日志记录时间
     */
    public void setAddDateTime(String addDateTime) {
        this.addDateTime = addDateTime;
    }

    private String message;

    /**
     *获取 返回信息
     */
    public String getMessage() {
        return message;
    }

    /**
     *设置 返回信息
     *@param message 返回信息
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
