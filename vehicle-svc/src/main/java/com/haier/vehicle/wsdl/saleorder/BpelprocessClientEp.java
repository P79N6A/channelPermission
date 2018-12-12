package com.haier.vehicle.wsdl.saleorder;

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
 * This class was generated by Apache CXF 3.1.12
 * 2017-09-08T14:42:07.352+08:00
 * Generated source version: 3.1.12
 * 
 */
@WebServiceClient(name = "bpelprocess_client_ep", 
                  wsdlLocation = "file:/D:/java/InsertSaleBill_ZKToCRM.wsdl",
                  targetNamespace = "http://xmlns.oracle.com/Application/InsertSaleBill_ZKToCRM/BPELProcess") 
public class BpelprocessClientEp extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://xmlns.oracle.com/Application/InsertSaleBill_ZKToCRM/BPELProcess", "bpelprocess_client_ep");
    public final static QName BPELProcessPt = new QName("http://xmlns.oracle.com/Application/InsertSaleBill_ZKToCRM/BPELProcess", "BPELProcess_pt");
    static {
        URL url = null;
        try {
            url = new URL("file:/D:/java/InsertSaleBill_ZKToCRM.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(BpelprocessClientEp.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/D:/java/InsertSaleBill_ZKToCRM.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public BpelprocessClientEp(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public BpelprocessClientEp(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BpelprocessClientEp() {
        super(WSDL_LOCATION, SERVICE);
    }
    




    /**
     *
     * @return
     *     returns BPELProcess
     */
    @WebEndpoint(name = "BPELProcess_pt")
    public BPELProcess getBPELProcessPt() {
        return super.getPort(BPELProcessPt, BPELProcess.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns BPELProcess
     */
    @WebEndpoint(name = "BPELProcess_pt")
    public BPELProcess getBPELProcessPt(WebServiceFeature... features) {
        return super.getPort(BPELProcessPt, BPELProcess.class, features);
    }

}