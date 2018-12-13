package com.haier.shop.model;

import com.haier.shop.util.excel.Excel;
import com.haier.shop.util.excel.ExcelTitle;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/14.
 */
@Excel(filename = "京东7月份入库明细")
public class Rejectsdetail implements Serializable {

    private static final long serialVersionUID = -2480137550806929300L;
    private Integer id;
    @ExcelTitle(titleName = "中心", importIndex = 0)
    private String core;                  //中心
    @ExcelTitle(titleName = "入库单号", importIndex = 1)
    private String warehouseNumber;     //入库单号
    @ExcelTitle(titleName = "签收时间", importIndex = 2)
    private Date signinTime;            //签收时间
    @ExcelTitle(titleName = "服务单申请时间", importIndex = 3)
    private Date servicelistApplyTime;  //服务单申请时间
    @ExcelTitle(titleName = "保修天数", importIndex = 4)
    private String guaranteeDays;          //保修天数
    @ExcelTitle(titleName = "拆包时间", importIndex = 5)
    private Date dismantlingTime;       //拆包时间
    @ExcelTitle(titleName = "申请时间", importIndex = 6)
    private Date applyTime;             //申请时间
    @ExcelTitle(titleName = "上架时间", importIndex = 7)
    private Date groundingTime;         //上架时间
    @ExcelTitle(titleName = "备件条码", importIndex = 8)
    private String attachmentBarCode;   //备件条码
    @ExcelTitle(titleName = "一级品类", importIndex = 9)
    private String oneCategory;         //一级品类
    @ExcelTitle(titleName = "二级品类", importIndex = 10)
    private String twoCategory;         //二级
    @ExcelTitle(titleName = "三级品类", importIndex = 11)
    private String threeCategory;       //三级
    @ExcelTitle(titleName = "品牌", importIndex = 12)
    private String brand;               //品牌
    @ExcelTitle(titleName = "商品编号", importIndex = 13)
    private String commodityNumber;     //商品编号
    @ExcelTitle(titleName = "商品名称", importIndex = 14)
    private String commodityName;       //商品名称
    @ExcelTitle(titleName = "入库类型", importIndex = 15)
    private String reservoirType;       //入库类型
    private String uploadPerson;        //上传人
    private Date uploadTime;            //上传时间
    private String  month;
    private String  month2;
    private Integer year;
    private String rejectnum;
    private String industry;
    private String outqty;
    private String preMonth;            //查询月信息开始时间
    private String nextMonth;           //结束时间


    public String getMonth2() {
        return month2;
    }

    public void setMonth2(String month2) {
        this.month2 = month2;
    }

    public String getPreMonth() {
        return preMonth;
    }

    public void setPreMonth(String preMonth) {
        this.preMonth = preMonth;
    }

    public String getNextMonth() {
        return nextMonth;
    }

    public void setNextMonth(String nextMonth) {
        this.nextMonth = nextMonth;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getOutqty() {
        return outqty;
    }

    public void setOutqty(String outqty) {
        this.outqty = outqty;
    }

    public String getRejectnum() {
        return rejectnum;
    }

    public void setRejectnum(String rejectnum) {
        this.rejectnum = rejectnum;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }

    public String getWarehouseNumber() {
        return warehouseNumber;
    }

    public void setWarehouseNumber(String warehouseNumber) {
        this.warehouseNumber = warehouseNumber;
    }

    public Date getSigninTime() {
        return signinTime;
    }

    public void setSigninTime(Date signinTime) {
        this.signinTime = signinTime;
    }

    public Date getServicelistApplyTime() {
        return servicelistApplyTime;
    }

    public void setServicelistApplyTime(Date servicelistApplyTime) {
        this.servicelistApplyTime = servicelistApplyTime;
    }

    public String getGuaranteeDays() {
        return guaranteeDays;
    }

    public void setGuaranteeDays(String guaranteeDays) {
        this.guaranteeDays = guaranteeDays;
    }

    public Date getDismantlingTime() {
        return dismantlingTime;
    }

    public void setDismantlingTime(Date dismantlingTime) {
        this.dismantlingTime = dismantlingTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getGroundingTime() {
        return groundingTime;
    }

    public void setGroundingTime(Date groundingTime) {
        this.groundingTime = groundingTime;
    }

    public String getAttachmentBarCode() {
        return attachmentBarCode;
    }

    public void setAttachmentBarCode(String attachmentBarCode) {
        this.attachmentBarCode = attachmentBarCode;
    }

    public String getOneCategory() {
        return oneCategory;
    }

    public void setOneCategory(String oneCategory) {
        this.oneCategory = oneCategory;
    }

    public String getTwoCategory() {
        return twoCategory;
    }

    public void setTwoCategory(String twoCategory) {
        this.twoCategory = twoCategory;
    }

    public String getThreeCategory() {
        return threeCategory;
    }

    public void setThreeCategory(String threeCategory) {
        this.threeCategory = threeCategory;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(String commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getReservoirType() {
        return reservoirType;
    }

    public void setReservoirType(String reservoirType) {
        this.reservoirType = reservoirType;
    }

    public String getUploadPerson() {
        return uploadPerson;
    }

    public void setUploadPerson(String uploadPerson) {
        this.uploadPerson = uploadPerson;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}
