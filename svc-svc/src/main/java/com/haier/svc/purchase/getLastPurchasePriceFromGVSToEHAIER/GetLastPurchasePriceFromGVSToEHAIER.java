package com.haier.svc.purchase.getLastPurchasePriceFromGVSToEHAIER;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2017-04-25T18:05:26.485+08:00
 * Generated source version: 2.7.18
 * 
 */
@WebService(targetNamespace = "http://www.example.org/GetLastPurchasePriceFromGVSToEHAIER/", name = "GetLastPurchasePriceFromGVSToEHAIER")
@XmlSeeAlso({ObjectFactory.class})
public interface GetLastPurchasePriceFromGVSToEHAIER {

    @WebResult(name = "out", targetNamespace = "")
    @RequestWrapper(localName = "GetLastPurchasePriceFromGVSToEHAIER", targetNamespace = "http://www.example.org/GetLastPurchasePriceFromGVSToEHAIER/", className = "com.haier.webserviceclient.client.NC.GetLastPurchasePriceFromGVSToEHAIER_Type")
    @WebMethod(operationName = "GetLastPurchasePriceFromGVSToEHAIER", action = "http://www.example.org/GetLastPurchasePriceFromGVSToEHAIER/GetLastPurchasePriceFromGVSToEHAIER")
    @ResponseWrapper(localName = "GetLastPurchasePriceFromGVSToEHAIERResponse", targetNamespace = "http://www.example.org/GetLastPurchasePriceFromGVSToEHAIER/", className = "com.haier.webserviceclient.client.NC.GetLastPurchasePriceFromGVSToEHAIERResponse")
    public java.util.List<OutType> getLastPurchasePriceFromGVSToEHAIER(
        @WebParam(name = "in", targetNamespace = "")
            java.util.List<InType> in,
        @WebParam(name = "SysName", targetNamespace = "")
            String sysName
    );
}
