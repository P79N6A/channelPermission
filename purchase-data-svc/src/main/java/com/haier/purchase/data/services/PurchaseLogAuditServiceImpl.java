package com.haier.purchase.data.services;


import com.alibaba.fastjson.JSONObject;
import com.haier.purchase.data.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.LogAuditDao;
import com.haier.purchase.data.service.PurchaseLogAuditService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseLogAuditServiceImpl implements PurchaseLogAuditService{
	
	@Autowired
	LogAuditDao logAuditDao;
	private static final String     TYPESTR = "导入,修改,新增,启用,禁用,删除";
	//模块对应的数据库名称，用来判断是哪个功能点的操作日志
	private static final String            TIMEGATE          = "haier_time_gate_t";                    //haier_time_gate_t 时间闸口设置
	private static final String            STOCKGATE         = "haier_stock_exceed_gate_t";            //haier_stock_exceed_gate_t 库存超期闸口设置
	private static final String            LIMITGATETARGET   = "haier_limit_gate_t_target";            //haier_limit_gate_t 额度闸口设置-指标
	private static final String            LIMITGATELIMITSUM = "haier_limit_gate_t_limit_sum";         //haier_limit_gate_t 额度闸口设置-总额度
	private static final String            LIMITGATELIMIT    = "haier_limit_gate_t_limit";             //haier_limit_gate_t 额度闸口设置-个别额度
	private static final String            LIMITGATELOAN     = "haier_limit_gate_t_loan";              //haier_limit_gate_t 额度闸口设置-拆借
	private static final String            LIMITGATE         = "haier_limit_gate_t";                   //haier_limit_gate_t 额度闸口设置-导入指标文件
	private static final String            RATEGATE          = "materials_acc_rate_gate_t";            //materials_acc_rate_gate_t 备料准确率闸口设置
	private static final String            CRMT2ORDER        = "haier_t2_order_t";                     //haier_t2_order_t 海尔T+2订单数量
	private static final String            CGOT2ORDER        = "leader_nb_t2_order_t_cgo";             //haier_t2_order_t 统帅T+2订单数量
	private static final String            DBMT2ORDER        = "leader_nb_t2_order_t_dbm";             //haier_t2_order_t 新宝婴童T+2订单数量
	private static final String            CRMGVSORDER       = "haier_gvs_order_t";                    //haier_gvs_order_t 海尔GVS订单数量
	/**
     * 保存操作日志信息
     * @param vo
     */
	@Override
    public void saveAuditLog(LogAuditInfo vo){
		logAuditDao.saveAuditLog(vo);
	}

	@Override
	public Map<String, Object> queryLogAudit(Map<String, Object> params) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		List<LogAuditInfo> list=logAuditDao.getLogAuditList(params);
		for (LogAuditInfo logInfo : list) {
			int typeData = logInfo.getType();
			logInfo.setTypeName(TYPESTR.split(",")[typeData]);
		}
		retMap.put("total", logAuditDao.countLogAuditWithLike(params));
		retMap.put("rows", list);
		return retMap;
	}

	@Override
	public List<LogAuditInfo> queryLogAuditExcle(Map<String, Object> params) {
		return logAuditDao.getLogAuditList(params);
	}

	public void log(String orderId, int type, String content, String operUserName,
					String operUserId, String jude_way_channel, String gate_way_channel,
					String channel, String category) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LogAuditInfo lai = new LogAuditInfo();
		lai.setContent(content);
		lai.setLog_time(sdf.format(new Date()));
		lai.setOper_user_name(operUserName);
		lai.setOper_user_id(operUserId);
		lai.setOrder_id(orderId);
		lai.setType(type);
		lai.setJude_way_channel(jude_way_channel);
		lai.setGate_way_channel(gate_way_channel);
		lai.setChannel(channel);
		lai.setCategory(category);
		logAuditDao.saveAuditLog(lai);
	}

	@Override
	public void unionLog(String model, Object oldData, Object newData, String type,
						 String operUserName, String orderId) {

		//要插入的变量
		String content = "";
		String operUserId = "";
		String jude_way_channel = "";
		String gate_way_channel = "";
		String channel = "";
		String category = "";
		int tp = Integer.parseInt(type);

		if (TIMEGATE.equals(model)) {
			content += "时间闸口设置：";
			//时间闸口
			GateItem[] timeGateOldArr = (GateItem[]) oldData;//原时间闸口数据
			GateItem[] timeGateNewArr = (GateItem[]) newData;//新时间闸口数据
			for (int i = 0; i < 18; i++) {
				GateItem tgo = timeGateOldArr[i];
				GateItem tgn = timeGateNewArr[i];
				//原字段信息
				String setting_idO = tgo.getSetting_id();
				String setting_weekO = tgo.getSetting_week();
				String setting_hourO = tgo.getSetting_hour();
				String setting_minuteO = tgo.getSetting_minute();
				String setting_secondO = tgo.getSetting_second();
				//新字段信息
				String setting_weekN = tgn.getSetting_week();
				String setting_hourN = tgn.getSetting_hour();
				String setting_minuteN = tgn.getSetting_minute();
				String setting_secondN = tgn.getSetting_second();
				//对比是否有改动，如果有则记录到操作日志中
				if (!setting_weekO.equals(setting_weekN) || !setting_hourO.equals(setting_hourN)
						|| !setting_minuteO.equals(setting_minuteN)
						|| !setting_secondO.equals(setting_secondN)) {
					content += "[" + setting_idO + ":原数据：第" + setting_weekO + "周，" + setting_hourO
							+ "时：" + setting_minuteO + "分：" + setting_secondO + "秒；  新数据：第"
							+ setting_weekN + "周，" + setting_hourN + "时：" + setting_minuteN
							+ "分：" + setting_secondN + "秒]  ";
				}
			}

			//判断是否有更新，并插入操作日志表
			if (!content.equals("时间闸口设置：")) {
				this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
						gate_way_channel, channel, category);
			}

		} else if (STOCKGATE.equals(model)) {
			//库存超期闸口
			List<GateOfStockExceedItem> stockGateOldList = (List<GateOfStockExceedItem>) oldData;//原库存超期闸口数据
			List<GateOfStockExceedItem> stockGateNewList = (List<GateOfStockExceedItem>) newData;//新库存超期闸口数据

			for (int i = 0; i < stockGateOldList.size(); i++) {
				content = "库存超期闸口设置：";
				GateOfStockExceedItem sgo = stockGateOldList.get(i);
				GateOfStockExceedItem sgn = stockGateNewList.get(i);
				//原字段信息
				String is_enabledO = sgo.getIs_enabled();
				String is_enabled_nameO = sgo.getIs_enabled_name();
				String judge_ed_channel_idO = sgo.getJudge_ed_channel_id();
				String judge_ed_channel_nameO = sgo.getJudge_ed_channel_name();
				String exceed_ageO = sgo.getExceed_age();
				String limit_ed_channel_idO = sgo.getLimit_ed_channel_id();
				String limit_ed_channel_nameO = sgo.getLimit_ed_channel_name();
				String brand_typeO = sgo.getBrand_type().equals("1") ? "本品牌" : "全品牌";
				String category_typeO = sgo.getCategory_type().equals("1") ? "本品类" : "全品类";
				String model_typeO = sgo.getModel_type().equals("1") ? "本型号" : "全型号";
				String storage_typeO = sgo.getStorage_type().equals("1") ? "本库位" : "全库位";
				//新字段信息
				String is_enabledN = sgn.getIs_enabled();
				String is_enabled_nameN = sgn.getIs_enabled_name();
				String judge_ed_channel_idN = sgn.getJudge_ed_channel_id();
				String judge_ed_channel_nameN = sgn.getJudge_ed_channel_name();
				String exceed_ageN = sgn.getExceed_age();
				String limit_ed_channel_idN = sgn.getLimit_ed_channel_id();
				String limit_ed_channel_nameN = sgn.getLimit_ed_channel_name();
				String brand_typeN = sgn.getBrand_type().equals("1") ? "本品牌" : "全品牌";
				String category_typeN = sgn.getCategory_type().equals("1") ? "本品类" : "全品类";
				String model_typeN = sgn.getModel_type().equals("1") ? "本型号" : "全型号";
				String storage_typeN = sgn.getStorage_type().equals("1") ? "本库位" : "全库位";
				//对比是否有改动，如果有则记录到操作日志中
				if (!is_enabledO.equals(is_enabledN)
						|| !judge_ed_channel_idO.equals(judge_ed_channel_idN)
						|| !exceed_ageO.equals(exceed_ageN)
						|| !limit_ed_channel_idO.equals(limit_ed_channel_idN)
						|| !brand_typeO.equals(brand_typeN) || !category_typeO.equals(category_typeN)
						|| !model_typeO.equals(model_typeN) || !storage_typeO.equals(storage_typeN)) {
					content += "原数据：" + is_enabled_nameO + "[判断方式：渠道：" + judge_ed_channel_nameO
							+ "，超期库龄：" + exceed_ageO + "][关闸方式：渠道：" + limit_ed_channel_nameO
							+ "，品牌：" + brand_typeO + "，品类：" + category_typeO + "，型号："
							+ model_typeO + "，库位：" + storage_typeO + "]；     新数据："
							+ is_enabled_nameN + "[判断方式：渠道：" + judge_ed_channel_nameN + "，超期库龄："
							+ exceed_ageN + "][关闸方式：渠道：" + limit_ed_channel_nameN + "，品牌："
							+ brand_typeN + "，品类：" + category_typeN + "，型号：" + model_typeN
							+ "，库位：" + storage_typeN + "]";
					//插入操作日志表
					this.log(orderId, tp, content, operUserName, operUserId, judge_ed_channel_idN,
							limit_ed_channel_idN, channel, category);

				}
			}

			//判断是否有新增操作
			if ("2".equals(type)) {
				int sizeOld = stockGateOldList.size();//库中数据条数
				int sizeNew = stockGateNewList.size();//页面数据条数
				for (int j = sizeOld; j < sizeNew; j++) {
					content = "库存超期闸口设置：";
					GateOfStockExceedItem sgnAdd = stockGateNewList.get(j);
					//新字段信息
					String is_enabled_nameN = sgnAdd.getIs_enabled_name();
					String judge_ed_channel_idN = sgnAdd.getJudge_ed_channel_id();
					String judge_ed_channel_nameN = sgnAdd.getJudge_ed_channel_name();
					String exceed_ageN = sgnAdd.getExceed_age();
					String limit_ed_channel_idN = sgnAdd.getLimit_ed_channel_id();
					String limit_ed_channel_nameN = sgnAdd.getLimit_ed_channel_name();
					String brand_typeN = sgnAdd.getBrand_type().equals("1") ? "本品牌" : "全品牌";
					String category_typeN = sgnAdd.getCategory_type().equals("1") ? "本品类" : "全品类";
					String model_typeN = sgnAdd.getModel_type().equals("1") ? "本型号" : "全型号";
					String storage_typeN = sgnAdd.getStorage_type().equals("1") ? "本库位" : "全库位";

					//拼接日志操作内容
					content += "新增数据：" + is_enabled_nameN + "[判断方式：渠道：" + judge_ed_channel_nameN
							+ "，超期库龄：" + exceed_ageN + "][关闸方式：渠道：" + limit_ed_channel_nameN
							+ "，品牌：" + brand_typeN + "，品类：" + category_typeN + "，型号："
							+ model_typeN + "，库位：" + storage_typeN + "]";
					//插入操作日志表
					this.log(orderId, tp, content, operUserName, operUserId, judge_ed_channel_idN,
							limit_ed_channel_idN, channel, category);
				}
			}
		} else if (LIMITGATE.equals(model)) {
			//额度闸口-导入指标文件
			content += "额度闸口-导入指标文件";
			//插入操作日志表
			this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
					gate_way_channel, channel, category);

		} else if (LIMITGATETARGET.equals(model)) {
			//额度闸口-指标
			content += "额度闸口设置-指标修改：";
			List<GateOfLimitItem> limitGateOldList = (List<GateOfLimitItem>) oldData;//原额度闸口数据
			List<GateOfLimitItem> limitGateNewList = (List<GateOfLimitItem>) newData;//新额度闸口数据
			for (int i = 0; i < limitGateOldList.size(); i++) {
				GateOfLimitItem glo = limitGateOldList.get(i);
				GateOfLimitItem gln = limitGateNewList.get(i);
				//原字段信息
				String target_numO = glo.getTarget_num();
				String ed_channel_nameO = glo.getEd_channel_name();
				String category_idO = glo.getCategory_id();
				//新字段信息
				String target_numN = gln.getTarget_num();
				String ed_channel_nameN = gln.getEd_channel_name();
				String category_idN = gln.getCategory_id();
				//对比是否有改动，如果有则记录到操作日志中
				if (!target_numO.equals(target_numN)) {
					content += "原数据[原品类：" + category_idO + "，原渠道：" + ed_channel_nameO + "，原指标："
							+ target_numO + "；  新数据：实际品类：" + category_idN + "，实际渠道："
							+ ed_channel_nameN + "，实际指标：" + target_numN + "],";
				}
			}

			//判断是否有更新，并插入操作日志表
			if (!content.equals("额度闸口设置-指标修改：")) {
				this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
						gate_way_channel, channel, category);
			}
		} else if (LIMITGATELIMITSUM.equals(model)) {
			//额度闸口-额度总数
			content += "额度闸口设置-总额度修改：";
			String currentTotalLimit = (String) oldData;
			String modifyTotalLimit = (String) newData;
			if (!currentTotalLimit.equals(modifyTotalLimit)) {
				content += "原数据：原总额度：" + currentTotalLimit + "；  新数据：实际总额度：" + modifyTotalLimit;
				this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
						gate_way_channel, channel, category);
			}
		} else if (LIMITGATELIMIT.equals(model)) {
			//额度闸口-个别额度
			content += "额度闸口设置-个别额度修改：";
			List<GateOfLimitItem> limitGateOldList = (List<GateOfLimitItem>) oldData;//原额度闸口数据
			List<GateOfLimitItem> limitGateNewList = (List<GateOfLimitItem>) newData;//新额度闸口数据
			//log.info("****原数据"+JsonUtil.toJson(limitGateOldList));
			//log.info("****新数据"+JsonUtil.toJson(limitGateNewList));
			for (int i = 0; i < limitGateOldList.size(); i++) {
				GateOfLimitItem glo = limitGateOldList.get(i);
				GateOfLimitItem gln = limitGateNewList.get(i);
				//原字段信息

				String limit_numO = glo.getLimit_num()==null?"":glo.getLimit_num();
				String ed_channel_nameO = glo.getEd_channel_name();
				String category_idO = glo.getCategory_id();
				//新字段信息
				String limit_numN = gln.getLimit_num();
				String ed_channel_nameN = gln.getEd_channel_name();
				String category_idN = gln.getCategory_id();
				//对比是否有改动，如果有则记录到操作日志中
				if (!limit_numO.equals(limit_numN)) {
					content += "原数据[原品类：" + category_idO + "，原渠道：" + ed_channel_nameO + "，原额度："
							+ limit_numO + "；  新数据：实际品类：" + category_idN + "，实际渠道："
							+ ed_channel_nameN + "，实际额度：" + limit_numN + "],";
				}
			}

			//判断是否有更新，并插入操作日志表
			if (!content.equals("额度闸口设置-个别额度修改：")) {
				this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
						gate_way_channel, channel, category);
			}
		} else if (LIMITGATELOAN.equals(model)) {
			//额度闸口-拆借
			content += "额度闸口设置-额度拆借管理：";
			List<GateOfLimitItem> limitGateOldList = (List<GateOfLimitItem>) oldData;//原额度闸口数据
			List<GateOfLimitItem> limitGateNewList = (List<GateOfLimitItem>) newData;//新额度闸口数据
			for (int i = 0; i < limitGateOldList.size(); i++) {
				GateOfLimitItem glo = limitGateOldList.get(i);
				GateOfLimitItem gln = limitGateNewList.get(i);
				//原字段信息
				String loan_numO = glo.getLoan_num() == null ? "" : glo.getLoan_num();
				String ed_channel_nameO = glo.getEd_channel_name();
				String category_idO = glo.getCategory_id();
				//新字段信息
				String loan_numN = gln.getLoan_num() == null ? "" : gln.getLoan_num();
				String ed_channel_nameN = gln.getEd_channel_name();
				String category_idN = gln.getCategory_id();
				//对比是否有改动，如果有则记录到操作日志中
				if (!loan_numO.equals(loan_numN)) {
					content += "原数据[原品类：" + category_idO + "，原渠道：" + ed_channel_nameO + "，原拆借："
							+ loan_numO + "；  新数据：实际品类：" + category_idN + "，实际渠道："
							+ ed_channel_nameN + "，实际拆借：" + loan_numN + "],";
				}
			}

			//判断是否有更新，并插入操作日志表
			if (!content.equals("额度闸口设置-额度拆借管理：")) {
				this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
						gate_way_channel, channel, category);
			}
		} else if (RATEGATE.equals(model)) {
			//备料准确率闸口
			List<GateOfMaterialsAccRateItem> rateGateOldList = (List<GateOfMaterialsAccRateItem>) oldData;//原备料准确率闸口数据
			List<GateOfMaterialsAccRateItem> rateGateNewList = (List<GateOfMaterialsAccRateItem>) newData;//新备料准确率闸口数据

			for (int i = 0; i < rateGateOldList.size(); i++) {
				content = "备料准确率闸口设置：";
				GateOfMaterialsAccRateItem rgo = rateGateOldList.get(i);
				GateOfMaterialsAccRateItem rgn = rateGateNewList.get(i);
				//原字段信息
				String ed_channel_idO = rgo.getEd_channel_id();
				String ed_channel_nameO = rgo.getEd_channel_name();
				String category_idO = rgo.getCategory_id();
				String acc_rateO = rgo.getAcc_rate();
				String is_enabledO = rgo.getIs_enabled();
				String is_enabled_nameO = rgo.getIs_enabled_name();
				//新字段信息
				String ed_channel_idN = rgn.getEd_channel_id();
				String ed_channel_nameN = rgn.getEd_channel_name();
				String category_idN = rgn.getCategory_id();
				String acc_rateN = rgn.getAcc_rate();
				String is_enabledN = rgn.getIs_enabled();
				String is_enabled_nameN = rgn.getIs_enabled_name();
				//对比是否有改动，如果有则记录到操作日志中
				if (!ed_channel_idO.equals(ed_channel_idN) || !category_idO.equals(category_idN)
						|| !acc_rateO.equals(acc_rateN) || !is_enabledO.equals(is_enabledN)) {
					content += "原数据：" + is_enabled_nameO + "，渠道：" + ed_channel_nameO + "，品类："
							+ category_idO + "，备料准确率闸口：" + acc_rateO + "； 新数据："
							+ is_enabled_nameN + "，渠道：" + ed_channel_nameN + "，品类："
							+ category_idN + "，备料准确率闸口：" + acc_rateN;
					//插入操作日志表
					this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
							gate_way_channel, ed_channel_idN, category_idN);
				}
			}

			//判断是否有新增操作
			if ("2".equals(type)) {
				int sizeOld = rateGateOldList.size();//库中数据条数
				int sizeNew = rateGateNewList.size();//页面数据条数
				for (int j = sizeOld; j < sizeNew; j++) {
					content = "备料准确率闸口设置：";
					GateOfMaterialsAccRateItem rgnAdd = rateGateNewList.get(j);
					//新字段信息
					String ed_channel_idN = rgnAdd.getEd_channel_id();
					String ed_channel_nameN = rgnAdd.getEd_channel_name();
					String category_idN = rgnAdd.getCategory_id();
					String acc_rateN = rgnAdd.getAcc_rate();
					String is_enabled_nameN = rgnAdd.getIs_enabled_name();
					//拼接日志操作内容
					content += "新数据：" + is_enabled_nameN + "，渠道：" + ed_channel_nameN + "，品类："
							+ category_idN + "，备料准确率闸口：" + acc_rateN;
					//插入操作日志表
					this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
							gate_way_channel, ed_channel_idN, category_idN);

				}
			}
		} else if (CRMT2ORDER.equals(model)) {
			//判断操作类型
			if (tp == 1) {
				//海尔T+2订单数量修改
				content += "海尔：T+2订单填报-数量修改：";
				String t2_delivery_prediction = (String) oldData;
				String modifycount = (String) newData;
				if (!t2_delivery_prediction.equals(modifycount)) {
					content += "订单号：" + orderId + "[原订单填报数量：" + t2_delivery_prediction
							+ "，内部审批通过订单数量：" + modifycount + "]";
					this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
							gate_way_channel, channel, category);
				}
			} else {
				//海尔T+2订单删除
				content += "海尔T+2订单填报--删除订单操作";
				List<String> orderIdList = (List<String>) oldData;
				for (int i = 0; i < orderIdList.size(); i++) {
					orderId = orderIdList.get(i);
					content += "[订单号：" + orderId + "]";
					this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
							gate_way_channel, channel, category);
				}

			}
		} else if (CRMGVSORDER.equals(model)) {
			//判断操作类型
			if (tp == 1) {
				//海尔GVS订单数量修改
				content += "海尔：GVS订单填报-数量修改：";
				String GVS_delivery_prediction = (String) oldData;
				String modifycount = (String) newData;
				if (!GVS_delivery_prediction.equals(modifycount)) {
					content += "订单号：" + orderId + "[原订单填报数量：" + GVS_delivery_prediction
							+ "，内部审批通过订单数量：" + modifycount + "]";
					this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
							gate_way_channel, channel, category);
				}
			} else {
				//海尔GVS订单删除
				content += "海尔GVS订单填报--删除订单操作";
				List<String> orderIdList = (List<String>) oldData;
				for (int i = 0; i < orderIdList.size(); i++) {
					orderId = orderIdList.get(i);
					content += "[订单号：" + orderId + "]";
					this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
							gate_way_channel, channel, category);
				}
			}
		} else if (CGOT2ORDER.equals(model)) {
			//判断操作类型
			if (tp == 1) {
				//统帅T+2订单数量修改
				content += "统帅：CGOT+2订单填报-数量修改：";
				String t2_delivery_prediction = (String) oldData;
				String modifycount = (String) newData;
				if (!t2_delivery_prediction.equals(modifycount)) {
					content += "订单号：" + orderId + "[原订单填报数量：" + t2_delivery_prediction
							+ "，内部审批通过订单数量：" + modifycount + "]";
					this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
							gate_way_channel, channel, category);
				}
			} else {
				//统帅T+2订单删除
				content += "统帅T+2订单填报--删除订单操作";
				List<String> orderIdList = (List<String>) oldData;
				for (int i = 0; i < orderIdList.size(); i++) {
					orderId = orderIdList.get(i);
					content += "[订单号：" + orderId + "]";
					this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
							gate_way_channel, channel, category);
				}

			}

		} else if (DBMT2ORDER.equals(model)) {
			//判断操作类型
			if (tp == 1) {
				//新宝婴童T+2订单数量修改
				content += "新宝婴童：DBMT+2订单填报-数量修改：";
				String t2_delivery_prediction = (String) oldData;
				String modifycount = (String) newData;
				if (!t2_delivery_prediction.equals(modifycount)) {
					content += "订单号：" + orderId + "[原订单填报数量：" + t2_delivery_prediction
							+ "，内部审批通过订单数量：" + modifycount + "]";
					this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
							gate_way_channel, channel, category);
				}
			} else {
				//新宝婴童T+2订单删除
				content += "新宝婴童T+2订单填报--删除订单操作";
				List<String> orderIdList = (List<String>) oldData;
				for (int i = 0; i < orderIdList.size(); i++) {
					orderId = orderIdList.get(i);
					content += "[订单号：" + orderId + "]";
					this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
							gate_way_channel, channel, category);
				}

			}

		} else {
			//海尔预测备料填报-导入
			content += "预测备料填报-导入";
			//插入操作日志表
			this.log(orderId, tp, content, operUserName, operUserId, jude_way_channel,
					gate_way_channel, channel, category);
		}

	}
}
