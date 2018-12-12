package com.haier.eis.model;

/**
 * Created by hanhaoyang@ehaier.com on 2017/11/27 0027.
 */
public class GQGYSStock {
    private int id;
    //商家编码
    private String sku;
    //商品名称
    private String productName;
    //商品类型
    private String productType;
    //商品重量(g)
    private int productWeight;
    //仓库回传重量(g)
    private int returnWeight;
    //商品类别
    private String productCategory;
    //货品ID
    private String productId;
    //库位
    private String scode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(int productWeight) {
        this.productWeight = productWeight;
    }

    public int getReturnWeight() {
        return returnWeight;
    }

    public void setReturnWeight(int returnWeight) {
        this.returnWeight = returnWeight;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }
}
