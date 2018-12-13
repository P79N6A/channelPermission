package com.haier.stock.model;

import com.haier.stock.util.excel.Excel;
import com.haier.stock.util.excel.ExcelTitle;

import java.io.Serializable;

@Excel(filename = "仓库管理")
public class StorageLocMgt implements Serializable{
    private static final long serialVersionUID = 1560905782850213139L;
    @ExcelTitle(titleName = "库位编码")
    private String whCode;
    @ExcelTitle(titleName = "库位名称")
    private String whName;
    @ExcelTitle(titleName = "状态")
    private String statusExcel;
    @ExcelTitle(titleName = "网单中心代码")
    private String centerCode;
    @ExcelTitle(titleName = "是否支持货到付款")
    private String supportCodExcel;
    @ExcelTitle(titleName = "该TC支持的物流模式")
    private String supportedDeliveryMode;
    @ExcelTitle(titleName = "送达方代码")
    private String lesFiveYard;
    @ExcelTitle(titleName = "LES库位编码")
    private String lesWhCode;
    private Integer status;

    private Integer supportCod;



    public String getStatusExcel() {
        return statusExcel;
    }

    public void setStatusExcel(String statusExcel) {
        this.statusExcel = statusExcel;
    }

    public String getSupportCodExcel() {
        return supportCodExcel;
    }

    public void setSupportCodExcel(String supportCodExcel) {
        this.supportCodExcel = supportCodExcel;
    }



    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode;
    }

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCenterCode() {
        return centerCode;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    public Integer getSupportCod() {
        return supportCod;
    }

    public void setSupportCod(Integer supportCod) {
        this.supportCod = supportCod;
    }

    public String getSupportedDeliveryMode() {
        return supportedDeliveryMode;
    }

    public void setSupportedDeliveryMode(String supportedDeliveryMode) {
        this.supportedDeliveryMode = supportedDeliveryMode;
    }

    public String getLesFiveYard() {
        return lesFiveYard;
    }

    public void setLesFiveYard(String lesFiveYard) {
        this.lesFiveYard = lesFiveYard;
    }

    public String getLesWhCode() {
        return lesWhCode;
    }

    public void setLesWhCode(String lesWhCode) {
        this.lesWhCode = lesWhCode;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
    @ExcelTitle(titleName = "最后更新时间")
    private String updateTime;
    private Integer page;
    private Integer rows;
    private Integer start;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    private Integer size;
}
