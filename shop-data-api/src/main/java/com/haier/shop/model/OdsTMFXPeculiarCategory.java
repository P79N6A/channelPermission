package com.haier.shop.model;


import java.io.Serializable;
import java.util.Date;

/**
 * 母婴产品主数据维护表
 * ODS_TMFX_PECULIAR_CATEGORY
 * 
 *
 * @author
 */
public class OdsTMFXPeculiarCategory implements Serializable {
    private static final long serialVersionUID = -4243445333460605250L;
    /**
     * 主键
     * ODS_TMFX_PECULIAR_CATEGORY.ID
     */
    private String id;

    /**
     * sku
     * ODS_TMFX_PECULIAR_CATEGORY.SKU
     */
    private String sku;

    /**
     * 品类
     * ODS_TMFX_PECULIAR_CATEGORY.CATEGORY
     */
    private String category;

    /**
     * 创建人
     * ODS_TMFX_PECULIAR_CATEGORY.CREATE_BY
     */
    private String createBy;

    /**
     * 创建时间
     * ODS_TMFX_PECULIAR_CATEGORY.CREATE_TIME
     */
    private Date createTime;

    /**
     * 修改人
     * ODS_TMFX_PECULIAR_CATEGORY.UPDATE_BY
     */
    private String updateBy;

    /**
     * 修改时间
     * ODS_TMFX_PECULIAR_CATEGORY.UPDATE_TIME
     */
    private Date updateTime;

    /**
     * 状态标志 'Y' 'N'
     * ODS_TMFX_PECULIAR_CATEGORY.FLAG
     */
    private String flag;

    private Integer page;
    private Integer rows;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    /**
     * 主键<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.ID<br>
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * 主键<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.ID<br>
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * sku<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.SKU<br>
     * @return SKU
     */
    public String getSku() {
        return sku;
    }

    /**
     * sku<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.SKU<br>
     * @param sku
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * 品类<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.CATEGORY<br>
     * @return CATEGORY
     */
    public String getCategory() {
        return category;
    }

    /**
     * 品类<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.CATEGORY<br>
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 创建人<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.CREATE_BY<br>
     * @return CREATE_BY
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 创建人<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.CREATE_BY<br>
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 创建时间<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.CREATE_TIME<br>
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.CREATE_TIME<br>
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改人<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.UPDATE_BY<br>
     * @return UPDATE_BY
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 修改人<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.UPDATE_BY<br>
     * @param updateBy
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 修改时间<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.UPDATE_TIME<br>
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.UPDATE_TIME<br>
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 状态标志 'Y' 'N'<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.FLAG<br>
     * @return FLAG
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 状态标志 'Y' 'N'<br>
     * 对应数据库表字段：ODS_TMFX_PECULIAR_CATEGORY.FLAG<br>
     * @param flag
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }
}