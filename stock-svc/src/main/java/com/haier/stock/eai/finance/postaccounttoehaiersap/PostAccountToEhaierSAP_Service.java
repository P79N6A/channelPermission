/*
 * 
 */

package com.haier.stock.eai.finance.postaccounttoehaiersap;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 2.2.3
 * Thu Aug 22 09:29:01 CST 2013
 * Generated source version: 2.2.3
 * 
 */

@WebServiceClient(name = "PostAccountToEhaierSAP", wsdlLocation = "file:/F:/My_Job/Hair_Job/workspace/wsdl-creator/wsdl-creator/src/main/resources/PostAccountToEhaierSAP.wsdl", targetNamespace = "http://www.example.org/PostAccountToEhaierSAP/")
public class PostAccountToEhaierSAP_Service extends Service {

    public final static URL   WSDL_LOCATION;
    public final static QName SERVICE                    = new QName(
                                                             "http://www.example.org/PostAccountToEhaierSAP/",
                                                             "PostAccountToEhaierSAP");
    public final static QName PostAccountToEhaierSAPSOAP = new QName(
                                                             "http://www.example.org/PostAccountToEhaierSAP/",
                                                             "PostAccountToEhaierSAPSOAP");
    static {
        URL url = null;
        try {
            url = new URL(
                "file:/F:/My_Job/Hair_Job/workspace/wsdl-creator/wsdl-creator/src/main/resources/PostAccountToEhaierSAP.wsdl");
        } catch (MalformedURLException e) {
            System.err
                .println("Can not initialize the default wsdl from file:/F:/My_Job/Hair_Job/workspace/wsdl-creator/wsdl-creator/src/main/resources/PostAccountToEhaierSAP.wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public PostAccountToEhaierSAP_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public PostAccountToEhaierSAP_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PostAccountToEhaierSAP_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns PostAccountToEhaierSAP
     */
    @WebEndpoint(name = "PostAccountToEhaierSAPSOAP")
    public PostAccountToEhaierSAP getPostAccountToEhaierSAPSOAP() {
        return super.getPort(PostAccountToEhaierSAPSOAP, PostAccountToEhaierSAP.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PostAccountToEhaierSAP
     */
    @WebEndpoint(name = "PostAccountToEhaierSAPSOAP")
    public PostAccountToEhaierSAP getPostAccountToEhaierSAPSOAP(WebServiceFeature... features) {
        return super.getPort(PostAccountToEhaierSAPSOAP, PostAccountToEhaierSAP.class, features);
    }

}