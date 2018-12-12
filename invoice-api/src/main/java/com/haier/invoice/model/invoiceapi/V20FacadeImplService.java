package com.haier.invoice.model.invoiceapi;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by Apache CXF 2.7.6
 * 2015-12-18T15:59:25.730+08:00
 * Generated source version: 2.7.6
 * 
 */
@WebServiceClient(name = "V20FacadeImplService",
                  wsdlLocation = "http://www.chinaeinv.com:980/igs/ws/invoiceApi?wsdl",
                  targetNamespace = "http://v20.api.igs.einv.ruihong.com/") 
public class V20FacadeImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://v20.api.igs.einv.ruihong.com/", "V20FacadeImplService");
    public final static QName InboundWebServicesTicketSaServiceSoap = new QName("http://v20.api.igs.einv.ruihong.com/", "inbound.webServices.ticket.saServiceSoap");
    static {
        URL url = null;
        try {
            url = new URL("http://www.chinaeinv.com:980/igs/ws/invoiceApi?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(V20FacadeImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://www.chinaeinv.com:980/igs/ws/invoiceApi?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public V20FacadeImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public V20FacadeImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public V20FacadeImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns V20Facade
     */
    @WebEndpoint(name = "inbound.webServices.ticket.saServiceSoap")
    public V20Facade getInboundWebServicesTicketSaServiceSoap() {
        return super.getPort(InboundWebServicesTicketSaServiceSoap, V20Facade.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns V20Facade
     */
    @WebEndpoint(name = "inbound.webServices.ticket.saServiceSoap")
    public V20Facade getInboundWebServicesTicketSaServiceSoap(WebServiceFeature... features) {
        return super.getPort(InboundWebServicesTicketSaServiceSoap, V20Facade.class, features);
    }

}
