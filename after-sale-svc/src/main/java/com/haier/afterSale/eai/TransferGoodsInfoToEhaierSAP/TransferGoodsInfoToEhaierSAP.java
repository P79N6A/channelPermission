package com.haier.afterSale.eai.TransferGoodsInfoToEhaierSAP;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.4
 * 2018-06-29T10:45:43.853+08:00
 * Generated source version: 3.2.4
 *
 */
@WebService(targetNamespace = "http://www.example.org/TransferGoodsInfoToEhaierSAP/", name = "TransferGoodsInfoToEhaierSAP")
@XmlSeeAlso({ObjectFactory.class})
public interface TransferGoodsInfoToEhaierSAP {

    @WebMethod(operationName = "TransferGoodsInfoToEhaierSAP", action = "http://www.example.org/TransferGoodsInfoToEhaierSAP/TransferGoodsInfoToEhaierSAP")
    @RequestWrapper(localName = "TransferGoodsInfoToEhaierSAP", targetNamespace = "http://www.example.org/TransferGoodsInfoToEhaierSAP/", className = "com.haier.afterSale.eai.TransferGoodsInfoToEhaierSAP.TransferGoodsInfoToEhaierSAP_Type")
    @ResponseWrapper(localName = "TransferGoodsInfoToEhaierSAPResponse", targetNamespace = "http://www.example.org/TransferGoodsInfoToEhaierSAP/", className = "com.haier.afterSale.eai.TransferGoodsInfoToEhaierSAP.TransferGoodsInfoToEhaierSAPResponse")
    public void transferGoodsInfoToEhaierSAP(
            @WebParam(name = "T_ZMMS0010", targetNamespace = "")
                    java.util.List<ZMMS0010> tZMMS0010,
            @WebParam(mode = WebParam.Mode.OUT, name = "EX_SUBRC", targetNamespace = "")
                    javax.xml.ws.Holder<Integer> exSUBRC,
            @WebParam(mode = WebParam.Mode.OUT, name = "T_MSG", targetNamespace = "")
                    javax.xml.ws.Holder<java.util.List<ZSDS0002>> tMSG
    );
}