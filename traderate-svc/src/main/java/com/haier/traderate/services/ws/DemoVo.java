
package com.haier.traderate.services.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>demoVo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="demoVo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://demo.server.webservice.cc.neusoft.com/}baseVo"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="invokeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="seq" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="val1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="val2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="wsName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "demoVo", propOrder = {
    "invokeDate",
    "seq",
    "val1",
    "val2",
    "wsName"
})
public class DemoVo
    extends BaseVo
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar invokeDate;
    protected long seq;
    protected String val1;
    protected String val2;
    protected String wsName;

    /**
     * 获取invokeDate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInvokeDate() {
        return invokeDate;
    }

    /**
     * 设置invokeDate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInvokeDate(XMLGregorianCalendar value) {
        this.invokeDate = value;
    }

    /**
     * 获取seq属性的值。
     * 
     */
    public long getSeq() {
        return seq;
    }

    /**
     * 设置seq属性的值。
     * 
     */
    public void setSeq(long value) {
        this.seq = value;
    }

    /**
     * 获取val1属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVal1() {
        return val1;
    }

    /**
     * 设置val1属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVal1(String value) {
        this.val1 = value;
    }

    /**
     * 获取val2属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVal2() {
        return val2;
    }

    /**
     * 设置val2属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVal2(String value) {
        this.val2 = value;
    }

    /**
     * 获取wsName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWsName() {
        return wsName;
    }

    /**
     * 设置wsName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWsName(String value) {
        this.wsName = value;
    }

}
