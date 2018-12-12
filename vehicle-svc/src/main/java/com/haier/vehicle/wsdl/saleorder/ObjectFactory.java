
package com.haier.vehicle.wsdl.saleorder;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.cbs.vehicle.webservice.saleorder package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MonitorInformation_QNAME = new QName("http://www.example.org/eaiMonitor", "monitorInformation");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.cbs.vehicle.webservice.saleorder
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InsertSaleBillGVS2ProcessRequest }
     * 
     */
    public InsertSaleBillGVS2ProcessRequest createInsertSaleBillGVS2ProcessRequest() {
        return new InsertSaleBillGVS2ProcessRequest();
    }

    /**
     * Create an instance of {@link MasterType }
     * 
     */
    public MasterType createMasterType() {
        return new MasterType();
    }

    /**
     * Create an instance of {@link DetailType }
     * 
     */
    public DetailType createDetailType() {
        return new DetailType();
    }

    /**
     * Create an instance of {@link InsertSaleBillGVS2ProcessResponse }
     * 
     */
    public InsertSaleBillGVS2ProcessResponse createInsertSaleBillGVS2ProcessResponse() {
        return new InsertSaleBillGVS2ProcessResponse();
    }

    /**
     * Create an instance of {@link OutputType }
     * 
     */
    public OutputType createOutputType() {
        return new OutputType();
    }

    /**
     * Create an instance of {@link EaiMonitorInfo }
     * 
     */
    public EaiMonitorInfo createEaiMonitorInfo() {
        return new EaiMonitorInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EaiMonitorInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/eaiMonitor", name = "monitorInformation")
    public JAXBElement<EaiMonitorInfo> createMonitorInformation(EaiMonitorInfo value) {
        return new JAXBElement<EaiMonitorInfo>(_MonitorInformation_QNAME, EaiMonitorInfo.class, null, value);
    }

}
