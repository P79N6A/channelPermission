package com.haier.shop.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 鐗╂枡闆嗗悎
 * <p>Table: <strong>item_base</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>灞炴�у悕</th><th nowrap>灞炴�х被鍨�</th><th nowrap>瀛楁鍚�</th><th nowrap>瀛楁绫诲瀷</th><th nowrap>璇存槑</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>鐗╂枡闆嗗悎ID</td></tr>
 *   <tr><td>rowId</td><td>{@link String}</td><td>row_id</td><td>varchar</td><td>row_id</td></tr>
 *   <tr><td>materialCode</td><td>{@link String}</td><td>material_code</td><td>varchar</td><td>MATERIAL_CODE</td></tr>
 *   <tr><td>materialDescription</td><td>{@link String}</td><td>material_description</td><td>varchar</td><td>MATERIAL_DESCRITION</td></tr>
 *   <tr><td>materialType</td><td>{@link String}</td><td>material_type</td><td>varchar</td><td>MATERIAL_TYPE</td></tr>
 *   <tr><td>industry</td><td>{@link String}</td><td>industry</td><td>varchar</td><td>INDUSTRY</td></tr>
 *   <tr><td>mtlGroupCode</td><td>{@link String}</td><td>mtl_group_code</td><td>varchar</td><td>MTL_GROUP_CODE</td></tr>
 *   <tr><td>primaryUom</td><td>{@link String}</td><td>primary_uom</td><td>varchar</td><td>PRIMARY_UOM</td></tr>
 *   <tr><td>department</td><td>{@link String}</td><td>department</td><td>varchar</td><td>DEPARTMENT</td></tr>
 *   <tr><td>deleteFlag</td><td>{@link Integer}</td><td>delete_flag</td><td>int</td><td>DELETE_FLAG</td></tr>
 *   <tr><td>created</td><td>{@link java.util.Date}</td><td>created</td><td>datetime</td><td>CREATED</td></tr>
 *   <tr><td>lastUpd</td><td>{@link java.util.Date}</td><td>last_upd</td><td>datetime</td><td>LAST_UPD</td></tr>
 *   <tr><td>kindOne</td><td>{@link String}</td><td>kind_one</td><td>varchar</td><td>KIND_ONE</td></tr>
 *   <tr><td>kindAdd</td><td>{@link String}</td><td>kind_add</td><td>varchar</td><td>KIND_ADD</td></tr>
 *   <tr><td>proType</td><td>{@link String}</td><td>pro_type</td><td>varchar</td><td>PRO_TYPE</td></tr>
 *   <tr><td>typeDisc</td><td>{@link String}</td><td>type_disc</td><td>varchar</td><td>TYPE_DISC</td></tr>
 *   <tr><td>isViliageProduct</td><td>{@link Integer}</td><td>is_viliage_product</td><td>int</td><td>IS_VILLAGE_PRODUCT</td></tr>
 *   <tr><td>markFlag</td><td>{@link Integer}</td><td>mark_flag</td><td>int</td><td>MARK_FLAG</td></tr>
 *   <tr><td>proBand</td><td>{@link String}</td><td>pro_band</td><td>varchar</td><td>PRO_BAND</td></tr>
 *   <tr><td>lengthNumber</td><td>{@link BigDecimal}</td><td>length_number</td><td>decimal</td><td>LENGTH_NUMBER</td></tr>
 *   <tr><td>widthNumber</td><td>{@link BigDecimal}</td><td>width_number</td><td>decimal</td><td>WIDTH_NUMBER</td></tr>
 *   <tr><td>highNumber</td><td>{@link BigDecimal}</td><td>high_number</td><td>decimal</td><td>HIGH_NUMBER</td></tr>
 *   <tr><td>storeLayerNumber</td><td>{@link BigDecimal}</td><td>store_layer_number</td><td>decimal</td><td>STORE_LAYER_NUMBER</td></tr>
 *   <tr><td>grossWeightNumber</td><td>{@link BigDecimal}</td><td>gross_weight_number</td><td>decimal</td><td>GROSS_WEIGHT_NUMBER</td></tr>
 *   <tr><td>weightUnit</td><td>{@link String}</td><td>weight_unit</td><td>varchar</td><td>WEIGHT_UNIT</td></tr>
 *   <tr><td>isCrustFlag</td><td>{@link String}</td><td>is_crust_flag</td><td>char</td><td>IS_CRUST_FLAG</td></tr>
 *   <tr><td>rebateRate</td><td>{@link BigDecimal}</td><td>rebate_rate</td><td>decimal</td><td>REBATE_RATE</td></tr>
 *   <tr><td>saleChar</td><td>{@link String}</td><td>sale_char</td><td>char</td><td>SALE_CHAR</td></tr>
 *   <tr><td>createdBy</td><td>{@link String}</td><td>created_by</td><td>varchar</td><td>CREATED_BY</td></tr>
 *   <tr><td>productList</td><td>{@link String}</td><td>product_list</td><td>varchar</td><td>PRODUCT_LIST</td></tr>
 * </table>
 *
 * 鐗╂枡鍩烘湰淇℃伅
 * @Filename: ItemBase.java
 * @Version: 1.0
 * @Author: maqiang 椹己
 * @Email: mqianger@163.com
 *
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "row", propOrder = { "rowId", "materialCode", "materialDescription",
                                    "materialType", "industry", "mtlGroupCode", "primaryUom",
                                    "department", "deleteFlag", "created", "lastUpd", "kindOne",
                                    "kindAdd", "proType", "typeDisc", "isViliageProduct",
                                    "markFlag", "proBand", "lengthNumber", "widthNumber",
                                    "highNumber", "storeLayerNumber", "grossWeightNumber",
                                    "weightUnit", "isCrustFlag", "rebateRate", "saleChar",
                                    "createdBy", "productList", "status", "isAutoUpdate",
                                    "productType", "modifier", "departments", "start", "size",
                                    "cbsCategory", "departmentName","price" })
public class ItemBase implements Serializable {
    public void setNumberNull() {
        this.setIsViliageProduct(null);
        this.setMarkFlag(null);
        this.setLengthNumber(null);
        this.setWidthNumber(null);
        this.setHighNumber(null);
        this.setStoreLayerNumber(null);
        this.setGrossWeightNumber(null);
        this.setRebateRate(null);
    }

    /**
     *Comment for <code>serialVersionUID</code>
     */
    @XmlTransient
    private static final long serialVersionUID = -264584636750741280L;

    @XmlTransient
    private Integer id;

    /**
     * 鑾峰彇鐗╂枡闆嗗悎ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 璁剧疆鐗╂枡闆嗗悎ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(defaultValue = "", name = "row_id")
    private String rowId = "";

    /**
     * 鑾峰彇row_id
     */
    public String getRowId() {
        return this.rowId;
    }

    /**
     * 璁剧疆row_id
     */
    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    @XmlElement(defaultValue = "", name = "material_code")
    private String materialCode = "";

    /**
     * 鑾峰彇MATERIAL_CODE
     */
    public String getMaterialCode() {
        return this.materialCode;
    }

    /**
     * 璁剧疆MATERIAL_CODE
     */
    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    @XmlElement(defaultValue = "", name = "material_descrition")
    private String materialDescription = "";

    /**
     * 鑾峰彇MATERIAL_DESCRITION
     */
    public String getMaterialDescription() {
        return this.materialDescription;
    }

    /**
     * 璁剧疆MATERIAL_DESCRITION
     */
    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    @XmlElement(defaultValue = "", name = "material_type")
    private String materialType = "";

    /**
     * 鑾峰彇MATERIAL_TYPE
     */
    public String getMaterialType() {
        return this.materialType;
    }

    /**
     * 璁剧疆MATERIAL_TYPE
     */
    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    @XmlElement(defaultValue = "", name = "industry")
    private String industry = "";

    /**
     * 鑾峰彇INDUSTRY
     */
    public String getIndustry() {
        return this.industry;
    }

    /**
     * 璁剧疆INDUSTRY
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @XmlElement(defaultValue = "", name = "mtl_group_code")
    private String mtlGroupCode = "";

    /**
     * 鑾峰彇MTL_GROUP_CODE
     */
    public String getMtlGroupCode() {
        return this.mtlGroupCode;
    }

    /**
     * 璁剧疆MTL_GROUP_CODE
     */
    public void setMtlGroupCode(String mtlGroupCode) {
        this.mtlGroupCode = mtlGroupCode;
    }

    @XmlElement(defaultValue = "", name = "primary_uom")
    private String primaryUom = "";

    /**
     * 鑾峰彇PRIMARY_UOM
     */
    public String getPrimaryUom() {
        return this.primaryUom;
    }

    /**
     * 璁剧疆PRIMARY_UOM
     */
    public void setPrimaryUom(String primaryUom) {
        this.primaryUom = primaryUom;
    }

    @XmlElement(defaultValue = "", name = "department")
    private String department = "";

    /**
     * 鑾峰彇DEPARTMENT
     */
    public String getDepartment() {
        return this.department;
    }

    /**
     * 璁剧疆DEPARTMENT
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    @XmlElement(defaultValue = "0", name = "delete_flag")
    private Integer deleteFlag = 0;

    /**
     * 鑾峰彇DELETE_FLAG
     */
    public Integer getDeleteFlag() {
        return this.deleteFlag;
    }

    /**
     * 璁剧疆DELETE_FLAG
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @XmlElement(name = "created")
    private java.util.Date created = null;

    /**
     * 鑾峰彇CREATED
     */
    public java.util.Date getCreated() {
        return this.created;
    }

    /**
     * 璁剧疆CREATED
     */
    public void setCreated(java.util.Date created) {
        this.created = created;
    }

    @XmlElement(name = "last_upd")
    private java.util.Date lastUpd = null;

    /**
     * 鑾峰彇LAST_UPD
     */
    public java.util.Date getLastUpd() {
        return this.lastUpd;
    }

    /**
     * 璁剧疆LAST_UPD
     */
    public void setLastUpd(java.util.Date lastUpd) {
        this.lastUpd = lastUpd;
    }

    @XmlElement(defaultValue = "", name = "kind_one")
    private String kindOne = "";

    /**
     * 鑾峰彇KIND_ONE
     */
    public String getKindOne() {
        return this.kindOne;
    }

    /**
     * 璁剧疆KIND_ONE
     */
    public void setKindOne(String kindOne) {
        this.kindOne = kindOne;
    }

    @XmlElement(defaultValue = "", name = "kind_add")
    private String kindAdd = "";

    /**
     * 鑾峰彇KIND_ADD
     */
    public String getKindAdd() {
        return this.kindAdd;
    }

    /**
     * 璁剧疆KIND_ADD
     */
    public void setKindAdd(String kindAdd) {
        this.kindAdd = kindAdd;
    }

    @XmlElement(defaultValue = "", name = "pro_type")
    private String proType = "";

    /**
     * 鑾峰彇PRO_TYPE
     */
    public String getProType() {
        return this.proType;
    }

    /**
     * 璁剧疆PRO_TYPE
     */
    public void setProType(String proType) {
        this.proType = proType;
    }

    @XmlElement(defaultValue = "", name = "type_disc")
    private String typeDisc = "";

    /**
     * 鑾峰彇TYPE_DISC
     */
    public String getTypeDisc() {
        return this.typeDisc;
    }

    /**
     * 璁剧疆TYPE_DISC
     */
    public void setTypeDisc(String typeDisc) {
        this.typeDisc = typeDisc;
    }

    @XmlElement(defaultValue = "0", name = "is_viliage_product")
    private Integer isViliageProduct = 0;

    /**
     * 鑾峰彇IS_VILLAGE_PRODUCT
     */
    public Integer getIsViliageProduct() {
        return this.isViliageProduct;
    }

    /**
     * 璁剧疆IS_VILLAGE_PRODUCT
     */
    public void setIsViliageProduct(Integer isViliageProduct) {
        this.isViliageProduct = isViliageProduct;
    }

    @XmlElement(defaultValue = "0", name = "mark_flag")
    private Integer markFlag = 0;

    /**
     * 鑾峰彇MARK_FLAG
     */
    public Integer getMarkFlag() {
        return this.markFlag;
    }

    /**
     * 璁剧疆MARK_FLAG
     */
    public void setMarkFlag(Integer markFlag) {
        this.markFlag = markFlag;
    }

    @XmlElement(defaultValue = "", name = "pro_band")
    private String proBand = "";

    /**
     * 鑾峰彇PRO_BAND
     */
    public String getProBand() {
        return this.proBand;
    }

    /**
     * 璁剧疆PRO_BAND
     */
    public void setProBand(String proBand) {
        this.proBand = proBand;
    }

    @XmlElement(name = "length_number")
    private BigDecimal lengthNumber = BigDecimal.ZERO;

    /**
     * 鑾峰彇LENGTH_NUMBER
     */
    public BigDecimal getLengthNumber() {
        return this.lengthNumber;
    }

    /**
     * 璁剧疆LENGTH_NUMBER
     */
    public void setLengthNumber(BigDecimal lengthNumber) {
        this.lengthNumber = lengthNumber;
    }

    @XmlElement(name = "width_number")
    private BigDecimal widthNumber = BigDecimal.ZERO;

    /**
     * 鑾峰彇WIDTH_NUMBER
     */
    public BigDecimal getWidthNumber() {
        return this.widthNumber;
    }

    /**
     * 璁剧疆WIDTH_NUMBER
     */
    public void setWidthNumber(BigDecimal widthNumber) {
        this.widthNumber = widthNumber;
    }

    @XmlElement(name = "high_number")
    private BigDecimal highNumber = BigDecimal.ZERO;

    /**
     * 鑾峰彇HIGH_NUMBER
     */
    public BigDecimal getHighNumber() {
        return this.highNumber;
    }

    /**
     * 璁剧疆HIGH_NUMBER
     */
    public void setHighNumber(BigDecimal highNumber) {
        this.highNumber = highNumber;
    }

    @XmlElement(name = "store_layer_number")
    private BigDecimal storeLayerNumber = BigDecimal.ZERO;

    /**
     * 鑾峰彇STORE_LAYER_NUMBER
     */
    public BigDecimal getStoreLayerNumber() {
        return this.storeLayerNumber;
    }

    /**
     * 璁剧疆STORE_LAYER_NUMBER
     */
    public void setStoreLayerNumber(BigDecimal storeLayerNumber) {
        this.storeLayerNumber = storeLayerNumber;
    }

    @XmlElement(name = "gross_weight_number")
    private BigDecimal grossWeightNumber = BigDecimal.ZERO;

    /**
     * 鑾峰彇GROSS_WEIGHT_NUMBER
     */
    public BigDecimal getGrossWeightNumber() {
        return this.grossWeightNumber;
    }

    /**
     * 璁剧疆GROSS_WEIGHT_NUMBER
     */
    public void setGrossWeightNumber(BigDecimal grossWeightNumber) {
        this.grossWeightNumber = grossWeightNumber;
    }

    @XmlElement(name = "weight_unit", defaultValue = "")
    private String weightUnit = "";

    /**
     * 鑾峰彇WEIGHT_UNIT
     */
    public String getWeightUnit() {
        return this.weightUnit;
    }

    /**
     * 璁剧疆WEIGHT_UNIT
     */
    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    @XmlElement(name = "is_crust_flag", defaultValue = "")
    private String isCrustFlag = "";

    /**
     * 鑾峰彇IS_CRUST_FLAG
     */
    public String getIsCrustFlag() {
        return this.isCrustFlag;
    }

    /**
     * 璁剧疆IS_CRUST_FLAG
     */
    public void setIsCrustFlag(String isCrustFlag) {
        this.isCrustFlag = isCrustFlag;
    }

    @XmlElement(name = "rebate_rate")
    private BigDecimal rebateRate = BigDecimal.ZERO;

    /**
     * 鑾峰彇REBATE_RATE
     */
    public BigDecimal getRebateRate() {
        return this.rebateRate;
    }

    /**
     * 璁剧疆REBATE_RATE
     */
    public void setRebateRate(BigDecimal rebateRate) {
        this.rebateRate = rebateRate;
    }

    @XmlElement(name = "sale_char", defaultValue = "")
    private String saleChar = "";

    /**
     * 鑾峰彇SALE_CHAR
     */
    public String getSaleChar() {
        return this.saleChar;
    }

    /**
     * 璁剧疆SALE_CHAR
     */
    public void setSaleChar(String saleChar) {
        this.saleChar = saleChar;
    }

    @XmlElement(name = "created_by", defaultValue = "")
    private String createdBy = "";

    /**
     * 鑾峰彇CREATED_BY
     */
    public String getCreatedBy() {
        return this.createdBy;
    }

    /**
     * 璁剧疆CREATED_BY
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @XmlElement(name = "product_list", defaultValue = "")
    private String productList = "";

    /**
     * 鑾峰彇PRODUCT_LIST
     */
    public String getProductList() {
        return this.productList;
    }

    /**
     * 璁剧疆PRODUCT_LIST
     */
    public void setProductList(String productList) {
        this.productList = productList;
    }

    private Integer status;

    /**
     * 鑾峰彇鐘舵��
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 璁剧疆鐘舵��
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer isAutoUpdate;

    /**
     * 鑾峰彇鏄惁鑷姩鏇存柊
     */
    public Integer getIsAutoUpdate() {
        return this.isAutoUpdate;
    }

    /**
     * 璁剧疆鏄惁鑷姩鏇存柊
     */
    public void setIsAutoUpdate(Integer isAutoUpdate) {
        this.isAutoUpdate = isAutoUpdate;
    }

    @XmlElement(name = "product_type")
    private String productType;

    /**
     * 鑾峰彇绫诲瀷
     */
    public String getProductType() {
        return this.productType;
    }

    /**
     * 璁剧疆绫诲瀷
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    private String modifier;

    /**
     * 鑾峰彇鏈�鍚庝慨鏀逛汉
     */
    public String getModifier() {
        return this.modifier;
    }

    /**
     * 璁剧疆鏈�鍚庝慨鏀逛汉
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    private String[] departments;

    /**
     * 鑾峰彇浜у搧缁勬暟缁�
     */
    public String[] getDepartments() {
        return this.departments;
    }

    /**
     * 璁剧疆浜у搧缁勬暟缁�
     */
    public void setDepartments(String[] departments) {
        this.departments = departments;
    }

    /**
     * 寮�濮嬮〉
     */
    private int start;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    /**
     * 姣忛〉澶у皬
     */
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * CBS鍝佺被
     */
    private String cbsCategory;

    public String getCbsCategory() {
        return cbsCategory;
    }

    public void setCbsCategory(String cbsCategory) {
        this.cbsCategory = cbsCategory;
    }

    /**
     * 浜у搧缁勫悕
     */
    private String departmentName;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    

}