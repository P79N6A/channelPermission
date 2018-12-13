package com.haier.purchase.data.services;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.dao.purchase.CnReplenishEntryOrderDao;
import com.haier.purchase.data.eai.finance.transfer.ObjectFactory;
import com.haier.purchase.data.eai.finance.transfer.TransferGoodsInfoToEhaierSAP;
import com.haier.purchase.data.eai.finance.transfer.TransferGoodsInfoToEhaierSAP_Service;
import com.haier.purchase.data.eai.finance.transfer.ZMMS0010;
import com.haier.purchase.data.eai.finance.transfer.ZSDS0002;
import com.haier.purchase.data.model.Cn3wReplenishOrders;
import com.haier.purchase.data.model.CnReplenishEntryOrder;
import com.haier.purchase.data.model.CnReplenishEntryOrderItem;
import com.haier.purchase.data.service.Cn3wReplenishOrdersService;
import com.haier.purchase.data.service.CnReplenishEntryOrderItemService;
import com.haier.purchase.data.service.CnReplenishEntryOrderService;

@Service
public class CnReplenishEntryOrderServiceImpl implements CnReplenishEntryOrderService {

	private static Logger logger = LoggerFactory.getLogger(CnReplenishEntryOrderServiceImpl.class);
	
	@Value("${wsdlPath}")
	private String wsdlLocation;

	@Autowired
	private CnReplenishEntryOrderDao cnReplenishEntryOrderDao;
	@Autowired
	private CnReplenishEntryOrderItemService cnReplenishEntryOrderItemService;
	@Autowired
	private Cn3wReplenishOrdersService cn3wReplenishOrdersService;

	@Override
	public void orderIn3WPushToSAP() {
		Integer pageIndex = 1;
		while(true){
			//一次取100条，完成为止。条件写在cnReplenishEntryOrderDao.getToPushSAPOrder。
			PagerInfo pi = new PagerInfo(100, pageIndex++);
			List<CnReplenishEntryOrder> list = cnReplenishEntryOrderDao.getToPushSAPOrders(pi);
			if(list != null && !list.isEmpty()){
				break;
			}
			logger.info("获得" + list.size() + "条待推送SAP调拨单。");
			for (CnReplenishEntryOrder order : list) {
				List<CnReplenishEntryOrderItem> itemList = cnReplenishEntryOrderItemService.getByReplEntryOrderId(order.getId());
				Cn3wReplenishOrders cn3wReplenishOrders = cn3wReplenishOrdersService.getByLBX(order.getStoreCode());
				for (int i = 0; i < itemList.size(); i++) {
					CnReplenishEntryOrderItem item = itemList.get(i);
					if(item.getInSap() == 5){
						//已经推送成功
					}else{
						String itemNum = item.getItemNum();
						if(StringUtils.isBlank(itemNum)){
							itemNum = genItemNum(i);
							item.setItemNum(itemNum);
						}
						ServiceResult<String> sr2 = pushToSap(order, item, cn3wReplenishOrders);
						if(sr2.getSuccess() == true){
							//推送成功
							item.setInSap(5);
							item.setInSapTime(new Date());
						}else{
							item.setSapMsg(sr2.getMessage());
						}
						cnReplenishEntryOrderItemService.updateStatusAfterInPushToSAP(item);
					}
				}
			}
		}
	}

	/**
	 * 生成行项目号
	 * @param i
	 * @return
	 */
	@Override
	public String genItemNum(int i) {
		//没有多次调拨的情况，最后一位都是1。
		//具体内容看generateZMMS0010方法注释
		return "0" + new DecimalFormat("00").format(i) + "1";
	}

	@Override
	public ServiceResult<String> pushToSap(CnReplenishEntryOrder transferOrder, CnReplenishEntryOrderItem item, Cn3wReplenishOrders cn3wReplenishOrders) {
		ServiceResult<String> response = new ServiceResult<>();
		URL resource = null;
		try {
			resource = this.getClass().getResource(wsdlLocation + "/TransferGoodsInfoToEhaierSAP.WSDL");
		} catch (Exception e) {
			logger.error("[CnReplenishEntryOrderServiceImpl] WSDL文件TransferGoodsInfoToEhaierSAP.WSDL路径配置错误或WSDL文件不存在", e);
			throw new BusinessException("[CnReplenishEntryOrderServiceImpl] 解析WSDL文件失败，配置错误");
		}

		TransferGoodsInfoToEhaierSAP soap = new TransferGoodsInfoToEhaierSAP_Service(resource).getTransferGoodsInfoToEhaierSAPSOAP();
		ZMMS0010 request = generateZMMS0010(transferOrder, item, cn3wReplenishOrders);
		List<ZMMS0010> tZMMS0010 = new ArrayList<ZMMS0010>();
		tZMMS0010.add(request);
		Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();
        Holder<Integer> exSUBRC = new Holder<Integer>();
        soap.transferGoodsInfoToEhaierSAP(tZMMS0010, exSUBRC, tMSG);
        List<ZSDS0002> resultLst = tMSG.value;
		if (null == resultLst || resultLst.size() == 0) {
			response.setSuccess(false);
			response.setResult("E");
			response.setMessage("EAI返回结果为空");
			return response;
		}
		for (ZSDS0002 result : resultLst) {
			if (result.getTYPE().equalsIgnoreCase("E")) {
				response.setSuccess(false);
				response.setResult("E");
				response.setMessage(result.getMESSAGE());
				return response;
			}
			if (!result.getTYPE().equalsIgnoreCase("E") && !result.getTYPE().equalsIgnoreCase("S")) {
				response.setSuccess(false);
				response.setResult(result.getTYPE());
				response.setMessage(result.getMESSAGE());
				return response;
			}
		}
		response.setSuccess(true);
		response.setResult("S");
		return response;
	}

	@Override
	public List<CnReplenishEntryOrder> getEditStatusSAPOrders(List<Map<String, Object>> list) {
		List<String> lineNumList = new ArrayList<>();
		for(Map<String, Object> map : list){
			lineNumList.add(map.get("soLineNum").toString());
		}
		return cnReplenishEntryOrderDao.getEditStatusSAPOrders(lineNumList);
	}

	/**
	 * 生成SAP请求
	 *
	 * @param order
	 *            调拨单信息
	 * @param isOutStock
	 *            出入库标记
	 *
	 *            1、调拨单为出库状态的，推送时“LES入库单号”“LES入库单行项目号”置空
	 *            2、调拨单为入库状态的，推送时“LES出库单号”“LES出库单行项目号”“出库位编码”置空 3、BLDAT 格式为
	 *            YYYY.MM.DD 菜鸟调拨出库/入库的单据日期 4、BUDAT 格式为 YYYY.MM.DD 传入数据时的当前日期
	 *
	 *            同一个LBX单（总单），其行项目字段为4位（例如0302）， 逻辑为： 1、首位数字不填或者填0
	 *            1、物料多种，中间两位从01开始排，如同一个LBX三种SKU，则中间两位分别是01、02、03；
	 *            2、分多次调拨，后一位从1开始，如分两次调拨，则分别是1、2； 如：第十种物料第二次调拨： "102" 或 "0102"
	 * 
	 *            出库推送sap： request.setZCBSN(IBC号) —— IBC号也就是调拨单号
	 *            request.setMATNR(SKU) request.setZLGORTI(入库位编码)
	 *            request.setZLSOT(出库LBX号后15位) request.setZLSOI(行项目号)
	 *            request.setZLGORTO(出库位编码) request.setMENGE(出库数量)
	 *            request.setBLDAT(出库确认时间) request.setBUDAT(当前时间)
	 *
	 * 
	 * @return
	 */
	private ZMMS0010 generateZMMS0010(CnReplenishEntryOrder order, CnReplenishEntryOrderItem item, Cn3wReplenishOrders cn3wReplenishOrders) {
		ObjectFactory objectFactory = new ObjectFactory();
		ZMMS0010 request = objectFactory.createZMMS0010();
		request.setZCBSN(order.getStoreOrderCode()); // 总得LBX单号
		request.setMATNR(item.getItemCode()); // sku

		request.setZLGORTI(order.getToStoreCode()); // 入库位编码

		request.setZLSIN(cn3wReplenishOrders.getEntryOrderId()); // LES入库单号
		request.setZLSII(item.getItemNum()); // LES入库单行项目号
		request.setMENGE(item.getConfirmQty()); // 
		// 期望数量=入库正品+入库残品
		// 残品也推SAP
		try {
			DateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			String bldat = sdf.format(order.getOrderConfirmTime());
			String budat = sdf.format(new Date());
			request.setBLDAT(bldat); // YYYY.MM.DD菜鸟调拨出库/入库的单据日期
			request.setBUDAT(budat); // YYYY.MM.DD传入数据时的当前日期
		} catch (Exception e) {
			logger.error("执行方法diaoboInStockTransferService发生错误，时间转换异常。参数" + order.getOrderConfirmTime());
		}
		return request;
	}

	@Override
	public List<CnReplenishEntryOrder> getToPushSAPOrders(PagerInfo pi) {
		return cnReplenishEntryOrderDao.getToPushSAPOrders(pi);
	}
}
