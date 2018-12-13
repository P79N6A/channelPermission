package com.haier.svc.services;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.haier.common.ServiceResult;
import com.haier.eis.model.SkuStockRelation;
import com.haier.eis.service.SkuStockRelationService;
import com.haier.purchase.data.model.WwwStockSaveRequire;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.svc.model.WwwStockModel;
import com.haier.svc.purchase.getLastPurchasePriceFromGVSToEHAIER.GetLastPurchasePriceFromGVSToEHAIER;
import com.haier.svc.purchase.getLastPurchasePriceFromGVSToEHAIER.GetLastPurchasePriceFromGVSToEHAIER_Service;
import com.haier.svc.purchase.getLastPurchasePriceFromGVSToEHAIER.InType;
import com.haier.svc.purchase.getLastPurchasePriceFromGVSToEHAIER.OutType;
import com.haier.svc.service.ItemService;
import com.haier.svc.service.WwwStockGetService;
import com.haier.svc.util.HttpUtils;
import com.haier.svc.util.MD5util;

/**
 * 3w库存获取接口实现
 *
 * @author
 * @create 2017-03-27 17:27
 **/
@Service
@ConfigurationProperties(prefix = "stockget")
public class WwwStockGetServiceImpl implements WwwStockGetService {

	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(WwwStockGetServiceImpl.class);

	@Value("${stockget.url}")
	private String url;
	@Value("${stockget.appKey}")
	private String appKey;
	@Value("${stockget.secretKey}")
	private String secretKey;
	@Value("${stockget.wsdlLocation}")

	private String wsdlLocation;

	@Autowired
	private WwwStockModel wwwStockModel;

	@Autowired
	private SkuStockRelationService skuStockRelationService;

	@Autowired
	private ItemService itemService;

	@Override
	public String getWwwStock() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		// 获取前月的第一天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, 0);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String firstDay = format.format(cal_1.getTime());

		// 每次执行取前一天数据
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd");
		// 取价格接口sap每个月月初执行月结操作 所以当天不更新 使用前一天数据
		if (firstDay.equals(currentFormat.format(currentTime.getTime()))) {
			return null;
		}
		Calendar date = Calendar.getInstance();
		date.setTime(currentTime);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
		String dateString = null;
		try {
			Date beginDate = formatter.parse(formatter.format(date.getTime()));
			dateString = formatter.format(beginDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
//			dateString = "20180427";
			List<SkuStockRelation> result = skuStockRelationService.get3WskuToNum(dateString);
			if (result != null && result.size() > 0) {
				wwwStockModel.delete();
				List<WwwStockSaveRequire> list = new ArrayList<WwwStockSaveRequire>();
				List<InType> list1 = new ArrayList<InType>();
				for (SkuStockRelation ssr : result) {
					// 根据物料获取产品组信息
					// 使用取到的产品组获取品类信息
					ServiceResult<ItemAttribute> productNameList = null;
					ServiceResult<List<ItemBase>> departmentList = itemService.findItemBaseByMaterialId(ssr.getSku());
					String materialDescription = null;
					String materialCode = ssr.getSku();
					if (departmentList.getSuccess() && departmentList.getResult().size() > 0) {
						// 物料描述
						materialDescription = departmentList.getResult().get(0).getMaterialDescription();
						// 产品组
						String department = departmentList.getResult().get(0).getDepartment();
						// 如果是空调
						if ("CA".equals(department)) {
							if (!materialDescription.contains("套机")) {
								continue;
							}
						}
						productNameList = itemService.getItemAttributeByValueSetIdAndValue("ProductGroup", department);
					}

					WwwStockSaveRequire wwwStockSaveRequire = new WwwStockSaveRequire();
					wwwStockSaveRequire.setProductTypeName(
							productNameList == null ? null : productNameList.getResult().getCbsCategory());
					wwwStockSaveRequire.setEnChannelId("TB");
					wwwStockSaveRequire.setMaterial(materialCode);
					wwwStockSaveRequire.setAmount(ssr.getSkuNum());
					wwwStockSaveRequire.setCreatedDate(dateString);
					list.add(wwwStockSaveRequire);
					InType input = new InType();
					input.setMATNR(materialCode);
					list1.add(input);
				}
				int saveSum = wwwStockModel.save(list);

				List<WwwStockSaveRequire> list2 = new ArrayList<WwwStockSaveRequire>();
				if (saveSum > 0) {
					try {
						URL url = this.getClass()
								.getResource(wsdlLocation + "/GetLastPurchasePriceFromGVSToEHAIER.wsdl");
						GetLastPurchasePriceFromGVSToEHAIER_Service service = new GetLastPurchasePriceFromGVSToEHAIER_Service(
								url);
						GetLastPurchasePriceFromGVSToEHAIER soap = service.getGetLastPurchasePriceFromGVSToEHAIERSOAP();
						List<OutType> response = soap.getLastPurchasePriceFromGVSToEHAIER(list1, "EHAIER");

						for (int i = 0; i < response.size(); i++) {
							WwwStockSaveRequire wwwStockSaveRequire = new WwwStockSaveRequire();
							wwwStockSaveRequire.setMaterial(response.get(i).getMATNR());
							wwwStockSaveRequire.setPrice(response.get(i).getWRBTRBHS().toString() == null ? "0.00"
									: response.get(i).getWRBTRBHS().toString());
							list2.add(wwwStockSaveRequire);
						}
						wwwStockModel.update(list2);
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}

			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return "success";
	}

	@Override
	public List<WwwStockSaveRequire> select(String productTypeName, String enChannleId) {
		return wwwStockModel.select(productTypeName, enChannleId);
	}

}
