package com.haier.stock.eai.finance.plpurchase.push;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2014-11-26T10:09:54.260+08:00
 * Generated source version: 2.7.11
 * 
 */
@WebService(targetNamespace = "http://www.example.org/Trans3PLReceiveInfoFromEhaierToGVS/", name = "Trans3PLReceiveInfoFromEhaierToGVS")
@XmlSeeAlso({ObjectFactory.class})
public interface Trans3PLReceiveInfoFromEhaierToGVS {

    @WebResult(name = "RETURN", targetNamespace = "")
    @RequestWrapper(localName = "Trans3PLReceiveInfoFromEhaierToGVS", targetNamespace = "http://www.example.org/Trans3PLReceiveInfoFromEhaierToGVS/", className = "org.example.trans3plreceiveinfofromehaiertogvs.Trans3PLReceiveInfoFromEhaierToGVS_Type")
    @WebMethod(operationName = "Trans3PLReceiveInfoFromEhaierToGVS", action = "http://www.example.org/Trans3PLReceiveInfoFromEhaierToGVS/Trans3PLReceiveInfoFromEhaierToGVS")
    @ResponseWrapper(localName = "Trans3PLReceiveInfoFromEhaierToGVSResponse", targetNamespace = "http://www.example.org/Trans3PLReceiveInfoFromEhaierToGVS/", className = "org.example.trans3plreceiveinfofromehaiertogvs.Trans3PLReceiveInfoFromEhaierToGVSResponse")
    public java.util.List<com.haier.stock.eai.finance.plpurchase.push.ZMMINBOUND007OUT> trans3PLReceiveInfoFromEhaierToGVS(
        @WebParam(name = "T_ZMMS0013", targetNamespace = "")
            java.util.List<com.haier.stock.eai.finance.plpurchase.push.ZMMS0013> tZMMS0013,
        @WebParam(name = "Sysname", targetNamespace = "")
            String sysname
    );
}
