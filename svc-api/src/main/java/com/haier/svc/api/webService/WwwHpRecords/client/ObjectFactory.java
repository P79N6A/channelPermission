
package com.haier.svc.api.webService.WwwHpRecords.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.svc.api.webService.WwwHpRecords.client package. 
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

    private final static QName _XmlString_QNAME = new QName("http://ServiceImpl.WwwHpRecords.webService.api.svc.haier.com/", "xmlString");
    private final static QName _XmlStringResponse_QNAME = new QName("http://ServiceImpl.WwwHpRecords.webService.api.svc.haier.com/", "xmlStringResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.svc.api.webService.WwwHpRecords.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link XmlString }
     * 
     */
    public XmlString createXmlString() {
        return new XmlString();
    }

    /**
     * Create an instance of {@link XmlStringResponse }
     * 
     */
    public XmlStringResponse createXmlStringResponse() {
        return new XmlStringResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XmlString }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ServiceImpl.WwwHpRecords.webService.api.svc.haier.com/", name = "xmlString")
    public JAXBElement<XmlString> createXmlString(XmlString value) {
        return new JAXBElement<XmlString>(_XmlString_QNAME, XmlString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XmlStringResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ServiceImpl.WwwHpRecords.webService.api.svc.haier.com/", name = "xmlStringResponse")
    public JAXBElement<XmlStringResponse> createXmlStringResponse(XmlStringResponse value) {
        return new JAXBElement<XmlStringResponse>(_XmlStringResponse_QNAME, XmlStringResponse.class, null, value);
    }

}
