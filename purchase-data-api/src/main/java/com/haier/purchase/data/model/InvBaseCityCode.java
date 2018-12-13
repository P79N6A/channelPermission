package com.haier.purchase.data.model;

import java.io.Serializable;

public class InvBaseCityCode implements Serializable {
    private static final long serialVersionUID = -6139614580240830198L;
    /**
     * 
     */
    private String            city_code;                              //城市代码
    private String            storage_id;                             //正品库位
    private String            city_desc;                              //城市描述
    private String            ehaier_city_code;                       //ehaier城市代码对照
    private Integer           is_enabled;                             //标识位（0启用，1禁用，4删除）

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }

    public String getCity_desc() {
        return city_desc;
    }

    public void setCity_desc(String city_desc) {
        this.city_desc = city_desc;
    }

    public String getEhaier_city_code() {
        return ehaier_city_code;
    }

    public void setEhaier_city_code(String ehaier_city_code) {
        this.ehaier_city_code = ehaier_city_code;
    }

    public Integer getIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(Integer is_enabled) {
        this.is_enabled = is_enabled;
    }

    private String is_enabled_name;

    public String getIs_enabled_name() {
        return is_enabled_name;
    }

    public void setIs_enabled_name(String is_enabled_name) {
        this.is_enabled_name = is_enabled_name;
    }

    @Override
    public String toString() {
        return "[city_code=" + city_code + ", storage_id=" + storage_id
               + ", city_desc=" + city_desc + ", ehaier_city_code=" + ehaier_city_code + "]";
    }
}
