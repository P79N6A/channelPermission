
package com.haier.order.wsdl.mdm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Output"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="out" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="PageCount" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="CurrentPage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "output"
})
@XmlRootElement(name = "SelectInfoFromMDM_OPResponse")
public class SelectInfoFromMDMOPResponse {

    @XmlElement(name = "Output", required = true)
    protected Output output;

    /**
     * ��ȡoutput���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Output }
     *     
     */
    public Output getOutput() {
        return output;
    }

    /**
     * ����output���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Output }
     *     
     */
    public void setOutput(Output value) {
        this.output = value;
    }


    /**
     * <p>anonymous complex type�� Java �ࡣ
     * 
     * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="out" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="PageCount" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="CurrentPage" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "out",
        "pageCount",
        "currentPage"
    })
    public static class Output {

        @XmlElement(required = true)
        protected String out;
        @XmlElement(name = "PageCount", required = true)
        protected String pageCount;
        @XmlElement(name = "CurrentPage", required = true)
        protected String currentPage;

        /**
         * ��ȡout���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOut() {
            return out;
        }

        /**
         * ����out���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOut(String value) {
            this.out = value;
        }

        /**
         * ��ȡpageCount���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPageCount() {
            return pageCount;
        }

        /**
         * ����pageCount���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPageCount(String value) {
            this.pageCount = value;
        }

        /**
         * ��ȡcurrentPage���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCurrentPage() {
            return currentPage;
        }

        /**
         * ����currentPage���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCurrentPage(String value) {
            this.currentPage = value;
        }

    }

}
