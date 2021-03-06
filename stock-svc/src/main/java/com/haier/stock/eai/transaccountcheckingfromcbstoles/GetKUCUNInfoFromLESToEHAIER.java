package com.haier.stock.eai.transaccountcheckingfromcbstoles;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.3
 * Mon Jun 03 16:38:08 CST 2013
 * Generated source version: 2.2.3
 * 
 */

@WebService(targetNamespace = "http://www.example.org/GetKUCUNInfoFromLESToEHAIER/", name = "GetKUCUNInfoFromLESToEHAIER")
@XmlSeeAlso({ ObjectFactory.class })
public interface GetKUCUNInfoFromLESToEHAIER {

    @RequestWrapper(localName = "GetKUCUNInfoFromLESToEHAIER", targetNamespace = "http://www.example.org/GetKUCUNInfoFromLESToEHAIER/", className = "org.example.getkucuninfofromlestoehaier.GetKUCUNInfoFromLESToEHAIER_Type")
    @ResponseWrapper(localName = "GetKUCUNInfoFromLESToEHAIERResponse", targetNamespace = "http://www.example.org/GetKUCUNInfoFromLESToEHAIER/", className = "org.example.getkucuninfofromlestoehaier.GetKUCUNInfoFromLESToEHAIERResponse")
    @WebMethod(operationName = "GetKUCUNInfoFromLESToEHAIER", action = "http://www.example.org/GetKUCUNInfoFromLESToEHAIER/GetKUCUNInfoFromLESToEHAIER")
    public void getKUCUNInfoFromLESToEHAIER(@WebParam(name = "CRK", targetNamespace = "") java.lang.String crk,
                                            @WebParam(name = "DATE_BEGIN", targetNamespace = "") java.lang.String dateBEGIN,
                                            @WebParam(name = "DATE_END", targetNamespace = "") java.lang.String dateEND,
                                            @WebParam(name = "KUWEI", targetNamespace = "") java.lang.String kuwei,
                                            @WebParam(name = "TIME_BEGIN", targetNamespace = "") java.lang.String timeBEGIN,
                                            @WebParam(name = "TIME_END", targetNamespace = "") java.lang.String timeEND,
                                            @WebParam(mode = WebParam.Mode.OUT, name = "FLAG", targetNamespace = "") javax.xml.ws.Holder<java.lang.String> flag,
                                            @WebParam(mode = WebParam.Mode.OUT, name = "MESSAGE", targetNamespace = "") javax.xml.ws.Holder<java.lang.String> message,
                                            @WebParam(mode = WebParam.Mode.OUT, name = "OUTPUT", targetNamespace = "") javax.xml.ws.Holder<java.util.List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans>> output,
                                            @WebParam(mode = WebParam.Mode.OUT, name = "OUTPUT1", targetNamespace = "") javax.xml.ws.Holder<java.util.List<GetKUCUNInfoFromLESToEHAIERResponseStockQty>> output1);
}
