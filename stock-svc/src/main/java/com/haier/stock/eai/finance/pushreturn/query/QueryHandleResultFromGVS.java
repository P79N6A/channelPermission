package com.haier.stock.eai.finance.pushreturn.query;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.3
 * Tue Aug 06 10:09:30 CST 2013
 * Generated source version: 2.2.3
 * 
 */

@WebService(targetNamespace = "http://www.example.org/QueryHandleResultFromGVS/", name = "QueryHandleResultFromGVS")
@XmlSeeAlso({ ObjectFactory.class })
public interface QueryHandleResultFromGVS {

    @WebResult(name = "T_ZSDS0003", targetNamespace = "")
    @RequestWrapper(localName = "QueryHandleResultFromGVS", targetNamespace = "http://www.example.org/QueryHandleResultFromGVS/", className = "org.example.queryhandleresultfromgvs.QueryHandleResultFromGVS_Type")
    @ResponseWrapper(localName = "QueryHandleResultFromGVSResponse", targetNamespace = "http://www.example.org/QueryHandleResultFromGVS/", className = "org.example.queryhandleresultfromgvs.QueryHandleResultFromGVSResponse")
    @WebMethod(operationName = "QueryHandleResultFromGVS", action = "http://www.example.org/QueryHandleResultFromGVS/QueryHandleResultFromGVS")
    public java.util.List<ZSDS0003> queryHandleResultFromGVS(
        @WebParam(name = "T_ZSDS0006", targetNamespace = "") java.util.List<ZSDS0006> tZSDS0006);
}
