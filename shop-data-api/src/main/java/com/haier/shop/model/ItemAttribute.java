//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.07 at 09:05:54 AM CST 
//

package com.haier.shop.model;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>Java class for itemAttribute complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="itemAttribute">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="activeFlag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="created" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="deleteFlag" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="lastUpd" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="parentValueLow" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parentValueSetId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valueId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valueMeaning" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valueMeaningEn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valueSetId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * @Filename: ItemAttribute.java
 * @Version: 1.0
 * @Author: maqiang 马强
 * @Email: mqianger@163.com
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "row", propOrder = { "valueId", "valueSetId", "description", "value",
                                    "valueMeaning", "valueMeaningEn", "parentValueSetId",
                                    "activeFlag", "created", "lastUpd", "deleteFlag",
                                    "parentValueLow", "start", "size" ,"page","rows"})
public class ItemAttribute implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    @XmlTransient
    private static final long serialVersionUID = -264584636750741280L;

    @XmlTransient
    protected String          cbsCategory      = "";
    @XmlElement(defaultValue = "0", name = "active_flag")
    protected Integer         activeFlag       = 0;
    @XmlElement(name = "created")
    protected Date            created          = null;
    @XmlElement(defaultValue = "0", name = "delete_flag")
    protected Integer         deleteFlag       = 0;
    @XmlElement(defaultValue = "", name = "description")
    protected String          description      = "";
    @XmlTransient
    protected Integer         id;
    @XmlElement(name = "last_upd")
    protected Date            lastUpd          = null;
    @XmlElement(defaultValue = "", name = "parent_value_low")
    protected String          parentValueLow   = "";
    @XmlElement(defaultValue = "", name = "parent_value_set_id")
    protected String          parentValueSetId = "";
    @XmlElement(defaultValue = "", name = "value")
    protected String          value            = "";
    @XmlElement(defaultValue = "", name = "value_id")
    protected String          valueId          = "";
    @XmlElement(defaultValue = "", name = "value_meaning")
    protected String          valueMeaning     = "";
    @XmlElement(defaultValue = "", name = "value_meaning_en")
    protected String          valueMeaningEn   = "";
    @XmlElement(defaultValue = "", name = "value_set_id")
    protected String          valueSetId       = "";

    /**
     * Gets the value of the activeFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getActiveFlag() {
        return activeFlag;
    }

    /**
     * Sets the value of the activeFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setActiveFlag(Integer value) {
        this.activeFlag = value;
    }

    /**
     * Gets the value of the created property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Sets the value of the created property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreated(Date value) {
        this.created = value;
    }

    /**
     * Gets the value of the deleteFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Sets the value of the deleteFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDeleteFlag(Integer value) {
        this.deleteFlag = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the lastUpd property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Date getLastUpd() {
        return lastUpd;
    }

    /**
     * Sets the value of the lastUpd property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastUpd(Date value) {
        this.lastUpd = value;
    }

    /**
     * Gets the value of the parentValueLow property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentValueLow() {
        return parentValueLow;
    }

    /**
     * Sets the value of the parentValueLow property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentValueLow(String value) {
        this.parentValueLow = value;
    }

    /**
     * Gets the value of the parentValueSetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentValueSetId() {
        return parentValueSetId;
    }

    /**
     * Sets the value of the parentValueSetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentValueSetId(String value) {
        this.parentValueSetId = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the valueId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueId() {
        return valueId;
    }

    /**
     * Sets the value of the valueId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueId(String value) {
        this.valueId = value;
    }

    /**
     * Gets the value of the valueMeaning property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueMeaning() {
        return valueMeaning;
    }

    /**
     * Sets the value of the valueMeaning property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueMeaning(String value) {
        this.valueMeaning = value;
    }

    /**
     * Gets the value of the valueMeaningEn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueMeaningEn() {
        return valueMeaningEn;
    }

    /**
     * Sets the value of the valueMeaningEn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueMeaningEn(String value) {
        this.valueMeaningEn = value;
    }

    /**
     * Gets the value of the valueSetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueSetId() {
        return valueSetId;
    }

    /**
     * Sets the value of the valueSetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueSetId(String value) {
        this.valueSetId = value;
    }

    public String getCbsCategory() {
        return cbsCategory;
    }

    public void setCbsCategory(String cbsCategory) {
        this.cbsCategory = cbsCategory;
    }
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
     * 开始页
     */
    private int start;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    /**
     * 每页大小
     */
    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
