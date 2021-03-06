package com.haier.stock.eai.getTidan;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.3
 * Wed May 28 14:02:40 CST 2014
 * Generated source version: 2.2.3
 * 
 */

@WebService(targetNamespace = "http://www.example.org/GetTidanZWDFromLESToEHAIER/", name = "GetTidanZWDFromLESToEHAIER")
@XmlSeeAlso({ ObjectFactory.class })
public interface GetTidanZWDFromLESToEHAIER {

    @RequestWrapper(localName = "GetTidanZWDFromLESToEHAIER", targetNamespace = "http://www.example.org/GetTidanZWDFromLESToEHAIER/", className = "org.example.gettidanzwdfromlestoehaier.GetTidanZWDFromLESToEHAIER_Type")
    @ResponseWrapper(localName = "GetTidanZWDFromLESToEHAIERResponse", targetNamespace = "http://www.example.org/GetTidanZWDFromLESToEHAIER/", className = "org.example.gettidanzwdfromlestoehaier.GetTidanZWDFromLESToEHAIERResponse")
    @WebMethod(operationName = "GetTidanZWDFromLESToEHAIER", action = "http://www.example.org/GetTidanZWDFromLESToEHAIER/GetTidanZWDFromLESToEHAIER")
    public void getTidanZWDFromLESToEHAIER(
        @WebParam(name = "ERDAT", targetNamespace = "") String erdat,
        @WebParam(mode = WebParam.Mode.OUT, name = "FLAG", targetNamespace = "") javax.xml.ws.Holder<String> flag,
        @WebParam(mode = WebParam.Mode.OUT, name = "MESSAGE", targetNamespace = "") javax.xml.ws.Holder<String> message,
        @WebParam(mode = WebParam.Mode.OUT, name = "OUTPUT", targetNamespace = "") javax.xml.ws.Holder<java.util.List<ZWDTABLE2>> output);
}
