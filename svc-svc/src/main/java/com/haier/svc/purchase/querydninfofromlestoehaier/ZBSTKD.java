package com.haier.svc.purchase.querydninfofromlestoehaier;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 *
 * <pre>
 * &lt;complexType name="ZBSTKD">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BSTKD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZBSTKD", propOrder = { "bstkd" })
public class ZBSTKD {

	@XmlElement(name = "BSTKD", required = true)
	protected String bstkd;

	/**
	 *
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBSTKD() {
		return bstkd;
	}

	/**
	 *
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setBSTKD(String value) {
		this.bstkd = value;
	}

}
