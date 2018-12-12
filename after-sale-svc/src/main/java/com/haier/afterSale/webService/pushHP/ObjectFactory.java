
package com.haier.afterSale.webService.pushHP;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.svc.api.webService.text package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.svc.api.webService.text
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InsertDataToHP_Type }
     * 
     */
    public InsertDataToHP_Type createInsertDataToHP_Type() {
        return new InsertDataToHP_Type();
    }

    /**
     * Create an instance of {@link InsertDataToHP_Type.Inputs }
     * 
     */
    public InsertDataToHP_Type.Inputs createInsertDataToHP_TypeInputs() {
        return new InsertDataToHP_Type.Inputs();
    }

    /**
     * Create an instance of {@link InsertDataToHPResponse }
     * 
     */
    public InsertDataToHPResponse createInsertDataToHPResponse() {
        return new InsertDataToHPResponse();
    }

}
