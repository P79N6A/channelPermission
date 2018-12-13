
package com.haier.stock.eai.queryProdDateFromEDW;


import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.example.queryproorderandsaleorderfromedw package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.example.queryproorderandsaleorderfromedw
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OutType }
     * 
     */
    public OutType createOutType() {
        return new OutType();
    }

    /**
     * Create an instance of {@link SelectITFMAKEPLANXIAXIANVOutput }
     * 
     */
    public SelectITFMAKEPLANXIAXIANVOutput createSelectITFMAKEPLANXIAXIANVOutput() {
        return new SelectITFMAKEPLANXIAXIANVOutput();
    }

    /**
     * Create an instance of {@link QueryProOrderAndSaleOrderFromEDW_Type }
     * 
     */
    public QueryProOrderAndSaleOrderFromEDW_Type createQueryProOrderAndSaleOrderFromEDW_Type() {
        return new QueryProOrderAndSaleOrderFromEDW_Type();
    }

    /**
     * Create an instance of {@link QueryProOrderAndSaleOrderFromEDWResponse }
     * 
     */
    public QueryProOrderAndSaleOrderFromEDWResponse createQueryProOrderAndSaleOrderFromEDWResponse() {
        return new QueryProOrderAndSaleOrderFromEDWResponse();
    }

}
