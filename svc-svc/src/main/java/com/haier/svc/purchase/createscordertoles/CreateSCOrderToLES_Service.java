package com.haier.svc.purchase.createscordertoles;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.1
 * 2014-08-13T16:43:23.118+08:00
 * Generated source version: 3.0.1
 * 
 */
@WebServiceClient(name = "CreateSCOrderToLES", 
                  wsdlLocation = "file:/C:/Users/Administrator/Desktop/wsdl/CreateSCOrderToLES.wsdl",
                  targetNamespace = "http://www.example.org/CreateSCOrderToLES/") 
public class CreateSCOrderToLES_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.example.org/CreateSCOrderToLES/", "CreateSCOrderToLES");
    public final static QName CreateSCOrderToLESSOAP = new QName("http://www.example.org/CreateSCOrderToLES/", "CreateSCOrderToLESSOAP");
    static {
        URL url = null;
        try {
            url = new URL("file:/C:/Users/Administrator/Desktop/wsdl/CreateSCOrderToLES.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(CreateSCOrderToLES_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/C:/Users/Administrator/Desktop/wsdl/CreateSCOrderToLES.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public CreateSCOrderToLES_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public CreateSCOrderToLES_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CreateSCOrderToLES_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns CreateSCOrderToLES
     */
    @WebEndpoint(name = "CreateSCOrderToLESSOAP")
    public CreateSCOrderToLES getCreateSCOrderToLESSOAP() {
        return super.getPort(CreateSCOrderToLESSOAP, CreateSCOrderToLES.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CreateSCOrderToLES
     */
    @WebEndpoint(name = "CreateSCOrderToLESSOAP")
    public CreateSCOrderToLES getCreateSCOrderToLESSOAP(WebServiceFeature... features) {
        return super.getPort(CreateSCOrderToLESSOAP, CreateSCOrderToLES.class, features);
    }

}