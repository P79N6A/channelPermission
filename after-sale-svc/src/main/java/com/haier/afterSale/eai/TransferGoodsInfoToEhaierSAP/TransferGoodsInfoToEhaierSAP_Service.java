package com.haier.afterSale.eai.TransferGoodsInfoToEhaierSAP;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.2.4
 * 2018-06-29T10:45:43.893+08:00
 * Generated source version: 3.2.4
 *
 */
@WebServiceClient(name = "TransferGoodsInfoToEhaierSAP",
                  wsdlLocation = "file:/D:/administrator/doc/haiersvc/TransferGoodsInfoToEhaierSAP.wsdl",
                  targetNamespace = "http://www.example.org/TransferGoodsInfoToEhaierSAP/")
public class TransferGoodsInfoToEhaierSAP_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.example.org/TransferGoodsInfoToEhaierSAP/", "TransferGoodsInfoToEhaierSAP");
    public final static QName TransferGoodsInfoToEhaierSAPSOAP = new QName("http://www.example.org/TransferGoodsInfoToEhaierSAP/", "TransferGoodsInfoToEhaierSAPSOAP");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/administrator/doc/haiersvc/TransferGoodsInfoToEhaierSAP.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(TransferGoodsInfoToEhaierSAP_Service.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "file:/D:/administrator/doc/haiersvc/TransferGoodsInfoToEhaierSAP.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public TransferGoodsInfoToEhaierSAP_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public TransferGoodsInfoToEhaierSAP_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TransferGoodsInfoToEhaierSAP_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    public TransferGoodsInfoToEhaierSAP_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public TransferGoodsInfoToEhaierSAP_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public TransferGoodsInfoToEhaierSAP_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns TransferGoodsInfoToEhaierSAP
     */
    @WebEndpoint(name = "TransferGoodsInfoToEhaierSAPSOAP")
    public TransferGoodsInfoToEhaierSAP getTransferGoodsInfoToEhaierSAPSOAP() {
        return super.getPort(TransferGoodsInfoToEhaierSAPSOAP, TransferGoodsInfoToEhaierSAP.class);
    }

    /**
     *
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TransferGoodsInfoToEhaierSAP
     */
    @WebEndpoint(name = "TransferGoodsInfoToEhaierSAPSOAP")
    public TransferGoodsInfoToEhaierSAP getTransferGoodsInfoToEhaierSAPSOAP(WebServiceFeature... features) {
        return super.getPort(TransferGoodsInfoToEhaierSAPSOAP, TransferGoodsInfoToEhaierSAP.class, features);
    }

}