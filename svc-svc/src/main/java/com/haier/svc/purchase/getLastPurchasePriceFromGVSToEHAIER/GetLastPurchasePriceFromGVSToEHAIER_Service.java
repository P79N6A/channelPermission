package com.haier.svc.purchase.getLastPurchasePriceFromGVSToEHAIER;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2017-04-25T18:05:26.493+08:00
 * Generated source version: 2.7.18
 * 
 */
@WebServiceClient(name = "GetLastPurchasePriceFromGVSToEHAIER", 
                  wsdlLocation = "file:/D:/work/wsdl/GetLastPurchasePriceFromGVSToEHAIER.wsdl",
                  targetNamespace = "http://www.example.org/GetLastPurchasePriceFromGVSToEHAIER/") 
public class GetLastPurchasePriceFromGVSToEHAIER_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.example.org/GetLastPurchasePriceFromGVSToEHAIER/", "GetLastPurchasePriceFromGVSToEHAIER");
    public final static QName GetLastPurchasePriceFromGVSToEHAIERSOAP = new QName("http://www.example.org/GetLastPurchasePriceFromGVSToEHAIER/", "GetLastPurchasePriceFromGVSToEHAIERSOAP");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/work/wsdl/GetLastPurchasePriceFromGVSToEHAIER.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(GetLastPurchasePriceFromGVSToEHAIER_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/D:/work/wsdl/GetLastPurchasePriceFromGVSToEHAIER.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public GetLastPurchasePriceFromGVSToEHAIER_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GetLastPurchasePriceFromGVSToEHAIER_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GetLastPurchasePriceFromGVSToEHAIER_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns GetLastPurchasePriceFromGVSToEHAIER
     */
    @WebEndpoint(name = "GetLastPurchasePriceFromGVSToEHAIERSOAP")
    public GetLastPurchasePriceFromGVSToEHAIER getGetLastPurchasePriceFromGVSToEHAIERSOAP() {
        return super.getPort(GetLastPurchasePriceFromGVSToEHAIERSOAP, GetLastPurchasePriceFromGVSToEHAIER.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GetLastPurchasePriceFromGVSToEHAIER
     */
    @WebEndpoint(name = "GetLastPurchasePriceFromGVSToEHAIERSOAP")
    public GetLastPurchasePriceFromGVSToEHAIER getGetLastPurchasePriceFromGVSToEHAIERSOAP(WebServiceFeature... features) {
        return super.getPort(GetLastPurchasePriceFromGVSToEHAIERSOAP, GetLastPurchasePriceFromGVSToEHAIER.class, features);
    }

}
