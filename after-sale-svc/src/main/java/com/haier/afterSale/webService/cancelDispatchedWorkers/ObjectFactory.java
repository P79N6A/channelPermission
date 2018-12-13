
package com.haier.afterSale.webService.cancelDispatchedWorkers;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.afterSale.webService.cancelDispatchedWorkers package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.afterSale.webService.cancelDispatchedWorkers
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Process }
     * 
     */
    public Process createProcess() {
        return new Process();
    }

    /**
     * Create an instance of {@link Inputs }
     * 
     */
    public Inputs createInputs() {
        return new Inputs();
    }

    /**
     * Create an instance of {@link ProcessResponse }
     * 
     */
    public ProcessResponse createProcessResponse() {
        return new ProcessResponse();
    }

    /**
     * Create an instance of {@link Outputs }
     * 
     */
    public Outputs createOutputs() {
        return new Outputs();
    }

}