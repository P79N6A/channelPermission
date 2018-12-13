
package com.haier.svc.purchase.omst3;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.svc.purchase.omst3 package. 
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

    private final static QName _RequestData_QNAME = new QName("http://service.oms.haier.com/services/InterfaceOMSForecast", "requestData");
    private final static QName _GetForecastRecordReturn_QNAME = new QName("http://service.oms.haier.com/services/InterfaceOMSForecast", "getForecastRecordReturn");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.svc.purchase.omst3
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RequestData }
     * 
     */
    public RequestData createRequestData() {
        return new RequestData();
    }

    /**
     * Create an instance of {@link ResponseData }
     * 
     */
    public ResponseData createResponseData() {
        return new ResponseData();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.oms.haier.com/services/InterfaceOMSForecast", name = "requestData")
    public JAXBElement<RequestData> createRequestData(RequestData value) {
        return new JAXBElement<RequestData>(_RequestData_QNAME, RequestData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.oms.haier.com/services/InterfaceOMSForecast", name = "getForecastRecordReturn")
    public JAXBElement<ResponseData> createGetForecastRecordReturn(ResponseData value) {
        return new JAXBElement<ResponseData>(_GetForecastRecordReturn_QNAME, ResponseData.class, null, value);
    }

}
