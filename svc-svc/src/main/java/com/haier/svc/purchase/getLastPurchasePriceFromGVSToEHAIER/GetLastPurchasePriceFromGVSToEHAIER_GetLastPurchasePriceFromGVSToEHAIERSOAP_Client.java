
package com.haier.svc.purchase.getLastPurchasePriceFromGVSToEHAIER;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 2.7.18
 * 2017-04-25T18:05:26.445+08:00
 * Generated source version: 2.7.18
 * 
 */
public final class GetLastPurchasePriceFromGVSToEHAIER_GetLastPurchasePriceFromGVSToEHAIERSOAP_Client {

    private static final QName SERVICE_NAME = new QName("http://www.example.org/GetLastPurchasePriceFromGVSToEHAIER/", "GetLastPurchasePriceFromGVSToEHAIER");

    private GetLastPurchasePriceFromGVSToEHAIER_GetLastPurchasePriceFromGVSToEHAIERSOAP_Client() {
    }

    public static void main(String args[]) throws Exception {
        URL wsdlURL = GetLastPurchasePriceFromGVSToEHAIER_Service.WSDL_LOCATION;
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
      
        GetLastPurchasePriceFromGVSToEHAIER_Service ss = new GetLastPurchasePriceFromGVSToEHAIER_Service(wsdlURL, SERVICE_NAME);
        GetLastPurchasePriceFromGVSToEHAIER port = ss.getGetLastPurchasePriceFromGVSToEHAIERSOAP();  
        
        {
        //System.out.println("Invoking getLastPurchasePriceFromGVSToEHAIER...");
        java.util.List<InType> _getLastPurchasePriceFromGVSToEHAIER_in = null;
        String _getLastPurchasePriceFromGVSToEHAIER_sysName = "";
        java.util.List<OutType> _getLastPurchasePriceFromGVSToEHAIER__return = port.getLastPurchasePriceFromGVSToEHAIER(_getLastPurchasePriceFromGVSToEHAIER_in, _getLastPurchasePriceFromGVSToEHAIER_sysName);
        //System.out.println("getLastPurchasePriceFromGVSToEHAIER.result=" + _getLastPurchasePriceFromGVSToEHAIER__return);


        }

        System.exit(0);
    }

}
