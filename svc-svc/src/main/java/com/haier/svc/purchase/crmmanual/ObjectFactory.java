
package com.haier.svc.purchase.crmmanual;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.cbs.purchase.crmmanual package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.cbs.purchase.crmmanual
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link InsertWAOrderBillFromEhaierToCRM_Type }
     * 
     */
    public InsertWAOrderBillFromEhaierToCRM_Type createInsertWAOrderBillFromEhaierToCRM_Type() {
        return new InsertWAOrderBillFromEhaierToCRM_Type();
    }

    /**
     * Create an instance of {@link MasterType }
     * 
     */
    public MasterType createMasterType() {
        return new MasterType();
    }

    /**
     * Create an instance of {@link DetailType }
     * 
     */
    public DetailType createDetailType() {
        return new DetailType();
    }

    /**
     * Create an instance of {@link InsertWAOrderBillFromEhaierToCRMResponse }
     * 
     */
    public InsertWAOrderBillFromEhaierToCRMResponse createInsertWAOrderBillFromEhaierToCRMResponse() {
        return new InsertWAOrderBillFromEhaierToCRMResponse();
    }

}
