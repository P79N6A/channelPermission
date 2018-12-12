
package com.haier.vehicle.wsdl.sendorder;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2017-12-12T16:52:18.606+08:00
 * Generated source version: 3.1.12
 * 
 */
public final class InsertOrderBillZKToOMS_InsertOrderBillZKToOMSPt_Client {

    private static final QName SERVICE_NAME = new QName("http://xmlns.oracle.com/Application/InsertOrderBill_ZKToOMS/InsertOrderBill_ZKToOMS", "insertorderbill_zktooms_client_ep");

    private InsertOrderBillZKToOMS_InsertOrderBillZKToOMSPt_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = InsertorderbillZktoomsClientEp.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        InsertorderbillZktoomsClientEp ss = new InsertorderbillZktoomsClientEp(wsdlURL, SERVICE_NAME);
        InsertOrderBillZKToOMS port = ss.getInsertOrderBillZKToOMSPt();  
        
        {
        System.out.println("Invoking process...");
        com.haier.vehicle.wsdl.sendorder.InsertOrderBillOMS2ProcessRequest _process_payload = null;
        try {
            com.haier.vehicle.wsdl.sendorder.InsertOrderBillOMS2ProcessResponse _process__return = port.process(_process_payload);
            System.out.println("process.result=" + _process__return);

        } catch (RemoteExceptionMessage e) { 
            System.out.println("Expected exception: RemoteExceptionMessage has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
