package com.haier.stock.eai.finance.order.query;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.3
 * Tue Aug 06 10:07:03 CST 2013
 * Generated source version: 2.2.3
 * 
 */

@WebService(targetNamespace = "http://www.example.org/QueryOrderHandleInfoFromGVS/", name = "QueryOrderHandleInfoFromGVS")
@XmlSeeAlso({ ObjectFactory.class })
public interface QueryOrderHandleInfoFromGVS {

    @WebResult(name = "T_ZSDS0003", targetNamespace = "")
    @RequestWrapper(localName = "QueryOrderHandleInfoFromGVS", targetNamespace = "http://www.example.org/QueryOrderHandleInfoFromGVS/", className = "org.example.queryorderhandleinfofromgvs.QueryOrderHandleInfoFromGVS_Type")
    @ResponseWrapper(localName = "QueryOrderHandleInfoFromGVSResponse", targetNamespace = "http://www.example.org/QueryOrderHandleInfoFromGVS/", className = "org.example.queryorderhandleinfofromgvs.QueryOrderHandleInfoFromGVSResponse")
    @WebMethod(operationName = "QueryOrderHandleInfoFromGVS", action = "http://www.example.org/QueryOrderHandleInfoFromGVS/QueryOrderHandleInfoFromGVS")
    public java.util.List<com.haier.stock.eai.finance.order.query.ZSDS0003> queryOrderHandleInfoFromGVS(
        @WebParam(name = "T_ZSDS0006", targetNamespace = "") java.util.List<com.haier.stock.eai.finance.order.query.ZSDS0006> tZSDS0006);
}