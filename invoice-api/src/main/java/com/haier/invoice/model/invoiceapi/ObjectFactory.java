
package com.haier.invoice.model.invoiceapi;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import java.lang.Exception;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.cbs.base.eai.invoice.invoiceapi package. 
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

    private final static QName _ChAsyncResponse_QNAME = new QName("http://v20.api.igs.einv.ruihong.com/", "ch_asyncResponse");
    private final static QName _ChResponse_QNAME = new QName("http://v20.api.igs.einv.ruihong.com/", "chResponse");
    private final static QName _KpAsyncResponse_QNAME = new QName("http://v20.api.igs.einv.ruihong.com/", "kp_asyncResponse");
    private final static QName _Exception_QNAME = new QName("http://v20.api.igs.einv.ruihong.com/", "Exception");
    private final static QName _CxResponse_QNAME = new QName("http://v20.api.igs.einv.ruihong.com/", "cxResponse");
    private final static QName _KpAsync_QNAME = new QName("http://v20.api.igs.einv.ruihong.com/", "kp_async");
    private final static QName _Cx_QNAME = new QName("http://v20.api.igs.einv.ruihong.com/", "cx");
    private final static QName _KpResponse_QNAME = new QName("http://v20.api.igs.einv.ruihong.com/", "kpResponse");
    private final static QName _Ch_QNAME = new QName("http://v20.api.igs.einv.ruihong.com/", "ch");
    private final static QName _ChAsync_QNAME = new QName("http://v20.api.igs.einv.ruihong.com/", "ch_async");
    private final static QName _Kp_QNAME = new QName("http://v20.api.igs.einv.ruihong.com/", "kp");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.cbs.base.eai.invoice.invoiceapi
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link KpAsyncResponse }
     * 
     */
    public KpAsyncResponse createKpAsyncResponse() {
        return new KpAsyncResponse();
    }

    /**
     * Create an instance of {@link Exception }
     *
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link ChAsyncResponse }
     *
     */
    public ChAsyncResponse createChAsyncResponse() {
        return new ChAsyncResponse();
    }

    /**
     * Create an instance of {@link ChResponse }
     *
     */
    public ChResponse createChResponse() {
        return new ChResponse();
    }

    /**
     * Create an instance of {@link Kp }
     *
     */
    public Kp createKp() {
        return new Kp();
    }

    /**
     * Create an instance of {@link KpResponse }
     *
     */
    public KpResponse createKpResponse() {
        return new KpResponse();
    }

    /**
     * Create an instance of {@link Cx }
     *
     */
    public Cx createCx() {
        return new Cx();
    }

    /**
     * Create an instance of {@link ChAsync }
     *
     */
    public ChAsync createChAsync() {
        return new ChAsync();
    }

    /**
     * Create an instance of {@link Ch }
     *
     */
    public Ch createCh() {
        return new Ch();
    }

    /**
     * Create an instance of {@link KpAsync }
     *
     */
    public KpAsync createKpAsync() {
        return new KpAsync();
    }

    /**
     * Create an instance of {@link CxResponse }
     *
     */
    public CxResponse createCxResponse() {
        return new CxResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChAsyncResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://v20.api.igs.einv.ruihong.com/", name = "ch_asyncResponse")
    public JAXBElement<ChAsyncResponse> createChAsyncResponse(ChAsyncResponse value) {
        return new JAXBElement<ChAsyncResponse>(_ChAsyncResponse_QNAME, ChAsyncResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://v20.api.igs.einv.ruihong.com/", name = "chResponse")
    public JAXBElement<ChResponse> createChResponse(ChResponse value) {
        return new JAXBElement<ChResponse>(_ChResponse_QNAME, ChResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KpAsyncResponse }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://v20.api.igs.einv.ruihong.com/", name = "kp_asyncResponse")
    public JAXBElement<KpAsyncResponse> createKpAsyncResponse(KpAsyncResponse value) {
        return new JAXBElement<KpAsyncResponse>(_KpAsyncResponse_QNAME, KpAsyncResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://v20.api.igs.einv.ruihong.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CxResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://v20.api.igs.einv.ruihong.com/", name = "cxResponse")
    public JAXBElement<CxResponse> createCxResponse(CxResponse value) {
        return new JAXBElement<CxResponse>(_CxResponse_QNAME, CxResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KpAsync }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://v20.api.igs.einv.ruihong.com/", name = "kp_async")
    public JAXBElement<KpAsync> createKpAsync(KpAsync value) {
        return new JAXBElement<KpAsync>(_KpAsync_QNAME, KpAsync.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Cx }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://v20.api.igs.einv.ruihong.com/", name = "cx")
    public JAXBElement<Cx> createCx(Cx value) {
        return new JAXBElement<Cx>(_Cx_QNAME, Cx.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://v20.api.igs.einv.ruihong.com/", name = "kpResponse")
    public JAXBElement<KpResponse> createKpResponse(KpResponse value) {
        return new JAXBElement<KpResponse>(_KpResponse_QNAME, KpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Ch }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://v20.api.igs.einv.ruihong.com/", name = "ch")
    public JAXBElement<Ch> createCh(Ch value) {
        return new JAXBElement<Ch>(_Ch_QNAME, Ch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChAsync }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://v20.api.igs.einv.ruihong.com/", name = "ch_async")
    public JAXBElement<ChAsync> createChAsync(ChAsync value) {
        return new JAXBElement<ChAsync>(_ChAsync_QNAME, ChAsync.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Kp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://v20.api.igs.einv.ruihong.com/", name = "kp")
    public JAXBElement<Kp> createKp(Kp value) {
        return new JAXBElement<Kp>(_Kp_QNAME, Kp.class, null, value);
    }

}
