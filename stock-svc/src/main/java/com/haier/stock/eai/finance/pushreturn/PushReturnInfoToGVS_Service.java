/*
 * 
 */

package com.haier.stock.eai.finance.pushreturn;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 2.2.3
 * Tue Aug 06 09:58:31 CST 2013
 * Generated source version: 2.2.3
 * 
 */

@WebServiceClient(name = "PushReturnInfoToGVS", wsdlLocation = "file:/F:/My_Job/Hair_Job/workspace/wsdl-creator/wsdl-creator/src/main/resources/PushReturnInfoToGVS.wsdl", targetNamespace = "http://www.example.org/PushReturnInfoToGVS/")
public class PushReturnInfoToGVS_Service extends Service {

    public final static URL   WSDL_LOCATION;
    public final static QName SERVICE                 = new QName(
                                                          "http://www.example.org/PushReturnInfoToGVS/",
                                                          "PushReturnInfoToGVS");
    public final static QName PushReturnInfoToGVSSOAP = new QName(
                                                          "http://www.example.org/PushReturnInfoToGVS/",
                                                          "PushReturnInfoToGVSSOAP");
    static {
        URL url = null;
        try {
            url = new URL(
                "file:/F:/My_Job/Hair_Job/workspace/wsdl-creator/wsdl-creator/src/main/resources/PushReturnInfoToGVS.wsdl");
        } catch (MalformedURLException e) {
            System.err
                .println("Can not initialize the default wsdl from file:/F:/My_Job/Hair_Job/workspace/wsdl-creator/wsdl-creator/src/main/resources/PushReturnInfoToGVS.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public PushReturnInfoToGVS_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public PushReturnInfoToGVS_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PushReturnInfoToGVS_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns PushReturnInfoToGVS
     */
    @WebEndpoint(name = "PushReturnInfoToGVSSOAP")
    public PushReturnInfoToGVS getPushReturnInfoToGVSSOAP() {
        return super.getPort(PushReturnInfoToGVSSOAP, PushReturnInfoToGVS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PushReturnInfoToGVS
     */
    @WebEndpoint(name = "PushReturnInfoToGVSSOAP")
    public PushReturnInfoToGVS getPushReturnInfoToGVSSOAP(WebServiceFeature... features) {
        return super.getPort(PushReturnInfoToGVSSOAP, PushReturnInfoToGVS.class, features);
    }

}