package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品活动。
 * <p>Table: <strong>ProductActivities</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>activityName</td><td>{@link String}</td><td>activityName</td><td>text</td><td>活动名称（促销标题）</td></tr>
 *   <tr><td>activityDesc</td><td>{@link String}</td><td>activityDesc</td><td>char</td><td>活动描述、说明</td></tr>
 *   <tr><td>activityRemark</td><td>{@link String}</td><td>activityRemark</td><td>text</td><td>活动说明</td></tr>
 *   <tr><td>partType</td><td>{@link Integer}</td><td>partType</td><td>tinyint</td><td>团购所属版块，1-今日团购，2-一台团</td></tr>
 *   <tr><td>virtualSaleNum</td><td>{@link Integer}</td><td>virtualSaleNum</td><td>int</td><td>虚拟销量</td></tr>
 *   <tr><td>startTime</td><td>{@link Integer}</td><td>startTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>endTime</td><td>{@link Integer}</td><td>endTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>productId</td><td>{@link Integer}</td><td>productId</td><td>int</td><td>商品id</td></tr>
 *   <tr><td>activityCateId</td><td>{@link Integer}</td><td>activityCateId</td><td>int</td><td>活动分类</td></tr>
 *   <tr><td>activityCatePath</td><td>{@link String}</td><td>activityCatePath</td><td>varchar</td><td>活动分类路径</td></tr>
 *   <tr><td>marketPrice</td><td>{@link BigDecimal}</td><td>marketPrice</td><td>decimal</td><td>原价或市场价</td></tr>
 *   <tr><td>price</td><td>{@link BigDecimal}</td><td>price</td><td>decimal</td><td>销售价</td></tr>
 *   <tr><td>stock</td><td>{@link Integer}</td><td>stock</td><td>int</td><td>库存</td></tr>
 *   <tr><td>firstBookAmount</td><td>{@link BigDecimal}</td><td>firstBookAmount</td><td>decimal</td><td>首付</td></tr>
 *   <tr><td>firstBookStartTime</td><td>{@link Integer}</td><td>firstBookStartTime</td><td>int</td><td>首付开始时间</td></tr>
 *   <tr><td>firstBookEndTime</td><td>{@link Integer}</td><td>firstBookEndTime</td><td>int</td><td>首付结束时间</td></tr>
 *   <tr><td>lastBookStartTime</td><td>{@link Integer}</td><td>lastBookStartTime</td><td>int</td><td>尾款结束时间</td></tr>
 *   <tr><td>lastBookEndTime</td><td>{@link Integer}</td><td>lastBookEndTime</td><td>int</td><td>尾款结束时间</td></tr>
 *   <tr><td>sortNum</td><td>{@link Integer}</td><td>sortNum</td><td>tinyint</td><td>权重</td></tr>
 *   <tr><td>isBest</td><td>{@link Integer}</td><td>isBest</td><td>tinyint</td><td>是否推荐</td></tr>
 *   <tr><td>limitCityIds</td><td>{@link String}</td><td>limitCityIds</td><td>varchar</td><td>活动适用城市ID</td></tr>
 *   <tr><td>activityState</td><td>{@link Integer}</td><td>activityState</td><td>tinyint</td><td>活动状态：0未发布 1发布 2结束</td></tr>
 *   <tr><td>activityType</td><td>{@link Integer}</td><td>activityType</td><td>tinyint</td><td>活动类型:团购是1,秒杀是2</td></tr>
 *   <tr><td>extensions</td><td>{@link String}</td><td>extensions</td><td>text</td><td>扩展信息</td></tr>
 *   <tr><td>state</td><td>{@link Integer}</td><td>state</td><td>tinyint</td><td>审核状态：0正常状态,1新记录,2修改过,3新记录被拒绝,4修改过被拒绝</td></tr>
 *   <tr><td>modifiedFields</td><td>{@link String}</td><td>modifiedFields</td><td>text</td><td>修改过的字段，序列化存储</td></tr>
 *   <tr><td>rejectReason</td><td>{@link String}</td><td>rejectReason</td><td>varchar</td><td>拒绝原因</td></tr>
 *   <tr><td>imageId</td><td>{@link String}</td><td>imageId</td><td>varchar</td><td>图片id</td></tr>
 * </table>
 * @author rongmai
 */
public class ProductActivities implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -4941896448575575168L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer siteId;

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private String activityName;

    /**
     * 获取 活动名称（促销标题）
     */
    public String getActivityName() {
        return this.activityName;
    }

    /**
     * 设置 活动名称（促销标题）
     * @param value 属性值
     */
    public void setActivityName(String value) {
        this.activityName = value;
    }

    private String activityDesc;

    /**
     * 获取 活动描述、说明
     */
    public String getActivityDesc() {
        return this.activityDesc;
    }

    /**
     * 设置 活动描述、说明
     * @param value 属性值
     */
    public void setActivityDesc(String value) {
        this.activityDesc = value;
    }

    private String activityRemark;

    /**
     * 获取 活动说明
     */
    public String getActivityRemark() {
        return this.activityRemark;
    }

    /**
     * 设置 活动说明
     * @param value 属性值
     */
    public void setActivityRemark(String value) {
        this.activityRemark = value;
    }

    private Integer partType;

    /**
     * 获取 团购所属版块，1-今日团购，2-一台团
     */
    public Integer getPartType() {
        return this.partType;
    }

    /**
     * 设置 团购所属版块，1-今日团购，2-一台团
     * @param value 属性值
     */
    public void setPartType(Integer value) {
        this.partType = value;
    }

    private Integer virtualSaleNum;

    /**
     * 获取 虚拟销量
     */
    public Integer getVirtualSaleNum() {
        return this.virtualSaleNum;
    }

    /**
     * 设置 虚拟销量
     * @param value 属性值
     */
    public void setVirtualSaleNum(Integer value) {
        this.virtualSaleNum = value;
    }

    private Integer startTime;

    public Integer getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Integer value) {
        this.startTime = value;
    }

    private Integer endTime;

    public Integer getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Integer value) {
        this.endTime = value;
    }

    private Integer productId;

    /**
     * 获取 商品id
     */
    public Integer getProductId() {
        return this.productId;
    }

    /**
     * 设置 商品id
     * @param value 属性值
     */
    public void setProductId(Integer value) {
        this.productId = value;
    }

    private Integer activityCateId;

    /**
     * 获取 活动分类
     */
    public Integer getActivityCateId() {
        return this.activityCateId;
    }

    /**
     * 设置 活动分类
     * @param value 属性值
     */
    public void setActivityCateId(Integer value) {
        this.activityCateId = value;
    }

    private String activityCatePath;

    /**
     * 获取 活动分类路径
     */
    public String getActivityCatePath() {
        return this.activityCatePath;
    }

    /**
     * 设置 活动分类路径
     * @param value 属性值
     */
    public void setActivityCatePath(String value) {
        this.activityCatePath = value;
    }

    private BigDecimal marketPrice;

    /**
     * 获取 原价或市场价
     */
    public BigDecimal getMarketPrice() {
        return this.marketPrice;
    }

    /**
     * 设置 原价或市场价
     * @param value 属性值
     */
    public void setMarketPrice(BigDecimal value) {
        this.marketPrice = value;
    }

    private BigDecimal price;

    /**
     * 获取 销售价
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * 设置 销售价
     * @param value 属性值
     */
    public void setPrice(BigDecimal value) {
        this.price = value;
    }

    private Integer stock;

    /**
     * 获取 库存
     */
    public Integer getStock() {
        return this.stock;
    }

    /**
     * 设置 库存
     * @param value 属性值
     */
    public void setStock(Integer value) {
        this.stock = value;
    }

    private BigDecimal firstBookAmount;

    /**
     * 获取 首付
     */
    public BigDecimal getFirstBookAmount() {
        return this.firstBookAmount;
    }

    /**
     * 设置 首付
     * @param value 属性值
     */
    public void setFirstBookAmount(BigDecimal value) {
        this.firstBookAmount = value;
    }

    private Integer firstBookStartTime;

    /**
     * 获取 首付开始时间
     */
    public Integer getFirstBookStartTime() {
        return this.firstBookStartTime;
    }

    /**
     * 设置 首付开始时间
     * @param value 属性值
     */
    public void setFirstBookStartTime(Integer value) {
        this.firstBookStartTime = value;
    }

    private Integer firstBookEndTime;

    /**
     * 获取 首付结束时间
     */
    public Integer getFirstBookEndTime() {
        return this.firstBookEndTime;
    }

    /**
     * 设置 首付结束时间
     * @param value 属性值
     */
    public void setFirstBookEndTime(Integer value) {
        this.firstBookEndTime = value;
    }

    private Integer lastBookStartTime;

    /**
     * 获取 尾款结束时间
     */
    public Integer getLastBookStartTime() {
        return this.lastBookStartTime;
    }

    /**
     * 设置 尾款结束时间
     * @param value 属性值
     */
    public void setLastBookStartTime(Integer value) {
        this.lastBookStartTime = value;
    }

    private Integer lastBookEndTime;

    /**
     * 获取 尾款结束时间
     */
    public Integer getLastBookEndTime() {
        return this.lastBookEndTime;
    }

    /**
     * 设置 尾款结束时间
     * @param value 属性值
     */
    public void setLastBookEndTime(Integer value) {
        this.lastBookEndTime = value;
    }

    private Integer sortNum;

    /**
     * 获取 权重
     */
    public Integer getSortNum() {
        return this.sortNum;
    }

    /**
     * 设置 权重
     * @param value 属性值
     */
    public void setSortNum(Integer value) {
        this.sortNum = value;
    }

    private Integer isBest;

    /**
     * 获取 是否推荐
     */
    public Integer getIsBest() {
        return this.isBest;
    }

    /**
     * 设置 是否推荐
     * @param value 属性值
     */
    public void setIsBest(Integer value) {
        this.isBest = value;
    }

    private String limitCityIds;

    /**
     * 获取 活动适用城市ID
     */
    public String getLimitCityIds() {
        return this.limitCityIds;
    }

    /**
     * 设置 活动适用城市ID
     * @param value 属性值
     */
    public void setLimitCityIds(String value) {
        this.limitCityIds = value;
    }

    private Integer activityState;

    /**
     * 获取 活动状态：0未发布 1发布 2结束
     */
    public Integer getActivityState() {
        return this.activityState;
    }

    /**
     * 设置 活动状态：0未发布 1发布 2结束
     * @param value 属性值
     */
    public void setActivityState(Integer value) {
        this.activityState = value;
    }

    private Integer activityType;

    /**
     * 获取 活动类型:团购是1,秒杀是2
     */
    public Integer getActivityType() {
        return this.activityType;
    }

    /**
     * 设置 活动类型:团购是1,秒杀是2
     * @param value 属性值
     */
    public void setActivityType(Integer value) {
        this.activityType = value;
    }

    private String extensions;

    /**
     * 获取 扩展信息
     */
    public String getExtensions() {
        return this.extensions;
    }

    /**
     * 设置 扩展信息
     * @param value 属性值
     */
    public void setExtensions(String value) {
        this.extensions = value;
    }

    private Integer state;

    /**
     * 获取 审核状态：0正常状态,1新记录,2修改过,3新记录被拒绝,4修改过被拒绝
     */
    public Integer getState() {
        return this.state;
    }

    /**
     * 设置 审核状态：0正常状态,1新记录,2修改过,3新记录被拒绝,4修改过被拒绝
     * @param value 属性值
     */
    public void setState(Integer value) {
        this.state = value;
    }

    private String modifiedFields;

    /**
     * 获取 修改过的字段，序列化存储
     */
    public String getModifiedFields() {
        return this.modifiedFields;
    }

    /**
     * 设置 修改过的字段，序列化存储
     * @param value 属性值
     */
    public void setModifiedFields(String value) {
        this.modifiedFields = value;
    }

    private String rejectReason;

    /**
     * 获取 拒绝原因
     */
    public String getRejectReason() {
        return this.rejectReason;
    }

    /**
     * 设置 拒绝原因
     * @param value 属性值
     */
    public void setRejectReason(String value) {
        this.rejectReason = value;
    }

    private String imageId;

    /**
     * 获取 图片id
     */
    public String getImageId() {
        return this.imageId;
    }

    /**
     * 设置 图片id
     * @param value 属性值
     */
    public void setImageId(String value) {
        this.imageId = value;
    }

}