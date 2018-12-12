package com.haier.svc.purchase.omst3;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.1
 * 2014-08-14T12:55:37.623+08:00
 * Generated source version: 3.0.1
 * 
 */
@WebServiceClient(name = "TransForecastInfoFromOMS", 
                  wsdlLocation = "TransForecastInfoFromOMS.wsdl",
                  targetNamespace = "http://www.example.org/TransForecastInfoFromOMS/") 
public class TransForecastInfoFromOMS_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.example.org/TransForecastInfoFromOMS/", "TransForecastInfoFromOMS");
    public final static QName TransForecastInfoFromOMSSOAP = new QName("http://www.example.org/TransForecastInfoFromOMS/", "TransForecastInfoFromOMSSOAP");
    static {
        URL url = TransForecastInfoFromOMS_Service.class.getResource("TransForecastInfoFromOMS.wsdl");
        if (url == null) {
            url = TransForecastInfoFromOMS_Service.class.getClassLoader().getResource("TransForecastInfoFromOMS.wsdl");
        } 
        if (url == null) {
            java.util.logging.Logger.getLogger(TransForecastInfoFromOMS_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "TransForecastInfoFromOMS.wsdl");
        }       
        WSDL_LOCATION = url;
    }

    public TransForecastInfoFromOMS_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public TransForecastInfoFromOMS_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TransForecastInfoFromOMS_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns TransForecastInfoFromOMS
     */
    @WebEndpoint(name = "TransForecastInfoFromOMSSOAP")
    public TransForecastInfoFromOMS getTransForecastInfoFromOMSSOAP() {
        return super.getPort(TransForecastInfoFromOMSSOAP, TransForecastInfoFromOMS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TransForecastInfoFromOMS
     */
    @WebEndpoint(name = "TransForecastInfoFromOMSSOAP")
    public TransForecastInfoFromOMS getTransForecastInfoFromOMSSOAP(WebServiceFeature... features) {
        return super.getPort(TransForecastInfoFromOMSSOAP, TransForecastInfoFromOMS.class, features);
    }

}