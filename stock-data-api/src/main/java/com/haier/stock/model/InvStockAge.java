package com.haier.stock.model;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


/**
 * 库龄表。
 *
 * <p>Table: <strong>inv_stock_age</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>itemId</td><td>{@link Integer}</td><td>item_id</td><td>int</td><td>商品Id</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编号</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>库位编码。</td></tr>
 *   <tr><td>secName</td><td>{@link String}</td><td>sec_name</td><td>varchar</td><td>库位名称</td></tr>
 *   <tr><td>productName</td><td>{@link String}</td><td>product_name</td><td>char</td><td>产品型号</td></tr>
 *   <tr><td>productTypeName</td><td>{@link String}</td><td>product_type_name</td><td>varchar</td><td>品类</td></tr>
 *    <tr><td>productGroupName</td><td>{@link String}</td><td>product_group_name</td><td>varchar</td><td>产品组</td></tr>
 *   <tr><td>channelCode</td><td>{@link String}</td><td>channel_code</td><td>varchar</td><td>渠道编号</td></tr>
 *   <tr><td>channelName</td><td>{@link String}</td><td>channel_name</td><td>varchar</td><td>渠道名称</td></tr>
 *   <tr><td>ageData</td><td>{@link String}</td><td>age_data</td><td>text</td><td>库龄数据<br />以{库龄1:数量,库龄2:数量}格式的json保存，库龄单位为天</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>更新时间</td></tr>
 *   <tr><td>date</td><td>{@link Date}</td><td>date</td><td>datetime</td><td>清算日期（计算库龄）</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>price</td><td>{@link BigDecimal}</td><td>price</td><td>decimal</td><td>创建时间</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-6-8
 * @email yudi@sina.com
 */
public class InvStockAge implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer itemId;

    /**
     * 获取 商品Id。
     */
    public Integer getItemId() {
        return this.itemId;
    }

    /**
     * 设置 商品Id。
     *
     * @param value 属性值
     */
    public void setItemId(Integer value) {
        this.itemId = value;
    }

    private String brand;

    /**
     * @return Returns the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand
     * The brand to set.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    private String sku;

    /**
     * 获取 物料编号。
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 物料编号。
     *
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private String secCode;

    /**
     * 获取 库位编码。
     */
    public String getSecCode() {
        return this.secCode;
    }

    /**
     * 设置 库位编码。
     *
     * @param value 属性值
     */
    public void setSecCode(String value) {
        this.secCode = value;
    }

    private String secName;

    /**
     * 获取 库位名称。
     */
    public String getSecName() {
        return this.secName;
    }

    /**
     * 设置 库位名称。
     *
     * @param value 属性值
     */
    public void setSecName(String value) {
        this.secName = value;
    }

    private String productName;

    /**
     * 获取 产品型号。
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * 设置 产品型号。
     *
     * @param value 属性值
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    private String productTypeName;

    /**
     * 获取 品类。
     */
    public String getProductTypeName() {
        return this.productTypeName;
    }

    /**
     * 设置 品类。
     *
     * @param value 属性值
     */
    public void setProductTypeName(String value) {
        this.productTypeName = value;
    }

    private String productGroupName;

    /**
     * 获取 产品组。
     */
    public String getProductGroupName() {
        return productGroupName;
    }

    /**
     * 设置 产品组。
     */
    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

    private String channelName;

    /**
     * 获取 渠道名称。
     */
    public String getChannelName() {
        return this.channelName;
    }

    /**
     * 设置 渠道名称。
     *
     * @param value 属性值
     */
    public void setChannelName(String value) {
        this.channelName = value;
    }

    private String channelCode;

    /**
     * 获取 渠道编号。
     */
    public String getChannelCode() {
        return this.channelCode;
    }

    /**
     * 设置 渠道编号。
     *
     * @param value 属性值
     */
    public void setChannelCode(String value) {
        this.channelCode = value;
    }

    /**
     * WA-共享数量
     */
    private Integer waStockQty;

    public Integer getWaStockQty() {
        return waStockQty;
    }

    public void setWaStockQty(Integer waStockQty) {
        this.waStockQty = waStockQty;
    }

    private String ageData;

    /**
     * 获取 库龄数据。
     *
     * <p>
     * 以{库龄1:数量,库龄2:数量}格式的json保存，库龄单位为天
     */
    public String getAgeData() {
        return this.ageData;
    }

    /**
     * 设置 库龄数据。
     *
     * <p>
     * 以{库龄1:数量,库龄2:数量}格式的json保存，库龄单位为天
     *
     * @param value 属性值
     */
    public void setAgeData(String value) {
        this.ageData = value;
    }

    /**
     * WA-共享库龄数据
     */
    private String waAgeData;

    public String getWaAgeData() {
        return waAgeData;
    }

    public void setWaAgeData(String waAgeData) {
        this.waAgeData = waAgeData;
    }

    private Integer frozenQty;

    public Integer getFrozenQty() {
        return frozenQty;
    }

    public void setFrozenQty(Integer frozenQty) {
        this.frozenQty = frozenQty;
    }

    private Integer waFrozenQty;

    public Integer getWaFrozenQty() {
        return waFrozenQty;
    }

    public void setWaFrozenQty(Integer waFrozenQty) {
        this.waFrozenQty = waFrozenQty;
    }

    private Date updateTime;

    /**
     * 获取 更新时间。
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置 更新时间。
     *
     * @param value 属性值
     */
    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

    private Date createTime;

    /**
     * 获取 创建时间。
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setCreateTime(Date value) {
        this.createTime = value;
    }

    /**
     * 日期
     */
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 库龄数据对象
     */
    private List<StockAgeData> stockAgeDatas;
    /**
     * WA-共享库龄数据对象
     */
    private List<StockAgeData> waStockAgeDatas;

    public List<StockAgeData> getWaStockAgeDatas() {
        if (waStockAgeDatas == null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<StockAgeData>>() {
            }.getType();
            waStockAgeDatas = gson.fromJson(ageData, type);
            if (waStockAgeDatas == null)
                waStockAgeDatas = new ArrayList<InvStockAge.StockAgeData>();
        }
        return waStockAgeDatas;
    }

    public void setWaStockAgeDatas(List<StockAgeData> waStockAgeDatas) {
        this.waStockAgeDatas = waStockAgeDatas;
    }

    public List<StockAgeData> getStockAgeDatas() {
        if (stockAgeDatas == null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<StockAgeData>>() {
            }.getType();
            stockAgeDatas = gson.fromJson(ageData, type);
            if (stockAgeDatas == null)
                stockAgeDatas = new ArrayList<InvStockAge.StockAgeData>();
        }
        return stockAgeDatas;
    }

    public void setStockAgeDatas(List<StockAgeData> stockAgeDatas) {
        this.stockAgeDatas = stockAgeDatas;
        refreshAgeDatas();
    }

    public void refreshAgeDatas() {
        if (stockAgeDatas == null)
            ageData = null;
        else {
            List<StockAgeData> stockAgeDatas1 = new ArrayList<InvStockAge.StockAgeData>();
            for (StockAgeData ageData : stockAgeDatas) {
                if (ageData.getStockQuantity() > 0) {
                    BigDecimal price = getPrice();
                    if (price != null)
                        ageData.setValue(getPrice().multiply(
                            new BigDecimal(ageData.getStockQuantity())));
                    stockAgeDatas1.add(ageData);
                }
            }
            stockAgeDatas = stockAgeDatas1;
            if (stockAgeDatas.size() == 0)
                ageData = null;
            else
                ageData = toJson(stockAgeDatas);
        }
    }
    /**
     * 将Java对象序列化成JSON字符串。
     * @param obj
     * @return
     */
    public static final String toJson(Object obj) {
        if (obj == null)
            return null;
        try {
            GsonBuilder gb = new GsonBuilder();
            gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
            return gb.create().toJson(obj);
        } catch (Exception e) {
//            log.warn("Can not serialize object to json", e);
            return null;
        }
    }
    public int ageDatasSize() {
        return getStockAgeDatas().size();
    }

    public StockAgeData getMaxAgeData() {
        return getStockAgeDatas().get(ageDatasSize() - 1);
    }

    public class StockAgeData implements Serializable {
        /**
         * 库龄 单位天
         */
        private Integer     age;

        /**
         * 存货
         */
        private Integer     stockQuantity;

        /**
         * 价值 单位（元）
         */
        private BigDecimal  value;

        /**
         * 关联的库龄
         */
        private InvStockAge referenceStockAge = null;

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Integer getStockQuantity() {
            return stockQuantity;
        }

        public void setStockQuantity(Integer stockQuantity) {
            this.stockQuantity = stockQuantity;
        }

        public BigDecimal getValue() {
            return value;
        }

        public void setValue(BigDecimal value) {
            this.value = value;
        }

        /**
         * @return Returns the referenceStockAge
         */
        public InvStockAge getReferenceStockAge() {
            return referenceStockAge;
        }

        /**
         * @param referenceStockAge
         * The referenceStockAge to set.
         */
        public void setReferenceStockAge(InvStockAge referenceStockAge) {
            this.referenceStockAge = referenceStockAge;
        }

    }

}
