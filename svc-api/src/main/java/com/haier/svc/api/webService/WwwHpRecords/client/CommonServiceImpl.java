
package com.haier.svc.api.webService.WwwHpRecords.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CommonServiceImpl", targetNamespace = "http://ServiceImpl.WwwHpRecords.webService.api.svc.haier.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CommonServiceImpl {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "xmlString", targetNamespace = "http://ServiceImpl.WwwHpRecords.webService.api.svc.haier.com/", className = "com.haier.svc.api.webService.WwwHpRecords.client.XmlString")
    @ResponseWrapper(localName = "xmlStringResponse", targetNamespace = "http://ServiceImpl.WwwHpRecords.webService.api.svc.haier.com/", className = "com.haier.svc.api.webService.WwwHpRecords.client.XmlStringResponse")
    @Action(input = "http://ServiceImpl.WwwHpRecords.webService.api.svc.haier.com/CommonServiceImpl/xmlStringRequest", output = "http://ServiceImpl.WwwHpRecords.webService.api.svc.haier.com/CommonServiceImpl/xmlStringResponse")
    public String xmlString(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0);

}