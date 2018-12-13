package com.haier.afterSale.webService.cancelDispatchedWorkers;

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
 * This class was generated by Apache CXF 3.1.11
 * 2018-06-04T11:12:59.863+08:00
 * Generated source version: 3.1.11
 * 
 */
@WebServiceClient(name = "transordercancelfromehaiertohp_client_ep", 
                  wsdlLocation = "file:/D:/java/TransOrderCancelFromEHAIERToHP.wsdl",
                  targetNamespace = "http://xmlns.oracle.com/MyFirstSOA/TransOrderCancelFromEHAIERToHP/TransOrderCancelFromEHAIERToHP") 
public class TransordercancelfromehaiertohpClientEp extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://xmlns.oracle.com/MyFirstSOA/TransOrderCancelFromEHAIERToHP/TransOrderCancelFromEHAIERToHP", "transordercancelfromehaiertohp_client_ep");
    public final static QName TransOrderCancelFromEHAIERToHPPt = new QName("http://xmlns.oracle.com/MyFirstSOA/TransOrderCancelFromEHAIERToHP/TransOrderCancelFromEHAIERToHP", "TransOrderCancelFromEHAIERToHP_pt");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/java/TransOrderCancelFromEHAIERToHP.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(TransordercancelfromehaiertohpClientEp.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/D:/java/TransOrderCancelFromEHAIERToHP.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public TransordercancelfromehaiertohpClientEp(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public TransordercancelfromehaiertohpClientEp(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TransordercancelfromehaiertohpClientEp() {
        super(WSDL_LOCATION, SERVICE);
    }
    




    /**
     *
     * @return
     *     returns TransOrderCancelFromEHAIERToHP
     */
    @WebEndpoint(name = "TransOrderCancelFromEHAIERToHP_pt")
    public TransOrderCancelFromEHAIERToHP getTransOrderCancelFromEHAIERToHPPt() {
        return super.getPort(TransOrderCancelFromEHAIERToHPPt, TransOrderCancelFromEHAIERToHP.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TransOrderCancelFromEHAIERToHP
     */
    @WebEndpoint(name = "TransOrderCancelFromEHAIERToHP_pt")
    public TransOrderCancelFromEHAIERToHP getTransOrderCancelFromEHAIERToHPPt(WebServiceFeature... features) {
        return super.getPort(TransOrderCancelFromEHAIERToHPPt, TransOrderCancelFromEHAIERToHP.class, features);
    }

}