package com.haier.eis.model;

import java.io.Serializable;

/**
 * LES出库明细。
 *
 * <p>Table: <strong>les_stock_item</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>lesStockSyncsId</td><td>{@link Integer}</td><td>les_stock_syncs_id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>sCode</td><td>{@link String}</td><td>sCode</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>stock</td><td>{@link Integer}</td><td>stock</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>type</td><td>{@link Integer}</td><td>type</td><td>int</td><td>类型<br />1：库存变化对应stock<br />2：出库对应status</td></tr>
 *   <tr><td>eaitime</td><td>{@link Integer}</td><td>eaitime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>data</td><td>{@link String}</td><td>data</td><td>nvarchar</td><td>数据内容</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>int</td><td>状态<br />0：待处理<br />1：已处理</td></tr>
 *   <tr><td>processTime</td><td>{@link Integer}</td><td>process_time</td><td>int</td><td>处理时间</td></tr>
 *   <tr><td>addTime</td><td>{@link Integer}</td><td>add_time</td><td>int</td><td>创建时间</td></tr>
 *   <tr><td>errorMessage</td><td>{@link String}</td><td>error_message</td><td>nvarchar</td><td>&nbsp;</td></tr>
 *   <tr><td>versionCode</td><td>{@link String}</td><td>version_code</td><td>varchar</td><td>版本编号 - 作为唯一标识使用</td></tr>
 *   <tr><td>channelCode</td><td>{@link String}</td><td>channel_code</td><td>varchar</td><td>渠道编码。<br />WA：网单库位<br />RRS：日日顺库位</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-5-17
 * @email yudi@sina.com
 */
public class LesStockItem implements Serializable {

    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    /**
     * 状态 - 处理中
     */
    public static Integer     STATUS_WAIT      = 0;
    /**
     * 状态 - 已接收
     */
    public static Integer     STATUS_RECEIVED  = 10;
    /**
     * 状态 - 已处理
     */
    public static Integer     STATUS_DONE      = 1;

    public static Integer     STATUS_ERROR     = -1;

    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer lesStockSyncsId;

    public Integer getLesStockSyncsId() {
        return this.lesStockSyncsId;
    }

    public void setLesStockSyncsId(Integer value) {
        this.lesStockSyncsId = value;
    }

    private String sCode;

    public String getSCode() {
        return this.sCode;
    }

    public void setSCode(String value) {
        this.sCode = value;
    }

    private String externalSku;

    private String sku;

    public String getSku() {
        return this.sku;
    }

    public void setSku(String value) {
        this.sku = value;
    }

    private Integer stock;

    public Integer getStock() {
        return this.stock;
    }

    public void setStock(Integer value) {
        this.stock = value;
    }

    private Integer type;

    /**
     * 获取 类型。
     *
     * <p>
     * 10：良品<br />
     * 90：非卖品<br />
     * 22: 不良品
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置 商品类型。
     *
     * <p>
     * 10：良品<br />
     * 90：非卖品<br />
     * 22: 不良品
     *
     * @param value 属性值
     */
    public void setType(Integer value) {
        this.type = value;
    }

    private Long eaitime;

    public Long getEaitime() {
        return this.eaitime;
    }

    public void setEaitime(Long value) {
        this.eaitime = value;
    }

    private String data;

    /**
     * 获取 数据内容。
     */
    public String getData() {
        return this.data;
    }

    /**
     * 设置 数据内容。
     *
     * @param value 属性值
     */
    public void setData(String value) {
        this.data = value;
    }

    private Integer status;

    /**
     * 获取 状态。
     *
     * <p>
     * 0：待处理<br />
     * 1：已处理
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态。
     *
     * <p>
     * 0：待处理<br />
     * 1：已处理
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private Integer processTime = 0;

    /**
     * 获取 处理时间。
     */
    public Integer getProcessTime() {
        return this.processTime;
    }

    /**
     * 设置 处理时间。
     *
     * @param value 属性值
     */
    public void setProcessTime(Integer value) {
        this.processTime = value;
    }

    private Integer addTime;

    /**
     * 获取 创建时间。
     */
    public Integer getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setAddTime(Integer value) {
        this.addTime = value;
    }

    private String errorMessage;

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    private String versionCode;

    /**
     * 获取 版本编号 - 作为唯一标识使用。
     */
    public String getVersionCode() {
        return this.versionCode;
    }

    /**
     * 设置 版本编号 - 作为唯一标识使用。
     *
     * @param value 属性值
     */
    public void setVersionCode(String value) {
        this.versionCode = value;
    }

    private String channelCode;

    /**
     * 获取 渠道编码。
     *
     * <p>
     * WA：网单库位<br />
     * RRS：日日顺库位
     */
    public String getChannelCode() {
        return this.channelCode;
    }

    /**
     * 设置 渠道编码。
     *
     * <p>
     * WA：网单库位<br />
     * RRS：日日顺库位
     *
     * @param value 属性值
     */
    public void setChannelCode(String value) {
        this.channelCode = value;
    }

    public String getExternalSku() {
        return externalSku;
    }

    public void setExternalSku(String externalSku) {
        this.externalSku = externalSku;
    }

}