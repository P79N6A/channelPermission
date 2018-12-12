package com.haier.vehicle.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.vehcile.OesMaterielDTO;
import com.haier.purchase.data.service.vechile.PurchaseOesMaterielService;
import com.haier.vehicle.service.OesMaterielService;

@Service
public class OesMaterielServiceImpl implements OesMaterielService{
	
	@Autowired
	PurchaseOesMaterielService purchaseOesMaterielService;
	
	public ServiceResult<List<OesMaterielDTO>> findOesMaterielList(OesMaterielDTO dto){
		ServiceResult<List<OesMaterielDTO>> result = new ServiceResult<List<OesMaterielDTO>>();
		if(dto == null || "".equals(dto.getPlantcode())){
			try {
				result.setResult(purchaseOesMaterielService.findOesMaterielList(dto));
				result.setSuccess(true);
			} catch (Exception e) {
				result.setMessage("获取OES工贸工厂接口发生未知异常：" + e.getMessage());
	            result.setSuccess(false);
			}
		}else{
			result.setMessage("获取OES工贸工厂接口plantcode不可为空");
            result.setSuccess(false);
		}
		return result;
	}
	
}