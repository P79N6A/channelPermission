package com.haier.stock.services;


import com.haier.afterSale.service.ThTransactionService;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceQueueData;
import com.haier.eis.service.EisInterfaceQueueDataService;
import com.haier.stock.model.InvThTransaction;
import com.haier.stock.util.HelpUtils;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.haier.stock.eai.finance.transBadGoodsInfoFromGvsToEhaier.*;

@Service("outToGVSModel")
public class TransferDefectiveProductsOutToGVSModel {
	 private static Logger logger = LoggerFactory.getLogger(TransferDefectiveProductsOutToGVSModel.class);
	@Autowired
	private ThTransactionService thTransactionService;
	@Autowired
	private HelpUtils help;
	private String wsdlFile;
	@Autowired
	private EisInterfaceQueueDataService eisInterfaceQueueDataDao;
	  @Value("${wsdlPath}")
	    private String wsdlPath;
	/**
	 * 查询不良品虚出数据
	 * @param channel
	 * @return
	 */
	private List<InvThTransaction> getTransferData(String channel) {
		ServiceResult<List<InvThTransaction>> result = thTransactionService.getOutDataList(channel);
		if (result == null || !result.getSuccess()) {
			logger.info("查询不良品虚出信息失败");
			return null;
		}
		return result.getResult();
	}

	private InvThTransaction getInvThTransactionById(Integer id) {
		ServiceResult<InvThTransaction> result = thTransactionService.get(id);
		if (result == null || !result.getSuccess()) {
			logger.info("查询不良品虚出信息失败");
			return null;
		}
		return result.getResult();
	}

	/**
	 * 查询发送失败的不良品虚出数据
	 * @return
	 */
	private List<InvThTransaction> getTransferFailureData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("source", "0");//0-不良品
		params.put("billType", "0");//0-出；1-入
		params.put("status", "2");//处理状态(-1-出现特殊处理；0-未知；1-成功；2-失败；3-错误)
		List<EisInterfaceQueueData> queueDatas = eisInterfaceQueueDataDao
				.queryEisInterfaceQueueData(params);
		if (queueDatas == null || queueDatas.size() == 0) {
			return null;
		}
		List<InvThTransaction> transactions = new ArrayList<InvThTransaction>();
		for (EisInterfaceQueueData queueData : queueDatas) {
			Integer sourceId = queueData.getSourceId();
			InvThTransaction thTransaction = this.getInvThTransactionById(sourceId);
			if (thTransaction != null) {
				transactions.add(thTransaction);
			}
		}
		return transactions;
	}
//
//	//发送不良品虚出信息到SAP
	public void transferDefectiveProductsOutToGVS() {
		//1、处理以往发送失败的数据
		List<InvThTransaction> failureData = this.getTransferFailureData();
		this.handler(failureData);
		//2、处理新的数据
		String channel = InvThTransaction.CHANNEL_SHANGCHENG;
		List<InvThTransaction> invThTransactions = this.getTransferData(channel);
		this.handler(invThTransactions);
		//2、处理3W不良品的数据
		String channel3W = InvThTransaction.CHANNEL_SHANGCHENG_3W;
		List<InvThTransaction> invThTransactions3W = this.getTransferData(channel3W);
		this.handler(invThTransactions3W);
	}

	/**
	 * 逐条发送数据到SAP
	 * @param invThTransactions
	 */
	private void handler(List<InvThTransaction> invThTransactions) {

		if (invThTransactions == null || invThTransactions.size() == 0) {
			logger.info("不良品虚出信息不存在");
			return;
		}

		for (InvThTransaction transaction : invThTransactions) {
			try {
				transfer(transaction);
			} catch (Exception e) {
				logger.error("New[To Sap]:发送不良品虚出信息到SAP发生异常InvThTransactionID：["
						+ transaction.getId() + "]," + e.getMessage());
			}
		}
	}

	/**
	 * 发送数据到SAP
	 * @param transaction
	 */
	private void transfer(InvThTransaction transaction) {

		String channelOrderSn = transaction.getChannelOrderSn();
		if (StringUtil.isEmpty(channelOrderSn)) {
			throw new BusinessException("网单号不存在");
		}

		//组装数据
		ObjectFactory factory = new ObjectFactory();
		ZMMINBOUND010IN zmminbound010in = factory.createZMMINBOUND010IN();
		zmminbound010in.setZSPDT(DateUtil.format(new Date(), "yyyy-MM-dd"));//订单出库日期
		zmminbound010in.setMATNR(transaction.getSku());//物料编号
		zmminbound010in.setMENGE(new BigDecimal(transaction.getQuantity()));//交货数量
		zmminbound010in.setLGORT(transaction.getSecCode());//库位
		zmminbound010in.setZFGLG("10");//批次编号：10/90
		zmminbound010in.setZLSGI(transaction.getInwhId());//入库过账凭证(入库_LES入库单号-LES入RA凭证号)
		zmminbound010in.setLIFNR("01");// 供应商,测试供应商：V98715， V98668,正式：01
		zmminbound010in.setVBELN(transaction.getVbelnSo());//  46单
		zmminbound010in.setPOSNR("10");
		zmminbound010in.setZWBDR(channelOrderSn);//网单号
		zmminbound010in.setSTAT("1");//0、正品退货  1、不良品退货 2、京东不良品退货
		zmminbound010in.setBZ1("");//备注

		List<ZMMINBOUND010IN> request = new ArrayList<ZMMINBOUND010IN>();
		request.add(zmminbound010in);
		logger.info("不良品虚出发送SAP参数：" + JsonUtil.toJson(request));
		// 要记录接口数据日志
		EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
		dataLog.setForeignKey(transaction.getChannelOrderSn());
		dataLog.setRequestTime(DateUtil.currentDateTime());
		dataLog.setRequestData(JsonUtil.toJson(request));
		Long startTime = System.currentTimeMillis();
		URL url = this.getClass().getResource(
				wsdlPath+ "/" +this.wsdlFile);
		TransBadGoodsInfoFromGVSToEHAIER soap = new TransBadGoodsInfoFromGVSToEHAIERSOAPQSService(
				url).getTransBadGoodsInfoFromGVSToEHAIERSOAPQSPort();

		Integer status = null;
		String message = null;
		try {
			String sysname = "EHAIER";
			//发送
			OutType out = soap.transBadGoodsInfoFromGVSToEHAIER(sysname, request);
			String msg = JsonUtil.toJson(out);

			dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
			dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
			dataLog.setResponseTime(DateUtil.currentDateTime());
			dataLog.setResponseData(msg);

			if (out == null || out.getTMSG() == null || out.getTMSG().size() == 0) {
				status = EisInterfaceQueueData.STATUS_FAILED;
				message = "EAI返回空";
				dataLog.setResponseData("");
			} else {
				List<ZMMINBOUND010OUT> results = out.getTMSG();
				boolean flag = true;
				for (ZMMINBOUND010OUT result : results) {
					//                    flag = !"E".equalsIgnoreCase(result.getTYPE());
					//                    if (!flag)
					//                        break;
					if (!"S".equalsIgnoreCase(result.getTYPE())) {
						flag = false;
						break;
					}
				}

				status = flag ? EisInterfaceQueueData.STATUS_SUCCESS
						: EisInterfaceQueueData.STATUS_FAILED;
				message = msg;
			}
		} catch (Exception e) {
			dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
			dataLog.setResponseTime(DateUtil.currentDateTime());
			dataLog.setResponseData("");
			dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
			dataLog.setErrorMessage(e.getMessage());
			status = EisInterfaceQueueData.STATUS_FAILED;
			message = "调用EAI接口失败";
			logger.error("调用EAI接口 TransBadGoodsInfoFromGVSToEHAIER 失败：", e);
		}
		dataLog.setCreateTime(DateUtil.currentDateTime());
		dataLog.setInterfaceCode(EisInterfaceQueueData.INTERFACE_CODE_DEFECTIVEPRODUCTS_OUT);
		//记录日志
		help.recordEisInterfaceDataLog(dataLog);
		//记录eis接口数据
		EisInterfaceQueueData queueData = new EisInterfaceQueueData();
		queueData.setSourceId(transaction.getId());
		queueData.setSource(EisInterfaceQueueData.SOURCE_DEFECTIVEPRODUCTS);
		queueData.setBillType(EisInterfaceQueueData.BILLTYPE_OUT);
		queueData.setInterfaceCode(EisInterfaceQueueData.INTERFACE_CODE_DEFECTIVEPRODUCTS_OUT);
		queueData.setStatus(status);
		queueData.setMessage(message);
		queueData.setDataLogId(dataLog.getId());
		Integer countRow = help.recordEisInterfaceQueueData(queueData);
		//发送成功且数据成功放入eis_interface_queue_data，更新源数据表inv_th_transaction状态
		if ((status != null && status == EisInterfaceQueueData.STATUS_SUCCESS)
				&& (countRow != null && countRow > 0)) {
			InvThTransaction thTransaction = new InvThTransaction();
			thTransaction.setId(transaction.getId());
			thTransaction.setOutStatus(InvThTransaction.TRANS_OUT_STATUS_SEND_TOSAP);//2-已发送SAP
			thTransactionService.updateById(thTransaction);
		}
	}




}
