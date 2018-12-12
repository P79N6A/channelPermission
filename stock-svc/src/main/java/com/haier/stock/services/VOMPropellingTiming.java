package com.haier.stock.services;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisExternalSku;
import com.haier.eis.service.EisExternalSkuService;
import com.haier.shop.model.OrderRepairLESRecords;
import com.haier.shop.model.OrdersVo;
import com.haier.shop.model.VOMSynOrderRequire;
import com.haier.shop.model.VOMSynSubOrderRequire;
import com.haier.shop.service.ShopOrderRepairLesreCordsService;
import com.haier.shop.service.ShopOrdersService;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.service.StockInvMachineSetService;
import com.haier.stock.util.Ustring;


/**
 * 定时推送VOM出入库信息
 * @author wukunyang
 *
 */
@Configuration
@EnableScheduling
public class VOMPropellingTiming {
	@Autowired
	private ShopOrderRepairLesreCordsService shopOrderRepairLesreCordsService;
	@Autowired
	private VOMOrderModel vomOrderModel;
	@Autowired
	private ShopOrdersService shopOrdersService;//订单
	@Autowired
    private StockInvMachineSetService         invMachineSetDao;
	@Autowired
	private EisExternalSkuService eisExternalSkuService;
	 private static Logger log = LoggerFactory.getLogger(VOMPropellingTiming.class);
	/**
	 * 推送到vom
	 */
//	 @Scheduled(cron="0/5 * *  * * ?")
	public void CallVOMProprlling(){
			List<OrderRepairLESRecords> repairLESRecords=shopOrderRepairLesreCordsService.queryOutofStorage();
		for(int i=0;i<repairLESRecords.size();i++){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = new Date();
			OrdersVo vo= shopOrdersService.queryVOMTransMission(repairLESRecords.get(i).getOrderProductId().toString());
			VOMSynOrderRequire synOrderRequire = new VOMSynOrderRequire();
			 synOrderRequire.setOrderNo(repairLESRecords.get(i).getRecordSn());
			 synOrderRequire.setSourceSn(repairLESRecords.get(i).getRecordSn());
			 synOrderRequire.setOrderType(Ustring.getString(vo.getOrderType()));
//			 synOrderRequire.setBusType("70");
			 synOrderRequire.setOrderDate(dateFormat.format(date1));
			 synOrderRequire.setStoreCode(vo.getsCode());
			 synOrderRequire.setProvince(vo.getProvince().toString());
			 synOrderRequire.setCity(vo.getCity().toString());
			 synOrderRequire.setCounty(vo.getRegion().toString());
			 synOrderRequire.setAddr(vo.getAddress());
//			 synOrderRequire.setGbCode("220102");
			 synOrderRequire.setName(vo.getConsignee());
			 synOrderRequire.setMobile(vo.getMobile());
			 synOrderRequire.setTel(vo.getPhone());
			 synOrderRequire.setPostCode(vo.getZipcode()); 
			 synOrderRequire.setPayState(vo.getPaymentStatus().toString());
			 synOrderRequire.setPayTime(vo.getPayTimeStr());
			 synOrderRequire.setPayType(vo.getPaymentCode());
			 synOrderRequire.setIsInv(vo.getIsReceipt());
			 synOrderRequire.setInvType(vo.getType());
			 synOrderRequire.setTaxBearer(vo.getTaxPayerNumber());
			 synOrderRequire.setRecAddr(vo.getRegisterAddressAndPhone());
			 synOrderRequire.setRemark(repairLESRecords.get(i).getRepairSn());
			 
			 StringBuffer sb = new StringBuffer(repairLESRecords.get(i).getRecordSn());  
			 sb.deleteCharAt(sb.length()-3);
			String Reorder =repairLESRecords.get(i).getRecordSn().substring(repairLESRecords.get(i).getRecordSn().length()-3, repairLESRecords.get(i).getRecordSn().length());
			 if("CX1".equals(Reorder)){
				 Reorder=sb+"CX2";
			 }else if("CX2".equals(Reorder)){
				 Reorder=sb+"CX1";
			 }else {
				 Reorder=repairLESRecords.get(i).getRecordSn();
			 }
			 synOrderRequire.setReorder(Reorder);
//			 synOrderRequire.setRecAcc("181901040011603");
			 synOrderRequire.setRecBank(vo.getBankNameAndAccount());
//			 synOrderRequire.setSname("海尔电商");
//			 synOrderRequire.setSprovince("山东省");
//			 synOrderRequire.setScity("青岛市");
//			 synOrderRequire.setScounty("崂山区");
//			 synOrderRequire.setSaddr("山东省青岛市崂山区海尔路1号海尔工业园");
//			 synOrderRequire.setBusFlag("2");
			 synOrderRequire.setFreight(vo.getShippingAmount().doubleValue());
			 synOrderRequire.setBillSum(vo.getOrderAmount().doubleValue());
//			 synOrderRequire.setBillOwe(0.0);
			 VOMSynSubOrderRequire synSubOrderRequire = new VOMSynSubOrderRequire();
			 //套机列表查询
			 String sku= getProductCode(repairLESRecords.get(i).getId(),repairLESRecords.get(i).getSku(),repairLESRecords.get(i).getPushFailNumber()); 
			 
			 synSubOrderRequire.setProductCode(sku);
			 synSubOrderRequire.setItemNo("1");
			 synSubOrderRequire.setStorageType(Ustring.getString(repairLESRecords.get(i).getStorageType()));
			 synSubOrderRequire.setHrCode(sku);
			 synSubOrderRequire.setProdes(repairLESRecords.get(i).getProductName());
			 synSubOrderRequire.setNumber(repairLESRecords.get(i).getNumber());
			 synSubOrderRequire.setUnprice(repairLESRecords.get(i).getPrice());
			 List<VOMSynSubOrderRequire> subOrderList = new ArrayList<VOMSynSubOrderRequire>();
			 subOrderList.add(synSubOrderRequire);
			 synOrderRequire.setSubOrderList(subOrderList);
			vomOrderModel.synOrderInfo(synOrderRequire);
			
			OrderRepairLESRecords records = new OrderRepairLESRecords();
        	records.setId(repairLESRecords.get(i).getId());
        	records.setFalg("1"); //修改推送状态
        	shopOrderRepairLesreCordsService.updataRecords(records);
		}
	}
		
	
	public String getProductCode(int id,String sku,Integer pushFailNumber){
		 //套机列表查询
	      List<InvMachineSet> imsList = null;
		 InvMachineSet machineSet = new InvMachineSet();
          machineSet.setMainSku(sku);
          ServiceResult<List<InvMachineSet>> stockcommresult = getSubMachinesByMainSku(machineSet);
          if (stockcommresult != null && stockcommresult.getSuccess()) {
              imsList = stockcommresult.getResult();
              if (imsList == null || imsList.size() == 0) {//判断如果不是套机就做一个常规处理
                  imsList = new ArrayList<InvMachineSet>();
                  InvMachineSet ims = new InvMachineSet();
                  ims.setPosnr("10");
                  ims.setSubSku(convertToExternalSku(sku));//转换为R码
                  if (ims.getSubSku() == null || ims.getSubSku().equals("")) {
                  	OrderRepairLESRecords records = new OrderRepairLESRecords();
                  	records.setId(id);
                  	records.setFalg("2"); //修改推送状态
                  	records.setFailReason("套机产品编码不能为空");
                  	records.setPushFailNumber(pushFailNumber+1); //修改失败推送次数
                  	shopOrderRepairLesreCordsService.updataRecords(records);
                  }
                  ims.setMenge(new BigDecimal(1));
                  imsList.add(ims);
                  
                  return ims.getSubSku();
              }
          }
          	return sku;
	}
		
		 public ServiceResult<List<InvMachineSet>> getSubMachinesByMainSku(InvMachineSet machineSet) {
		        ServiceResult<List<InvMachineSet>> result = new ServiceResult<List<InvMachineSet>>();
		        try {
		            result.setResult(invMachineSetDao.getByMainSku(machineSet.getMainSku()));
		        } catch (Exception e) {
		            log.error("根据mainSku(" + machineSet.getMainSku() + ")获取仓库信息时发生异常:", e);
		            result.setSuccess(false);
		            result.setMessage(e.getMessage());
		        }

		        return result;
		    }
		 /**
		     * 转换为外部系统使用的物料编码
		     *
		     * @param sku 内部的物料编码
		     * @return 外部的物料编码
		     */
		    //12
		    private String convertToExternalSku(String sku) {
		        if (sku == null || sku.equals("")) {
		            return null;
		        }
		        ServiceResult<EisExternalSku> result = getBySkuType(sku, EisExternalSku.TYPE_R);
		        EisExternalSku es = result.getResult();

		        if (!result.getSuccess()) {
		            log.error("通过itemService转换物料编码发生未知异常：" + result.getMessage());
		            //throw new BusinessException("通过itemService转换物料编码发生未知异常：" + result.getMessage());
		            return sku;
		        } else {
		            if (es != null && es.getExternalSku() != null && !es.getExternalSku().equals("")) {
		                return es.getExternalSku();
		            } else {
		                return sku;
		            }
		        }
		    }
		    public ServiceResult<EisExternalSku> getBySkuType(String sku, String type) {
		        ServiceResult<EisExternalSku> result = new ServiceResult<EisExternalSku>();
		        try {
		            result.setResult(eisExternalSkuService.getBySkuType(sku, type));
		        } catch (Exception e) {
		            log.error("根据sku和type查询物料对照信息时，发生未知异常：", e);
		            result.setMessage("根据sku和type查询物料对照信息发生未知异常：" + e.getMessage());
		            result.setSuccess(false);
		        }
		        return result;
		    }
		    

}