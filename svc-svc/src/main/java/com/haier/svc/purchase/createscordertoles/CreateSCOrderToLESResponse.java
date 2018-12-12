
package com.haier.svc.purchase.createscordertoles;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Output" type="{http://www.example.org/CreateSCOrderToLES/}OutputType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "output"
})
@XmlRootElement(name = "CreateSCOrderToLESResponse")
public class CreateSCOrderToLESResponse {

    @XmlElement(name = "Output")
    protected List<OutputType> output;

    /**
     * Gets the value of the output property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the output property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOutput().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OutputType }
     * 
     * 
     */
    public List<OutputType> getOutput() {
        if (output == null) {
            output = new ArrayList<OutputType>();
        }
        return this.output;
    }
    private static final long serialVersionUID = 1L;

    private String FLAG;  //1：成功     其它：失败
    private String MESSAGE;  //失败是，会有对应的错误信息
    private String VBELN;  //成功时，会生成提单号
    private String BSTKD;  //网单号

    public String getVBELN() {
        return VBELN;
    }
    public void setVBELN(String vBELN) {
        VBELN = vBELN;
    }
    public String getBSTKD() {
        return BSTKD;
    }
    public void setBSTKD(String bSTKD) {
        BSTKD = bSTKD;
    }
    public String getFLAG() {
        return FLAG;
    }
    public void setFLAG(String fLAG) {
        FLAG = fLAG;
    }
    public String getMESSAGE() {
        return MESSAGE;
    }
    public void setMESSAGE(String mESSAGE) {
        MESSAGE = mESSAGE;
    }


}
