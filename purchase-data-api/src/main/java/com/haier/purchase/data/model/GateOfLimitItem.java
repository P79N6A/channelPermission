package com.haier.purchase.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author 刘骥飞
 * @date 2014-8-18
 * @email jifei.liu@dhc.com.cn
 */
public class GateOfLimitItem implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7327783337319341946L;
    private String            id;
    private String            category_id;                             //品类
    private String            ed_channel_id;                           //渠道
    private String            ed_channel_name;                         //渠道名
    private String            target_num;                              //指标
    private String            limit_num;                               //额度
    private BigDecimal        total_num;                               //总库存
    private BigDecimal        on_way_num;                              //在途
    private BigDecimal        used_num;                                //本周已用
    private BigDecimal        available_num;                           //可用额度
    private String            loan_num;                                //拆借
    private String            modify_user;                             //修改人
    private String            modify_time;                             //修改时间
    private Integer           month;                                   //月份
    private BigDecimal        target_num_month1;                       //1月份指标
    private BigDecimal        target_num_month2;                       //2月份指标
    private BigDecimal        target_num_month3;                       //3月份指标
    private BigDecimal        target_num_month4;                       //4月份指标
    private BigDecimal        target_num_month5;                       //5月份指标
    private BigDecimal        target_num_month6;                       //6月份指标
    private BigDecimal        target_num_month7;                       //7月份指标
    private BigDecimal        target_num_month8;                       //8月份指标
    private BigDecimal        target_num_month9;                       //9月份指标
    private BigDecimal        target_num_month10;                      //10月份指标
    private BigDecimal        target_num_month11;                      //11月份指标
    private BigDecimal        target_num_month12;                      //12月份指标

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
     * @return Returns the category_id
     */
    public String getCategory_id() {
        return category_id;
    }

    /**
     * @param category_id
     * The category_id to set.
     */
    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    /**
     * @return Returns the ed_channel_id
     */
    public String getEd_channel_id() {
        return ed_channel_id;
    }

    /**
     * @param ed_channel_id
     * The ed_channel_id to set.
     */
    public void setEd_channel_id(String ed_channel_id) {
        this.ed_channel_id = ed_channel_id;
    }

    /**
     * @return Returns the ed_channel_name
     */
    public String getEd_channel_name() {
        return ed_channel_name;
    }

    /**
     * @param ed_channel_name
     * The ed_channel_name to set.
     */
    public void setEd_channel_name(String ed_channel_name) {
        this.ed_channel_name = ed_channel_name;
    }

    /**
     * @return Returns the target_num
     */
    public String getTarget_num() {
        return target_num;
    }

    /**
     * @param target_num
     * The target_num to set.
     */
    public void setTarget_num(String target_num) {
        this.target_num = target_num;
    }

    /**
     * @return Returns the limit_num
     */
    public String getLimit_num() {
        return limit_num;
    }

    /**
     * @param limit_num
     * The limit_num to set.
     */
    public void setLimit_num(String limit_num) {
        this.limit_num = limit_num;
    }

    /**
     * @return Returns the total_num
     */
    public BigDecimal getTotal_num() {
        return total_num;
    }

    /**
     * @param total_num
     * The total_num to set.
     */
    public void setTotal_num(BigDecimal total_num) {
        this.total_num = total_num;
    }

    /**
     * @return Returns the on_way_num
     */
    public BigDecimal getOn_way_num() {
        return on_way_num;
    }

    /**
     * @param on_way_num
     * The on_way_num to set.
     */
    public void setOn_way_num(BigDecimal on_way_num) {
        this.on_way_num = on_way_num;
    }

    /**
     * @return Returns the used_num
     */
    public BigDecimal getUsed_num() {
        return used_num;
    }

    /**
     * @param used_num
     * The used_num to set.
     */
    public void setUsed_num(BigDecimal used_num) {
        this.used_num = used_num;
    }

    /**
     * @return Returns the available_num
     */
    public BigDecimal getAvailable_num() {
        return available_num;
    }

    /**
     * @param available_num
     * The available_num to set.
     */
    public void setAvailable_num(BigDecimal available_num) {
        this.available_num = available_num;
    }

    /**
     * @return Returns the loan_num
     */
    public String getLoan_num() {
        return loan_num;
    }

    /**
     * @param loan_num
     * The loan_num to set.
     */
    public void setLoan_num(String loan_num) {
        this.loan_num = loan_num;
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

    /**
     * @return Returns the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @param month
     * The modify_time to set.
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * @return Returns the month
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * @return Returns the target_num_month1
     */
    public BigDecimal getTarget_num_month1() {
        return target_num_month1;
    }

    /**
     * @param target_num_month1
     * The target_num_month1 to set.
     */
    public void setTarget_num_month1(BigDecimal target_num_month1) {
        this.target_num_month1 = target_num_month1;
    }

    /**
     * @return Returns the target_num_month2
     */
    public BigDecimal getTarget_num_month2() {
        return target_num_month2;
    }

    /**
     * @param target_num_month2
     * The target_num_month2 to set.
     */
    public void setTarget_num_month2(BigDecimal target_num_month2) {
        this.target_num_month2 = target_num_month2;
    }

    /**
     * @return Returns the target_num_month3
     */
    public BigDecimal getTarget_num_month3() {
        return target_num_month3;
    }

    /**
     * @param target_num_month3
     * The target_num_month3 to set.
     */
    public void setTarget_num_month3(BigDecimal target_num_month3) {
        this.target_num_month3 = target_num_month3;
    }

    /**
     * @return Returns the target_num_month4
     */
    public BigDecimal getTarget_num_month4() {
        return target_num_month4;
    }

    /**
     * @param target_num_month4
     * The target_num_month4 to set.
     */
    public void setTarget_num_month4(BigDecimal target_num_month4) {
        this.target_num_month4 = target_num_month4;
    }

    /**
     * @return Returns the target_num_month5
     */
    public BigDecimal getTarget_num_month5() {
        return target_num_month5;
    }

    /**
     * @param target_num_month5
     * The target_num_month5 to set.
     */
    public void setTarget_num_month5(BigDecimal target_num_month5) {
        this.target_num_month5 = target_num_month5;
    }

    /**
     * @return Returns the target_num_month6
     */
    public BigDecimal getTarget_num_month6() {
        return target_num_month6;
    }

    /**
     * @param target_num_month6
     * The target_num_month6 to set.
     */
    public void setTarget_num_month6(BigDecimal target_num_month6) {
        this.target_num_month6 = target_num_month6;
    }

    /**
     * @return Returns the target_num_month7
     */
    public BigDecimal getTarget_num_month7() {
        return target_num_month7;
    }

    /**
     * @param target_num_month7
     * The target_num_month7 to set.
     */
    public void setTarget_num_month7(BigDecimal target_num_month7) {
        this.target_num_month7 = target_num_month7;
    }

    /**
     * @return Returns the target_num_month8
     */
    public BigDecimal getTarget_num_month8() {
        return target_num_month8;
    }

    /**
     * @param target_num_month8
     * The target_num_month8 to set.
     */
    public void setTarget_num_month8(BigDecimal target_num_month8) {
        this.target_num_month8 = target_num_month8;
    }

    /**
     * @return Returns the target_num_month9
     */
    public BigDecimal getTarget_num_month9() {
        return target_num_month9;
    }

    /**
     * @param target_num_month9
     * The target_num_month9 to set.
     */
    public void setTarget_num_month9(BigDecimal target_num_month9) {
        this.target_num_month9 = target_num_month9;
    }

    /**
     * @return Returns the target_num_month10
     */
    public BigDecimal getTarget_num_month10() {
        return target_num_month10;
    }

    /**
     * @param target_num_month10
     * The target_num_month10 to set.
     */
    public void setTarget_num_month10(BigDecimal target_num_month10) {
        this.target_num_month10 = target_num_month10;
    }

    /**
     * @return Returns the target_num_month11
     */
    public BigDecimal getTarget_num_month11() {
        return target_num_month11;
    }

    /**
     * @param target_num_month11
     * The target_num_month11 to set.
     */
    public void setTarget_num_month11(BigDecimal target_num_month11) {
        this.target_num_month11 = target_num_month11;
    }

    /**
     * @return Returns the target_num_month12
     */
    public BigDecimal getTarget_num_month12() {
        return target_num_month12;
    }

    /**
     * @param target_num_month12
     * The target_num_month12 to set.
     */
    public void setTarget_num_month12(BigDecimal target_num_month12) {
        this.target_num_month12 = target_num_month12;
    }

}