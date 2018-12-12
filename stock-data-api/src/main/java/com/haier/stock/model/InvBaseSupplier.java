package com.haier.stock.model;

import java.io.Serializable;

public class InvBaseSupplier implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String            sale_org_id;          //销售组织编码
    private String            supplier_id;          //供应商编码
    private String            supplier_name;        //供应商名称
    private String            shou_da_fang_code;    //对电商的售达方码
    private String            legal_customer;       //法人客户

    /**
     * @return Returns the sale_org_id
     */
    public String getSale_org_id() {
        return sale_org_id;
    }

    /**
     * @param sale_org_id
     * The sale_org_id to set.
     */
    public void setSale_org_id(String sale_org_id) {
        this.sale_org_id = sale_org_id;
    }

    /**
     * @return Returns the supplier_id
     */
    public String getSupplier_id() {
        return supplier_id;
    }

    /**
     * @param supplier_id
     * The supplier_id to set.
     */
    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    /**
     * @return Returns the supplier_name
     */
    public String getSupplier_name() {
        return supplier_name;
    }

    /**
     * @param supplier_name
     * The supplier_name to set.
     */
    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    /**
     * @return Returns the shou_da_fang_code
     */
    public String getShou_da_fang_code() {
        return shou_da_fang_code;
    }

    /**
     * @param shou_da_fang_code
     * The shou_da_fang_code to set.
     */
    public void setShou_da_fang_code(String shou_da_fang_code) {
        this.shou_da_fang_code = shou_da_fang_code;
    }

    /**
     * @return Returns the legal_customer
     */
    public String getLegal_customer() {
        return legal_customer;
    }

    /**
     * @param legal_customer
     * The legal_customer to set.
     */
    public void setLegal_customer(String legal_customer) {
        this.legal_customer = legal_customer;
    }

}