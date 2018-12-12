package com.haier.purchase.data.model;

import java.io.Serializable;

/**
 * 
 * @author 马军
 * 
 */
public class InvBudgetOrg implements Serializable {

    private static final long serialVersionUID = -4411504843206078873L;
    private Integer           id;
    private Integer           is_enabled;
    private String            is_enabled_name;
    private String            budgetorg_id;                            // 预算体编码
    private String            budgetorg_name;                          // 预算体名称
    private String            business_dep;                            // 事业部
    private String            area_id;                                 // 地区编码
    private String            area_name;                               // 地区名称
    private String            product_group_id;                        // 产品组编码
    private String            product_group_name;                      // 产品组名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBudgetorg_id() {
        return budgetorg_id;
    }

    public void setBudgetorg_id(String budgetorg_id) {
        this.budgetorg_id = budgetorg_id;
    }

    public String getBudgetorg_name() {
        return budgetorg_name;
    }

    public void setBudgetorg_name(String budgetorg_name) {
        this.budgetorg_name = budgetorg_name;
    }

    public String getBusiness_dep() {
        return business_dep;
    }

    public void setBusiness_dep(String business_dep) {
        this.business_dep = business_dep;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getProduct_group_id() {
        return product_group_id;
    }

    public void setProduct_group_id(String product_group_id) {
        this.product_group_id = product_group_id;
    }

    public String getProduct_group_name() {
        return product_group_name;
    }

    public void setProduct_group_name(String product_group_name) {
        this.product_group_name = product_group_name;
    }

    public Integer getIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(Integer is_enabled) {
        this.is_enabled = is_enabled;
    }

    public String getIs_enabled_name() {
        return is_enabled_name;
    }

    public void setIs_enabled_name(String is_enabled_name) {
        this.is_enabled_name = is_enabled_name;
    }

    @Override
    public String toString() {
        return "InvBudgetOrg [id=" + id + ", is_enabled=" + is_enabled + ", budgetorg_id="
               + budgetorg_id + ", budgetorg_name=" + budgetorg_name + ", business_dep="
               + business_dep + ", area_id=" + area_id + ", area_name=" + area_name
               + ", product_group_id=" + product_group_id + ", product_group_name="
               + product_group_name + "]";
    }

}