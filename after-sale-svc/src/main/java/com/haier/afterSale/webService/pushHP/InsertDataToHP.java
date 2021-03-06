
package com.haier.afterSale.webService.pushHP;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "InsertDataToHP", targetNamespace = "http://www.example.org/InsertDataToHP/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface InsertDataToHP {


    /**
     * 
     * @param msg
     * @param flag
     * @param inputs
     */
    @WebMethod(operationName = "InsertDataToHP", action = "http://www.example.org/InsertDataToHP/InsertDataToHP")
    @RequestWrapper(localName = "InsertDataToHP", targetNamespace = "http://www.example.org/InsertDataToHP/", className = "com.haier.afterSale.webService.pushHP.InsertDataToHP_Type")
    @ResponseWrapper(localName = "InsertDataToHPResponse", targetNamespace = "http://www.example.org/InsertDataToHP/", className = "com.haier.afterSale.webService.pushHP.InsertDataToHPResponse")
    public void insertDataToHP(
            @WebParam(name = "Inputs", targetNamespace = "")
                    List<com.haier.afterSale.webService.pushHP.InsertDataToHP_Type.Inputs> inputs,
            @WebParam(name = "FLAG", targetNamespace = "", mode = WebParam.Mode.OUT)
                    Holder<String> flag,
            @WebParam(name = "MSG", targetNamespace = "", mode = WebParam.Mode.OUT)
                    Holder<String> msg);

}
