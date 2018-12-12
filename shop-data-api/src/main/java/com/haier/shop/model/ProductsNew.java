package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Table: <strong>ProductsNew</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>productName</td><td>{@link String}</td><td>productName</td><td>varchar</td><td>商品名称</td></tr>
 *   <tr><td>stockToTaotao</td><td>{@link Integer}</td><td>stockToTaotao</td><td>tinyint</td><td>&nbsp;</td></tr>
 *   <tr><td>lastSyncTime</td><td>{@link Integer}</td><td>lastSyncTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>taobaoId</td><td>{@link String}</td><td>taobaoId</td><td>varchar</td><td>对应的淘宝商品ID</td></tr>
 *   <tr><td>wlbItemId</td><td>{@link String}</td><td>wlbItemId</td><td>varchar</td><td>对应的WLB商品ID</td></tr>
 *   <tr><td>productActivityInfo</td><td>{@link String}</td><td>productActivityInfo</td><td>varchar</td><td>商品活动信息 用于商品详情页显示在商品名称后面</td></tr>
 *   <tr><td>productTitle</td><td>{@link String}</td><td>productTitle</td><td>varchar</td><td>商品标题，商品在前台展示时显示在名称后面的描述信息</td></tr>
 *   <tr><td>productTags</td><td>{@link String}</td><td>productTags</td><td>varchar</td><td>存储格式（手机,电脑,gps）</td></tr>
 *   <tr><td>brandId</td><td>{@link Integer}</td><td>brandId</td><td>int</td><td>品牌id</td></tr>
 *   <tr><td>defaultImageFileId</td><td>{@link String}</td><td>defaultImageFileId</td><td>varchar</td><td>商品图片文件id</td></tr>
 *   <tr><td>detailImageFileId</td><td>{@link String}</td><td>detailImageFileId</td><td>varchar</td><td>商品细节图</td></tr>
 *   <tr><td>sceneImageFileId</td><td>{@link String}</td><td>sceneImageFileId</td><td>varchar</td><td>商品场景图</td></tr>
 *   <tr><td>productTypeId</td><td>{@link Integer}</td><td>productTypeId</td><td>int</td><td>商品类型id</td></tr>
 *   <tr><td>sellingPoint</td><td>{@link String}</td><td>sellingPoint</td><td>text</td><td>产品卖点，序列化数据</td></tr>
 *   <tr><td>productDetail</td><td>{@link String}</td><td>productDetail</td><td>char</td><td>商品详细描述</td></tr>
 *   <tr><td>productBlockDetail</td><td>{@link String}</td><td>productBlockDetail</td><td>text</td><td>带版式商品详情</td></tr>
 *   <tr><td>productDetail2</td><td>{@link String}</td><td>productDetail2</td><td>char</td><td>详情底部内容</td></tr>
 *   <tr><td>addTime</td><td>{@link Integer}</td><td>addTime</td><td>int</td><td>添加时间</td></tr>
 *   <tr><td>lastModify</td><td>{@link Integer}</td><td>lastModify</td><td>int</td><td>最后更改时间</td></tr>
 *   <tr><td>productCateId</td><td>{@link Integer}</td><td>productCateId</td><td>int</td><td>商品类目id</td></tr>
 *   <tr><td>productCatePath</td><td>{@link String}</td><td>productCatePath</td><td>varchar</td><td>商品分类路径（存储格式：/1/2/3/），搜索时可以like用索引</td></tr>
 *   <tr><td>onSale</td><td>{@link Integer}</td><td>onSale</td><td>tinyint</td><td>1上架0下架</td></tr>
 *   <tr><td>isStar</td><td>{@link Integer}</td><td>isStar</td><td>tinyint</td><td>是否加星标 0 不加星标 1 加星标</td></tr>
 *   <tr><td>isHot</td><td>{@link Integer}</td><td>isHot</td><td>tinyint</td><td>热卖</td></tr>
 *   <tr><td>isBest</td><td>{@link Integer}</td><td>isBest</td><td>tinyint</td><td>精品</td></tr>
 *   <tr><td>isNew</td><td>{@link Integer}</td><td>isNew</td><td>tinyint</td><td>新品</td></tr>
 *   <tr><td>isSpecial</td><td>{@link Integer}</td><td>isSpecial</td><td>tinyint</td><td>是否特殊商品 0普通商品 1特殊商品</td></tr>
 *   <tr><td>isDelete</td><td>{@link Integer}</td><td>isDelete</td><td>tinyint</td><td>是否为删除状态,1-已删除,0-未删除</td></tr>
 *   <tr><td>bookable</td><td>{@link Integer}</td><td>bookable</td><td>tinyint</td><td>可预订</td></tr>
 *   <tr><td>bookDays</td><td>{@link Integer}</td><td>bookDays</td><td>int</td><td>预订到货天数</td></tr>
 *   <tr><td>isForbidArrivalNotice</td><td>{@link Integer}</td><td>isForbidArrivalNotice</td><td>tinyint</td><td>是否禁止到货通知</td></tr>
 *   <tr><td>supplyPrice</td><td>{@link BigDecimal}</td><td>supplyPrice</td><td>decimal</td><td>商品供货价格</td></tr>
 *   <tr><td>packagePrice</td><td>{@link BigDecimal}</td><td>packagePrice</td><td>decimal</td><td>套装价</td></tr>
 *   <tr><td>saleGuidePrice</td><td>{@link BigDecimal}</td><td>saleGuidePrice</td><td>decimal</td><td>参考价格,销售指导价格</td></tr>
 *   <tr><td>energySubsidyAmount</td><td>{@link BigDecimal}</td><td>energySubsidyAmount</td><td>decimal</td><td>节能补贴金额</td></tr>
 *   <tr><td>energySubsidyProductName</td><td>{@link String}</td><td>energySubsidyProductName</td><td>varchar</td><td>节能补贴商品名称-会在发票上体现</td></tr>
 *   <tr><td>internalPrice</td><td>{@link BigDecimal}</td><td>internalPrice</td><td>decimal</td><td>内部员工价</td></tr>
 *   <tr><td>rankPrice</td><td>{@link BigDecimal}</td><td>rankPrice</td><td>decimal</td><td>会员组会员价</td></tr>
 *   <tr><td>rankGroups</td><td>{@link String}</td><td>rankGroups</td><td>varchar</td><td>可用会员价的会员组</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>商家编码</td></tr>
 *   <tr><td>storageType</td><td>{@link Integer}</td><td>storageType</td><td>tinyint</td><td>物料编号所属库位类型，1-大库,2-小库</td></tr>
 *   <tr><td>memberRanks</td><td>{@link String}</td><td>memberRanks</td><td>varchar</td><td>限定会员组</td></tr>
 *   <tr><td>similarProductIds</td><td>{@link String}</td><td>similarProductIds</td><td>varchar</td><td>相似商品，逗号分隔，左右加逗号</td></tr>
 *   <tr><td>modifiedFields</td><td>{@link String}</td><td>modifiedFields</td><td>text</td><td>修改过的字段，序列化存储</td></tr>
 *   <tr><td>rejectReason</td><td>{@link String}</td><td>rejectReason</td><td>varchar</td><td>拒绝原因</td></tr>
 *   <tr><td>state</td><td>{@link Integer}</td><td>state</td><td>tinyint</td><td>审核状态：0正常状态,1新记录,2修改过,3新记录被拒绝,4修改过被拒绝</td></tr>
 *   <tr><td>keywords</td><td>{@link String}</td><td>keywords</td><td>text</td><td>页面关键字，用户搜索引擎优化</td></tr>
 *   <tr><td>description</td><td>{@link String}</td><td>description</td><td>text</td><td>商品描述，用户搜索引擎优化</td></tr>
 *   <tr><td>cities</td><td>{@link String}</td><td>cities</td><td>text</td><td>有哪些城市在卖此商品，存储时前后以英文逗号结尾</td></tr>
 *   <tr><td>saleNum</td><td>{@link Integer}</td><td>saleNum</td><td>int</td><td>销量</td></tr>
 *   <tr><td>virtualSaleNum</td><td>{@link Integer}</td><td>virtualSaleNum</td><td>int</td><td>虚拟销量</td></tr>
 *   <tr><td>commentNum</td><td>{@link Integer}</td><td>commentNum</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>gradeAvg</td><td>{@link BigDecimal}</td><td>gradeAvg</td><td>decimal</td><td>&nbsp;</td></tr>
 *   <tr><td>heroitteCommentNum</td><td>{@link Integer}</td><td>heroitteCommentNum</td><td>int</td><td>黑石礁评论次数</td></tr>
 *   <tr><td>heroitteAvg</td><td>{@link BigDecimal}</td><td>heroitteAvg</td><td>decimal</td><td>黑石礁评论平均分数</td></tr>
 *   <tr><td>shippingMode</td><td>{@link String}</td><td>shippingMode</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>giftCardType</td><td>{@link Integer}</td><td>giftCardType</td><td>tinyint</td><td>礼品卡类型,1-电子礼品卡,2-增值卡</td></tr>
 *   <tr><td>giftCardAmount</td><td>{@link Integer}</td><td>giftCardAmount</td><td>int</td><td>礼品卡的面额</td></tr>
 *   <tr><td>isVirtual</td><td>{@link Integer}</td><td>isVirtual</td><td>tinyint</td><td>是否是虚拟商品,虚拟商品不在前台展示</td></tr>
 *   <tr><td>isNoLimitStockProduct</td><td>{@link Integer}</td><td>isNoLimitStockProduct</td><td>tinyint</td><td>是否是无限制库存量的商品</td></tr>
 *   <tr><td>sCode</td><td>{@link String}</td><td>sCode</td><td>varchar</td><td>商品所属库位编码,如果不为空,则表示该商品统一从该库发货</td></tr>
 *   <tr><td>limitCityIds</td><td>{@link String}</td><td>limitCityIds</td><td>text</td><td>商品所覆盖的城市</td></tr>
 *   <tr><td>isNotConfirm</td><td>{@link Integer}</td><td>isNotConfirm</td><td>tinyint</td><td>是否是无需确认商品</td></tr>
 *   <tr><td>packageId</td><td>{@link Integer}</td><td>packageId</td><td>int</td><td>套装ID，用于商城套装销售</td></tr>
 *   <tr><td>specailPrice</td><td>{@link BigDecimal}</td><td>specailPrice</td><td>decimal</td><td>特殊会员组价格</td></tr>
 *   <tr><td>multiStorage</td><td>{@link Integer}</td><td>multiStorage</td><td>tinyint</td><td>是否采用多级库位关系</td></tr>
 *   <tr><td>inspectType</td><td>{@link Integer}</td><td>inspectType</td><td>tinyint</td><td>商品退货检验方式</td></tr>
 *   <tr><td>limitedPrice</td><td>{@link BigDecimal}</td><td>limitedPrice</td><td>decimal</td><td>产品最低限价</td></tr>
 *   <tr><td>conTaxCode</td><td>{@link String}</td><td>conTaxCode</td><td>varchar</td><td>发票码</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-4-30
 * @email yudi@sina.com
 */
public class ProductsNew implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long   serialVersionUID = -6515640277706077330L;

    // 商品退货检验方式
    public static final Integer IT_DONOTINSPECT  = 3;
    public static final Integer IT_NORMAL        = 1;
    public static final Integer IT_APPOINT       = 2;

    Map<Integer, String>        inspectTypeMap   = new HashMap<Integer, String>();

    {
        inspectTypeMap.put(IT_DONOTINSPECT, "网点不质检");
        inspectTypeMap.put(IT_NORMAL, "网点上门质检");
        inspectTypeMap.put(IT_APPOINT, "生活家电指定网点质检");
    }

    private Integer             id;

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

    private String productName;

    /**
     * 获取 商品名称。
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * 设置 商品名称。
     *
     * @param value 属性值
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    private Integer stockToTaotao = 0;

    public Integer getStockToTaotao() {
        return this.stockToTaotao;
    }

    public void setStockToTaotao(Integer value) {
        this.stockToTaotao = value;
    }

    private Integer lastSyncTime;

    public Integer getLastSyncTime() {
        return this.lastSyncTime;
    }

    public void setLastSyncTime(Integer value) {
        this.lastSyncTime = value;
    }

    private String taobaoId;

    /**
     * 获取 对应的淘宝商品ID。
     */
    public String getTaobaoId() {
        return this.taobaoId;
    }

    /**
     * 设置 对应的淘宝商品ID。
     *
     * @param value 属性值
     */
    public void setTaobaoId(String value) {
        this.taobaoId = value;
    }

    private String wlbItemId;

    /**
     * 获取 对应的WLB商品ID。
     */
    public String getWlbItemId() {
        return this.wlbItemId;
    }

    /**
     * 设置 对应的WLB商品ID。
     *
     * @param value 属性值
     */
    public void setWlbItemId(String value) {
        this.wlbItemId = value;
    }

    private String productActivityInfo;

    /**
     * 获取 商品活动信息 用于商品详情页显示在商品名称后面。
     */
    public String getProductActivityInfo() {
        return this.productActivityInfo;
    }

    /**
     * 设置 商品活动信息 用于商品详情页显示在商品名称后面。
     *
     * @param value 属性值
     */
    public void setProductActivityInfo(String value) {
        this.productActivityInfo = value;
    }

    private String productTitle;

    /**
     * 获取 商品标题，商品在前台展示时显示在名称后面的描述信息。
     */
    public String getProductTitle() {
        return this.productTitle;
    }

    /**
     * 设置 商品标题，商品在前台展示时显示在名称后面的描述信息。
     *
     * @param value 属性值
     */
    public void setProductTitle(String value) {
        this.productTitle = value;
    }

    private String productTags;

    /**
     * 获取 存储格式（手机,电脑,gps）。
     */
    public String getProductTags() {
        return this.productTags;
    }

    /**
     * 设置 存储格式（手机,电脑,gps）。
     *
     * @param value 属性值
     */
    public void setProductTags(String value) {
        this.productTags = value;
    }

    private Integer brandId;

    /**
     * 获取 品牌id。
     */
    public Integer getBrandId() {
        return this.brandId;
    }

    /**
     * 设置 品牌id。
     *
     * @param value 属性值
     */
    public void setBrandId(Integer value) {
        this.brandId = value;
    }

    private String defaultImageFileId;

    /**
     * 获取 商品图片文件id。
     */
    public String getDefaultImageFileId() {
        return this.defaultImageFileId;
    }

    /**
     * 设置 商品图片文件id。
     *
     * @param value 属性值
     */
    public void setDefaultImageFileId(String value) {
        this.defaultImageFileId = value;
    }

    private String detailImageFileId;

    /**
     * 获取 商品细节图。
     */
    public String getDetailImageFileId() {
        return this.detailImageFileId;
    }

    /**
     * 设置 商品细节图。
     *
     * @param value 属性值
     */
    public void setDetailImageFileId(String value) {
        this.detailImageFileId = value;
    }

    private String sceneImageFileId;

    /**
     * 获取 商品场景图。
     */
    public String getSceneImageFileId() {
        return this.sceneImageFileId;
    }

    /**
     * 设置 商品场景图。
     *
     * @param value 属性值
     */
    public void setSceneImageFileId(String value) {
        this.sceneImageFileId = value;
    }

    private Integer productTypeId;

    /**
     * 获取 商品类型id。
     */
    public Integer getProductTypeId() {
        return this.productTypeId;
    }

    /**
     * 设置 商品类型id。
     *
     * @param value 属性值
     */
    public void setProductTypeId(Integer value) {
        this.productTypeId = value;
    }

    private String sellingPoint;

    /**
     * 获取 产品卖点，序列化数据。
     */
    public String getSellingPoint() {
        return this.sellingPoint;
    }

    /**
     * 设置 产品卖点，序列化数据。
     *
     * @param value 属性值
     */
    public void setSellingPoint(String value) {
        this.sellingPoint = value;
    }

    private String productDetail;

    /**
     * 获取 商品详细描述。
     */
    public String getProductDetail() {
        return this.productDetail;
    }

    /**
     * 设置 商品详细描述。
     *
     * @param value 属性值
     */
    public void setProductDetail(String value) {
        this.productDetail = value;
    }

    private String productBlockDetail;

    /**
     * 获取 带版式商品详情。
     */
    public String getProductBlockDetail() {
        return this.productBlockDetail;
    }

    /**
     * 设置 带版式商品详情。
     *
     * @param value 属性值
     */
    public void setProductBlockDetail(String value) {
        this.productBlockDetail = value;
    }

    private String productDetail2;

    /**
     * 获取 详情底部内容。
     */
    public String getProductDetail2() {
        return this.productDetail2;
    }

    /**
     * 设置 详情底部内容。
     *
     * @param value 属性值
     */
    public void setProductDetail2(String value) {
        this.productDetail2 = value;
    }

    private Integer addTime;

    /**
     * 获取 添加时间。
     */
    public Integer getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 添加时间。
     *
     * @param value 属性值
     */
    public void setAddTime(Integer value) {
        this.addTime = value;
    }

    private Integer lastModify;

    /**
     * 获取 最后更改时间。
     */
    public Integer getLastModify() {
        return this.lastModify;
    }

    /**
     * 设置 最后更改时间。
     *
     * @param value 属性值
     */
    public void setLastModify(Integer value) {
        this.lastModify = value;
    }

    private Integer productCateId;

    /**
     * 获取 商品类目id。
     */
    public Integer getProductCateId() {
        return this.productCateId;
    }

    /**
     * 设置 商品类目id。
     *
     * @param value 属性值
     */
    public void setProductCateId(Integer value) {
        this.productCateId = value;
    }

    private String productCatePath;

    /**
     * 获取 商品分类路径（存储格式：/1/2/3/），搜索时可以like用索引。
     */
    public String getProductCatePath() {
        return this.productCatePath;
    }

    /**
     * 设置 商品分类路径（存储格式：/1/2/3/），搜索时可以like用索引。
     *
     * @param value 属性值
     */
    public void setProductCatePath(String value) {
        this.productCatePath = value;
    }

    private Integer onSale;

    /**
     * 获取 1上架0下架。
     */
    public Integer getOnSale() {
        return this.onSale;
    }

    /**
     * 设置 1上架0下架。
     *
     * @param value 属性值
     */
    public void setOnSale(Integer value) {
        this.onSale = value;
    }

    private Integer isStar;

    /**
     * 获取 是否加星标 0 不加星标 1 加星标。
     */
    public Integer getIsStar() {
        return this.isStar;
    }

    /**
     * 设置 是否加星标 0 不加星标 1 加星标。
     *
     * @param value 属性值
     */
    public void setIsStar(Integer value) {
        this.isStar = value;
    }

    private Integer isHot;

    /**
     * 获取 热卖。
     */
    public Integer getIsHot() {
        return this.isHot;
    }

    /**
     * 设置 热卖。
     *
     * @param value 属性值
     */
    public void setIsHot(Integer value) {
        this.isHot = value;
    }

    private Integer isBest;

    /**
     * 获取 精品。
     */
    public Integer getIsBest() {
        return this.isBest;
    }

    /**
     * 设置 精品。
     *
     * @param value 属性值
     */
    public void setIsBest(Integer value) {
        this.isBest = value;
    }

    private Integer isNew;

    /**
     * 获取 新品。
     */
    public Integer getIsNew() {
        return this.isNew;
    }

    /**
     * 设置 新品。
     *
     * @param value 属性值
     */
    public void setIsNew(Integer value) {
        this.isNew = value;
    }

    private Integer isSpecial;

    /**
     * 获取 是否特殊商品 0普通商品 1特殊商品。
     */
    public Integer getIsSpecial() {
        return this.isSpecial;
    }

    /**
     * 设置 是否特殊商品 0普通商品 1特殊商品。
     *
     * @param value 属性值
     */
    public void setIsSpecial(Integer value) {
        this.isSpecial = value;
    }

    private Integer isDelete;

    /**
     * 获取 是否为删除状态,1-已删除,0-未删除。
     */
    public Integer getIsDelete() {
        return this.isDelete;
    }

    /**
     * 设置 是否为删除状态,1-已删除,0-未删除。
     *
     * @param value 属性值
     */
    public void setIsDelete(Integer value) {
        this.isDelete = value;
    }

    private Integer bookable;

    /**
     * 获取 可预订。
     */
    public Integer getBookable() {
        return this.bookable;
    }

    /**
     * 设置 可预订。
     *
     * @param value 属性值
     */
    public void setBookable(Integer value) {
        this.bookable = value;
    }

    private Integer bookDays;

    /**
     * 获取 预订到货天数。
     */
    public Integer getBookDays() {
        return this.bookDays;
    }

    /**
     * 设置 预订到货天数。
     *
     * @param value 属性值
     */
    public void setBookDays(Integer value) {
        this.bookDays = value;
    }

    private Integer isForbidArrivalNotice;

    /**
     * 获取 是否禁止到货通知。
     */
    public Integer getIsForbidArrivalNotice() {
        return this.isForbidArrivalNotice;
    }

    /**
     * 设置 是否禁止到货通知。
     *
     * @param value 属性值
     */
    public void setIsForbidArrivalNotice(Integer value) {
        this.isForbidArrivalNotice = value;
    }

    private BigDecimal supplyPrice;

    /**
     * 获取 商品供货价格。
     */
    public BigDecimal getSupplyPrice() {
        return this.supplyPrice;
    }

    /**
     * 设置 商品供货价格。
     *
     * @param value 属性值
     */
    public void setSupplyPrice(BigDecimal value) {
        this.supplyPrice = value;
    }

    private BigDecimal packagePrice;

    /**
     * 获取 套装价。
     */
    public BigDecimal getPackagePrice() {
        return this.packagePrice;
    }

    /**
     * 设置 套装价。
     *
     * @param value 属性值
     */
    public void setPackagePrice(BigDecimal value) {
        this.packagePrice = value;
    }

    private BigDecimal saleGuidePrice;

    /**
     * 获取 参考价格,销售指导价格。
     */
    public BigDecimal getSaleGuidePrice() {
        return this.saleGuidePrice;
    }

    /**
     * 设置 参考价格,销售指导价格。
     *
     * @param value 属性值
     */
    public void setSaleGuidePrice(BigDecimal value) {
        this.saleGuidePrice = value;
    }

    private BigDecimal energySubsidyAmount;

    /**
     * 获取 节能补贴金额。
     */
    public BigDecimal getEnergySubsidyAmount() {
        return this.energySubsidyAmount;
    }

    /**
     * 设置 节能补贴金额。
     *
     * @param value 属性值
     */
    public void setEnergySubsidyAmount(BigDecimal value) {
        this.energySubsidyAmount = value;
    }

    private String energySubsidyProductName;

    /**
     * 获取 节能补贴商品名称-会在发票上体现。
     */
    public String getEnergySubsidyProductName() {
        return this.energySubsidyProductName;
    }

    /**
     * 设置 节能补贴商品名称-会在发票上体现。
     *
     * @param value 属性值
     */
    public void setEnergySubsidyProductName(String value) {
        this.energySubsidyProductName = value;
    }

    private BigDecimal internalPrice;

    /**
     * 获取 内部员工价。
     */
    public BigDecimal getInternalPrice() {
        return this.internalPrice;
    }

    /**
     * 设置 内部员工价。
     *
     * @param value 属性值
     */
    public void setInternalPrice(BigDecimal value) {
        this.internalPrice = value;
    }

    private BigDecimal rankPrice;

    /**
     * 获取 会员组会员价。
     */
    public BigDecimal getRankPrice() {
        return this.rankPrice;
    }

    /**
     * 设置 会员组会员价。
     *
     * @param value 属性值
     */
    public void setRankPrice(BigDecimal value) {
        this.rankPrice = value;
    }

    private String rankGroups;

    /**
     * 获取 可用会员价的会员组。
     */
    public String getRankGroups() {
        return this.rankGroups;
    }

    /**
     * 设置 可用会员价的会员组。
     *
     * @param value 属性值
     */
    public void setRankGroups(String value) {
        this.rankGroups = value;
    }

    private String sku;

    /**
     * 获取 商家编码。
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 商家编码。
     *
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private Integer storageType;

    /**
     * 获取 物料编号所属库位类型，1-大库,2-小库。
     */
    public Integer getStorageType() {
        return this.storageType;
    }

    /**
     * 设置 物料编号所属库位类型，1-大库,2-小库。
     *
     * @param value 属性值
     */
    public void setStorageType(Integer value) {
        this.storageType = value;
    }

    private String memberRanks;

    /**
     * 获取 限定会员组。
     */
    public String getMemberRanks() {
        return this.memberRanks;
    }

    /**
     * 设置 限定会员组。
     *
     * @param value 属性值
     */
    public void setMemberRanks(String value) {
        this.memberRanks = value;
    }

    private String similarProductIds;

    /**
     * 获取 相似商品，逗号分隔，左右加逗号。
     */
    public String getSimilarProductIds() {
        return this.similarProductIds;
    }

    /**
     * 设置 相似商品，逗号分隔，左右加逗号。
     *
     * @param value 属性值
     */
    public void setSimilarProductIds(String value) {
        this.similarProductIds = value;
    }

    private String modifiedFields;

    /**
     * 获取 修改过的字段，序列化存储。
     */
    public String getModifiedFields() {
        return this.modifiedFields;
    }

    /**
     * 设置 修改过的字段，序列化存储。
     *
     * @param value 属性值
     */
    public void setModifiedFields(String value) {
        this.modifiedFields = value;
    }

    private String rejectReason;

    /**
     * 获取 拒绝原因。
     */
    public String getRejectReason() {
        return this.rejectReason;
    }

    /**
     * 设置 拒绝原因。
     *
     * @param value 属性值
     */
    public void setRejectReason(String value) {
        this.rejectReason = value;
    }

    private Integer state;

    /**
     * 获取 审核状态：0正常状态,1新记录,2修改过,3新记录被拒绝,4修改过被拒绝。
     */
    public Integer getState() {
        return this.state;
    }

    /**
     * 设置 审核状态：0正常状态,1新记录,2修改过,3新记录被拒绝,4修改过被拒绝。
     *
     * @param value 属性值
     */
    public void setState(Integer value) {
        this.state = value;
    }

    private String keywords;

    /**
     * 获取 页面关键字，用户搜索引擎优化。
     */
    public String getKeywords() {
        return this.keywords;
    }

    /**
     * 设置 页面关键字，用户搜索引擎优化。
     *
     * @param value 属性值
     */
    public void setKeywords(String value) {
        this.keywords = value;
    }

    private String description;

    /**
     * 获取 商品描述，用户搜索引擎优化。
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置 商品描述，用户搜索引擎优化。
     *
     * @param value 属性值
     */
    public void setDescription(String value) {
        this.description = value;
    }

    private String cities;

    /**
     * 获取 有哪些城市在卖此商品，存储时前后以英文逗号结尾。
     */
    public String getCities() {
        return this.cities;
    }

    /**
     * 设置 有哪些城市在卖此商品，存储时前后以英文逗号结尾。
     *
     * @param value 属性值
     */
    public void setCities(String value) {
        this.cities = value;
    }

    private Integer saleNum;

    /**
     * 获取 销量。
     */
    public Integer getSaleNum() {
        return this.saleNum;
    }

    /**
     * 设置 销量。
     *
     * @param value 属性值
     */
    public void setSaleNum(Integer value) {
        this.saleNum = value;
    }

    private Integer virtualSaleNum;

    /**
     * 获取 虚拟销量。
     */
    public Integer getVirtualSaleNum() {
        return this.virtualSaleNum;
    }

    /**
     * 设置 虚拟销量。
     *
     * @param value 属性值
     */
    public void setVirtualSaleNum(Integer value) {
        this.virtualSaleNum = value;
    }

    private Integer commentNum;

    public Integer getCommentNum() {
        return this.commentNum;
    }

    public void setCommentNum(Integer value) {
        this.commentNum = value;
    }

    private BigDecimal gradeAvg;

    public BigDecimal getGradeAvg() {
        return this.gradeAvg;
    }

    public void setGradeAvg(BigDecimal value) {
        this.gradeAvg = value;
    }

    private Integer heroitteCommentNum;

    /**
     * 获取 黑石礁评论次数。
     */
    public Integer getHeroitteCommentNum() {
        return this.heroitteCommentNum;
    }

    /**
     * 设置 黑石礁评论次数。
     *
     * @param value 属性值
     */
    public void setHeroitteCommentNum(Integer value) {
        this.heroitteCommentNum = value;
    }

    private BigDecimal heroitteAvg;

    /**
     * 获取 黑石礁评论平均分数。
     */
    public BigDecimal getHeroitteAvg() {
        return this.heroitteAvg;
    }

    /**
     * 设置 黑石礁评论平均分数。
     *
     * @param value 属性值
     */
    public void setHeroitteAvg(BigDecimal value) {
        this.heroitteAvg = value;
    }

    private String shippingMode;

    public String getShippingMode() {
        return this.shippingMode;
    }

    public void setShippingMode(String value) {
        this.shippingMode = value;
    }

    private Integer giftCardType;

    /**
     * 获取 礼品卡类型,1-电子礼品卡,2-增值卡。
     */
    public Integer getGiftCardType() {
        return this.giftCardType;
    }

    /**
     * 设置 礼品卡类型,1-电子礼品卡,2-增值卡。
     *
     * @param value 属性值
     */
    public void setGiftCardType(Integer value) {
        this.giftCardType = value;
    }

    private Integer giftCardAmount;

    /**
     * 获取 礼品卡的面额。
     */
    public Integer getGiftCardAmount() {
        return this.giftCardAmount;
    }

    /**
     * 设置 礼品卡的面额。
     *
     * @param value 属性值
     */
    public void setGiftCardAmount(Integer value) {
        this.giftCardAmount = value;
    }

    private Integer isVirtual;

    /**
     * 获取 是否是虚拟商品,虚拟商品不在前台展示。
     */
    public Integer getIsVirtual() {
        return this.isVirtual;
    }

    /**
     * 设置 是否是虚拟商品,虚拟商品不在前台展示。
     *
     * @param value 属性值
     */
    public void setIsVirtual(Integer value) {
        this.isVirtual = value;
    }

    private Integer isNoLimitStockProduct;

    /**
     * 获取 是否是无限制库存量的商品。
     */
    public Integer getIsNoLimitStockProduct() {
        return this.isNoLimitStockProduct;
    }

    /**
     * 设置 是否是无限制库存量的商品。
     *
     * @param value 属性值
     */
    public void setIsNoLimitStockProduct(Integer value) {
        this.isNoLimitStockProduct = value;
    }

    private String sCode;

    /**
     * 获取 商品所属库位编码,如果不为空,则表示该商品统一从该库发货。
     */
    public String getSCode() {
        return this.sCode;
    }

    /**
     * 设置 商品所属库位编码,如果不为空,则表示该商品统一从该库发货。
     *
     * @param value 属性值
     */
    public void setSCode(String value) {
        this.sCode = value;
    }

    private String limitCityIds;

    /**
     * 获取 商品所覆盖的城市。
     */
    public String getLimitCityIds() {
        return this.limitCityIds;
    }

    /**
     * 设置 商品所覆盖的城市。
     *
     * @param value 属性值
     */
    public void setLimitCityIds(String value) {
        this.limitCityIds = value;
    }

    private Integer isNotConfirm = 0;

    /**
     * 获取 是否是无需确认商品。
     */
    public Integer getIsNotConfirm() {
        return this.isNotConfirm;
    }

    /**
     * 设置 是否是无需确认商品。
     *
     * @param value 属性值
     */
    public void setIsNotConfirm(Integer value) {
        this.isNotConfirm = value;
    }

    private Integer packageId;

    /**
     * 获取 套装ID，用于商城套装销售。
     */
    public Integer getPackageId() {
        return this.packageId;
    }

    /**
     * 设置 套装ID，用于商城套装销售。
     *
     * @param value 属性值
     */
    public void setPackageId(Integer value) {
        this.packageId = value;
    }

    private BigDecimal specailPrice;

    /**
     * 获取 特殊会员组价格。
     */
    public BigDecimal getSpecailPrice() {
        return this.specailPrice;
    }

    /**
     * 设置 特殊会员组价格。
     *
     * @param value 属性值
     */
    public void setSpecailPrice(BigDecimal value) {
        this.specailPrice = value;
    }

    private Integer multiStorage = 0;

    /**
     * 获取 是否采用多级库位关系。
     */
    public Integer getMultiStorage() {
        return this.multiStorage;
    }

    /**
     * 设置 是否采用多级库位关系。
     *
     * @param value 属性值
     */
    public void setMultiStorage(Integer value) {
        this.multiStorage = value;
    }

    private Integer inspectType = 0;

    /**
     * 获取 商品退货检验方式。
     */
    public Integer getInspectType() {
        return this.inspectType;
    }

    /**
     * 设置 商品退货检验方式。
     *
     * @param value 属性值
     */
    public void setInspectType(Integer value) {
        this.inspectType = value;
    }

    private BigDecimal limitedPrice = BigDecimal.ZERO;

    /**
     * 获取 产品最低限价。
     */
    public BigDecimal getLimitedPrice() {
        return this.limitedPrice;
    }

    /**
     * 设置 产品最低限价。
     *
     * @param value 属性值
     */
    public void setLimitedPrice(BigDecimal value) {
        this.limitedPrice = value;
    }

    private Integer isGift;

    public Integer getIsGift() {
        return isGift;
    }

    public void setIsGift(Integer isGift) {
        this.isGift = isGift;
    }

    private String conTaxCode;

    /**
     * 获取 发票码
     */
    public String getConTaxCode() {
        return this.conTaxCode;
    }

    /**
     * 设置 发票码
     * @param conTaxCode
     */
    public void setConTaxCode(String conTaxCode) {
        this.conTaxCode = conTaxCode;
    }
}