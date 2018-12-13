package com.haier.svc.services;

import com.haier.svc.purchase.products.GetMTLInfoMDM2SHCMine;
import com.haier.svc.purchase.products.GetMTLInfoMDM2SHCMine_Service;
import com.haier.svc.purchase.products.OutType;
import com.haier.svc.service.GetMtlInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/12 14:11
 */
@Service
public class GetMtlInfoServiceImpl implements GetMtlInfoService{
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(GetMtlInfoServiceImpl.class);
    private static final String error = "error";
    @Value("${getMtlInfoResponse.location}")
    private String wsdlLocation;

    @Override
    public String getConTaxCode(String materialCode) {
        try {
            URL url = this.getClass().getResource(
                    wsdlLocation
                            + "/GetMTLInfoMDM2SHC_MINE.wsdl");
            GetMTLInfoMDM2SHCMine_Service service = new GetMTLInfoMDM2SHCMine_Service(url);
            GetMTLInfoMDM2SHCMine soap = service.getGetMTLInfoMDM2SHCMineSOAP();
            OutType outType = soap.getMTLInfoMDM2SHCMine(materialCode);
            if(outType == null){
                return error;
            }else{
                return outType.getConTaxCode();
            }
        } catch (Exception e) {
            log.error("[GetMtlInfoServiceImpl][getConTaxCode]异常，e:"+e.getMessage());
        }
        return null;
    }
}
