package com.haier.vehicle.wsdl.mdm;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2018-01-26T20:34:50.255+08:00
 * Generated source version: 3.1.12
 * 
 */
@WebService(targetNamespace = "http://www.example.org/TransCustomerInfoXDToMDM/", name = "TransCustomerInfoXDToMDM")
@XmlSeeAlso({ObjectFactory.class})
public interface TransCustomerInfoXDToMDM {

    @WebMethod(operationName = "TransCustomerInfoXDToMDM", action = "http://www.example.org/TransCustomerInfoXDToMDM/TransCustomerInfoXDToMDM")
    @RequestWrapper(localName = "TransCustomerInfoXDToMDM", targetNamespace = "http://www.example.org/TransCustomerInfoXDToMDM/", className = "com.haier.vehicle.wsdl.mdm.TransCustomerInfoXDToMDM_Type")
    @ResponseWrapper(localName = "TransCustomerInfoXDToMDMResponse", targetNamespace = "http://www.example.org/TransCustomerInfoXDToMDM/", className = "com.haier.vehicle.wsdl.mdm.TransCustomerInfoXDToMDMResponse")
    public void transCustomerInfoXDToMDM(
        @WebParam(name = "IN_SYS_NAME", targetNamespace = "")
        java.lang.String inSYSNAME,
        @WebParam(name = "IN_MASTER_TYPE", targetNamespace = "")
        java.lang.String inMASTERTYPE,
        @WebParam(name = "IN_TABLE_NAME", targetNamespace = "")
        java.lang.String inTABLENAME,
        @WebParam(name = "IN_FIELDS_VALUE_TABLE", targetNamespace = "")
        java.util.List<com.haier.vehicle.wsdl.mdm.HAIERMDMFIELDSVALUETABLE> inFIELDSVALUETABLE,
        @WebParam(mode = WebParam.Mode.OUT, name = "OUT_RESULT", targetNamespace = "")
        javax.xml.ws.Holder<java.lang.String> outRESULT,
        @WebParam(mode = WebParam.Mode.OUT, name = "OUT_RETMSG", targetNamespace = "")
        javax.xml.ws.Holder<java.lang.String> outRETMSG,
        @WebParam(mode = WebParam.Mode.OUT, name = "OUT_RETCODE", targetNamespace = "")
        javax.xml.ws.Holder<java.lang.String> outRETCODE
    );
}