package com.haier.invoice.model.einvoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * 开票结果
 *                       
 * @Filename: Reslut.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reslut")
public class Reslut implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3022020774796675993L;
    /**
     * 处理结果代码。各代码代表的意义
     * 1.处理成功；4.报文数据验证错误；5.报文编码格式错误； 
     * 6.证书不存在或已过期； 7.数字签名不正确；99.系统内部错误；
     */
    @XmlAttribute
    String code;
    /**
     * 处理结果消息
     */
    @XmlAttribute
    String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
