package com.haier.svc.purchase.transforecastpracticalfromb2ctooms;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.0.1
 * 2016-01-28T16:07:59.585+08:00
 * Generated source version: 3.0.1
 * 
 */
@WebService(targetNamespace = "http://www.example.org/TransForecastPracticalFromB2CToOMS/", name = "TransForecastPracticalFromB2CToOMS")
@XmlSeeAlso({ObjectFactory.class})
public interface TransForecastPracticalFromB2CToOMS {

    @WebResult(name = "out", targetNamespace = "")
    @RequestWrapper(localName = "TransForecastPracticalFromB2CToOMS", targetNamespace = "http://www.example.org/TransForecastPracticalFromB2CToOMS/", className = "com.haier.svc.bean.transforecastpracticalfromb2ctooms.TransForecastPracticalFromB2CToOMS_Type")
    @WebMethod(operationName = "TransForecastPracticalFromB2CToOMS", action = "http://www.example.org/TransForecastPracticalFromB2CToOMS/TransForecastPracticalFromB2CToOMS")
    @ResponseWrapper(localName = "TransForecastPracticalFromB2CToOMSResponse", targetNamespace = "http://www.example.org/TransForecastPracticalFromB2CToOMS/", className = "com.haier.svc.purchase.transforecastpracticalfromb2ctooms.TransForecastPracticalFromB2CToOMSResponse")
    public java.util.List<com.haier.svc.bean.transforecastpracticalfromb2ctooms.OutType> transForecastPracticalFromB2CToOMS(
        @WebParam(name = "customerCode", targetNamespace = "")
        java.lang.String customerCode,
        @WebParam(name = "customerName", targetNamespace = "")
        java.lang.String customerName,
        @WebParam(name = "fromSystem", targetNamespace = "")
        java.lang.String fromSystem,
        @WebParam(name = "omsRole", targetNamespace = "")
        java.lang.String omsRole,
        @WebParam(name = "roleChannel", targetNamespace = "")
        java.lang.String roleChannel,
        @WebParam(name = "tradeCode", targetNamespace = "")
        java.lang.String tradeCode,
        @WebParam(name = "itemcode", targetNamespace = "")
        java.lang.String itemcode,
        @WebParam(name = "itemname", targetNamespace = "")
        java.lang.String itemname
    );
}
