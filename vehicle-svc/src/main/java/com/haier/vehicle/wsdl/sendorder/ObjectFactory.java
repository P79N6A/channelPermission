
package com.haier.vehicle.wsdl.sendorder;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.cbs.vehicle.webservice.sendorder package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.cbs.vehicle.webservice.sendorder
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InsertOrderBillOMS2ProcessRequest }
     * 
     */
    public InsertOrderBillOMS2ProcessRequest createInsertOrderBillOMS2ProcessRequest() {
        return new InsertOrderBillOMS2ProcessRequest();
    }

    /**
     * Create an instance of {@link InputType }
     * 
     */
    public InputType createInputType() {
        return new InputType();
    }

    /**
     * Create an instance of {@link InsertOrderBillOMS2ProcessResponse }
     * 
     */
    public InsertOrderBillOMS2ProcessResponse createInsertOrderBillOMS2ProcessResponse() {
        return new InsertOrderBillOMS2ProcessResponse();
    }

    /**
     * Create an instance of {@link OutPutType }
     * 
     */
    public OutPutType createOutPutType() {
        return new OutPutType();
    }

}
