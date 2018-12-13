package com.haier.stock.eai.finance.plpurchase.push;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 2.7.11
 * 2014-11-26T10:09:54.303+08:00
 * Generated source version: 2.7.11
 * 
 */
@WebServiceClient(name = "Trans3PLReceiveInfoFromEhaierToGVS", 
                  wsdlLocation = "file:/D:/idea-workspace/haier-cbs/wsdl-creator/src/main/resources/Trans3PLReceiveInfoFromEhaierToGVS.wsdl",
                  targetNamespace = "http://www.example.org/Trans3PLReceiveInfoFromEhaierToGVS/") 
public class Trans3PLReceiveInfoFromEhaierToGVS_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.example.org/Trans3PLReceiveInfoFromEhaierToGVS/", "Trans3PLReceiveInfoFromEhaierToGVS");
    public final static QName Trans3PLReceiveInfoFromEhaierToGVSSOAP = new QName("http://www.example.org/Trans3PLReceiveInfoFromEhaierToGVS/", "Trans3PLReceiveInfoFromEhaierToGVSSOAP");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/idea-workspace/haier-cbs/wsdl-creator/src/main/resources/Trans3PLReceiveInfoFromEhaierToGVS.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(Trans3PLReceiveInfoFromEhaierToGVS_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/D:/idea-workspace/haier-cbs/wsdl-creator/src/main/resources/Trans3PLReceiveInfoFromEhaierToGVS.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public Trans3PLReceiveInfoFromEhaierToGVS_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public Trans3PLReceiveInfoFromEhaierToGVS_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Trans3PLReceiveInfoFromEhaierToGVS_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns Trans3PLReceiveInfoFromEhaierToGVS
     */
    @WebEndpoint(name = "Trans3PLReceiveInfoFromEhaierToGVSSOAP")
    public Trans3PLReceiveInfoFromEhaierToGVS getTrans3PLReceiveInfoFromEhaierToGVSSOAP() {
        return super.getPort(Trans3PLReceiveInfoFromEhaierToGVSSOAP, Trans3PLReceiveInfoFromEhaierToGVS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Trans3PLReceiveInfoFromEhaierToGVS
     */
    @WebEndpoint(name = "Trans3PLReceiveInfoFromEhaierToGVSSOAP")
    public Trans3PLReceiveInfoFromEhaierToGVS getTrans3PLReceiveInfoFromEhaierToGVSSOAP(WebServiceFeature... features) {
        return super.getPort(Trans3PLReceiveInfoFromEhaierToGVSSOAP, Trans3PLReceiveInfoFromEhaierToGVS.class, features);
    }

}
