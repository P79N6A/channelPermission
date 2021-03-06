
package com.haier.vehicle.wsdl.saleorder;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.1.12
 * 2017-09-08T14:42:07.329+08:00
 * Generated source version: 3.1.12
 */

@WebFault(name = "InsertSaleBill_GVS2ProcessResponse", targetNamespace = "http://xmlns.oracle.com/Application/InsertSaleBill_ZKToCRM/BPELProcess")
public class RemoteExceptionMessage extends Exception {
    
    private InsertSaleBillGVS2ProcessResponse insertSaleBillGVS2ProcessResponse;

    public RemoteExceptionMessage() {
        super();
    }
    
    public RemoteExceptionMessage(String message) {
        super(message);
    }
    
    public RemoteExceptionMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteExceptionMessage(String message, InsertSaleBillGVS2ProcessResponse insertSaleBillGVS2ProcessResponse) {
        super(message);
        this.insertSaleBillGVS2ProcessResponse = insertSaleBillGVS2ProcessResponse;
    }

    public RemoteExceptionMessage(String message, InsertSaleBillGVS2ProcessResponse insertSaleBillGVS2ProcessResponse, Throwable cause) {
        super(message, cause);
        this.insertSaleBillGVS2ProcessResponse = insertSaleBillGVS2ProcessResponse;
    }

    public InsertSaleBillGVS2ProcessResponse getFaultInfo() {
        return this.insertSaleBillGVS2ProcessResponse;
    }
}
