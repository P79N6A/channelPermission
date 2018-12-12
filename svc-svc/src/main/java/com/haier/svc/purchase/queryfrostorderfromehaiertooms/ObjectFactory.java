
package com.haier.svc.purchase.queryfrostorderfromehaiertooms;

import javax.xml.bind.annotation.XmlRegistry;

import com.haier.svc.bean.queryfrostorderfromehaiertooms.InType;
import com.haier.svc.bean.queryfrostorderfromehaiertooms.OutType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.cbs.purchase.queryfrostorderfromehaiertooms package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.cbs.purchase.queryfrostorderfromehaiertooms
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryFrostOrderFromEHAIERToOMSResponse }
     * 
     */
    public QueryFrostOrderFromEHAIERToOMSResponse createQueryFrostOrderFromEHAIERToOMSResponse() {
        return new QueryFrostOrderFromEHAIERToOMSResponse();
    }

    /**
     * Create an instance of {@link OutType }
     * 
     */
    public OutType createOutType() {
        return new OutType();
    }

    /**
     * Create an instance of {@link QueryFrostOrderFromEHAIERToOMS_Type }
     * 
     */
    public QueryFrostOrderFromEHAIERToOMS_Type createQueryFrostOrderFromEHAIERToOMS_Type() {
        return new QueryFrostOrderFromEHAIERToOMS_Type();
    }

    /**
     * Create an instance of {@link InType }
     * 
     */
    public InType createInType() {
        return new InType();
    }

}
