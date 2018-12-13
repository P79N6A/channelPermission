package com.haier.svc.api.controller.util.http;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

public class ItemBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public ItemBean() {
        // TODO Auto-generated constructor stub
    }

    private String sourceOrderSn;

    private String reason;

    private String description;

    private String sku;

    private String count;

    private Long   refundFee;
    
    private String source;

    @XmlElement(name = "sourceOrderSn")
    public String getSourceOrderSn() {
        return sourceOrderSn;
    }

    public void setSourceOrderSn(String sourceOrderSn) {
        this.sourceOrderSn = sourceOrderSn;
    }

    @XmlElement(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "sku")
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @XmlElement(name = "count")
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @XmlElement(name = "refundAmount")
    public Long getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Long refundFee) {
        this.refundFee = refundFee;
    }

    @XmlElement(name = "source")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}