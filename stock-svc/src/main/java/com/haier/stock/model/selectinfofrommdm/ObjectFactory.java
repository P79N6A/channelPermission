package com.haier.stock.model.selectinfofrommdm;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.example.selectinfofrommdm package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.example.selectinfofrommdm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SelectInfoFromMDMOP }
     * 
     */
    public SelectInfoFromMDMOP createSelectInfoFromMDMOP() {
        return new SelectInfoFromMDMOP();
    }

    /**
     * Create an instance of {@link SelectInfoFromMDMOPResponse }
     * 
     */
    public SelectInfoFromMDMOPResponse createSelectInfoFromMDMOPResponse() {
        return new SelectInfoFromMDMOPResponse();
    }

    /**
     * Create an instance of {@link SelectInfoFromMDMOPResponse.Output }
     * 
     */
    public SelectInfoFromMDMOPResponse.Output createSelectInfoFromMDMOPResponseOutput() {
        return new SelectInfoFromMDMOPResponse.Output();
    }

    /**
     * Create an instance of {@link InType }
     * 
     */
    public InType createInType() {
        return new InType();
    }

}
