
package com.haier.svc.purchase.transforecastpracticalfromb2ctooms;

import javax.xml.bind.annotation.XmlRegistry;

import com.haier.svc.bean.transforecastpracticalfromb2ctooms.OutType;
import com.haier.svc.bean.transforecastpracticalfromb2ctooms.TransForecastPracticalFromB2CToOMS_Type;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.cbs.purchase.transforecastpracticalfromb2ctooms package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.cbs.purchase.transforecastpracticalfromb2ctooms
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TransForecastPracticalFromB2CToOMSResponse }
     * 
     */
    public TransForecastPracticalFromB2CToOMSResponse createTransForecastPracticalFromB2CToOMSResponse() {
        return new TransForecastPracticalFromB2CToOMSResponse();
    }

    /**
     * Create an instance of {@link OutType }
     * 
     */
    public OutType createOutType() {
        return new OutType();
    }

    /**
     * Create an instance of {@link TransForecastPracticalFromB2CToOMS_Type }
     * 
     */
    public TransForecastPracticalFromB2CToOMS_Type createTransForecastPracticalFromB2CToOMS_Type() {
        return new TransForecastPracticalFromB2CToOMS_Type();
    }

}
