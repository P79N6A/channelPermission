
package com.haier.stock.eai.finance.pushReturnInfoToGVS;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.example.pushreturninfotogvs package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.example.pushreturninfotogvs
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InType }
     * 
     */
    public InType createInType() {
        return new InType();
    }

    /**
     * Create an instance of {@link PushReturnInfoToGVSResponse }
     * 
     */
    public PushReturnInfoToGVSResponse createPushReturnInfoToGVSResponse() {
        return new PushReturnInfoToGVSResponse();
    }

    /**
     * Create an instance of {@link PushReturnInfoToGVS_Type }
     * 
     */
    public PushReturnInfoToGVS_Type createPushReturnInfoToGVS_Type() {
        return new PushReturnInfoToGVS_Type();
    }

    /**
     * Create an instance of {@link OutType }
     * 
     */
    public OutType createOutType() {
        return new OutType();
    }

    /**
     * Create an instance of {@link TMSGType }
     * 
     */
    public TMSGType createTMSGType() {
        return new TMSGType();
    }

}
