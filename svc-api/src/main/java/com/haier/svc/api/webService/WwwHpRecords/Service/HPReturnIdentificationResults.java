package com.haier.svc.api.webService.WwwHpRecords.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.haier.shop.model.OrderRepairshpLogs;
/**
 * HP回传信息（已开箱正品 为开箱正品）
 * @author wukunyang
 *
 */
@WebService
public interface HPReturnIdentificationResults {
	@WebMethod
	String ReturnInformation(List<OrderRepairshpLogs> bean) throws IOException, ParseException, Exception;//HP返回鉴定结果方法

}
