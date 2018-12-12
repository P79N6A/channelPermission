package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * www vom同步出库信息
 *                       
 * @Filename: VomwwwOutstockSynchronizeLogs.java
 * @Version: 1.0
 * @Author:  于善涛
 * @Email: yushantao@ehaier.com
 *
 */
public class VomwwwOutstockSynchronizeLogs implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID               = 1L;
    /**
     * 响应状态 - 未处理／失败
     */
    public static Integer RESPONSE_STATUS_UNTREADED      = 0;
    /**
     * 响应状态 - 成功
     */
    public static Integer RESPONSE_STATUS_SUCCESS        = 1;
    /**
     * 响应状态 - 失败
     */
    public static Integer RESPONSE_STATUS_ERROR          = 2;
    /**
     * 接口编码 - vom出库数据获取
     */
    public static String INTERFACE_CODE_VOMWWW_OUTSTOCK = "vomwww_outstock";
    private Integer id;                                                //主键
    private Long addTime;                                           //添加日志时间
    private String pushData;                                          //请求数据格式
    private String returnData;                                        //接收数据
    private Integer analysisResult;                                    //解析结果
    private Integer verifyResult;                                      //校验结果
    private Integer status;                                            //是否成功，0：未处理/失败；1：成功;2：停止
    private Integer count;                                             //推送次数
    private Date lastTime;                                          //最后一次推送时间
    private String message;                                           //最后一次推送信息
    private String type;                                              //请求类型 xml or json

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public String getPushData() {
        return pushData;
    }

    public void setPushData(String pushData) {
        this.pushData = pushData;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public Integer getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(Integer analysisResult) {
        this.analysisResult = analysisResult;
    }

    public Integer getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(Integer verifyResult) {
        this.verifyResult = verifyResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
