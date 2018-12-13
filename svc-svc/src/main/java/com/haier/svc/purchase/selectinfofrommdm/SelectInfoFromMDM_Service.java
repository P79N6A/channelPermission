/*
 * 
 */

package com.haier.svc.purchase.selectinfofrommdm;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 2.2.3
 * Fri Jul 19 10:09:45 CST 2013
 * Generated source version: 2.2.3
 * 
 */

@WebServiceClient(name = "SelectInfoFromMDM", wsdlLocation = "file:/Users/benio/Documents/projects/wsdl-creator/wsdl-creator/src/main/resources/SelectInfoFromMDM.wsdl", targetNamespace = "http://www.example.org/SelectInfoFromMDM/")
public class SelectInfoFromMDM_Service extends Service {

    public final static URL   WSDL_LOCATION;
    public final static QName SERVICE               = new QName(
                                                        "http://www.example.org/SelectInfoFromMDM/",
                                                        "SelectInfoFromMDM");
    public final static QName SelectInfoFromMDMSOAP = new QName(
                                                        "http://www.example.org/SelectInfoFromMDM/",
                                                        "SelectInfoFromMDMSOAP");
    static {
        URL url = null;
        try {
            url = new URL(
                "file:/Users/benio/Documents/projects/wsdl-creator/wsdl-creator/src/main/resources/SelectInfoFromMDM.wsdl");
        } catch (MalformedURLException e) {
            System.err
                .println("Can not initialize the default wsdl from file:/Users/benio/Documents/projects/wsdl-creator/wsdl-creator/src/main/resources/SelectInfoFromMDM.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public SelectInfoFromMDM_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SelectInfoFromMDM_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SelectInfoFromMDM_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns SelectInfoFromMDM
     */
    @WebEndpoint(name = "SelectInfoFromMDMSOAP")
    public SelectInfoFromMDM getSelectInfoFromMDMSOAP() {
        return super.getPort(SelectInfoFromMDMSOAP, SelectInfoFromMDM.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SelectInfoFromMDM
     */
    @WebEndpoint(name = "SelectInfoFromMDMSOAP")
    public SelectInfoFromMDM getSelectInfoFromMDMSOAP(WebServiceFeature... features) {
        return super.getPort(SelectInfoFromMDMSOAP, SelectInfoFromMDM.class, features);
    }

}