package com.haier.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.haier.stock.model.InvStockAge.StockAgeData;

public class HaierStockExceedCacheVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4270709984572702715L;
	private String             materials_id;
    private String             ed_channel_id;
    private String             materials_desc;
    private String             category_id;
    private String             brand_id;
    private String             storage_id;
    private int                age;
    private List<StockAgeData> l_stockAgeData;

    private BigDecimal         valueTotal;

    private String             ageTotal;

    private List<AgeItem>      ageItems;

    public static class AgeItem implements Serializable {
        private String age_data;

        public String getAge_data() {
            return age_data;
        }

        public void setAge_data(String age_data) {
            this.age_data = age_data;
        }

    }

    public List<AgeItem> getAgeItems() {
        return ageItems;
    }

    public void setAgeItems(List<AgeItem> ageItems) {
        this.ageItems = ageItems;
    }

    public String getMaterials_id() {
        return materials_id;
    }

    public void setMaterials_id(String materials_id) {
        this.materials_id = materials_id;
    }

    public String getEd_channel_id() {
        return ed_channel_id;
    }

    public void setEd_channel_id(String ed_channel_id) {
        this.ed_channel_id = ed_channel_id;
    }

    public String getMaterials_desc() {
        return materials_desc;
    }

    public void setMaterials_desc(String materials_desc) {
        this.materials_desc = materials_desc;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getValueTotal() {
        return valueTotal;
    }

    public void setValueTotal(BigDecimal valueTotal) {
        this.valueTotal = valueTotal;
    }

    public List<StockAgeData> getL_stockAgeData() {
        return l_stockAgeData;
    }

    public void setL_stockAgeData(List<StockAgeData> l_stockAgeData) {
        this.l_stockAgeData = l_stockAgeData;
    }

    public String getAgeTotal() {
        return ageTotal;
    }

    public void setAgeTotal(String ageTotal) {
        this.ageTotal = ageTotal;
    }

}
