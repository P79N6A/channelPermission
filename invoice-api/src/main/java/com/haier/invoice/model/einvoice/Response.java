package com.haier.invoice.model.einvoice;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * 电子发票响应
 *                       
 * @Filename: Response.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
@XmlRootElement(name = "response")
@XmlType(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class Response implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2952722829259631593L;
    /**
     * 响应头部信息
     */
    @XmlElement(name = "header", required = true)
    Header                    header;
    /**
     * 开票结果
     */
    @XmlElement(name = "result", required = true)
    Reslut                    result;
    /**
     * 发票
     */
    @XmlElement(name = "invoice", required = true)
    Invoice                   invoice;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Reslut getResult() {
        return result;
    }

    public void setResult(Reslut result) {
        this.result = result;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
