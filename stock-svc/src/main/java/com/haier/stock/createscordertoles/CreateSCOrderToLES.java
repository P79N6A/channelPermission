package com.haier.stock.createscordertoles;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.4
 * 2018-06-01T16:48:01.415+08:00
 * Generated source version: 3.2.4
 *
 */
@WebService(targetNamespace = "http://www.example.org/CreateSCOrderToLES/", name = "CreateSCOrderToLES")
@XmlSeeAlso({ObjectFactory.class})
public interface CreateSCOrderToLES {

    @WebMethod(operationName = "CreateSCOrderToLES", action = "http://www.example.org/CreateSCOrderToLES/CreateSCOrderToLES")
    @RequestWrapper(localName = "CreateSCOrderToLES", targetNamespace = "http://www.example.org/CreateSCOrderToLES/", className = "com.haier.stock.createscordertoles.CreateSCOrderToLES_Type")
    @ResponseWrapper(localName = "CreateSCOrderToLESResponse", targetNamespace = "http://www.example.org/CreateSCOrderToLES/", className = "com.haier.stock.createscordertoles.CreateSCOrderToLESResponse")
    @WebResult(name = "Output", targetNamespace = "")
    public java.util.List<com.haier.stock.createscordertoles.OutputType> createSCOrderToLES(
        @WebParam(name = "Input", targetNamespace = "")
        java.util.List<com.haier.stock.createscordertoles.InputType> input
    );
}