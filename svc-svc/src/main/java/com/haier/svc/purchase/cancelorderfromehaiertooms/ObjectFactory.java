
package com.haier.svc.purchase.cancelorderfromehaiertooms;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.cbs.purchase.cancelorderfromehaiertooms package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.cbs.purchase.cancelorderfromehaiertooms
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CancelOrderFromEHaierToOMS_Type }
     * 
     */
    public CancelOrderFromEHaierToOMS_Type createCancelOrderFromEHaierToOMS_Type() {
        return new CancelOrderFromEHaierToOMS_Type();
    }

    /**
     * Create an instance of {@link RequestData }
     * 
     */
    public RequestData createRequestData() {
        return new RequestData();
    }

    /**
     * Create an instance of {@link CancelOrderFromEHaierToOMSResponse }
     * 
     */
    public CancelOrderFromEHaierToOMSResponse createCancelOrderFromEHaierToOMSResponse() {
        return new CancelOrderFromEHaierToOMSResponse();
    }

    /**
     * Create an instance of {@link ResponseData }
     * 
     */
    public ResponseData createResponseData() {
        return new ResponseData();
    }

}
