package com.haier.svc.bean.gettidanzwdfromlestoehaier;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.1
 * 2014-09-02T18:12:05.632+08:00
 * Generated source version: 3.0.1
 * 
 */
@WebServiceClient(name = "GetTidanZWDFromLESToEHAIER", 
                  wsdlLocation = "GetTidanZWDFromLESToEHAIER.wsdl",
                  targetNamespace = "http://www.example.org/GetTidanZWDFromLESToEHAIER/") 
public class GetTidanZWDFromLESToEHAIER_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.example.org/GetTidanZWDFromLESToEHAIER/", "GetTidanZWDFromLESToEHAIER");
    public final static QName GetTidanZWDFromLESToEHAIERSOAP = new QName("http://www.example.org/GetTidanZWDFromLESToEHAIER/", "GetTidanZWDFromLESToEHAIERSOAP");
    static {
        URL url = GetTidanZWDFromLESToEHAIER_Service.class.getResource("GetTidanZWDFromLESToEHAIER.wsdl");
        if (url == null) {
            url = GetTidanZWDFromLESToEHAIER_Service.class.getClassLoader().getResource("GetTidanZWDFromLESToEHAIER.wsdl");
        } 
        if (url == null) {
            java.util.logging.Logger.getLogger(GetTidanZWDFromLESToEHAIER_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "GetTidanZWDFromLESToEHAIER.wsdl");
        }       
        WSDL_LOCATION = url;
    }

    public GetTidanZWDFromLESToEHAIER_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GetTidanZWDFromLESToEHAIER_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GetTidanZWDFromLESToEHAIER_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns GetTidanZWDFromLESToEHAIER
     */
    @WebEndpoint(name = "GetTidanZWDFromLESToEHAIERSOAP")
    public GetTidanZWDFromLESToEHAIER getGetTidanZWDFromLESToEHAIERSOAP() {
        return super.getPort(GetTidanZWDFromLESToEHAIERSOAP, GetTidanZWDFromLESToEHAIER.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GetTidanZWDFromLESToEHAIER
     */
    @WebEndpoint(name = "GetTidanZWDFromLESToEHAIERSOAP")
    public GetTidanZWDFromLESToEHAIER getGetTidanZWDFromLESToEHAIERSOAP(WebServiceFeature... features) {
        return super.getPort(GetTidanZWDFromLESToEHAIERSOAP, GetTidanZWDFromLESToEHAIER.class, features);
    }

}
