package com.haier.afterSale.webService.emptyOutSAP;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2018-03-16T14:42:32.128+08:00
 * Generated source version: 3.1.12
 * 
 */
@WebService(targetNamespace = "http://www.example.org/TransBadGoodsInfoFromGVSToEHAIER/", name = "TransBadGoodsInfoFromGVSToEHAIER")
@XmlSeeAlso({ObjectFactory.class})
public interface TransBadGoodsInfoFromGVSToEHAIER {

    @WebMethod(operationName = "TransBadGoodsInfoFromGVSToEHAIER", action = "http://www.example.org/TransBadGoodsInfoFromGVSToEHAIER/TransBadGoodsInfoFromGVSToEHAIER")
    @RequestWrapper(localName = "TransBadGoodsInfoFromGVSToEHAIER", targetNamespace = "http://www.example.org/TransBadGoodsInfoFromGVSToEHAIER/", className = "com.haier.afterSale.webService.emptyOutSAP.TransBadGoodsInfoFromGVSToEHAIER_Type")
    @ResponseWrapper(localName = "TransBadGoodsInfoFromGVSToEHAIERResponse", targetNamespace = "http://www.example.org/TransBadGoodsInfoFromGVSToEHAIER/", className = "com.haier.afterSale.webService.emptyOutSAP.TransBadGoodsInfoFromGVSToEHAIERResponse")
    @WebResult(name = "out", targetNamespace = "")
    public com.haier.afterSale.webService.emptyOutSAP.OutType transBadGoodsInfoFromGVSToEHAIER(
        @WebParam(name = "SYSNAME", targetNamespace = "")
        java.lang.String sysname,
        @WebParam(name = "T_ZMMS0008", targetNamespace = "")
        java.util.List<com.haier.afterSale.webService.emptyOutSAP.ZMMINBOUND010IN> tZMMS0008
    );
}
