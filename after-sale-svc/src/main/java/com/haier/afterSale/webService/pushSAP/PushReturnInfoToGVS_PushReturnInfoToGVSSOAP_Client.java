
package com.haier.afterSale.webService.pushSAP;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2018-03-12T17:28:34.387+08:00
 * Generated source version: 3.1.12
 * 
 */
public final class PushReturnInfoToGVS_PushReturnInfoToGVSSOAP_Client {

    private static final QName SERVICE_NAME = new QName("http://www.example.org/PushReturnInfoToGVS/", "PushReturnInfoToGVS");

    private PushReturnInfoToGVS_PushReturnInfoToGVSSOAP_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = PushReturnInfoToGVS_Service.WSDL_LOCATION;
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
      
        PushReturnInfoToGVS_Service ss = new PushReturnInfoToGVS_Service(wsdlURL, SERVICE_NAME);
        PushReturnInfoToGVS port = ss.getPushReturnInfoToGVSSOAP();  
        
        {
        System.out.println("Invoking pushReturnInfoToGVS...");
        java.util.List<InType> _pushReturnInfoToGVS_in = null;
        OutType _pushReturnInfoToGVS__return = port.pushReturnInfoToGVS(_pushReturnInfoToGVS_in);
        System.out.println("pushReturnInfoToGVS.result=" + _pushReturnInfoToGVS__return);


        }

        System.exit(0);
    }

}