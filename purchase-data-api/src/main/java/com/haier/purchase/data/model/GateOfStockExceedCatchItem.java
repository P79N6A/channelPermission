package com.haier.purchase.data.model;

import java.io.Serializable;

/**
 *
 * @author 刘骥飞
 * @date 2014-9-5
 * @email jifei.liu@dhc.com.cn
 */
public class GateOfStockExceedCatchItem implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -9014654995589692202L;
    private String            ed_channel_id;
    private String            materials_desc;
    private String            category_id;
    private String            brand_id;
    private String            storage_id;
    private Integer           age;

    /**
     * @return Returns the ed_channel_id
     */
    public String getEd_channel_id() {
        return ed_channel_id;
    }

    /**
     * @param ed_channel_id
     * The ed_channel_id to set.
     */
    public void setEd_channel_id(String ed_channel_id) {
        this.ed_channel_id = ed_channel_id;
    }

    /**
     * @return Returns the materials_desc
     */
    public String getMaterials_desc() {
        return materials_desc;
    }

    /**
     * @param materials_desc
     * The materials_desc to set.
     */
    public void setMaterials_desc(String materials_desc) {
        this.materials_desc = materials_desc;
    }

    /**
     * @return Returns the category_id
     */
    public String getCategory_id() {
        return category_id;
    }

    /**
     * @param category_id
     * The category_id to set.
     */
    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    /**
     * @return Returns the brand_id
     */
    public String getBrand_id() {
        return brand_id;
    }

    /**
     * @param brand_id
     * The brand_id to set.
     */
    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    /**
     * @return Returns the storage_id
     */
    public String getStorage_id() {
        return storage_id;
    }

    /**
     * @param storage_id
     * The storage_id to set.
     */
    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }

    /**
     * @return Returns the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     * The age to set.
     */
    public void setAge(Integer age) {
        this.age = age;
    }

}