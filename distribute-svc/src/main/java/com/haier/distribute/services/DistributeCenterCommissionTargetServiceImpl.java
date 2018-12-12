package com.haier.distribute.services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.CommissionTarget;
import com.haier.distribute.data.model.PopOrderProducts;
import com.haier.distribute.data.service.BrandsService;
import com.haier.distribute.data.service.CommissionTargetService;
import com.haier.distribute.data.service.DepartmentProductTypeService;
import com.haier.distribute.data.service.OrderProductsService;
import com.haier.distribute.data.service.ProductCatesService;
import com.haier.distribute.data.service.ProductsService;
import com.haier.distribute.service.DistributeCenterCommissionTargetService;

/**
 * 佣金目标
 * 孙玉凯 2017-12-01
 * @author sunyukai
 *
 */

/***
 * 主表分页查询
 */
@Service
public class DistributeCenterCommissionTargetServiceImpl implements DistributeCenterCommissionTargetService {

	@Autowired
	private com.haier.distribute.data.service.CommissionTargetService commission_targetDao;

	@Autowired
	private OrderProductsService orderProductsService;

	@Autowired
	private CommissionTargetService commissionTargetService;

	@Autowired
	private DepartmentProductTypeService departmentProductTypeService;


	@Override
	public JSONObject findCommission_targetList(PagerInfo pager, CommissionTarget condition) {
		List<CommissionTarget> list = commission_targetDao.selectCommission_productListf(condition, pager.getStart(),
				pager.getPageSize());
		int total = commission_targetDao.getPagerCountS(condition);
		JSONArray res = new JSONArray();

		for (Object o : list) {
			CommissionTarget dto = (CommissionTarget) o;
			JSONObject json = new JSONObject();
			json.put("id", dto.getId());

			json.put("channelId", dto.getChannelId());
			if (condition.getChannelType().equals("1")) {
				json.put("channelName", dto.getDistributionName());
			} else {
				json.put("channelName", dto.getChannelName());
			}
			json.put("brandId", dto.getBrandId());
			json.put("brandName", dto.getBrandName());
			json.put("categoryId", dto.getCategoryId());
			json.put("categoryName", dto.getCateName());
			json.put("policyYear", dto.getPolicyYear());
			json.put("targetValue1", dto.getTargetValue1());
			json.put("targetValue2", dto.getTargetValue2());
			json.put("targetValue3", dto.getTargetValue3());
			json.put("targetValue4", dto.getTargetValue4());
			json.put("targetValue5", dto.getTargetValue5());
			json.put("targetValue6", dto.getTargetValue6());
			json.put("targetValue7", dto.getTargetValue7());
			json.put("targetValue8", dto.getTargetValue8());
			json.put("targetValue9", dto.getTargetValue9());
			json.put("targetValue10", dto.getTargetValue10());
			json.put("targetValue11", dto.getTargetValue11());
			json.put("targetValue12", dto.getTargetValue12());

			json.put("targetPolicy1", dto.getTargetValue1());
			json.put("targetPolicy2", dto.getTargetPolicy2());
			json.put("targetPolicy3", dto.getTargetPolicy3());
			json.put("targetPolicy4", dto.getTargetPolicy4());
			json.put("targetPolicy5", dto.getTargetPolicy5());
			json.put("targetPolicy6", dto.getTargetPolicy6());
			json.put("targetPolicy7", dto.getTargetPolicy7());
			json.put("targetPolicy8", dto.getTargetPolicy8());
			json.put("targetPolicy9", dto.getTargetPolicy9());
			json.put("targetPolicy10", dto.getTargetPolicy10());
			json.put("targetPolicy11", dto.getTargetPolicy11());
			json.put("targetPolicy12", dto.getTargetPolicy12());
			json.put("targetValueSum", dto.getTargetValueSum());
			json.put("createTime", dto.getCreateTime());
			json.put("updateTime", dto.getUpdateTime());
			json.put("remark", dto.getRemark());
			json.put("channelType", dto.getChannelType());
			json.put("channelId", dto.getChannelId());
			json.put("quarterStandardReward", dto.getQuarterStandardReward());
			json.put("yearStandardReward", dto.getYearStandardReward());
			res.add(json);
		}
		return jsonResult(res, total);

	}

	private <T> JSONObject jsonResult(List<T> list, long total) {
		JSONObject json = new JSONObject();
		json.put("total", total);
		if (list == null || list.isEmpty()) {
			json.put("rows", new ArrayList<T>());
		} else {
			json.put("rows", list);
		}
		return json;
	}

	@Override
	public int addCommission(CommissionTarget commission) {
		commission.setCreateTime(new Date());
		commission_targetDao.insert(commission);
		return 1;
	}

	@Override
	public int updateCommission(CommissionTarget commission) {
		commission.setUpdateTime(new Date());
		commission_targetDao.updateByPrimaryKey(commission);
		return 1;
	}

	@Override
	public int deleteCommission(int id) {
		commission_targetDao.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public Boolean jiaoyanCommission(CommissionTarget commission) {
		List<CommissionTarget> list = commission_targetDao.jiaoyan(commission);
		if (list.size() > 0) {
			return false;
		}
		return true;
	}

	/***
	 * 季度佣金结算
	 * 
	 * @param pager
	 * @param categoryId
	 * @param startTime
	 * @param endTime
	 * @param policyYear
	 * @param channelId
	 * @param channelType
	 * @param brandId
	 * @return
	 */
	public JSONObject commission_product_invoice(PagerInfo pager, String categoryId, String startTime, String endTime,
			String policyYear, int channelId, int channelType, int brandId) {
		DecimalFormat df = new DecimalFormat("######0.00");
		List<PopOrderProducts> product = orderProductsService.commission_product_invoice(pager.getStart(),
				pager.getPageSize(), categoryId, startTime, endTime, policyYear, channelId, channelType, brandId);
		int total = commissionTargetService.getPagerCountInvoice(categoryId, startTime, endTime, policyYear, channelId,
				channelType, brandId);
		List<CommissionTarget> comm = commissionTargetService.selectCommission_target_invoice(pager.getStart(),
				pager.getPageSize(), categoryId, startTime, endTime, policyYear, channelId, channelType, brandId);

		JSONArray res = new JSONArray();
		double sum = 0;
		double yue1 = 0;
		double yue2 = 0;
		double yue3 = 0;
		double yue4 = 0;
		try {

			for (int i = 0; i < comm.size(); i++) {
				int v1 = comm.size();
				if (comm.size() > 0) {
					JSONObject json = new JSONObject();
					// 判断政策表是否不为空
					// 目标
					json.put("col08", comm.get(i).getCol06());
					json.put("col12", comm.get(i).getCol07());
					json.put("col16", comm.get(i).getCol08());
					json.put("col20", comm.get(i).getCol09());
					json.put("col24", comm.get(i).getTargetValueSum());
					// 政策
					json.put("col09", comm.get(i).getQuarterStandardReward());
					json.put("col13", comm.get(i).getQuarterStandardReward());
					json.put("col17", comm.get(i).getQuarterStandardReward());
					json.put("col21", comm.get(i).getQuarterStandardReward());
					json.put("col25", comm.get(i).getYearStandardReward());
					if (product.size() != 0) {
						for (int j = 0; j < product.size(); j++) {
							String c = comm.get(i).getCol10();
							int c1 = Integer.parseInt(product.get(j).getCol14());
							int b = comm.get(i).getBrandId();
							int b1 = product.get(j).getBrandId();
							int a = comm.get(i).getChannelId();
							json.put("col03", product.get(j).getCol11());
							int a1 = Integer.parseInt(product.get(j).getCol12());
							sum = Double.parseDouble(product.get(j).getCol11());
							yue1 = Double.parseDouble(product.get(j).getCol07());
							yue2 = Double.parseDouble(product.get(j).getCol08());
							yue3 = Double.parseDouble(product.get(j).getCol09());
							yue4 = Double.parseDouble(product.get(j).getCol10());

							String c11 = departmentProductTypeService.selectcode(c1);
							// 目标

							// 判断网单品类与政策表品类是否相同c == c1 &&
							if (c.equals(c11) && b == b1 && a == a1) {

								// 比较结算金额是否大于目标
								// 判断实际金额是否大于年目标
								if (sum * 0.0001 >= (comm.get(i).getTargetValueSum()).doubleValue()) {
									double year = (Double.parseDouble(product.get(j).getCol11())
											* (comm.get(i).getYearStandardReward()).doubleValue()) * 0.01;
									json.put("col26", df.format(year));
								} else {
									json.put("col26", "0");
								}
								// 判断1季度金额与目标值
								if (yue1 * 0.0001 >= Double.valueOf(comm.get(i).getCol06()).doubleValue()) {
									double yue6 = (Double.parseDouble(product.get(j).getCol07())
											* (comm.get(i).getQuarterStandardReward()).doubleValue()) * 0.01;
									json.put("col10", yue6);
								} else {
									json.put("col10", "0");
								}
								// 判断2季度金额与目标值
								if (yue2 * 0.0001 >= Double.valueOf(comm.get(i).getCol07()).doubleValue()) {
									double yue7 = (Double.parseDouble(product.get(j).getCol08())
											* (comm.get(i).getQuarterStandardReward()).doubleValue()) * 0.01;
									json.put("col14", df.format(yue7));
								} else {
									json.put("col14", "0");
								}
								// 判断3季度金额与目标值
								if (yue3 * 0.0001 >= Double.valueOf(comm.get(i).getCol08()).doubleValue()) {
									double yue8 = (Double.parseDouble(product.get(j).getCol09())
											* (comm.get(i).getQuarterStandardReward()).doubleValue()) * 0.01;
									json.put("col18", df.format(yue8));
								} else {
									json.put("col18", "0");
								}
								// 判断4季度金额与目标值
								double v = yue4 * 0.0001;
								double e = Double.valueOf(comm.get(i).getCol09()).doubleValue();
								if (yue4 * 0.0001 >= Double.valueOf(comm.get(i).getCol09()).doubleValue()) {
									double yue9 = (Double.parseDouble(product.get(j).getCol10())
											* (comm.get(i).getQuarterStandardReward()).doubleValue()) * 0.01;
									json.put("col22", df.format(yue9));
								} else {
									json.put("col22", "0");
								}
								// 结算金额
								json.put("col07", Double.valueOf(product.get(j).getCol07()).doubleValue() * 0.0001);
								json.put("col11", Double.valueOf(product.get(j).getCol08()).doubleValue() * 0.0001);
								json.put("col15", Double.valueOf(product.get(j).getCol09()).doubleValue() * 0.0001);
								json.put("col19", Double.valueOf(product.get(j).getCol10()).doubleValue() * 0.0001);
								json.put("col23", Double.valueOf(product.get(j).getCol11()).doubleValue() * 0.0001);

							}
						}
					}
					json.put("col04", comm.get(i).getCol04());
					json.put("col05", comm.get(i).getCol05());

					json.put("col06", comm.get(i).getCol03());
					res.add(json);
				}
			}
		} catch (Error e) {
			System.out.println("错误信息：    " + e);
		}
		return jsonResult(res, total);
	}

	/***
	 * 季度佣金重载 导出
	 * 
	 * @param categoryId
	 * @param policyYear
	 * @param channelId
	 * @param channelType
	 * @param brandId
	 * @return
	 */
	public JSONArray commission_product_invoice(String categoryId, String policyYear, int channelId, int channelType,
			int brandId) {
		DecimalFormat df = new DecimalFormat("######0.00");
		List<PopOrderProducts> product = orderProductsService.commission_product_invoice1(categoryId, policyYear,
				channelId, channelType, brandId);

		List<CommissionTarget> comm = commissionTargetService.selectCommission_target_invoice2(categoryId, policyYear,
				channelId, channelType, brandId);

		JSONArray res = new JSONArray();
		double sum = 0;
		double yue1 = 0;
		double yue2 = 0;
		double yue3 = 0;
		double yue4 = 0;
		try {

			for (int i = 0; i < comm.size(); i++) {
				int v1 = comm.size();
				if (comm.size() > 0) {
					JSONObject json = new JSONObject();
					// 判断政策表是否不为空
					// 目标
					json.put("col08", comm.get(i).getCol06());
					json.put("col12", comm.get(i).getCol07());
					json.put("col16", comm.get(i).getCol08());
					json.put("col20", comm.get(i).getCol09());
					json.put("col24", comm.get(i).getTargetValueSum());
					// 政策
					json.put("col09", comm.get(i).getQuarterStandardReward());
					json.put("col13", comm.get(i).getQuarterStandardReward());
					json.put("col17", comm.get(i).getQuarterStandardReward());
					json.put("col21", comm.get(i).getQuarterStandardReward());
					json.put("col25", comm.get(i).getYearStandardReward());
					if (product.size() != 0) {
						for (int j = 0; j < product.size(); j++) {
							String c = comm.get(i).getCol10();
							int c1 = Integer.parseInt(product.get(j).getCol14());
							int b = comm.get(i).getBrandId();
							int b1 = product.get(j).getBrandId();
							int a = comm.get(i).getChannelId();
							json.put("col03", product.get(j).getCol11());
							int a1 = Integer.parseInt(product.get(j).getCol12());
							sum = Double.parseDouble(product.get(j).getCol11());
							yue1 = Double.parseDouble(product.get(j).getCol07());
							yue2 = Double.parseDouble(product.get(j).getCol08());
							yue3 = Double.parseDouble(product.get(j).getCol09());
							yue4 = Double.parseDouble(product.get(j).getCol10());

							String c11 = departmentProductTypeService.selectcode(c1);
							// 目标

							// 判断网单品类与政策表品类是否相同c == c1 &&
							if (c.equals(c11) && b == b1 && a == a1) {

								// 比较结算金额是否大于目标
								// 判断实际金额是否大于年目标
								if (sum * 0.0001 >= (comm.get(i).getTargetValueSum()).doubleValue()) {
									double year = (Double.parseDouble(product.get(j).getCol11())
											* (comm.get(i).getYearStandardReward()).doubleValue()) * 0.01;
									json.put("col26", df.format(year));
								} else {
									json.put("col26", "0");
								}
								// 判断1季度金额与目标值
								if (yue1 * 0.0001 >= Double.valueOf(comm.get(i).getCol06()).doubleValue()) {
									double yue6 = (Double.parseDouble(product.get(j).getCol07())
											* (comm.get(i).getQuarterStandardReward()).doubleValue()) * 0.01;
									json.put("col10", yue6);
								} else {
									json.put("col10", "0");
								}
								// 判断2季度金额与目标值
								if (yue2 * 0.0001 >= Double.valueOf(comm.get(i).getCol07()).doubleValue()) {
									double yue7 = (Double.parseDouble(product.get(j).getCol08())
											* (comm.get(i).getQuarterStandardReward()).doubleValue()) * 0.01;
									json.put("col14", df.format(yue7));
								} else {
									json.put("col14", "0");
								}
								// 判断3季度金额与目标值
								if (yue3 * 0.0001 >= Double.valueOf(comm.get(i).getCol08()).doubleValue()) {
									double yue8 = (Double.parseDouble(product.get(j).getCol09())
											* (comm.get(i).getQuarterStandardReward()).doubleValue()) * 0.01;
									json.put("col18", df.format(yue8));
								} else {
									json.put("col18", "0");
								}
								// 判断4季度金额与目标值
								double v = yue4 * 0.0001;
								double e = Double.valueOf(comm.get(i).getCol09()).doubleValue();
								if (yue4 * 0.0001 >= Double.valueOf(comm.get(i).getCol09()).doubleValue()) {
									double yue9 = (Double.parseDouble(product.get(j).getCol10())
											* (comm.get(i).getQuarterStandardReward()).doubleValue()) * 0.01;
									json.put("col22", df.format(yue9));
								} else {
									json.put("col22", "0");
								}
								// 结算金额
								json.put("col07",
										df.format(Double.valueOf(product.get(j).getCol07()).doubleValue() * 0.0001));
								json.put("col11",
										df.format(Double.valueOf(product.get(j).getCol08()).doubleValue() * 0.0001));
								json.put("col15",
										df.format(Double.valueOf(product.get(j).getCol09()).doubleValue() * 0.0001));
								json.put("col19",
										df.format(Double.valueOf(product.get(j).getCol10()).doubleValue() * 0.0001));
								json.put("col23",
										df.format(Double.valueOf(product.get(j).getCol11()).doubleValue() * 0.0001));
							} else {
								json.put("col26", "0.00");
								json.put("col10", "0.00");
								json.put("col14", "0.00");
								json.put("col18", "0.00");
								json.put("col22", "0.00");
							}
						}
					}
					json.put("col04", comm.get(i).getCol04());
					json.put("col05", comm.get(i).getCol05());

					json.put("col06", comm.get(i).getCol03());
					res.add(json);
				}
			}
		} catch (Error e) {
			System.out.println("错误信息：    " + e);
		}
		return res;
	}

	/***
	 * 月度佣金结算
	 * 
	 * @param pager
	 * @param categoryId
	 * @param startTime
	 * @param endTime
	 * @param policyYear
	 * @param channelId
	 * @param channelType
	 * @param brandId
	 * @return
	 */
	public JSONObject commission_product_invoice1(PagerInfo pager, String categoryId, String startTime, String endTime,
			String policyYear, int channelId, int channelType, int brandId) {
		DecimalFormat df = new DecimalFormat("######0.00");
		List<PopOrderProducts> product = orderProductsService.commission_product_invoice2(pager.getStart(),
				pager.getPageSize(), categoryId, startTime, endTime, policyYear, channelId, channelType, brandId);
		int total = commissionTargetService.getPagerCountInvoice(categoryId, startTime, endTime, policyYear, channelId,
				channelType, brandId);
		List<CommissionTarget> comm = commissionTargetService.selectCommission_target_invoice1(pager.getStart(),
				pager.getPageSize(), categoryId, startTime, endTime, policyYear, channelId, channelType, brandId);

		JSONArray res = new JSONArray();
		double sum = 0;
		double yue1 = 0;
		double yue2 = 0;
		double yue3 = 0;
		double yue4 = 0;
		double yue5 = 0;
		double yue6 = 0;
		double yue7 = 0;
		double yue8 = 0;
		double yue9 = 0;
		double yue10 = 0;
		double yue11 = 0;
		double yue12 = 0;
		try {

			for (int i = 0; i < comm.size(); i++) {
				int v1 = comm.size();
				if (comm.size() > 0) {
					JSONObject json = new JSONObject();
					// 判断政策表是否不为空
					// 目标
					json.put("col08", comm.get(i).getTargetValue1());
					json.put("col12", comm.get(i).getTargetValue2());
					json.put("col16", comm.get(i).getTargetValue3());
					json.put("col20", comm.get(i).getTargetValue4());
					json.put("col24", comm.get(i).getTargetValue5());
					json.put("col28", comm.get(i).getTargetValue6());
					json.put("col32", comm.get(i).getTargetValue7());
					json.put("col36", comm.get(i).getTargetValue8());
					json.put("col40", comm.get(i).getTargetValue9());
					json.put("col44", comm.get(i).getTargetValue10());
					json.put("col48", comm.get(i).getTargetValue11());
					json.put("col52", comm.get(i).getTargetValue12());
					json.put("col56", comm.get(i).getTargetValueSum());
					// 政策
					json.put("col09", comm.get(i).getTargetPolicy1());
					json.put("col13", comm.get(i).getTargetPolicy2());
					json.put("col17", comm.get(i).getTargetPolicy3());
					json.put("col21", comm.get(i).getTargetPolicy4());
					json.put("col25", comm.get(i).getTargetPolicy5());
					json.put("col29", comm.get(i).getTargetPolicy6());
					json.put("col33", comm.get(i).getTargetPolicy7());
					json.put("col37", comm.get(i).getTargetPolicy8());
					json.put("col41", comm.get(i).getTargetPolicy9());
					json.put("col45", comm.get(i).getTargetPolicy10());
					json.put("col49", comm.get(i).getTargetPolicy11());
					json.put("col53", comm.get(i).getTargetPolicy12());
					json.put("col57", comm.get(i).getYearStandardReward());
					if (product.size() != 0) {
						for (int j = 0; j < product.size(); j++) {
							String c = comm.get(i).getCol10();
							int c1 = Integer.parseInt(product.get(j).getCol14());
							int b = comm.get(i).getBrandId();
							int b1 = product.get(j).getBrandId();
							int a = comm.get(i).getChannelId();
							json.put("col03", product.get(j).getCol11());
							int a1 = Integer.parseInt(product.get(j).getCol12());
							sum = Double.parseDouble(product.get(j).getCol11());
							yue1 = Double.parseDouble(product.get(j).getCol07());
							yue2 = Double.parseDouble(product.get(j).getCol08());
							yue3 = Double.parseDouble(product.get(j).getCol09());
							yue4 = Double.parseDouble(product.get(j).getCol10());
							yue5 = Double.parseDouble(product.get(j).getCol14());
							yue6 = Double.parseDouble(product.get(j).getCol15());
							yue7 = Double.parseDouble(product.get(j).getCol16());
							yue8 = Double.parseDouble(product.get(j).getCol17());
							yue9 = Double.parseDouble(product.get(j).getCol18());
							yue10 = Double.parseDouble(product.get(j).getCol19());
							yue11 = Double.parseDouble(product.get(j).getCol20());
							yue12 = Double.parseDouble(product.get(j).getCol21());
							String c11 = departmentProductTypeService.selectcode(c1);
							// 目标

							// 判断网单品类与政策表品类是否相同c == c1 &&
							if (c.equals(c11) && b == b1 && a == a1) {

								// 比较结算金额是否大于目标
								// 判断实际金额是否大于年目标
								if (sum * 0.0001 >= (comm.get(i).getTargetValueSum()).doubleValue()) {
									double year = (Double.parseDouble(product.get(j).getCol11())
											* (comm.get(i).getYearStandardReward()).doubleValue()) * 0.01;
									json.put("col58", df.format(year));
								} else {
									json.put("col58", "0");
								}
								// 判断1月金额与目标值
								if (yue1 * 0.0001 >= (comm.get(i).getTargetValue1()).doubleValue()) {
									double m1 = (Double.parseDouble(product.get(j).getCol07())
											* (comm.get(i).getTargetPolicy1()).doubleValue()) * 0.01;
									json.put("col10", m1);
								} else {
									json.put("col10", "0");
								}
								// 判断2月金额与目标值
								if (yue2 * 0.0001 >= (comm.get(i).getTargetValue2()).doubleValue()) {
									double m2 = (Double.parseDouble(product.get(j).getCol08())
											* (comm.get(i).getTargetPolicy2()).doubleValue()) * 0.01;
									json.put("col14", m2);
								} else {
									json.put("col14", "0");
								}
								// 判断3月金额与目标值
								if (yue3 * 0.0001 >= (comm.get(i).getTargetValue3()).doubleValue()) {
									double m3 = (Double.parseDouble(product.get(j).getCol09())
											* (comm.get(i).getTargetPolicy3()).doubleValue()) * 0.01;
									json.put("col18", m3);
								} else {
									json.put("col18", "0");
								}
								// 判断4月金额与目标值
								if (yue4 * 0.0001 >= (comm.get(i).getTargetValue4()).doubleValue()) {
									double m4 = (Double.parseDouble(product.get(j).getCol10())
											* (comm.get(i).getTargetPolicy4()).doubleValue()) * 0.01;
									json.put("col22", m4);
								} else {
									json.put("col22", "0");
								}

								// 判断5月金额与目标值
								if (yue5 * 0.0001 >= (comm.get(i).getTargetValue5()).doubleValue()) {
									double m5 = (Double.parseDouble(product.get(j).getCol14())
											* (comm.get(i).getTargetPolicy5()).doubleValue()) * 0.01;
									json.put("col26", m5);
								} else {
									json.put("col26", "0");
								}
								// 判断6月金额与目标值
								if (yue6 * 0.0001 >= (comm.get(i).getTargetValue6()).doubleValue()) {
									double m6 = (Double.parseDouble(product.get(j).getCol15())
											* (comm.get(i).getTargetPolicy6()).doubleValue()) * 0.01;
									json.put("col30", m6);
								} else {
									json.put("col30", "0");
								}
								// 判断7月金额与目标值
								if (yue7 * 0.0001 >= (comm.get(i).getTargetValue7()).doubleValue()) {
									double m7 = (Double.parseDouble(product.get(j).getCol16())
											* (comm.get(i).getTargetPolicy7()).doubleValue()) * 0.01;
									json.put("col34", m7);
								} else {
									json.put("col34", "0");
								}
								// 判断8月金额与目标值
								if (yue8 * 0.0001 >= (comm.get(i).getTargetValue8()).doubleValue()) {
									double m8 = (Double.parseDouble(product.get(j).getCol17())
											* (comm.get(i).getTargetPolicy8()).doubleValue()) * 0.01;
									json.put("col38", m8);
								} else {
									json.put("col38", "0");
								}
								// 判断9月金额与目标值
								if (yue9 * 0.0001 >= (comm.get(i).getTargetValue9()).doubleValue()) {
									double m9 = (Double.parseDouble(product.get(j).getCol18())
											* (comm.get(i).getTargetPolicy9()).doubleValue()) * 0.01;
									json.put("col42", m9);
								} else {
									json.put("col42", "0");
								}
								// 判断10月金额与目标值
								if (yue10 * 0.0001 >= (comm.get(i).getTargetValue10()).doubleValue()) {
									double m10 = (Double.parseDouble(product.get(j).getCol09())
											* (comm.get(i).getTargetPolicy10()).doubleValue()) * 0.01;
									json.put("col46", m10);
								} else {
									json.put("col46", "0");
								}
								// 判断11月金额与目标值
								if (yue11 * 0.0001 >= (comm.get(i).getTargetValue11()).doubleValue()) {
									double m11 = (Double.parseDouble(product.get(j).getCol10())
											* (comm.get(i).getTargetPolicy11()).doubleValue()) * 0.01;
									json.put("col50", m11);
								} else {
									json.put("col50", "0");
								}
								// 判断12月金额与目标值
								if (yue12 * 0.0001 >= (comm.get(i).getTargetValue12()).doubleValue()) {
									double m12 = (Double.parseDouble(product.get(j).getCol11())
											* (comm.get(i).getTargetPolicy6()).doubleValue()) * 0.01;
									json.put("col54", m12);
								} else {
									json.put("col54", "0");
								}
								// 结算金额
								json.put("col07", Double.valueOf(product.get(j).getCol07()).doubleValue() * 0.0001);
								json.put("col11", Double.valueOf(product.get(j).getCol08()).doubleValue() * 0.0001);
								json.put("col15", Double.valueOf(product.get(j).getCol09()).doubleValue() * 0.0001);
								json.put("col19", Double.valueOf(product.get(j).getCol10()).doubleValue() * 0.0001);
								json.put("col23", Double.valueOf(product.get(j).getCol14()).doubleValue() * 0.0001);
								json.put("col27", Double.valueOf(product.get(j).getCol15()).doubleValue() * 0.0001);
								json.put("col31", Double.valueOf(product.get(j).getCol16()).doubleValue() * 0.0001);
								json.put("col35", Double.valueOf(product.get(j).getCol17()).doubleValue() * 0.0001);
								json.put("col39", Double.valueOf(product.get(j).getCol18()).doubleValue() * 0.0001);
								json.put("col43", Double.valueOf(product.get(j).getCol19()).doubleValue() * 0.0001);
								json.put("col47", Double.valueOf(product.get(j).getCol20()).doubleValue() * 0.0001);
								json.put("col51", Double.valueOf(product.get(j).getCol21()).doubleValue() * 0.0001);
								json.put("col55", Double.valueOf(product.get(j).getCol11()).doubleValue() * 0.0001);

							}
						}
					}
					json.put("col04", comm.get(i).getCol04());
					json.put("col05", comm.get(i).getCol05());

					json.put("col06", comm.get(i).getCol03());
					res.add(json);
				}
			}
		} catch (Error e) {
			System.out.println("错误信息：    " + e);
		}
		return jsonResult(res, total);
	}

	/***
	 * 重载月度结算 导出
	 * 
	 * @param categoryId
	 * @param policyYear
	 * @param channelId
	 * @param channelType
	 * @param brandId
	 * @return
	 */
	public JSONArray commission_product_invoice1(String categoryId, String policyYear, int channelId, int channelType,
			int brandId) {
		DecimalFormat df = new DecimalFormat("######0.00");
		List<PopOrderProducts> product = orderProductsService.commission_product_invoice3(categoryId, policyYear,
				channelId, channelType, brandId);
		List<CommissionTarget> comm = commissionTargetService.selectCommission_target_invoice3(categoryId, policyYear,
				channelId, channelType, brandId);

		JSONArray res = new JSONArray();
		double sum = 0;
		double yue1 = 0;
		double yue2 = 0;
		double yue3 = 0;
		double yue4 = 0;
		double yue5 = 0;
		double yue6 = 0;
		double yue7 = 0;
		double yue8 = 0;
		double yue9 = 0;
		double yue10 = 0;
		double yue11 = 0;
		double yue12 = 0;
		try {

			for (int i = 0; i < comm.size(); i++) {
				int v1 = comm.size();
				if (comm.size() > 0) {
					JSONObject json = new JSONObject();
					// 判断政策表是否不为空
					// 目标
					json.put("col08", comm.get(i).getTargetValue1());
					json.put("col12", comm.get(i).getTargetValue2());
					json.put("col16", comm.get(i).getTargetValue3());
					json.put("col20", comm.get(i).getTargetValue4());
					json.put("col24", comm.get(i).getTargetValue5());
					json.put("col28", comm.get(i).getTargetValue6());
					json.put("col32", comm.get(i).getTargetValue7());
					json.put("col36", comm.get(i).getTargetValue8());
					json.put("col40", comm.get(i).getTargetValue9());
					json.put("col44", comm.get(i).getTargetValue10());
					json.put("col48", comm.get(i).getTargetValue11());
					json.put("col52", comm.get(i).getTargetValue12());
					json.put("col56", comm.get(i).getTargetValueSum());
					// 政策
					json.put("col09", comm.get(i).getTargetPolicy1());
					json.put("col13", comm.get(i).getTargetPolicy2());
					json.put("col17", comm.get(i).getTargetPolicy3());
					json.put("col21", comm.get(i).getTargetPolicy4());
					json.put("col25", comm.get(i).getTargetPolicy5());
					json.put("col29", comm.get(i).getTargetPolicy6());
					json.put("col33", comm.get(i).getTargetPolicy7());
					json.put("col37", comm.get(i).getTargetPolicy8());
					json.put("col41", comm.get(i).getTargetPolicy9());
					json.put("col45", comm.get(i).getTargetPolicy10());
					json.put("col49", comm.get(i).getTargetPolicy11());
					json.put("col53", comm.get(i).getTargetPolicy12());
					json.put("col57", comm.get(i).getYearStandardReward());
					if (product.size() != 0) {
						for (int j = 0; j < product.size(); j++) {
							String c = comm.get(i).getCol10();
							int c1 = Integer.parseInt(product.get(j).getCol14());
							int b = comm.get(i).getBrandId();
							int b1 = product.get(j).getBrandId();
							int a = comm.get(i).getChannelId();
							json.put("col03", product.get(j).getCol11());
							int a1 = Integer.parseInt(product.get(j).getCol12());
							sum = Double.parseDouble(product.get(j).getCol11());
							yue1 = Double.parseDouble(product.get(j).getCol07());
							yue2 = Double.parseDouble(product.get(j).getCol08());
							yue3 = Double.parseDouble(product.get(j).getCol09());
							yue4 = Double.parseDouble(product.get(j).getCol10());
							yue5 = Double.parseDouble(product.get(j).getCol14());
							yue6 = Double.parseDouble(product.get(j).getCol15());
							yue7 = Double.parseDouble(product.get(j).getCol16());
							yue8 = Double.parseDouble(product.get(j).getCol17());
							yue9 = Double.parseDouble(product.get(j).getCol18());
							yue10 = Double.parseDouble(product.get(j).getCol19());
							yue11 = Double.parseDouble(product.get(j).getCol20());
							yue12 = Double.parseDouble(product.get(j).getCol21());
							String c11 = departmentProductTypeService.selectcode(c1);
							// 目标

							// 判断网单品类与政策表品类是否相同c == c1 &&
							if (c.equals(c11) && b == b1 && a == a1) {

								// 比较结算金额是否大于目标
								// 判断实际金额是否大于年目标
								if (sum * 0.0001 >= (comm.get(i).getTargetValueSum()).doubleValue()) {
									double year = (Double.parseDouble(product.get(j).getCol11())
											* (comm.get(i).getYearStandardReward()).doubleValue()) * 0.01;
									json.put("col58", df.format(year));
								} else {
									json.put("col58", "0");
								}
								// 判断1月金额与目标值
								if (yue1 * 0.0001 >= (comm.get(i).getTargetValue1()).doubleValue()) {
									double m1 = (Double.parseDouble(product.get(j).getCol07())
											* (comm.get(i).getTargetPolicy1()).doubleValue()) * 0.01;
									json.put("col10", df.format(m1));
								} else {
									json.put("col10", "0");
								}
								// 判断2月金额与目标值
								if (yue2 * 0.0001 >= (comm.get(i).getTargetValue2()).doubleValue()) {
									double m2 = (Double.parseDouble(product.get(j).getCol08())
											* (comm.get(i).getTargetPolicy2()).doubleValue()) * 0.01;
									json.put("col14", df.format(m2));
								} else {
									json.put("col14", "0");
								}
								// 判断3月金额与目标值
								if (yue3 * 0.0001 >= (comm.get(i).getTargetValue3()).doubleValue()) {
									double m3 = (Double.parseDouble(product.get(j).getCol09())
											* (comm.get(i).getTargetPolicy3()).doubleValue()) * 0.01;
									json.put("col18", df.format(m3));
								} else {
									json.put("col18", "0");
								}
								// 判断4月金额与目标值
								if (yue4 * 0.0001 >= (comm.get(i).getTargetValue4()).doubleValue()) {
									double m4 = (Double.parseDouble(product.get(j).getCol10())
											* (comm.get(i).getTargetPolicy4()).doubleValue()) * 0.01;
									json.put("col22", df.format(m4));
								} else {
									json.put("col22", "0");
								}

								// 判断5月金额与目标值
								if (yue5 * 0.0001 >= (comm.get(i).getTargetValue5()).doubleValue()) {
									double m5 = (Double.parseDouble(product.get(j).getCol14())
											* (comm.get(i).getTargetPolicy5()).doubleValue()) * 0.01;
									json.put("col26", df.format(m5));
								} else {
									json.put("col26", "0");
								}
								// 判断6月金额与目标值
								if (yue6 * 0.0001 >= (comm.get(i).getTargetValue6()).doubleValue()) {
									double m6 = (Double.parseDouble(product.get(j).getCol15())
											* (comm.get(i).getTargetPolicy6()).doubleValue()) * 0.01;
									json.put("col30", df.format(m6));
								} else {
									json.put("col30", "0");
								}
								// 判断7月金额与目标值
								if (yue7 * 0.0001 >= (comm.get(i).getTargetValue7()).doubleValue()) {
									double m7 = (Double.parseDouble(product.get(j).getCol16())
											* (comm.get(i).getTargetPolicy7()).doubleValue()) * 0.01;
									json.put("col34", df.format(m7));
								} else {
									json.put("col34", "0");
								}
								// 判断8月金额与目标值
								if (yue8 * 0.0001 >= (comm.get(i).getTargetValue8()).doubleValue()) {
									double m8 = (Double.parseDouble(product.get(j).getCol17())
											* (comm.get(i).getTargetPolicy8()).doubleValue()) * 0.01;
									json.put("col38", df.format(m8));
								} else {
									json.put("col38", "0");
								}
								// 判断9月金额与目标值
								if (yue9 * 0.0001 >= (comm.get(i).getTargetValue9()).doubleValue()) {
									double m9 = (Double.parseDouble(product.get(j).getCol18())
											* (comm.get(i).getTargetPolicy9()).doubleValue()) * 0.01;
									json.put("col42", df.format(m9));
								} else {
									json.put("col42", "0");
								}
								// 判断10月金额与目标值
								if (yue10 * 0.0001 >= (comm.get(i).getTargetValue10()).doubleValue()) {
									double m10 = (Double.parseDouble(product.get(j).getCol09())
											* (comm.get(i).getTargetPolicy10()).doubleValue()) * 0.01;
									json.put("col46", df.format(m10));
								} else {
									json.put("col46", "0");
								}
								// 判断11月金额与目标值
								if (yue11 * 0.0001 >= (comm.get(i).getTargetValue11()).doubleValue()) {
									double m11 = (Double.parseDouble(product.get(j).getCol10())
											* (comm.get(i).getTargetPolicy11()).doubleValue()) * 0.01;
									json.put("col50", df.format(m11));
								} else {
									json.put("col50", "0");
								}
								// 判断12月金额与目标值
								if (yue12 * 0.0001 >= (comm.get(i).getTargetValue12()).doubleValue()) {
									double m12 = (Double.parseDouble(product.get(j).getCol11())
											* (comm.get(i).getTargetPolicy6()).doubleValue()) * 0.01;
									json.put("col54", df.format(m12));
								} else {
									json.put("col54", "0");
								}
								// 结算金额
								json.put("col07",
										df.format(Double.valueOf(product.get(j).getCol07()).doubleValue() * 0.0001));
								json.put("col11",
										df.format(Double.valueOf(product.get(j).getCol08()).doubleValue() * 0.0001));
								json.put("col15",
										df.format(Double.valueOf(product.get(j).getCol09()).doubleValue() * 0.0001));
								json.put("col19",
										df.format(Double.valueOf(product.get(j).getCol10()).doubleValue() * 0.0001));
								json.put("col23",
										df.format(Double.valueOf(product.get(j).getCol14()).doubleValue() * 0.0001));
								json.put("col27",
										df.format(Double.valueOf(product.get(j).getCol15()).doubleValue() * 0.0001));
								json.put("col31",
										df.format(Double.valueOf(product.get(j).getCol16()).doubleValue() * 0.0001));
								json.put("col35",
										df.format(Double.valueOf(product.get(j).getCol17()).doubleValue() * 0.0001));
								json.put("col39",
										df.format(Double.valueOf(product.get(j).getCol18()).doubleValue() * 0.0001));
								json.put("col43",
										df.format(Double.valueOf(product.get(j).getCol19()).doubleValue() * 0.0001));
								json.put("col47",
										df.format(Double.valueOf(product.get(j).getCol20()).doubleValue() * 0.0001));
								json.put("col51",
										df.format(Double.valueOf(product.get(j).getCol21()).doubleValue() * 0.0001));
								json.put("col55",
										df.format(Double.valueOf(product.get(j).getCol11()).doubleValue() * 0.0001));

							}
						}
					}
					json.put("col04", comm.get(i).getCol04());
					json.put("col05", comm.get(i).getCol05());

					json.put("col06", comm.get(i).getCol03());
					res.add(json);
				}
			}
		} catch (Error e) {
			System.out.println("错误信息：    " + e);
		}
		return res;
	}
}
