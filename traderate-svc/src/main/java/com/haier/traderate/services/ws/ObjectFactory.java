
package com.haier.traderate.services.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ehaier.eop.review.stock.service.impl package. 
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

    private final static QName _SQMOrderShare_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "SQMOrderShare");
    private final static QName _SQMOrderShareResponse_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "SQMOrderShareResponse");
    private final static QName _EhaierOrderShare_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "ehaierOrderShare");
    private final static QName _EhaierOrderShareResponse_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "ehaierOrderShareResponse");
    private final static QName _GetHCSPHandleResultEhaier_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "getHCSPHandleResultEhaier");
    private final static QName _GetHCSPHandleResultEhaierResponse_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "getHCSPHandleResultEhaierResponse");
    private final static QName _GetVo_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "getVo");
    private final static QName _GetVoResponse_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "getVoResponse");
    private final static QName _RrsResult_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "rrsResult");
    private final static QName _RrsResultResponse_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "rrsResultResponse");
    private final static QName _TestWs_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "testWs");
    private final static QName _TestWsResponse_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "testWsResponse");
    private final static QName _UpQygResult_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "upQygResult");
    private final static QName _UpQygResultResponse_QNAME = new QName("http://demo.server.webservice.cc.neusoft.com/", "upQygResultResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ehaier.eop.review.stock.service.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SQMOrderShare }
     * 
     */
    public SQMOrderShare createSQMOrderShare() {
        return new SQMOrderShare();
    }

    /**
     * Create an instance of {@link SQMOrderShareResponse }
     * 
     */
    public SQMOrderShareResponse createSQMOrderShareResponse() {
        return new SQMOrderShareResponse();
    }

    /**
     * Create an instance of {@link EhaierOrderShare }
     * 
     */
    public EhaierOrderShare createEhaierOrderShare() {
        return new EhaierOrderShare();
    }

    /**
     * Create an instance of {@link EhaierOrderShareResponse }
     * 
     */
    public EhaierOrderShareResponse createEhaierOrderShareResponse() {
        return new EhaierOrderShareResponse();
    }

    /**
     * Create an instance of {@link GetHCSPHandleResultEhaier }
     * 
     */
    public GetHCSPHandleResultEhaier createGetHCSPHandleResultEhaier() {
        return new GetHCSPHandleResultEhaier();
    }

    /**
     * Create an instance of {@link GetHCSPHandleResultEhaierResponse }
     * 
     */
    public GetHCSPHandleResultEhaierResponse createGetHCSPHandleResultEhaierResponse() {
        return new GetHCSPHandleResultEhaierResponse();
    }

    /**
     * Create an instance of {@link GetVo }
     * 
     */
    public GetVo createGetVo() {
        return new GetVo();
    }

    /**
     * Create an instance of {@link GetVoResponse }
     * 
     */
    public GetVoResponse createGetVoResponse() {
        return new GetVoResponse();
    }

    /**
     * Create an instance of {@link RrsResult }
     * 
     */
    public RrsResult createRrsResult() {
        return new RrsResult();
    }

    /**
     * Create an instance of {@link RrsResultResponse }
     * 
     */
    public RrsResultResponse createRrsResultResponse() {
        return new RrsResultResponse();
    }

    /**
     * Create an instance of {@link TestWs }
     * 
     */
    public TestWs createTestWs() {
        return new TestWs();
    }

    /**
     * Create an instance of {@link TestWsResponse }
     * 
     */
    public TestWsResponse createTestWsResponse() {
        return new TestWsResponse();
    }

    /**
     * Create an instance of {@link UpQygResult }
     * 
     */
    public UpQygResult createUpQygResult() {
        return new UpQygResult();
    }

    /**
     * Create an instance of {@link UpQygResultResponse }
     * 
     */
    public UpQygResultResponse createUpQygResultResponse() {
        return new UpQygResultResponse();
    }

    /**
     * Create an instance of {@link EhaierOrder }
     * 
     */
    public EhaierOrder createEhaierOrder() {
        return new EhaierOrder();
    }

    /**
     * Create an instance of {@link QygOrder }
     * 
     */
    public QygOrder createQygOrder() {
        return new QygOrder();
    }

    /**
     * Create an instance of {@link SqmOrder }
     * 
     */
    public SqmOrder createSqmOrder() {
        return new SqmOrder();
    }

    /**
     * Create an instance of {@link DemoVo }
     * 
     */
    public DemoVo createDemoVo() {
        return new DemoVo();
    }

    /**
     * Create an instance of {@link RrsOrder }
     * 
     */
    public RrsOrder createRrsOrder() {
        return new RrsOrder();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SQMOrderShare }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "SQMOrderShare")
    public JAXBElement<SQMOrderShare> createSQMOrderShare(SQMOrderShare value) {
        return new JAXBElement<SQMOrderShare>(_SQMOrderShare_QNAME, SQMOrderShare.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SQMOrderShareResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "SQMOrderShareResponse")
    public JAXBElement<SQMOrderShareResponse> createSQMOrderShareResponse(SQMOrderShareResponse value) {
        return new JAXBElement<SQMOrderShareResponse>(_SQMOrderShareResponse_QNAME, SQMOrderShareResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EhaierOrderShare }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "ehaierOrderShare")
    public JAXBElement<EhaierOrderShare> createEhaierOrderShare(EhaierOrderShare value) {
        return new JAXBElement<EhaierOrderShare>(_EhaierOrderShare_QNAME, EhaierOrderShare.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EhaierOrderShareResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "ehaierOrderShareResponse")
    public JAXBElement<EhaierOrderShareResponse> createEhaierOrderShareResponse(EhaierOrderShareResponse value) {
        return new JAXBElement<EhaierOrderShareResponse>(_EhaierOrderShareResponse_QNAME, EhaierOrderShareResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHCSPHandleResultEhaier }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "getHCSPHandleResultEhaier")
    public JAXBElement<GetHCSPHandleResultEhaier> createGetHCSPHandleResultEhaier(GetHCSPHandleResultEhaier value) {
        return new JAXBElement<GetHCSPHandleResultEhaier>(_GetHCSPHandleResultEhaier_QNAME, GetHCSPHandleResultEhaier.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHCSPHandleResultEhaierResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "getHCSPHandleResultEhaierResponse")
    public JAXBElement<GetHCSPHandleResultEhaierResponse> createGetHCSPHandleResultEhaierResponse(GetHCSPHandleResultEhaierResponse value) {
        return new JAXBElement<GetHCSPHandleResultEhaierResponse>(_GetHCSPHandleResultEhaierResponse_QNAME, GetHCSPHandleResultEhaierResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "getVo")
    public JAXBElement<GetVo> createGetVo(GetVo value) {
        return new JAXBElement<GetVo>(_GetVo_QNAME, GetVo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "getVoResponse")
    public JAXBElement<GetVoResponse> createGetVoResponse(GetVoResponse value) {
        return new JAXBElement<GetVoResponse>(_GetVoResponse_QNAME, GetVoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RrsResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "rrsResult")
    public JAXBElement<RrsResult> createRrsResult(RrsResult value) {
        return new JAXBElement<RrsResult>(_RrsResult_QNAME, RrsResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RrsResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "rrsResultResponse")
    public JAXBElement<RrsResultResponse> createRrsResultResponse(RrsResultResponse value) {
        return new JAXBElement<RrsResultResponse>(_RrsResultResponse_QNAME, RrsResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestWs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "testWs")
    public JAXBElement<TestWs> createTestWs(TestWs value) {
        return new JAXBElement<TestWs>(_TestWs_QNAME, TestWs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestWsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "testWsResponse")
    public JAXBElement<TestWsResponse> createTestWsResponse(TestWsResponse value) {
        return new JAXBElement<TestWsResponse>(_TestWsResponse_QNAME, TestWsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpQygResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "upQygResult")
    public JAXBElement<UpQygResult> createUpQygResult(UpQygResult value) {
        return new JAXBElement<UpQygResult>(_UpQygResult_QNAME, UpQygResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpQygResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://demo.server.webservice.cc.neusoft.com/", name = "upQygResultResponse")
    public JAXBElement<UpQygResultResponse> createUpQygResultResponse(UpQygResultResponse value) {
        return new JAXBElement<UpQygResultResponse>(_UpQygResultResponse_QNAME, UpQygResultResponse.class, null, value);
    }

}
