package com.haier.vehicle.services;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.service.vechile.PurchaseVehicleInterfaceLogService;
import com.haier.vehicle.model.InsertOrderToOMSItem;
import com.haier.vehicle.service.VehicleInsertOrderToOMSService;
import com.haier.vehicle.util.ConversionClassUtil;
import com.haier.vehicle.wsdl.sendorder.InputType;
import com.haier.vehicle.wsdl.sendorder.InsertOrderBillOMS2ProcessRequest;
import com.haier.vehicle.wsdl.sendorder.InsertOrderBillOMS2ProcessResponse;
import com.haier.vehicle.wsdl.sendorder.InsertOrderBillZKToOMS;
import com.haier.vehicle.wsdl.sendorder.InsertorderbillZktoomsClientEp;
import com.haier.vehicle.wsdl.sendorder.RemoteExceptionMessage;

/**
 * 下單
 *
 * @author zzb
 * @create 2017-09-05 10:09
 **/
@Service
public class VehicleInsertOrderToOMSServiceImpl implements VehicleInsertOrderToOMSService {

    @Value("${wsdlPath}")
	private String wsdlLocation;

    @Autowired
    private PurchaseVehicleInterfaceLogService vehicleInterfaceLogService;


    @Override
    public ServiceResult<String> sendOrderToOms(InsertOrderToOMSItem item) {
        ServiceResult<String> result = new ServiceResult<String>();
        InsertOrderBillOMS2ProcessResponse out = null;
        JSONObject json = null;
		// String path = "file:"
		// + this.getClass()
		// .getResource(wsdlLocation + "/InsertOrderBill_ZKToOMS.wsdl")
		// .getPath();
		// URL url = new URL(path);
		URL url = this.getClass().getResource(
				wsdlLocation + "/InsertOrderBill_ZKToOMS.wsdl");
		InsertorderbillZktoomsClientEp service = new InsertorderbillZktoomsClientEp(
				url);
		InsertOrderBillZKToOMS soap = service.getInsertOrderBillZKToOMSPt();
		InsertOrderBillOMS2ProcessRequest insertOrderBillOMS2ProcessRequest = new InsertOrderBillOMS2ProcessRequest();
		InputType in = new InputType();
		ConversionClassUtil.conversion(item, in);
		in.setADD1(item.getADD1());
		in.setADD2(item.getADD2());
		in.setADD3(item.getADD3());
		in.setADD4(item.getADD4());
		in.setADD5(item.getADD5());
		in.setADD6(item.getADD6());
		in.setADD7(item.getADD7());
		in.setADD8(item.getADD8());
		in.setADD9(item.getADD9());
		in.setADD10(item.getADD10());
		in.setADD11(item.getADD11());
		in.setADD12(item.getADD12());
		in.setADD13(item.getADD13());
		in.setADD14(item.getADD14());
		in.setADD15(item.getADD15());
		in.setADD16(item.getADD16());
		in.setADD17(item.getADD17());
		in.setADD18(item.getADD18());
		in.setProjectCode(item.getProjectCode());

		insertOrderBillOMS2ProcessRequest.getInput().add(in);

		try {
			JSONObject object = (JSONObject) JSONObject
					.toJSON(insertOrderBillOMS2ProcessRequest);
			vehicleInterfaceLogService.insertLog("整车直发向CRM下单入参",
					object.toJSONString());
			out = soap.process(insertOrderBillOMS2ProcessRequest);
			json = (JSONObject) JSONObject.toJSON(out);
			vehicleInterfaceLogService.insertLog("整车直发向CRM下单出参",
					json.toJSONString());
			JSONObject jsonResult = new JSONObject();
			if ("Y".equals(out.getOut().get(0).getFlag())) {
				jsonResult.put("billCode", out.getOut().get(0).getBillCode());
				jsonResult.put("vbeln", out.getOut().get(0).getVbeln());
				result.setResult(jsonResult.toJSONString());
				result.setSuccess(true);
			} else {

				jsonResult.put("returnMsg", "");
				result.setSuccess(false);
			}
			result.setMessage(out.getOut().get(0).getReturnMsg());

		} catch (RemoteExceptionMessage remoteExceptionMessage) {
			remoteExceptionMessage.printStackTrace();
			result.setMessage("调用下单接口异常");
			result.setSuccess(false);
			return result;
		}
        return result;
    }
}
