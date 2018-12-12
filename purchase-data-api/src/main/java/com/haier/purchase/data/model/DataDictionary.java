package com.haier.purchase.data.model;



import java.io.Serializable;

/**
 * <p>Table: <strong>data_dictionary</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link String}</td><td>id</td><td>int</td><td>自增ID</td></tr>
 *   <tr><td>value_set_id</td><td>{@link String}</td><td>value_set_id</td><td>varchar</td><td>数据分类</td></tr>
 *   <tr><td>value</td><td>{@link String}</td><td>value</td><td>varchar</td><td>数据code</td></tr>
 *   <tr><td>value_meaning</td><td>{@link String}</td><td>value_meaning</td><td>varchar</td><td>数据名称</td></tr>
 *   <tr><td>delete_flag</td><td>{@link String}</td><td>delete_flag</td><td>smallint</td><td>删除标记</td></tr>
 * </table>
 *
 * @author 刘骥飞
 * @date 2014-7-28
 * @email jifei.liu@dhc.com.cn
 */
public class DataDictionary implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7101753819719613702L;

    private String            id;
    private String            value_set_id;
    private String            value;
    private String            value_meaning;
    private String            delete_flag;

    /**
     * @return Returns the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     * The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the value_set_id
     */
    public String getValue_set_id() {
        return value_set_id;
    }

    /**
     * @param value_set_id
     * The value_set_id to set.
     */
    public void setValue_set_id(String value_set_id) {
        this.value_set_id = value_set_id;
    }

    /**
     * @return Returns the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     * The value to set.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return Returns the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return Returns the value_meaning
     */
    public String getValue_meaning() {
        return value_meaning;
    }

    /**
     * @param value_meaning
     * The value_meaning to set.
     */
    public void setValue_meaning(String value_meaning) {
        this.value_meaning = value_meaning;
    }

    /**
     * @return Returns the delete_flag
     */
    public String getDelete_flag() {
        return delete_flag;
    }

    /**
     * @param delete_flag
     * The delete_flag to set.
     */
    public void setDelete_flag(String delete_flag) {
        this.delete_flag = delete_flag;
    }

}