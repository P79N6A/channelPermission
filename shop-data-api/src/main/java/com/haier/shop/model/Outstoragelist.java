package com.haier.shop.model;

import com.haier.shop.util.excel.Excel;
import com.haier.shop.util.excel.ExcelTitle;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/14.
 */
@Excel(filename = "商品出库明细")
public class Outstoragelist implements Serializable {

    private static final long serialVersionUID = 7814832536750759452L;
    private Integer id;
    @ExcelTitle(titleName = "年份", importIndex = 0)
    private String years;          //年份
    @ExcelTitle(titleName = "月份", importIndex = 1)
    private String months;         //月份
    @ExcelTitle(titleName = "三级品类", importIndex = 2)
    private String threeCategory;   //三级品类
    @ExcelTitle(titleName = "数量", importIndex = 3)
    private String outqty;             //出库数
    private String uploadPerson;    //上传人
    private Date uploadTime;        //上传时间
    private String months2;

    public String getMonths2() {
        return months2;
    }
    public void setMonths2(String months2) {
        this.months2 = months2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getThreeCategory() {
        return threeCategory;
    }

    public void setThreeCategory(String threeCategory) {
        this.threeCategory = threeCategory;
    }

    public String getOutqty() {
        return outqty;
    }

    public void setOutqty(String outqty) {
        this.outqty = outqty;
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
