/*
 * 
 */

package com.haier.stock.eai.finance.order;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 2.2.3
 * Tue Aug 06 09:48:54 CST 2013
 * Generated source version: 2.2.3
 * 
 */

@WebServiceClient(name = "TransOrderInfoFromEhaierToGVS", wsdlLocation = "file:/F:/My_Job/Hair_Job/workspace/wsdl-creator/wsdl-creator/src/main/resources/TransOrderInfoFromEhaierToGVS.wsdl", targetNamespace = "http://www.example.org/TransOrderInfoFromEhaierToGVS/")
public class TransOrderInfoFromEhaierToGVS_Service extends Service {

    public final static URL   WSDL_LOCATION;
    public final static QName SERVICE                           = new QName(
                                                                    "http://www.example.org/TransOrderInfoFromEhaierToGVS/",
                                                                    "TransOrderInfoFromEhaierToGVS");
    public final static QName TransOrderInfoFromEhaierToGVSSOAP = new QName(
                                                                    "http://www.example.org/TransOrderInfoFromEhaierToGVS/",
                                                                    "TransOrderInfoFromEhaierToGVSSOAP");
    static {
        URL url = null;
        try {
            url = new URL(
                "file:/F:/My_Job/Hair_Job/workspace/wsdl-creator/wsdl-creator/src/main/resources/TransOrderInfoFromEhaierToGVS.wsdl");
        } catch (MalformedURLException e) {
            System.err
                .println("Can not initialize the default wsdl from file:/F:/My_Job/Hair_Job/workspace/wsdl-creator/wsdl-creator/src/main/resources/TransOrderInfoFromEhaierToGVS.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public TransOrderInfoFromEhaierToGVS_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public TransOrderInfoFromEhaierToGVS_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TransOrderInfoFromEhaierToGVS_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns TransOrderInfoFromEhaierToGVS
     */
    @WebEndpoint(name = "TransOrderInfoFromEhaierToGVSSOAP")
    public TransOrderInfoFromEhaierToGVS getTransOrderInfoFromEhaierToGVSSOAP() {
        return super
            .getPort(TransOrderInfoFromEhaierToGVSSOAP, TransOrderInfoFromEhaierToGVS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TransOrderInfoFromEhaierToGVS
     */
    @WebEndpoint(name = "TransOrderInfoFromEhaierToGVSSOAP")
    public TransOrderInfoFromEhaierToGVS getTransOrderInfoFromEhaierToGVSSOAP(WebServiceFeature... features) {
        return super.getPort(TransOrderInfoFromEhaierToGVSSOAP,
            TransOrderInfoFromEhaierToGVS.class, features);
    }

}
