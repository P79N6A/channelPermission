package com.haier.svc.api.webService.WwwHpRecords.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Created by zhangbo on 2017/11/20.
 */
@WebService
public interface CommonService {
    @WebMethod
    public String xmlString(String xmlString);
}
