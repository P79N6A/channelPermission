
package com.haier.traderate.services.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>baseVo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="baseVo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="totalNums" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseVo", propOrder = {
    "totalNums"
})
@XmlSeeAlso({
    EhaierOrder.class,
    QygOrder.class,
    SqmOrder.class,
    DemoVo.class,
    RrsOrder.class
})
public abstract class BaseVo {

    protected int totalNums;

    /**
     * 获取totalNums属性的值。
     * 
     */
    public int getTotalNums() {
        return totalNums;
    }

    /**
     * 设置totalNums属性的值。
     * 
     */
    public void setTotalNums(int value) {
        this.totalNums = value;
    }

}
