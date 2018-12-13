package com.haier.distribute.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.distribute.data.model.ProductCenterDTO;
import com.haier.distribute.data.model.ProductFeaturesDTO;
import com.haier.distribute.data.model.ProductGatefoldsDTO;
import com.haier.distribute.data.model.ProductOuterPackingBoxesDTO;
import com.haier.distribute.data.model.ProductPicturesDTO;
import com.haier.distribute.data.model.ProductSpecificationsDTO;
import com.haier.distribute.data.service.PopProductService;
import com.haier.distribute.data.service.ProductCenterService;
import com.haier.distribute.data.service.ProductFeatureService;
import com.haier.distribute.data.service.ProductGatefoldService;
import com.haier.distribute.data.service.ProductOuterPackingBoxeService;
import com.haier.distribute.data.service.ProductPictureService;
import com.haier.distribute.data.service.ProductSpecificationService;
import com.haier.distribute.model.Request;
import com.haier.distribute.model.Result;
import com.haier.distribute.service.PushProductInfoService;
import com.thoughtworks.xstream.XStream;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PushProductInfoServiceImpl implements PushProductInfoService {
	// 商品基本信息
	@Resource
	private ProductCenterService productCenterService;
	// 产品图
	@Autowired
	private ProductPictureService productPictureService;
	// 拉页图
	@Autowired
	private ProductGatefoldService productGatefoldService;
	// 特征图
	@Autowired
	private ProductFeatureService productFeatureService;
	@Autowired
	private ProductOuterPackingBoxeService productOuterPackingBoxeService;
	// 规格参数
	@Autowired
	private ProductSpecificationService productSpecificationService;
	@Autowired
	private PopProductService popProductService;
	private static final Logger logger = LogManager.getLogger(PushProductInfoServiceImpl.class);

	// @Scheduled(cron = "*/10 * * * * ?")
	public JSONObject pushData(String url, Integer channelId) {
		Request request = new Request();
		List<Result> results = new ArrayList<>();
		List<String> skuList = popProductService.selectSkuByChannelId(channelId);
		List<ProductCenterDTO> productCenterDTOList = productCenterService.selectBySku(skuList);
		// List<ProductCenterDTO>
		// productCenterDTOList=productCenterService.selectAll();
		for (ProductCenterDTO productCenterDTO : productCenterDTOList) {
			Integer id = productCenterDTO.getId();
			List<ProductPicturesDTO> productPicturesDTOList = productPictureService.selectByProductId(id);
			List<ProductGatefoldsDTO> productGatefoldsDTOList = productGatefoldService.selectByProductId(id);
			List<ProductFeaturesDTO> productFeaturesDTOList = productFeatureService.selectByProductId(id);
			List<ProductOuterPackingBoxesDTO> productOuterPackingBoxesDTOList = productOuterPackingBoxeService
					.selectByProductId(id);
			List<ProductSpecificationsDTO> productSpecificationsDTOList = productSpecificationService
					.selectByProductId(id);
			Result result = new Result();
			result.setProductCenterDTO(productCenterDTO);
			result.setProductFeaturesDTOList(productFeaturesDTOList);
			result.setProductGatefoldsDTOList(productGatefoldsDTOList);
			result.setProductOuterPackingBoxesDTOList(productOuterPackingBoxesDTOList);
			result.setProductPicturesDTOList(productPicturesDTOList);
			result.setProductSpecificationsDTOList(productSpecificationsDTOList);
			results.add(result);
		}
		request.setList(results);
		// url="http://123.57.25.176:8080/api/productInfo/getProductInfo";
		// 创建httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);
		XStream xstream = new XStream();
		xstream.alias("request", Request.class);
		xstream.alias("result", Result.class);
		xstream.alias("productCenterDTO", ProductCenterDTO.class);
		xstream.alias("productPicturesDTO", com.haier.distribute.data.model.ProductPicturesDTO.class);
		xstream.alias("productGatefoldsDTO", ProductGatefoldsDTO.class);
		xstream.alias("productOuterPackingBoxesDTO", ProductOuterPackingBoxesDTO.class);
		xstream.alias("productSpecificationsDTO", ProductSpecificationsDTO.class);
		xstream.alias("productFeaturesDTO", ProductFeaturesDTO.class);
		String xml = xstream.toXML(request);
		HttpEntity rqentity = new StringEntity(xml, "utf-8");
		httpPost.setEntity(rqentity);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000)
				.setConnectionRequestTimeout(60000).setSocketTimeout(60000).build();
		httpPost.setConfig(requestConfig);
		int statusCode;
		JSONObject jsonObject = new JSONObject();
		try {
			CloseableHttpResponse response = httpClient.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				logger.info("此channelId:" + channelId + "推送数据成功");
				jsonObject.put("msg", "推送数据成功");
				jsonObject.put("isSuccess", true);
				return jsonObject;
			} else {
				logger.info("HTTP返回状态错误，状态码为：" + statusCode);
				jsonObject.put("msg", "推送数据失败;状态码为:" + statusCode);
				jsonObject.put("isSuccess", false);
				return jsonObject;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			jsonObject.put("msg", "推送数据失败;错误信息:" + e.getMessage());
			jsonObject.put("isSuccess", false);
			return jsonObject;
		}
	}
}
