package com.haier.svc.purchase.crmmanual;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.0.1
 * 2014-08-08T16:31:37.997+08:00
 * Generated source version: 3.0.1
 * 
 */
@WebServiceClient(name = "InsertWAOrderBillFromEhaierToCRM", 
                  wsdlLocation = "file:/D:/work/source/purchase-service/purchase-impl/src/main/resources/wsdl/InsertWAOrderBillFromEhaierToCRM.wsdl",
                  targetNamespace = "http://www.example.org/InsertWAOrderBillFromEhaierToCRM/") 
public class InsertWAOrderBillFromEhaierToCRM_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.example.org/InsertWAOrderBillFromEhaierToCRM/", "InsertWAOrderBillFromEhaierToCRM");
    public final static QName InsertWAOrderBillFromEhaierToCRMSOAP = new QName("http://www.example.org/InsertWAOrderBillFromEhaierToCRM/", "InsertWAOrderBillFromEhaierToCRMSOAP");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/work/source/purchase-service/purchase-impl/src/main/resources/wsdl/InsertWAOrderBillFromEhaierToCRM.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(InsertWAOrderBillFromEhaierToCRM_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/D:/work/source/purchase-service/purchase-impl/src/main/resources/wsdl/InsertWAOrderBillFromEhaierToCRM.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public InsertWAOrderBillFromEhaierToCRM_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public InsertWAOrderBillFromEhaierToCRM_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public InsertWAOrderBillFromEhaierToCRM_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns InsertWAOrderBillFromEhaierToCRM
     */
    @WebEndpoint(name = "InsertWAOrderBillFromEhaierToCRMSOAP")
    public InsertWAOrderBillFromEhaierToCRM getInsertWAOrderBillFromEhaierToCRMSOAP() {
        return super.getPort(InsertWAOrderBillFromEhaierToCRMSOAP, InsertWAOrderBillFromEhaierToCRM.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns InsertWAOrderBillFromEhaierToCRM
     */
    @WebEndpoint(name = "InsertWAOrderBillFromEhaierToCRMSOAP")
    public InsertWAOrderBillFromEhaierToCRM getInsertWAOrderBillFromEhaierToCRMSOAP(WebServiceFeature... features) {
        return super.getPort(InsertWAOrderBillFromEhaierToCRMSOAP, InsertWAOrderBillFromEhaierToCRM.class, features);
    }

}
