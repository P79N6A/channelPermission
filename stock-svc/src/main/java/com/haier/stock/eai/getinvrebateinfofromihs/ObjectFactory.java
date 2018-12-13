package com.haier.stock.eai.getinvrebateinfofromihs;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.example.getinvrebateinfofromihs package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.example.getinvrebateinfofromihs
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OutputType }
     * 
     */
    public OutputType createOutputType() {
        return new OutputType();
    }

    /**
     * Create an instance of {@link InputType }
     * 
     */
    public InputType createInputType() {
        return new InputType();
    }

    /**
     * Create an instance of {@link GetInvRebateInfoFromIHS_Type }
     * 
     */
    public GetInvRebateInfoFromIHS_Type createGetInvRebateInfoFromIHS_Type() {
        return new GetInvRebateInfoFromIHS_Type();
    }

    /**
     * Create an instance of {@link GetInvRebateInfoFromIHSResponse }
     * 
     */
    public GetInvRebateInfoFromIHSResponse createGetInvRebateInfoFromIHSResponse() {
        return new GetInvRebateInfoFromIHSResponse();
    }

    /**
     * Create an instance of {@link DetailType }
     * 
     */
    public DetailType createDetailType() {
        return new DetailType();
    }

}