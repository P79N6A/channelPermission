package com.haier.stock.eai.finance.rebacktocvs;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.3
 * Thu Aug 15 19:39:46 CST 2013
 * Generated source version: 2.2.3
 * 
 */

@WebService(targetNamespace = "http://www.example.org/TransReBackInfoFromEHAIERToGVS/", name = "TransReBackInfoFromEHAIERToGVS")
@XmlSeeAlso({ ObjectFactory.class })
public interface TransReBackInfoFromEHAIERToGVS {

    @RequestWrapper(localName = "TransReBackInfoFromEHAIERToGVS", targetNamespace = "http://www.example.org/TransReBackInfoFromEHAIERToGVS/", className = "org.example.transrebackinfofromehaiertogvs.TransReBackInfoFromEHAIERToGVS_Type")
    @ResponseWrapper(localName = "TransReBackInfoFromEHAIERToGVSResponse", targetNamespace = "http://www.example.org/TransReBackInfoFromEHAIERToGVS/", className = "org.example.transrebackinfofromehaiertogvs.TransReBackInfoFromEHAIERToGVSResponse")
    @WebMethod(operationName = "TransReBackInfoFromEHAIERToGVS", action = "http://www.example.org/TransReBackInfoFromEHAIERToGVS/TransReBackInfoFromEHAIERToGVS")
    public void transReBackInfoFromEHAIERToGVS(
        @WebParam(name = "T_ZMMS0008", targetNamespace = "") java.util.List<ZMMS0008> tZMMS0008,
        @WebParam(mode = WebParam.Mode.OUT, name = "EX_SUBRC", targetNamespace = "") javax.xml.ws.Holder<Integer> exSUBRC,
        @WebParam(mode = WebParam.Mode.OUT, name = "T_MSG", targetNamespace = "") javax.xml.ws.Holder<java.util.List<ZSDS0002>> tMSG);
}
