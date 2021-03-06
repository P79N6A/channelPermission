package com.haier.svc.bean.getucunioninfofromles;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * OSB Service
 *
 * This class was generated by Apache CXF 3.1.12
 * 2018-04-10T15:38:02.148+08:00
 * Generated source version: 3.1.12
 * 
 */
@WebServiceClient(name = "GetKUCUNInfoFromLESToEHAIER", 
                  wsdlLocation = "file:/D:/java/GetKUCUNInfoFromLESToEHAIER.wsdl",
                  targetNamespace = "http://www.example.org/GetKUCUNInfoFromLESToEHAIER/") 
public class GetKUCUNInfoFromLESToEHAIER_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.example.org/GetKUCUNInfoFromLESToEHAIER/", "GetKUCUNInfoFromLESToEHAIER");
    public final static QName GetKUCUNInfoFromLESToEHAIERSOAP = new QName("http://www.example.org/GetKUCUNInfoFromLESToEHAIER/", "GetKUCUNInfoFromLESToEHAIERSOAP");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/java/GetKUCUNInfoFromLESToEHAIER.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(GetKUCUNInfoFromLESToEHAIER_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/D:/java/GetKUCUNInfoFromLESToEHAIER.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public GetKUCUNInfoFromLESToEHAIER_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GetKUCUNInfoFromLESToEHAIER_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GetKUCUNInfoFromLESToEHAIER_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    




    /**
     *
     * @return
     *     returns GetKUCUNInfoFromLESToEHAIER
     */
    @WebEndpoint(name = "GetKUCUNInfoFromLESToEHAIERSOAP")
    public GetKUCUNInfoFromLESToEHAIER getGetKUCUNInfoFromLESToEHAIERSOAP() {
        return super.getPort(GetKUCUNInfoFromLESToEHAIERSOAP, GetKUCUNInfoFromLESToEHAIER.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GetKUCUNInfoFromLESToEHAIER
     */
    @WebEndpoint(name = "GetKUCUNInfoFromLESToEHAIERSOAP")
    public GetKUCUNInfoFromLESToEHAIER getGetKUCUNInfoFromLESToEHAIERSOAP(WebServiceFeature... features) {
        return super.getPort(GetKUCUNInfoFromLESToEHAIERSOAP, GetKUCUNInfoFromLESToEHAIER.class, features);
    }

}
