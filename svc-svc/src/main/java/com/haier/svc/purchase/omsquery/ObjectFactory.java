
package com.haier.svc.purchase.omsquery;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.svc.purchase.omsquery package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.svc.purchase.omsquery
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryOrderFromEHaierToOMS_Type }
     * 
     */
    public QueryOrderFromEHaierToOMS_Type createQueryOrderFromEHaierToOMS_Type() {
        return new QueryOrderFromEHaierToOMS_Type();
    }

    /**
     * Create an instance of {@link RequestData }
     * 
     */
    public RequestData createRequestData() {
        return new RequestData();
    }

    /**
     * Create an instance of {@link QueryOrderFromEHaierToOMSResponse }
     * 
     */
    public QueryOrderFromEHaierToOMSResponse createQueryOrderFromEHaierToOMSResponse() {
        return new QueryOrderFromEHaierToOMSResponse();
    }

    /**
     * Create an instance of {@link ResponseData }
     * 
     */
    public ResponseData createResponseData() {
        return new ResponseData();
    }

}
