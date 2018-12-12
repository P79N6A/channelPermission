package com.haier.stock.model;

import java.io.Serializable;

public class InvBaseSupplierForBaseSend implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private int               type;                 //1:水家电正向 2:水家电逆向 3:3C正向
    private String            sale_org;             //销售组织
    private String            company;              //公司名称
    private String            order_type;           //订单类型
    private String            shoudafang;           //售达方

    /**
     * @return Returns the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type
     * The type to set.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return Returns the sale_org
     */
    public String getSale_org() {
        return sale_org;
    }

    /**
     * @param sale_org
     * The sale_org to set.
     */
    public void setSale_org(String sale_org) {
        this.sale_org = sale_org;
    }

    /**
     * @return Returns the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company
     * The company to set.
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return Returns the order_type
     */
    public String getOrder_type() {
        return order_type;
    }

    /**
     * @param order_type
     * The order_type to set.
     */
    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    /**
     * @return Returns the shoudafang
     */
    public String getShoudafang() {
        return shoudafang;
    }

    /**
     * @param shoudafang
     * The shoudafang to set.
     */
    public void setShoudafang(String shoudafang) {
        this.shoudafang = shoudafang;
    }

}