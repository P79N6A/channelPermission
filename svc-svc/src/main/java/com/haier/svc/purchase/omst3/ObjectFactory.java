
package com.haier.svc.purchase.omst3;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.svc.omst3 package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.svc.omst3
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TransForecastInfoFromOMS_Type }
     * 
     */
    public TransForecastInfoFromOMS_Type createTransForecastInfoFromOMS_Type() {
        return new TransForecastInfoFromOMS_Type();
    }

    /**
     * Create an instance of {@link RequestData }
     * 
     */
    public RequestData createRequestData() {
        return new RequestData();
    }

    /**
     * Create an instance of {@link TransForecastInfoFromOMSResponse }
     * 
     */
    public TransForecastInfoFromOMSResponse createTransForecastInfoFromOMSResponse() {
        return new TransForecastInfoFromOMSResponse();
    }

    /**
     * Create an instance of {@link ResponseData }
     * 
     */
    public ResponseData createResponseData() {
        return new ResponseData();
    }

}
